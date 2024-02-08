package com.vincentcodes.jishoapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.Duration;

// https://docs.spring.io/spring-framework/reference/web/webmvc/mvc-config/static-resources.html
@Configuration
@EnableWebMvc
public class StaticFilesConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/public/**")
                .addResourceLocations("file:./public/", "classpath:/static/")
                .setCacheControl(CacheControl.maxAge(Duration.ofDays(7)));
    }
}
