package com.soreak.config;


import com.soreak.entity.UserEntity;
import com.soreak.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: welog
 * @author: soreak
 * @description:
 * @create: 2020-12-19 17:24
 **/
@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserEntity user = userService.getUserInfo(s);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        // 得到用户角色
        String role = user.getRole();
        String[] split = role.split(",");

        if (role.equals("-1") ){
            throw new UsernameNotFoundException("用户被封禁");
        }else{
            // 角色集合
            List<GrantedAuthority> authorities = new ArrayList<>();
            // 角色必须以`ROLE_`开头，数据库中没有，则在这里加
            for (String s1: split) {
                authorities.add(new SimpleGrantedAuthority("ROLE_" + s1));
            }
            return new User(user.getPhone(),user.getPassword(),authorities);
        }

    }
}

