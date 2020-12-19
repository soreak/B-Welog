package com.soreak.controller;

import com.soreak.entity.UserEntity;
import com.soreak.service.RegisterService;
import com.soreak.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @program: welog
 * @author: soreak
 * @description: 注册控制器
 * @create: 2020-12-19 15:02
 **/
@Controller
public class RegisterController {

    private static final String FLAGHEADER = "soreak";

    @Autowired
    private RegisterService registerService;

    @GetMapping("/registered")
    public String registerPage(){
        return "/registered";
    }

    @PostMapping("/registered")
    public String register(@RequestParam String phone,
                        @RequestParam String password,
                        @RequestParam String nickname,
                        @RequestParam String flag,
                        RedirectAttributes attributes){
        if(flag != FLAGHEADER+phone ){
            attributes.addFlashAttribute("message","这好像不是您刚刚验证的手机");
            return "redirect:/registered";
        }

        int resultName = registerService.findByUsername(nickname);
        if (resultName == 0){
            UserEntity userEntity = new UserEntity();
            userEntity.setNickname(nickname);
            userEntity.setPhone(phone);
            userEntity.setPassword(MD5Utils.code(password));
            int result = registerService.insUser(userEntity);
            if (result >0){
                return "/login";
            }else {
                attributes.addFlashAttribute("message","注册失败");
                return "redirect:/registered";
            }
        }else {
            attributes.addFlashAttribute("message","用户名已被注册");
            return "redirect:/registered";
        }

    }



}
