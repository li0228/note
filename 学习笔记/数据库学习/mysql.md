# mysql高级

## mysql配置文件

1. **二进制日志log-bin**
   - 用于主从复制
2. **错误日志log-error**
   - 默认是关闭的，记录警告和错误信息
3. **查询日志log**
   - 默认关闭，记录查询的日志
4. **数据文件**
   - /var/lib/mysql
   - frm文件：存放表结构
   - myd文件：存放表数据
   - myi文件：存放表索引
5. **如何配置**
   - windows - my.ini文件
   - linux - /etc/my.conf文件

## mysql架构

- 连接层
- 服务层
- 引擎层
- 存储层

## mysql存储引擎

![image-20210624202307034](https://raw.githubusercontent.com/li0228/image/master/image-20210624202307034.png)

## 索引优化

### 性能下降sql慢

- 查询语句写的烂
- 索引失效
  - 单值
  - 复合
- 关联查询太多join（分库分表不能太多的join）
- 服务器调优及几个参数设置（缓冲、线程数等，不重要，一般都是默认）

### 常见通用的join查询

- SQL执行顺序
  - 手写：select xx from xx join xx on xx where xx group by xx having xx order by xx;
  - 机读：FROM -> ON ->JOIN ->WHERE -> GROUP BY ->HAVING->SELECT->DISTINCT->)RDER BY
- Join图
- 建表SQL
- 七种JOIN

### 索引简介

- 是什么

  索引是帮助Mysql高效获取数据的结构。可以简单的理解为：“排好序的快速查找的数据结构”

- 优势 

- 劣势

- mysql索引分类

  - 单值索引：一个索引只包含单个列，一个表可以又多个单列索引
  - 唯一索引：索引列的值必须唯一，但允许有空值
  - 复合索引：一个索引包含多个列
  - 基本语法：
    - 创建：CREATE[UNIQUE] INDEX indexName ON mytable(columnname(length))
    - ALTER mytable ADD [UNIQUE] INDEX [indexName] ON *columnname(length))
    - 删除：DROP INDEX [indexname] ON mytable
    - 查看：SHOW INDEX FROM table_name\G

- mysql索引结构

  - **B+树（待扩展）**
  - **B+树（待扩展）**
  - **B+树（待扩展）**
  - **B+树（待扩展）**
  - **B+树（待扩展）**

- 哪些情况需要创建索引

  - 主键自动建立唯一索引
  - 频繁作为查询条件的字段应该创建索引
  - 查询中与其他表关联的字段，外键关系建立索引
  - 频繁更新的字段不适合创建索引
  - where条件里用不到的不创建索引
  - 高并发倾向于创建组合索引
  - 查询中排序的字段，排序字段若通过索引去访问将大大提高排序的速度
  - 查询中统计或者分组字段

- 哪些情况不要创建索引

  - 表记录太少
  - 经常增删改的文件
  - 数据重复切分布平均的表字段

### 性能分析

- **MySql Query Optimizer（mysql查询优化器）**
- **mysql常见瓶颈**
  - CPU：Cpu在饱和的时候一般发生在数据装入内存huo从磁盘读取数据的时候
  - IO：磁盘I/O瓶颈发生在装入数据远大于内存容量的时候
  - 服务器硬件性能瓶颈：top，free，iostat和vmstat来查看系统的性能状态
