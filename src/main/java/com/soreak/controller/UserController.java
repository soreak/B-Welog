package com.soreak.controller;

import com.alibaba.fastjson.JSONObject;
import com.soreak.entity.UserEntity;
import com.soreak.entity.VO.BlogVO;
import com.soreak.service.BlogLikeService;
import com.soreak.service.BlogService;
import com.soreak.service.UserFollowService;
import com.soreak.service.UserService;
import com.soreak.utils.phoneVerify.util.RandomNum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
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

    @Autowired
    private BlogLikeService blogLikeService;

    @Autowired
    private BlogService blogService;

    @Autowired
    private PasswordEncoder passwordEncoder;



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
        if (userById == null){
            return "/error/404";
        }
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

        List<BlogVO> myBlogListByUserId = blogService.getMyBlogListByUserId(id);
        for (BlogVO b : myBlogListByUserId) {
            b.setLikeCount(blogLikeService.selectBlogLikeCountByBlogId(b.getId()));
        }

        model.addAttribute("blogList",myBlogListByUserId);

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

        if (!avatar.isEmpty()){
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
            }catch (IllegalStateException | IOException e){
                e.printStackTrace();
            }

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

    @RequestMapping(value = "/editPassword",method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public String editPassword(Model model){
        getUser(model);
        return "resetPwd";
    }



    @PostMapping("/loginResetPwd")
    @ResponseBody
    @PreAuthorize("isAuthenticated()")
    public JSONObject loginResetPwd(@RequestParam("oldPwd") String oldPwd,
                                    @RequestParam("newPwd") String newPwd,
                                    HttpSession session){
        String phone = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userService.findByPhone(phone);
       JSONObject jsonObject = new JSONObject();
       if (!passwordEncoder.matches(oldPwd,user.getPassword())){
           jsonObject.put("status",500);
           jsonObject.put("message","原密码错误");
           return jsonObject;
       }

        if (oldPwd.equals(newPwd)){
            jsonObject.put("status",500);
            jsonObject.put("message","不能设置与之前相同的密码");
            return jsonObject;
        }
        String newP = passwordEncoder.encode(newPwd);
        user.setPassword(newP);
        userService.saveUser(user);
        jsonObject.put("status",200);
        jsonObject.put("message","修改成功，请重新登录");
        session.removeAttribute("user");
        return jsonObject;
    }



}
