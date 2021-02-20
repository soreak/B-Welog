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
 * @description: 新闻点赞表
 * @create: 2020-12-17 16:24
 **/
@Data
@TableName(value = "sk_News_like")
public class NewsLike implements Serializable {

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
     * 新闻Id
     */
    private Long newsId;

    /**
     * 状态（赞或踩）
     */
    private Integer status;

    /**
     * 更新时间
     */
    private Date updateTime;

}
