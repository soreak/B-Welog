package com.soreak.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.soreak.entity.Blog;
import com.soreak.entity.VO.BlogVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: welog
 * @author: soreak
 * @description:
 * @create: 2020-12-17 16:45
 **/
@Repository
public interface BlogDao extends BaseMapper<Blog> {
    List<BlogVO> getBlogList();
}
