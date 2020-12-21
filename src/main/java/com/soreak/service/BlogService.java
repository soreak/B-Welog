package com.soreak.service;

import com.soreak.entity.Blog;
import com.soreak.entity.VO.BlogVO;

import java.util.List;

/**
 * @program: welog
 * @author: soreak
 * @description:
 * @create: 2020-12-20 14:59
 **/
public interface BlogService {
    List<BlogVO> getBlogList();

    List<Blog> getRecommendBlogList();


}
