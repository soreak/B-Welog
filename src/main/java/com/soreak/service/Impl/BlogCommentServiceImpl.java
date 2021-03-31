package com.soreak.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.soreak.dao.BlogCommentDao;
import com.soreak.dao.BlogCommentVODao;
import com.soreak.entity.BlogComment;
import com.soreak.entity.VO.CommentVO;
import com.soreak.service.BlogCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: welog
 * @author: soreak
 * @description:
 * @create: 2020-12-23 20:58
 **/
@Repository
public class BlogCommentServiceImpl implements BlogCommentService {

    @Autowired
    private BlogCommentVODao blogCommentVODao;


    @Autowired
    private BlogCommentDao blogCommentDao;


    @Override
    public List<CommentVO> listCommentByBlogId(Long blogId) {

        List<CommentVO> comments = blogCommentVODao.getBlogCommentByBlogIdAndParentCommentNull(blogId);
        return eachComment(comments);
    }

    @Override
    public int saveOne(BlogComment comment) {
        return blogCommentDao.insert(comment);
    }

    @Override
    public void batchDelete(Long blogId,Long id) {
        ArrayList<Long> deleteList = new ArrayList<>();
        List<BlogComment> commentVOS = blogCommentDao.selectList(new QueryWrapper<BlogComment>().eq("blog_id",blogId));



        deleteList.add(id);

        findDeleteId(id,deleteList);

        System.out.println(deleteList);


    }

    private void findDeleteId(Long id, ArrayList<Long> deleteList) {
        List<BlogComment> childCommentByParentId = blogCommentDao.selectList(new QueryWrapper<BlogComment>().eq("parent_comment_id",id));
        if(childCommentByParentId.size()>0){
            for (BlogComment reply1 : childCommentByParentId) {
                deleteList.add(reply1.getId());
                if(blogCommentVODao.getChildCommentByParentId(reply1.getId()).size()>0){
                    findDeleteId(reply1.getId(),deleteList);
                }
            }
        }

    }

    /**
     * 循环顶层结点
     * @param comments
     * @return
     */
    private List<CommentVO> eachComment(List<CommentVO> comments) {

        // 合并各层子代到第一级子代集合中
        combineChildren(comments);
        return comments;

    }

    private void combineChildren(List<CommentVO> comments) {


        for (CommentVO comment : comments) {
            List<CommentVO> replies = blogCommentVODao.getChildCommentByParentId(comment.getId());

            for (CommentVO reply : replies) {
                // 循环找出子代
                reply.setParentComment(blogCommentVODao.selectById(reply.getParentCommentId()));
                headComment(reply);

            }
            comment.setReplyComments(tempReplies);
            // 清除临时存放区域

            tempReplies = new ArrayList<>();
        }
    }

    private List<CommentVO> tempReplies = new ArrayList<>();

    /**
     * 顶节点添加到临时存放集合,不加入循环
     * @param comment
     */
    private void headComment(CommentVO comment){

        tempReplies.add(comment);// 顶节点添加到临时存放集合
        recursively(comment);
    }


    /**
     * 迭代找出子代集合
     */
    private void recursively(CommentVO comment) {
        List<CommentVO> childCommentByParentId = blogCommentVODao.getChildCommentByParentId(comment.getId());
        if(childCommentByParentId.size()>0){
            for (CommentVO reply1 : childCommentByParentId) {
                reply1.setParentComment(blogCommentVODao.selectById(reply1.getParentCommentId()));
                tempReplies.add(reply1);
                if( blogCommentVODao.getChildCommentByParentId(reply1.getId()).size()>0){
                    recursively(reply1);
                }
            }
        }
    }




}
