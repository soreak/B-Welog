package com.soreak.service;

import com.soreak.entity.BlogComment;
import com.soreak.entity.VO.CommentVO;

import java.util.List;

/**
 * @program: welog
 * @author: soreak
 * @description:
 * @create: 2020-12-23 20:56
 **/
public interface BlogCommentService {
    List<CommentVO> listCommentByBlogId(Long blogId);

}
