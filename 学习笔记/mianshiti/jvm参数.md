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
// 设置每个线程的堆栈大小

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
-XX:MetaspaceSize 
-XX:MaxMetaspaceSize

-XX:MaxTenuringThreshold=15
// 设置经过15次YGC依然存活的对象，放到老年代里面


-XX:+printGC
-XX:+PrintGCDetails
// 打印GC信息

-XX:+HeapDumpOnOutOfMemoryError	
// 让jvm溢出时dump出内存快照，以便分析使用
```







