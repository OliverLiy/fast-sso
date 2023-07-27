package com.fast.sso.client.entity;

import lombok.Data;

import java.util.Map;

/**
 * @author by:ly
 * @ClassName: SsoUser
 * @Description: SsoUser
 * @Date: 2021/7/15 10:57
 **/
@Data
public class SsoUser {
    /**
     * 用户id
     */
    private String userId;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 其他扩展信息
     */
    private Map<String,Object> properties;

}
