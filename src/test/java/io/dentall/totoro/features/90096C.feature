Feature: 90096C 難症特別處理-符合附表3.3.1標準之多根管根管治療。(三根管)

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
            | 90096C       | 11         | MOB          | Pass      |

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
            | 90096C       | 11         | MOB          | 90001C           | 11             | MOB              | Pass      |
            | 90096C       | 11         | MOB          | 90002C           | 11             | MOB              | Pass      |
            | 90096C       | 11         | MOB          | 90003C           | 11             | MOB              | Pass      |
            | 90096C       | 11         | MOB          | 90019C           | 11             | MOB              | Pass      |
            | 90096C       | 11         | MOB          | 90020C           | 11             | MOB              | Pass      |

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
            | 90096C       | 11         | MOB          | 當日                | 90001C           | 11             | MOB              | Pass      |
            | 90096C       | 11         | MOB          | 當日                | 90002C           | 11             | MOB              | Pass      |
            | 90096C       | 11         | MOB          | 當日                | 90003C           | 11             | MOB              | Pass      |
            | 90096C       | 11         | MOB          | 當日                | 90019C           | 11             | MOB              | Pass      |
            | 90096C       | 11         | MOB          | 當日                | 90020C           | 11             | MOB              | Pass      |
            | 90096C       | 11         | MOB          | 昨日                | 90001C           | 11             | MOB              | NotPass   |
            | 90096C       | 11         | MOB          | 昨日                | 90002C           | 11             | MOB              | NotPass   |
            | 90096C       | 11         | MOB          | 昨日                | 90003C           | 11             | MOB              | NotPass   |
            | 90096C       | 11         | MOB          | 昨日                | 90019C           | 11             | MOB              | NotPass   |
            | 90096C       | 11         | MOB          | 昨日                | 90020C           | 11             | MOB              | NotPass   |

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
            | 90096C       | 11         | MOB          | 當日              | 90001C         | 11           | Pass      |
            | 90096C       | 11         | MOB          | 當日              | 90002C         | 11           | Pass      |
            | 90096C       | 11         | MOB          | 當日              | 90003C         | 11           | Pass      |
            | 90096C       | 11         | MOB          | 當日              | 90019C         | 11           | Pass      |
            | 90096C       | 11         | MOB          | 當日              | 90020C         | 11           | Pass      |
            | 90096C       | 11         | MOB          | 昨日              | 90001C         | 11           | NotPass   |
            | 90096C       | 11         | MOB          | 昨日              | 90002C         | 11           | NotPass   |
            | 90096C       | 11         | MOB          | 昨日              | 90003C         | 11           | NotPass   |
            | 90096C       | 11         | MOB          | 昨日              | 90019C         | 11           | NotPass   |
            | 90096C       | 11         | MOB          | 昨日              | 90020C         | 11           | NotPass   |

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
            | 90096C       | 51         | DL           | NotPass   |
            | 90096C       | 52         | DL           | NotPass   |
            | 90096C       | 53         | DL           | NotPass   |
            | 90096C       | 54         | DL           | NotPass   |
            | 90096C       | 55         | DL           | NotPass   |
            | 90096C       | 61         | DL           | NotPass   |
            | 90096C       | 62         | DL           | NotPass   |
            | 90096C       | 63         | DL           | NotPass   |
            | 90096C       | 64         | DL           | NotPass   |
            | 90096C       | 65         | DL           | NotPass   |
            | 90096C       | 71         | DL           | NotPass   |
            | 90096C       | 72         | DL           | NotPass   |
            | 90096C       | 73         | DL           | NotPass   |
            | 90096C       | 74         | DL           | NotPass   |
            | 90096C       | 75         | DL           | NotPass   |
            | 90096C       | 81         | DL           | NotPass   |
            | 90096C       | 82         | DL           | NotPass   |
            | 90096C       | 83         | DL           | NotPass   |
            | 90096C       | 84         | DL           | NotPass   |
            | 90096C       | 85         | DL           | NotPass   |
            # 恆牙
            | 90096C       | 11         | DL           | Pass      |
            | 90096C       | 12         | DL           | Pass      |
            | 90096C       | 13         | DL           | Pass      |
            | 90096C       | 14         | DL           | Pass      |
            | 90096C       | 15         | DL           | Pass      |
            | 90096C       | 16         | DL           | Pass      |
            | 90096C       | 17         | DL           | Pass      |
            | 90096C       | 18         | DL           | Pass      |
            | 90096C       | 21         | DL           | Pass      |
            | 90096C       | 22         | DL           | Pass      |
            | 90096C       | 23         | DL           | Pass      |
            | 90096C       | 24         | DL           | Pass      |
            | 90096C       | 25         | DL           | Pass      |
            | 90096C       | 26         | DL           | Pass      |
            | 90096C       | 27         | DL           | Pass      |
            | 90096C       | 28         | DL           | Pass      |
            | 90096C       | 31         | DL           | Pass      |
            | 90096C       | 32         | DL           | Pass      |
            | 90096C       | 33         | DL           | Pass      |
            | 90096C       | 34         | DL           | Pass      |
            | 90096C       | 35         | DL           | Pass      |
            | 90096C       | 36         | DL           | Pass      |
            | 90096C       | 37         | DL           | Pass      |
            | 90096C       | 38         | DL           | Pass      |
            | 90096C       | 41         | DL           | Pass      |
            | 90096C       | 42         | DL           | Pass      |
            | 90096C       | 43         | DL           | Pass      |
            | 90096C       | 44         | DL           | Pass      |
            | 90096C       | 45         | DL           | Pass      |
            | 90096C       | 46         | DL           | Pass      |
            | 90096C       | 47         | DL           | Pass      |
            | 90096C       | 48         | DL           | Pass      |
            # 無牙
            | 90096C       |            | DL           | NotPass   |
            #
            | 90096C       | 19         | DL           | Pass      |
            | 90096C       | 29         | DL           | Pass      |
            | 90096C       | 39         | DL           | Pass      |
            | 90096C       | 49         | DL           | Pass      |
            | 90096C       | 59         | DL           | NotPass   |
            | 90096C       | 69         | DL           | NotPass   |
            | 90096C       | 79         | DL           | NotPass   |
            | 90096C       | 89         | DL           | NotPass   |
            | 90096C       | 99         | DL           | Pass      |
            # 牙位為區域型態
            | 90096C       | FM         | DL           | NotPass   |
            | 90096C       | UR         | DL           | NotPass   |
            | 90096C       | UL         | DL           | NotPass   |
            | 90096C       | LL         | DL           | NotPass   |
            | 90096C       | LR         | DL           | NotPass   |
            | 90096C       | UA         | DL           | NotPass   |
            | 90096C       | LA         | DL           | NotPass   |
            # 非法牙位
            | 90096C       | 00         | DL           | NotPass   |
            | 90096C       | 01         | DL           | NotPass   |
            | 90096C       | 10         | DL           | NotPass   |
            | 90096C       | 56         | DL           | NotPass   |
            | 90096C       | 66         | DL           | NotPass   |
            | 90096C       | 76         | DL           | NotPass   |
            | 90096C       | 86         | DL           | NotPass   |
            | 90096C       | 91         | DL           | NotPass   |
