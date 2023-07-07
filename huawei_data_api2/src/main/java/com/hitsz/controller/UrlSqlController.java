package com.hitsz.controller;

import com.hitsz.pojo.UrlSqlInfo;
import com.hitsz.service.UrlSqlInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 建立URL和SQL语句的映射关系，生成API接口，并持久化到数据库
 */
@RestController
@RequestMapping("/url/sql")
public class UrlSqlController {

    @Autowired
    private UrlSqlInfoService urlSqlInfoService;

    @PostMapping
    public String generateApi(@RequestParam("url") String url,
                              @RequestParam("method") String method,
                              @RequestParam("sql") String sql,
                              @RequestParam("dbId") Integer dbId) {
        urlSqlInfoService.insertOrUpdate(url, method, sql, dbId);
        return "api接口已生成！";
    }

    @DeleteMapping
    public String deleteApi(@RequestParam("url") String url, @RequestParam("method") String method) {
        urlSqlInfoService.deleteExisted(url, method);
        return "api接口已删除！";
    }

    @GetMapping
    public List<UrlSqlInfo> getApiList() {
        return urlSqlInfoService.getUrlSqlInfoList();
    }
}
