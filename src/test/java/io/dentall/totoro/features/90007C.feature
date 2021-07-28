@nhi @nhi-90-series
Feature: 90007C 去除鑄造牙冠

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
            | 90007C       | 11         | MOB          | Pass      |

    Scenario Outline: 提醒須檢附影像
        Given 建立醫師
        Given Wind 24 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 提醒"須檢附影像"，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            | 90007C       | 11         | MOB          | Pass      |

    Scenario Outline: 檢查治療的牙位是否為 PERMANENT_TOOTH
        Given 建立醫師
        Given Wind 24 歲病人
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
            | 90007C       | 51         | DL           | NotPass   |
            | 90007C       | 52         | DL           | NotPass   |
            | 90007C       | 53         | DL           | NotPass   |
            | 90007C       | 54         | DL           | NotPass   |
            | 90007C       | 55         | DL           | NotPass   |
            | 90007C       | 61         | DL           | NotPass   |
            | 90007C       | 62         | DL           | NotPass   |
            | 90007C       | 63         | DL           | NotPass   |
            | 90007C       | 64         | DL           | NotPass   |
            | 90007C       | 65         | DL           | NotPass   |
            | 90007C       | 71         | DL           | NotPass   |
            | 90007C       | 72         | DL           | NotPass   |
            | 90007C       | 73         | DL           | NotPass   |
            | 90007C       | 74         | DL           | NotPass   |
            | 90007C       | 75         | DL           | NotPass   |
            | 90007C       | 81         | DL           | NotPass   |
            | 90007C       | 82         | DL           | NotPass   |
            | 90007C       | 83         | DL           | NotPass   |
            | 90007C       | 84         | DL           | NotPass   |
            | 90007C       | 85         | DL           | NotPass   |
            # 恆牙
            | 90007C       | 11         | DL           | Pass      |
            | 90007C       | 12         | DL           | Pass      |
            | 90007C       | 13         | DL           | Pass      |
            | 90007C       | 14         | DL           | Pass      |
            | 90007C       | 15         | DL           | Pass      |
            | 90007C       | 16         | DL           | Pass      |
            | 90007C       | 17         | DL           | Pass      |
            | 90007C       | 18         | DL           | Pass      |
            | 90007C       | 21         | DL           | Pass      |
            | 90007C       | 22         | DL           | Pass      |
            | 90007C       | 23         | DL           | Pass      |
            | 90007C       | 24         | DL           | Pass      |
            | 90007C       | 25         | DL           | Pass      |
            | 90007C       | 26         | DL           | Pass      |
            | 90007C       | 27         | DL           | Pass      |
            | 90007C       | 28         | DL           | Pass      |
            | 90007C       | 31         | DL           | Pass      |
            | 90007C       | 32         | DL           | Pass      |
            | 90007C       | 33         | DL           | Pass      |
            | 90007C       | 34         | DL           | Pass      |
            | 90007C       | 35         | DL           | Pass      |
            | 90007C       | 36         | DL           | Pass      |
            | 90007C       | 37         | DL           | Pass      |
            | 90007C       | 38         | DL           | Pass      |
            | 90007C       | 41         | DL           | Pass      |
            | 90007C       | 42         | DL           | Pass      |
            | 90007C       | 43         | DL           | Pass      |
            | 90007C       | 44         | DL           | Pass      |
            | 90007C       | 45         | DL           | Pass      |
            | 90007C       | 46         | DL           | Pass      |
            | 90007C       | 47         | DL           | Pass      |
            | 90007C       | 48         | DL           | Pass      |
            # 無牙
            | 90007C       |            | DL           | NotPass   |
            #
            | 90007C       | 19         | DL           | Pass      |
            | 90007C       | 29         | DL           | Pass      |
            | 90007C       | 39         | DL           | Pass      |
            | 90007C       | 49         | DL           | Pass      |
            | 90007C       | 59         | DL           | NotPass   |
            | 90007C       | 69         | DL           | NotPass   |
            | 90007C       | 79         | DL           | NotPass   |
            | 90007C       | 89         | DL           | NotPass   |
            | 90007C       | 99         | DL           | Pass      |
            # 牙位為區域型態
            | 90007C       | FM         | DL           | NotPass   |
            | 90007C       | UR         | DL           | NotPass   |
            | 90007C       | UL         | DL           | NotPass   |
            | 90007C       | UA         | DL           | NotPass   |
            | 90007C       | UB         | DL           | NotPass   |
            | 90007C       | LL         | DL           | NotPass   |
            | 90007C       | LR         | DL           | NotPass   |
            | 90007C       | LA         | DL           | NotPass   |
            | 90007C       | LB         | DL           | NotPass   |
            # 非法牙位
            | 90007C       | 00         | DL           | NotPass   |
            | 90007C       | 01         | DL           | NotPass   |
            | 90007C       | 10         | DL           | NotPass   |
            | 90007C       | 56         | DL           | NotPass   |
            | 90007C       | 66         | DL           | NotPass   |
            | 90007C       | 76         | DL           | NotPass   |
            | 90007C       | 86         | DL           | NotPass   |
            | 90007C       | 91         | DL           | NotPass   |
