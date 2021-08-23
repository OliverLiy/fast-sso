package com.fast.sso.server.service.impl;

import com.fast.sso.client.entity.SsoUser;
import com.fast.sso.client.executor.SsoExecutor;
import com.fast.sso.client.model.Result;
import com.fast.sso.client.model.ResultCode;
import com.fast.sso.client.util.MD5Util;
import com.fast.sso.server.entity.UserDO;
import com.fast.sso.server.entity.UserVO;
import com.fast.sso.server.mapper.UserMapper;
import com.fast.sso.server.service.SsoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @author by:ly
 * @ClassName: SsoServiceImpl
 * @Description: TODO
 * @Date: 2021/8/3 10:00
 **/
@Service
public class SsoServiceImpl implements SsoService {

    @Autowired
    private SsoExecutor ssoExecutor;

    @Resource
    private UserMapper userMapper;

    @Override
    public UserDO checkLogin(String username, String password) throws Exception {
        UserDO params=new UserDO();
        params.setUsername(username);
        params.setPassword(MD5Util.get32BitMD5(password));
        UserDO checkedUser = userMapper.selectByRecord(params);
        return checkedUser;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result register(String username, String password) throws Exception {
        Result result=new Result();
        UserDO params=new UserDO();
        params.setUsername(username);
        //1、判断用户名是否重复
        UserDO userDO = userMapper.selectByRecord(params);
        if (userDO!=null){
            result.setCode(ResultCode.DATA_EXISTS.getCode());
            result.setMsg(ResultCode.DATA_EXISTS.getMsg());
            return result;
        }
        //2、注册
        params.setPassword(MD5Util.get32BitMD5(password));
        params.setUserId(UUID.randomUUID().toString().replaceAll("-",""));
        userMapper.insert(params);
        return Result.success();
    }

    @Override
    public Result getUserInfo(HttpServletRequest request, HttpServletResponse response) {
        SsoUser user = ssoExecutor.getUser(request, response);
        if (user==null){
            return Result.failure(ResultCode.DATA_NOT_EXISTS);
        }
        String userId = user.getUserId();
        UserDO param=new UserDO();
        param.setUserId(userId);
        UserDO userDO = userMapper.selectByRecord(param);
        UserVO userVO=new UserVO();
        userVO.setUserId(userDO.getUserId());
        userVO.setUsername(userDO.getUsername());
        return Result.success(userVO);
    }
}
