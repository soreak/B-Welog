package com.soreak.controller;

import com.soreak.entity.Blog;
import com.soreak.entity.Topic;
import com.soreak.entity.UserEntity;
import com.soreak.entity.VO.BlogVO;
import com.soreak.entity.VO.TagVO;
import com.soreak.entity.VO.TopicVO;
import com.soreak.service.TopicCommentService;
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
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @program: welog
 * @author: soreak
 * @description:
 * @create: 2021-02-28 22:22
 **/
@Controller
public class TopicController {

    @Autowired
    private TopicService topicService;

    @Autowired
    private TopicCommentService topicCommentService;

    @Autowired
    private UserService userService;

    @GetMapping("/topic")
    public String topic(Model model){
        String phone = SecurityContextHolder.getContext().getAuthentication().getName();
        if (phone!="anonymousUser"){
            UserEntity entity = userService.findByPhone(phone);
            userService.updateTime(entity.getId());
            model.addAttribute("master",entity);
        }

        List<TopicVO> topicVOS= topicService.getTopicList();

        for (TopicVO t :  topicVOS) {
            if (t.getContent().length()>50){
                t.setContent(t.getContent().substring(0,50));
            }
            t.setCommentCount(topicCommentService.selectCommentCount(t.getId()));
        }
        model.addAttribute("topics",topicVOS);


        return "topics";
    }



}
