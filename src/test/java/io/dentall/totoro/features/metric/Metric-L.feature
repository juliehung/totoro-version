@metric
Feature: 指標L

    Scenario: L39 主體診所
        Given 設定指標主體類型為診所
        Given 載入指定檔案資料集 metric_l39_data_set.csv
        Then 指定執行日期 2021-09-30，檢查指標 L39Formula，計算結果數值應為 1

    Scenario: L57 主體診所
        Given 設定指標主體類型為診所
        Given 載入指定檔案資料集 metric_l57_data_set.csv
        Then 指定執行日期 2021-09-30，檢查指標 L57Formula，計算結果數值應為 1660
