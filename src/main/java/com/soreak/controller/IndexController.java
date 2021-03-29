package com.soreak.controller;

import com.soreak.entity.Blog;
import com.soreak.entity.BlogLike;
import com.soreak.entity.UserEntity;
import com.soreak.entity.VO.BlogVO;
import com.soreak.entity.VO.TagVO;
import com.soreak.service.BlogLikeService;
import com.soreak.service.BlogService;
import com.soreak.service.TagService;
import com.soreak.service.UserService;
import com.soreak.utils.HTMLUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @program: welog
 * @author: soreak
 * @description:
 * @create: 2020-12-19 21:21
 **/
@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private BlogLikeService blogLikeService;

    @Autowired
    private TagService tagService;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index(Model model){
        String phone = SecurityContextHolder.getContext().getAuthentication().getName();
        if (phone!="anonymousUser"){
                UserEntity entity = userService.findByPhone(phone);
            userService.updateTime(entity.getId());
            model.addAttribute("master",entity);
        }

        List<TagVO> tags = tagService.getTagNameAndCount();
        model.addAttribute("tags",tags);

        List<BlogVO> blogs= blogService.getBlogList();

        for (BlogVO b :  blogs) {
            String content = b.getContent();
            Parser parser = Parser.builder().build();
            Node document = parser.parse(content);
            HtmlRenderer renderer = HtmlRenderer.builder().build();
            String renderString = renderer.render(document);
            if (HTMLUtils.convert(renderString).length()>100){
                b.setContent(HTMLUtils.convert(renderString).substring(0,100));
            }
            b.setLikeCount(blogLikeService.selectBlogLikeCountByBlogId(b.getId()));
        }
        model.addAttribute("blogs",blogs);

        List<Blog> recommendBlog = blogService.getHotBlogList();
        model.addAttribute("recommendBlogs",recommendBlog);
        return "index";
    }




}
