@nhi @nhi-92-series
Feature: 92055C 乳牙拔除

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
            | 92055C       | 51         | DL           | Pass      |

    Scenario Outline: 檢查治療的牙位是否為 DECIDUOUS_TOOTH
        Given 建立醫師
        Given Scott 24 歲病人
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
            | 92055C       | 51         | DL           | Pass      |
            | 92055C       | 52         | DL           | Pass      |
            | 92055C       | 53         | DL           | Pass      |
            | 92055C       | 54         | DL           | Pass      |
            | 92055C       | 55         | DL           | Pass      |
            | 92055C       | 61         | DL           | Pass      |
            | 92055C       | 62         | DL           | Pass      |
            | 92055C       | 63         | DL           | Pass      |
            | 92055C       | 64         | DL           | Pass      |
            | 92055C       | 65         | DL           | Pass      |
            | 92055C       | 71         | DL           | Pass      |
            | 92055C       | 72         | DL           | Pass      |
            | 92055C       | 73         | DL           | Pass      |
            | 92055C       | 74         | DL           | Pass      |
            | 92055C       | 75         | DL           | Pass      |
            | 92055C       | 81         | DL           | Pass      |
            | 92055C       | 82         | DL           | Pass      |
            | 92055C       | 83         | DL           | Pass      |
            | 92055C       | 84         | DL           | Pass      |
            | 92055C       | 85         | DL           | Pass      |
            # 恆牙
            | 92055C       | 11         | DL           | NotPass   |
            | 92055C       | 12         | DL           | NotPass   |
            | 92055C       | 13         | DL           | NotPass   |
            | 92055C       | 14         | DL           | NotPass   |
            | 92055C       | 15         | DL           | NotPass   |
            | 92055C       | 16         | DL           | NotPass   |
            | 92055C       | 17         | DL           | NotPass   |
            | 92055C       | 18         | DL           | NotPass   |
            | 92055C       | 21         | DL           | NotPass   |
            | 92055C       | 22         | DL           | NotPass   |
            | 92055C       | 23         | DL           | NotPass   |
            | 92055C       | 24         | DL           | NotPass   |
            | 92055C       | 25         | DL           | NotPass   |
            | 92055C       | 26         | DL           | NotPass   |
            | 92055C       | 27         | DL           | NotPass   |
            | 92055C       | 28         | DL           | NotPass   |
            | 92055C       | 31         | DL           | NotPass   |
            | 92055C       | 32         | DL           | NotPass   |
            | 92055C       | 33         | DL           | NotPass   |
            | 92055C       | 34         | DL           | NotPass   |
            | 92055C       | 35         | DL           | NotPass   |
            | 92055C       | 36         | DL           | NotPass   |
            | 92055C       | 37         | DL           | NotPass   |
            | 92055C       | 38         | DL           | NotPass   |
            | 92055C       | 41         | DL           | NotPass   |
            | 92055C       | 42         | DL           | NotPass   |
            | 92055C       | 43         | DL           | NotPass   |
            | 92055C       | 44         | DL           | NotPass   |
            | 92055C       | 45         | DL           | NotPass   |
            | 92055C       | 46         | DL           | NotPass   |
            | 92055C       | 47         | DL           | NotPass   |
            | 92055C       | 48         | DL           | NotPass   |
            # 無牙
            | 92055C       |            | DL           | NotPass   |
            #
            | 92055C       | 19         | DL           | NotPass   |
            | 92055C       | 29         | DL           | NotPass   |
            | 92055C       | 39         | DL           | NotPass   |
            | 92055C       | 49         | DL           | NotPass   |
            | 92055C       | 59         | DL           | NotPass   |
            | 92055C       | 69         | DL           | NotPass   |
            | 92055C       | 79         | DL           | NotPass   |
            | 92055C       | 89         | DL           | NotPass   |
            | 92055C       | 99         | DL           | Pass      |
            # 牙位為區域型態
            | 92055C       | FM         | DL           | NotPass   |
            | 92055C       | UR         | DL           | NotPass   |
            | 92055C       | UL         | DL           | NotPass   |
            | 92055C       | UA         | DL           | NotPass   |
            | 92055C       | UB         | DL           | NotPass   |
            | 92055C       | LL         | DL           | NotPass   |
            | 92055C       | LR         | DL           | NotPass   |
            | 92055C       | LA         | DL           | NotPass   |
            | 92055C       | LB         | DL           | NotPass   |
            # 非法牙位
            | 92055C       | 00         | DL           | NotPass   |
            | 92055C       | 01         | DL           | NotPass   |
            | 92055C       | 10         | DL           | NotPass   |
            | 92055C       | 56         | DL           | NotPass   |
            | 92055C       | 66         | DL           | NotPass   |
            | 92055C       | 76         | DL           | NotPass   |
            | 92055C       | 86         | DL           | NotPass   |
            | 92055C       | 91         | DL           | NotPass   |
