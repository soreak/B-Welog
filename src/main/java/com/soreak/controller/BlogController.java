package com.soreak.controller;

import com.soreak.entity.*;
import com.soreak.entity.VO.BlogVO;
import com.soreak.entity.VO.CommentVO;
import com.soreak.entity.VO.TagVO;
import com.soreak.service.*;
import com.soreak.utils.HTMLUtils;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: welog
 * @author: soreak
 * @description:
 * @create: 2020-12-23 19:43
 **/
@Controller
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private UserService userService;

    @Autowired
    private BlogTagService blogTagService;

    @Autowired
    private TagService tagService;

    @Autowired
    private BlogCommentService blogCommentService;

    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id, Model model){
        setUser(model);

        BlogVO blog =blogService.getBlogById(id);
        model.addAttribute("blog",blog);
        return "blog";
    }

    @PostMapping("/blog")
    public String post(BlogVO blog, RedirectAttributes attributes,Model model){
        UserEntity userEntity = setUser(model);

        if ("".equals(blog.getFlag())){
            blog.setFlag("原创");
        }

        String tagIds = blog.getTagIds();

        String[] split = tagIds.split(",");
        List<Long> tagIdList = new ArrayList<>();
        Tag tag = new Tag();
        for (String c : split){

            if (!c.matches("^[0-9]*$")){
                tag.setName(c);
                Long aLong = tagService.saveTag(tag);
                System.out.println("tagid+++++"+aLong);
                System.out.println(tag.getId());
                tagIdList.add(tag.getId());
                tag = new Tag();
            }else {
                if (blog.getId() == null){
                    tagIdList.add(Long.valueOf(c));
                }

            }
        }
        /*创建一个新的blog来接受blogVO里的数据*/
        Blog blog1 = new Blog();

        blog1.setTitle(blog.getTitle());
        blog1.setContent(blog.getContent());
        blog1.setFlag(blog.getFlag());
        blog1.setCommentabled(blog.isCommentabled());
        blog1.setRecommend(blog.isRecommend());
        blog1.setPublished(blog.isPublished());

        Long id;
        if (blog.getId() == null){
            blog1.setUserId(userEntity.getId());
             id = blogService.saveBlog(blog1);
        }else {
            blog1.setId(blog.getId());
            id = blogService.updateBlog(blog1);
        }
        if (id == 0){
            attributes.addFlashAttribute("message","操作失败");
        }else {
            attributes.addFlashAttribute("message","操作成功");
        }

        BlogTag blogTag = new BlogTag();
        for (Long id1: tagIdList){
            blogTag.setBlogId(blog1.getId());
            blogTag.setTagId(id1);
            blogTagService.save(blogTag);
            blogTag = new BlogTag();
        }
        return "redirect:/MY";
    }

    private void setTag(Model model){
        model.addAttribute("tags",tagService.getTagNameAndCount());

    }

    private UserEntity setUser(Model model){
        String phone = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println(SecurityContextHolder.getContext().getAuthentication());
        UserEntity byPhone = new UserEntity();
        if (phone!=null){
           byPhone = userService.findByPhone(phone);
            model.addAttribute("user",byPhone);
        }
        return byPhone;
    }

    @GetMapping("/blog/input")
    @PreAuthorize("isAuthenticated()")
    public String input(Model model){
        setUser(model);
        setTag(model);

        model.addAttribute("blog",new BlogVO());
        return "/blog-input";
    }

    @GetMapping("/blog/{id}/input")
    @PreAuthorize("isAuthenticated()")
    public String editInput(@PathVariable Long id,Model model){
        setUser(model);
        setTag(model);
        BlogVO blog= blogService.getOneBlog(id);

        blog.init();

        model.addAttribute("blog",blog);
        return "/blog-input";
    }

    @GetMapping("/blog/{id}/delete")
    @PreAuthorize("isAuthenticated()")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        blogService.deleteBlogById(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/MY";
    }


    @GetMapping("/MY")
    @PreAuthorize("isAuthenticated()")
    public String my(Model model){
        UserEntity userEntity = setUser(model);

        List<BlogVO> blogs= blogService.getMyBlogListByUserId(userEntity.getId());
        model.addAttribute("blogs",blogs);
        return "/my";
    }


}
