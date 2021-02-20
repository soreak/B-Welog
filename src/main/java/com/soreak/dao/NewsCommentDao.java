package com.soreak.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.soreak.entity.NewsComment;
import org.springframework.stereotype.Repository;

/**
 * @program: welog
 * @author: soreak
 * @description:
 * @create: 2021-02-20 21:49
 **/
@Repository
public interface NewsCommentDao extends BaseMapper<NewsComment> {
}
