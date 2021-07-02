@nhi-89-series
Feature: 89015C 後牙雙鄰接面複合樹脂充填

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
            | 89015C       | 14         | MOBL         | Pass      |

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
            # 後乳牙
            | 89015C       | 54         | MOBL         | 365      | 0                 | 89001C           | 54             | NotPass   |
            | 89015C       | 54         | MOBL         | 365      | 364               | 89001C           | 54             | NotPass   |
            | 89015C       | 54         | MOBL         | 365      | 365               | 89001C           | 54             | NotPass   |
            | 89015C       | 54         | MOBL         | 365      | 366               | 89001C           | 54             | Pass      |
            # 後恆牙
            | 89015C       | 14         | MOBL         | 545      | 0                 | 89001C           | 14             | NotPass   |
            | 89015C       | 14         | MOBL         | 545      | 544               | 89001C           | 14             | NotPass   |
            | 89015C       | 14         | MOBL         | 545      | 545               | 89001C           | 14             | NotPass   |
            | 89015C       | 14         | MOBL         | 545      | 546               | 89001C           | 14             | Pass      |
            # 申報的牙齒與欲檢查的牙齒不同顆
            | 89015C       | 54         | MOBL         | 365      | 0                 | 89001C           | 64             | Pass      |
            | 89015C       | 54         | MOBL         | 365      | 364               | 89001C           | 64             | Pass      |
            | 89015C       | 54         | MOBL         | 365      | 365               | 89001C           | 64             | Pass      |
            | 89015C       | 14         | MOBL         | 545      | 0                 | 89001C           | 24             | Pass      |
            | 89015C       | 14         | MOBL         | 545      | 544               | 89001C           | 24             | Pass      |
            | 89015C       | 14         | MOBL         | 545      | 545               | 89001C           | 24             | Pass      |
            # 測試其他NhiCode
            | 89015C       | 54         | MOBL         | 365      | 365               | 89002C           | 54             | NotPass   |
            | 89015C       | 54         | MOBL         | 365      | 365               | 89003C           | 54             | NotPass   |
            | 89015C       | 54         | MOBL         | 365      | 365               | 89004C           | 54             | NotPass   |
            | 89015C       | 54         | MOBL         | 365      | 365               | 89005C           | 54             | NotPass   |
            | 89015C       | 54         | MOBL         | 365      | 365               | 89008C           | 54             | NotPass   |
            | 89015C       | 54         | MOBL         | 365      | 365               | 89009C           | 54             | NotPass   |
            | 89015C       | 54         | MOBL         | 365      | 365               | 89010C           | 54             | NotPass   |
            | 89015C       | 54         | MOBL         | 365      | 365               | 89011C           | 54             | NotPass   |
            | 89015C       | 54         | MOBL         | 365      | 365               | 89012C           | 54             | NotPass   |
            | 89015C       | 54         | MOBL         | 365      | 365               | 89014C           | 54             | NotPass   |
            | 89015C       | 54         | MOBL         | 365      | 365               | 89015C           | 54             | NotPass   |

    Scenario Outline: 限制牙面在 4 以上
        Given 建立醫師
        Given Wind 24 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 限制牙面在 4 以上，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            | 89015C       | 54         | MOBL         | Pass      |
            | 89015C       | 54         | MOB          | NotPass   |
            | 89015C       | 54         | DL           | NotPass   |
            | 89015C       | 54         | O            | NotPass   |
            | 89015C       | 54         |              | NotPass   |

    Scenario Outline: （HIS）未曾申報過 90007C 代碼
        Given 建立醫師
        Given Wind 24 歲病人
        Given 在過去第 <PastTreatmentDays> 天，建立預約
        Given 在過去第 <PastTreatmentDays> 天，建立掛號
        Given 在過去第 <PastTreatmentDays> 天，產生診療計畫
        And 新增診療代碼:
            | PastDays            | A72 | A73                | A74 | A75  | A76 | A77 | A78 | A79 |
            | <PastTreatmentDays> | 3   | <TreatmentNhiCode> | 54  | MOBL | 0   | 1.0 | 03  |     |
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 任意時間點未曾申報過指定代碼 <TreatmentNhiCode>，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastTreatmentDays | TreatmentNhiCode | PassOrNot |
            | 89015C       | 54         | MOBL         | 30                | 90007C           | NotPass   |
            | 89015C       | 54         | MOBL         | 30                | 01271C           | Pass      |

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
            | 89015C       | 54         | MOBL         | 30              | 90007C         | NotPass   |
            | 89015C       | 54         | MOBL         | 30              | 01271C         | Pass      |

    Scenario Outline: （HIS）前30天內不得有89006C，但如果這中間有90001C, 90002C, 90003C, 90019C, 90020C則例外
        Given 建立醫師
        Given Wind 24 歲病人
        Given 在過去第 <89006CTreatmentDay> 天，建立預約
        Given 在過去第 <89006CTreatmentDay> 天，建立掛號
        Given 在過去第 <89006CTreatmentDay> 天，產生診療計畫
        And 新增診療代碼:
            | PastDays             | A72 | A73    | A74 | A75  | A76 | A77 | A78 | A79 |
            | <89006CTreatmentDay> | 3   | 89006C | 54  | MOBL | 0   | 1.0 | 03  |     |
        Given 在過去第 <PastTreatmentDays> 天，建立預約
        Given 在過去第 <PastTreatmentDays> 天，建立掛號
        Given 在過去第 <PastTreatmentDays> 天，產生診療計畫
        And 新增診療代碼:
            | PastDays            | A72 | A73                | A74 | A75  | A76 | A77 | A78 | A79 |
            | <PastTreatmentDays> | 3   | <TreatmentNhiCode> | 54  | MOBL | 0   | 1.0 | 03  |     |
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
            | 89015C       | 54         | MOBL         | 15                | 01271C           | 30                 | NotPass   |
            # 測試89006C在超過30天前建立
            | 89015C       | 54         | MOBL         | 15                | 01271C           | 31                 | Pass      |
            # 測試TreatmentNhiCode是產生在89006C之後，但在30天內
            | 89015C       | 54         | MOBL         | 29                | 90001C           | 30                 | Pass      |
            | 89015C       | 54         | MOBL         | 29                | 90002C           | 30                 | Pass      |
            | 89015C       | 54         | MOBL         | 29                | 90003C           | 30                 | Pass      |
            | 89015C       | 54         | MOBL         | 29                | 90019C           | 30                 | Pass      |
            | 89015C       | 54         | MOBL         | 29                | 90020C           | 30                 | Pass      |
            # 測試TreatmentNhiCode是產生在89006C之前，但超過30天
            | 89015C       | 54         | MOBL         | 31                | 90001C           | 30                 | NotPass   |
            | 89015C       | 54         | MOBL         | 31                | 90002C           | 30                 | NotPass   |
            | 89015C       | 54         | MOBL         | 31                | 90003C           | 30                 | NotPass   |
            | 89015C       | 54         | MOBL         | 31                | 90019C           | 30                 | NotPass   |
            | 89015C       | 54         | MOBL         | 31                | 90020C           | 30                 | NotPass   |
            # 測試TreatmentNhiCode與89006C都在同一天
            | 89015C       | 54         | MOBL         | 30                | 90001C           | 30                 | NotPass   |
            | 89015C       | 54         | MOBL         | 30                | 90002C           | 30                 | NotPass   |
            | 89015C       | 54         | MOBL         | 30                | 90003C           | 30                 | NotPass   |
            | 89015C       | 54         | MOBL         | 30                | 90019C           | 30                 | NotPass   |
            | 89015C       | 54         | MOBL         | 30                | 90020C           | 30                 | NotPass   |

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
            | 89015C       | 14         | MOBL         | 15              | 01271C         | 30                    | NotPass   |
            # 測試89006C在超過30天前建立
            | 89015C       | 14         | MOBL         | 15              | 01271C         | 31                    | Pass      |
            # 測試TreatmentNhiCode是產生在89006C之後，但在30天內
            | 89015C       | 14         | MOBL         | 29              | 90001C         | 30                    | Pass      |
            | 89015C       | 14         | MOBL         | 29              | 90002C         | 30                    | Pass      |
            | 89015C       | 14         | MOBL         | 29              | 90003C         | 30                    | Pass      |
            | 89015C       | 14         | MOBL         | 29              | 90019C         | 30                    | Pass      |
            | 89015C       | 14         | MOBL         | 29              | 90020C         | 30                    | Pass      |
            # 測試TreatmentNhiCode是產生在89006C之前，但超過30天
            | 89015C       | 14         | MOBL         | 31              | 90001C         | 30                    | NotPass   |
            | 89015C       | 14         | MOBL         | 31              | 90002C         | 30                    | NotPass   |
            | 89015C       | 14         | MOBL         | 31              | 90003C         | 30                    | NotPass   |
            | 89015C       | 14         | MOBL         | 31              | 90019C         | 30                    | NotPass   |
            | 89015C       | 14         | MOBL         | 31              | 90020C         | 30                    | NotPass   |
            # 測試TreatmentNhiCode與89006C都在同一天
            | 89015C       | 14         | MOBL         | 30              | 90001C         | 30                    | NotPass   |
            | 89015C       | 14         | MOBL         | 30              | 90002C         | 30                    | NotPass   |
            | 89015C       | 14         | MOBL         | 30              | 90003C         | 30                    | NotPass   |
            | 89015C       | 14         | MOBL         | 30              | 90019C         | 30                    | NotPass   |
            | 89015C       | 14         | MOBL         | 30              | 90020C         | 30                    | NotPass   |

    Scenario Outline: （HIS）同牙未曾申報過 92013C~92015C
        Given 建立醫師
        Given Wind 24 歲病人
        Given 在過去第 <PastTreatmentDays> 天，建立預約
        Given 在過去第 <PastTreatmentDays> 天，建立掛號
        Given 在過去第 <PastTreatmentDays> 天，產生診療計畫
        And 新增診療代碼:
            | PastDays            | A72 | A73                | A74              | A75  | A76 | A77 | A78 | A79 |
            | <PastTreatmentDays> | 3   | <TreatmentNhiCode> | <TreatmentTeeth> | MOBL | 0   | 1.0 | 03  |     |
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
            | 89015C       | 54         | MOBL         | 3650              | 92013C           | 54             | NotPass   |
            | 89015C       | 54         | MOBL         | 3650              | 92014C           | 54             | NotPass   |
            | 89015C       | 54         | MOBL         | 3650              | 92015C           | 54             | NotPass   |
            # 測試不同牙
            | 89015C       | 54         | MOBL         | 3650              | 92013C           | 55             | Pass      |
            | 89015C       | 54         | MOBL         | 3650              | 92014C           | 55             | Pass      |
            | 89015C       | 54         | MOBL         | 3650              | 92015C           | 55             | Pass      |

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
            | 89015C       | 54         | MOBL         | 3650            | 92013C         | 54             | NotPass   |
            | 89015C       | 54         | MOBL         | 3650            | 92014C         | 54             | NotPass   |
            | 89015C       | 54         | MOBL         | 3650            | 92015C         | 54             | NotPass   |
            # 測試不同牙
            | 89015C       | 54         | MOBL         | 3650            | 92013C         | 55             | Pass      |
            | 89015C       | 54         | MOBL         | 3650            | 92014C         | 55             | Pass      |
            | 89015C       | 54         | MOBL         | 3650            | 92015C         | 55             | Pass      |

    Scenario Outline: 檢查治療的牙位是否為 BACK_TOOTH_EXCLUDE_WISDOM_TOOTH
        Given 建立醫師
        Given Wind 24 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 檢查 <IssueTeeth> 牙位，依 BACK_TOOTH_EXCLUDE_WISDOM_TOOTH 判定是否為核可牙位，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            # 乳牙
            | 89015C       | 51         | MOBL         | NotPass   |
            | 89015C       | 52         | MOBL         | NotPass   |
            | 89015C       | 53         | MOBL         | NotPass   |
            | 89015C       | 54         | MOBL         | Pass      |
            | 89015C       | 55         | MOBL         | Pass      |
            | 89015C       | 61         | MOBL         | NotPass   |
            | 89015C       | 62         | MOBL         | NotPass   |
            | 89015C       | 63         | MOBL         | NotPass   |
            | 89015C       | 64         | MOBL         | Pass      |
            | 89015C       | 65         | MOBL         | Pass      |
            | 89015C       | 71         | MOBL         | NotPass   |
            | 89015C       | 72         | MOBL         | NotPass   |
            | 89015C       | 73         | MOBL         | NotPass   |
            | 89015C       | 74         | MOBL         | Pass      |
            | 89015C       | 75         | MOBL         | Pass      |
            | 89015C       | 81         | MOBL         | NotPass   |
            | 89015C       | 82         | MOBL         | NotPass   |
            | 89015C       | 83         | MOBL         | NotPass   |
            | 89015C       | 84         | MOBL         | Pass      |
            | 89015C       | 85         | MOBL         | Pass      |
            # 恆牙
            | 89015C       | 11         | MOBL         | NotPass   |
            | 89015C       | 12         | MOBL         | NotPass   |
            | 89015C       | 13         | MOBL         | NotPass   |
            | 89015C       | 14         | MOBL         | Pass      |
            | 89015C       | 15         | MOBL         | Pass      |
            | 89015C       | 16         | MOBL         | Pass      |
            | 89015C       | 17         | MOBL         | Pass      |
            | 89015C       | 18         | MOBL         | NotPass   |
            | 89015C       | 21         | MOBL         | NotPass   |
            | 89015C       | 22         | MOBL         | NotPass   |
            | 89015C       | 23         | MOBL         | NotPass   |
            | 89015C       | 24         | MOBL         | Pass      |
            | 89015C       | 25         | MOBL         | Pass      |
            | 89015C       | 26         | MOBL         | Pass      |
            | 89015C       | 27         | MOBL         | Pass      |
            | 89015C       | 28         | MOBL         | NotPass   |
            | 89015C       | 31         | MOBL         | NotPass   |
            | 89015C       | 32         | MOBL         | NotPass   |
            | 89015C       | 33         | MOBL         | NotPass   |
            | 89015C       | 34         | MOBL         | Pass      |
            | 89015C       | 35         | MOBL         | Pass      |
            | 89015C       | 36         | MOBL         | Pass      |
            | 89015C       | 37         | MOBL         | Pass      |
            | 89015C       | 38         | MOBL         | NotPass   |
            | 89015C       | 41         | MOBL         | NotPass   |
            | 89015C       | 42         | MOBL         | NotPass   |
            | 89015C       | 43         | MOBL         | NotPass   |
            | 89015C       | 44         | MOBL         | Pass      |
            | 89015C       | 45         | MOBL         | Pass      |
            | 89015C       | 46         | MOBL         | Pass      |
            | 89015C       | 47         | MOBL         | Pass      |
            | 89015C       | 48         | MOBL         | NotPass   |
            # 無牙
            | 89015C       |            | MOBL         | NotPass   |
            #
            | 89015C       | 19         | MOBL         | Pass      |
            | 89015C       | 29         | MOBL         | Pass      |
            | 89015C       | 39         | MOBL         | Pass      |
            | 89015C       | 49         | MOBL         | Pass      |
            | 89015C       | 59         | MOBL         | NotPass   |
            | 89015C       | 69         | MOBL         | NotPass   |
            | 89015C       | 79         | MOBL         | NotPass   |
            | 89015C       | 89         | MOBL         | NotPass   |
            | 89015C       | 99         | MOBL         | Pass      |
            # 牙位為區域型態
            | 89015C       | FM         | MOBL         | NotPass   |
            | 89015C       | UR         | MOBL         | NotPass   |
            | 89015C       | UL         | MOBL         | NotPass   |
            | 89015C       | LL         | MOBL         | NotPass   |
            | 89015C       | LR         | MOBL         | NotPass   |
            | 89015C       | UA         | MOBL         | NotPass   |
            | 89015C       | LA         | MOBL         | NotPass   |
            # 非法牙位
            | 89015C       | 00         | MOBL         | NotPass   |
            | 89015C       | 01         | MOBL         | NotPass   |
            | 89015C       | 10         | MOBL         | NotPass   |
            | 89015C       | 56         | MOBL         | NotPass   |
            | 89015C       | 66         | MOBL         | NotPass   |
            | 89015C       | 76         | MOBL         | NotPass   |
            | 89015C       | 86         | MOBL         | NotPass   |
            | 89015C       | 91         | MOBL         | NotPass   |

