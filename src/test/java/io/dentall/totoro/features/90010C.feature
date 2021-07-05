@nhi-90-series
Feature: 90010C 根尖逆充填術

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
            | 90010C       | 11         | MOB          | Pass      |

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
            | 90010C       | 11         | MOB          | Pass      |

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
            | 90010C       | 51         | DL           | NotPass   |
            | 90010C       | 52         | DL           | NotPass   |
            | 90010C       | 53         | DL           | NotPass   |
            | 90010C       | 54         | DL           | NotPass   |
            | 90010C       | 55         | DL           | NotPass   |
            | 90010C       | 61         | DL           | NotPass   |
            | 90010C       | 62         | DL           | NotPass   |
            | 90010C       | 63         | DL           | NotPass   |
            | 90010C       | 64         | DL           | NotPass   |
            | 90010C       | 65         | DL           | NotPass   |
            | 90010C       | 71         | DL           | NotPass   |
            | 90010C       | 72         | DL           | NotPass   |
            | 90010C       | 73         | DL           | NotPass   |
            | 90010C       | 74         | DL           | NotPass   |
            | 90010C       | 75         | DL           | NotPass   |
            | 90010C       | 81         | DL           | NotPass   |
            | 90010C       | 82         | DL           | NotPass   |
            | 90010C       | 83         | DL           | NotPass   |
            | 90010C       | 84         | DL           | NotPass   |
            | 90010C       | 85         | DL           | NotPass   |
            # 恆牙
            | 90010C       | 11         | DL           | Pass      |
            | 90010C       | 12         | DL           | Pass      |
            | 90010C       | 13         | DL           | Pass      |
            | 90010C       | 14         | DL           | Pass      |
            | 90010C       | 15         | DL           | Pass      |
            | 90010C       | 16         | DL           | Pass      |
            | 90010C       | 17         | DL           | Pass      |
            | 90010C       | 18         | DL           | Pass      |
            | 90010C       | 21         | DL           | Pass      |
            | 90010C       | 22         | DL           | Pass      |
            | 90010C       | 23         | DL           | Pass      |
            | 90010C       | 24         | DL           | Pass      |
            | 90010C       | 25         | DL           | Pass      |
            | 90010C       | 26         | DL           | Pass      |
            | 90010C       | 27         | DL           | Pass      |
            | 90010C       | 28         | DL           | Pass      |
            | 90010C       | 31         | DL           | Pass      |
            | 90010C       | 32         | DL           | Pass      |
            | 90010C       | 33         | DL           | Pass      |
            | 90010C       | 34         | DL           | Pass      |
            | 90010C       | 35         | DL           | Pass      |
            | 90010C       | 36         | DL           | Pass      |
            | 90010C       | 37         | DL           | Pass      |
            | 90010C       | 38         | DL           | Pass      |
            | 90010C       | 41         | DL           | Pass      |
            | 90010C       | 42         | DL           | Pass      |
            | 90010C       | 43         | DL           | Pass      |
            | 90010C       | 44         | DL           | Pass      |
            | 90010C       | 45         | DL           | Pass      |
            | 90010C       | 46         | DL           | Pass      |
            | 90010C       | 47         | DL           | Pass      |
            | 90010C       | 48         | DL           | Pass      |
            # 無牙
            | 90010C       |            | DL           | NotPass   |
            #
            | 90010C       | 19         | DL           | Pass      |
            | 90010C       | 29         | DL           | Pass      |
            | 90010C       | 39         | DL           | Pass      |
            | 90010C       | 49         | DL           | Pass      |
            | 90010C       | 59         | DL           | NotPass   |
            | 90010C       | 69         | DL           | NotPass   |
            | 90010C       | 79         | DL           | NotPass   |
            | 90010C       | 89         | DL           | NotPass   |
            | 90010C       | 99         | DL           | Pass      |
            # 牙位為區域型態
            | 90010C       | FM         | DL           | NotPass   |
            | 90010C       | UR         | DL           | NotPass   |
            | 90010C       | UL         | DL           | NotPass   |
            | 90010C       | UA         | DL           | NotPass   |
            | 90010C       | UB         | DL           | NotPass   |
            | 90010C       | LL         | DL           | NotPass   |
            | 90010C       | LR         | DL           | NotPass   |
            | 90010C       | LA         | DL           | NotPass   |
            | 90010C       | LB         | DL           | NotPass   |
            # 非法牙位
            | 90010C       | 00         | DL           | NotPass   |
            | 90010C       | 01         | DL           | NotPass   |
            | 90010C       | 10         | DL           | NotPass   |
            | 90010C       | 56         | DL           | NotPass   |
            | 90010C       | 66         | DL           | NotPass   |
            | 90010C       | 76         | DL           | NotPass   |
            | 90010C       | 86         | DL           | NotPass   |
            | 90010C       | 91         | DL           | NotPass   |
