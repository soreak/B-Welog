package com.soreak.service;

import com.soreak.entity.BlogLike;
import com.soreak.entity.NewsLike;

/**
 * @program: welog
 * @author: soreak
 * @description:
 * @create: 2021-02-22 21:52
 **/
public interface NewsLikeService {
    NewsLike SelectNewsLike(Long NewsId, Long UserId);

    int selectNewsLikeCountByNewsId(Long NewsId);

    int CreateNewsLike(Long NewsId,Long UserId);

    int DeleteNewsLike(Long NewsId,Long UserId);


}
