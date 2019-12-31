# spring 最佳实践

总结了本人多年Java开发中的一些开发经验以及工具类和Spring框架的应用  
采用了项目Demo的方式把零散的内容联系在一起去展示其用法，可以直接拿来作为种子项目，用于快速构建中小型的spring-boot项目  
**项目持续更新中，将会逐步扩充完善**

Spring Boot版本 ：2.1.7 , 兼容的Spring Cloud版本为 Greenwich ,版本对照参考[官网Overview](https://spring.io/projects/spring-cloud#overview)

## 概要

- 如何配置拦截器：interceptor、filter、@RestControllerAdvice
- bean的初始化： InitializingBean接口、@conditionXXX 注解
- 如何获取applicationContext上下文： ApplicationContextAware
- 枚举的优雅使用： 1、如何把枚举作为接口的交互参数：@JsonCreator、@JsonValue ,要注意fastjson和spring采用的jackson 对注解的支持 2、valueOfByXX
- 缓存的优雅使用： @Cacheable 、CaffeineCacheManager、请求级别的缓存RequestScopedCacheManager，注意防止副作用操作污染缓存数据
- 配置文件配置时间属性：java.time.Duration
- 正确的报错方式，message的国际化： [参考](https://www.jianshu.com/p/4d5f16f6ab82)
- 接口参数校验：@Validated、javax.validation.constraints、自定义参数校验注解
- 日志的优雅配置：log4j与logback的基础、使用MDC增加tractId跟踪日志
- AOP的应用：自定义注解、方法拦截
- java8 stream 的使用技巧

第三方工具的使用

- json的处理：gson、fastjson、jackson，json-path
- RestTemplate的优雅使用：工具类的封装、header的注入
- 借助Mybatis-Plus实现零SQL开发
- 借助MapStruct实现po、bo、vo等对象之间的转换
- 健康接口，项目部署版本检查： buildnumber-maven-plugin
- 如何深度复制对象：json复制、mapStruct、BeanUtils
- apache-common 系列、hutool等基本工具类
- 自定义的时间格式化工具类、jackson的封装工具

运维

- 数据库版本维护 flyway
- CI/CD相关: Jenkins的常用配置方式、docker、kubernetes

### 在项目中的实践

- 项目必备的工具包：apache-common系列、gson 等
- 借助@RestControllerAdvice实现全局统一的response返回，方法直接通过抛异常来返回
- 更好的实现项目的初始化相关操作

### 代码之道

- 如何干掉if-else

## 导航

#### 健康接口

[web/src/main/java/top/rizon/springbestpractice/web/controller/ServerHealthController.java](web/src/main/java/top/rizon/springbestpractice/web/controller/ServerHealthController.java)  

健康接口是项目必备接口  
基本的ping-pong：接口作为最基本的服务存活检测  
服务器信息接口：可以打印服务常用信息的接口，比如服务器时间等  
git版本信息的接口：接口打印了git版本号和build时间，在开发联调期间是非常重要的一个运维参考  
_ps. 要从auth认证拦截中排除_

#### 接口参数校验

[web/src/main/java/top/rizon/springbestpractice/web/controller/UserController.java](web/src/main/java/top/rizon/springbestpractice/web/controller/UserController.java)  
错误提示配置：[web/src/main/resources/ValidationMessages.properties](web/src/main/resources/ValidationMessages.properties)  

示例方法：`top.rizon.springbestpractice.web.controller.UserController.list`

通过注解实现参数校验，
错误提示信息可以使用配置文件实现国际化处理

#### 缓存的几种写法  

[web/src/main/java/top/rizon/springbestpractice/web/controller/CacheExampleController.java](web/src/main/java/top/rizon/springbestpractice/web/controller/CacheExampleController.java)    

#### 登陆认证的简单实现方式
  
[web/src/main/java/top/rizon/springbestpractice/web/config/auth/AuthWebConfig.java](web/src/main/java/top/rizon/springbestpractice/web/config/auth/AuthWebConfig.java)  

#### 全局异常拦截处理
  
[common/src/main/java/top/rizon/springbestpractice/common/handler/ExceptionHandlers.java](common/src/main/java/top/rizon/springbestpractice/common/handler/ExceptionHandlers.java)  

#### http请求工具RestTemplate的简单封装使用
  
[common/src/main/java/top/rizon/springbestpractice/common/utils/http/SimpleRestTemplateUtils.java](common/src/main/java/top/rizon/springbestpractice/common/utils/http/SimpleRestTemplateUtils.java)    
RestTemplate拦截器：RestTemplateAuthConfig、BaseAuthHeaderHttpRequestInterceptor

#### 封装的工具类

[common/src/main/java/top/rizon/springbestpractice/common/utils/](common/src/main/java/top/rizon/springbestpractice/common/utils/)  

##### sql注入的应用

[dao/src/main/java/top/rizon/springbestpractice/dao/utils/dynamictblname/](dao/src/main/java/top/rizon/springbestpractice/dao/utils/dynamictblname/)  
`top.rizon.springbestpractice.web.controller.SqlExampleController.queryDateTable`  
动态表名,一般可用于按日期等方式做分表的业务场景  
顺便演示了PageHelper的分页工具的使用  

#### java8 stream 的技巧

[common/src/test/java/top/rizon/springbestpractice/common/utils/StreamUtilTest.java](common/src/test/java/top/rizon/springbestpractice/common/utils/StreamUtilTest.java)  

JDK8引入的Lambda表达式和Stream为Java平台提供了函数式编程的支持，java提供了consumer、function等一系列接口为函数式编程提供了基础。  
Lambda表达式是一个能够作为参数传递的匿名函数对象，它没有名字，有参数列表、函数体、返回类型，也可以抛出异常。它的类型是函数接口（Functional Interface）。  
函数式编程以操作（函数）为中心，强调变量不变性，无副作用。  

#### 第三方工具类相关

MapStruct java对象映射工具  
[web/src/main/java/top/rizon/springbestpractice/web/model/WebObjMapper.java](web/src/main/java/top/rizon/springbestpractice/web/model/WebObjMapper.java)  
ps. 某些场景下用于deepClone也是一个不错的选择    

#### AOP的应用案例

[common/src/main/java/top/rizon/springbestpractice/common/aspect/](common/src/main/java/top/rizon/springbestpractice/common/aspect/)  

#####  分页页码自动修复

[web/src/main/java/top/rizon/springbestpractice/web/controller/UserController.java](web/src/main/java/top/rizon/springbestpractice/web/controller/UserController.java)  
`top.rizon.springbestpractice.web.controller.UserController.list`  
当页码大于数据真实页码时会纠正为最后一页的数据，这可以解决前端分页展示删除最后一页的最后一条数据时刷新后为无数据的空白页的问题  

##### 打印方法执行时间

[web/src/main/java/top/rizon/springbestpractice/web/controller/DemoController.java](web/src/main/java/top/rizon/springbestpractice/web/controller/DemoController.java)
`top.rizon.springbestpractice.web.service.AopExampleService.sleepMethod`  
打印方法执行时间，这在优化代码，排查耗时过高的方法时有一定的帮助

#### 代码之道

##### 策略模式 干掉if-else

[web/src/main/java/top/rizon/springbestpractice/web/controller/DemoController.java](web/src/main/java/top/rizon/springbestpractice/web/controller/DemoController.java)  
`top.rizon.springbestpractice.web.controller.DemoController.formatDate`  
如果你的if-else过于复杂那么应当考虑抽象业务了，简单的业务可以直接用枚举写抽象方法的实现，复杂的业务则可以写接口类去实现  

#### 使用枚举作为请求参数

[web/src/main/java/top/rizon/springbestpractice/web/controller/DemoController.java](web/src/main/java/top/rizon/springbestpractice/web/controller/DemoController.java)  
`top.rizon.springbestpractice.web.controller.DemoController.formatDate`  
jackson的`@JsonCreator`可以指定json反序列化时的构造函数，`@JsonValue`则可以指定对象序列化时的取值属性

## 使用

[API Doc - Postman](https://documenter.getpostman.com/view/494976/SWLZgAn8)    

编译

```bash
# buildnumber-maven-plugin配置所致，在clean之后运行测试用例会由于尚未替换的配置导致配置文件读取失败
mvn clean package -DskipTests=true
java -jar web/target/web-0.0.1-SNAPSHOT.jar
```

### Online IDE

[![在 Gitpod 中打开](https://gitpod.io/button/open-in-gitpod.svg)](https://gitpod.io#https://github.com/othorizon/spring-best-practices)
