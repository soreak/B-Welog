package com.soreak.controller.admin;


import com.alibaba.fastjson.JSONObject;
import com.soreak.entity.UserEntity;
import com.soreak.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @program: welog
 * @author: soreak
 * @description:
 * @create: 2021-01-18 16:19
 **/
@Controller
@RequestMapping("/admin")
public class usersController {
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/index")
    public String index(Model model)
    {

        return "admin/index";
    }

    @GetMapping("/users")
    public String list(Model model)
    {
        model.addAttribute("users",userService.getAllUser());
        return "admin/users";
    }

    @RequestMapping(value = "/oneUser",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject one(@RequestParam("id") Long id){
        UserEntity userById = userService.getUserById(id);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("user",userById);
        return jsonObject;
    }

}
