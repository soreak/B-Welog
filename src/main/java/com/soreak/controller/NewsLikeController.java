package com.soreak.controller;

import com.alibaba.fastjson.JSONObject;
import com.soreak.entity.UserEntity;
import com.soreak.service.NewsLikeService;
import com.soreak.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @program: welog
 * @author: soreak
 * @description:
 * @create: 2021-02-23 21:41
 **/
@Controller
public class NewsLikeController {
    @Autowired
    private NewsLikeService newsLikeService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/NewsLike",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject Like(@RequestParam("newsId") String newsId,
                           @RequestParam("likeStyle") String likeStyle){
        String phone = SecurityContextHolder.getContext().getAuthentication().getName();

        UserEntity byPhone = userService.findByPhone(phone);
        int i = 0;
        if (likeStyle.equals("-1")){
            i = newsLikeService.CreateNewsLike(Long.valueOf(newsId), byPhone.getId());
        }else if (likeStyle.equals("1")){
            i = newsLikeService.DeleteNewsLike(Long.valueOf(newsId), byPhone.getId());
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
