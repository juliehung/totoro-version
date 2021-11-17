@nhi @nhi-90-series @part2
Feature: 90015C 根管開擴及清創

    Scenario Outline: 全部檢核成功
        Given 建立醫師
        Given Wind 24 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 確認診療代碼 <IssueNhiCode> ，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            | 90015C       | 11         | MOB          | Pass      |

    Scenario Outline: 提醒須檢附影像
        Given 建立醫師
        Given Wind 24 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 提醒"須檢附影像"，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            | 90015C       | 11         | MOB          | Pass      |

    Scenario Outline: （HIS）60天內，同牙位不應有 90015C 診療項目
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
        Then 病患的牙齒 <TreatmentTeeth> 在 <PastTreatmentDays> 天前，被申報 <TreatmentNhiCode> 健保代碼，而現在病患的牙齒 <IssueTeeth> 要被申報 <IssueNhiCode> 健保代碼，是否抵觸同顆牙齒在 <GapDay> 天內不得申報指定健保代碼，確認結果是否為 <PassOrNot> 且檢查訊息類型為 D1_3
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastTreatmentDays | TreatmentNhiCode | TreatmentTeeth | GapDay | PassOrNot |
            # 同牙
            | 90015C       | 11         | DL           | 0                 | 90015C           | 11             | 60     | NotPass   |
            | 90015C       | 11         | DL           | 60                | 90015C           | 11             | 60     | NotPass   |
            | 90015C       | 11         | DL           | 61                | 90015C           | 11             | 60     | Pass      |
            # 不同牙
            | 90015C       | 11         | DL           | 0                 | 90015C           | 12             | 60     | Pass      |
            | 90015C       | 11         | DL           | 60                | 90015C           | 12             | 60     | Pass      |
            | 90015C       | 11         | DL           | 61                | 90015C           | 12             | 60     | Pass      |

    Scenario Outline: （IC）60天內，同牙位不應有 90015C 診療項目
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
        Then 病患的牙齒 <MedicalTeeth> 在 <PastMedicalDays> 天前，被申報 <MedicalNhiCode> 健保代碼，而現在病患的牙齒 <IssueTeeth> 要被申報 <IssueNhiCode> 健保代碼，是否抵觸同顆牙齒在 <GapDay> 天內不得申報指定健保代碼，確認結果是否為 <PassOrNot> 且檢查訊息類型為 D1_3
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastMedicalDays | MedicalNhiCode | MedicalTeeth | GapDay | PassOrNot |
            # 同牙
            | 90015C       | 11         | DL           | 0               | 90015C         | 11           | 60     | NotPass   |
            | 90015C       | 11         | DL           | 60              | 90015C         | 11           | 60     | NotPass   |
            | 90015C       | 11         | DL           | 61              | 90015C         | 11           | 60     | Pass      |
            # 不同牙
            | 90015C       | 11         | DL           | 0               | 90015C         | 12           | 60     | Pass      |
            | 90015C       | 11         | DL           | 60              | 90015C         | 12           | 60     | Pass      |
            | 90015C       | 11         | DL           | 61              | 90015C         | 12           | 60     | Pass      |

    Scenario Outline: （HIS）60天內，同牙位不應有 90005C 診療項目
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
        Then 病患的牙齒 <TreatmentTeeth> 在 <PastTreatmentDays> 天前，被申報 <TreatmentNhiCode> 健保代碼，而現在病患的牙齒 <IssueTeeth> 要被申報 <IssueNhiCode> 健保代碼，是否抵觸同顆牙齒在 <DayRange> 天內不得申報指定健保代碼，確認結果是否為 <PassOrNot> 且檢查訊息類型為 D1_3
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastTreatmentDays | TreatmentNhiCode | TreatmentTeeth | DayRange | PassOrNot |
            # 同牙
            | 90015C       | 11         | DL           | 0                 | 90005C           | 11             | 60     | NotPass   |
            | 90015C       | 11         | DL           | 60                | 90005C           | 11             | 60     | NotPass   |
            | 90015C       | 11         | DL           | 61                | 90005C           | 11             | 60     | Pass      |
            # 不同牙
            | 90015C       | 11         | DL           | 0                 | 90005C           | 12             | 60     | Pass      |
            | 90015C       | 11         | DL           | 60                | 90005C           | 12             | 60     | Pass      |
            | 90015C       | 11         | DL           | 61                | 90005C           | 12             | 60     | Pass      |

    Scenario Outline: （IC）60天內，同牙位不應有 90005C 診療項目
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
        Then 病患的牙齒 <MedicalTeeth> 在 <PastMedicalDays> 天前，被申報 <MedicalNhiCode> 健保代碼，而現在病患的牙齒 <IssueTeeth> 要被申報 <IssueNhiCode> 健保代碼，是否抵觸同顆牙齒在 <DayRange> 天內不得申報指定健保代碼，確認結果是否為 <PassOrNot> 且檢查訊息類型為 D1_3
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastMedicalDays | MedicalNhiCode | MedicalTeeth | DayRange | PassOrNot |
            # 同牙
            | 90015C       | 11         | DL           | 0               | 90005C         | 11           | 60     | NotPass   |
            | 90015C       | 11         | DL           | 60              | 90005C         | 11           | 60     | NotPass   |
            | 90015C       | 11         | DL           | 61              | 90005C         | 11           | 60     | Pass      |
            # 不同牙
            | 90015C       | 11         | DL           | 0               | 90005C         | 12           | 60     | Pass      |
            | 90015C       | 11         | DL           | 60              | 90005C         | 12           | 60     | Pass      |
            | 90015C       | 11         | DL           | 61              | 90005C         | 12           | 60     | Pass      |

    Scenario Outline: 檢查治療的牙位是否為 GENERAL_TOOTH
        Given 建立醫師
        Given Wind 24 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 檢查 <IssueTeeth> 牙位，依 GENERAL_TOOTH 判定是否為核可牙位，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            # 乳牙
            | 90015C       | 51         | DL           | Pass      |
            | 90015C       | 52         | DL           | Pass      |
            | 90015C       | 53         | DL           | Pass      |
            | 90015C       | 54         | DL           | Pass      |
            | 90015C       | 55         | DL           | Pass      |
            | 90015C       | 61         | DL           | Pass      |
            | 90015C       | 62         | DL           | Pass      |
            | 90015C       | 63         | DL           | Pass      |
            | 90015C       | 64         | DL           | Pass      |
            | 90015C       | 65         | DL           | Pass      |
            | 90015C       | 71         | DL           | Pass      |
            | 90015C       | 72         | DL           | Pass      |
            | 90015C       | 73         | DL           | Pass      |
            | 90015C       | 74         | DL           | Pass      |
            | 90015C       | 75         | DL           | Pass      |
            | 90015C       | 81         | DL           | Pass      |
            | 90015C       | 82         | DL           | Pass      |
            | 90015C       | 83         | DL           | Pass      |
            | 90015C       | 84         | DL           | Pass      |
            | 90015C       | 85         | DL           | Pass      |
            # 恆牙
            | 90015C       | 11         | DL           | Pass      |
            | 90015C       | 12         | DL           | Pass      |
            | 90015C       | 13         | DL           | Pass      |
            | 90015C       | 14         | DL           | Pass      |
            | 90015C       | 15         | DL           | Pass      |
            | 90015C       | 16         | DL           | Pass      |
            | 90015C       | 17         | DL           | Pass      |
            | 90015C       | 18         | DL           | Pass      |
            | 90015C       | 21         | DL           | Pass      |
            | 90015C       | 22         | DL           | Pass      |
            | 90015C       | 23         | DL           | Pass      |
            | 90015C       | 24         | DL           | Pass      |
            | 90015C       | 25         | DL           | Pass      |
            | 90015C       | 26         | DL           | Pass      |
            | 90015C       | 27         | DL           | Pass      |
            | 90015C       | 28         | DL           | Pass      |
            | 90015C       | 31         | DL           | Pass      |
            | 90015C       | 32         | DL           | Pass      |
            | 90015C       | 33         | DL           | Pass      |
            | 90015C       | 34         | DL           | Pass      |
            | 90015C       | 35         | DL           | Pass      |
            | 90015C       | 36         | DL           | Pass      |
            | 90015C       | 37         | DL           | Pass      |
            | 90015C       | 38         | DL           | Pass      |
            | 90015C       | 41         | DL           | Pass      |
            | 90015C       | 42         | DL           | Pass      |
            | 90015C       | 43         | DL           | Pass      |
            | 90015C       | 44         | DL           | Pass      |
            | 90015C       | 45         | DL           | Pass      |
            | 90015C       | 46         | DL           | Pass      |
            | 90015C       | 47         | DL           | Pass      |
            | 90015C       | 48         | DL           | Pass      |
            # 無牙
            | 90015C       |            | DL           | NotPass   |
            #
            | 90015C       | 19         | DL           | Pass      |
            | 90015C       | 29         | DL           | Pass      |
            | 90015C       | 39         | DL           | Pass      |
            | 90015C       | 49         | DL           | Pass      |
            | 90015C       | 59         | DL           | NotPass   |
            | 90015C       | 69         | DL           | NotPass   |
            | 90015C       | 79         | DL           | NotPass   |
            | 90015C       | 89         | DL           | NotPass   |
            | 90015C       | 99         | DL           | Pass      |
            # 牙位為區域型態
            | 90015C       | FM         | DL           | NotPass   |
            | 90015C       | UR         | DL           | NotPass   |
            | 90015C       | UL         | DL           | NotPass   |
            | 90015C       | UA         | DL           | NotPass   |
            | 90015C       | UB         | DL           | NotPass   |
            | 90015C       | LL         | DL           | NotPass   |
            | 90015C       | LR         | DL           | NotPass   |
            | 90015C       | LA         | DL           | NotPass   |
            | 90015C       | LB         | DL           | NotPass   |
            # 非法牙位
            | 90015C       | 00         | DL           | NotPass   |
            | 90015C       | 01         | DL           | NotPass   |
            | 90015C       | 10         | DL           | NotPass   |
            | 90015C       | 56         | DL           | NotPass   |
            | 90015C       | 66         | DL           | NotPass   |
            | 90015C       | 76         | DL           | NotPass   |
            | 90015C       | 86         | DL           | NotPass   |
            | 90015C       | 91         | DL           | NotPass   |
