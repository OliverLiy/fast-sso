package com.fast.sso.server.controller;

import com.fast.sso.client.constant.ParamConstant;
import com.fast.sso.client.entity.SsoUser;
import com.fast.sso.client.executor.SsoExecutor;
import com.fast.sso.client.model.Result;
import com.fast.sso.client.model.ResultCode;
import com.fast.sso.server.entity.UserDO;
import com.fast.sso.server.service.SsoService;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @Description: SsoController
 * @Date: 2021/7/22 19:55
 **/
@Controller
public class SsoController {

    @Autowired
    private SsoExecutor ssoExecutor;
    @Resource
    private SsoService ssoService;

    /**
     * Sso首页
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "index",method = RequestMethod.GET)
    public String index(HttpServletRequest request,HttpServletResponse response){
        return "index";
    }

    /**
     * 登录接口
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = {"/","/login"},method = RequestMethod.GET)
    public String login(HttpServletRequest request, HttpServletResponse response, Model model){
        String jumpUrl = request.getParameter(ParamConstant.jumpUrl);
        model.addAttribute(ParamConstant.jumpUrl,jumpUrl);
        return "login";
    }

    /**
     * 验证登录接口
     * @param params
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "checkLogin",method = RequestMethod.POST)
    @ResponseBody
    public Result checkLogin(@RequestBody Map<String,String> params,HttpServletRequest request,HttpServletResponse response) throws Exception {
        String username = params.get("username");
        String password = params.get("password");
        boolean remember = Boolean.parseBoolean(params.get("remember"));
        UserDO checkedUser = ssoService.checkLogin(username, password);
        if (checkedUser==null){
            return Result.failure(ResultCode.FAILURE);
        }else{
            SsoUser ssoUser=new SsoUser();
            ssoUser.setUserId(checkedUser.getUserId());
            ssoUser.setUserName(checkedUser.getUsername());
            ssoExecutor.login(ssoUser,remember,request,response);
            return Result.success();
        }
    }

    /**
     * 登出接口
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    @ResponseBody
    public Result loginOut(@RequestBody HttpServletRequest request,HttpServletResponse response){
        boolean logout = ssoExecutor.logout(request, response);
        if (!logout){
            Result.failure(ResultCode.DATA_NOT_EXISTS);
        }
        return Result.success();
    }

    /**
     * 注册接口
     * @return
     */
    @RequestMapping(value = "/register",method = RequestMethod.GET)
    public String register(){
        return "register";
    }

    /**
     * 验证注册接口
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/checkRegister",method = RequestMethod.POST)
    @ResponseBody
    public Result checkRegister(@RequestBody Map<String,String> params) throws Exception {
        String username = params.get("username");
        String password = params.get("password");
        return ssoService.register(username,password);
    }

    @RequestMapping(value = "/getUserInfo",method = RequestMethod.POST)
    @ResponseBody
    public Result getUserInfo(HttpServletRequest request,HttpServletResponse response){
        return ssoService.getUserInfo(request,response);
    }

}
