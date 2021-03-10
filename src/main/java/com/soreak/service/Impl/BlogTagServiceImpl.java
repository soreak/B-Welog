package com.soreak.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.soreak.dao.BlogTagDao;
import com.soreak.entity.BlogTag;
import com.soreak.service.BlogTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @program: welog
 * @author: soreak
 * @description:
 * @create: 2020-12-29 22:53
 **/
@Repository
public class BlogTagServiceImpl implements BlogTagService {

    @Autowired
    private BlogTagDao blogTagDao;


    @Override
    public int save(BlogTag blogTag) {
        return blogTagDao.insert(blogTag);
    }

    @Override
    public int deleteByBlogId(Long id) {
        QueryWrapper<BlogTag> wrapper = new QueryWrapper<>();
        wrapper.eq("blog_id",id);
        return blogTagDao.delete(wrapper);
    }

    @Override
    public int deleteByTagId(Long id) {
        QueryWrapper<BlogTag> wrapper = new QueryWrapper<>();
        wrapper.eq("tag_id",id);
        return blogTagDao.delete(wrapper);

    }
}
