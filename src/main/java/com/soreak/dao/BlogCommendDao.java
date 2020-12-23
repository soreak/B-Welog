package com.soreak.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.soreak.entity.BlogComment;
import com.soreak.entity.VO.CommentVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: welog
 * @author: soreak
 * @description:
 * @create: 2020-12-17 16:47
 **/
@Repository
public interface BlogCommendDao extends BaseMapper<CommentVO> {

    @Select("select bc.*,u.nickname as 'nickname' FROM sk_blog_comment bc, sk_user u WHERE bc.user_id = u.id and bc.blog_id=#{blogId} and bc.parent_comment_id is NULL ORDER BY create_time asc")
    List<CommentVO> getBlogCommentByBlogIdAndParentCommentNull(@Param("blogId") Long blogId);

    @Select("select bc.*,u.nickname as 'nickname' FROM sk_blog_comment bc, sk_user u WHERE bc.user_id = u.id and parent_comment_id=#{id} ORDER BY create_time asc")
    List<CommentVO> getChildCommentByParentId(Long id);

    @Select("select bc.*,u.nickname as 'nickname' FROM sk_blog_comment bc, sk_user u WHERE bc.user_id = u.id and bc.id = #{id}")
    CommentVO selectById(@Param("id") Long id);
}
