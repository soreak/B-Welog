package com.soreak.service;

import com.soreak.entity.UserEntity;

/**
 * @program: welog
 * @author: soreak
 * @description: 注册Service
 * @create: 2020-12-19 14:32
 **/
public interface RegisterService {
    /**
     * 注册
     * @param userEntity
     * @return
     */
    public int insUser(UserEntity userEntity);

    /**
     * 手机号检测
     * @param phone
     * @return
     */
    public int findByPhone(String phone);

    /**
     * 用户名检测
     * @param username
     * @return
     */
    public int findByUsername(String username);
}
