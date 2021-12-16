@nhi @nhi-91-series @part2
Feature: 91014C 牙周暨齲齒控制基本處置

    Scenario Outline: 全部檢核成功
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
        Then 確認診療代碼 <IssueNhiCode> ，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            | 91014C       | FM         | MOB          | Pass      |

    Scenario Outline: （Disposal）同日得同時有 91003C/91004C/91005C/91020C 診療項目
        Given 建立醫師
        Given Kelly 24 歲病人
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
            | 91014C       | FM         | MOB          | 91003C           | FM             | MOB              | Pass      |
            | 91014C       | FM         | MOB          | 91004C           | FM             | MOB              | Pass      |
            | 91014C       | FM         | MOB          | 91005C           | FM             | MOB              | Pass      |
            | 91014C       | FM         | MOB          | 91020C           | FM             | MOB              | Pass      |

    Scenario Outline: （HIS-Today）同日得同時有 91003C/91004C/91005C/91020C 診療項目
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
        Then 同日得有 91003C/91004C/91005C/91020C 診療項目，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastTreatmentDate | TreatmentNhiCode | TreatmentTeeth | TreatmentSurface | PassOrNot |
            | 91014C       | FM         | MOB          | 當日                | 91003C           | FM             | MOB              | Pass      |
            | 91014C       | FM         | MOB          | 當日                | 91004C           | FM             | MOB              | Pass      |
            | 91014C       | FM         | MOB          | 當日                | 91005C           | FM             | MOB              | Pass      |
            | 91014C       | FM         | MOB          | 當日                | 91020C           | FM             | MOB              | Pass      |
            | 91014C       | FM         | MOB          | 昨日                | 91003C           | FM             | MOB              | NotPass   |
            | 91014C       | FM         | MOB          | 昨日                | 91004C           | FM             | MOB              | NotPass   |
            | 91014C       | FM         | MOB          | 昨日                | 91005C           | FM             | MOB              | NotPass   |
            | 91014C       | FM         | MOB          | 昨日                | 91020C           | FM             | MOB              | NotPass   |

    Scenario Outline: （IC）同日得同時有 91003C/91004C/91005C/91020C 診療項目
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
        Then 同日得有 91003C/91004C/91005C/91020C 診療項目，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastMedicalDate | MedicalNhiCode | MedicalTeeth | PassOrNot |
            | 91014C       | FM         | MOB          | 當日              | 91003C         | FM           | Pass      |
            | 91014C       | FM         | MOB          | 當日              | 91004C         | FM           | Pass      |
            | 91014C       | FM         | MOB          | 當日              | 91005C         | FM           | Pass      |
            | 91014C       | FM         | MOB          | 當日              | 91020C         | FM           | Pass      |
            | 91014C       | FM         | MOB          | 昨日              | 91003C         | FM           | NotPass   |
            | 91014C       | FM         | MOB          | 昨日              | 91004C         | FM           | NotPass   |
            | 91014C       | FM         | MOB          | 昨日              | 91005C         | FM           | NotPass   |
            | 91014C       | FM         | MOB          | 昨日              | 91020C         | FM           | NotPass   |

    Scenario Outline: （HIS）與91003C或91004C或91005C同時申報時，每360天限申報一次。與91020C同時申報時，則每180天限申報一次
        Given 建立醫師
        Given Kelly 24 歲病人
        Given 在過去第 <PastTreatmentDays> 天，建立預約
        Given 在過去第 <PastTreatmentDays> 天，建立掛號
        Given 在過去第 <PastTreatmentDays> 天，產生診療計畫
        And 新增診療代碼:
            | PastDays            | A72 | A73                | A74              | A75                | A76 | A77 | A78 | A79 |
            | <PastTreatmentDays> | 3   | <IssueNhiCode>     | <IssueTeeth>     | <IssueSurface>     | 0   | 1.0 | 03  |     |
            | <PastTreatmentDays> | 3   | <TreatmentNhiCode> | <TreatmentTeeth> | <TreatmentSurface> | 0   | 1.0 | 03  |     |
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode         | NewTeeth         | NewSurface         |
            |         |       |         | <IssueNhiCode>     | <IssueTeeth>     | <IssueSurface>     |
            |         |       |         | <TreatmentNhiCode> | <TreatmentTeeth> | <TreatmentSurface> |
        Then 檢查 <IssueNhiCode> 診療項目，在病患過去 <GapDay> 天紀錄中，不應包含特定的 <IssueNhiCode> 診療代碼，確認結果是否為 <PassOrNot> 且檢查訊息類型為 D4_1
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastTreatmentDays | TreatmentNhiCode | TreatmentTeeth | TreatmentSurface | GapDay | PassOrNot |
            | 91014C       | FM         | MOB          | 359               | 91003C           | FM             | MOB              | 360    | NotPass   |
            | 91014C       | FM         | MOB          | 360               | 91003C           | FM             | MOB              | 360    | NotPass   |
            | 91014C       | FM         | MOB          | 361               | 91003C           | FM             | MOB              | 360    | Pass      |
            | 91014C       | FM         | MOB          | 359               | 91004C           | FM             | MOB              | 360    | NotPass   |
            | 91014C       | FM         | MOB          | 360               | 91004C           | FM             | MOB              | 360    | NotPass   |
            | 91014C       | FM         | MOB          | 361               | 91004C           | FM             | MOB              | 360    | Pass      |
            | 91014C       | FM         | MOB          | 359               | 91005C           | FM             | MOB              | 360    | NotPass   |
            | 91014C       | FM         | MOB          | 360               | 91005C           | FM             | MOB              | 360    | NotPass   |
            | 91014C       | FM         | MOB          | 361               | 91005C           | FM             | MOB              | 360    | Pass      |
            | 91014C       | FM         | MOB          | 179               | 91020C           | FM             | MOB              | 180    | NotPass   |
            | 91014C       | FM         | MOB          | 180               | 91020C           | FM             | MOB              | 180    | NotPass   |
            | 91014C       | FM         | MOB          | 181               | 91020C           | FM             | MOB              | 180    | Pass      |

    Scenario Outline: （IC）與91003C或91004C或91005C同時申報時，每360天限申報一次。與91020C同時申報時，則每180天限申報一次
        Given 建立醫師
        Given Kelly 24 歲病人
        Given 新增健保醫療:
            | PastDays          | NhiCode          | Teeth          |
            | <PastMedicalDays> | <IssueNhiCode>   | <IssueTeeth>   |
            | <PastMedicalDays> | <MedicalNhiCode> | <MedicalTeeth> |
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode       | NewTeeth       | NewSurface     |
            |         |       |         | <IssueNhiCode>   | <IssueTeeth>   | <IssueSurface> |
            |         |       |         | <MedicalNhiCode> | <MedicalTeeth> | <IssueSurface> |
        Then 檢查 <IssueNhiCode> 診療項目，在病患過去 <GapDay> 天紀錄中，不應包含特定的 <IssueNhiCode> 診療代碼，確認結果是否為 <PassOrNot> 且檢查訊息類型為 D4_1
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastMedicalDays | MedicalNhiCode | MedicalTeeth | GapDay | PassOrNot |
            | 91014C       | FM         | MOB          | 359             | 91003C         | FM           | 360    | NotPass   |
            | 91014C       | FM         | MOB          | 360             | 91003C         | FM           | 360    | NotPass   |
            | 91014C       | FM         | MOB          | 361             | 91003C         | FM           | 360    | Pass      |
            | 91014C       | FM         | MOB          | 359             | 91004C         | FM           | 360    | NotPass   |
            | 91014C       | FM         | MOB          | 360             | 91004C         | FM           | 360    | NotPass   |
            | 91014C       | FM         | MOB          | 361             | 91004C         | FM           | 360    | Pass      |
            | 91014C       | FM         | MOB          | 359             | 91005C         | FM           | 360    | NotPass   |
            | 91014C       | FM         | MOB          | 360             | 91005C         | FM           | 360    | NotPass   |
            | 91014C       | FM         | MOB          | 361             | 91005C         | FM           | 360    | Pass      |
            | 91014C       | FM         | MOB          | 179             | 91020C         | FM           | 180    | NotPass   |
            | 91014C       | FM         | MOB          | 180             | 91020C         | FM           | 180    | NotPass   |
            | 91014C       | FM         | MOB          | 181             | 91020C         | FM           | 180    | Pass      |

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
            | 91014C       | 51         | DL           | NotPass   |
            | 91014C       | 52         | DL           | NotPass   |
            | 91014C       | 53         | DL           | NotPass   |
            | 91014C       | 54         | DL           | NotPass   |
            | 91014C       | 55         | DL           | NotPass   |
            | 91014C       | 61         | DL           | NotPass   |
            | 91014C       | 62         | DL           | NotPass   |
            | 91014C       | 63         | DL           | NotPass   |
            | 91014C       | 64         | DL           | NotPass   |
            | 91014C       | 65         | DL           | NotPass   |
            | 91014C       | 71         | DL           | NotPass   |
            | 91014C       | 72         | DL           | NotPass   |
            | 91014C       | 73         | DL           | NotPass   |
            | 91014C       | 74         | DL           | NotPass   |
            | 91014C       | 75         | DL           | NotPass   |
            | 91014C       | 81         | DL           | NotPass   |
            | 91014C       | 82         | DL           | NotPass   |
            | 91014C       | 83         | DL           | NotPass   |
            | 91014C       | 84         | DL           | NotPass   |
            | 91014C       | 85         | DL           | NotPass   |
            # 恆牙
            | 91014C       | 11         | DL           | NotPass   |
            | 91014C       | 12         | DL           | NotPass   |
            | 91014C       | 13         | DL           | NotPass   |
            | 91014C       | 14         | DL           | NotPass   |
            | 91014C       | 15         | DL           | NotPass   |
            | 91014C       | 16         | DL           | NotPass   |
            | 91014C       | 17         | DL           | NotPass   |
            | 91014C       | 18         | DL           | NotPass   |
            | 91014C       | 21         | DL           | NotPass   |
            | 91014C       | 22         | DL           | NotPass   |
            | 91014C       | 23         | DL           | NotPass   |
            | 91014C       | 24         | DL           | NotPass   |
            | 91014C       | 25         | DL           | NotPass   |
            | 91014C       | 26         | DL           | NotPass   |
            | 91014C       | 27         | DL           | NotPass   |
            | 91014C       | 28         | DL           | NotPass   |
            | 91014C       | 31         | DL           | NotPass   |
            | 91014C       | 32         | DL           | NotPass   |
            | 91014C       | 33         | DL           | NotPass   |
            | 91014C       | 34         | DL           | NotPass   |
            | 91014C       | 35         | DL           | NotPass   |
            | 91014C       | 36         | DL           | NotPass   |
            | 91014C       | 37         | DL           | NotPass   |
            | 91014C       | 38         | DL           | NotPass   |
            | 91014C       | 41         | DL           | NotPass   |
            | 91014C       | 42         | DL           | NotPass   |
            | 91014C       | 43         | DL           | NotPass   |
            | 91014C       | 44         | DL           | NotPass   |
            | 91014C       | 45         | DL           | NotPass   |
            | 91014C       | 46         | DL           | NotPass   |
            | 91014C       | 47         | DL           | NotPass   |
            | 91014C       | 48         | DL           | NotPass   |
            # 無牙
            | 91014C       |            | DL           | NotPass   |
            #
            | 91014C       | 19         | DL           | NotPass   |
            | 91014C       | 29         | DL           | NotPass   |
            | 91014C       | 39         | DL           | NotPass   |
            | 91014C       | 49         | DL           | NotPass   |
            | 91014C       | 59         | DL           | NotPass   |
            | 91014C       | 69         | DL           | NotPass   |
            | 91014C       | 79         | DL           | NotPass   |
            | 91014C       | 89         | DL           | NotPass   |
            | 91014C       | 99         | DL           | NotPass   |
            # 牙位為區域型態
            | 91014C       | FM         | DL           | Pass      |
            | 91014C       | UR         | DL           | NotPass   |
            | 91014C       | UL         | DL           | NotPass   |
            | 91014C       | UA         | DL           | NotPass   |
            | 91014C       | UB         | DL           | NotPass   |
            | 91014C       | LL         | DL           | NotPass   |
            | 91014C       | LR         | DL           | NotPass   |
            | 91014C       | LA         | DL           | NotPass   |
            | 91014C       | LB         | DL           | NotPass   |
            # 非法牙位
            | 91014C       | 00         | DL           | NotPass   |
            | 91014C       | 01         | DL           | NotPass   |
            | 91014C       | 10         | DL           | NotPass   |
            | 91014C       | 56         | DL           | NotPass   |
            | 91014C       | 66         | DL           | NotPass   |
            | 91014C       | 76         | DL           | NotPass   |
            | 91014C       | 86         | DL           | NotPass   |
            | 91014C       | 91         | DL           | NotPass   |

    Scenario Outline: （HIS）90天內，不應有 P6702C、P6703C、P6704C、P6705C 診療項目
        Given 建立醫師
        Given Kelly 11 歲病人
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
            | 91014C       | FM         | DL           | 89               | P6702C           | FM             | 90    | NotPass   |
            | 91014C       | FM         | DL           | 89               | P6703C           | FM             | 90    | NotPass   |
            | 91014C       | FM         | DL           | 89               | P6704C           | FM             | 90    | NotPass   |
            | 91014C       | FM         | DL           | 89               | P6705C           | FM             | 90    | NotPass   |
            | 91014C       | FM         | DL           | 90               | P6702C           | FM             | 90    | NotPass   |
            | 91014C       | FM         | DL           | 90               | P6703C           | FM             | 90    | NotPass   |
            | 91014C       | FM         | DL           | 90               | P6704C           | FM             | 90    | NotPass   |
            | 91014C       | FM         | DL           | 90               | P6705C           | FM             | 90    | NotPass   |
            | 91014C       | FM         | DL           | 91               | P6702C           | FM             | 90    | Pass      |
            | 91014C       | FM         | DL           | 91               | P6703C           | FM             | 90    | Pass      |
            | 91014C       | FM         | DL           | 91               | P6704C           | FM             | 90    | Pass      |
            | 91014C       | FM         | DL           | 91               | P6705C           | FM             | 90    | Pass      |

    Scenario Outline: （IC）90，不應有 P6702C、P6703C、P6704C、P6705C 診療項目
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
        Then 檢查 <IssueNhiCode> 診療項目，在病患過去 <GapDay> 天紀錄中，不應包含特定的 <MedicalNhiCode> 診療代碼，確認結果是否為 <PassOrNot> 且檢查訊息類型為 D1_2
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastMedicalDays | MedicalNhiCode | MedicalTeeth | GapDay | PassOrNot |
            | 91014C       | FM         | DL           | 89               | P6702C           | FM             | 90    | NotPass   |
            | 91014C       | FM         | DL           | 89               | P6703C           | FM             | 90    | NotPass   |
            | 91014C       | FM         | DL           | 89               | P6704C           | FM             | 90    | NotPass   |
            | 91014C       | FM         | DL           | 89               | P6705C           | FM             | 90    | NotPass   |
            | 91014C       | FM         | DL           | 90               | P6702C           | FM             | 90    | NotPass   |
            | 91014C       | FM         | DL           | 90               | P6703C           | FM             | 90    | NotPass   |
            | 91014C       | FM         | DL           | 90               | P6704C           | FM             | 90    | NotPass   |
            | 91014C       | FM         | DL           | 90               | P6705C           | FM             | 90    | NotPass   |
            | 91014C       | FM         | DL           | 91               | P6702C           | FM             | 90    | Pass      |
            | 91014C       | FM         | DL           | 91               | P6703C           | FM             | 90    | Pass      |
            | 91014C       | FM         | DL           | 91               | P6704C           | FM             | 90    | Pass      |
            | 91014C       | FM         | DL           | 91               | P6705C           | FM             | 90    | Pass      |
