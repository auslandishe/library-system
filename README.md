📚 Library Management System（線上圖書借閱系統）

一個基於 Spring Boot + MySQL + Vue.js 的線上圖書借閱管理系統，提供書籍管理、借閱流程與使用者操作功能。

🚀 專案特色

📖 書籍管理（新增 / 修改 / 刪除 / 查詢）

📦 書籍庫存數量管理（同書名不重複）

🔄 借閱與歸還流程

👤 使用者管理

📊 書籍狀態控管：

在庫

出借中

整理中（歸還後未入庫）

遺失

損毀

🌐 前後端分離架構

🛠️ 技術架構
Backend（後端）

Java 17

Spring Boot

Spring Data JPA

MySQL

Frontend（前端）

Vue.js

Axios

Element UI（或你用的 UI 套件）

📂 專案結構
library-system/
│
├── backend/                  # Spring Boot 後端
│   ├── controller/
│   ├── service/
│   ├── repository/
│   ├── entity/
│   └── config/
│
├── frontend/                 # Vue 前端
│   ├── src/
│   │   ├── views/
│   │   ├── components/
│   │   └── api/
│
└── README.md
⚙️ 安裝與執行
1️⃣ Clone 專案
git clone https://github.com/你的帳號/library-system.git
cd library-system
2️⃣ 資料庫設定（MySQL）

建立資料庫：

CREATE DATABASE library_system;

修改 application.yml 或 application.properties：

spring.datasource.url=jdbc:mysql://localhost:3306/library_system
spring.datasource.username=root
spring.datasource.password=你的密碼

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
3️⃣ 啟動後端（Spring Boot）
cd backend
./mvnw spring-boot:run

或用 IntelliJ 直接執行 Application.java

4️⃣ 啟動前端（Vue）
cd frontend
npm install
npm run serve

預設：

http://localhost:8080
📌 API 簡介
📚 書籍相關
方法	API	說明
GET	/books	取得所有書籍
POST	/books	新增書籍
PUT	/books/{id}	更新書籍
DELETE	/books/{id}	刪除書籍
🔄 借閱相關
方法	API	說明
POST	/borrow	借書
POST	/return	還書
📖 書籍狀態說明
狀態	說明
在庫	可借閱
出借中	已借出
整理中	已歸還但尚未入庫
遺失	書籍遺失
損毀	書籍損壞
