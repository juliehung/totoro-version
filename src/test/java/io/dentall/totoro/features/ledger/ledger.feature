Feature: 收支邏輯
    Scenario: 簡單收支成功案例
        Given 建立醫師
        Given Stan 24 歲病人
        Given 為病患產生一個新的專案
            | id | amount | date | type | projectCode | displayName |
            | 1  | 1000   | 2021-01-01T00:00:00Z | t-1 | p-c-1 | d-n-1 |
        Given 增加一筆收支到新專案
            | gid | charge | note | date | includeStampTax |
            | 1   | 50     | l-n-1  | 2021-01-02T00:00:00Z | true |
            | 1   | 60     | l-n-2  | 2021-01-03T00:00:00Z | true |
            | 1   | 70     | l-n-3  | 2021-01-04T00:00:00Z | false |
        Then 依專案 id 查詢
            | gid | amount | type | projectCode | displayName | date| charge | note | includeStampTax |
            | 1  | 1000   | t-1 | p-c-1 | d-n-1 | 2021-01-02T00:00:00Z| 50     | l-n-1 | true |
            | 1  | 1000   | t-1 | p-c-1 | d-n-1 | 2021-01-03T00:00:00Z| 60     | l-n-2 | true |
            | 1  | 1000   | t-1 | p-c-1 | d-n-1 | 2021-01-04T00:00:00Z| 70     | l-n-3 | false |
