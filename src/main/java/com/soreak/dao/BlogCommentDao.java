package com.soreak.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.soreak.entity.BlogComment;
import org.springframework.stereotype.Repository;

/**
 * @program: welog
 * @author: soreak
 * @description:
 * @create: 2020-12-28 14:52
 **/
@Repository
public interface BlogCommentDao extends BaseMapper<BlogComment> {
}
