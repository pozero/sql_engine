package com.hitsz.controller;

import com.hitsz.mapper.UserDbInfoMapper;
import com.hitsz.pojo.UserDbInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 数据源控制器，用于处理用户配置数据源的请求
 */
@RestController
@CrossOrigin
public class DataSourceController {

    @Autowired
    private UserDbInfoMapper userDbInfoMapper;

//    @PostMapping("/datasource")
//    public String initDataSource(@RequestParam("dbName") String dbName,
//                                 @RequestParam("dbAddr") String dbAddr,
//                                 @RequestParam("user") String user,
//                                 @RequestParam("password") String password) {
//        userDbInfoMapper.insert(dbName, dbAddr, user, password);
//        return "数据源配置完成！";
//    }

    @PostMapping("/datasource")
    public String initDataSource(@RequestBody UserDbInfo userDbInfo) {
        userDbInfoMapper.insert(userDbInfo.getDbName(), userDbInfo.getDbAddr(), userDbInfo.getUser(), userDbInfo.getPassword());
        return "数据源配置完成！";
    }

    @GetMapping("/dbInfo")
    public List<UserDbInfo> getAllAddedDb() {
        return userDbInfoMapper.selectAll();
    }

    @GetMapping("/allDbInfo")
    public List<String> getAllAvailableDb() {
        return userDbInfoMapper.showAllDb();
    }
}
