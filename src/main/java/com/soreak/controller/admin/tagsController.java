package com.soreak.controller.admin;

import com.alibaba.fastjson.JSONObject;
import com.soreak.entity.Tag;
import com.soreak.entity.Topic;
import com.soreak.entity.TopicTag;
import com.soreak.entity.VO.BlogVO;
import com.soreak.entity.VO.NewsVO;
import com.soreak.entity.VO.TopicVO;
import com.soreak.service.BlogTagService;
import com.soreak.service.NewsTagService;
import com.soreak.service.TagService;
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
 * @create: 2021-03-10 20:22
 **/
@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAnyRole('1', '2')")
public class tagsController {

    @Autowired
    private BlogTagService blogTagService;

    @Autowired
    private NewsTagService newsTagService;

    @Autowired
    private TopicTagService topicTagService;

    @Autowired
    private TagService tagService;

    @GetMapping("/tags")
    public String Tag(Model model){
        List<Tag> tagList = tagService.getAllTag();

        model.addAttribute("tagsList",tagList);
        return "admin/tags";
    }

    @GetMapping("/tags/input")
    public String inputTag(Model model){
        model.addAttribute("tag",new Tag());
        return "admin/tags-input";
    }

    @PostMapping("/tags/save")
    public String saveTag(Tag tag,Model model){
        try{
            tagService.saveTag(tag);
        }catch (Exception e){
            model.addAttribute("message","数据库已存在该标签");
            return "admin/tags-input";
        }
        return "redirect:/admin/tags";
    }

    @GetMapping("/tags/{id}/delete")
    public String deleteTag(@PathVariable Long id, RedirectAttributes attributes){
        blogTagService.deleteByTagId(id);
        newsTagService.deleteByTagId(id);
        topicTagService.deleteByTagId(id);
        tagService.deleteTag(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/tags";
    }

    @RequestMapping(value = "/oneTag",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject oneTag(@RequestParam("id") Long id){
        Tag tag = tagService.getTagById(id);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("tag",tag);
        return jsonObject;
    }

    @RequestMapping(value = "/editTag",method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasRole('1')")
    public JSONObject  editTag(@RequestParam("id") Long id,
                                 @RequestParam("name") String name){

        Tag tag =new Tag();
        tag.setId(id);
        tag.setName(name);
        JSONObject jsonObject = new JSONObject();
        int i=tagService.updateTag(tag);
        if (i !=1){
            jsonObject.put("message","操作失败");
        }else {
            jsonObject.put("message","操作成功");
        }
        return jsonObject;
    }

}
