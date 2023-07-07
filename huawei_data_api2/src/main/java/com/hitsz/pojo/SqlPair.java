package com.hitsz.pojo;

import lombok.Data;

/**
 * 根据url和method查询到用户定义好的sql语句和数据库id，封装在一个对象中返回
 */
@Data
public class SqlPair {
    private String userSql;

    private Integer dbId;
}
