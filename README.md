# cathybank API Project

此專案為使用 Spring Boot + Maven 所建置的練習專案，實作內容包含呼叫 Coindesk API、幣別中文維護、資料轉換以及 CRUD API，並搭配 H2 資料庫儲存幣別中文資訊。

## 🔧 專案規格

| 項目              | 說明                         |
|------------------|------------------------------|
| JDK              | 8                            |
| Build Tool       | Maven                        |
| Framework        | Spring Boot                  |
| ORM              | Spring Data JPA              |
| 資料庫            | H2                           |

---

## 📦 功能說明

### ✅ 幣別維護功能（Currency CRUD）

- 查詢幣別列表
- 新增幣別
- 修改幣別
- 刪除幣別

### ✅ 呼叫 Coindesk 原始 API

- API 路徑：`https://kengp3.github.io/blog/coindesk.json`
- 包含：時間、幣別代號、匯率等原始資料

### ✅ 轉換後 API 格式

- API 路徑：`/api/coindesk/transformed`
- 內容包含：
  - 更新時間（格式：yyyy/MM/dd HH:mm:ss）
  - 幣別代碼
  - 幣別中文名稱（來自資料庫）
  - 幣值

---

## 🔗 API 文件

| 方法 | 路徑                              | 說明                 |
|------|-----------------------------------|----------------------|
| GET  | `/api/currency`                  | 查詢所有幣別         |
| GET  | `/api/currency/{code}`           | 查詢單一幣別         |
| POST | `/api/currency`                  | 新增幣別             |
| PUT  | `/api/currency/{code}`           | 修改幣別             |
| DELETE | `/api/currency/{code}`         | 刪除幣別             |
| GET  | `/api/coindesk/original`         | 呼叫原始 Coindesk API |
| GET  | `/api/coindesk/transformed`      | 呼叫轉換後資料格式   |

---

## 🧪 測試說明

- 單元測試：驗證資料轉換邏輯與時間格式轉換
- 整合測試：測試 CRUD API 與外部 API 整合邏輯

可使用 Postman 測試 API，建議測試順序如下：

1. `GET /api/currency`
2. `POST /api/currency`
3. `GET /api/coindesk/original`
4. `GET /api/coindesk/transformed`

---

## 🛠️ 專案執行方式

1. 匯入專案到 IntelliJ IDEA 或其他 IDE
2. 執行 `CoindeskApiApplication` 主程式
3. 使用 Postman 測試 API：
   - 例如：`http://localhost:8080/api/coindesk/transformed`

---

## 🗃️ 資料表建置 SQL

```sql
CREATE TABLE currency (
  code VARCHAR(10) PRIMARY KEY,
  name_zh VARCHAR(50)
);

-- 初始化資料
INSERT INTO currency (code, name_zh) VALUES ('USD', '美元');
INSERT INTO currency (code, name_zh) VALUES ('GBP', '英鎊');
INSERT INTO currency (code, name_zh) VALUES ('EUR', '歐元');
