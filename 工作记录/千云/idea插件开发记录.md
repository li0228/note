# 一、插件开发

## 计划：

1. 学习idead的插件开发流程
2. 了解旧插件的操作和原理
3. 移植插件内容，使用IDEA的API替换掉ecplise

## 资料

**官网**

https://jetbrains.org/intellij/sdk/docs/basics/getting_started.html

**教程**

https://blog.csdn.net/zhulier1124/article/details/107307722/Idea插件开发（一）——插件的分类及基础认识

https://blog.csdn.net/zhangbuzhangbu/article/details/52227403参考教程找到Structure的右键菜单，主要插件旧插件，通过这里的右键菜单进行调试

https://blog.csdn.net/danpu0978/article/details/106777111?utm_medium=distribute.pc_relevant.none-task-blog-baidulandingword-1&spm=1001.2101.3001.4242您的插件操作应从IntelliJ OpenAPI扩展AnAction抽象类。 传递给actionPerformed方法的唯一参数是AnActionEvent。 从该对象可以访问各个地方：

https://blog.csdn.net/huachao1001/article/details/53883500AndroidStudio插件开发（进阶篇之Action机制）

https://www.jianshu.com/p/8ab8efdfc647 PSI元素

## 开发

参考https://www.cnblogs.com/milovetingting/p/12853621.html IntelliJ IDEA插件开发的简单流程

**关于Action**

​	插件可以向现有的IDE菜单和工具栏添加操作，也可以添加新的菜单和工具栏。IntelliJ平台调用插件的Action来响应用户与IDE的交互。但是，必须首先定义插件的	动作并在IntelliJ平台上注册。

​	**注册Action可以直接使用idea的快速创建功能或者自己在配置文件中配置**

<img src="C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20200815180852038.png" alt="image-20200815180852038" style="zoom: 67%;" />

​	**配置action的id,name等，在下方的Groups面板中，根据实际情况，选择action需要加入的group**

<img src="C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20200815181628565.png" alt="image-20200815181628565" style="zoom:80%;" />

**关于AnActionEvent**

​	AnActionEvent 函数和update函数的形参都包含AnActionEvent对象。AnActionEvent对象是我们与IntelliJ IDEA交互的桥梁，我们可以通过AnActionEvent对象获取当前IntelliJ IDEA的各个模块对象，如编辑框窗口对象、项目窗口对象等，获取到这些对象我们就可以做一些定制的效果。

 **getData函数**

​	通过AnActionEvent对象的getData函数可以得到IDEA界面各个窗口对象以及各个窗口为实现某些特定功能的对象。getData函数需要传入`DataKey<T>`对象，用于指明想要获取的IDEA中的哪个对象。在`CommonDataKeys`已经定义好各个IDEA对象对应的`DataKey<T>`对象。

**通过这个函数我们就可以取到点击的元素上下文数据**

```java
PsiElement data = e.getData(CommonDataKeys.PSI_ELEMENT);
```

**关于PSI**

​	https://www.jianshu.com/p/8ab8efdfc647

**我们的需求是：**

​	**1、通过点击Structure中的方法来调试代码（需要注意的不是方法是无法进行调试的，所以需要判断取到的PisElement元素是否为PsiMethod）**

​	**2、通过在编辑器中点击类中的方法来调试代码（需求和在Stracuture一样）**

**代码实现**

