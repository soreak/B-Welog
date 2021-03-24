package com.soreak.utils;

import lombok.Data;

import java.util.Date;

/**
 * @program: welog
 * @author: soreak
 * @description: 验证码验证
 * @create: 2021-03-24 21:43
 **/
@Data
public class Captcha {
    private String phone;

    private String code;

    private Date createTime;
}
