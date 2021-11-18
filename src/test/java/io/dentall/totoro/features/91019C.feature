@nhi @nhi-91-series @part3
Feature: 91019C 懷孕婦女牙周緊急處置

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
            | 91019C       | 11         | MOB          | Pass      |

    Scenario Outline: （Disposal）同日不得同時有 91001C/91003C/91004C/91005C/91017C/91089C/91103C/91104C 診療項目
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
            | 91019C       | 11         | MOB          | 91001C           | 11             | MOB              | NotPass   |
            | 91019C       | 11         | MOB          | 91003C           | 11             | MOB              | NotPass   |
            | 91019C       | 11         | MOB          | 91004C           | 11             | MOB              | NotPass   |
            | 91019C       | 11         | MOB          | 91005C           | 11             | MOB              | NotPass   |
            | 91019C       | 11         | MOB          | 91017C           | 11             | MOB              | NotPass   |
            | 91019C       | 11         | MOB          | 91089C           | 11             | MOB              | NotPass   |
            | 91019C       | 11         | MOB          | 91103C           | 11             | MOB              | NotPass   |
            | 91019C       | 11         | MOB          | 91104C           | 11             | MOB              | NotPass   |

    Scenario Outline: （HIS-Today）同日不得同時有 91001C/91003C/91004C/91005C/91017C/91089C/91103C/91104C 診療項目
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
            | 91019C       | 11         | MOB          | 當日                | 91001C           | 11             | MOB              | NotPass   |
            | 91019C       | 11         | MOB          | 當日                | 91003C           | 11             | MOB              | NotPass   |
            | 91019C       | 11         | MOB          | 當日                | 91004C           | 11             | MOB              | NotPass   |
            | 91019C       | 11         | MOB          | 當日                | 91005C           | 11             | MOB              | NotPass   |
            | 91019C       | 11         | MOB          | 當日                | 91017C           | 11             | MOB              | NotPass   |
            | 91019C       | 11         | MOB          | 當日                | 91089C           | 11             | MOB              | NotPass   |
            | 91019C       | 11         | MOB          | 當日                | 91103C           | 11             | MOB              | NotPass   |
            | 91019C       | 11         | MOB          | 當日                | 91104C           | 11             | MOB              | NotPass   |
            | 91019C       | 11         | MOB          | 昨日                | 91001C           | 11             | MOB              | Pass      |
            | 91019C       | 11         | MOB          | 昨日                | 91003C           | 11             | MOB              | Pass      |
            | 91019C       | 11         | MOB          | 昨日                | 91004C           | 11             | MOB              | Pass      |
            | 91019C       | 11         | MOB          | 昨日                | 91005C           | 11             | MOB              | Pass      |
            | 91019C       | 11         | MOB          | 昨日                | 91017C           | 11             | MOB              | Pass      |
            | 91019C       | 11         | MOB          | 昨日                | 91089C           | 11             | MOB              | Pass      |
            | 91019C       | 11         | MOB          | 昨日                | 91103C           | 11             | MOB              | Pass      |
            | 91019C       | 11         | MOB          | 昨日                | 91104C           | 11             | MOB              | Pass      |

    Scenario Outline: （IC）同日不得同時有 91001C/91003C/91004C/91005C/91017C/91089C/91103C/91104C 診療項目
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
            | 91019C       | 11         | MOB          | 當日              | 91001C         | 11           | NotPass   |
            | 91019C       | 11         | MOB          | 當日              | 91003C         | 11           | NotPass   |
            | 91019C       | 11         | MOB          | 當日              | 91004C         | 11           | NotPass   |
            | 91019C       | 11         | MOB          | 當日              | 91005C         | 11           | NotPass   |
            | 91019C       | 11         | MOB          | 當日              | 91017C         | 11           | NotPass   |
            | 91019C       | 11         | MOB          | 當日              | 91089C         | 11           | NotPass   |
            | 91019C       | 11         | MOB          | 當日              | 91103C         | 11           | NotPass   |
            | 91019C       | 11         | MOB          | 當日              | 91104C         | 11           | NotPass   |
            | 91019C       | 11         | MOB          | 昨日              | 91001C         | 11           | Pass      |
            | 91019C       | 11         | MOB          | 昨日              | 91003C         | 11           | Pass      |
            | 91019C       | 11         | MOB          | 昨日              | 91004C         | 11           | Pass      |
            | 91019C       | 11         | MOB          | 昨日              | 91005C         | 11           | Pass      |
            | 91019C       | 11         | MOB          | 昨日              | 91017C         | 11           | Pass      |
            | 91019C       | 11         | MOB          | 昨日              | 91089C         | 11           | Pass      |
            | 91019C       | 11         | MOB          | 昨日              | 91103C         | 11           | Pass      |
            | 91019C       | 11         | MOB          | 昨日              | 91104C         | 11           | Pass      |

    Scenario Outline: （HIS）90天內，不應有 91089C 診療項目
        Given 建立醫師
        Given Kelly 24 歲病人
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
            | 91019C       | 11         | DL           | 89                | 91089C           | 11             | 90     | NotPass   |
            | 91019C       | 11         | DL           | 90                | 91089C           | 11             | 90     | NotPass   |
            | 91019C       | 11         | DL           | 91                | 91089C           | 11             | 90     | Pass      |

    Scenario Outline: （IC）90天內，不應有 91089C 診療項目
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
        Then 檢查 <IssueNhiCode> 診療項目，在病患過去 <GapDay> 天紀錄中，不應包含特定的 <MedicalNhiCode> 診療代碼，確認結果是否為 <PassOrNot> 且檢查訊息類型為 D1_2
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastMedicalDays | MedicalNhiCode | MedicalTeeth | GapDay | PassOrNot |
            | 91019C       | 11         | DL           | 89              | 91089C         | 11           | 90     | NotPass   |
            | 91019C       | 11         | DL           | 90              | 91089C         | 11           | 90     | NotPass   |
            | 91019C       | 11         | DL           | 91              | 91089C         | 11           | 90     | Pass      |

    Scenario Outline: 檢查治療的牙位是否為 VALIDATED_ALL_EXCLUDE_FM
        Given 建立醫師
        Given Kelly 24 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 檢查 <IssueTeeth> 牙位，依 VALIDATED_ALL_EXCLUDE_FM 判定是否為核可牙位，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            # 乳牙
            | 91019C       | 51         | DL           | Pass      |
            | 91019C       | 52         | DL           | Pass      |
            | 91019C       | 53         | DL           | Pass      |
            | 91019C       | 54         | DL           | Pass      |
            | 91019C       | 55         | DL           | Pass      |
            | 91019C       | 61         | DL           | Pass      |
            | 91019C       | 62         | DL           | Pass      |
            | 91019C       | 63         | DL           | Pass      |
            | 91019C       | 64         | DL           | Pass      |
            | 91019C       | 65         | DL           | Pass      |
            | 91019C       | 71         | DL           | Pass      |
            | 91019C       | 72         | DL           | Pass      |
            | 91019C       | 73         | DL           | Pass      |
            | 91019C       | 74         | DL           | Pass      |
            | 91019C       | 75         | DL           | Pass      |
            | 91019C       | 81         | DL           | Pass      |
            | 91019C       | 82         | DL           | Pass      |
            | 91019C       | 83         | DL           | Pass      |
            | 91019C       | 84         | DL           | Pass      |
            | 91019C       | 85         | DL           | Pass      |
            # 恆牙
            | 91019C       | 11         | DL           | Pass      |
            | 91019C       | 12         | DL           | Pass      |
            | 91019C       | 13         | DL           | Pass      |
            | 91019C       | 14         | DL           | Pass      |
            | 91019C       | 15         | DL           | Pass      |
            | 91019C       | 16         | DL           | Pass      |
            | 91019C       | 17         | DL           | Pass      |
            | 91019C       | 18         | DL           | Pass      |
            | 91019C       | 21         | DL           | Pass      |
            | 91019C       | 22         | DL           | Pass      |
            | 91019C       | 23         | DL           | Pass      |
            | 91019C       | 24         | DL           | Pass      |
            | 91019C       | 25         | DL           | Pass      |
            | 91019C       | 26         | DL           | Pass      |
            | 91019C       | 27         | DL           | Pass      |
            | 91019C       | 28         | DL           | Pass      |
            | 91019C       | 31         | DL           | Pass      |
            | 91019C       | 32         | DL           | Pass      |
            | 91019C       | 33         | DL           | Pass      |
            | 91019C       | 34         | DL           | Pass      |
            | 91019C       | 35         | DL           | Pass      |
            | 91019C       | 36         | DL           | Pass      |
            | 91019C       | 37         | DL           | Pass      |
            | 91019C       | 38         | DL           | Pass      |
            | 91019C       | 41         | DL           | Pass      |
            | 91019C       | 42         | DL           | Pass      |
            | 91019C       | 43         | DL           | Pass      |
            | 91019C       | 44         | DL           | Pass      |
            | 91019C       | 45         | DL           | Pass      |
            | 91019C       | 46         | DL           | Pass      |
            | 91019C       | 47         | DL           | Pass      |
            | 91019C       | 48         | DL           | Pass      |
            # 無牙
            | 91019C       |            | DL           | NotPass   |
            #
            | 91019C       | 19         | DL           | Pass      |
            | 91019C       | 29         | DL           | Pass      |
            | 91019C       | 39         | DL           | Pass      |
            | 91019C       | 49         | DL           | Pass      |
            | 91019C       | 59         | DL           | NotPass   |
            | 91019C       | 69         | DL           | NotPass   |
            | 91019C       | 79         | DL           | NotPass   |
            | 91019C       | 89         | DL           | NotPass   |
            | 91019C       | 99         | DL           | Pass      |
            # 牙位為區域型態
            | 91019C       | FM         | DL           | NotPass   |
            | 91019C       | UR         | DL           | Pass      |
            | 91019C       | UL         | DL           | Pass      |
            | 91019C       | UA         | DL           | Pass      |
            | 91019C       | UB         | DL           | NotPass   |
            | 91019C       | LL         | DL           | Pass      |
            | 91019C       | LR         | DL           | Pass      |
            | 91019C       | LA         | DL           | Pass      |
            | 91019C       | LB         | DL           | NotPass   |
            # 非法牙位
            | 91019C       | 00         | DL           | NotPass   |
            | 91019C       | 01         | DL           | NotPass   |
            | 91019C       | 10         | DL           | NotPass   |
            | 91019C       | 56         | DL           | NotPass   |
            | 91019C       | 66         | DL           | NotPass   |
            | 91019C       | 76         | DL           | NotPass   |
            | 91019C       | 86         | DL           | NotPass   |
            | 91019C       | 91         | DL           | NotPass   |
