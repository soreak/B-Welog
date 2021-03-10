package com.soreak.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.soreak.dao.NewsTagDao;
import com.soreak.entity.BlogTag;
import com.soreak.entity.NewsTag;
import com.soreak.service.NewsTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @program: welog
 * @author: soreak
 * @description:
 * @create: 2021-02-20 22:15
 **/
@Repository
public class NewsTagServiceImpl implements NewsTagService {
    @Autowired
    private NewsTagDao newsTagDao;

    @Override
    public int save(NewsTag newsTag) {
        return newsTagDao.insert(newsTag);
    }

    @Override
    public int deleteByNewsId(Long id) {
        QueryWrapper<NewsTag> wrapper = new QueryWrapper<>();
        wrapper.eq("news_id",id);
        return newsTagDao.delete(wrapper);
    }

    @Override
    public int deleteByTagId(Long id) {
        QueryWrapper<NewsTag> wrapper = new QueryWrapper<>();
        wrapper.eq("tag_id",id);
        return newsTagDao.delete(wrapper);
    }
}
