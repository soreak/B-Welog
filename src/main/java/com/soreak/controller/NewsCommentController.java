package com.soreak.controller;

import com.soreak.entity.BlogComment;
import com.soreak.entity.NewsComment;
import com.soreak.entity.UserEntity;
import com.soreak.entity.VO.BlogVO;
import com.soreak.entity.VO.NewsVO;
import com.soreak.service.NewsCommentService;
import com.soreak.service.NewsService;
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
 * @create: 2021-02-23 21:43
 **/
@Controller
public class NewsCommentController {
    @Autowired
    private NewsCommentService newsCommentService;

    @Autowired
    private UserService userService;

    @Autowired
    private NewsService newsService;

    @GetMapping("/newsComments/{id}")
    public String comments(@PathVariable Long id, Model model){
        model.addAttribute("comments",newsCommentService.listCommentByNewsId(id));
       NewsVO oneBlog = newsService.getOneNews(id);
        model.addAttribute("authorId",oneBlog.getUserId());
        return "showNews :: newsCommentList";
    }


    @PostMapping("/newsComments")
    @PreAuthorize("isAuthenticated()")
    public String post(@RequestParam Long parentCommentId,
                       @RequestParam Long newsId,
                       @RequestParam String content){
        String phone = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity userEntity = userService.findByPhone(phone);
        NewsComment comment = new NewsComment();
        comment.setUserId(userEntity.getId());
        comment.setNewsId(newsId);
        comment.setContent(content);
        comment.setCreateTime(new Date());
        comment.setStatus(0);
        if (parentCommentId != -1){
            comment.setParentCommentId(parentCommentId);
        }

        newsCommentService.saveOne(comment);

        return "redirect:/newsComments/"+newsId;
    }

    @PostMapping("/admin/newsComments/delete")
    @PreAuthorize("hasAnyRole('1', '2')")
    public String delete(@RequestParam String id,
                         @RequestParam String newsId){
        newsCommentService.batchDelete(Long.valueOf(newsId),Long.valueOf(id));

        return "redirect:/newsComments/"+newsId;
    }

}
