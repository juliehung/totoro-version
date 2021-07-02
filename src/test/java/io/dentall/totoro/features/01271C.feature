Feature: 01271C 環口全景X光初診診察

    Scenario Outline: 全部檢核成功
        Given 建立醫師
        Given Wind 24 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 確認診療代碼 <IssueNhiCode> ，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            | 01271C       | 51         | FM           | Pass      |

    Scenario Outline: （HIS）1095天內沒有任何治療紀錄
        Given 建立醫師
        Given Wind 24 歲病人
        Given 在過去第 <PastTreatmentDays> 天，建立預約
        Given 在過去第 <PastTreatmentDays> 天，建立掛號
        Given 在過去第 <PastTreatmentDays> 天，產生診療計畫
        And 新增診療代碼:
            | PastDays            | A72 | A73    | A74 | A75 | A76 | A77 | A78 | A79 |
            | <PastTreatmentDays> | 3   | 89001C | FM  | DO  | 0   | 1.0 | 03  |     |
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 在過去 <PastTreatmentDays> 天，應沒有任何治療紀錄，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastTreatmentDays | PassOrNot |
            | 01271C       | 51         | DO           | 1094              | NotPass   |
            | 01271C       | 51         | DO           | 1095              | NotPass   |
            | 01271C       | 51         | DO           | 1096              | Pass      |

    Scenario Outline: 提醒須檢附影像
        Given 建立醫師
        Given Wind 24 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 提醒"須檢附影像"，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            | 01271C       | 14         | MOB          | Pass      |

    Scenario Outline: 檢查同一處置單，是否沒有健保定義的其他衝突診療
        Given 建立醫師
        Given Wind 24 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        And 新增診療代碼:
            | A72 | A73    | A74 | A75 | A76 | A77 | A78 | A79 |
            | 3   | 01271C | FM  | DO  | 0   | 1.0 | 03  |     |
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            | 01271C  | FM    | DO      |                |              |                |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 檢查同一處置單，是否沒有健保定義的 <IssueNhiCode> 重複診療衝突，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            | 01271C       | 14         | MOB          | NotPass   |

    Scenario Outline: （HIS）365天內，不應有 01272C/01273C/00315C/00316C/00317C 診療項目
        Given 建立醫師
        Given Scott 11 歲病人
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
            | 01271C       | 11         | MOB          | 364               | 01272C           | 11             | MOB              | 365    | NotPass   |
            | 01271C       | 11         | MOB          | 365               | 01272C           | 11             | MOB              | 365    | NotPass   |
            | 01271C       | 11         | MOB          | 366               | 01272C           | 11             | MOB              | 365    | Pass      |
            | 01271C       | 11         | MOB          | 364               | 01273C           | 11             | MOB              | 365    | NotPass   |
            | 01271C       | 11         | MOB          | 365               | 01273C           | 11             | MOB              | 365    | NotPass   |
            | 01271C       | 11         | MOB          | 366               | 01273C           | 11             | MOB              | 365    | Pass      |
            | 01271C       | 11         | MOB          | 364               | 00315C           | 11             | MOB              | 365    | NotPass   |
            | 01271C       | 11         | MOB          | 365               | 00315C           | 11             | MOB              | 365    | NotPass   |
            | 01271C       | 11         | MOB          | 366               | 00315C           | 11             | MOB              | 365    | Pass      |
            | 01271C       | 11         | MOB          | 364               | 00316C           | 11             | MOB              | 365    | NotPass   |
            | 01271C       | 11         | MOB          | 365               | 00316C           | 11             | MOB              | 365    | NotPass   |
            | 01271C       | 11         | MOB          | 366               | 00316C           | 11             | MOB              | 365    | Pass      |
            | 01271C       | 11         | MOB          | 364               | 00317C           | 11             | MOB              | 365    | NotPass   |
            | 01271C       | 11         | MOB          | 365               | 00317C           | 11             | MOB              | 365    | NotPass   |
            | 01271C       | 11         | MOB          | 366               | 00317C           | 11             | MOB              | 365    | Pass      |

    Scenario Outline: （IC）365天內，不應有 01272C/01273C/00315C/00316C/00317C 診療項目
        Given 建立醫師
        Given Kelly 11 歲病人
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
            | 01271C       | 11         | MOB          | 364             | 01272C         | 11           | 365    | NotPass   |
            | 01271C       | 11         | MOB          | 365             | 01272C         | 11           | 365    | NotPass   |
            | 01271C       | 11         | MOB          | 366             | 01272C         | 11           | 365    | Pass      |
            | 01271C       | 11         | MOB          | 364             | 01273C         | 11           | 365    | NotPass   |
            | 01271C       | 11         | MOB          | 365             | 01273C         | 11           | 365    | NotPass   |
            | 01271C       | 11         | MOB          | 366             | 01273C         | 11           | 365    | Pass      |
            | 01271C       | 11         | MOB          | 364             | 00315C         | 11           | 365    | NotPass   |
            | 01271C       | 11         | MOB          | 365             | 00315C         | 11           | 365    | NotPass   |
            | 01271C       | 11         | MOB          | 366             | 00315C         | 11           | 365    | Pass      |
            | 01271C       | 11         | MOB          | 364             | 00316C         | 11           | 365    | NotPass   |
            | 01271C       | 11         | MOB          | 365             | 00316C         | 11           | 365    | NotPass   |
            | 01271C       | 11         | MOB          | 366             | 00316C         | 11           | 365    | Pass      |
            | 01271C       | 11         | MOB          | 364             | 00317C         | 11           | 365    | NotPass   |
            | 01271C       | 11         | MOB          | 365             | 00317C         | 11           | 365    | NotPass   |
            | 01271C       | 11         | MOB          | 366             | 00317C         | 11           | 365    | Pass      |
