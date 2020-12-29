package com.soreak.controller;

import com.soreak.entity.BlogComment;
import com.soreak.entity.UserEntity;
import com.soreak.entity.VO.CommentVO;
import com.soreak.service.BlogCommentService;
import com.soreak.service.BlogService;
import com.soreak.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * @program: welog
 * @author: soreak
 * @description:
 * @create: 2020-12-28 13:45
 **/
@Controller
public class BlogCommentController {

    @Autowired
    private BlogCommentService commentService;

    @Autowired
    private UserService userService;



    @GetMapping("/comments/{id}")
    public String comments(@PathVariable Long id, Model model){
        model.addAttribute("comments",commentService.listCommentByBlogId(id));
        return "blog :: commentList";
    }

    @PostMapping("/comments")
    public String post(@RequestParam Long parentCommentId,
                       @RequestParam Long blogId,
                       @RequestParam String content){
        String phone = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userService.findByPhone(phone);
        BlogComment comment = new BlogComment();

        comment.setUserId(user.getId());
        comment.setBlogId(blogId);
        comment.setContent(content);
        comment.setCreateTime(new Date());
        comment.setStatus(0);
        if (parentCommentId != -1){
            comment.setParentCommentId(parentCommentId);
        }

        commentService.saveOne(comment);

        return "redirect:/comments/"+blogId;
    }
}
