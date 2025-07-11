# JVAV 教务管理系统

## 项目简介

JVAV 教务管理系统是一个基于 Spring Boot 的教务管理后端服务，提供课表管理、教师管理、教室管理、课程排课等功能。

## 技术栈

- **Java**: 17
- **Spring Boot**: 3.0.6
- **数据库**: MySQL 8.0
- **ORM 框架**: MyBatis Plus
- **API 文档**: Swagger/SpringDoc
- **数据导入**: EasyExcel
- **安全认证**: JWT
- **构建工具**: Maven

## 功能模块

- 🏫 **教室管理**: 教室信息的增删改查
- 👨‍🏫 **教师管理**: 教师信息管理
- 📚 **课程管理**: 课程信息维护
- 📅 **排课管理**: 课程排课与时间表管理
- ⚙️ **课表设置**: 课表显示配置
- 📊 **报表统计**: 教师上课数量等统计报表
- 🔐 **用户认证**: 用户注册、登录、个人信息管理
- 💌 **消息通知**: 邮件提醒功能

## 环境要求

- JDK 17+
- Maven 3.6+
- MySQL 8.0+

## 快速开始

### 1. 数据库准备

创建 MySQL 数据库：

```sql
CREATE DATABASE timetable CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
```

执行初始化脚本：

```bash
mysql -u root -p timetable < deploy/install/1_init_ddl.sql
mysql -u root -p timetable < deploy/install/2_init_dml.sql
```

### 2. 配置修改

修改 `src/main/resources/application-dev.yaml` 中的数据库连接信息：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/timetable?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
    username: your_username
    password: your_password
```

### 3. 构建项目

```bash
cd timetable-server
mvn clean install
```

### 4. 启动服务

```bash
java -jar target/jvav-server-*.jar
```

服务启动后，访问地址：

- **服务地址**: http://localhost:12010
- **API 文档**: http://localhost:12010/swagger-ui/index.html

## Docker 部署

### 使用 Docker Compose 一键部署

```bash
cd deploy/base
docker-compose up -d
```

这将启动：

- MySQL 数据库 (端口: 13306)
- 后端服务 (端口: 12010)
- Nginx 前端代理 (端口: 80)

## API 接口

系统提供 RESTful API，主要接口包括：

### 认证相关

- `POST /login` - 用户登录
- `POST /register` - 用户注册
- `GET /user/username/{username}` - 获取用户信息
- `PUT /user/update/{username}` - 更新用户信息

### 教室管理

- `GET /classroom` - 分页查询教室
- `POST /classroom` - 新增教室
- `PUT /classroom/{id}` - 更新教室
- `DELETE /classroom/{id}` - 删除教室

### 教师管理

- `GET /teacher` - 分页查询教师
- `POST /teacher` - 新增教师
- `PUT /teacher/{id}` - 更新教师
- `DELETE /teacher/{id}` - 删除教师

### 课程管理

- `GET /course` - 分页查询课程
- `POST /course` - 新增课程
- `PUT /course/{id}` - 更新课程
- `DELETE /course/{id}` - 删除课程

### 排课管理

- `POST /course-scheduling/list` - 查询排课列表
- `POST /course-scheduling` - 新增排课
- `PUT /course-scheduling/{id}` - 更新排课
- `DELETE /course-scheduling/{id}` - 删除排课
- `POST /course-scheduling/upload` - Excel 批量导入排课

### 报表统计

- `GET /report/teacher/count` - 教师上课数量统计

详细的 API 文档请访问 Swagger UI。

## 开发说明

### 项目结构

```
src/main/java/com/jvav/timetable/
├── JvavTimetableApp.java          # 应用启动类
├── common/                        # 公共模块
│   ├── base/                      # 基础类
│   ├── consts/                    # 常量定义
│   ├── exception/                 # 异常处理
│   ├── model/                     # 公共模型
│   └── util/                      # 工具类
├── config/                        # 配置类
│   ├── JacksonConfig.java         # JSON 序列化配置
│   ├── MybatisPlusConfig.java     # MyBatis Plus 配置
│   ├── SpringDocConfig.java       # API 文档配置
│   └── WebConfig.java             # Web 配置
└── module/                        # 业务模块
    ├── classroom/                 # 教室管理
    ├── course/                    # 课程管理
    ├── coursescheduling/          # 排课管理
    ├── message/                   # 消息通知
    ├── report/                    # 报表统计
    ├── settings/                  # 系统设置
    ├── teacher/                   # 教师管理
    └── user/                      # 用户管理
```

### 开发环境配置

1. 默认使用 `dev` 环境配置
2. 生产环境使用 `-Dspring.profiles.active=prod` 启动
3. 日志文件位置：`logs/jvav/logs/`

### 数据库升级

执行升级脚本：

```bash
cd deploy/upgrade
./upgrade.sh
```

## 许可证

本项目采用 MIT 许可证。
