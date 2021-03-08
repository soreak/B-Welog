package com.soreak.service;

import com.soreak.entity.Tag;
import com.soreak.entity.VO.TagVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @program: welog
 * @author: soreak
 * @description:
 * @create: 2020-12-20 14:45
 **/
public interface TagService {

    List<Tag> getTagByBlogId(Long blogId);

    List<Tag> getTagByNewsId(Long newsId);

    List<TagVO> getTagNameAndCount();

    List<TagVO> getTagNameAndCountByNews();

    List<TagVO> getTagNameAndCountByTopics();

    Tag getTagById(Long id);

    Long saveTag(Tag tag);

    Tag selectTagByName(String name);
}
