@nhi @nhi-P6-series
Feature: P6703C 嚴重齲齒兒童口腔健康照護複診治療-第一次

    Scenario Outline: 全部檢核成功
        Given 建立醫師
        Given Scott 24 歲病人
        Given 在過去第 91 天，建立預約
        Given 在過去第 91 天，建立掛號
        Given 在過去第 91 天，產生診療計畫
        And 新增診療代碼:
            | PastDays            | A72 | A73                | A74              | A75                | A76 | A77 | A78 | A79 |
            | 91                  | 3   |  P6702C | 11 | MOB | 0   | 1.0 | 03  |     |
            | 0                   | 3   |  91014C | 11 | MOB | 0   | 1.0 | 03  |     |
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 確認診療代碼 <IssueNhiCode> ，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            | P6703C       | 11         | MOB          | Pass      |

    Scenario Outline: （HIS）90天內，不應有 P6702C 診療項目
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
            | P6703C       | FM         | DL           | 89                | P6702C           | FM             | 90     | NotPass   |
            | P6703C       | FM         | DL           | 90                | P6702C           | FM             | 90     | NotPass   |
            | P6703C       | FM         | DL           | 91                | P6702C           | FM             | 90     | Pass      |

    Scenario Outline: （IC）90天內，不應有 P6702C 診療項目
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
            | P6703C       | FM         | DL           | 89                | P6702C           | FM             | 90     | NotPass   |
            | P6703C       | FM         | DL           | 90                | P6702C           | FM             | 90     | NotPass   |
            | P6703C       | FM         | DL           | 91                | P6702C           | FM             | 90     | Pass      |
            | P6703C       | FM         | DL           | 89                | P6702C           | FM             | 90     | NotPass   |
            | P6703C       | FM         | DL           | 90                | P6702C           | FM             | 90     | NotPass   |
            | P6703C       | FM         | DL           | 91                | P6702C           | FM             | 90     | Pass      |

    Scenario Outline: （HIS）180 天內，曾經有 P6702C 治療項目
        Given 建立醫師
        Given Wind 24 歲病人
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
        Then 檢查 180 天內，應有 <TreatmentNhiCode> 診療項目存在，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastTreatmentDays | TreatmentNhiCode | TreatmentTeeth | PassOrNot |
            | P6703C       | 11         | MOB          | 179                | P6702C           | 11             | Pass      |
            | P6703C       | 11         | MOB          | 180                | P6702C           | 11             | Pass      |
            | P6703C       | 11         | MOB          | 181                | P6702C           | 11             | NotPass   |

    Scenario Outline: （IC）180 天內，曾經有 P6702C 治療項目
        Given 建立醫師
        Given Wind 24 歲病人
        Given 新增健保醫療:
            | PastDays          | NhiCode          | Teeth          |
            | <PastMedicalDays> | <MedicalNhiCode> | <MedicalTeeth> |
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 檢查 180 天內，應有 <MedicalNhiCode> 診療項目存在，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastMedicalDays | MedicalNhiCode | MedicalTeeth | PassOrNot |
            | P6703C       | 11         | MOB          | 179                | P6702C           | 11             | Pass      |
            | P6703C       | 11         | MOB          | 180                | P6702C           | 11             | Pass      |
            | P6703C       | 11         | MOB          | 181                | P6702C           | 11             | NotPass   |

    Scenario Outline: （Disposal）需與91014C或91114C或91020C 併同申報
        Given 建立醫師
        Given Wind 24 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode         | NewTeeth     | NewSurface     |
            |         |       |         | <TreatmentNhiCode> | <IssueTeeth> | <IssueSurface> |
            |         |       |         | <IssueNhiCode>     | <IssueTeeth> | <IssueSurface> |
        Then 檢查同一處置單，必須包含 91014C/91114C/91020C 其中之一的診療項目，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | TreatmentNhiCode | PassOrNot |
            | P6703C       | 14         | MOB          | 01271C           | NotPass   |
            | P6703C       | 14         | MOB          | 91014C           | Pass      |
            | P6703C       | 14         | MOB          | 91114C           | Pass      |
            | P6703C       | 14         | MOB          | 91020C           | Pass      |

    Scenario Outline: （HIS-Today）需與91014C或91114C或91020C 併同申報
        Given 建立醫師
        Given Wind 24 歲病人
        Given 在 當日 ，建立預約
        Given 在 當日 ，建立掛號
        Given 在 當日 ，產生診療計畫
        And 新增診療代碼:
            | PastDate | A72 | A73                | A74          | A75            | A76 | A77 | A78 | A79 |
            | 當日       | 3   | <TreatmentNhiCode> | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 檢查同一處置單，必須包含 91014C/91114C/91020C 其中之一的診療項目，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | TreatmentNhiCode | PassOrNot |
            | P6703C       | 14         | MOB          | 01271C           | NotPass   |
            | P6703C       | 14         | MOB          | 91014C           | Pass      |
            | P6703C       | 14         | MOB          | 91114C           | Pass      |
            | P6703C       | 14         | MOB          | 91020C           | Pass      |
