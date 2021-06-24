Feature: 90098C 難症特別處理-符合附表3.3.1標準之多根管根管治療。(五根及五根以上根管)

    Scenario Outline: 全部檢核成功
        Given 建立醫師
        Given Wind 24 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | 90001C         | <IssueTeeth> | <IssueSurface> |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 確認診療代碼 <IssueNhiCode> ，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            | 90098C       | 11         | MOB          | Pass      |

    Scenario Outline: （Disposal）同日得同時有 90001C~90003C/90019C/90020C
        Given 建立醫師
        Given Wind 24 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode         | NewTeeth         | NewSurface         |
            |         |       |         | <TreatmentNhiCode> | <TreatmentTeeth> | <TreatmentSurface> |
            |         |       |         | <IssueNhiCode>     | <IssueTeeth>     | <IssueSurface>     |
        Then 同日得有 91004C/91005C/91020C 診療項目，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | TreatmentNhiCode | TreatmentTeeth | TreatmentSurface | PassOrNot |
            | 90098C       | 11         | MOB          | 90001C           | 11             | MOB              | Pass      |
            | 90098C       | 11         | MOB          | 90002C           | 11             | MOB              | Pass      |
            | 90098C       | 11         | MOB          | 90003C           | 11             | MOB              | Pass      |
            | 90098C       | 11         | MOB          | 90019C           | 11             | MOB              | Pass      |
            | 90098C       | 11         | MOB          | 90020C           | 11             | MOB              | Pass      |

    Scenario Outline: （HIS-Today）同日得同時有 90001C~90003C/90019C/90020C
        Given 建立醫師
        Given Wind 24 歲病人
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
        Then 同日得有 90001C/90002C/90003C/90019C/90020C 診療項目，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastTreatmentDate | TreatmentNhiCode | TreatmentTeeth | TreatmentSurface | PassOrNot |
            | 90098C       | 11         | MOB          | 當日                | 90001C           | 11             | MOB              | Pass      |
            | 90098C       | 11         | MOB          | 當日                | 90002C           | 11             | MOB              | Pass      |
            | 90098C       | 11         | MOB          | 當日                | 90003C           | 11             | MOB              | Pass      |
            | 90098C       | 11         | MOB          | 當日                | 90019C           | 11             | MOB              | Pass      |
            | 90098C       | 11         | MOB          | 當日                | 90020C           | 11             | MOB              | Pass      |
            | 90098C       | 11         | MOB          | 昨日                | 90001C           | 11             | MOB              | NotPass   |
            | 90098C       | 11         | MOB          | 昨日                | 90002C           | 11             | MOB              | NotPass   |
            | 90098C       | 11         | MOB          | 昨日                | 90003C           | 11             | MOB              | NotPass   |
            | 90098C       | 11         | MOB          | 昨日                | 90019C           | 11             | MOB              | NotPass   |
            | 90098C       | 11         | MOB          | 昨日                | 90020C           | 11             | MOB              | NotPass   |

    Scenario Outline: （IC）同日得同時有 90001C~90003C/90019C/90020C
        Given 建立醫師
        Given Wind 24 歲病人
        Given 新增健保醫療:
            | PastDate          | NhiCode          | Teeth          |
            | <PastMedicalDate> | <MedicalNhiCode> | <MedicalTeeth> |
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 同日得有 90001C/90002C/90003C/90019C/90020C 診療項目，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastMedicalDate | MedicalNhiCode | MedicalTeeth | PassOrNot |
            | 90098C       | 11         | MOB          | 當日              | 90001C         | 11           | Pass      |
            | 90098C       | 11         | MOB          | 當日              | 90002C         | 11           | Pass      |
            | 90098C       | 11         | MOB          | 當日              | 90003C         | 11           | Pass      |
            | 90098C       | 11         | MOB          | 當日              | 90019C         | 11           | Pass      |
            | 90098C       | 11         | MOB          | 當日              | 90020C         | 11           | Pass      |
            | 90098C       | 11         | MOB          | 昨日              | 90001C         | 11           | NotPass   |
            | 90098C       | 11         | MOB          | 昨日              | 90002C         | 11           | NotPass   |
            | 90098C       | 11         | MOB          | 昨日              | 90003C         | 11           | NotPass   |
            | 90098C       | 11         | MOB          | 昨日              | 90019C         | 11           | NotPass   |
            | 90098C       | 11         | MOB          | 昨日              | 90020C         | 11           | NotPass   |
