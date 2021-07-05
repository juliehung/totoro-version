@nhi-92-series
Feature: 92014C 複雜性拔牙

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
            | 92014C       | 11         | DL           | Pass      |

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
            | 92014C       | 11         | MOB          | Pass      |

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
            | 92014C       | 51         | DL           | NotPass   |
            | 92014C       | 52         | DL           | NotPass   |
            | 92014C       | 53         | DL           | NotPass   |
            | 92014C       | 54         | DL           | NotPass   |
            | 92014C       | 55         | DL           | NotPass   |
            | 92014C       | 61         | DL           | NotPass   |
            | 92014C       | 62         | DL           | NotPass   |
            | 92014C       | 63         | DL           | NotPass   |
            | 92014C       | 64         | DL           | NotPass   |
            | 92014C       | 65         | DL           | NotPass   |
            | 92014C       | 71         | DL           | NotPass   |
            | 92014C       | 72         | DL           | NotPass   |
            | 92014C       | 73         | DL           | NotPass   |
            | 92014C       | 74         | DL           | NotPass   |
            | 92014C       | 75         | DL           | NotPass   |
            | 92014C       | 81         | DL           | NotPass   |
            | 92014C       | 82         | DL           | NotPass   |
            | 92014C       | 83         | DL           | NotPass   |
            | 92014C       | 84         | DL           | NotPass   |
            | 92014C       | 85         | DL           | NotPass   |
            # 恆牙
            | 92014C       | 11         | DL           | Pass      |
            | 92014C       | 12         | DL           | Pass      |
            | 92014C       | 13         | DL           | Pass      |
            | 92014C       | 14         | DL           | Pass      |
            | 92014C       | 15         | DL           | Pass      |
            | 92014C       | 16         | DL           | Pass      |
            | 92014C       | 17         | DL           | Pass      |
            | 92014C       | 18         | DL           | Pass      |
            | 92014C       | 21         | DL           | Pass      |
            | 92014C       | 22         | DL           | Pass      |
            | 92014C       | 23         | DL           | Pass      |
            | 92014C       | 24         | DL           | Pass      |
            | 92014C       | 25         | DL           | Pass      |
            | 92014C       | 26         | DL           | Pass      |
            | 92014C       | 27         | DL           | Pass      |
            | 92014C       | 28         | DL           | Pass      |
            | 92014C       | 31         | DL           | Pass      |
            | 92014C       | 32         | DL           | Pass      |
            | 92014C       | 33         | DL           | Pass      |
            | 92014C       | 34         | DL           | Pass      |
            | 92014C       | 35         | DL           | Pass      |
            | 92014C       | 36         | DL           | Pass      |
            | 92014C       | 37         | DL           | Pass      |
            | 92014C       | 38         | DL           | Pass      |
            | 92014C       | 41         | DL           | Pass      |
            | 92014C       | 42         | DL           | Pass      |
            | 92014C       | 43         | DL           | Pass      |
            | 92014C       | 44         | DL           | Pass      |
            | 92014C       | 45         | DL           | Pass      |
            | 92014C       | 46         | DL           | Pass      |
            | 92014C       | 47         | DL           | Pass      |
            | 92014C       | 48         | DL           | Pass      |
            # 無牙
            | 92014C       |            | DL           | NotPass   |
            #
            | 92014C       | 19         | DL           | Pass      |
            | 92014C       | 29         | DL           | Pass      |
            | 92014C       | 39         | DL           | Pass      |
            | 92014C       | 49         | DL           | Pass      |
            | 92014C       | 59         | DL           | NotPass   |
            | 92014C       | 69         | DL           | NotPass   |
            | 92014C       | 79         | DL           | NotPass   |
            | 92014C       | 89         | DL           | NotPass   |
            | 92014C       | 99         | DL           | Pass      |
            # 牙位為區域型態
            | 92014C       | FM         | DL           | NotPass   |
            | 92014C       | UR         | DL           | NotPass   |
            | 92014C       | UL         | DL           | NotPass   |
            | 92014C       | UA         | DL           | NotPass   |
            | 92014C       | UB         | DL           | NotPass   |
            | 92014C       | LL         | DL           | NotPass   |
            | 92014C       | LR         | DL           | NotPass   |
            | 92014C       | LA         | DL           | NotPass   |
            | 92014C       | LB         | DL           | NotPass   |
            # 非法牙位
            | 92014C       | 00         | DL           | NotPass   |
            | 92014C       | 01         | DL           | NotPass   |
            | 92014C       | 10         | DL           | NotPass   |
            | 92014C       | 56         | DL           | NotPass   |
            | 92014C       | 66         | DL           | NotPass   |
            | 92014C       | 76         | DL           | NotPass   |
            | 92014C       | 86         | DL           | NotPass   |
            | 92014C       | 91         | DL           | NotPass   |
