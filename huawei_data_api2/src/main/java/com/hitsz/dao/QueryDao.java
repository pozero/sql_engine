package com.hitsz.dao;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.metadata.Table;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hitsz.pojo.UserDbInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.hitsz.constant.OperateConstant.*;
import static com.hitsz.util.JDBCUtils.getConnection;

/**
 * 处理查询语句
 */
@Component
@Slf4j
public class QueryDao {

    public JSONArray doQuery(String sql, UserDbInfo dbInfo, String operate) throws SQLException {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        JSONArray jsonArray = new JSONArray();

        try {
            // 获取连接；若为空则抛出异常
            String url = "jdbc:mysql://" + dbInfo.getDbAddr() + "/" + dbInfo.getDbName()
                    + "?characterEncoding=utf-8&serverTimezone=UTC";
            if ((conn = getConnection(url, dbInfo.getUser(), dbInfo.getPassword())) == null) {
                throw new Exception("Fail to get connection!");
            }

            statement = conn.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            statement.setFetchSize(Integer.MIN_VALUE);

            System.out.println(sql);

            rs = statement.executeQuery(sql);
            ResultSetMetaData metaData = rs.getMetaData();

            // 获取元数据：有哪些字段
            String[] columns = new String[metaData.getColumnCount()];
            for (int i = 0; i < columns.length; i++) {
                columns[i] = metaData.getColumnName(i + 1);
            }

            switch (operate) {
                case TO_CSV: {
                    String path = "./" + Thread.currentThread().getName().substring(14) + ".csv";
                    writeToCsv(path, columns, rs);
                    break;
                }
                case TO_XLSX: {
                    String path = "./excel数据.xlsx";
                    writeToXlsx(path, columns, rs);
                    break;
                }
                case SELECT:
                    // 将查询结果返回给前端网页
                    while (rs.next()) {
                        JSONObject json = new JSONObject();
                        for (int i = 0; i < columns.length; i++) {
                            json.put(columns[i], rs.getObject(i + 1));
                        }
                        jsonArray.add(json);
                    }
                    break;
                default:
                    log.warn("无效的操作！");
                    return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (rs != null) {
                rs.close();
            }
        }

        return jsonArray;
    }

    private void writeToCsv(String path, String[] columns, ResultSet rs) throws IOException, SQLException {
        File file = new File(path);
        FileWriter fw = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fw);

        StringBuilder sb = new StringBuilder();
        for (String column : columns) { // csv的第一行
            sb.append(column).append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        bw.write(sb.toString());
        bw.newLine();
        bw.flush();

        int cnt = 1;
        while (rs.next()) {
            cnt++;
            sb = new StringBuilder();
            for (int i = 0; i < columns.length; i++) {
                sb.append(rs.getObject(i + 1)).append(",");
            }
            sb.deleteCharAt(sb.length() - 1);
            bw.write(sb.toString());
            bw.newLine();
            bw.flush();
            if (cnt % 100000 == 0) {
                System.out.println(Thread.currentThread().getName() + " 已写入" + cnt + "条数据");
            }
        }
        System.out.println("csv文件已生成！");
    }

    private void writeToXlsx(String path, String[] columns, ResultSet rs) throws SQLException, IOException {
        OutputStream out = new FileOutputStream("./200w数据.xlsx");
        ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX);

        // 设置字段名
        Table table = new Table(1);
        List<List<String>> titles = new ArrayList<List<String>>();
        for (String column : columns) {
            titles.add(Arrays.asList(column));
        }
        table.setHead(titles);

        int sheetRows = 200000;  // 一个sheet的数据条数
        for (int i = 0; i < 10; i++) {
            Sheet sheet = new Sheet(i, 0);
            String sheetName = "Sheet" + (i + 1);
            sheet.setSheetName(sheetName);

            // 一个sheet中的数据
            List<List<String>> data = new ArrayList<>();
            for (int j = 0; j < sheetRows && rs.next(); j++) {
                // 一行数据
                List<String> row = new ArrayList<>();
                for (int k = 0; k < columns.length; k++) {
                    row.add(rs.getObject(k + 1).toString());
                }
                data.add(row);
            }
            writer.write0(data, sheet, table);
            log.info("{}条数据写入{}", sheetRows, sheetName);
        }

        writer.finish();
    }
}
