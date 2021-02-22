package com.soreak.service;

import com.soreak.entity.Blog;
import com.soreak.entity.News;

import com.soreak.entity.VO.NewsVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: welog
 * @author: soreak
 * @description:
 * @create: 2021-02-20 21:07
 **/

public interface NewsService {

    List<NewsVO> getNewsList();

    List<News> getOneDayHotNews();

    List<News> getWeekHotNews();

    /* 修改的时候  */
    NewsVO getNewsById(Long id);

    /* 查看的时候  */
    NewsVO getOneNews(Long id);

    Long saveNews(News news);

    Long updateNews(News news);

    int deleteNewsById(Long id);

    List<News> searchNews(String title,String tagId);
}
