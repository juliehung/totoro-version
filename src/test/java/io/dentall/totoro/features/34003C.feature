@nhi @nhi-34-series
Feature: 34003C 咬合片 X光攝影

    Scenario Outline: 全部檢核成功
        Given 建立醫師
        Given Scott 24 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 確認診療代碼 <IssueNhiCode> ，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            | 34003C       | 11         | MO           | Pass      |

    Scenario Outline: （Disposal）同日或同處置單不得申報 34003C/90001C~90003C/90006C/90007C/90012C/90015C/90016C/90018C~90020C/90112C/92015C/92016C/92028C/92033C/92041C/92042C/92050C/92056C~92059C/92063C/92064C
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
            # 同牙
            | 34003C       | 11         | MO           | 34002C           | 11             | MO               | NotPass   |
            | 34003C       | 11         | MO           | 90001C           | 11             | MO               | NotPass   |
            | 34003C       | 11         | MO           | 90002C           | 11             | MO               | NotPass   |
            | 34003C       | 11         | MO           | 90003C           | 11             | MO               | NotPass   |
            | 34003C       | 11         | MO           | 90006C           | 11             | MO               | NotPass   |
            | 34003C       | 11         | MO           | 90007C           | 11             | MO               | NotPass   |
            | 34003C       | 11         | MO           | 90012C           | 11             | MO               | NotPass   |
            | 34003C       | 11         | MO           | 90015C           | 11             | MO               | NotPass   |
            | 34003C       | 11         | MO           | 90016C           | 11             | MO               | NotPass   |
            | 34003C       | 11         | MO           | 90018C           | 11             | MO               | NotPass   |
            | 34003C       | 11         | MO           | 90019C           | 11             | MO               | NotPass   |
            | 34003C       | 11         | MO           | 90020C           | 11             | MO               | NotPass   |
            | 34003C       | 11         | MO           | 90112C           | 11             | MO               | NotPass   |
            | 34003C       | 11         | MO           | 92015C           | 11             | MO               | NotPass   |
            | 34003C       | 11         | MO           | 92016C           | 11             | MO               | NotPass   |
            | 34003C       | 11         | MO           | 92028C           | 11             | MO               | NotPass   |
            | 34003C       | 11         | MO           | 92033C           | 11             | MO               | NotPass   |
            | 34003C       | 11         | MO           | 92041C           | 11             | MO               | NotPass   |
            | 34003C       | 11         | MO           | 92042C           | 11             | MO               | NotPass   |
            | 34003C       | 11         | MO           | 92050C           | 11             | MO               | NotPass   |
            | 34003C       | 11         | MO           | 92056C           | 11             | MO               | NotPass   |
            | 34003C       | 11         | MO           | 92057C           | 11             | MO               | NotPass   |
            | 34003C       | 11         | MO           | 92058C           | 11             | MO               | NotPass   |
            | 34003C       | 11         | MO           | 92059C           | 11             | MO               | NotPass   |
            | 34003C       | 11         | MO           | 92063C           | 11             | MO               | NotPass   |
            | 34003C       | 11         | MO           | 92064C           | 11             | MO               | NotPass   |
            # 不同牙
            | 34003C       | 11         | MO           | 34002C           | 12             | MO               | Pass      |
            | 34003C       | 11         | MO           | 90001C           | 12             | MO               | Pass      |
            | 34003C       | 11         | MO           | 90002C           | 12             | MO               | Pass      |
            | 34003C       | 11         | MO           | 90003C           | 12             | MO               | Pass      |
            | 34003C       | 11         | MO           | 90006C           | 12             | MO               | Pass      |
            | 34003C       | 11         | MO           | 90007C           | 12             | MO               | Pass      |
            | 34003C       | 11         | MO           | 90012C           | 12             | MO               | Pass      |
            | 34003C       | 11         | MO           | 90015C           | 12             | MO               | Pass      |
            | 34003C       | 11         | MO           | 90016C           | 12             | MO               | Pass      |
            | 34003C       | 11         | MO           | 90018C           | 12             | MO               | Pass      |
            | 34003C       | 11         | MO           | 90019C           | 12             | MO               | Pass      |
            | 34003C       | 11         | MO           | 90020C           | 12             | MO               | Pass      |
            | 34003C       | 11         | MO           | 90112C           | 12             | MO               | Pass      |
            | 34003C       | 11         | MO           | 92015C           | 12             | MO               | Pass      |
            | 34003C       | 11         | MO           | 92016C           | 12             | MO               | Pass      |
            | 34003C       | 11         | MO           | 92028C           | 12             | MO               | Pass      |
            | 34003C       | 11         | MO           | 92033C           | 12             | MO               | Pass      |
            | 34003C       | 11         | MO           | 92041C           | 12             | MO               | Pass      |
            | 34003C       | 11         | MO           | 92042C           | 12             | MO               | Pass      |
            | 34003C       | 11         | MO           | 92050C           | 12             | MO               | Pass      |
            | 34003C       | 11         | MO           | 92056C           | 12             | MO               | Pass      |
            | 34003C       | 11         | MO           | 92057C           | 12             | MO               | Pass      |
            | 34003C       | 11         | MO           | 92058C           | 12             | MO               | Pass      |
            | 34003C       | 11         | MO           | 92059C           | 12             | MO               | Pass      |
            | 34003C       | 11         | MO           | 92063C           | 12             | MO               | Pass      |
            | 34003C       | 11         | MO           | 92064C           | 12             | MO               | Pass      |

    Scenario Outline: （HIS-Today）同日或同處置單不得申報 34003C/90001C~90003C/90006C/90007C/90012C/90015C/90016C/90018C~90020C/90112C/92015C/92016C/92028C/92033C/92041C/92042C/92050C/92056C~92059C/92063C/92064C
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
            # 同牙
            | 34003C       | 11         | MO           | 當日                | 34002C           | 11             | MO               | NotPass   |
            | 34003C       | 11         | MO           | 當日                | 90001C           | 11             | MO               | NotPass   |
            | 34003C       | 11         | MO           | 當日                | 90002C           | 11             | MO               | NotPass   |
            | 34003C       | 11         | MO           | 當日                | 90003C           | 11             | MO               | NotPass   |
            | 34003C       | 11         | MO           | 當日                | 90006C           | 11             | MO               | NotPass   |
            | 34003C       | 11         | MO           | 當日                | 90007C           | 11             | MO               | NotPass   |
            | 34003C       | 11         | MO           | 當日                | 90012C           | 11             | MO               | NotPass   |
            | 34003C       | 11         | MO           | 當日                | 90015C           | 11             | MO               | NotPass   |
            | 34003C       | 11         | MO           | 當日                | 90016C           | 11             | MO               | NotPass   |
            | 34003C       | 11         | MO           | 當日                | 90018C           | 11             | MO               | NotPass   |
            | 34003C       | 11         | MO           | 當日                | 90019C           | 11             | MO               | NotPass   |
            | 34003C       | 11         | MO           | 當日                | 90020C           | 11             | MO               | NotPass   |
            | 34003C       | 11         | MO           | 當日                | 90112C           | 11             | MO               | NotPass   |
            | 34003C       | 11         | MO           | 當日                | 92015C           | 11             | MO               | NotPass   |
            | 34003C       | 11         | MO           | 當日                | 92016C           | 11             | MO               | NotPass   |
            | 34003C       | 11         | MO           | 當日                | 92028C           | 11             | MO               | NotPass   |
            | 34003C       | 11         | MO           | 當日                | 92033C           | 11             | MO               | NotPass   |
            | 34003C       | 11         | MO           | 當日                | 92041C           | 11             | MO               | NotPass   |
            | 34003C       | 11         | MO           | 當日                | 92042C           | 11             | MO               | NotPass   |
            | 34003C       | 11         | MO           | 當日                | 92050C           | 11             | MO               | NotPass   |
            | 34003C       | 11         | MO           | 當日                | 92056C           | 11             | MO               | NotPass   |
            | 34003C       | 11         | MO           | 當日                | 92057C           | 11             | MO               | NotPass   |
            | 34003C       | 11         | MO           | 當日                | 92058C           | 11             | MO               | NotPass   |
            | 34003C       | 11         | MO           | 當日                | 92059C           | 11             | MO               | NotPass   |
            | 34003C       | 11         | MO           | 當日                | 92063C           | 11             | MO               | NotPass   |
            | 34003C       | 11         | MO           | 當日                | 92064C           | 11             | MO               | NotPass   |
            # 不同牙
            | 34003C       | 11         | MO           | 當日                | 34002C           | 12             | MO               | Pass      |
            | 34003C       | 11         | MO           | 當日                | 90001C           | 12             | MO               | Pass      |
            | 34003C       | 11         | MO           | 當日                | 90002C           | 12             | MO               | Pass      |
            | 34003C       | 11         | MO           | 當日                | 90003C           | 12             | MO               | Pass      |
            | 34003C       | 11         | MO           | 當日                | 90006C           | 12             | MO               | Pass      |
            | 34003C       | 11         | MO           | 當日                | 90007C           | 12             | MO               | Pass      |
            | 34003C       | 11         | MO           | 當日                | 90012C           | 12             | MO               | Pass      |
            | 34003C       | 11         | MO           | 當日                | 90015C           | 12             | MO               | Pass      |
            | 34003C       | 11         | MO           | 當日                | 90016C           | 12             | MO               | Pass      |
            | 34003C       | 11         | MO           | 當日                | 90018C           | 12             | MO               | Pass      |
            | 34003C       | 11         | MO           | 當日                | 90019C           | 12             | MO               | Pass      |
            | 34003C       | 11         | MO           | 當日                | 90020C           | 12             | MO               | Pass      |
            | 34003C       | 11         | MO           | 當日                | 90112C           | 12             | MO               | Pass      |
            | 34003C       | 11         | MO           | 當日                | 92015C           | 12             | MO               | Pass      |
            | 34003C       | 11         | MO           | 當日                | 92016C           | 12             | MO               | Pass      |
            | 34003C       | 11         | MO           | 當日                | 92028C           | 12             | MO               | Pass      |
            | 34003C       | 11         | MO           | 當日                | 92033C           | 12             | MO               | Pass      |
            | 34003C       | 11         | MO           | 當日                | 92041C           | 12             | MO               | Pass      |
            | 34003C       | 11         | MO           | 當日                | 92042C           | 12             | MO               | Pass      |
            | 34003C       | 11         | MO           | 當日                | 92050C           | 12             | MO               | Pass      |
            | 34003C       | 11         | MO           | 當日                | 92056C           | 12             | MO               | Pass      |
            | 34003C       | 11         | MO           | 當日                | 92057C           | 12             | MO               | Pass      |
            | 34003C       | 11         | MO           | 當日                | 92058C           | 12             | MO               | Pass      |
            | 34003C       | 11         | MO           | 當日                | 92059C           | 12             | MO               | Pass      |
            | 34003C       | 11         | MO           | 當日                | 92063C           | 12             | MO               | Pass      |
            | 34003C       | 11         | MO           | 當日                | 92064C           | 12             | MO               | Pass      |

    Scenario Outline: （IC-Today）同日或同處置單不得申報 34003C/90001C~90003C/90006C/90007C/90012C/90015C/90016C/90018C~90020C/90112C/92015C/92016C/92028C/92033C/92041C/92042C/92050C/92056C~92059C/92063C/92064C
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
            # 同牙
            | 34003C       | 11         | MO           | 當日              | 34002C         | 11           | NotPass   |
            | 34003C       | 11         | MO           | 當日              | 90001C         | 11           | NotPass   |
            | 34003C       | 11         | MO           | 當日              | 90002C         | 11           | NotPass   |
            | 34003C       | 11         | MO           | 當日              | 90003C         | 11           | NotPass   |
            | 34003C       | 11         | MO           | 當日              | 90006C         | 11           | NotPass   |
            | 34003C       | 11         | MO           | 當日              | 90007C         | 11           | NotPass   |
            | 34003C       | 11         | MO           | 當日              | 90012C         | 11           | NotPass   |
            | 34003C       | 11         | MO           | 當日              | 90015C         | 11           | NotPass   |
            | 34003C       | 11         | MO           | 當日              | 90016C         | 11           | NotPass   |
            | 34003C       | 11         | MO           | 當日              | 90018C         | 11           | NotPass   |
            | 34003C       | 11         | MO           | 當日              | 90019C         | 11           | NotPass   |
            | 34003C       | 11         | MO           | 當日              | 90020C         | 11           | NotPass   |
            | 34003C       | 11         | MO           | 當日              | 90112C         | 11           | NotPass   |
            | 34003C       | 11         | MO           | 當日              | 92015C         | 11           | NotPass   |
            | 34003C       | 11         | MO           | 當日              | 92016C         | 11           | NotPass   |
            | 34003C       | 11         | MO           | 當日              | 92028C         | 11           | NotPass   |
            | 34003C       | 11         | MO           | 當日              | 92033C         | 11           | NotPass   |
            | 34003C       | 11         | MO           | 當日              | 92041C         | 11           | NotPass   |
            | 34003C       | 11         | MO           | 當日              | 92042C         | 11           | NotPass   |
            | 34003C       | 11         | MO           | 當日              | 92050C         | 11           | NotPass   |
            | 34003C       | 11         | MO           | 當日              | 92056C         | 11           | NotPass   |
            | 34003C       | 11         | MO           | 當日              | 92057C         | 11           | NotPass   |
            | 34003C       | 11         | MO           | 當日              | 92058C         | 11           | NotPass   |
            | 34003C       | 11         | MO           | 當日              | 92059C         | 11           | NotPass   |
            | 34003C       | 11         | MO           | 當日              | 92063C         | 11           | NotPass   |
            | 34003C       | 11         | MO           | 當日              | 92064C         | 11           | NotPass   |
            # 不同牙
            | 34003C       | 11         | MO           | 當日              | 34002C         | 12           | Pass      |
            | 34003C       | 11         | MO           | 當日              | 90001C         | 12           | Pass      |
            | 34003C       | 11         | MO           | 當日              | 90002C         | 12           | Pass      |
            | 34003C       | 11         | MO           | 當日              | 90003C         | 12           | Pass      |
            | 34003C       | 11         | MO           | 當日              | 90006C         | 12           | Pass      |
            | 34003C       | 11         | MO           | 當日              | 90007C         | 12           | Pass      |
            | 34003C       | 11         | MO           | 當日              | 90012C         | 12           | Pass      |
            | 34003C       | 11         | MO           | 當日              | 90015C         | 12           | Pass      |
            | 34003C       | 11         | MO           | 當日              | 90016C         | 12           | Pass      |
            | 34003C       | 11         | MO           | 當日              | 90018C         | 12           | Pass      |
            | 34003C       | 11         | MO           | 當日              | 90019C         | 12           | Pass      |
            | 34003C       | 11         | MO           | 當日              | 90020C         | 12           | Pass      |
            | 34003C       | 11         | MO           | 當日              | 90112C         | 12           | Pass      |
            | 34003C       | 11         | MO           | 當日              | 92015C         | 12           | Pass      |
            | 34003C       | 11         | MO           | 當日              | 92016C         | 12           | Pass      |
            | 34003C       | 11         | MO           | 當日              | 92028C         | 12           | Pass      |
            | 34003C       | 11         | MO           | 當日              | 92033C         | 12           | Pass      |
            | 34003C       | 11         | MO           | 當日              | 92041C         | 12           | Pass      |
            | 34003C       | 11         | MO           | 當日              | 92042C         | 12           | Pass      |
            | 34003C       | 11         | MO           | 當日              | 92050C         | 12           | Pass      |
            | 34003C       | 11         | MO           | 當日              | 92056C         | 12           | Pass      |
            | 34003C       | 11         | MO           | 當日              | 92057C         | 12           | Pass      |
            | 34003C       | 11         | MO           | 當日              | 92058C         | 12           | Pass      |
            | 34003C       | 11         | MO           | 當日              | 92059C         | 12           | Pass      |
            | 34003C       | 11         | MO           | 當日              | 92063C         | 12           | Pass      |
            | 34003C       | 11         | MO           | 當日              | 92064C         | 12           | Pass      |
