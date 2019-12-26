# spring 最佳实践

总结了本人3年Java开发中的一些开发经验和工具类以及Spring框架的应用  
采用了Spring项目的模式来最简单直观的呈现，直接拿来作为初始化项目也是不错的选择  
该项目还在打磨中，仍有很多需要完善和优化的地方  

**持续更新，欢迎PR**

Spring Boot版本 ：2.1.7 , 兼容的Spring Cloud版本为 Greenwich ,版本对照参考[官网Overview](https://spring.io/projects/spring-cloud#overview)

## 概要

- 如何配置拦截器：interceptor、filter、@RestControllerAdvice
- bean的初始化： InitializingBean接口、@conditionXXX 注解
- 如何获取applicationContext上下文： ApplicationContextAware
- 枚举的优雅使用： 1、如何把枚举作为接口的交互参数：@JsonCreator、@JsonValue ,要注意fastjson和spring采用的jackson 对注解的支持 2、valueOfByXX
- 缓存的优雅使用： @Cacheable 、CaffeineCacheManager、请求级别的缓存RequestScopedCacheManager，注意防止副作用操作污染缓存数据
- 配置文件配置时间属性：java.time.Duration
- 正确的报错方式，message的国际化： [参考](https://www.jianshu.com/p/4d5f16f6ab82)
- 日志的优雅配置：log4j与logback的基础、使用MDC增加tractId跟踪日志
- AOP的应用：自定义注解、方法拦截

第三方工具的使用

- RestTemplate的优雅使用：工具类的封装、header的注入
- 借助Mybatis-Plus实现零SQL开发
- 借助MapStruct实现po、bo、vo等对象之间的转换
- 健康接口，项目部署版本检查： buildnumber-maven-plugin
- 如何深度复制对象：json复制、mapStruct、BeanUtils
- apache-common 系列、hutool等基本工具类
- 自定义的时间格式化工具类、jackson的封装工具

运维

- 数据库版本维护 flyway
- Jenkins的常用配置方式

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

#### 缓存的几种写法  

[web/src/main/java/top/rizon/springbestpractice/web/controller/CacheExampleController.java](web/src/main/java/top/rizon/springbestpractice/web/controller/CacheExampleController.java)    

#### 登陆认证的简单实现方式
  
[web/src/main/java/top/rizon/springbestpractice/web/config/auth/AuthWebConfig.java](web/src/main/java/top/rizon/springbestpractice/web/config/auth/AuthWebConfig.java)  

#### 全局异常拦截处理
  
[common/src/main/java/top/rizon/springbestpractice/common/handler/ExceptionHandlers.java](common/src/main/java/top/rizon/springbestpractice/common/handler/ExceptionHandlers.java)  

#### http请求工具RestTemplate的简单封装使用
  
[common/src/main/java/top/rizon/springbestpractice/common/utils/http/SimpleRestTemplateUtils.java](common/src/main/java/top/rizon/springbestpractice/common/utils/http/SimpleRestTemplateUtils.java)    
RestTemplate拦截器：RestTemplateAuthConfig、BaseAuthHeaderHttpRequestInterceptor

#### 封装的一些简单工具类

[common/src/main/java/top/rizon/springbestpractice/common/utils/](common/src/main/java/top/rizon/springbestpractice/common/utils/)

#### 第三方工具类相关

MapStruct java对象映射工具  
[web/src/main/java/top/rizon/springbestpractice/web/model/WebObjMapper.java](web/src/main/java/top/rizon/springbestpractice/web/model/WebObjMapper.java)
ps. 某些场景下用于deepClone也是一个不错的选择    

#### Spring Boot 优雅配置校验

适用于Starter和启动配置的编写时使用
通过Bean-Valid 来校验启动的Bean,并且提供相关注释,优雅使用配置

[config-valid/src/main/java/cn/boommanpro/config/valid/config/CasConfigProperties.java](config-valid/src/main/java/cn/boommanpro/config/valid/config/CasConfigProperties.java)

ps. 优雅配置校验及提示