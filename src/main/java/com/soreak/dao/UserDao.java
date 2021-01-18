package com.soreak.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.soreak.entity.UserEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: welog
 * @author: soreak
 * @description:
 * @create: 2020-12-17 16:07
 **/
@Repository
public interface UserDao extends BaseMapper<UserEntity> {
    @Select("select * from sk_user where phone = #{phone} and password = #{password}")
    UserEntity checkUser(@Param("phone") String phone, @Param("password") String password);

    @Select("select * from sk_user where phone = #{username}")
    UserEntity getUserInfoByUsername(@Param("username") String username);

    @Select("select * from sk_user")
    List<UserEntity> selectAll();
}
