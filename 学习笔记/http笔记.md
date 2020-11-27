# HTTP协议笔记

## 主要特点：

1. 简单快速：协议简单通信速度快
2. 灵活：通过Content-Type标记数据类型
3. 无连接：每次连接一个请求
4. 无状态：对事务处理没有记忆功能

## HTTP请求组成部分

客户端发送一个HTTP请求到服务器的请求消息包括以下格式：

##### 请求行（request line）、请求头部（header）、空行和请求数据四个部分组成。

![img](https://upload-images.jianshu.io/upload_images/2964446-fdfb1a8fce8de946.png?imageMogr2/auto-orient/strip%7CimageView2/2)

1. GET请求报文

   ```
   GET	/jksjdafj2930.jpg	HTTP1.1
   Host 	img.mukewang.com
   Content-type  :  application/x-www-form-urlencode
   
   ```

   

2. POST请求报文

   ```
      POST / HTTP1.1
   
      Host:www.wrox.com 
   
      ....
   
   
   
      name = Professional..
   ```

   

1. Reaponse响应报文

   ```
   HTTP1.1 200 ok
   Date: Fri, 22 May 2009 06:07:21 GMT
   Content-Type: text/html; charset=UTF-8
   
   <html>
   	<head></head>
   	<body>
   	
   	</body>
   </html>
   ```

## HTTP之状态码

状态代码有三位数字组成，第一个数字定义了响应的类别，共分五种类别:

###### 1xx：指示信息--表示请求已接收，继续处理

###### 2xx：成功--表示请求已被成功接收、理解、接受

###### 3xx：重定向--要完成请求必须进行更进一步的操作

###### 4xx：客户端错误--请求有语法错误或请求无法实现

###### 5xx：服务器端错误--服务器未能实现合法的请求

常见状态码：

```
200 OK                        //客户端请求成功
400 Bad Request               //客户端请求有语法错误，不能被服务器所理解
401 Unauthorized              //请求未经授权，这个状态代码必须和WWW-Authenticate报头域一起使用 
403 Forbidden                 //服务器收到请求，但是拒绝提供服务
404 Not Found                 //请求资源不存在，eg：输入了错误的URL
500 Internal Server Error     //服务器发生不可预期的错误
503 Server Unavailable        //服务器当前不能处理客户端的请求，一段时间后可能恢复正常
```

更多状态码http://www.runoob.com/http/http-status-codes.html

## HTTP之工作原理

### **栗子:**

在浏览器地址栏键入URL，按下回车之后会经历以下流程：

1、浏览器向 DNS 服务器请求解析该 URL 中的域名所对应的 IP 地址;

2、解析出 IP 地址后，根据该 IP 地址和默认端口 80，和服务器建立[TCP连接](http://www.jianshu.com/p/ef892323e68f);

3、浏览器发出读取文件(URL 中域名后面部分对应的文件)的HTTP 请求，该请求报文作为 [TCP 三次握手](http://www.jianshu.com/p/ef892323e68f)的第三个报文的数据发送给服务器;

4、服务器对浏览器请求作出响应，并把对应的 html 文本发送给浏览器;

5、释放 [TCP连接](http://www.jianshu.com/p/ef892323e68f);

6、浏览器将该 html 文本并显示内容; 　

### TCP协议

参考文章：https://mp.weixin.qq.com/s/rubnCfjMQc86cC6KkaWdpg

**报文格式**

![img](https://mmbiz.qpic.cn/mmbiz_png/J0g14CUwaZeo9xBVAyPJ8iaWCC6sYS843ZPb6tFLvCVuXEn98khfs7y2KRvOV0ia5icVByzIK3aAKRURuVZKagsKw/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

16位源端口号：16位的源端口中包含初始化通信的端口。源端口和源IP地址的作用是标识报文的返回地址。

16位目的端口号：16位的目的端口域定义传输的目的。这个端口指明报文接收计算机上的应用程序地址接口。

**32位序号：32位的序列号由接收端计算机使用，重新分段的报文成最初形式。当SYN出现，序列码实际上是初始序列码（Initial Sequence Number，ISN），而第一个数据字节是ISN+1。这个序列号（序列码）可用来补偿传输中的不一致。**

**32位确认序号：32位的序列号由接收端计算机使用，重组分段的报文成最初形式。如果设置了ACK控制位，这个值表示一个准备接收的包的序列码。**

4位首部长度：4位包括TCP头大小，指示何处数据开始。

保留（6位）：6位值域，这些位必须是0。为了将来定义新的用途而保留。

标志：6位标志域。表示为：紧急标志、有意义的应答标志、推、重置连接标志、同步序列号标志、完成发送数据标志。按照顺序排列是：URG、ACK、PSH、RST、SYN、FIN。

16位窗口大小：用来表示想收到的每个TCP数据段的大小。TCP的流量控制由连接的每一端通过声明的窗口大小来提供。窗口大小为字节数，起始于确认序号字段指明的值，这个值是接收端正期望接收的字节。窗口大小是一个16字节字段，因而窗口大小最大为65535字节。

16位校验和：16位TCP头。源机器基于数据内容计算一个数值，收信息机要与源机器数值 结果完全一样，从而证明数据的有效性。检验和覆盖了整个的TCP报文段：这是一个强制性的字段，一定是由发送端计算和存储，并由接收端进行验证的。

16位紧急指针：指向后面是优先数据的字节，在URG标志设置了时才有效。如果URG标志没有被设置，紧急域作为填充。加快处理标示为紧急的数据段。

选项：长度不定，但长度必须为1个字节。如果没有选项就表示这个1字节的域等于0。

数据：该TCP协议包负载的数据。

在上述字段中，6位标志域的各个选项功能如下。

**URG：紧急标志。紧急标志为"1"表明该位有效。**

**ACK：确认标志。表明确认编号栏有效。大多数情况下该标志位是置位的。TCP报头内的确认编号栏内包含的确认编号（w+1）为下一个预期的序列编号，同时提示远端系统已经成功接收所有数据。**

**PSH：推标志。该标志置位时，接收端不将该数据进行队列处理，而是尽可能快地将数据转由应用处理。在处理Telnet或rlogin等交互模式的连接时，该标志总是置位的。**

**RST：复位标志。用于复位相应的TCP连接。**

**SYN：同步标志。表明同步序列编号栏有效。该标志仅在三次握手建立TCP连接时有效。它提示TCP连接的服务端检查序列编号，该序列编号为TCP连接初始端（一般是客户端）的初始序列编号。在这里，可以把TCP序列编号看作是一个范围从0到4，294，967，295的32位计数器。通过TCP连接交换的数据中每一个字节都经过序列编号。在TCP报头中的序列编号栏包括了TCP分段中第一个字节的序列编号。**

**FIN：结束标志。**

### 三次握手

**建立tcp连接**

![img](https://mmbiz.qpic.cn/mmbiz_png/J0g14CUwaZeo9xBVAyPJ8iaWCC6sYS843fFol7gd3035Kibg3gPMSAZQLVibf9nwEblOUaX80hoOaRLVpaYCAI44w/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)



### 四次挥手

**断开连接**

![img](https://mmbiz.qpic.cn/mmbiz_png/J0g14CUwaZeo9xBVAyPJ8iaWCC6sYS843KaMMu2mHfFLZNgiaREDZ5JicRYrlaiciayQjh9HDsacxIbMT0emGUpAX5w/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)



三次握手：

1.客户端：SYN=1,Seq=1(client_isn)	状态：CLOSE->SYN-SENT

2.服务端：SYN=1，ACK=1，ack=client_isn+1,seq=1(server_isn)	状态：LISTEN->SYN_RCVD

3.客户端：ACK=1,ack=server_isn+1	状态：SYN-SENT->ESTABLISTED

客户端收到应答之后：状态：SYN-RCVD ->ESTABLISTED



