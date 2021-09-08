@metric @report
Feature: 東區指標

    Scenario Outline: 東區指標
        Given 設定指標主體類型為醫師 趙子睿 id 1065
        Given 載入指定檔案資料集 nhi_metrics_data_set_1.csv
        Then 指定執行日期 <Date>，檢查指標 <Formula>，計算結果數值應為 <Value>
        Examples:
            | Date       | Formula     | Value |
            | 2021-05-01 | G6Formula   | 75295 |
            | 2021-05-01 | G8h1Formula | 0.00  |
            | 2021-05-01 | G8h2Formula | 0.00  |
            | 2021-05-01 | G8h3Formula | 15.59 |
            | 2021-05-01 | G8h4Formula | 57.14 |

