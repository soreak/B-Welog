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

    @RequestMapping(value = "/oneTopic",method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("isAuthenticated()")
    public JSONObject oneTopic(@RequestParam("id") Long id){
        TopicVO topic = topicService.getOneTopicById(id,0);
        topic.init();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("topic",topic);
        return jsonObject;
    }

    @RequestMapping(value = "/editTopic",method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("isAuthenticated()")
    public JSONObject  editTopic(@RequestParam("id") Long id,
                                 @RequestParam("title") String title,
                                 @RequestParam("content") String content,
                                 @RequestParam("tagIds") String tagIds){
        Topic topic = new Topic();
        topic.setId(id);
        topic.setTitle(title);
        topic.setContent(content);
        String[] split = tagIds.split(",");
        List<Long> tagIdList = new ArrayList<>();
        Tag tag = new Tag();
        for (String c : split){
            if (!c.matches("^[0-9]*$")){
                tag.setName(c);
                Long aLong = tagService.saveTag(tag);
                tagIdList.add(tag.getId());
                tag = new Tag();
            }else {
                tagIdList.add(Long.valueOf(c));
            }
        }


        Long i = topicService.updateTopic(topic);

        JSONObject jsonObject = new JSONObject();
        if (i !=1){
            jsonObject.put("message","操作失败");
        }else {
            jsonObject.put("message","操作成功");
        }

        TopicTag topicTag = new TopicTag();
        topicTagService.deleteByTopicId(id);
        for (Long id1: tagIdList){
            topicTag.setTopicId(topic.getId());
            topicTag.setTagId(id1);
            try{
                topicTagService.save(topicTag);
            }catch (Exception e){
                e.printStackTrace();
            }
            topicTag = new TopicTag();
        }

        return jsonObject;
    }


    @GetMapping("/topic/{id}/delete")
    @PreAuthorize("isAuthenticated()")
    public String delete(@PathVariable Long id, RedirectAttributes attributes){
        topicService.deleteTopicById(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/MY";
    }

}
