package com.soreak.service;

import com.soreak.entity.NewsTag;

/**
 * @program: welog
 * @author: soreak
 * @description:
 * @create: 2021-02-20 22:13
 **/
public interface NewsTagService {
    int save(NewsTag newsTag);

    int deleteByNewsId(Long id);

    int deleteByTagId(Long id);
}
