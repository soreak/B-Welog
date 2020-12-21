package com.soreak.entity.VO;

import lombok.Data;

import java.util.Date;

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

    private Date updateTime;

    private String views;

    private String userName;

    private String userAvatar;


}
