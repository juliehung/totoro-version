@metric @report
Feature: 中區指標

    Scenario Outline: 中區指標
        Given 設定指標主體類型為醫師 趙子睿 id 1065
        Given 載入指定檔案資料集 nhi_metrics_data_set_1.csv
        Then 指定執行日期 <Date>，檢查指標 <Formula>，計算結果數值應為 <Value>
        Examples:
            | Date       | Formula   | Value  |
            | 2021-05-01 | H1Formula | 58040  |
            | 2021-05-01 | H2Formula | 951.48 |
            | 2021-05-01 | H4Formula | 35.71  |
            | 2021-05-01 | H3Formula | 18.04  |
            | 2021-05-01 | H5Formula | 0.00   |
            | 2021-05-01 | H7Formula | 0.26   |

