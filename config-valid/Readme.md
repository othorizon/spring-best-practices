# Config-Valid

## 使用教程

** 以CasConfigProperties为例 **

1. 创建配置类 CasConfigProperties.java
2. 增加@ConfigurationProperties注解
3. 增加 @Validated 及想要校验的注解 e.g. `@NotBlank`

启动时,如果配置信息不满足条件,项目会启动失败 !!

项目在为编译的情况下,application.yml没有提示. 如果需要强制提示,可以在项目目录下增加 META-INF/spring-configuration-metadata.json

缺点是:编译后会有两份提示.