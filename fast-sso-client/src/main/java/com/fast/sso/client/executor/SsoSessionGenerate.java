package com.fast.sso.client.executor;

import com.fast.sso.client.entity.SsoUser;
import org.apache.commons.lang3.StringUtils;

/**
 * @author by:ly
 * @ClassName: SsoGenerate
 * @Description: session生成，分解工具类
 * @Date: 2021/7/15 14:01
 **/
public class SsoSessionGenerate {

    /**
     * 生成sessionId
     * @param ssoUser
     * @return
     */
    public static String generateSessionId(SsoUser ssoUser){
        String sessionId=ssoUser.getUserId()+"_"+System.currentTimeMillis();
        return sessionId;
    }

    /**
     * 根据sessionId获取到当前用户
     * @param sessionId
     * @return
     */
    public static SsoUser getSessionUser(String sessionId){
        if (StringUtils.isEmpty(sessionId)){
            return null;
        }else {
            SsoUser user=new SsoUser();
            String[] split = sessionId.split("_");
            user.setUserId(split[0]);
            return user;
        }
    }
}
