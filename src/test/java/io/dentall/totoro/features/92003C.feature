@nhi-92-series
Feature: 92003C 口內切開排膿

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
            | 92003C       | 11         | DL           | Pass      |

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
            | 92003C       | 51         | DL           | Pass      |
            | 92003C       | 52         | DL           | Pass      |
            | 92003C       | 53         | DL           | Pass      |
            | 92003C       | 54         | DL           | Pass      |
            | 92003C       | 55         | DL           | Pass      |
            | 92003C       | 61         | DL           | Pass      |
            | 92003C       | 62         | DL           | Pass      |
            | 92003C       | 63         | DL           | Pass      |
            | 92003C       | 64         | DL           | Pass      |
            | 92003C       | 65         | DL           | Pass      |
            | 92003C       | 71         | DL           | Pass      |
            | 92003C       | 72         | DL           | Pass      |
            | 92003C       | 73         | DL           | Pass      |
            | 92003C       | 74         | DL           | Pass      |
            | 92003C       | 75         | DL           | Pass      |
            | 92003C       | 81         | DL           | Pass      |
            | 92003C       | 82         | DL           | Pass      |
            | 92003C       | 83         | DL           | Pass      |
            | 92003C       | 84         | DL           | Pass      |
            | 92003C       | 85         | DL           | Pass      |
            # 恆牙
            | 92003C       | 11         | DL           | Pass      |
            | 92003C       | 12         | DL           | Pass      |
            | 92003C       | 13         | DL           | Pass      |
            | 92003C       | 14         | DL           | Pass      |
            | 92003C       | 15         | DL           | Pass      |
            | 92003C       | 16         | DL           | Pass      |
            | 92003C       | 17         | DL           | Pass      |
            | 92003C       | 18         | DL           | Pass      |
            | 92003C       | 21         | DL           | Pass      |
            | 92003C       | 22         | DL           | Pass      |
            | 92003C       | 23         | DL           | Pass      |
            | 92003C       | 24         | DL           | Pass      |
            | 92003C       | 25         | DL           | Pass      |
            | 92003C       | 26         | DL           | Pass      |
            | 92003C       | 27         | DL           | Pass      |
            | 92003C       | 28         | DL           | Pass      |
            | 92003C       | 31         | DL           | Pass      |
            | 92003C       | 32         | DL           | Pass      |
            | 92003C       | 33         | DL           | Pass      |
            | 92003C       | 34         | DL           | Pass      |
            | 92003C       | 35         | DL           | Pass      |
            | 92003C       | 36         | DL           | Pass      |
            | 92003C       | 37         | DL           | Pass      |
            | 92003C       | 38         | DL           | Pass      |
            | 92003C       | 41         | DL           | Pass      |
            | 92003C       | 42         | DL           | Pass      |
            | 92003C       | 43         | DL           | Pass      |
            | 92003C       | 44         | DL           | Pass      |
            | 92003C       | 45         | DL           | Pass      |
            | 92003C       | 46         | DL           | Pass      |
            | 92003C       | 47         | DL           | Pass      |
            | 92003C       | 48         | DL           | Pass      |
            # 無牙
            | 92003C       |            | DL           | NotPass   |
            #
            | 92003C       | 19         | DL           | Pass      |
            | 92003C       | 29         | DL           | Pass      |
            | 92003C       | 39         | DL           | Pass      |
            | 92003C       | 49         | DL           | Pass      |
            | 92003C       | 59         | DL           | NotPass   |
            | 92003C       | 69         | DL           | NotPass   |
            | 92003C       | 79         | DL           | NotPass   |
            | 92003C       | 89         | DL           | NotPass   |
            | 92003C       | 99         | DL           | Pass      |
            # 牙位為區域型態
            | 92003C       | FM         | DL           | NotPass   |
            | 92003C       | UR         | DL           | Pass      |
            | 92003C       | UL         | DL           | Pass      |
            | 92003C       | UA         | DL           | Pass      |
            | 92003C       | UB         | DL           | NotPass   |
            | 92003C       | LL         | DL           | Pass      |
            | 92003C       | LR         | DL           | Pass      |
            | 92003C       | LA         | DL           | Pass      |
            | 92003C       | LB         | DL           | NotPass   |
            # 非法牙位
            | 92003C       | 00         | DL           | NotPass   |
            | 92003C       | 01         | DL           | NotPass   |
            | 92003C       | 10         | DL           | NotPass   |
            | 92003C       | 56         | DL           | NotPass   |
            | 92003C       | 66         | DL           | NotPass   |
            | 92003C       | 76         | DL           | NotPass   |
            | 92003C       | 86         | DL           | NotPass   |
            | 92003C       | 91         | DL           | NotPass   |
