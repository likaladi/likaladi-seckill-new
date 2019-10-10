# likaladi-seckill
## spring-boot-maven-plugin造成install失败解决
1. 删除父likaladi-seckill的pom中的spring-boot-maven-plugin插件依赖
2. 只在需要独立运行的模块，如likaladi-user服务中中加载spring-boot-maven-plugin插件依赖
3. 删除不需要独立运行的模块likaladi-common中的spring-boot-maven-plugin插件依赖
## logback-spring.xml运行失败解决
1. logback-spring.xml日志生效，首先进行编译compile，改动配置也要重写编译compile
2. 编译compile后，查看target目录下的classes目录是否存在logback-spring.xml文件
3. spring-cloud-starter-feign改为spring-cloud-starter-openfeign
## SpringBootApplication指定扫描路径
### 配置指定扫包路径：
    @SpringBootApplication(scanBasePackages = {"com.ec.product", "com.tencent.iov.parent"})
### 排除不扫描的路径：
    @SpringBootApplication(excludeName = {"com.likaladi.db.base"})
## gateway网关启动失败原因
    必须排除相关web依赖：spring-boot-starter-web
## gateway网关跨域解决：
    com.ec.gateway.config.cors.CorsConfiguration