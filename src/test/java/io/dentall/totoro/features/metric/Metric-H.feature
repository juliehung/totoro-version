@metric
Feature: 指標H

    Scenario: H3 主體診所
        Given 設定指標主體類型為診所
        Given 載入指定檔案資料集 metric_h3_data_set.csv
        Then 指定執行日期 2021-09-30，檢查指標 H3Formula，計算結果數值應為 69.30

    Scenario: H4 主體診所
        Given 設定指標主體類型為診所
        Given 載入指定檔案資料集 metric_h4_data_set.csv
        Then 指定執行日期 2021-09-30，檢查指標 H4Formula，計算結果數值應為 -100.00

