# （一）概述
最近突然有个想法想造点轮子，平常写的代码业务居多，因此想写点别的。于是就有了造轮子系列。登录认证应该是每个程序员写的第一个功能，至少对我来说是的，于是我的第一个造轮子项目也打算写登录认证，只不过换成了分布式统一认证项目。希望达到的效果是接入的项目可以通过简单的配置快速接入，不用再关注登录认证的事情。

github地址：**http://github.com/OliverLiy/fast-sso**

gitee地址：**https://gitee.com/lyucoding/fast-sso**

maven：**中仓仓库直接搜fast-sso**

# （二）演示效果
访问接入fast-sso的应用：localhost:8999/index ，因为未登录，自动跳转到统一认证服务的登录页

![](https://img-blog.csdnimg.cn/22fe94bfebac44c5ac5cf4cccc45ca43.png?x-oss-process=image)

输入用户名密码后点击Sign in，自动跳回localhost:8999/index

![](https://img-blog.csdnimg.cn/4a0699be27be48a6882176d105ae17c4.png?x-oss-process=image)

另外一个也接入了fast-sso项目在同一个浏览器打开：localhost:9000/index，不需要登录直接进入系统：

![](https://img-blog.csdnimg.cn/6d9cf77c60064012b21b2ee847388e74.png?x-oss-process=image)



# （三）如何接入
项目包含fast-sso-server和fast-sso-client，fast-sso-server是统一认证服务中心，fast-sso-client给各个系统接入使用。因为目前未正式发版，因此采用源码编译的方式。首先下载源代码：https://github.com/OliverLiy/fast-sso或https://gitee.com/lyucoding/fast-sso

## 配置统一认证中心
### 1、在数据库中建立sso用户信息表
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

### 2、在fast-sso-server中配置中修改sso.server.path为自己的sso应用路径
```properties
sso.server.path=http://localhost:8777
```
### 3、修改redis的地址和连接信息
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
### 4、修改mysql的连接信息
```properties
spring.datasource.url=jdbc:mysql://10.10.128.226:3306/sso_user?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8&useSSL=false
spring.datasource.username=root
spring.datasource.password=123456
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```


## 配置每个接入的应用
### 1、引入依赖，目前最新为0.0.2
```xml
<dependency>
 	<artifactId>fast-sso-client</artifactId>
    <groupId>io.github.oliverliy</groupId>
    <version>{latest}</version>
</dependency>
```

### 2、配置sso地址、redis地址
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

### 3、配置一个拦截器
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

## 当前提供的可使用接口
获取当前登录用户信息：

**sso-server地址/getUserInfo**

登出：

**sso-server地址/logout**


# （四）如何使用
如果本地跑，直接运行fast-sso-server即可，如果放到服务器，可以将项目打成jar包后运行。注意，当前实现方式基于cookie，因此统一认证中心和项目需要在同一个域名下。

fast-sso-server启动后，运行自己接入的项目即可。

# （五）如何扩展
## 5.1 想增加用户信息怎么办？
目前的用户表中没有其他的字段信息，如果想要新增更多的用户信息，可新建一张用户详情表，与用户表中id作关联。mybatis的sql中做联表操作即可。

## 5.2 想垮域接入统一认证怎么办？
后续会引入token机制，不依赖cookie。

## 5.3 其他有问题怎么办？
可以在github或gitee直接提，或者联系本人。