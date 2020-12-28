package com.soreak.service.Impl;

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
                recursively(reply);
            }
            comment.setReplyComments(tempReplies);
            // 清除临时存放区域

            tempReplies = new ArrayList<>();
        }
    }

    private List<CommentVO> tempReplies = new ArrayList<>();
    /**
     * 迭代找出子代集合
     */
    private void recursively(CommentVO comment) {
        tempReplies.add(comment);// 顶节点添加到临时存放集合
        List<CommentVO> childCommentByParentId = blogCommentVODao.getChildCommentByParentId(comment.getId());
        if(childCommentByParentId.size()>0){
            for (CommentVO reply : childCommentByParentId) {
                reply.setParentComment(blogCommentVODao.selectById(reply.getParentCommentId()));
                tempReplies.add(reply);
                if( blogCommentVODao.getChildCommentByParentId(reply.getId()).size()>0){
                    recursively(reply);
                }
            }
        }
    }




}
