package com.soreak.controller;

import com.alibaba.fastjson.JSONObject;
import com.soreak.entity.UserEntity;
import com.soreak.entity.VO.BlogVO;
import com.soreak.service.BlogLikeService;
import com.soreak.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @program: welog
 * @author: soreak
 * @description:
 * @create: 2021-01-28 15:45
 **/
@Controller
public class BlogLikeController {
    @Autowired
    private BlogLikeService blogLikeService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/BlogLike",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject Like(@RequestParam("blogId") String blogId,
                           @RequestParam("likeStyle") String likeStyle){
        String phone = SecurityContextHolder.getContext().getAuthentication().getName();

        UserEntity byPhone = userService.findByPhone(phone);
        int i = 0;
        if (likeStyle.equals("-1")){
            i = blogLikeService.CreateBlogLike(Long.valueOf(blogId), byPhone.getId());
        }else if (likeStyle.equals("1")){
            i = blogLikeService.DeleteBlogLike(Long.valueOf(blogId), byPhone.getId());
        }


        JSONObject jsonObject = new JSONObject();
        if (i == 1){
            jsonObject.put("status","success");
        }else {
            jsonObject.put("status","error");
        }

        return jsonObject;
    }
}
