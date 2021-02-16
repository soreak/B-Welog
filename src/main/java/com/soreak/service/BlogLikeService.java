package com.soreak.service;

import com.soreak.entity.BlogLike;

/**
 * @program: welog
 * @author: soreak
 * @description:
 * @create: 2021-01-28 15:43
 **/
public interface BlogLikeService {

    BlogLike SelectBlogLike(Long BlogId,Long UserId);

    int selectBlogLikeCountByBlogId(Long BlogId);

    int CreateBlogLike(Long BlogId,Long UserId);

    int DeleteBlogLike(Long BlogId,Long UserId);

}
