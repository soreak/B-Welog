package com.soreak.controller;

import com.soreak.entity.Tag;
import com.soreak.entity.UserEntity;
import com.soreak.entity.VO.BlogVO;
import com.soreak.entity.VO.NewsVO;
import com.soreak.entity.VO.TagVO;
import com.soreak.entity.VO.TopicVO;
import com.soreak.service.*;
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
 * @create: 2021-03-08 21:49
 **/
@Controller
public class TagController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private NewsService newsService;

    @Autowired
    private TopicService topicService;

    @Autowired
    private TagService tagService;

    @Autowired
    private UserService userService;

    @GetMapping("/tags/{id}")
    public String searchTag(@PathVariable Long id, Model model){
        setUser(model);

        Tag tag = tagService.getTagById(id);
        model.addAttribute("tag",tag);

        List<BlogVO> blogVOS = blogService.getBlogListByTagId(id);
        List<NewsVO> newsVOS = newsService.getNewsListByTagId(id);
        List<TopicVO> topicVOS = topicService.getTopicListByTagId(id);
        model.addAttribute("blogs",blogVOS);
        model.addAttribute("news",newsVOS);
        model.addAttribute("topics",topicVOS);
        return "/searchTag";
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

    @GetMapping("/tags")
    public String Tag(Model model){
        setUser(model);

        List<TagVO> blogTag = tagService.getAllTagNameAndCountByBlog();
        List<TagVO> newsTag = tagService.getAllTagNameAndCountByNews();
        List<TagVO> topicTag = tagService.getAllTagNameAndCountByTopics();


        model.addAttribute("blogTagList",blogTag);
        model.addAttribute("newsTagList",newsTag);
        model.addAttribute("topicTagList",topicTag);
        return "/tags";
    }



}
