Feature: 92094C 週六、日及國定假日牙醫門診急症處置

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
            | 92094C       | 11         | DL           | Pass      |

    Scenario Outline: 提醒限週六、日及國定假日申報, 當月看診需≦二十六日, 前月於健保VPN完成登錄
        Given 建立醫師
        Given Scott 24 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 提醒"限週六、日及國定假日申報, 當月看診需≦二十六日, 前月於健保VPN完成登錄"，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            | 92094C       | 11         | MOB          | Pass      |

    Scenario Outline: （Disposal）同日不得同時有 34001C/34002C/90004C/91001C/92001C/92012C/92043C/92066C/92071C/92093B/92096C 診療項目
        Given 建立醫師
        Given Scott 24 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode         | NewTeeth         | NewSurface         |
            |         |       |         | <TreatmentNhiCode> | <TreatmentTeeth> | <TreatmentSurface> |
            |         |       |         | <IssueNhiCode>     | <IssueTeeth>     | <IssueSurface>     |
        Then 同日不得有 34001C/34002C/90004C/91001C/92001C/92012C/92043C/92066C/92071C/92093B/92096C 診療項目，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | TreatmentNhiCode | TreatmentTeeth | TreatmentSurface | PassOrNot |
            | 92094C       | 11         | MOB          | 34001C           | 11             | MOB              | NotPass   |
            | 92094C       | 11         | MOB          | 34002C           | 11             | MOB              | NotPass   |
            | 92094C       | 11         | MOB          | 90004C           | 11             | MOB              | NotPass   |
            | 92094C       | 11         | MOB          | 91001C           | 11             | MOB              | NotPass   |
            | 92094C       | 11         | MOB          | 92001C           | 11             | MOB              | NotPass   |
            | 92094C       | 11         | MOB          | 92012C           | 11             | MOB              | NotPass   |
            | 92094C       | 11         | MOB          | 92043C           | 11             | MOB              | NotPass   |
            | 92094C       | 11         | MOB          | 92066C           | 11             | MOB              | NotPass   |
            | 92094C       | 11         | MOB          | 92071C           | 11             | MOB              | NotPass   |
            | 92094C       | 11         | MOB          | 92093B           | 11             | MOB              | NotPass   |
            | 92094C       | 11         | MOB          | 92096C           | 11             | MOB              | NotPass   |

    Scenario Outline: （HIS-Today）同日不得同時有 34001C/34002C/90004C/91001C/92001C/92012C/92043C/92066C/92071C/92093B/92096C 診療項目
        Given 建立醫師
        Given Scott 24 歲病人
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
        Then 同日不得有 34001C/34002C/90004C/91001C/92001C/92012C/92043C/92066C/92071C/92093B/92096C 診療項目，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastTreatmentDate | TreatmentNhiCode | TreatmentTeeth | TreatmentSurface | PassOrNot |
            | 92094C       | 11         | MOB          | 當日                | 34001C           | 11             | MOB              | NotPass   |
            | 92094C       | 11         | MOB          | 當日                | 34002C           | 11             | MOB              | NotPass   |
            | 92094C       | 11         | MOB          | 當日                | 90004C           | 11             | MOB              | NotPass   |
            | 92094C       | 11         | MOB          | 當日                | 91001C           | 11             | MOB              | NotPass   |
            | 92094C       | 11         | MOB          | 當日                | 92001C           | 11             | MOB              | NotPass   |
            | 92094C       | 11         | MOB          | 當日                | 92012C           | 11             | MOB              | NotPass   |
            | 92094C       | 11         | MOB          | 當日                | 92043C           | 11             | MOB              | NotPass   |
            | 92094C       | 11         | MOB          | 當日                | 92066C           | 11             | MOB              | NotPass   |
            | 92094C       | 11         | MOB          | 當日                | 92071C           | 11             | MOB              | NotPass   |
            | 92094C       | 11         | MOB          | 當日                | 92093B           | 11             | MOB              | NotPass   |
            | 92094C       | 11         | MOB          | 當日                | 92096C           | 11             | MOB              | NotPass   |
            | 92094C       | 11         | MOB          | 昨日                | 34001C           | 11             | MOB              | Pass      |
            | 92094C       | 11         | MOB          | 昨日                | 34002C           | 11             | MOB              | Pass      |
            | 92094C       | 11         | MOB          | 昨日                | 90004C           | 11             | MOB              | Pass      |
            | 92094C       | 11         | MOB          | 昨日                | 91001C           | 11             | MOB              | Pass      |
            | 92094C       | 11         | MOB          | 昨日                | 92001C           | 11             | MOB              | Pass      |
            | 92094C       | 11         | MOB          | 昨日                | 92012C           | 11             | MOB              | Pass      |
            | 92094C       | 11         | MOB          | 昨日                | 92043C           | 11             | MOB              | Pass      |
            | 92094C       | 11         | MOB          | 昨日                | 92066C           | 11             | MOB              | Pass      |
            | 92094C       | 11         | MOB          | 昨日                | 92071C           | 11             | MOB              | Pass      |
            | 92094C       | 11         | MOB          | 昨日                | 92093B           | 11             | MOB              | Pass      |
            | 92094C       | 11         | MOB          | 昨日                | 92096C           | 11             | MOB              | Pass      |

    Scenario Outline: （IC）同日不得同時有 34001C/34002C/90004C/91001C/92001C/92012C/92043C/92066C/92071C/92093B/92096C 診療項目
        Given 建立醫師
        Given Scott 24 歲病人
        Given 新增健保醫療:
            | PastDate          | NhiCode          | Teeth          |
            | <PastMedicalDate> | <MedicalNhiCode> | <MedicalTeeth> |
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 同日不得有 34001C/34002C/90004C/91001C/92001C/92012C/92043C/92066C/92071C/92093B/92096C 診療項目，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastMedicalDate | MedicalNhiCode | MedicalTeeth | PassOrNot |
            | 92094C       | 11         | MOB          | 當日              | 34001C         | 11           | NotPass   |
            | 92094C       | 11         | MOB          | 當日              | 34002C         | 11           | NotPass   |
            | 92094C       | 11         | MOB          | 當日              | 90004C         | 11           | NotPass   |
            | 92094C       | 11         | MOB          | 當日              | 91001C         | 11           | NotPass   |
            | 92094C       | 11         | MOB          | 當日              | 92001C         | 11           | NotPass   |
            | 92094C       | 11         | MOB          | 當日              | 92012C         | 11           | NotPass   |
            | 92094C       | 11         | MOB          | 當日              | 92043C         | 11           | NotPass   |
            | 92094C       | 11         | MOB          | 當日              | 92066C         | 11           | NotPass   |
            | 92094C       | 11         | MOB          | 當日              | 92071C         | 11           | NotPass   |
            | 92094C       | 11         | MOB          | 當日              | 92093B         | 11           | NotPass   |
            | 92094C       | 11         | MOB          | 當日              | 92096C         | 11           | NotPass   |
            | 92094C       | 11         | MOB          | 昨日              | 34001C         | 11           | Pass      |
            | 92094C       | 11         | MOB          | 昨日              | 34002C         | 11           | Pass      |
            | 92094C       | 11         | MOB          | 昨日              | 90004C         | 11           | Pass      |
            | 92094C       | 11         | MOB          | 昨日              | 91001C         | 11           | Pass      |
            | 92094C       | 11         | MOB          | 昨日              | 92001C         | 11           | Pass      |
            | 92094C       | 11         | MOB          | 昨日              | 92012C         | 11           | Pass      |
            | 92094C       | 11         | MOB          | 昨日              | 92043C         | 11           | Pass      |
            | 92094C       | 11         | MOB          | 昨日              | 92066C         | 11           | Pass      |
            | 92094C       | 11         | MOB          | 昨日              | 92071C         | 11           | Pass      |
            | 92094C       | 11         | MOB          | 昨日              | 92093B         | 11           | Pass      |
            | 92094C       | 11         | MOB          | 昨日              | 92096C         | 11           | Pass      |
