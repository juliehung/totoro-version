@nhi @nhi-90-series
Feature: 90112C 特殊狀況橡皮障防濕裝置

    Scenario Outline: 全部檢核成功
        Given 建立醫師
        Given Wind 24 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 確認診療代碼 <IssueNhiCode> ，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            | 90112C       | 11         | MOB          | Pass      |

    Scenario Outline: 提醒須檢附影像
        Given 建立醫師
        Given Wind 24 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 提醒"須檢附影像"，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            | 90112C       | 11         | MOB          | Pass      |

    Scenario Outline: 檢查治療的牙位是否為 GENERAL_TOOTH
        Given 建立醫師
        Given Wind 24 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 檢查 <IssueTeeth> 牙位，依 GENERAL_TOOTH 判定是否為核可牙位，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            # 乳牙
            | 90112C       | 51         | DL           | Pass      |
            | 90112C       | 52         | DL           | Pass      |
            | 90112C       | 53         | DL           | Pass      |
            | 90112C       | 54         | DL           | Pass      |
            | 90112C       | 55         | DL           | Pass      |
            | 90112C       | 61         | DL           | Pass      |
            | 90112C       | 62         | DL           | Pass      |
            | 90112C       | 63         | DL           | Pass      |
            | 90112C       | 64         | DL           | Pass      |
            | 90112C       | 65         | DL           | Pass      |
            | 90112C       | 71         | DL           | Pass      |
            | 90112C       | 72         | DL           | Pass      |
            | 90112C       | 73         | DL           | Pass      |
            | 90112C       | 74         | DL           | Pass      |
            | 90112C       | 75         | DL           | Pass      |
            | 90112C       | 81         | DL           | Pass      |
            | 90112C       | 82         | DL           | Pass      |
            | 90112C       | 83         | DL           | Pass      |
            | 90112C       | 84         | DL           | Pass      |
            | 90112C       | 85         | DL           | Pass      |
            # 恆牙
            | 90112C       | 11         | DL           | Pass      |
            | 90112C       | 12         | DL           | Pass      |
            | 90112C       | 13         | DL           | Pass      |
            | 90112C       | 14         | DL           | Pass      |
            | 90112C       | 15         | DL           | Pass      |
            | 90112C       | 16         | DL           | Pass      |
            | 90112C       | 17         | DL           | Pass      |
            | 90112C       | 18         | DL           | Pass      |
            | 90112C       | 21         | DL           | Pass      |
            | 90112C       | 22         | DL           | Pass      |
            | 90112C       | 23         | DL           | Pass      |
            | 90112C       | 24         | DL           | Pass      |
            | 90112C       | 25         | DL           | Pass      |
            | 90112C       | 26         | DL           | Pass      |
            | 90112C       | 27         | DL           | Pass      |
            | 90112C       | 28         | DL           | Pass      |
            | 90112C       | 31         | DL           | Pass      |
            | 90112C       | 32         | DL           | Pass      |
            | 90112C       | 33         | DL           | Pass      |
            | 90112C       | 34         | DL           | Pass      |
            | 90112C       | 35         | DL           | Pass      |
            | 90112C       | 36         | DL           | Pass      |
            | 90112C       | 37         | DL           | Pass      |
            | 90112C       | 38         | DL           | Pass      |
            | 90112C       | 41         | DL           | Pass      |
            | 90112C       | 42         | DL           | Pass      |
            | 90112C       | 43         | DL           | Pass      |
            | 90112C       | 44         | DL           | Pass      |
            | 90112C       | 45         | DL           | Pass      |
            | 90112C       | 46         | DL           | Pass      |
            | 90112C       | 47         | DL           | Pass      |
            | 90112C       | 48         | DL           | Pass      |
            # 無牙
            | 90112C       |            | DL           | NotPass   |
            #
            | 90112C       | 19         | DL           | Pass      |
            | 90112C       | 29         | DL           | Pass      |
            | 90112C       | 39         | DL           | Pass      |
            | 90112C       | 49         | DL           | Pass      |
            | 90112C       | 59         | DL           | NotPass   |
            | 90112C       | 69         | DL           | NotPass   |
            | 90112C       | 79         | DL           | NotPass   |
            | 90112C       | 89         | DL           | NotPass   |
            | 90112C       | 99         | DL           | Pass      |
            # 牙位為區域型態
            | 90112C       | FM         | DL           | NotPass   |
            | 90112C       | UR         | DL           | NotPass   |
            | 90112C       | UL         | DL           | NotPass   |
            | 90112C       | UA         | DL           | NotPass   |
            | 90112C       | UB         | DL           | NotPass   |
            | 90112C       | LL         | DL           | NotPass   |
            | 90112C       | LR         | DL           | NotPass   |
            | 90112C       | LA         | DL           | NotPass   |
            | 90112C       | LB         | DL           | NotPass   |
            # 非法牙位
            | 90112C       | 00         | DL           | NotPass   |
            | 90112C       | 01         | DL           | NotPass   |
            | 90112C       | 10         | DL           | NotPass   |
            | 90112C       | 56         | DL           | NotPass   |
            | 90112C       | 66         | DL           | NotPass   |
            | 90112C       | 76         | DL           | NotPass   |
            | 90112C       | 86         | DL           | NotPass   |
            | 90112C       | 91         | DL           | NotPass   |
