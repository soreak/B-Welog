package com.soreak.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.soreak.dao.BlogDao;
import com.soreak.dao.BlogTagDao;
import com.soreak.dao.TagDao;
import com.soreak.entity.Blog;
import com.soreak.entity.BlogTag;
import com.soreak.entity.Tag;
import com.soreak.entity.UserEntity;
import com.soreak.entity.VO.BlogVO;
import com.soreak.service.BlogService;
import com.soreak.utils.HTMLUtils;
import com.soreak.utils.MarkdownUtils;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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

    @Autowired
    private BlogTagDao blogTagDao;

    @Override
    public List<BlogVO> getBlogList() {
        return blogDao.getBlogList();
    }

    @Override
    public List<BlogVO> getMyBlogListByUserId(Long userId) {
        return blogDao.getMyBlogListByUserId(userId);
    }

    @Override
    public List<Blog> getHotBlogList() {

        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();

        queryWrapper.orderByDesc("views");
        queryWrapper.last("limit 5");

        return blogDao.selectList(queryWrapper);
    }

    @Override
    public List<BlogVO> getBlogListByTagId(Long tagId) {
        List<BlogVO> blogListByTagId = blogDao.getBlogListByTagId(tagId);
        for (BlogVO blogVO:
                blogListByTagId) {
            String content = blogVO.getContent();
            Parser parser = Parser.builder().build();
            Node document = parser.parse(content);
            HtmlRenderer renderer = HtmlRenderer.builder().build();
            String renderString = renderer.render(document);
            if (HTMLUtils.convert(renderString).length()>100){
                blogVO.setContent(HTMLUtils.convert(renderString).substring(0,100));
            }
            blogVO.setTags(tagDao.getTagByBlogId(blogVO.getId()));
        }
        return blogListByTagId;
    }

    @Override
    public BlogVO getBlogById(Long id) {
        BlogVO Blog = blogDao.getOneBlogById(id);
        String content = Blog.getContent();
        Blog.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        Blog.setTags(tagDao.getTagByBlogId(id));

        blogDao.updateViews(id);
        return Blog;
    }

    @Override
    public BlogVO getOneBlog(Long id) {
        BlogVO blog = blogDao.getOneBlogById(id);
        blog.setTags(tagDao.getTagByBlogId(id));
        return blog;
    }



    @Override
    public Long saveBlog(Blog blog) {

        blog.setCreateTime(new Date());
        blog.setUpdateTime(new Date());
        blog.setViews(0);
        return blogDao.saveBlog(blog);
    }


    @Override
    public Long updateBlog(Blog blog) {
        blog.setUpdateTime(new Date());
        return blogDao.updateBlog(blog);
    }


    @Override
    public int deleteBlogById(Long id) {
        QueryWrapper<BlogTag> wrapper = new QueryWrapper<>();
        wrapper.eq("blog_id",id);
        blogTagDao.delete(wrapper);
        return blogDao.deleteById(id);
    }

    @Override
    public List<Blog> searchBlog(String title, String tagId, int recommend) {
        QueryWrapper<Blog> wrapper =new QueryWrapper<>();

        if (tagId.equals("-1")) {
            if (!title.equals("soreak")) {
                wrapper.like("title", title);
            }
            if (recommend != -1) {
                wrapper.eq("recommend", recommend);
            }
            return blogDao.selectList(wrapper);
        }else {
            if (!title.equals("soreak")&&recommend != -1) {
               return blogDao.searchBlogListByTTR(title,tagId,recommend);
            }else if (title.equals("soreak")&&recommend != -1) {
                return blogDao.searchBlogListByTR(tagId,recommend);
            }else if (!title.equals("soreak")&&recommend == -1) {
                return blogDao.searchBlogListByTT(title,tagId);
            }else{
                return blogDao.searchBlogListByT(tagId);
            }

        }
    }

    @Override
    public List<BlogVO> searchBlogByQuery(String query) {
        return blogDao.searchBlogByQuery(query);
    }


}
