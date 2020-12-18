package com.soreak.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: welog
 * @author: soreak
 * @description: 博客表
 * @create: 2020-12-17 16:07
 **/
@Data
@TableName(value = "sk_blog")
public class Blog implements Serializable {

    /**
     * 标识符 ID
     */
    @TableId
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 类型（判断是否为原创）
     */
    private String  flag;

    /**
     * 是否开启讨论区
     */
    private boolean commentabled;

    /**
     * 是否为推荐
     */
    private boolean recommend;

    /**
     * 发布状态（草稿，发表）
     */
    private boolean published;

    /**
     * 访问量
     */
    private String views;





}
