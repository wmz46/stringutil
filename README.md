# stringutil
一个使用C#ToString规则的java字符串处理工具类


## 一、当前最新版本
```xml
<dependency>
  <groupId>com.iceolive</groupId>
  <artifactId>stringutil</artifactId>
  <version>1.1.3</version>
</dependency>
```
## 二、快速开始
### 格式化
所有数值类型，包括整型和浮点型(byte,short,int,long,float,double,Byte,Short,Integer,Long,Float,Double,BigDecimal)
```java
    double d = 1234.567d;
    System.out.println(StringUtil.format(d, "C2"));//￥1,234.57
    System.out.println(StringUtil.format(d, "E2"));//1.23E+03
    System.out.println(StringUtil.format(d, "F2"));//1234.57
    System.out.println(StringUtil.format(d, "G2"));//1234.57
    System.out.println(StringUtil.format(d, "N2"));//1,234.57
    System.out.println(StringUtil.format(d, "#,##0.00"));//1,234.57
    double p = 0.025d;
    System.out.println(StringUtil.format(p, "P2"));//2.50%
    System.out.println(StringUtil.format(p, "0.00%"));//2.50%
    System.out.println(StringUtil.format(p, "0.00‰"));//25.00‰
```
整型特有(byte,short,int,longByte,Short,Integer,Long)
```java
    int i = 15;
    System.out.println(StringUtil.format(i, "D6"));//000015
    System.out.println(StringUtil.format(i, "X4"));//000E
```
日期类型(Date,LocalDate,LocalDateTime)
```java
    Date d1 = new Date();
    System.out.println(StringUtil.format(d1, "yyyy-MM-dd HH:mm:ss"));//2020-06-18 22:40:03
    LocalDate d2 = LocalDate.now();
    System.out.println(StringUtil.format(d2, "yyyy-MM-dd"));//2020-06-18
    LocalDateTime d3 = LocalDateTime.now();
    System.out.println(StringUtil.format(d3, "yyyy-MM-dd HH:mm:ss"));//2020-06-18 22:40:03
```
### 反格式化
```java
    //布尔型
    String str = "true";
    System.out.println(StringUtil.parse(str,  boolean.class));//true
    System.out.println(StringUtil.parseBoolean(str, boolean.class));//true
    str = "是";
    System.out.println(StringUtil.parseBoolean(str, "是", "否", boolean.class));//true
    //十六进制
    str = "0FF";
    System.out.println(StringUtil.parse(str, "X", int.class));//255
    str = "0ff";
    System.out.println(StringUtil.parse(str, "x", int.class));//255
    //千位符
    str = "-1,234.56";
    System.out.println(StringUtil.parse(str, "N", double.class));//-1234.56
    System.out.println(StringUtil.parse(str, "#,###.00", double.class));//-1234.56
    //百分比
    str = "-1.23%";
    System.out.println(StringUtil.parse(str, "P", double.class));//0.0123
    System.out.println(StringUtil.parse(str, "0.##%", double.class));//0.0123
    //千分比
    str = "-1.23‰";
    System.out.println(StringUtil.parse(str, "0.##‰", double.class));//0.00123
    //指数
    str = "-1.235400e-003";
    System.out.println(StringUtil.parse(str, "E", double.class));//-0.0012354
    System.out.println(StringUtil.parse(str,  double.class));//-0.0012354
    //货币
    str = "-$12.34";
    System.out.println(StringUtil.parse(str,"C",double.class));//-12.34
    str = "-￥12.34";
    System.out.println(StringUtil.parse(str,"C",double.class));//-12.34
    //日期
    str = "2020-06-19";
    System.out.println(StringUtil.parse(str,"yyyy-MM-dd",LocalDate.class));//2020-06-19

    //以上方法反格式化错误均会抛运行时异常，请自行捕获处理。如不想处理异常请使用tryParse方法,并提供出错默认值，进行反格式化
    System.out.println(StringUtil.tryParse("20160503","yyyy-MM-dd",LocalDate.now(),LocalDate.class));
```
数值格式化的定义请参考[标准数字格式字符串](https://docs.microsoft.com/zh-cn/dotnet/standard/base-types/standard-numeric-format-strings)和[自定义数字格式字符串](https://docs.microsoft.com/zh-cn/dotnet/standard/base-types/custom-numeric-format-strings),部分非常用格式可能未支持
## 三、开发背景

作为一个从事多年C#开发的程序员，习惯了ToString("")各种格式化的便利，在java上虽然也有各种各样的字符串工具库，但还是觉得C#的那套用起来更称手一点。

我是一个不喜欢造轮子的人，不过既然找不到类似的轮子，那也只能自己动手丰衣足食了。

java的语法虽然注定了没法移植一个一模一样的ToString()，不过参考format字符串规则构造一个字符串工具库还是很简单的。

## 四、我认为的优点
1.减轻部分C#代码迁移到java的工作量。

2.C#开发人员在做java开发，格式化字符串时可以用自己更擅长的处理方式。

3.减少了代码，增加了代码可读性。

## 五、存在的问题
由于“G”或“g”使用场景不多，格式处理逻辑较为复杂（主要原因），目前组件处理逻辑和“F”或“f”相同。
