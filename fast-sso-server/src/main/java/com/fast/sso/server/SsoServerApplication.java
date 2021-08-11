package com.fast.sso.server;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author by:ly
 * @ClassName: SsoServerApplication
 * @Description: TODO
 * @Date: 2021/7/13 17:05
 **/
@SpringBootApplication
@MapperScan(value = "com.fast.sso.server.mapper")
public class SsoServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(SsoServerApplication.class);
    }
}
