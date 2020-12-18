package com.soreak.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: welog
 * @author: soreak
 * @description: 博客评论表
 * @create: 2020-12-17 16:24
 **/
@Data
@TableName(value = "sk_blog_commend")
public class BlogCommend implements Serializable {

    /**
     * 标识符Id
     */
    @TableId
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 博客ID
     */
    private Long blogId;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论创建时间
     */
    private Date createTime;

    /**
     * 父级评论
     */
    private Long parentCommentId;
}
