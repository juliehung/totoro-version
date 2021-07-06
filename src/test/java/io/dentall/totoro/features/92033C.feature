@nhi @nhi-92-series
Feature: 92033C 牙齒切半術或牙根切斷術

    Scenario Outline: 全部檢核成功
        Given 建立醫師
        Given Scott 24 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 確認診療代碼 <IssueNhiCode> ，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            | 92033C       | 16         | DL           | Pass      |

    Scenario Outline: 提醒須檢附影像
        Given 建立醫師
        Given Scott 24 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 提醒"須檢附影像"，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            | 92033C       | 16         | MOB          | Pass      |

    Scenario Outline: 檢查治療的牙位是否為 PERMANENT_TOOTH
        Given 建立醫師
        Given Scott 24 歲病人
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
            | 92033C       | 51         | DL           | NotPass   |
            | 92033C       | 52         | DL           | NotPass   |
            | 92033C       | 53         | DL           | NotPass   |
            | 92033C       | 54         | DL           | NotPass   |
            | 92033C       | 55         | DL           | NotPass   |
            | 92033C       | 61         | DL           | NotPass   |
            | 92033C       | 62         | DL           | NotPass   |
            | 92033C       | 63         | DL           | NotPass   |
            | 92033C       | 64         | DL           | NotPass   |
            | 92033C       | 65         | DL           | NotPass   |
            | 92033C       | 71         | DL           | NotPass   |
            | 92033C       | 72         | DL           | NotPass   |
            | 92033C       | 73         | DL           | NotPass   |
            | 92033C       | 74         | DL           | NotPass   |
            | 92033C       | 75         | DL           | NotPass   |
            | 92033C       | 81         | DL           | NotPass   |
            | 92033C       | 82         | DL           | NotPass   |
            | 92033C       | 83         | DL           | NotPass   |
            | 92033C       | 84         | DL           | NotPass   |
            | 92033C       | 85         | DL           | NotPass   |
            # 恆牙
            | 92033C       | 11         | DL           | Pass      |
            | 92033C       | 12         | DL           | Pass      |
            | 92033C       | 13         | DL           | Pass      |
            | 92033C       | 14         | DL           | Pass      |
            | 92033C       | 15         | DL           | Pass      |
            | 92033C       | 16         | DL           | Pass      |
            | 92033C       | 17         | DL           | Pass      |
            | 92033C       | 18         | DL           | Pass      |
            | 92033C       | 21         | DL           | Pass      |
            | 92033C       | 22         | DL           | Pass      |
            | 92033C       | 23         | DL           | Pass      |
            | 92033C       | 24         | DL           | Pass      |
            | 92033C       | 25         | DL           | Pass      |
            | 92033C       | 26         | DL           | Pass      |
            | 92033C       | 27         | DL           | Pass      |
            | 92033C       | 28         | DL           | Pass      |
            | 92033C       | 31         | DL           | Pass      |
            | 92033C       | 32         | DL           | Pass      |
            | 92033C       | 33         | DL           | Pass      |
            | 92033C       | 34         | DL           | Pass      |
            | 92033C       | 35         | DL           | Pass      |
            | 92033C       | 36         | DL           | Pass      |
            | 92033C       | 37         | DL           | Pass      |
            | 92033C       | 38         | DL           | Pass      |
            | 92033C       | 41         | DL           | Pass      |
            | 92033C       | 42         | DL           | Pass      |
            | 92033C       | 43         | DL           | Pass      |
            | 92033C       | 44         | DL           | Pass      |
            | 92033C       | 45         | DL           | Pass      |
            | 92033C       | 46         | DL           | Pass      |
            | 92033C       | 47         | DL           | Pass      |
            | 92033C       | 48         | DL           | Pass      |
            # 無牙
            | 92033C       |            | DL           | NotPass   |
            #
            | 92033C       | 19         | DL           | Pass      |
            | 92033C       | 29         | DL           | Pass      |
            | 92033C       | 39         | DL           | Pass      |
            | 92033C       | 49         | DL           | Pass      |
            | 92033C       | 59         | DL           | NotPass   |
            | 92033C       | 69         | DL           | NotPass   |
            | 92033C       | 79         | DL           | NotPass   |
            | 92033C       | 89         | DL           | NotPass   |
            | 92033C       | 99         | DL           | Pass      |
            # 牙位為區域型態
            | 92033C       | FM         | DL           | NotPass   |
            | 92033C       | UR         | DL           | NotPass   |
            | 92033C       | UL         | DL           | NotPass   |
            | 92033C       | UA         | DL           | NotPass   |
            | 92033C       | UB         | DL           | NotPass   |
            | 92033C       | LL         | DL           | NotPass   |
            | 92033C       | LR         | DL           | NotPass   |
            | 92033C       | LA         | DL           | NotPass   |
            | 92033C       | LB         | DL           | NotPass   |
            # 非法牙位
            | 92033C       | 00         | DL           | NotPass   |
            | 92033C       | 01         | DL           | NotPass   |
            | 92033C       | 10         | DL           | NotPass   |
            | 92033C       | 56         | DL           | NotPass   |
            | 92033C       | 66         | DL           | NotPass   |
            | 92033C       | 76         | DL           | NotPass   |
            | 92033C       | 86         | DL           | NotPass   |
            | 92033C       | 91         | DL           | NotPass   |
