package com.soreak.service.Impl;

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

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public List<TagVO> getTagNameAndCount() {
        return tagDao.getTagNameAndCount();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public List<TagVO> getTagNameAndCountByNews() {
        return tagDao.getTagNameAndCountByNews();
    }

    @Override
    public Long saveTag(Tag tag) {
        return tagDao.saveBlog(tag);
    }
}
