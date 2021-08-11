package com.fast.sso.server.service;

import com.fast.sso.client.model.Result;
import com.fast.sso.server.entity.UserDO;

/**
 * @author by:ly
 * @ClassName: SsoService
 * @Description: TODO Sso逻辑service
 * @Date: 2021/8/3 10:00
 **/
public interface SsoService {
    /**
     * 验证登录成功还是失败
     * @param username
     * @param password
     */
    UserDO checkLogin(String username, String password, String remember) throws Exception;

    Result register(String username, String password) throws Exception;
}
