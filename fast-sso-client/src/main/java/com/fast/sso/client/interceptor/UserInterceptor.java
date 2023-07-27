package com.fast.sso.client.interceptor;

import com.fast.sso.client.constant.ParamConstant;
import com.fast.sso.client.entity.SsoUser;
import com.fast.sso.client.executor.SsoExecutor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author by:ly
 * @ClassName: UserInterceptor
 * @Description: UserInterceptor
 * @Date: 2021/7/14 19:47
 **/
public class UserInterceptor implements HandlerInterceptor {

    private SsoExecutor ssoExecutor=new SsoExecutor();

    @Value("${sso.server.path}")
    private String ssoServerPath;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        SsoUser ssoUser = ssoExecutor.checkLogin(request,response);
        String jumpUrl=request.getRequestURL().toString();
        if (ssoUser==null){
            String loginUrl=ssoServerPath+ParamConstant.loginPath+"?"+ParamConstant.jumpUrl+"="+jumpUrl;
            response.sendRedirect(loginUrl);
            return false;
        }else {
            return true;
        }

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
