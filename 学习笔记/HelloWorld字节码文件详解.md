# class文件详解

## 基本概念

### 1. class文件格式类型

1. 无符号数

   它以 u1、u2、u4、u8 六七分别代表 1 个字节、2 个字节、4 个字节、8 个字节的无符号数。无符号数可以用来描述数字、索引引用、数量值或者按照 UTF-8 编码构成的字符串值。

2. 表

   **表是由多个无符号数或者其他表作为数据项构成的复合数据类型。**所有表都习惯性地以`_info`结尾。

### 1.class文件的所有组成部分

![img](https://raw.githubusercontent.com/li0228/image/master/595137-20181219204230458-43856381.png)

## java源文件

```
package com.lhh;

/**
 * @author lihonghao
 * @date 2021/1/6 17:01
 */
public class TestJvm {
   public static void main(String[] args) {
      System.out.println("hello world");
   }
}
```

## Class文件

使用javac把上面的源文件编译一下，生成TestJvm.calss。然后使用notepad++打开（需要安装插件），看到下面这堆东西。

![image-20210107144523628](https://raw.githubusercontent.com/li0228/image/master/image-20210107144523628.png)

## 逐字节解读

根据《java虚拟机规范 SE8.0》版的描述，为了方便理解。我们可以手动把这个calss文件分成下面七部分。

- 魔数与Class文件版本
- 常量池
- 访问标志
- 类索引、父类索引、接口索引
- 字段表集合
- 方法表集合
- 属性表集合

事实上我们只需要从这个文件中准确找出这七个部分，并且知道这些部分是代表什么意思就好了。

### 魔数和Class文件版本

- [1 - 4] - 魔数。这个是固定不变的。值为cafebabe,如果前四个字节不是这个值，java虚拟机会拒绝这个文件。

- [5 - 6] - 次版本号。(Minor Version）；文件里面的值为 00。转成是静止为0

- [7 - 8] - 主版本号。(Major Version）；文件里面的值为 34。转成十进制为52

  可以知道编译这个class文件的版本号为52.00。下图是主次版本号和JDK版本的对应表。

  ![img](https://raw.githubusercontent.com/li0228/image/master/595137-20181219204254298-373095239.png)

## 常量池

接下来是常量池部分。这里我们区分的部分事实上在calss文件是没有什么分隔符来区分的，所有的部分都是紧挨在一起的。

根据《java虚拟机规范》可以知道，常量池是由两个部分组成的:一、两个字节表示常量个数;二、不定长的常量数据。

![image-20210107151518694](https://raw.githubusercontent.com/li0228/image/master/image-20210107151518694.png)

### constant_pool[]的格式是这样的：

![image-20210107152307603](https://raw.githubusercontent.com/li0228/image/master/image-20210107152307603.png)

### tag的类型

主要就是为了区分常量的类型，一个字节。如下：

| 常量类型                    | 值   | 描述                     |
| --------------------------- | ---- | ------------------------ |
| CONSTANT_Class              | 7    | 类或者接口的符号引用     |
| CONSTANT_Fileldref          | 9    | 字段的符号引用           |
| CONSTANT_Methodref          | 10   | 类中方法的符号引用       |
| CONSTANT_InterfaceMethodref | 11   | 接口中方法的符号引用     |
| CONSTANT_String             | 8    | 字符串类型字面量         |
| CONSTANT_Integer            | 3    | 整形字面量               |
| CONSTANT_Float              | 4    | 浮点型字面量             |
| CONSTANT_Long               | 5    | 长整型字面量             |
| CONSTANT_Double             | 6    | 双精度浮点型字面量       |
| CONSTANT_NameAndType        | 12   | 字段或方法的部分符号引用 |
| CONSTANT_utf8               | 1    | Utf-8字面量              |
| CONSTANT_MethodHandle       | 15   | 表示方法句柄             |
| CONSTANT_MethodType         | 16   | 标识方法类型             |
| CONSTANT_InbokeDynamic      | 18   | 表示一个动态方法调用点   |

### Info[]数据

每种类型的项是不一样的，所有的类型的项如下：

![img](https://raw.githubusercontent.com/li0228/image/master/595137-20181219204338051-305022474.png)

### 举例分析

#### 常量池计数器（CONSTANT_POOL_COUNT）

![image-20210107161441657](https://raw.githubusercontent.com/li0228/image/master/image-20210107161441657.png)

0x0022转为二进制：34 。可以得出：一共有34个常量。

#### 常量池（CONSTANT_POOL[])

接下来就是每一项每一项来对比了。

- 第一个常量

  - **tag:0a** 

    转为十进制等于**10**，查tag表可知类型为：CONSTANT_Methodref（类方法符号引用）

    ![image-20210107163152147](https://raw.githubusercontent.com/li0228/image/master/image-20210107163152147.png)

  - **info**

    tag为10的常量结构如图：

    ![image-20210107163706466](https://raw.githubusercontent.com/li0228/image/master/image-20210107163706466.png)

    第二项：指向声明方法的描述符的索引值（类名），第三项：指向名称及类型描述符的索引值（方法名）

    接下来拿出后面四个字节

    ![image-20210107164357764](https://raw.githubusercontent.com/li0228/image/master/image-20210107164357764.png)

    转成十进制，分别是6和20。

    通过javap命令看一下class文件信息，将这两者组合起来就是：`java/lang/Object.<init>:V`，即 Object 的 init 初始化方法。

    ![image-20210107164750857](https://raw.githubusercontent.com/li0228/image/master/image-20210107164750857.png)

    接下来就是剩下的33个常量了

- 第二个常量

  ![image-20210107175939424](https://raw.githubusercontent.com/li0228/image/master/image-20210107175939424.png)

  - tag值为9

  ![image-20210107175920706](https://raw.githubusercontent.com/li0228/image/master/image-20210107175920706.png)

