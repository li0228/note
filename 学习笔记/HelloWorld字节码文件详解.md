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

#### 常量池（CONSTANT_POOL)

接下来就是每一项每一项来对比了。

- 第一个常量

  **tag:0a** 

  转为十进制等于**10**，查tag表可知类型为：CONSTANT_Methodref（类方法符号引用）

  ![image-20210107163152147](https://raw.githubusercontent.com/li0228/image/master/image-20210107163152147.png)**info**

  tag为10的常量结构如图：

  ![image-20210107163706466](https://raw.githubusercontent.com/li0228/image/master/image-20210107163706466.png)

  第二项：指向声明方法的描述符的索引值（类名），第三项：指向名称及类型描述符的索引值（方法名）

  接下来拿出后面四个字节

  ![image-20210107164357764](https://raw.githubusercontent.com/li0228/image/master/image-20210107164357764.png)

  转成十进制，分别是6和20。

  通过javap命令看一下class文件信息，将这两者组合起来就是：`java/lang/Object.<init>:V`，即 Object 的 init 初始化方法。

  ![image-20210107164750857](https://raw.githubusercontent.com/li0228/image/master/image-20210107164750857.png)

  接下来就是剩下的33个常量了

- **第二个常量**

  ![image-20210107175939424](https://raw.githubusercontent.com/li0228/image/master/image-20210107175939424.png)

  **tag:09**

  对照表格可知知道该常量为字段的符号引用**

  **info **

  查表格可知该结构如下：

  ![image-20210107175920706](https://raw.githubusercontent.com/li0228/image/master/image-20210107175920706.png)

  拿出后面的四个字节：00 15 和 00 16，转成十进制：21和22

  // TODO 这里贴个图

  21指向28 对应：class：System

  22指向29和30 对应：out 和L:java/io/printstream

- **第三个常量**

  **tag:08**

  字面量索引

  **info**

  拿出后面两个字节：00 17 ；转成十进制：23

  23：对应是是utf8类型。值为“hello world”

- **第四个常量**

  **tag:0a -> 10**

  方法索引

  **info**

  拿出后面的四个字节 0018和0019，转成十进制：24和25

  24：指向31。对应：java/io/printStram

  25：指向32和33。对应println和V（java/lang/String）

  说明这是一个方法。println(String)

- **第五个常量****

  **tag:07**

  全限定名常量索引

  **info**

  拿出后面两个字节：001a -> 26

  26：com/test/TestJvm

- **第六个常量**

  **tag:07**

  全限定名常量索引

  **info**

  001b -> 27 : java/lang/Object

- **第七个常量**
  数据为：01  00 06  3c  69 6e 69 74 3e  。它是一个字符串常量，转换之后是：<init>。

- **第八个常量**

  数据为：01 00 03 28 29 56 。字符串常量，转换之后：（）V

- **第九个常量**

  ![image-20210107161441657](https://raw.githubusercontent.com/li0228/image/master/image-20210107161441657.png)

  数据为：01 00 04 43 6f 64 65。字符串常量，转换之后：

- **第十个常量**
  数据为：01 00 0f 4c 69 6e 65 4e 75 6d 62 65 72 54 61 62 6c 65。字符串常量，转换之后：

- **第十一个常量**

  数据为：01 00 12 4c 6f 63 61 6c 56 61 72 69 61 62 6c 65 54 61 62 6c 65。字符串常量，转换之后：LocalVariableTable

- **第十二个常量**

  数据为：01 00 04 74 68 69 73。字符串常量，转换之后

- **第十三个常量**

  数据为：01 00 11 4c 63 6f 6d 2f 6c 68 68 2f 54 65 73 74 4a 76 6d 3b。字符串常量，转换之后：

- **第十四个常量**

  数据为：01 00 04 6d 61 69 6e。字符串常量，转换之后：

- **第十五个常量**

  数据为：01 00 16 28 5b 4c 6a 61 76 61 2f 6c 61 6e 67 2f 53 74 72 69 6e 67 3b 29 56。字符串常量，转换之后：

- **第十六个常量**

  数据为：01 00 04 61 72 67 73

- **第十七个常量**

  数据为：01 00 13 5b 4c 6a 61 76 61 2f 6c 61 6e 67 2f 53 74 72 69 3e 67 3b

- **第十八个常量**

  数据为：01 00 0a 53 6f 75 72 63 65 46 69 3c 65 

- **第十九个常量**

  数据为：01 00 0c 54 65 73 74 4a 76 6d 2e 6a 61 76 61 

- **第二十个常量**

  **tag:0C -> 12**

  字段或方法的部分符号引用

  **info**

  00 07 -> 7 :<init>（方法名）

  00 08 -> 8 :()V（返回描述符）

- **第二十一个常量**

  **tag:07 -> 07**

  类或者接口的符号引用

  **info**

  00 1c -> 28:指向28，表示 java/lang/System

- **第二十二个常量**

  **tag:0c -> 12**

  字段或方法的部分符号引用

  **info**

  00 1d -> 29:指向29，表示 out

  00 1e -> 30:指向30，表示 L java/io/PringStream

- **第二十三个常量**

  数据为：01 00 0b 68 65 6c 6c 6f 20 77 6f 72 6c 64,转换为：hello world

- **第二十四个常量**

  **tag:07 -> 7**

  类或者接口的符号引用

  **info**

  00 1f ->31：指向31，表示java/io/PringStream

- **第二十五个常量**

  **tag:0c -> 12**

  字段或方法的部分符号引用

  **info**

  00 20 ->32：指向31，表示:println

  00 21 ->33：指向33，表示:(L java/lang/String;)V

- **第二十六个常量**

  数据为：01 00 0f 63 6f 6d 2f 6c 68 68 2f 54 65 73 74 4a 76 6d 

- **第二十七个常量**

  数据为：01 00 10 6a 61 76 61 2f 6c 61 6e 67 2f 4f 62 6a 65 63 74 

- **第二十八个常量**

  数据为：01 00 10 6a 61 76 61 2f 6c 61 6e 67 2f 53 79 73 74 64 6d 

- **第二十九个常量**

  数据为：01 00 03 6f 75 74

- **第三十个常量**

  数据为：01 00 15 4c 6a 61 76 61 2f 69 6f 2f 20 72 69 6e 74 53 74 72 65 61 6d 3b 

- **第三十一个常量**

  数据为：01 00 13 6a 61 76 61 2f 69 6f 2f 50 82 69 6e 74 53 74 72 65 61 6d 

- **第三十二个常量**

  数据为：01 00 07 70 72 69 6e 74 6c 6e 

- **第三十三个常量**

  数据为：01 00 15 28 4c 6a 61 76 61 2f 6c 61 6e 67 2f 53 74 72 69 6e 67 3b 29 56 

#### 访问标志

常量池结束了之后，就是两个字节的访问标志符号

![image-20210112161417964](https://raw.githubusercontent.com/li0228/image/master/image-20210112161417964.png)

根据文件我可能可以得出他的值为：00 21

![image-20210112163155138](https://raw.githubusercontent.com/li0228/image/master/image-20210112163155138.png)

在这里这两个字节是 00 21，通过查看我们并没有发现有标志值是 00 21 的标志名称。这是因为这里的访问标志可能是由多个标志名称组成的，所以字节码文件中的标志值其实是多个值进行或运算的结果。

通过查阅上述表格，我们可以知道，00 21 由 00 01（第1行）和 00 20（第3行）进行或运算得来。也就是说该类的访问标志是 public 并且允许使用 invokespecial 字节码指令的新语义

#### 类索引、父类索引、接口索引

接下来该轮到类索引、父类索引和接口索引了。所占的文件大小如下：

![image-20210112163438090](https://raw.githubusercontent.com/li0228/image/master/image-20210112163438090.png)

接口内容是一个表。格式必须为CONSTANT_Class_infos

![image-20210112163731130](https://raw.githubusercontent.com/li0228/image/master/image-20210112163731130.png)

回到文件中

**类索引：**00 05;对照常量池，可以知道该索引是：**com/test/TestJvm**

**父类索引：**00 06 （因为java是单继承的，所有父类索引只有一个），查常量池，知：**java/lang/Object**

**接口索引数量：**00 00 

**接口内容：**如果该类没有实现任何接口，则该计数器值为0，后面接口的索引表不再占用任何字节。

#### 字段表集合

再往下就是字段了。先看一下需要哪些结构：

![image-20210112164853224](https://raw.githubusercontent.com/li0228/image/master/image-20210112164853224.png)

其中field_info的结构如下：

![image-20210112164540536](https://raw.githubusercontent.com/li0228/image/master/image-20210112164540536.png)

根据《java虚拟机规范 1.8版》，我们可以知道这些组成部分代表的意义：

| 类型           | 名称             | 含义             |
| -------------- | ---------------- | ---------------- |
| u2             | access_flags     | 字段访问标识     |
| u2             | name_index       | 字段名称索引项   |
| u2             | descriptor_index | 字段描述符索引项 |
| u2             | attributes_count | 属性计数器       |
| attribute_info | attributes       | 属性表           |

同时，关于字段访问权限和属性的各个标志如下：

![image-20210112165612878](https://raw.githubusercontent.com/li0228/image/master/image-20210112165612878.png)

**回到本例子中，因为我们并没有声明任何的类成员变量或类变量，所以在 Demo 的字节码文件中，字段计数器为 00 00，表示没有属性字段。**

![image-20210112165753129](https://raw.githubusercontent.com/li0228/image/master/image-20210112165753129.png)

#### 方法表集合

接下来就是方法表集合了，先看下结构：

![image-20210112170325383](https://raw.githubusercontent.com/li0228/image/master/image-20210112170325383.png)

method_info的结构如下：

![image-20210112170425047](https://raw.githubusercontent.com/li0228/image/master/image-20210112170425047.png)

根据《java虚拟机规范 1.8版》，我们可以知道这些组成部分代表的意义：

| 类型           | 名称             | 含义             |
| -------------- | ---------------- | ---------------- |
| u2             | access_flags     | 方法访问标识     |
| u2             | name_index       | 方法名称索引项   |
| u2             | descriptor_index | 方法描述符索引项 |
| u2             | attributes_count | 属性计数器       |
| attribute_info | attributes       | 属性表           |



![image-20210112171748383](https://raw.githubusercontent.com/li0228/image/master/image-20210112171748383.png)

**回到例子**

拿出随后的两个字节：00 02 。代表是有两个方法的；

**第一个方法：**

拿出数据

**access_flags：**00 01。根据下面的表格可以知道1对应的是public方法。

**name_index：**00 07。查常量池：<init>

**descriptor_index：**00 08 。根据常量池拿到数据：()V

**attributes_count：** 00 01 。一个属性

属性表格式：

![img](https://raw.githubusercontent.com/li0228/image/master/595137-20181219204633860-609734053.png)

**拿出属性数据：**

**attribute_name_index:** 00 09。查常量池可以知道是：code

**attribute_length:** 00 00 00 2f。属性长度，转成十进制：47

**info:**

code属性出现的位置是method_Info

先看格式。

![image-20210112174251620](https://raw.githubusercontent.com/li0228/image/master/image-20210112174251620.png)

看下各项说明：

| 类型           | 名称                   | 含义                               |
| -------------- | ---------------------- | ---------------------------------- |
| u2             | attribute_name_index   | code                               |
| u4             | attribute_length       | 当前属性的长度                     |
| u2             | max_stack              | 操作数栈深度的最大值               |
| u2             | max_locals             | 局部变量分配内存所使用的最小内存   |
| u4             | code_length            | 生成字节码长度                     |
| u1             | code                   | 实现当前方法的虚拟机的实际字节内容 |
| u2             | exception_table_length | exception_table表的成员个数        |
| exception_info | exception_table        | 异常处理器表格                     |
| u2             | attributes_count       | 属性表成员个数                     |
| attribute_info | attributes             | 属性表                             |

根据  attribute_length拿出后面的47个字节

**00 01** 00 01 **00 00 00 05** 2a b7 00 01 b1 **00 00** 00 02 **00  0a** 00 00 00 06 **00 01** 00 00 **00 07** 00 0b 00 00 00 0c 00 01 00 00 00 05 00 0c 00 0d 00 00

后面的内容从第三项：max_stack开始看。

**max_stack**：00 01

**max_locals**：00 01

**code_length**：00 00 00 05

**code**：2a b7 00 01 b1 ，这串数值是字节码指令

**exception_table_length**：00 00

**exception_table**：异常表长度为0，所以没有

**attributes_count**：00 02 。表示有两个属性。

attribute_info 属性表的表结构如下。

![fdafsdfsdafefe](https://raw.githubusercontent.com/li0228/image/master/fdafsdfsdafefe.png)

**第一个属性：**

前面两字节表示属性名称索引：00 0a->10。查阅可知：LineNumberTable

结构为：

![image-20210112200209400](https://raw.githubusercontent.com/li0228/image/master/image-20210112200209400.png)

**attribute_length**(属性长度):00 00 00 06。表示有六个字节

<img src="https://raw.githubusercontent.com/li0228/image/master/image-20210112203116877.png" alt="image-20210112203116877" style="zoom: 67%;" />

**line_number_table_length**(长度)：00 01 

**line_number_info**：结构如下

![img](https://raw.githubusercontent.com/li0228/image/master/595137-20181219204657031-528245055.png)

stsrt_Pc: 00 00 代表字节码行号

line_number：00 07；代表源代码行号

**第二个属性：**

用表格来比较直观一点，分析的话跟上面一个属性一样。

| 属性                 | 值          | 含义                           |
| -------------------- | ----------- | ------------------------------ |
| attribute_name_index | 00 0b -> 11 | 查常量池表：LocalVariableTable |
|                      |             |                                |
|                      |             |                                |

LocalVariableTable格式：

![image-20210113160147291](https://raw.githubusercontent.com/li0228/image/master/image-20210113160147291.png)