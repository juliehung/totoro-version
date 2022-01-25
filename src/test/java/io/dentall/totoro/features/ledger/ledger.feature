Feature: 收支邏輯
    Scenario: 簡單收支成功案例
        Given 建立醫師
        Given Alice 24 歲病人
        Given 為病患產生一個新的專案
            | amount | date | type | projectCode | displayName |
            | 1000   | 2021-01-01T00:00:00Z | t-1 | p-c-1 | d-n-1 |
        Given 增加一筆收支到新專案
            | charge | note | date | includeStampTax |
            | 50     | l-n-1  | 2021-01-02T00:00:00Z | true |
            | 60     | l-n-2  | 2021-01-03T00:00:00Z | true |
            | 70     | l-n-3  | 2021-01-04T00:00:00Z | false |
        Then 依專案 gid 查詢
            | amount | type | projectCode | displayName | date| charge | note | includeStampTax |
            | 1000   | t-1 | p-c-1 | d-n-1 | 2021-01-02T00:00:00Z| 50     | l-n-1 | true |
            | 1000   | t-1 | p-c-1 | d-n-1 | 2021-01-03T00:00:00Z| 60     | l-n-2 | true |
            | 1000   | t-1 | p-c-1 | d-n-1 | 2021-01-04T00:00:00Z| 70     | l-n-3 | false |
        Then 依專案 patient id 查詢
            | amount | type | projectCode | displayName | date| charge | note | includeStampTax |
            | 1000   | t-1 | p-c-1 | d-n-1 | 2021-01-02T00:00:00Z| 50     | l-n-1 | true |
            | 1000   | t-1 | p-c-1 | d-n-1 | 2021-01-03T00:00:00Z| 60     | l-n-2 | true |
            | 1000   | t-1 | p-c-1 | d-n-1 | 2021-01-04T00:00:00Z| 70     | l-n-3 | false |
        Then 依專案 doctor id 查詢
            | amount | type | projectCode | displayName | date| charge | note | includeStampTax |
            | 1000   | t-1 | p-c-1 | d-n-1 | 2021-01-02T00:00:00Z| 50     | l-n-1 | true |
            | 1000   | t-1 | p-c-1 | d-n-1 | 2021-01-03T00:00:00Z| 60     | l-n-2 | true |
            | 1000   | t-1 | p-c-1 | d-n-1 | 2021-01-04T00:00:00Z| 70     | l-n-3 | false |

    Scenario: 多醫生多病患筆下搜尋資料正確性
        Given 建立醫師
        Given Alice 24 歲病人
        Given 為病患產生一個新的專案
            | amount | date | type | projectCode | displayName |
            | 1000   | 2021-01-01T00:00:00Z | t-1 | p-c-1 | d-n-1 |
        Given 增加一筆收支到新專案
            | charge | note | date | includeStampTax |
            | 10     | l-1-n-1  | 2021-01-01T00:00:00Z | true |
            | 10     | l-1-n-2  | 2021-01-02T00:00:00Z | true |
        Given Bob 24 歲病人
        Given 為病患產生一個新的專案
            | amount | date | type | projectCode | displayName |
            | 2000   | 2021-01-01T00:00:00Z | t-1 | p-c-1 | d-n-1 |
        Given 增加一筆收支到新專案
            | charge | note | date | includeStampTax |
            | 20     | l-2-n-1  | 2021-01-01T00:00:00Z | true |
            | 20     | l-2-n-2  | 2021-01-02T00:00:00Z | false |
        Given 建立醫師
        Given Cathy 24 歲病人
        Given 為病患產生一個新的專案
            | amount | date | type | projectCode | displayName |
            | 1000   | 2021-01-01T00:00:00Z | t-1 | p-c-1 | d-n-1 |
        Given 增加一筆收支到新專案
            | charge | note | date | includeStampTax |
            | 50     | l-n-1  | 2021-01-02T00:00:00Z | true |
            | 60     | l-n-2  | 2021-01-03T00:00:00Z | true |
            | 70     | l-n-3  | 2021-01-04T00:00:00Z | false |
        Then 依專案 gid 查詢
            | amount | type | projectCode | displayName | date| charge | note | includeStampTax |
            | 1000   | t-1 | p-c-1 | d-n-1 | 2021-01-02T00:00:00Z| 50     | l-n-1 | true |
            | 1000   | t-1 | p-c-1 | d-n-1 | 2021-01-03T00:00:00Z| 60     | l-n-2 | true |
            | 1000   | t-1 | p-c-1 | d-n-1 | 2021-01-04T00:00:00Z| 70     | l-n-3 | false |
        Then 依專案 patient id 查詢
            | amount | type | projectCode | displayName | date| charge | note | includeStampTax |
            | 1000   | t-1 | p-c-1 | d-n-1 | 2021-01-02T00:00:00Z| 50     | l-n-1 | true |
            | 1000   | t-1 | p-c-1 | d-n-1 | 2021-01-03T00:00:00Z| 60     | l-n-2 | true |
            | 1000   | t-1 | p-c-1 | d-n-1 | 2021-01-04T00:00:00Z| 70     | l-n-3 | false |
        Then 依專案 doctor id 查詢
            | amount | type | projectCode | displayName | date| charge | note | includeStampTax |
            | 1000   | t-1 | p-c-1 | d-n-1 | 2021-01-02T00:00:00Z| 50     | l-n-1 | true |
            | 1000   | t-1 | p-c-1 | d-n-1 | 2021-01-03T00:00:00Z| 60     | l-n-2 | true |
            | 1000   | t-1 | p-c-1 | d-n-1 | 2021-01-04T00:00:00Z| 70     | l-n-3 | false |

    Scenario: 簡單增加收據
        Given 建立醫師
        Given Alice 24 歲病人
        Given 為病患產生一個新的專案
            | amount | date | type | projectCode | displayName |
            | 1000   | 2021-01-01T00:00:00Z | t-1 | p-c-1 | d-n-1 |
        Given 增加一筆收支到新專案
            | charge | note | date | includeStampTax |
            | 50     | l-n-1  | 2021-01-02T00:00:00Z | true |
            | 60     | l-n-2  | 2021-01-03T00:00:00Z | true |
            | 70     | l-n-3  | 2021-01-04T00:00:00Z | false |
        Given 增加當前限定收據，包含印花總繳
        Then 依專案 gid 查詢，應當僅當前收支包含收據資料

    Scenario: 多收支多收據
        Given 建立醫師
        Given Alice 24 歲病人
        Given 為病患產生一個新的專案
            | amount | date | type | projectCode | displayName |
            | 1000   | 2021-01-01T00:00:00Z | t-1 | p-c-1 | d-n-1 |
        Given 增加一筆收支到新專案
            | charge | note | date | includeStampTax |
            | 50     | l-n-1  | 2021-01-02T00:00:00Z | true |
            | 60     | l-n-2  | 2021-01-03T00:00:00Z | true |
            | 70     | l-n-3  | 2021-01-04T00:00:00Z | false |
        Given 增加當前限定收據，包含印花總繳
        Given 增加期間限定收據2021-01-03~2021-01-04，不包含印花總繳
        Then 依專案 gid 查詢，收支包含數筆收據資料

    Scenario: 增加列印紀錄
        Given 建立醫師
        Given Alice 24 歲病人
        Given 為病患產生一個新的專案
            | amount | date | type | projectCode | displayName |
            | 1000   | 2021-01-01T00:00:00Z | t-1 | p-c-1 | d-n-1 |
        Given 增加一筆收支到新專案
            | charge | note | date | includeStampTax |
            | 70     | l-n-3  | 2021-01-04T00:00:00Z | false |
        Given 增加當前限定收據，包含印花總繳
        Given 增加列印
        Given 增加列印
        Then 依專案 gid 查詢，收支包含數筆列印紀錄資料

    Scenario: 刪除收支後，其他收支仍有收據紀錄
        Given 建立醫師
        Given Alice 24 歲病人
        Given 為病患產生一個新的專案
            | amount | date | type | projectCode | displayName |
            | 1000   | 2021-01-01T00:00:00Z | t-1 | p-c-1 | d-n-1 |
        Given 增加一筆收支到新專案
            | charge | note | date | includeStampTax |
            | 50     | l-n-1  | 2021-01-02T00:00:00Z | true |
            | 60     | l-n-2  | 2021-01-03T00:00:00Z | true |
            | 70     | l-n-3  | 2021-01-04T00:00:00Z | false |
        Given 增加當前限定收據，包含印花總繳
        Given 增加期間限定收據2021-01-01~2021-01-04，不包含印花總繳
        Then 刪除收支
        Then 依專案 gid 查詢，其他收支收據仍然存在