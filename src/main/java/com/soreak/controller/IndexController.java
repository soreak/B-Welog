package com.soreak.controller;

import com.soreak.entity.Blog;
import com.soreak.entity.VO.BlogVO;
import com.soreak.entity.VO.TagVO;
import com.soreak.service.BlogService;
import com.soreak.service.TagService;
import com.soreak.utils.HTMLUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
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
    private TagService tagService;

    @GetMapping("/")
    public String index(Model model){
        List<TagVO> tags = tagService.getTagNameAndCount();
        model.addAttribute("tags",tags);

        List<BlogVO> blogs= blogService.getBlogList();

        for (BlogVO b :  blogs) {
            String content = b.getContent();
            Parser parser = Parser.builder().build();
            Node document = parser.parse(content);
            HtmlRenderer renderer = HtmlRenderer.builder().build();
            String renderString = renderer.render(document);
            b.setContent(HTMLUtils.convert(renderString).substring(0,100));
        }
        model.addAttribute("blogs",blogs);

        List<Blog> recommendBlog = blogService.getRecommendBlogList();
        model.addAttribute("recommendBlogs",recommendBlog);
        return "index";
    }

}