```java
package gameServerDebug.ui.handlers;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.*;
import gameServerDebug.util.HttpRequesterUtil;
import gameServerDebug.util.HttpRespons;
import org.freedesktop.dbus.messages.Message;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lhh
 */
public class GameTestHandler extends AnAction {
   public static String KEY = "f7191ffa-21cf-43c8-9aa4-14f6a862b668";
   public static String SERVERURL = "http://localhost/game/test/debug";
   @Override
   public void actionPerformed(AnActionEvent e) {
      // 获取当前的project对象
      Project project = e.getProject();
      String clzName;
      String methodName;
      PsiElement element = e.getData(CommonDataKeys.PSI_ELEMENT);
      /**
       * 方法对象
       */
      if(element instanceof PsiMethod){
         //类
         PsiClass psiClass = (PsiClass) element.getParent();
         //类名
         clzName = psiClass.getQualifiedName();
         //方法名
         methodName = ((PsiMethod)element).getName();

         Map<String, String> args = new HashMap<String, String>(4);
         args.put("key", KEY);
         args.put("clzName", clzName);
         args.put("methodName", methodName);
         try {
            HttpRespons res = HttpRequesterUtil.sendGet(SERVERURL, args);
            Messages.showMessageDialog(res.getContent(), "结果", Messages.getInformationIcon());
         } catch (IOException exception) {
            exception.printStackTrace();
            Messages.showMessageDialog(exptToString(exception), "结果", Messages.getErrorIcon());
         }
      }
   }
   /**
    * 返回该异常的堆栈字符串信息
    *
    * @param e
    * @return
    */
   public static String exptToString(Exception e) {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      PrintStream ps = new PrintStream(baos);
      e.printStackTrace(ps);
      return new String(baos.toByteArray());
   }

}
```

取到全类名和当前操作的方法名就可以直接调用之前的代码，再打印返回的结果即可。

## 安装使用

**安装：**文件->设置->Plugins

![image-20200817094323392](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20200817094323392.png)

选择本地安装，找到插件所在位置，然后重启idea




**使用：**

编码要求：所有测试的代码都需要写在lilith.server.tank.debug.test包下面，测试函数必须为 一个String[],返回String,如public String testList(String[] args) 

**在调试方法上直接右键**

![image-20200817094903976](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20200817094903976.png)

**在Structure（使用Alt+7调出)上右键**

![image-20200817095046581](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20200817095046581.png)

# 二、插件优化

## 计划

1. 实现本地的动态加载类文件并且运行类中的方法（参考bsh指令调试）
2. 通过网络传输类文件并且在远程服务起动态加载类
   - 传送方法体远程服务器
3. 实现idea插件界面，实现动态配置信息
   - 界面的构建，跳转逻辑
   - 配置信息的持久化

## java实现动态编译并动态加载

**代码里有相似的功能，参照运维工具bsh的实现（可复用代码）**

- **资料**

  https://blog.csdn.net/zhoufanyang_china/article/details/82767406?utm_medium=distribute.pc_relevant.none-task-blog-title-1&spm=1001.2101.3001.4242 java实现动态编译并动态加载
  
  https://blog.csdn.net/u011637069/article/details/51305268 Java中利用Interpreter动态编译实现eval

```java
System.out.println("调试代码:\n"+code);
Interpreter inter = new Interpreter();//这里是重点
try {
   inter.eval("import java.*;");
   Object o = inter.eval(code);//还有这里
   if (o == null) {
      ret = "执行成功,返回 NULL";
   } else {
      ret += o.getClass().getSimpleName() + "\n";
      ret += o;
   }
} catch (Exception e) {
   ret = exptToString(e);
   e.printStackTrace();
}
```

通过Interpreter,我们可以在实现动态编译，我们只要把代码通过http传到服务器，就可以实现远程调试的

## 通过网络传输类文件并且在远程服务起动态加载类

这里直接传string（import语句和方法体）

```java
/**
* 提取方法体
*/
PsiElement element = e.getData(CommonDataKeys.PSI_ELEMENT);
String text = element.getText();//方法文本

int start = text.indexOf("{");
int end = text .lastIndexOf("}");
String substring = text.substring(start + 1, end);

/**
 * 提取导入语句
 */
StringBuilder code = new StringBuilder();
Document document = e.getData(CommonDataKeys.EDITOR).getDocument();
String documentText = document.getText();
String importStr = documentText.substring(documentText.indexOf(";")+1,documentText.indexOf("public"));
if(importStr != null && importStr.length() > 0){
   code.append(importStr).append("\n").append(substring);
   return code.toString();
}else{
   return substring;
}
```

提取方法体：通过psi来找到操作的方法体

提取导入语句：psi中暂时找不到导入语句的获取方式，暂时通过document获取当前操作窗口的文本信息，来裁切出import语句

