## 1、启动mycat连接数据库失败

连接mycat的时候无法到数据库

查看日志发现了这个

```shell
can't connect to mysql server ,errmsg:Client does not support authentication protocol requested by server; consider upgrading MySQL client MySQLConnectioncan't connect to mysql server ,errmsg:Client does not support authentication protocol requested by server; consider upgrading MySQL client MySQLConnection
```

主要就是说：

无法连接到mysql服务器，不支持服务器请求的认证协议；考虑升级MySQL客户端

**解决：**

通过进一步百度和查看mysql8.0.11的官方文档，发现可以通过变更数据库用户的默认登陆认证机制来规避。
于是将“root@%”这个用户的默认认证机制从” caching_sha2_password“变更为”mysql_native_password“。
参考如下：
ALTER USER 'root'@'%' IDENTIFIED WITH mysql_native_password BY 'xxxx';
FLUSH PRIVILEGES;
ALTER USER 'root'@'%' IDENTIFIED BY 'xxxx'; ##必须执行这个变更密码,否则会报错找不到caching_sha2_password模块
FLUSH PRIVILEGES;

变更之后，使用mysql5.7.20版本可以正常访问后端的mysql8.0.11数据库。将mycat重启之后，mycat无报错信息。

**参考连接：**

https://github.com/MyCATApache/Mycat-Server/issues/1899