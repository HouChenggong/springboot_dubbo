## 包找不到的解决方案



```java
重做过开发环境后在intellij idea中载入java工程，通过maven build工程时报出程序包不存在问题，在工程里点击报不存在的类，又能进入相关class。网上贴出的程序包不存在的解释和修复措施不能解决我遇到的情况，后来仔细对比以往工程目录结构，发现这次载入工程居然没有自动创建xxx.iml文件，如是尝试解决此问题：开始->运行->切换到项目目录执行 mvn idea:module 命令执行完毕后生成iml文件，再次点击maven build工程顺利编译通过。
 
原文链接：https://blog.csdn.net/jbgtwang/article/details/90443069
```

