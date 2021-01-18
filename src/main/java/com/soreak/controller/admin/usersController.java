package com.soreak.controller.admin;

import com.soreak.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @GetMapping("/index")
    public String index(Model model)
    {

        return "admin/index";
    }

    @PostMapping("/users")
    public String list(Model model)
    {
        model.addAttribute("users",userService.getAllUser());
        return "admin/users";
    }

}
