@nhi @nhi-90-series @part3
Feature: 90092C 難症特別處理－有額外根管者(1)前牙及下顎小臼齒有超過一根管者。(2)上顎小臼齒有超過二根管者。(3)大臼齒有超過三根管者。(4)以實際超過根管數計算。

    Scenario Outline: 全部檢核成功
        Given 建立醫師
        Given Wind 24 歲病人
        Given 在過去第 60 天，建立預約
        Given 在過去第 60 天，建立掛號
        Given 在過去第 60 天，產生診療計畫
        And 新增診療代碼:
            | PastDays | A72 | A73    | A74 | A75 | A76 | A77 | A78 | A79 |
            | 60       | 3   | 90015C | 11  | MOB | 0   | 1.0 | 03  |     |
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
            | 90092C       | 11         | MOB          | Pass      |

    Scenario Outline: 提醒須檢附影像
        Given 建立醫師
        Given Wind 24 歲病人
        Given 在過去第 60 天，建立預約
        Given 在過去第 60 天，建立掛號
        Given 在過去第 60 天，產生診療計畫
        And 新增診療代碼:
            | PastDays | A72 | A73    | A74 | A75 | A76 | A77 | A78 | A79 |
            | 60       | 3   | 90015C | 11  | MOB | 0   | 1.0 | 03  |     |
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | 90001C         | <IssueTeeth> | <IssueSurface> |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 提醒"須檢附影像"，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            | 90092C       | 11         | MOB          | Pass      |

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
        Then 同日得有 90001C/90002C/90003C/90019C/90020C 診療項目，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | TreatmentNhiCode | TreatmentTeeth | TreatmentSurface | PassOrNot |
            | 90092C       | 11         | MOB          | 90001C           | 11             | MOB              | Pass      |
            | 90092C       | 11         | MOB          | 90002C           | 11             | MOB              | Pass      |
            | 90092C       | 11         | MOB          | 90003C           | 11             | MOB              | Pass      |
            | 90092C       | 11         | MOB          | 90019C           | 11             | MOB              | Pass      |
            | 90092C       | 11         | MOB          | 90020C           | 11             | MOB              | Pass      |
            | 90092C       | 11         | MOB          | 01271C           | 11             | MOB              | NotPass   |

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
            | 90092C       | 11         | MOB          | 當日                | 90001C           | 11             | MOB              | Pass      |
            | 90092C       | 11         | MOB          | 當日                | 90002C           | 11             | MOB              | Pass      |
            | 90092C       | 11         | MOB          | 當日                | 90003C           | 11             | MOB              | Pass      |
            | 90092C       | 11         | MOB          | 當日                | 90019C           | 11             | MOB              | Pass      |
            | 90092C       | 11         | MOB          | 當日                | 90020C           | 11             | MOB              | Pass      |
            | 90092C       | 11         | MOB          | 昨日                | 90001C           | 11             | MOB              | NotPass   |
            | 90092C       | 11         | MOB          | 昨日                | 90002C           | 11             | MOB              | NotPass   |
            | 90092C       | 11         | MOB          | 昨日                | 90003C           | 11             | MOB              | NotPass   |
            | 90092C       | 11         | MOB          | 昨日                | 90019C           | 11             | MOB              | NotPass   |
            | 90092C       | 11         | MOB          | 昨日                | 90020C           | 11             | MOB              | NotPass   |

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
            | 90092C       | 11         | MOB          | 當日              | 90001C         | 11           | Pass      |
            | 90092C       | 11         | MOB          | 當日              | 90002C         | 11           | Pass      |
            | 90092C       | 11         | MOB          | 當日              | 90003C         | 11           | Pass      |
            | 90092C       | 11         | MOB          | 當日              | 90019C         | 11           | Pass      |
            | 90092C       | 11         | MOB          | 當日              | 90020C         | 11           | Pass      |
            | 90092C       | 11         | MOB          | 昨日              | 90001C         | 11           | NotPass   |
            | 90092C       | 11         | MOB          | 昨日              | 90002C         | 11           | NotPass   |
            | 90092C       | 11         | MOB          | 昨日              | 90003C         | 11           | NotPass   |
            | 90092C       | 11         | MOB          | 昨日              | 90019C         | 11           | NotPass   |
            | 90092C       | 11         | MOB          | 昨日              | 90020C         | 11           | NotPass   |

    Scenario Outline: 檢查治療的牙位是否為 PERMANENT_TOOTH
        Given 建立醫師
        Given Wind 24 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | 90001C         | <IssueTeeth> | <IssueSurface> |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 檢查 <IssueTeeth> 牙位，依 PERMANENT_TOOTH 判定是否為核可牙位，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            # 乳牙
            | 90092C       | 51         | DL           | NotPass   |
            | 90092C       | 52         | DL           | NotPass   |
            | 90092C       | 53         | DL           | NotPass   |
            | 90092C       | 54         | DL           | NotPass   |
            | 90092C       | 55         | DL           | NotPass   |
            | 90092C       | 61         | DL           | NotPass   |
            | 90092C       | 62         | DL           | NotPass   |
            | 90092C       | 63         | DL           | NotPass   |
            | 90092C       | 64         | DL           | NotPass   |
            | 90092C       | 65         | DL           | NotPass   |
            | 90092C       | 71         | DL           | NotPass   |
            | 90092C       | 72         | DL           | NotPass   |
            | 90092C       | 73         | DL           | NotPass   |
            | 90092C       | 74         | DL           | NotPass   |
            | 90092C       | 75         | DL           | NotPass   |
            | 90092C       | 81         | DL           | NotPass   |
            | 90092C       | 82         | DL           | NotPass   |
            | 90092C       | 83         | DL           | NotPass   |
            | 90092C       | 84         | DL           | NotPass   |
            | 90092C       | 85         | DL           | NotPass   |
            # 恆牙
            | 90092C       | 11         | DL           | Pass      |
            | 90092C       | 12         | DL           | Pass      |
            | 90092C       | 13         | DL           | Pass      |
            | 90092C       | 14         | DL           | Pass      |
            | 90092C       | 15         | DL           | Pass      |
            | 90092C       | 16         | DL           | Pass      |
            | 90092C       | 17         | DL           | Pass      |
            | 90092C       | 18         | DL           | Pass      |
            | 90092C       | 21         | DL           | Pass      |
            | 90092C       | 22         | DL           | Pass      |
            | 90092C       | 23         | DL           | Pass      |
            | 90092C       | 24         | DL           | Pass      |
            | 90092C       | 25         | DL           | Pass      |
            | 90092C       | 26         | DL           | Pass      |
            | 90092C       | 27         | DL           | Pass      |
            | 90092C       | 28         | DL           | Pass      |
            | 90092C       | 31         | DL           | Pass      |
            | 90092C       | 32         | DL           | Pass      |
            | 90092C       | 33         | DL           | Pass      |
            | 90092C       | 34         | DL           | Pass      |
            | 90092C       | 35         | DL           | Pass      |
            | 90092C       | 36         | DL           | Pass      |
            | 90092C       | 37         | DL           | Pass      |
            | 90092C       | 38         | DL           | Pass      |
            | 90092C       | 41         | DL           | Pass      |
            | 90092C       | 42         | DL           | Pass      |
            | 90092C       | 43         | DL           | Pass      |
            | 90092C       | 44         | DL           | Pass      |
            | 90092C       | 45         | DL           | Pass      |
            | 90092C       | 46         | DL           | Pass      |
            | 90092C       | 47         | DL           | Pass      |
            | 90092C       | 48         | DL           | Pass      |
            # 無牙
            | 90092C       |            | DL           | NotPass   |
            #
            | 90092C       | 19         | DL           | Pass      |
            | 90092C       | 29         | DL           | Pass      |
            | 90092C       | 39         | DL           | Pass      |
            | 90092C       | 49         | DL           | Pass      |
            | 90092C       | 59         | DL           | NotPass   |
            | 90092C       | 69         | DL           | NotPass   |
            | 90092C       | 79         | DL           | NotPass   |
            | 90092C       | 89         | DL           | NotPass   |
            | 90092C       | 99         | DL           | Pass      |
            # 牙位為區域型態
            | 90092C       | FM         | DL           | NotPass   |
            | 90092C       | UR         | DL           | NotPass   |
            | 90092C       | UL         | DL           | NotPass   |
            | 90092C       | UA         | DL           | NotPass   |
            | 90092C       | UB         | DL           | NotPass   |
            | 90092C       | LL         | DL           | NotPass   |
            | 90092C       | LR         | DL           | NotPass   |
            | 90092C       | LA         | DL           | NotPass   |
            | 90092C       | LB         | DL           | NotPass   |
            # 非法牙位
            | 90092C       | 00         | DL           | NotPass   |
            | 90092C       | 01         | DL           | NotPass   |
            | 90092C       | 10         | DL           | NotPass   |
            | 90092C       | 56         | DL           | NotPass   |
            | 90092C       | 66         | DL           | NotPass   |
            | 90092C       | 76         | DL           | NotPass   |
            | 90092C       | 86         | DL           | NotPass   |
            | 90092C       | 91         | DL           | NotPass   |