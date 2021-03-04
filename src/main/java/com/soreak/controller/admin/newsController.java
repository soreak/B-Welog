package com.soreak.controller.admin;

import com.soreak.entity.*;
import com.soreak.entity.VO.BlogVO;
import com.soreak.entity.VO.NewsVO;
import com.soreak.service.NewsService;
import com.soreak.service.NewsTagService;
import com.soreak.service.TagService;
import com.soreak.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
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
 * @create: 2021-02-20 21:09
 **/
@Controller
@RequestMapping("/admin")
public class newsController {

    @Autowired
    private UserService userService;

    @Autowired
    private NewsService newsService;

    @Autowired
    private TagService tagService;

    @Autowired
    private NewsTagService newsTagService;

    @RequestMapping(value = "/news",method = RequestMethod.GET)
    @PreAuthorize("hasRole('1')")
    public String news(Model model){
        model.addAttribute("tags",tagService.getTagNameAndCountByNews());
        model.addAttribute("news",newsService.getNewsList());
        return "admin/news";
    }

    @RequestMapping(value = "/news/{id}/input",method = RequestMethod.GET)
    @PreAuthorize("hasRole('1')")
    public String one(@PathVariable Long id, Model model){
        model.addAttribute("tags",tagService.getTagNameAndCountByNews());
        NewsVO news = newsService.getOneNews(id);
        news.init();
        model.addAttribute("news",news);
        return "admin/news-input";
    }

    @GetMapping("/news/{id}/delete")
    @PreAuthorize("hasRole('1')")
    public String delete(@PathVariable Long id, RedirectAttributes attributes){
        newsService.deleteNewsById(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/news";
    }

    @GetMapping("/news/input")
    @PreAuthorize("hasRole('1')")
    public String input(Model model){
        model.addAttribute("tags",tagService.getTagNameAndCountByNews());

        model.addAttribute("news",new NewsVO());
        return "/admin/news-input";
    }


    @PostMapping("/news")
    @PreAuthorize("hasRole('1')")
    public String post(NewsVO newsVO, RedirectAttributes attributes,Model model){

        String phone = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println(SecurityContextHolder.getContext().getAuthentication());
        UserEntity userEntity  = new UserEntity();
        userEntity = userService.findByPhone(phone);
        model.addAttribute("master",userEntity);


        String tagIds = newsVO.getTagIds();

        String[] split = tagIds.split(",");
        List<Long> tagIdList = new ArrayList<>();
        Tag tag = new Tag();
        for (String c : split){

            if (!c.matches("^[0-9]*$")){
                tag.setName(c);
                Long aLong = tagService.saveTag(tag);
                System.out.println("tagid============"+aLong);
                System.out.println(tag.getId());
                tagIdList.add(tag.getId());
                tag = new Tag();
            }else {
                if (newsVO.getId() == null){
                    tagIdList.add(Long.valueOf(c));
                }

            }
        }
        News news = new News();
        news.setTitle(newsVO.getTitle());
        news.setContent(newsVO.getContent());
        news.setCommentabled(newsVO.isCommentabled());
        Long id;
        if (newsVO.getId() == null){
            news.setUserId(userEntity.getId());
            id = newsService.saveNews(news);
        }else {
            news.setId(newsVO.getId());
            newsTagService.deleteByNewsId(newsVO.getId());
            id = newsService.updateNews(news);
        }
        if (id == 0){
            attributes.addFlashAttribute("message","操作失败");
        }else {
            attributes.addFlashAttribute("message","操作成功");
        }

        NewsTag newsTag = new NewsTag();
        for (Long id1: tagIdList){
            newsTag.setNewsId(news.getId());
            newsTag.setTagId(id1);
            try{
                newsTagService.save(newsTag);
            }
           catch (Exception e){
                e.printStackTrace();
           }
            newsTag = new NewsTag();
        }
        return "redirect:/admin/news";
    }

    @PostMapping("/news/search")
    @PreAuthorize("hasRole('1')")
    public String search( @RequestParam(value = "title",defaultValue = "soreak") String title,
                          @RequestParam(value = "tagId",defaultValue = "-1") String tagId,
                          Model model){

        List<News> news = newsService.searchNews(title,tagId);
        model.addAttribute("news",news);
        return "admin/news :: newsList";
    }

}
