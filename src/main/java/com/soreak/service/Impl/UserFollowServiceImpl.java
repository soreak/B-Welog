package com.soreak.service.Impl;

import com.soreak.dao.UserFollowDao;
import com.soreak.entity.UserEntity;
import com.soreak.service.UserFollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: welog
 * @author: soreak
 * @description:
 * @create: 2021-01-29 16:09
 **/
@Service
public class UserFollowServiceImpl implements UserFollowService {

    @Autowired
    private UserFollowDao userFollowDao;

    @Override
    public List<UserEntity> selectFollowByUId(Long id) {
        return userFollowDao.selectFollowByUId(id);
    }

    @Override
    public List<UserEntity> selectFollowByUFId(Long id) {
        return userFollowDao.selectFollowByUFId(id);
    }
}
