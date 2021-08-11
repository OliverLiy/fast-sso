package com.fast.sso.client.util;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author by:ly
 * @ClassName: CookieUtil
 * @Description: TODO
 * @Date: 2021/7/21 20:31
 **/
public class CookieUtil {

    // Cookie时间
    private static final int COOKIE_AGE = 60*60*24;
    // 保存路径,根路径
    private static final String COOKIE_PATH = "/";

    public static String get(HttpServletRequest request,String key){
        Cookie[] cookies = request.getCookies();
        if (cookies!=null){
            for (int i = 0; i < cookies.length; i++) {
                if (cookies[i].getName().equals(key)){
                    return cookies[i].getValue();
                }
            }
        }
        return null;
    }

    /**
     * 保存cookie
     * @param response
     * @param key
     * @param value
     */
    public static void set(HttpServletResponse response, String key, String value) {
        set(response, key, value, null, COOKIE_PATH, COOKIE_AGE, true);
    }

    /**
     * 保存
     *
     * @param response
     * @param key
     * @param value
     * @param maxAge
     */
    private static void set(HttpServletResponse response, String key, String value, String domain, String path, int maxAge, boolean isHttpOnly) {
        Cookie cookie = new Cookie(key, value);
        if (domain != null) {
            cookie.setDomain(domain);
        }
        cookie.setPath(path);
        cookie.setMaxAge(maxAge);
        cookie.setHttpOnly(isHttpOnly);
        response.addCookie(cookie);
    }

    public static void remove(HttpServletRequest request,HttpServletResponse response, String key) {
        String cookieValue = get(request, key);
        if (StringUtils.isNotEmpty(cookieValue)){
            set(response,key,"",null,COOKIE_PATH,0,true);
        }
    }
}
