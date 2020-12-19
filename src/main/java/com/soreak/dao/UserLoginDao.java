package com.soreak.dao;

import com.soreak.entity.UserEntity;
import org.springframework.stereotype.Repository;

/**
 * @program: welog
 * @author: soreak
 * @description: 登录
 * @create: 2020-12-18 10:12
 **/
@Repository
public interface UserLoginDao{
    UserEntity checkUser(String phone, String password);
    UserEntity getUserInfoByUsername(String username);

}
