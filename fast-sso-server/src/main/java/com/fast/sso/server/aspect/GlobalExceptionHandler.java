package com.fast.sso.server.aspect;

import com.fast.sso.client.model.Result;
import com.fast.sso.client.model.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author by:ly
 * @ClassName: GlobalExceptionController
 * @Description: TODO
 * @Date: 2021/7/14 16:31
 **/

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Result handleException(HttpServletRequest req, Exception e) {
        logger.info(e.getMessage());
        logger.error(e.getMessage(), e);
        return Result.failure(ResultCode.FAILURE.getCode(), e.getMessage());
    }
}
