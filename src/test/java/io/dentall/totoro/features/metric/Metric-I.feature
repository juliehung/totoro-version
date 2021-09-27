@metric
Feature: 指標I

    Scenario: I12 主體診所
        Given 設定指標主體類型為診所
        Given 載入指定檔案資料集 metric_i12_data_set.csv
        Then 指定執行日期 2021-09-30，檢查指標 I12Formula，計算結果數值應為 -250.00

    Scenario: I13 主體診所
        Given 設定指標主體類型為診所
        Given 載入指定檔案資料集 metric_i13_data_set.csv
        Then 指定執行日期 2021-09-30，檢查指標 I13Formula，計算結果數值應為 10.00

    Scenario: I15 主體診所
        Given 設定指標主體類型為診所
        Given 載入指定檔案資料集 metric_i15_data_set.csv
        Then 指定執行日期 2021-09-30，檢查指標 I15Formula，計算結果數值應為 16.67

    Scenario: I18 主體診所
        Given 設定指標主體類型為診所
        Given 載入指定檔案資料集 metric_i18_data_set.csv
        Then 指定執行日期 2021-09-30，檢查指標 I18Formula，計算結果數值應為 86.81
