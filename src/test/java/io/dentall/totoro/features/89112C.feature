@nhi @nhi-89-series
Feature: 89112C 特殊狀況之前牙三面複合樹脂充填

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
            | 89112C       | 11         | MOB          | Pass      |

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
            | 89112C       | 11         | MOB          | Pass      |

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
            | 89112C       | 51         | MOB          | Pass      |
            | 89112C       | 51         | DL           | NotPass   |
            | 89112C       | 51         | O            | NotPass   |
            | 89112C       | 51         |              | NotPass   |

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
            | 89112C       | 11         | MOB          | 15                | 01271C           | 11             | 30                 | NotPass   |
            # 測試TreatmentNhiCode為非指定代碼且不同牙位
            | 89112C       | 11         | MOB          | 15                | 01271C           | 12             | 30                 | NotPass   |
            # 測試89006C在超過30天前建立且同牙位
            | 89112C       | 11         | MOB          | 15                | 01271C           | 11             | 31                 | Pass      |
            # 測試89006C在超過30天前建立且不同牙位
            | 89112C       | 11         | MOB          | 15                | 01271C           | 12             | 31                 | Pass      |
            # 測試TreatmentNhiCode是產生在89006C之後，但在30天內且同牙位
            | 89112C       | 11         | MOB          | 29                | 90001C           | 11             | 30                 | Pass      |
            | 89112C       | 11         | MOB          | 29                | 90002C           | 11             | 30                 | Pass      |
            | 89112C       | 11         | MOB          | 29                | 90003C           | 11             | 30                 | Pass      |
            | 89112C       | 11         | MOB          | 29                | 90019C           | 11             | 30                 | Pass      |
            | 89112C       | 11         | MOB          | 29                | 90020C           | 11             | 30                 | Pass      |
            # 測試TreatmentNhiCode是產生在89006C之後，但在30天內且不同牙位
            | 89112C       | 11         | MOB          | 29                | 90001C           | 12             | 30                 | NotPass   |
            | 89112C       | 11         | MOB          | 29                | 90002C           | 12             | 30                 | NotPass   |
            | 89112C       | 11         | MOB          | 29                | 90003C           | 12             | 30                 | NotPass   |
            | 89112C       | 11         | MOB          | 29                | 90019C           | 12             | 30                 | NotPass   |
            | 89112C       | 11         | MOB          | 29                | 90020C           | 12             | 30                 | NotPass   |
            # 測試TreatmentNhiCode是產生在89006C之前，但超過30天且同牙位
            | 89112C       | 11         | MOB          | 31                | 90001C           | 11             | 30                 | NotPass   |
            | 89112C       | 11         | MOB          | 31                | 90002C           | 11             | 30                 | NotPass   |
            | 89112C       | 11         | MOB          | 31                | 90003C           | 11             | 30                 | NotPass   |
            | 89112C       | 11         | MOB          | 31                | 90019C           | 11             | 30                 | NotPass   |
            | 89112C       | 11         | MOB          | 31                | 90020C           | 11             | 30                 | NotPass   |
            # 測試TreatmentNhiCode是產生在89006C之前，但超過30天且不同牙位
            | 89112C       | 11         | MOB          | 31                | 90001C           | 12             | 30                 | NotPass   |
            | 89112C       | 11         | MOB          | 31                | 90002C           | 12             | 30                 | NotPass   |
            | 89112C       | 11         | MOB          | 31                | 90003C           | 12             | 30                 | NotPass   |
            | 89112C       | 11         | MOB          | 31                | 90019C           | 12             | 30                 | NotPass   |
            | 89112C       | 11         | MOB          | 31                | 90020C           | 12             | 30                 | NotPass   |
            # 測試TreatmentNhiCode與89006C都在同一天且同牙位
            | 89112C       | 11         | MOB          | 30                | 90001C           | 11             | 30                 | NotPass   |
            | 89112C       | 11         | MOB          | 30                | 90002C           | 11             | 30                 | NotPass   |
            | 89112C       | 11         | MOB          | 30                | 90003C           | 11             | 30                 | NotPass   |
            | 89112C       | 11         | MOB          | 30                | 90019C           | 11             | 30                 | NotPass   |
            | 89112C       | 11         | MOB          | 30                | 90020C           | 11             | 30                 | NotPass   |
            # 測試TreatmentNhiCode與89006C都在同一天且不同牙位
            | 89112C       | 11         | MOB          | 30                | 90001C           | 12             | 30                 | NotPass   |
            | 89112C       | 11         | MOB          | 30                | 90002C           | 12             | 30                 | NotPass   |
            | 89112C       | 11         | MOB          | 30                | 90003C           | 12             | 30                 | NotPass   |
            | 89112C       | 11         | MOB          | 30                | 90019C           | 12             | 30                 | NotPass   |
            | 89112C       | 11         | MOB          | 30                | 90020C           | 12             | 30                 | NotPass   |

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
            | 89112C       | 11         | MOB          | 15              | 01271C         | 11           | 30                    | NotPass   |
            # 測試TreatmentNhiCode為非指定代碼且不同牙位
            | 89112C       | 11         | MOB          | 15              | 01271C         | 12           | 30                    | NotPass   |
            # 測試89006C在超過30天前建立且同牙位
            | 89112C       | 11         | MOB          | 15              | 01271C         | 11           | 31                    | Pass      |
            # 測試89006C在超過30天前建立且不同牙位
            | 89112C       | 11         | MOB          | 15              | 01271C         | 12           | 31                    | Pass      |
            # 測試TreatmentNhiCode是產生在89006C之後，但在30天內且同牙位
            | 89112C       | 11         | MOB          | 29              | 90001C         | 11           | 30                    | Pass      |
            | 89112C       | 11         | MOB          | 29              | 90002C         | 11           | 30                    | Pass      |
            | 89112C       | 11         | MOB          | 29              | 90003C         | 11           | 30                    | Pass      |
            | 89112C       | 11         | MOB          | 29              | 90019C         | 11           | 30                    | Pass      |
            | 89112C       | 11         | MOB          | 29              | 90020C         | 11           | 30                    | Pass      |
            # 測試TreatmentNhiCode是產生在89006C之後，但在30天內且不同牙位
            | 89112C       | 11         | MOB          | 29              | 90001C         | 12           | 30                    | NotPass   |
            | 89112C       | 11         | MOB          | 29              | 90002C         | 12           | 30                    | NotPass   |
            | 89112C       | 11         | MOB          | 29              | 90003C         | 12           | 30                    | NotPass   |
            | 89112C       | 11         | MOB          | 29              | 90019C         | 12           | 30                    | NotPass   |
            | 89112C       | 11         | MOB          | 29              | 90020C         | 12           | 30                    | NotPass   |
            # 測試TreatmentNhiCode是產生在89006C之前，但超過30天且同牙位
            | 89112C       | 11         | MOB          | 31              | 90001C         | 11           | 30                    | NotPass   |
            | 89112C       | 11         | MOB          | 31              | 90002C         | 11           | 30                    | NotPass   |
            | 89112C       | 11         | MOB          | 31              | 90003C         | 11           | 30                    | NotPass   |
            | 89112C       | 11         | MOB          | 31              | 90019C         | 11           | 30                    | NotPass   |
            | 89112C       | 11         | MOB          | 31              | 90020C         | 11           | 30                    | NotPass   |
            # 測試TreatmentNhiCode是產生在89006C之前，但超過30天且不同牙位
            | 89112C       | 11         | MOB          | 31              | 90001C         | 12           | 30                    | NotPass   |
            | 89112C       | 11         | MOB          | 31              | 90002C         | 12           | 30                    | NotPass   |
            | 89112C       | 11         | MOB          | 31              | 90003C         | 12           | 30                    | NotPass   |
            | 89112C       | 11         | MOB          | 31              | 90019C         | 12           | 30                    | NotPass   |
            | 89112C       | 11         | MOB          | 31              | 90020C         | 12           | 30                    | NotPass   |
            # 測試TreatmentNhiCode與89006C都在同一天且同牙位
            | 89112C       | 11         | MOB          | 30              | 90001C         | 11           | 30                    | NotPass   |
            | 89112C       | 11         | MOB          | 30              | 90002C         | 11           | 30                    | NotPass   |
            | 89112C       | 11         | MOB          | 30              | 90003C         | 11           | 30                    | NotPass   |
            | 89112C       | 11         | MOB          | 30              | 90019C         | 11           | 30                    | NotPass   |
            | 89112C       | 11         | MOB          | 30              | 90020C         | 11           | 30                    | NotPass   |
            # 測試TreatmentNhiCode與89006C都在同一天且不同牙位
            | 89112C       | 11         | MOB          | 30              | 90001C         | 12           | 30                    | NotPass   |
            | 89112C       | 11         | MOB          | 30              | 90002C         | 12           | 30                    | NotPass   |
            | 89112C       | 11         | MOB          | 30              | 90003C         | 12           | 30                    | NotPass   |
            | 89112C       | 11         | MOB          | 30              | 90019C         | 12           | 30                    | NotPass   |
            | 89112C       | 11         | MOB          | 30              | 90020C         | 12           | 30                    | NotPass   |

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
            | 89112C       | 51         | MOB          | Pass      |
            | 89112C       | 52         | MOB          | Pass      |
            | 89112C       | 53         | MOB          | Pass      |
            | 89112C       | 54         | MOB          | NotPass   |
            | 89112C       | 55         | MOB          | NotPass   |
            | 89112C       | 61         | MOB          | Pass      |
            | 89112C       | 62         | MOB          | Pass      |
            | 89112C       | 63         | MOB          | Pass      |
            | 89112C       | 64         | MOB          | NotPass   |
            | 89112C       | 65         | MOB          | NotPass   |
            | 89112C       | 71         | MOB          | Pass      |
            | 89112C       | 72         | MOB          | Pass      |
            | 89112C       | 73         | MOB          | Pass      |
            | 89112C       | 74         | MOB          | NotPass   |
            | 89112C       | 75         | MOB          | NotPass   |
            | 89112C       | 81         | MOB          | Pass      |
            | 89112C       | 82         | MOB          | Pass      |
            | 89112C       | 83         | MOB          | Pass      |
            | 89112C       | 84         | MOB          | NotPass   |
            | 89112C       | 85         | MOB          | NotPass   |
            # 恆牙
            | 89112C       | 11         | MOB          | Pass      |
            | 89112C       | 12         | MOB          | Pass      |
            | 89112C       | 13         | MOB          | Pass      |
            | 89112C       | 14         | MOB          | NotPass   |
            | 89112C       | 15         | MOB          | NotPass   |
            | 89112C       | 16         | MOB          | NotPass   |
            | 89112C       | 17         | MOB          | NotPass   |
            | 89112C       | 18         | MOB          | NotPass   |
            | 89112C       | 21         | MOB          | Pass      |
            | 89112C       | 22         | MOB          | Pass      |
            | 89112C       | 23         | MOB          | Pass      |
            | 89112C       | 24         | MOB          | NotPass   |
            | 89112C       | 25         | MOB          | NotPass   |
            | 89112C       | 26         | MOB          | NotPass   |
            | 89112C       | 27         | MOB          | NotPass   |
            | 89112C       | 28         | MOB          | NotPass   |
            | 89112C       | 31         | MOB          | Pass      |
            | 89112C       | 32         | MOB          | Pass      |
            | 89112C       | 33         | MOB          | Pass      |
            | 89112C       | 34         | MOB          | NotPass   |
            | 89112C       | 35         | MOB          | NotPass   |
            | 89112C       | 36         | MOB          | NotPass   |
            | 89112C       | 37         | MOB          | NotPass   |
            | 89112C       | 38         | MOB          | NotPass   |
            | 89112C       | 41         | MOB          | Pass      |
            | 89112C       | 42         | MOB          | Pass      |
            | 89112C       | 43         | MOB          | Pass      |
            | 89112C       | 44         | MOB          | NotPass   |
            | 89112C       | 45         | MOB          | NotPass   |
            | 89112C       | 46         | MOB          | NotPass   |
            | 89112C       | 47         | MOB          | NotPass   |
            | 89112C       | 48         | MOB          | NotPass   |
            # 無牙
            | 89112C       |            | MOB          | NotPass   |
            #
            | 89112C       | 19         | MOB          | Pass      |
            | 89112C       | 29         | MOB          | Pass      |
            | 89112C       | 39         | MOB          | Pass      |
            | 89112C       | 49         | MOB          | Pass      |
            | 89112C       | 59         | MOB          | NotPass   |
            | 89112C       | 69         | MOB          | NotPass   |
            | 89112C       | 79         | MOB          | NotPass   |
            | 89112C       | 89         | MOB          | NotPass   |
            | 89112C       | 99         | MOB          | Pass      |
            # 牙位為區域型態
            | 89112C       | FM         | MOB          | NotPass   |
            | 89112C       | UR         | MOB          | NotPass   |
            | 89112C       | UL         | MOB          | NotPass   |
            | 89112C       | UA         | MOB          | NotPass   |
            | 89112C       | UB         | MOB          | NotPass   |
            | 89112C       | LL         | MOB          | NotPass   |
            | 89112C       | LR         | MOB          | NotPass   |
            | 89112C       | LA         | MOB          | NotPass   |
            | 89112C       | LB         | MOB          | NotPass   |
            # 非法牙位
            | 89112C       | 00         | MOB          | NotPass   |
            | 89112C       | 01         | MOB          | NotPass   |
            | 89112C       | 10         | MOB          | NotPass   |
            | 89112C       | 56         | MOB          | NotPass   |
            | 89112C       | 66         | MOB          | NotPass   |
            | 89112C       | 76         | MOB          | NotPass   |
            | 89112C       | 86         | MOB          | NotPass   |
            | 89112C       | 91         | MOB          | NotPass   |
