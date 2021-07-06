@nhi @nhi-8-series
Feature: 8B 一、施作牙位：26二、服務項目1.恆牙第一大臼齒窩溝封填2.一般口腔檢查、衛教指導三、補助對象1.103年入學國小一年級學童（出生日期為96年9月2日至97年9月1日(含)）2.低收入戶及中低收入戶之國小二年級學童3.不含山地原住民鄉、離島地區及身心障礙國小一、二年級

    Scenario Outline: 全部檢核成功
        Given 建立醫師
        Given Kelly 11 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 確認診療代碼 <IssueNhiCode> ，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            | 8B           | 26         | FM           | Pass      |

    Scenario Outline: 檢查治療的牙位是否為 ONLY_26
        Given 建立醫師
        Given Kelly 11 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 檢查 <IssueTeeth> 牙位，依 ONLY_26 判定是否為核可牙位，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            # 乳牙
            | 8B           | 51         | DL           | NotPass   |
            | 8B           | 52         | DL           | NotPass   |
            | 8B           | 53         | DL           | NotPass   |
            | 8B           | 54         | DL           | NotPass   |
            | 8B           | 55         | DL           | NotPass   |
            | 8B           | 61         | DL           | NotPass   |
            | 8B           | 62         | DL           | NotPass   |
            | 8B           | 63         | DL           | NotPass   |
            | 8B           | 64         | DL           | NotPass   |
            | 8B           | 65         | DL           | NotPass   |
            | 8B           | 71         | DL           | NotPass   |
            | 8B           | 72         | DL           | NotPass   |
            | 8B           | 73         | DL           | NotPass   |
            | 8B           | 74         | DL           | NotPass   |
            | 8B           | 75         | DL           | NotPass   |
            | 8B           | 81         | DL           | NotPass   |
            | 8B           | 82         | DL           | NotPass   |
            | 8B           | 83         | DL           | NotPass   |
            | 8B           | 84         | DL           | NotPass   |
            | 8B           | 85         | DL           | NotPass   |
            # 恆牙
            | 8B           | 11         | DL           | NotPass   |
            | 8B           | 12         | DL           | NotPass   |
            | 8B           | 13         | DL           | NotPass   |
            | 8B           | 14         | DL           | NotPass   |
            | 8B           | 15         | DL           | NotPass   |
            | 8B           | 16         | DL           | NotPass   |
            | 8B           | 17         | DL           | NotPass   |
            | 8B           | 18         | DL           | NotPass   |
            | 8B           | 21         | DL           | NotPass   |
            | 8B           | 22         | DL           | NotPass   |
            | 8B           | 23         | DL           | NotPass   |
            | 8B           | 24         | DL           | NotPass   |
            | 8B           | 25         | DL           | NotPass   |
            | 8B           | 26         | DL           | Pass      |
            | 8B           | 27         | DL           | NotPass   |
            | 8B           | 28         | DL           | NotPass   |
            | 8B           | 31         | DL           | NotPass   |
            | 8B           | 32         | DL           | NotPass   |
            | 8B           | 33         | DL           | NotPass   |
            | 8B           | 34         | DL           | NotPass   |
            | 8B           | 35         | DL           | NotPass   |
            | 8B           | 36         | DL           | NotPass   |
            | 8B           | 37         | DL           | NotPass   |
            | 8B           | 38         | DL           | NotPass   |
            | 8B           | 41         | DL           | NotPass   |
            | 8B           | 42         | DL           | NotPass   |
            | 8B           | 43         | DL           | NotPass   |
            | 8B           | 44         | DL           | NotPass   |
            | 8B           | 45         | DL           | NotPass   |
            | 8B           | 46         | DL           | NotPass   |
            | 8B           | 47         | DL           | NotPass   |
            | 8B           | 48         | DL           | NotPass   |
            # 無牙
            | 8B           |            | DL           | NotPass   |
            #
            | 8B           | 19         | DL           | NotPass   |
            | 8B           | 29         | DL           | NotPass   |
            | 8B           | 39         | DL           | NotPass   |
            | 8B           | 49         | DL           | NotPass   |
            | 8B           | 59         | DL           | NotPass   |
            | 8B           | 69         | DL           | NotPass   |
            | 8B           | 79         | DL           | NotPass   |
            | 8B           | 89         | DL           | NotPass   |
            | 8B           | 99         | DL           | NotPass   |
            # 牙位為區域型態
            | 8B           | FM         | DL           | NotPass   |
            | 8B           | UR         | DL           | NotPass   |
            | 8B           | UL         | DL           | NotPass   |
            | 8B           | UA         | DL           | NotPass   |
            | 8B           | UB         | DL           | NotPass   |
            | 8B           | LR         | DL           | NotPass   |
            | 8B           | LL         | DL           | NotPass   |
            | 8B           | LA         | DL           | NotPass   |
            | 8B           | LB         | DL           | NotPass   |
            # 非法牙位
            | 8B           | 00         | DL           | NotPass   |
            | 8B           | 01         | DL           | NotPass   |
            | 8B           | 10         | DL           | NotPass   |
            | 8B           | 56         | DL           | NotPass   |
            | 8B           | 66         | DL           | NotPass   |
            | 8B           | 76         | DL           | NotPass   |
            | 8B           | 86         | DL           | NotPass   |
            | 8B           | 91         | DL           | NotPass   |

    Scenario Outline: （Disposal）8B 終生只能申報一次
        Given 建立醫師
        Given Kelly 11 歲病人
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
            | 8B           | 26         | MOB          | 8B               | 16             | MOB              | NotPass   |
            | 8B           | 26         | MOB          | 01271C           | 16             | MOB              | Pass      |

    Scenario Outline: （HIS-Today）8B 終生只能申報一次
        Given 建立醫師
        Given Kelly 11 歲病人
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
            | 8B           | 26         | FM           | 8B               | 16             | MO               | NotPass   |
            | 8B           | 26         | FM           | 01271C           | 16             | MO               | Pass      |

    Scenario Outline: （IC-Today）8B 終生只能申報一次
        Given 建立醫師
        Given Kelly 11 歲病人
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
            | 8B           | 26         | FM           | 8B             | 16           | NotPass   |
            | 8B           | 26         | FM           | 01271C         | 16           | Pass      |

    Scenario Outline: （HIS）8B 終生只能申報一次
        Given 建立醫師
        Given Kelly 11 歲病人
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
            | 8B           | 26         | FM           | 8B               | 16             | MO               | NotPass   |
            | 8B           | 26         | FM           | 01271C           | 16             | MO               | Pass      |

    Scenario Outline: （IC）8B 終生只能申報一次
        Given 建立醫師
        Given Kelly 11 歲病人
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
            | 8B           | 26         | FM           | 8B             | 16           | NotPass   |
            | 8B           | 26         | FM           | 01271C         | 16           | Pass      |
