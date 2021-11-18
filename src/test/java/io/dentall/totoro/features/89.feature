@nhi @nhi-8-series @part1
Feature: 89 社區巡迴服務氟化防齲處理(服務項目詳附註)

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
            | 89           | 51         | FM           | Pass      |

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
            | 11         | 89           | 51         | FM           | Pass      |
            | 12         | 89           | 51         | FM           | NotPass   |
            | 13         | 89           | 51         | FM           | NotPass   |

    Scenario Outline: （HIS）3個月內，不應有 81/87~89 診療項目
        Given 建立醫師
        Given Kelly 11 歲病人
        Given 在 <PastTreatmentDate> ，建立預約
        Given 在 <PastTreatmentDate> ，建立掛號
        Given 在 <PastTreatmentDate> ，產生診療計畫
        And 新增診療代碼:
            | PastDate            | A72 | A73                | A74              | A75                | A76 | A77 | A78 | A79 |
            | <PastTreatmentDate> | 3   | <TreatmentNhiCode> | <TreatmentTeeth> | <TreatmentSurface> | 0   | 1.0 | 03  |     |
        Given 在 月底 ，建立預約
        Given 在 月底 ，建立掛號
        Given 在 月底 ，產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 檢查 <IssueNhiCode> 診療項目，在病患過去 <GapMonth> 紀錄中，不應包含特定的 <TreatmentNhiCode> 診療代碼，確認結果是否為 <PassOrNot> 且檢查訊息類型為 D1_2_2
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastTreatmentDate | TreatmentNhiCode | TreatmentTeeth | TreatmentSurface | GapMonth | PassOrNot |
            | 89           | FM         | MOB          | 當月                | 81               | FM             | MOB              | 3個月      | NotPass   |
            | 89           | FM         | MOB          | 1個月前              | 81               | FM             | MOB              | 3個月      | NotPass   |
            | 89           | FM         | MOB          | 2個月前              | 81               | FM             | MOB              | 3個月      | NotPass   |
            | 89           | FM         | MOB          | 3個月前              | 81               | FM             | MOB              | 3個月      | Pass      |
            | 89           | FM         | MOB          | 當月                | 87               | FM             | MOB              | 3個月      | NotPass   |
            | 89           | FM         | MOB          | 1個月前              | 87               | FM             | MOB              | 3個月      | NotPass   |
            | 89           | FM         | MOB          | 2個月前              | 87               | FM             | MOB              | 3個月      | NotPass   |
            | 89           | FM         | MOB          | 3個月前              | 87               | FM             | MOB              | 3個月      | Pass      |
            | 89           | FM         | MOB          | 當月                | 88               | FM             | MOB              | 3個月      | NotPass   |
            | 89           | FM         | MOB          | 1個月前              | 88               | FM             | MOB              | 3個月      | NotPass   |
            | 89           | FM         | MOB          | 2個月前              | 88               | FM             | MOB              | 3個月      | NotPass   |
            | 89           | FM         | MOB          | 3個月前              | 88               | FM             | MOB              | 3個月      | Pass      |
            | 89           | FM         | MOB          | 當月                | 89               | FM             | MOB              | 3個月      | NotPass   |
            | 89           | FM         | MOB          | 1個月前              | 89               | FM             | MOB              | 3個月      | NotPass   |
            | 89           | FM         | MOB          | 2個月前              | 89               | FM             | MOB              | 3個月      | NotPass   |
            | 89           | FM         | MOB          | 3個月前              | 89               | FM             | MOB              | 3個月      | Pass      |

    Scenario Outline: （IC）3個月內，不應有 81/87~89 診療項目
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
        Then 檢查 <IssueNhiCode> 診療項目，在病患過去 <GapMonth> 紀錄中，不應包含特定的 <MedicalNhiCode> 診療代碼，確認結果是否為 <PassOrNot> 且檢查訊息類型為 D1_2_2
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastMedicalDate | MedicalNhiCode | MedicalTeeth | GapMonth | PassOrNot |
            | 89           | FM         | MOB          | 當月              | 81             | FM           | 3個月      | NotPass   |
            | 89           | FM         | MOB          | 1個月前            | 81             | FM           | 3個月      | NotPass   |
            | 89           | FM         | MOB          | 2個月前            | 81             | FM           | 3個月      | NotPass   |
            | 89           | FM         | MOB          | 3個月前            | 81             | FM           | 3個月      | Pass      |
            | 89           | FM         | MOB          | 當月              | 87             | FM           | 3個月      | NotPass   |
            | 89           | FM         | MOB          | 1個月前            | 87             | FM           | 3個月      | NotPass   |
            | 89           | FM         | MOB          | 2個月前            | 87             | FM           | 3個月      | NotPass   |
            | 89           | FM         | MOB          | 3個月前            | 87             | FM           | 3個月      | Pass      |
            | 89           | FM         | MOB          | 當月              | 88             | FM           | 3個月      | NotPass   |
            | 89           | FM         | MOB          | 1個月前            | 88             | FM           | 3個月      | NotPass   |
            | 89           | FM         | MOB          | 2個月前            | 88             | FM           | 3個月      | NotPass   |
            | 89           | FM         | MOB          | 3個月前            | 88             | FM           | 3個月      | Pass      |
            | 89           | FM         | MOB          | 當月              | 89             | FM           | 3個月      | NotPass   |
            | 89           | FM         | MOB          | 1個月前            | 89             | FM           | 3個月      | NotPass   |
            | 89           | FM         | MOB          | 2個月前            | 89             | FM           | 3個月      | NotPass   |
            | 89           | FM         | MOB          | 3個月前            | 89             | FM           | 3個月      | Pass      |
