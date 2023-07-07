package com.hitsz.util;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * jdbc工具类
 */
public class JDBCUtils {

    private JDBCUtils() {}

    public static Connection getConnection(String url, String user, String password) throws Exception {
        // 加载驱动
        Class.forName("com.mysql.cj.jdbc.Driver");

        // 获取连接
        return DriverManager.getConnection(url, user, password);
    }
}
