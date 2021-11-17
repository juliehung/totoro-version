@nhi @nhi-8-series @part1
Feature: 81 氟化防齲處理(包括牙醫師專業塗氟處理、一般性口腔檢查、衛生教育）

    Scenario Outline: 全部檢核成功
        Given 建立醫師
        Given Kelly 5 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 確認診療代碼 <IssueNhiCode> ，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            | 81           | 51         | FM           | Pass      |

    Scenario Outline: 病患在診療當下年紀需未滿 6 歲
        Given 建立醫師
        Given Kelly <PatientAge> 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 病患是否在診療 <IssueNhiCode> 當下年紀未滿 6 歲，確認結果是否為 <PassOrNot>
        Examples:
            | PatientAge | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            | 5          | 81           | 51         | FM           | Pass      |
            | 6          | 81           | 51         | FM           | NotPass   |
            | 7          | 81           | 51         | FM           | NotPass   |

    Scenario Outline: （HIS）半年內，不應有 81 診療項目
        Given 建立醫師
        Given Kelly 5 歲病人
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
            | 81           | 11         | DL           | 6個月前              | 81               | 11             | DL               | 6個月      | Pass      |
            | 81           | 11         | DL           | 5個月前              | 81               | 11             | DL               | 6個月      | NotPass   |
            | 81           | 11         | DL           | 4個月前              | 81               | 11             | DL               | 6個月      | NotPass   |
            | 81           | 11         | DL           | 3個月前              | 81               | 11             | DL               | 6個月      | NotPass   |
            | 81           | 11         | DL           | 2個月前              | 81               | 11             | DL               | 6個月      | NotPass   |
            | 81           | 11         | DL           | 1個月前              | 81               | 11             | DL               | 6個月      | NotPass   |
            | 81           | 11         | DL           | 當月                | 81               | 11             | DL               | 6個月      | NotPass   |

    Scenario Outline: （IC）半年內，不應有 81 診療項目
        Given 建立醫師
        Given Kelly 5 歲病人
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
            | 81           | 11         | DL           | 6個月前            | 81             | 11           | 6個月      | Pass      |
            | 81           | 11         | DL           | 5個月前            | 81             | 11           | 6個月      | NotPass   |
            | 81           | 11         | DL           | 4個月前            | 81             | 11           | 6個月      | NotPass   |
            | 81           | 11         | DL           | 3個月前            | 81             | 11           | 6個月      | NotPass   |
            | 81           | 11         | DL           | 2個月前            | 81             | 11           | 6個月      | NotPass   |
            | 81           | 11         | DL           | 1個月前            | 81             | 11           | 6個月      | NotPass   |
            | 81           | 11         | DL           | 當月              | 81             | 11           | 6個月      | NotPass   |


