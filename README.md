<h2 align="center" style="text-align:center;">
    <img src="logo.png" width="128" />
    <br />
    Donut Admin (甜甜圈后台通用模板)
</h2>
<p align="center">
	<strong>基于Spring Boot3、JDK17的一套开源管理系统</strong>
</p>

## 功能：
* 用户管理：基础的用户管理、用户登录等功能
* 部门管理：提供基于mybatis-plus的数据权限过滤
* 岗位管理：用户关联岗位、岗位与部门关联
* 角色管理：用户关联角色
* 菜单管理：结合前端的router配置，实现动态路由
* 数据字典：存放字典信息，并且结合easy-trans使用作为数据字典转换
* 操作日志：对于新增、修改与删除等操作存储操作日志
* 登录日志：登录日志存放
* 定时任务：基于Hutool的Cron表达式，支持动态修改
* 服务监控：查看服务器相关信息
* 文件管理：使用X-File-Storage实现文件上传与下载，并实现文件与数据库的关联，结合easy-trans自动生成加密的访问链接
* 系统设置：配置信息信息、验证码开关、默认密码与默认菜单等相关信息

当然，还有许多的功能正在开发中！敬请期待！

## 技术栈：
#### 后端：
* Spring Boot 3
* JDK 17
* Mysql 8
* Redis
* Sa-Token
* MyBatis-Plus
* Easy-Trans
* Hutool
* Knife4j
* EasyExcel
* X-File-Storage
* MapStruct

#### 前端：
前端基于开源项目[Vben](http://vben.vvbin.cn)
* Vue3
* Vite
* Ant-Design4

### 项目预览：

http://139.155.69.83:10222/

账号：admin

密码：123456

### 项目启动：
如果您熟悉docker可能部署较为简单，可以直接使用docker-compose启动项目（mysql、redis）服务

您可以在项目根目录下执行 docker-compose up -d mysql redis minio

mysql会自动创建数据库并执行初始化sql

当然您可以自己使用您已经启动好的数据库和redis，修改对应application-dev.yml的配置即可

如果您不使用minio或者使用其他的文件存储服务，请修改application.yml中的配置即可

### 项目部署：

进入项目根目录

 ``mvn clean package -DskipTests`` 打包api的jar包

``docker-compose build`` 构建web、api的命令

``docker-compose up -d`` 启动项目


### 项目运行：
#### 后端：
* 启动类：``donut-api/src/mainDonutApiApplication.java``

#### 前端：
``cd donut-admin-web/``

您需要使用node 16.x，并且建议您使用pnpm

pnpm install

pnpm dev

默认账户密码：admin/admin123


### 演示画面

<p align="center">
    <img alt="Donut admin" width="100%" src="./images/preview1.jpg">
    <img alt="Donut admin" width="100%" src="./images/preview2.jpg">
    <img alt="Donut admin" width="100%" src="./images/preview3.jpg">
    <img alt="Donut admin" width="100%" src="./images/preview4.jpg">
</p>