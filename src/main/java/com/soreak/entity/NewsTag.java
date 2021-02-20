package com.soreak.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @program: welog
 * @author: soreak
 * @description: 新闻标签表
 * @create: 2020-12-17 16:18
 **/
@Data
@TableName(value = "sk_news_tag")
public class NewsTag implements Serializable {

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
     * 对应新闻Id
     */
    private Long newsId;


}
