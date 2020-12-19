package com.soreak.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import

/**
 * @program: welog
 * @author: soreak
 * @description:
 * @create: 2020-12-19 21:21
 **/
@Controller
public class IndexController {

    @GetMapping("/")
    public String index(@PageableDefault(size = 3,sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable,
                        Model model){
        model.addAttribute("page",blogService.listBlog(pageable));
        model.addAttribute("types",typeService.listTypeTop(6));
        model.addAttribute("tags",tagService.listTagTop(10));
        model.addAttribute("recommendBlogs",blogService.listRecommendBlogTop(8));

        return "index";
    }

}
