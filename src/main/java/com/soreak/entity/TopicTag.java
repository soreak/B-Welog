package com.soreak.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @program: welog
 * @author: soreak
 * @description: 话题标签连接
 * @create: 2021-02-28 21:56
 **/
@Data
@TableName(value = "sk_topic_tag")
public class TopicTag implements Serializable {
    /**
     * 标识符Id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 标签id
     */
    private Long tagId;

    /**
     * 对应博客Id
     */
    private Long topicId;
}
