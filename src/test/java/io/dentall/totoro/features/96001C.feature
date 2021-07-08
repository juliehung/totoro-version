@nhi @nhi-96-series
Feature: 96001C 牙科局部麻醉

    Scenario Outline: 全部檢核成功
        Given 建立醫師
        Given Scott 24 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | 89001C         | <IssueTeeth> | <IssueSurface> |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 確認診療代碼 <IssueNhiCode> ，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            | 96001C       | 51         | MOB          | Pass      |

    Scenario Outline: 檢查治療的牙位是否為 DECIDUOUS_TOOTH_AND_PERMANENT_WEIRD_TOOTH
        Given 建立醫師
        Given Scott 24 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 檢查 <IssueTeeth> 牙位，依 DECIDUOUS_TOOTH_AND_PERMANENT_WEIRD_TOOTH 判定是否為核可牙位，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
           # 乳牙
            | 96001C       | 51         | DL           | Pass      |
            | 96001C       | 52         | DL           | Pass      |
            | 96001C       | 53         | DL           | Pass      |
            | 96001C       | 54         | DL           | Pass      |
            | 96001C       | 55         | DL           | Pass      |
            | 96001C       | 61         | DL           | Pass      |
            | 96001C       | 62         | DL           | Pass      |
            | 96001C       | 63         | DL           | Pass      |
            | 96001C       | 64         | DL           | Pass      |
            | 96001C       | 65         | DL           | Pass      |
            | 96001C       | 71         | DL           | Pass      |
            | 96001C       | 72         | DL           | Pass      |
            | 96001C       | 73         | DL           | Pass      |
            | 96001C       | 74         | DL           | Pass      |
            | 96001C       | 75         | DL           | Pass      |
            | 96001C       | 81         | DL           | Pass      |
            | 96001C       | 82         | DL           | Pass      |
            | 96001C       | 83         | DL           | Pass      |
            | 96001C       | 84         | DL           | Pass      |
            | 96001C       | 85         | DL           | Pass      |
            # 恆牙
            | 96001C       | 11         | DL           | NotPass   |
            | 96001C       | 12         | DL           | NotPass   |
            | 96001C       | 13         | DL           | NotPass   |
            | 96001C       | 14         | DL           | NotPass   |
            | 96001C       | 15         | DL           | NotPass   |
            | 96001C       | 16         | DL           | NotPass   |
            | 96001C       | 17         | DL           | NotPass   |
            | 96001C       | 18         | DL           | NotPass   |
            | 96001C       | 21         | DL           | NotPass   |
            | 96001C       | 22         | DL           | NotPass   |
            | 96001C       | 23         | DL           | NotPass   |
            | 96001C       | 24         | DL           | NotPass   |
            | 96001C       | 25         | DL           | NotPass   |
            | 96001C       | 26         | DL           | NotPass   |
            | 96001C       | 27         | DL           | NotPass   |
            | 96001C       | 28         | DL           | NotPass   |
            | 96001C       | 31         | DL           | NotPass   |
            | 96001C       | 32         | DL           | NotPass   |
            | 96001C       | 33         | DL           | NotPass   |
            | 96001C       | 34         | DL           | NotPass   |
            | 96001C       | 35         | DL           | NotPass   |
            | 96001C       | 36         | DL           | NotPass   |
            | 96001C       | 37         | DL           | NotPass   |
            | 96001C       | 38         | DL           | NotPass   |
            | 96001C       | 41         | DL           | NotPass   |
            | 96001C       | 42         | DL           | NotPass   |
            | 96001C       | 43         | DL           | NotPass   |
            | 96001C       | 44         | DL           | NotPass   |
            | 96001C       | 45         | DL           | NotPass   |
            | 96001C       | 46         | DL           | NotPass   |
            | 96001C       | 47         | DL           | NotPass   |
            | 96001C       | 48         | DL           | NotPass   |
            # 無牙
            | 96001C       |            | DL           | NotPass   |
            #
            | 96001C       | 19         | DL           | Pass      |
            | 96001C       | 29         | DL           | Pass      |
            | 96001C       | 39         | DL           | Pass      |
            | 96001C       | 49         | DL           | Pass      |
            | 96001C       | 59         | DL           | NotPass   |
            | 96001C       | 69         | DL           | NotPass   |
            | 96001C       | 79         | DL           | NotPass   |
            | 96001C       | 89         | DL           | NotPass   |
            | 96001C       | 99         | DL           | Pass      |
            # 牙位為區域型態
            | 96001C       | FM         | DL           | NotPass   |
            | 96001C       | UR         | DL           | NotPass   |
            | 96001C       | UL         | DL           | NotPass   |
            | 96001C       | UA         | DL           | NotPass   |
            | 96001C       | UB         | DL           | NotPass   |
            | 96001C       | LR         | DL           | NotPass   |
            | 96001C       | LL         | DL           | NotPass   |
            | 96001C       | LA         | DL           | NotPass   |
            | 96001C       | LB         | DL           | NotPass   |
            # 非法牙位
            | 96001C       | 00         | DL           | NotPass   |
            | 96001C       | 01         | DL           | NotPass   |
            | 96001C       | 10         | DL           | NotPass   |
            | 96001C       | 56         | DL           | NotPass   |
            | 96001C       | 66         | DL           | NotPass   |
            | 96001C       | 76         | DL           | NotPass   |
            | 96001C       | 86         | DL           | NotPass   |
            | 96001C       | 91         | DL           | NotPass   |

    Scenario Outline: （Disposal）同日或同處置單不得申報 90001C/90002C/90003C/90005C/90015C/90016C/90018C/90019C/90020C/91013C/92012C/92013C/92014C/92015C/92016C/92017C/92027C/92028C/92029C/92030C/92031C/92032C/92033C/92041C/92042C/92043C/92050C/92055C/92056C/92057C/92058C/92059C/92063C/92064C/92071C/92092C
        Given 建立醫師
        Given Scott 24 歲病人
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
            | 96001C       | 51         | MO           | 90001C           | 51             | MO               | NotPass   |
            | 96001C       | 51         | MO           | 90002C           | 51             | MO               | NotPass   |
            | 96001C       | 51         | MO           | 90003C           | 51             | MO               | NotPass   |
            | 96001C       | 51         | MO           | 90005C           | 51             | MO               | NotPass   |
            | 96001C       | 51         | MO           | 90015C           | 51             | MO               | NotPass   |
            | 96001C       | 51         | MO           | 90016C           | 51             | MO               | NotPass   |
            | 96001C       | 51         | MO           | 90018C           | 51             | MO               | NotPass   |
            | 96001C       | 51         | MO           | 90019C           | 51             | MO               | NotPass   |
            | 96001C       | 51         | MO           | 90020C           | 51             | MO               | NotPass   |
            | 96001C       | 51         | MO           | 91013C           | 51             | MO               | NotPass   |
            | 96001C       | 51         | MO           | 92012C           | 51             | MO               | NotPass   |
            | 96001C       | 51         | MO           | 92013C           | 51             | MO               | NotPass   |
            | 96001C       | 51         | MO           | 92014C           | 51             | MO               | NotPass   |
            | 96001C       | 51         | MO           | 92015C           | 51             | MO               | NotPass   |
            | 96001C       | 51         | MO           | 92016C           | 51             | MO               | NotPass   |
            | 96001C       | 51         | MO           | 92017C           | 51             | MO               | NotPass   |
            | 96001C       | 51         | MO           | 92027C           | 51             | MO               | NotPass   |
            | 96001C       | 51         | MO           | 92028C           | 51             | MO               | NotPass   |
            | 96001C       | 51         | MO           | 92029C           | 51             | MO               | NotPass   |
            | 96001C       | 51         | MO           | 92030C           | 51             | MO               | NotPass   |
            | 96001C       | 51         | MO           | 92031C           | 51             | MO               | NotPass   |
            | 96001C       | 51         | MO           | 92032C           | 51             | MO               | NotPass   |
            | 96001C       | 51         | MO           | 92033C           | 51             | MO               | NotPass   |
            | 96001C       | 51         | MO           | 92041C           | 51             | MO               | NotPass   |
            | 96001C       | 51         | MO           | 92042C           | 51             | MO               | NotPass   |
            | 96001C       | 51         | MO           | 92043C           | 51             | MO               | NotPass   |
            | 96001C       | 51         | MO           | 92050C           | 51             | MO               | NotPass   |
            | 96001C       | 51         | MO           | 92055C           | 51             | MO               | NotPass   |
            | 96001C       | 51         | MO           | 92056C           | 51             | MO               | NotPass   |
            | 96001C       | 51         | MO           | 92057C           | 51             | MO               | NotPass   |
            | 96001C       | 51         | MO           | 92058C           | 51             | MO               | NotPass   |
            | 96001C       | 51         | MO           | 92059C           | 51             | MO               | NotPass   |
            | 96001C       | 51         | MO           | 92063C           | 51             | MO               | NotPass   |
            | 96001C       | 51         | MO           | 92064C           | 51             | MO               | NotPass   |
            | 96001C       | 51         | MO           | 92071C           | 51             | MO               | NotPass   |
            | 96001C       | 51         | MO           | 92092C           | 51             | MO               | NotPass   |

    Scenario Outline: （HIS-Today）同日或同處置單不得申報 90001C/90002C/90003C/90005C/90015C/90016C/90018C/90019C/90020C/91013C/92012C/92013C/92014C/92015C/92016C/92017C/92027C/92028C/92029C/92030C/92031C/92032C/92033C/92041C/92042C/92043C/92050C/92055C/92056C/92057C/92058C/92059C/92063C/92064C/92071C/92092C
        Given 建立醫師
        Given Scott 24 歲病人
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
            | 96001C       | 51         | MO           | 當日                | 90001C           | 51             | MO               | NotPass   |
            | 96001C       | 51         | MO           | 當日                | 90002C           | 51             | MO               | NotPass   |
            | 96001C       | 51         | MO           | 當日                | 90003C           | 51             | MO               | NotPass   |
            | 96001C       | 51         | MO           | 當日                | 90005C           | 51             | MO               | NotPass   |
            | 96001C       | 51         | MO           | 當日                | 90015C           | 51             | MO               | NotPass   |
            | 96001C       | 51         | MO           | 當日                | 90016C           | 51             | MO               | NotPass   |
            | 96001C       | 51         | MO           | 當日                | 90018C           | 51             | MO               | NotPass   |
            | 96001C       | 51         | MO           | 當日                | 90019C           | 51             | MO               | NotPass   |
            | 96001C       | 51         | MO           | 當日                | 90020C           | 51             | MO               | NotPass   |
            | 96001C       | 51         | MO           | 當日                | 91013C           | 51             | MO               | NotPass   |
            | 96001C       | 51         | MO           | 當日                | 92012C           | 51             | MO               | NotPass   |
            | 96001C       | 51         | MO           | 當日                | 92013C           | 51             | MO               | NotPass   |
            | 96001C       | 51         | MO           | 當日                | 92014C           | 51             | MO               | NotPass   |
            | 96001C       | 51         | MO           | 當日                | 92015C           | 51             | MO               | NotPass   |
            | 96001C       | 51         | MO           | 當日                | 92016C           | 51             | MO               | NotPass   |
            | 96001C       | 51         | MO           | 當日                | 92017C           | 51             | MO               | NotPass   |
            | 96001C       | 51         | MO           | 當日                | 92027C           | 51             | MO               | NotPass   |
            | 96001C       | 51         | MO           | 當日                | 92028C           | 51             | MO               | NotPass   |
            | 96001C       | 51         | MO           | 當日                | 92029C           | 51             | MO               | NotPass   |
            | 96001C       | 51         | MO           | 當日                | 92030C           | 51             | MO               | NotPass   |
            | 96001C       | 51         | MO           | 當日                | 92031C           | 51             | MO               | NotPass   |
            | 96001C       | 51         | MO           | 當日                | 92032C           | 51             | MO               | NotPass   |
            | 96001C       | 51         | MO           | 當日                | 92033C           | 51             | MO               | NotPass   |
            | 96001C       | 51         | MO           | 當日                | 92041C           | 51             | MO               | NotPass   |
            | 96001C       | 51         | MO           | 當日                | 92042C           | 51             | MO               | NotPass   |
            | 96001C       | 51         | MO           | 當日                | 92043C           | 51             | MO               | NotPass   |
            | 96001C       | 51         | MO           | 當日                | 92050C           | 51             | MO               | NotPass   |
            | 96001C       | 51         | MO           | 當日                | 92055C           | 51             | MO               | NotPass   |
            | 96001C       | 51         | MO           | 當日                | 92056C           | 51             | MO               | NotPass   |
            | 96001C       | 51         | MO           | 當日                | 92057C           | 51             | MO               | NotPass   |
            | 96001C       | 51         | MO           | 當日                | 92058C           | 51             | MO               | NotPass   |
            | 96001C       | 51         | MO           | 當日                | 92059C           | 51             | MO               | NotPass   |
            | 96001C       | 51         | MO           | 當日                | 92063C           | 51             | MO               | NotPass   |
            | 96001C       | 51         | MO           | 當日                | 92064C           | 51             | MO               | NotPass   |
            | 96001C       | 51         | MO           | 當日                | 92071C           | 51             | MO               | NotPass   |
            | 96001C       | 51         | MO           | 當日                | 92092C           | 51             | MO               | NotPass   |
            | 96001C       | 51         | MO           | 昨日                | 90001C           | 51             | MO               | Pass      |
            | 96001C       | 51         | MO           | 昨日                | 90002C           | 51             | MO               | Pass      |
            | 96001C       | 51         | MO           | 昨日                | 90003C           | 51             | MO               | Pass      |
            | 96001C       | 51         | MO           | 昨日                | 90005C           | 51             | MO               | Pass      |
            | 96001C       | 51         | MO           | 昨日                | 90015C           | 51             | MO               | Pass      |
            | 96001C       | 51         | MO           | 昨日                | 90016C           | 51             | MO               | Pass      |
            | 96001C       | 51         | MO           | 昨日                | 90018C           | 51             | MO               | Pass      |
            | 96001C       | 51         | MO           | 昨日                | 90019C           | 51             | MO               | Pass      |
            | 96001C       | 51         | MO           | 昨日                | 90020C           | 51             | MO               | Pass      |
            | 96001C       | 51         | MO           | 昨日                | 91013C           | 51             | MO               | Pass      |
            | 96001C       | 51         | MO           | 昨日                | 92012C           | 51             | MO               | Pass      |
            | 96001C       | 51         | MO           | 昨日                | 92013C           | 51             | MO               | Pass      |
            | 96001C       | 51         | MO           | 昨日                | 92014C           | 51             | MO               | Pass      |
            | 96001C       | 51         | MO           | 昨日                | 92015C           | 51             | MO               | Pass      |
            | 96001C       | 51         | MO           | 昨日                | 92016C           | 51             | MO               | Pass      |
            | 96001C       | 51         | MO           | 昨日                | 92017C           | 51             | MO               | Pass      |
            | 96001C       | 51         | MO           | 昨日                | 92027C           | 51             | MO               | Pass      |
            | 96001C       | 51         | MO           | 昨日                | 92028C           | 51             | MO               | Pass      |
            | 96001C       | 51         | MO           | 昨日                | 92029C           | 51             | MO               | Pass      |
            | 96001C       | 51         | MO           | 昨日                | 92030C           | 51             | MO               | Pass      |
            | 96001C       | 51         | MO           | 昨日                | 92031C           | 51             | MO               | Pass      |
            | 96001C       | 51         | MO           | 昨日                | 92032C           | 51             | MO               | Pass      |
            | 96001C       | 51         | MO           | 昨日                | 92033C           | 51             | MO               | Pass      |
            | 96001C       | 51         | MO           | 昨日                | 92041C           | 51             | MO               | Pass      |
            | 96001C       | 51         | MO           | 昨日                | 92042C           | 51             | MO               | Pass      |
            | 96001C       | 51         | MO           | 昨日                | 92043C           | 51             | MO               | Pass      |
            | 96001C       | 51         | MO           | 昨日                | 92050C           | 51             | MO               | Pass      |
            | 96001C       | 51         | MO           | 昨日                | 92055C           | 51             | MO               | Pass      |
            | 96001C       | 51         | MO           | 昨日                | 92056C           | 51             | MO               | Pass      |
            | 96001C       | 51         | MO           | 昨日                | 92057C           | 51             | MO               | Pass      |
            | 96001C       | 51         | MO           | 昨日                | 92058C           | 51             | MO               | Pass      |
            | 96001C       | 51         | MO           | 昨日                | 92059C           | 51             | MO               | Pass      |
            | 96001C       | 51         | MO           | 昨日                | 92063C           | 51             | MO               | Pass      |
            | 96001C       | 51         | MO           | 昨日                | 92064C           | 51             | MO               | Pass      |
            | 96001C       | 51         | MO           | 昨日                | 92071C           | 51             | MO               | Pass      |
            | 96001C       | 51         | MO           | 昨日                | 92092C           | 51             | MO               | Pass      |

    Scenario Outline: （IC）同日或同處置單不得申報 90001C/90002C/90003C/90005C/90015C/90016C/90018C/90019C/90020C/91013C/92012C/92013C/92014C/92015C/92016C/92017C/92027C/92028C/92029C/92030C/92031C/92032C/92033C/92041C/92042C/92043C/92050C/92055C/92056C/92057C/92058C/92059C/92063C/92064C/92071C/92092C
        Given 建立醫師
        Given Scott 24 歲病人
        Given 新增健保醫療:
            | PastDate          | NhiCode          | Teeth          |
            | <PastMedicalDate> | <MedicalNhiCode> | <MedicalTeeth> |
        Given 在 當日 ，建立預約
        Given 在 當日 ，建立掛號
        Given 在 當日 ，產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 同日或同處置單不得申報 <MedicalNhiCode> 診療代碼，確認結果是否為 <PassOrNot> 且檢查訊息類型為 W4_1
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastMedicalDate | MedicalNhiCode | MedicalTeeth | PassOrNot |
            | 96001C       | 51         | MO           | 當日              | 90001C         | 51           | NotPass   |
            | 96001C       | 51         | MO           | 當日              | 90002C         | 51           | NotPass   |
            | 96001C       | 51         | MO           | 當日              | 90003C         | 51           | NotPass   |
            | 96001C       | 51         | MO           | 當日              | 90005C         | 51           | NotPass   |
            | 96001C       | 51         | MO           | 當日              | 90015C         | 51           | NotPass   |
            | 96001C       | 51         | MO           | 當日              | 90016C         | 51           | NotPass   |
            | 96001C       | 51         | MO           | 當日              | 90018C         | 51           | NotPass   |
            | 96001C       | 51         | MO           | 當日              | 90019C         | 51           | NotPass   |
            | 96001C       | 51         | MO           | 當日              | 90020C         | 51           | NotPass   |
            | 96001C       | 51         | MO           | 當日              | 91013C         | 51           | NotPass   |
            | 96001C       | 51         | MO           | 當日              | 92012C         | 51           | NotPass   |
            | 96001C       | 51         | MO           | 當日              | 92013C         | 51           | NotPass   |
            | 96001C       | 51         | MO           | 當日              | 92014C         | 51           | NotPass   |
            | 96001C       | 51         | MO           | 當日              | 92015C         | 51           | NotPass   |
            | 96001C       | 51         | MO           | 當日              | 92016C         | 51           | NotPass   |
            | 96001C       | 51         | MO           | 當日              | 92017C         | 51           | NotPass   |
            | 96001C       | 51         | MO           | 當日              | 92027C         | 51           | NotPass   |
            | 96001C       | 51         | MO           | 當日              | 92028C         | 51           | NotPass   |
            | 96001C       | 51         | MO           | 當日              | 92029C         | 51           | NotPass   |
            | 96001C       | 51         | MO           | 當日              | 92030C         | 51           | NotPass   |
            | 96001C       | 51         | MO           | 當日              | 92031C         | 51           | NotPass   |
            | 96001C       | 51         | MO           | 當日              | 92032C         | 51           | NotPass   |
            | 96001C       | 51         | MO           | 當日              | 92033C         | 51           | NotPass   |
            | 96001C       | 51         | MO           | 當日              | 92041C         | 51           | NotPass   |
            | 96001C       | 51         | MO           | 當日              | 92042C         | 51           | NotPass   |
            | 96001C       | 51         | MO           | 當日              | 92043C         | 51           | NotPass   |
            | 96001C       | 51         | MO           | 當日              | 92050C         | 51           | NotPass   |
            | 96001C       | 51         | MO           | 當日              | 92055C         | 51           | NotPass   |
            | 96001C       | 51         | MO           | 當日              | 92056C         | 51           | NotPass   |
            | 96001C       | 51         | MO           | 當日              | 92057C         | 51           | NotPass   |
            | 96001C       | 51         | MO           | 當日              | 92058C         | 51           | NotPass   |
            | 96001C       | 51         | MO           | 當日              | 92059C         | 51           | NotPass   |
            | 96001C       | 51         | MO           | 當日              | 92063C         | 51           | NotPass   |
            | 96001C       | 51         | MO           | 當日              | 92064C         | 51           | NotPass   |
            | 96001C       | 51         | MO           | 當日              | 92071C         | 51           | NotPass   |
            | 96001C       | 51         | MO           | 當日              | 92092C         | 51           | NotPass   |
            | 96001C       | 51         | MO           | 昨日              | 90001C         | 51           | Pass      |
            | 96001C       | 51         | MO           | 昨日              | 90002C         | 51           | Pass      |
            | 96001C       | 51         | MO           | 昨日              | 90003C         | 51           | Pass      |
            | 96001C       | 51         | MO           | 昨日              | 90005C         | 51           | Pass      |
            | 96001C       | 51         | MO           | 昨日              | 90015C         | 51           | Pass      |
            | 96001C       | 51         | MO           | 昨日              | 90016C         | 51           | Pass      |
            | 96001C       | 51         | MO           | 昨日              | 90018C         | 51           | Pass      |
            | 96001C       | 51         | MO           | 昨日              | 90019C         | 51           | Pass      |
            | 96001C       | 51         | MO           | 昨日              | 90020C         | 51           | Pass      |
            | 96001C       | 51         | MO           | 昨日              | 91013C         | 51           | Pass      |
            | 96001C       | 51         | MO           | 昨日              | 92012C         | 51           | Pass      |
            | 96001C       | 51         | MO           | 昨日              | 92013C         | 51           | Pass      |
            | 96001C       | 51         | MO           | 昨日              | 92014C         | 51           | Pass      |
            | 96001C       | 51         | MO           | 昨日              | 92015C         | 51           | Pass      |
            | 96001C       | 51         | MO           | 昨日              | 92016C         | 51           | Pass      |
            | 96001C       | 51         | MO           | 昨日              | 92017C         | 51           | Pass      |
            | 96001C       | 51         | MO           | 昨日              | 92027C         | 51           | Pass      |
            | 96001C       | 51         | MO           | 昨日              | 92028C         | 51           | Pass      |
            | 96001C       | 51         | MO           | 昨日              | 92029C         | 51           | Pass      |
            | 96001C       | 51         | MO           | 昨日              | 92030C         | 51           | Pass      |
            | 96001C       | 51         | MO           | 昨日              | 92031C         | 51           | Pass      |
            | 96001C       | 51         | MO           | 昨日              | 92032C         | 51           | Pass      |
            | 96001C       | 51         | MO           | 昨日              | 92033C         | 51           | Pass      |
            | 96001C       | 51         | MO           | 昨日              | 92041C         | 51           | Pass      |
            | 96001C       | 51         | MO           | 昨日              | 92042C         | 51           | Pass      |
            | 96001C       | 51         | MO           | 昨日              | 92043C         | 51           | Pass      |
            | 96001C       | 51         | MO           | 昨日              | 92050C         | 51           | Pass      |
            | 96001C       | 51         | MO           | 昨日              | 92055C         | 51           | Pass      |
            | 96001C       | 51         | MO           | 昨日              | 92056C         | 51           | Pass      |
            | 96001C       | 51         | MO           | 昨日              | 92057C         | 51           | Pass      |
            | 96001C       | 51         | MO           | 昨日              | 92058C         | 51           | Pass      |
            | 96001C       | 51         | MO           | 昨日              | 92059C         | 51           | Pass      |
            | 96001C       | 51         | MO           | 昨日              | 92063C         | 51           | Pass      |
            | 96001C       | 51         | MO           | 昨日              | 92064C         | 51           | Pass      |
            | 96001C       | 51         | MO           | 昨日              | 92071C         | 51           | Pass      |
            | 96001C       | 51         | MO           | 昨日              | 92092C         | 51           | Pass      |

    Scenario Outline: 96001C 不得單獨申報
        Given 建立醫師
        Given Scott 24 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then <IssueNhiCode> 不得單獨申報，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            | 96001C       | 51         | MO           | NotPass   |
