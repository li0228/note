### 一、什么是JDBC

　JDBC是什么？JDBC英文名为：Java Data Base Connectivity(Java数据库连接)，官方解释它是Java编程语言和广泛的数据库之间独立于数据库的连接标准的Java API，根本上说JDBC是一种规范，它提供的接口，一套完整的，允许便捷式访问底层数据库。可以用JAVA来写不同类型的可执行文件：JAVA应用程序、JAVA Applets、Java Servlet、JSP等，不同的可执行文件都能通过JDBC访问数据库，又兼备存储的优势。简单说它就是JAVA与数据库的连接的桥梁或者插件，用JAVA代码就能操作数据库的增删改查、存储过程、事务等

### 二、JDBC使用步骤

#### 1、注册驱动

```java
String driver = "com.mysql.jdbc.Driver";
Class.forName(driver);
```

#### 2、获取连接

```java
// 获取连接
String url = "jdbc:mysql://192.168.16.2:10000/lihonghao";
String user = "root";
String password = "123456";
Connection connection = DriverManager.getConnection(url, user, password);
```

#### 3、获取数据库操作对象（执行sql语句）

```java
// 获取数据库操作对象
Statement stat = connection.createStatement();
```



#### 4、执行sql语句

```java
String sql = "delete from user where id = 3";
int i = stat.executeUpdate(sql);
```

#### 5、处理查询结果集

查询的是时候

```java
ResultSet
```

#### 6、释放资源

```java
if(statement != null){
	try {
		statement.close();
	}catch (SQLException e){
		e.printStackTrace();
	}
	try {
		conn.close();
	}catch (SQLException e){
		e.printStackTrace();
	}
 }
```

### 三、jdbc面试题

**1、JDBC访问数据库的基本步骤是什么？**

- 注册动
- 获取数据库连接
- 创建数据库操作对象
- 执行数据库操作
- 获取并操作结果集
- 关闭对象

**2、说说prepareStatement和Statement的区别**

- preparementStatement会预编译,statement每次都要编译所以prepareStatement效率高
- preparementStatement安全性高，因为可以防止sql注入

**3、说说事务的概念，在JDBC中处理事务的步骤。**

​	事务是作为单个逻辑工作单元执行的一系列操作，一个逻辑工作单元必须有四个属性：原子性、一致性、隔离性、持久性。才能称作一个事务

步骤：

- conn.setAutoComit(false);设置提交方式为手工提交
- conn.commit（）提交事务
- conn.rollback(),回滚
- 提交和回滚只能选择一个执行，正常提交事务，出现异常就回滚

**4、数据库连接池的原理，为什么要用数据库连接池。**

  	数据库连接是一种关键的有限的昂贵的资源，对数据库连接的管理能显著影响到整个应用程序的伸缩性和健壮性，影响到程序的性能指标。数据库连接池正是针对这个问题提出来的。

数据库连接池负责分配、管理和释放数据库连接，它允许应用程序重复使用一个现有的数据库连接，而不是重新建立一个；释放空闲时间超过最大空闲时间的数据库连接来避免因为没有释放数据库连接而引起的数据库连接遗漏。这项技术能明显提高对数据库操作的性能。

数据库连接池在初始化时将创建一定数量的数据库连接放到连接池中，这些数据库连接的数量是由最小数据库连接数来设定的。无论这些数据库连接是否被使用，连接池都将一直保证至少拥有这么多的连接数量。连接池的最大数据库连接数量限定了这个连接池能占有的最大连接数，当应用程序向连接池请求的连接数超过最大连接数量时，这些请求将被加入到等待队列中。

**5、JDBC的脏读是什么？哪种数据库隔离级别能防止脏读？**

​		当我们使用事务时，有可能会出现这样的情况，有一行数据刚更新，与此同时另一个查询读到了这个刚更新的值。这样就导致了脏读，因为更新的数据还没有进行持久化，更新这行数据的业务可能会进行回滚，这样这个数据就是无效的。这种情况就是脏读。

**6、什么是幻读，哪种隔离级别可以防止幻读？**

​		幻读是指一个事务多次执行一条查询返回的却是不同的值。假设一个事务正根据某个条件进行数据查询，然后另一个事务插入了一行满足这个查询条件的数据。之后这个事务再次执行了这条查询，返回的结果集中会包含刚插入的那条新数据。这行新数据被称为幻行，而这种现象就叫做幻读。

**7、JDBC的DriverManager是用来做什么的？**

​		JDBC的DriverManager是一个工厂类，我们通过它来创建数据库连接。当JDBC的Driver类被加载进来时，它会自己注册到DriverManager类里面，然后我们会把数据库配置信息传成DriverManager.getConnection()方法，DriverManager会使用注册到它里面的驱动来获取数据库连接，并返回给调用的程序。

**8、execute，executeQuery，executeUpdate的区别是什么？**

execute可以用来执行任意SQL语句，返回一个boolean的值，表明该语句是否返回了一个结果集对象ResultSet。

executeUpdate 用来执行修改，插入，删除操作，执行SELECT会抛出异常

executeQuery只能进行SELECT，从而得到结果集对象，DELETE,UPDATE,INSERT操作都会抛出异常

通常我们不使用execute，如果你不知道要执行的SQL语句是什么类型的，就可以使用execute。

**9、JDBC的ResultSet是什么？**

答：ResultSet，数据库结果集的数据表，通常通过执行查询数据库的语句生成。

ResultSet 对象具有指向其当前数据行的指针。最初，指针被置于第一行之前。next 方法将指针移动到下一行；因为该方法在 ResultSet 对象中没有下一行时返回 false，所以可以在 while 循环中使用它来迭代结果集。

![img](https://raw.githubusercontent.com/li0228/image/master/48540923dd54564e68f12f9dbb643f87d0584f3a.jpeg)