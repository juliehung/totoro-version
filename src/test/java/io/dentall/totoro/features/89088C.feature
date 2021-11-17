@nhi @nhi-89-series @part3
Feature: 89088C 牙體復形轉出醫療院所之轉診費用

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
            | 89088C       | 11         | DL           | Pass      |

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
            | 89088C       | 51         | DL           | Pass      |
            | 89088C       | 52         | DL           | Pass      |
            | 89088C       | 53         | DL           | Pass      |
            | 89088C       | 54         | DL           | Pass      |
            | 89088C       | 55         | DL           | Pass      |
            | 89088C       | 61         | DL           | Pass      |
            | 89088C       | 62         | DL           | Pass      |
            | 89088C       | 63         | DL           | Pass      |
            | 89088C       | 64         | DL           | Pass      |
            | 89088C       | 65         | DL           | Pass      |
            | 89088C       | 71         | DL           | Pass      |
            | 89088C       | 72         | DL           | Pass      |
            | 89088C       | 73         | DL           | Pass      |
            | 89088C       | 74         | DL           | Pass      |
            | 89088C       | 75         | DL           | Pass      |
            | 89088C       | 81         | DL           | Pass      |
            | 89088C       | 82         | DL           | Pass      |
            | 89088C       | 83         | DL           | Pass      |
            | 89088C       | 84         | DL           | Pass      |
            | 89088C       | 85         | DL           | Pass      |
            # 恆牙
            | 89088C       | 11         | DL           | Pass      |
            | 89088C       | 12         | DL           | Pass      |
            | 89088C       | 13         | DL           | Pass      |
            | 89088C       | 14         | DL           | Pass      |
            | 89088C       | 15         | DL           | Pass      |
            | 89088C       | 16         | DL           | Pass      |
            | 89088C       | 17         | DL           | Pass      |
            | 89088C       | 18         | DL           | Pass      |
            | 89088C       | 21         | DL           | Pass      |
            | 89088C       | 22         | DL           | Pass      |
            | 89088C       | 23         | DL           | Pass      |
            | 89088C       | 24         | DL           | Pass      |
            | 89088C       | 25         | DL           | Pass      |
            | 89088C       | 26         | DL           | Pass      |
            | 89088C       | 27         | DL           | Pass      |
            | 89088C       | 28         | DL           | Pass      |
            | 89088C       | 31         | DL           | Pass      |
            | 89088C       | 32         | DL           | Pass      |
            | 89088C       | 33         | DL           | Pass      |
            | 89088C       | 34         | DL           | Pass      |
            | 89088C       | 35         | DL           | Pass      |
            | 89088C       | 36         | DL           | Pass      |
            | 89088C       | 37         | DL           | Pass      |
            | 89088C       | 38         | DL           | Pass      |
            | 89088C       | 41         | DL           | Pass      |
            | 89088C       | 42         | DL           | Pass      |
            | 89088C       | 43         | DL           | Pass      |
            | 89088C       | 44         | DL           | Pass      |
            | 89088C       | 45         | DL           | Pass      |
            | 89088C       | 46         | DL           | Pass      |
            | 89088C       | 47         | DL           | Pass      |
            | 89088C       | 48         | DL           | Pass      |
            # 無牙
            | 89088C       |            | DL           | NotPass   |
            #
            | 89088C       | 19         | DL           | Pass      |
            | 89088C       | 29         | DL           | Pass      |
            | 89088C       | 39         | DL           | Pass      |
            | 89088C       | 49         | DL           | Pass      |
            | 89088C       | 59         | DL           | NotPass   |
            | 89088C       | 69         | DL           | NotPass   |
            | 89088C       | 79         | DL           | NotPass   |
            | 89088C       | 89         | DL           | NotPass   |
            | 89088C       | 99         | DL           | Pass      |
            # 牙位為區域型態
            | 89088C       | FM         | DL           | NotPass   |
            | 89088C       | UR         | DL           | NotPass   |
            | 89088C       | UL         | DL           | NotPass   |
            | 89088C       | UA         | DL           | NotPass   |
            | 89088C       | UB         | DL           | NotPass   |
            | 89088C       | LL         | DL           | NotPass   |
            | 89088C       | LR         | DL           | NotPass   |
            | 89088C       | LA         | DL           | NotPass   |
            | 89088C       | LB         | DL           | NotPass   |
            # 非法牙位
            | 89088C       | 00         | DL           | NotPass   |
            | 89088C       | 01         | DL           | NotPass   |
            | 89088C       | 10         | DL           | NotPass   |
            | 89088C       | 56         | DL           | NotPass   |
            | 89088C       | 66         | DL           | NotPass   |
            | 89088C       | 76         | DL           | NotPass   |
            | 89088C       | 86         | DL           | NotPass   |
            | 89088C       | 91         | DL           | NotPass   |
