Feature: 89111C 特殊狀況之玻璃離子體充填

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
            | 89111C       | 14         | MOB          | Pass      |

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
            | 89111C       | 14         | MOB          | Pass      |

    Scenario Outline: 前30天內不得有89006C，但如果這中間有90001C, 90002C, 90003C, 90019C, 90020C則例外
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
            | 89111C       | 54         | MOB          | 15                | 01271C           | 30                 | NotPass   |
            # 測試89006C在超過30天前建立
            | 89111C       | 54         | MOB          | 15                | 01271C           | 31                 | Pass      |
            # 測試TreatmentNhiCode是產生在89006C之後，但在30天內
            | 89111C       | 54         | MOB          | 29                | 90001C           | 30                 | Pass      |
            | 89111C       | 54         | MOB          | 29                | 90002C           | 30                 | Pass      |
            | 89111C       | 54         | MOB          | 29                | 90003C           | 30                 | Pass      |
            | 89111C       | 54         | MOB          | 29                | 90019C           | 30                 | Pass      |
            | 89111C       | 54         | MOB          | 29                | 90020C           | 30                 | Pass      |
            # 測試TreatmentNhiCode是產生在89006C之前，但超過30天
            | 89111C       | 54         | MOB          | 31                | 90001C           | 30                 | NotPass   |
            | 89111C       | 54         | MOB          | 31                | 90002C           | 30                 | NotPass   |
            | 89111C       | 54         | MOB          | 31                | 90003C           | 30                 | NotPass   |
            | 89111C       | 54         | MOB          | 31                | 90019C           | 30                 | NotPass   |
            | 89111C       | 54         | MOB          | 31                | 90020C           | 30                 | NotPass   |
            # 測試TreatmentNhiCode與89006C都在同一天
            | 89111C       | 54         | MOB          | 30                | 90001C           | 30                 | NotPass   |
            | 89111C       | 54         | MOB          | 30                | 90002C           | 30                 | NotPass   |
            | 89111C       | 54         | MOB          | 30                | 90003C           | 30                 | NotPass   |
            | 89111C       | 54         | MOB          | 30                | 90019C           | 30                 | NotPass   |
            | 89111C       | 54         | MOB          | 30                | 90020C           | 30                 | NotPass   |

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
            | 89111C       | 51         | MOB          | Pass      |
            | 89111C       | 52         | MOB          | Pass      |
            | 89111C       | 53         | MOB          | Pass      |
            | 89111C       | 54         | MOB          | Pass      |
            | 89111C       | 55         | MOB          | Pass      |
            | 89111C       | 61         | MOB          | Pass      |
            | 89111C       | 62         | MOB          | Pass      |
            | 89111C       | 63         | MOB          | Pass      |
            | 89111C       | 64         | MOB          | Pass      |
            | 89111C       | 65         | MOB          | Pass      |
            | 89111C       | 71         | MOB          | Pass      |
            | 89111C       | 72         | MOB          | Pass      |
            | 89111C       | 73         | MOB          | Pass      |
            | 89111C       | 74         | MOB          | Pass      |
            | 89111C       | 75         | MOB          | Pass      |
            | 89111C       | 81         | MOB          | Pass      |
            | 89111C       | 82         | MOB          | Pass      |
            | 89111C       | 83         | MOB          | Pass      |
            | 89111C       | 84         | MOB          | Pass      |
            | 89111C       | 85         | MOB          | Pass      |
            # 恆牙
            | 89111C       | 11         | MOB          | Pass      |
            | 89111C       | 12         | MOB          | Pass      |
            | 89111C       | 13         | MOB          | Pass      |
            | 89111C       | 14         | MOB          | Pass      |
            | 89111C       | 15         | MOB          | Pass      |
            | 89111C       | 16         | MOB          | Pass      |
            | 89111C       | 17         | MOB          | Pass      |
            | 89111C       | 18         | MOB          | Pass      |
            | 89111C       | 21         | MOB          | Pass      |
            | 89111C       | 22         | MOB          | Pass      |
            | 89111C       | 23         | MOB          | Pass      |
            | 89111C       | 24         | MOB          | Pass      |
            | 89111C       | 25         | MOB          | Pass      |
            | 89111C       | 26         | MOB          | Pass      |
            | 89111C       | 27         | MOB          | Pass      |
            | 89111C       | 28         | MOB          | Pass      |
            | 89111C       | 31         | MOB          | Pass      |
            | 89111C       | 32         | MOB          | Pass      |
            | 89111C       | 33         | MOB          | Pass      |
            | 89111C       | 34         | MOB          | Pass      |
            | 89111C       | 35         | MOB          | Pass      |
            | 89111C       | 36         | MOB          | Pass      |
            | 89111C       | 37         | MOB          | Pass      |
            | 89111C       | 38         | MOB          | Pass      |
            | 89111C       | 41         | MOB          | Pass      |
            | 89111C       | 42         | MOB          | Pass      |
            | 89111C       | 43         | MOB          | Pass      |
            | 89111C       | 44         | MOB          | Pass      |
            | 89111C       | 45         | MOB          | Pass      |
            | 89111C       | 46         | MOB          | Pass      |
            | 89111C       | 47         | MOB          | Pass      |
            | 89111C       | 48         | MOB          | Pass      |
            # 無牙
            | 89111C       |            | MOB          | NotPass   |
            #
            | 89111C       | 19         | MOB          | Pass      |
            | 89111C       | 29         | MOB          | Pass      |
            | 89111C       | 39         | MOB          | Pass      |
            | 89111C       | 49         | MOB          | Pass      |
            | 89111C       | 59         | MOB          | Pass      |
            | 89111C       | 69         | MOB          | Pass      |
            | 89111C       | 79         | MOB          | Pass      |
            | 89111C       | 89         | MOB          | Pass      |
            | 89111C       | 99         | MOB          | Pass      |
            # 牙位為區域型態
            | 89111C       | FM         | MOB          | NotPass   |
            # 非法牙位
            | 89111C       | 00         | MOB          | NotPass   |
            | 89111C       | 01         | MOB          | NotPass   |
            | 89111C       | 10         | MOB          | NotPass   |
            | 89111C       | 56         | MOB          | NotPass   |
            | 89111C       | 66         | MOB          | NotPass   |
            | 89111C       | 76         | MOB          | NotPass   |
            | 89111C       | 86         | MOB          | NotPass   |
            | 89111C       | 91         | MOB          | NotPass   |
