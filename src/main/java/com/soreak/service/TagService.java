package com.soreak.service;

import com.soreak.entity.VO.TagVO;

import java.util.List;
import java.util.Map;

/**
 * @program: welog
 * @author: soreak
 * @description:
 * @create: 2020-12-20 14:45
 **/
public interface TagService {
    List<TagVO> getTagNameAndCount();
}
