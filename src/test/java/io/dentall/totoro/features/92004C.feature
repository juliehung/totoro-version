@nhi @nhi-92-series
Feature: 92004C 口內切開排膿

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
            | 92004C       | UR         | DL           | Pass      |

    Scenario Outline: 檢查治療的牙位是否為 PARTIAL_ZONE_AND_99
        Given 建立醫師
        Given Scott 24 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 檢查 <IssueTeeth> 牙位，依 PARTIAL_ZONE_AND_99 判定是否為核可牙位，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            # 乳牙
            | 92004C       | 51         | DL           | NotPass   |
            | 92004C       | 52         | DL           | NotPass   |
            | 92004C       | 53         | DL           | NotPass   |
            | 92004C       | 54         | DL           | NotPass   |
            | 92004C       | 55         | DL           | NotPass   |
            | 92004C       | 61         | DL           | NotPass   |
            | 92004C       | 62         | DL           | NotPass   |
            | 92004C       | 63         | DL           | NotPass   |
            | 92004C       | 64         | DL           | NotPass   |
            | 92004C       | 65         | DL           | NotPass   |
            | 92004C       | 71         | DL           | NotPass   |
            | 92004C       | 72         | DL           | NotPass   |
            | 92004C       | 73         | DL           | NotPass   |
            | 92004C       | 74         | DL           | NotPass   |
            | 92004C       | 75         | DL           | NotPass   |
            | 92004C       | 81         | DL           | NotPass   |
            | 92004C       | 82         | DL           | NotPass   |
            | 92004C       | 83         | DL           | NotPass   |
            | 92004C       | 84         | DL           | NotPass   |
            | 92004C       | 85         | DL           | NotPass   |
            # 恆牙
            | 92004C       | 11         | DL           | NotPass   |
            | 92004C       | 12         | DL           | NotPass   |
            | 92004C       | 13         | DL           | NotPass   |
            | 92004C       | 14         | DL           | NotPass   |
            | 92004C       | 15         | DL           | NotPass   |
            | 92004C       | 16         | DL           | NotPass   |
            | 92004C       | 17         | DL           | NotPass   |
            | 92004C       | 18         | DL           | NotPass   |
            | 92004C       | 21         | DL           | NotPass   |
            | 92004C       | 22         | DL           | NotPass   |
            | 92004C       | 23         | DL           | NotPass   |
            | 92004C       | 24         | DL           | NotPass   |
            | 92004C       | 25         | DL           | NotPass   |
            | 92004C       | 26         | DL           | NotPass   |
            | 92004C       | 27         | DL           | NotPass   |
            | 92004C       | 28         | DL           | NotPass   |
            | 92004C       | 31         | DL           | NotPass   |
            | 92004C       | 32         | DL           | NotPass   |
            | 92004C       | 33         | DL           | NotPass   |
            | 92004C       | 34         | DL           | NotPass   |
            | 92004C       | 35         | DL           | NotPass   |
            | 92004C       | 36         | DL           | NotPass   |
            | 92004C       | 37         | DL           | NotPass   |
            | 92004C       | 38         | DL           | NotPass   |
            | 92004C       | 41         | DL           | NotPass   |
            | 92004C       | 42         | DL           | NotPass   |
            | 92004C       | 43         | DL           | NotPass   |
            | 92004C       | 44         | DL           | NotPass   |
            | 92004C       | 45         | DL           | NotPass   |
            | 92004C       | 46         | DL           | NotPass   |
            | 92004C       | 47         | DL           | NotPass   |
            | 92004C       | 48         | DL           | NotPass   |
            # 無牙
            | 92004C       |            | DL           | NotPass   |
            #
            | 92004C       | 19         | DL           | NotPass   |
            | 92004C       | 29         | DL           | NotPass   |
            | 92004C       | 39         | DL           | NotPass   |
            | 92004C       | 49         | DL           | NotPass   |
            | 92004C       | 59         | DL           | NotPass   |
            | 92004C       | 69         | DL           | NotPass   |
            | 92004C       | 79         | DL           | NotPass   |
            | 92004C       | 89         | DL           | NotPass   |
            | 92004C       | 99         | DL           | Pass      |
            # 牙位為區域型態
            | 92004C       | FM         | DL           | NotPass   |
            | 92004C       | UR         | DL           | Pass      |
            | 92004C       | UL         | DL           | Pass      |
            | 92004C       | UA         | DL           | Pass      |
            | 92004C       | UB         | DL           | NotPass   |
            | 92004C       | LL         | DL           | Pass      |
            | 92004C       | LR         | DL           | Pass      |
            | 92004C       | LA         | DL           | Pass      |
            | 92004C       | LB         | DL           | NotPass   |
            # 非法牙位
            | 92004C       | 00         | DL           | NotPass   |
            | 92004C       | 01         | DL           | NotPass   |
            | 92004C       | 10         | DL           | NotPass   |
            | 92004C       | 56         | DL           | NotPass   |
            | 92004C       | 66         | DL           | NotPass   |
            | 92004C       | 76         | DL           | NotPass   |
            | 92004C       | 86         | DL           | NotPass   |
            | 92004C       | 91         | DL           | NotPass   |
