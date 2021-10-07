@metric
Feature: 指標E

    Scenario: E1-1 主體診所
        Given 設定指標主體類型為診所
        Given 載入指定檔案資料集 metric_e1-1_data_set.csv
        Then 指定執行日期 2021-09-30，檢查指標 E1h1Formula，計算結果數值應為 3

    Scenario: E1-3 主體診所
        Given 設定指標主體類型為診所
        Given 載入指定檔案資料集 metric_e1-3_data_set.csv
        Then 指定執行日期 2021-09-30，檢查指標 E1h3Formula，計算結果數值應為 12

    Scenario: E1-14 主體診所
        Given 設定指標主體類型為診所
        Given 載入指定檔案資料集 metric_e1-14_data_set.csv
        Then 指定執行日期 2021-09-30，檢查指標 E1h14Formula，計算結果數值應為 16.67

    Scenario: E1-16 主體診所
        Given 設定指標主體類型為診所
        Given 載入指定檔案資料集 metric_e1-16_data_set.csv
        Then 指定執行日期 2021-09-30，檢查指標 E1h16Formula，計算結果數值應為 42.86
