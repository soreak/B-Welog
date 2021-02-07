package com.soreak.controller;

import com.alibaba.fastjson.JSONObject;
import com.soreak.entity.UserEntity;
import com.soreak.entity.UserFollow;
import com.soreak.service.UserFollowService;
import com.soreak.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: welog
 * @author: soreak
 * @description: \
 * @create: 2021-01-29 16:48
 **/
@Controller
public class UserFollowController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserFollowService userFollowService;



    @RequestMapping(value = "/userLike/like/{id}",method = RequestMethod.GET)
    public String like(@PathVariable Long id, Model model){
        UserEntity byPhone = setUser(model);
        UserEntity userById = userService.getUserById(id);
        if (id.equals(byPhone.getId())){
            model.addAttribute("self","1");
        }
        List<UserEntity> userEntities = userFollowService.selectFollowByUId(id);
        model.addAttribute("users",userEntities);
        model.addAttribute("flag","1");
        model.addAttribute("id",id);
        model.addAttribute("name",userById.getNickname());
        return "follow";
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

    @RequestMapping(value = "/userLike/fan/{id}",method = RequestMethod.GET)
    public String fan(@PathVariable Long id,Model model){
        UserEntity byPhone = setUser(model);
        UserEntity userById = userService.getUserById(id);
        if (id.equals(byPhone.getId())){
            model.addAttribute("self","1");
        }
        List<UserEntity> userEntities = userFollowService.selectFollowByUFId(id);
        model.addAttribute("users",userEntities);
        model.addAttribute("flag","2");
        model.addAttribute("id",id);
        model.addAttribute("name",userById.getNickname());
        return "follow";
    }

    @RequestMapping(value = "/FollowOperate",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject Like(@RequestParam("UFId") String UFId,
                           @RequestParam("followFlag") String followFlag){
        String phone = SecurityContextHolder.getContext().getAuthentication().getName();
        UFId = UFId.replace("uid:","");
        UserEntity byPhone = userService.findByPhone(phone);
        int i = 0;
        if (followFlag.equals("-1")){
            i = userFollowService.createFollow(byPhone.getId(),Long.valueOf(UFId));
        }else if (followFlag.equals("1")){
            i = userFollowService.deleteFollow(byPhone.getId(),Long.valueOf(UFId));
        }


        JSONObject jsonObject = new JSONObject();
        if (i == 1){
            jsonObject.put("status","success");
        }else {
            jsonObject.put("status","error");
        }

        return jsonObject;
    }


    @RequestMapping(value = "/fanOperate",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject fan(@RequestParam("UFId") String UFId,
                           @RequestParam("fanFlag") String fanFlag){
        String phone = SecurityContextHolder.getContext().getAuthentication().getName();
        UFId = UFId.replace("uid:","");
        UserEntity byPhone = userService.findByPhone(phone);
        int i = 0;
        if (fanFlag.equals("-1")){
            i = userFollowService.createFollow(Long.valueOf(UFId),byPhone.getId());
        }else if (fanFlag.equals("1")){
            i = userFollowService.deleteFollow(Long.valueOf(UFId),byPhone.getId());
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
