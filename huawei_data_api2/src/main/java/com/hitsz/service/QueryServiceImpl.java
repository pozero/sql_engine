package com.hitsz.service;

import com.alibaba.fastjson.JSONArray;
import com.hitsz.dao.QueryDao;
import com.hitsz.pojo.UserDbInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class QueryServiceImpl extends BaseService implements QueryService {

    @Autowired
    private QueryDao queryDao;

    @Override
    public JSONArray query(String sql, Integer dbId, String operate) {
        UserDbInfo dbInfo = getDbInfo(dbId);
        if (dbInfo == null) return null;

        JSONArray res = null;
        try {

//            String key = "test:ten";
//            String cache = stringRedisTemplate.opsForValue().get(key);
//            if (cache != null) {
//                System.out.println("走缓存逻辑 解析");
//                System.out.println(cache.substring(0, 20));
//                return new JSONArray();
//            }
//            System.out.println("不走缓存逻辑");

            res = queryDao.doQuery(sql, dbInfo, operate);

//            stringRedisTemplate.opsForValue().set(key, JSONObject.toJSONString(res));
//            System.out.println("写入缓存");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return res;
    }
}
