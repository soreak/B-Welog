package com.soreak.service;

import com.soreak.entity.Blog;
import com.soreak.entity.Topic;
import com.soreak.entity.VO.BlogVO;
import com.soreak.entity.VO.TopicVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @program: welog
 * @author: soreak
 * @description:
 * @create: 2021-02-28 22:18
 **/
public interface TopicService {
    List<TopicVO> getTopicList();

    List<TopicVO> getAllTopicList();

    List<TopicVO> getMyTopicListByUserId(Long userId);

    List<TopicVO> getTopicListByTagId(Long tagId);

    TopicVO getOneTopicById(Long id,int flag);

    Long saveTopic(Topic topic);

    Long updateTopic(Topic topic);

    int deleteTopicById(Long id);

    List<Topic> searchTopic(String title, String tagId, int published);
}
