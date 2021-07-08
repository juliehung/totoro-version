@nhi @nhi-91-series
Feature: 91002C 牙周敷料 每次

    Scenario Outline: 全部檢核成功
        Given 建立醫師
        Given Stan 24 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 確認診療代碼 <IssueNhiCode> ，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            | 91002C       | UR         | MOB          | Pass      |

    Scenario Outline: 91002C 不得單獨申報
        Given 建立醫師
        Given Stan 24 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then <IssueNhiCode> 不得單獨申報，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            | 91002C       | UR         | MOB          | NotPass   |

    Scenario Outline: 檢查治療的牙位是否為 ALL_ZONE
        Given 建立醫師
        Given Wind 24 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 檢查 <IssueTeeth> 牙位，依 ALL_ZONE 判定是否為核可牙位，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            # 乳牙
            | 91002C       | 51         | DL           | NotPass   |
            | 91002C       | 52         | DL           | NotPass   |
            | 91002C       | 53         | DL           | NotPass   |
            | 91002C       | 54         | DL           | NotPass   |
            | 91002C       | 55         | DL           | NotPass   |
            | 91002C       | 61         | DL           | NotPass   |
            | 91002C       | 62         | DL           | NotPass   |
            | 91002C       | 63         | DL           | NotPass   |
            | 91002C       | 64         | DL           | NotPass   |
            | 91002C       | 65         | DL           | NotPass   |
            | 91002C       | 71         | DL           | NotPass   |
            | 91002C       | 72         | DL           | NotPass   |
            | 91002C       | 73         | DL           | NotPass   |
            | 91002C       | 74         | DL           | NotPass   |
            | 91002C       | 75         | DL           | NotPass   |
            | 91002C       | 81         | DL           | NotPass   |
            | 91002C       | 82         | DL           | NotPass   |
            | 91002C       | 83         | DL           | NotPass   |
            | 91002C       | 84         | DL           | NotPass   |
            | 91002C       | 85         | DL           | NotPass   |
            # 恆牙
            | 91002C       | 11         | DL           | NotPass   |
            | 91002C       | 12         | DL           | NotPass   |
            | 91002C       | 13         | DL           | NotPass   |
            | 91002C       | 14         | DL           | NotPass   |
            | 91002C       | 15         | DL           | NotPass   |
            | 91002C       | 16         | DL           | NotPass   |
            | 91002C       | 17         | DL           | NotPass   |
            | 91002C       | 18         | DL           | NotPass   |
            | 91002C       | 21         | DL           | NotPass   |
            | 91002C       | 22         | DL           | NotPass   |
            | 91002C       | 23         | DL           | NotPass   |
            | 91002C       | 24         | DL           | NotPass   |
            | 91002C       | 25         | DL           | NotPass   |
            | 91002C       | 26         | DL           | NotPass   |
            | 91002C       | 27         | DL           | NotPass   |
            | 91002C       | 28         | DL           | NotPass   |
            | 91002C       | 31         | DL           | NotPass   |
            | 91002C       | 32         | DL           | NotPass   |
            | 91002C       | 33         | DL           | NotPass   |
            | 91002C       | 34         | DL           | NotPass   |
            | 91002C       | 35         | DL           | NotPass   |
            | 91002C       | 36         | DL           | NotPass   |
            | 91002C       | 37         | DL           | NotPass   |
            | 91002C       | 38         | DL           | NotPass   |
            | 91002C       | 41         | DL           | NotPass   |
            | 91002C       | 42         | DL           | NotPass   |
            | 91002C       | 43         | DL           | NotPass   |
            | 91002C       | 44         | DL           | NotPass   |
            | 91002C       | 45         | DL           | NotPass   |
            | 91002C       | 46         | DL           | NotPass   |
            | 91002C       | 47         | DL           | NotPass   |
            | 91002C       | 48         | DL           | NotPass   |
            # 無牙
            | 91002C       |            | DL           | NotPass   |
            #
            | 91002C       | 19         | DL           | NotPass   |
            | 91002C       | 29         | DL           | NotPass   |
            | 91002C       | 39         | DL           | NotPass   |
            | 91002C       | 49         | DL           | NotPass   |
            | 91002C       | 59         | DL           | NotPass   |
            | 91002C       | 69         | DL           | NotPass   |
            | 91002C       | 79         | DL           | NotPass   |
            | 91002C       | 89         | DL           | NotPass   |
            | 91002C       | 99         | DL           | NotPass   |
            # 牙位為區域型態
            | 91002C       | FM         | DL           | Pass      |
            | 91002C       | UR         | DL           | Pass      |
            | 91002C       | UL         | DL           | Pass      |
            | 91002C       | UA         | DL           | Pass      |
            | 91002C       | UB         | DL           | Pass      |
            | 91002C       | LL         | DL           | Pass      |
            | 91002C       | LR         | DL           | Pass      |
            | 91002C       | LA         | DL           | Pass      |
            | 91002C       | LB         | DL           | Pass      |
            # 非法牙位
            | 91002C       | 00         | DL           | NotPass   |
            | 91002C       | 01         | DL           | NotPass   |
            | 91002C       | 10         | DL           | NotPass   |
            | 91002C       | 56         | DL           | NotPass   |
            | 91002C       | 66         | DL           | NotPass   |
            | 91002C       | 76         | DL           | NotPass   |
            | 91002C       | 86         | DL           | NotPass   |
            | 91002C       | 91         | DL           | NotPass   |
