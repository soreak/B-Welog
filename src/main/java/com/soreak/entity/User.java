package com.soreak.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: welog
 * @author: soreak
 * @description: 用户表
 * @create: 2020-12-17 16:07
 **/

@Data
@TableName(value = "sk_user")
public class User implements Serializable {

    /**
     * 标识符 ID
     */
    @TableId
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
     * 账户状态
     */
    private Integer type;

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


}
