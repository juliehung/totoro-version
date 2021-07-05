@nhi @nhi-8-series
Feature: 8C 一、施作牙位：36二、服務項目1.恆牙第一大臼齒窩溝封填2.一般口腔檢查、衛教指導三、補助對象1.103年入學國小一年級學童（出生日期為96年9月2日至97年9月1日(含)）2.低收入戶及中低收入戶之國小二年級學童3.不含山地原住民鄉、離島地區及身心障礙國小一、二年級

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
            | 8C           | 36         | FM           | Pass      |

    Scenario Outline: 檢查治療的牙位是否為 ONLY_36
        Given 建立醫師
        Given Kelly 11 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 檢查 <IssueTeeth> 牙位，依 ONLY_36 判定是否為核可牙位，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            # 乳牙
            | 8C           | 51         | DL           | NotPass   |
            | 8C           | 52         | DL           | NotPass   |
            | 8C           | 53         | DL           | NotPass   |
            | 8C           | 54         | DL           | NotPass   |
            | 8C           | 55         | DL           | NotPass   |
            | 8C           | 61         | DL           | NotPass   |
            | 8C           | 62         | DL           | NotPass   |
            | 8C           | 63         | DL           | NotPass   |
            | 8C           | 64         | DL           | NotPass   |
            | 8C           | 65         | DL           | NotPass   |
            | 8C           | 71         | DL           | NotPass   |
            | 8C           | 72         | DL           | NotPass   |
            | 8C           | 73         | DL           | NotPass   |
            | 8C           | 74         | DL           | NotPass   |
            | 8C           | 75         | DL           | NotPass   |
            | 8C           | 81         | DL           | NotPass   |
            | 8C           | 82         | DL           | NotPass   |
            | 8C           | 83         | DL           | NotPass   |
            | 8C           | 84         | DL           | NotPass   |
            | 8C           | 85         | DL           | NotPass   |
            # 恆牙
            | 8C           | 11         | DL           | NotPass   |
            | 8C           | 12         | DL           | NotPass   |
            | 8C           | 13         | DL           | NotPass   |
            | 8C           | 14         | DL           | NotPass   |
            | 8C           | 15         | DL           | NotPass   |
            | 8C           | 16         | DL           | NotPass   |
            | 8C           | 17         | DL           | NotPass   |
            | 8C           | 18         | DL           | NotPass   |
            | 8C           | 21         | DL           | NotPass   |
            | 8C           | 22         | DL           | NotPass   |
            | 8C           | 23         | DL           | NotPass   |
            | 8C           | 24         | DL           | NotPass   |
            | 8C           | 25         | DL           | NotPass   |
            | 8C           | 26         | DL           | NotPass   |
            | 8C           | 27         | DL           | NotPass   |
            | 8C           | 28         | DL           | NotPass   |
            | 8C           | 31         | DL           | NotPass   |
            | 8C           | 32         | DL           | NotPass   |
            | 8C           | 33         | DL           | NotPass   |
            | 8C           | 34         | DL           | NotPass   |
            | 8C           | 35         | DL           | NotPass   |
            | 8C           | 36         | DL           | Pass      |
            | 8C           | 37         | DL           | NotPass   |
            | 8C           | 38         | DL           | NotPass   |
            | 8C           | 41         | DL           | NotPass   |
            | 8C           | 42         | DL           | NotPass   |
            | 8C           | 43         | DL           | NotPass   |
            | 8C           | 44         | DL           | NotPass   |
            | 8C           | 45         | DL           | NotPass   |
            | 8C           | 46         | DL           | NotPass   |
            | 8C           | 47         | DL           | NotPass   |
            | 8C           | 48         | DL           | NotPass   |
            # 無牙
            | 8C           |            | DL           | NotPass   |
            #
            | 8C           | 19         | DL           | NotPass   |
            | 8C           | 29         | DL           | NotPass   |
            | 8C           | 39         | DL           | NotPass   |
            | 8C           | 49         | DL           | NotPass   |
            | 8C           | 59         | DL           | NotPass   |
            | 8C           | 69         | DL           | NotPass   |
            | 8C           | 79         | DL           | NotPass   |
            | 8C           | 89         | DL           | NotPass   |
            | 8C           | 99         | DL           | NotPass   |
            # 牙位為區域型態
            | 8C           | FM         | DL           | NotPass   |
            | 8C           | UR         | DL           | NotPass   |
            | 8C           | UL         | DL           | NotPass   |
            | 8C           | UA         | DL           | NotPass   |
            | 8C           | UB         | DL           | NotPass   |
            | 8C           | LR         | DL           | NotPass   |
            | 8C           | LL         | DL           | NotPass   |
            | 8C           | LA         | DL           | NotPass   |
            | 8C           | LB         | DL           | NotPass   |
            # 非法牙位
            | 8C           | 00         | DL           | NotPass   |
            | 8C           | 01         | DL           | NotPass   |
            | 8C           | 10         | DL           | NotPass   |
            | 8C           | 56         | DL           | NotPass   |
            | 8C           | 66         | DL           | NotPass   |
            | 8C           | 76         | DL           | NotPass   |
            | 8C           | 86         | DL           | NotPass   |
            | 8C           | 91         | DL           | NotPass   |

    Scenario Outline: （Disposal）8C 終生只能申報一次
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
            | 8C           | 36         | MOB          | 8C               | 36             | MOB              | NotPass   |
            | 8C           | 36         | MOB          | 01271C           | 36             | MOB              | Pass      |

    Scenario Outline: （HIS-Today）8C 終生只能申報一次
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
            | 8C           | 36         | FM           | 8C               | 36             | MO               | NotPass   |
            | 8C           | 36         | FM           | 01271C           | 36             | MO               | Pass      |

    Scenario Outline: （IC-Today）8C 終生只能申報一次
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
            | 8C           | 36         | FM           | 8C             | 36           | NotPass   |
            | 8C           | 36         | FM           | 01271C         | 36           | Pass      |

    Scenario Outline: （HIS）8C 終生只能申報一次
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
            | 8C           | 36         | FM           | 8C               | 36             | MO               | NotPass   |
            | 8C           | 36         | FM           | 01271C           | 36             | MO               | Pass      |

    Scenario Outline: （IC）8C 終生只能申報一次
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
            | 8C           | 36         | FM           | 8C             | 36           | NotPass   |
            | 8C           | 36         | FM           | 01271C         | 36           | Pass      |
