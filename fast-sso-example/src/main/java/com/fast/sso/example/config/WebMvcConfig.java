package com.fast.sso.example.config;

import com.fast.sso.client.interceptor.UserInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author by:ly
 * @ClassName: WebMvcConfig
 * @Description: TODO
 * @Date: 2021/7/14 18:57
 **/
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    UserInterceptor userInterceptor;

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userInterceptor)
                .addPathPatterns("/**");
    }

    @Bean
    public UserInterceptor userInterceptor(){
        return new UserInterceptor();
    }
}
