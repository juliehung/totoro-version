@nhi @nhi-89-series @part2
Feature: 89008C 後牙複合樹脂充填-單面

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
            | 89008C       | 14         | MOB          | Pass      |

    Scenario Outline: （HIS）病患同牙位是否有特定健保代碼於365天(乳牙)/545天(恆牙)前已被申報過
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
        Then 病患的牙齒 <TreatmentTeeth> 在 <PastTreatmentDays> 天前，被申報 <TreatmentNhiCode> 健保代碼，而現在病患的牙齒 <IssueTeeth> 要被申報 <IssueNhiCode> 健保代碼，是否抵觸同顆牙齒在 <DayRange> 天內不得申報指定健保代碼，確認結果是否為 <PassOrNot> 且檢查訊息類型為 D1_3
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | DayRange | PastTreatmentDays | TreatmentNhiCode | TreatmentTeeth | PassOrNot |
            # 後乳牙
            | 89008C       | 54         | MOB          | 365      | 0                 | 89001C           | 54             | NotPass   |
            | 89008C       | 54         | MOB          | 365      | 364               | 89001C           | 54             | NotPass   |
            | 89008C       | 54         | MOB          | 365      | 365               | 89001C           | 54             | NotPass   |
            | 89008C       | 54         | MOB          | 365      | 366               | 89001C           | 54             | Pass      |
            # 後恆牙
            | 89008C       | 14         | MOB          | 545      | 0                 | 89001C           | 14             | NotPass   |
            | 89008C       | 14         | MOB          | 545      | 544               | 89001C           | 14             | NotPass   |
            | 89008C       | 14         | MOB          | 545      | 545               | 89001C           | 14             | NotPass   |
            | 89008C       | 14         | MOB          | 545      | 546               | 89001C           | 14             | Pass      |
            # 申報的牙齒與欲檢查的牙齒不同顆
            | 89008C       | 54         | MOB          | 365      | 0                 | 89001C           | 64             | Pass      |
            | 89008C       | 54         | MOB          | 365      | 364               | 89001C           | 64             | Pass      |
            | 89008C       | 54         | MOB          | 365      | 365               | 89001C           | 64             | Pass      |
            | 89008C       | 14         | MOB          | 545      | 0                 | 89001C           | 24             | Pass      |
            | 89008C       | 14         | MOB          | 545      | 544               | 89001C           | 24             | Pass      |
            | 89008C       | 14         | MOB          | 545      | 545               | 89001C           | 24             | Pass      |
            # 測試其他NhiCode
            | 89008C       | 54         | MOB          | 365      | 365               | 89002C           | 54             | NotPass   |
            | 89008C       | 54         | MOB          | 365      | 365               | 89003C           | 54             | NotPass   |
            | 89008C       | 54         | MOB          | 365      | 365               | 89004C           | 54             | NotPass   |
            | 89008C       | 54         | MOB          | 365      | 365               | 89005C           | 54             | NotPass   |
            | 89008C       | 54         | MOB          | 365      | 365               | 89008C           | 54             | NotPass   |
            | 89008C       | 54         | MOB          | 365      | 365               | 89009C           | 54             | NotPass   |
            | 89008C       | 54         | MOB          | 365      | 365               | 89010C           | 54             | NotPass   |
            | 89008C       | 54         | MOB          | 365      | 365               | 89011C           | 54             | NotPass   |
            | 89008C       | 54         | MOB          | 365      | 365               | 89012C           | 54             | NotPass   |
            | 89008C       | 54         | MOB          | 365      | 365               | 89014C           | 54             | NotPass   |
            | 89008C       | 54         | MOB          | 365      | 365               | 89015C           | 54             | NotPass   |

    Scenario Outline: 限制牙面在 1 以上
        Given 建立醫師
        Given Wind 24 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 限制牙面在 1 以上，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            | 89008C       | 54         | MOB          | Pass      |
            | 89008C       | 54         | DL           | Pass      |
            | 89008C       | 54         | O            | Pass      |
            | 89008C       | 54         |              | NotPass   |

    Scenario Outline: （HIS）同牙位未曾申報過 90007C 代碼
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
        Then 任意時間點牙位 <IssueTeeth> 未曾申報過指定代碼 <TreatmentNhiCode>，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastTreatmentDays | TreatmentNhiCode | TreatmentTeeth | PassOrNot |
            | 89008C       | 54         | DL           | 30                | 90007C           | 54             | NotPass   |
            | 89008C       | 54         | DL           | 30                | 90007C           | 55             | Pass      |

    Scenario Outline: （IC）同牙位未曾申報過 90007C 代碼
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
        Then 任意時間點牙位 <IssueTeeth> 未曾申報過指定代碼 <MedicalNhiCode>，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastMedicalDays | MedicalNhiCode | MedicalTeeth | PassOrNot |
            | 89008C       | 54         | DL           | 30              | 90007C         | 54           | NotPass   |
            | 89008C       | 54         | DL           | 30              | 90007C         | 55           | Pass      |

    Scenario Outline: （HIS）同牙位前30天內不得有89006C，但如果這中間有90001C, 90002C, 90003C, 90019C, 90020C則例外
        Given 建立醫師
        Given Wind 24 歲病人
        Given 在過去第 <89006CTreatmentDay> 天，建立預約
        Given 在過去第 <89006CTreatmentDay> 天，建立掛號
        Given 在過去第 <89006CTreatmentDay> 天，產生診療計畫
        And 新增診療代碼:
            | PastDays             | A72 | A73    | A74              | A75 | A76 | A77 | A78 | A79 |
            | <89006CTreatmentDay> | 3   | 89006C | <TreatmentTeeth> | MOB | 0   | 1.0 | 03  |     |
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
        Then 89006C 在前 <89006CTreatmentDay> 天建立， <TreatmentNhiCode> 在前 <PastTreatmentDays> 天建立，治療牙位為 <IssueTeeth> ，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastTreatmentDays | TreatmentNhiCode | TreatmentTeeth | 89006CTreatmentDay | PassOrNot |
            # 測試TreatmentNhiCode為非指定代碼且同牙位
            | 89008C       | 54         | MOB          | 15                | 01271C           | 54             | 30                 | NotPass   |
            # 測試TreatmentNhiCode為非指定代碼且不同牙位
            | 89008C       | 54         | MOB          | 15                | 01271C           | 55             | 30                 | Pass      |
            # 測試89006C在超過30天前建立且同牙位
            | 89008C       | 54         | MOB          | 15                | 01271C           | 54             | 31                 | Pass      |
            # 測試89006C在超過30天前建立且不同牙位
            | 89008C       | 54         | MOB          | 15                | 01271C           | 55             | 31                 | Pass      |
            # 測試TreatmentNhiCode是產生在89006C之後，但在30天內且同牙位
            | 89008C       | 54         | MOB          | 29                | 90001C           | 54             | 30                 | Pass      |
            | 89008C       | 54         | MOB          | 29                | 90002C           | 54             | 30                 | Pass      |
            | 89008C       | 54         | MOB          | 29                | 90003C           | 54             | 30                 | Pass      |
            | 89008C       | 54         | MOB          | 29                | 90019C           | 54             | 30                 | Pass      |
            | 89008C       | 54         | MOB          | 29                | 90020C           | 54             | 30                 | Pass      |
            # 測試TreatmentNhiCode是產生在89006C之後，但在30天內且不同牙位
            | 89008C       | 54         | MOB          | 29                | 90001C           | 55             | 30                 | Pass      |
            | 89008C       | 54         | MOB          | 29                | 90002C           | 55             | 30                 | Pass      |
            | 89008C       | 54         | MOB          | 29                | 90003C           | 55             | 30                 | Pass      |
            | 89008C       | 54         | MOB          | 29                | 90019C           | 55             | 30                 | Pass      |
            | 89008C       | 54         | MOB          | 29                | 90020C           | 55             | 30                 | Pass      |
            # 測試TreatmentNhiCode是產生在89006C之前，但超過30天且同牙位
            | 89008C       | 54         | MOB          | 31                | 90001C           | 54             | 30                 | NotPass   |
            | 89008C       | 54         | MOB          | 31                | 90002C           | 54             | 30                 | NotPass   |
            | 89008C       | 54         | MOB          | 31                | 90003C           | 54             | 30                 | NotPass   |
            | 89008C       | 54         | MOB          | 31                | 90019C           | 54             | 30                 | NotPass   |
            | 89008C       | 54         | MOB          | 31                | 90020C           | 54             | 30                 | NotPass   |
            # 測試TreatmentNhiCode是產生在89006C之前，但超過30天且不同牙位
            | 89008C       | 54         | MOB          | 31                | 90001C           | 55             | 30                 | Pass      |
            | 89008C       | 54         | MOB          | 31                | 90002C           | 55             | 30                 | Pass      |
            | 89008C       | 54         | MOB          | 31                | 90003C           | 55             | 30                 | Pass      |
            | 89008C       | 54         | MOB          | 31                | 90019C           | 55             | 30                 | Pass      |
            | 89008C       | 54         | MOB          | 31                | 90020C           | 55             | 30                 | Pass      |
            # 測試TreatmentNhiCode與89006C都在同一天且同牙位
            | 89008C       | 54         | MOB          | 30                | 90001C           | 54             | 30                 | NotPass   |
            | 89008C       | 54         | MOB          | 30                | 90002C           | 54             | 30                 | NotPass   |
            | 89008C       | 54         | MOB          | 30                | 90003C           | 54             | 30                 | NotPass   |
            | 89008C       | 54         | MOB          | 30                | 90019C           | 54             | 30                 | NotPass   |
            | 89008C       | 54         | MOB          | 30                | 90020C           | 54             | 30                 | NotPass   |
            # 測試TreatmentNhiCode與89006C都在同一天且不同牙位
            | 89008C       | 54         | MOB          | 30                | 90001C           | 55             | 30                 | Pass      |
            | 89008C       | 54         | MOB          | 30                | 90002C           | 55             | 30                 | Pass      |
            | 89008C       | 54         | MOB          | 30                | 90003C           | 55             | 30                 | Pass      |
            | 89008C       | 54         | MOB          | 30                | 90019C           | 55             | 30                 | Pass      |
            | 89008C       | 54         | MOB          | 30                | 90020C           | 55             | 30                 | Pass      |

    Scenario Outline: （IC）同牙位前30天內不得有89006C，但如果這中間有90001C, 90002C, 90003C, 90019C, 90020C則例外
        Given 建立醫師
        Given Wind 24 歲病人
        Given 新增健保醫療:
            | PastDays                | NhiCode          | Teeth          |
            | <89006CPastMedicalDays> | 89006C           | <MedicalTeeth> |
            | <PastMedicalDays>       | <MedicalNhiCode> | <MedicalTeeth> |
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 89006C 在前 <89006CPastMedicalDays> 天建立， <MedicalNhiCode> 在前 <PastMedicalDays> 天建立，治療牙位為 <IssueTeeth> ，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastMedicalDays | MedicalNhiCode | MedicalTeeth | 89006CPastMedicalDays | PassOrNot |
            # 測試TreatmentNhiCode為非指定代碼且同牙位
            | 89008C       | 54         | MOB          | 15              | 01271C         | 54           | 30                    | NotPass   |
            # 測試TreatmentNhiCode為非指定代碼且不同牙位
            | 89008C       | 54         | MOB          | 15              | 01271C         | 55           | 30                    | Pass      |
            # 測試89006C在超過30天前建立且同牙位
            | 89008C       | 54         | MOB          | 15              | 01271C         | 54           | 31                    | Pass      |
            # 測試89006C在超過30天前建立且不同牙位
            | 89008C       | 54         | MOB          | 15              | 01271C         | 55           | 31                    | Pass      |
            # 測試TreatmentNhiCode是產生在89006C之後，但在30天內且同牙位
            | 89008C       | 54         | MOB          | 29              | 90001C         | 54           | 30                    | Pass      |
            | 89008C       | 54         | MOB          | 29              | 90002C         | 54           | 30                    | Pass      |
            | 89008C       | 54         | MOB          | 29              | 90003C         | 54           | 30                    | Pass      |
            | 89008C       | 54         | MOB          | 29              | 90019C         | 54           | 30                    | Pass      |
            | 89008C       | 54         | MOB          | 29              | 90020C         | 54           | 30                    | Pass      |
            # 測試TreatmentNhiCode是產生在89006C之後，但在30天內且不同牙位
            | 89008C       | 54         | MOB          | 29              | 90001C         | 55           | 30                    | Pass      |
            | 89008C       | 54         | MOB          | 29              | 90002C         | 55           | 30                    | Pass      |
            | 89008C       | 54         | MOB          | 29              | 90003C         | 55           | 30                    | Pass      |
            | 89008C       | 54         | MOB          | 29              | 90019C         | 55           | 30                    | Pass      |
            | 89008C       | 54         | MOB          | 29              | 90020C         | 55           | 30                    | Pass      |
            # 測試TreatmentNhiCode是產生在89006C之前，但超過30天且同牙位
            | 89008C       | 54         | MOB          | 31              | 90001C         | 54           | 30                    | NotPass   |
            | 89008C       | 54         | MOB          | 31              | 90002C         | 54           | 30                    | NotPass   |
            | 89008C       | 54         | MOB          | 31              | 90003C         | 54           | 30                    | NotPass   |
            | 89008C       | 54         | MOB          | 31              | 90019C         | 54           | 30                    | NotPass   |
            | 89008C       | 54         | MOB          | 31              | 90020C         | 54           | 30                    | NotPass   |
            # 測試TreatmentNhiCode是產生在89006C之前，但超過30天且不同牙位
            | 89008C       | 54         | MOB          | 31              | 90001C         | 55           | 30                    | Pass      |
            | 89008C       | 54         | MOB          | 31              | 90002C         | 55           | 30                    | Pass      |
            | 89008C       | 54         | MOB          | 31              | 90003C         | 55           | 30                    | Pass      |
            | 89008C       | 54         | MOB          | 31              | 90019C         | 55           | 30                    | Pass      |
            | 89008C       | 54         | MOB          | 31              | 90020C         | 55           | 30                    | Pass      |
            # 測試TreatmentNhiCode與89006C都在同一天且同牙位
            | 89008C       | 54         | MOB          | 30              | 90001C         | 54           | 30                    | NotPass   |
            | 89008C       | 54         | MOB          | 30              | 90002C         | 54           | 30                    | NotPass   |
            | 89008C       | 54         | MOB          | 30              | 90003C         | 54           | 30                    | NotPass   |
            | 89008C       | 54         | MOB          | 30              | 90019C         | 54           | 30                    | NotPass   |
            | 89008C       | 54         | MOB          | 30              | 90020C         | 54           | 30                    | NotPass   |
            # 測試TreatmentNhiCode與89006C都在同一天且不同牙位
            | 89008C       | 54         | MOB          | 30              | 90001C         | 55           | 30                    | Pass      |
            | 89008C       | 54         | MOB          | 30              | 90002C         | 55           | 30                    | Pass      |
            | 89008C       | 54         | MOB          | 30              | 90003C         | 55           | 30                    | Pass      |
            | 89008C       | 54         | MOB          | 30              | 90019C         | 55           | 30                    | Pass      |
            | 89008C       | 54         | MOB          | 30              | 90020C         | 55           | 30                    | Pass      |

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
            | 89008C       | 54         | DL           | 3650              | 92013C           | 54             | NotPass   |
            | 89008C       | 54         | DL           | 3650              | 92014C           | 54             | NotPass   |
            | 89008C       | 54         | DL           | 3650              | 92015C           | 54             | NotPass   |
            # 測試不同牙
            | 89008C       | 54         | DL           | 3650              | 92013C           | 55             | Pass      |
            | 89008C       | 54         | DL           | 3650              | 92014C           | 55             | Pass      |
            | 89008C       | 54         | DL           | 3650              | 92015C           | 55             | Pass      |

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
            | 89008C       | 54         | DL           | 3650            | 92013C         | 54             | NotPass   |
            | 89008C       | 54         | DL           | 3650            | 92014C         | 54             | NotPass   |
            | 89008C       | 54         | DL           | 3650            | 92015C         | 54             | NotPass   |
            # 測試不同牙
            | 89008C       | 54         | DL           | 3650            | 92013C         | 55             | Pass      |
            | 89008C       | 54         | DL           | 3650            | 92014C         | 55             | Pass      |
            | 89008C       | 54         | DL           | 3650            | 92015C         | 55             | Pass      |

    Scenario Outline: 檢查治療的牙位是否為 BACK_TOOTH
        Given 建立醫師
        Given Wind 24 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 檢查 <IssueTeeth> 牙位，依 BACK_TOOTH 判定是否為核可牙位，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            # 乳牙
            | 89008C       | 51         | DL           | NotPass   |
            | 89008C       | 52         | DL           | NotPass   |
            | 89008C       | 53         | DL           | NotPass   |
            | 89008C       | 54         | DL           | Pass      |
            | 89008C       | 55         | DL           | Pass      |
            | 89008C       | 61         | DL           | NotPass   |
            | 89008C       | 62         | DL           | NotPass   |
            | 89008C       | 63         | DL           | NotPass   |
            | 89008C       | 64         | DL           | Pass      |
            | 89008C       | 65         | DL           | Pass      |
            | 89008C       | 71         | DL           | NotPass   |
            | 89008C       | 72         | DL           | NotPass   |
            | 89008C       | 73         | DL           | NotPass   |
            | 89008C       | 74         | DL           | Pass      |
            | 89008C       | 75         | DL           | Pass      |
            | 89008C       | 81         | DL           | NotPass   |
            | 89008C       | 82         | DL           | NotPass   |
            | 89008C       | 83         | DL           | NotPass   |
            | 89008C       | 84         | DL           | Pass      |
            | 89008C       | 85         | DL           | Pass      |
            # 恆牙
            | 89008C       | 11         | DL           | NotPass   |
            | 89008C       | 12         | DL           | NotPass   |
            | 89008C       | 13         | DL           | NotPass   |
            | 89008C       | 14         | DL           | Pass      |
            | 89008C       | 15         | DL           | Pass      |
            | 89008C       | 16         | DL           | Pass      |
            | 89008C       | 17         | DL           | Pass      |
            | 89008C       | 18         | DL           | Pass      |
            | 89008C       | 21         | DL           | NotPass   |
            | 89008C       | 22         | DL           | NotPass   |
            | 89008C       | 23         | DL           | NotPass   |
            | 89008C       | 24         | DL           | Pass      |
            | 89008C       | 25         | DL           | Pass      |
            | 89008C       | 26         | DL           | Pass      |
            | 89008C       | 27         | DL           | Pass      |
            | 89008C       | 28         | DL           | Pass      |
            | 89008C       | 31         | DL           | NotPass   |
            | 89008C       | 32         | DL           | NotPass   |
            | 89008C       | 33         | DL           | NotPass   |
            | 89008C       | 34         | DL           | Pass      |
            | 89008C       | 35         | DL           | Pass      |
            | 89008C       | 36         | DL           | Pass      |
            | 89008C       | 37         | DL           | Pass      |
            | 89008C       | 38         | DL           | Pass      |
            | 89008C       | 41         | DL           | NotPass   |
            | 89008C       | 42         | DL           | NotPass   |
            | 89008C       | 43         | DL           | NotPass   |
            | 89008C       | 44         | DL           | Pass      |
            | 89008C       | 45         | DL           | Pass      |
            | 89008C       | 46         | DL           | Pass      |
            | 89008C       | 47         | DL           | Pass      |
            | 89008C       | 48         | DL           | Pass      |
            # 無牙
            | 89008C       |            | DL           | NotPass   |
            #
            | 89008C       | 19         | DL           | Pass      |
            | 89008C       | 29         | DL           | Pass      |
            | 89008C       | 39         | DL           | Pass      |
            | 89008C       | 49         | DL           | Pass      |
            | 89008C       | 59         | DL           | NotPass   |
            | 89008C       | 69         | DL           | NotPass   |
            | 89008C       | 79         | DL           | NotPass   |
            | 89008C       | 89         | DL           | NotPass   |
            | 89008C       | 99         | DL           | Pass      |
            # 牙位為區域型態
            | 89008C       | FM         | DL           | NotPass   |
            | 89008C       | UR         | DL           | NotPass   |
            | 89008C       | UL         | DL           | NotPass   |
            | 89008C       | UA         | DL           | NotPass   |
            | 89008C       | UB         | DL           | NotPass   |
            | 89008C       | LR         | DL           | NotPass   |
            | 89008C       | LL         | DL           | NotPass   |
            | 89008C       | LA         | DL           | NotPass   |
            | 89008C       | LB         | DL           | NotPass   |
            # 非法牙位
            | 89008C       | 00         | DL           | NotPass   |
            | 89008C       | 01         | DL           | NotPass   |
            | 89008C       | 10         | DL           | NotPass   |
            | 89008C       | 56         | DL           | NotPass   |
            | 89008C       | 66         | DL           | NotPass   |
            | 89008C       | 76         | DL           | NotPass   |
            | 89008C       | 86         | DL           | NotPass   |
            | 89008C       | 91         | DL           | NotPass   |
