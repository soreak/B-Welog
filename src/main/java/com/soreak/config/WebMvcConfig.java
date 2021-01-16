package com.soreak.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @program: welog
 * @author: soreak
 * @description:
 * @create: 2021-01-12 15:30
 **/
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Value("${absoluteImgPath}")
    String absoluteImgPath;

    @Value("${sonImgPath}")
    String sonImgPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(sonImgPath + "**").addResourceLocations("file:"+absoluteImgPath);
    }
}
