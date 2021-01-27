package com.soreak.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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

    List<BlogVO> getMyBlogListByUserId(Long userId);

    List<Blog> getHotBlogList();

    BlogVO getBlogById(Long id);

    BlogVO getOneBlog(Long id);

    Long saveBlog(Blog blog);

    Long updateBlog(Blog blog);

    int deleteBlogById(Long id);

    List<Blog> searchBlog(String title,String tagId,int recommend);
}
