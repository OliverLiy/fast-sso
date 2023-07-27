package com.fast.sso.server.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author by:ly
 * @ClassName: UserVO
 * @Description: UserVO
 * @Date: 2021/8/19 20:30
 **/
@Data
public class UserVO implements Serializable {
    /**
     * 用户Id
     */
    private String userId;

    /**
     * 用户名
     */
    private String username;
}
