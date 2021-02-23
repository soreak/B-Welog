package com.soreak.service;

import com.soreak.entity.BlogComment;
import com.soreak.entity.NewsComment;
import com.soreak.entity.VO.CommentVO;

import java.util.List;

/**
 * @program: welog
 * @author: soreak
 * @description:
 * @create: 2021-02-23 21:44
 **/
public interface NewsCommentService {
    List<CommentVO> listCommentByNewsId(Long newsId);

    int saveOne(NewsComment comment);
}