- **Explain（重点）**
  - 是什么（查看执行计划）
    - 使用explain关键字可以模拟优化器执行sql查询语句，从而知道mysql是如何处理你的sql语句的。分析你的查询语句或是表结构瓶颈
    
  - 能干嘛
    - **表的读取顺序（根据id来判断）**
    - **数据读取操作的操作类型（通过select_type来判断）**
    - 哪些索引可以使用
    - 哪些索引被实际使用
    - 表之间的引用
    - 每张表有多少行被优化器查询
    
  - 怎么玩
    -  **EXPLAIN+SELECT**
    - 执行计划包含的信息
    
  - **各字段解释**
    - **id**	
      - **select查询的序列号，包含一组数字，表示查询中select子句 或者表的顺序**
      - **三种情况**
        - id相同，执行顺序由上至下
        - id不同，如果是子查询，id的序号会递增，id值越高优先级越高，越先被执行
        - id不同，同时存在。先执行优先级高的，在依次执行。
    - select_type
      - **有哪些**
        - **SIMPLE**：简单的select查询，查询中不包括子查询或者UNION
        - **PRIMARY**：查询中若包含任何复杂的子部分，最外层查询则被标记为**PRIMARY**
        - **SUBQUERY: 在select或where列表中包含了子查询**（子查询括号里面的）
        - DERIVED：在FROM列表中包含的子查询被标记为DERIVED（衍生）MySQL会递归执行这些子查询，把结果放在临时表中
        - **UNION**：若第二个SELECT出现在UNION之后，则被标记为UNION；若UNION包含在FROM子句的子查询中，外出SELECT将被标记为：DERIVED
        - **UNION RESULT**：从UNION表获取结果的SELECT(两种UNION的合并)
    - table：操作哪个表
    - **type**
      - 访问类型排列
      - **显示查询用了哪种类型**
      - 从最好到最差依次是
        - system:表只有一行记录（等于系统表），这是const类型的特例，平时不会出现，这个也可以忽略不计
        - **const：表示通过索引一次就找到了，const用于比较primary 可以或者unique索引。因为只匹配一行数据，所以很快。如将主键至于where列表中，mysql就能将该查询转换为一个常量**
        - eq_ref：唯一索引扫描，对于每个索引键，表中只有一条记录与之匹配。常见于主键或者唯一索引扫描。
        - **ref：非唯一性索引，返回匹配某个单独值的左右行，本质也是一种索引访问，它返回所有匹配某个当度值的行，然而，它可能会找到多个符合条件的行，所以它应该术语查找和扫描的混合体**
        - **range**：**只检索给定范围的行，使用一个索引来选择行。key列显示使用了那个索引。一般就是你的where语句中出现了between、<、>、in等的查询，这种范围扫描索引比全表扫描摇号，因为它只需要开始于索引的某一点而结束于索引的另一点，不用扫描全部索引。**
        - **index：index与All的区别为index类型只遍历索引树。这通常比ALL快，因为索引文件通常比数据文件小，但是index是从索引中读取的，而all是从硬盘中读的**
        - ALL：扫描全表（性能最差，尽量不要出现）
    - possible_keys：显示可能应用在这张表的索引，一个或多个。查询涉及的字段若存在索引，则该索引被列出，**但不一定被查询实际使用。**
    - **key**：真实使用到的索引。如果为null。证明没有使用索引。**若查询中使用了覆盖索引，则该索引仅出现在key列表中。**
    - key_len：表示索引中使用的字节数，可通过该列计算查询中使用的索引的长度。在不损失精确性的情况下，长度越短越好。key_len显示的值为索引字段的最大可能长度，**并非实际使用长度**，即key_len是根据表定义计算而得的，不是通过表内检索出的。
    - ref：显示索引的那一列被使用了，如果可能的话，是一个常数。那些列或常量被用于查找索引列的值。
    - **rows： 根据表统计信息及索引选用情况，大致估算出找到所需记录所需要读取的行数**
    - **Extra：**
      - **Usingfilesort**：说明mysql会对数据使用一个外部的索引排序，而不是按照表内的索引顺序进行读取。Mysql中无法龙索引完成的排序操作称为“文件排序”。（**出现这个很危险，九死一生**）
      - **Using temporary:**使用了临时表保存中间结果，MySQL在对查询结果排序时使用临时表。常见于order by和分组查询group by（**火烧眉毛了，赶紧优化**）
      - **Using index：**表示响应的select操作中使用了覆盖索引，避免访问了表的数据行，效率不错！如果同时出现Using where，表明这个索引被用来窒息感索引键值的查找；如果没有同时出现using where，表面索引来读取数据而非执行查找动作。（**发财**）
      - Using where :使用到where
      - using join buffer
      - impossible where :where子句的值总是false，不能用来获取任何元组
      - select tables optimized away
      - distinct

  - **热身case** 

    ![image-20210618211955503](https://raw.githubusercontent.com/li0228/image/master/image-20210618211955503.png)

    执行顺序：

    1. （select name,id from t2）
    2.   (select id,name from t1 where other_colum = "")因为在from里面，所以select_type是**derived(派生)**。
    3. (select id from t3)
    4. select name,id from t2

### 索引优化  

#### 索引分析

**尽可能减少Join语句中的NestedLoop循环总次数：“永远用小结果集驱动大的结果集” -- 小表驱动大表。**

**保证Join语句中被驱动上的join条件字段已经被索引。**

#### 索引失效（应该避免）

1. **全值匹配我最爱**

   引按照字段查询的顺序来建立符合索引。

2. **最佳左前缀法则**：如果索引多列，要遵守最左前缀法则。指的是查询从索引的最前列开始并且不跳过索引中的列。

3. **不要在索引上做任何操作（计算、函数、（自动或者手动）类型转换），会导致索引失效二转向全表扫描**

4. **存储引擎不能使用索引中范围条件右边的列**（范围之后全失效）

5. **尽量使用覆盖索引（只访问索引的查询（索引列和查询列一致))，减少select***

6. **mysql在使用不等于（！=或者<>)的时候无法索引会导致全表扫描 **

7. **is null ,is not null 也无法使用索引**

8. **like以通配符开头（‘%abc..‘）会导致索引失效**

   问题：解决like’%字符串%'时索引失效的方法？？

   - 使用覆盖索引来解决（推荐   ）。

9. **字符串不加单引号索引失效（类型转换）**

10. **少用or，用它来连接时会导致索引失效**

#### 一般性建议

