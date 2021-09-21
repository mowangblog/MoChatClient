# MoChatClient
 Multi user instant messaging system client

## 项目概述

不使用任何框架的Java多用户即时通讯系统，可以学习到面向对象，集合，多线程，IO流，网络编程等多种知识，页面由于没有学Java-Gui就简化了，整体通讯框架已经搭好，界面的意义不大，以后有兴趣可能会做一个界面。

## 项目结构

1. [MoChat客户端](https://github.com/mowangblog/MoChatClient)
2. [MoChat服务端](https://github.com/mowangblog/MoChatServer)

## 项目主要功能模块

- 用户登录
- 用户注册
- 在线用户列表
- 私发消息
- 群发消息
- 发送文件
- 系统通知
- 离线消息
- 离线文件

## 服务端启动方式

配置服务端的`set.properties`文件，可修改端口和文件存储路径，然后通过`StartServer.java`的main方法即可启动服务端；

## 客户端启动方式

配置客户端的`set.properties`文件，可修改服务器的地址和端口号，注意要设置服务端对应的ip地址和服务端设置好的端口号，然后通过`StartApplication.java`的main方法即可启动客户端

## 项目截图

![image-20210921153503549](https://res.mowangblog.top/img/2021/09/image-20210921153503549.png)

![image-20210921153527339](https://res.mowangblog.top/img/2021/09/image-20210921153527339.png)

