@nhi @nhi-89-series
Feature: 89105C 特殊狀況之前牙複合樹脂充填-雙面

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
            | 89105C       | 11         | MOB          | Pass      |

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
            | 89105C       | 11         | MOB          | Pass      |

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
            | 89105C       | 11         | MOB          | Pass      |
            | 89105C       | 11         | DL           | Pass      |
            | 89105C       | 11         | O            | NotPass   |
            | 89105C       | 11         |              | NotPass   |

    Scenario Outline: （HIS）前30天內不得有89006C，但如果這中間有90001C, 90002C, 90003C, 90019C, 90020C則例外
        Given 建立醫師
        Given Wind 24 歲病人
        Given 在過去第 <89006CTreatmentDay> 天，建立預約
        Given 在過去第 <89006CTreatmentDay> 天，建立掛號
        Given 在過去第 <89006CTreatmentDay> 天，產生診療計畫
        And 新增診療代碼:
            | PastDays             | A72 | A73    | A74 | A75 | A76 | A77 | A78 | A79 |
            | <89006CTreatmentDay> | 3   | 89006C | 54  | MOB | 0   | 1.0 | 03  |     |
        Given 在過去第 <PastTreatmentDays> 天，建立預約
        Given 在過去第 <PastTreatmentDays> 天，建立掛號
        Given 在過去第 <PastTreatmentDays> 天，產生診療計畫
        And 新增診療代碼:
            | PastDays            | A72 | A73                | A74 | A75 | A76 | A77 | A78 | A79 |
            | <PastTreatmentDays> | 3   | <TreatmentNhiCode> | 54  | MOB | 0   | 1.0 | 03  |     |
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
            | 89105C       | 11         | MOB          | 15                | 01271C           | 30                 | NotPass   |
            # 測試89006C在超過30天前建立
            | 89105C       | 11         | MOB          | 15                | 01271C           | 31                 | Pass      |
            # 測試TreatmentNhiCode是產生在89006C之後，但在30天內
            | 89105C       | 11         | MOB          | 29                | 90001C           | 30                 | Pass      |
            | 89105C       | 11         | MOB          | 29                | 90002C           | 30                 | Pass      |
            | 89105C       | 11         | MOB          | 29                | 90003C           | 30                 | Pass      |
            | 89105C       | 11         | MOB          | 29                | 90019C           | 30                 | Pass      |
            | 89105C       | 11         | MOB          | 29                | 90020C           | 30                 | Pass      |
            # 測試TreatmentNhiCode是產生在89006C之前，但超過30天
            | 89105C       | 11         | MOB          | 31                | 90001C           | 30                 | NotPass   |
            | 89105C       | 11         | MOB          | 31                | 90002C           | 30                 | NotPass   |
            | 89105C       | 11         | MOB          | 31                | 90003C           | 30                 | NotPass   |
            | 89105C       | 11         | MOB          | 31                | 90019C           | 30                 | NotPass   |
            | 89105C       | 11         | MOB          | 31                | 90020C           | 30                 | NotPass   |
            # 測試TreatmentNhiCode與89006C都在同一天
            | 89105C       | 11         | MOB          | 30                | 90001C           | 30                 | NotPass   |
            | 89105C       | 11         | MOB          | 30                | 90002C           | 30                 | NotPass   |
            | 89105C       | 11         | MOB          | 30                | 90003C           | 30                 | NotPass   |
            | 89105C       | 11         | MOB          | 30                | 90019C           | 30                 | NotPass   |
            | 89105C       | 11         | MOB          | 30                | 90020C           | 30                 | NotPass   |

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
            | 89105C       | 11         | MOB          | 15              | 01271C         | 30                    | NotPass   |
            # 測試89006C在超過30天前建立
            | 89105C       | 11         | MOB          | 15              | 01271C         | 31                    | Pass      |
            # 測試TreatmentNhiCode是產生在89006C之後，但在30天內
            | 89105C       | 11         | MOB          | 29              | 90001C         | 30                    | Pass      |
            | 89105C       | 11         | MOB          | 29              | 90002C         | 30                    | Pass      |
            | 89105C       | 11         | MOB          | 29              | 90003C         | 30                    | Pass      |
            | 89105C       | 11         | MOB          | 29              | 90019C         | 30                    | Pass      |
            | 89105C       | 11         | MOB          | 29              | 90020C         | 30                    | Pass      |
            # 測試TreatmentNhiCode是產生在89006C之前，但超過30天
            | 89105C       | 11         | MOB          | 31              | 90001C         | 30                    | NotPass   |
            | 89105C       | 11         | MOB          | 31              | 90002C         | 30                    | NotPass   |
            | 89105C       | 11         | MOB          | 31              | 90003C         | 30                    | NotPass   |
            | 89105C       | 11         | MOB          | 31              | 90019C         | 30                    | NotPass   |
            | 89105C       | 11         | MOB          | 31              | 90020C         | 30                    | NotPass   |
            # 測試TreatmentNhiCode與89006C都在同一天
            | 89105C       | 11         | MOB          | 30              | 90001C         | 30                    | NotPass   |
            | 89105C       | 11         | MOB          | 30              | 90002C         | 30                    | NotPass   |
            | 89105C       | 11         | MOB          | 30              | 90003C         | 30                    | NotPass   |
            | 89105C       | 11         | MOB          | 30              | 90019C         | 30                    | NotPass   |
            | 89105C       | 11         | MOB          | 30              | 90020C         | 30                    | NotPass   |

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
            | 89105C       | 51         | DL           | Pass      |
            | 89105C       | 52         | DL           | Pass      |
            | 89105C       | 53         | DL           | Pass      |
            | 89105C       | 54         | DL           | NotPass   |
            | 89105C       | 55         | DL           | NotPass   |
            | 89105C       | 61         | DL           | Pass      |
            | 89105C       | 62         | DL           | Pass      |
            | 89105C       | 63         | DL           | Pass      |
            | 89105C       | 64         | DL           | NotPass   |
            | 89105C       | 65         | DL           | NotPass   |
            | 89105C       | 71         | DL           | Pass      |
            | 89105C       | 72         | DL           | Pass      |
            | 89105C       | 73         | DL           | Pass      |
            | 89105C       | 74         | DL           | NotPass   |
            | 89105C       | 75         | DL           | NotPass   |
            | 89105C       | 81         | DL           | Pass      |
            | 89105C       | 82         | DL           | Pass      |
            | 89105C       | 83         | DL           | Pass      |
            | 89105C       | 84         | DL           | NotPass   |
            | 89105C       | 85         | DL           | NotPass   |
            # 恆牙
            | 89105C       | 11         | DL           | Pass      |
            | 89105C       | 12         | DL           | Pass      |
            | 89105C       | 13         | DL           | Pass      |
            | 89105C       | 14         | DL           | NotPass   |
            | 89105C       | 15         | DL           | NotPass   |
            | 89105C       | 16         | DL           | NotPass   |
            | 89105C       | 17         | DL           | NotPass   |
            | 89105C       | 18         | DL           | NotPass   |
            | 89105C       | 21         | DL           | Pass      |
            | 89105C       | 22         | DL           | Pass      |
            | 89105C       | 23         | DL           | Pass      |
            | 89105C       | 24         | DL           | NotPass   |
            | 89105C       | 25         | DL           | NotPass   |
            | 89105C       | 26         | DL           | NotPass   |
            | 89105C       | 27         | DL           | NotPass   |
            | 89105C       | 28         | DL           | NotPass   |
            | 89105C       | 31         | DL           | Pass      |
            | 89105C       | 32         | DL           | Pass      |
            | 89105C       | 33         | DL           | Pass      |
            | 89105C       | 34         | DL           | NotPass   |
            | 89105C       | 35         | DL           | NotPass   |
            | 89105C       | 36         | DL           | NotPass   |
            | 89105C       | 37         | DL           | NotPass   |
            | 89105C       | 38         | DL           | NotPass   |
            | 89105C       | 41         | DL           | Pass      |
            | 89105C       | 42         | DL           | Pass      |
            | 89105C       | 43         | DL           | Pass      |
            | 89105C       | 44         | DL           | NotPass   |
            | 89105C       | 45         | DL           | NotPass   |
            | 89105C       | 46         | DL           | NotPass   |
            | 89105C       | 47         | DL           | NotPass   |
            | 89105C       | 48         | DL           | NotPass   |
            # 無牙
            | 89105C       |            | DL           | NotPass   |
            #
            | 89105C       | 19         | DL           | Pass      |
            | 89105C       | 29         | DL           | Pass      |
            | 89105C       | 39         | DL           | Pass      |
            | 89105C       | 49         | DL           | Pass      |
            | 89105C       | 59         | DL           | NotPass   |
            | 89105C       | 69         | DL           | NotPass   |
            | 89105C       | 79         | DL           | NotPass   |
            | 89105C       | 89         | DL           | NotPass   |
            | 89105C       | 99         | DL           | Pass      |
            # 牙位為區域型態
            | 89105C       | FM         | DL           | NotPass   |
            | 89105C       | UR         | DL           | NotPass   |
            | 89105C       | UL         | DL           | NotPass   |
            | 89105C       | UA         | DL           | NotPass   |
            | 89105C       | UB         | DL           | NotPass   |
            | 89105C       | LL         | DL           | NotPass   |
            | 89105C       | LR         | DL           | NotPass   |
            | 89105C       | LA         | DL           | NotPass   |
            | 89105C       | LB         | DL           | NotPass   |
            # 非法牙位
            | 89105C       | 00         | DL           | NotPass   |
            | 89105C       | 01         | DL           | NotPass   |
            | 89105C       | 10         | DL           | NotPass   |
            | 89105C       | 56         | DL           | NotPass   |
            | 89105C       | 66         | DL           | NotPass   |
            | 89105C       | 76         | DL           | NotPass   |
            | 89105C       | 86         | DL           | NotPass   |
            | 89105C       | 91         | DL           | NotPass   |
