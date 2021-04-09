package com.soreak.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.soreak.dao.RegisterDao;
import com.soreak.entity.UserEntity;
import com.soreak.service.RegisterService;
import com.soreak.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @program: welog
 * @author: soreak
 * @description:
 * @create: 2020-12-19 14:33
 **/
@Service
public class RegisterServiceImpl implements RegisterService {


    @Autowired
    private RegisterDao registerDao;


    @Autowired
    private PasswordEncoder passwordEncoder;


    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int insUser(UserEntity userEntity) {
        userEntity.setRole("0");
        userEntity.setCreateTime(new Date());
        userEntity.setUpdateTime(new Date());
        userEntity.setAvatar("/imgs/avatar.jpg");
        String password = userEntity.getPassword();

        String newPasswd = passwordEncoder.encode(password);
        userEntity.setPassword(newPasswd);


        return registerDao.insert(userEntity);
    }

    @Override
    public int findByPhone(String phone) {
        int phonenum = registerDao.selectCount(new QueryWrapper<UserEntity>().eq("phone",phone));
        return phonenum;
    }

    @Override
    public int findByUsername(String username) {
        int name = registerDao.selectCount(new QueryWrapper<UserEntity>().eq("nickname",username));
        return name;
    }
}
