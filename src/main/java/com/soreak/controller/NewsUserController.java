package com.soreak.controller;

import com.soreak.entity.BlogLike;
import com.soreak.entity.News;
import com.soreak.entity.NewsLike;
import com.soreak.entity.UserEntity;
import com.soreak.entity.VO.BlogVO;
import com.soreak.entity.VO.NewsVO;
import com.soreak.entity.VO.TagVO;
import com.soreak.service.*;
import com.soreak.utils.HTMLUtils;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @program: welog
 * @author: soreak
 * @description:
 * @create: 2021-02-22 21:44
 **/
@Controller
public class NewsUserController {
    @Autowired
    private NewsService newsService;

    @Autowired
    private NewsLikeService newsLikeService;

    @Autowired
    private NewsTagService newsTagService;


    @Autowired
    private TagService tagService;

    @Autowired
    private UserService userService;

    @GetMapping("/news")
    public String news(Model model){
        String phone = SecurityContextHolder.getContext().getAuthentication().getName();
        if (phone!="anonymousUser"){
            UserEntity entity = userService.findByPhone(phone);
            entity.setPassword("");
            userService.updateTime(entity.getId());
            model.addAttribute("master",entity);
        }
        setTag(model);

        List<NewsVO> newsVOS = newsService.getNewsList();


        for (NewsVO n :  newsVOS) {
            String content = n.getContent();
            Parser parser = Parser.builder().build();
            Node document = parser.parse(content);
            HtmlRenderer renderer = HtmlRenderer.builder().build();
            String renderString = renderer.render(document);
            if (n.getContent().length()>50){
                n.setContent(HTMLUtils.convert(renderString).substring(0,100));
            }
            n.setLikeCount(newsLikeService.selectNewsLikeCountByNewsId(n.getId()));
        }
        model.addAttribute("newsList",newsVOS);

        List<News> newsList = newsService.getOneDayHotNews();
        model.addAttribute("TodayHotNewsList",newsList);
        List<News> weekNewsList = newsService.getWeekHotNews();
        model.addAttribute("WeekHotNewsList",weekNewsList);
        return "news";

    }

    private void setTag(Model model){
        model.addAttribute("tags",tagService.getTagNameAndCountByNews());

    }

    private UserEntity setUser(Model model){
        String phone = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity byPhone = new UserEntity();
        if (phone!=null){
            byPhone = userService.findByPhone(phone);
            model.addAttribute("master",byPhone);
        }
        return byPhone;
    }

    @GetMapping("/news/{id}")
    public String showNews(@PathVariable Long id, Model model){
        UserEntity userEntity = setUser(model);
        NewsLike newsLike = null;
        if (userEntity !=null){
            newsLike = newsLikeService.SelectNewsLike(id, userEntity.getId());
        }


        if (newsLike !=null){
            model.addAttribute("NewsLikeFlag","1");
        }else {
            model.addAttribute("NewsLikeFlag","0");
        }

        NewsVO newsVO =newsService.getNewsById(id);
        if (newsVO == null){
            return "/error/404";
        }
        model.addAttribute("news",newsVO);
        return "showNews";
    }
}
