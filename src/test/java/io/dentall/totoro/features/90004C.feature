Feature: 90004C 齒內治療緊急處理

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
            | 90004C       | 11         | MOB          | Pass      |

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
            | 90004C       | 51         | DL           | Pass      |
            | 90004C       | 52         | DL           | Pass      |
            | 90004C       | 53         | DL           | Pass      |
            | 90004C       | 54         | DL           | Pass      |
            | 90004C       | 55         | DL           | Pass      |
            | 90004C       | 61         | DL           | Pass      |
            | 90004C       | 62         | DL           | Pass      |
            | 90004C       | 63         | DL           | Pass      |
            | 90004C       | 64         | DL           | Pass      |
            | 90004C       | 65         | DL           | Pass      |
            | 90004C       | 71         | DL           | Pass      |
            | 90004C       | 72         | DL           | Pass      |
            | 90004C       | 73         | DL           | Pass      |
            | 90004C       | 74         | DL           | Pass      |
            | 90004C       | 75         | DL           | Pass      |
            | 90004C       | 81         | DL           | Pass      |
            | 90004C       | 82         | DL           | Pass      |
            | 90004C       | 83         | DL           | Pass      |
            | 90004C       | 84         | DL           | Pass      |
            | 90004C       | 85         | DL           | Pass      |
            # 恆牙
            | 90004C       | 11         | DL           | Pass      |
            | 90004C       | 12         | DL           | Pass      |
            | 90004C       | 13         | DL           | Pass      |
            | 90004C       | 14         | DL           | Pass      |
            | 90004C       | 15         | DL           | Pass      |
            | 90004C       | 16         | DL           | Pass      |
            | 90004C       | 17         | DL           | Pass      |
            | 90004C       | 18         | DL           | Pass      |
            | 90004C       | 21         | DL           | Pass      |
            | 90004C       | 22         | DL           | Pass      |
            | 90004C       | 23         | DL           | Pass      |
            | 90004C       | 24         | DL           | Pass      |
            | 90004C       | 25         | DL           | Pass      |
            | 90004C       | 26         | DL           | Pass      |
            | 90004C       | 27         | DL           | Pass      |
            | 90004C       | 28         | DL           | Pass      |
            | 90004C       | 31         | DL           | Pass      |
            | 90004C       | 32         | DL           | Pass      |
            | 90004C       | 33         | DL           | Pass      |
            | 90004C       | 34         | DL           | Pass      |
            | 90004C       | 35         | DL           | Pass      |
            | 90004C       | 36         | DL           | Pass      |
            | 90004C       | 37         | DL           | Pass      |
            | 90004C       | 38         | DL           | Pass      |
            | 90004C       | 41         | DL           | Pass      |
            | 90004C       | 42         | DL           | Pass      |
            | 90004C       | 43         | DL           | Pass      |
            | 90004C       | 44         | DL           | Pass      |
            | 90004C       | 45         | DL           | Pass      |
            | 90004C       | 46         | DL           | Pass      |
            | 90004C       | 47         | DL           | Pass      |
            | 90004C       | 48         | DL           | Pass      |
            # 無牙
            | 90004C       |            | DL           | NotPass   |
            #
            | 90004C       | 19         | DL           | Pass      |
            | 90004C       | 29         | DL           | Pass      |
            | 90004C       | 39         | DL           | Pass      |
            | 90004C       | 49         | DL           | Pass      |
            | 90004C       | 59         | DL           | NotPass   |
            | 90004C       | 69         | DL           | NotPass   |
            | 90004C       | 79         | DL           | NotPass   |
            | 90004C       | 89         | DL           | NotPass   |
            | 90004C       | 99         | DL           | Pass      |
            # 牙位為區域型態
            | 90004C       | FM         | DL           | NotPass   |
            | 90004C       | UR         | DL           | NotPass   |
            | 90004C       | UL         | DL           | NotPass   |
            | 90004C       | LL         | DL           | NotPass   |
            | 90004C       | LR         | DL           | NotPass   |
            | 90004C       | UA         | DL           | NotPass   |
            | 90004C       | LA         | DL           | NotPass   |
            # 非法牙位
            | 90004C       | 00         | DL           | NotPass   |
            | 90004C       | 01         | DL           | NotPass   |
            | 90004C       | 10         | DL           | NotPass   |
            | 90004C       | 56         | DL           | NotPass   |
            | 90004C       | 66         | DL           | NotPass   |
            | 90004C       | 76         | DL           | NotPass   |
            | 90004C       | 86         | DL           | NotPass   |
            | 90004C       | 91         | DL           | NotPass   |
