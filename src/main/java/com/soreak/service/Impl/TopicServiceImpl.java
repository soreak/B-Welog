package com.soreak.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.soreak.dao.TagDao;
import com.soreak.dao.TopicDao;
import com.soreak.dao.TopicTagDao;
import com.soreak.entity.Blog;
import com.soreak.entity.BlogTag;
import com.soreak.entity.Topic;
import com.soreak.entity.TopicTag;
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
    public List<TopicVO> getAllTopicList() {
        return topicDao.getAllTopicList();
    }

    @Override
    public List<TopicVO> getMyTopicListByUserId(Long userId) {
        return topicDao.getMyTopicListByUserId(userId);
    }

    @Override
    public List<TopicVO> getTopicListByTagId(Long tagId) {
        List<TopicVO> topicListByTagId = topicDao.getTopicListByTagId(tagId);
        for (TopicVO topicVO:
             topicListByTagId) {
            if (topicVO.getContent().length()>50){
                topicVO.setContent(topicVO.getContent().substring(0,50));
            }
            topicVO.setTags(tagDao.getTagByTopicId(topicVO.getId()));
        }
        return topicListByTagId;
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

    @Override
    public int deleteTopicById(Long id) {
        QueryWrapper<TopicTag> wrapper = new QueryWrapper<>();
        wrapper.eq("topic_id",id);
        topicTagDao.delete(wrapper);
        return topicDao.deleteById(id);
    }

    @Override
    public List<Topic> searchTopic(String title, String tagId, int published) {
        QueryWrapper<Topic> wrapper =new QueryWrapper<>();

        if (tagId.equals("-1")) {
            if (!title.equals("soreak")) {
                wrapper.like("title", title);
            }
            if (published != -1) {
                wrapper.eq("published", published);
            }
            return topicDao.selectList(wrapper);
        }else {
            if (!title.equals("soreak")&&published != -1) {
                return topicDao.searchTopicListByTTP(title,tagId,published);
            }else if (title.equals("soreak")&&published != -1) {
                return topicDao.searchTopicListByTP(tagId,published);
            }else if (!title.equals("soreak")&&published == -1) {
                return topicDao.searchTopicListByTT(title,tagId);
            }else{
                return topicDao.searchTopicListByT(tagId);
            }

        }
    }

    @Override
    public List<TopicVO> searchTopicByQuery(String query) {
        return topicDao.searchTopicByQuery(query);
    }
}
