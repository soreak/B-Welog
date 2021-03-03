package com.soreak.controller;

import com.soreak.entity.*;
import com.soreak.entity.VO.BlogVO;
import com.soreak.entity.VO.TagVO;
import com.soreak.entity.VO.TopicVO;
import com.soreak.service.*;
import com.soreak.utils.HTMLUtils;
import org.apache.http.HttpResponse;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
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
    private TopicTagService topicTagService;

    @Autowired
    private UserService userService;

    @Autowired
    private TagService tagService;

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

        List<TagVO> tagVOS = tagService.getTagNameAndCountByTopics();

        model.addAttribute("tags",tagVOS);

        return "topics";
    }

    @GetMapping("/topic/{id}")
    public String topic(@PathVariable Long id, Model model){
        UserEntity userEntity = setUser(model);

        TopicVO topicVO =topicService.getOneTopicById(id,1);
        model.addAttribute("topic",topicVO);
        return "showTopics";
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

    @PostMapping("/topic/save")
    @PreAuthorize("isAuthenticated()")
    public String save(@RequestParam("title") String title,
                       @RequestParam("content") String content,
                       @RequestParam("tagIds") String tagIds,
                       Model model){

        String phone = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity userEntity = new UserEntity();
        if (phone!=null){
            userEntity = userService.findByPhone(phone);
        }




        Topic topic = new Topic();
        topic.setTitle(title);
        topic.setContent(content);
        topic.setPublished(false);
        topic.setUserId(userEntity.getId());
        Long id = topicService.saveTopic(topic);

        if (!tagIds.isEmpty()){
            String[] split = tagIds.split(",");
            List<Long> tagIdList = new ArrayList<>();
            Tag tag = new Tag();
            for (String c : split){
                try{
                    tag.setName(c);
                    tagService.saveTag(tag);
                    tagIdList.add(tag.getId());
                    tag = new Tag();
                }catch (Exception e){
                    tagIdList.add(tagService.selectTagByName(c).getId());
                }
            }
            TopicTag topicTag = new TopicTag();
            for (Long id1: tagIdList){
                topicTag.setTopicId(topic.getId());
                topicTag.setTagId(id1);
                topicTagService.save(topicTag);
                topicTag = new TopicTag();
            }
        }

        return "redirect:/topic";
    }

}
