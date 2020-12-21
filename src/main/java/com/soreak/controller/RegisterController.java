package com.soreak.controller;

import com.soreak.entity.UserEntity;
import com.soreak.service.RegisterService;
import com.soreak.service.UserService;
import com.soreak.utils.MD5Utils;
import com.soreak.utils.phoneVerify.util.SMSUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.alibaba.fastjson.JSONObject;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @program: welog
 * @author: soreak
 * @description: 注册控制器
 * @create: 2020-12-19 15:02
 **/
@Controller
public class RegisterController {

    private static final String FLAGHEADER = "soreak";
    private String Encode;

    @Autowired
    private RegisterService registerService;

    @Autowired
    private UserService userService;



    @GetMapping("/registered")
    public String registerPage(){
        return "/registered";
    }

    @PostMapping("/registered")
    @ResponseBody
    public JSONObject registered(@RequestParam String phone,
                        @RequestParam String password,
                        @RequestParam String username,
                        @RequestParam String flag,
                        @RequestParam String code){
        JSONObject jsonObject = new JSONObject();
        System.out.println(flag);
        System.out.println(FLAGHEADER+phone);
        if (!code.equals(Encode)){
            jsonObject.put("status",500);
            jsonObject.put("message","验证码错误");
            return jsonObject;
        }

        System.out.println(flag);
        System.out.println(FLAGHEADER+phone);
        if(!flag.equals( FLAGHEADER + phone)){
            jsonObject.put("status",500);
            jsonObject.put("message","请重新输入手机号码");
            return jsonObject;
        }

        int resultName = registerService.findByUsername(username);
        if (resultName == 0){
            UserEntity userEntity = new UserEntity();
            userEntity.setNickname(username);
            userEntity.setPhone(phone);
            userEntity.setPassword(password);
            int result = registerService.insUser(userEntity);
            if (result >0){
                jsonObject.put("status",200);
                return jsonObject;
            }else {
                jsonObject.put("status",500);
                jsonObject.put("message","注册失败");
                return jsonObject;
            }
        }else {
            jsonObject.put("status",500);
            jsonObject.put("message","用户名已被注册");
            return jsonObject;
        }

    }

    @PostMapping("/getCode")
    @ResponseBody
    public JSONObject getCode(@RequestParam("phone") String phone){
        try {
            //Encode = SMSUtil.sendSMS(phone);
            Encode = "123456";
            System.out.println(Encode);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("status",200);
            jsonObject.put("flag",FLAGHEADER+phone);
            return jsonObject;
        }catch (Exception e){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("status",500);
            return jsonObject;
        }
    }

    @PostMapping("/phoneCheck")
    @ResponseBody
    public JSONObject phoneCheck(@RequestParam("phone") String phone){
        int result = userService.findByPhone(phone);
        System.out.println(result);
        JSONObject jsonObject = new JSONObject();
        if(result == 0){
            jsonObject.put("status",200);
        }else{
            jsonObject.put("status",500);
        }
        return jsonObject;
    }



}
