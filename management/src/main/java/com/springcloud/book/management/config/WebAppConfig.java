package com.springcloud.book.management.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebAppConfig implements WebMvcConfigurer {
    //静态文件下载
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String os = System.getProperty("os.name");
        if (os.toLowerCase().startsWith("win")){
            registry.addResourceHandler(OverallConfig.TEXT_FULL + "/**").addResourceLocations("file:///" + OverallConfig.FILE_PATH + "\\");
        }
    }
}