package com.hitsz.dao;

import com.hitsz.pojo.UserDbInfo;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.hitsz.util.JDBCUtils.getConnection;

/**
 * 处理增、删、改
 */
@Component
public class UpdateDao {
    public int doUpdate(String sql, UserDbInfo dbInfo) throws SQLException {
        Connection conn = null;
        PreparedStatement statement = null;

        int updateRow = 0;  // 本次更新影响的行数

        try {
            // 获取连接；若为空则抛出异常
            String url = "jdbc:mysql://" + dbInfo.getDbAddr() + "/" + dbInfo.getDbName()
                    + "?characterEncoding=utf-8&serverTimezone=UTC";
            if ((conn = getConnection(url, dbInfo.getUser(), dbInfo.getPassword())) == null) {
                throw new Exception("Fail to get connection!");
            }

            statement = conn.prepareStatement(sql);

            updateRow = statement.executeUpdate(sql);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
            if (statement != null) {
                statement.close();
            }
        }

        return updateRow;
    }
}
