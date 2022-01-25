@nhi @nhi-89-series @part2
Feature: 89011C 玻璃離子體充填

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
            | 89011C       | 14         | MOB          | Pass      |

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
        Then 病患的牙齒 <TreatmentTeeth> 在 <PastTreatmentDays> 天前，被申報 <TreatmentNhiCode> 健保代碼，而現在病患的牙齒 <IssueTeeth> 要被申報 <IssueNhiCode> 健保代碼，是否抵觸同顆牙齒在 <DayRange> 天內不得申報指定健保代碼，確認結果是否為 <PassOrNot> 且檢查訊息類型為 D1_3
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | DayRange | PastTreatmentDays | TreatmentNhiCode | TreatmentTeeth | PassOrNot |
            # 前乳牙
            | 89011C       | 51         | MOB          | 365      | 0                 | 89001C           | 51             | NotPass   |
            | 89011C       | 51         | MOB          | 365      | 364               | 89001C           | 51             | NotPass   |
            | 89011C       | 51         | MOB          | 365      | 365               | 89001C           | 51             | NotPass   |
            | 89011C       | 51         | MOB          | 365      | 366               | 89001C           | 51             | Pass      |
            # 前恆牙
            | 89011C       | 11         | MOB          | 545      | 0                 | 89001C           | 11             | NotPass   |
            | 89011C       | 11         | MOB          | 545      | 544               | 89001C           | 11             | NotPass   |
            | 89011C       | 11         | MOB          | 545      | 545               | 89001C           | 11             | NotPass   |
            | 89011C       | 11         | MOB          | 545      | 546               | 89001C           | 11             | Pass      |
            # 申報的牙齒與欲檢查的牙齒不同顆
            | 89011C       | 51         | MOB          | 365      | 0                 | 89001C           | 61             | Pass      |
            | 89011C       | 51         | MOB          | 365      | 364               | 89001C           | 61             | Pass      |
            | 89011C       | 51         | MOB          | 365      | 365               | 89001C           | 61             | Pass      |
            | 89011C       | 11         | MOB          | 545      | 0                 | 89001C           | 21             | Pass      |
            | 89011C       | 11         | MOB          | 545      | 544               | 89001C           | 21             | Pass      |
            | 89011C       | 11         | MOB          | 545      | 545               | 89001C           | 21             | Pass      |
            # 測試其他NhiCode
            | 89011C       | 51         | MOB          | 365      | 365               | 89002C           | 51             | NotPass   |
            | 89011C       | 51         | MOB          | 365      | 365               | 89003C           | 51             | NotPass   |
            | 89011C       | 51         | MOB          | 365      | 365               | 89004C           | 51             | NotPass   |
            | 89011C       | 51         | MOB          | 365      | 365               | 89005C           | 51             | NotPass   |
            | 89011C       | 51         | MOB          | 365      | 365               | 89008C           | 51             | NotPass   |
            | 89011C       | 51         | MOB          | 365      | 365               | 89009C           | 51             | NotPass   |
            | 89011C       | 51         | MOB          | 365      | 365               | 89010C           | 51             | NotPass   |
            | 89011C       | 51         | MOB          | 365      | 365               | 89011C           | 51             | NotPass   |
            | 89011C       | 51         | MOB          | 365      | 365               | 89012C           | 51             | NotPass   |
            | 89011C       | 51         | MOB          | 365      | 365               | 89014C           | 51             | NotPass   |
            | 89011C       | 51         | MOB          | 365      | 365               | 89015C           | 51             | NotPass   |

    Scenario Outline: （IC）病患牙齒是否有特定健保代碼於365天(乳牙)/545天(恆牙)前已被申報過
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
            | 89011C       | 51         | MOB          | 365      | 0               | 89001C         | 51           | NotPass   |
            | 89011C       | 51         | MOB          | 365      | 364             | 89001C         | 51           | NotPass   |
            | 89011C       | 51         | MOB          | 365      | 365             | 89001C         | 51           | NotPass   |
            | 89011C       | 51         | MOB          | 365      | 366             | 89001C         | 51           | Pass      |
            # 前恆牙
            | 89011C       | 11         | MOB          | 545      | 0               | 89001C         | 11           | NotPass   |
            | 89011C       | 11         | MOB          | 545      | 544             | 89001C         | 11           | NotPass   |
            | 89011C       | 11         | MOB          | 545      | 545             | 89001C         | 11           | NotPass   |
            | 89011C       | 11         | MOB          | 545      | 546             | 89001C         | 11           | Pass      |
            # 申報的牙齒與欲檢查的牙齒不同顆
            | 89011C       | 51         | MOB          | 365      | 0               | 89001C         | 61           | Pass      |
            | 89011C       | 51         | MOB          | 365      | 364             | 89001C         | 61           | Pass      |
            | 89011C       | 51         | MOB          | 365      | 365             | 89001C         | 61           | Pass      |
            | 89011C       | 11         | MOB          | 545      | 0               | 89001C         | 21           | Pass      |
            | 89011C       | 11         | MOB          | 545      | 544             | 89001C         | 21           | Pass      |
            | 89011C       | 11         | MOB          | 545      | 545             | 89001C         | 21           | Pass      |
            # 測試其他NhiCode
            | 89011C       | 51         | MOB          | 365      | 365             | 89002C         | 51           | NotPass   |
            | 89011C       | 51         | MOB          | 365      | 365             | 89003C         | 51           | NotPass   |
            | 89011C       | 51         | MOB          | 365      | 365             | 89004C         | 51           | NotPass   |
            | 89011C       | 51         | MOB          | 365      | 365             | 89005C         | 51           | NotPass   |
            | 89011C       | 51         | MOB          | 365      | 365             | 89008C         | 51           | NotPass   |
            | 89011C       | 51         | MOB          | 365      | 365             | 89009C         | 51           | NotPass   |
            | 89011C       | 51         | MOB          | 365      | 365             | 89010C         | 51           | NotPass   |
            | 89011C       | 51         | MOB          | 365      | 365             | 89011C         | 51           | NotPass   |
            | 89011C       | 51         | MOB          | 365      | 365             | 89012C         | 51           | NotPass   |
            | 89011C       | 51         | MOB          | 365      | 365             | 89014C         | 51           | NotPass   |
            | 89011C       | 51         | MOB          | 365      | 365             | 89015C         | 51           | NotPass   |

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
            | 89011C       | 51         | DL           | 30                | 90007C           | 51             | NotPass   |
            | 89011C       | 51         | DL           | 30                | 90007C           | 52             | Pass      |

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
            | 89011C       | 54         | DL           | 30              | 90007C         | 54           | NotPass   |
            | 89011C       | 54         | DL           | 30              | 90007C         | 55           | Pass      |

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
            | 89011C       | 54         | MOB          | 15                | 01271C           | 54             | 30                 | NotPass   |
            # 測試TreatmentNhiCode為非指定代碼且不同牙位
            | 89011C       | 54         | MOB          | 15                | 01271C           | 55             | 30                 | Pass      |
            # 測試89006C在超過30天前建立且同牙位
            | 89011C       | 54         | MOB          | 15                | 01271C           | 54             | 31                 | Pass      |
            # 測試89006C在超過30天前建立且不同牙位
            | 89011C       | 54         | MOB          | 15                | 01271C           | 55             | 31                 | Pass      |
            # 測試TreatmentNhiCode是產生在89006C之後，但在30天內且同牙位
            | 89011C       | 54         | MOB          | 29                | 90001C           | 54             | 30                 | Pass      |
            | 89011C       | 54         | MOB          | 29                | 90002C           | 54             | 30                 | Pass      |
            | 89011C       | 54         | MOB          | 29                | 90003C           | 54             | 30                 | Pass      |
            | 89011C       | 54         | MOB          | 29                | 90019C           | 54             | 30                 | Pass      |
            | 89011C       | 54         | MOB          | 29                | 90020C           | 54             | 30                 | Pass      |
            # 測試TreatmentNhiCode是產生在89006C之後，但在30天內且不同牙位
            | 89011C       | 54         | MOB          | 29                | 90001C           | 55             | 30                 | Pass      |
            | 89011C       | 54         | MOB          | 29                | 90002C           | 55             | 30                 | Pass      |
            | 89011C       | 54         | MOB          | 29                | 90003C           | 55             | 30                 | Pass      |
            | 89011C       | 54         | MOB          | 29                | 90019C           | 55             | 30                 | Pass      |
            | 89011C       | 54         | MOB          | 29                | 90020C           | 55             | 30                 | Pass      |
            # 測試TreatmentNhiCode是產生在89006C之前，但超過30天且同牙位
            | 89011C       | 54         | MOB          | 31                | 90001C           | 54             | 30                 | NotPass   |
            | 89011C       | 54         | MOB          | 31                | 90002C           | 54             | 30                 | NotPass   |
            | 89011C       | 54         | MOB          | 31                | 90003C           | 54             | 30                 | NotPass   |
            | 89011C       | 54         | MOB          | 31                | 90019C           | 54             | 30                 | NotPass   |
            | 89011C       | 54         | MOB          | 31                | 90020C           | 54             | 30                 | NotPass   |
            # 測試TreatmentNhiCode是產生在89006C之前，但超過30天且不同牙位
            | 89011C       | 54         | MOB          | 31                | 90001C           | 55             | 30                 | Pass      |
            | 89011C       | 54         | MOB          | 31                | 90002C           | 55             | 30                 | Pass      |
            | 89011C       | 54         | MOB          | 31                | 90003C           | 55             | 30                 | Pass      |
            | 89011C       | 54         | MOB          | 31                | 90019C           | 55             | 30                 | Pass      |
            | 89011C       | 54         | MOB          | 31                | 90020C           | 55             | 30                 | Pass      |
            # 測試TreatmentNhiCode與89006C都在同一天且同牙位
            | 89011C       | 54         | MOB          | 30                | 90001C           | 54             | 30                 | NotPass   |
            | 89011C       | 54         | MOB          | 30                | 90002C           | 54             | 30                 | NotPass   |
            | 89011C       | 54         | MOB          | 30                | 90003C           | 54             | 30                 | NotPass   |
            | 89011C       | 54         | MOB          | 30                | 90019C           | 54             | 30                 | NotPass   |
            | 89011C       | 54         | MOB          | 30                | 90020C           | 54             | 30                 | NotPass   |
            # 測試TreatmentNhiCode與89006C都在同一天且不同牙位
            | 89011C       | 54         | MOB          | 30                | 90001C           | 55             | 30                 | Pass      |
            | 89011C       | 54         | MOB          | 30                | 90002C           | 55             | 30                 | Pass      |
            | 89011C       | 54         | MOB          | 30                | 90003C           | 55             | 30                 | Pass      |
            | 89011C       | 54         | MOB          | 30                | 90019C           | 55             | 30                 | Pass      |
            | 89011C       | 54         | MOB          | 30                | 90020C           | 55             | 30                 | Pass      |

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
            | 89011C       | 54         | MOB          | 15              | 01271C         | 54           | 30                    | NotPass   |
            # 測試TreatmentNhiCode為非指定代碼且不同牙位
            | 89011C       | 54         | MOB          | 15              | 01271C         | 55           | 30                    | Pass      |
            # 測試89006C在超過30天前建立且同牙位
            | 89011C       | 54         | MOB          | 15              | 01271C         | 54           | 31                    | Pass      |
            # 測試89006C在超過30天前建立且不同牙位
            | 89011C       | 54         | MOB          | 15              | 01271C         | 55           | 31                    | Pass      |
            # 測試TreatmentNhiCode是產生在89006C之後，但在30天內且同牙位
            | 89011C       | 54         | MOB          | 29              | 90001C         | 54           | 30                    | Pass      |
            | 89011C       | 54         | MOB          | 29              | 90002C         | 54           | 30                    | Pass      |
            | 89011C       | 54         | MOB          | 29              | 90003C         | 54           | 30                    | Pass      |
            | 89011C       | 54         | MOB          | 29              | 90019C         | 54           | 30                    | Pass      |
            | 89011C       | 54         | MOB          | 29              | 90020C         | 54           | 30                    | Pass      |
            # 測試TreatmentNhiCode是產生在89006C之後，但在30天內且不同牙位
            | 89011C       | 54         | MOB          | 29              | 90001C         | 55           | 30                    | Pass      |
            | 89011C       | 54         | MOB          | 29              | 90002C         | 55           | 30                    | Pass      |
            | 89011C       | 54         | MOB          | 29              | 90003C         | 55           | 30                    | Pass      |
            | 89011C       | 54         | MOB          | 29              | 90019C         | 55           | 30                    | Pass      |
            | 89011C       | 54         | MOB          | 29              | 90020C         | 55           | 30                    | Pass      |
            # 測試TreatmentNhiCode是產生在89006C之前，但超過30天且同牙位
            | 89011C       | 54         | MOB          | 31              | 90001C         | 54           | 30                    | NotPass   |
            | 89011C       | 54         | MOB          | 31              | 90002C         | 54           | 30                    | NotPass   |
            | 89011C       | 54         | MOB          | 31              | 90003C         | 54           | 30                    | NotPass   |
            | 89011C       | 54         | MOB          | 31              | 90019C         | 54           | 30                    | NotPass   |
            | 89011C       | 54         | MOB          | 31              | 90020C         | 54           | 30                    | NotPass   |
            # 測試TreatmentNhiCode是產生在89006C之前，但超過30天且不同牙位
            | 89011C       | 54         | MOB          | 31              | 90001C         | 55           | 30                    | Pass      |
            | 89011C       | 54         | MOB          | 31              | 90002C         | 55           | 30                    | Pass      |
            | 89011C       | 54         | MOB          | 31              | 90003C         | 55           | 30                    | Pass      |
            | 89011C       | 54         | MOB          | 31              | 90019C         | 55           | 30                    | Pass      |
            | 89011C       | 54         | MOB          | 31              | 90020C         | 55           | 30                    | Pass      |
            # 測試TreatmentNhiCode與89006C都在同一天且同牙位
            | 89011C       | 54         | MOB          | 30              | 90001C         | 54           | 30                    | NotPass   |
            | 89011C       | 54         | MOB          | 30              | 90002C         | 54           | 30                    | NotPass   |
            | 89011C       | 54         | MOB          | 30              | 90003C         | 54           | 30                    | NotPass   |
            | 89011C       | 54         | MOB          | 30              | 90019C         | 54           | 30                    | NotPass   |
            | 89011C       | 54         | MOB          | 30              | 90020C         | 54           | 30                    | NotPass   |
            # 測試TreatmentNhiCode與89006C都在同一天且不同牙位
            | 89011C       | 54         | MOB          | 30              | 90001C         | 55           | 30                    | Pass      |
            | 89011C       | 54         | MOB          | 30              | 90002C         | 55           | 30                    | Pass      |
            | 89011C       | 54         | MOB          | 30              | 90003C         | 55           | 30                    | Pass      |
            | 89011C       | 54         | MOB          | 30              | 90019C         | 55           | 30                    | Pass      |
            | 89011C       | 54         | MOB          | 30              | 90020C         | 55           | 30                    | Pass      |

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
            | 89011C       | 54         | DL           | 3650              | 92013C           | 54             | NotPass   |
            | 89011C       | 54         | DL           | 3650              | 92014C           | 54             | NotPass   |
            | 89011C       | 54         | DL           | 3650              | 92015C           | 54             | NotPass   |
            # 測試不同牙
            | 89011C       | 54         | DL           | 3650              | 92013C           | 55             | Pass      |
            | 89011C       | 54         | DL           | 3650              | 92014C           | 55             | Pass      |
            | 89011C       | 54         | DL           | 3650              | 92015C           | 55             | Pass      |

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
            | 89011C       | 54         | DL           | 3650            | 92013C         | 54             | NotPass   |
            | 89011C       | 54         | DL           | 3650            | 92014C         | 54             | NotPass   |
            | 89011C       | 54         | DL           | 3650            | 92015C         | 54             | NotPass   |
            # 測試不同牙
            | 89011C       | 54         | DL           | 3650            | 92013C         | 55             | Pass      |
            | 89011C       | 54         | DL           | 3650            | 92014C         | 55             | Pass      |
            | 89011C       | 54         | DL           | 3650            | 92015C         | 55             | Pass      |