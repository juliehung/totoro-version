@nhi-92-series
Feature: 92013C 簡單性拔牙

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
            | 92013C       | 11         | DL           | Pass      |

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
            | 92013C       | 51         | DL           | NotPass   |
            | 92013C       | 52         | DL           | NotPass   |
            | 92013C       | 53         | DL           | NotPass   |
            | 92013C       | 54         | DL           | NotPass   |
            | 92013C       | 55         | DL           | NotPass   |
            | 92013C       | 61         | DL           | NotPass   |
            | 92013C       | 62         | DL           | NotPass   |
            | 92013C       | 63         | DL           | NotPass   |
            | 92013C       | 64         | DL           | NotPass   |
            | 92013C       | 65         | DL           | NotPass   |
            | 92013C       | 71         | DL           | NotPass   |
            | 92013C       | 72         | DL           | NotPass   |
            | 92013C       | 73         | DL           | NotPass   |
            | 92013C       | 74         | DL           | NotPass   |
            | 92013C       | 75         | DL           | NotPass   |
            | 92013C       | 81         | DL           | NotPass   |
            | 92013C       | 82         | DL           | NotPass   |
            | 92013C       | 83         | DL           | NotPass   |
            | 92013C       | 84         | DL           | NotPass   |
            | 92013C       | 85         | DL           | NotPass   |
            # 恆牙
            | 92013C       | 11         | DL           | Pass      |
            | 92013C       | 12         | DL           | Pass      |
            | 92013C       | 13         | DL           | Pass      |
            | 92013C       | 14         | DL           | Pass      |
            | 92013C       | 15         | DL           | Pass      |
            | 92013C       | 16         | DL           | Pass      |
            | 92013C       | 17         | DL           | Pass      |
            | 92013C       | 18         | DL           | Pass      |
            | 92013C       | 21         | DL           | Pass      |
            | 92013C       | 22         | DL           | Pass      |
            | 92013C       | 23         | DL           | Pass      |
            | 92013C       | 24         | DL           | Pass      |
            | 92013C       | 25         | DL           | Pass      |
            | 92013C       | 26         | DL           | Pass      |
            | 92013C       | 27         | DL           | Pass      |
            | 92013C       | 28         | DL           | Pass      |
            | 92013C       | 31         | DL           | Pass      |
            | 92013C       | 32         | DL           | Pass      |
            | 92013C       | 33         | DL           | Pass      |
            | 92013C       | 34         | DL           | Pass      |
            | 92013C       | 35         | DL           | Pass      |
            | 92013C       | 36         | DL           | Pass      |
            | 92013C       | 37         | DL           | Pass      |
            | 92013C       | 38         | DL           | Pass      |
            | 92013C       | 41         | DL           | Pass      |
            | 92013C       | 42         | DL           | Pass      |
            | 92013C       | 43         | DL           | Pass      |
            | 92013C       | 44         | DL           | Pass      |
            | 92013C       | 45         | DL           | Pass      |
            | 92013C       | 46         | DL           | Pass      |
            | 92013C       | 47         | DL           | Pass      |
            | 92013C       | 48         | DL           | Pass      |
            # 無牙
            | 92013C       |            | DL           | NotPass   |
            #
            | 92013C       | 19         | DL           | Pass      |
            | 92013C       | 29         | DL           | Pass      |
            | 92013C       | 39         | DL           | Pass      |
            | 92013C       | 49         | DL           | Pass      |
            | 92013C       | 59         | DL           | NotPass   |
            | 92013C       | 69         | DL           | NotPass   |
            | 92013C       | 79         | DL           | NotPass   |
            | 92013C       | 89         | DL           | NotPass   |
            | 92013C       | 99         | DL           | Pass      |
            # 牙位為區域型態
            | 92013C       | FM         | DL           | NotPass   |
            | 92013C       | UR         | DL           | NotPass   |
            | 92013C       | UL         | DL           | NotPass   |
            | 92013C       | UA         | DL           | NotPass   |
            | 92013C       | UB         | DL           | NotPass   |
            | 92013C       | LL         | DL           | NotPass   |
            | 92013C       | LR         | DL           | NotPass   |
            | 92013C       | LA         | DL           | NotPass   |
            | 92013C       | LB         | DL           | NotPass   |
            # 非法牙位
            | 92013C       | 00         | DL           | NotPass   |
            | 92013C       | 01         | DL           | NotPass   |
            | 92013C       | 10         | DL           | NotPass   |
            | 92013C       | 56         | DL           | NotPass   |
            | 92013C       | 66         | DL           | NotPass   |
            | 92013C       | 76         | DL           | NotPass   |
            | 92013C       | 86         | DL           | NotPass   |
            | 92013C       | 91         | DL           | NotPass   |
