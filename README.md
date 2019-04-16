# How-Tomcat-Works

本仓库的代码源于《How TomcatWorks》（《深入剖析Tomcat》）

##Why
书中的代码年代较为久远，不能直接在当前的环境下运行。  

因此我稍微修改了一部分由于JDK版本变化引起的Bug（enum关键字），同时通过Maven进行依赖管理。  

目的只有一个：在JDK8和Maven3.6的环境下可以运行。

##Use
环境：JDK8 & Maven3.6  

建议:使用IDEA更好，可以方便查看依赖包的源码。  

*每一章的单个小程序都是独立运行的*  

*每一章的程序Run起来之后，测试的URL和书中一致*

##Where
修改的地方大致如下（“大致”是因为写到这里的时候距修改代码已经过了一段时间）：  
####第一章：
Response.java----在*ResponseMessage*中无Response头部信息，在使用Edge浏览器时能接收到服务器响应而Chrome不能显示，增加了头部信息。


####第二章：
Pom.xml----引入*javax.servlet*  
同时添加Response的头部（同第一章）
####第三章：
Pom.xml----引入*catalina*  
同时添加Response的头部（同第一章）
####第四章：
取消添加Response的头部（见第一章），因为使用了catalina的Connector后会自动补充头部
####第五章-第七章：
无
####第八章：
具体改动可以对比Old和New文件夹的readme.txt
####第九章-第十章：
无
####第十一章：
Pom.xml----引入collection和logging依赖
####第十三章-第十四章：
无
####第十五章：
web.xml----见Digester_withTomcat的readme
####第十六章：
MySwingApp.java----书上有正确源码，但下载的源码中漏掉了部分，添加了MyShutdownHook类
####第12、17、18、19无程序，20章未测试

