@nhi @nhi-89-series @part1
Feature: 89004C 前牙複合樹脂充填-單面

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
            | 89004C       | 11         | MOB          | Pass      |

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
            # 前乳牙
            | 89004C       | 51         | MOB          | 365      | 0                 | 89001C           | 51             | NotPass   |
            | 89004C       | 51         | MOB          | 365      | 364               | 89001C           | 51             | NotPass   |
            | 89004C       | 51         | MOB          | 365      | 365               | 89001C           | 51             | NotPass   |
            | 89004C       | 51         | MOB          | 365      | 366               | 89001C           | 51             | Pass      |
            # 前恆牙
            | 89004C       | 11         | MOB          | 545      | 0                 | 89001C           | 11             | NotPass   |
            | 89004C       | 11         | MOB          | 545      | 544               | 89001C           | 11             | NotPass   |
            | 89004C       | 11         | MOB          | 545      | 545               | 89001C           | 11             | NotPass   |
            | 89004C       | 11         | MOB          | 545      | 546               | 89001C           | 11             | Pass      |
            # 申報的牙齒與欲檢查的牙齒不同顆
            | 89004C       | 51         | MOB          | 365      | 0                 | 89001C           | 61             | Pass      |
            | 89004C       | 51         | MOB          | 365      | 364               | 89001C           | 61             | Pass      |
            | 89004C       | 51         | MOB          | 365      | 365               | 89001C           | 61             | Pass      |
            | 89004C       | 11         | MOB          | 545      | 0                 | 89001C           | 21             | Pass      |
            | 89004C       | 11         | MOB          | 545      | 544               | 89001C           | 21             | Pass      |
            | 89004C       | 11         | MOB          | 545      | 545               | 89001C           | 21             | Pass      |
            # 測試其他NhiCode
            | 89004C       | 51         | MOB          | 365      | 365               | 89002C           | 51             | NotPass   |
            | 89004C       | 51         | MOB          | 365      | 365               | 89003C           | 51             | NotPass   |
            | 89004C       | 51         | MOB          | 365      | 365               | 89004C           | 51             | NotPass   |
            | 89004C       | 51         | MOB          | 365      | 365               | 89005C           | 51             | NotPass   |
            | 89004C       | 51         | MOB          | 365      | 365               | 89008C           | 51             | NotPass   |
            | 89004C       | 51         | MOB          | 365      | 365               | 89009C           | 51             | NotPass   |
            | 89004C       | 51         | MOB          | 365      | 365               | 89010C           | 51             | NotPass   |
            | 89004C       | 51         | MOB          | 365      | 365               | 89011C           | 51             | NotPass   |
            | 89004C       | 51         | MOB          | 365      | 365               | 89012C           | 51             | NotPass   |
            | 89004C       | 51         | MOB          | 365      | 365               | 89014C           | 51             | NotPass   |
            | 89004C       | 51         | MOB          | 365      | 365               | 89015C           | 51             | NotPass   |

    Scenario Outline: （IC）病患同牙位是否有特定健保代碼於365天(乳牙)/545天(恆牙)前已被申報過
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
        Then 病患的牙齒 <MedicalTeeth> 在 <PastMedicalDays> 天前，被申報 <MedicalNhiCode> 健保代碼，而現在病患的牙齒 <IssueTeeth> 要被申報 <IssueNhiCode> 健保代碼，是否抵觸同顆牙齒在 <DayRange> 天內不得申報指定健保代碼，確認結果是否為 <PassOrNot> 且檢查訊息類型為 D1_3
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | DayRange | PastMedicalDays | MedicalNhiCode | MedicalTeeth | PassOrNot |
            # 前乳牙
            | 89004C       | 51         | MOB          | 365      | 0               | 89001C         | 51           | NotPass   |
            | 89004C       | 51         | MOB          | 365      | 364             | 89001C         | 51           | NotPass   |
            | 89004C       | 51         | MOB          | 365      | 365             | 89001C         | 51           | NotPass   |
            | 89004C       | 51         | MOB          | 365      | 366             | 89001C         | 51           | Pass      |
            # 前恆牙
            | 89004C       | 11         | MOB          | 545      | 0               | 89001C         | 11           | NotPass   |
            | 89004C       | 11         | MOB          | 545      | 544             | 89001C         | 11           | NotPass   |
            | 89004C       | 11         | MOB          | 545      | 545             | 89001C         | 11           | NotPass   |
            | 89004C       | 11         | MOB          | 545      | 546             | 89001C         | 11           | Pass      |
            # 申報的牙齒與欲檢查的牙齒不同顆
            | 89004C       | 51         | MOB          | 365      | 0               | 89001C         | 61           | Pass      |
            | 89004C       | 51         | MOB          | 365      | 364             | 89001C         | 61           | Pass      |
            | 89004C       | 51         | MOB          | 365      | 365             | 89001C         | 61           | Pass      |
            | 89004C       | 11         | MOB          | 545      | 0               | 89001C         | 21           | Pass      |
            | 89004C       | 11         | MOB          | 545      | 544             | 89001C         | 21           | Pass      |
            | 89004C       | 11         | MOB          | 545      | 545             | 89001C         | 21           | Pass      |
            # 測試其他NhiCode
            | 89004C       | 51         | MOB          | 365      | 365             | 89002C         | 51           | NotPass   |
            | 89004C       | 51         | MOB          | 365      | 365             | 89003C         | 51           | NotPass   |
            | 89004C       | 51         | MOB          | 365      | 365             | 89004C         | 51           | NotPass   |
            | 89004C       | 51         | MOB          | 365      | 365             | 89005C         | 51           | NotPass   |
            | 89004C       | 51         | MOB          | 365      | 365             | 89008C         | 51           | NotPass   |
            | 89004C       | 51         | MOB          | 365      | 365             | 89009C         | 51           | NotPass   |
            | 89004C       | 51         | MOB          | 365      | 365             | 89010C         | 51           | NotPass   |
            | 89004C       | 51         | MOB          | 365      | 365             | 89011C         | 51           | NotPass   |
            | 89004C       | 51         | MOB          | 365      | 365             | 89012C         | 51           | NotPass   |
            | 89004C       | 51         | MOB          | 365      | 365             | 89014C         | 51           | NotPass   |
            | 89004C       | 51         | MOB          | 365      | 365             | 89015C         | 51           | NotPass   |

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
            | 89004C       | 51         | MOB          | Pass      |
            | 89004C       | 51         | DL           | Pass      |
            | 89004C       | 51         | O            | Pass      |
            | 89004C       | 51         |              | NotPass   |

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
            | 89004C       | 51         | DL           | 30                | 90007C           | 51             | NotPass   |
            | 89004C       | 51         | DL           | 30                | 90007C           | 52             | Pass      |

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
            | 89004C       | 51         | DL           | 30              | 90007C         | 51           | NotPass   |
            | 89004C       | 51         | DL           | 30              | 90007C         | 52           | Pass      |

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
            | 89004C       | 51         | MOB          | 15                | 01271C           | 51             | 30                 | NotPass   |
            # 測試TreatmentNhiCode為非指定代碼且不同牙位
            | 89004C       | 51         | MOB          | 15                | 01271C           | 52             | 30                 | Pass      |
            # 測試89006C在超過30天前建立且同牙位
            | 89004C       | 51         | MOB          | 15                | 01271C           | 51             | 31                 | Pass      |
            # 測試89006C在超過30天前建立且不同牙位
            | 89004C       | 51         | MOB          | 15                | 01271C           | 52             | 31                 | Pass      |
            # 測試TreatmentNhiCode是產生在89006C之後，但在30天內且同牙位
            | 89004C       | 51         | MOB          | 29                | 90001C           | 51             | 30                 | Pass      |
            | 89004C       | 51         | MOB          | 29                | 90002C           | 51             | 30                 | Pass      |
            | 89004C       | 51         | MOB          | 29                | 90003C           | 51             | 30                 | Pass      |
            | 89004C       | 51         | MOB          | 29                | 90019C           | 51             | 30                 | Pass      |
            | 89004C       | 51         | MOB          | 29                | 90020C           | 51             | 30                 | Pass      |
            # 測試TreatmentNhiCode是產生在89006C之後，但在30天內且不同牙位
            | 89004C       | 51         | MOB          | 29                | 90001C           | 52             | 30                 | Pass      |
            | 89004C       | 51         | MOB          | 29                | 90002C           | 52             | 30                 | Pass      |
            | 89004C       | 51         | MOB          | 29                | 90003C           | 52             | 30                 | Pass      |
            | 89004C       | 51         | MOB          | 29                | 90019C           | 52             | 30                 | Pass      |
            | 89004C       | 51         | MOB          | 29                | 90020C           | 52             | 30                 | Pass      |
            # 測試TreatmentNhiCode是產生在89006C之前，但超過30天且同牙位
            | 89004C       | 51         | MOB          | 31                | 90001C           | 51             | 30                 | NotPass   |
            | 89004C       | 51         | MOB          | 31                | 90002C           | 51             | 30                 | NotPass   |
            | 89004C       | 51         | MOB          | 31                | 90003C           | 51             | 30                 | NotPass   |
            | 89004C       | 51         | MOB          | 31                | 90019C           | 51             | 30                 | NotPass   |
            | 89004C       | 51         | MOB          | 31                | 90020C           | 51             | 30                 | NotPass   |
            # 測試TreatmentNhiCode是產生在89006C之前，但超過30天且不同牙位
            | 89004C       | 51         | MOB          | 31                | 90001C           | 52             | 30                 | Pass      |
            | 89004C       | 51         | MOB          | 31                | 90002C           | 52             | 30                 | Pass      |
            | 89004C       | 51         | MOB          | 31                | 90003C           | 52             | 30                 | Pass      |
            | 89004C       | 51         | MOB          | 31                | 90019C           | 52             | 30                 | Pass      |
            | 89004C       | 51         | MOB          | 31                | 90020C           | 52             | 30                 | Pass      |
            # 測試TreatmentNhiCode與89006C都在同一天且同牙位
            | 89004C       | 51         | MOB          | 30                | 90001C           | 51             | 30                 | NotPass   |
            | 89004C       | 51         | MOB          | 30                | 90002C           | 51             | 30                 | NotPass   |
            | 89004C       | 51         | MOB          | 30                | 90003C           | 51             | 30                 | NotPass   |
            | 89004C       | 51         | MOB          | 30                | 90019C           | 51             | 30                 | NotPass   |
            | 89004C       | 51         | MOB          | 30                | 90020C           | 51             | 30                 | NotPass   |
            # 測試TreatmentNhiCode與89006C都在同一天且不同牙位
            | 89004C       | 51         | MOB          | 30                | 90001C           | 52             | 30                 | Pass      |
            | 89004C       | 51         | MOB          | 30                | 90002C           | 52             | 30                 | Pass      |
            | 89004C       | 51         | MOB          | 30                | 90003C           | 52             | 30                 | Pass      |
            | 89004C       | 51         | MOB          | 30                | 90019C           | 52             | 30                 | Pass      |
            | 89004C       | 51         | MOB          | 30                | 90020C           | 52             | 30                 | Pass      |

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
            | 89004C       | 51         | MOB          | 15              | 01271C         | 51           | 30                    | NotPass   |
            # 測試TreatmentNhiCode為非指定代碼且不同牙位
            | 89004C       | 51         | MOB          | 15              | 01271C         | 52           | 30                    | Pass      |
            # 測試89006C在超過30天前建立且同牙位
            | 89004C       | 51         | MOB          | 15              | 01271C         | 51           | 31                    | Pass      |
            # 測試89006C在超過30天前建立且不同牙位
            | 89004C       | 51         | MOB          | 15              | 01271C         | 52           | 31                    | Pass      |
            # 測試TreatmentNhiCode是產生在89006C之後，但在30天內且同牙位
            | 89004C       | 51         | MOB          | 29              | 90001C         | 51           | 30                    | Pass      |
            | 89004C       | 51         | MOB          | 29              | 90002C         | 51           | 30                    | Pass      |
            | 89004C       | 51         | MOB          | 29              | 90003C         | 51           | 30                    | Pass      |
            | 89004C       | 51         | MOB          | 29              | 90019C         | 51           | 30                    | Pass      |
            | 89004C       | 51         | MOB          | 29              | 90020C         | 51           | 30                    | Pass      |
            # 測試TreatmentNhiCode是產生在89006C之後，但在30天內且不同牙位
            | 89004C       | 51         | MOB          | 29              | 90001C         | 52           | 30                    | Pass      |
            | 89004C       | 51         | MOB          | 29              | 90002C         | 52           | 30                    | Pass      |
            | 89004C       | 51         | MOB          | 29              | 90003C         | 52           | 30                    | Pass      |
            | 89004C       | 51         | MOB          | 29              | 90019C         | 52           | 30                    | Pass      |
            | 89004C       | 51         | MOB          | 29              | 90020C         | 52           | 30                    | Pass      |
            # 測試TreatmentNhiCode是產生在89006C之前，但超過30天且同牙位
            | 89004C       | 51         | MOB          | 31              | 90001C         | 51           | 30                    | NotPass   |
            | 89004C       | 51         | MOB          | 31              | 90002C         | 51           | 30                    | NotPass   |
            | 89004C       | 51         | MOB          | 31              | 90003C         | 51           | 30                    | NotPass   |
            | 89004C       | 51         | MOB          | 31              | 90019C         | 51           | 30                    | NotPass   |
            | 89004C       | 51         | MOB          | 31              | 90020C         | 51           | 30                    | NotPass   |
            # 測試TreatmentNhiCode是產生在89006C之前，但超過30天且不同牙位
            | 89004C       | 51         | MOB          | 31              | 90001C         | 52           | 30                    | Pass      |
            | 89004C       | 51         | MOB          | 31              | 90002C         | 52           | 30                    | Pass      |
            | 89004C       | 51         | MOB          | 31              | 90003C         | 52           | 30                    | Pass      |
            | 89004C       | 51         | MOB          | 31              | 90019C         | 52           | 30                    | Pass      |
            | 89004C       | 51         | MOB          | 31              | 90020C         | 52           | 30                    | Pass      |
            # 測試TreatmentNhiCode與89006C都在同一天且同牙位
            | 89004C       | 51         | MOB          | 30              | 90001C         | 51           | 30                    | NotPass   |
            | 89004C       | 51         | MOB          | 30              | 90002C         | 51           | 30                    | NotPass   |
            | 89004C       | 51         | MOB          | 30              | 90003C         | 51           | 30                    | NotPass   |
            | 89004C       | 51         | MOB          | 30              | 90019C         | 51           | 30                    | NotPass   |
            | 89004C       | 51         | MOB          | 30              | 90020C         | 51           | 30                    | NotPass   |
            # 測試TreatmentNhiCode與89006C都在同一天且不同牙位
            | 89004C       | 51         | MOB          | 30              | 90001C         | 52           | 30                    | Pass      |
            | 89004C       | 51         | MOB          | 30              | 90002C         | 52           | 30                    | Pass      |
            | 89004C       | 51         | MOB          | 30              | 90003C         | 52           | 30                    | Pass      |
            | 89004C       | 51         | MOB          | 30              | 90019C         | 52           | 30                    | Pass      |
            | 89004C       | 51         | MOB          | 30              | 90020C         | 52           | 30                    | Pass      |

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
            | 89004C       | 51         | DL           | 3650              | 92013C           | 51             | NotPass   |
            | 89004C       | 51         | DL           | 3650              | 92014C           | 51             | NotPass   |
            | 89004C       | 51         | DL           | 3650              | 92015C           | 51             | NotPass   |
            # 測試不同牙
            | 89004C       | 51         | DL           | 3650              | 92013C           | 11             | Pass      |
            | 89004C       | 51         | DL           | 3650              | 92014C           | 11             | Pass      |
            | 89004C       | 51         | DL           | 3650              | 92015C           | 11             | Pass      |

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
            | 89004C       | 51         | DL           | 3650            | 92013C         | 51             | NotPass   |
            | 89004C       | 51         | DL           | 3650            | 92014C         | 51             | NotPass   |
            | 89004C       | 51         | DL           | 3650            | 92015C         | 51             | NotPass   |
            # 測試不同牙
            | 89004C       | 51         | DL           | 3650            | 92013C         | 11             | Pass      |
            | 89004C       | 51         | DL           | 3650            | 92014C         | 11             | Pass      |
            | 89004C       | 51         | DL           | 3650            | 92015C         | 11             | Pass      |

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
            | 89004C       | 51         | DL           | Pass      |
            | 89004C       | 52         | DL           | Pass      |
            | 89004C       | 53         | DL           | Pass      |
            | 89004C       | 54         | DL           | NotPass   |
            | 89004C       | 55         | DL           | NotPass   |
            | 89004C       | 61         | DL           | Pass      |
            | 89004C       | 62         | DL           | Pass      |
            | 89004C       | 63         | DL           | Pass      |
            | 89004C       | 64         | DL           | NotPass   |
            | 89004C       | 65         | DL           | NotPass   |
            | 89004C       | 71         | DL           | Pass      |
            | 89004C       | 72         | DL           | Pass      |
            | 89004C       | 73         | DL           | Pass      |
            | 89004C       | 74         | DL           | NotPass   |
            | 89004C       | 75         | DL           | NotPass   |
            | 89004C       | 81         | DL           | Pass      |
            | 89004C       | 82         | DL           | Pass      |
            | 89004C       | 83         | DL           | Pass      |
            | 89004C       | 84         | DL           | NotPass   |
            | 89004C       | 85         | DL           | NotPass   |
            # 恆牙
            | 89004C       | 11         | DL           | Pass      |
            | 89004C       | 12         | DL           | Pass      |
            | 89004C       | 13         | DL           | Pass      |
            | 89004C       | 14         | DL           | NotPass   |
            | 89004C       | 15         | DL           | NotPass   |
            | 89004C       | 16         | DL           | NotPass   |
            | 89004C       | 17         | DL           | NotPass   |
            | 89004C       | 18         | DL           | NotPass   |
            | 89004C       | 21         | DL           | Pass      |
            | 89004C       | 22         | DL           | Pass      |
            | 89004C       | 23         | DL           | Pass      |
            | 89004C       | 24         | DL           | NotPass   |
            | 89004C       | 25         | DL           | NotPass   |
            | 89004C       | 26         | DL           | NotPass   |
            | 89004C       | 27         | DL           | NotPass   |
            | 89004C       | 28         | DL           | NotPass   |
            | 89004C       | 31         | DL           | Pass      |
            | 89004C       | 32         | DL           | Pass      |
            | 89004C       | 33         | DL           | Pass      |
            | 89004C       | 34         | DL           | NotPass   |
            | 89004C       | 35         | DL           | NotPass   |
            | 89004C       | 36         | DL           | NotPass   |
            | 89004C       | 37         | DL           | NotPass   |
            | 89004C       | 38         | DL           | NotPass   |
            | 89004C       | 41         | DL           | Pass      |
            | 89004C       | 42         | DL           | Pass      |
            | 89004C       | 43         | DL           | Pass      |
            | 89004C       | 44         | DL           | NotPass   |
            | 89004C       | 45         | DL           | NotPass   |
            | 89004C       | 46         | DL           | NotPass   |
            | 89004C       | 47         | DL           | NotPass   |
            | 89004C       | 48         | DL           | NotPass   |
            # 無牙
            | 89004C       |            | DL           | NotPass   |
            #
            | 89004C       | 19         | DL           | Pass      |
            | 89004C       | 29         | DL           | Pass      |
            | 89004C       | 39         | DL           | Pass      |
            | 89004C       | 49         | DL           | Pass      |
            | 89004C       | 59         | DL           | NotPass   |
            | 89004C       | 69         | DL           | NotPass   |
            | 89004C       | 79         | DL           | NotPass   |
            | 89004C       | 89         | DL           | NotPass   |
            | 89004C       | 99         | DL           | Pass      |
            # 牙位為區域型態
            | 89004C       | FM         | DL           | NotPass   |
            | 89004C       | UR         | DL           | NotPass   |
            | 89004C       | UL         | DL           | NotPass   |
            | 89004C       | UA         | DL           | NotPass   |
            | 89004C       | UB         | DL           | NotPass   |
            | 89004C       | LR         | DL           | NotPass   |
            | 89004C       | LL         | DL           | NotPass   |
            | 89004C       | LA         | DL           | NotPass   |
            | 89004C       | LB         | DL           | NotPass   |
            # 非法牙位
            | 89004C       | 00         | DL           | NotPass   |
            | 89004C       | 01         | DL           | NotPass   |
            | 89004C       | 10         | DL           | NotPass   |
            | 89004C       | 56         | DL           | NotPass   |
            | 89004C       | 66         | DL           | NotPass   |
            | 89004C       | 76         | DL           | NotPass   |
            | 89004C       | 86         | DL           | NotPass   |
            | 89004C       | 91         | DL           | NotPass   |
