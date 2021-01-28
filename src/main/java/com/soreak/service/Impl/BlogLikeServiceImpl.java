package com.soreak.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.soreak.dao.BlogLikeDao;
import com.soreak.entity.BlogLike;
import com.soreak.service.BlogLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @program: welog
 * @author: soreak
 * @description:
 * @create: 2021-01-28 15:43
 **/
@Service
public class BlogLikeServiceImpl implements BlogLikeService {
    @Autowired
    private BlogLikeDao blogLikeDao;

    @Override
    public BlogLike SelectBlogLike(Long BlogId, Long UserId) {
        QueryWrapper<BlogLike> wrapper = new QueryWrapper<>();
        wrapper.eq("blog_id",BlogId)
                .eq("user_id",UserId);
        return blogLikeDao.selectOne(wrapper);
    }

    @Override
    public int CreateBlogLike(Long BlogId, Long UserId) {
        BlogLike blogLike = new BlogLike();
        blogLike.setBlogId(BlogId);
        blogLike.setUserId(UserId);
        blogLike.setStatus(1);
        blogLike.setUpdateTime(new Date());
        return blogLikeDao.insert(blogLike);
    }

    @Override
    public int DeleteBlogLike(Long BlogId, Long UserId) {
        QueryWrapper<BlogLike> wrapper = new QueryWrapper<>();
        wrapper.eq("blog_id",BlogId)
                .eq("user_id",UserId);

        return blogLikeDao.delete(wrapper);
    }
}
