package com.soreak.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.soreak.entity.UserEntity;
import org.springframework.stereotype.Repository;

/**
 * @program: welog
 * @author: soreak
 * @description: 注册
 * @create: 2020-12-19 14:30
 **/
@Repository
public interface RegisterDao extends BaseMapper<UserEntity> {
}
