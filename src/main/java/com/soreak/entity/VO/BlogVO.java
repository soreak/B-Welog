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

    private String tagIds;

    public void init() {
        this.tagIds = tagsToIds(this.getTags());
    }

    private String tagsToIds(List<Tag> tags) {
        StringBuilder ids = new StringBuilder();
        if (!tags.isEmpty()) {
            boolean flag = false;
            // 1,2,3
            for (Tag tag : tags) {
                if (flag) {
                    ids.append(",");
                } else {
                    flag = true;
                }
                ids.append(tag.getId());
            }
        }
        return ids.toString();
    }

}
