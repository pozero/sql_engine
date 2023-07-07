package com.hitsz.mapper;

import com.hitsz.pojo.SqlPair;
import com.hitsz.pojo.UrlSqlInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * sys_db : url_sql_info
 */
@Mapper
public interface UrlSqlInfoMapper {

    @Insert("insert into url_sql_info values (0,#{url},#{method},#{sql},#{dbId})")
    void insert(String url, String method, String sql, Integer dbId);

    @Update("update url_sql_info set user_sql = #{sql} , db_id = #{dbId} where url = #{url} and method = #{method}")
    void update(String url, String method, String sql, Integer dbId);

    @Select("select user_sql, db_id from url_sql_info where url = #{url} and method = #{method} limit 1")
    SqlPair selectSql(String url, String method);

    @Delete("delete from url_sql_info where url = #{url} and method = #{method}")
    void delete(String url, String method);

    @Select("select * from url_sql_info")
    List<UrlSqlInfo> selectAll();
}
