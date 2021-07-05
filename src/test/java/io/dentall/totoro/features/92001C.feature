@nhi @nhi-92-series
Feature: 92001C 非特定局部治療

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
            | 92001C       | 11         | DL           | Pass      |

    Scenario Outline: （HIS）3天內，有 92001C 診療項目
        Given 建立醫師
        Given Scott 24 歲病人
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
        Then 檢查 <IssueNhiCode> 診療項目，在病患過去 <GapDay> 天紀錄中，不應包含特定的 <TreatmentNhiCode> 診療代碼，確認結果是否為 <PassOrNot> 且檢查訊息類型為 D7_1
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastTreatmentDays | TreatmentNhiCode | TreatmentTeeth | GapDay | PassOrNot |
            | 92001C       | 11         | DL           | 2                 | 92001C           | 11             | 3      | NotPass   |
            | 92001C       | 11         | DL           | 3                 | 92001C           | 11             | 3      | NotPass   |
            | 92001C       | 11         | DL           | 4                 | 92001C           | 11             | 3      | Pass      |

    Scenario Outline: （IC）3天內，有 92001C 診療項目
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
        Then 檢查 <IssueNhiCode> 診療項目，在病患過去 <GapDay> 天紀錄中，不應包含特定的 <MedicalNhiCode> 診療代碼，確認結果是否為 <PassOrNot> 且檢查訊息類型為 D7_1
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastMedicalDays | MedicalNhiCode | MedicalTeeth | GapDay | PassOrNot |
            | 92001C       | 11         | DL           | 2               | 92001C         | 11           | 3      | NotPass   |
            | 92001C       | 11         | DL           | 3               | 92001C         | 11           | 3      | NotPass   |
            | 92001C       | 11         | DL           | 4               | 92001C         | 11           | 3      | Pass      |

    Scenario Outline: （HIS）30天內只能申報2次 92001C 健保代碼
        Given 建立醫師
        Given Scott 24 歲病人
        Given 新增 <Nums> 筆診療處置:
            | Id | PastDays | A72 | A73            | A74 | A75            | A76 | A77 | A78 | A79 |
            | 1  | 29       | 3   | <IssueNhiCode> | 21  | <IssueSurface> | 0   | 1.0 | 03  |     |
            | 2  | 30       | 3   | <IssueNhiCode> | 31  | <IssueSurface> | 0   | 1.0 | 03  |     |
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 在 30天 的記錄中，<IssueNhiCode> 診療代碼最多只能 2 次，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | Nums | PassOrNot |
            | 92001C       | 11         | DL           | 1    | Pass      |
            | 92001C       | 11         | DL           | 2    | NotPass   |

    Scenario Outline: （IC）30天內只能申報2次 92001C 健保代碼
        Given 建立醫師
        Given Scott 24 歲病人
        Given 新增 <Nums> 筆健保醫療:
            | PastDays | NhiCode        | Teeth |
            | 29       | <IssueNhiCode> | 21    |
            | 30       | <IssueNhiCode> | 31    |
        Given 在 當月底 ，建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 在 30天 的記錄中，<IssueNhiCode> 診療代碼最多只能 2 次，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | Nums | PassOrNot |
            | 92001C       | 11         | DL           | 1    | Pass      |
            | 92001C       | 11         | DL           | 2    | NotPass   |
