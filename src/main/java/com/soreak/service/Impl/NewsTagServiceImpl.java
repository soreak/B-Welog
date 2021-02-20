package com.soreak.service.Impl;

import com.soreak.dao.NewsTagDao;
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
}
