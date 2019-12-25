# spring 最佳实践

总结了本人3年Java开发中的一些开发经验和工具类以及Spring框架的应用  
采用了Spring项目的模式来最简单直观的呈现，直接拿来作为初始化项目也是不错的选择  
该项目还在打磨中，仍有很多需要完善和优化的地方  
持续更新，欢迎PR

## 概要

- 如何配置拦截器：interceptor、filter、@RestControllerAdvice
- bean的初始化： InitializingBean接口、@conditionXXX 注解
- 如何获取applicationContext上下文： ApplicationContextAware
- 枚举的优雅使用： 1、如何把枚举作为接口的交互参数：@JsonCreator、@JsonValue ,要注意fastjson和spring采用的jackson 对注解的支持 2、valueOfByXX
- 缓存的优雅使用： @Cacheable 、CaffeineCacheManager、请求级别的缓存RequestScopedCacheManager，注意防止副作用操作污染缓存数据
- 配置文件配置时间属性：java.time.Duration
- 正确的报错方式，message的国际化： [参考](https://www.jianshu.com/p/4d5f16f6ab82)
- 日志的优雅配置：log4j与logback的基础、使用MDC增加tractId跟踪日志

第三方工具的使用

- RestTemplate的优雅使用：工具类的封装、header的注入
- 借助Mybatis-Plus实现零SQL开发
- 借助MapStruct实现po、bo、vo等对象之间的转换
- 健康接口，版本检查： buildnumber-maven-plugin
- 如何深度复制对象：json复制、mapStruct
- apache-common 系列、hutool等基本工具类
- 自定义的时间格式化工具类、jackson的封装工具

运维

- 数据库版本维护 flyway
- Jenkins的常用配置方式

### 在项目中的实践

- 项目必备的工具包：apache-common系列、gson 等
- 借助@RestControllerAdvice实现全局统一的response返回，方法直接通过抛异常来返回
- 更好的实现项目的初始化相关操作
