package com.soreak.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.soreak.entity.Topic;
import com.soreak.entity.VO.TopicVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
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

    @Select("SELECT t.*,u.nickname as 'userName',u.avatar as 'userAvatar' FROM sk_topic t,sk_user u WHERE t.user_id = u.id and t.user_id=#{id} ORDER BY t.create_time DESC")
    List<TopicVO> getMyTopicListByUserId(@Param("id") Long userId);



    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    @Insert("insert into sk_topic(title,content,create_time,update_time,published,views,user_id) value(#{title},#{content},#{createTime},#{updateTime},#{published},#{views},#{userId})")
    Long saveTopic(Topic topic);

}