ps：因为是通过文本裁切的，所以如果有写import语句，要按照裁切的规则来写，不然会报奇奇怪怪的错误（暂时没办法解决，只能按照规则来写）

**裁切规则：**

**从第一行的包语句的分号为起点，到类声明语句的public为止（所以创建类时一定要加public）**

![image-20200903155921717](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20200903155921717.png)

接下来只要通过http发送到指定的远程服务器即可，发送http请求的代码参考之前的实现。

## 界面优化，动态配置信息

### 基础页面设计和实现

可视化开发。可以通过界面来拖拽控件。

<img src="C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20200903163705550.png" alt="image-20200903163705550" style="zoom: 67%;" />

**主要组成：**

​	文本框：名称、ip地址、端口

​	加按钮：增加一个主机信息

​	减按钮：删除一个主机信息

​	ok按钮：选中后点击ok可以设置选中的主机，选中的主机需要持久化，避免后面启动的时候又要重新选择

​	cancel按钮：取消

​	**这部分的工作主要就是监听“+”和“-”还有“ok”这些按钮，还有主机列表的初始化**

### 配置信息持久化

**Service：**

Service 也是一种按需加载的 component，在调用 *ServiceManager.getService(Class)*（https://upsource.jetbrains.com/idea-ce/file/idea-ce-d00d8b4ae3ed33	097972b8a4286b336bf4ffcfab/platform/core-api/src/com/intellij/openapi/components/ServiceManager.java）时才会加载，且程序中只有一个实例。

Serivce 在 IntelliJ IDEA 中是以 extension point 形式提供的，实现自己的 service 需要扩展相应 extension point。

- **applicationService: application level service**
- **projectService: project level service**
- **moduleService: module level service**

声明 service 时必须包含 `serviceImplementation` 属性用于实例化 service， `serviceInterface` 属性是可选的，可用于获取 service 实例。	

**ps：为什么讲到这里，是因为想要初始化配置信息必须是针对component和service才可以，插件中使用的是service所以只说这个**

**PropertiesComponent：**

对于一些简单少量的值，我们可以使用 `PropertiesComponent`，它可以保存 application 级别和 project 级别的值。

下面方法用于获取 PropertiesComponent 对象：

```java
//获取 application 级别的 PropertiesComponent
PropertiesComponent.getInstance()
//获取 project 级别的 PropertiesComponent，指定相应的 project
PropertiesComponent.getInstance(Project)

propertiesComponent.setValue(name, value)
propertiesComponent.getValue(name)
```

PropertiesComponent` 保存的是键值对，由于所有插件使用的是同一个 namespace，强烈建议使用前缀来命名 name，比如使用 plugin id。

**PS：这里我们不用PropertiesComponent，因为这个只能存键值对的形式，我们需要一个列表都存，只做**

**PersistentStateComponent：**

*PersistentStateComponent*（https://upsource.jetbrains.com/idea-ce/file/idea-ce-d00d8b4ae3ed33097972b8a4286b336bf4ffcfab/platform/projectModel-api/src/com/intellij/openapi/components/PersistentStateComponent.java） 用于持久化比较复杂的 components 或 services，可以指定需要持久化的值、	值的格式以及存储位置。

要使用 `PersistentStateComponent` 持久化状态：

**需要提供一个 `PersistentStateComponent<T>` 接口的实现类（component 或 service），指定类型参数，重写 getState() 和 loadState() 方法**

**类型参数就是要被持久化的类，它可以是一个 bean class，也可以是 `PersistentStateComponent`实现类本身。**

**在 `PersistentStateComponent<T>` 的实现类上，通过 `@com.intellij.openapi.components.State` 注解指定存储的位置**

下面通过两个例子进行说明：

```java
class MyService implements PersistentStateComponent<MyService.State> {
  //这里 state 是一个 bean class
  static class State {
    public String value;
    ...
  }

  //用于保存当前的状态
  State myState;

  // 从当前对象里获取状态
  public State getState() {
    return myState;
  }
  // 从外部加载状态，设置给当前对象的相应字段
  public void loadState(State state) {
    myState = state;
  }
}
```

```java
// 这里的 state 就是实现类本身
class MyService implements PersistentStateComponent<MyService> {
  public String stateValue;
  ...

