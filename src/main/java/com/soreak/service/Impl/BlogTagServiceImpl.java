package com.soreak.service.Impl;

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
}
