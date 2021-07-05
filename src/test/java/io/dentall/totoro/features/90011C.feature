@nhi-90-series
Feature: 90011C 牙齒再植術

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
            | 90011C       | 11         | MOB          | Pass      |

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
            | 90011C       | 11         | MOB          | Pass      |

    Scenario Outline: 檢查治療的牙位是否為 PERMANENT_TOOTH
        Given 建立醫師
        Given Wind 24 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 檢查 <IssueTeeth> 牙位，依 PERMANENT_TOOTH 判定是否為核可牙位，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            # 乳牙
            | 90011C       | 51         | DL           | NotPass   |
            | 90011C       | 52         | DL           | NotPass   |
            | 90011C       | 53         | DL           | NotPass   |
            | 90011C       | 54         | DL           | NotPass   |
            | 90011C       | 55         | DL           | NotPass   |
            | 90011C       | 61         | DL           | NotPass   |
            | 90011C       | 62         | DL           | NotPass   |
            | 90011C       | 63         | DL           | NotPass   |
            | 90011C       | 64         | DL           | NotPass   |
            | 90011C       | 65         | DL           | NotPass   |
            | 90011C       | 71         | DL           | NotPass   |
            | 90011C       | 72         | DL           | NotPass   |
            | 90011C       | 73         | DL           | NotPass   |
            | 90011C       | 74         | DL           | NotPass   |
            | 90011C       | 75         | DL           | NotPass   |
            | 90011C       | 81         | DL           | NotPass   |
            | 90011C       | 82         | DL           | NotPass   |
            | 90011C       | 83         | DL           | NotPass   |
            | 90011C       | 84         | DL           | NotPass   |
            | 90011C       | 85         | DL           | NotPass   |
            # 恆牙
            | 90011C       | 11         | DL           | Pass      |
            | 90011C       | 12         | DL           | Pass      |
            | 90011C       | 13         | DL           | Pass      |
            | 90011C       | 14         | DL           | Pass      |
            | 90011C       | 15         | DL           | Pass      |
            | 90011C       | 16         | DL           | Pass      |
            | 90011C       | 17         | DL           | Pass      |
            | 90011C       | 18         | DL           | Pass      |
            | 90011C       | 21         | DL           | Pass      |
            | 90011C       | 22         | DL           | Pass      |
            | 90011C       | 23         | DL           | Pass      |
            | 90011C       | 24         | DL           | Pass      |
            | 90011C       | 25         | DL           | Pass      |
            | 90011C       | 26         | DL           | Pass      |
            | 90011C       | 27         | DL           | Pass      |
            | 90011C       | 28         | DL           | Pass      |
            | 90011C       | 31         | DL           | Pass      |
            | 90011C       | 32         | DL           | Pass      |
            | 90011C       | 33         | DL           | Pass      |
            | 90011C       | 34         | DL           | Pass      |
            | 90011C       | 35         | DL           | Pass      |
            | 90011C       | 36         | DL           | Pass      |
            | 90011C       | 37         | DL           | Pass      |
            | 90011C       | 38         | DL           | Pass      |
            | 90011C       | 41         | DL           | Pass      |
            | 90011C       | 42         | DL           | Pass      |
            | 90011C       | 43         | DL           | Pass      |
            | 90011C       | 44         | DL           | Pass      |
            | 90011C       | 45         | DL           | Pass      |
            | 90011C       | 46         | DL           | Pass      |
            | 90011C       | 47         | DL           | Pass      |
            | 90011C       | 48         | DL           | Pass      |
            # 無牙
            | 90011C       |            | DL           | NotPass   |
            #
            | 90011C       | 19         | DL           | Pass      |
            | 90011C       | 29         | DL           | Pass      |
            | 90011C       | 39         | DL           | Pass      |
            | 90011C       | 49         | DL           | Pass      |
            | 90011C       | 59         | DL           | NotPass   |
            | 90011C       | 69         | DL           | NotPass   |
            | 90011C       | 79         | DL           | NotPass   |
            | 90011C       | 89         | DL           | NotPass   |
            | 90011C       | 99         | DL           | Pass      |
            # 牙位為區域型態
            | 90011C       | FM         | DL           | NotPass   |
            | 90011C       | UR         | DL           | NotPass   |
            | 90011C       | UL         | DL           | NotPass   |
            | 90011C       | UA         | DL           | NotPass   |
            | 90011C       | UB         | DL           | NotPass   |
            | 90011C       | LL         | DL           | NotPass   |
            | 90011C       | LR         | DL           | NotPass   |
            | 90011C       | LA         | DL           | NotPass   |
            | 90011C       | LB         | DL           | NotPass   |
            # 非法牙位
            | 90011C       | 00         | DL           | NotPass   |
            | 90011C       | 01         | DL           | NotPass   |
            | 90011C       | 10         | DL           | NotPass   |
            | 90011C       | 56         | DL           | NotPass   |
            | 90011C       | 66         | DL           | NotPass   |
            | 90011C       | 76         | DL           | NotPass   |
            | 90011C       | 86         | DL           | NotPass   |
            | 90011C       | 91         | DL           | NotPass   |
