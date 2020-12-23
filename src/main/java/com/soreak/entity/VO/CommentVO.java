package com.soreak.entity.VO;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.soreak.entity.BlogComment;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @program: welog
 * @author: soreak
 * @description:
 * @create: 2020-12-23 22:32
 **/
@Data
public class CommentVO {

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

    private List<CommentVO> replyComments;

    private CommentVO parentComment;

    private String nickname;
}
