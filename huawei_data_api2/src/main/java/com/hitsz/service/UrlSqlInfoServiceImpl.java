package com.hitsz.service;

import cn.hutool.json.JSONUtil;
import com.hitsz.mapper.UrlSqlInfoMapper;
import com.hitsz.pojo.SqlPair;
import com.hitsz.pojo.UrlSqlInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.hitsz.constant.RedisConstant.*;

@Service
public class UrlSqlInfoServiceImpl implements UrlSqlInfoService {

    @Autowired
    private UrlSqlInfoMapper urlSqlInfoMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public SqlPair getSql(String url, String method) {
        if (url == null || method == null) {
            System.out.println("\nurl or method is null!\n");
            return null;
        }
        // 查询redis缓存
        String key = KEY_API + method + ":" + url;
        String sqlPairJson = stringRedisTemplate.opsForValue().get(key);
        // 缓存命中，直接返回
        if (sqlPairJson != null) {
//            System.out.println("redis缓存命中");
            return JSONUtil.toBean(sqlPairJson, SqlPair.class);
        }
        // 缓存未命中，进行数据库查询
        SqlPair sqlPair = urlSqlInfoMapper.selectSql(url, method);
        if (sqlPair == null) {
            return null;
        }
        // 查询结果写入redis缓存
        stringRedisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(sqlPair), EXP_TIME, TimeUnit.MILLISECONDS);
        return sqlPair;
    }

    @Override
    @Transactional
    public void insertOrUpdate(String url, String method, String sql, Integer dbId) {
        // 先判断该映射关系是否已经存在
        String key = KEY_EXISTED_URL + method + ":" + url;

        synchronized (this) {
            String existed = stringRedisTemplate.opsForValue().get(key);
            if (existed == null) {
                System.out.println("新增url-sql映射关系");
                urlSqlInfoMapper.insert(url, method, sql, dbId);
                // 写入缓存
                stringRedisTemplate.opsForValue().set(key, EXISTED_FLAG);
                System.out.println("existed_flag写入缓存");
                return;
            }
        }

        // 映射关系已存在，需要更新
        // 缓存更新策略，先更新数据库，再删除缓存
        System.out.println("更新sql语句");
        urlSqlInfoMapper.update(url, method, sql, dbId);
        stringRedisTemplate.delete(KEY_API + method + ":" + url);
        System.out.println("映射缓存已删除");
    }

    @Override
    @Transactional
    public synchronized void deleteExisted(String url, String method) {
        // 删除数据库中的数据
        urlSqlInfoMapper.delete(url, method);
        System.out.println("数据库删除完成");
        // 删除缓存
        stringRedisTemplate.delete(KEY_EXISTED_URL + method + ":" + url);
        stringRedisTemplate.delete(KEY_API + method + ":" + url);
        System.out.println("existed_flag、api缓存均已删除");
    }

    @Override
    public List<UrlSqlInfo> getUrlSqlInfoList() {
        return urlSqlInfoMapper.selectAll();
    }
}
