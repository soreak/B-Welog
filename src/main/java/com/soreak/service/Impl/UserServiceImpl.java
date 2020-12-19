package com.soreak.service.Impl;

import com.soreak.dao.UserLoginDao;
import com.soreak.entity.UserEntity;
import com.soreak.service.UserService;
import com.soreak.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @program: welog
 * @author: soreak
 * @description:
 * @create: 2020-12-17 16:52
 **/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserLoginDao userLoginDao;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public UserEntity checkUser(String phone, String password) {
        return userLoginDao.checkUser(phone, MD5Utils.code(password));
    }

    @Override
    public UserEntity getUserInfo(String username) {
        return userLoginDao.getUserInfoByUsername(username);
    }
}
