@nhi-89-series
Feature: 89113C 特殊狀況之複合體充填

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
            | 89113C       | 11         | MOB          | Pass      |

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
            | 89113C       | 11         | MOB          | Pass      |

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
            | 89113C       | 11         | MOB          | 15                | 01271C           | 30                 | NotPass   |
            # 測試89006C在超過30天前建立
            | 89113C       | 11         | MOB          | 15                | 01271C           | 31                 | Pass      |
            # 測試TreatmentNhiCode是產生在89006C之後，但在30天內
            | 89113C       | 11         | MOB          | 29                | 90001C           | 30                 | Pass      |
            | 89113C       | 11         | MOB          | 29                | 90002C           | 30                 | Pass      |
            | 89113C       | 11         | MOB          | 29                | 90003C           | 30                 | Pass      |
            | 89113C       | 11         | MOB          | 29                | 90019C           | 30                 | Pass      |
            | 89113C       | 11         | MOB          | 29                | 90020C           | 30                 | Pass      |
            # 測試TreatmentNhiCode是產生在89006C之前，但超過30天
            | 89113C       | 11         | MOB          | 31                | 90001C           | 30                 | NotPass   |
            | 89113C       | 11         | MOB          | 31                | 90002C           | 30                 | NotPass   |
            | 89113C       | 11         | MOB          | 31                | 90003C           | 30                 | NotPass   |
            | 89113C       | 11         | MOB          | 31                | 90019C           | 30                 | NotPass   |
            | 89113C       | 11         | MOB          | 31                | 90020C           | 30                 | NotPass   |
            # 測試TreatmentNhiCode與89006C都在同一天
            | 89113C       | 11         | MOB          | 30                | 90001C           | 30                 | NotPass   |
            | 89113C       | 11         | MOB          | 30                | 90002C           | 30                 | NotPass   |
            | 89113C       | 11         | MOB          | 30                | 90003C           | 30                 | NotPass   |
            | 89113C       | 11         | MOB          | 30                | 90019C           | 30                 | NotPass   |
            | 89113C       | 11         | MOB          | 30                | 90020C           | 30                 | NotPass   |

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
            | 89113C       | 11         | MOB          | 15              | 01271C         | 30                    | NotPass   |
            # 測試89006C在超過30天前建立
            | 89113C       | 11         | MOB          | 15              | 01271C         | 31                    | Pass      |
            # 測試TreatmentNhiCode是產生在89006C之後，但在30天內
            | 89113C       | 11         | MOB          | 29              | 90001C         | 30                    | Pass      |
            | 89113C       | 11         | MOB          | 29              | 90002C         | 30                    | Pass      |
            | 89113C       | 11         | MOB          | 29              | 90003C         | 30                    | Pass      |
            | 89113C       | 11         | MOB          | 29              | 90019C         | 30                    | Pass      |
            | 89113C       | 11         | MOB          | 29              | 90020C         | 30                    | Pass      |
            # 測試TreatmentNhiCode是產生在89006C之前，但超過30天
            | 89113C       | 11         | MOB          | 31              | 90001C         | 30                    | NotPass   |
            | 89113C       | 11         | MOB          | 31              | 90002C         | 30                    | NotPass   |
            | 89113C       | 11         | MOB          | 31              | 90003C         | 30                    | NotPass   |
            | 89113C       | 11         | MOB          | 31              | 90019C         | 30                    | NotPass   |
            | 89113C       | 11         | MOB          | 31              | 90020C         | 30                    | NotPass   |
            # 測試TreatmentNhiCode與89006C都在同一天
            | 89113C       | 11         | MOB          | 30              | 90001C         | 30                    | NotPass   |
            | 89113C       | 11         | MOB          | 30              | 90002C         | 30                    | NotPass   |
            | 89113C       | 11         | MOB          | 30              | 90003C         | 30                    | NotPass   |
            | 89113C       | 11         | MOB          | 30              | 90019C         | 30                    | NotPass   |
            | 89113C       | 11         | MOB          | 30              | 90020C         | 30                    | NotPass   |

    Scenario Outline: 檢查治療的牙位是否為 PERMANENT_TOOTH
        Given 建立醫師
        Given Wind 24 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 檢查 <IssueTeeth> 牙位，依 PERMANENT_TOOTH 判定是否為核可牙位，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            # 乳牙
            | 89113C       | 51         | DL           | NotPass   |
            | 89113C       | 52         | DL           | NotPass   |
            | 89113C       | 53         | DL           | NotPass   |
            | 89113C       | 54         | DL           | NotPass   |
            | 89113C       | 55         | DL           | NotPass   |
            | 89113C       | 61         | DL           | NotPass   |
            | 89113C       | 62         | DL           | NotPass   |
            | 89113C       | 63         | DL           | NotPass   |
            | 89113C       | 64         | DL           | NotPass   |
            | 89113C       | 65         | DL           | NotPass   |
            | 89113C       | 71         | DL           | NotPass   |
            | 89113C       | 72         | DL           | NotPass   |
            | 89113C       | 73         | DL           | NotPass   |
            | 89113C       | 74         | DL           | NotPass   |
            | 89113C       | 75         | DL           | NotPass   |
            | 89113C       | 81         | DL           | NotPass   |
            | 89113C       | 82         | DL           | NotPass   |
            | 89113C       | 83         | DL           | NotPass   |
            | 89113C       | 84         | DL           | NotPass   |
            | 89113C       | 85         | DL           | NotPass   |
            # 恆牙
            | 89113C       | 11         | DL           | Pass      |
            | 89113C       | 12         | DL           | Pass      |
            | 89113C       | 13         | DL           | Pass      |
            | 89113C       | 14         | DL           | Pass      |
            | 89113C       | 15         | DL           | Pass      |
            | 89113C       | 16         | DL           | Pass      |
            | 89113C       | 17         | DL           | Pass      |
            | 89113C       | 18         | DL           | Pass      |
            | 89113C       | 21         | DL           | Pass      |
            | 89113C       | 22         | DL           | Pass      |
            | 89113C       | 23         | DL           | Pass      |
            | 89113C       | 24         | DL           | Pass      |
            | 89113C       | 25         | DL           | Pass      |
            | 89113C       | 26         | DL           | Pass      |
            | 89113C       | 27         | DL           | Pass      |
            | 89113C       | 28         | DL           | Pass      |
            | 89113C       | 31         | DL           | Pass      |
            | 89113C       | 32         | DL           | Pass      |
            | 89113C       | 33         | DL           | Pass      |
            | 89113C       | 34         | DL           | Pass      |
            | 89113C       | 35         | DL           | Pass      |
            | 89113C       | 36         | DL           | Pass      |
            | 89113C       | 37         | DL           | Pass      |
            | 89113C       | 38         | DL           | Pass      |
            | 89113C       | 41         | DL           | Pass      |
            | 89113C       | 42         | DL           | Pass      |
            | 89113C       | 43         | DL           | Pass      |
            | 89113C       | 44         | DL           | Pass      |
            | 89113C       | 45         | DL           | Pass      |
            | 89113C       | 46         | DL           | Pass      |
            | 89113C       | 47         | DL           | Pass      |
            | 89113C       | 48         | DL           | Pass      |
            # 無牙
            | 89113C       |            | DL           | NotPass   |
            #
            | 89113C       | 19         | DL           | Pass      |
            | 89113C       | 29         | DL           | Pass      |
            | 89113C       | 39         | DL           | Pass      |
            | 89113C       | 49         | DL           | Pass      |
            | 89113C       | 59         | DL           | NotPass   |
            | 89113C       | 69         | DL           | NotPass   |
            | 89113C       | 79         | DL           | NotPass   |
            | 89113C       | 89         | DL           | NotPass   |
            | 89113C       | 99         | DL           | Pass      |
            # 牙位為區域型態
            | 89113C       | FM         | DL           | NotPass   |
            | 89113C       | UR         | DL           | NotPass   |
            | 89113C       | UL         | DL           | NotPass   |
            | 89113C       | UA         | DL           | NotPass   |
            | 89113C       | UB         | DL           | NotPass   |
            | 89113C       | LL         | DL           | NotPass   |
            | 89113C       | LR         | DL           | NotPass   |
            | 89113C       | LA         | DL           | NotPass   |
            | 89113C       | LB         | DL           | NotPass   |
            # 非法牙位
            | 89113C       | 00         | DL           | NotPass   |
            | 89113C       | 01         | DL           | NotPass   |
            | 89113C       | 10         | DL           | NotPass   |
            | 89113C       | 56         | DL           | NotPass   |
            | 89113C       | 66         | DL           | NotPass   |
            | 89113C       | 76         | DL           | NotPass   |
            | 89113C       | 86         | DL           | NotPass   |
            | 89113C       | 91         | DL           | NotPass   |
