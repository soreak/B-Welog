package com.soreak.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.soreak.dao.BlogDao;
import com.soreak.dao.TagDao;
import com.soreak.entity.Blog;
import com.soreak.entity.VO.BlogVO;
import com.soreak.service.BlogService;
import com.soreak.utils.MarkdownUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: welog
 * @author: soreak
 * @description:
 * @create: 2020-12-20 15:06
 **/
@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogDao blogDao;

    @Autowired
    private TagDao tagDao;

    @Override
    public List<BlogVO> getBlogList() {
        return blogDao.getBlogList();
    }

    @Override
    public List<Blog> getRecommendBlogList() {

        QueryWrapper queryWrapper = new QueryWrapper();

        queryWrapper.orderByDesc("views");
        queryWrapper.last("limit 5");

        return blogDao.selectList(queryWrapper);
    }

    @Override
    public BlogVO getBlogById(Long id) {
        BlogVO oneBlogById = blogDao.getOneBlogById(id);
        String content = oneBlogById.getContent();
        oneBlogById.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        oneBlogById.setTags(tagDao.getTagByBlogId(id));
        return oneBlogById;
    }
}
