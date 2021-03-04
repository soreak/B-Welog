package com.soreak.controller.admin;

import com.alibaba.fastjson.JSONObject;
import com.soreak.entity.*;
import com.soreak.entity.VO.TopicVO;
import com.soreak.service.TagService;
import com.soreak.service.TopicService;
import com.soreak.service.TopicTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
 * @create: 2021-03-04 21:21
 **/
@Controller
@RequestMapping("/admin")
public class topicsController {

    @Autowired
    private TopicService topicService;

    @Autowired
    private TagService tagService;

    @Autowired
    private TopicTagService topicTagService;

    @RequestMapping(value = "/topics",method = RequestMethod.GET)
    @PreAuthorize("hasRole('1')")
    public String blogs(Model model){
        model.addAttribute("tags",tagService.getTagNameAndCountByTopics());
        model.addAttribute("topics",topicService.getAllTopicList());
        return "admin/topics";
    }

    @PostMapping("/topics/search")
    @PreAuthorize("hasRole('1')")
    public String search( @RequestParam(value = "title",defaultValue = "soreak") String title,
                          @RequestParam(value = "tagId",defaultValue = "-1") String tagId,
                          @RequestParam(value = "published",required = false) String published,
                          Model model){

        int published1 = -1;
        switch (published) {
            case "true":
                published1 = 0;
                break;
            case "false":
                published1 = 1;
                break;
        }
        List<Topic> topics = topicService.searchTopic(title,tagId,published1);
        model.addAttribute("topics",topics);
        return "admin/topics :: topicList";
    }


    @GetMapping("/topics/{id}/delete")
    @PreAuthorize("hasRole('1')")
    public String delete(@PathVariable Long id, RedirectAttributes attributes){
        topicService.deleteTopicById(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/topics";
    }

    @RequestMapping(value = "/oneTopic",method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasRole('1')")
    public JSONObject oneTopic(@RequestParam("id") Long id){
        TopicVO topic = topicService.getOneTopicById(id,0);
        topic.init();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("topic",topic);
        return jsonObject;
    }

    @RequestMapping(value = "/editTopic",method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasRole('1')")
    public JSONObject  editTopic(@RequestParam("id") Long id,
                                @RequestParam("title") String title,
                                @RequestParam("content") String content,
                                @RequestParam("tagIds") String tagIds,
                                @RequestParam("published") Integer published){
       Topic topic = new Topic();
       topic.setId(id);
       topic.setTitle(title);
       topic.setContent(content);
       topic.setPublished(published);
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


}
