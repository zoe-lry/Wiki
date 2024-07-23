package com.zoe.wiki.config;

import com.zoe.wiki.interceptor.LoginInterceptor;
import javax.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SpringMvcConfig implements WebMvcConfigurer {

  @Resource
  LoginInterceptor loginInterceptor;

  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(loginInterceptor)
        .addPathPatterns("/**")
        .excludePathPatterns(
            "/test/**",
            "/redis/**",
            "/user/login",
            "/category/all",
            "/ebook/list",
            "/doc/all/**",
            "/doc/find-content/**"
        );
  }
}
