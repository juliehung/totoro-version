@nhi @nhi-92-series @part1
Feature: 92002C 齒間暫時固定術，每齒

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
            | 92002C       | 11         | DL           | Pass      |

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
            | 92002C       | 11         | MOB          | Pass      |

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
            | 92002C       | 51         | DL           | Pass      |
            | 92002C       | 52         | DL           | Pass      |
            | 92002C       | 53         | DL           | Pass      |
            | 92002C       | 54         | DL           | Pass      |
            | 92002C       | 55         | DL           | Pass      |
            | 92002C       | 61         | DL           | Pass      |
            | 92002C       | 62         | DL           | Pass      |
            | 92002C       | 63         | DL           | Pass      |
            | 92002C       | 64         | DL           | Pass      |
            | 92002C       | 65         | DL           | Pass      |
            | 92002C       | 71         | DL           | Pass      |
            | 92002C       | 72         | DL           | Pass      |
            | 92002C       | 73         | DL           | Pass      |
            | 92002C       | 74         | DL           | Pass      |
            | 92002C       | 75         | DL           | Pass      |
            | 92002C       | 81         | DL           | Pass      |
            | 92002C       | 82         | DL           | Pass      |
            | 92002C       | 83         | DL           | Pass      |
            | 92002C       | 84         | DL           | Pass      |
            | 92002C       | 85         | DL           | Pass      |
            # 恆牙
            | 92002C       | 11         | DL           | Pass      |
            | 92002C       | 12         | DL           | Pass      |
            | 92002C       | 13         | DL           | Pass      |
            | 92002C       | 14         | DL           | Pass      |
            | 92002C       | 15         | DL           | Pass      |
            | 92002C       | 16         | DL           | Pass      |
            | 92002C       | 17         | DL           | Pass      |
            | 92002C       | 18         | DL           | Pass      |
            | 92002C       | 21         | DL           | Pass      |
            | 92002C       | 22         | DL           | Pass      |
            | 92002C       | 23         | DL           | Pass      |
            | 92002C       | 24         | DL           | Pass      |
            | 92002C       | 25         | DL           | Pass      |
            | 92002C       | 26         | DL           | Pass      |
            | 92002C       | 27         | DL           | Pass      |
            | 92002C       | 28         | DL           | Pass      |
            | 92002C       | 31         | DL           | Pass      |
            | 92002C       | 32         | DL           | Pass      |
            | 92002C       | 33         | DL           | Pass      |
            | 92002C       | 34         | DL           | Pass      |
            | 92002C       | 35         | DL           | Pass      |
            | 92002C       | 36         | DL           | Pass      |
            | 92002C       | 37         | DL           | Pass      |
            | 92002C       | 38         | DL           | Pass      |
            | 92002C       | 41         | DL           | Pass      |
            | 92002C       | 42         | DL           | Pass      |
            | 92002C       | 43         | DL           | Pass      |
            | 92002C       | 44         | DL           | Pass      |
            | 92002C       | 45         | DL           | Pass      |
            | 92002C       | 46         | DL           | Pass      |
            | 92002C       | 47         | DL           | Pass      |
            | 92002C       | 48         | DL           | Pass      |
            # 無牙
            | 92002C       |            | DL           | NotPass   |
            #
            | 92002C       | 19         | DL           | Pass      |
            | 92002C       | 29         | DL           | Pass      |
            | 92002C       | 39         | DL           | Pass      |
            | 92002C       | 49         | DL           | Pass      |
            | 92002C       | 59         | DL           | NotPass   |
            | 92002C       | 69         | DL           | NotPass   |
            | 92002C       | 79         | DL           | NotPass   |
            | 92002C       | 89         | DL           | NotPass   |
            | 92002C       | 99         | DL           | Pass      |
            # 牙位為區域型態
            | 92002C       | FM         | DL           | NotPass   |
            | 92002C       | UR         | DL           | NotPass   |
            | 92002C       | UL         | DL           | NotPass   |
            | 92002C       | UA         | DL           | NotPass   |
            | 92002C       | UB         | DL           | NotPass   |
            | 92002C       | LL         | DL           | NotPass   |
            | 92002C       | LR         | DL           | NotPass   |
            | 92002C       | LA         | DL           | NotPass   |
            | 92002C       | LB         | DL           | NotPass   |
            # 非法牙位
            | 92002C       | 00         | DL           | NotPass   |
            | 92002C       | 01         | DL           | NotPass   |
            | 92002C       | 10         | DL           | NotPass   |
            | 92002C       | 56         | DL           | NotPass   |
            | 92002C       | 66         | DL           | NotPass   |
            | 92002C       | 76         | DL           | NotPass   |
            | 92002C       | 86         | DL           | NotPass   |
            | 92002C       | 91         | DL           | NotPass   |
