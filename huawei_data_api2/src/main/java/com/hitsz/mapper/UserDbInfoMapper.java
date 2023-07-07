package com.hitsz.mapper;

import com.hitsz.pojo.UserDbInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * sys_db : user_db_info
 */
@Mapper
public interface UserDbInfoMapper {

    @Insert("insert into user_db_info values" +
            "(0,#{dbName},#{dbAddr},#{user},#{password})")
    void insert(String dbName, String dbAddr, String user, String password);

    @Select("select * from user_db_info where id = #{dbId}")
    UserDbInfo select(Integer dbId);

}
