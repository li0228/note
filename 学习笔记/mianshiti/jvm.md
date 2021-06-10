# jvm

## 1、jvm参数

### 标准参数

只有一个：-

```
-version 
-help 
-server 
-cp
```

## -X参数

非标准参数，也就是在jdk各个版本中可能会变动

```
-Xint 解释执行
-Xcomp 第一次使用就编译成本地代码
-Xmixed 混合模式
-X:loggc:log/gc.log 生成gc日志
```

### -XX参数

这个参数是最常用的。

**主要用于JVM调优和Debug**

```
-Xms 1000 == -XX:InitialHeapSize=1000
// 设置初始堆大小

-Xmx 1000 == -XX:MaxHeapSize=1000
// 设置最大的堆大小

-Xss1000 ==  -XX:ThreadStackSize=100
// 设置每个线程的堆栈大小（这个一般不管他）

-Xmn100
// 设置年轻代大小

-XX:NewRatio=2
-XX:SurvivorRatio=8
// 表示Eden和service的比率是2/8

-XX:PermSize=100M
// 表示设置永久代的内存大小

-XX:MaxPermSize=100M 
// 表示设置永久代最大的内存大小

// 不过jdk1.8之后会永久代被移除了，使用下面的参数来配置
-XX:MetaspaceSize=100M  
-XX:MaxMetaspaceSize=100M 

-XX:MaxTenuringThreshold=15
// 设置经过15次YGC依然存活的对象，放到老年代里面


-XX:+printGC
-XX:+PrintGCDetails
// 打印GC信息

-XX:+HeapDumpOnOutOfMemoryError	
// 让jvm溢出时dump出内存快照，以便分析使用（生产环境必配）
-XX:HeapDumpPath=/xx/xx/xx.hprof
// 生成dump文件的路径，一般和上面的一起配

-XX:+PrintFlggsFinal
// 输出所有参数的名称和默认值
-XX:+PrintFlagsInitial
// 输出初始值

-XX:+PrintCommandLineFlags -version
// 打印默认的参数


-XX:NewRatio
// 指定新生代内存比例
```

## 2、jvm调优思路

### 2.1 工具

**top指令**：查看当前所有进程的使用情况，CPU占有率，内存使用情况，服务器负载状态等参数。除此之外它还是个交互命令，使用可参考[完全解读top](https://zhuanlan.zhihu.com/p/36995443)。
**jps**：与linux上的ps类似，用于查看有权访问的虚拟机的进程，可以查看本地运行着几个java程序，并显示他们的进程号。当未指定hostid时，默认查看本机jvm进程。
**jinfo**：可以输出并修改运行时的java 进程的一些参数。
**jstat**：可以用来监视jvm内存内的各种堆和非堆的大小及其内存使用量。
**jstack**：堆栈跟踪工具，一般用于查看某个进程包含线程的情况。
**jmap：**打印出某个java进程（使用pid）内存内的所有对象的情况。一般用于查看内存占用情况。
**jconsole**：一个java GUI监视工具，可以以图表化的形式显示各种数据。并可通过远程连接监视远程的服务器的jvm进程。

### 2.2 步骤

#### 1、监控GC的状态

后面在补上。。。。。。。。。。。。

## 3、jvm调优实战课程笔记

### （一）背景说明

#### 生产环境中的问题

1. 内存溢出
2. 服务器分配多少内存
3. 如何对辣鸡收集器的性能进行调优
4. 生成环境的CPU负载飙高如何处理
5. 生产环境应该分配多少线程合适
6. 不加log，如何确定请求是否执行了某一行的代码
7. 不加log，如何实时查看某个方法的入参和返回值

#### 为什么要调优？

1. 防止OOM
2. 解决OOM（报警系统）
3. 减少Full GC出现的频率（用户反馈）

#### 不同阶段的考虑

1. 上线前（测试的时候）
2. 项目运行阶段（监控环境）
3. 线上出现OOM

### （二）调优概述

#### 监控的依据

1. 运行日志
2. 异常堆栈
3. GC日志
4. 线程快照
5. 堆转储快照（dump)

#### 调优大方向

1. 合理编写代码
2. 充分并合理的使用硬件资源
3. 合理地进行jvm调优

### （三）性能优化的步骤

#### 性能监控

**无监控不调优。注意需要非强行或入侵的方式来收集或查看应用的性能数据**

1. GC频繁
2. cpu load过高
3. OOM
4. 内存泄漏
5. 死锁
6. 程序响应时间长

#### 性能分析

一般是在测试环境来做的。

- 打印GC日志，通过GCview或者GC easy来分析日志信息
- 命令行工具，jstack，jmap，jinfo等
- dump出堆文件，使用内存分析工具来分析文件
- 使用arhtas或者jconsole，jvisualvm来实时查看
- jstack查看堆栈信息

#### 性能调优

- 适当增加内存，根据业务选择辣鸡回收器
- 优化代码
- 增加机器，分散节点压力
- 合理设置线程池数量
- 使用中间件提高程序效率
- 其他---

### （四）性能评价/测试指标

1. **停顿时间（或响应时间）**

   一般关注平均响应时间

2. **吞吐量**

3. 并发数

4. 内存占用

