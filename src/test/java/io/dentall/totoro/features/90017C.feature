@nhi @nhi-90-series
Feature: 90017C 恆牙斷髓處理

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
            | 90017C       | 11         | MOB          | Pass      |

    Scenario Outline: 提醒適用於健保特殊醫療服務對象
        Given 建立醫師
        Given Wind 24 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 提醒"適用於健保特殊醫療服務對象"，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            | 90017C       | 11         | MOB          | Pass      |

    Scenario Outline: （Disposal）同日或同處置單不得申報 90001C~90003C
        Given 建立醫師
        Given Wind 24 歲病人
        Given 在 當日 ，建立預約
        Given 在 當日 ，建立掛號
        Given 在 當日 ，產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode         | NewTeeth         | NewSurface         |
            |         |       |         | <TreatmentNhiCode> | <TreatmentTeeth> | <TreatmentSurface> |
            |         |       |         | <IssueNhiCode>     | <IssueTeeth>     | <IssueSurface>     |
        Then 同日或同處置單不得申報 <TreatmentNhiCode> 診療代碼，確認結果是否為 <PassOrNot> 且檢查訊息類型為 W4_1
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | TreatmentNhiCode | TreatmentTeeth | TreatmentSurface | PassOrNot |
            | 90017C       | 11         | MO           | 90001C           | 11             | MO               | NotPass   |
            | 90017C       | 11         | MO           | 90002C           | 11             | MO               | NotPass   |
            | 90017C       | 11         | MO           | 90003C           | 11             | MO               | NotPass   |

    Scenario Outline: （HIS-Today）同日或同處置單不得申報 90001C~90003C
        Given 建立醫師
        Given Wind 24 歲病人
        Given 在 <PastTreatmentDate> ，建立預約
        Given 在 <PastTreatmentDate> ，建立掛號
        Given 在 <PastTreatmentDate> ，產生診療計畫
        And 新增診療代碼:
            | PastDate            | A72 | A73                | A74              | A75                | A76 | A77 | A78 | A79 |
            | <PastTreatmentDate> | 3   | <TreatmentNhiCode> | <TreatmentTeeth> | <TreatmentSurface> | 0   | 1.0 | 03  |     |
        Given 在 當日 ，建立預約
        Given 在 當日 ，建立掛號
        Given 在 當日 ，產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 同日或同處置單不得申報 <TreatmentNhiCode> 診療代碼，確認結果是否為 <PassOrNot> 且檢查訊息類型為 W4_1
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastTreatmentDate | TreatmentNhiCode | TreatmentTeeth | TreatmentSurface | PassOrNot |
            | 90017C       | 11         | MO           | 當日                | 90001C           | 11             | MO               | NotPass   |
            | 90017C       | 11         | MO           | 當日                | 90002C           | 11             | MO               | NotPass   |
            | 90017C       | 11         | MO           | 當日                | 90003C           | 11             | MO               | NotPass   |
            | 90017C       | 11         | MO           | 昨日                | 90001C           | 11             | MO               | Pass      |
            | 90017C       | 11         | MO           | 昨日                | 90002C           | 11             | MO               | Pass      |
            | 90017C       | 11         | MO           | 昨日                | 90003C           | 11             | MO               | Pass      |

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
            | 90017C       | 51         | DL           | NotPass   |
            | 90017C       | 52         | DL           | NotPass   |
            | 90017C       | 53         | DL           | NotPass   |
            | 90017C       | 54         | DL           | NotPass   |
            | 90017C       | 55         | DL           | NotPass   |
            | 90017C       | 61         | DL           | NotPass   |
            | 90017C       | 62         | DL           | NotPass   |
            | 90017C       | 63         | DL           | NotPass   |
            | 90017C       | 64         | DL           | NotPass   |
            | 90017C       | 65         | DL           | NotPass   |
            | 90017C       | 71         | DL           | NotPass   |
            | 90017C       | 72         | DL           | NotPass   |
            | 90017C       | 73         | DL           | NotPass   |
            | 90017C       | 74         | DL           | NotPass   |
            | 90017C       | 75         | DL           | NotPass   |
            | 90017C       | 81         | DL           | NotPass   |
            | 90017C       | 82         | DL           | NotPass   |
            | 90017C       | 83         | DL           | NotPass   |
            | 90017C       | 84         | DL           | NotPass   |
            | 90017C       | 85         | DL           | NotPass   |
            # 恆牙
            | 90017C       | 11         | DL           | Pass      |
            | 90017C       | 12         | DL           | Pass      |
            | 90017C       | 13         | DL           | Pass      |
            | 90017C       | 14         | DL           | Pass      |
            | 90017C       | 15         | DL           | Pass      |
            | 90017C       | 16         | DL           | Pass      |
            | 90017C       | 17         | DL           | Pass      |
            | 90017C       | 18         | DL           | Pass      |
            | 90017C       | 21         | DL           | Pass      |
            | 90017C       | 22         | DL           | Pass      |
            | 90017C       | 23         | DL           | Pass      |
            | 90017C       | 24         | DL           | Pass      |
            | 90017C       | 25         | DL           | Pass      |
            | 90017C       | 26         | DL           | Pass      |
            | 90017C       | 27         | DL           | Pass      |
            | 90017C       | 28         | DL           | Pass      |
            | 90017C       | 31         | DL           | Pass      |
            | 90017C       | 32         | DL           | Pass      |
            | 90017C       | 33         | DL           | Pass      |
            | 90017C       | 34         | DL           | Pass      |
            | 90017C       | 35         | DL           | Pass      |
            | 90017C       | 36         | DL           | Pass      |
            | 90017C       | 37         | DL           | Pass      |
            | 90017C       | 38         | DL           | Pass      |
            | 90017C       | 41         | DL           | Pass      |
            | 90017C       | 42         | DL           | Pass      |
            | 90017C       | 43         | DL           | Pass      |
            | 90017C       | 44         | DL           | Pass      |
            | 90017C       | 45         | DL           | Pass      |
            | 90017C       | 46         | DL           | Pass      |
            | 90017C       | 47         | DL           | Pass      |
            | 90017C       | 48         | DL           | Pass      |
            # 無牙
            | 90017C       |            | DL           | NotPass   |
            #
            | 90017C       | 19         | DL           | Pass      |
            | 90017C       | 29         | DL           | Pass      |
            | 90017C       | 39         | DL           | Pass      |
            | 90017C       | 49         | DL           | Pass      |
            | 90017C       | 59         | DL           | NotPass   |
            | 90017C       | 69         | DL           | NotPass   |
            | 90017C       | 79         | DL           | NotPass   |
            | 90017C       | 89         | DL           | NotPass   |
            | 90017C       | 99         | DL           | Pass      |
            # 牙位為區域型態
            | 90017C       | FM         | DL           | NotPass   |
            | 90017C       | UR         | DL           | NotPass   |
            | 90017C       | UL         | DL           | NotPass   |
            | 90017C       | UA         | DL           | NotPass   |
            | 90017C       | UB         | DL           | NotPass   |
            | 90017C       | LL         | DL           | NotPass   |
            | 90017C       | LR         | DL           | NotPass   |
            | 90017C       | LA         | DL           | NotPass   |
            | 90017C       | LB         | DL           | NotPass   |
            # 非法牙位
            | 90017C       | 00         | DL           | NotPass   |
            | 90017C       | 01         | DL           | NotPass   |
            | 90017C       | 10         | DL           | NotPass   |
            | 90017C       | 56         | DL           | NotPass   |
            | 90017C       | 66         | DL           | NotPass   |
            | 90017C       | 76         | DL           | NotPass   |
            | 90017C       | 86         | DL           | NotPass   |
            | 90017C       | 91         | DL           | NotPass   |
