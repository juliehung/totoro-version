Feature: 92015C 單純齒切除術

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
            | 92015C       | 11         | DL           | Pass      |

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
            | 92015C       | 11         | MOB          | Pass      |

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
            | 92015C       | 51         | DL           | NotPass   |
            | 92015C       | 52         | DL           | NotPass   |
            | 92015C       | 53         | DL           | NotPass   |
            | 92015C       | 54         | DL           | NotPass   |
            | 92015C       | 55         | DL           | NotPass   |
            | 92015C       | 61         | DL           | NotPass   |
            | 92015C       | 62         | DL           | NotPass   |
            | 92015C       | 63         | DL           | NotPass   |
            | 92015C       | 64         | DL           | NotPass   |
            | 92015C       | 65         | DL           | NotPass   |
            | 92015C       | 71         | DL           | NotPass   |
            | 92015C       | 72         | DL           | NotPass   |
            | 92015C       | 73         | DL           | NotPass   |
            | 92015C       | 74         | DL           | NotPass   |
            | 92015C       | 75         | DL           | NotPass   |
            | 92015C       | 81         | DL           | NotPass   |
            | 92015C       | 82         | DL           | NotPass   |
            | 92015C       | 83         | DL           | NotPass   |
            | 92015C       | 84         | DL           | NotPass   |
            | 92015C       | 85         | DL           | NotPass   |
            # 恆牙
            | 92015C       | 11         | DL           | Pass      |
            | 92015C       | 12         | DL           | Pass      |
            | 92015C       | 13         | DL           | Pass      |
            | 92015C       | 14         | DL           | Pass      |
            | 92015C       | 15         | DL           | Pass      |
            | 92015C       | 16         | DL           | Pass      |
            | 92015C       | 17         | DL           | Pass      |
            | 92015C       | 18         | DL           | Pass      |
            | 92015C       | 21         | DL           | Pass      |
            | 92015C       | 22         | DL           | Pass      |
            | 92015C       | 23         | DL           | Pass      |
            | 92015C       | 24         | DL           | Pass      |
            | 92015C       | 25         | DL           | Pass      |
            | 92015C       | 26         | DL           | Pass      |
            | 92015C       | 27         | DL           | Pass      |
            | 92015C       | 28         | DL           | Pass      |
            | 92015C       | 31         | DL           | Pass      |
            | 92015C       | 32         | DL           | Pass      |
            | 92015C       | 33         | DL           | Pass      |
            | 92015C       | 34         | DL           | Pass      |
            | 92015C       | 35         | DL           | Pass      |
            | 92015C       | 36         | DL           | Pass      |
            | 92015C       | 37         | DL           | Pass      |
            | 92015C       | 38         | DL           | Pass      |
            | 92015C       | 41         | DL           | Pass      |
            | 92015C       | 42         | DL           | Pass      |
            | 92015C       | 43         | DL           | Pass      |
            | 92015C       | 44         | DL           | Pass      |
            | 92015C       | 45         | DL           | Pass      |
            | 92015C       | 46         | DL           | Pass      |
            | 92015C       | 47         | DL           | Pass      |
            | 92015C       | 48         | DL           | Pass      |
            # 無牙
            | 92015C       |            | DL           | NotPass   |
            #
            | 92015C       | 19         | DL           | Pass      |
            | 92015C       | 29         | DL           | Pass      |
            | 92015C       | 39         | DL           | Pass      |
            | 92015C       | 49         | DL           | Pass      |
            | 92015C       | 59         | DL           | NotPass   |
            | 92015C       | 69         | DL           | NotPass   |
            | 92015C       | 79         | DL           | NotPass   |
            | 92015C       | 89         | DL           | NotPass   |
            | 92015C       | 99         | DL           | Pass      |
            # 牙位為區域型態
            | 92015C       | FM         | DL           | NotPass   |
            | 92015C       | UR         | DL           | NotPass   |
            | 92015C       | UL         | DL           | NotPass   |
            | 92015C       | LL         | DL           | NotPass   |
            | 92015C       | LR         | DL           | NotPass   |
            | 92015C       | UA         | DL           | NotPass   |
            | 92015C       | LA         | DL           | NotPass   |
            # 非法牙位
            | 92015C       | 00         | DL           | NotPass   |
            | 92015C       | 01         | DL           | NotPass   |
            | 92015C       | 10         | DL           | NotPass   |
            | 92015C       | 56         | DL           | NotPass   |
            | 92015C       | 66         | DL           | NotPass   |
            | 92015C       | 76         | DL           | NotPass   |
            | 92015C       | 86         | DL           | NotPass   |
            | 92015C       | 91         | DL           | NotPass   |
