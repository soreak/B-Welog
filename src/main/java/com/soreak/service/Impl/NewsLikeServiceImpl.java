package com.soreak.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.soreak.dao.NewsLikeDao;
import com.soreak.entity.BlogLike;
import com.soreak.entity.NewsLike;
import com.soreak.service.NewsLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @program: welog
 * @author: soreak
 * @description:
 * @create: 2021-02-22 21:54
 **/
@Service
public class NewsLikeServiceImpl implements NewsLikeService {

    @Autowired
    private NewsLikeDao newsLikeDao;


    @Override
    public NewsLike SelectNewsLike(Long NewsId, Long UserId) {
        QueryWrapper<NewsLike> wrapper = new QueryWrapper<>();
        wrapper.eq("news_id",NewsId)
                .eq("user_id",UserId);
        return newsLikeDao.selectOne(wrapper);
    }

    @Override
    public int selectNewsLikeCountByNewsId(Long NewsId) {
        QueryWrapper<NewsLike> wrapper = new QueryWrapper<>();
        wrapper.eq("news_id",NewsId);
        return newsLikeDao.selectCount(wrapper);
    }

    @Override
    public int CreateNewsLike(Long NewsId, Long UserId) {
        NewsLike newsLike = new NewsLike();
        newsLike.setNewsId(NewsId);
        newsLike.setUserId(UserId);
        newsLike.setStatus(1);
        newsLike.setUpdateTime(new Date());
        return newsLikeDao.insert(newsLike);
    }

    @Override
    public int DeleteNewsLike(Long NewsId, Long UserId) {
        QueryWrapper<NewsLike> wrapper = new QueryWrapper<>();
        wrapper.eq("news_id",NewsId)
                .eq("user_id",UserId);

        return newsLikeDao.delete(wrapper);
    }
}
