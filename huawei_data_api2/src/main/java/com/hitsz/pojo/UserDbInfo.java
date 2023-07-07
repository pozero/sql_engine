package com.hitsz.pojo;

import lombok.Data;

/**
 * 用户的数据源信息
 */
@Data
public class UserDbInfo {
    private Integer id;

    private String dbName;

    /**
     * 数据库的ip和端口号，例如localhost:3306
     */
    private String dbAddr;

    private String user;

    private String password;
}
