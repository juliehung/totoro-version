@nhi @nhi-90-series
Feature: 90002C 恆牙根管治療（雙根）

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
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 確認診療代碼 <IssueNhiCode> ，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            | 90002C       | 11         | MOB          | Pass      |

    Scenario Outline: （HIS）同牙未曾申報過 92013C~92015C
        Given 建立醫師
        Given Wind 24 歲病人
        Given 在過去第 60 天，建立預約
        Given 在過去第 60 天，建立掛號
        Given 在過去第 60 天，產生診療計畫
        And 新增診療代碼:
            | PastDays | A72 | A73    | A74 | A75 | A76 | A77 | A78 | A79 |
            | 60       | 3   | 90015C | 11  | MOB | 0   | 1.0 | 03  |     |
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
        Then 同牙 <IssueTeeth> 未曾申報過，指定代碼 <TreatmentNhiCode> ，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastTreatmentDays | TreatmentNhiCode | TreatmentTeeth | PassOrNot |
            # 測試同牙
            | 90002C       | 11         | MOB          | 3650              | 92013C           | 11             | NotPass   |
            | 90002C       | 11         | MOB          | 3650              | 92014C           | 11             | NotPass   |
            | 90002C       | 11         | MOB          | 3650              | 92015C           | 11             | NotPass   |
            # 測試不同牙
            | 90002C       | 11         | MOB          | 3650              | 92013C           | 51             | Pass      |
            | 90002C       | 11         | MOB          | 3650              | 92014C           | 51             | Pass      |
            | 90002C       | 11         | MOB          | 3650              | 92015C           | 51             | Pass      |

    Scenario Outline: （IC）同牙未曾申報過 92013C~92015C
        Given 建立醫師
        Given Wind 24 歲病人
        Given 在過去第 60 天，建立預約
        Given 在過去第 60 天，建立掛號
        Given 在過去第 60 天，產生診療計畫
        And 新增診療代碼:
            | PastDays | A72 | A73    | A74 | A75 | A76 | A77 | A78 | A79 |
            | 60       | 3   | 90015C | 11  | MOB | 0   | 1.0 | 03  |     |
        Given 新增健保醫療:
            | PastDays          | NhiCode          | Teeth          |
            | <PastMedicalDays> | <MedicalNhiCode> | <MedicalTeeth> |
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 同牙 <IssueTeeth> 未曾申報過，指定代碼 <MedicalNhiCode> ，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastMedicalDays | MedicalNhiCode | MedicalTeeth | PassOrNot |
            # 測試同牙
            | 90002C       | 11         | MOB          | 3650            | 92013C         | 11           | NotPass   |
            | 90002C       | 11         | MOB          | 3650            | 92014C         | 11           | NotPass   |
            | 90002C       | 11         | MOB          | 3650            | 92015C         | 11           | NotPass   |
            # 測試不同牙
            | 90002C       | 11         | MOB          | 3650            | 92013C         | 51           | Pass      |
            | 90002C       | 11         | MOB          | 3650            | 92014C         | 51           | Pass      |
            | 90002C       | 11         | MOB          | 3650            | 92015C         | 51           | Pass      |

    Scenario Outline: 檢查治療的牙位是否為 PERMANENT_TOOTH
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
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 檢查 <IssueTeeth> 牙位，依 PERMANENT_TOOTH 判定是否為核可牙位，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            # 乳牙
            | 90002C       | 51         | DL           | NotPass   |
            | 90002C       | 52         | DL           | NotPass   |
            | 90002C       | 53         | DL           | NotPass   |
            | 90002C       | 54         | DL           | NotPass   |
            | 90002C       | 55         | DL           | NotPass   |
            | 90002C       | 61         | DL           | NotPass   |
            | 90002C       | 62         | DL           | NotPass   |
            | 90002C       | 63         | DL           | NotPass   |
            | 90002C       | 64         | DL           | NotPass   |
            | 90002C       | 65         | DL           | NotPass   |
            | 90002C       | 71         | DL           | NotPass   |
            | 90002C       | 72         | DL           | NotPass   |
            | 90002C       | 73         | DL           | NotPass   |
            | 90002C       | 74         | DL           | NotPass   |
            | 90002C       | 75         | DL           | NotPass   |
            | 90002C       | 81         | DL           | NotPass   |
            | 90002C       | 82         | DL           | NotPass   |
            | 90002C       | 83         | DL           | NotPass   |
            | 90002C       | 84         | DL           | NotPass   |
            | 90002C       | 85         | DL           | NotPass   |
            # 恆牙
            | 90002C       | 11         | DL           | Pass      |
            | 90002C       | 12         | DL           | Pass      |
            | 90002C       | 13         | DL           | Pass      |
            | 90002C       | 14         | DL           | Pass      |
            | 90002C       | 15         | DL           | Pass      |
            | 90002C       | 16         | DL           | Pass      |
            | 90002C       | 17         | DL           | Pass      |
            | 90002C       | 18         | DL           | Pass      |
            | 90002C       | 21         | DL           | Pass      |
            | 90002C       | 22         | DL           | Pass      |
            | 90002C       | 23         | DL           | Pass      |
            | 90002C       | 24         | DL           | Pass      |
            | 90002C       | 25         | DL           | Pass      |
            | 90002C       | 26         | DL           | Pass      |
            | 90002C       | 27         | DL           | Pass      |
            | 90002C       | 28         | DL           | Pass      |
            | 90002C       | 31         | DL           | Pass      |
            | 90002C       | 32         | DL           | Pass      |
            | 90002C       | 33         | DL           | Pass      |
            | 90002C       | 34         | DL           | Pass      |
            | 90002C       | 35         | DL           | Pass      |
            | 90002C       | 36         | DL           | Pass      |
            | 90002C       | 37         | DL           | Pass      |
            | 90002C       | 38         | DL           | Pass      |
            | 90002C       | 41         | DL           | Pass      |
            | 90002C       | 42         | DL           | Pass      |
            | 90002C       | 43         | DL           | Pass      |
            | 90002C       | 44         | DL           | Pass      |
            | 90002C       | 45         | DL           | Pass      |
            | 90002C       | 46         | DL           | Pass      |
            | 90002C       | 47         | DL           | Pass      |
            | 90002C       | 48         | DL           | Pass      |
            # 無牙
            | 90002C       |            | DL           | NotPass   |
            #
            | 90002C       | 19         | DL           | Pass      |
            | 90002C       | 29         | DL           | Pass      |
            | 90002C       | 39         | DL           | Pass      |
            | 90002C       | 49         | DL           | Pass      |
            | 90002C       | 59         | DL           | NotPass   |
            | 90002C       | 69         | DL           | NotPass   |
            | 90002C       | 79         | DL           | NotPass   |
            | 90002C       | 89         | DL           | NotPass   |
            | 90002C       | 99         | DL           | Pass      |
            # 牙位為區域型態
            | 90002C       | FM         | DL           | NotPass   |
            | 90002C       | UR         | DL           | NotPass   |
            | 90002C       | UL         | DL           | NotPass   |
            | 90002C       | UA         | DL           | NotPass   |
            | 90002C       | UB         | DL           | NotPass   |
            | 90002C       | LR         | DL           | NotPass   |
            | 90002C       | LL         | DL           | NotPass   |
            | 90002C       | LA         | DL           | NotPass   |
            | 90002C       | LB         | DL           | NotPass   |
            # 非法牙位
            | 90002C       | 00         | DL           | NotPass   |
            | 90002C       | 01         | DL           | NotPass   |
            | 90002C       | 10         | DL           | NotPass   |
            | 90002C       | 56         | DL           | NotPass   |
            | 90002C       | 66         | DL           | NotPass   |
            | 90002C       | 76         | DL           | NotPass   |
            | 90002C       | 86         | DL           | NotPass   |
            | 90002C       | 91         | DL           | NotPass   |

    Scenario Outline: （HIS）60 天內，曾經有 90015C 治療項目
        Given 建立醫師
        Given Wind 24 歲病人
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
        Then 檢查 60 天內，應有 <TreatmentNhiCode> 診療項目存在，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastTreatmentDays | TreatmentNhiCode | TreatmentTeeth | PassOrNot |
            | 90002C       | 11         | MOB          | 59                | 90015C           | 11             | Pass      |
            | 90002C       | 11         | MOB          | 60                | 90015C           | 11             | Pass      |
            | 90002C       | 11         | MOB          | 61                | 90015C           | 11             | NotPass   |

    Scenario Outline: （IC）60 天內，曾經有 90015C 治療項目
        Given 建立醫師
        Given Wind 24 歲病人
        Given 新增健保醫療:
            | PastDays          | NhiCode          | Teeth          |
            | <PastMedicalDays> | <MedicalNhiCode> | <MedicalTeeth> |
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 檢查 60 天內，應有 <MedicalNhiCode> 診療項目存在，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastMedicalDays | MedicalNhiCode | MedicalTeeth | PassOrNot |
            | 90002C       | 11         | MOB          | 59              | 90015C         | 11           | Pass      |
            | 90002C       | 11         | MOB          | 60              | 90015C         | 11           | Pass      |
            | 90002C       | 11         | MOB          | 61              | 90015C         | 11           | NotPass   |

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
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 提醒"須檢附影像"，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            | 90002C       | 11         | MOB          | Pass      |

    Scenario Outline: （HIS）病患牙齒是否有 90002C 健保代碼於60天內已被申報過
        Given 建立醫師
        Given Wind 24 歲病人
        Given 在過去第 60 天，建立預約
        Given 在過去第 60 天，建立掛號
        Given 在過去第 60 天，產生診療計畫
        And 新增診療代碼:
            | PastDays | A72 | A73    | A74 | A75 | A76 | A77 | A78 | A79 |
            | 60       | 3   | 90015C | 11  | MOB | 0   | 1.0 | 03  |     |
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
        Then 病患的牙齒 <TreatmentTeeth> 在 <PastTreatmentDays> 天前，被申報 <TreatmentNhiCode> 健保代碼，而現在病患的牙齒 <IssueTeeth> 要被申報 <IssueNhiCode> 健保代碼，是否抵觸同顆牙齒在 <DayRange> 天內不得申報指定健保代碼，確認主要結果是否為 <PassOrNot> 和細項結果是否為 <InnerPassOrNot> 且檢查訊息類型為 D7_2
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | DayRange | PastTreatmentDays | TreatmentNhiCode | TreatmentTeeth | PassOrNot | InnerPassOrNot |
            # 恆牙
            | 90002C       | 11         | MOB          | 60       | 0                 | 90002C           | 11             | NotPass   | NotPass        |
            | 90002C       | 11         | MOB          | 60       | 60                | 90002C           | 11             | NotPass   | NotPass        |
            | 90002C       | 11         | MOB          | 60       | 61                | 90002C           | 11             | NotPass   | Pass           |
            # 申報的牙齒與欲檢查的牙齒不同顆
            | 90002C       | 11         | MOB          | 60       | 0                 | 90002C           | 14             | Pass      | Pass           |
            | 90002C       | 11         | MOB          | 60       | 60                | 90002C           | 14             | Pass      | Pass           |
            | 90002C       | 11         | MOB          | 60       | 61                | 90002C           | 14             | Pass      | Pass           |

    Scenario Outline: （IC）病患牙齒是否有 90002C 健保代碼於60天內已被申報過
        Given 建立醫師
        Given Wind 24 歲病人
        Given 在過去第 60 天，建立預約
        Given 在過去第 60 天，建立掛號
        Given 在過去第 60 天，產生診療計畫
        And 新增診療代碼:
            | PastDays | A72 | A73    | A74 | A75 | A76 | A77 | A78 | A79 |
            | 60       | 3   | 90015C | 11  | MOB | 0   | 1.0 | 03  |     |
        Given 新增健保醫療:
            | PastDays          | NhiCode          | Teeth          |
            | <PastMedicalDays> | <MedicalNhiCode> | <MedicalTeeth> |
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 病患的牙齒 <MedicalTeeth> 在 <PastMedicalDays> 天前，被申報 <MedicalNhiCode> 健保代碼，而現在病患的牙齒 <IssueTeeth> 要被申報 <IssueNhiCode> 健保代碼，是否抵觸同顆牙齒在 <DayRange> 天內不得申報指定健保代碼，確認主要結果是否為 <PassOrNot> 和細項結果是否為 <InnerPassOrNot> 且檢查訊息類型為 D7_2
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | DayRange | PastMedicalDays | MedicalNhiCode | MedicalTeeth | PassOrNot | InnerPassOrNot |
            # 恆牙
            | 90002C       | 11         | MOB          | 60       | 0               | 90002C         | 11           | NotPass   | NotPass        |
            | 90002C       | 11         | MOB          | 60       | 60              | 90002C         | 11           | NotPass   | NotPass        |
            | 90002C       | 11         | MOB          | 60       | 61              | 90002C         | 11           | NotPass   | Pass           |
            # 申報的牙齒與欲檢查的牙齒不同顆
            | 90002C       | 11         | MOB          | 60       | 0               | 90002C         | 14           | Pass      | Pass           |
            | 90002C       | 11         | MOB          | 60       | 60              | 90002C         | 14           | Pass      | Pass           |
            | 90002C       | 11         | MOB          | 60       | 61              | 90002C         | 14           | Pass      | Pass           |

    Scenario Outline: （HIS）病患牙齒是否有 90001C~90003C/90019C~90020C 健保代碼於90天前已被申報過
        Given 建立醫師
        Given Wind 24 歲病人
        Given 在過去第 60 天，建立預約
        Given 在過去第 60 天，建立掛號
        Given 在過去第 60 天，產生診療計畫
        And 新增診療代碼:
            | PastDays | A72 | A73    | A74 | A75 | A76 | A77 | A78 | A79 |
            | 60       | 3   | 90015C | 11  | MOB | 0   | 1.0 | 03  |     |
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
        Then 病患的牙齒 <TreatmentTeeth> 在 <PastTreatmentDays> 天前，被申報 <TreatmentNhiCode> 健保代碼，而現在病患的牙齒 <IssueTeeth> 要被申報 <IssueNhiCode> 健保代碼，是否抵觸同顆牙齒在 <DayRange> 天內不得申報指定健保代碼，確認主要結果是否為 <PassOrNot> 和細項結果是否為 <InnerPassOrNot> 且檢查訊息類型為 W6_1
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | DayRange | PastTreatmentDays | TreatmentNhiCode | TreatmentTeeth | PassOrNot | InnerPassOrNot |
            # 恆牙
            | 90002C       | 11         | MOB          | 90       | 0                 | 90001C           | 11             | NotPass   | NotPass        |
            | 90002C       | 11         | MOB          | 90       | 89                | 90001C           | 11             | NotPass   | NotPass        |
            | 90002C       | 11         | MOB          | 90       | 90                | 90001C           | 11             | NotPass   | NotPass        |
            | 90002C       | 11         | MOB          | 90       | 91                | 90001C           | 11             | Pass      | Pass           |
            # 申報的牙齒與欲檢查的牙齒不同顆
            | 90002C       | 11         | MOB          | 90       | 0                 | 90001C           | 14             | Pass      | Pass           |
            | 90002C       | 11         | MOB          | 90       | 89                | 90001C           | 14             | Pass      | Pass           |
            | 90002C       | 11         | MOB          | 90       | 90                | 90001C           | 14             | Pass      | Pass           |
            | 90002C       | 11         | MOB          | 90       | 91                | 90001C           | 14             | Pass      | Pass           |
            # 測試其他指定健保代碼
            | 90002C       | 11         | MOB          | 90       | 90                | 90002C           | 11             | NotPass   | NotPass        |
            | 90002C       | 11         | MOB          | 90       | 90                | 90003C           | 11             | NotPass   | NotPass        |
            | 90002C       | 11         | MOB          | 90       | 90                | 90019C           | 11             | NotPass   | NotPass        |
            | 90002C       | 11         | MOB          | 90       | 90                | 90020C           | 11             | NotPass   | NotPass        |
            | 90002C       | 11         | MOB          | 90       | 91                | 90002C           | 11             | Pass      | Pass           |
            | 90002C       | 11         | MOB          | 90       | 91                | 90003C           | 11             | Pass      | Pass           |
            | 90002C       | 11         | MOB          | 90       | 91                | 90019C           | 11             | Pass      | Pass           |
            | 90002C       | 11         | MOB          | 90       | 91                | 90020C           | 11             | Pass      | Pass           |

    Scenario Outline: （IC）病患牙齒是否有 90001C~90003C/90019C~90020C 健保代碼於90天前已被申報過
        Given 建立醫師
        Given Wind 24 歲病人
        Given 在過去第 60 天，建立預約
        Given 在過去第 60 天，建立掛號
        Given 在過去第 60 天，產生診療計畫
        And 新增診療代碼:
            | PastDays | A72 | A73    | A74 | A75 | A76 | A77 | A78 | A79 |
            | 60       | 3   | 90015C | 11  | MOB | 0   | 1.0 | 03  |     |
        Given 新增健保醫療:
            | PastDays          | NhiCode          | Teeth          |
            | <PastMedicalDays> | <MedicalNhiCode> | <MedicalTeeth> |
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 病患的牙齒 <MedicalTeeth> 在 <PastMedicalDays> 天前，被申報 <MedicalNhiCode> 健保代碼，而現在病患的牙齒 <IssueTeeth> 要被申報 <IssueNhiCode> 健保代碼，是否抵觸同顆牙齒在 <DayRange> 天內不得申報指定健保代碼，確認主要結果是否為 <PassOrNot> 和細項結果是否為 <InnerPassOrNot> 且檢查訊息類型為 W6_1
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | DayRange | PastMedicalDays | MedicalNhiCode | MedicalTeeth | PassOrNot | InnerPassOrNot |
            # 恆牙
            | 90002C       | 11         | MOB          | 90       | 0               | 90001C         | 11           | NotPass   | NotPass        |
            | 90002C       | 11         | MOB          | 90       | 89              | 90001C         | 11           | NotPass   | NotPass        |
            | 90002C       | 11         | MOB          | 90       | 90              | 90001C         | 11           | NotPass   | NotPass        |
            | 90002C       | 11         | MOB          | 90       | 91              | 90001C         | 11           | Pass      | Pass           |
            # 申報的牙齒與欲檢查的牙齒不同顆
            | 90002C       | 11         | MOB          | 90       | 0               | 90001C         | 14           | Pass      | Pass           |
            | 90002C       | 11         | MOB          | 90       | 89              | 90001C         | 14           | Pass      | Pass           |
            | 90002C       | 11         | MOB          | 90       | 90              | 90001C         | 14           | Pass      | Pass           |
            | 90002C       | 11         | MOB          | 90       | 91              | 90001C         | 14           | Pass      | Pass           |
            # 測試其他指定健保代碼
            | 90002C       | 11         | MOB          | 90       | 90              | 90002C         | 11           | NotPass   | NotPass        |
            | 90002C       | 11         | MOB          | 90       | 90              | 90003C         | 11           | NotPass   | NotPass        |
            | 90002C       | 11         | MOB          | 90       | 90              | 90019C         | 11           | NotPass   | NotPass        |
            | 90002C       | 11         | MOB          | 90       | 90              | 90020C         | 11           | NotPass   | NotPass        |
            | 90002C       | 11         | MOB          | 90       | 91              | 90002C         | 11           | Pass      | Pass           |
            | 90002C       | 11         | MOB          | 90       | 91              | 90003C         | 11           | Pass      | Pass           |
            | 90002C       | 11         | MOB          | 90       | 91              | 90019C         | 11           | Pass      | Pass           |
            | 90002C       | 11         | MOB          | 90       | 91              | 90020C         | 11           | Pass      | Pass           |
