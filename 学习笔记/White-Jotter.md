# White-Jotter开发问题记录

## 整合mybatis时，无法映射

![image-20201127232824229](C:\Users\li\AppData\Roaming\Typora\typora-user-images\image-20201127232824229.png)

描述：这个问题的根源其实是在mapper配置文件与dao接口做映射绑定时候出现的问题，它所指的意识就是，接口与响应的xml找不到，或者是匹配不到

解决：https://blog.csdn.net/weixin_40936211/article/details/89513691?utm_medium=distribute.pc_relevant_t0.none-task-blog-BlogCommendFromBaidu-1.control&depth_1-utm_source=distribute.pc_relevant_t0.none-task-blog-BlogCommendFromBaidu-1.control 

跟着这个一步一步来就好了，无非就是这几种情况