package com.hitsz.controller;

import com.hitsz.mapper.UserDbInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 数据源控制器，用于处理用户配置数据源的请求
 */
@RestController
public class DataSourceController {

    @Autowired
    private UserDbInfoMapper userDbInfoMapper;

    @PostMapping("/datasource")
    public String initDataSource(@RequestParam("dbName") String dbName,
                                 @RequestParam("dbAddr") String dbAddr,
                                 @RequestParam("user") String user,
                                 @RequestParam("password") String password) {
        userDbInfoMapper.insert(dbName, dbAddr, user, password);
        return "数据源配置完成！";
    }

}
