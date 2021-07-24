一、创建web项目，配置tomcat。

目录结构如下：

![image-20210427103342376](https://raw.githubusercontent.com/li0228/image/master/image-20210427103342376.png)

二、引入spring

**导入spring核心依赖**

```xml
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-context</artifactId>
      <version>5.2.0.RELEASE</version>
    </dependency>
```

**创建applicationContext.xml文件**

暂时就增加一个注解扫描。排除controller，controller后面交给springmvc管理。

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd">

    <!--注解扫描，排除controller-->
    <context:component-scan base-package="com.lhh.curd">
        <context:exclude-filter type="annotation" expression="org.springframework.stereotype.Controller"/>
    </context:component-scan>

</beans>
```

**在web.xml文件里面指定spring配置文件位置并且增加监听器启动applicationContext**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">
    <!--制定spirng配置文件-->
    <context-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>classpath:applicationContext.xml</param-value>
    </context-param>
    
    <!--监听器启动spring容器-->
    <listener>
        <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
    </listener>
</web-app>
```

三、引入mybatis

**创建数据库和各层类文件**
![image-20210427114513737](https://raw.githubusercontent.com/li0228/image/master/image-20210427114513737.png)

![image-20210427114630521](https://raw.githubusercontent.com/li0228/image/master/image-20210427114630521.png)

**导入依赖**

```xml
    <!--mybatis部分-->
    <!--mybatis-spring-->
    <dependency>
      <groupId>org.mybatis</groupId>
      <artifactId>mybatis-spring</artifactId>
      <version>2.0.6</version>
    </dependency>
    <!--mybatis-->
    <dependency>
      <groupId>org.mybatis</groupId>
      <artifactId>mybatis</artifactId>
      <version>3.5.6</version>
    </dependency>
    <!--spring-jdbc-->
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-jdbc</artifactId>
      <version>5.2.0.RELEASE</version>
    </dependency>
    <!-- mysql连接 -->
    <dependency>
      <groupId>mysql</groupId>
      <artifactId>mysql-connector-java</artifactId>
      <version>5.1.27</version>
    </dependency>
    <!--数据库连接池druid-->
     <dependency>
         <groupId>com.alibaba</groupId>
         <artifactId>druid</artifactId>
         <version>1.2.5</version>
     </dependency>
```

**创建数据源文件dbconfig.properties**

```properties
jdbc.jdbcUrl=jdbc:mysql://192.168.16.2:10000/lihonghao?useUnicode=true&amp;characterEncoding=utf-8
jdbc.driver=com.mysql.jdbc.Driver
jdbc.user=root
jdbc.password=123456
```

**创建mybatis-config配置文件**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <!--取别名-->
    <typeAliases>
        <package name="com.lhh.curd.entity"/>
    </typeAliases>
</configuration>
```

**接下来，把mybatis配置到spring中**

配置数据源

```xml
    <context:property-placeholder location="classpath:dbconfig.properties"/>
    <bean id="pooledDataSource" class="com.alibaba.druid.pool.DruidDataSource">
        <property name="url" value="${jdbc.jdbcUrl}"></property>
        <property name="driverClassName" value="${jdbc.driver}"></property>
        <property name="username" value="${jdbc.user}"></property>
        <property name="password" value="${jdbc.password}"></property>
    </bean>
```

配置sqlSessionFactory

```xml
```

