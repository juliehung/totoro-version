Feature: 90005C 乳牙斷髓處理

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
            | 90005C       | 51         | MOB          | Pass      |

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
            | 90005C       | 51         | MOB          | Pass      |

    Scenario Outline: 檢查治療的牙位是否為 DECIDUOUS_TOOTH
        Given 建立醫師
        Given Wind 24 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 檢查 <IssueTeeth> 牙位，依 DECIDUOUS_TOOTH 判定是否為核可牙位，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            # 乳牙
            | 90005C       | 51         | DL           | Pass      |
            | 90005C       | 52         | DL           | Pass      |
            | 90005C       | 53         | DL           | Pass      |
            | 90005C       | 54         | DL           | Pass      |
            | 90005C       | 55         | DL           | Pass      |
            | 90005C       | 61         | DL           | Pass      |
            | 90005C       | 62         | DL           | Pass      |
            | 90005C       | 63         | DL           | Pass      |
            | 90005C       | 64         | DL           | Pass      |
            | 90005C       | 65         | DL           | Pass      |
            | 90005C       | 71         | DL           | Pass      |
            | 90005C       | 72         | DL           | Pass      |
            | 90005C       | 73         | DL           | Pass      |
            | 90005C       | 74         | DL           | Pass      |
            | 90005C       | 75         | DL           | Pass      |
            | 90005C       | 81         | DL           | Pass      |
            | 90005C       | 82         | DL           | Pass      |
            | 90005C       | 83         | DL           | Pass      |
            | 90005C       | 84         | DL           | Pass      |
            | 90005C       | 85         | DL           | Pass      |
            # 恆牙
            | 90005C       | 11         | DL           | NotPass   |
            | 90005C       | 12         | DL           | NotPass   |
            | 90005C       | 13         | DL           | NotPass   |
            | 90005C       | 14         | DL           | NotPass   |
            | 90005C       | 15         | DL           | NotPass   |
            | 90005C       | 16         | DL           | NotPass   |
            | 90005C       | 17         | DL           | NotPass   |
            | 90005C       | 18         | DL           | NotPass   |
            | 90005C       | 21         | DL           | NotPass   |
            | 90005C       | 22         | DL           | NotPass   |
            | 90005C       | 23         | DL           | NotPass   |
            | 90005C       | 24         | DL           | NotPass   |
            | 90005C       | 25         | DL           | NotPass   |
            | 90005C       | 26         | DL           | NotPass   |
            | 90005C       | 27         | DL           | NotPass   |
            | 90005C       | 28         | DL           | NotPass   |
            | 90005C       | 31         | DL           | NotPass   |
            | 90005C       | 32         | DL           | NotPass   |
            | 90005C       | 33         | DL           | NotPass   |
            | 90005C       | 34         | DL           | NotPass   |
            | 90005C       | 35         | DL           | NotPass   |
            | 90005C       | 36         | DL           | NotPass   |
            | 90005C       | 37         | DL           | NotPass   |
            | 90005C       | 38         | DL           | NotPass   |
            | 90005C       | 41         | DL           | NotPass   |
            | 90005C       | 42         | DL           | NotPass   |
            | 90005C       | 43         | DL           | NotPass   |
            | 90005C       | 44         | DL           | NotPass   |
            | 90005C       | 45         | DL           | NotPass   |
            | 90005C       | 46         | DL           | NotPass   |
            | 90005C       | 47         | DL           | NotPass   |
            | 90005C       | 48         | DL           | NotPass   |
            # 無牙
            | 90005C       |            | DL           | NotPass   |
            #
            | 90005C       | 19         | DL           | NotPass   |
            | 90005C       | 29         | DL           | NotPass   |
            | 90005C       | 39         | DL           | NotPass   |
            | 90005C       | 49         | DL           | NotPass   |
            | 90005C       | 59         | DL           | NotPass   |
            | 90005C       | 69         | DL           | NotPass   |
            | 90005C       | 79         | DL           | NotPass   |
            | 90005C       | 89         | DL           | NotPass   |
            | 90005C       | 99         | DL           | Pass      |
            # 牙位為區域型態
            | 90005C       | FM         | DL           | NotPass   |
            | 90005C       | UR         | DL           | NotPass   |
            | 90005C       | UL         | DL           | NotPass   |
            | 90005C       | LL         | DL           | NotPass   |
            | 90005C       | LR         | DL           | NotPass   |
            | 90005C       | UA         | DL           | NotPass   |
            | 90005C       | LA         | DL           | NotPass   |
            # 非法牙位
            | 90005C       | 00         | DL           | NotPass   |
            | 90005C       | 01         | DL           | NotPass   |
            | 90005C       | 10         | DL           | NotPass   |
            | 90005C       | 56         | DL           | NotPass   |
            | 90005C       | 66         | DL           | NotPass   |
            | 90005C       | 76         | DL           | NotPass   |
            | 90005C       | 86         | DL           | NotPass   |
            | 90005C       | 91         | DL           | NotPass   |
