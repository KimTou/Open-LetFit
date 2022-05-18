# Open-LetFit

**LetFit 小程序（暂下线）后台开源版**

<img src="https://cdn.tojintao.cn/LetFit.jpg" style="zoom: 67%;" />


## 技术选型

* JDK 1.8
* SpringBoot 2.4
* Mybatis：持久层框架
* MySQL：关系数据库
* Redis：缓存
* MongoDB：分布式文件存储数据库
* druid：数据库连接池
* shiro：权限验证

**技术补充说明：**

* 使用七牛云对象存储
* 配置MySQL多数据源



## 后台架构

![](https://cdn.tojintao.cn/LetFit系统架构.png)

**关系数据库：**

为了预防 MySQL 的单点故障以及提高整体服务性能，本项目使用了 MySQL 的【**主从复制**】以及【**读写分离**】

主数据库处理写操作以及实时性要求比较高的读操作，而从数据库处理只读操作。

关于读写分离的实现，可参照我的博客：[SpringBoot实现MySQL读写分离](https://blog.csdn.net/KIMTOU/article/details/121570095)

**NoSQL：**

同时使用Redis内存型数据库进行缓存数据，用于存放热点数据，以减少数据查询的时间，提升系统响应速度。

使用MongoDB分布式文件存储数据库，适用于存储文章、题目等文档型数据，有利于海量数据的读写。

***

如果这个项目能帮助到你，请点个 star 吧 ~ :star:

