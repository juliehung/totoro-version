Feature: 91017C 懷孕婦女牙結石清除-全口

    Scenario Outline: 全部檢核成功
        Given 建立醫師
        Given Kelly 24 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 確認診療代碼 <IssueNhiCode> ，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            | 91017C       | 11         | MOB          | Pass      |

    Scenario Outline: （HIS）90天內，不應有 91017C 診療項目
        Given 建立醫師
        Given Kelly 24 歲病人
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
        Then （HIS）檢查 <IssueNhiCode> 診療項目，在病患過去 <GapDay> 天紀錄中，不應包含特定的 <TreatmentNhiCode> 診療代碼，確認結果是否為 <PassOrNot> 且檢查訊息類型為 D4_1
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastTreatmentDays | TreatmentNhiCode | TreatmentTeeth | TreatmentSurface | GapDay | PassOrNot |
            | 91017C       | FM         | MOB          | 1                 | 91017C           | FM             | MOB              | 90     | NotPass   |
            | 91017C       | FM         | MOB          | 90                | 91017C           | FM             | MOB              | 90     | NotPass   |
            | 91017C       | FM         | MOB          | 91                | 91017C           | FM             | MOB              | 90     | Pass      |

    Scenario Outline: （IC）90天內，不應有 91017C 診療項目
        Given 建立醫師
        Given Kelly 24 歲病人
        Given 新增健保醫療:
            | PastDays          | NhiCode          | Teeth          |
            | <PastMedicalDays> | <MedicalNhiCode> | <MedicalTeeth> |
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then （IC）檢查 <IssueNhiCode> 診療項目，在病患過去 <GapDay> 天紀錄中，不應包含特定的 <MedicalNhiCode> 診療代碼，確認結果是否為 <PassOrNot> 且檢查訊息類型為 D4_1
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastMedicalDays | MedicalNhiCode | MedicalTeeth | GapDay | PassOrNot |
            | 91017C       | FM         | MOB          | 1               | 91017C         | FM           | 90     | NotPass   |
            | 91017C       | FM         | MOB          | 90              | 91017C         | FM           | 90     | NotPass   |
            | 91017C       | FM         | MOB          | 91              | 91017C         | FM           | 90     | Pass      |

    Scenario Outline: （Disposal）同日不得同時有 91001C/91003C/91004C/91005C/91014C/91103C/91104C/91019C 診療項目
        Given 建立醫師
        Given Kelly 24 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode         | NewTeeth         | NewSurface         |
            |         |       |         | <TreatmentNhiCode> | <TreatmentTeeth> | <TreatmentSurface> |
            |         |       |         | <IssueNhiCode>     | <IssueTeeth>     | <IssueSurface>     |
        Then 同日不得有 91001C/91003C/91004C/91005C/91014C/91103C/91104C/91019C 診療項目，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | TreatmentNhiCode | TreatmentTeeth | TreatmentSurface | PassOrNot |
            | 91017C       | 11         | MOB          | 91001C           | 11             | MOB              | NotPass   |
            | 91017C       | 11         | MOB          | 91003C           | 11             | MOB              | NotPass   |
            | 91017C       | 11         | MOB          | 91004C           | 11             | MOB              | NotPass   |
            | 91017C       | 11         | MOB          | 91005C           | 11             | MOB              | NotPass   |
            | 91017C       | 11         | MOB          | 91014C           | 11             | MOB              | NotPass   |
            | 91017C       | 11         | MOB          | 91103C           | 11             | MOB              | NotPass   |
            | 91017C       | 11         | MOB          | 91104C           | 11             | MOB              | NotPass   |
            | 91017C       | 11         | MOB          | 91019C           | 11             | MOB              | NotPass   |

    Scenario Outline: （HIS-Today）同日不得同時有 91001C/91003C/91004C/91005C/91014C/91103C/91104C/91019C 診療項目
        Given 建立醫師
        Given Kelly 24 歲病人
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
        Then 同日不得有 91001C/91003C/91004C/91005C/91014C/91103C/91104C/91019C 診療項目，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastTreatmentDate | TreatmentNhiCode | TreatmentTeeth | TreatmentSurface | PassOrNot |
            | 91017C       | 11         | MOB          | 當日                | 91001C           | 11             | MOB              | NotPass   |
            | 91017C       | 11         | MOB          | 當日                | 91003C           | 11             | MOB              | NotPass   |
            | 91017C       | 11         | MOB          | 當日                | 91004C           | 11             | MOB              | NotPass   |
            | 91017C       | 11         | MOB          | 當日                | 91005C           | 11             | MOB              | NotPass   |
            | 91017C       | 11         | MOB          | 當日                | 91014C           | 11             | MOB              | NotPass   |
            | 91017C       | 11         | MOB          | 當日                | 91103C           | 11             | MOB              | NotPass   |
            | 91017C       | 11         | MOB          | 當日                | 91104C           | 11             | MOB              | NotPass   |
            | 91017C       | 11         | MOB          | 當日                | 91019C           | 11             | MOB              | NotPass   |
            | 91017C       | 11         | MOB          | 昨日                | 91001C           | 11             | MOB              | Pass      |
            | 91017C       | 11         | MOB          | 昨日                | 91003C           | 11             | MOB              | Pass      |
            | 91017C       | 11         | MOB          | 昨日                | 91004C           | 11             | MOB              | Pass      |
            | 91017C       | 11         | MOB          | 昨日                | 91005C           | 11             | MOB              | Pass      |
            | 91017C       | 11         | MOB          | 昨日                | 91014C           | 11             | MOB              | Pass      |
            | 91017C       | 11         | MOB          | 昨日                | 91103C           | 11             | MOB              | Pass      |
            | 91017C       | 11         | MOB          | 昨日                | 91104C           | 11             | MOB              | Pass      |
            | 91017C       | 11         | MOB          | 昨日                | 91019C           | 11             | MOB              | Pass      |

    Scenario Outline: （IC）同日不得同時有 91001C/91003C/91004C/91005C/91014C/91103C/91104C/91019C 診療項目
        Given 建立醫師
        Given Kelly 24 歲病人
        Given 新增健保醫療:
            | PastDate          | NhiCode          | Teeth          |
            | <PastMedicalDate> | <MedicalNhiCode> | <MedicalTeeth> |
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 同日不得有 91001C/91003C/91004C/91005C/91014C/91103C/91104C/91019C 診療項目，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastMedicalDate | MedicalNhiCode | MedicalTeeth | PassOrNot |
            | 91017C       | 11         | MOB          | 當日              | 91001C         | 11           | NotPass   |
            | 91017C       | 11         | MOB          | 當日              | 91003C         | 11           | NotPass   |
            | 91017C       | 11         | MOB          | 當日              | 91004C         | 11           | NotPass   |
            | 91017C       | 11         | MOB          | 當日              | 91005C         | 11           | NotPass   |
            | 91017C       | 11         | MOB          | 當日              | 91014C         | 11           | NotPass   |
            | 91017C       | 11         | MOB          | 當日              | 91103C         | 11           | NotPass   |
            | 91017C       | 11         | MOB          | 當日              | 91104C         | 11           | NotPass   |
            | 91017C       | 11         | MOB          | 當日              | 91019C         | 11           | NotPass   |
            | 91017C       | 11         | MOB          | 昨日              | 91001C         | 11           | Pass      |
            | 91017C       | 11         | MOB          | 昨日              | 91003C         | 11           | Pass      |
            | 91017C       | 11         | MOB          | 昨日              | 91004C         | 11           | Pass      |
            | 91017C       | 11         | MOB          | 昨日              | 91005C         | 11           | Pass      |
            | 91017C       | 11         | MOB          | 昨日              | 91014C         | 11           | Pass      |
            | 91017C       | 11         | MOB          | 昨日              | 91103C         | 11           | Pass      |
            | 91017C       | 11         | MOB          | 昨日              | 91104C         | 11           | Pass      |
            | 91017C       | 11         | MOB          | 昨日              | 91019C         | 11           | Pass      |
