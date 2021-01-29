package com.soreak.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.soreak.entity.UserEntity;
import com.soreak.entity.UserFollow;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: welog
 * @author: soreak
 * @description:
 * @create: 2020-12-17 16:49
 **/
@Repository
public interface UserFollowDao extends BaseMapper<UserFollow> {

    /*找粉丝*/
    @Select("SELECT u.id,u.nickname,u.information,u.avatar FROM sk_user_follow uf,sk_user u where uf.user_follow_id =u.id and uf.user_id = #{UId}")
    List<UserEntity> selectFollowByUId(@Param("UId") Long id);

    /*找关注*/
    @Select("SELECT u.id,u.nickname,u.information,u.avatar FROM sk_user_follow uf,sk_user u where uf.user_id =u.id and uf.user_follow_id = #{UFId}")
    List<UserEntity> selectFollowByUFId(@Param("UFId")Long id);


}
