package com.soreak.controller;

import com.soreak.entity.Blog;
import com.soreak.entity.News;
import com.soreak.entity.Topic;
import com.soreak.entity.UserEntity;
import com.soreak.entity.VO.BlogVO;
import com.soreak.entity.VO.NewsVO;
import com.soreak.entity.VO.TopicVO;
import com.soreak.service.BlogService;
import com.soreak.service.NewsService;
import com.soreak.service.TopicService;
import com.soreak.service.UserService;
import com.soreak.utils.HTMLUtils;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

/**
 * @program: welog
 * @author: soreak
 * @description:
 * @create: 2021-03-15 19:56
 **/
@Controller
public class SearchController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private NewsService newsService;

    @Autowired
    private TopicService topicService;


    @Autowired
    private UserService userService;



    @RequestMapping(value = "/search",method = RequestMethod.POST)
    public String search(@RequestParam("query")String query,
                         Model model){
        String phone = SecurityContextHolder.getContext().getAuthentication().getName();
        if (phone!="anonymousUser"){
            UserEntity entity = userService.findByPhone(phone);
            model.addAttribute("master",entity);
        }

        model.addAttribute("searchTitle",query);

        List<BlogVO> blogs = blogService.searchBlogByQuery(query);
        for (BlogVO b :  blogs) {
            String content = b.getContent();
            Parser parser = Parser.builder().build();
            Node document = parser.parse(content);
            HtmlRenderer renderer = HtmlRenderer.builder().build();
            String renderString = renderer.render(document);
            if (HTMLUtils.convert(renderString).length()>100){
                b.setContent(HTMLUtils.convert(renderString).substring(0,100));
            }
        }
        model.addAttribute("blogs",blogs);

        List<NewsVO> news = newsService.searchNewsByQuery(query);
        for (NewsVO n :  news) {
            String content = n.getContent();
            Parser parser = Parser.builder().build();
            Node document = parser.parse(content);
            HtmlRenderer renderer = HtmlRenderer.builder().build();
            String renderString = renderer.render(document);
            if (n.getContent().length()>50){
                n.setContent(HTMLUtils.convert(renderString).substring(0,100));
            }
        }
        model.addAttribute("news",news);

        List<TopicVO> topics = topicService.searchTopicByQuery(query);
        for (TopicVO t :  topics) {
            if (t.getContent().length()>50){
                t.setContent(t.getContent().substring(0,50));
            }
        }
        model.addAttribute("topics",topics);

        List<UserEntity> userEntityList = userService.searchUserByQuery(query);
        model.addAttribute("users",userEntityList);

        return "/search";
    }



}
