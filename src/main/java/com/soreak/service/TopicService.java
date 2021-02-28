package com.soreak.service;

import com.soreak.entity.Blog;
import com.soreak.entity.Topic;
import com.soreak.entity.VO.BlogVO;
import com.soreak.entity.VO.TopicVO;

import java.util.List;

/**
 * @program: welog
 * @author: soreak
 * @description:
 * @create: 2021-02-28 22:18
 **/
public interface TopicService {
    List<TopicVO> getTopicList();

    List<TopicVO> getMyTopicListByUserId(Long userId);


    Long saveTopic(Topic topic);
}
