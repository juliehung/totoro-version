@nhi @nhi-89-series
Feature: 89104C 特殊狀況之前牙複合樹脂充填-單面

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
            | 89104C       | 11         | MOB          | Pass      |

    Scenario Outline: 提醒適用於健保特殊醫療服務對象、化療、放射線治療患者
        Given 建立醫師
        Given Wind 24 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 提醒"適用於健保特殊醫療服務對象、化療、放射線治療患者"，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            | 89104C       | 11         | MOB          | Pass      |

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
            | 89104C       | 11         | MOB          | Pass      |
            | 89104C       | 11         | DL           | Pass      |
            | 89104C       | 11         | O            | Pass      |
            | 89104C       | 11         |              | NotPass   |

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
            | 89104C       | 11         | MOB          | 15                | 01271C           | 11             | 30                 | NotPass   |
            # 測試TreatmentNhiCode為非指定代碼且不同牙位
            | 89104C       | 11         | MOB          | 15                | 01271C           | 12             | 30                 | NotPass   |
            # 測試89006C在超過30天前建立且同牙位
            | 89104C       | 11         | MOB          | 15                | 01271C           | 11             | 31                 | Pass      |
            # 測試89006C在超過30天前建立且不同牙位
            | 89104C       | 11         | MOB          | 15                | 01271C           | 12             | 31                 | Pass      |
            # 測試TreatmentNhiCode是產生在89006C之後，但在30天內且同牙位
            | 89104C       | 11         | MOB          | 29                | 90001C           | 11             | 30                 | Pass      |
            | 89104C       | 11         | MOB          | 29                | 90002C           | 11             | 30                 | Pass      |
            | 89104C       | 11         | MOB          | 29                | 90003C           | 11             | 30                 | Pass      |
            | 89104C       | 11         | MOB          | 29                | 90019C           | 11             | 30                 | Pass      |
            | 89104C       | 11         | MOB          | 29                | 90020C           | 11             | 30                 | Pass      |
            # 測試TreatmentNhiCode是產生在89006C之後，但在30天內且不同牙位
            | 89104C       | 11         | MOB          | 29                | 90001C           | 12             | 30                 | NotPass   |
            | 89104C       | 11         | MOB          | 29                | 90002C           | 12             | 30                 | NotPass   |
            | 89104C       | 11         | MOB          | 29                | 90003C           | 12             | 30                 | NotPass   |
            | 89104C       | 11         | MOB          | 29                | 90019C           | 12             | 30                 | NotPass   |
            | 89104C       | 11         | MOB          | 29                | 90020C           | 12             | 30                 | NotPass   |
            # 測試TreatmentNhiCode是產生在89006C之前，但超過30天且同牙位
            | 89104C       | 11         | MOB          | 31                | 90001C           | 11             | 30                 | NotPass   |
            | 89104C       | 11         | MOB          | 31                | 90002C           | 11             | 30                 | NotPass   |
            | 89104C       | 11         | MOB          | 31                | 90003C           | 11             | 30                 | NotPass   |
            | 89104C       | 11         | MOB          | 31                | 90019C           | 11             | 30                 | NotPass   |
            | 89104C       | 11         | MOB          | 31                | 90020C           | 11             | 30                 | NotPass   |
            # 測試TreatmentNhiCode是產生在89006C之前，但超過30天且不同牙位
            | 89104C       | 11         | MOB          | 31                | 90001C           | 12             | 30                 | NotPass   |
            | 89104C       | 11         | MOB          | 31                | 90002C           | 12             | 30                 | NotPass   |
            | 89104C       | 11         | MOB          | 31                | 90003C           | 12             | 30                 | NotPass   |
            | 89104C       | 11         | MOB          | 31                | 90019C           | 12             | 30                 | NotPass   |
            | 89104C       | 11         | MOB          | 31                | 90020C           | 12             | 30                 | NotPass   |
            # 測試TreatmentNhiCode與89006C都在同一天且同牙位
            | 89104C       | 11         | MOB          | 30                | 90001C           | 11             | 30                 | NotPass   |
            | 89104C       | 11         | MOB          | 30                | 90002C           | 11             | 30                 | NotPass   |
            | 89104C       | 11         | MOB          | 30                | 90003C           | 11             | 30                 | NotPass   |
            | 89104C       | 11         | MOB          | 30                | 90019C           | 11             | 30                 | NotPass   |
            | 89104C       | 11         | MOB          | 30                | 90020C           | 11             | 30                 | NotPass   |
            # 測試TreatmentNhiCode與89006C都在同一天且不同牙位
            | 89104C       | 11         | MOB          | 30                | 90001C           | 12             | 30                 | NotPass   |
            | 89104C       | 11         | MOB          | 30                | 90002C           | 12             | 30                 | NotPass   |
            | 89104C       | 11         | MOB          | 30                | 90003C           | 12             | 30                 | NotPass   |
            | 89104C       | 11         | MOB          | 30                | 90019C           | 12             | 30                 | NotPass   |
            | 89104C       | 11         | MOB          | 30                | 90020C           | 12             | 30                 | NotPass   |

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
            | 89104C       | 11         | MOB          | 15              | 01271C         | 11           | 30                    | NotPass   |
            # 測試TreatmentNhiCode為非指定代碼且不同牙位
            | 89104C       | 11         | MOB          | 15              | 01271C         | 12           | 30                    | NotPass   |
            # 測試89006C在超過30天前建立且同牙位
            | 89104C       | 11         | MOB          | 15              | 01271C         | 11           | 31                    | Pass      |
            # 測試89006C在超過30天前建立且不同牙位
            | 89104C       | 11         | MOB          | 15              | 01271C         | 12           | 31                    | Pass      |
            # 測試TreatmentNhiCode是產生在89006C之後，但在30天內且同牙位
            | 89104C       | 11         | MOB          | 29              | 90001C         | 11           | 30                    | Pass      |
            | 89104C       | 11         | MOB          | 29              | 90002C         | 11           | 30                    | Pass      |
            | 89104C       | 11         | MOB          | 29              | 90003C         | 11           | 30                    | Pass      |
            | 89104C       | 11         | MOB          | 29              | 90019C         | 11           | 30                    | Pass      |
            | 89104C       | 11         | MOB          | 29              | 90020C         | 11           | 30                    | Pass      |
            # 測試TreatmentNhiCode是產生在89006C之後，但在30天內且不同牙位
            | 89104C       | 11         | MOB          | 29              | 90001C         | 12           | 30                    | NotPass   |
            | 89104C       | 11         | MOB          | 29              | 90002C         | 12           | 30                    | NotPass   |
            | 89104C       | 11         | MOB          | 29              | 90003C         | 12           | 30                    | NotPass   |
            | 89104C       | 11         | MOB          | 29              | 90019C         | 12           | 30                    | NotPass   |
            | 89104C       | 11         | MOB          | 29              | 90020C         | 12           | 30                    | NotPass   |
            # 測試TreatmentNhiCode是產生在89006C之前，但超過30天且同牙位
            | 89104C       | 11         | MOB          | 31              | 90001C         | 11           | 30                    | NotPass   |
            | 89104C       | 11         | MOB          | 31              | 90002C         | 11           | 30                    | NotPass   |
            | 89104C       | 11         | MOB          | 31              | 90003C         | 11           | 30                    | NotPass   |
            | 89104C       | 11         | MOB          | 31              | 90019C         | 11           | 30                    | NotPass   |
            | 89104C       | 11         | MOB          | 31              | 90020C         | 11           | 30                    | NotPass   |
            # 測試TreatmentNhiCode是產生在89006C之前，但超過30天且不同牙位
            | 89104C       | 11         | MOB          | 31              | 90001C         | 12           | 30                    | NotPass   |
            | 89104C       | 11         | MOB          | 31              | 90002C         | 12           | 30                    | NotPass   |
            | 89104C       | 11         | MOB          | 31              | 90003C         | 12           | 30                    | NotPass   |
            | 89104C       | 11         | MOB          | 31              | 90019C         | 12           | 30                    | NotPass   |
            | 89104C       | 11         | MOB          | 31              | 90020C         | 12           | 30                    | NotPass   |
            # 測試TreatmentNhiCode與89006C都在同一天且同牙位
            | 89104C       | 11         | MOB          | 30              | 90001C         | 11           | 30                    | NotPass   |
            | 89104C       | 11         | MOB          | 30              | 90002C         | 11           | 30                    | NotPass   |
            | 89104C       | 11         | MOB          | 30              | 90003C         | 11           | 30                    | NotPass   |
            | 89104C       | 11         | MOB          | 30              | 90019C         | 11           | 30                    | NotPass   |
            | 89104C       | 11         | MOB          | 30              | 90020C         | 11           | 30                    | NotPass   |
            # 測試TreatmentNhiCode與89006C都在同一天且不同牙位
            | 89104C       | 11         | MOB          | 30              | 90001C         | 12           | 30                    | NotPass   |
            | 89104C       | 11         | MOB          | 30              | 90002C         | 12           | 30                    | NotPass   |
            | 89104C       | 11         | MOB          | 30              | 90003C         | 12           | 30                    | NotPass   |
            | 89104C       | 11         | MOB          | 30              | 90019C         | 12           | 30                    | NotPass   |
            | 89104C       | 11         | MOB          | 30              | 90020C         | 12           | 30                    | NotPass   |

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
            | 89104C       | 51         | DL           | Pass      |
            | 89104C       | 52         | DL           | Pass      |
            | 89104C       | 53         | DL           | Pass      |
            | 89104C       | 54         | DL           | NotPass   |
            | 89104C       | 55         | DL           | NotPass   |
            | 89104C       | 61         | DL           | Pass      |
            | 89104C       | 62         | DL           | Pass      |
            | 89104C       | 63         | DL           | Pass      |
            | 89104C       | 64         | DL           | NotPass   |
            | 89104C       | 65         | DL           | NotPass   |
            | 89104C       | 71         | DL           | Pass      |
            | 89104C       | 72         | DL           | Pass      |
            | 89104C       | 73         | DL           | Pass      |
            | 89104C       | 74         | DL           | NotPass   |
            | 89104C       | 75         | DL           | NotPass   |
            | 89104C       | 81         | DL           | Pass      |
            | 89104C       | 82         | DL           | Pass      |
            | 89104C       | 83         | DL           | Pass      |
            | 89104C       | 84         | DL           | NotPass   |
            | 89104C       | 85         | DL           | NotPass   |
            # 恆牙
            | 89104C       | 11         | DL           | Pass      |
            | 89104C       | 12         | DL           | Pass      |
            | 89104C       | 13         | DL           | Pass      |
            | 89104C       | 14         | DL           | NotPass   |
            | 89104C       | 15         | DL           | NotPass   |
            | 89104C       | 16         | DL           | NotPass   |
            | 89104C       | 17         | DL           | NotPass   |
            | 89104C       | 18         | DL           | NotPass   |
            | 89104C       | 21         | DL           | Pass      |
            | 89104C       | 22         | DL           | Pass      |
            | 89104C       | 23         | DL           | Pass      |
            | 89104C       | 24         | DL           | NotPass   |
            | 89104C       | 25         | DL           | NotPass   |
            | 89104C       | 26         | DL           | NotPass   |
            | 89104C       | 27         | DL           | NotPass   |
            | 89104C       | 28         | DL           | NotPass   |
            | 89104C       | 31         | DL           | Pass      |
            | 89104C       | 32         | DL           | Pass      |
            | 89104C       | 33         | DL           | Pass      |
            | 89104C       | 34         | DL           | NotPass   |
            | 89104C       | 35         | DL           | NotPass   |
            | 89104C       | 36         | DL           | NotPass   |
            | 89104C       | 37         | DL           | NotPass   |
            | 89104C       | 38         | DL           | NotPass   |
            | 89104C       | 41         | DL           | Pass      |
            | 89104C       | 42         | DL           | Pass      |
            | 89104C       | 43         | DL           | Pass      |
            | 89104C       | 44         | DL           | NotPass   |
            | 89104C       | 45         | DL           | NotPass   |
            | 89104C       | 46         | DL           | NotPass   |
            | 89104C       | 47         | DL           | NotPass   |
            | 89104C       | 48         | DL           | NotPass   |
            # 無牙
            | 89104C       |            | DL           | NotPass   |
            #
            | 89104C       | 19         | DL           | Pass      |
            | 89104C       | 29         | DL           | Pass      |
            | 89104C       | 39         | DL           | Pass      |
            | 89104C       | 49         | DL           | Pass      |
            | 89104C       | 59         | DL           | NotPass   |
            | 89104C       | 69         | DL           | NotPass   |
            | 89104C       | 79         | DL           | NotPass   |
            | 89104C       | 89         | DL           | NotPass   |
            | 89104C       | 99         | DL           | Pass      |
            # 牙位為區域型態
            | 89104C       | FM         | DL           | NotPass   |
            | 89104C       | UR         | DL           | NotPass   |
            | 89104C       | UL         | DL           | NotPass   |
            | 89104C       | UA         | DL           | NotPass   |
            | 89104C       | UB         | DL           | NotPass   |
            | 89104C       | LL         | DL           | NotPass   |
            | 89104C       | LR         | DL           | NotPass   |
            | 89104C       | LA         | DL           | NotPass   |
            | 89104C       | LB         | DL           | NotPass   |
            # 非法牙位
            | 89104C       | 00         | DL           | NotPass   |
            | 89104C       | 01         | DL           | NotPass   |
            | 89104C       | 10         | DL           | NotPass   |
            | 89104C       | 56         | DL           | NotPass   |
            | 89104C       | 66         | DL           | NotPass   |
            | 89104C       | 76         | DL           | NotPass   |
            | 89104C       | 86         | DL           | NotPass   |
            | 89104C       | 91         | DL           | NotPass   |
