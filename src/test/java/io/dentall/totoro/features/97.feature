Feature: 97 口腔黏膜檢查

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
            | 97           | 11         | MOB          | Pass      |

    Scenario Outline: （HIS）545天內，不應有 97 診療項目
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
        Then 檢查 <IssueNhiCode> 診療項目，在病患過去 <GapDay> 天紀錄中，不應包含特定的 <TreatmentNhiCode> 診療代碼，確認結果是否為 <PassOrNot> 且檢查訊息類型為 D4_1
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastTreatmentDays | TreatmentNhiCode | TreatmentTeeth | TreatmentSurface | GapDay | PassOrNot |
            | 97           | 11         | MOB          | 544               | 97               | 11             | MOB              | 545    | NotPass   |
            | 97           | 11         | MOB          | 545               | 97               | 11             | MOB              | 545    | NotPass   |
            | 97           | 11         | MOB          | 546               | 97               | 11             | MOB              | 545    | Pass      |

    Scenario Outline: （IC）545天內，不應有 97 診療項目
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
        Then 檢查 <IssueNhiCode> 診療項目，在病患過去 <GapDay> 天紀錄中，不應包含特定的 <MedicalNhiCode> 診療代碼，確認結果是否為 <PassOrNot> 且檢查訊息類型為 D4_1
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastMedicalDays | MedicalNhiCode | MedicalTeeth | GapDay | PassOrNot |
            | 97           | 11         | MOB          | 544             | 97             | 11           | 545    | NotPass   |
            | 97           | 11         | MOB          | 545             | 97             | 11           | 545    | NotPass   |
            | 97           | 11         | MOB          | 546             | 97             | 11           | 545    | Pass      |

    Scenario Outline: 病患在診療當下年紀需未滿 30 歲及大於 17 歲
        Given 建立醫師
        Given Scott <PatientAge> 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 病患是否在診療 <IssueNhiCode> 當下年紀未滿 30 歲及大於 17 歲，確認結果是否為 <PassOrNot>
        Examples:
            | PatientAge | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            | 17         | 97           | 11         | MOB          | NotPass   |
            | 18         | 97           | 11         | MOB          | Pass      |
            | 19         | 97           | 11         | MOB          | Pass      |
            | 20         | 97           | 11         | MOB          | Pass      |
            | 21         | 97           | 11         | MOB          | Pass      |
            | 22         | 97           | 11         | MOB          | Pass      |
            | 23         | 97           | 11         | MOB          | Pass      |
            | 24         | 97           | 11         | MOB          | Pass      |
            | 25         | 97           | 11         | MOB          | Pass      |
            | 26         | 97           | 11         | MOB          | Pass      |
            | 27         | 97           | 11         | MOB          | Pass      |
            | 28         | 97           | 11         | MOB          | Pass      |
            | 29         | 97           | 11         | MOB          | Pass      |
            | 30         | 97           | 11         | MOB          | NotPass   |
            | 31         | 97           | 11         | MOB          | NotPass   |
