@metric
Feature: 指標G

    Scenario: G8-4 主體診所
        Given 設定指標主體類型為診所
        Given 載入指定檔案資料集 metric_g8-4_data_set.csv
        Then 指定執行日期 2021-09-01，檢查指標 G8h4Formula，計算結果數值應為 -100.00
