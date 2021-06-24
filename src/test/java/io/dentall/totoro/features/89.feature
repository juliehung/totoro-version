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
            | 89           | FM         | MOB          | 89                | 81               | FM             | MOB              | 90     | NotPass   |
            | 89           | FM         | MOB          | 90                | 81               | FM             | MOB              | 90     | NotPass   |
            | 89           | FM         | MOB          | 91                | 81               | FM             | MOB              | 90     | Pass      |
            | 89           | FM         | MOB          | 89                | 87               | FM             | MOB              | 90     | NotPass   |
            | 89           | FM         | MOB          | 90                | 87               | FM             | MOB              | 90     | NotPass   |
            | 89           | FM         | MOB          | 91                | 87               | FM             | MOB              | 90     | Pass      |
            | 89           | FM         | MOB          | 89                | 88               | FM             | MOB              | 90     | NotPass   |
            | 89           | FM         | MOB          | 90                | 88               | FM             | MOB              | 90     | NotPass   |
            | 89           | FM         | MOB          | 91                | 88               | FM             | MOB              | 90     | Pass      |
            | 89           | FM         | MOB          | 89                | 89               | FM             | MOB              | 90     | NotPass   |
            | 89           | FM         | MOB          | 90                | 89               | FM             | MOB              | 90     | NotPass   |
            | 89           | FM         | MOB          | 91                | 89               | FM             | MOB              | 90     | Pass      |

    Scenario Outline: （IC）3個月內，不應有 81/87~89 診療項目
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
            | 89           | FM         | MOB          | 89              | 81             | FM           | 90     | NotPass   |
            | 89           | FM         | MOB          | 90              | 81             | FM           | 90     | NotPass   |
            | 89           | FM         | MOB          | 91              | 81             | FM           | 90     | Pass      |
            | 89           | FM         | MOB          | 89              | 87             | FM           | 90     | NotPass   |
            | 89           | FM         | MOB          | 90              | 87             | FM           | 90     | NotPass   |
            | 89           | FM         | MOB          | 91              | 87             | FM           | 90     | Pass      |
            | 89           | FM         | MOB          | 89              | 88             | FM           | 90     | NotPass   |
            | 89           | FM         | MOB          | 90              | 88             | FM           | 90     | NotPass   |
            | 89           | FM         | MOB          | 91              | 88             | FM           | 90     | Pass      |
            | 89           | FM         | MOB          | 89              | 89             | FM           | 90     | NotPass   |
            | 89           | FM         | MOB          | 90              | 89             | FM           | 90     | NotPass   |
            | 89           | FM         | MOB          | 91              | 89             | FM           | 90     | Pass      |
