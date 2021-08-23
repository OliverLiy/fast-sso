package com.fast.sso.client.executor;

import com.alibaba.fastjson.JSON;

import com.fast.sso.client.constant.ParamConstant;
import com.fast.sso.client.entity.SsoUser;
import com.fast.sso.client.util.CookieUtil;
import com.fast.sso.client.util.JedisUtil;
import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.Jedis;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author by:ly
 * @ClassName: SsoExecutor
 * @Description: TODO sso逻辑核心处理类
 * @Date: 2021/7/14 20:25
 **/
public class SsoExecutor {

    private Jedis jedis= JedisUtil.getJedis();

    private int expireMinute=60*2;
    /**
     * 记住我时24小时失效
     */
    private int rememberExpireMinute=60*24;

    public void setExpireMinute(int expireMinute) {
        if (expireMinute<30){
            this.expireMinute=30;
        }
        this.expireMinute = expireMinute;
    }

    public int getExpireMinute() {
        return expireMinute;
    }

    /**
     * 处理登录逻辑
     * @param ssoUser
     */
    public void login(SsoUser ssoUser,boolean remember, HttpServletRequest request, HttpServletResponse response){
        String sessionId = SsoSessionGenerate.generateSessionId(ssoUser);
        String userJson = JSON.toJSONString(ssoUser);
        //将session绑定到redis中
        if (remember){
            jedis.setex(sessionId, rememberExpireMinute * 60, userJson);
        }else {
            jedis.setex(sessionId, expireMinute * 60, userJson);
        }
        //将值塞到cookie中
        CookieUtil.set(response, ParamConstant.cookiePrefix,sessionId,remember);

    }

    /**
     * 验证登录状态
     * @param request
     * @param response
     * @return
     */
    public SsoUser checkLogin(HttpServletRequest request,HttpServletResponse response){
        String sessionId = CookieUtil.get(request, ParamConstant.cookiePrefix);
        if (StringUtils.isEmpty(sessionId)){
            return null;
        }
        String ssoUserJson = jedis.get(sessionId);
        if (StringUtils.isEmpty(ssoUserJson)){
            return null;
        }
        SsoUser ssoUser = JSON.parseObject(ssoUserJson, SsoUser.class);

        return ssoUser;
    }

    /**
     * 登出
     * @param request
     * @param response
     */
    public boolean logout(HttpServletRequest request,HttpServletResponse response){
        String sessionId = CookieUtil.get(request, ParamConstant.cookiePrefix);
        if (StringUtils.isEmpty(sessionId)){
            return false;
        }
        jedis.del(sessionId);
        CookieUtil.remove(request,response, ParamConstant.cookiePrefix);
        return true;
    }

    /**
     * 获取当前登录用户
      * @param request
     * @param response
     * @return
     */
    public SsoUser getUser(HttpServletRequest request,HttpServletResponse response){
        String sessionId = CookieUtil.get(request, ParamConstant.cookiePrefix);
        if (StringUtils.isNotEmpty(sessionId)){
            SsoUser sessionUser = SsoSessionGenerate.getSessionUser(sessionId);
            return sessionUser;
        }
        return null;
    }

}
