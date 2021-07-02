@nhi-90-series
Feature: 90013C 根尖成形術或根尖生成術-前牙

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
            | 90013C       | 11         | MOB          | Pass      |

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
            | 90013C       | 11         | MOB          | Pass      |

    Scenario Outline: 檢查治療的牙位是否為 PERMANENT_FRONT_TOOTH
        Given 建立醫師
        Given Wind 24 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 檢查 <IssueTeeth> 牙位，依 PERMANENT_FRONT_TOOTH 判定是否為核可牙位，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            # 乳牙
            | 90013C       | 51         | DL           | NotPass   |
            | 90013C       | 52         | DL           | NotPass   |
            | 90013C       | 53         | DL           | NotPass   |
            | 90013C       | 54         | DL           | NotPass   |
            | 90013C       | 55         | DL           | NotPass   |
            | 90013C       | 61         | DL           | NotPass   |
            | 90013C       | 62         | DL           | NotPass   |
            | 90013C       | 63         | DL           | NotPass   |
            | 90013C       | 64         | DL           | NotPass   |
            | 90013C       | 65         | DL           | NotPass   |
            | 90013C       | 71         | DL           | NotPass   |
            | 90013C       | 72         | DL           | NotPass   |
            | 90013C       | 73         | DL           | NotPass   |
            | 90013C       | 74         | DL           | NotPass   |
            | 90013C       | 75         | DL           | NotPass   |
            | 90013C       | 81         | DL           | NotPass   |
            | 90013C       | 82         | DL           | NotPass   |
            | 90013C       | 83         | DL           | NotPass   |
            | 90013C       | 84         | DL           | NotPass   |
            | 90013C       | 85         | DL           | NotPass   |
            # 恆牙
            | 90013C       | 11         | DL           | Pass      |
            | 90013C       | 12         | DL           | Pass      |
            | 90013C       | 13         | DL           | Pass      |
            | 90013C       | 14         | DL           | NotPass   |
            | 90013C       | 15         | DL           | NotPass   |
            | 90013C       | 16         | DL           | NotPass   |
            | 90013C       | 17         | DL           | NotPass   |
            | 90013C       | 18         | DL           | NotPass   |
            | 90013C       | 21         | DL           | Pass      |
            | 90013C       | 22         | DL           | Pass      |
            | 90013C       | 23         | DL           | Pass      |
            | 90013C       | 24         | DL           | NotPass   |
            | 90013C       | 25         | DL           | NotPass   |
            | 90013C       | 26         | DL           | NotPass   |
            | 90013C       | 27         | DL           | NotPass   |
            | 90013C       | 28         | DL           | NotPass   |
            | 90013C       | 31         | DL           | Pass      |
            | 90013C       | 32         | DL           | Pass      |
            | 90013C       | 33         | DL           | Pass      |
            | 90013C       | 34         | DL           | NotPass   |
            | 90013C       | 35         | DL           | NotPass   |
            | 90013C       | 36         | DL           | NotPass   |
            | 90013C       | 37         | DL           | NotPass   |
            | 90013C       | 38         | DL           | NotPass   |
            | 90013C       | 41         | DL           | Pass      |
            | 90013C       | 42         | DL           | Pass      |
            | 90013C       | 43         | DL           | Pass      |
            | 90013C       | 44         | DL           | NotPass   |
            | 90013C       | 45         | DL           | NotPass   |
            | 90013C       | 46         | DL           | NotPass   |
            | 90013C       | 47         | DL           | NotPass   |
            | 90013C       | 48         | DL           | NotPass   |
            # 無牙
            | 90013C       |            | DL           | NotPass   |
            #
            | 90013C       | 19         | DL           | Pass      |
            | 90013C       | 29         | DL           | Pass      |
            | 90013C       | 39         | DL           | Pass      |
            | 90013C       | 49         | DL           | Pass      |
            | 90013C       | 59         | DL           | NotPass   |
            | 90013C       | 69         | DL           | NotPass   |
            | 90013C       | 79         | DL           | NotPass   |
            | 90013C       | 89         | DL           | NotPass   |
            | 90013C       | 99         | DL           | Pass      |
            # 牙位為區域型態
            | 90013C       | FM         | DL           | NotPass   |
            | 90013C       | UR         | DL           | NotPass   |
            | 90013C       | UL         | DL           | NotPass   |
            | 90013C       | UA         | DL           | NotPass   |
            | 90013C       | UB         | DL           | NotPass   |
            | 90013C       | LL         | DL           | NotPass   |
            | 90013C       | LR         | DL           | NotPass   |
            | 90013C       | LA         | DL           | NotPass   |
            | 90013C       | LB         | DL           | NotPass   |
            # 非法牙位
            | 90013C       | 00         | DL           | NotPass   |
            | 90013C       | 01         | DL           | NotPass   |
            | 90013C       | 10         | DL           | NotPass   |
            | 90013C       | 56         | DL           | NotPass   |
            | 90013C       | 66         | DL           | NotPass   |
            | 90013C       | 76         | DL           | NotPass   |
            | 90013C       | 86         | DL           | NotPass   |
            | 90013C       | 91         | DL           | NotPass   |
