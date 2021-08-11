package com.fast.sso.server.controller;

import com.fast.sso.client.constant.ParamConstant;
import com.fast.sso.client.entity.SsoUser;
import com.fast.sso.client.executor.SsoExecutor;
import com.fast.sso.client.model.Result;
import com.fast.sso.client.model.ResultCode;
import com.fast.sso.server.entity.UserDO;
import com.fast.sso.server.service.SsoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author by:ly
 * @ClassName: SsoController
 * @Description: TODO
 * @Date: 2021/7/22 19:55
 **/
@Controller
public class SsoController {

    private SsoExecutor ssoExecutor=new SsoExecutor();

    @Resource
    private SsoService ssoService;

    @RequestMapping(value = "index",method = RequestMethod.GET)
    public String index(HttpServletRequest request,HttpServletResponse response){
        return "index";
    }

    @RequestMapping(value = {"/","/login"},method = RequestMethod.GET)
    public String login(HttpServletRequest request, HttpServletResponse response, Model model){
        String jumpUrl = request.getParameter(ParamConstant.jumpUrl);
        model.addAttribute(ParamConstant.jumpUrl,jumpUrl);
        return "login";
    }

    @RequestMapping(value = "checkLogin",method = RequestMethod.POST)
    @ResponseBody
    public Result checkLogin(@RequestBody Map<String,String> params,HttpServletRequest request,HttpServletResponse response) throws Exception {
        String username = params.get("username");
        String password = params.get("password");
        String remember = params.get("remember");
        UserDO checkedUser = ssoService.checkLogin(username, password, remember);
        if (checkedUser==null){
            return Result.failure(ResultCode.FAILURE);
        }else{
            SsoUser ssoUser=new SsoUser();
            ssoUser.setUserId(checkedUser.getUserId());
            ssoUser.setUserName(checkedUser.getUsername());
            ssoExecutor.login(ssoUser,request,response);
            return Result.success();
        }
    }

    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    public String loginOut(@RequestBody Map<String,String> param,HttpServletRequest request,HttpServletResponse response){
        return "logout";
    }

    @RequestMapping(value = "/register",method = RequestMethod.GET)
    public String register(){
        return "register";
    }

    @RequestMapping(value = "/checkRegister",method = RequestMethod.POST)
    @ResponseBody
    public Result checkRegister(@RequestBody Map<String,String> params) throws Exception {
        String username = params.get("username");
        String password = params.get("password");
        return ssoService.register(username,password);
    }
}
