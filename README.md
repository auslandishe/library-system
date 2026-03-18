# 線上圖書借閱系統

本專案使用 **Spring Boot + Vue 3 + Element Plus + MySQL** 實作一套前後端分離的圖書借閱系統，提供使用者註冊、登入、書籍查詢、借書、還書與借閱紀錄查詢等功能。

系統依題目需求實作：

- 三層式架構
- RESTful API
- Transaction
- Stored Procedure
- SQL Injection 防護
- XSS 基本防護
- JWT 驗證

---

## 技術棧

### Backend
- Java 17
- Spring Boot 3
- Spring Web
- Spring JDBC
- Spring Validation
- Spring Security
- JWT
- Maven

### Frontend
- Vue 3
- Vite
- Vue Router
- Pinia
- Axios
- Element Plus

### Database
- MySQL 8

---

## 功能說明

### 會員功能
- 使用者註冊
- 使用者登入
- Header 顯示登入使用者資訊與頭像
- JWT 驗證登入狀態

### 書籍功能
- 查詢書籍列表
- 顯示館藏位置
- 顯示館藏狀態

### 借閱功能
- 借書
- 還書
- 查詢我的借閱紀錄

---

## 系統狀態設計

館藏狀態包含以下六種：

- `AVAILABLE`：在庫
- `BORROWED`：出借中
- `SORTING`：整理中
- `LOST`：遺失
- `DAMAGED`：損毀
- `DISCARDED`：廢棄

> 實際借書僅允許 `AVAILABLE` 狀態借出。  
> 本專案目前還書後會直接更新回 `AVAILABLE`。

---

## 專案結構

```text
library-system/
├─ backend/
│  ├─ pom.xml
│  └─ src/main/
│     ├─ java/com/example/library/
│     │  ├─ common/
│     │  ├─ config/
│     │  ├─ controller/
│     │  ├─ dto/
│     │  ├─ model/
│     │  ├─ repository/
│     │  ├─ security/
│     │  ├─ service/
│     │  └─ LibraryApplication.java
│     └─ resources/
│        ├─ application.yml
│        └─ DB/
│           ├─ schema.sql
│           ├─ data.sql
│           └─ stored_procedures.sql
├─ frontend/
│  ├─ package.json
│  ├─ vite.config.js
│  └─ src/
│     ├─ api/
│     ├─ components/
│     ├─ layouts/
│     ├─ router/
│     ├─ stores/
│     ├─ utils/
│     ├─ views/
│     ├─ App.vue
│     └─ main.js
└─ README.md
```

---

## 資料表設計

### users
使用者資料表

- `user_id`
- `phone_number`
- `password`
- `user_name`
- `registration_time`
- `last_login_time`

### books
書籍基本資料表

- `isbn`
- `name`
- `author`
- `introduction`

### inventory
實體館藏資料表

- `inventory_id`
- `isbn`
- `store_time`
- `status`
- `location`

### borrowing_records
借閱紀錄表

- `record_id`
- `user_id`
- `inventory_id`
- `borrowing_time`
- `return_time`

---

## Transaction 設計

借書與還書都會同時異動多張資料表，因此使用 Transaction 避免資料不一致。

### 借書流程
借書時會同時：

1. 更新 `inventory.status = 'BORROWED'`
2. 新增一筆 `borrowing_records`

### 還書流程
還書時會同時：

1. 更新 `borrowing_records.return_time`
2. 更新 `inventory.status = 'AVAILABLE'`

本專案透過以下方式確保一致性：

- Service 層使用 `@Transactional`
- MySQL Stored Procedure 使用 `START TRANSACTION ... COMMIT`

---

## 安全性設計

### 1. SQL Injection 防護
- 使用 `JdbcTemplate` 參數化查詢
- 使用 `CallableStatement` 呼叫 Stored Procedure
- 不直接以字串拼接 SQL

### 2. XSS 防護
- 前端使用 Vue 預設資料綁定顯示內容
- 未使用 `v-html` 直接渲染不受信任資料
- 後端 API 回傳 JSON，不直接回傳 HTML

### 3. 密碼保護
- 註冊時使用 BCrypt 對密碼進行雜湊
- 資料庫僅儲存雜湊後密碼
- 不儲存明文密碼

---

## API 設計

### Auth
- `POST /api/auth/register` 註冊
- `POST /api/auth/login` 登入

### Books
- `GET /api/books` 查詢書籍列表
- `GET /api/books/my-borrows` 查詢我的借閱

### Borrow
- `POST /api/borrow` 借書
- `POST /api/borrow/return` 還書

---

## 安裝與啟動方式

### 1. 建立資料庫

請先依序執行：

1. `schema.sql`
2. `data.sql`
3. `stored_procedures.sql`

若需重建資料庫，可先執行：

```sql
DROP DATABASE IF EXISTS library_system;
```

---

### 2. 啟動後端

進入 `backend` 目錄，修改 `application.yml` 的資料庫連線資訊：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/library_system?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Taipei&characterEncoding=utf8
    username: your_username
    password: your_password
    driver-class-name: com.mysql.cj.jdbc.Driver
```

執行：

```bash
mvn spring-boot:run
```

後端預設位址：

```text
http://localhost:8080
```

---

### 3. 啟動前端

進入 `frontend` 目錄後執行：

```bash
npm install
npm run dev
```

前端預設位址：

```text
http://localhost:5173
```

---

## 測試流程

### 1. 註冊
輸入：
- 手機號碼
- 密碼
- 使用者名稱

### 2. 登入
登入成功後，系統會回傳 JWT 並儲存在 localStorage。

### 3. 查詢書籍列表
登入後可查看館藏列表與目前狀態。

### 4. 借書
若館藏狀態為 `AVAILABLE`，即可借閱。  
借閱成功後：

- `inventory.status` 變為 `BORROWED`
- 新增一筆借閱紀錄

### 5. 還書
還書成功後：

- `borrowing_records.return_time` 會更新
- `inventory.status` 變回 `AVAILABLE`

### 6. 查詢我的借閱
可查看目前尚未歸還的館藏。

---

## 畫面說明

目前系統包含以下頁面：

- 註冊頁
- 登入頁
- 書籍列表頁
- 我的借閱頁
- Header 使用者資訊區塊

> 可於此處補上專案截圖。

---

## 設計說明

### 為何使用 Stored Procedure
借書與還書都涉及多張資料表異動，使用 Stored Procedure 可集中管理資料庫端交易邏輯，提升一致性與可維護性。

### 為何使用 JWT
JWT 適合前後端分離架構，前端只需於每次請求時攜帶 token，後端即可驗證使用者身份。

### 為何使用三層式架構
將 Controller、Service、Repository 分層，可讓各層職責清楚，提升可讀性與維護性。

---

## 可再擴充方向

- 書籍搜尋功能
- 分頁查詢
- Swagger / OpenAPI 文件
- 單元測試
- Docker Compose
- 管理員後台
- 借閱歷史查詢

---

## 作者

本專案為 Java 後端工程師實作題練習作品。
