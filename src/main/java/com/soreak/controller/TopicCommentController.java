package com.soreak.controller;

import com.soreak.entity.BlogComment;
import com.soreak.entity.TopicComment;
import com.soreak.entity.UserEntity;
import com.soreak.entity.VO.BlogVO;
import com.soreak.entity.VO.TopicVO;
import com.soreak.service.TopicCommentService;
import com.soreak.service.TopicService;
import com.soreak.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

/**
 * @program: welog
 * @author: soreak
 * @description:
 * @create: 2021-03-02 17:21
 **/
@Controller
public class TopicCommentController {
    @Autowired
    private TopicCommentService topicCommentService;

    @Autowired
    private UserService userService;

    @Autowired
    private TopicService topicService;

    @GetMapping("/topicComments/{id}")
    public String comments(@PathVariable Long id, Model model){
        model.addAttribute("comments",topicCommentService.listCommentByTopicId(id));
        TopicVO topicVO = topicService.getOneTopicById(id,0);
        model.addAttribute("authorId",topicVO.getUserId());
        String phone = SecurityContextHolder.getContext().getAuthentication().getName();
        if (phone !="anonymousUser"){
            UserEntity entity = userService.findByPhone(phone);
            if (entity.getId().equals(topicVO.getUserId())){
                model.addAttribute("authorFlag","true");
            }
        }
        return "showTopics :: topicCommentList";
    }


    @PostMapping("/topicComments")
    @PreAuthorize("isAuthenticated()")
    public String post(@RequestParam Long parentCommentId,
                       @RequestParam Long topicId,
                       @RequestParam String content){
        String phone = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userService.findByPhone(phone);
        TopicComment comment = new TopicComment();

        comment.setUserId(user.getId());
        comment.setTopicId(topicId);
        comment.setContent(content);
        comment.setCreateTime(new Date());
        comment.setStatus(0);
        if (parentCommentId != -1){
            comment.setParentCommentId(parentCommentId);
        }

        topicCommentService.saveOne(comment);

        return "redirect:/topicComments/"+topicId;
    }

    @PostMapping("/topicComments/delete")
    @PreAuthorize("isAuthenticated()")
    public String delete(@RequestParam String id,
                         @RequestParam String topicId){
        topicCommentService.batchDelete(Long.valueOf(topicId),Long.valueOf(id));

        return "redirect:/topicComments/"+topicId;
    }
}
