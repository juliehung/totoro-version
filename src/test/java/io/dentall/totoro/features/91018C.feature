Feature: 91018C 牙周病支持性治療

    Scenario Outline: 全部檢核成功
        Given 建立醫師
        Given Kelly 24 歲病人
        Given 在過去第 91 天，建立預約
        Given 在過去第 91 天，建立掛號
        Given 在過去第 91 天，產生診療計畫
        And 新增診療代碼:
            | PastDays | A72 | A73    | A74 | A75 | A76 | A77 | A78 | A79 |
            | 91       | 3   | 91022C | FM  | MOB | 0   | 1.0 | 03  |     |
        Given 在過去第 45 天，建立預約
        Given 在過去第 45 天，建立掛號
        Given 在過去第 45 天，產生診療計畫
        And 新增診療代碼:
            | PastDays | A72 | A73    | A74 | A75 | A76 | A77 | A78 | A79 |
            | 45       | 3   | 91023C | FM  | MOB | 0   | 1.0 | 03  |     |
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 確認診療代碼 <IssueNhiCode> ，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            | 91018C       | 11         | MOB          | Pass      |

    Scenario Outline: （HIS）欲申報91018C，需與第二階段91022C間隔九十天
        Given 建立醫師
        Given Kelly 24 歲病人
        Given 在過去第 <PastTreatmentDays> 天，建立預約
        Given 在過去第 <PastTreatmentDays> 天，建立掛號
        Given 在過去第 <PastTreatmentDays> 天，產生診療計畫
        And 新增診療代碼:
            | PastDays            | A72 | A73                | A74              | A75                | A76 | A77 | A78 | A79 |
            | <PastTreatmentDays> | 3   | <TreatmentNhiCode> | <TreatmentTeeth> | <TreatmentSurface> | 0   | 1.0 | 03  |     |
        Given 在過去第 45 天，建立預約
        Given 在過去第 45 天，建立掛號
        Given 在過去第 45 天，產生診療計畫
        And 新增診療代碼:
            | PastDays | A72 | A73    | A74 | A75 | A76 | A77 | A78 | A79 |
            | 45       | 3   | 91023C | FM  | MOB | 0   | 1.0 | 03  |     |
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then （HIS）檢查 <IssueNhiCode> 診療項目，在病患過去 <GapDay> 天紀錄中，不應包含特定的 <TreatmentNhiCode> 診療代碼，確認結果是否為 <PassOrNot> 且檢查訊息類型為 PERIO_1
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastTreatmentDays | TreatmentNhiCode | TreatmentTeeth | TreatmentSurface | GapDay | PassOrNot |
            | 91018C       | FM         | MOB          | 89                | 91022C           | FM             | MOB              | 90     | NotPass   |
            | 91018C       | FM         | MOB          | 90                | 91022C           | FM             | MOB              | 90     | NotPass   |
            | 91018C       | FM         | MOB          | 91                | 91022C           | FM             | MOB              | 90     | Pass      |

    Scenario Outline: （HIS）欲申報91018C，需先有第三階段91023C患者之牙醫醫療服務
        Given 建立醫師
        Given Kelly 24 歲病人
        Given 在過去第 91 天，建立預約
        Given 在過去第 91 天，建立掛號
        Given 在過去第 91 天，產生診療計畫
        And 新增診療代碼:
            | PastDays | A72 | A73    | A74 | A75 | A76 | A77 | A78 | A79 |
            | 91       | 3   | 91022C | FM  | MOB | 0   | 1.0 | 03  |     |
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
        Then （HIS）檢查 90 天內，應有 <TreatmentNhiCode> 診療項目存在，確認結果是否為 <PassOrNot> 且檢查訊息類型為 PERIO_1
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastTreatmentDays | TreatmentNhiCode | TreatmentTeeth | PassOrNot |
            | 91018C       | 11         | MOB          | 89                | 91023C           | 11             | Pass      |
            | 91018C       | 11         | MOB          | 90                | 91023C           | 11             | Pass      |
            | 91018C       | 11         | MOB          | 91                | 91023C           | 11             | NotPass   |

    Scenario Outline: （HIS）90天內，不應有 91018C 診療項目
        Given 建立醫師
        Given Kelly 24 歲病人
        Given 在過去第 91 天，建立預約
        Given 在過去第 91 天，建立掛號
        Given 在過去第 91 天，產生診療計畫
        And 新增診療代碼:
            | PastDays | A72 | A73    | A74 | A75 | A76 | A77 | A78 | A79 |
            | 91       | 3   | 91022C | FM  | MOB | 0   | 1.0 | 03  |     |
        Given 在過去第 45 天，建立預約
        Given 在過去第 45 天，建立掛號
        Given 在過去第 45 天，產生診療計畫
        And 新增診療代碼:
            | PastDays | A72 | A73    | A74 | A75 | A76 | A77 | A78 | A79 |
            | 45       | 3   | 91023C | FM  | MOB | 0   | 1.0 | 03  |     |
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
        Then （HIS）檢查 <IssueNhiCode> 診療項目，在病患過去 <GapDay> 天紀錄中，不應包含特定的 <TreatmentNhiCode> 診療代碼，確認結果是否為 <PassOrNot> 且檢查訊息類型為 D4_1
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastTreatmentDays | TreatmentNhiCode | TreatmentTeeth | TreatmentSurface | GapDay | PassOrNot |
            | 91018C       | FM         | MOB          | 89                | 91018C           | FM             | MOB              | 90     | NotPass   |
            | 91018C       | FM         | MOB          | 90                | 91018C           | FM             | MOB              | 90     | NotPass   |
            | 91018C       | FM         | MOB          | 91                | 91018C           | FM             | MOB              | 90     | Pass      |

    Scenario Outline: （IC）90天內，不應有 91018C 診療項目
        Given 建立醫師
        Given Kelly 24 歲病人
        Given 在過去第 91 天，建立預約
        Given 在過去第 91 天，建立掛號
        Given 在過去第 91 天，產生診療計畫
        And 新增診療代碼:
            | PastDays | A72 | A73    | A74 | A75 | A76 | A77 | A78 | A79 |
            | 91       | 3   | 91022C | FM  | MOB | 0   | 1.0 | 03  |     |
        Given 在過去第 45 天，建立預約
        Given 在過去第 45 天，建立掛號
        Given 在過去第 45 天，產生診療計畫
        And 新增診療代碼:
            | PastDays | A72 | A73    | A74 | A75 | A76 | A77 | A78 | A79 |
            | 45       | 3   | 91023C | FM  | MOB | 0   | 1.0 | 03  |     |
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
            | 91018C       | FM         | MOB          | 89              | 91018C         | FM           | 90     | NotPass   |
            | 91018C       | FM         | MOB          | 90              | 91018C         | FM           | 90     | NotPass   |
            | 91018C       | FM         | MOB          | 91              | 91018C         | FM           | 90     | Pass      |

    Scenario Outline: （HIS）180天內，不應有 91006C~91008C 診療項目
        Given 建立醫師
        Given Kelly 24 歲病人
        Given 在過去第 <PastTreatmentDays> 天，建立預約
        Given 在過去第 <PastTreatmentDays> 天，建立掛號
        Given 在過去第 <PastTreatmentDays> 天，產生診療計畫
        And 新增診療代碼:
            | PastDays            | A72 | A73                | A74              | A75                | A76 | A77 | A78 | A79 |
            | <PastTreatmentDays> | 3   | <TreatmentNhiCode> | <TreatmentTeeth> | <TreatmentSurface> | 0   | 1.0 | 03  |     |
        Given 在過去第 91 天，建立預約
        Given 在過去第 91 天，建立掛號
        Given 在過去第 91 天，產生診療計畫
        And 新增診療代碼:
            | PastDays | A72 | A73    | A74 | A75 | A76 | A77 | A78 | A79 |
            | 91       | 3   | 91022C | FM  | MOB | 0   | 1.0 | 03  |     |
        Given 在過去第 45 天，建立預約
        Given 在過去第 45 天，建立掛號
        Given 在過去第 45 天，產生診療計畫
        And 新增診療代碼:
            | PastDays | A72 | A73    | A74 | A75 | A76 | A77 | A78 | A79 |
            | 45       | 3   | 91023C | FM  | MOB | 0   | 1.0 | 03  |     |
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then （HIS）檢查 <IssueNhiCode> 診療項目，在病患過去 <GapDay> 天紀錄中，不應包含特定的 <TreatmentNhiCode> 診療代碼，確認結果是否為 <PassOrNot> 且檢查訊息類型為 D1_2
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastTreatmentDays | TreatmentNhiCode | TreatmentTeeth | TreatmentSurface | GapDay | PassOrNot |
            | 91018C       | FM         | MOB          | 179               | 91006C           | FM             | MOB              | 180    | NotPass   |
            | 91018C       | FM         | MOB          | 180               | 91006C           | FM             | MOB              | 180    | NotPass   |
            | 91018C       | FM         | MOB          | 181               | 91006C           | FM             | MOB              | 180    | Pass      |
            | 91018C       | FM         | MOB          | 179               | 91007C           | FM             | MOB              | 180    | NotPass   |
            | 91018C       | FM         | MOB          | 180               | 91007C           | FM             | MOB              | 180    | NotPass   |
            | 91018C       | FM         | MOB          | 181               | 91007C           | FM             | MOB              | 180    | Pass      |
            | 91018C       | FM         | MOB          | 179               | 91008C           | FM             | MOB              | 180    | NotPass   |
            | 91018C       | FM         | MOB          | 180               | 91008C           | FM             | MOB              | 180    | NotPass   |
            | 91018C       | FM         | MOB          | 181               | 91008C           | FM             | MOB              | 180    | Pass      |

    Scenario Outline: （IC）180天內，不應有 91006C~91008C 診療項目
        Given 建立醫師
        Given Kelly 24 歲病人
        Given 新增健保醫療:
            | PastDays          | NhiCode          | Teeth          |
            | <PastMedicalDays> | <MedicalNhiCode> | <MedicalTeeth> |
        Given 在過去第 91 天，建立預約
        Given 在過去第 91 天，建立掛號
        Given 在過去第 91 天，產生診療計畫
        And 新增診療代碼:
            | PastDays | A72 | A73    | A74 | A75 | A76 | A77 | A78 | A79 |
            | 91       | 3   | 91022C | FM  | MOB | 0   | 1.0 | 03  |     |
        Given 在過去第 45 天，建立預約
        Given 在過去第 45 天，建立掛號
        Given 在過去第 45 天，產生診療計畫
        And 新增診療代碼:
            | PastDays | A72 | A73    | A74 | A75 | A76 | A77 | A78 | A79 |
            | 45       | 3   | 91023C | FM  | MOB | 0   | 1.0 | 03  |     |
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then （IC）檢查 <IssueNhiCode> 診療項目，在病患過去 <GapDay> 天紀錄中，不應包含特定的 <MedicalNhiCode> 診療代碼，確認結果是否為 <PassOrNot> 且檢查訊息類型為 D1_2
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastMedicalDays | MedicalNhiCode | MedicalTeeth | GapDay | PassOrNot |
            | 91018C       | FM         | MOB          | 179             | 91006C         | FM           | 180    | NotPass   |
            | 91018C       | FM         | MOB          | 180             | 91006C         | FM           | 180    | NotPass   |
            | 91018C       | FM         | MOB          | 181             | 91006C         | FM           | 180    | Pass      |
            | 91018C       | FM         | MOB          | 179             | 91007C         | FM           | 180    | NotPass   |
            | 91018C       | FM         | MOB          | 180             | 91007C         | FM           | 180    | NotPass   |
            | 91018C       | FM         | MOB          | 181             | 91007C         | FM           | 180    | Pass      |
            | 91018C       | FM         | MOB          | 179             | 91008C         | FM           | 180    | NotPass   |
            | 91018C       | FM         | MOB          | 180             | 91008C         | FM           | 180    | NotPass   |
            | 91018C       | FM         | MOB          | 181             | 91008C         | FM           | 180    | Pass      |
