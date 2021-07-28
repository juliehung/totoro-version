@nhi @nhi-34-series
Feature: 34001C 根尖周 X光攝影

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
            | 34001C       | 11         | MO           | Pass      |

    Scenario Outline: （HIS）同月份不得申報 34002C/90001C~90003C/90006C/90007C/90012C/90015C/90016C/90018C~90020C/90112C/92015C/92016C/92028C/92033C/92041C/92042C/92050C/92056C~92059C/92063C/92064C
        Given 建立醫師
        Given Scott 24 歲病人
        Given 在 <PastTreatmentDate> ，建立預約
        Given 在 <PastTreatmentDate> ，建立掛號
        Given 在 <PastTreatmentDate> ，產生診療計畫
        And 新增診療代碼:
            | PastDate            | A72 | A73                | A74              | A75                | A76 | A77 | A78 | A79 |
            | <PastTreatmentDate> | 3   | <TreatmentNhiCode> | <TreatmentTeeth> | <TreatmentSurface> | 0   | 1.0 | 03  |     |
        Given 在 當月底 ，建立預約
        Given 在 當月底 ，建立掛號
        Given 在 當月底 ，產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 在同月份中，不得申報 <TreatmentNhiCode> 診療代碼，確認結果是否為 <PassOrNot> 且檢查訊息類型為 W4_1
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastTreatmentDate | TreatmentNhiCode | TreatmentTeeth | TreatmentSurface | PassOrNot |
            # 同牙
            | 34001C       | 11         | MO           | 上個月初              | 34002C           | 11             | MO               | Pass      |
            | 34001C       | 11         | MO           | 當月初               | 34002C           | 11             | MO               | NotPass   |
            | 34001C       | 11         | MO           | 上個月初              | 90001C           | 11             | MO               | Pass      |
            | 34001C       | 11         | MO           | 當月初               | 90001C           | 11             | MO               | NotPass   |
            | 34001C       | 11         | MO           | 上個月初              | 90002C           | 11             | MO               | Pass      |
            | 34001C       | 11         | MO           | 當月初               | 90002C           | 11             | MO               | NotPass   |
            | 34001C       | 11         | MO           | 上個月初              | 90003C           | 11             | MO               | Pass      |
            | 34001C       | 11         | MO           | 當月初               | 90003C           | 11             | MO               | NotPass   |
            | 34001C       | 11         | MO           | 上個月初              | 90006C           | 11             | MO               | Pass      |
            | 34001C       | 11         | MO           | 當月初               | 90006C           | 11             | MO               | NotPass   |
            | 34001C       | 11         | MO           | 上個月初              | 90007C           | 11             | MO               | Pass      |
            | 34001C       | 11         | MO           | 當月初               | 90007C           | 11             | MO               | NotPass   |
            | 34001C       | 11         | MO           | 上個月初              | 90012C           | 11             | MO               | Pass      |
            | 34001C       | 11         | MO           | 當月初               | 90012C           | 11             | MO               | NotPass   |
            | 34001C       | 11         | MO           | 上個月初              | 90015C           | 11             | MO               | Pass      |
            | 34001C       | 11         | MO           | 當月初               | 90015C           | 11             | MO               | NotPass   |
            | 34001C       | 11         | MO           | 上個月初              | 90016C           | 11             | MO               | Pass      |
            | 34001C       | 11         | MO           | 當月初               | 90016C           | 11             | MO               | NotPass   |
            | 34001C       | 11         | MO           | 上個月初              | 90018C           | 11             | MO               | Pass      |
            | 34001C       | 11         | MO           | 當月初               | 90018C           | 11             | MO               | NotPass   |
            | 34001C       | 11         | MO           | 上個月初              | 90019C           | 11             | MO               | Pass      |
            | 34001C       | 11         | MO           | 當月初               | 90019C           | 11             | MO               | NotPass   |
            | 34001C       | 11         | MO           | 上個月初              | 90020C           | 11             | MO               | Pass      |
            | 34001C       | 11         | MO           | 當月初               | 90020C           | 11             | MO               | NotPass   |
            | 34001C       | 11         | MO           | 上個月初              | 90112C           | 11             | MO               | Pass      |
            | 34001C       | 11         | MO           | 當月初               | 90112C           | 11             | MO               | NotPass   |
            | 34001C       | 11         | MO           | 上個月初              | 92015C           | 11             | MO               | Pass      |
            | 34001C       | 11         | MO           | 當月初               | 92015C           | 11             | MO               | NotPass   |
            | 34001C       | 11         | MO           | 上個月初              | 92016C           | 11             | MO               | Pass      |
            | 34001C       | 11         | MO           | 當月初               | 92016C           | 11             | MO               | NotPass   |
            | 34001C       | 11         | MO           | 上個月初              | 92028C           | 11             | MO               | Pass      |
            | 34001C       | 11         | MO           | 當月初               | 92028C           | 11             | MO               | NotPass   |
            | 34001C       | 11         | MO           | 上個月初              | 92033C           | 11             | MO               | Pass      |
            | 34001C       | 11         | MO           | 當月初               | 92033C           | 11             | MO               | NotPass   |
            | 34001C       | 11         | MO           | 上個月初              | 92041C           | 11             | MO               | Pass      |
            | 34001C       | 11         | MO           | 當月初               | 92041C           | 11             | MO               | NotPass   |
            | 34001C       | 11         | MO           | 上個月初              | 92042C           | 11             | MO               | Pass      |
            | 34001C       | 11         | MO           | 當月初               | 92042C           | 11             | MO               | NotPass   |
            | 34001C       | 11         | MO           | 上個月初              | 92050C           | 11             | MO               | Pass      |
            | 34001C       | 11         | MO           | 當月初               | 92050C           | 11             | MO               | NotPass   |
            | 34001C       | 11         | MO           | 上個月初              | 92056C           | 11             | MO               | Pass      |
            | 34001C       | 11         | MO           | 當月初               | 92056C           | 11             | MO               | NotPass   |
            | 34001C       | 11         | MO           | 上個月初              | 92057C           | 11             | MO               | Pass      |
            | 34001C       | 11         | MO           | 當月初               | 92057C           | 11             | MO               | NotPass   |
            | 34001C       | 11         | MO           | 上個月初              | 92058C           | 11             | MO               | Pass      |
            | 34001C       | 11         | MO           | 當月初               | 92058C           | 11             | MO               | NotPass   |
            | 34001C       | 11         | MO           | 上個月初              | 92059C           | 11             | MO               | Pass      |
            | 34001C       | 11         | MO           | 當月初               | 92059C           | 11             | MO               | NotPass   |
            | 34001C       | 11         | MO           | 上個月初              | 92063C           | 11             | MO               | Pass      |
            | 34001C       | 11         | MO           | 當月初               | 92063C           | 11             | MO               | NotPass   |
            | 34001C       | 11         | MO           | 上個月初              | 92064C           | 11             | MO               | Pass      |
            | 34001C       | 11         | MO           | 當月初               | 92064C           | 11             | MO               | NotPass   |
            # 不同牙
            | 34001C       | 11         | MO           | 當月初               | 34002C           | 12             | MO               | Pass      |
            | 34001C       | 11         | MO           | 當月初               | 90001C           | 12             | MO               | Pass      |
            | 34001C       | 11         | MO           | 當月初               | 90002C           | 12             | MO               | Pass      |
            | 34001C       | 11         | MO           | 當月初               | 90003C           | 12             | MO               | Pass      |
            | 34001C       | 11         | MO           | 當月初               | 90006C           | 12             | MO               | Pass      |
            | 34001C       | 11         | MO           | 當月初               | 90007C           | 12             | MO               | Pass      |
            | 34001C       | 11         | MO           | 當月初               | 90012C           | 12             | MO               | Pass      |
            | 34001C       | 11         | MO           | 當月初               | 90015C           | 12             | MO               | Pass      |
            | 34001C       | 11         | MO           | 當月初               | 90016C           | 12             | MO               | Pass      |
            | 34001C       | 11         | MO           | 當月初               | 90018C           | 12             | MO               | Pass      |
            | 34001C       | 11         | MO           | 當月初               | 90019C           | 12             | MO               | Pass      |
            | 34001C       | 11         | MO           | 當月初               | 90020C           | 12             | MO               | Pass      |
            | 34001C       | 11         | MO           | 當月初               | 90112C           | 12             | MO               | Pass      |
            | 34001C       | 11         | MO           | 當月初               | 92015C           | 12             | MO               | Pass      |
            | 34001C       | 11         | MO           | 當月初               | 92016C           | 12             | MO               | Pass      |
            | 34001C       | 11         | MO           | 當月初               | 92028C           | 12             | MO               | Pass      |
            | 34001C       | 11         | MO           | 當月初               | 92033C           | 12             | MO               | Pass      |
            | 34001C       | 11         | MO           | 當月初               | 92041C           | 12             | MO               | Pass      |
            | 34001C       | 11         | MO           | 當月初               | 92042C           | 12             | MO               | Pass      |
            | 34001C       | 11         | MO           | 當月初               | 92050C           | 12             | MO               | Pass      |
            | 34001C       | 11         | MO           | 當月初               | 92056C           | 12             | MO               | Pass      |
            | 34001C       | 11         | MO           | 當月初               | 92057C           | 12             | MO               | Pass      |
            | 34001C       | 11         | MO           | 當月初               | 92058C           | 12             | MO               | Pass      |
            | 34001C       | 11         | MO           | 當月初               | 92059C           | 12             | MO               | Pass      |
            | 34001C       | 11         | MO           | 當月初               | 92063C           | 12             | MO               | Pass      |
            | 34001C       | 11         | MO           | 當月初               | 92064C           | 12             | MO               | Pass      |

    Scenario Outline: （IC）同月份不得申報 34002C/90001C~90003C/90006C/90007C/90012C/90015C/90016C/90018C~90020C/90112C/92015C/92016C/92028C/92033C/92041C/92042C/92050C/92056C~92059C/92063C/92064C
        Given 建立醫師
        Given Scott 24 歲病人
        Given 新增健保醫療:
            | PastDate          | NhiCode          | Teeth          |
            | <PastMedicalDate> | <MedicalNhiCode> | <MedicalTeeth> |
        Given 在 當月底 ，建立預約
        Given 在 當月底 ，建立掛號
        Given 在 當月底 ，產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 在同月份中，不得申報 <MedicalNhiCode> 診療代碼，確認結果是否為 <PassOrNot> 且檢查訊息類型為 W4_1
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastMedicalDate | MedicalNhiCode | MedicalTeeth | PassOrNot |
            # 同牙
            | 34001C       | 11         | MO           | 上個月初            | 34002C         | 11           | Pass      |
            | 34001C       | 11         | MO           | 當月初             | 34002C         | 11           | NotPass   |
            | 34001C       | 11         | MO           | 上個月初            | 90001C         | 11           | Pass      |
            | 34001C       | 11         | MO           | 當月初             | 90001C         | 11           | NotPass   |
            | 34001C       | 11         | MO           | 上個月初            | 90002C         | 11           | Pass      |
            | 34001C       | 11         | MO           | 當月初             | 90002C         | 11           | NotPass   |
            | 34001C       | 11         | MO           | 上個月初            | 90003C         | 11           | Pass      |
            | 34001C       | 11         | MO           | 當月初             | 90003C         | 11           | NotPass   |
            | 34001C       | 11         | MO           | 上個月初            | 90006C         | 11           | Pass      |
            | 34001C       | 11         | MO           | 當月初             | 90006C         | 11           | NotPass   |
            | 34001C       | 11         | MO           | 上個月初            | 90007C         | 11           | Pass      |
            | 34001C       | 11         | MO           | 當月初             | 90007C         | 11           | NotPass   |
            | 34001C       | 11         | MO           | 上個月初            | 90012C         | 11           | Pass      |
            | 34001C       | 11         | MO           | 當月初             | 90012C         | 11           | NotPass   |
            | 34001C       | 11         | MO           | 上個月初            | 90015C         | 11           | Pass      |
            | 34001C       | 11         | MO           | 當月初             | 90015C         | 11           | NotPass   |
            | 34001C       | 11         | MO           | 上個月初            | 90016C         | 11           | Pass      |
            | 34001C       | 11         | MO           | 當月初             | 90016C         | 11           | NotPass   |
            | 34001C       | 11         | MO           | 上個月初            | 90018C         | 11           | Pass      |
            | 34001C       | 11         | MO           | 當月初             | 90018C         | 11           | NotPass   |
            | 34001C       | 11         | MO           | 上個月初            | 90019C         | 11           | Pass      |
            | 34001C       | 11         | MO           | 當月初             | 90019C         | 11           | NotPass   |
            | 34001C       | 11         | MO           | 上個月初            | 90020C         | 11           | Pass      |
            | 34001C       | 11         | MO           | 當月初             | 90020C         | 11           | NotPass   |
            | 34001C       | 11         | MO           | 上個月初            | 90112C         | 11           | Pass      |
            | 34001C       | 11         | MO           | 當月初             | 90112C         | 11           | NotPass   |
            | 34001C       | 11         | MO           | 上個月初            | 92015C         | 11           | Pass      |
            | 34001C       | 11         | MO           | 當月初             | 92015C         | 11           | NotPass   |
            | 34001C       | 11         | MO           | 上個月初            | 92016C         | 11           | Pass      |
            | 34001C       | 11         | MO           | 當月初             | 92016C         | 11           | NotPass   |
            | 34001C       | 11         | MO           | 上個月初            | 92028C         | 11           | Pass      |
            | 34001C       | 11         | MO           | 當月初             | 92028C         | 11           | NotPass   |
            | 34001C       | 11         | MO           | 上個月初            | 92033C         | 11           | Pass      |
            | 34001C       | 11         | MO           | 當月初             | 92033C         | 11           | NotPass   |
            | 34001C       | 11         | MO           | 上個月初            | 92041C         | 11           | Pass      |
            | 34001C       | 11         | MO           | 當月初             | 92041C         | 11           | NotPass   |
            | 34001C       | 11         | MO           | 上個月初            | 92042C         | 11           | Pass      |
            | 34001C       | 11         | MO           | 當月初             | 92042C         | 11           | NotPass   |
            | 34001C       | 11         | MO           | 上個月初            | 92050C         | 11           | Pass      |
            | 34001C       | 11         | MO           | 當月初             | 92050C         | 11           | NotPass   |
            | 34001C       | 11         | MO           | 上個月初            | 92056C         | 11           | Pass      |
            | 34001C       | 11         | MO           | 當月初             | 92056C         | 11           | NotPass   |
            | 34001C       | 11         | MO           | 上個月初            | 92057C         | 11           | Pass      |
            | 34001C       | 11         | MO           | 當月初             | 92057C         | 11           | NotPass   |
            | 34001C       | 11         | MO           | 上個月初            | 92058C         | 11           | Pass      |
            | 34001C       | 11         | MO           | 當月初             | 92058C         | 11           | NotPass   |
            | 34001C       | 11         | MO           | 上個月初            | 92059C         | 11           | Pass      |
            | 34001C       | 11         | MO           | 當月初             | 92059C         | 11           | NotPass   |
            | 34001C       | 11         | MO           | 上個月初            | 92063C         | 11           | Pass      |
            | 34001C       | 11         | MO           | 當月初             | 92063C         | 11           | NotPass   |
            | 34001C       | 11         | MO           | 上個月初            | 92064C         | 11           | Pass      |
            | 34001C       | 11         | MO           | 當月初             | 92064C         | 11           | NotPass   |
            # 不同牙
            | 34001C       | 11         | MO           | 當月初             | 34002C         | 12           | Pass      |
            | 34001C       | 11         | MO           | 當月初             | 90001C         | 12           | Pass      |
            | 34001C       | 11         | MO           | 當月初             | 90002C         | 12           | Pass      |
            | 34001C       | 11         | MO           | 當月初             | 90003C         | 12           | Pass      |
            | 34001C       | 11         | MO           | 當月初             | 90006C         | 12           | Pass      |
            | 34001C       | 11         | MO           | 當月初             | 90007C         | 12           | Pass      |
            | 34001C       | 11         | MO           | 當月初             | 90012C         | 12           | Pass      |
            | 34001C       | 11         | MO           | 當月初             | 90015C         | 12           | Pass      |
            | 34001C       | 11         | MO           | 當月初             | 90016C         | 12           | Pass      |
            | 34001C       | 11         | MO           | 當月初             | 90018C         | 12           | Pass      |
            | 34001C       | 11         | MO           | 當月初             | 90019C         | 12           | Pass      |
            | 34001C       | 11         | MO           | 當月初             | 90020C         | 12           | Pass      |
            | 34001C       | 11         | MO           | 當月初             | 90112C         | 12           | Pass      |
            | 34001C       | 11         | MO           | 當月初             | 92015C         | 12           | Pass      |
            | 34001C       | 11         | MO           | 當月初             | 92016C         | 12           | Pass      |
            | 34001C       | 11         | MO           | 當月初             | 92028C         | 12           | Pass      |
            | 34001C       | 11         | MO           | 當月初             | 92033C         | 12           | Pass      |
            | 34001C       | 11         | MO           | 當月初             | 92041C         | 12           | Pass      |
            | 34001C       | 11         | MO           | 當月初             | 92042C         | 12           | Pass      |
            | 34001C       | 11         | MO           | 當月初             | 92050C         | 12           | Pass      |
            | 34001C       | 11         | MO           | 當月初             | 92056C         | 12           | Pass      |
            | 34001C       | 11         | MO           | 當月初             | 92057C         | 12           | Pass      |
            | 34001C       | 11         | MO           | 當月初             | 92058C         | 12           | Pass      |
            | 34001C       | 11         | MO           | 當月初             | 92059C         | 12           | Pass      |
            | 34001C       | 11         | MO           | 當月初             | 92063C         | 12           | Pass      |
            | 34001C       | 11         | MO           | 當月初             | 92064C         | 12           | Pass      |

    Scenario Outline: （Disposal）同日或同處置單不得申報 34002C/90001C~90003C/90006C/90007C/90012C/90015C/90016C/90018C~90020C/90112C/92015C/92016C/92028C/92033C/92041C/92042C/92050C/92056C~92059C/92063C/92064C
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
            | 34001C       | 11         | MO           | 34002C           | 11             | MO               | NotPass   |
            | 34001C       | 11         | MO           | 90001C           | 11             | MO               | NotPass   |
            | 34001C       | 11         | MO           | 90002C           | 11             | MO               | NotPass   |
            | 34001C       | 11         | MO           | 90003C           | 11             | MO               | NotPass   |
            | 34001C       | 11         | MO           | 90006C           | 11             | MO               | NotPass   |
            | 34001C       | 11         | MO           | 90007C           | 11             | MO               | NotPass   |
            | 34001C       | 11         | MO           | 90012C           | 11             | MO               | NotPass   |
            | 34001C       | 11         | MO           | 90015C           | 11             | MO               | NotPass   |
            | 34001C       | 11         | MO           | 90016C           | 11             | MO               | NotPass   |
            | 34001C       | 11         | MO           | 90018C           | 11             | MO               | NotPass   |
            | 34001C       | 11         | MO           | 90019C           | 11             | MO               | NotPass   |
            | 34001C       | 11         | MO           | 90020C           | 11             | MO               | NotPass   |
            | 34001C       | 11         | MO           | 90112C           | 11             | MO               | NotPass   |
            | 34001C       | 11         | MO           | 92015C           | 11             | MO               | NotPass   |
            | 34001C       | 11         | MO           | 92016C           | 11             | MO               | NotPass   |
            | 34001C       | 11         | MO           | 92028C           | 11             | MO               | NotPass   |
            | 34001C       | 11         | MO           | 92033C           | 11             | MO               | NotPass   |
            | 34001C       | 11         | MO           | 92041C           | 11             | MO               | NotPass   |
            | 34001C       | 11         | MO           | 92042C           | 11             | MO               | NotPass   |
            | 34001C       | 11         | MO           | 92050C           | 11             | MO               | NotPass   |
            | 34001C       | 11         | MO           | 92056C           | 11             | MO               | NotPass   |
            | 34001C       | 11         | MO           | 92057C           | 11             | MO               | NotPass   |
            | 34001C       | 11         | MO           | 92058C           | 11             | MO               | NotPass   |
            | 34001C       | 11         | MO           | 92059C           | 11             | MO               | NotPass   |
            | 34001C       | 11         | MO           | 92063C           | 11             | MO               | NotPass   |
            | 34001C       | 11         | MO           | 92064C           | 11             | MO               | NotPass   |
            # 不同牙
            | 34001C       | 11         | MO           | 34002C           | 12             | MO               | Pass      |
            | 34001C       | 11         | MO           | 90001C           | 12             | MO               | Pass      |
            | 34001C       | 11         | MO           | 90002C           | 12             | MO               | Pass      |
            | 34001C       | 11         | MO           | 90003C           | 12             | MO               | Pass      |
            | 34001C       | 11         | MO           | 90006C           | 12             | MO               | Pass      |
            | 34001C       | 11         | MO           | 90007C           | 12             | MO               | Pass      |
            | 34001C       | 11         | MO           | 90012C           | 12             | MO               | Pass      |
            | 34001C       | 11         | MO           | 90015C           | 12             | MO               | Pass      |
            | 34001C       | 11         | MO           | 90016C           | 12             | MO               | Pass      |
            | 34001C       | 11         | MO           | 90018C           | 12             | MO               | Pass      |
            | 34001C       | 11         | MO           | 90019C           | 12             | MO               | Pass      |
            | 34001C       | 11         | MO           | 90020C           | 12             | MO               | Pass      |
            | 34001C       | 11         | MO           | 90112C           | 12             | MO               | Pass      |
            | 34001C       | 11         | MO           | 92015C           | 12             | MO               | Pass      |
            | 34001C       | 11         | MO           | 92016C           | 12             | MO               | Pass      |
            | 34001C       | 11         | MO           | 92028C           | 12             | MO               | Pass      |
            | 34001C       | 11         | MO           | 92033C           | 12             | MO               | Pass      |
            | 34001C       | 11         | MO           | 92041C           | 12             | MO               | Pass      |
            | 34001C       | 11         | MO           | 92042C           | 12             | MO               | Pass      |
            | 34001C       | 11         | MO           | 92050C           | 12             | MO               | Pass      |
            | 34001C       | 11         | MO           | 92056C           | 12             | MO               | Pass      |
            | 34001C       | 11         | MO           | 92057C           | 12             | MO               | Pass      |
            | 34001C       | 11         | MO           | 92058C           | 12             | MO               | Pass      |
            | 34001C       | 11         | MO           | 92059C           | 12             | MO               | Pass      |
            | 34001C       | 11         | MO           | 92063C           | 12             | MO               | Pass      |
            | 34001C       | 11         | MO           | 92064C           | 12             | MO               | Pass      |

    Scenario Outline: （HIS-Today）同日或同處置單不得申報 34002C/90001C~90003C/90006C/90007C/90012C/90015C/90016C/90018C~90020C/90112C/92015C/92016C/92028C/92033C/92041C/92042C/92050C/92056C~92059C/92063C/92064C
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
            | 34001C       | 11         | MO           | 當日                | 34002C           | 11             | MO               | NotPass   |
            | 34001C       | 11         | MO           | 當日                | 90001C           | 11             | MO               | NotPass   |
            | 34001C       | 11         | MO           | 當日                | 90002C           | 11             | MO               | NotPass   |
            | 34001C       | 11         | MO           | 當日                | 90003C           | 11             | MO               | NotPass   |
            | 34001C       | 11         | MO           | 當日                | 90006C           | 11             | MO               | NotPass   |
            | 34001C       | 11         | MO           | 當日                | 90007C           | 11             | MO               | NotPass   |
            | 34001C       | 11         | MO           | 當日                | 90012C           | 11             | MO               | NotPass   |
            | 34001C       | 11         | MO           | 當日                | 90015C           | 11             | MO               | NotPass   |
            | 34001C       | 11         | MO           | 當日                | 90016C           | 11             | MO               | NotPass   |
            | 34001C       | 11         | MO           | 當日                | 90018C           | 11             | MO               | NotPass   |
            | 34001C       | 11         | MO           | 當日                | 90019C           | 11             | MO               | NotPass   |
            | 34001C       | 11         | MO           | 當日                | 90020C           | 11             | MO               | NotPass   |
            | 34001C       | 11         | MO           | 當日                | 90112C           | 11             | MO               | NotPass   |
            | 34001C       | 11         | MO           | 當日                | 92015C           | 11             | MO               | NotPass   |
            | 34001C       | 11         | MO           | 當日                | 92016C           | 11             | MO               | NotPass   |
            | 34001C       | 11         | MO           | 當日                | 92028C           | 11             | MO               | NotPass   |
            | 34001C       | 11         | MO           | 當日                | 92033C           | 11             | MO               | NotPass   |
            | 34001C       | 11         | MO           | 當日                | 92041C           | 11             | MO               | NotPass   |
            | 34001C       | 11         | MO           | 當日                | 92042C           | 11             | MO               | NotPass   |
            | 34001C       | 11         | MO           | 當日                | 92050C           | 11             | MO               | NotPass   |
            | 34001C       | 11         | MO           | 當日                | 92056C           | 11             | MO               | NotPass   |
            | 34001C       | 11         | MO           | 當日                | 92057C           | 11             | MO               | NotPass   |
            | 34001C       | 11         | MO           | 當日                | 92058C           | 11             | MO               | NotPass   |
            | 34001C       | 11         | MO           | 當日                | 92059C           | 11             | MO               | NotPass   |
            | 34001C       | 11         | MO           | 當日                | 92063C           | 11             | MO               | NotPass   |
            | 34001C       | 11         | MO           | 當日                | 92064C           | 11             | MO               | NotPass   |
            # 不同牙
            | 34001C       | 11         | MO           | 當日                | 34002C           | 12             | MO               | Pass      |
            | 34001C       | 11         | MO           | 當日                | 90001C           | 12             | MO               | Pass      |
            | 34001C       | 11         | MO           | 當日                | 90002C           | 12             | MO               | Pass      |
            | 34001C       | 11         | MO           | 當日                | 90003C           | 12             | MO               | Pass      |
            | 34001C       | 11         | MO           | 當日                | 90006C           | 12             | MO               | Pass      |
            | 34001C       | 11         | MO           | 當日                | 90007C           | 12             | MO               | Pass      |
            | 34001C       | 11         | MO           | 當日                | 90012C           | 12             | MO               | Pass      |
            | 34001C       | 11         | MO           | 當日                | 90015C           | 12             | MO               | Pass      |
            | 34001C       | 11         | MO           | 當日                | 90016C           | 12             | MO               | Pass      |
            | 34001C       | 11         | MO           | 當日                | 90018C           | 12             | MO               | Pass      |
            | 34001C       | 11         | MO           | 當日                | 90019C           | 12             | MO               | Pass      |
            | 34001C       | 11         | MO           | 當日                | 90020C           | 12             | MO               | Pass      |
            | 34001C       | 11         | MO           | 當日                | 90112C           | 12             | MO               | Pass      |
            | 34001C       | 11         | MO           | 當日                | 92015C           | 12             | MO               | Pass      |
            | 34001C       | 11         | MO           | 當日                | 92016C           | 12             | MO               | Pass      |
            | 34001C       | 11         | MO           | 當日                | 92028C           | 12             | MO               | Pass      |
            | 34001C       | 11         | MO           | 當日                | 92033C           | 12             | MO               | Pass      |
            | 34001C       | 11         | MO           | 當日                | 92041C           | 12             | MO               | Pass      |
            | 34001C       | 11         | MO           | 當日                | 92042C           | 12             | MO               | Pass      |
            | 34001C       | 11         | MO           | 當日                | 92050C           | 12             | MO               | Pass      |
            | 34001C       | 11         | MO           | 當日                | 92056C           | 12             | MO               | Pass      |
            | 34001C       | 11         | MO           | 當日                | 92057C           | 12             | MO               | Pass      |
            | 34001C       | 11         | MO           | 當日                | 92058C           | 12             | MO               | Pass      |
            | 34001C       | 11         | MO           | 當日                | 92059C           | 12             | MO               | Pass      |
            | 34001C       | 11         | MO           | 當日                | 92063C           | 12             | MO               | Pass      |
            | 34001C       | 11         | MO           | 當日                | 92064C           | 12             | MO               | Pass      |

    Scenario Outline: 檢查治療的牙位是否為 VALIDATED_ALL
        Given 建立醫師
        Given Scott 24 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 檢查 <IssueTeeth> 牙位，依 VALIDATED_ALL 判定是否為核可牙位，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            # 乳牙
            | 34001C       | 51         | DL           | Pass      |
            | 34001C       | 52         | DL           | Pass      |
            | 34001C       | 53         | DL           | Pass      |
            | 34001C       | 54         | DL           | Pass      |
            | 34001C       | 55         | DL           | Pass      |
            | 34001C       | 61         | DL           | Pass      |
            | 34001C       | 62         | DL           | Pass      |
            | 34001C       | 63         | DL           | Pass      |
            | 34001C       | 64         | DL           | Pass      |
            | 34001C       | 65         | DL           | Pass      |
            | 34001C       | 71         | DL           | Pass      |
            | 34001C       | 72         | DL           | Pass      |
            | 34001C       | 73         | DL           | Pass      |
            | 34001C       | 74         | DL           | Pass      |
            | 34001C       | 75         | DL           | Pass      |
            | 34001C       | 81         | DL           | Pass      |
            | 34001C       | 82         | DL           | Pass      |
            | 34001C       | 83         | DL           | Pass      |
            | 34001C       | 84         | DL           | Pass      |
            | 34001C       | 85         | DL           | Pass      |
            # 恆牙
            | 34001C       | 11         | DL           | Pass      |
            | 34001C       | 12         | DL           | Pass      |
            | 34001C       | 13         | DL           | Pass      |
            | 34001C       | 14         | DL           | Pass      |
            | 34001C       | 15         | DL           | Pass      |
            | 34001C       | 16         | DL           | Pass      |
            | 34001C       | 17         | DL           | Pass      |
            | 34001C       | 18         | DL           | Pass      |
            | 34001C       | 21         | DL           | Pass      |
            | 34001C       | 22         | DL           | Pass      |
            | 34001C       | 23         | DL           | Pass      |
            | 34001C       | 24         | DL           | Pass      |
            | 34001C       | 25         | DL           | Pass      |
            | 34001C       | 26         | DL           | Pass      |
            | 34001C       | 27         | DL           | Pass      |
            | 34001C       | 28         | DL           | Pass      |
            | 34001C       | 31         | DL           | Pass      |
            | 34001C       | 32         | DL           | Pass      |
            | 34001C       | 33         | DL           | Pass      |
            | 34001C       | 34         | DL           | Pass      |
            | 34001C       | 35         | DL           | Pass      |
            | 34001C       | 36         | DL           | Pass      |
            | 34001C       | 37         | DL           | Pass      |
            | 34001C       | 38         | DL           | Pass      |
            | 34001C       | 41         | DL           | Pass      |
            | 34001C       | 42         | DL           | Pass      |
            | 34001C       | 43         | DL           | Pass      |
            | 34001C       | 44         | DL           | Pass      |
            | 34001C       | 45         | DL           | Pass      |
            | 34001C       | 46         | DL           | Pass      |
            | 34001C       | 47         | DL           | Pass      |
            | 34001C       | 48         | DL           | Pass      |
            # 無牙
            | 34001C       |            | DL           | NotPass   |
            #
            | 34001C       | 19         | DL           | Pass      |
            | 34001C       | 29         | DL           | Pass      |
            | 34001C       | 39         | DL           | Pass      |
            | 34001C       | 49         | DL           | Pass      |
            | 34001C       | 59         | DL           | NotPass   |
            | 34001C       | 69         | DL           | NotPass   |
            | 34001C       | 79         | DL           | NotPass   |
            | 34001C       | 89         | DL           | NotPass   |
            | 34001C       | 99         | DL           | Pass      |
            # 牙位為區域型態
            | 34001C       | FM         | DL           | Pass      |
            | 34001C       | UR         | DL           | Pass      |
            | 34001C       | UL         | DL           | Pass      |
            | 34001C       | UA         | DL           | Pass      |
            | 34001C       | UB         | DL           | NotPass   |
            | 34001C       | LL         | DL           | Pass      |
            | 34001C       | LR         | DL           | Pass      |
            | 34001C       | LA         | DL           | Pass      |
            | 34001C       | LB         | DL           | NotPass   |
            # 非法牙位
            | 34001C       | 00         | DL           | NotPass   |
            | 34001C       | 01         | DL           | NotPass   |
            | 34001C       | 10         | DL           | NotPass   |
            | 34001C       | 56         | DL           | NotPass   |
            | 34001C       | 66         | DL           | NotPass   |
            | 34001C       | 76         | DL           | NotPass   |
            | 34001C       | 86         | DL           | NotPass   |
            | 34001C       | 91         | DL           | NotPass   |