5. 相互间的关系

### （五）JVM监控及诊断工具-命令行篇

#### 概述

使用数据说明问题，使用知识分析问题，使用工具处理问题

无监控、不调优！

#### jps

查看正在运行的java进程

```
jps
jps -v
jps -l
jps -m
```

#### jstat

查看jvm的统计信息（类加载，内存，垃圾收集）

```
// 类加载相关
jstat -class pid

// 垃圾回收相关
jstat -gc pid 1000 10
jstat -gcutil pid 1000 10（这个用得多）
加一个 -t 可以打印出时间
```

#### jinfo

实时查看和修改jvm配置的参数

```
jinfo -flags pid 
// 查看曾经赋值过的所有参数
jinfo -flag 具体参数 pid
// 查看具体参数的值

如果参数被标记为manageable的可以被实时修改
java -XX:PrintFlagsFinal -version |grep manageable
// 可以查看被标记为manageable的参数

例子：
jinfo -flag +PrintGCDetails 6006
// 修改参数

// 扩展
java -XX:+PrintFlagsInitial pid // 查看所有JVM参数启动的初始值
java -XX:+PrintFlagsFinal pid // 查看所有的JVM参数的最终值
java -XX:+PrintCommandLineFlags pid // 查看已经被用户或者jvm设置过的详细的参数
```

#### jmap

导出内存映像文件&内存使用情况

```
jmap -dump:format=b,file=dump.hprof pid
// 生成堆转储快照，可以使用参数配置出现OOM时自动导出dump文件
jmap -heap pid 
// 查看内存使用情况
jmap -histo pid 
// 查看对中对象的统计信息，包括类，实例数量和合计容量
```

#### jhat

查看堆转储快照文件的（一般不用这个，用visualvm或者jprofiler）

#### jstack

生成线程快照的作用：**可用于定位线程出现长时间停顿的原因**，如线程死锁、死循环、请求外部资源导致的长时间等待等问题。

重点关注：

- **死锁**
- **等待资源**

- **等待获取监视器**
- **阻塞**
- 执行中
- 暂停
- 对象等待中
- 停止

```
jstack pid
```

#### jcmd

缝合怪，不学

#### jstatd

远程监控（了解）

### （六）JVM监控及诊断工具-GUI篇

#### jconsole

#### visual VM

装上visualGC

这个比较重要。必会

#### jProfiler

#### Arthas

### （七）内存泄漏的八种情况

1. 静态集合类
2. 单例模式
3. 内部类持有外部类
4. 各种连接，如数据库连接、网络连接和IO连接
5. 变量不合理的作用域
6. 改变哈希值
7. 缓存泄漏
8. 监听器和回调

### （八）JVM运行时参数

#### Jvm参数选项类型

- 标准参数
- -X参数
- -XX参数

#### 添加JVM参数选项

。。。

#### 常用的JVM参数选项

**打印设置的XX选项及值**

```
-XX:+PrintCommandLineFlags 打印手动设置或者jvm设置的XX选项
-XX:+PrintFlagsInitial 打印初始的默认值
-XX:+PrintFlagsFinal 打印生效的参数（ 这个会打印所有，700多个）
-XX:+PrintVMOptions打印jvm的参数
```

**堆、栈、方法区等内存大小设置(面试）**

```
栈
-Xss128k //设置每个线程的栈大小为128k
等价于：-XX:ThreadStackSize=128k
(不怎么设置)

堆
-Xms4096m //等价于-XX:InitialHeapSize,设置堆的初始大小
-Xmx4096m //等价于-XX:MaxHeapSize，设置jvm最大内存大小
（一般上面两个设置一样）

-Xmn2g //设置年轻代的大小，等价于-XX:NewSize=2g
-XX:MaxNewSize // 年轻代最大值

-XX:SurvivorRatio=8 //设置年轻代中Eden区区一个Servivor区的比值
-XX:UseAdaptiveSizePolicy //自动选择各区大小（默认开启）
-XX:NewRatio // 设置年轻代和老年代的比值

-XX:PretenureSizeThreadshold=1024 //设置让大于此阈值的对象直接分配在老年代
-XX:MaxTenuringThreshold=15 //年龄

方法区
老年代
-XX:PermSize=256m
-XX:MaxPermSize=256m

元空间
-XX:MetaspeaceSize //初始空间大小
-XX:MaxMetaspaceSize //最大空间
```

**OutofMemory相关的值**

```
-XX:+HeapDumpOnoutOfMemoryErro //表示内存出现OOM时，把Heap转存到文件
-XX:HeapDumpPath=<path> //文件的存储路径
-XX:+HeapDumpBeforeFullGC //表示出现FullGc之前转储文件
-XX:OnOutMemoryError //制定一个可行性或者脚本路径，发生OOM时执行
```

**垃圾收集器相关**

```
-XX:+UserSeiral 
-XX:+UseParallel
-XX:+UseConconcMarkSweep
```

**GC日志相关选项**

```
-verbose：gc
-XX:+PrintGC
-XX:+PrintGCDetails
-XX:+PrintGCTimeStamps(不能单独使用)
-XX:+PrintGCDateStamps(不能单独使用)
```

**其他**

#### 通过java代码获取JVM参数

