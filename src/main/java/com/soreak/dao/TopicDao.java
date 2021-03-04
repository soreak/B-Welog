package com.soreak.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.soreak.entity.Blog;
import com.soreak.entity.Topic;
import com.soreak.entity.VO.BlogVO;
import com.soreak.entity.VO.TopicVO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: welog
 * @author: soreak
 * @description:
 * @create: 2021-02-28 22:05
 **/
@Repository
public interface TopicDao extends BaseMapper<Topic> {

    @Select("SELECT t.*,u.nickname as 'userName',u.avatar as 'userAvatar' FROM sk_topic t,sk_user u WHERE t.user_id = u.id and t.published = 1 ORDER BY t.update_time DESC")
    List<TopicVO> getTopicList();

    @Select("SELECT t.*,u.nickname as 'userName',u.avatar as 'userAvatar' FROM sk_topic t,sk_user u WHERE t.user_id = u.id ORDER BY t.update_time DESC")
    List<TopicVO> getAllTopicList();

    @Select("SELECT t.*,u.nickname as 'userName',u.avatar as 'userAvatar' FROM sk_topic t,sk_user u WHERE t.user_id = u.id and t.user_id=#{id} ORDER BY t.create_time DESC")
    List<TopicVO> getMyTopicListByUserId(@Param("id") Long userId);

    @Select("SELECT t.*,u.nickname as 'userName',u.avatar as 'userAvatar' FROM sk_topic t,sk_user u WHERE t.user_id = u.id and t.id=#{id}")
    TopicVO getOneTopicById(@Param("id") Long id);

    @Update("update sk_topic t set t.views = t.views+1 where t.id=#{id}")
    int updateViews(@Param("id")Long id);


    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    @Insert("insert into sk_topic(title,content,create_time,update_time,published,views,user_id) value(#{title},#{content},#{createTime},#{updateTime},#{published},#{views},#{userId})")
    Long saveTopic(Topic topic);


    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    @Insert("update sk_topic set title=#{title},content=#{content},update_time=#{updateTime},published=#{published} where sk_topic.id = #{id}")
    Long updateTopic(Topic topic);


    @Select("SELECT t.* FROM sk_topic t,sk_topic_tag tt WHERE t.id = tt.topic_id and t.title like concat('%',#{title},'%') and tt.tag_id=#{tagId} and t.published = #{published}")
    List<Topic> searchTopicListByTTP(@Param("title")String title,@Param("tagId")String tagId,@Param("published")int published);

    @Select("SELECT t.* FROM sk_topic t,sk_topic_tag tt WHERE t.id = tt.topic_id and t.title like concat('%',#{title},'%') and tt.tag_id=#{tagId} ")
    List<Topic> searchTopicListByTT(@Param("title")String title,@Param("tagId")String tagId);

    @Select("SELECT t.* FROM sk_topic t,sk_topic_tag tt WHERE t.id = tt.topic_id and tt.tag_id=#{tagId} and t.published = #{published}")
    List<Topic> searchTopicListByTP(@Param("tagId")String tagId,@Param("published")int published);

    @Select("SELECT t.* FROM sk_topic t,sk_topic_tag tt WHERE t.id = tt.topic_id and tt.tag_id=#{tagId}")
    List<Topic> searchTopicListByT(@Param("tagId")String tagId);


}
