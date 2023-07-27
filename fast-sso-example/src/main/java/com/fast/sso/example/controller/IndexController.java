package com.fast.sso.example.controller;

import com.fast.sso.client.executor.SsoExecutor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author by:ly
 * @ClassName: IndexController
 * @Description: IndexController
 * @Date: 2021/7/14 18:47
 **/
@RestController
public class IndexController {

    private SsoExecutor ssoExecutor=new SsoExecutor();

    @RequestMapping("index")
    public String index(){
        return "index";
    }

    @RequestMapping("logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        return String.valueOf(ssoExecutor.logout(request, response));
    }
}
