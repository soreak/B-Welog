package com.soreak.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @program: welog
 * @author: soreak
 * @description: 话题评论
 * @create: 2021-02-28 21:55
 **/
@Data
@TableName(value = "sk_topic_comment")
public class TopicComment implements Serializable {
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
     * 博客ID
     */
    private Long topicId;

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
