package com.hitsz.service;

import com.alibaba.fastjson.JSONArray;

public interface QueryService {

    JSONArray query(String sql, Integer dbId, String operate);

}
