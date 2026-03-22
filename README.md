# LeadBot · AI 智能获客机器人系统

一套面向销售/运营团队的 **AI 智能获客系统**，包含：

- 手机端风格前端工作台（Vue 3 + Vite）
- Spring Boot 后端服务
- MySQL 数据库存储
- JWT 登录鉴权
- 角色权限控制
- 线索管理、跟进记录、团队成员、渠道预算、机器人规则等模块

---

## 1. 项目概述

LeadBot 是一个用于销售线索发现、客户跟进、AI 话术辅助和内部协作管理的系统原型/开发版，适合以下场景：

- 教育培训机构获客
- 本地生活门店拉新
- 医疗、口腔、咨询行业线索转化
- 私域线索管理和销售跟进

系统当前包含：

### 前端功能
- 登录页
- 工作台首页
- 线索管理
- AI 助手
- 任务中心
- 我的页面
- 数据报表页
- 团队成员页
- 客户详情弹窗
- 跟进记录弹窗
- 客户建档流程
- 客户分层页
- 渠道预算设置
- 机器人规则编辑
- 修改密码
- 权限控制
- Loading / Empty / Error 状态统一

### 后端功能
- JWT 登录认证
- 用户信息获取
- 修改密码
- 线索 CRUD
- 跟进日志
- 任务管理
- 聊天消息
- 团队成员 CRUD
- 渠道配置
- 规则配置
- 操作日志
- Swagger 接口文档
- Flyway 数据库迁移
- BCrypt 密码加密
- 角色权限控制（ADMIN / MANAGER / USER）

---

## 2. 技术栈

### 前端
- Vue 3
- Vite
- Vue Router
- Axios
- 原生 Canvas 图表
- 本地持久化（localStorage，mock 场景）

### 后端
- Java 17
- Spring Boot 3
- Spring Web
- Spring Security
- Spring Data JPA
- JWT
- Flyway
- MapStruct
- Lombok
- Swagger / OpenAPI

### 数据库
- MySQL 8
- H2（开发演示可选）

---

## 3. 项目目录说明

### 前端目录（示例）

```text
leadbot-vue/
├─ src/
│  ├─ api/
│  ├─ components/
│  ├─ composables/
│  ├─ router/
│  ├─ store/
│  ├─ utils/
│  ├─ views/
│  ├─ App.vue
│  ├─ main.js
│  └─ style.css
├─ .env.development
├─ .env.production
├─ package.json
└─ vite.config.js
```

### 后端目录（示例）

```text
leadbot-backend/
├─ src/main/java/com/example/leadbot/
│  ├─ auth/
│  ├─ user/
│  ├─ lead/
│  ├─ log/
│  ├─ task/
│  ├─ chat/
│  ├─ member/
│  ├─ channel/
│  ├─ rule/
│  ├─ security/
│  ├─ config/
│  ├─ common/
│  └─ init/
├─ src/main/resources/
│  ├─ application.yml
│  ├─ application-dev.yml
│  ├─ application-prod.yml
│  └─ db/migration/
├─ pom.xml
└─ README.md
```

---

## 4. 环境要求

### 前端
- Node.js 18+
- npm 9+

### 后端
- JDK 17
- Maven 3.8+

### 数据库
- MySQL 8.0+

---

## 5. 本地启动方式

### 5.1 启动后端

进入后端目录：

```bash
cd leadbot-backend
mvn clean package -DskipTests
mvn spring-boot:run
```

默认端口：

```text
http://localhost:8080
```

Swagger 地址：

```text
http://localhost:8080/swagger-ui.html
http://localhost:8080/v3/api-docs
```

---

### 5.2 启动前端

进入前端目录：

```bash
cd leadbot-vue
npm install
npm run dev
```

默认地址：

```text
http://localhost:5173
```

---

## 6. 前端环境变量

### 开发环境 `.env.development`

```env
VITE_API_MODE=real
VITE_API_BASE_URL=http://localhost:8080
VITE_APP_TITLE=LeadBot Dev
```

### 生产环境 `.env.production`

```env
VITE_API_MODE=real
VITE_API_BASE_URL=/api
VITE_APP_TITLE=LeadBot
```

---

## 7. 默认账号

如果启用了后端初始化逻辑，可使用以下测试账号：

```text
管理员：
账号：admin
密码：123456

经理：
账号：manager
密码：123456

普通用户：
账号：user
密码：123456
```

> 生产环境请务必修改默认密码。

---

## 8. 角色权限说明

### ADMIN
- 全部权限
- 用户管理
- 团队成员管理
- 渠道管理
- 规则管理
- 线索管理
- 删除客户
- 查看报表

### MANAGER
- 业务管理权限
- 线索管理
- 跟进日志
- 任务管理
- AI 助手
- 查看报表
- 渠道查看/更新
- 规则查看/更新
- 删除客户

### USER
- 基础业务权限
- 线索查看/编辑
- 跟进记录
- 任务管理
- AI 助手
- 个人信息
- 无法访问：
  - 团队成员页
  - 渠道设置
  - 规则设置
  - 报表页
  - 删除客户

---

## 9. 主要接口清单

