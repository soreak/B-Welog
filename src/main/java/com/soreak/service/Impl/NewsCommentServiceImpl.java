package com.soreak.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.soreak.dao.NewsCommentDao;
import com.soreak.dao.NewsCommentVODao;
import com.soreak.entity.BlogComment;
import com.soreak.entity.NewsComment;
import com.soreak.entity.VO.CommentVO;
import com.soreak.service.NewsCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: welog
 * @author: soreak
 * @description:
 * @create: 2021-02-23 21:44
 **/
@Service
public class NewsCommentServiceImpl implements NewsCommentService {

    @Autowired
    private NewsCommentDao newsCommentDao;

    @Autowired
    private NewsCommentVODao newsCommentVODao;


    @Override
    public List<CommentVO> listCommentByNewsId(Long newsId) {
        List<CommentVO> commentVOS = newsCommentVODao.getNewsCommentByBlogIdAndParentCommentNull(newsId);
        return eachComment(commentVOS);
    }

    @Override
    public int saveOne(NewsComment comment) {
        return newsCommentDao.insert(comment);
    }


    @Override
    public void batchDelete(Long newsId,Long id) {
        ArrayList<Long> deleteList = new ArrayList<>();
        deleteList.add(id);
        findDeleteId(id,deleteList);
        newsCommentDao.deleteBatchIds(deleteList);
    }

    private void findDeleteId(Long id, ArrayList<Long> deleteList) {
        List<NewsComment> childCommentByParentId = newsCommentDao.selectList(new QueryWrapper<NewsComment>().eq("parent_comment_id",id));
        if(childCommentByParentId.size()>0){
            for (NewsComment reply1 : childCommentByParentId) {
                deleteList.add(reply1.getId());
                if(newsCommentVODao.getChildCommentByParentId(reply1.getId()).size()>0){
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
            List<CommentVO> replies = newsCommentVODao.getChildCommentByParentId(comment.getId());

            for (CommentVO reply : replies) {
                // 循环找出子代
                reply.setParentComment(newsCommentVODao.selectById(reply.getParentCommentId()));
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
        List<CommentVO> childCommentByParentId = newsCommentVODao.getChildCommentByParentId(comment.getId());
        if(childCommentByParentId.size()>0){
            for (CommentVO reply1 : childCommentByParentId) {
                reply1.setParentComment(newsCommentVODao.selectById(reply1.getParentCommentId()));
                tempReplies.add(reply1);
                if( newsCommentVODao.getChildCommentByParentId(reply1.getId()).size()>0){
                    recursively(reply1);
                }
            }
        }
    }



}
