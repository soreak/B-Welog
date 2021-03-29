package com.soreak.controller;

import com.alibaba.fastjson.JSONObject;
import com.soreak.entity.Tag;
import com.soreak.entity.Topic;
import com.soreak.entity.TopicTag;
import com.soreak.entity.UserEntity;
import com.soreak.entity.VO.BlogVO;
import com.soreak.entity.VO.TopicVO;
import com.soreak.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: welog
 * @author: soreak
 * @description:
 * @create: 2021-03-07 22:16
 **/
@Controller
public class MyController {


    @Autowired
    private BlogService blogService;

    @Autowired
    private UserService userService;

    @Autowired
    private TopicService topicService;

    @Autowired
    private TopicTagService topicTagService;

    @Autowired
    private TagService tagService;

    @GetMapping("/MY")
    @PreAuthorize("isAuthenticated()")
    public String my(Model model){
        UserEntity userEntity = setUser(model);

        List<BlogVO> blogs= blogService.getMyBlogListByUserId(userEntity.getId());

        List<TopicVO> topicVOS = topicService.getMyTopicListByUserId(userEntity.getId());
        model.addAttribute("blogs",blogs);
        model.addAttribute("topics",topicVOS);
        return "/my";
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


}
