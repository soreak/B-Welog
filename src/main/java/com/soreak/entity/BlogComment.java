package com.soreak.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @program: welog
 * @author: soreak
 * @description: 博客评论表
 * @create: 2020-12-17 16:24
 **/
@Data
@TableName(value = "sk_blog_comment")
public class BlogComment implements Serializable {

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
     * 判断已读
     */
    private int status;

    /**
     * 评论创建时间
     */
    private Date createTime;

    /**
     * 父级评论
     */
    private Long parentCommentId;

    /**
     * 子级评论
     */
    @TableField(exist = false)
    private List<BlogComment> replyComments;
}
