@nhi-91-series
Feature: 91023C 牙周病統合治療第三階段支付

    Scenario Outline: 全部檢核成功
        Given 建立醫師
        Given Kelly 30 歲病人
        Given 在過去第 180 天，建立預約
        Given 在過去第 180 天，建立掛號
        Given 在過去第 180 天，產生診療計畫
        And 新增診療代碼:
            | PastDays | A72 | A73    | A74 | A75 | A76 | A77 | A78 | A79 |
            | 180      | 3   | 91021C | 11  | MOB | 0   | 1.0 | 03  |     |
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 確認診療代碼 <IssueNhiCode> ，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            | 91023C       | 11         | MOB          | Pass      |

    Scenario Outline: 提醒檢附牙菌斑控制紀錄表、牙周病檢查紀錄表
        Given 建立醫師
        Given Kelly 24 歲病人
        Given 在過去第 180 天，建立預約
        Given 在過去第 180 天，建立掛號
        Given 在過去第 180 天，產生診療計畫
        And 新增診療代碼:
            | PastDays | A72 | A73    | A74 | A75 | A76 | A77 | A78 | A79 |
            | 180      | 3   | 91021C | 11  | MOB | 0   | 1.0 | 03  |     |
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 提醒"檢附牙菌斑控制紀錄表、牙周病檢查紀錄表"，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            | 91023C       | FM         | MOB          | Pass      |

    Scenario Outline: （HIS）180天內，得有91021C治療項目
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
        Then 檢查 180 天內，應有 <TreatmentNhiCode> 診療項目存在，確認結果是否為 <PassOrNot> 且檢查訊息類型為 D8_1
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastTreatmentDays | TreatmentNhiCode | TreatmentTeeth | PassOrNot |
            | 91023C       | 11         | MOB          | 178               | 91021C           | 11             | Pass      |
            | 91023C       | 11         | MOB          | 180               | 91021C           | 11             | Pass      |
            | 91023C       | 11         | MOB          | 181               | 91021C           | 11             | NotPass   |

    Scenario Outline: （IC）180天內，得有91021C治療項目
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
        Then 檢查 180 天內，應有 <MedicalNhiCode> 診療項目存在，確認結果是否為 <PassOrNot> 且檢查訊息類型為 D8_1
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastMedicalDays | MedicalNhiCode | MedicalTeeth | PassOrNot |
            | 91023C       | 11         | MOB          | 179             | 91021C         | 11           | Pass      |
            | 91023C       | 11         | MOB          | 180             | 91021C         | 11           | Pass      |
            | 91023C       | 11         | MOB          | 181             | 91021C         | 11           | NotPass   |

    Scenario Outline: （HIS）已申報91006C或91007C三次以上者，一年內不得申報牙周病統合性治療方案91021C~91023C
        Given 建立醫師
        Given Kelly 30 歲病人
        Given 在過去第 180 天，建立預約
        Given 在過去第 180 天，建立掛號
        Given 在過去第 180 天，產生診療計畫
        And 新增診療代碼:
            | PastDays | A72 | A73    | A74 | A75 | A76 | A77 | A78 | A79 |
            | 180      | 3   | 91021C | 11  | MOB | 0   | 1.0 | 03  |     |
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
            | 91023C       | 11         | MOB          | 1    | 91006C           | 11             | MOB              | Pass      |
            | 91023C       | 11         | MOB          | 2    | 91006C           | 11             | MOB              | Pass      |
            | 91023C       | 11         | MOB          | 3    | 91006C           | 11             | MOB              | NotPass   |
            | 91023C       | 11         | MOB          | 1    | 91007C           | 11             | MOB              | Pass      |
            | 91023C       | 11         | MOB          | 2    | 91007C           | 11             | MOB              | Pass      |
            | 91023C       | 11         | MOB          | 3    | 91007C           | 11             | MOB              | NotPass   |

    Scenario Outline: （IC）已申報91006C或91007C三次以上者，一年內不得申報牙周病統合性治療方案91021C~91023C
        Given 建立醫師
        Given Kelly 30 歲病人
        Given 在過去第 180 天，建立預約
        Given 在過去第 180 天，建立掛號
        Given 在過去第 180 天，產生診療計畫
        And 新增診療代碼:
            | PastDays | A72 | A73    | A74 | A75 | A76 | A77 | A78 | A79 |
            | 180      | 3   | 91021C | 11  | MOB | 0   | 1.0 | 03  |     |
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
            | 91023C       | 11         | MOB          | 1    | 91006C           | Pass      |
            | 91023C       | 11         | MOB          | 2    | 91006C           | Pass      |
            | 91023C       | 11         | MOB          | 3    | 91006C           | NotPass   |
            | 91023C       | 11         | MOB          | 1    | 91007C           | Pass      |
            | 91023C       | 11         | MOB          | 2    | 91007C           | Pass      |
            | 91023C       | 11         | MOB          | 3    | 91007C           | NotPass   |
