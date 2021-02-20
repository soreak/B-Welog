package com.soreak.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.soreak.entity.Tag;
import com.soreak.entity.VO.TagVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
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
    @Select("select t.id,t.name,COUNT(*) as 'count' FROM sk_tag t,sk_blog b,sk_blog_tag bt where t.id = bt.tag_id and b.id = bt.blog_id GROUP BY t.`Name` ORDER BY COUNT(*) desc  LIMIT 8")
    List<TagVO> getTagNameAndCount();

    @Select("select t.id,t.name,COUNT(*) as 'count' FROM sk_tag t,sk_news n,sk_news_tag nt where t.id = nt.tag_id and n.id = nt.news_id GROUP BY t.`Name` ORDER BY COUNT(*) desc  LIMIT 8")
    List<TagVO> getTagNameAndCountByNews();

    @Select("select t.`id`,t.`Name` FROM sk_tag t,sk_blog_tag bt WHERE t.id = bt.tag_id and bt.blog_id=#{blogId}")
    List<Tag> getTagByBlogId(@Param("blogId") Long blogId);

    @Select("select t.`id`,t.`Name` FROM sk_tag t,sk_news_tag nt WHERE t.id = nt.tag_id and nt.news_id=#{newsId}")
    List<Tag> getTagByNewsId(@Param("newsId") Long newsId);

    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    @Insert("insert  into sk_tag(name) value (#{name})")
    Long saveBlog(Tag tag);



}
