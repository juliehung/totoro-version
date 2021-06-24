Feature: 91006C 齒齦下括除術(含牙根整平術)-全口

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
            | 91006C       | FM         | MOB          | Pass      |

    Scenario Outline: （HIS）90天內，不應有 91006C~91008/91015C/91016C/91018C 診療項目
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
        Then （HIS）檢查 <IssueNhiCode> 診療項目，在病患過去 <GapDay> 天紀錄中，不應包含特定的 <TreatmentNhiCode> 診療代碼，確認結果是否為 <PassOrNot> 且檢查訊息類型為 D1_2
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastTreatmentDays | TreatmentNhiCode | TreatmentTeeth | GapDay | PassOrNot |
            | 91006C       | FM         | DL           | 89                | 91006C           | FM             | 90     | NotPass   |
            | 91006C       | FM         | DL           | 90                | 91006C           | FM             | 90     | NotPass   |
            | 91006C       | FM         | DL           | 91                | 91006C           | FM             | 90     | Pass      |
            | 91006C       | FM         | DL           | 89                | 91007C           | FM             | 90     | NotPass   |
            | 91006C       | FM         | DL           | 90                | 91007C           | FM             | 90     | NotPass   |
            | 91006C       | FM         | DL           | 91                | 91007C           | FM             | 90     | Pass      |
            | 91006C       | FM         | DL           | 89                | 91008C           | FM             | 90     | NotPass   |
            | 91006C       | FM         | DL           | 90                | 91008C           | FM             | 90     | NotPass   |
            | 91006C       | FM         | DL           | 91                | 91008C           | FM             | 90     | Pass      |
            | 91006C       | FM         | DL           | 89                | 91015C           | FM             | 90     | NotPass   |
            | 91006C       | FM         | DL           | 90                | 91015C           | FM             | 90     | NotPass   |
            | 91006C       | FM         | DL           | 91                | 91015C           | FM             | 90     | Pass      |
            | 91006C       | FM         | DL           | 89                | 91016C           | FM             | 90     | NotPass   |
            | 91006C       | FM         | DL           | 90                | 91016C           | FM             | 90     | NotPass   |
            | 91006C       | FM         | DL           | 91                | 91016C           | FM             | 90     | Pass      |
            | 91006C       | FM         | DL           | 89                | 91018C           | FM             | 90     | NotPass   |
            | 91006C       | FM         | DL           | 90                | 91018C           | FM             | 90     | NotPass   |
            | 91006C       | FM         | DL           | 91                | 91018C           | FM             | 90     | Pass      |

    Scenario Outline: （IC）90天內，不應有 91006C~91008/91015C/91016C/91018C 診療項目
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
        Then （IC）檢查 <IssueNhiCode> 診療項目，在病患過去 <GapDay> 天紀錄中，不應包含特定的 <MedicalNhiCode> 診療代碼，確認結果是否為 <PassOrNot> 且檢查訊息類型為 D1_2
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastMedicalDays | MedicalNhiCode | MedicalTeeth | GapDay | PassOrNot |
            | 91006C       | FM         | DL           | 89              | 91006C         | FM           | 90     | NotPass   |
            | 91006C       | FM         | DL           | 90              | 91006C         | FM           | 90     | NotPass   |
            | 91006C       | FM         | DL           | 91              | 91006C         | FM           | 90     | Pass      |
            | 91006C       | FM         | DL           | 89              | 91007C         | FM           | 90     | NotPass   |
            | 91006C       | FM         | DL           | 90              | 91007C         | FM           | 90     | NotPass   |
            | 91006C       | FM         | DL           | 91              | 91007C         | FM           | 90     | Pass      |
            | 91006C       | FM         | DL           | 89              | 91008C         | FM           | 90     | NotPass   |
            | 91006C       | FM         | DL           | 90              | 91008C         | FM           | 90     | NotPass   |
            | 91006C       | FM         | DL           | 91              | 91008C         | FM           | 90     | Pass      |
            | 91006C       | FM         | DL           | 89              | 91015C         | FM           | 90     | NotPass   |
            | 91006C       | FM         | DL           | 90              | 91015C         | FM           | 90     | NotPass   |
            | 91006C       | FM         | DL           | 91              | 91015C         | FM           | 90     | Pass      |
            | 91006C       | FM         | DL           | 89              | 91016C         | FM           | 90     | NotPass   |
            | 91006C       | FM         | DL           | 90              | 91016C         | FM           | 90     | NotPass   |
            | 91006C       | FM         | DL           | 91              | 91016C         | FM           | 90     | Pass      |
            | 91006C       | FM         | DL           | 89              | 91018C         | FM           | 90     | NotPass   |
            | 91006C       | FM         | DL           | 90              | 91018C         | FM           | 90     | NotPass   |
            | 91006C       | FM         | DL           | 91              | 91018C         | FM           | 90     | Pass      |

    Scenario Outline: 提醒須檢附影像
        Given 建立醫師
        Given Stan 24 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 提醒"須檢附影像"，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            | 91006C       | FM         | MOB          | Pass      |

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
            | 91006C       | UR         | DL           | NotPass   |
            | 91006C       | UL         | DL           | NotPass   |
            | 91006C       | LR         | DL           | NotPass   |
            | 91006C       | LL         | DL           | NotPass   |
            | 91006C       | UA         | DL           | NotPass   |
            | 91006C       | UB         | DL           | NotPass   |
            | 91006C       | LA         | DL           | NotPass   |
            | 91006C       | LB         | DL           | NotPass   |
            | 91006C       | FM         | DL           | Pass      |
            | 91006C       | 14         | DL           | NotPass   |
            | 91006C       | 35         | DL           | NotPass   |
            | 91006C       | 53         | DL           | NotPass   |
