package com.soreak.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.soreak.entity.Tag;
import com.soreak.entity.VO.TagVO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @program: welog
 * @author: soreak
 * @description:
 * @create: 2020-12-20 14:45
 **/
@Repository
public interface TagDao extends BaseMapper<Tag> {
    List<TagVO> getTagNameAndCount();
}
