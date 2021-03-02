package com.soreak.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.soreak.dao.TopicCommentDao;
import com.soreak.dao.TopicCommentVODao;
import com.soreak.entity.TopicComment;
import com.soreak.entity.VO.CommentVO;
import com.soreak.service.TopicCommentService;
import com.soreak.utils.TimeFromNow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @program: welog
 * @author: soreak
 * @description:
 * @create: 2021-02-28 22:24
 **/
@Service
public class TopicCommentServiceImpl implements TopicCommentService {

    @Autowired
    private TopicCommentDao topicCommentDao;

    @Autowired
    private TopicCommentVODao topicCommentVODao;

    TimeFromNow timeFromNow = new TimeFromNow();


    @Override
    public List<CommentVO> listCommentByTopicId(Long topicId) {
        List<CommentVO> comments = topicCommentVODao.getTopicCommentByTopicIdAndParentCommentNull(topicId);
        return eachComment(comments);
    }

    @Override
    public int saveOne(TopicComment comment) {
        return topicCommentDao.insert(comment);
    }

    @Override
    public int selectCommentCount(Long topicId) {
        QueryWrapper<TopicComment> wrapper = new QueryWrapper<>();
        wrapper.eq("id",topicId);
        return topicCommentDao.selectCount(wrapper);
    }

    private List<CommentVO> eachComment(List<CommentVO> comments) {

        // 合并各层子代到第一级子代集合中
        combineChildren(comments);
        return comments;

    }
    /**
     * 循环顶层结点
     * @param comments
     * @return
     */
    private void combineChildren(List<CommentVO> comments) {
        for (CommentVO comment : comments) {
            List<CommentVO> replies = topicCommentVODao.getChildCommentByParentId(comment.getId());
            comment.setTimeFromNow(timeFromNow.CalculateTime(comment.getCreateTime()));
            for (CommentVO reply : replies) {
                // 循环找出子代
                System.out.println(reply.getCreateTime());
                reply.setTimeFromNow(timeFromNow.CalculateTime(reply.getCreateTime()));
                reply.setParentComment(topicCommentVODao.selectById(reply.getParentCommentId()));
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
        List<CommentVO> childCommentByParentId = topicCommentVODao.getChildCommentByParentId(comment.getId());
        if(childCommentByParentId.size()>0){
            for (CommentVO reply1 : childCommentByParentId) {
                reply1.setTimeFromNow(timeFromNow.CalculateTime(reply1.getCreateTime()));
                reply1.setParentComment(topicCommentVODao.selectById(reply1.getParentCommentId()));
                tempReplies.add(reply1);
                if( topicCommentVODao.getChildCommentByParentId(reply1.getId()).size()>0){
                    recursively(reply1);
                }
            }
        }
    }




}
