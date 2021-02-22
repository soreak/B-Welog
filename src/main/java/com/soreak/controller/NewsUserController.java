package com.soreak.controller;

import com.soreak.entity.News;
import com.soreak.entity.NewsLike;
import com.soreak.entity.UserEntity;
import com.soreak.entity.VO.BlogVO;
import com.soreak.entity.VO.NewsVO;
import com.soreak.entity.VO.TagVO;
import com.soreak.service.NewsLikeService;
import com.soreak.service.NewsService;
import com.soreak.service.TagService;
import com.soreak.service.UserService;
import com.soreak.utils.HTMLUtils;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
        List<TagVO> tags = tagService.getTagNameAndCountByNews();
        model.addAttribute("tags",tags);

        List<NewsVO> newsVOS = newsService.getNewsList();

        for (NewsVO n :  newsVOS) {
            String content = n.getContent();
            Parser parser = Parser.builder().build();
            Node document = parser.parse(content);
            HtmlRenderer renderer = HtmlRenderer.builder().build();
            String renderString = renderer.render(document);
            n.setContent(HTMLUtils.convert(renderString).substring(0,100));
            n.setLikeCount(newsLikeService.selectNewsLikeCountByNewsId(n.getId()));
        }
        model.addAttribute("newsList",newsVOS);

        List<News> newsList = newsService.getOneDayHotNews();
        model.addAttribute("TodayHotNewsList",newsList);
        List<News> weekNewsList = newsService.getWeekHotNews();
        model.addAttribute("WeekHotNewsList",weekNewsList);
        return "news";

    }
}
