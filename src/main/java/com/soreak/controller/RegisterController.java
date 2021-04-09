package com.soreak.controller;

import com.soreak.entity.UserEntity;
import com.soreak.service.RegisterService;
import com.soreak.service.UserService;
import com.soreak.utils.Captcha;
import com.soreak.utils.MD5Utils;
import com.soreak.utils.phoneVerify.util.SMSUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.alibaba.fastjson.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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

    private Map<String, Captcha> codeMap = new HashMap<>() ;

    @Autowired
    private RegisterService registerService;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @GetMapping("/registered")
    public String registerPage(){
        return "/registered";
    }

    @PostMapping("/registered")
    @ResponseBody
    public JSONObject registered(@RequestParam String phone,
                        @RequestParam String password,
                        @RequestParam String username,
                        @RequestParam String code){
        JSONObject jsonObject = new JSONObject();

        Captcha captcha = codeMap.get(phone);
        if (captcha== null){
            jsonObject.put("status",500);
            jsonObject.put("message","手机号码错误");
            return jsonObject;
        }
        if((new Date().getTime() - captcha.getCreateTime().getTime())/1000/60>1){
            jsonObject.put("status",500);
            jsonObject.put("message","验证码已过期，请重新发送");
            return jsonObject;
        }

        if(!captcha.getCode().equals(code)){
            jsonObject.put("status",500);
            jsonObject.put("message","验证码错误");
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
            }else {
                jsonObject.put("status",500);
                jsonObject.put("message","注册失败");
            }
        }else {
            jsonObject.put("status",500);
            jsonObject.put("message","用户名已被注册");
        }
        return jsonObject;

    }




    @PostMapping("/resetPassword")
    @ResponseBody
    public JSONObject resetPassword(@RequestParam String phone,
                                 @RequestParam String newPassword,
                                 @RequestParam String code){
        JSONObject jsonObject = new JSONObject();
        Captcha captcha = codeMap.get(phone);
        if (captcha== null){
            jsonObject.put("status",500);
            jsonObject.put("message","手机号码错误");
            return jsonObject;
        }
        if((new Date().getTime() - captcha.getCreateTime().getTime())/1000/60>1){
            jsonObject.put("status",500);
            jsonObject.put("message","验证码已过期，请重新发送");
            return jsonObject;
        }

        if(!captcha.getCode().equals(code)){
            jsonObject.put("status",500);
            jsonObject.put("message","验证码错误");
            return jsonObject;
        }

        UserEntity userServiceByPhone = userService.findByPhone(phone);
        if (userServiceByPhone != null){
            String newPwd = passwordEncoder.encode(newPassword);
            userServiceByPhone.setPassword(newPwd);
            int result = userService.saveUser(userServiceByPhone);
            if (result >0){
                jsonObject.put("status",200);
                jsonObject.put("message","修改成功");
            }else {
                jsonObject.put("status",500);
                jsonObject.put("message","修改失败");
            }
        }else {
            jsonObject.put("status",500);
            jsonObject.put("message","该手机未注册");
        }
        return jsonObject;

    }


    @PostMapping("/getCode")
    @ResponseBody
    public JSONObject getCode(@RequestParam("phone") String phone){
        try {
            String Encode = SMSUtil.sendSMS(phone);
            //Encode = "123456";
            Captcha one = new Captcha();
            one.setPhone(phone);
            one.setCode(Encode);
            one.setCreateTime(new Date());
            codeMap.put(phone,one);
            System.out.println("one == > "+one);
            System.out.println("map == > "+codeMap);

            System.out.println(Encode);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("status",200);
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
        UserEntity result = userService.findByPhone(phone);
        System.out.println(result);
        JSONObject jsonObject = new JSONObject();
        if(result == null){
            jsonObject.put("status",200);
        }else{
            jsonObject.put("status",500);
        }
        return jsonObject;
    }



}
