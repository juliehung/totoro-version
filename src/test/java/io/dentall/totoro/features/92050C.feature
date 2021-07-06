@nhi @nhi-92-series
Feature: 92050C 埋伏齒露出手術

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
            | 92050C       | 11         | DL           | Pass      |

    Scenario Outline: 提醒須檢附影像
        Given 建立醫師
        Given Scott 24 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 提醒"須檢附影像"，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            | 92050C       | 16         | MOB          | Pass      |

    Scenario Outline: 檢查治療的牙位是否為 PERMANENT_TOOTH
        Given 建立醫師
        Given Scott 24 歲病人
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
            | 92050C       | 51         | DL           | NotPass   |
            | 92050C       | 52         | DL           | NotPass   |
            | 92050C       | 53         | DL           | NotPass   |
            | 92050C       | 54         | DL           | NotPass   |
            | 92050C       | 55         | DL           | NotPass   |
            | 92050C       | 61         | DL           | NotPass   |
            | 92050C       | 62         | DL           | NotPass   |
            | 92050C       | 63         | DL           | NotPass   |
            | 92050C       | 64         | DL           | NotPass   |
            | 92050C       | 65         | DL           | NotPass   |
            | 92050C       | 71         | DL           | NotPass   |
            | 92050C       | 72         | DL           | NotPass   |
            | 92050C       | 73         | DL           | NotPass   |
            | 92050C       | 74         | DL           | NotPass   |
            | 92050C       | 75         | DL           | NotPass   |
            | 92050C       | 81         | DL           | NotPass   |
            | 92050C       | 82         | DL           | NotPass   |
            | 92050C       | 83         | DL           | NotPass   |
            | 92050C       | 84         | DL           | NotPass   |
            | 92050C       | 85         | DL           | NotPass   |
            # 恆牙
            | 92050C       | 11         | DL           | Pass      |
            | 92050C       | 12         | DL           | Pass      |
            | 92050C       | 13         | DL           | Pass      |
            | 92050C       | 14         | DL           | Pass      |
            | 92050C       | 15         | DL           | Pass      |
            | 92050C       | 16         | DL           | Pass      |
            | 92050C       | 17         | DL           | Pass      |
            | 92050C       | 18         | DL           | Pass      |
            | 92050C       | 21         | DL           | Pass      |
            | 92050C       | 22         | DL           | Pass      |
            | 92050C       | 23         | DL           | Pass      |
            | 92050C       | 24         | DL           | Pass      |
            | 92050C       | 25         | DL           | Pass      |
            | 92050C       | 26         | DL           | Pass      |
            | 92050C       | 27         | DL           | Pass      |
            | 92050C       | 28         | DL           | Pass      |
            | 92050C       | 31         | DL           | Pass      |
            | 92050C       | 32         | DL           | Pass      |
            | 92050C       | 33         | DL           | Pass      |
            | 92050C       | 34         | DL           | Pass      |
            | 92050C       | 35         | DL           | Pass      |
            | 92050C       | 36         | DL           | Pass      |
            | 92050C       | 37         | DL           | Pass      |
            | 92050C       | 38         | DL           | Pass      |
            | 92050C       | 41         | DL           | Pass      |
            | 92050C       | 42         | DL           | Pass      |
            | 92050C       | 43         | DL           | Pass      |
            | 92050C       | 44         | DL           | Pass      |
            | 92050C       | 45         | DL           | Pass      |
            | 92050C       | 46         | DL           | Pass      |
            | 92050C       | 47         | DL           | Pass      |
            | 92050C       | 48         | DL           | Pass      |
            # 無牙
            | 92050C       |            | DL           | NotPass   |
            #
            | 92050C       | 19         | DL           | Pass      |
            | 92050C       | 29         | DL           | Pass      |
            | 92050C       | 39         | DL           | Pass      |
            | 92050C       | 49         | DL           | Pass      |
            | 92050C       | 59         | DL           | NotPass   |
            | 92050C       | 69         | DL           | NotPass   |
            | 92050C       | 79         | DL           | NotPass   |
            | 92050C       | 89         | DL           | NotPass   |
            | 92050C       | 99         | DL           | Pass      |
            # 牙位為區域型態
            | 92050C       | FM         | DL           | NotPass   |
            | 92050C       | UR         | DL           | NotPass   |
            | 92050C       | UL         | DL           | NotPass   |
            | 92050C       | UA         | DL           | NotPass   |
            | 92050C       | UB         | DL           | NotPass   |
            | 92050C       | LL         | DL           | NotPass   |
            | 92050C       | LR         | DL           | NotPass   |
            | 92050C       | LA         | DL           | NotPass   |
            | 92050C       | LB         | DL           | NotPass   |
            # 非法牙位
            | 92050C       | 00         | DL           | NotPass   |
            | 92050C       | 01         | DL           | NotPass   |
            | 92050C       | 10         | DL           | NotPass   |
            | 92050C       | 56         | DL           | NotPass   |
            | 92050C       | 66         | DL           | NotPass   |
            | 92050C       | 76         | DL           | NotPass   |
            | 92050C       | 86         | DL           | NotPass   |
            | 92050C       | 91         | DL           | NotPass   |

    Scenario Outline: （Disposal）92050C 終生只能申報一次
        Given 建立醫師
        Given Scott 24 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode         | NewTeeth         | NewSurface         |
            |         |       |         | <TreatmentNhiCode> | <TreatmentTeeth> | <TreatmentSurface> |
            |         |       |         | <IssueNhiCode>     | <IssueTeeth>     | <IssueSurface>     |
        Then <IssueNhiCode> 終生只能申報一次，確認結果是否為 <PassOrNot> 且檢查訊息類型為 D2_2
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | TreatmentNhiCode | TreatmentTeeth | TreatmentSurface | PassOrNot |
            | 92050C       | 16         | MOB          | 92050C           | 16             | MOB              | NotPass   |
            | 92050C       | 16         | MOB          | 01271C           | 16             | MOB              | Pass      |

    Scenario Outline: （HIS-Today）92050C 終生只能申報一次
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
        Then <IssueNhiCode> 終生只能申報一次，確認結果是否為 <PassOrNot> 且檢查訊息類型為 D2_2
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | TreatmentNhiCode | TreatmentTeeth | TreatmentSurface | PassOrNot |
            | 92050C       | 16         | FM           | 92050C           | 16             | MO               | NotPass   |
            | 92050C       | 16         | FM           | 01271C           | 16             | MO               | Pass      |

    Scenario Outline: （IC-Today）92050C 終生只能申報一次
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
        Then <IssueNhiCode> 終生只能申報一次，確認結果是否為 <PassOrNot> 且檢查訊息類型為 D2_2
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | MedicalNhiCode | MedicalTeeth | PassOrNot |
            | 92050C       | 16         | FM           | 92050C         | 16           | NotPass   |
            | 92050C       | 16         | FM           | 01271C         | 16           | Pass      |

    Scenario Outline: （HIS）92050C 終生只能申報一次
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
        Then <IssueNhiCode> 終生只能申報一次，確認結果是否為 <PassOrNot> 且檢查訊息類型為 D2_2
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | TreatmentNhiCode | TreatmentTeeth | TreatmentSurface | PassOrNot |
            | 92050C       | 16         | FM           | 92050C           | 16             | MO               | NotPass   |
            | 92050C       | 16         | FM           | 01271C           | 16             | MO               | Pass      |

    Scenario Outline: （IC）92050C 終生只能申報一次
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
        Then <IssueNhiCode> 終生只能申報一次，確認結果是否為 <PassOrNot> 且檢查訊息類型為 D2_2
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | MedicalNhiCode | MedicalTeeth | PassOrNot |
            | 92050C       | 16         | FM           | 92050C         | 16           | NotPass   |
            | 92050C       | 16         | FM           | 01271C         | 16           | Pass      |
