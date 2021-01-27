package com.soreak.controller.admin;

import com.soreak.entity.Blog;
import com.soreak.entity.BlogTag;
import com.soreak.entity.Tag;
import com.soreak.entity.UserEntity;
import com.soreak.entity.VO.BlogVO;
import com.soreak.service.BlogService;
import com.soreak.service.BlogTagService;
import com.soreak.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: welog
 * @author: soreak
 * @description:
 * @create: 2021-01-27 15:06
 **/
@Controller
@RequestMapping("/admin")
public class blogsController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TagService tagService;

    @Autowired
    private BlogTagService blogTagService;

    @RequestMapping(value = "/blogs",method = RequestMethod.GET)
    @PreAuthorize("hasRole('1')")
    public String blogs(Model model){
        model.addAttribute("tags",tagService.getTagNameAndCount());
        model.addAttribute("blogs",blogService.getBlogList());
        return "admin/blogs";
    }

    @RequestMapping(value = "/blogs/{id}/input",method = RequestMethod.GET)
    @PreAuthorize("hasRole('1')")
    public String one(@PathVariable Long id, Model model){
        model.addAttribute("tags",tagService.getTagNameAndCount());
        BlogVO blog= blogService.getOneBlog(id);
        blog.init();
        model.addAttribute("blog",blog);
        return "admin/blogs-input";
    }

    @GetMapping("/blogs/{id}/delete")
    @PreAuthorize("hasRole('1')")
    public String delete(@PathVariable Long id, RedirectAttributes attributes){
        blogService.deleteBlogById(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/blogs";
    }


    @PostMapping("/blogs")
    public String post(BlogVO blog, RedirectAttributes attributes,Model model){

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
        blog1.setId(blog.getId());
        Long id = blogService.updateBlog(blog1);
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
        return "redirect:/admin/blogs";
    }


    @PostMapping("/blogs/search")
    @PreAuthorize("hasRole('1')")
    public String search( @RequestParam(value = "title",defaultValue = "soreak") String title,
                          @RequestParam(value = "tagId",defaultValue = "-1") String tagId,
                          @RequestParam(value = "recommend",required = false) String recommend,
                          Model model){

        int recommend1 = -1;
        switch (recommend) {
            case "true":
                recommend1 = 1;
                break;
            case "false":
                recommend1 = 0;
                break;
        }
        List<Blog> blogs = blogService.searchBlog(title,tagId,recommend1);
        model.addAttribute("blogs",blogs);
        return "admin/blogs :: blogList";
    }

}
