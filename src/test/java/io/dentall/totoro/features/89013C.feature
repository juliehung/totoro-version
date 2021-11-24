@nhi @nhi-89-series @part3
Feature: 89013C 複合體充填

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
            | 89013C       | 11         | MOB          | Pass      |

    Scenario Outline: （HIS）545天內，同牙位不應有 89013C 診療項目
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
            | IssueNhiCode | IssueTeeth | IssueSurface | PastTreatmentDays | TreatmentNhiCode | TreatmentTeeth | DayRange | PassOrNot |
            # 同牙
            | 89013C       | 11         | DL           | 544               | 89013C           | 11             | 545      | NotPass   |
            | 89013C       | 11         | DL           | 545               | 89013C           | 11             | 545      | NotPass   |
            | 89013C       | 11         | DL           | 546               | 89013C           | 11             | 545      | Pass      |
            # 不同牙
            | 89013C       | 11         | DL           | 544               | 89013C           | 12             | 545      | Pass      |
            | 89013C       | 11         | DL           | 545               | 89013C           | 12             | 545      | Pass      |
            | 89013C       | 11         | DL           | 546               | 89013C           | 12             | 545      | Pass      |
            # 非同一個診療項目
            | 89006C       | 11         | DL           | 179               | 01271C           | 11             | 545      | Pass      |
            | 89006C       | 11         | DL           | 180               | 01271C           | 11             | 545      | Pass      |
            | 89006C       | 11         | DL           | 181               | 01271C           | 11             | 545      | Pass      |

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
            | 89013C       | 51         | DL           | NotPass   |
            | 89013C       | 52         | DL           | NotPass   |
            | 89013C       | 53         | DL           | NotPass   |
            | 89013C       | 54         | DL           | NotPass   |
            | 89013C       | 55         | DL           | NotPass   |
            | 89013C       | 61         | DL           | NotPass   |
            | 89013C       | 62         | DL           | NotPass   |
            | 89013C       | 63         | DL           | NotPass   |
            | 89013C       | 64         | DL           | NotPass   |
            | 89013C       | 65         | DL           | NotPass   |
            | 89013C       | 71         | DL           | NotPass   |
            | 89013C       | 72         | DL           | NotPass   |
            | 89013C       | 73         | DL           | NotPass   |
            | 89013C       | 74         | DL           | NotPass   |
            | 89013C       | 75         | DL           | NotPass   |
            | 89013C       | 81         | DL           | NotPass   |
            | 89013C       | 82         | DL           | NotPass   |
            | 89013C       | 83         | DL           | NotPass   |
            | 89013C       | 84         | DL           | NotPass   |
            | 89013C       | 85         | DL           | NotPass   |
            # 恆牙
            | 89013C       | 11         | DL           | Pass      |
            | 89013C       | 12         | DL           | Pass      |
            | 89013C       | 13         | DL           | Pass      |
            | 89013C       | 14         | DL           | Pass      |
            | 89013C       | 15         | DL           | Pass      |
            | 89013C       | 16         | DL           | Pass      |
            | 89013C       | 17         | DL           | Pass      |
            | 89013C       | 18         | DL           | Pass      |
            | 89013C       | 21         | DL           | Pass      |
            | 89013C       | 22         | DL           | Pass      |
            | 89013C       | 23         | DL           | Pass      |
            | 89013C       | 24         | DL           | Pass      |
            | 89013C       | 25         | DL           | Pass      |
            | 89013C       | 26         | DL           | Pass      |
            | 89013C       | 27         | DL           | Pass      |
            | 89013C       | 28         | DL           | Pass      |
            | 89013C       | 31         | DL           | Pass      |
            | 89013C       | 32         | DL           | Pass      |
            | 89013C       | 33         | DL           | Pass      |
            | 89013C       | 34         | DL           | Pass      |
            | 89013C       | 35         | DL           | Pass      |
            | 89013C       | 36         | DL           | Pass      |
            | 89013C       | 37         | DL           | Pass      |
            | 89013C       | 38         | DL           | Pass      |
            | 89013C       | 41         | DL           | Pass      |
            | 89013C       | 42         | DL           | Pass      |
            | 89013C       | 43         | DL           | Pass      |
            | 89013C       | 44         | DL           | Pass      |
            | 89013C       | 45         | DL           | Pass      |
            | 89013C       | 46         | DL           | Pass      |
            | 89013C       | 47         | DL           | Pass      |
            | 89013C       | 48         | DL           | Pass      |
            # 無牙
            | 89013C       |            | DL           | NotPass   |
            #
            | 89013C       | 19         | DL           | Pass      |
            | 89013C       | 29         | DL           | Pass      |
            | 89013C       | 39         | DL           | Pass      |
            | 89013C       | 49         | DL           | Pass      |
            | 89013C       | 59         | DL           | NotPass   |
            | 89013C       | 69         | DL           | NotPass   |
            | 89013C       | 79         | DL           | NotPass   |
            | 89013C       | 89         | DL           | NotPass   |
            | 89013C       | 99         | DL           | Pass      |
            # 牙位為區域型態
            | 89013C       | FM         | DL           | NotPass   |
            | 89013C       | UR         | DL           | NotPass   |
            | 89013C       | UL         | DL           | NotPass   |
            | 89013C       | UA         | DL           | NotPass   |
            | 89013C       | UB         | DL           | NotPass   |
            | 89013C       | LR         | DL           | NotPass   |
            | 89013C       | LL         | DL           | NotPass   |
            | 89013C       | LA         | DL           | NotPass   |
            | 89013C       | LB         | DL           | NotPass   |
            # 非法牙位
            | 89013C       | 00         | DL           | NotPass   |
            | 89013C       | 01         | DL           | NotPass   |
            | 89013C       | 10         | DL           | NotPass   |
            | 89013C       | 56         | DL           | NotPass   |
            | 89013C       | 66         | DL           | NotPass   |
            | 89013C       | 76         | DL           | NotPass   |
            | 89013C       | 86         | DL           | NotPass   |
            | 89013C       | 91         | DL           | NotPass   |
