package com.soreak.controller;

import com.soreak.entity.Blog;
import com.soreak.entity.VO.BlogVO;
import com.soreak.entity.VO.TagVO;
import com.soreak.service.UserService;
import com.soreak.utils.HTMLUtils;
import com.soreak.utils.phoneVerify.util.RandomNum;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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



    @Value("${absoluteImgPath}")
    String absoluteImgPath;

    @Value("${sonImgPath}")
    String sonImgPath;

    @GetMapping("/showInfo")
    public String showInfo(Model model){
        String phone = SecurityContextHolder.getContext().getAuthentication().getName();

        model.addAttribute("user",userService.findByPhone(phone));

        return "showInfo";
    }
    @RequestMapping(value = "/showInfo",method = RequestMethod.POST)
    public String editInfo(Model model){
        String phone = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("user",userService.findByPhone(phone));
        return "editInfo";
    }
    @RequestMapping(value = "/saveInfo",method = RequestMethod.POST)
    public String save(@RequestParam("avatar") MultipartFile avatar,
                       @RequestParam("nickname") String nickname,
                       @RequestParam("information") String information,
                       @RequestParam("git") String git,
                       @RequestParam("qq") String qq,
                       @RequestParam("wechat") String wechat,
                       @RequestParam("email") String email,
                       @RequestParam("id") Long id,
                       Model model){

        if(avatar.isEmpty()){

        }
        String originalFilename = avatar.getOriginalFilename();
        //随机生成文件名
        String fileName = RandomNum.createRandomBySize(10) + originalFilename;
        File dest = new File(absoluteImgPath + fileName);
        String imgUrl = sonImgPath +fileName;
        try{
            avatar.transferTo(dest);


        }catch (IllegalStateException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }

    return "showInfo";


    }





}
