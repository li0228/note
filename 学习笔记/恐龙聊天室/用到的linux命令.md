# 搭建各种环境用到的linux命令

## 一、防火墙

- 开放端口

  ```java
  firewall-cmd --zone=pulic --add-port=70/tcp --permanent
  ```

- 关闭端口

  ```java
  firewall-cmd --permanent --remove-port=70/tcp
  ```

- 查询端口是否开放

  ```java
  firewall-cmd --query-port=70/tcp
  ```

- 重启

  ```java
  firewall-cmd --reload
  ```