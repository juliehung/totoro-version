@nhi @nhi-91-series @part3
Feature: 91089C 糖尿病患者牙結石清除-全口

    Scenario Outline: 全部檢核成功
        Given 建立醫師
        Given Kelly 24 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 確認診療代碼 <IssueNhiCode> ，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            | 91089C       | FM         | MOB          | Pass      |

    Scenario Outline: （HIS）90天內，不應有 91089C 診療項目
        Given 建立醫師
        Given Kelly 24 歲病人
        Given 在過去第 <PastTreatmentDays> 天，建立預約
        Given 在過去第 <PastTreatmentDays> 天，建立掛號
        Given 在過去第 <PastTreatmentDays> 天，產生診療計畫
        And 新增診療代碼:
            | PastDays            | A72 | A73                | A74              | A75                | A76 | A77 | A78 | A79 |
            | <PastTreatmentDays> | 3   | <TreatmentNhiCode> | <TreatmentTeeth> | <TreatmentSurface> | 0   | 1.0 | 03  |     |
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 檢查 <IssueNhiCode> 診療項目，在病患過去 <GapDay> 天紀錄中，不應包含特定的 <TreatmentNhiCode> 診療代碼，確認結果是否為 <PassOrNot> 且檢查訊息類型為 D4_1
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastTreatmentDays | TreatmentNhiCode | TreatmentTeeth | TreatmentSurface | GapDay | PassOrNot |
            | 91089C       | FM         | MOB          | 89                | 91089C           | FM             | MOB              | 90     | NotPass   |
            | 91089C       | FM         | MOB          | 90                | 91089C           | FM             | MOB              | 90     | NotPass   |
            | 91089C       | FM         | MOB          | 91                | 91089C           | FM             | MOB              | 90     | Pass      |

    Scenario Outline: （IC）90天內，不應有 91089C 診療項目
        Given 建立醫師
        Given Kelly 24 歲病人
        Given 新增健保醫療:
            | PastDays          | NhiCode          | Teeth          |
            | <PastMedicalDays> | <MedicalNhiCode> | <MedicalTeeth> |
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 檢查 <IssueNhiCode> 診療項目，在病患過去 <GapDay> 天紀錄中，不應包含特定的 <MedicalNhiCode> 診療代碼，確認結果是否為 <PassOrNot> 且檢查訊息類型為 D4_1
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastMedicalDays | MedicalNhiCode | MedicalTeeth | GapDay | PassOrNot |
            | 91089C       | FM         | MOB          | 89              | 91089C         | FM           | 90     | NotPass   |
            | 91089C       | FM         | MOB          | 90              | 91089C         | FM           | 90     | NotPass   |
            | 91089C       | FM         | MOB          | 91              | 91089C         | FM           | 90     | Pass      |

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
            | 91089C       | 51         | DL           | NotPass   |
            | 91089C       | 52         | DL           | NotPass   |
            | 91089C       | 53         | DL           | NotPass   |
            | 91089C       | 54         | DL           | NotPass   |
            | 91089C       | 55         | DL           | NotPass   |
            | 91089C       | 61         | DL           | NotPass   |
            | 91089C       | 62         | DL           | NotPass   |
            | 91089C       | 63         | DL           | NotPass   |
            | 91089C       | 64         | DL           | NotPass   |
            | 91089C       | 65         | DL           | NotPass   |
            | 91089C       | 71         | DL           | NotPass   |
            | 91089C       | 72         | DL           | NotPass   |
            | 91089C       | 73         | DL           | NotPass   |
            | 91089C       | 74         | DL           | NotPass   |
            | 91089C       | 75         | DL           | NotPass   |
            | 91089C       | 81         | DL           | NotPass   |
            | 91089C       | 82         | DL           | NotPass   |
            | 91089C       | 83         | DL           | NotPass   |
            | 91089C       | 84         | DL           | NotPass   |
            | 91089C       | 85         | DL           | NotPass   |
            # 恆牙
            | 91089C       | 11         | DL           | NotPass   |
            | 91089C       | 12         | DL           | NotPass   |
            | 91089C       | 13         | DL           | NotPass   |
            | 91089C       | 14         | DL           | NotPass   |
            | 91089C       | 15         | DL           | NotPass   |
            | 91089C       | 16         | DL           | NotPass   |
            | 91089C       | 17         | DL           | NotPass   |
            | 91089C       | 18         | DL           | NotPass   |
            | 91089C       | 21         | DL           | NotPass   |
            | 91089C       | 22         | DL           | NotPass   |
            | 91089C       | 23         | DL           | NotPass   |
            | 91089C       | 24         | DL           | NotPass   |
            | 91089C       | 25         | DL           | NotPass   |
            | 91089C       | 26         | DL           | NotPass   |
            | 91089C       | 27         | DL           | NotPass   |
            | 91089C       | 28         | DL           | NotPass   |
            | 91089C       | 31         | DL           | NotPass   |
            | 91089C       | 32         | DL           | NotPass   |
            | 91089C       | 33         | DL           | NotPass   |
            | 91089C       | 34         | DL           | NotPass   |
            | 91089C       | 35         | DL           | NotPass   |
            | 91089C       | 36         | DL           | NotPass   |
            | 91089C       | 37         | DL           | NotPass   |
            | 91089C       | 38         | DL           | NotPass   |
            | 91089C       | 41         | DL           | NotPass   |
            | 91089C       | 42         | DL           | NotPass   |
            | 91089C       | 43         | DL           | NotPass   |
            | 91089C       | 44         | DL           | NotPass   |
            | 91089C       | 45         | DL           | NotPass   |
            | 91089C       | 46         | DL           | NotPass   |
            | 91089C       | 47         | DL           | NotPass   |
            | 91089C       | 48         | DL           | NotPass   |
            # 無牙
            | 91089C       |            | DL           | NotPass   |
            #
            | 91089C       | 19         | DL           | NotPass   |
            | 91089C       | 29         | DL           | NotPass   |
            | 91089C       | 39         | DL           | NotPass   |
            | 91089C       | 49         | DL           | NotPass   |
            | 91089C       | 59         | DL           | NotPass   |
            | 91089C       | 69         | DL           | NotPass   |
            | 91089C       | 79         | DL           | NotPass   |
            | 91089C       | 89         | DL           | NotPass   |
            | 91089C       | 99         | DL           | NotPass   |
            # 牙位為區域型態
            | 91089C       | FM         | DL           | Pass      |
            | 91089C       | UR         | DL           | NotPass   |
            | 91089C       | UL         | DL           | NotPass   |
            | 91089C       | UA         | DL           | NotPass   |
            | 91089C       | UB         | DL           | NotPass   |
            | 91089C       | LL         | DL           | NotPass   |
            | 91089C       | LR         | DL           | NotPass   |
            | 91089C       | LA         | DL           | NotPass   |
            | 91089C       | LB         | DL           | NotPass   |
            # 非法牙位
            | 91089C       | 00         | DL           | NotPass   |
            | 91089C       | 01         | DL           | NotPass   |
            | 91089C       | 10         | DL           | NotPass   |
            | 91089C       | 56         | DL           | NotPass   |
            | 91089C       | 66         | DL           | NotPass   |
            | 91089C       | 76         | DL           | NotPass   |
            | 91089C       | 86         | DL           | NotPass   |
            | 91089C       | 91         | DL           | NotPass   |

    Scenario Outline: （HIS）90天內，不應有 91005C/91015C~91018C 診療項目
        Given 建立醫師
        Given Stan 24 歲病人
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
        Then 檢查 <IssueNhiCode> 診療項目，在病患過去 <GapDay> 天紀錄中，不應包含特定的 <TreatmentNhiCode> 診療代碼，確認結果是否為 <PassOrNot> 且檢查訊息類型為 D1_2
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastTreatmentDays | TreatmentNhiCode | TreatmentTeeth | GapDay | PassOrNot |
            | 91089C       | FM         | DL           | 89                | 91005C           | UL             | 90     | NotPass   |
            | 91089C       | FM         | DL           | 90                | 91005C           | UL             | 90     | NotPass   |
            | 91089C       | FM         | DL           | 91                | 91005C           | UL             | 90     | Pass      |
            | 91089C       | FM         | DL           | 89                | 91015C           | UL             | 90     | NotPass   |
            | 91089C       | FM         | DL           | 90                | 91015C           | UL             | 90     | NotPass   |
            | 91089C       | FM         | DL           | 91                | 91015C           | UL             | 90     | Pass      |
            | 91089C       | FM         | DL           | 89                | 91016C           | UL             | 90     | NotPass   |
            | 91089C       | FM         | DL           | 90                | 91016C           | UL             | 90     | NotPass   |
            | 91089C       | FM         | DL           | 91                | 91016C           | UL             | 90     | Pass      |
            | 91089C       | FM         | DL           | 89                | 91017C           | UL             | 90     | NotPass   |
            | 91089C       | FM         | DL           | 90                | 91017C           | UL             | 90     | NotPass   |
            | 91089C       | FM         | DL           | 91                | 91017C           | UL             | 90     | Pass      |
            | 91089C       | FM         | DL           | 89                | 91018C           | UL             | 90     | NotPass   |
            | 91089C       | FM         | DL           | 90                | 91018C           | UL             | 90     | NotPass   |
            | 91089C       | FM         | DL           | 91                | 91018C           | UL             | 90     | Pass      |

    Scenario Outline: （IC）90天內，不應有 91005C/91015C~91018C 診療項目
        Given 建立醫師
        Given Stan 24 歲病人
        Given 新增健保醫療:
            | PastDays          | NhiCode          | Teeth          |
            | <PastMedicalDays> | <MedicalNhiCode> | <MedicalTeeth> |
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 檢查 <IssueNhiCode> 診療項目，在病患過去 <GapDay> 天紀錄中，不應包含特定的 <MedicalNhiCode> 診療代碼，確認結果是否為 <PassOrNot> 且檢查訊息類型為 D1_2
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastMedicalDays | MedicalNhiCode | MedicalTeeth | GapDay | PassOrNot |
            | 91089C       | FM         | DL           | 89              | 91005C         | UL           | 90     | NotPass   |
            | 91089C       | FM         | DL           | 90              | 91005C         | UL           | 90     | NotPass   |
            | 91089C       | FM         | DL           | 91              | 91005C         | UL           | 90     | Pass      |
            | 91089C       | FM         | DL           | 89              | 91015C         | UL           | 90     | NotPass   |
            | 91089C       | FM         | DL           | 90              | 91015C         | UL           | 90     | NotPass   |
            | 91089C       | FM         | DL           | 91              | 91015C         | UL           | 90     | Pass      |
            | 91089C       | FM         | DL           | 89              | 91016C         | UL           | 90     | NotPass   |
            | 91089C       | FM         | DL           | 90              | 91016C         | UL           | 90     | NotPass   |
            | 91089C       | FM         | DL           | 91              | 91016C         | UL           | 90     | Pass      |
            | 91089C       | FM         | DL           | 89              | 91017C         | UL           | 90     | NotPass   |
            | 91089C       | FM         | DL           | 90              | 91017C         | UL           | 90     | NotPass   |
            | 91089C       | FM         | DL           | 91              | 91017C         | UL           | 90     | Pass      |
            | 91089C       | FM         | DL           | 89              | 91018C         | UL           | 90     | NotPass   |
            | 91089C       | FM         | DL           | 90              | 91018C         | UL           | 90     | NotPass   |
            | 91089C       | FM         | DL           | 91              | 91018C         | UL           | 90     | Pass      |
