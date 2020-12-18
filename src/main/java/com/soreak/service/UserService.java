package com.soreak.service;

import com.soreak.entity.User;

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
    User checkUser(String phone,String password);



}
