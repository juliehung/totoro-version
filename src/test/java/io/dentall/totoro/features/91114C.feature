@nhi-91-series
Feature: 91114C 特殊牙周暨齲齒控制基本處置

    Scenario Outline: 全部檢核成功
        Given 建立醫師
        Given Kelly 30 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 確認診療代碼 <IssueNhiCode> ，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            | 91114C       | 11         | MOB          | Pass      |

    Scenario Outline: 提醒適用於健保特殊醫療服務對象、化療、放射線治療患者
        Given 建立醫師
        Given Kelly 30 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 提醒"適用於健保特殊醫療服務對象、化療、放射線治療患者"，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            | 91114C       | 11         | MOB          | Pass      |

    Scenario Outline: （HIS）90天內，不應有 91114C 診療項目
        Given 建立醫師
        Given Kelly 30 歲病人
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
        Then （HIS）檢查 <IssueNhiCode> 診療項目，在病患過去 <GapDay> 天紀錄中，不應包含特定的 <TreatmentNhiCode> 診療代碼，確認結果是否為 <PassOrNot> 且檢查訊息類型為 D4_1
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastTreatmentDays | TreatmentNhiCode | TreatmentTeeth | GapDay | PassOrNot |
            | 91114C       | 11         | DL           | 89                | 91114C           | 11             | 90     | NotPass   |
            | 91114C       | 11         | DL           | 90                | 91114C           | 11             | 90     | NotPass   |
            | 91114C       | 11         | DL           | 91                | 91114C           | 11             | 90     | Pass      |

    Scenario Outline: （IC）90天內，不應有 91114C 診療項目
        Given 建立醫師
        Given Kelly 30 歲病人
        Given 新增健保醫療:
            | PastDays          | NhiCode          | Teeth          |
            | <PastMedicalDays> | <MedicalNhiCode> | <MedicalTeeth> |
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then （IC）檢查 <IssueNhiCode> 診療項目，在病患過去 <GapDay> 天紀錄中，不應包含特定的 <MedicalNhiCode> 診療代碼，確認結果是否為 <PassOrNot> 且檢查訊息類型為 D4_1
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastMedicalDays | MedicalNhiCode | MedicalTeeth | GapDay | PassOrNot |
            | 91114C       | 11         | DL           | 89              | 91114C         | 11           | 90     | NotPass   |
            | 91114C       | 11         | DL           | 90              | 91114C         | 11           | 90     | NotPass   |
            | 91114C       | 11         | DL           | 91              | 91114C         | 11           | 90     | Pass      |

    Scenario Outline: 檢查治療的牙位是否為 FULL_ZONE
        Given 建立醫師
        Given Kelly 24 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        And 新增診療代碼:
            | A72 | A73    | A74 | A75 | A76 | A77 | A78 | A79 |
            | 3   | 91004C | FM  | MOB | 0   | 1.0 | 03  |     |
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            | 91004C  | FM    | MOB     |                |              |                |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 檢查 <IssueTeeth> 牙位，依 FULL_ZONE 判定是否為核可牙位，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            # 乳牙
            | 91114C       | 51         | DL           | NotPass   |
            | 91114C       | 52         | DL           | NotPass   |
            | 91114C       | 53         | DL           | NotPass   |
            | 91114C       | 54         | DL           | NotPass   |
            | 91114C       | 55         | DL           | NotPass   |
            | 91114C       | 61         | DL           | NotPass   |
            | 91114C       | 62         | DL           | NotPass   |
            | 91114C       | 63         | DL           | NotPass   |
            | 91114C       | 64         | DL           | NotPass   |
            | 91114C       | 65         | DL           | NotPass   |
            | 91114C       | 71         | DL           | NotPass   |
            | 91114C       | 72         | DL           | NotPass   |
            | 91114C       | 73         | DL           | NotPass   |
            | 91114C       | 74         | DL           | NotPass   |
            | 91114C       | 75         | DL           | NotPass   |
            | 91114C       | 81         | DL           | NotPass   |
            | 91114C       | 82         | DL           | NotPass   |
            | 91114C       | 83         | DL           | NotPass   |
            | 91114C       | 84         | DL           | NotPass   |
            | 91114C       | 85         | DL           | NotPass   |
            # 恆牙
            | 91114C       | 11         | DL           | NotPass   |
            | 91114C       | 12         | DL           | NotPass   |
            | 91114C       | 13         | DL           | NotPass   |
            | 91114C       | 14         | DL           | NotPass   |
            | 91114C       | 15         | DL           | NotPass   |
            | 91114C       | 16         | DL           | NotPass   |
            | 91114C       | 17         | DL           | NotPass   |
            | 91114C       | 18         | DL           | NotPass   |
            | 91114C       | 21         | DL           | NotPass   |
            | 91114C       | 22         | DL           | NotPass   |
            | 91114C       | 23         | DL           | NotPass   |
            | 91114C       | 24         | DL           | NotPass   |
            | 91114C       | 25         | DL           | NotPass   |
            | 91114C       | 26         | DL           | NotPass   |
            | 91114C       | 27         | DL           | NotPass   |
            | 91114C       | 28         | DL           | NotPass   |
            | 91114C       | 31         | DL           | NotPass   |
            | 91114C       | 32         | DL           | NotPass   |
            | 91114C       | 33         | DL           | NotPass   |
            | 91114C       | 34         | DL           | NotPass   |
            | 91114C       | 35         | DL           | NotPass   |
            | 91114C       | 36         | DL           | NotPass   |
            | 91114C       | 37         | DL           | NotPass   |
            | 91114C       | 38         | DL           | NotPass   |
            | 91114C       | 41         | DL           | NotPass   |
            | 91114C       | 42         | DL           | NotPass   |
            | 91114C       | 43         | DL           | NotPass   |
            | 91114C       | 44         | DL           | NotPass   |
            | 91114C       | 45         | DL           | NotPass   |
            | 91114C       | 46         | DL           | NotPass   |
            | 91114C       | 47         | DL           | NotPass   |
            | 91114C       | 48         | DL           | NotPass   |
            # 無牙
            | 91114C       |            | DL           | NotPass   |
            #
            | 91114C       | 19         | DL           | NotPass   |
            | 91114C       | 29         | DL           | NotPass   |
            | 91114C       | 39         | DL           | NotPass   |
            | 91114C       | 49         | DL           | NotPass   |
            | 91114C       | 59         | DL           | NotPass   |
            | 91114C       | 69         | DL           | NotPass   |
            | 91114C       | 79         | DL           | NotPass   |
            | 91114C       | 89         | DL           | NotPass   |
            | 91114C       | 99         | DL           | NotPass   |
            # 牙位為區域型態
            | 91114C       | FM         | DL           | Pass      |
            | 91114C       | UR         | DL           | NotPass   |
            | 91114C       | UL         | DL           | NotPass   |
            | 91114C       | UA         | DL           | NotPass   |
            | 91114C       | UB         | DL           | NotPass   |
            | 91114C       | LL         | DL           | NotPass   |
            | 91114C       | LR         | DL           | NotPass   |
            | 91114C       | LA         | DL           | NotPass   |
            | 91114C       | LB         | DL           | NotPass   |
            # 非法牙位
            | 91114C       | 00         | DL           | NotPass   |
            | 91114C       | 01         | DL           | NotPass   |
            | 91114C       | 10         | DL           | NotPass   |
            | 91114C       | 56         | DL           | NotPass   |
            | 91114C       | 66         | DL           | NotPass   |
            | 91114C       | 76         | DL           | NotPass   |
            | 91114C       | 86         | DL           | NotPass   |
            | 91114C       | 91         | DL           | NotPass   |
