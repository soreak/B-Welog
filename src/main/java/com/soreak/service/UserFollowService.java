package com.soreak.service;

import com.soreak.entity.UserEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @program: welog
 * @author: soreak
 * @description:
 * @create: 2021-01-29 16:09
 **/
public interface UserFollowService {

    List<UserEntity> selectFollowByUId(Long id);

    List<UserEntity> selectFollowByUFId(Long id);

    int createFollow(Long UId,Long UFId);

    int deleteFollow(Long UId,Long UFId);
}
