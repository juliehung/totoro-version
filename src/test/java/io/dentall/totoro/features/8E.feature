@nhi @nhi-8-series @part1
Feature: 8E 一、施作牙位：16二、服務項目1.恆牙第一大臼齒窩溝封填2.一般口腔檢查、衛教指導三、補助對象1.山地原住民鄉及離島地區之國小一、二年級學童補助條件：依兒童戶籍資料之所在地或學校所在地認定2.身心障礙之國小一、二年級補助條件：持有社政主管機關核發之身心障礙手冊或身心障礙證明

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
            | 8E           | 16         | FM           | Pass      |

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
            | 8E           | 51         | DL           | NotPass   |
            | 8E           | 52         | DL           | NotPass   |
            | 8E           | 53         | DL           | NotPass   |
            | 8E           | 54         | DL           | NotPass   |
            | 8E           | 55         | DL           | NotPass   |
            | 8E           | 61         | DL           | NotPass   |
            | 8E           | 62         | DL           | NotPass   |
            | 8E           | 63         | DL           | NotPass   |
            | 8E           | 64         | DL           | NotPass   |
            | 8E           | 65         | DL           | NotPass   |
            | 8E           | 71         | DL           | NotPass   |
            | 8E           | 72         | DL           | NotPass   |
            | 8E           | 73         | DL           | NotPass   |
            | 8E           | 74         | DL           | NotPass   |
            | 8E           | 75         | DL           | NotPass   |
            | 8E           | 81         | DL           | NotPass   |
            | 8E           | 82         | DL           | NotPass   |
            | 8E           | 83         | DL           | NotPass   |
            | 8E           | 84         | DL           | NotPass   |
            | 8E           | 85         | DL           | NotPass   |
            # 恆牙
            | 8E           | 11         | DL           | NotPass   |
            | 8E           | 12         | DL           | NotPass   |
            | 8E           | 13         | DL           | NotPass   |
            | 8E           | 14         | DL           | NotPass   |
            | 8E           | 15         | DL           | NotPass   |
            | 8E           | 16         | DL           | Pass      |
            | 8E           | 17         | DL           | NotPass   |
            | 8E           | 18         | DL           | NotPass   |
            | 8E           | 21         | DL           | NotPass   |
            | 8E           | 22         | DL           | NotPass   |
            | 8E           | 23         | DL           | NotPass   |
            | 8E           | 24         | DL           | NotPass   |
            | 8E           | 25         | DL           | NotPass   |
            | 8E           | 26         | DL           | NotPass   |
            | 8E           | 27         | DL           | NotPass   |
            | 8E           | 28         | DL           | NotPass   |
            | 8E           | 31         | DL           | NotPass   |
            | 8E           | 32         | DL           | NotPass   |
            | 8E           | 33         | DL           | NotPass   |
            | 8E           | 34         | DL           | NotPass   |
            | 8E           | 35         | DL           | NotPass   |
            | 8E           | 36         | DL           | NotPass   |
            | 8E           | 37         | DL           | NotPass   |
            | 8E           | 38         | DL           | NotPass   |
            | 8E           | 41         | DL           | NotPass   |
            | 8E           | 42         | DL           | NotPass   |
            | 8E           | 43         | DL           | NotPass   |
            | 8E           | 44         | DL           | NotPass   |
            | 8E           | 45         | DL           | NotPass   |
            | 8E           | 46         | DL           | NotPass   |
            | 8E           | 47         | DL           | NotPass   |
            | 8E           | 48         | DL           | NotPass   |
            # 無牙
            | 8E           |            | DL           | NotPass   |
            #
            | 8E           | 19         | DL           | NotPass   |
            | 8E           | 29         | DL           | NotPass   |
            | 8E           | 39         | DL           | NotPass   |
            | 8E           | 49         | DL           | NotPass   |
            | 8E           | 59         | DL           | NotPass   |
            | 8E           | 69         | DL           | NotPass   |
            | 8E           | 79         | DL           | NotPass   |
            | 8E           | 89         | DL           | NotPass   |
            | 8E           | 99         | DL           | NotPass   |
            # 牙位為區域型態
            | 8E           | FM         | DL           | NotPass   |
            | 8E           | UR         | DL           | NotPass   |
            | 8E           | UL         | DL           | NotPass   |
            | 8E           | UA         | DL           | NotPass   |
            | 8E           | UB         | DL           | NotPass   |
            | 8E           | LR         | DL           | NotPass   |
            | 8E           | LL         | DL           | NotPass   |
            | 8E           | LA         | DL           | NotPass   |
            | 8E           | LB         | DL           | NotPass   |
            # 非法牙位
            | 8E           | 00         | DL           | NotPass   |
            | 8E           | 01         | DL           | NotPass   |
            | 8E           | 10         | DL           | NotPass   |
            | 8E           | 56         | DL           | NotPass   |
            | 8E           | 66         | DL           | NotPass   |
            | 8E           | 76         | DL           | NotPass   |
            | 8E           | 86         | DL           | NotPass   |
            | 8E           | 91         | DL           | NotPass   |

    Scenario Outline: （Disposal）8E 終生只能申報一次
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
            | 8E           | 16         | MOB          | 8E               | 16             | MOB              | NotPass   |
            | 8E           | 16         | MOB          | 01271C           | 16             | MOB              | Pass      |

    Scenario Outline: （HIS-Today）8E 終生只能申報一次
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
            | 8E           | 16         | FM           | 8E               | 16             | MO               | NotPass   |
            | 8E           | 16         | FM           | 01271C           | 16             | MO               | Pass      |

    Scenario Outline: （IC-Today）8E 終生只能申報一次
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
            | 8E           | 16         | FM           | 8E             | 16           | NotPass   |
            | 8E           | 16         | FM           | 01271C         | 16           | Pass      |

    Scenario Outline: （HIS）8E 終生只能申報一次
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
            | 8E           | 16         | FM           | 8E               | 16             | MO               | NotPass   |
            | 8E           | 16         | FM           | 01271C           | 16             | MO               | Pass      |

    Scenario Outline: （IC）8E 終生只能申報一次
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
            | 8E           | 16         | FM           | 8E             | 16           | NotPass   |
            | 8E           | 16         | FM           | 01271C         | 16           | Pass      |
