### **一、Mybatis是什么**

mybatis是一个sql映射框架，提供了数据库的操作能力，增强的JDBC。

使用mybaits让开发人员集中精神写sql就可以了，不必关心connection,Statement,ResultSet的创建和销毁，sql的执行。

### 二、使用步骤

1、新建表格

2、加入maven的mybaits坐标，mysql驱动坐标

3、创建实体类

4、创建持久层dao接口，定义操作数据库的方法

5、创建一个mybatis使用的配置文件

​	叫做sql映射文件：写sql语句的。一般一个表一个sql映射文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org/DTD Mapper 3.0" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.lifeng.demo.mybatis.dao.IUserDao">
 
    <resultMap type="UserInfo" id="userData">
        <id property="id" column="f_id" />
        <result property="name" column="f_name" />
        <result property="birth" column="f_birth" />
        <result property="salary" column="f_salary" />
    </resultMap>
</mapper>
```



6、创建mybatis的主配置文件：

​	一个项目就一个主配置文件。

​	主配置文件提供了数据库的**连接信息**和映射文件的**位置信息**

7、创建使用mybatis类

​	通过mybatis访问数据库。

### 三、关键东西



### 四、面试题