package com.soreak.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.soreak.dao.BlogTagDao;
import com.soreak.dao.NewsDao;
import com.soreak.dao.NewsTagDao;
import com.soreak.dao.TagDao;
import com.soreak.entity.Blog;
import com.soreak.entity.BlogTag;
import com.soreak.entity.News;
import com.soreak.entity.NewsTag;
import com.soreak.entity.VO.NewsVO;
import com.soreak.service.NewsService;
import com.soreak.utils.MarkdownUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @program: welog
 * @author: soreak
 * @description:
 * @create: 2021-02-20 21:08
 **/
@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    private NewsDao newsDao;

    @Autowired
    private TagDao tagDao;

    @Autowired
    private NewsTagDao newsTagDao;



    @Override
    public List<NewsVO> getNewsList() {
        return newsDao.getNewsList();
    }

    @Override
    public List<News> getHotNewsList() {

        QueryWrapper<News> queryWrapper = new QueryWrapper<>();

        queryWrapper.orderByDesc("views");
        queryWrapper.last("limit 5");
        return newsDao.selectList(queryWrapper);
    }

    @Override
    public NewsVO getNewsById(Long id) {
        NewsVO newsVO = newsDao.getOneNewsById(id);
        String content = newsVO.getContent();
        newsVO.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        newsVO.setTags(tagDao.getTagByNewsId(id));
        newsDao.updateViews(id);
        return newsVO;
    }

    @Override
    public NewsVO getOneNews(Long id) {
        NewsVO newsVO = newsDao.getOneNewsById(id);
        newsVO.setTags(tagDao.getTagByNewsId(id));
        return newsVO;
    }

    @Override
    public Long saveNews(News news) {
        news.setCreateTime(new Date());
        news.setUpdateTime(new Date());
        news.setViews(0);

        return newsDao.saveNews(news);
    }

    @Override
    public Long updateNews(News news) {
        news.setUpdateTime(new Date());
        return newsDao.updateNews(news);
    }

    @Override
    public int deleteNewsById(Long id) {
        QueryWrapper<NewsTag> wrapper = new QueryWrapper<>();
        wrapper.eq("news_id",id);
        newsTagDao.delete(wrapper);
        return newsDao.deleteById(id);
    }

    @Override
    public List<News> searchNews(String title, String tagId) {
        QueryWrapper<News> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("update_time");
        if (tagId.equals("-1")) {
            if (!title.equals("soreak")) {
                wrapper.like("title", title);
            }

            return newsDao.selectList(wrapper);
        }else {
            if (!title.equals("soreak")) {
                return newsDao.searchNewsListByTT(title,tagId);
            }
            return newsDao.searchNewsListByT(tagId);
        }
    }
}
