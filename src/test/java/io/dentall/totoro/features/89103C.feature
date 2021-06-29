Feature: 89103C 特殊狀況之銀粉充填 -三面

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
            | 89103C       | 14         | MOB          | Pass      |

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
            | 89103C       | 14         | MOB          | Pass      |

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
            | 89103C       | 51         | MOB          | Pass      |
            | 89103C       | 51         | DL           | NotPass   |
            | 89103C       | 51         | O            | NotPass   |
            | 89103C       | 51         |              | NotPass   |

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
            | 89103C       | 54         | MOB          | 15                | 01271C           | 30                 | NotPass   |
            # 測試89006C在超過30天前建立
            | 89103C       | 54         | MOB          | 15                | 01271C           | 31                 | Pass      |
            # 測試TreatmentNhiCode是產生在89006C之後，但在30天內
            | 89103C       | 54         | MOB          | 29                | 90001C           | 30                 | Pass      |
            | 89103C       | 54         | MOB          | 29                | 90002C           | 30                 | Pass      |
            | 89103C       | 54         | MOB          | 29                | 90003C           | 30                 | Pass      |
            | 89103C       | 54         | MOB          | 29                | 90019C           | 30                 | Pass      |
            | 89103C       | 54         | MOB          | 29                | 90020C           | 30                 | Pass      |
            # 測試TreatmentNhiCode是產生在89006C之前，但超過30天
            | 89103C       | 54         | MOB          | 31                | 90001C           | 30                 | NotPass   |
            | 89103C       | 54         | MOB          | 31                | 90002C           | 30                 | NotPass   |
            | 89103C       | 54         | MOB          | 31                | 90003C           | 30                 | NotPass   |
            | 89103C       | 54         | MOB          | 31                | 90019C           | 30                 | NotPass   |
            | 89103C       | 54         | MOB          | 31                | 90020C           | 30                 | NotPass   |
            # 測試TreatmentNhiCode與89006C都在同一天
            | 89103C       | 54         | MOB          | 30                | 90001C           | 30                 | NotPass   |
            | 89103C       | 54         | MOB          | 30                | 90002C           | 30                 | NotPass   |
            | 89103C       | 54         | MOB          | 30                | 90003C           | 30                 | NotPass   |
            | 89103C       | 54         | MOB          | 30                | 90019C           | 30                 | NotPass   |
            | 89103C       | 54         | MOB          | 30                | 90020C           | 30                 | NotPass   |

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
            | 89103C       | 14         | MOB          | 15              | 01271C         | 30                    | NotPass   |
            # 測試89006C在超過30天前建立
            | 89103C       | 14         | MOB          | 15              | 01271C         | 31                    | Pass      |
            # 測試TreatmentNhiCode是產生在89006C之後，但在30天內
            | 89103C       | 14         | MOB          | 29              | 90001C         | 30                    | Pass      |
            | 89103C       | 14         | MOB          | 29              | 90002C         | 30                    | Pass      |
            | 89103C       | 14         | MOB          | 29              | 90003C         | 30                    | Pass      |
            | 89103C       | 14         | MOB          | 29              | 90019C         | 30                    | Pass      |
            | 89103C       | 14         | MOB          | 29              | 90020C         | 30                    | Pass      |
            # 測試TreatmentNhiCode是產生在89006C之前，但超過30天
            | 89103C       | 14         | MOB          | 31              | 90001C         | 30                    | NotPass   |
            | 89103C       | 14         | MOB          | 31              | 90002C         | 30                    | NotPass   |
            | 89103C       | 14         | MOB          | 31              | 90003C         | 30                    | NotPass   |
            | 89103C       | 14         | MOB          | 31              | 90019C         | 30                    | NotPass   |
            | 89103C       | 14         | MOB          | 31              | 90020C         | 30                    | NotPass   |
            # 測試TreatmentNhiCode與89006C都在同一天
            | 89103C       | 14         | MOB          | 30              | 90001C         | 30                    | NotPass   |
            | 89103C       | 14         | MOB          | 30              | 90002C         | 30                    | NotPass   |
            | 89103C       | 14         | MOB          | 30              | 90003C         | 30                    | NotPass   |
            | 89103C       | 14         | MOB          | 30              | 90019C         | 30                    | NotPass   |
            | 89103C       | 14         | MOB          | 30              | 90020C         | 30                    | NotPass   |

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
            | 89103C       | 51         | MOB          | Pass      |
            | 89103C       | 52         | MOB          | Pass      |
            | 89103C       | 53         | MOB          | Pass      |
            | 89103C       | 54         | MOB          | Pass      |
            | 89103C       | 55         | MOB          | Pass      |
            | 89103C       | 61         | MOB          | Pass      |
            | 89103C       | 62         | MOB          | Pass      |
            | 89103C       | 63         | MOB          | Pass      |
            | 89103C       | 64         | MOB          | Pass      |
            | 89103C       | 65         | MOB          | Pass      |
            | 89103C       | 71         | MOB          | Pass      |
            | 89103C       | 72         | MOB          | Pass      |
            | 89103C       | 73         | MOB          | Pass      |
            | 89103C       | 74         | MOB          | Pass      |
            | 89103C       | 75         | MOB          | Pass      |
            | 89103C       | 81         | MOB          | Pass      |
            | 89103C       | 82         | MOB          | Pass      |
            | 89103C       | 83         | MOB          | Pass      |
            | 89103C       | 84         | MOB          | Pass      |
            | 89103C       | 85         | MOB          | Pass      |
            # 恆牙
            | 89103C       | 11         | MOB          | Pass      |
            | 89103C       | 12         | MOB          | Pass      |
            | 89103C       | 13         | MOB          | Pass      |
            | 89103C       | 14         | MOB          | Pass      |
            | 89103C       | 15         | MOB          | Pass      |
            | 89103C       | 16         | MOB          | Pass      |
            | 89103C       | 17         | MOB          | Pass      |
            | 89103C       | 18         | MOB          | Pass      |
            | 89103C       | 21         | MOB          | Pass      |
            | 89103C       | 22         | MOB          | Pass      |
            | 89103C       | 23         | MOB          | Pass      |
            | 89103C       | 24         | MOB          | Pass      |
            | 89103C       | 25         | MOB          | Pass      |
            | 89103C       | 26         | MOB          | Pass      |
            | 89103C       | 27         | MOB          | Pass      |
            | 89103C       | 28         | MOB          | Pass      |
            | 89103C       | 31         | MOB          | Pass      |
            | 89103C       | 32         | MOB          | Pass      |
            | 89103C       | 33         | MOB          | Pass      |
            | 89103C       | 34         | MOB          | Pass      |
            | 89103C       | 35         | MOB          | Pass      |
            | 89103C       | 36         | MOB          | Pass      |
            | 89103C       | 37         | MOB          | Pass      |
            | 89103C       | 38         | MOB          | Pass      |
            | 89103C       | 41         | MOB          | Pass      |
            | 89103C       | 42         | MOB          | Pass      |
            | 89103C       | 43         | MOB          | Pass      |
            | 89103C       | 44         | MOB          | Pass      |
            | 89103C       | 45         | MOB          | Pass      |
            | 89103C       | 46         | MOB          | Pass      |
            | 89103C       | 47         | MOB          | Pass      |
            | 89103C       | 48         | MOB          | Pass      |
            # 無牙
            | 89103C       |            | MOB          | NotPass   |
            #
            | 89103C       | 19         | MOB          | Pass      |
            | 89103C       | 29         | MOB          | Pass      |
            | 89103C       | 39         | MOB          | Pass      |
            | 89103C       | 49         | MOB          | Pass      |
            | 89103C       | 59         | MOB          | NotPass   |
            | 89103C       | 69         | MOB          | NotPass   |
            | 89103C       | 79         | MOB          | NotPass   |
            | 89103C       | 89         | MOB          | NotPass   |
            | 89103C       | 99         | MOB          | Pass      |
            # 牙位為區域型態
            | 89103C       | FM         | MOB          | NotPass   |
            | 89103C       | UR         | MOB          | NotPass   |
            | 89103C       | UL         | MOB          | NotPass   |
            | 89103C       | UA         | MOB          | NotPass   |
            | 89103C       | LL         | MOB          | NotPass   |
            | 89103C       | LR         | MOB          | NotPass   |
            | 89103C       | LA         | MOB          | NotPass   |
            # 非法牙位
            | 89103C       | 00         | MOB          | NotPass   |
            | 89103C       | 01         | MOB          | NotPass   |
            | 89103C       | 10         | MOB          | NotPass   |
            | 89103C       | 56         | MOB          | NotPass   |
            | 89103C       | 66         | MOB          | NotPass   |
            | 89103C       | 76         | MOB          | NotPass   |
            | 89103C       | 86         | MOB          | NotPass   |
            | 89103C       | 91         | MOB          | NotPass   |
