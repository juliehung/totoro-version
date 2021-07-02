@nhi-92-series
Feature: 92092C 乳牙複雜性拔牙

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
            | 92092C       | 51         | DL           | Pass      |

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
            | 92092C       | 51         | MOB          | Pass      |

    Scenario Outline: 檢查治療的牙位是否為 DECIDUOUS_TOOTH_AND_PERMANENT_WEIRD_TOOTH
        Given 建立醫師
        Given Scott 24 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 檢查 <IssueTeeth> 牙位，依 DECIDUOUS_TOOTH_AND_PERMANENT_WEIRD_TOOTH 判定是否為核可牙位，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            # 乳牙
            | 92092C       | 51         | DL           | Pass      |
            | 92092C       | 52         | DL           | Pass      |
            | 92092C       | 53         | DL           | Pass      |
            | 92092C       | 54         | DL           | Pass      |
            | 92092C       | 55         | DL           | Pass      |
            | 92092C       | 61         | DL           | Pass      |
            | 92092C       | 62         | DL           | Pass      |
            | 92092C       | 63         | DL           | Pass      |
            | 92092C       | 64         | DL           | Pass      |
            | 92092C       | 65         | DL           | Pass      |
            | 92092C       | 71         | DL           | Pass      |
            | 92092C       | 72         | DL           | Pass      |
            | 92092C       | 73         | DL           | Pass      |
            | 92092C       | 74         | DL           | Pass      |
            | 92092C       | 75         | DL           | Pass      |
            | 92092C       | 81         | DL           | Pass      |
            | 92092C       | 82         | DL           | Pass      |
            | 92092C       | 83         | DL           | Pass      |
            | 92092C       | 84         | DL           | Pass      |
            | 92092C       | 85         | DL           | Pass      |
            # 恆牙
            | 92092C       | 11         | DL           | NotPass   |
            | 92092C       | 12         | DL           | NotPass   |
            | 92092C       | 13         | DL           | NotPass   |
            | 92092C       | 14         | DL           | NotPass   |
            | 92092C       | 15         | DL           | NotPass   |
            | 92092C       | 16         | DL           | NotPass   |
            | 92092C       | 17         | DL           | NotPass   |
            | 92092C       | 18         | DL           | NotPass   |
            | 92092C       | 21         | DL           | NotPass   |
            | 92092C       | 22         | DL           | NotPass   |
            | 92092C       | 23         | DL           | NotPass   |
            | 92092C       | 24         | DL           | NotPass   |
            | 92092C       | 25         | DL           | NotPass   |
            | 92092C       | 26         | DL           | NotPass   |
            | 92092C       | 27         | DL           | NotPass   |
            | 92092C       | 28         | DL           | NotPass   |
            | 92092C       | 31         | DL           | NotPass   |
            | 92092C       | 32         | DL           | NotPass   |
            | 92092C       | 33         | DL           | NotPass   |
            | 92092C       | 34         | DL           | NotPass   |
            | 92092C       | 35         | DL           | NotPass   |
            | 92092C       | 36         | DL           | NotPass   |
            | 92092C       | 37         | DL           | NotPass   |
            | 92092C       | 38         | DL           | NotPass   |
            | 92092C       | 41         | DL           | NotPass   |
            | 92092C       | 42         | DL           | NotPass   |
            | 92092C       | 43         | DL           | NotPass   |
            | 92092C       | 44         | DL           | NotPass   |
            | 92092C       | 45         | DL           | NotPass   |
            | 92092C       | 46         | DL           | NotPass   |
            | 92092C       | 47         | DL           | NotPass   |
            | 92092C       | 48         | DL           | NotPass   |
            # 無牙
            | 92092C       |            | DL           | NotPass   |
            #
            | 92092C       | 19         | DL           | Pass      |
            | 92092C       | 29         | DL           | Pass      |
            | 92092C       | 39         | DL           | Pass      |
            | 92092C       | 49         | DL           | Pass      |
            | 92092C       | 59         | DL           | NotPass   |
            | 92092C       | 69         | DL           | NotPass   |
            | 92092C       | 79         | DL           | NotPass   |
            | 92092C       | 89         | DL           | NotPass   |
            | 92092C       | 99         | DL           | Pass      |
            # 牙位為區域型態
            | 92092C       | FM         | DL           | NotPass   |
            | 92092C       | UR         | DL           | NotPass   |
            | 92092C       | UL         | DL           | NotPass   |
            | 92092C       | UA         | DL           | NotPass   |
            | 92092C       | UB         | DL           | NotPass   |
            | 92092C       | LL         | DL           | NotPass   |
            | 92092C       | LR         | DL           | NotPass   |
            | 92092C       | LA         | DL           | NotPass   |
            | 92092C       | LB         | DL           | NotPass   |
            # 非法牙位
            | 92092C       | 00         | DL           | NotPass   |
            | 92092C       | 01         | DL           | NotPass   |
            | 92092C       | 10         | DL           | NotPass   |
            | 92092C       | 56         | DL           | NotPass   |
            | 92092C       | 66         | DL           | NotPass   |
            | 92092C       | 76         | DL           | NotPass   |
            | 92092C       | 86         | DL           | NotPass   |
            | 92092C       | 91         | DL           | NotPass   |
