
## 1.1.2(2024-03-26)

### 新功能
- 在线用户：监控在线用户，实现强制下线

### fix bug
- 修复生成代码的bug


## 1.1.1(2024-03-22)

### 新功能
- 输入密码错误达到次数后，自动锁定账户

### 优化
- 修改bug，优化的代码

## 1.1.0(2024-03-11)

### 新功能
- 代码生成：可以动态生成代码，前后端代码一键生成


## 1.0.0(2024-01-24)

### 正式发布
- 用户管理：基础的用户管理、用户登录等功能
- 部门管理：提供基于mybatis-plus的数据权限过滤
- 岗位管理：用户关联岗位、岗位与部门关联
- 角色管理：用户关联角色
- 菜单管理：结合前端的router配置，实现动态路由
- 数据字典：存放字典信息，并且结合easy-trans使用作为数据字典转换
- 操作日志：对于新增、修改与删除等操作存储操作日志
- 登录日志：登录日志存放
- 定时任务：基于Hutool的Cron表达式，支持动态修改
- 服务监控：查看服务器相关信息
- 文件管理：使用X-File-Storage实现文件上传与下载，并实现文件与数据库的关联，结合easy-trans自动生成加密的访问链接
- 系统设置：配置信息信息、验证码开关、默认密码与默认菜单等相关信息