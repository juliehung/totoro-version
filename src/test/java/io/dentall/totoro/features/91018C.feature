@nhi @nhi-91-series @part3
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
            | 91018C       | FM         | MOB          | Pass      |

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
        Then 檢查 <IssueNhiCode> 診療項目，在病患過去 <GapDay> 天紀錄中，不應包含特定的 <TreatmentNhiCode> 診療代碼，確認結果是否為 <PassOrNot> 且檢查訊息類型為 PERIO_1
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastTreatmentDays | TreatmentNhiCode | TreatmentTeeth | TreatmentSurface | GapDay | PassOrNot |
            | 91018C       | FM         | MOB          | 89                | 91022C           | FM             | MOB              | 90     | NotPass   |
            | 91018C       | FM         | MOB          | 90                | 91022C           | FM             | MOB              | 90     | NotPass   |
            | 91018C       | FM         | MOB          | 91                | 91022C           | FM             | MOB              | 90     | Pass      |

    Scenario Outline: （IC）欲申報91018C，需與第二階段91022C間隔九十天
        Given 建立醫師
        Given Kelly 24 歲病人
        Given 新增健保醫療:
            | PastDays          | NhiCode          | Teeth          |
            | <PastMedicalDays> | <MedicalNhiCode> | <MedicalTeeth> |
        Given 新增健保醫療:
            | PastDays | NhiCode | Teeth |
            | 45       | 91023C  | FM    |
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 檢查 <IssueNhiCode> 診療項目，在病患過去 <GapDay> 天紀錄中，不應包含特定的 <MedicalNhiCode> 診療代碼，確認結果是否為 <PassOrNot> 且檢查訊息類型為 PERIO_1
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastMedicalDays | MedicalNhiCode | MedicalTeeth | GapDay | PassOrNot |
            | 91018C       | FM         | MOB          | 89              | 91022C         | FM           | 90     | NotPass   |
            | 91018C       | FM         | MOB          | 90              | 91022C         | FM           | 90     | NotPass   |
            | 91018C       | FM         | MOB          | 91              | 91022C         | FM           | 90     | Pass      |

    Scenario Outline: （HIS）欲申報91018C，需先有第三階段91023C
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
        Then 檢查 90 天內，應有 <TreatmentNhiCode> 診療項目存在，確認結果是否為 <PassOrNot> 且檢查訊息類型為 PERIO_1
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastTreatmentDays | TreatmentNhiCode | TreatmentTeeth | PassOrNot |
            | 91018C       | FM         | MOB          | 89                | 91023C           | FM             | Pass      |
            | 91018C       | FM         | MOB          | 90                | 91023C           | FM             | Pass      |
            | 91018C       | FM         | MOB          | 91                | 91023C           | FM             | NotPass   |

    Scenario Outline: （IC）欲申報91018C，需先有第三階段91023C
        Given 建立醫師
        Given Kelly 24 歲病人
        Given 新增健保醫療:
            | PastDays | NhiCode | Teeth |
            | 91       | 91022C  | FM    |
        Given 新增健保醫療:
            | PastDays          | NhiCode          | Teeth          |
            | <PastMedicalDays> | <MedicalNhiCode> | <MedicalTeeth> |
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 檢查 90 天內，應有 <MedicalNhiCode> 診療項目存在，確認結果是否為 <PassOrNot> 且檢查訊息類型為 PERIO_1
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastMedicalDays | MedicalNhiCode | MedicalTeeth | PassOrNot |
            | 91018C       | FM         | MOB          | 89              | 91023C         | FM           | Pass      |
            | 91018C       | FM         | MOB          | 90              | 91023C         | FM           | Pass      |
            | 91018C       | FM         | MOB          | 91              | 91023C         | FM           | NotPass   |

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
        Then 檢查 <IssueNhiCode> 診療項目，在病患過去 <GapDay> 天紀錄中，不應包含特定的 <TreatmentNhiCode> 診療代碼，確認結果是否為 <PassOrNot> 且檢查訊息類型為 D4_1
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
        Then 檢查 <IssueNhiCode> 診療項目，在病患過去 <GapDay> 天紀錄中，不應包含特定的 <MedicalNhiCode> 診療代碼，確認結果是否為 <PassOrNot> 且檢查訊息類型為 D4_1
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastMedicalDays | MedicalNhiCode | MedicalTeeth | GapDay | PassOrNot |
            | 91018C       | FM         | MOB          | 89              | 91018C         | FM           | 90     | NotPass   |
            | 91018C       | FM         | MOB          | 90              | 91018C         | FM           | 90     | NotPass   |
            | 91018C       | FM         | MOB          | 91              | 91018C         | FM           | 90     | Pass      |

    Scenario Outline: （HIS）90天內，不應有 91089C 診療項目
        Given 建立醫師
        Given Kelly 24 歲病人
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
            | 91018C       | FM         | DL           | 89                | 91089C           | FM             | 90     | NotPass   |
            | 91018C       | FM         | DL           | 90                | 91089C           | FM             | 90     | NotPass   |
            | 91018C       | FM         | DL           | 91                | 91089C           | FM             | 90     | Pass      |

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
        Then 檢查 <IssueNhiCode> 診療項目，在病患過去 <GapDay> 天紀錄中，不應包含特定的 <MedicalNhiCode> 診療代碼，確認結果是否為 <PassOrNot> 且檢查訊息類型為 D1_2
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastMedicalDays | MedicalNhiCode | MedicalTeeth | GapDay | PassOrNot |
            | 91018C       | FM         | DL           | 89              | 91089C         | FM           | 90     | NotPass   |
            | 91018C       | FM         | DL           | 90              | 91089C         | FM           | 90     | NotPass   |
            | 91018C       | FM         | DL           | 91              | 91089C         | FM           | 90     | Pass      |

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
        Then 檢查 <IssueNhiCode> 診療項目，在病患過去 <GapDay> 天紀錄中，不應包含特定的 <TreatmentNhiCode> 診療代碼，確認結果是否為 <PassOrNot> 且檢查訊息類型為 D1_2
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
        Then 檢查 <IssueNhiCode> 診療項目，在病患過去 <GapDay> 天紀錄中，不應包含特定的 <MedicalNhiCode> 診療代碼，確認結果是否為 <PassOrNot> 且檢查訊息類型為 D1_2
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
            | 91018C       | 51         | DL           | NotPass   |
            | 91018C       | 52         | DL           | NotPass   |
            | 91018C       | 53         | DL           | NotPass   |
            | 91018C       | 54         | DL           | NotPass   |
            | 91018C       | 55         | DL           | NotPass   |
            | 91018C       | 61         | DL           | NotPass   |
            | 91018C       | 62         | DL           | NotPass   |
            | 91018C       | 63         | DL           | NotPass   |
            | 91018C       | 64         | DL           | NotPass   |
            | 91018C       | 65         | DL           | NotPass   |
            | 91018C       | 71         | DL           | NotPass   |
            | 91018C       | 72         | DL           | NotPass   |
            | 91018C       | 73         | DL           | NotPass   |
            | 91018C       | 74         | DL           | NotPass   |
            | 91018C       | 75         | DL           | NotPass   |
            | 91018C       | 81         | DL           | NotPass   |
            | 91018C       | 82         | DL           | NotPass   |
            | 91018C       | 83         | DL           | NotPass   |
            | 91018C       | 84         | DL           | NotPass   |
            | 91018C       | 85         | DL           | NotPass   |
            # 恆牙
            | 91018C       | 11         | DL           | NotPass   |
            | 91018C       | 12         | DL           | NotPass   |
            | 91018C       | 13         | DL           | NotPass   |
            | 91018C       | 14         | DL           | NotPass   |
            | 91018C       | 15         | DL           | NotPass   |
            | 91018C       | 16         | DL           | NotPass   |
            | 91018C       | 17         | DL           | NotPass   |
            | 91018C       | 18         | DL           | NotPass   |
            | 91018C       | 21         | DL           | NotPass   |
            | 91018C       | 22         | DL           | NotPass   |
            | 91018C       | 23         | DL           | NotPass   |
            | 91018C       | 24         | DL           | NotPass   |
            | 91018C       | 25         | DL           | NotPass   |
            | 91018C       | 26         | DL           | NotPass   |
            | 91018C       | 27         | DL           | NotPass   |
            | 91018C       | 28         | DL           | NotPass   |
            | 91018C       | 31         | DL           | NotPass   |
            | 91018C       | 32         | DL           | NotPass   |
            | 91018C       | 33         | DL           | NotPass   |
            | 91018C       | 34         | DL           | NotPass   |
            | 91018C       | 35         | DL           | NotPass   |
            | 91018C       | 36         | DL           | NotPass   |
            | 91018C       | 37         | DL           | NotPass   |
            | 91018C       | 38         | DL           | NotPass   |
            | 91018C       | 41         | DL           | NotPass   |
            | 91018C       | 42         | DL           | NotPass   |
            | 91018C       | 43         | DL           | NotPass   |
            | 91018C       | 44         | DL           | NotPass   |
            | 91018C       | 45         | DL           | NotPass   |
            | 91018C       | 46         | DL           | NotPass   |
            | 91018C       | 47         | DL           | NotPass   |
            | 91018C       | 48         | DL           | NotPass   |
            # 無牙
            | 91018C       |            | DL           | NotPass   |
            #
            | 91018C       | 19         | DL           | NotPass   |
            | 91018C       | 29         | DL           | NotPass   |
            | 91018C       | 39         | DL           | NotPass   |
            | 91018C       | 49         | DL           | NotPass   |
            | 91018C       | 59         | DL           | NotPass   |
            | 91018C       | 69         | DL           | NotPass   |
            | 91018C       | 79         | DL           | NotPass   |
            | 91018C       | 89         | DL           | NotPass   |
            | 91018C       | 99         | DL           | NotPass   |
            # 牙位為區域型態
            | 91018C       | FM         | DL           | Pass      |
            | 91018C       | UR         | DL           | NotPass   |
            | 91018C       | UL         | DL           | NotPass   |
            | 91018C       | UA         | DL           | NotPass   |
            | 91018C       | UB         | DL           | NotPass   |
            | 91018C       | LL         | DL           | NotPass   |
            | 91018C       | LR         | DL           | NotPass   |
            | 91018C       | LA         | DL           | NotPass   |
            | 91018C       | LB         | DL           | NotPass   |
            # 非法牙位
            | 91018C       | 00         | DL           | NotPass   |
            | 91018C       | 01         | DL           | NotPass   |
            | 91018C       | 10         | DL           | NotPass   |
            | 91018C       | 56         | DL           | NotPass   |
            | 91018C       | 66         | DL           | NotPass   |
            | 91018C       | 76         | DL           | NotPass   |
            | 91018C       | 86         | DL           | NotPass   |
            | 91018C       | 91         | DL           | NotPass   |
