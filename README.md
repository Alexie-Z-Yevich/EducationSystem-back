# FoundationTemplate

该项目是裁剪的 Education System 项目部分内容做成的集成 Java 17 / Spring Boot 3+ / Spring Security 6+ / Mybatis-Plus 3.5.1.3 等组件搭建的后端的登录校验模块。因为早期的 [JavaWeb-Java](https://github.com/Alexie-Z-Yevich/JavaWeb-Java) 总体技术栈老旧以及我自身对整个框架的了解不够充分，所以将校验部分抽取出来做成了一个“船新”版本的后台Beginning！

本来想用若依学习的，但是发现若依主体仍然在 Java 8 阶段，且过分集成反而对我这种菜鸡码农不是很友好，学习成本过大，所以这个 FoundationTemplate 项目不在于让你了解多深多详尽的 Java 框架开发知识，而是用最新的技术、最快的方式，直接上手你自己的模块开发，毕竟——**好的框架千篇一律，有意思的模块只有你能想更只有你能做！千万别把时间浪费在每个教学视频都不一样的框架搭建上，做点自己喜欢的代码开发，因为热爱，才深耕计算机。**



#### TODO

- [x] 验证码美化
  - [x] 修改字体、去除噪声
  - [ ] 设置颜色随机
- [ ] 解决 stuMessageServiceImpl 与 sysMenuServiceImpl 依赖循环的问题
- [ ] 数据库表结构优化
  - [ ] 字段优化
  - [ ] 类型优化
- [ ] 重写代码生成器



#### 技术选型

|           服务            |                             依赖                             |                             版本                             |
| :-----------------------: | :----------------------------------------------------------: | :----------------------------------------------------------: |
|        SpringBoot         | spring-boot-starter-web<br />spring-boot-devtools<br />lombok<br />[jakarta.servlet-api](#1jakarta-javax接口调整) |                            3.1.1                             |
|           Java            |                                                              |                              17                              |
|       Mybatis-Plus        | mybatis-plus-boot-starter<br />mybatis-plus-generator<br />freemarker | [3.5.3.1](#2版本无法协同的mybatis-plus-boot-starter与mybatis-plus-generator) |
| Springboot Security + Jwt |            spring-boot-starter-security<br />jjwt            |                        NAN<br />0.9.1                        |
|           MySQL           |                     mysql-connector-java                     |                            8.0.27                            |
|           Redis           |                spring-boot-starter-data-redis                |                                                              |
|                           |                                                              |                                                              |
|          Others           |     kaptcha（验证码）<br />hutool-all<br />commons-lang3     |                0.0.9<br />5.8.18<br />3.12.0                 |



#### 设计理念



#### 数据库结构说明

首先现在的数据库字段啥的绝对是不合理的，从命名到设计不合理的地方还是比较多，这个会在 v1.1.0 中修改，目前没得太多空。主要由 3 张表构成：用户表、菜单表、权限-菜单表。下面是每张表中的必填字段

用户表【stu_message】：记录用户的基本信息、验证登录功能、记录用户权限；

|    名    |    类型    |         注释          |
| :------: | :--------: | :-------------------: |
|    id    |   bigint   |                       |
|  stu_id  | varchar255 | 学号/工号（登录账号） |
|   name   | varchar255 |       显示名称        |
| class_id |    int     |       班级代码        |
| password | varchar255 |         密码          |
|   role   | varchar255 |       权限/身份       |

菜单表【sys_menu】：对整个侧边栏的信息的记录，同时附带操作的原子权限（目前数据中并不带有）；

|    名     |    类型    |         注释          |
| :-------: | :--------: | :-------------------: |
|    id     |   bigint   |                       |
| parent_id |   bigint   | 父菜单ID，一级菜单为0 |
|   name    | varchar255 |       分栏名称        |
|   path    | varchar255 |                       |

权限-菜单表：根据用户权限对照能够获取菜单中信息的中间关系表；

【用户-权限表】：对权限的进一步拆分，用来对权限种类的增加/修改/减少【目前没有】。



#### 部分疑难杂症

###### 1、jakarta-javax接口调整

因为一些[“众所周知”]([Spring Boot 3.0.0 M1 Release Notes · spring-projects/spring-boot Wiki (github.com)](https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-3.0.0-M1-Release-Notes))的原因，javax 在 SpringBoot 3+ 被抛弃咯，进而被新老板 jakarata 取代，好的是新老板有更多、更规范的功能，坏的是这个名字也肽难记了。



###### 2、版本无法协同的mybatis-plus-boot-starter与mybatis-plus-generator

如果哪天这个问题被划掉了，那一定是我 or 哪位高手好心重写了 Mybatis-Plus 的代码生成器。目前没有细细研究，但是老版本的代码生成器无法使用了，所以有计划研究完新版的源码后再重写一下。

不过不用担心，目前的基本够用，不会说因为代码生成而影响开发哦！（另：低版本的生成会有 Bean 生成不了的问题 [报错 SqlSession，目前网上无很好的解答]）