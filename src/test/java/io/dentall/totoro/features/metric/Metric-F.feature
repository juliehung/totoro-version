@metric
Feature: 指標F

    Scenario: F1-3 主體診所
        Given 設定指標主體類型為診所
        Given 載入指定檔案資料集 metric_f1-3_data_set.csv
        Then 指定執行日期 2021-09-30，檢查指標 F1h3Formula，計算結果數值應為 368.75

    Scenario: F2-4 主體診所
        Given 設定指標主體類型為診所
        Given 載入指定檔案資料集 metric_f2-4_data_set.csv
        Then 指定執行日期 2021-09-30，檢查指標 F2h4Formula，計算結果數值應為 25.00

    Scenario: F5-8 主體診所
        Given 設定指標主體類型為診所
        Given 載入指定檔案資料集 metric_f5-8_data_set.csv
        Then 指定執行日期 2021-09-30，檢查指標 F5h8Formula，計算結果數值應為 75.00
