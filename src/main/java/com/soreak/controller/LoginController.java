package com.soreak.controller;


import com.soreak.service.UserService;
import com.soreak.utils.Captcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: welog
 * @author: soreak
 * @description: 登录控制器
 * @create: 2020-12-18 10:04
 **/
@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String loginPage(){

        return "/login";
    }

    @PostMapping("/login")
    public String login(){
        return "login";
    }



    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/login";
    }





}
