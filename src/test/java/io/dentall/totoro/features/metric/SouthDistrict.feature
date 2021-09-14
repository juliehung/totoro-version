@metric @report
Feature: 南區指標

    Scenario Outline: 南區指標
        Given 設定指標主體類型為醫師 趙子睿 id 1065
        Given 載入指定檔案資料集 nhi_metrics_data_set_1.csv
        Then 指定執行日期 <Date>，檢查指標 <Formula>，計算結果數值應為 <Value>
        Examples:
            | Date       | Formula    | Value  |
            | 2021-05-01 | A10Formula | 58840  |
            | 2021-05-01 | I2Formula  | 951.48 |
            | 2021-05-01 | I13Formula | 1.08   |
            | 2021-05-01 | I11Formula | 0.79   |
            | 2021-05-01 | I12Formula | 15.38  |
            | 2021-05-01 | I15Formula | 0.00   |
            | 2021-05-01 | I3Formula  | 25.43  |
            | 2021-05-01 | I4Formula  | 934.72 |
            | 2021-05-01 | I5Formula  | 0.26   |
            | 2021-05-01 | I6Formula  | 127.78 |
            | 2021-05-01 | I7Formula  | 1.89   |
            | 2021-05-01 | I8Formula  | 0.00   |
            | 2021-05-01 | I19Formula | 0.00   |
