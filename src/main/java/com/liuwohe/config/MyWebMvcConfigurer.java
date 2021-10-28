package com.liuwohe.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyWebMvcConfigurer implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //配置静态资源访问相对路径
        registry.addResourceHandler("/images/**").addResourceLocations("file:E:\\JavaProject\\defect-management-system\\src\\main\\resources\\static\\assets\\images/");
    }
}
