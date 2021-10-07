@metric @report
Feature: 台北區指標

    Scenario Outline: 台北區指標
        Given 設定指標主體類型為醫師 趙子睿 id 1065
        Given 載入指定檔案資料集 nhi_metrics_data_set_1.csv
        Then 指定執行日期 <Date>，檢查指標 <Formula>，計算結果數值應為 <Value>
        Examples:
            | Date       | Formula     | Value   |
            | 2021-05-01 | F1h1Formula | 62190   |
            | 2021-05-01 | F1h2Formula | 1019.51 |
            | 2021-05-01 | F3h1Formula | 59240   |
            | 2021-05-01 | F4h3Formula | 59240   |
            | 2021-05-01 | I12Formula  | 15.38   |
            | 2021-05-01 | F1h3Formula | 762.50  |
            | 2021-05-01 | F1h5Formula | 0       |
            | 2021-05-01 | F1h6Formula | 0       |
            | 2021-05-01 | F3h2Formula | 0.26    |
            | 2021-05-01 | F5h3Formula | 0.00    |
