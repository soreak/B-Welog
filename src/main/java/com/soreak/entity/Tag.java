package com.soreak.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


/**
 * @program: welog
 * @author: soreak
 * @description: 标签表
 * @create: 2020-12-19 21:36
 **/
@Data
@TableName("sk_tag")
public class Tag {
    @TableId(type = IdType.AUTO)
    private Long id;

    private String name;



}
