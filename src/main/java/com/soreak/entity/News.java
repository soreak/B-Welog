package com.soreak.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: welog
 * @author: soreak
 * @description: 新闻
 * @create: 2021-02-20 21:04
 **/
@Data
@TableName(value = "sk_news")
public class News implements Serializable {
    /**
     * 标识符 ID
     */
    @TableId(type = IdType.AUTO)
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
     * 是否开启讨论区
     */
    private boolean commentabled;
    /**
     * 访问量
     */
    private int views;

    /**
     * 作者id
     */
    private Long userId;


}
