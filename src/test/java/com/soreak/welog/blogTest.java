package com.soreak.welog;

import com.soreak.entity.Blog;
import com.soreak.entity.VO.BlogVO;
import com.soreak.entity.VO.TagVO;
import com.soreak.service.BlogService;
import com.soreak.service.TagService;
import com.soreak.utils.phoneVerify.util.SMSUtil;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.Test;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @program: welog
 * @author: soreak
 * @description:
 * @create: 2020-12-21 15:27
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class blogTest {
    @Autowired
    private BlogService blogService;

    @Autowired
    private TagService tagService;

    @Test
    public void test2() {
        // selectList 是内置的方法，logYjxxMapper中并不需要自己定义 selectList 这么一个方法
        // selectList括号里的参数是条件构造器，可参看官方文档
        List<BlogVO> yjxxLoglist = blogService.getBlogList();
        for (BlogVO logYjxx : yjxxLoglist) {
            System.out.println(logYjxx);
        }
    }

    @Test
    public void test3() {
        // selectList 是内置的方法，logYjxxMapper中并不需要自己定义 selectList 这么一个方法
        // selectList括号里的参数是条件构造器，可参看官方文档
        List<TagVO> yjxxLoglist = tagService.getTagNameAndCount();
        for (TagVO logYjxx : yjxxLoglist) {
            System.out.println(logYjxx);
        }
    }

    @Test
    public void test4() {
        SMSUtil.sendSMS("17689400639");
    }
}
