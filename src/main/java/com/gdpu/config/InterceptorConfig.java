package com.gdpu.config;

import com.gdpu.interceptors.JWTInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author:王浩东
 * @createTime: 2021/10/9
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JWTInterceptor())
                //需要token验证的请求
                .addPathPatterns("/**")
                //放行用户相关的拦截
                .excludePathPatterns("/user/login")
                .excludePathPatterns("/user/addUser")
                .excludePathPatterns("/user/getUserInfo")
                //放行首页相关
                .excludePathPatterns("/goods/**")
                .excludePathPatterns("/category/getCategory")
                //放行评论相关
                .excludePathPatterns("/comment/**")
                //放行广告
                .excludePathPatterns("/ad/**");
    }
}
