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
 * @description: 博客点赞表
 * @create: 2020-12-17 16:24
 **/
@Data
@TableName(value = "sk_blog_like")
public class BlogLike implements Serializable {

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
     * 博客Id
     */
    private Long blogId;

    /**
     * 状态（是否点赞）
     */
    private Integer status;

    /**
     * 点赞创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

}
