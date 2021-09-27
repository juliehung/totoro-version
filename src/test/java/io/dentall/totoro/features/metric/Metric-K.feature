@metric
Feature: 指標K

    Scenario: K12 主體診所
        Given 設定指標主體類型為診所
        Given 載入指定檔案資料集 metric_k12_data_set.csv
        Then 指定執行日期 2021-09-30，檢查指標 K12Formula，計算結果數值應為 0.11
