package com.soreak.controller.admin;


import com.alibaba.fastjson.JSONObject;
import com.soreak.entity.UserEntity;
import com.soreak.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collection;
import java.util.List;

/**
 * @program: welog
 * @author: soreak
 * @description:
 * @create: 2021-01-18 16:19
 **/
@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('1') or hasRole('2')")
public class usersController {
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/index")
    public String index()
    {

        return "admin/index";
    }

    @GetMapping("/users")
    public String list(Model model)
    {
        List<UserEntity> allUser = userService.getUserNotAdmin();
        Authentication phone = SecurityContextHolder.getContext().getAuthentication();
        for (GrantedAuthority auth :
                phone.getAuthorities()) {
            if (auth.getAuthority().equals("ROLE_2")) {
               allUser = userService.getAllUser();
            }
        }
        System.out.println(phone);
        model.addAttribute("users",allUser);
        return "admin/users";
    }

    @RequestMapping(value = "/oneUser",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject oneUser(@RequestParam("id") Long id){
        UserEntity userById = userService.getUserById(id);
        userById.setPassword("");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("user",userById);
        return jsonObject;
    }
    @RequestMapping(value = "/editUser",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject  editUser(@RequestParam("id") Long id,
                            @RequestParam("nickname") String nickname,
                            @RequestParam("phone") String phone,
                            @RequestParam("role") String role,
                            @RequestParam(value = "password",defaultValue = "-1") String password,
                            @RequestParam("resetAvatar") boolean resetAvatar){
       UserEntity userEntity = new UserEntity();
       userEntity.setId(id);
       userEntity.setNickname(nickname);
       userEntity.setPhone(phone);
       userEntity.setRole(role);
        System.out.println(password);
       if (!"-1".equals(password)){
           String newPassword = passwordEncoder.encode(password);
           userEntity.setPassword(newPassword);
       }
       if (resetAvatar == true){
           userEntity.setAvatar("/imgs/avatar.jpg");
       }
        int i = userService.saveUser(userEntity);
       JSONObject jsonObject = new JSONObject();
       if (i !=1){
           jsonObject.put("message","操作失败");
       }
        return jsonObject;
    }

    @PostMapping("/users/search")
    public String search( @RequestParam(value = "nickname",defaultValue = "soreak") String nickname,
                          @RequestParam(value = "role",defaultValue = "-1") String role,
                          Model model){
        List<UserEntity> userEntities = userService.searchUser(nickname, role);
        model.addAttribute("users",userEntities);
        return "admin/users :: userList";
    }
    @GetMapping("/users/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        userService.deleteUser(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/users";
    }

}
