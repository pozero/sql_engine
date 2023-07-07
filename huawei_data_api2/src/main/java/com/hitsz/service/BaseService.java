package com.hitsz.service;

import cn.hutool.json.JSONUtil;
import com.hitsz.mapper.UserDbInfoMapper;
import com.hitsz.pojo.UserDbInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import static com.hitsz.constant.RedisConstant.KEY_DBINFO;

public abstract class BaseService {

    @Autowired
    protected StringRedisTemplate stringRedisTemplate;

    @Autowired
    protected UserDbInfoMapper userDbInfoMapper;

    /**
     * 根据数据库Id查询数据源信息
     */
    protected UserDbInfo getDbInfo(Integer dbId) {
        // 查询缓存
        String key = KEY_DBINFO + dbId;
        String dbInfoJson = stringRedisTemplate.opsForValue().get(key);

        UserDbInfo dbInfo;
        if (dbInfoJson == null) { // 缓存未命中，查询数据库
            if ((dbInfo = userDbInfoMapper.select(dbId)) == null) {
                System.out.println("无法根据dbId查询到数据源信息！");
                return null;
            }
            // 写入缓存
            stringRedisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(dbInfo));
        } else {
            dbInfo = JSONUtil.toBean(dbInfoJson, UserDbInfo.class);
        }

        return dbInfo;
    }
}
