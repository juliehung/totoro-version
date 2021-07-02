Feature: 8A 一、施作牙位：16二、服務項目1.恆牙第一大臼齒窩溝封填2.一般口腔檢查、衛教指導三、補助對象1.103年入學國小一年級學童（出生日期為96年9月2日至97年9月1日(含)）2.低收入戶及中低收入戶之國小二年級學童3.不含山地原住民鄉、離島地區及身心障礙國小一、二年級

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
            | 8A           | 16         | FM           | Pass      |

    Scenario Outline: 檢查治療的牙位是否為 ONLY_16
        Given 建立醫師
        Given Kelly 11 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 檢查 <IssueTeeth> 牙位，依 ONLY_16 判定是否為核可牙位，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            # 乳牙
            | 8A           | 51         | DL           | NotPass   |
            | 8A           | 52         | DL           | NotPass   |
            | 8A           | 53         | DL           | NotPass   |
            | 8A           | 54         | DL           | NotPass   |
            | 8A           | 55         | DL           | NotPass   |
            | 8A           | 61         | DL           | NotPass   |
            | 8A           | 62         | DL           | NotPass   |
            | 8A           | 63         | DL           | NotPass   |
            | 8A           | 64         | DL           | NotPass   |
            | 8A           | 65         | DL           | NotPass   |
            | 8A           | 71         | DL           | NotPass   |
            | 8A           | 72         | DL           | NotPass   |
            | 8A           | 73         | DL           | NotPass   |
            | 8A           | 74         | DL           | NotPass   |
            | 8A           | 75         | DL           | NotPass   |
            | 8A           | 81         | DL           | NotPass   |
            | 8A           | 82         | DL           | NotPass   |
            | 8A           | 83         | DL           | NotPass   |
            | 8A           | 84         | DL           | NotPass   |
            | 8A           | 85         | DL           | NotPass   |
            # 恆牙
            | 8A           | 11         | DL           | NotPass   |
            | 8A           | 12         | DL           | NotPass   |
            | 8A           | 13         | DL           | NotPass   |
            | 8A           | 14         | DL           | NotPass   |
            | 8A           | 15         | DL           | NotPass   |
            | 8A           | 16         | DL           | Pass      |
            | 8A           | 17         | DL           | NotPass   |
            | 8A           | 18         | DL           | NotPass   |
            | 8A           | 21         | DL           | NotPass   |
            | 8A           | 22         | DL           | NotPass   |
            | 8A           | 23         | DL           | NotPass   |
            | 8A           | 24         | DL           | NotPass   |
            | 8A           | 25         | DL           | NotPass   |
            | 8A           | 26         | DL           | NotPass   |
            | 8A           | 27         | DL           | NotPass   |
            | 8A           | 28         | DL           | NotPass   |
            | 8A           | 31         | DL           | NotPass   |
            | 8A           | 32         | DL           | NotPass   |
            | 8A           | 33         | DL           | NotPass   |
            | 8A           | 34         | DL           | NotPass   |
            | 8A           | 35         | DL           | NotPass   |
            | 8A           | 36         | DL           | NotPass   |
            | 8A           | 37         | DL           | NotPass   |
            | 8A           | 38         | DL           | NotPass   |
            | 8A           | 41         | DL           | NotPass   |
            | 8A           | 42         | DL           | NotPass   |
            | 8A           | 43         | DL           | NotPass   |
            | 8A           | 44         | DL           | NotPass   |
            | 8A           | 45         | DL           | NotPass   |
            | 8A           | 46         | DL           | NotPass   |
            | 8A           | 47         | DL           | NotPass   |
            | 8A           | 48         | DL           | NotPass   |
            # 無牙
            | 8A           |            | DL           | NotPass   |
            #
            | 8A           | 19         | DL           | NotPass   |
            | 8A           | 29         | DL           | NotPass   |
            | 8A           | 39         | DL           | NotPass   |
            | 8A           | 49         | DL           | NotPass   |
            | 8A           | 59         | DL           | NotPass   |
            | 8A           | 69         | DL           | NotPass   |
            | 8A           | 79         | DL           | NotPass   |
            | 8A           | 89         | DL           | NotPass   |
            | 8A           | 99         | DL           | NotPass   |
            # 牙位為區域型態
            | 8A           | FM         | DL           | NotPass   |
            | 8A           | UR         | DL           | NotPass   |
            | 8A           | UL         | DL           | NotPass   |
            | 8A           | UA         | DL           | NotPass   |
            | 8A           | UB         | DL           | NotPass   |
            | 8A           | LR         | DL           | NotPass   |
            | 8A           | LL         | DL           | NotPass   |
            | 8A           | LA         | DL           | NotPass   |
            | 8A           | LB         | DL           | NotPass   |
            # 非法牙位
            | 8A           | 00         | DL           | NotPass   |
            | 8A           | 01         | DL           | NotPass   |
            | 8A           | 10         | DL           | NotPass   |
            | 8A           | 56         | DL           | NotPass   |
            | 8A           | 66         | DL           | NotPass   |
            | 8A           | 76         | DL           | NotPass   |
            | 8A           | 86         | DL           | NotPass   |
            | 8A           | 91         | DL           | NotPass   |

    Scenario Outline: （Disposal）8A 終生只能申報一次
        Given 建立醫師
        Given Kelly 11 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode         | NewTeeth         | NewSurface         |
            |         |       |         | <TreatmentNhiCode> | <TreatmentTeeth> | <TreatmentSurface> |
            |         |       |         | <IssueNhiCode>     | <IssueTeeth>     | <IssueSurface>     |
        Then <IssueNhiCode> 終生只能申報一次，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | TreatmentNhiCode | TreatmentTeeth | TreatmentSurface | PassOrNot |
            | 8A           | 16         | MOB          | 8A               | 16             | MOB              | NotPass   |
            | 8A           | 16         | MOB          | 01271C           | 16             | MOB              | Pass      |

    Scenario Outline: （HIS-Today）8A 終生只能申報一次
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
        Then <IssueNhiCode> 終生只能申報一次，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | TreatmentNhiCode | TreatmentTeeth | TreatmentSurface | PassOrNot |
            | 8A           | 16         | FM           | 8A               | 16             | MO               | NotPass   |
            | 8A           | 16         | FM           | 01271C           | 16             | MO               | Pass      |

    Scenario Outline: （IC-Today）8A 終生只能申報一次
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
        Then <IssueNhiCode> 終生只能申報一次，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | MedicalNhiCode | MedicalTeeth | PassOrNot |
            | 8A           | 16         | FM           | 8A             | 16           | NotPass   |
            | 8A           | 16         | FM           | 01271C         | 16           | Pass      |

    Scenario Outline: （HIS）8A 終生只能申報一次
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
        Then <IssueNhiCode> 終生只能申報一次，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | TreatmentNhiCode | TreatmentTeeth | TreatmentSurface | PassOrNot |
            | 8A           | 16         | FM           | 8A               | 16             | MO               | NotPass   |
            | 8A           | 16         | FM           | 01271C           | 16             | MO               | Pass      |

    Scenario Outline: （IC）8A 終生只能申報一次
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
        Then <IssueNhiCode> 終生只能申報一次，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | MedicalNhiCode | MedicalTeeth | PassOrNot |
            | 8A           | 16         | FM           | 8A             | 16           | NotPass   |
            | 8A           | 16         | FM           | 01271C         | 16           | Pass      |
