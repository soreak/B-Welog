package com.soreak.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.soreak.dao.UserFollowDao;
import com.soreak.entity.UserEntity;
import com.soreak.entity.UserFollow;
import com.soreak.service.UserFollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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

    @Override
    public int createFollow(Long UId, Long UFId) {
        UserFollow userFollow = new UserFollow();
        userFollow.setUserId(UId);
        userFollow.setUserFollowId(UFId);
        userFollow.setStatus(true);
        userFollow.setCreateTime(new Date());
        return userFollowDao.insert(userFollow);
    }

    @Override
    public int deleteFollow(Long UId, Long UFId) {
        QueryWrapper<UserFollow> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",UId);
        queryWrapper.eq("user_follow_id",UFId);

        return userFollowDao.delete(queryWrapper);
    }
}
