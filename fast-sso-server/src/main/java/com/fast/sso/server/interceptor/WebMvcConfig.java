package com.fast.sso.server.interceptor;

import com.fast.sso.client.interceptor.UserInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author by:ly
 * @ClassName: UserWebMvc
 * @Description: UserWebMvc
 * @Date: 2021/7/14 16:18
 **/
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private UserInterceptor userInterceptor;

    @Value("${exclude.url}")
    private String[] excludeUrl;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(excludeUrl);
    }
}
