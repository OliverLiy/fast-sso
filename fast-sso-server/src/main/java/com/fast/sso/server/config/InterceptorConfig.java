package com.fast.sso.server.config;

import com.fast.sso.client.executor.SsoExecutor;
import com.fast.sso.client.interceptor.UserInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author by:ly
 * @ClassName: InterceptorConfig
 * @Description: InterceptorConfig
 * @Date: 2021/7/27 19:47
 **/
@Configuration
public class InterceptorConfig {

    @Bean
    public UserInterceptor userInterceptor(){
        return new UserInterceptor();
    }

    @Bean
    public SsoExecutor ssoExecutor(){
        return new SsoExecutor();
    }
}
