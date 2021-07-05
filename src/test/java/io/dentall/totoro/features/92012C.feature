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
            | 92012C       | UR         | MOB          | Pass      |

    Scenario Outline: 檢查治療的牙位是否為 PARTIAL_ZONE
        Given 建立醫師
        Given Scott 24 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 檢查 <IssueTeeth> 牙位，依 PARTIAL_ZONE 判定是否為核可牙位，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            # 乳牙
            | 92012C       | 51         | DL           | NotPass   |
            | 92012C       | 52         | DL           | NotPass   |
            | 92012C       | 53         | DL           | NotPass   |
            | 92012C       | 54         | DL           | NotPass   |
            | 92012C       | 55         | DL           | NotPass   |
            | 92012C       | 61         | DL           | NotPass   |
            | 92012C       | 62         | DL           | NotPass   |
            | 92012C       | 63         | DL           | NotPass   |
            | 92012C       | 64         | DL           | NotPass   |
            | 92012C       | 65         | DL           | NotPass   |
            | 92012C       | 71         | DL           | NotPass   |
            | 92012C       | 72         | DL           | NotPass   |
            | 92012C       | 73         | DL           | NotPass   |
            | 92012C       | 74         | DL           | NotPass   |
            | 92012C       | 75         | DL           | NotPass   |
            | 92012C       | 81         | DL           | NotPass   |
            | 92012C       | 82         | DL           | NotPass   |
            | 92012C       | 83         | DL           | NotPass   |
            | 92012C       | 84         | DL           | NotPass   |
            | 92012C       | 85         | DL           | NotPass   |
            # 恆牙
            | 92012C       | 11         | DL           | NotPass   |
            | 92012C       | 12         | DL           | NotPass   |
            | 92012C       | 13         | DL           | NotPass   |
            | 92012C       | 14         | DL           | NotPass   |
            | 92012C       | 15         | DL           | NotPass   |
            | 92012C       | 16         | DL           | NotPass   |
            | 92012C       | 17         | DL           | NotPass   |
            | 92012C       | 18         | DL           | NotPass   |
            | 92012C       | 21         | DL           | NotPass   |
            | 92012C       | 22         | DL           | NotPass   |
            | 92012C       | 23         | DL           | NotPass   |
            | 92012C       | 24         | DL           | NotPass   |
            | 92012C       | 25         | DL           | NotPass   |
            | 92012C       | 26         | DL           | NotPass   |
            | 92012C       | 27         | DL           | NotPass   |
            | 92012C       | 28         | DL           | NotPass   |
            | 92012C       | 31         | DL           | NotPass   |
            | 92012C       | 32         | DL           | NotPass   |
            | 92012C       | 33         | DL           | NotPass   |
            | 92012C       | 34         | DL           | NotPass   |
            | 92012C       | 35         | DL           | NotPass   |
            | 92012C       | 36         | DL           | NotPass   |
            | 92012C       | 37         | DL           | NotPass   |
            | 92012C       | 38         | DL           | NotPass   |
            | 92012C       | 41         | DL           | NotPass   |
            | 92012C       | 42         | DL           | NotPass   |
            | 92012C       | 43         | DL           | NotPass   |
            | 92012C       | 44         | DL           | NotPass   |
            | 92012C       | 45         | DL           | NotPass   |
            | 92012C       | 46         | DL           | NotPass   |
            | 92012C       | 47         | DL           | NotPass   |
            | 92012C       | 48         | DL           | NotPass   |
            # 無牙
            | 92012C       |            | DL           | NotPass   |
            #
            | 92012C       | 19         | DL           | NotPass   |
            | 92012C       | 29         | DL           | NotPass   |
            | 92012C       | 39         | DL           | NotPass   |
            | 92012C       | 49         | DL           | NotPass   |
            | 92012C       | 59         | DL           | NotPass   |
            | 92012C       | 69         | DL           | NotPass   |
            | 92012C       | 79         | DL           | NotPass   |
            | 92012C       | 89         | DL           | NotPass   |
            | 92012C       | 99         | DL           | NotPass   |
            # 牙位為區域型態
            | 92012C       | FM         | DL           | NotPass   |
            | 92012C       | UR         | DL           | Pass      |
            | 92012C       | UL         | DL           | Pass      |
            | 92012C       | UA         | DL           | Pass      |
            | 92012C       | UB         | DL           | NotPass   |
            | 92012C       | LL         | DL           | Pass      |
            | 92012C       | LR         | DL           | Pass      |
            | 92012C       | LA         | DL           | Pass      |
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
