@metric @report
Feature: 台北區指標

    Scenario Outline: 台北區指標
        Given 設定指標主體類型為醫師 趙子睿 id 1065
        Given 載入指定檔案資料集 nhi_metrics_data_set_1.csv
        Then 指定執行日期 <Date>，檢查指標 <Formula>，計算結果數值應為 <Value>
        Examples:
            | Date       | Formula     | Value   |
            | 2021-05-01 | L1Formula   | 78245   |
            | 2021-05-01 | F1h2Formula | 1066.47 |
            | 2021-05-01 | F3h1Formula | 58040   |
            | 2021-05-01 | F4h3Formula | 58040   |
            | 2021-05-01 | I12Formula  | 15.38   |
            | 2021-05-01 | F1h3Formula | 731.52  |
            | 2021-05-01 | F2h4Formula | 0.00    |
            | 2021-05-01 | F3h2Formula | 0.26    |
            | 2021-05-01 | F5h3Formula | 0.00    |
            | 2021-05-01 | F5h4Formula | 1.89    |
            | 2021-05-01 | F5h5Formula | 1.45    |
            | 2021-05-01 | F5h6Formula | 18.04   |
            | 2021-05-01 | F5h7Formula | 0.00    |
            | 2021-05-01 | F5h8Formula | 0       |

