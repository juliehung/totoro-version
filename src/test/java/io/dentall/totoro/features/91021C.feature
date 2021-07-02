@nhi-91-series
Feature: 91021C 牙周病統合治療第一階段支付

    Scenario Outline: 全部檢核成功
        Given 建立醫師
        Given Kelly 29 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 確認診療代碼 <IssueNhiCode> ，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            | 91021C       | 11         | MOB          | Pass      |

    Scenario Outline: 小於等於30歲，提醒須檢附影像
        Given 建立醫師
        Given Kelly <Age> 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 提醒"須檢附影像"，確認結果是否為 <PassOrNot>，且檢核訊息應 <ShowOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | Age | PassOrNot | ShowOrNot |
            | 91021C       | 11         | MOB          | 29  | Pass      | 顯示        |
            | 91021C       | 11         | MOB          | 30  | Pass      | 顯示        |
            | 91021C       | 11         | MOB          | 31  | Pass      | 不顯示       |

    Scenario Outline: 在同一診所兩年內有申報91021C項目，提醒須檢附影像
        Given 建立醫師
        Given Kelly 35 歲病人
        Given 新增 <Nums> 筆診療處置:
            | Id | PastDate | A72 | A73            | A74          | A75            | A76 | A77 | A78 | A79 |
            | 1  | 24個月前的月底 | 3   | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
            | 2  | 23個月前的月初 | 3   | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
            | 3  | 23個月前的月中 | 3   | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 提醒"須檢附影像"，確認結果是否為 <PassOrNot>，且檢核訊息應 <ShowOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | Nums | PassOrNot | ShowOrNot |
            | 91021C       | 11         | MOB          | 1    | Pass      | 不顯示       |
            | 91021C       | 11         | MOB          | 2    | Pass      | 不顯示       |
            | 91021C       | 11         | MOB          | 3    | Pass      | 顯示        |

    Scenario Outline: 提醒須至健保VPN登錄
        Given 建立醫師
        Given Kelly 29 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 提醒"執行本方案前，須至健保VPN登錄"，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            | 91021C       | 11         | MOB          | Pass      |

    Scenario Outline: （HIS）365天內，不應有 91021C 診療項目
        Given 建立醫師
        Given Kelly 35 歲病人
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
            | 91021C       | 11         | DL           | 364               | 91021C           | 11             | 365    | NotPass   |
            | 91021C       | 11         | DL           | 365               | 91021C           | 11             | 365    | NotPass   |
            | 91021C       | 11         | DL           | 366               | 91021C           | 11             | 365    | Pass      |

    Scenario Outline: （IC）365天內，不應有 91021C 診療項目
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
        Then （IC）檢查 <IssueNhiCode> 診療項目，在病患過去 <GapDay> 天紀錄中，不應包含特定的 <MedicalNhiCode> 診療代碼，確認結果是否為 <PassOrNot> 且檢查訊息類型為 D4_1
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastMedicalDays | MedicalNhiCode | MedicalTeeth | GapDay | PassOrNot |
            | 91021C       | 11         | DL           | 364             | 91021C         | 11           | 365    | NotPass   |
            | 91021C       | 11         | DL           | 365             | 91021C         | 11           | 365    | NotPass   |
            | 91021C       | 11         | DL           | 366             | 91021C         | 11           | 365    | Pass      |

    Scenario Outline: （HIS）已申報91006C或91007C三次以上者，一年內不得申報牙周病統合性治療方案91021C~91023C
        Given 建立醫師
        Given Stan 24 歲病人
        Given 新增 <Nums> 筆診療處置:
            | Id | PastDays | A72 | A73                | A74              | A75                | A76 | A77 | A78 | A79 |
            | 1  | 100      | 3   | <TreatmentNhiCode> | <TreatmentTeeth> | <TreatmentSurface> | 0   | 1.0 | 03  |     |
            | 2  | 364      | 3   | <TreatmentNhiCode> | <TreatmentTeeth> | <TreatmentSurface> | 0   | 1.0 | 03  |     |
            | 3  | 365      | 3   | <TreatmentNhiCode> | <TreatmentTeeth> | <TreatmentSurface> | 0   | 1.0 | 03  |     |
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 在 365 天內的記錄中，<TreatmentNhiCode> 診療代碼已達 3 次以上，不得申報 <IssueNhiCode>，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | Nums | TreatmentNhiCode | TreatmentTeeth | TreatmentSurface | PassOrNot |
            | 91021C       | 11         | MOB          | 1    | 91006C           | 11             | MOB              | Pass      |
            | 91021C       | 11         | MOB          | 2    | 91006C           | 11             | MOB              | Pass      |
            | 91021C       | 11         | MOB          | 3    | 91006C           | 11             | MOB              | NotPass   |
            | 91021C       | 11         | MOB          | 1    | 91007C           | 11             | MOB              | Pass      |
            | 91021C       | 11         | MOB          | 2    | 91007C           | 11             | MOB              | Pass      |
            | 91021C       | 11         | MOB          | 3    | 91007C           | 11             | MOB              | NotPass   |

    Scenario Outline: （IC）已申報91006C或91007C三次以上者，一年內不得申報牙周病統合性治療方案91021C~91023C
        Given 建立醫師
        Given Stan 24 歲病人
        Given 新增 <Nums> 筆健保醫療:
            | PastDays | NhiCode            | Teeth |
            | 100      | <TreatmentNhiCode> | 11    |
            | 364      | <TreatmentNhiCode> | 11    |
            | 365      | <TreatmentNhiCode> | 11    |
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 在 365 天內的記錄中，<TreatmentNhiCode> 診療代碼已達 3 次以上，不得申報 <IssueNhiCode>，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | Nums | TreatmentNhiCode | PassOrNot |
            | 91021C       | 11         | MOB          | 1    | 91006C           | Pass      |
            | 91021C       | 11         | MOB          | 2    | 91006C           | Pass      |
            | 91021C       | 11         | MOB          | 3    | 91006C           | NotPass   |
            | 91021C       | 11         | MOB          | 1    | 91007C           | Pass      |
            | 91021C       | 11         | MOB          | 2    | 91007C           | Pass      |
            | 91021C       | 11         | MOB          | 3    | 91007C           | NotPass   |
