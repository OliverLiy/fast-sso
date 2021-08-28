# 演示效果
访问接入统一认证的应用：localhost:8999/index


# 配置统一认证中心
## 1、在数据库中建立sso用户信息表
```mysql
CREATE DATABASE sso_user;
USE sso_user;
SET NAMES utf8mb4;
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户Id',
  `username` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci;
```

## 2、在fast-sso-server中配置中修改sso.server.path为自己的sso应用路径
```properties
sso.server.path=http://localhost:8777
```
## 3、修改redis的地址和连接信息
```properties
redis.host = 10.10.128.226
redis.port = 6379
#最大空闲数
redis.maxidle = 10
#最大连接数
redis.maxtotal = 30
#如果redis没有设置密码则置为空
redis.password =
```
## 4、修改mysql的连接信息
```properties
spring.datasource.url=jdbc:mysql://10.10.128.226:3306/sso_user?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8&useSSL=false
spring.datasource.username=root
spring.datasource.password=123456
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```


# 配置自己的应用
# 1、引入依赖，目前最新为0.0.2
```xml
<dependency>
            <artifactId>fast-sso-client</artifactId>
            <groupId>io.github.oliverliy</groupId>
            <version>{latest}</version>
</dependency>
```

# 2、配置sso地址、redis地址以及不走统一认证的地址
```properties
sso.server.path = http://localhost:8777
redis.host = 10.10.128.226
redis.port = 6379
redis.maxidle = 10
redis.maxtotal = 30
redis.password =
#添加后下面的地址将不会走统一认证
exclude.url=/css/**,/actuator/**
```

# 3、配置一个拦截器
```java
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    UserInterceptor userInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userInterceptor)
                .addPathPatterns("/**");
    }
    @Bean
    public UserInterceptor userInterceptor(){
        return new UserInterceptor();
    }
}
```

# 当前提供的可使用接口
获取当前登录用户信息
sso-server地址/getUserInfo
登出
sso-server地址/logout

