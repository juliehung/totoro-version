@nhi @nhi-92-series @part1
Feature: 92016C 複雜齒切除術

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
            | 92016C       | 11         | DL           | Pass      |

    Scenario Outline: 提醒須檢附影像
        Given 建立醫師
        Given Scott 24 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 提醒"須檢附影像"，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            | 92016C       | 11         | MOB          | Pass      |

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
            | 92016C       | 51         | DL           | Pass      |
            | 92016C       | 52         | DL           | Pass      |
            | 92016C       | 53         | DL           | Pass      |
            | 92016C       | 54         | DL           | Pass      |
            | 92016C       | 55         | DL           | Pass      |
            | 92016C       | 61         | DL           | Pass      |
            | 92016C       | 62         | DL           | Pass      |
            | 92016C       | 63         | DL           | Pass      |
            | 92016C       | 64         | DL           | Pass      |
            | 92016C       | 65         | DL           | Pass      |
            | 92016C       | 71         | DL           | Pass      |
            | 92016C       | 72         | DL           | Pass      |
            | 92016C       | 73         | DL           | Pass      |
            | 92016C       | 74         | DL           | Pass      |
            | 92016C       | 75         | DL           | Pass      |
            | 92016C       | 81         | DL           | Pass      |
            | 92016C       | 82         | DL           | Pass      |
            | 92016C       | 83         | DL           | Pass      |
            | 92016C       | 84         | DL           | Pass      |
            | 92016C       | 85         | DL           | Pass      |
            # 恆牙
            | 92016C       | 11         | DL           | Pass      |
            | 92016C       | 12         | DL           | Pass      |
            | 92016C       | 13         | DL           | Pass      |
            | 92016C       | 14         | DL           | Pass      |
            | 92016C       | 15         | DL           | Pass      |
            | 92016C       | 16         | DL           | Pass      |
            | 92016C       | 17         | DL           | Pass      |
            | 92016C       | 18         | DL           | Pass      |
            | 92016C       | 21         | DL           | Pass      |
            | 92016C       | 22         | DL           | Pass      |
            | 92016C       | 23         | DL           | Pass      |
            | 92016C       | 24         | DL           | Pass      |
            | 92016C       | 25         | DL           | Pass      |
            | 92016C       | 26         | DL           | Pass      |
            | 92016C       | 27         | DL           | Pass      |
            | 92016C       | 28         | DL           | Pass      |
            | 92016C       | 31         | DL           | Pass      |
            | 92016C       | 32         | DL           | Pass      |
            | 92016C       | 33         | DL           | Pass      |
            | 92016C       | 34         | DL           | Pass      |
            | 92016C       | 35         | DL           | Pass      |
            | 92016C       | 36         | DL           | Pass      |
            | 92016C       | 37         | DL           | Pass      |
            | 92016C       | 38         | DL           | Pass      |
            | 92016C       | 41         | DL           | Pass      |
            | 92016C       | 42         | DL           | Pass      |
            | 92016C       | 43         | DL           | Pass      |
            | 92016C       | 44         | DL           | Pass      |
            | 92016C       | 45         | DL           | Pass      |
            | 92016C       | 46         | DL           | Pass      |
            | 92016C       | 47         | DL           | Pass      |
            | 92016C       | 48         | DL           | Pass      |
            # 無牙
            | 92016C       |            | DL           | NotPass   |
            #
            | 92016C       | 19         | DL           | Pass      |
            | 92016C       | 29         | DL           | Pass      |
            | 92016C       | 39         | DL           | Pass      |
            | 92016C       | 49         | DL           | Pass      |
            | 92016C       | 59         | DL           | NotPass   |
            | 92016C       | 69         | DL           | NotPass   |
            | 92016C       | 79         | DL           | NotPass   |
            | 92016C       | 89         | DL           | NotPass   |
            | 92016C       | 99         | DL           | Pass      |
            # 牙位為區域型態
            | 92016C       | FM         | DL           | NotPass   |
            | 92016C       | UR         | DL           | NotPass   |
            | 92016C       | UL         | DL           | NotPass   |
            | 92016C       | UA         | DL           | NotPass   |
            | 92016C       | UB         | DL           | NotPass   |
            | 92016C       | LL         | DL           | NotPass   |
            | 92016C       | LR         | DL           | NotPass   |
            | 92016C       | LA         | DL           | NotPass   |
            | 92016C       | LB         | DL           | NotPass   |
            # 非法牙位
            | 92016C       | 00         | DL           | NotPass   |
            | 92016C       | 01         | DL           | NotPass   |
            | 92016C       | 10         | DL           | NotPass   |
            | 92016C       | 56         | DL           | NotPass   |
            | 92016C       | 66         | DL           | NotPass   |
            | 92016C       | 76         | DL           | NotPass   |
            | 92016C       | 86         | DL           | NotPass   |
            | 92016C       | 91         | DL           | NotPass   |