- 对于单值索引，尽量选择针对当前查询过滤性更好的索引。
- 在选择组合索引的时候，当前过滤性最好的字段在索引的循序中，位置越靠前越好。
- 在选择索引的时候，尽量选择可以**包含当前的查询中的where子句中更多**的索引。
- 尽可能通过缝隙统计信息和调整查询的写法来达到选择合适索引的目的。

## 查询截取分析

### 分析过程

1. 观察，至少跑一天，看看生产的慢SQL情况
2. **开启慢查询日志，设置阈值，比如超过5秒钟的就是慢SQL，并抓取出来**
3. **EXPLAIN+慢SQL分析**
4. **show profile**
5. 运维经理 or DBA，进行数据库服务器参数调优

### 查询优化

1. **永远小表驱动大表**

2. **order by 关键字优化**

   - Mysql支持两种方式的排序，fileSort和index，index效率高。它指MySQL扫描索引本身完成排序。FIleSort方式效率低
   - ORDER BY满足两种情况会使用Index排序：
     - ORDER BY语句使用索引最左前列
     - 使用where子句与order by子句条件列满足索引最左前列

   ![image-20210622103554878](https://raw.githubusercontent.com/li0228/image/master/image-20210622103554878.png)

3. **group by关键字优化**

   - 跟order by 差不多

   - where 高于having，能在where限定的条件不要去having限定

### 慢查询日志

1. **是什么**

   mysql提供的一种日志记录，如果mysql的响应时间超过设置好的long_query_time。则会被记录到慢查询日志中。默认是关闭的。

2. **怎么玩**

   - **说明**

     **默认是没有开启的，需要手动设置，不是调优的话不建议开启，因为会影响到性能。**

   - **查看如何开启及开启**

     ```sql
      **SHOW VARIABLES LIKE ‘%slow_query_log%'**
     ```

     开启和关闭

     ```sql
     set global slow_query_log=1;#一次行开启，mysql重启之后会失效
     ```

   - **开启之后什么样的sql会被记录到慢查询日志中？**

     这个是由long_query_time控制，默认情况下long_query_time的值为10秒

     ```sql
     SHOW VARIABLES LIKE 'long_query_time%';
     ```

     可以使用命令修改，也可以在my.cnf参数里面修改。

     **加入运行时间正好等于long_query_time，并不会被记录下来。**

3. Case

   1. 查看多少秒算慢

      ```sql
      SHOW VARIABLES LIKE 'long_query_time%';
      ```

   2. 设置慢的阈值时间

      ```sql
      set global long_query_time=3;
      ```

   3. 为什么设置后看不出变化

      **需要重新连接**

   4. 记录慢sql并后续分析

   5. 查询当前系统中有多少慢查询记录

      ```sql 
      show global status like '%Slow_queries%';
      ```

4. **日志分析工具mysqldumpslow**

   1. 查看mysqldumpslow的帮助信息
   2. 工作常用参考

### 批量数据脚本

往表里插入1000W条数据

1. 建表

   ```sql
   #1 建表dept
   CREATE TABLE `dept`  (
     `id` int(10) UNSIGNED NOT NULL,
     `deptno` mediumint(8) UNSIGNED NOT NULL DEFAULT 0,
     `dname` varchar(20) CHARACTER SET gbk COLLATE gbk_chinese_ci NOT NULL DEFAULT '',
     `loc` varchar(13) CHARACTER SET gbk COLLATE gbk_chinese_ci NOT NULL DEFAULT '',
     PRIMARY KEY (`id`) USING BTREE
   ) ENGINE = InnoDB CHARACTER SET = gbk COLLATE = gbk_chinese_ci ROW_FORMAT = Dynamic;
   
   
   #2 建表emp
   CREATE TABLE `emp`  (
     `id` int(11) NOT NULL,
     `empno` int(10) UNSIGNED ZEROFILL NOT NULL,
     `ename` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
     `job` varchar(9) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
     `mgr` int(11) NOT NULL,
     `hireDate` date NOT NULL,
     `sal` double NOT NULL,
     `comm` double NOT NULL,
     `deptno` int(11) NOT NULL,
     PRIMARY KEY (`id`) USING BTREE
   ) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;
   ```

2. **设置参数log_bin_trust_function_creators**

   ```sql
   show VARIABLES like 'log_bin_trust_function_creators';
   ```

   ```sql
   set global log_bin_trust_function_creators = 1;
   ```

3. **创建函数，保证每条数据都不同**

   - 随机产生字符串
   - 随机产生部门编号

4. **创建存储过程**

   - 创建往emp表中插入数据的存储过程
   - 创建往dept表中插入数据的存储过程  

5. **调用存储过程**

   - dept
   - emp

### show Profile

### 全局查询日志

## 复合索引的底层存储结构

![image-20210622173629169](https://raw.githubusercontent.com/li0228/image/master/image-20210622173629169.png)

**key放多个字段。**

逐个字段比较

所以需要符合最佳做前缀原理
