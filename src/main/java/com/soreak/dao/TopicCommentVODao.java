package com.soreak.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.soreak.entity.VO.CommentVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: welog
 * @author: soreak
 * @description:
 * @create: 2021-02-28 22:25
 **/
@Repository
public interface TopicCommentVODao extends BaseMapper<CommentVO> {

    @Select("select tc.*,u.nickname as 'nickname',u.avatar as 'userAvatar' FROM sk_topic_comment tc, sk_user u WHERE tc.user_id = u.id and tc.topic_id=#{topicId} and tc.parent_comment_id is NULL ORDER BY create_time asc")
    List<CommentVO> getTopicCommentByTopicIdAndParentCommentNull(@Param("topicId") Long topicId);

    @Select("select tc.*,u.nickname as 'nickname',u.avatar as 'userAvatar' FROM sk_topic_comment tc, sk_user u WHERE tc.user_id = u.id and parent_comment_id=#{id} ORDER BY create_time asc")
    List<CommentVO> getChildCommentByParentId(Long id);

    @Select("select tc.*,u.nickname as 'nickname',u.avatar as 'userAvatar' FROM sk_topic_comment tc, sk_user u WHERE tc.user_id = u.id and tc.id = #{id}")
    CommentVO selectById(@Param("id") Long id);
}
