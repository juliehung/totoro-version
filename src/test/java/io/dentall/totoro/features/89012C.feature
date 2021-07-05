@nhi-89-series
Feature: 89012C 前牙三面複合樹脂充填

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
            | 89012C       | 11         | MOB          | Pass      |

    Scenario Outline: （HIS）病患牙齒是否有特定健保代碼於365天(乳牙)/545天(恆牙)前已被申報過
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
        Then 病患的牙齒 <TreatmentTeeth> 在 <PastTreatmentDays> 天前，被申報 <TreatmentNhiCode> 健保代碼，而現在病患的牙齒 <IssueTeeth> 要被申報 <IssueNhiCode> 健保代碼，是否抵觸同顆牙齒在 <DayRange> 天內不得申報指定健保代碼，確認結果是否為 <PassOrNot> 且檢查訊息類型為 D1_2
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | DayRange | PastTreatmentDays | TreatmentNhiCode | TreatmentTeeth | PassOrNot |
            # 前乳牙
            | 89012C       | 51         | MOB          | 365      | 0                 | 89001C           | 51             | NotPass   |
            | 89012C       | 51         | MOB          | 365      | 364               | 89001C           | 51             | NotPass   |
            | 89012C       | 51         | MOB          | 365      | 365               | 89001C           | 51             | NotPass   |
            | 89012C       | 51         | MOB          | 365      | 366               | 89001C           | 51             | Pass      |
            # 前恆牙
            | 89012C       | 11         | MOB          | 545      | 0                 | 89001C           | 11             | NotPass   |
            | 89012C       | 11         | MOB          | 545      | 544               | 89001C           | 11             | NotPass   |
            | 89012C       | 11         | MOB          | 545      | 545               | 89001C           | 11             | NotPass   |
            | 89012C       | 11         | MOB          | 545      | 546               | 89001C           | 11             | Pass      |
            # 申報的牙齒與欲檢查的牙齒不同顆
            | 89012C       | 51         | MOB          | 365      | 0                 | 89001C           | 61             | Pass      |
            | 89012C       | 51         | MOB          | 365      | 364               | 89001C           | 61             | Pass      |
            | 89012C       | 51         | MOB          | 365      | 365               | 89001C           | 61             | Pass      |
            | 89012C       | 11         | MOB          | 545      | 0                 | 89001C           | 21             | Pass      |
            | 89012C       | 11         | MOB          | 545      | 544               | 89001C           | 21             | Pass      |
            | 89012C       | 11         | MOB          | 545      | 545               | 89001C           | 21             | Pass      |
            # 測試其他NhiCode
            | 89012C       | 51         | MOB          | 365      | 365               | 89002C           | 51             | NotPass   |
            | 89012C       | 51         | MOB          | 365      | 365               | 89003C           | 51             | NotPass   |
            | 89012C       | 51         | MOB          | 365      | 365               | 89004C           | 51             | NotPass   |
            | 89012C       | 51         | MOB          | 365      | 365               | 89005C           | 51             | NotPass   |
            | 89012C       | 51         | MOB          | 365      | 365               | 89008C           | 51             | NotPass   |
            | 89012C       | 51         | MOB          | 365      | 365               | 89009C           | 51             | NotPass   |
            | 89012C       | 51         | MOB          | 365      | 365               | 89010C           | 51             | NotPass   |
            | 89012C       | 51         | MOB          | 365      | 365               | 89011C           | 51             | NotPass   |
            | 89012C       | 51         | MOB          | 365      | 365               | 89012C           | 51             | NotPass   |
            | 89012C       | 51         | MOB          | 365      | 365               | 89014C           | 51             | NotPass   |
            | 89012C       | 51         | MOB          | 365      | 365               | 89015C           | 51             | NotPass   |

    Scenario Outline: 限制牙面在 3 以上
        Given 建立醫師
        Given Wind 24 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 限制牙面在 3 以上，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            | 89012C       | 11         | MOB          | Pass      |
            | 89012C       | 11         | DL           | NotPass   |
            | 89012C       | 11         | O            | NotPass   |
            | 89012C       | 11         |              | NotPass   |

    Scenario Outline: （HIS）未曾申報過 90007C 代碼
        Given 建立醫師
        Given Wind 24 歲病人
        Given 在過去第 <PastTreatmentDays> 天，建立預約
        Given 在過去第 <PastTreatmentDays> 天，建立掛號
        Given 在過去第 <PastTreatmentDays> 天，產生診療計畫
        And 新增診療代碼:
            | PastDays            | A72 | A73                | A74 | A75 | A76 | A77 | A78 | A79 |
            | <PastTreatmentDays> | 3   | <TreatmentNhiCode> | 11  | MOB | 0   | 1.0 | 03  |     |
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 任意時間點未曾申報過指定代碼 <TreatmentNhiCode>，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastTreatmentDays | TreatmentNhiCode | PassOrNot |
            | 89012C       | 11         | MOB          | 30                | 90007C           | NotPass   |
            | 89012C       | 11         | MOB          | 30                | 01271C           | Pass      |

    Scenario Outline: （IC）未曾申報過 90007C 代碼
        Given 建立醫師
        Given Wind 24 歲病人
        Given 新增健保醫療:
            | PastDays          | NhiCode          | Teeth |
            | <PastMedicalDays> | <MedicalNhiCode> | 11    |
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 任意時間點未曾申報過指定代碼 <MedicalNhiCode>，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastMedicalDays | MedicalNhiCode | PassOrNot |
            | 89012C       | 11         | MOB          | 30              | 90007C         | NotPass   |
            | 89012C       | 11         | MOB          | 30              | 01271C         | Pass      |

    Scenario Outline: （HIS）前30天內不得有89006C，但如果這中間有90001C, 90002C, 90003C, 90019C, 90020C則例外
        Given 建立醫師
        Given Wind 24 歲病人
        Given 在過去第 <89006CTreatmentDay> 天，建立預約
        Given 在過去第 <89006CTreatmentDay> 天，建立掛號
        Given 在過去第 <89006CTreatmentDay> 天，產生診療計畫
        And 新增診療代碼:
            | PastDays             | A72 | A73    | A74 | A75 | A76 | A77 | A78 | A79 |
            | <89006CTreatmentDay> | 3   | 89006C | 11  | MOB | 0   | 1.0 | 03  |     |
        Given 在過去第 <PastTreatmentDays> 天，建立預約
        Given 在過去第 <PastTreatmentDays> 天，建立掛號
        Given 在過去第 <PastTreatmentDays> 天，產生診療計畫
        And 新增診療代碼:
            | PastDays            | A72 | A73                | A74 | A75 | A76 | A77 | A78 | A79 |
            | <PastTreatmentDays> | 3   | <TreatmentNhiCode> | 11  | MOB | 0   | 1.0 | 03  |     |
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 89006C 在前 <89006CTreatmentDay> 天建立， <TreatmentNhiCode> 在前 <PastTreatmentDays> 天建立，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastTreatmentDays | TreatmentNhiCode | 89006CTreatmentDay | PassOrNot |
            # 測試TreatmentNhiCode為非指定代碼
            | 89012C       | 11         | MOB          | 15                | 01271C           | 30                 | NotPass   |
            # 測試89006C在超過30天前建立
            | 89012C       | 11         | MOB          | 15                | 01271C           | 31                 | Pass      |
            # 測試TreatmentNhiCode是產生在89006C之後，但在30天內
            | 89012C       | 11         | MOB          | 29                | 90001C           | 30                 | Pass      |
            | 89012C       | 11         | MOB          | 29                | 90002C           | 30                 | Pass      |
            | 89012C       | 11         | MOB          | 29                | 90003C           | 30                 | Pass      |
            | 89012C       | 11         | MOB          | 29                | 90019C           | 30                 | Pass      |
            | 89012C       | 11         | MOB          | 29                | 90020C           | 30                 | Pass      |
            # 測試TreatmentNhiCode是產生在89006C之前，但超過30天
            | 89012C       | 11         | MOB          | 31                | 90001C           | 30                 | NotPass   |
            | 89012C       | 11         | MOB          | 31                | 90002C           | 30                 | NotPass   |
            | 89012C       | 11         | MOB          | 31                | 90003C           | 30                 | NotPass   |
            | 89012C       | 11         | MOB          | 31                | 90019C           | 30                 | NotPass   |
            | 89012C       | 11         | MOB          | 31                | 90020C           | 30                 | NotPass   |
            # 測試TreatmentNhiCode與89006C都在同一天
            | 89012C       | 11         | MOB          | 30                | 90001C           | 30                 | NotPass   |
            | 89012C       | 11         | MOB          | 30                | 90002C           | 30                 | NotPass   |
            | 89012C       | 11         | MOB          | 30                | 90003C           | 30                 | NotPass   |
            | 89012C       | 11         | MOB          | 30                | 90019C           | 30                 | NotPass   |
            | 89012C       | 11         | MOB          | 30                | 90020C           | 30                 | NotPass   |

    Scenario Outline: （IC）前30天內不得有89006C，但如果這中間有90001C, 90002C, 90003C, 90019C, 90020C則例外
        Given 建立醫師
        Given Wind 24 歲病人
        Given 新增健保醫療:
            | PastDays                | NhiCode          | Teeth |
            | <89006CPastMedicalDays> | 89006C           | 14    |
            | <PastMedicalDays>       | <MedicalNhiCode> | 14    |
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 89006C 在前 <89006CPastMedicalDays> 天建立， <MedicalNhiCode> 在前 <PastMedicalDays> 天建立，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastMedicalDays | MedicalNhiCode | 89006CPastMedicalDays | PassOrNot |
            # 測試TreatmentNhiCode為非指定代碼
            | 89012C       | 11         | MOB          | 15              | 01271C         | 30                    | NotPass   |
            # 測試89006C在超過30天前建立
            | 89012C       | 11         | MOB          | 15              | 01271C         | 31                    | Pass      |
            # 測試TreatmentNhiCode是產生在89006C之後，但在30天內
            | 89012C       | 11         | MOB          | 29              | 90001C         | 30                    | Pass      |
            | 89012C       | 11         | MOB          | 29              | 90002C         | 30                    | Pass      |
            | 89012C       | 11         | MOB          | 29              | 90003C         | 30                    | Pass      |
            | 89012C       | 11         | MOB          | 29              | 90019C         | 30                    | Pass      |
            | 89012C       | 11         | MOB          | 29              | 90020C         | 30                    | Pass      |
            # 測試TreatmentNhiCode是產生在89006C之前，但超過30天
            | 89012C       | 11         | MOB          | 31              | 90001C         | 30                    | NotPass   |
            | 89012C       | 11         | MOB          | 31              | 90002C         | 30                    | NotPass   |
            | 89012C       | 11         | MOB          | 31              | 90003C         | 30                    | NotPass   |
            | 89012C       | 11         | MOB          | 31              | 90019C         | 30                    | NotPass   |
            | 89012C       | 11         | MOB          | 31              | 90020C         | 30                    | NotPass   |
            # 測試TreatmentNhiCode與89006C都在同一天
            | 89012C       | 11         | MOB          | 30              | 90001C         | 30                    | NotPass   |
            | 89012C       | 11         | MOB          | 30              | 90002C         | 30                    | NotPass   |
            | 89012C       | 11         | MOB          | 30              | 90003C         | 30                    | NotPass   |
            | 89012C       | 11         | MOB          | 30              | 90019C         | 30                    | NotPass   |
            | 89012C       | 11         | MOB          | 30              | 90020C         | 30                    | NotPass   |

    Scenario Outline: （HIS）同牙未曾申報過 92013C~92015C
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
        Then 同牙 <IssueTeeth> 未曾申報過，指定代碼 <TreatmentNhiCode> ，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastTreatmentDays | TreatmentNhiCode | TreatmentTeeth | PassOrNot |
            # 測試同牙
            | 89012C       | 11         | MOB          | 3650              | 92013C           | 11             | NotPass   |
            | 89012C       | 11         | MOB          | 3650              | 92014C           | 11             | NotPass   |
            | 89012C       | 11         | MOB          | 3650              | 92015C           | 11             | NotPass   |
            # 測試不同牙
            | 89012C       | 11         | MOB          | 3650              | 92013C           | 51             | Pass      |
            | 89012C       | 11         | MOB          | 3650              | 92014C           | 51             | Pass      |
            | 89012C       | 11         | MOB          | 3650              | 92015C           | 51             | Pass      |

    Scenario Outline: （IC）同牙未曾申報過 92013C~92015C
        Given 建立醫師
        Given Wind 24 歲病人
        Given 新增健保醫療:
            | PastDays          | NhiCode          | Teeth            |
            | <PastMedicalDays> | <MedicalNhiCode> | <TreatmentTeeth> |
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 同牙 <IssueTeeth> 未曾申報過，指定代碼 <MedicalNhiCode> ，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastMedicalDays | MedicalNhiCode | TreatmentTeeth | PassOrNot |
            # 測試同牙
            | 89012C       | 11         | MOB          | 3650            | 92013C         | 11             | NotPass   |
            | 89012C       | 11         | MOB          | 3650            | 92014C         | 11             | NotPass   |
            | 89012C       | 11         | MOB          | 3650            | 92015C         | 11             | NotPass   |
            # 測試不同牙
            | 89012C       | 11         | MOB          | 3650            | 92013C         | 51             | Pass      |
            | 89012C       | 11         | MOB          | 3650            | 92014C         | 51             | Pass      |
            | 89012C       | 11         | MOB          | 3650            | 92015C         | 51             | Pass      |

    Scenario Outline: 檢查治療的牙位是否為 FRONT_TOOTH
        Given 建立醫師
        Given Wind 24 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 檢查 <IssueTeeth> 牙位，依 FRONT_TOOTH 判定是否為核可牙位，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            # 乳牙
            | 89012C       | 51         | MOB          | Pass      |
            | 89012C       | 52         | MOB          | Pass      |
            | 89012C       | 53         | MOB          | Pass      |
            | 89012C       | 54         | MOB          | NotPass   |
            | 89012C       | 55         | MOB          | NotPass   |
            | 89012C       | 61         | MOB          | Pass      |
            | 89012C       | 62         | MOB          | Pass      |
            | 89012C       | 63         | MOB          | Pass      |
            | 89012C       | 64         | MOB          | NotPass   |
            | 89012C       | 65         | MOB          | NotPass   |
            | 89012C       | 71         | MOB          | Pass      |
            | 89012C       | 72         | MOB          | Pass      |
            | 89012C       | 73         | MOB          | Pass      |
            | 89012C       | 74         | MOB          | NotPass   |
            | 89012C       | 75         | MOB          | NotPass   |
            | 89012C       | 81         | MOB          | Pass      |
            | 89012C       | 82         | MOB          | Pass      |
            | 89012C       | 83         | MOB          | Pass      |
            | 89012C       | 84         | MOB          | NotPass   |
            | 89012C       | 85         | MOB          | NotPass   |
            # 恆牙
            | 89012C       | 11         | MOB          | Pass      |
            | 89012C       | 12         | MOB          | Pass      |
            | 89012C       | 13         | MOB          | Pass      |
            | 89012C       | 14         | MOB          | NotPass   |
            | 89012C       | 15         | MOB          | NotPass   |
            | 89012C       | 16         | MOB          | NotPass   |
            | 89012C       | 17         | MOB          | NotPass   |
            | 89012C       | 18         | MOB          | NotPass   |
            | 89012C       | 21         | MOB          | Pass      |
            | 89012C       | 22         | MOB          | Pass      |
            | 89012C       | 23         | MOB          | Pass      |
            | 89012C       | 24         | MOB          | NotPass   |
            | 89012C       | 25         | MOB          | NotPass   |
            | 89012C       | 26         | MOB          | NotPass   |
            | 89012C       | 27         | MOB          | NotPass   |
            | 89012C       | 28         | MOB          | NotPass   |
            | 89012C       | 31         | MOB          | Pass      |
            | 89012C       | 32         | MOB          | Pass      |
            | 89012C       | 33         | MOB          | Pass      |
            | 89012C       | 34         | MOB          | NotPass   |
            | 89012C       | 35         | MOB          | NotPass   |
            | 89012C       | 36         | MOB          | NotPass   |
            | 89012C       | 37         | MOB          | NotPass   |
            | 89012C       | 38         | MOB          | NotPass   |
            | 89012C       | 41         | MOB          | Pass      |
            | 89012C       | 42         | MOB          | Pass      |
            | 89012C       | 43         | MOB          | Pass      |
            | 89012C       | 44         | MOB          | NotPass   |
            | 89012C       | 45         | MOB          | NotPass   |
            | 89012C       | 46         | MOB          | NotPass   |
            | 89012C       | 47         | MOB          | NotPass   |
            | 89012C       | 48         | MOB          | NotPass   |
            # 無牙
            | 89012C       |            | MOB          | NotPass   |
            #
            | 89012C       | 19         | MOB          | Pass      |
            | 89012C       | 29         | MOB          | Pass      |
            | 89012C       | 39         | MOB          | Pass      |
            | 89012C       | 49         | MOB          | Pass      |
            | 89012C       | 59         | MOB          | NotPass   |
            | 89012C       | 69         | MOB          | NotPass   |
            | 89012C       | 79         | MOB          | NotPass   |
            | 89012C       | 89         | MOB          | NotPass   |
            | 89012C       | 99         | MOB          | Pass      |
            # 牙位為區域型態
            | 89012C       | FM         | MOB          | NotPass   |
            | 89012C       | UR         | MOB          | NotPass   |
            | 89012C       | UL         | MOB          | NotPass   |
            | 89012C       | UA         | MOB          | NotPass   |
            | 89012C       | UB         | MOB          | NotPass   |
            | 89012C       | LR         | MOB          | NotPass   |
            | 89012C       | LL         | MOB          | NotPass   |
            | 89012C       | LA         | MOB          | NotPass   |
            | 89012C       | LB         | MOB          | NotPass   |
            # 非法牙位
            | 89012C       | 00         | MOB          | NotPass   |
            | 89012C       | 01         | MOB          | NotPass   |
            | 89012C       | 10         | MOB          | NotPass   |
            | 89012C       | 56         | MOB          | NotPass   |
            | 89012C       | 66         | MOB          | NotPass   |
            | 89012C       | 76         | MOB          | NotPass   |
            | 89012C       | 86         | MOB          | NotPass   |
            | 89012C       | 91         | MOB          | NotPass   |

