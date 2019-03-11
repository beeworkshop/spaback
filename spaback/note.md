后端设计的关键点就在于: **后台接口只验证权限,不看角色.**

角色的作用其实只是用来管理分配权限的,真正的验证只验证**权限** ,而不去管你是否是那种角色.体现在代码上就是接口上注解为

```java
@RequiresPermissions("article:add")
```

而不是

```java
@RequiresRoles(value = {"admin","manager","writer"}, logical = Logical.OR)
```

***

    对于Mybatis的输入/输出映射可以直接使用com.alibaba.fastjson.JSONObject。如果使用JSONObject那么model中可以不定义对应表结构的javabean——这里JSONObject是引入的类。

***

#### spring boot拦截器WebMvcConfigurerAdapter，以及高版本的替换方案





#### 新的版本解决方案目前有两种

> 方案1 直接实现WebMvcConfigurer

```java
    @Configuration
public class WebMvcConfg implements WebMvcConfigurer {

        @Override
        public void addViewControllers(ViewControllerRegistry registry) {
                registry.addViewController("/index").setViewName("index");
        }

}
```

> 方案2 直接继承WebMvcConfigurationSupport（推荐）

```java
    @Configuration
public class WebMvcConfg extends WebMvcConfigurationSupport {

        @Override
        public void addViewControllers(ViewControllerRegistry registry) {
                registry.addViewController("/index").setViewName("index");
        }

}
```
***
### 前后台的json接口
>1. login  
**web--->server**
```json
{ "password":"123456",
  "username":"admin"
}
```
**server--->web**  
<font color=red>成功</font>
```json
{ "msg":"请求成功",
  "code":"100",
  "info":{
    "result":"success"
  }
}
```
**server--->web**  
<font color=red>失败</font>
```json
{ "msg":"请求成功",
  "code":"100",
  "info":{
    "result":"fail"
  }
}
```
