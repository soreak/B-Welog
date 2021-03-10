package com.soreak.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.soreak.dao.TagDao;
import com.soreak.entity.Tag;
import com.soreak.entity.VO.TagVO;
import com.soreak.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @program: welog
 * @author: soreak
 * @description:
 * @create: 2020-12-20 14:47
 **/
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagDao tagDao;

    @Override
    public List<Tag> getTagByBlogId(Long blogId) {
        return tagDao.getTagByBlogId(blogId);
    }


    @Override
    public List<Tag> getTagByNewsId(Long newsId) {
        return tagDao.getTagByNewsId(newsId);
    }

    @Override
    public List<Tag> getTagByTopicId(Long topicId) {
        return tagDao.getTagByTopicId(topicId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public List<TagVO> getTagNameAndCount() {
        return tagDao.getTagNameAndCount();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public List<TagVO> getAllTagNameAndCountByBlog() {
        return tagDao.getAllTagNameAndCountByBlog();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public List<TagVO> getTagNameAndCountByNews() {
        return tagDao.getTagNameAndCountByNews();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public List<TagVO> getAllTagNameAndCountByNews() {
        return tagDao.getAllTagNameAndCountByNews();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public List<TagVO> getTagNameAndCountByTopics() {
        return tagDao.getTagNameAndCountByTopics();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public List<TagVO> getAllTagNameAndCountByTopics() {
        return tagDao.getAllTagNameAndCountByTopics();
    }

    @Override
    public Tag getTagById(Long id) {
        return tagDao.getTagById(id);
    }

    @Override
    public Long saveTag(Tag tag) {
        return tagDao.saveBlog(tag);
    }

    @Override
    public int updateTag(Tag tag) {
        return tagDao.updateById(tag);
    }

    @Override
    public int deleteTag(Long id) {
        return tagDao.delete(new QueryWrapper<Tag>().eq("id",id));
    }

    @Override
    public List<Tag> getAllTag() {
        return tagDao.selectList(new QueryWrapper<Tag>().orderByAsc("id"));
    }


    @Override
    public Tag selectTagByName(String name) {
        QueryWrapper<Tag> wrapper = new QueryWrapper<>();
        wrapper.eq("name",name);
        return tagDao.selectOne(wrapper);
    }
}
