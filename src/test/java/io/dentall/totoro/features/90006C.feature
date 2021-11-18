@nhi @nhi-90-series @part1
Feature: 90006C 去除縫成牙冠

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
            | 90006C       | 11         | MOB          | Pass      |

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
            | 90006C       | 11         | MOB          | Pass      |

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
            | 90006C       | 51         | DL           | NotPass   |
            | 90006C       | 52         | DL           | NotPass   |
            | 90006C       | 53         | DL           | NotPass   |
            | 90006C       | 54         | DL           | NotPass   |
            | 90006C       | 55         | DL           | NotPass   |
            | 90006C       | 61         | DL           | NotPass   |
            | 90006C       | 62         | DL           | NotPass   |
            | 90006C       | 63         | DL           | NotPass   |
            | 90006C       | 64         | DL           | NotPass   |
            | 90006C       | 65         | DL           | NotPass   |
            | 90006C       | 71         | DL           | NotPass   |
            | 90006C       | 72         | DL           | NotPass   |
            | 90006C       | 73         | DL           | NotPass   |
            | 90006C       | 74         | DL           | NotPass   |
            | 90006C       | 75         | DL           | NotPass   |
            | 90006C       | 81         | DL           | NotPass   |
            | 90006C       | 82         | DL           | NotPass   |
            | 90006C       | 83         | DL           | NotPass   |
            | 90006C       | 84         | DL           | NotPass   |
            | 90006C       | 85         | DL           | NotPass   |
            # 恆牙
            | 90006C       | 11         | DL           | Pass      |
            | 90006C       | 12         | DL           | Pass      |
            | 90006C       | 13         | DL           | Pass      |
            | 90006C       | 14         | DL           | Pass      |
            | 90006C       | 15         | DL           | Pass      |
            | 90006C       | 16         | DL           | Pass      |
            | 90006C       | 17         | DL           | Pass      |
            | 90006C       | 18         | DL           | Pass      |
            | 90006C       | 21         | DL           | Pass      |
            | 90006C       | 22         | DL           | Pass      |
            | 90006C       | 23         | DL           | Pass      |
            | 90006C       | 24         | DL           | Pass      |
            | 90006C       | 25         | DL           | Pass      |
            | 90006C       | 26         | DL           | Pass      |
            | 90006C       | 27         | DL           | Pass      |
            | 90006C       | 28         | DL           | Pass      |
            | 90006C       | 31         | DL           | Pass      |
            | 90006C       | 32         | DL           | Pass      |
            | 90006C       | 33         | DL           | Pass      |
            | 90006C       | 34         | DL           | Pass      |
            | 90006C       | 35         | DL           | Pass      |
            | 90006C       | 36         | DL           | Pass      |
            | 90006C       | 37         | DL           | Pass      |
            | 90006C       | 38         | DL           | Pass      |
            | 90006C       | 41         | DL           | Pass      |
            | 90006C       | 42         | DL           | Pass      |
            | 90006C       | 43         | DL           | Pass      |
            | 90006C       | 44         | DL           | Pass      |
            | 90006C       | 45         | DL           | Pass      |
            | 90006C       | 46         | DL           | Pass      |
            | 90006C       | 47         | DL           | Pass      |
            | 90006C       | 48         | DL           | Pass      |
            # 無牙
            | 90006C       |            | DL           | NotPass   |
            #
            | 90006C       | 19         | DL           | Pass      |
            | 90006C       | 29         | DL           | Pass      |
            | 90006C       | 39         | DL           | Pass      |
            | 90006C       | 49         | DL           | Pass      |
            | 90006C       | 59         | DL           | NotPass   |
            | 90006C       | 69         | DL           | NotPass   |
            | 90006C       | 79         | DL           | NotPass   |
            | 90006C       | 89         | DL           | NotPass   |
            | 90006C       | 99         | DL           | Pass      |
            # 牙位為區域型態
            | 90006C       | FM         | DL           | NotPass   |
            | 90006C       | UR         | DL           | NotPass   |
            | 90006C       | UL         | DL           | NotPass   |
            | 90006C       | UA         | DL           | NotPass   |
            | 90006C       | UB         | DL           | NotPass   |
            | 90006C       | LL         | DL           | NotPass   |
            | 90006C       | LR         | DL           | NotPass   |
            | 90006C       | LA         | DL           | NotPass   |
            | 90006C       | LB         | DL           | NotPass   |
            # 非法牙位
            | 90006C       | 00         | DL           | NotPass   |
            | 90006C       | 01         | DL           | NotPass   |
            | 90006C       | 10         | DL           | NotPass   |
            | 90006C       | 56         | DL           | NotPass   |
            | 90006C       | 66         | DL           | NotPass   |
            | 90006C       | 76         | DL           | NotPass   |
            | 90006C       | 86         | DL           | NotPass   |
            | 90006C       | 91         | DL           | NotPass   |
