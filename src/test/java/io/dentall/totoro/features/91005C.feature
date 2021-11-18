@nhi @nhi-91-series @part1
Feature: 91005C 口乾症牙結石清除-全口

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
            | 91005C       | FM         | MOB          | Pass      |

    Scenario Outline: 提醒限口乾症患者施行申報
        Given 建立醫師
        Given Stan 24 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 提醒"限口乾症患者施行申報"，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            | 91005C       | FM         | MOB          | Pass      |

    Scenario Outline: （HIS）90天內，不應有 91005C 診療項目
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
        Then 檢查 <IssueNhiCode> 診療項目，在病患過去 <GapDay> 天紀錄中，不應包含特定的 <TreatmentNhiCode> 診療代碼，確認結果是否為 <PassOrNot> 且檢查訊息類型為 D4_1
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastTreatmentDays | TreatmentNhiCode | TreatmentTeeth | GapDay | PassOrNot |
            | 91005C       | FM         | DL           | 89                | 91005C           | FM             | 90     | NotPass   |
            | 91005C       | FM         | DL           | 90                | 91005C           | FM             | 90     | NotPass   |
            | 91005C       | FM         | DL           | 91                | 91005C           | FM             | 90     | Pass      |

    Scenario Outline: （IC）90天內，不應有 91005C 診療項目
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
        Then 檢查 <IssueNhiCode> 診療項目，在病患過去 <GapDay> 天紀錄中，不應包含特定的 <MedicalNhiCode> 診療代碼，確認結果是否為 <PassOrNot> 且檢查訊息類型為 D4_1
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastMedicalDays | MedicalNhiCode | MedicalTeeth | GapDay | PassOrNot |
            | 91005C       | FM         | DL           | 89              | 91005C         | FM           | 90     | NotPass   |
            | 91005C       | FM         | DL           | 90              | 91005C         | FM           | 90     | NotPass   |
            | 91005C       | FM         | DL           | 91              | 91005C         | FM           | 90     | Pass      |

    Scenario Outline: （HIS）90天內，不應有 91003/91015C/91016C/91017C/91089C 診療項目
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
            | 91005C       | FM         | DL           | 89                | 91003C           | FM             | 90     | NotPass   |
            | 91005C       | FM         | DL           | 90                | 91003C           | FM             | 90     | NotPass   |
            | 91005C       | FM         | DL           | 91                | 91003C           | FM             | 90     | Pass      |
            | 91005C       | FM         | DL           | 89                | 91015C           | FM             | 90     | NotPass   |
            | 91005C       | FM         | DL           | 90                | 91015C           | FM             | 90     | NotPass   |
            | 91005C       | FM         | DL           | 91                | 91015C           | FM             | 90     | Pass      |
            | 91005C       | FM         | DL           | 89                | 91016C           | FM             | 90     | NotPass   |
            | 91005C       | FM         | DL           | 90                | 91016C           | FM             | 90     | NotPass   |
            | 91005C       | FM         | DL           | 91                | 91016C           | FM             | 90     | Pass      |
            | 91005C       | FM         | DL           | 89                | 91017C           | FM             | 90     | NotPass   |
            | 91005C       | FM         | DL           | 90                | 91017C           | FM             | 90     | NotPass   |
            | 91005C       | FM         | DL           | 91                | 91017C           | FM             | 90     | Pass      |
            | 91005C       | FM         | DL           | 89                | 91089C           | FM             | 90     | NotPass   |
            | 91005C       | FM         | DL           | 90                | 91089C           | FM             | 90     | NotPass   |
            | 91005C       | FM         | DL           | 91                | 91089C           | FM             | 90     | Pass      |

    Scenario Outline: （IC）90天內，不應有 91003/91015C/91016C/91017C/91089C 診療項目
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
            | 91005C       | FM         | DL           | 89              | 91003C         | FM           | 90     | NotPass   |
            | 91005C       | FM         | DL           | 90              | 91003C         | FM           | 90     | NotPass   |
            | 91005C       | FM         | DL           | 91              | 91003C         | FM           | 90     | Pass      |
            | 91005C       | FM         | DL           | 89              | 91015C         | FM           | 90     | NotPass   |
            | 91005C       | FM         | DL           | 90              | 91015C         | FM           | 90     | NotPass   |
            | 91005C       | FM         | DL           | 91              | 91015C         | FM           | 90     | Pass      |
            | 91005C       | FM         | DL           | 89              | 91016C         | FM           | 90     | NotPass   |
            | 91005C       | FM         | DL           | 90              | 91016C         | FM           | 90     | NotPass   |
            | 91005C       | FM         | DL           | 91              | 91016C         | FM           | 90     | Pass      |
            | 91005C       | FM         | DL           | 89              | 91017C         | FM           | 90     | NotPass   |
            | 91005C       | FM         | DL           | 90              | 91017C         | FM           | 90     | NotPass   |
            | 91005C       | FM         | DL           | 91              | 91017C         | FM           | 90     | Pass      |
            | 91005C       | FM         | DL           | 89              | 91089C         | FM           | 90     | NotPass   |
            | 91005C       | FM         | DL           | 90              | 91089C         | FM           | 90     | NotPass   |
            | 91005C       | FM         | DL           | 91              | 91089C         | FM           | 90     | Pass      |

    Scenario Outline: 檢查治療的牙位是否為 FULL_ZONE
        Given 建立醫師
        Given Stan 24 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 檢查 <IssueTeeth> 牙位，依 FULL_ZONE 判定是否為核可牙位，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            # 乳牙
            | 91005C       | 51         | DL           | NotPass   |
            | 91005C       | 52         | DL           | NotPass   |
            | 91005C       | 53         | DL           | NotPass   |
            | 91005C       | 54         | DL           | NotPass   |
            | 91005C       | 55         | DL           | NotPass   |
            | 91005C       | 61         | DL           | NotPass   |
            | 91005C       | 62         | DL           | NotPass   |
            | 91005C       | 63         | DL           | NotPass   |
            | 91005C       | 64         | DL           | NotPass   |
            | 91005C       | 65         | DL           | NotPass   |
            | 91005C       | 71         | DL           | NotPass   |
            | 91005C       | 72         | DL           | NotPass   |
            | 91005C       | 73         | DL           | NotPass   |
            | 91005C       | 74         | DL           | NotPass   |
            | 91005C       | 75         | DL           | NotPass   |
            | 91005C       | 81         | DL           | NotPass   |
            | 91005C       | 82         | DL           | NotPass   |
            | 91005C       | 83         | DL           | NotPass   |
            | 91005C       | 84         | DL           | NotPass   |
            | 91005C       | 85         | DL           | NotPass   |
            # 恆牙
            | 91005C       | 11         | DL           | NotPass   |
            | 91005C       | 12         | DL           | NotPass   |
            | 91005C       | 13         | DL           | NotPass   |
            | 91005C       | 14         | DL           | NotPass   |
            | 91005C       | 15         | DL           | NotPass   |
            | 91005C       | 16         | DL           | NotPass   |
            | 91005C       | 17         | DL           | NotPass   |
            | 91005C       | 18         | DL           | NotPass   |
            | 91005C       | 21         | DL           | NotPass   |
            | 91005C       | 22         | DL           | NotPass   |
            | 91005C       | 23         | DL           | NotPass   |
            | 91005C       | 24         | DL           | NotPass   |
            | 91005C       | 25         | DL           | NotPass   |
            | 91005C       | 26         | DL           | NotPass   |
            | 91005C       | 27         | DL           | NotPass   |
            | 91005C       | 28         | DL           | NotPass   |
            | 91005C       | 31         | DL           | NotPass   |
            | 91005C       | 32         | DL           | NotPass   |
            | 91005C       | 33         | DL           | NotPass   |
            | 91005C       | 34         | DL           | NotPass   |
            | 91005C       | 35         | DL           | NotPass   |
            | 91005C       | 36         | DL           | NotPass   |
            | 91005C       | 37         | DL           | NotPass   |
            | 91005C       | 38         | DL           | NotPass   |
            | 91005C       | 41         | DL           | NotPass   |
            | 91005C       | 42         | DL           | NotPass   |
            | 91005C       | 43         | DL           | NotPass   |
            | 91005C       | 44         | DL           | NotPass   |
            | 91005C       | 45         | DL           | NotPass   |
            | 91005C       | 46         | DL           | NotPass   |
            | 91005C       | 47         | DL           | NotPass   |
            | 91005C       | 48         | DL           | NotPass   |
            # 無牙
            | 91005C       |            | DL           | NotPass   |
            #
            | 91005C       | 19         | DL           | NotPass   |
            | 91005C       | 29         | DL           | NotPass   |
            | 91005C       | 39         | DL           | NotPass   |
            | 91005C       | 49         | DL           | NotPass   |
            | 91005C       | 59         | DL           | NotPass   |
            | 91005C       | 69         | DL           | NotPass   |
            | 91005C       | 79         | DL           | NotPass   |
            | 91005C       | 89         | DL           | NotPass   |
            | 91005C       | 99         | DL           | NotPass   |
            # 牙位為區域型態
            | 91005C       | FM         | DL           | Pass      |
            | 91005C       | UR         | DL           | NotPass   |
            | 91005C       | UL         | DL           | NotPass   |
            | 91005C       | UA         | DL           | NotPass   |
            | 91005C       | UB         | DL           | NotPass   |
            | 91005C       | LL         | DL           | NotPass   |
            | 91005C       | LR         | DL           | NotPass   |
            | 91005C       | LA         | DL           | NotPass   |
            | 91005C       | LB         | DL           | NotPass   |
            # 非法牙位
            | 91005C       | 00         | DL           | NotPass   |
            | 91005C       | 01         | DL           | NotPass   |
            | 91005C       | 10         | DL           | NotPass   |
            | 91005C       | 56         | DL           | NotPass   |
            | 91005C       | 66         | DL           | NotPass   |
            | 91005C       | 76         | DL           | NotPass   |
            | 91005C       | 86         | DL           | NotPass   |
            | 91005C       | 91         | DL           | NotPass   |
