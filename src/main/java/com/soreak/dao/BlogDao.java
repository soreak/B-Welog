package com.soreak.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.soreak.entity.Blog;
import com.soreak.entity.VO.BlogVO;
import org.apache.ibatis.annotations.*;
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

    @Select("SELECT b.*,u.nickname as 'userName',u.avatar as 'userAvatar' FROM sk_blog b,sk_user u WHERE b.user_id = u.id and b.user_id=#{id} ORDER BY b.create_time DESC")
    List<BlogVO> getMyBlogListByUserId(@Param("id") Long userId);


    @Select("SELECT b.*,u.nickname as 'userName',u.avatar as 'userAvatar' FROM sk_blog b,sk_user u WHERE b.user_id = u.id and b.id=#{id}")
    BlogVO getOneBlogById(@Param("id") Long id);

    @Update("update sk_blog b set b.views = b.views+1 where b.id=#{id}")
    int updateViews(@Param("id")Long id);


    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    @Insert("insert into sk_blog(title,content,create_time,update_time,flag,commentabled,recommend,published,user_id) value(#{title},#{content},#{createTime},#{updateTime},#{flag},#{commentabled},#{recommend},#{published},#{userId})")
    Long saveBlog(Blog blog);


    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    @Insert("update sk_blog set title=#{title},content=#{content},update_time=#{updateTime},flag=#{flag},commentabled=#{commentabled},recommend=#{recommend},published=#{published} where sk_blog.id = #{id}")
    Long updateBlog(Blog blog);


    @Select("SELECT b.* FROM sk_blog b,sk_blog_tag bt WHERE b.id = bt.blog_id and b.title like concat('%',#{title},'%') and bt.tag_id=#{tagId} and b.recommend = #{recommend}")
    List<Blog> searchBlogListByTTR(@Param("title")String title,@Param("tagId")String tagId,@Param("recommend")int recommend);

    @Select("SELECT b.* FROM sk_blog b,sk_blog_tag bt WHERE b.id = bt.blog_id and b.title like concat('%',#{title},'%') and bt.tag_id=#{tagId} ")
    List<Blog> searchBlogListByTT(@Param("title")String title,@Param("tagId")String tagId);

    @Select("SELECT b.* FROM sk_blog b,sk_blog_tag bt WHERE b.id = bt.blog_id and bt.tag_id=#{tagId} and b.recommend = #{recommend}")
    List<Blog> searchBlogListByTR(@Param("tagId")String tagId,@Param("recommend")int recommend);

    @Select("SELECT b.* FROM sk_blog b,sk_blog_tag bt WHERE b.id = bt.blog_id and bt.tag_id=#{tagId}")
    List<Blog> searchBlogListByT(@Param("tagId")String tagId);




}
