package com.fast.sso.example.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author by:ly
 * @ClassName: IndexController
 * @Description: TODO
 * @Date: 2021/7/14 18:47
 **/
@RestController
public class IndexController {


    @RequestMapping("index")
    public String index(){
        return "index";
    }
}
