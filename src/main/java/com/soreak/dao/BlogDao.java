package com.soreak.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.soreak.entity.Blog;
import com.soreak.entity.VO.BlogVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
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

    @Select("SELECT b.*,u.nickname as 'userName',u.avatar as 'userAvatar' FROM sk_blog b,sk_user u WHERE b.user_id = u.id ORDER BY b.update_time DESC")
    List<BlogVO> getBlogList();

    @Select("SELECT b.*,u.nickname as 'userName',u.avatar as 'userAvatar' FROM sk_blog b,sk_user u WHERE b.user_id = u.id and b.id=#{id}")
    BlogVO getOneBlogById(@Param("id") Long id);
}
