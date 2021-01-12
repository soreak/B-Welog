package com.soreak.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @program: welog
 * @author: soreak
 * @description: 用户表
 * @create: 2020-12-17 16:07
 **/

@Data
@TableName(value = "sk_user")
public class UserEntity implements Serializable {

    /**
     * 标识符 ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 呢称
     */
    private String nickname;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 密码
     */
    private String password;

    /**
     * 个人介绍
     */
    private String information;

    /**
     * 账户角色
     */
    private String role;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 邮箱
     */
    private String email;

    /**
     * github
     */
    private String git;

    /**
     * QQ
     */
    private String qq;
    /**
     * QQ
     */
    private String wechat;


}
