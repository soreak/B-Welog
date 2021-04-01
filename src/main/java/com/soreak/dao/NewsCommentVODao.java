package com.soreak.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.soreak.entity.VO.CommentVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: welog
 * @author: soreak
 * @description:
 * @create: 2021-02-23 21:21
 **/
@Repository
public interface NewsCommentVODao extends BaseMapper<CommentVO> {
    @Select("select nc.*,u.nickname as 'nickname' FROM sk_news_comment nc, sk_user u WHERE nc.user_id = u.id and nc.news_id=#{newsId} and nc.parent_comment_id is NULL ORDER BY create_time asc")
    List<CommentVO> getNewsCommentByBlogIdAndParentCommentNull(@Param("newsId") Long newsId);

    @Select("select nc.*,u.nickname as 'nickname' FROM sk_news_comment nc, sk_user u WHERE nc.user_id = u.id and parent_comment_id=#{id} ORDER BY create_time asc")
    List<CommentVO> getChildCommentByParentId(Long id);

    @Select("select nc.*,u.nickname as 'nickname' FROM sk_news_comment nc, sk_user u WHERE nc.user_id = u.id and nc.id = #{id}")
    CommentVO selectById(@Param("id") Long id);

}
