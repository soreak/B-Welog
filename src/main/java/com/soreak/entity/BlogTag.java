package com.soreak.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @program: welog
 * @author: soreak
 * @description: 博客标签表
 * @create: 2020-12-17 16:18
 **/
@Data
@TableName(value = "sk_blog_tag")
public class BlogTag implements Serializable {

    /**
     * 标识符Id
     */
    @TableId
    private Long id;

    /**
     * 标签id
     */
    private String tagId;

    /**
     * 对应博客Id
     */
    private Long blogId;


}
