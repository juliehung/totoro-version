Feature: 89110C 特殊狀況之後牙複合樹脂充填-三面

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
            | 89110C       | 14         | MOB          | Pass      |

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
            | 89110C       | 14         | MOB          | Pass      |

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
            | 89110C       | 14         | MOB          | Pass      |
            | 89110C       | 14         | DL           | Pass      |
            | 89110C       | 14         | O            | Pass      |
            | 89110C       | 14         |              | NotPass   |

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
            | 89110C       | 14         | MOB          | 15                | 01271C           | 30                 | NotPass   |
            # 測試89006C在超過30天前建立
            | 89110C       | 14         | MOB          | 15                | 01271C           | 31                 | Pass      |
            # 測試TreatmentNhiCode是產生在89006C之後，但在30天內
            | 89110C       | 14         | MOB          | 29                | 90001C           | 30                 | Pass      |
            | 89110C       | 14         | MOB          | 29                | 90002C           | 30                 | Pass      |
            | 89110C       | 14         | MOB          | 29                | 90003C           | 30                 | Pass      |
            | 89110C       | 14         | MOB          | 29                | 90019C           | 30                 | Pass      |
            | 89110C       | 14         | MOB          | 29                | 90020C           | 30                 | Pass      |
            # 測試TreatmentNhiCode是產生在89006C之前，但超過30天
            | 89110C       | 14         | MOB          | 31                | 90001C           | 30                 | NotPass   |
            | 89110C       | 14         | MOB          | 31                | 90002C           | 30                 | NotPass   |
            | 89110C       | 14         | MOB          | 31                | 90003C           | 30                 | NotPass   |
            | 89110C       | 14         | MOB          | 31                | 90019C           | 30                 | NotPass   |
            | 89110C       | 14         | MOB          | 31                | 90020C           | 30                 | NotPass   |
            # 測試TreatmentNhiCode與89006C都在同一天
            | 89110C       | 14         | MOB          | 30                | 90001C           | 30                 | NotPass   |
            | 89110C       | 14         | MOB          | 30                | 90002C           | 30                 | NotPass   |
            | 89110C       | 14         | MOB          | 30                | 90003C           | 30                 | NotPass   |
            | 89110C       | 14         | MOB          | 30                | 90019C           | 30                 | NotPass   |
            | 89110C       | 14         | MOB          | 30                | 90020C           | 30                 | NotPass   |

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
            # 後恆牙
            | 89110C       | 14         | DL           | Pass      |
            | 89110C       | 15         | DL           | Pass      |
            | 89110C       | 16         | DL           | Pass      |
            | 89110C       | 17         | DL           | Pass      |
            | 89110C       | 18         | DL           | Pass      |
            | 89110C       | 24         | DL           | Pass      |
            | 89110C       | 25         | DL           | Pass      |
            | 89110C       | 26         | DL           | Pass      |
            | 89110C       | 27         | DL           | Pass      |
            | 89110C       | 28         | DL           | Pass      |
            | 89110C       | 34         | DL           | Pass      |
            | 89110C       | 35         | DL           | Pass      |
            | 89110C       | 36         | DL           | Pass      |
            | 89110C       | 37         | DL           | Pass      |
            | 89110C       | 38         | DL           | Pass      |
            | 89110C       | 44         | DL           | Pass      |
            | 89110C       | 45         | DL           | Pass      |
            | 89110C       | 46         | DL           | Pass      |
            | 89110C       | 47         | DL           | Pass      |
            | 89110C       | 48         | DL           | Pass      |
            # 後乳牙
            | 89110C       | 54         | DL           | Pass      |
            | 89110C       | 55         | DL           | Pass      |
            | 89110C       | 64         | DL           | Pass      |
            | 89110C       | 65         | DL           | Pass      |
            | 89110C       | 74         | DL           | Pass      |
            | 89110C       | 75         | DL           | Pass      |
            | 89110C       | 84         | DL           | Pass      |
            | 89110C       | 85         | DL           | Pass      |
            # 前恆牙
            | 89110C       | 11         | DL           | NotPass   |
            | 89110C       | 12         | DL           | NotPass   |
            | 89110C       | 13         | DL           | NotPass   |
            | 89110C       | 21         | DL           | NotPass   |
            | 89110C       | 22         | DL           | NotPass   |
            | 89110C       | 23         | DL           | NotPass   |
            | 89110C       | 31         | DL           | NotPass   |
            | 89110C       | 32         | DL           | NotPass   |
            | 89110C       | 33         | DL           | NotPass   |
            | 89110C       | 41         | DL           | NotPass   |
            | 89110C       | 42         | DL           | NotPass   |
            | 89110C       | 43         | DL           | NotPass   |
            # 前乳牙
            | 89110C       | 51         | DL           | NotPass   |
            | 89110C       | 52         | DL           | NotPass   |
            | 89110C       | 53         | DL           | NotPass   |
            | 89110C       | 61         | DL           | NotPass   |
            | 89110C       | 62         | DL           | NotPass   |
            | 89110C       | 63         | DL           | NotPass   |
            | 89110C       | 71         | DL           | NotPass   |
            | 89110C       | 72         | DL           | NotPass   |
            | 89110C       | 73         | DL           | NotPass   |
            | 89110C       | 81         | DL           | NotPass   |
            | 89110C       | 82         | DL           | NotPass   |
            | 89110C       | 83         | DL           | NotPass   |
            # 無牙
            | 89110C       |            | DL           | NotPass   |
            #
            | 89110C       | 19         | DL           | Pass      |
            | 89110C       | 29         | DL           | Pass      |
            | 89110C       | 39         | DL           | Pass      |
            | 89110C       | 49         | DL           | Pass      |
            | 89110C       | 59         | DL           | Pass      |
            | 89110C       | 69         | DL           | Pass      |
            | 89110C       | 79         | DL           | Pass      |
            | 89110C       | 89         | DL           | Pass      |
            | 89110C       | 99         | DL           | Pass      |
            # 牙位為區域型態
            | 89110C       | FM         | DL           | NotPass   |
            # 非法牙位
            | 89110C       | 00         | DL           | NotPass   |
            | 89110C       | 01         | DL           | NotPass   |
            | 89110C       | 10         | DL           | NotPass   |
            | 89110C       | 56         | DL           | NotPass   |
            | 89110C       | 66         | DL           | NotPass   |
            | 89110C       | 76         | DL           | NotPass   |
            | 89110C       | 86         | DL           | NotPass   |
            | 89110C       | 91         | DL           | NotPass   |