### 认证
- `POST /auth/login`
- `GET /auth/profile`
- `POST /auth/change-password`

### 线索
- `GET /leads`
- `POST /leads`
- `PATCH /leads/{id}`
- `DELETE /leads/{id}`

### 跟进记录
- `GET /leads/{id}/logs`
- `POST /leads/{id}/logs`

### 任务
- `GET /tasks`
- `PATCH /tasks/{id}`

### 聊天
- `GET /chats`
- `POST /chats`

### 团队成员
- `GET /members`
- `POST /members`
- `PATCH /members/{id}`
- `DELETE /members/{id}`

### 渠道设置
- `GET /channels`
- `PATCH /channels/{id}`

### 规则设置
- `GET /rules`
- `PATCH /rules`

---

## 10. 统一返回格式

成功：

```json
{
  "code": 0,
  "message": "ok",
  "success": true,
  "data": {}
}
```

失败：

```json
{
  "code": 401,
  "message": "未登录或登录已过期",
  "success": false
}
```

---

## 11. 分页说明（线索列表）

线索接口支持参数：

- `keyword`
- `level`
- `page`
- `pageSize`

返回结构：

```json
{
  "code": 0,
  "message": "ok",
  "success": true,
  "data": {
    "list": [],
    "total": 100,
    "page": 1,
    "pageSize": 10
  }
}
```

---

## 12. 数据库说明

推荐使用：

- MySQL 8
- 字符集 `utf8mb4`

数据库脚本建议拆分为：

```text
sql/
├─ 01_schema.sql
└─ 02_seed.sql
```

或使用 Flyway：

```text
src/main/resources/db/migration/
├─ V1__init.sql
└─ V2__seed_data.sql
```

---

## 13. 部署建议

### 前端
- 执行 `npm run build`
- 生成 `dist/`
- 使用 Nginx 托管静态文件

### 后端
- 执行 `mvn clean package -DskipTests`
- 生成 jar 包
- 使用 `java -jar` 方式运行

### 推荐生产架构
- Nginx
- Spring Boot
- MySQL
- Docker Compose（可选）

---

## 14. Nginx 反向代理建议

前端请求统一走 `/api`，Nginx 转发到后端：

```nginx
server {
    listen 80;
    server_name your-domain.com;

    root /opt/leadbot/frontend/dist;
    index index.html;

    location / {
        try_files $uri $uri/ /index.html;
    }

    location /api/ {
        proxy_pass http://127.0.0.1:8080/;
        proxy_http_version 1.1;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
    }
}
```

---

## 15. Docker Compose 建议

推荐容器化部署：

- `mysql`
- `backend`
- `frontend`

启动命令：

```bash
docker compose build
docker compose up -d
```

---

## 16. 联调检查清单

```text
[ ] 前端 VITE_API_BASE_URL 配置正确
[ ] 后端端口 8080 正常启动
[ ] MySQL 可连接
[ ] Flyway 已成功迁移
[ ] Swagger 可访问
[ ] 登录成功
[ ] profile 接口正常
[ ] leads 分页 / 搜索 / 筛选正常
[ ] tasks 更新正常
[ ] chats 发送正常
[ ] logs 查看/新增正常
[ ] members / channels / rules 权限控制正常
[ ] 前端角色菜单显示正确
[ ] 401 / 403 表现正常
```

---

## 17. 常见问题

### 1）登录失败
检查：
- 用户是否初始化成功
- BCrypt 密码是否匹配
- token 是否返回

### 2）接口 401
检查：
- 前端是否携带 `Authorization: Bearer xxx`
- JWT secret 是否一致
- token 是否过期

### 3）接口 403
检查：
- 当前账号角色
- 后端角色控制
- 前端权限显示逻辑

### 4）前端页面空白
检查：
- Vite 环境变量
- `VITE_API_BASE_URL`
- 路由守卫是否跳到登录页
- 浏览器 Console 和 Network

### 5）Flyway 执行失败
检查：
- 旧表结构是否冲突
- 开发环境是否混用了 `ddl-auto=update`
- 是否重复执行了旧初始化 SQL

---

## 18. 后续可扩展方向

- 用户管理后台
- 客户归属人
- 渠道成本统计
- AI 模型接入（OpenAI / 通义 / 千帆）
- 文件上传（客户资料、合同）
- Excel 导入导出
- 操作日志查询
- 更细粒度权限控制
- 移动端 App 打包

---

## 19. 交付建议

如果是交付给测试/客户演示，建议同时提供：

- 前端源码包
- 后端源码包
- 数据库 SQL
- README 文档
- 默认测试账号
- 接口文档地址
- 部署说明

---

## 20. 当前版本说明

当前版本更偏向：
- **业务系统原型增强版**
- **可继续开发的前后端基础框架**
- **适合作为 MVP / DEMO / 内部管理系统起点**

如果需要上线生产，建议进一步补充：

- 更严格的权限模型
- 审计日志页面
- 数据备份方案
- 监控与告警
- 单元测试 / 集成测试
- CI/CD 发布流程
```

如果你愿意，下一条我可以继续给你：
- **Apifox 导入说明模板**
- 或 **项目交付邮件模板**