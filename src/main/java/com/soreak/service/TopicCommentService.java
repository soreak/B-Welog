package com.soreak.service;

import com.soreak.entity.TopicComment;
import com.soreak.entity.VO.CommentVO;

import java.util.List;

/**
 * @program: welog
 * @author: soreak
 * @description:
 * @create: 2021-02-28 22:24
 **/
public interface TopicCommentService {

    List<CommentVO> listCommentByTopicId(Long topicId);

    int saveOne(TopicComment comment);

    int selectCommentCount(Long topicId);
}
