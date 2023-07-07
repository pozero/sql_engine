package com.hitsz.pojo;

import lombok.Data;

/**
 * 维护URL和SQL语句的映射关系
 */
@Data
public class UrlSqlInfo {
    private Integer id;

    private String url;

    private String method;

    private String userSql;

    private Integer dbId;
}
