USE library_system;

INSERT INTO books (isbn, name, author, introduction) VALUES
                                                         ('9789860000001', 'Java 核心入門', '王小明', 'Java 基礎語法、物件導向與後端開發入門'),
                                                         ('9789860000002', 'Spring Boot 實戰指南', '李小華', 'Spring Boot 專案架構、RESTful API 與整合實務'),
                                                         ('9789860000003', 'Vue 3 前端開發實務', '陳小美', 'Vue 3、Composition API 與前後端串接'),
                                                         ('9789860000004', '資料庫設計與 SQL 應用', '張志遠', '資料表設計、正規化、交易控制與 SQL 操作'),
                                                         ('9789860000005', '軟體測試與品質保證', '林雅婷', '單元測試、整合測試與系統品質管理'),
                                                         ('9789860000006', '資訊安全與 Web 防護', '黃柏翰', 'SQL Injection、XSS、認證授權與常見攻擊防禦');

INSERT INTO inventory (isbn, store_time, status, location) VALUES
                                                               ('9789860000001', NOW(), 'AVAILABLE', 'A01'),
                                                               ('9789860000001', NOW(), 'AVAILABLE', 'A02'),
                                                               ('9789860000002', NOW(), 'SORTING', 'B01'),
                                                               ('9789860000003', NOW(), 'LOST', 'C01'),
                                                               ('9789860000004', NOW(), 'DAMAGED', 'D01'),
                                                               ('9789860000005', NOW(), 'DISCARDED', 'E01'),
                                                               ('9789860000006', NOW(), 'AVAILABLE', 'F01'),
                                                               ('9789860000006', NOW(), 'AVAILABLE', 'F02');