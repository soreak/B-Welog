package com.soreak.service.Impl;

import com.soreak.dao.TagDao;
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

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public List<TagVO> getTagNameAndCount() {
        return tagDao.getTagNameAndCount();
    }
}