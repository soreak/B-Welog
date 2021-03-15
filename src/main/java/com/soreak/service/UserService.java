package com.soreak.service;

import com.soreak.entity.UserEntity;

import java.util.Date;
import java.util.List;

/**
 * @program: welog
 * @author: soreak
 * @description:
 * @create: 2020-12-17 16:52
 **/
public interface UserService {
    /**
     * 通过账号密码找User
     * @param phone
     * @return
     */
    UserEntity checkUser(String phone, String password);

    UserEntity getUserInfo(String username);

    UserEntity findByPhone(String phone);

    UserEntity getUserById(Long id);

    int updateTime(Long id);

    int saveUser(UserEntity userEntity);

    List<UserEntity> getAllUser();

    List<UserEntity> searchUser(String nickname,String role);

    int deleteUser(Long id);

    List<UserEntity> searchUserByQuery(String query);
}
