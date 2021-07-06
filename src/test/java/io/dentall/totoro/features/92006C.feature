@nhi @nhi-92-series
Feature: 92006C 拆線 每次

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
            | 92006C       | 11         | DL           | Pass      |

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
            | 92006C       | 51         | DL           | Pass      |
            | 92006C       | 52         | DL           | Pass      |
            | 92006C       | 53         | DL           | Pass      |
            | 92006C       | 54         | DL           | Pass      |
            | 92006C       | 55         | DL           | Pass      |
            | 92006C       | 61         | DL           | Pass      |
            | 92006C       | 62         | DL           | Pass      |
            | 92006C       | 63         | DL           | Pass      |
            | 92006C       | 64         | DL           | Pass      |
            | 92006C       | 65         | DL           | Pass      |
            | 92006C       | 71         | DL           | Pass      |
            | 92006C       | 72         | DL           | Pass      |
            | 92006C       | 73         | DL           | Pass      |
            | 92006C       | 74         | DL           | Pass      |
            | 92006C       | 75         | DL           | Pass      |
            | 92006C       | 81         | DL           | Pass      |
            | 92006C       | 82         | DL           | Pass      |
            | 92006C       | 83         | DL           | Pass      |
            | 92006C       | 84         | DL           | Pass      |
            | 92006C       | 85         | DL           | Pass      |
            # 恆牙
            | 92006C       | 11         | DL           | Pass      |
            | 92006C       | 12         | DL           | Pass      |
            | 92006C       | 13         | DL           | Pass      |
            | 92006C       | 14         | DL           | Pass      |
            | 92006C       | 15         | DL           | Pass      |
            | 92006C       | 16         | DL           | Pass      |
            | 92006C       | 17         | DL           | Pass      |
            | 92006C       | 18         | DL           | Pass      |
            | 92006C       | 21         | DL           | Pass      |
            | 92006C       | 22         | DL           | Pass      |
            | 92006C       | 23         | DL           | Pass      |
            | 92006C       | 24         | DL           | Pass      |
            | 92006C       | 25         | DL           | Pass      |
            | 92006C       | 26         | DL           | Pass      |
            | 92006C       | 27         | DL           | Pass      |
            | 92006C       | 28         | DL           | Pass      |
            | 92006C       | 31         | DL           | Pass      |
            | 92006C       | 32         | DL           | Pass      |
            | 92006C       | 33         | DL           | Pass      |
            | 92006C       | 34         | DL           | Pass      |
            | 92006C       | 35         | DL           | Pass      |
            | 92006C       | 36         | DL           | Pass      |
            | 92006C       | 37         | DL           | Pass      |
            | 92006C       | 38         | DL           | Pass      |
            | 92006C       | 41         | DL           | Pass      |
            | 92006C       | 42         | DL           | Pass      |
            | 92006C       | 43         | DL           | Pass      |
            | 92006C       | 44         | DL           | Pass      |
            | 92006C       | 45         | DL           | Pass      |
            | 92006C       | 46         | DL           | Pass      |
            | 92006C       | 47         | DL           | Pass      |
            | 92006C       | 48         | DL           | Pass      |
            # 無牙
            | 92006C       |            | DL           | NotPass   |
            #
            | 92006C       | 19         | DL           | Pass      |
            | 92006C       | 29         | DL           | Pass      |
            | 92006C       | 39         | DL           | Pass      |
            | 92006C       | 49         | DL           | Pass      |
            | 92006C       | 59         | DL           | NotPass   |
            | 92006C       | 69         | DL           | NotPass   |
            | 92006C       | 79         | DL           | NotPass   |
            | 92006C       | 89         | DL           | NotPass   |
            | 92006C       | 99         | DL           | Pass      |
            # 牙位為區域型態
            | 92006C       | FM         | DL           | NotPass   |
            | 92006C       | UR         | DL           | Pass      |
            | 92006C       | UL         | DL           | Pass      |
            | 92006C       | UA         | DL           | Pass      |
            | 92006C       | UB         | DL           | NotPass   |
            | 92006C       | LL         | DL           | Pass      |
            | 92006C       | LR         | DL           | Pass      |
            | 92006C       | LA         | DL           | Pass      |
            | 92006C       | LB         | DL           | NotPass   |
            # 非法牙位
            | 92006C       | 00         | DL           | NotPass   |
            | 92006C       | 01         | DL           | NotPass   |
            | 92006C       | 10         | DL           | NotPass   |
            | 92006C       | 56         | DL           | NotPass   |
            | 92006C       | 66         | DL           | NotPass   |
            | 92006C       | 76         | DL           | NotPass   |
            | 92006C       | 86         | DL           | NotPass   |
            | 92006C       | 91         | DL           | NotPass   |
