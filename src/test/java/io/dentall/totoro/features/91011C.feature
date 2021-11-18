@nhi @nhi-91-series @part2
Feature: 91011C 牙齦切除術-局部（3齒以內）

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
            | 91011C       | 11         | MOB          | Pass      |

    Scenario Outline: 提醒需檢附牙周囊袋記錄表
        Given 建立醫師
        Given Kelly 29 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 提醒"需檢附牙周囊袋記錄表"，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            | 91011C       | 11         | MOB          | Pass      |

    Scenario Outline: 檢查治療的牙位是否為 PARTIAL_ZONE_AND_PERMANENT_TOOTH
        Given 建立醫師
        Given Stan 24 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 檢查 <IssueTeeth> 牙位，依 PARTIAL_ZONE_AND_PERMANENT_TOOTH 判定是否為核可牙位，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            # 乳牙
            | 91011C       | 51         | DL           | NotPass   |
            | 91011C       | 52         | DL           | NotPass   |
            | 91011C       | 53         | DL           | NotPass   |
            | 91011C       | 54         | DL           | NotPass   |
            | 91011C       | 55         | DL           | NotPass   |
            | 91011C       | 61         | DL           | NotPass   |
            | 91011C       | 62         | DL           | NotPass   |
            | 91011C       | 63         | DL           | NotPass   |
            | 91011C       | 64         | DL           | NotPass   |
            | 91011C       | 65         | DL           | NotPass   |
            | 91011C       | 71         | DL           | NotPass   |
            | 91011C       | 72         | DL           | NotPass   |
            | 91011C       | 73         | DL           | NotPass   |
            | 91011C       | 74         | DL           | NotPass   |
            | 91011C       | 75         | DL           | NotPass   |
            | 91011C       | 81         | DL           | NotPass   |
            | 91011C       | 82         | DL           | NotPass   |
            | 91011C       | 83         | DL           | NotPass   |
            | 91011C       | 84         | DL           | NotPass   |
            | 91011C       | 85         | DL           | NotPass   |
            # 恆牙
            | 91011C       | 11         | DL           | Pass      |
            | 91011C       | 12         | DL           | Pass      |
            | 91011C       | 13         | DL           | Pass      |
            | 91011C       | 14         | DL           | Pass      |
            | 91011C       | 15         | DL           | Pass      |
            | 91011C       | 16         | DL           | Pass      |
            | 91011C       | 17         | DL           | Pass      |
            | 91011C       | 18         | DL           | Pass      |
            | 91011C       | 21         | DL           | Pass      |
            | 91011C       | 22         | DL           | Pass      |
            | 91011C       | 23         | DL           | Pass      |
            | 91011C       | 24         | DL           | Pass      |
            | 91011C       | 25         | DL           | Pass      |
            | 91011C       | 26         | DL           | Pass      |
            | 91011C       | 27         | DL           | Pass      |
            | 91011C       | 28         | DL           | Pass      |
            | 91011C       | 31         | DL           | Pass      |
            | 91011C       | 32         | DL           | Pass      |
            | 91011C       | 33         | DL           | Pass      |
            | 91011C       | 34         | DL           | Pass      |
            | 91011C       | 35         | DL           | Pass      |
            | 91011C       | 36         | DL           | Pass      |
            | 91011C       | 37         | DL           | Pass      |
            | 91011C       | 38         | DL           | Pass      |
            | 91011C       | 41         | DL           | Pass      |
            | 91011C       | 42         | DL           | Pass      |
            | 91011C       | 43         | DL           | Pass      |
            | 91011C       | 44         | DL           | Pass      |
            | 91011C       | 45         | DL           | Pass      |
            | 91011C       | 46         | DL           | Pass      |
            | 91011C       | 47         | DL           | Pass      |
            | 91011C       | 48         | DL           | Pass      |
            # 無牙
            | 91011C       |            | DL           | NotPass   |
            #
            | 91011C       | 19         | DL           | Pass      |
            | 91011C       | 29         | DL           | Pass      |
            | 91011C       | 39         | DL           | Pass      |
            | 91011C       | 49         | DL           | Pass      |
            | 91011C       | 59         | DL           | NotPass   |
            | 91011C       | 69         | DL           | NotPass   |
            | 91011C       | 79         | DL           | NotPass   |
            | 91011C       | 89         | DL           | NotPass   |
            | 91011C       | 99         | DL           | Pass      |
            # 牙位為區域型態
            | 91011C       | FM         | DL           | NotPass   |
            | 91011C       | UR         | DL           | Pass      |
            | 91011C       | UL         | DL           | Pass      |
            | 91011C       | UA         | DL           | Pass      |
            | 91011C       | UB         | DL           | NotPass   |
            | 91011C       | LL         | DL           | Pass      |
            | 91011C       | LR         | DL           | Pass      |
            | 91011C       | LA         | DL           | Pass      |
            | 91011C       | LB         | DL           | NotPass   |
            # 非法牙位
            | 91011C       | 00         | DL           | NotPass   |
            | 91011C       | 01         | DL           | NotPass   |
            | 91011C       | 10         | DL           | NotPass   |
            | 91011C       | 56         | DL           | NotPass   |
            | 91011C       | 66         | DL           | NotPass   |
            | 91011C       | 76         | DL           | NotPass   |
            | 91011C       | 86         | DL           | NotPass   |
            | 91011C       | 91         | DL           | NotPass   |
