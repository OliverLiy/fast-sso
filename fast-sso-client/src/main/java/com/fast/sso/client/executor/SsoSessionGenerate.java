package com.fast.sso.client.executor;

import com.fast.sso.client.entity.SsoUser;

/**
 * @author by:ly
 * @ClassName: SsoGenerate
 * @Description: TODO session生成，分解工具类
 * @Date: 2021/7/15 14:01
 **/
public class SsoSessionGenerate {

    /**
     * 生成sessionId
     * @param ssoUser
     * @return
     */
    public static String generateSessionId(SsoUser ssoUser){
        String sessionId=ssoUser.getUserId()+"_"+ssoUser.getUserName()+"_"+System.currentTimeMillis();
        return sessionId;
    }

    public static String getSessionId(String sessionId){
        return null;
    }
}
