@nhi @nhi-91-series
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
            | 91017C       | FM         | MOB          | Pass      |

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
        Then 檢查 <IssueNhiCode> 診療項目，在病患過去 <GapDay> 天紀錄中，不應包含特定的 <TreatmentNhiCode> 診療代碼，確認結果是否為 <PassOrNot> 且檢查訊息類型為 D4_1
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
        Then 檢查 <IssueNhiCode> 診療項目，在病患過去 <GapDay> 天紀錄中，不應包含特定的 <MedicalNhiCode> 診療代碼，確認結果是否為 <PassOrNot> 且檢查訊息類型為 D4_1
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
        Then 同日不得有 <TreatmentNhiCode> 診療項目，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | TreatmentNhiCode | TreatmentTeeth | TreatmentSurface | PassOrNot |
            | 91017C       | FM         | MOB          | 91001C           | FM             | MOB              | NotPass   |
            | 91017C       | FM         | MOB          | 91003C           | FM             | MOB              | NotPass   |
            | 91017C       | FM         | MOB          | 91004C           | FM             | MOB              | NotPass   |
            | 91017C       | FM         | MOB          | 91005C           | FM             | MOB              | NotPass   |
            | 91017C       | FM         | MOB          | 91014C           | FM             | MOB              | NotPass   |
            | 91017C       | FM         | MOB          | 91103C           | FM             | MOB              | NotPass   |
            | 91017C       | FM         | MOB          | 91104C           | FM             | MOB              | NotPass   |
            | 91017C       | FM         | MOB          | 91019C           | FM             | MOB              | NotPass   |

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
        Then 同日不得有 <TreatmentNhiCode> 診療項目，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastTreatmentDate | TreatmentNhiCode | TreatmentTeeth | TreatmentSurface | PassOrNot |
            | 91017C       | FM         | MOB          | 當日                | 91001C           | FM             | MOB              | NotPass   |
            | 91017C       | FM         | MOB          | 當日                | 91003C           | FM             | MOB              | NotPass   |
            | 91017C       | FM         | MOB          | 當日                | 91004C           | FM             | MOB              | NotPass   |
            | 91017C       | FM         | MOB          | 當日                | 91005C           | FM             | MOB              | NotPass   |
            | 91017C       | FM         | MOB          | 當日                | 91014C           | FM             | MOB              | NotPass   |
            | 91017C       | FM         | MOB          | 當日                | 91103C           | FM             | MOB              | NotPass   |
            | 91017C       | FM         | MOB          | 當日                | 91104C           | FM             | MOB              | NotPass   |
            | 91017C       | FM         | MOB          | 當日                | 91019C           | FM             | MOB              | NotPass   |
            | 91017C       | FM         | MOB          | 昨日                | 91001C           | FM             | MOB              | Pass      |
            | 91017C       | FM         | MOB          | 昨日                | 91003C           | FM             | MOB              | Pass      |
            | 91017C       | FM         | MOB          | 昨日                | 91004C           | FM             | MOB              | Pass      |
            | 91017C       | FM         | MOB          | 昨日                | 91005C           | FM             | MOB              | Pass      |
            | 91017C       | FM         | MOB          | 昨日                | 91014C           | FM             | MOB              | Pass      |
            | 91017C       | FM         | MOB          | 昨日                | 91103C           | FM             | MOB              | Pass      |
            | 91017C       | FM         | MOB          | 昨日                | 91104C           | FM             | MOB              | Pass      |
            | 91017C       | FM         | MOB          | 昨日                | 91019C           | FM             | MOB              | Pass      |

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
        Then 同日不得有 <MedicalNhiCode> 診療項目，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastMedicalDate | MedicalNhiCode | MedicalTeeth | PassOrNot |
            | 91017C       | FM         | MOB          | 當日              | 91001C         | FM           | NotPass   |
            | 91017C       | FM         | MOB          | 當日              | 91003C         | FM           | NotPass   |
            | 91017C       | FM         | MOB          | 當日              | 91004C         | FM           | NotPass   |
            | 91017C       | FM         | MOB          | 當日              | 91005C         | FM           | NotPass   |
            | 91017C       | FM         | MOB          | 當日              | 91014C         | FM           | NotPass   |
            | 91017C       | FM         | MOB          | 當日              | 91103C         | FM           | NotPass   |
            | 91017C       | FM         | MOB          | 當日              | 91104C         | FM           | NotPass   |
            | 91017C       | FM         | MOB          | 當日              | 91019C         | FM           | NotPass   |
            | 91017C       | FM         | MOB          | 昨日              | 91001C         | FM           | Pass      |
            | 91017C       | FM         | MOB          | 昨日              | 91003C         | FM           | Pass      |
            | 91017C       | FM         | MOB          | 昨日              | 91004C         | FM           | Pass      |
            | 91017C       | FM         | MOB          | 昨日              | 91005C         | FM           | Pass      |
            | 91017C       | FM         | MOB          | 昨日              | 91014C         | FM           | Pass      |
            | 91017C       | FM         | MOB          | 昨日              | 91103C         | FM           | Pass      |
            | 91017C       | FM         | MOB          | 昨日              | 91104C         | FM           | Pass      |
            | 91017C       | FM         | MOB          | 昨日              | 91019C         | FM           | Pass      |

    Scenario Outline: 檢查治療的牙位是否為 FULL_ZONE
        Given 建立醫師
        Given Kelly 24 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        And 新增診療代碼:
            | A72 | A73    | A74 | A75 | A76 | A77 | A78 | A79 |
            | 3   | 91004C | FM  | MOB | 0   | 1.0 | 03  |     |
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            | 91004C  | FM    | MOB     |                |              |                |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 檢查 <IssueTeeth> 牙位，依 FULL_ZONE 判定是否為核可牙位，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            # 乳牙
            | 91017C       | 51         | DL           | NotPass   |
            | 91017C       | 52         | DL           | NotPass   |
            | 91017C       | 53         | DL           | NotPass   |
            | 91017C       | 54         | DL           | NotPass   |
            | 91017C       | 55         | DL           | NotPass   |
            | 91017C       | 61         | DL           | NotPass   |
            | 91017C       | 62         | DL           | NotPass   |
            | 91017C       | 63         | DL           | NotPass   |
            | 91017C       | 64         | DL           | NotPass   |
            | 91017C       | 65         | DL           | NotPass   |
            | 91017C       | 71         | DL           | NotPass   |
            | 91017C       | 72         | DL           | NotPass   |
            | 91017C       | 73         | DL           | NotPass   |
            | 91017C       | 74         | DL           | NotPass   |
            | 91017C       | 75         | DL           | NotPass   |
            | 91017C       | 81         | DL           | NotPass   |
            | 91017C       | 82         | DL           | NotPass   |
            | 91017C       | 83         | DL           | NotPass   |
            | 91017C       | 84         | DL           | NotPass   |
            | 91017C       | 85         | DL           | NotPass   |
            # 恆牙
            | 91017C       | 11         | DL           | NotPass   |
            | 91017C       | 12         | DL           | NotPass   |
            | 91017C       | 13         | DL           | NotPass   |
            | 91017C       | 14         | DL           | NotPass   |
            | 91017C       | 15         | DL           | NotPass   |
            | 91017C       | 16         | DL           | NotPass   |
            | 91017C       | 17         | DL           | NotPass   |
            | 91017C       | 18         | DL           | NotPass   |
            | 91017C       | 21         | DL           | NotPass   |
            | 91017C       | 22         | DL           | NotPass   |
            | 91017C       | 23         | DL           | NotPass   |
            | 91017C       | 24         | DL           | NotPass   |
            | 91017C       | 25         | DL           | NotPass   |
            | 91017C       | 26         | DL           | NotPass   |
            | 91017C       | 27         | DL           | NotPass   |
            | 91017C       | 28         | DL           | NotPass   |
            | 91017C       | 31         | DL           | NotPass   |
            | 91017C       | 32         | DL           | NotPass   |
            | 91017C       | 33         | DL           | NotPass   |
            | 91017C       | 34         | DL           | NotPass   |
            | 91017C       | 35         | DL           | NotPass   |
            | 91017C       | 36         | DL           | NotPass   |
            | 91017C       | 37         | DL           | NotPass   |
            | 91017C       | 38         | DL           | NotPass   |
            | 91017C       | 41         | DL           | NotPass   |
            | 91017C       | 42         | DL           | NotPass   |
            | 91017C       | 43         | DL           | NotPass   |
            | 91017C       | 44         | DL           | NotPass   |
            | 91017C       | 45         | DL           | NotPass   |
            | 91017C       | 46         | DL           | NotPass   |
            | 91017C       | 47         | DL           | NotPass   |
            | 91017C       | 48         | DL           | NotPass   |
            # 無牙
            | 91017C       |            | DL           | NotPass   |
            #
            | 91017C       | 19         | DL           | NotPass   |
            | 91017C       | 29         | DL           | NotPass   |
            | 91017C       | 39         | DL           | NotPass   |
            | 91017C       | 49         | DL           | NotPass   |
            | 91017C       | 59         | DL           | NotPass   |
            | 91017C       | 69         | DL           | NotPass   |
            | 91017C       | 79         | DL           | NotPass   |
            | 91017C       | 89         | DL           | NotPass   |
            | 91017C       | 99         | DL           | NotPass   |
            # 牙位為區域型態
            | 91017C       | FM         | DL           | Pass      |
            | 91017C       | UR         | DL           | NotPass   |
            | 91017C       | UL         | DL           | NotPass   |
            | 91017C       | UA         | DL           | NotPass   |
            | 91017C       | UB         | DL           | NotPass   |
            | 91017C       | LL         | DL           | NotPass   |
            | 91017C       | LR         | DL           | NotPass   |
            | 91017C       | LA         | DL           | NotPass   |
            | 91017C       | LB         | DL           | NotPass   |
            # 非法牙位
            | 91017C       | 00         | DL           | NotPass   |
            | 91017C       | 01         | DL           | NotPass   |
            | 91017C       | 10         | DL           | NotPass   |
            | 91017C       | 56         | DL           | NotPass   |
            | 91017C       | 66         | DL           | NotPass   |
            | 91017C       | 76         | DL           | NotPass   |
            | 91017C       | 86         | DL           | NotPass   |
            | 91017C       | 91         | DL           | NotPass   |
