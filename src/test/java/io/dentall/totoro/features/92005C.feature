@nhi-92-series
Feature: 92005C 拆線 每次

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
            | 92005C       | 11         | DL           | Pass      |

    Scenario Outline: 檢查治療的牙位是否為 VALIDATED_ALL_EXCLUDE_FM
        Given 建立醫師
        Given Scott 24 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 檢查 <IssueTeeth> 牙位，依 VALIDATED_ALL_EXCLUDE_FM 判定是否為核可牙位，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            # 乳牙
            | 92005C       | 51         | DL           | Pass      |
            | 92005C       | 52         | DL           | Pass      |
            | 92005C       | 53         | DL           | Pass      |
            | 92005C       | 54         | DL           | Pass      |
            | 92005C       | 55         | DL           | Pass      |
            | 92005C       | 61         | DL           | Pass      |
            | 92005C       | 62         | DL           | Pass      |
            | 92005C       | 63         | DL           | Pass      |
            | 92005C       | 64         | DL           | Pass      |
            | 92005C       | 65         | DL           | Pass      |
            | 92005C       | 71         | DL           | Pass      |
            | 92005C       | 72         | DL           | Pass      |
            | 92005C       | 73         | DL           | Pass      |
            | 92005C       | 74         | DL           | Pass      |
            | 92005C       | 75         | DL           | Pass      |
            | 92005C       | 81         | DL           | Pass      |
            | 92005C       | 82         | DL           | Pass      |
            | 92005C       | 83         | DL           | Pass      |
            | 92005C       | 84         | DL           | Pass      |
            | 92005C       | 85         | DL           | Pass      |
            # 恆牙
            | 92005C       | 11         | DL           | Pass      |
            | 92005C       | 12         | DL           | Pass      |
            | 92005C       | 13         | DL           | Pass      |
            | 92005C       | 14         | DL           | Pass      |
            | 92005C       | 15         | DL           | Pass      |
            | 92005C       | 16         | DL           | Pass      |
            | 92005C       | 17         | DL           | Pass      |
            | 92005C       | 18         | DL           | Pass      |
            | 92005C       | 21         | DL           | Pass      |
            | 92005C       | 22         | DL           | Pass      |
            | 92005C       | 23         | DL           | Pass      |
            | 92005C       | 24         | DL           | Pass      |
            | 92005C       | 25         | DL           | Pass      |
            | 92005C       | 26         | DL           | Pass      |
            | 92005C       | 27         | DL           | Pass      |
            | 92005C       | 28         | DL           | Pass      |
            | 92005C       | 31         | DL           | Pass      |
            | 92005C       | 32         | DL           | Pass      |
            | 92005C       | 33         | DL           | Pass      |
            | 92005C       | 34         | DL           | Pass      |
            | 92005C       | 35         | DL           | Pass      |
            | 92005C       | 36         | DL           | Pass      |
            | 92005C       | 37         | DL           | Pass      |
            | 92005C       | 38         | DL           | Pass      |
            | 92005C       | 41         | DL           | Pass      |
            | 92005C       | 42         | DL           | Pass      |
            | 92005C       | 43         | DL           | Pass      |
            | 92005C       | 44         | DL           | Pass      |
            | 92005C       | 45         | DL           | Pass      |
            | 92005C       | 46         | DL           | Pass      |
            | 92005C       | 47         | DL           | Pass      |
            | 92005C       | 48         | DL           | Pass      |
            # 無牙
            | 92005C       |            | DL           | NotPass   |
            #
            | 92005C       | 19         | DL           | Pass      |
            | 92005C       | 29         | DL           | Pass      |
            | 92005C       | 39         | DL           | Pass      |
            | 92005C       | 49         | DL           | Pass      |
            | 92005C       | 59         | DL           | NotPass   |
            | 92005C       | 69         | DL           | NotPass   |
            | 92005C       | 79         | DL           | NotPass   |
            | 92005C       | 89         | DL           | NotPass   |
            | 92005C       | 99         | DL           | Pass      |
            # 牙位為區域型態
            | 92005C       | FM         | DL           | NotPass   |
            | 92005C       | UR         | DL           | Pass      |
            | 92005C       | UL         | DL           | Pass      |
            | 92005C       | UA         | DL           | Pass      |
            | 92005C       | UB         | DL           | NotPass   |
            | 92005C       | LL         | DL           | Pass      |
            | 92005C       | LR         | DL           | Pass      |
            | 92005C       | LA         | DL           | Pass      |
            | 92005C       | LB         | DL           | NotPass   |
            # 非法牙位
            | 92005C       | 00         | DL           | NotPass   |
            | 92005C       | 01         | DL           | NotPass   |
            | 92005C       | 10         | DL           | NotPass   |
            | 92005C       | 56         | DL           | NotPass   |
            | 92005C       | 66         | DL           | NotPass   |
            | 92005C       | 76         | DL           | NotPass   |
            | 92005C       | 86         | DL           | NotPass   |
            | 92005C       | 91         | DL           | NotPass   |
