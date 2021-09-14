@metric @report
Feature: 高屏區指標

    Scenario Outline: 高屏區指標
        Given 設定指標主體類型為醫師 趙子睿 id 1065
        Given 載入指定檔案資料集 nhi_metrics_data_set_1.csv
        Then 指定執行日期 <Date>，檢查指標 <Formula>，計算結果數值應為 <Value>
        Examples:
            | Date       | Formula     | Value    |
            | 2021-05-01 | K1Formula   | 71213.33 |
            | 2021-05-01 | K2Formula   | 440.68   |
            | 2021-05-01 | K11Formula  | 1.08     |
            | 2021-05-01 | J2h5Formula | 15.38    |
            | 2021-05-01 | K10Formula  | 1.89     |
            | 2021-05-01 | K3Formula   | 1077.78  |
            | 2021-05-01 | K4Formula   | 25.09    |
            | 2021-05-01 | K5Formula   | 0.30     |
            | 2021-05-01 | K6Formula   | 0.00     |
            | 2021-05-01 | K7Formula   | 0.00     |
            | 2021-05-01 | K12Formula  | 0.10     |
            | 2021-05-01 | K14Formula  | 0.00     |

