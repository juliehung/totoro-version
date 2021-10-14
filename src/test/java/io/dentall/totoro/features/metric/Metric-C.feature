@metric
Feature: 指標C

    Scenario: C1-1 主體病人 Terry
        Given 設定指標主體類型為病人 Terry
        Given 載入指定檔案資料集 metric_c1-1_data_set.csv
        Then 指定執行日期 2021-09-01，檢查指標 C1h1Formula，計算結果數值應為 1890

    Scenario: C1-3 主體病人 Terry
        Given 設定指標主體類型為病人 Terry
        Given 載入指定檔案資料集 metric_c1-3_data_set.csv
        Then 指定執行日期 2021-09-01，檢查指標 C1h3Formula，計算結果數值應為 2960

    Scenario: C2-2 主體診所
        Given 設定指標主體類型為診所
        Given 載入指定檔案資料集 metric_c2-2_data_set.csv
        Then 指定執行日期 2021-09-01，檢查指標 C2h2Formula，計算結果數值應為 7

    Scenario: C2-3 主體診所
        Given 設定指標主體類型為診所
        Given 載入指定檔案資料集 metric_c2-3_data_set.csv
        Then 指定執行日期 2021-09-01，檢查指標 C2h3Formula，計算結果數值應為 7

    Scenario: C2-4 主體病人 Terry
        Given 設定指標主體類型為病人 Terry
        Given 載入指定檔案資料集 metric_c2-4_data_set.csv
        Then 指定執行日期 2021-09-01，檢查指標 C2h4Formula，計算結果數值應為 4850

    Scenario: C2-5 主體病人 Terry
        Given 設定指標主體類型為病人 Terry
        Given 載入指定檔案資料集 metric_c2-5_data_set.csv
        Then 指定執行日期 2021-09-01，檢查指標 C2h5Formula，計算結果數值應為 4600

    Scenario: C2-6 主體診所
        Given 設定指標主體類型為診所
        Given 載入指定檔案資料集 metric_c2-6_data_set.csv
        Then 指定執行日期 2021-09-01，檢查指標 C2h6Formula，計算結果數值應為 2
