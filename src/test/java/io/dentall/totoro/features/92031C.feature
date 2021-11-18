@nhi @nhi-92-series @part1
Feature: 92031C 小臼齒根尖切除術

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
            | 92031C       | 14         | DL           | Pass      |

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
            | 92031C       | 14         | MOB          | Pass      |

    Scenario Outline: 檢查治療的牙位是否為 PERMANENT_PREMOLAR_TOOTH
        Given 建立醫師
        Given Scott 24 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 檢查 <IssueTeeth> 牙位，依 PERMANENT_PREMOLAR_TOOTH 判定是否為核可牙位，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            # 乳牙
            | 92031C       | 51         | DL           | NotPass   |
            | 92031C       | 52         | DL           | NotPass   |
            | 92031C       | 53         | DL           | NotPass   |
            | 92031C       | 54         | DL           | NotPass   |
            | 92031C       | 55         | DL           | NotPass   |
            | 92031C       | 61         | DL           | NotPass   |
            | 92031C       | 62         | DL           | NotPass   |
            | 92031C       | 63         | DL           | NotPass   |
            | 92031C       | 64         | DL           | NotPass   |
            | 92031C       | 65         | DL           | NotPass   |
            | 92031C       | 71         | DL           | NotPass   |
            | 92031C       | 72         | DL           | NotPass   |
            | 92031C       | 73         | DL           | NotPass   |
            | 92031C       | 74         | DL           | NotPass   |
            | 92031C       | 75         | DL           | NotPass   |
            | 92031C       | 81         | DL           | NotPass   |
            | 92031C       | 82         | DL           | NotPass   |
            | 92031C       | 83         | DL           | NotPass   |
            | 92031C       | 84         | DL           | NotPass   |
            | 92031C       | 85         | DL           | NotPass   |
            # 恆牙
            | 92031C       | 11         | DL           | NotPass   |
            | 92031C       | 12         | DL           | NotPass   |
            | 92031C       | 13         | DL           | NotPass   |
            | 92031C       | 14         | DL           | Pass      |
            | 92031C       | 15         | DL           | Pass      |
            | 92031C       | 16         | DL           | NotPass   |
            | 92031C       | 17         | DL           | NotPass   |
            | 92031C       | 18         | DL           | NotPass   |
            | 92031C       | 21         | DL           | NotPass   |
            | 92031C       | 22         | DL           | NotPass   |
            | 92031C       | 23         | DL           | NotPass   |
            | 92031C       | 24         | DL           | Pass      |
            | 92031C       | 25         | DL           | Pass      |
            | 92031C       | 26         | DL           | NotPass   |
            | 92031C       | 27         | DL           | NotPass   |
            | 92031C       | 28         | DL           | NotPass   |
            | 92031C       | 31         | DL           | NotPass   |
            | 92031C       | 32         | DL           | NotPass   |
            | 92031C       | 33         | DL           | NotPass   |
            | 92031C       | 34         | DL           | Pass      |
            | 92031C       | 35         | DL           | Pass      |
            | 92031C       | 36         | DL           | NotPass   |
            | 92031C       | 37         | DL           | NotPass   |
            | 92031C       | 38         | DL           | NotPass   |
            | 92031C       | 41         | DL           | NotPass   |
            | 92031C       | 42         | DL           | NotPass   |
            | 92031C       | 43         | DL           | NotPass   |
            | 92031C       | 44         | DL           | Pass      |
            | 92031C       | 45         | DL           | Pass      |
            | 92031C       | 46         | DL           | NotPass   |
            | 92031C       | 47         | DL           | NotPass   |
            | 92031C       | 48         | DL           | NotPass   |
            # 無牙
            | 92031C       |            | DL           | NotPass   |
            #
            | 92031C       | 19         | DL           | Pass      |
            | 92031C       | 29         | DL           | Pass      |
            | 92031C       | 39         | DL           | Pass      |
            | 92031C       | 49         | DL           | Pass      |
            | 92031C       | 59         | DL           | NotPass   |
            | 92031C       | 69         | DL           | NotPass   |
            | 92031C       | 79         | DL           | NotPass   |
            | 92031C       | 89         | DL           | NotPass   |
            | 92031C       | 99         | DL           | Pass      |
            # 牙位為區域型態
            | 92031C       | FM         | DL           | NotPass   |
            | 92031C       | UR         | DL           | NotPass   |
            | 92031C       | UL         | DL           | NotPass   |
            | 92031C       | UA         | DL           | NotPass   |
            | 92031C       | UB         | DL           | NotPass   |
            | 92031C       | LL         | DL           | NotPass   |
            | 92031C       | LR         | DL           | NotPass   |
            | 92031C       | LA         | DL           | NotPass   |
            | 92031C       | LB         | DL           | NotPass   |
            # 非法牙位
            | 92031C       | 00         | DL           | NotPass   |
            | 92031C       | 01         | DL           | NotPass   |
            | 92031C       | 10         | DL           | NotPass   |
            | 92031C       | 56         | DL           | NotPass   |
            | 92031C       | 66         | DL           | NotPass   |
            | 92031C       | 76         | DL           | NotPass   |
            | 92031C       | 86         | DL           | NotPass   |
            | 92031C       | 91         | DL           | NotPass   |

    Scenario Outline: （HIS）730天內，同牙位不應有 92031C 診療項目
        Given 建立醫師
        Given Scott 24 歲病人
        Given 在過去第 <PastTreatmentDays> 天，建立預約
        Given 在過去第 <PastTreatmentDays> 天，建立掛號
        Given 在過去第 <PastTreatmentDays> 天，產生診療計畫
        And 新增診療代碼:
            | PastDays            | A72 | A73                | A74              | A75 | A76 | A77 | A78 | A79 |
            | <PastTreatmentDays> | 3   | <TreatmentNhiCode> | <TreatmentTeeth> | MOB | 0   | 1.0 | 03  |     |
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 病患的牙齒 <TreatmentTeeth> 在 <PastTreatmentDays> 天前，被申報 <TreatmentNhiCode> 健保代碼，而現在病患的牙齒 <IssueTeeth> 要被申報 <IssueNhiCode> 健保代碼，是否抵觸同顆牙齒在 <DayRange> 天內不得申報指定健保代碼，確認結果是否為 <PassOrNot> 且檢查訊息類型為 D1_3
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastTreatmentDays | TreatmentNhiCode | TreatmentTeeth | DayRange | PassOrNot |
            # 同牙
            | 92031C       | 14         | DL           | 0                 | 92031C           | 14             | 730      | NotPass   |
            | 92031C       | 14         | DL           | 729               | 92031C           | 14             | 730      | NotPass   |
            | 92031C       | 14         | DL           | 730               | 92031C           | 14             | 730      | NotPass   |
            | 92031C       | 14         | DL           | 731               | 92031C           | 14             | 730      | Pass      |
            # 不同牙
            | 92031C       | 14         | DL           | 0                 | 92031C           | 15             | 730      | Pass      |
            | 92031C       | 14         | DL           | 729               | 92031C           | 15             | 730      | Pass      |
            | 92031C       | 14         | DL           | 730               | 92031C           | 15             | 730      | Pass      |
            | 92031C       | 14         | DL           | 731               | 92031C           | 15             | 730      | Pass      |
