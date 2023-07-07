package com.hitsz;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hitsz.mapper.UserDbInfoMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.hitsz.constant.RedisConstant.EXISTED_FLAG;
import static com.hitsz.constant.RedisConstant.KEY_EXISTED_URL;

@SpringBootTest
class HuaweiDataApi2ApplicationTests {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private UserDbInfoMapper userDbInfoMapper;

    @Test
    void contextLoads() {
    }

    @Test
    public void myTest() throws IOException {
        String method = "GET", url = "/users";
        String key1 = KEY_EXISTED_URL + method + ":" + url;

        method = "PUT"; url = "/yb";
        String key2 = KEY_EXISTED_URL + method + ":" + url;

        method = "GET"; url = "/bigdata";
        String key3 = KEY_EXISTED_URL + method + ":" + url;

        method = "GET"; url = "/bigdata2";
        String key4 = KEY_EXISTED_URL + method + ":" + url;

        method = "GET"; url = "/bigdata2/10";
        String key5 = KEY_EXISTED_URL + method + ":" + url;

        stringRedisTemplate.opsForValue().set(key1, EXISTED_FLAG);
        stringRedisTemplate.opsForValue().set(key2, EXISTED_FLAG);
        stringRedisTemplate.opsForValue().set(key3, EXISTED_FLAG);
        stringRedisTemplate.opsForValue().set(key4, EXISTED_FLAG);
        stringRedisTemplate.opsForValue().set(key5, EXISTED_FLAG);
    }

}
