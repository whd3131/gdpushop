# 1. 前言

*gdpushop* 项目是广东药科大学192计算机科学与技术软件工程课程，完成的课程大作业。仅用于学习使用。

# 2. 项目介绍

*gdpushop* 项目是一套电商系统，包括前台系统和后台管理系统，基于Springboot+MyBatisPlus实现，前端采用Vue框架。

* 前台商城系统包含首页、商品推荐与广告、商品搜索、商品展示、购物车、订单与支付流程等模块。
* 后台管理系统包含商品管理，订单管理，会员管理，统计报表等模块。

## 2.1 项目演示

* **后台管理系统**
* **前台商城系统**

## 2.2 组织结构

> 暂时是单体架构，后面有时间再进行微服务拆分

gdpu
├─config
├─controller
│      AdvertisementController
│      CartController
│      CartOrderController
│      CategoryController
│      CommentInfoController
│      GoodsController
│      GoodsOrderController
│      OrderInfoController
│      PersonController
│      UserController
├─entity
├─interceptors
├─listener
├─mapper
├─service
└─utils

## 2.3 技术选型

### 2.3.1 后端技术

| 技术                          | 说明                | 官网                                                         |
| ----------------------------- | ------------------- | ------------------------------------------------------------ |
| SpringBoot                    | 容器+MVC框架        | [https://spring.io/projects/spring-boot](https://spring.io/projects/spring-boot) |
| MyBatisPlus及代码生成器<br /> | ORM框架<br />       | [https://mp.baomidou.com/](https://github.com/jwtk/jjwt)<br /> |
| Druid                         | 数据库连接池        | [https://github.com/alibaba/druid](https://github.com/alibaba/druid) |
| OSS                           | 对象存储            | [https://github.com/aliyun/aliyun-oss-java-sdk](https://github.com/aliyun/aliyun-oss-java-sdk) |
| JWT                           | JWT登录支持         | [https://github.com/jwtk/jjwt](https://github.com/jwtk/jjwt) |
| Lombok                        | 简化对象封装工具    | [https://github.com/rzwitserloot/lombok](https://github.com/rzwitserloot/lombok) |
| Hutool                        | Java工具类库        | [https://github.com/looly/hutool](https://github.com/looly/hutool) |
| Swagger-UI<br />              | 文档生成工具        | [https://github.com/swagger-api/swagger-ui](https://github.com/swagger-api/swagger-ui) |
| Easyexcel<br />               | 读写Excel的开源项目 | https://alibaba-easyexcel.github.io/                         |

### 2.3.2 前端技术

| 技术       | 说明             | 选型                                                         |
| ---------- | ---------------- | ------------------------------------------------------------ |
| Vue        | 前端框架         | [https://vuejs.org/](https://vuejs.org/)                     |
| Vue-router | 路由框架         | [https://router.vuejs.org/](https://router.vuejs.org/)       |
| VueX       | 全局状态管理框架 | [https://vuex.vuejs.org/](https://vuex.vuejs.org/)           |
| Element    | 前端UI框架       | [https://element.eleme.io](https://element.eleme.io/)        |
| Axios      | 前端HTTP框架     | [https://github.com/axios/axios](https://github.com/axios/axios) |
| Js-cookie  | cookie管理工具   | [https://github.com/js-cookie/js-cookie](https://github.com/js-cookie/js-cookie) |

## 2.4 架构图

### 2.4.1 系统架构图

### 2.4.2 业务架构图

### 2.4.3 模块介绍

#### 1. 后台管理系统

#### 2. 前台商城系统

## 2.5 开发进度

# 3.环境搭建

## 3.1 开发工具

## 3.2 开发环境

| 工具  | 版本 | 下载                                                         |
| ----- | ---- | ------------------------------------------------------------ |
| JDK   | 1.8  | [https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) |
| Mysql | 5.5  | [https://www.mysql.com/](https://www.mysql.com/)             |

## 3.3 搭建步骤

> windows环境部署