  public MyService getState() {
    return this;
  }

  public void loadState(MyService state) {
    XmlSerializerUtil.copyBean(state, this);
  }
}
```

**1、实现 State 类**

**a、字段要求**

state 类中可能有多个字段，但不是所有字段都可以被持久化，可以被持久化的字段：

- public 字段
- bean 属性：提供 getter 和 setter 方法
- 被*注解*（https://upsource.jetbrains.com/idea-ce/file/idea-ce-d00d8b4ae3ed33097972b8a4286b336bf4ffcfab/platform/util/src/com/intellij/util/xmlb/annotations）的私有字段：使用 @Tag, @Attribute, @Property, @MapAnnotation, @AbstractCollection 等注解来自定义存储格式，一般在实现向后兼容时才考虑使用这些注解

这些字段也有类型要求：

- 数字（包括基础类型，如int，和封装类型，如Integer）
- 布尔值
- 字符串
- 集合
- map
- 枚举

如果不希望某个字段被持久化，可以使用 `@com.intellij.util.xmlb.annotations.Transient` 注解。

**b、构造器要求**

state 类必须有一个默认构造器，这个构造器返回的 state 对象被认为是默认状态，只有当当前状态与默认状态不同时，状态才会被持久化。

**2、定义存储位置**

我们可以使用 `@State` 注解来定义存储位置（如果没有设置的话可能会无法持久化）

```
@State(name = "PersistentDemo", storages = {@Storage(value = "PluginDemo.xml")})
public class PersistentDemo implements PersistentStateComponent<PersistentDemo> {
  ...
}
```

**name：** 定义 xml 文件根标签的名称

**storages：** 一个或多个 `@Storage`，定义存储的位置

- 若是 application 级别的组件 运行调试时 xml 文件的位置： `~/IdeaICxxxx/system/plugins-sandbox/config/options` 正式环境时 xml 文件的位置： `~/IdeaICxxxx/config/options`
- 若是 project 级别的组件，默认为项目的 `.idea/misc.xml`，若指定为 `StoragePathMacros.WORKSPACE_FILE`，则会被保存在 `.idea/worksapce.xml`

**3、生命周期**

- **loadState()** 当组件被创建或 xml 文件被外部改变（比如被版本控制系统更新）时被调用
- **getState()** 当 settings 被保存（比如settings窗口失去焦点，关闭IDE）时，该方法会被调用并保存状态值。如果 `getState()` 返回的状态与默认状态相同，那么什么都不会被保存。
- **noStateLoaded()** 该方法不是必须实现的，当初始化组件，但是没有状态被持久化时会被调用

**4、组件声明**

持久化组件可以声明为 component，也可以声明为 service

声明为 service，plugin.xml 文件如下配置：

```java
<extensions defaultExtensionNs="com.intellij">
    <applicationService serviceImplementation="com.example.test.persisting.PersistentDemo"/>
    <projectService serviceImplementation="com.example.test.persisting.PersistentDemo2"/>
  </extensions>
```

代码中获取状态与获取 service 的方式一样：

```java
PersistentDemo persistDemo = ServiceManager.getService(PersistentDemo.class);
PersistentDemo2 persistDemo2 = ServiceManager.getService(project，PersistentDemo.class);
```

声明为 component，plugin.xml 文件如下配置：

```java
<application-components>
  <!--将持久化组件声明为component-->
  <component>
    <implementation-class>com.example.persistentdemo.PersistentComponent</implementation-class>
  </component>
</application-components>
```

获取状态与获取 component 的方式一样：

```java
public static PersistentComponent getInstance() {
    return ApplicationManager.getApplication().getComponent(PersistentComponent.class);
}
public static PersistentComponent getInstance(Project project) {
    return project.getComponent(PersistentComponent.class);
}
```

# 三、问题记录

## 无法持久化配置信息

<img src="C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20200902175206564.png" alt="image-20200902175206564" style="zoom: 80%;" />

解决：

```java
@State(name = "PersistentDemo", storages = {@Storage(value = "PluginDemo.xml")})
```

需要执行存放的地址和名称