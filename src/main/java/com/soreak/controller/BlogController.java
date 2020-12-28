package com.soreak.controller;

import com.soreak.entity.BlogComment;
import com.soreak.entity.UserEntity;
import com.soreak.entity.VO.BlogVO;
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

import java.util.List;

/**
 * @program: welog
 * @author: soreak
 * @description:
 * @create: 2020-12-23 19:43
 **/
@Controller
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private UserService userService;

    @Autowired
    private BlogCommentService blogCommentService;

    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id, Model model){
        String phone = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println(SecurityContextHolder.getContext().getAuthentication());
        if (phone!=null){
            UserEntity byPhone = userService.findByPhone(phone);
            model.addAttribute("user",byPhone);
        }

        BlogVO blog =blogService.getBlogById(id);

        model.addAttribute("blog",blog);
        return "blog";
    }
}
