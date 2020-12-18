package com.soreak.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.soreak.entity.User;
import org.springframework.stereotype.Repository;

/**
 * @program: welog
 * @author: soreak
 * @description: 登录
 * @create: 2020-12-18 10:12
 **/
@Repository
public interface UserLoginDao{
    User checkUser(String phone,String password);
}
