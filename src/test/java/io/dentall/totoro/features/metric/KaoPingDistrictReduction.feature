@metric @report
Feature: 高屏區指標 - 減量

    Scenario Outline: 高屏區指標 - 減量
        Given 設定指標主體類型為醫師 趙子睿 id 1065
        Given 載入指定檔案資料集 nhi_metrics_data_set_1.csv
        Then 指定執行日期 <Date>，檢查指標 <Formula>，計算結果數值應為 <Value>
        Examples:
            | Date       | Formula     | Value    |
            | 2021-05-01 | J1h1Formula | 80645    |
            | 2021-05-01 | J2h2Formula | 78793.33 |
            | 2021-05-01 | J2h3Formula | 486.16   |
            | 2021-05-01 | J2h4Formula | 1.00     |
            | 2021-05-01 | J2h5Formula | 15.38    |

    Scenario Outline: 高屏區指標 - 減量
        Given 設定指標主體類型為診所
        Given 載入指定檔案資料集 nhi_metrics_data_set_1.csv
        Then 指定執行日期 <Date>，檢查指標 <Formula>，計算結果數值應為 <Value>
        Examples:
            | Date       | Formula     | Value |
            | 2021-05-01 | J1h2Formula | 0     |

