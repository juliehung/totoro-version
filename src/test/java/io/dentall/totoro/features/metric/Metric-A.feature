@metric
Feature: 指標A

    Scenario: A8 主體診所
        Given 設定指標主體類型為診所
        Given 載入指定檔案資料集 metric_a8_data_set.csv
        Then 指定執行日期 2021-09-30，檢查指標 A8Formula，計算結果數值應為 42.86

    Scenario: A15-2 主體診所
        Given 設定指標主體類型為診所
        Given 載入指定檔案資料集 metric_a15-2_data_set.csv
        Then 指定執行日期 2021-09-30，檢查指標 A15h2Formula，計算結果數值應為 5
