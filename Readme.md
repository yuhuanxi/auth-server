**项目介绍**

> 使用 Spring Boot + Spring Security + Spring Security OAuth2 作权限控制

使用 MySQL 存储权限及 token。

执行 classpath/resources/auth.sql,创建对应的数据表。

#### 注解

hasRole 与 hasAuthory 效果一样。
唯一区别：

[Difference between Role and GrantedAuthority in Spring Security](https://stackoverflow.com/questions/19525380/difference-between-role-and-grantedauthority-in-spring-security)

hasRole 不起作用：

[Spring Security hasRole() not working](https://stackoverflow.com/questions/30788105/spring-security-hasrole-not-working)

**注解使用方法：**

1. 具有 super 或者 normal 权限的两种写法：
    `@PreAuthorize("hasAnyAuthority('super','normal')")`
    `@PreAuthorize("hasAuthority('super') or hasAuthority('normal')")`
    
2. 具有 scope=server 的用户才能访问
    `@PreAuthorize("#oauth2.hasScope('server')")`
    
3. 具有 super 权限的用户才能访问
    `@PreAuthorize("hasAuthority('super')")`
