package com.soreak.entity.VO;

import com.soreak.entity.Tag;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @program: welog
 * @author: soreak
 * @description: 首页博客数据处理
 * @create: 2020-12-21 15:38
 **/
@Data
public class BlogVO {
    private Long id;

    private String title;

    private String content;

    private Date createTime;

    private Date updateTime;

    private boolean commentabled;

    private boolean recommend;

    private boolean published;

    private String flag;

    private String views;

    private String userName;

    private String userAvatar;

    private List<Tag> tags;

}
