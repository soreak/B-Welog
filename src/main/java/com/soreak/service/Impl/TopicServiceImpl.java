package com.soreak.service.Impl;

import com.soreak.dao.TagDao;
import com.soreak.dao.TopicDao;
import com.soreak.dao.TopicTagDao;
import com.soreak.entity.Blog;
import com.soreak.entity.Topic;
import com.soreak.entity.VO.TopicVO;
import com.soreak.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @program: welog
 * @author: soreak
 * @description:
 * @create: 2021-02-28 22:18
 **/
@Service
public class TopicServiceImpl implements TopicService{

    @Autowired
    private TopicDao topicDao;

    @Autowired
    private TagDao tagDao;

    @Autowired
    private TopicTagDao topicTagDao;

    @Override
    public List<TopicVO> getTopicList() {
        return topicDao.getTopicList();
    }

    @Override
    public List<TopicVO> getMyTopicListByUserId(Long userId) {
        return topicDao.getMyTopicListByUserId(userId);
    }

    @Override
    public TopicVO getOneTopicById(Long id,int flag) {
        TopicVO topicVO= topicDao.getOneTopicById(id);
        topicVO.setTags(tagDao.getTagByTopicId(id));
        if (flag == 1){
            topicDao.updateViews(id);
        }
        return topicVO;
    }

    @Override
    public Long saveTopic(Topic topic) {
        topic.setCreateTime(new Date());
        topic.setUpdateTime(new Date());
        topic.setViews(0);
        return topicDao.saveTopic(topic);
    }

    @Override
    public Long updateTopic(Topic topic) {
        topic.setUpdateTime(new Date());
        return topicDao.updateTopic(topic);
    }
}
