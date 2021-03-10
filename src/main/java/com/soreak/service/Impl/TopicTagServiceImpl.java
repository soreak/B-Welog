package com.soreak.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.soreak.dao.TopicTagDao;
import com.soreak.entity.BlogTag;
import com.soreak.entity.TopicTag;
import com.soreak.service.TopicTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 * @program: welog
 * @author: soreak
 * @description:
 * @create: 2021-03-02 17:43
 **/
@Repository
public class TopicTagServiceImpl implements TopicTagService {
    @Autowired
    private TopicTagDao topicTagDao;


    @Override
    public int save(TopicTag topicTag) {
        return topicTagDao.insert(topicTag);
    }

    @Override
    public int deleteByTopicId(Long id) {
        QueryWrapper<TopicTag> wrapper= new QueryWrapper<>();
        wrapper.eq("topic_id",id);
        return topicTagDao.delete(wrapper);
    }

    @Override
    public int deleteByTagId(Long id) {
        QueryWrapper<TopicTag> wrapper = new QueryWrapper<>();
        wrapper.eq("tag_id",id);
        return topicTagDao.delete(wrapper);
    }
}
