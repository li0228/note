## 一、FastDFS介绍

  FastDFS是一款类Google FS的开源分布式文件系统，它用纯C语言实现，支持Linux、FreeBSD、AIX等UNIX系统。它只能通过 专有API对文件进行存取访问，不支持POSIX接口方式，不能mount使用。准确地讲，Google FS以及FastDFS、mogileFS、 HDFS、TFS等类Google FS都不是系统级的分布式文件系统，而是应用级的分布式文件存储服务。    

   FastDFS 是一个开源的高性能分布式文件系统（DFS）。 它的主要功能包括：文件存储，文件同步和文件访问，以及高容量和负载平衡。主要解决了海量数据存储问题，特别适合以中小文件（建议范围：4KB < file_size <500MB）为载体的在线服务。

## 二、FastDFS架构

FastDFS架构包括 Tracker server和Storage server。客户端请求Tracker server进行文件上传、下载，通过Trackerserver调度最终由Storage server完成文件上传和下载。

FastDFS 系统有三个角色：跟踪服务器(Tracker Server)、存储服务器(Storage Server)和客户端(Client)。

　　**Tracker Server：**跟踪服务器，主要做调度工作，起到均衡的作用；负责管理所有的 storage server和 group，每个 storage 在启动后会连接 Tracker，告知自己所属 group 等信息，并保持周期性心跳。通过Trackerserver在文件上传时可以根据一些策略找到Storageserver提供文件上传服务。

　　**Storage Server：**存储服务器，主要提供容量和备份服务；以 group 为单位，每个 group 内可以有多台 storage server，数据互为备份。Storage server没有实现自己的文件系统而是利用操作系统 的文件系统来管理文件。

　　**Client：**客户端，上传下载数据的服务器，也就是我们自己的项目所部署在的服务器。

![img](https://imgconvert.csdnimg.cn/aHR0cHM6Ly9pbWFnZXMyMDE3LmNuYmxvZ3MuY29tL2Jsb2cvODU2MTU0LzIwMTcxMC84NTYxNTQtMjAxNzEwMTExNDQxNTM4NDAtMTE4NTE0MTkwMy5wbmc?x-oss-process=image/format,png)

### **FastDFS的上传过程**

FastDFS向使用者提供基本文件访问接口，比如upload、download、append、delete等，以客户端库的方式提供给用户使用。

Storage Server会定期的向Tracker Server发送自己的存储信息。当Tracker Server Cluster中的Tracker Server不止一个时，各个Tracker之间的关系是对等的，所以客户端上传时可以选择任意一个Tracker。

当Tracker收到客户端上传文件的请求时，会为该文件分配一个可以存储文件的group，当选定了group后就要决定给客户端分配group中的哪一个storage server。当分配好storage server后，客户端向storage发送写文件请求，storage将会为文件分配一个数据存储目录。然后为文件分配一个fileid，最后根据以上的信息生成文件名存储文件。

![img](https://images2017.cnblogs.com/blog/856154/201710/856154-20171012121639387-1574147926.png)

## 三、搭建步骤

