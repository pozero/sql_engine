package com.hitsz.service;

import com.hitsz.pojo.SqlPair;
import com.hitsz.pojo.UrlSqlInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UrlSqlInfoService {

    /**
     * 根据url和method获取对应的sql语句
     */
    SqlPair getSql(String url, String method);

    /**
     * 建立URL和SQL的映射；若不存在，插入；否则，更新
     */
    void insertOrUpdate(String url, String method, String sql, Integer dbId);

    /**
     * 删除URL和SQL的映射
     */
    void deleteExisted(String url, String method);

    /**
     * 进入网页时，加载所有已生成API接口信息到左侧边栏
     */
    List<UrlSqlInfo> getUrlSqlInfoList();

}
