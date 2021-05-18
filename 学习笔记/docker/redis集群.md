# redis集群

模型：分片+高可用+负载均衡（三主三从）

## 步骤

###  1、创建网卡

```shell
#命令：docker network create redis(网卡名) --subnet 172.38.0.0/16

[root@localhost home]# docker network create redis --subnet 172.38.0.0/16
e1899b83d82fe37b264aa64a00a9f92cea9e50ab7fc2fe8bf6b7ac09ff2927d7
[root@localhost home]# docker network ls
NETWORK ID     NAME      DRIVER    SCOPE
a8e5e02cde10   bridge    bridge    local
da5bf154c84d   host      host      local
e5be89d5ff23   none      null      local
e1899b83d82f   redis     bridge    local
```

### 2、创建redis配置

```shell
for port in $(seq 1 6);\
do \
mkdir -p /mydata/redis/node-${port}/conf
touch /mydata/redis/node-${port}/conf/redis.conf
cat << EOF >/mydata/redis/node-${port}/conf/redis.conf
port 6379
bind 0.0.0.0
cluster-enabled yes
cluster-config-file nodes.conf
cluster-node-timeout 5000
cluster-announce-ip 172.38.0.1${port}
cluster-announce-port 6379
cluster-anoounce-bus-port 16379
appendonly yes
EOF
done
```

生成六个节点配置文件

```shell
[root@localhost redis]# pwd
/mydata/redis
[root@localhost redis]# ls
node-1  node-2  node-3  node-4  node-5  node-6
[root@localhost redis]# 

```

### 3、启动redis

```shell
docker run -p 6371:6379 -p 16371:16379 --name redis-1 \
-v /mydata/redis/node-1/data:/data \
-v /mydata/redis/node-1/conf/redis.conf:/etc/redis/redis.conf \
-d --net redis --ip 172.38.0.11 redis:5.0.9-alpine3.11 redis-server /etc/redis/redis.conf
```

