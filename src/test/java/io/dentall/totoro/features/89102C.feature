@nhi @nhi-89-series
Feature: 89102C 特殊狀況之銀粉充填 -雙面

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
            | 89102C       | 14         | MOB          | Pass      |

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
            | 89102C       | 14         | MOB          | Pass      |

    Scenario Outline: 限制牙面在 2 以上
        Given 建立醫師
        Given Wind 24 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 限制牙面在 2 以上，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            | 89102C       | 51         | MOB          | Pass      |
            | 89102C       | 51         | DL           | Pass      |
            | 89102C       | 51         | O            | NotPass   |
            | 89102C       | 51         |              | NotPass   |

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
            | 89102C       | 54         | MOB          | 15                | 01271C           | 54             | 30                 | NotPass   |
            # 測試TreatmentNhiCode為非指定代碼且不同牙位
            | 89102C       | 54         | MOB          | 15                | 01271C           | 55             | 30                 | NotPass   |
            # 測試89006C在超過30天前建立且同牙位
            | 89102C       | 54         | MOB          | 15                | 01271C           | 54             | 31                 | Pass      |
            # 測試89006C在超過30天前建立且不同牙位
            | 89102C       | 54         | MOB          | 15                | 01271C           | 55             | 31                 | Pass      |
            # 測試TreatmentNhiCode是產生在89006C之後，但在30天內且同牙位
            | 89102C       | 54         | MOB          | 29                | 90001C           | 54             | 30                 | Pass      |
            | 89102C       | 54         | MOB          | 29                | 90002C           | 54             | 30                 | Pass      |
            | 89102C       | 54         | MOB          | 29                | 90003C           | 54             | 30                 | Pass      |
            | 89102C       | 54         | MOB          | 29                | 90019C           | 54             | 30                 | Pass      |
            | 89102C       | 54         | MOB          | 29                | 90020C           | 54             | 30                 | Pass      |
            # 測試TreatmentNhiCode是產生在89006C之後，但在30天內且不同牙位
            | 89102C       | 54         | MOB          | 29                | 90001C           | 55             | 30                 | NotPass   |
            | 89102C       | 54         | MOB          | 29                | 90002C           | 55             | 30                 | NotPass   |
            | 89102C       | 54         | MOB          | 29                | 90003C           | 55             | 30                 | NotPass   |
            | 89102C       | 54         | MOB          | 29                | 90019C           | 55             | 30                 | NotPass   |
            | 89102C       | 54         | MOB          | 29                | 90020C           | 55             | 30                 | NotPass   |
            # 測試TreatmentNhiCode是產生在89006C之前，但超過30天且同牙位
            | 89102C       | 54         | MOB          | 31                | 90001C           | 54             | 30                 | NotPass   |
            | 89102C       | 54         | MOB          | 31                | 90002C           | 54             | 30                 | NotPass   |
            | 89102C       | 54         | MOB          | 31                | 90003C           | 54             | 30                 | NotPass   |
            | 89102C       | 54         | MOB          | 31                | 90019C           | 54             | 30                 | NotPass   |
            | 89102C       | 54         | MOB          | 31                | 90020C           | 54             | 30                 | NotPass   |
            # 測試TreatmentNhiCode是產生在89006C之前，但超過30天且不同牙位
            | 89102C       | 54         | MOB          | 31                | 90001C           | 55             | 30                 | NotPass   |
            | 89102C       | 54         | MOB          | 31                | 90002C           | 55             | 30                 | NotPass   |
            | 89102C       | 54         | MOB          | 31                | 90003C           | 55             | 30                 | NotPass   |
            | 89102C       | 54         | MOB          | 31                | 90019C           | 55             | 30                 | NotPass   |
            | 89102C       | 54         | MOB          | 31                | 90020C           | 55             | 30                 | NotPass   |
            # 測試TreatmentNhiCode與89006C都在同一天且同牙位
            | 89102C       | 54         | MOB          | 30                | 90001C           | 54             | 30                 | NotPass   |
            | 89102C       | 54         | MOB          | 30                | 90002C           | 54             | 30                 | NotPass   |
            | 89102C       | 54         | MOB          | 30                | 90003C           | 54             | 30                 | NotPass   |
            | 89102C       | 54         | MOB          | 30                | 90019C           | 54             | 30                 | NotPass   |
            | 89102C       | 54         | MOB          | 30                | 90020C           | 54             | 30                 | NotPass   |
            # 測試TreatmentNhiCode與89006C都在同一天且不同牙位
            | 89102C       | 54         | MOB          | 30                | 90001C           | 55             | 30                 | NotPass   |
            | 89102C       | 54         | MOB          | 30                | 90002C           | 55             | 30                 | NotPass   |
            | 89102C       | 54         | MOB          | 30                | 90003C           | 55             | 30                 | NotPass   |
            | 89102C       | 54         | MOB          | 30                | 90019C           | 55             | 30                 | NotPass   |
            | 89102C       | 54         | MOB          | 30                | 90020C           | 55             | 30                 | NotPass   |

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
            | 89102C       | 54         | MOB          | 15              | 01271C         | 54           | 30                    | NotPass   |
            # 測試TreatmentNhiCode為非指定代碼且不同牙位
            | 89102C       | 54         | MOB          | 15              | 01271C         | 55           | 30                    | NotPass   |
            # 測試89006C在超過30天前建立且同牙位
            | 89102C       | 54         | MOB          | 15              | 01271C         | 54           | 31                    | Pass      |
            # 測試89006C在超過30天前建立且不同牙位
            | 89102C       | 54         | MOB          | 15              | 01271C         | 55           | 31                    | Pass      |
            # 測試TreatmentNhiCode是產生在89006C之後，但在30天內且同牙位
            | 89102C       | 54         | MOB          | 29              | 90001C         | 54           | 30                    | Pass      |
            | 89102C       | 54         | MOB          | 29              | 90002C         | 54           | 30                    | Pass      |
            | 89102C       | 54         | MOB          | 29              | 90003C         | 54           | 30                    | Pass      |
            | 89102C       | 54         | MOB          | 29              | 90019C         | 54           | 30                    | Pass      |
            | 89102C       | 54         | MOB          | 29              | 90020C         | 54           | 30                    | Pass      |
            # 測試TreatmentNhiCode是產生在89006C之後，但在30天內且不同牙位
            | 89102C       | 54         | MOB          | 29              | 90001C         | 55           | 30                    | NotPass   |
            | 89102C       | 54         | MOB          | 29              | 90002C         | 55           | 30                    | NotPass   |
            | 89102C       | 54         | MOB          | 29              | 90003C         | 55           | 30                    | NotPass   |
            | 89102C       | 54         | MOB          | 29              | 90019C         | 55           | 30                    | NotPass   |
            | 89102C       | 54         | MOB          | 29              | 90020C         | 55           | 30                    | NotPass   |
            # 測試TreatmentNhiCode是產生在89006C之前，但超過30天且同牙位
            | 89102C       | 54         | MOB          | 31              | 90001C         | 54           | 30                    | NotPass   |
            | 89102C       | 54         | MOB          | 31              | 90002C         | 54           | 30                    | NotPass   |
            | 89102C       | 54         | MOB          | 31              | 90003C         | 54           | 30                    | NotPass   |
            | 89102C       | 54         | MOB          | 31              | 90019C         | 54           | 30                    | NotPass   |
            | 89102C       | 54         | MOB          | 31              | 90020C         | 54           | 30                    | NotPass   |
            # 測試TreatmentNhiCode是產生在89006C之前，但超過30天且不同牙位
            | 89102C       | 54         | MOB          | 31              | 90001C         | 55           | 30                    | NotPass   |
            | 89102C       | 54         | MOB          | 31              | 90002C         | 55           | 30                    | NotPass   |
            | 89102C       | 54         | MOB          | 31              | 90003C         | 55           | 30                    | NotPass   |
            | 89102C       | 54         | MOB          | 31              | 90019C         | 55           | 30                    | NotPass   |
            | 89102C       | 54         | MOB          | 31              | 90020C         | 55           | 30                    | NotPass   |
            # 測試TreatmentNhiCode與89006C都在同一天且同牙位
            | 89102C       | 54         | MOB          | 30              | 90001C         | 54           | 30                    | NotPass   |
            | 89102C       | 54         | MOB          | 30              | 90002C         | 54           | 30                    | NotPass   |
            | 89102C       | 54         | MOB          | 30              | 90003C         | 54           | 30                    | NotPass   |
            | 89102C       | 54         | MOB          | 30              | 90019C         | 54           | 30                    | NotPass   |
            | 89102C       | 54         | MOB          | 30              | 90020C         | 54           | 30                    | NotPass   |
            # 測試TreatmentNhiCode與89006C都在同一天且不同牙位
            | 89102C       | 54         | MOB          | 30              | 90001C         | 55           | 30                    | NotPass   |
            | 89102C       | 54         | MOB          | 30              | 90002C         | 55           | 30                    | NotPass   |
            | 89102C       | 54         | MOB          | 30              | 90003C         | 55           | 30                    | NotPass   |
            | 89102C       | 54         | MOB          | 30              | 90019C         | 55           | 30                    | NotPass   |
            | 89102C       | 54         | MOB          | 30              | 90020C         | 55           | 30                    | NotPass   |

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
            | 89102C       | 51         | DL           | Pass      |
            | 89102C       | 52         | DL           | Pass      |
            | 89102C       | 53         | DL           | Pass      |
            | 89102C       | 54         | DL           | Pass      |
            | 89102C       | 55         | DL           | Pass      |
            | 89102C       | 61         | DL           | Pass      |
            | 89102C       | 62         | DL           | Pass      |
            | 89102C       | 63         | DL           | Pass      |
            | 89102C       | 64         | DL           | Pass      |
            | 89102C       | 65         | DL           | Pass      |
            | 89102C       | 71         | DL           | Pass      |
            | 89102C       | 72         | DL           | Pass      |
            | 89102C       | 73         | DL           | Pass      |
            | 89102C       | 74         | DL           | Pass      |
            | 89102C       | 75         | DL           | Pass      |
            | 89102C       | 81         | DL           | Pass      |
            | 89102C       | 82         | DL           | Pass      |
            | 89102C       | 83         | DL           | Pass      |
            | 89102C       | 84         | DL           | Pass      |
            | 89102C       | 85         | DL           | Pass      |
            # 恆牙
            | 89102C       | 11         | DL           | Pass      |
            | 89102C       | 12         | DL           | Pass      |
            | 89102C       | 13         | DL           | Pass      |
            | 89102C       | 14         | DL           | Pass      |
            | 89102C       | 15         | DL           | Pass      |
            | 89102C       | 16         | DL           | Pass      |
            | 89102C       | 17         | DL           | Pass      |
            | 89102C       | 18         | DL           | Pass      |
            | 89102C       | 21         | DL           | Pass      |
            | 89102C       | 22         | DL           | Pass      |
            | 89102C       | 23         | DL           | Pass      |
            | 89102C       | 24         | DL           | Pass      |
            | 89102C       | 25         | DL           | Pass      |
            | 89102C       | 26         | DL           | Pass      |
            | 89102C       | 27         | DL           | Pass      |
            | 89102C       | 28         | DL           | Pass      |
            | 89102C       | 31         | DL           | Pass      |
            | 89102C       | 32         | DL           | Pass      |
            | 89102C       | 33         | DL           | Pass      |
            | 89102C       | 34         | DL           | Pass      |
            | 89102C       | 35         | DL           | Pass      |
            | 89102C       | 36         | DL           | Pass      |
            | 89102C       | 37         | DL           | Pass      |
            | 89102C       | 38         | DL           | Pass      |
            | 89102C       | 41         | DL           | Pass      |
            | 89102C       | 42         | DL           | Pass      |
            | 89102C       | 43         | DL           | Pass      |
            | 89102C       | 44         | DL           | Pass      |
            | 89102C       | 45         | DL           | Pass      |
            | 89102C       | 46         | DL           | Pass      |
            | 89102C       | 47         | DL           | Pass      |
            | 89102C       | 48         | DL           | Pass      |
            # 無牙
            | 89102C       |            | DL           | NotPass   |
            #
            | 89102C       | 19         | DL           | Pass      |
            | 89102C       | 29         | DL           | Pass      |
            | 89102C       | 39         | DL           | Pass      |
            | 89102C       | 49         | DL           | Pass      |
            | 89102C       | 59         | DL           | NotPass   |
            | 89102C       | 69         | DL           | NotPass   |
            | 89102C       | 79         | DL           | NotPass   |
            | 89102C       | 89         | DL           | NotPass   |
            | 89102C       | 99         | DL           | Pass      |
            # 牙位為區域型態
            | 89102C       | FM         | DL           | NotPass   |
            | 89102C       | UR         | DL           | NotPass   |
            | 89102C       | UL         | DL           | NotPass   |
            | 89102C       | UA         | DL           | NotPass   |
            | 89102C       | UB         | DL           | NotPass   |
            | 89102C       | LL         | DL           | NotPass   |
            | 89102C       | LR         | DL           | NotPass   |
            | 89102C       | LA         | DL           | NotPass   |
            | 89102C       | LB         | DL           | NotPass   |
            # 非法牙位
            | 89102C       | 00         | DL           | NotPass   |
            | 89102C       | 01         | DL           | NotPass   |
            | 89102C       | 10         | DL           | NotPass   |
            | 89102C       | 56         | DL           | NotPass   |
            | 89102C       | 66         | DL           | NotPass   |
            | 89102C       | 76         | DL           | NotPass   |
            | 89102C       | 86         | DL           | NotPass   |
            | 89102C       | 91         | DL           | NotPass   |

