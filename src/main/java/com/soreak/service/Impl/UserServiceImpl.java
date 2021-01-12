package com.soreak.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.soreak.dao.UserDao;
import com.soreak.entity.UserEntity;
import com.soreak.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @program: welog
 * @author: soreak
 * @description:
 * @create: 2020-12-17 16:52
 **/
@Service
public class UserServiceImpl
        implements UserService {



    @Autowired
    private UserDao userDao;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public UserEntity checkUser(String phone, String password) {
        return userDao.checkUser(phone, password);
    }

    @Override
    public UserEntity getUserInfo(String username) {
        return userDao.getUserInfoByUsername(username);
    }

    @Override
    public UserEntity findByPhone(String phone) {
        return userDao.selectOne(new QueryWrapper<UserEntity>().eq("phone", phone));
    }

    @Override
    public UserEntity getUserById(Long id) {
        return userDao.selectById(id);
    }

    @Override
    public int updateTime(Long id) {
        UserEntity entity = new UserEntity();

        entity.setId(id);
        entity.setUpdateTime(new Date());
        return userDao.updateById(entity);
    }


}
