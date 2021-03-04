package com.soreak.entity.VO;

import com.soreak.entity.Tag;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @program: welog
 * @author: soreak
 * @description:
 * @create: 2021-02-28 22:13
 **/
@Data
public class TopicVO {
    private Long id;

    private String title;

    private String content;

    private Date createTime;

    private Date updateTime;

    private int published;

    private String views;

    private Long userId;

    private String userName;

    private String userAvatar;

    private List<Tag> tags;

    private String tagIds;

    private int commentCount;


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
