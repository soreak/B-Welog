package com.soreak.controller;

import com.soreak.entity.Blog;
import com.soreak.entity.UserEntity;
import com.soreak.entity.VO.BlogVO;
import com.soreak.entity.VO.TagVO;
import com.soreak.service.UserFollowService;
import com.soreak.service.UserService;
import com.soreak.utils.HTMLUtils;
import com.soreak.utils.phoneVerify.util.RandomNum;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @program: welog
 * @author: soreak
 * @description:
 * @create: 2021-01-06 16:34
 **/
@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserFollowService userFollowService;



    @Value("${absoluteImgPath}")
    String absoluteImgPath;

    @Value("${sonImgPath}")
    String sonImgPath;

    @GetMapping("/showInfo/{id}")
    @PreAuthorize("isAuthenticated()")
    public String showInfo(@PathVariable Long id, Model model){
        String phone = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity master = userService.findByPhone(phone);
        model.addAttribute("master",master);
        UserEntity userById = userService.getUserById(id);
        if (phone.equals(userById.getPhone())){
            model.addAttribute("self","1");
        }
        List<UserEntity> masterLikeList = userFollowService.selectFollowByUId(master.getId());
        int followLikeFlag = 0;
        for (UserEntity u : masterLikeList) {
            if (id.equals(u.getId())){
                followLikeFlag = 1;
            }
        }
        model.addAttribute("followLikeFlag",followLikeFlag);
        List<UserEntity> list;
        list = userFollowService.selectFollowByUId(id);
        model.addAttribute("like",list.size());
        list = userFollowService.selectFollowByUFId(id);
        model.addAttribute("fan",list.size());
        model.addAttribute("user",userById);
        return "showInfo";
    }
    @RequestMapping(value = "/editInfo",method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public String editInfo(Model model){
       getUser(model);
        return "editInfo";
    }


    private Model getUser(Model model){
        String phone = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("master",userService.findByPhone(phone));
        return model;
    }

    @RequestMapping(value = "/saveInfo",method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public String  save(@RequestParam("avatar") MultipartFile avatar,
                       @RequestParam("nickname") String nickname,
                       @RequestParam("information") String information,
                       @RequestParam("git") String git,
                       @RequestParam("qq") String qq,
                       @RequestParam("wechat") String wechat,
                       @RequestParam("email") String email,
                       Model model){

        String phone = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userService.findByPhone(phone);
        String oldAvatar = user.getAvatar();
        String replace = oldAvatar.replace(sonImgPath, "");
        String delpath = absoluteImgPath+replace;
        if (replace != "avatar.jpg"){
            File f1 = new File(delpath);
            f1.delete();
        }


            String originalFilename = avatar.getOriginalFilename();
            //随机生成文件名
            String fileName = RandomNum.createRandomBySize(10) + originalFilename;
            File dest = new File(absoluteImgPath + fileName);
            String imgUrl = sonImgPath +fileName;
            System.out.println(imgUrl);
            try{
                avatar.transferTo(dest);
                user.setAvatar(imgUrl);
            }catch (IllegalStateException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }

        user.setNickname(nickname);
        user.setInformation(information);
        user.setGit(git);
        user.setQq(qq);
        user.setWechat(wechat);
        user.setEmail(email);

        userService.saveUser(user);

        getUser(model);
        showInfo(user.getId(),model);

        return "showInfo";


    }





}
