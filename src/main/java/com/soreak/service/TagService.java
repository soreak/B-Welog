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

    List<Tag> getTagByTopicId(Long topicId);

    List<TagVO> getTagNameAndCount();

    List<TagVO> getAllTagNameAndCountByBlog();

    List<TagVO> getTagNameAndCountByNews();

    List<TagVO> getAllTagNameAndCountByNews();

    List<TagVO> getTagNameAndCountByTopics();

    List<TagVO> getAllTagNameAndCountByTopics();

    Tag getTagById(Long id);

    Long saveTag(Tag tag);

    int updateTag(Tag tag);

    int deleteTag(Long id);

    List<Tag> getAllTag();

    Tag selectTagByName(String name);
}
