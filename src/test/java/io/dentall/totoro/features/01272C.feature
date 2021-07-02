Feature: 01272C 年度初診X光檢查

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
            | 01272C       | 11         | MOB          | Pass      |

    Scenario Outline: （HIS）365天內沒有任何治療紀錄及545天內沒有01272C治療記錄
        Given 建立醫師
        Given Scott 24 歲病人
        Given 在過去第 <Past01272CTreatmentDays> 天，建立預約
        Given 在過去第 <Past01272CTreatmentDays> 天，建立掛號
        Given 在過去第 <Past01272CTreatmentDays> 天，產生診療計畫
        And 新增診療代碼:
            | PastDays                  | A72 | A73            | A74 | A75 | A76 | A77 | A78 | A79 |
            | <Past01272CTreatmentDays> | 3   | <IssueNhiCode> | 11  | DO  | 0   | 1.0 | 03  |     |
        Given 在過去第 <PastAnyTreatmentDays> 天，建立預約
        Given 在過去第 <PastAnyTreatmentDays> 天，建立掛號
        Given 在過去第 <PastAnyTreatmentDays> 天，產生診療計畫
        And 新增診療代碼:
            | PastDays               | A72 | A73    | A74 | A75 | A76 | A77 | A78 | A79 |
            | <PastAnyTreatmentDays> | 3   | 89001C | 11  | DO  | 0   | 1.0 | 03  |     |
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then （HIS）在過去 <PastAnyTreatmentDays> 天，應沒有任何治療紀錄，確認結果是否為 <PassOrNot>
        And （HIS）檢查 <IssueNhiCode> 診療項目，在病患過去 <Past01272CGapDay> 天紀錄中，不應包含特定的 <IssueNhiCode> 診療代碼，確認結果是否為 <PassOrNot> 且檢查訊息類型為 D4_1
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastAnyTreatmentDays | Past01272CTreatmentDays | Past01272CGapDay | PassOrNot |
            | 01272C       | 11         | DO           | 364                  | 544                     | 545              | NotPass   |
            | 01272C       | 11         | DO           | 365                  | 544                     | 545              | NotPass   |
            | 01272C       | 11         | DO           | 366                  | 544                     | 545              | Pass      |
            | 01272C       | 11         | DO           | 364                  | 545                     | 545              | NotPass   |
            | 01272C       | 11         | DO           | 365                  | 545                     | 545              | NotPass   |
            | 01272C       | 11         | DO           | 366                  | 545                     | 545              | Pass      |
            | 01272C       | 11         | DO           | 364                  | 546                     | 545              | Pass      |
            | 01272C       | 11         | DO           | 365                  | 546                     | 545              | Pass      |
            | 01272C       | 11         | DO           | 366                  | 546                     | 545              | Pass      |

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
            | 01272C       | 11         | MOB          | Pass      |

    Scenario Outline: （HIS）365天內，不應有 01271C/01273C/00315C/00316C/00317C 診療項目
        Given 建立醫師
        Given Scott 24 歲病人
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
        Then （HIS）檢查 <IssueNhiCode> 診療項目，在病患過去 <GapDay> 天紀錄中，不應包含特定的 <TreatmentNhiCode> 診療代碼，確認結果是否為 <PassOrNot> 且檢查訊息類型為 D1_2
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastTreatmentDays | TreatmentNhiCode | TreatmentTeeth | TreatmentSurface | GapDay | PassOrNot |
            | 01272C       | 11         | MOB          | 364               | 01271C           | 11             | MOB              | 365    | NotPass   |
            | 01272C       | 11         | MOB          | 365               | 01271C           | 11             | MOB              | 365    | NotPass   |
            | 01272C       | 11         | MOB          | 366               | 01271C           | 11             | MOB              | 365    | Pass      |
            | 01272C       | 11         | MOB          | 364               | 01273C           | 11             | MOB              | 365    | NotPass   |
            | 01272C       | 11         | MOB          | 365               | 01273C           | 11             | MOB              | 365    | NotPass   |
            | 01272C       | 11         | MOB          | 366               | 01273C           | 11             | MOB              | 365    | Pass      |
            | 01272C       | 11         | MOB          | 364               | 00315C           | 11             | MOB              | 365    | NotPass   |
            | 01272C       | 11         | MOB          | 365               | 00315C           | 11             | MOB              | 365    | NotPass   |
            | 01272C       | 11         | MOB          | 366               | 00315C           | 11             | MOB              | 365    | Pass      |
            | 01272C       | 11         | MOB          | 364               | 00316C           | 11             | MOB              | 365    | NotPass   |
            | 01272C       | 11         | MOB          | 365               | 00316C           | 11             | MOB              | 365    | NotPass   |
            | 01272C       | 11         | MOB          | 366               | 00316C           | 11             | MOB              | 365    | Pass      |
            | 01272C       | 11         | MOB          | 364               | 00317C           | 11             | MOB              | 365    | NotPass   |
            | 01272C       | 11         | MOB          | 365               | 00317C           | 11             | MOB              | 365    | NotPass   |
            | 01272C       | 11         | MOB          | 366               | 00317C           | 11             | MOB              | 365    | Pass      |

    Scenario Outline: （IC）365天內，不應有 01271C/01273C/00315C/00316C/00317C 診療項目
        Given 建立醫師
        Given Scott 24 歲病人
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
            | 01272C       | 11         | MOB          | 364             | 01271C         | 11           | 365    | NotPass   |
            | 01272C       | 11         | MOB          | 365             | 01271C         | 11           | 365    | NotPass   |
            | 01272C       | 11         | MOB          | 366             | 01271C         | 11           | 365    | Pass      |
            | 01272C       | 11         | MOB          | 364             | 01273C         | 11           | 365    | NotPass   |
            | 01272C       | 11         | MOB          | 365             | 01273C         | 11           | 365    | NotPass   |
            | 01272C       | 11         | MOB          | 366             | 01273C         | 11           | 365    | Pass      |
            | 01272C       | 11         | MOB          | 364             | 00315C         | 11           | 365    | NotPass   |
            | 01272C       | 11         | MOB          | 365             | 00315C         | 11           | 365    | NotPass   |
            | 01272C       | 11         | MOB          | 366             | 00315C         | 11           | 365    | Pass      |
            | 01272C       | 11         | MOB          | 364             | 00316C         | 11           | 365    | NotPass   |
            | 01272C       | 11         | MOB          | 365             | 00316C         | 11           | 365    | NotPass   |
            | 01272C       | 11         | MOB          | 366             | 00316C         | 11           | 365    | Pass      |
            | 01272C       | 11         | MOB          | 364             | 00317C         | 11           | 365    | NotPass   |
            | 01272C       | 11         | MOB          | 365             | 00317C         | 11           | 365    | NotPass   |
            | 01272C       | 11         | MOB          | 366             | 00317C         | 11           | 365    | Pass      |
