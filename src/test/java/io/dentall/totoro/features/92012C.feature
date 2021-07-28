@nhi @nhi-92-series
Feature: 92012C 齒齦下括除術(含牙根整平術)-1/2 顎

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
            | 92012C       | 11         | MOB          | Pass      |

    Scenario Outline: 檢查治療的牙位是否為 GENERAL_TOOTH
        Given 建立醫師
        Given Scott 24 歲病人
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
            | 92012C       | 51         | DL           | Pass      |
            | 92012C       | 52         | DL           | Pass      |
            | 92012C       | 53         | DL           | Pass      |
            | 92012C       | 54         | DL           | Pass      |
            | 92012C       | 55         | DL           | Pass      |
            | 92012C       | 61         | DL           | Pass      |
            | 92012C       | 62         | DL           | Pass      |
            | 92012C       | 63         | DL           | Pass      |
            | 92012C       | 64         | DL           | Pass      |
            | 92012C       | 65         | DL           | Pass      |
            | 92012C       | 71         | DL           | Pass      |
            | 92012C       | 72         | DL           | Pass      |
            | 92012C       | 73         | DL           | Pass      |
            | 92012C       | 74         | DL           | Pass      |
            | 92012C       | 75         | DL           | Pass      |
            | 92012C       | 81         | DL           | Pass      |
            | 92012C       | 82         | DL           | Pass      |
            | 92012C       | 83         | DL           | Pass      |
            | 92012C       | 84         | DL           | Pass      |
            | 92012C       | 85         | DL           | Pass      |
            # 恆牙
            | 92012C       | 11         | DL           | Pass      |
            | 92012C       | 12         | DL           | Pass      |
            | 92012C       | 13         | DL           | Pass      |
            | 92012C       | 14         | DL           | Pass      |
            | 92012C       | 15         | DL           | Pass      |
            | 92012C       | 16         | DL           | Pass      |
            | 92012C       | 17         | DL           | Pass      |
            | 92012C       | 18         | DL           | Pass      |
            | 92012C       | 21         | DL           | Pass      |
            | 92012C       | 22         | DL           | Pass      |
            | 92012C       | 23         | DL           | Pass      |
            | 92012C       | 24         | DL           | Pass      |
            | 92012C       | 25         | DL           | Pass      |
            | 92012C       | 26         | DL           | Pass      |
            | 92012C       | 27         | DL           | Pass      |
            | 92012C       | 28         | DL           | Pass      |
            | 92012C       | 31         | DL           | Pass      |
            | 92012C       | 32         | DL           | Pass      |
            | 92012C       | 33         | DL           | Pass      |
            | 92012C       | 34         | DL           | Pass      |
            | 92012C       | 35         | DL           | Pass      |
            | 92012C       | 36         | DL           | Pass      |
            | 92012C       | 37         | DL           | Pass      |
            | 92012C       | 38         | DL           | Pass      |
            | 92012C       | 41         | DL           | Pass      |
            | 92012C       | 42         | DL           | Pass      |
            | 92012C       | 43         | DL           | Pass      |
            | 92012C       | 44         | DL           | Pass      |
            | 92012C       | 45         | DL           | Pass      |
            | 92012C       | 46         | DL           | Pass      |
            | 92012C       | 47         | DL           | Pass      |
            | 92012C       | 48         | DL           | Pass      |
            # 無牙
            | 92012C       |            | DL           | NotPass   |
            #
            | 92012C       | 19         | DL           | Pass      |
            | 92012C       | 29         | DL           | Pass      |
            | 92012C       | 39         | DL           | Pass      |
            | 92012C       | 49         | DL           | Pass      |
            | 92012C       | 59         | DL           | NotPass   |
            | 92012C       | 69         | DL           | NotPass   |
            | 92012C       | 79         | DL           | NotPass   |
            | 92012C       | 89         | DL           | NotPass   |
            | 92012C       | 99         | DL           | Pass      |
            # 牙位為區域型態
            | 92012C       | FM         | DL           | NotPass   |
            | 92012C       | UR         | DL           | NotPass   |
            | 92012C       | UL         | DL           | NotPass   |
            | 92012C       | UA         | DL           | NotPass   |
            | 92012C       | UB         | DL           | NotPass   |
            | 92012C       | LL         | DL           | NotPass   |
            | 92012C       | LR         | DL           | NotPass   |
            | 92012C       | LA         | DL           | NotPass   |
            | 92012C       | LB         | DL           | NotPass   |
            # 非法牙位
            | 92012C       | 00         | DL           | NotPass   |
            | 92012C       | 01         | DL           | NotPass   |
            | 92012C       | 10         | DL           | NotPass   |
            | 92012C       | 56         | DL           | NotPass   |
            | 92012C       | 66         | DL           | NotPass   |
            | 92012C       | 76         | DL           | NotPass   |
            | 92012C       | 86         | DL           | NotPass   |
            | 92012C       | 91         | DL           | NotPass   |
