@nhi @nhi-8-series @part1
Feature: 87 氟化防齲處理(包括牙醫師專業塗氟處理、一般性口腔檢查、衛生教育)

    Scenario Outline: 全部檢核成功
        Given 建立醫師
        Given Kelly 11 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 確認診療代碼 <IssueNhiCode> ，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            | 87           | 51         | FM           | Pass      |

    Scenario Outline: 病患在診療當下年紀需未滿 12 歲
        Given 建立醫師
        Given Kelly <PatientAge> 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 病患是否在診療 <IssueNhiCode> 當下年紀未滿 12 歲，確認結果是否為 <PassOrNot>
        Examples:
            | PatientAge | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            | 11         | 87           | 51         | FM           | Pass      |
            | 12         | 87           | 51         | FM           | NotPass   |
            | 13         | 87           | 51         | FM           | NotPass   |

    Scenario Outline: （HIS）3個月內，不應有 87 診療項目
        Given 建立醫師
        Given Kelly 11 歲病人
        Given 在 <PastTreatmentDate> ，建立預約
        Given 在 <PastTreatmentDate> ，建立掛號
        Given 在 <PastTreatmentDate> ，產生診療計畫
        And 新增診療代碼:
            | PastDate            | A72 | A73                | A74              | A75                | A76 | A77 | A78 | A79 |
            | <PastTreatmentDate> | 3   | <TreatmentNhiCode> | <TreatmentTeeth> | <TreatmentSurface> | 0   | 1.0 | 03  |     |
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 檢查 <IssueNhiCode> 診療項目，在病患過去 <GapMonth> 紀錄中，不應包含特定的 <TreatmentNhiCode> 診療代碼，確認結果是否為 <PassOrNot> 且檢查訊息類型為 D4_1
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastTreatmentDate | TreatmentNhiCode | TreatmentTeeth | TreatmentSurface | GapMonth | PassOrNot |
            | 87           | 11         | DL           | 3個月前              | 87               | 11             | DL               | 3個月      | Pass      |
            | 87           | 11         | DL           | 2個月前              | 87               | 11             | DL               | 3個月      | NotPass   |
            | 87           | 11         | DL           | 1個月前              | 87               | 11             | DL               | 3個月      | NotPass   |
            | 87           | 11         | DL           | 當月                | 87               | 11             | DL               | 3個月      | NotPass   |

    Scenario Outline: （IC）3個月內，不應有 87 診療項目
        Given 建立醫師
        Given Kelly 11 歲病人
        Given 新增健保醫療:
            | PastDate          | NhiCode          | Teeth          |
            | <PastMedicalDate> | <MedicalNhiCode> | <MedicalTeeth> |
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 檢查 <IssueNhiCode> 診療項目，在病患過去 <GapMonth> 紀錄中，不應包含特定的 <MedicalNhiCode> 診療代碼，確認結果是否為 <PassOrNot> 且檢查訊息類型為 D4_1
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastMedicalDate | MedicalNhiCode | MedicalTeeth | GapMonth | PassOrNot |
            | 87           | 11         | DL           | 3個月前            | 87             | 11           | 3個月      | Pass      |
            | 87           | 11         | DL           | 2個月前            | 87             | 11           | 3個月      | NotPass   |
            | 87           | 11         | DL           | 1個月前            | 87             | 11           | 3個月      | NotPass   |
            | 87           | 11         | DL           | 當月              | 87             | 11           | 3個月      | NotPass   |

    Scenario Outline: （HIS）90天內，不應有 P6702C、P6703C、P6704C、P6705C 診療項目
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
        Then 檢查 <IssueNhiCode> 診療項目，在病患過去 <GapDay> 天紀錄中，不應包含特定的 <TreatmentNhiCode> 診療代碼，確認結果是否為 <PassOrNot> 且檢查訊息類型為 D1_2
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastTreatmentDays | TreatmentNhiCode | TreatmentTeeth | GapDay | PassOrNot |
            | 87       | FM         | DL           | 89                | P6702C           | FM             | 90     | NotPass   |
            | 87       | FM         | DL           | 89                | P6703C           | FM             | 90     | NotPass   |
            | 87       | FM         | DL           | 89                | P6704C           | FM             | 90     | NotPass   |
            | 87       | FM         | DL           | 89                | P6705C           | FM             | 90     | NotPass   |
            | 87       | FM         | DL           | 90                | P6702C           | FM             | 90     | NotPass   |
            | 87       | FM         | DL           | 90                | P6703C           | FM             | 90     | NotPass   |
            | 87       | FM         | DL           | 90                | P6704C           | FM             | 90     | NotPass   |
            | 87       | FM         | DL           | 90                | P6705C           | FM             | 90     | NotPass   |
            | 87       | FM         | DL           | 91                | P6702C           | FM             | 90     | Pass      |
            | 87       | FM         | DL           | 91                | P6703C           | FM             | 90     | Pass      |
            | 87       | FM         | DL           | 91                | P6704C           | FM             | 90     | Pass      |
            | 87       | FM         | DL           | 91                | P6705C           | FM             | 90     | Pass      |

    Scenario Outline: （IC）90天內，不應有 P6702C、P6703C、P6704C、P6705C 診療項目
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
        Then 檢查 <IssueNhiCode> 診療項目，在病患過去 <GapDay> 天紀錄中，不應包含特定的 <MedicalNhiCode> 診療代碼，確認結果是否為 <PassOrNot> 且檢查訊息類型為 D1_2
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastMedicalDays | MedicalNhiCode | MedicalTeeth | GapDay | PassOrNot |
            | 87       | FM         | DL           | 89              | P6702C         | FM           | 90     | NotPass   |
            | 87       | FM         | DL           | 89              | P6703C         | FM           | 90     | NotPass   |
            | 87       | FM         | DL           | 89              | P6704C         | FM           | 90     | NotPass   |
            | 87       | FM         | DL           | 89              | P6705C         | FM           | 90     | NotPass   |
            | 87       | FM         | DL           | 90              | P6702C         | FM           | 90     | NotPass   |
            | 87       | FM         | DL           | 90              | P6703C         | FM           | 90     | NotPass   |
            | 87       | FM         | DL           | 90              | P6704C         | FM           | 90     | NotPass   |
            | 87       | FM         | DL           | 90              | P6705C         | FM           | 90     | NotPass   |
            | 87       | FM         | DL           | 91              | P6702C         | FM           | 90     | Pass      |
            | 87       | FM         | DL           | 91              | P6703C         | FM           | 90     | Pass      |
            | 87       | FM         | DL           | 91              | P6704C         | FM           | 90     | Pass      |
            | 87       | FM         | DL           | 91              | P6705C         | FM           | 90     | Pass      |
