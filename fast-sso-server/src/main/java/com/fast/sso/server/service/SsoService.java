package com.fast.sso.server.service;

import com.fast.sso.client.model.Result;
import com.fast.sso.server.entity.UserDO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author by:ly
 * @ClassName: SsoService
 * @Description: Sso逻辑service
 * @Date: 2021/8/3 10:00
 **/
public interface SsoService {
    /**
     * 验证登录成功还是失败
     * @param username
     * @param password
     */
    UserDO checkLogin(String username, String password) throws Exception;

    /**
     * 注册用户
     * @param username
     * @param password
     * @return
     * @throws Exception
     */
    Result register(String username, String password) throws Exception;

    /**
     * 获取用户信息
     * @param request
     * @param response
     * @return
     */
    Result getUserInfo(HttpServletRequest request, HttpServletResponse response);
}
