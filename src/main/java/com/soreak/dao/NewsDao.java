package com.soreak.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.soreak.entity.Blog;
import com.soreak.entity.News;
import com.soreak.entity.VO.BlogVO;
import com.soreak.entity.VO.NewsVO;
import com.soreak.entity.VO.TopicVO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: welog
 * @author: soreak
 * @description:
 * @create: 2021-02-20 21:06
 **/
@Repository
public interface NewsDao extends BaseMapper<News> {

    @Select("SELECT n.*,u.nickname as 'userName',u.avatar as 'userAvatar' FROM sk_news n,sk_user u WHERE n.user_id = u.id ORDER BY n.update_time DESC")
    List<NewsVO> getNewsList();

    @Select("select * from sk_news n where to_days(create_time) = to_days(now()) ORDER BY n.views DESC limit 5")
    List<News> getOneDayHotNews();

    @Select("select * from sk_news n WHERE YEARWEEK(date_format(create_time,'%Y-%m-%d')) = YEARWEEK(now(),1) ORDER BY n.views DESC limit 5")
    List<News> getWeekHotNews();

    @Select("SELECT n.*,u.nickname as 'userName',u.avatar as 'userAvatar'  FROM sk_news n,sk_news_tag nt,sk_user u WHERE n.user_id = u.id and n.id = nt.news_id and nt.tag_id=#{tagId}  ORDER BY n.update_time DESC")
    List<NewsVO> getNewsListByTagId(@Param("tagId") Long tagId);


    @Select("SELECT n.*,u.nickname as 'userName',u.avatar as 'userAvatar' FROM sk_news n,sk_user u WHERE n.user_id = u.id and n.id=#{id}")
    NewsVO getOneNewsById(@Param("id") Long id);


    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    @Insert("insert into sk_news(title,content,create_time,update_time,commentabled,views,user_id) value(#{title},#{content},#{createTime},#{updateTime},#{commentabled},#{views},#{userId})")
    Long saveNews(News news);

    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    @Insert("update sk_news set title=#{title},content=#{content},update_time=#{updateTime},commentabled=#{commentabled} where sk_news.id = #{id}")
    Long updateNews(News news);

    @Update("update sk_news n set n.views = n.views+1 where n.id=#{id}")
    int updateViews(@Param("id")Long id);

    @Select("SELECT n.* FROM sk_news n,sk_news_tag nt WHERE n.id = nt.news_id and n.title like concat('%',#{title},'%') and nt.tag_id=#{tagId} ")
    List<News> searchNewsListByTT(@Param("title")String title,@Param("tagId")String tagId);

    @Select("SELECT n.* FROM sk_news n,sk_news_tag nt WHERE n.id = nt.news_id and nt.tag_id=#{tagId}")
    List<News> searchNewsListByT(@Param("tagId")String tagId);

    @Select("SELECT n.*,u.nickname as 'userName',u.avatar as 'userAvatar' FROM sk_news n,sk_user u WHERE n.user_id = u.id and n.title like concat('%',#{query},'%') or n.content like concat('%',#{query},'%')  GROUP BY n.id ORDER BY n.update_time")
    List<NewsVO> searchNewsByQuery(@Param("query")String query);


}
