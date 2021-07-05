@nhi @nhi-34-series
Feature: 34004C 齒顎全景 X光片攝影

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
            | 34004C       | 11         | MO           | Pass      |

    Scenario Outline: （Disposal）34004C 終生只能申報一次
        Given 建立醫師
        Given Scott 24 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode         | NewTeeth         | NewSurface         |
            |         |       |         | <TreatmentNhiCode> | <TreatmentTeeth> | <TreatmentSurface> |
            |         |       |         | <IssueNhiCode>     | <IssueTeeth>     | <IssueSurface>     |
        Then <IssueNhiCode> 終生只能申報一次，確認結果是否為 <PassOrNot> 且檢查訊息類型為 D2_1
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | TreatmentNhiCode | TreatmentTeeth | TreatmentSurface | PassOrNot |
            | 34004C       | 36         | MOB          | 34004C           | 36             | MOB              | NotPass   |
            | 34004C       | 36         | MOB          | 01271C           | 36             | MOB              | Pass      |

    Scenario Outline: （HIS-Today）34004C 終生只能申報一次
        Given 建立醫師
        Given Scott 24 歲病人
        Given 在 當日 ，建立預約
        Given 在 當日 ，建立掛號
        Given 在 當日 ，產生診療計畫
        And 新增診療代碼:
            | PastDate | A72 | A73                | A74              | A75                | A76 | A77 | A78 | A79 |
            | 當日       | 3   | <TreatmentNhiCode> | <TreatmentTeeth> | <TreatmentSurface> | 0   | 1.0 | 03  |     |
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then <IssueNhiCode> 終生只能申報一次，確認結果是否為 <PassOrNot> 且檢查訊息類型為 D2_1
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | TreatmentNhiCode | TreatmentTeeth | TreatmentSurface | PassOrNot |
            | 34004C       | 36         | FM           | 34004C           | 36             | MO               | NotPass   |
            | 34004C       | 36         | FM           | 01271C           | 36             | MO               | Pass      |

    Scenario Outline: （IC-Today）34004C 終生只能申報一次
        Given 建立醫師
        Given Scott 24 歲病人
        Given 新增健保醫療:
            | PastDate | NhiCode          | Teeth          |
            | 當日       | <MedicalNhiCode> | <MedicalTeeth> |
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then <IssueNhiCode> 終生只能申報一次，確認結果是否為 <PassOrNot> 且檢查訊息類型為 D2_1
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | MedicalNhiCode | MedicalTeeth | PassOrNot |
            | 34004C       | 36         | FM           | 34004C         | 36           | NotPass   |
            | 34004C       | 36         | FM           | 01271C         | 36           | Pass      |

    Scenario Outline: （HIS）34004C 終生只能申報一次
        Given 建立醫師
        Given Scott 24 歲病人
        Given 在 昨日 ，建立預約
        Given 在 昨日 ，建立掛號
        Given 在 昨日 ，產生診療計畫
        And 新增診療代碼:
            | PastDate | A72 | A73                | A74              | A75                | A76 | A77 | A78 | A79 |
            | 昨日       | 3   | <TreatmentNhiCode> | <TreatmentTeeth> | <TreatmentSurface> | 0   | 1.0 | 03  |     |
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then <IssueNhiCode> 終生只能申報一次，確認結果是否為 <PassOrNot> 且檢查訊息類型為 D2_1
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | TreatmentNhiCode | TreatmentTeeth | TreatmentSurface | PassOrNot |
            | 34004C       | 36         | FM           | 34004C           | 36             | MO               | NotPass   |
            | 34004C       | 36         | FM           | 01271C           | 36             | MO               | Pass      |

    Scenario Outline: （IC）34004C 終生只能申報一次
        Given 建立醫師
        Given Scott 24 歲病人
        Given 新增健保醫療:
            | PastDate | NhiCode          | Teeth          |
            | 昨日       | <MedicalNhiCode> | <MedicalTeeth> |
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then <IssueNhiCode> 終生只能申報一次，確認結果是否為 <PassOrNot> 且檢查訊息類型為 D2_1
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | MedicalNhiCode | MedicalTeeth | PassOrNot |
            | 34004C       | 36         | FM           | 34004C         | 36           | NotPass   |
            | 34004C       | 36         | FM           | 01271C         | 36           | Pass      |

    Scenario Outline: 檢查治療的牙位是否為 GENERAL_TOOTH_AND_FM
        Given 建立醫師
        Given Scott 24 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 檢查 <IssueTeeth> 牙位，依 GENERAL_TOOTH_AND_FM 判定是否為核可牙位，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            # 乳牙
            | 34004C       | 51         | DL           | Pass      |
            | 34004C       | 52         | DL           | Pass      |
            | 34004C       | 53         | DL           | Pass      |
            | 34004C       | 54         | DL           | Pass      |
            | 34004C       | 55         | DL           | Pass      |
            | 34004C       | 61         | DL           | Pass      |
            | 34004C       | 62         | DL           | Pass      |
            | 34004C       | 63         | DL           | Pass      |
            | 34004C       | 64         | DL           | Pass      |
            | 34004C       | 65         | DL           | Pass      |
            | 34004C       | 71         | DL           | Pass      |
            | 34004C       | 72         | DL           | Pass      |
            | 34004C       | 73         | DL           | Pass      |
            | 34004C       | 74         | DL           | Pass      |
            | 34004C       | 75         | DL           | Pass      |
            | 34004C       | 81         | DL           | Pass      |
            | 34004C       | 82         | DL           | Pass      |
            | 34004C       | 83         | DL           | Pass      |
            | 34004C       | 84         | DL           | Pass      |
            | 34004C       | 85         | DL           | Pass      |
            # 恆牙
            | 34004C       | 11         | DL           | Pass      |
            | 34004C       | 12         | DL           | Pass      |
            | 34004C       | 13         | DL           | Pass      |
            | 34004C       | 14         | DL           | Pass      |
            | 34004C       | 15         | DL           | Pass      |
            | 34004C       | 16         | DL           | Pass      |
            | 34004C       | 17         | DL           | Pass      |
            | 34004C       | 18         | DL           | Pass      |
            | 34004C       | 21         | DL           | Pass      |
            | 34004C       | 22         | DL           | Pass      |
            | 34004C       | 23         | DL           | Pass      |
            | 34004C       | 24         | DL           | Pass      |
            | 34004C       | 25         | DL           | Pass      |
            | 34004C       | 26         | DL           | Pass      |
            | 34004C       | 27         | DL           | Pass      |
            | 34004C       | 28         | DL           | Pass      |
            | 34004C       | 31         | DL           | Pass      |
            | 34004C       | 32         | DL           | Pass      |
            | 34004C       | 33         | DL           | Pass      |
            | 34004C       | 34         | DL           | Pass      |
            | 34004C       | 35         | DL           | Pass      |
            | 34004C       | 36         | DL           | Pass      |
            | 34004C       | 37         | DL           | Pass      |
            | 34004C       | 38         | DL           | Pass      |
            | 34004C       | 41         | DL           | Pass      |
            | 34004C       | 42         | DL           | Pass      |
            | 34004C       | 43         | DL           | Pass      |
            | 34004C       | 44         | DL           | Pass      |
            | 34004C       | 45         | DL           | Pass      |
            | 34004C       | 46         | DL           | Pass      |
            | 34004C       | 47         | DL           | Pass      |
            | 34004C       | 48         | DL           | Pass      |
            # 無牙
            | 34004C       |            | DL           | NotPass   |
            #
            | 34004C       | 19         | DL           | Pass      |
            | 34004C       | 29         | DL           | Pass      |
            | 34004C       | 39         | DL           | Pass      |
            | 34004C       | 49         | DL           | Pass      |
            | 34004C       | 59         | DL           | NotPass   |
            | 34004C       | 69         | DL           | NotPass   |
            | 34004C       | 79         | DL           | NotPass   |
            | 34004C       | 89         | DL           | NotPass   |
            | 34004C       | 99         | DL           | Pass      |
            # 牙位為區域型態
            | 34004C       | FM         | DL           | Pass      |
            | 34004C       | UR         | DL           | NotPass   |
            | 34004C       | UL         | DL           | NotPass   |
            | 34004C       | UA         | DL           | NotPass   |
            | 34004C       | UB         | DL           | NotPass   |
            | 34004C       | LL         | DL           | NotPass   |
            | 34004C       | LR         | DL           | NotPass   |
            | 34004C       | LA         | DL           | NotPass   |
            | 34004C       | LB         | DL           | NotPass   |
            # 非法牙位
            | 34004C       | 00         | DL           | NotPass   |
            | 34004C       | 01         | DL           | NotPass   |
            | 34004C       | 10         | DL           | NotPass   |
            | 34004C       | 56         | DL           | NotPass   |
            | 34004C       | 66         | DL           | NotPass   |
            | 34004C       | 76         | DL           | NotPass   |
            | 34004C       | 86         | DL           | NotPass   |
            | 34004C       | 91         | DL           | NotPass   |
