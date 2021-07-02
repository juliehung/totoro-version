@nhi-92-series
Feature: 92032C 大臼齒根尖切除術

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
            | 92032C       | 16         | DL           | Pass      |

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
            | 92032C       | 16         | MOB          | Pass      |

    Scenario Outline: 檢查治療的牙位是否為 PERMANENT_MOLAR_TOOTH
        Given 建立醫師
        Given Scott 24 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 檢查 <IssueTeeth> 牙位，依 PERMANENT_MOLAR_TOOTH 判定是否為核可牙位，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            # 乳牙
            | 92032C       | 51         | DL           | NotPass   |
            | 92032C       | 52         | DL           | NotPass   |
            | 92032C       | 53         | DL           | NotPass   |
            | 92032C       | 54         | DL           | NotPass   |
            | 92032C       | 55         | DL           | NotPass   |
            | 92032C       | 61         | DL           | NotPass   |
            | 92032C       | 62         | DL           | NotPass   |
            | 92032C       | 63         | DL           | NotPass   |
            | 92032C       | 64         | DL           | NotPass   |
            | 92032C       | 65         | DL           | NotPass   |
            | 92032C       | 71         | DL           | NotPass   |
            | 92032C       | 72         | DL           | NotPass   |
            | 92032C       | 73         | DL           | NotPass   |
            | 92032C       | 74         | DL           | NotPass   |
            | 92032C       | 75         | DL           | NotPass   |
            | 92032C       | 81         | DL           | NotPass   |
            | 92032C       | 82         | DL           | NotPass   |
            | 92032C       | 83         | DL           | NotPass   |
            | 92032C       | 84         | DL           | NotPass   |
            | 92032C       | 85         | DL           | NotPass   |
            # 恆牙
            | 92032C       | 11         | DL           | NotPass   |
            | 92032C       | 12         | DL           | NotPass   |
            | 92032C       | 13         | DL           | NotPass   |
            | 92032C       | 14         | DL           | NotPass   |
            | 92032C       | 15         | DL           | NotPass   |
            | 92032C       | 16         | DL           | Pass      |
            | 92032C       | 17         | DL           | Pass      |
            | 92032C       | 18         | DL           | Pass      |
            | 92032C       | 21         | DL           | NotPass   |
            | 92032C       | 22         | DL           | NotPass   |
            | 92032C       | 23         | DL           | NotPass   |
            | 92032C       | 24         | DL           | NotPass   |
            | 92032C       | 25         | DL           | NotPass   |
            | 92032C       | 26         | DL           | Pass      |
            | 92032C       | 27         | DL           | Pass      |
            | 92032C       | 28         | DL           | Pass      |
            | 92032C       | 31         | DL           | NotPass   |
            | 92032C       | 32         | DL           | NotPass   |
            | 92032C       | 33         | DL           | NotPass   |
            | 92032C       | 34         | DL           | NotPass   |
            | 92032C       | 35         | DL           | NotPass   |
            | 92032C       | 36         | DL           | Pass      |
            | 92032C       | 37         | DL           | Pass      |
            | 92032C       | 38         | DL           | Pass      |
            | 92032C       | 41         | DL           | NotPass   |
            | 92032C       | 42         | DL           | NotPass   |
            | 92032C       | 43         | DL           | NotPass   |
            | 92032C       | 44         | DL           | NotPass   |
            | 92032C       | 45         | DL           | NotPass   |
            | 92032C       | 46         | DL           | Pass      |
            | 92032C       | 47         | DL           | Pass      |
            | 92032C       | 48         | DL           | Pass      |
            # 無牙
            | 92032C       |            | DL           | NotPass   |
            #
            | 92032C       | 19         | DL           | Pass      |
            | 92032C       | 29         | DL           | Pass      |
            | 92032C       | 39         | DL           | Pass      |
            | 92032C       | 49         | DL           | Pass      |
            | 92032C       | 59         | DL           | NotPass   |
            | 92032C       | 69         | DL           | NotPass   |
            | 92032C       | 79         | DL           | NotPass   |
            | 92032C       | 89         | DL           | NotPass   |
            | 92032C       | 99         | DL           | Pass      |
            # 牙位為區域型態
            | 92032C       | FM         | DL           | NotPass   |
            | 92032C       | UR         | DL           | NotPass   |
            | 92032C       | UL         | DL           | NotPass   |
            | 92032C       | UA         | DL           | NotPass   |
            | 92032C       | UB         | DL           | NotPass   |
            | 92032C       | LL         | DL           | NotPass   |
            | 92032C       | LR         | DL           | NotPass   |
            | 92032C       | LA         | DL           | NotPass   |
            | 92032C       | LB         | DL           | NotPass   |
            # 非法牙位
            | 92032C       | 00         | DL           | NotPass   |
            | 92032C       | 01         | DL           | NotPass   |
            | 92032C       | 10         | DL           | NotPass   |
            | 92032C       | 56         | DL           | NotPass   |
            | 92032C       | 66         | DL           | NotPass   |
            | 92032C       | 76         | DL           | NotPass   |
            | 92032C       | 86         | DL           | NotPass   |
            | 92032C       | 91         | DL           | NotPass   |

    Scenario Outline: （HIS）730天內，不應有 92032C 診療項目
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
        Then 檢查 <IssueNhiCode> 診療項目，在病患過去 <GapDay> 天紀錄中，不應包含特定的 <TreatmentNhiCode> 診療代碼，確認結果是否為 <PassOrNot> 且檢查訊息類型為 D4_1
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastTreatmentDays | TreatmentNhiCode | TreatmentTeeth | GapDay | PassOrNot |
            | 92032C       | 16         | DL           | 0                 | 92032C           | 16             | 730    | NotPass   |
            | 92032C       | 16         | DL           | 729               | 92032C           | 16             | 730    | NotPass   |
            | 92032C       | 16         | DL           | 730               | 92032C           | 16             | 730    | NotPass   |
            | 92032C       | 16         | DL           | 731               | 92032C           | 16             | 730    | Pass      |
