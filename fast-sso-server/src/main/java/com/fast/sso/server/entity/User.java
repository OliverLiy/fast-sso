package com.fast.sso.server.entity;

import lombok.Data;

/**
 * @author by:ly
 * @ClassName: User
 * @Description: TODO
 * @Date: 2021/7/29 19:57
 **/
@Data
public class User {
    private long userId;
    private String username;
    private String password;
}
