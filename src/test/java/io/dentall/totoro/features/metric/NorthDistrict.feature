@metric @report
Feature: 北區指標

    Scenario Outline: 北區指標
        Given 設定指標主體類型為醫師 趙子睿 id 1065
        Given 載入指定檔案資料集 nhi_metrics_data_set_1.csv
        Then 指定執行日期 <Date>，檢查指標 <Formula>，計算結果數值應為 <Value>
        Examples:
            | Date       | Formula      | Value   |
            | 2021-05-01 | A10Formula   | 61240   |
            | 2021-05-01 | A7Formula    | 1322.05 |
            | 2021-05-01 | A8Formula    | 38.10   |
            | 2021-05-01 | A14Formula   | 0.00    |
            | 2021-05-01 | A15h1Formula | 0       |
            | 2021-05-01 | A17h2Formula | 0       |
            | 2021-05-01 | A9Formula    | 18.10   |
            | 2021-05-01 | A18h1Formula | 0       |
            | 2021-05-01 | A18h2Formula | 0       |

