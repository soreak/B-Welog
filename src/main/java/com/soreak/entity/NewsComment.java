package com.soreak.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * @description: 新闻评论表
 * @create: 2020-12-17 16:24
 **/
@Data
@TableName(value = "sk_news_comment")
public class NewsComment implements Serializable {

    /**
     * 标识符Id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 新闻ID
     */
    private Long newsId;

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
    private List<NewsComment> replyComments;
}
