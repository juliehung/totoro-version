@nhi @nhi-8-series
Feature: 8D 一、施作牙位：46二、服務項目1.恆牙第一大臼齒窩溝封填2.一般口腔檢查、衛教指導三、補助對象1.103年入學國小一年級學童（出生日期為96年9月2日至97年9月1日(含)）2.低收入戶及中低收入戶之國小二年級學童3.不含山地原住民鄉、離島地區及身心障礙國小一、二年級

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
            | 8D           | 46         | FM           | Pass      |

    Scenario Outline: 檢查治療的牙位是否為 ONLY_46
        Given 建立醫師
        Given Kelly 11 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 檢查 <IssueTeeth> 牙位，依 ONLY_46 判定是否為核可牙位，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            # 乳牙
            | 8D           | 51         | DL           | NotPass   |
            | 8D           | 52         | DL           | NotPass   |
            | 8D           | 53         | DL           | NotPass   |
            | 8D           | 54         | DL           | NotPass   |
            | 8D           | 55         | DL           | NotPass   |
            | 8D           | 61         | DL           | NotPass   |
            | 8D           | 62         | DL           | NotPass   |
            | 8D           | 63         | DL           | NotPass   |
            | 8D           | 64         | DL           | NotPass   |
            | 8D           | 65         | DL           | NotPass   |
            | 8D           | 71         | DL           | NotPass   |
            | 8D           | 72         | DL           | NotPass   |
            | 8D           | 73         | DL           | NotPass   |
            | 8D           | 74         | DL           | NotPass   |
            | 8D           | 75         | DL           | NotPass   |
            | 8D           | 81         | DL           | NotPass   |
            | 8D           | 82         | DL           | NotPass   |
            | 8D           | 83         | DL           | NotPass   |
            | 8D           | 84         | DL           | NotPass   |
            | 8D           | 85         | DL           | NotPass   |
            # 恆牙
            | 8D           | 11         | DL           | NotPass   |
            | 8D           | 12         | DL           | NotPass   |
            | 8D           | 13         | DL           | NotPass   |
            | 8D           | 14         | DL           | NotPass   |
            | 8D           | 15         | DL           | NotPass   |
            | 8D           | 16         | DL           | NotPass   |
            | 8D           | 17         | DL           | NotPass   |
            | 8D           | 18         | DL           | NotPass   |
            | 8D           | 21         | DL           | NotPass   |
            | 8D           | 22         | DL           | NotPass   |
            | 8D           | 23         | DL           | NotPass   |
            | 8D           | 24         | DL           | NotPass   |
            | 8D           | 25         | DL           | NotPass   |
            | 8D           | 26         | DL           | NotPass   |
            | 8D           | 27         | DL           | NotPass   |
            | 8D           | 28         | DL           | NotPass   |
            | 8D           | 31         | DL           | NotPass   |
            | 8D           | 32         | DL           | NotPass   |
            | 8D           | 33         | DL           | NotPass   |
            | 8D           | 34         | DL           | NotPass   |
            | 8D           | 35         | DL           | NotPass   |
            | 8D           | 36         | DL           | NotPass   |
            | 8D           | 37         | DL           | NotPass   |
            | 8D           | 38         | DL           | NotPass   |
            | 8D           | 41         | DL           | NotPass   |
            | 8D           | 42         | DL           | NotPass   |
            | 8D           | 43         | DL           | NotPass   |
            | 8D           | 44         | DL           | NotPass   |
            | 8D           | 45         | DL           | NotPass   |
            | 8D           | 46         | DL           | Pass      |
            | 8D           | 47         | DL           | NotPass   |
            | 8D           | 48         | DL           | NotPass   |
            # 無牙
            | 8D           |            | DL           | NotPass   |
            #
            | 8D           | 19         | DL           | NotPass   |
            | 8D           | 29         | DL           | NotPass   |
            | 8D           | 39         | DL           | NotPass   |
            | 8D           | 49         | DL           | NotPass   |
            | 8D           | 59         | DL           | NotPass   |
            | 8D           | 69         | DL           | NotPass   |
            | 8D           | 79         | DL           | NotPass   |
            | 8D           | 89         | DL           | NotPass   |
            | 8D           | 99         | DL           | NotPass   |
            # 牙位為區域型態
            | 8D           | FM         | DL           | NotPass   |
            | 8D           | UR         | DL           | NotPass   |
            | 8D           | UL         | DL           | NotPass   |
            | 8D           | UA         | DL           | NotPass   |
            | 8D           | UB         | DL           | NotPass   |
            | 8D           | LR         | DL           | NotPass   |
            | 8D           | LL         | DL           | NotPass   |
            | 8D           | LA         | DL           | NotPass   |
            | 8D           | LB         | DL           | NotPass   |
            # 非法牙位
            | 8D           | 00         | DL           | NotPass   |
            | 8D           | 01         | DL           | NotPass   |
            | 8D           | 10         | DL           | NotPass   |
            | 8D           | 56         | DL           | NotPass   |
            | 8D           | 66         | DL           | NotPass   |
            | 8D           | 76         | DL           | NotPass   |
            | 8D           | 86         | DL           | NotPass   |
            | 8D           | 91         | DL           | NotPass   |

    Scenario Outline: （Disposal）8D 終生只能申報一次
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
            | 8D           | 46         | MOB          | 8D               | 46             | MOB              | NotPass   |
            | 8D           | 46         | MOB          | 01271C           | 46             | MOB              | Pass      |

    Scenario Outline: （HIS-Today）8D 終生只能申報一次
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
            | 8D           | 46         | FM           | 8D               | 46             | MO               | NotPass   |
            | 8D           | 46         | FM           | 01271C           | 46             | MO               | Pass      |

    Scenario Outline: （IC-Today）8D 終生只能申報一次
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
            | 8D           | 46         | FM           | 8D             | 46           | NotPass   |
            | 8D           | 46         | FM           | 01271C         | 46           | Pass      |

    Scenario Outline: （HIS）8D 終生只能申報一次
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
            | 8D           | 46         | FM           | 8D               | 46             | MO               | NotPass   |
            | 8D           | 46         | FM           | 01271C           | 46             | MO               | Pass      |

    Scenario Outline: （IC）8D 終生只能申報一次
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
            | 8D           | 46         | FM           | 8D             | 46           | NotPass   |
            | 8D           | 46         | FM           | 01271C         | 46           | Pass      |
