# RBAC Demo - Spring Boot + JWT + RBAC 权限管理系统

## 技术栈

| 组件 | 版本 |
|------|------|
| Spring Boot | 2.7.14 |
| Spring Security | (随 Boot 版本) |
| JPA / Hibernate | (随 Boot 版本) |
| Flyway | (随 Boot 版本) |
| PostgreSQL | 12+ |
| JWT (jjwt) | 0.11.5 |
| Java | 11 |

## 快速开始

### 1. 准备 PostgreSQL 数据库

```sql
CREATE DATABASE rbac_demo;
```

确保 `application.yml` 中的连接信息与你的 PostgreSQL 配置一致：

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/rbac_demo
    username: postgres
    password: 123456
```

### 2. 构建与运行

```bash
# 编译打包
mvn clean package -DskipTests

# 运行
java -jar target/rbac-demo-1.0.0.jar
```

或直接用 Maven 运行：

```bash
mvn spring-boot:run
```

Flyway 会在启动时自动创建表结构并插入种子数据。

### 3. 预置用户

| 用户名 | 密码 | 角色 | 权限 |
|--------|------|------|------|
| admin | admin123 | ADMIN | article:read, article:create, article:update, article:delete, user:read |
| editor | admin123 | EDITOR | article:read, article:create, article:update, article:delete |
| viewer | admin123 | VIEWER | article:read |

## API 接口

### 登录

```
POST /api/auth/login
Content-Type: application/json

{
  "username": "admin",
  "password": "admin123"
}
```

响应示例：

```json
{
  "code": 200,
  "message": "登录成功",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiJ9...",
    "username": "admin",
    "roles": ["article:read", "article:create", "article:update", "article:delete", "user:read"]
  }
}
```

### 文章管理

| 方法 | 路径 | 权限 | 说明 |
|------|------|------|------|
| GET | /api/articles | article:read | 获取所有文章 |
| GET | /api/articles/{id} | article:read | 获取单篇文章 |
| POST | /api/articles | article:create | 创建文章 |
| PUT | /api/articles/{id} | article:update | 更新文章 |
| DELETE | /api/articles/{id} | article:delete | 删除文章 |

### 管理员接口

| 方法 | 路径 | 权限 | 说明 |
|------|------|------|------|
| GET | /api/admin/users | user:read | 获取用户列表 |

### 认证方式

除登录接口外，所有接口均需在请求头中携带 JWT：

```
Authorization: Bearer <your-jwt-token>
```

## 项目结构

```
rbac-demo
├── pom.xml
├── README.md
├── src/main/java/com/example/rbac
│   ├── RbacApplication.java
│   ├── config
│   │   ├── SecurityConfig.java
│   │   └── JwtAuthenticationFilter.java
│   ├── controller
│   │   ├── AuthController.java
│   │   ├── ArticleController.java
│   │   └── AdminController.java
│   ├── entity
│   │   ├── User.java
│   │   ├── Role.java
│   │   ├── Permission.java
│   │   ├── Article.java
│   │   ├── UserRole.java
│   │   ├── UserRoleId.java
│   │   ├── RolePermission.java
│   │   └── RolePermissionId.java
│   ├── repository
│   │   ├── UserRepository.java
│   │   ├── RoleRepository.java
│   │   ├── PermissionRepository.java
│   │   ├── ArticleRepository.java
│   │   ├── UserRoleRepository.java
│   │   └── RolePermissionRepository.java
│   ├── service
│   │   ├── CustomUserDetailsService.java
│   │   ├── JwtTokenProvider.java
│   │   ├── ArticleService.java
│   │   └── UserService.java
│   ├── dto
│   │   ├── LoginRequest.java
│   │   ├── LoginResponse.java
│   │   ├── ArticleRequest.java
│   │   └── ApiResponse.java
│   └── exception
│       ├── GlobalExceptionHandler.java
│       └── UnauthorizedException.java
└── src/main/resources
    ├── application.yml
    └── db/migration
        ├── V1__create_tables.sql
        └── V2__seed_data.sql
```

## 数据库表结构

- **users** - 用户表
- **roles** - 角色表
- **permissions** - 权限表
- **user_roles** - 用户-角色关联表
- **role_permissions** - 角色-权限关联表
- **articles** - 文章表

## 权限模型

```
User → UserRole → Role → RolePermission → Permission (resource:action)
```

用户通过角色间接关联权限，实现标准的 RBAC 模型。
