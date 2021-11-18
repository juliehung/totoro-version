@nhi @nhi-8-series @part1
Feature: 8H 一、施作牙位：46二、服務項目1.恆牙第一大臼齒窩溝封填2.一般口腔檢查、衛教指導三、補助對象1.山地原住民鄉及離島地區之國小一、二年級學童補助條件：依兒童戶籍資料之所在地或學校所在地認定2.身心障礙之國小一、二年級補助條件：持有社政主管機關核發之身心障礙手冊或身心障礙證明

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
            | 8H           | 46         | FM           | Pass      |

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
            | 8H           | 51         | DL           | NotPass   |
            | 8H           | 52         | DL           | NotPass   |
            | 8H           | 53         | DL           | NotPass   |
            | 8H           | 54         | DL           | NotPass   |
            | 8H           | 55         | DL           | NotPass   |
            | 8H           | 61         | DL           | NotPass   |
            | 8H           | 62         | DL           | NotPass   |
            | 8H           | 63         | DL           | NotPass   |
            | 8H           | 64         | DL           | NotPass   |
            | 8H           | 65         | DL           | NotPass   |
            | 8H           | 71         | DL           | NotPass   |
            | 8H           | 72         | DL           | NotPass   |
            | 8H           | 73         | DL           | NotPass   |
            | 8H           | 74         | DL           | NotPass   |
            | 8H           | 75         | DL           | NotPass   |
            | 8H           | 81         | DL           | NotPass   |
            | 8H           | 82         | DL           | NotPass   |
            | 8H           | 83         | DL           | NotPass   |
            | 8H           | 84         | DL           | NotPass   |
            | 8H           | 85         | DL           | NotPass   |
            # 恆牙
            | 8H           | 11         | DL           | NotPass   |
            | 8H           | 12         | DL           | NotPass   |
            | 8H           | 13         | DL           | NotPass   |
            | 8H           | 14         | DL           | NotPass   |
            | 8H           | 15         | DL           | NotPass   |
            | 8H           | 16         | DL           | NotPass   |
            | 8H           | 17         | DL           | NotPass   |
            | 8H           | 18         | DL           | NotPass   |
            | 8H           | 21         | DL           | NotPass   |
            | 8H           | 22         | DL           | NotPass   |
            | 8H           | 23         | DL           | NotPass   |
            | 8H           | 24         | DL           | NotPass   |
            | 8H           | 25         | DL           | NotPass   |
            | 8H           | 26         | DL           | NotPass   |
            | 8H           | 27         | DL           | NotPass   |
            | 8H           | 28         | DL           | NotPass   |
            | 8H           | 31         | DL           | NotPass   |
            | 8H           | 32         | DL           | NotPass   |
            | 8H           | 33         | DL           | NotPass   |
            | 8H           | 34         | DL           | NotPass   |
            | 8H           | 35         | DL           | NotPass   |
            | 8H           | 36         | DL           | NotPass   |
            | 8H           | 37         | DL           | NotPass   |
            | 8H           | 38         | DL           | NotPass   |
            | 8H           | 41         | DL           | NotPass   |
            | 8H           | 42         | DL           | NotPass   |
            | 8H           | 43         | DL           | NotPass   |
            | 8H           | 44         | DL           | NotPass   |
            | 8H           | 45         | DL           | NotPass   |
            | 8H           | 46         | DL           | Pass      |
            | 8H           | 47         | DL           | NotPass   |
            | 8H           | 48         | DL           | NotPass   |
            # 無牙
            | 8H           |            | DL           | NotPass   |
            #
            | 8H           | 19         | DL           | NotPass   |
            | 8H           | 29         | DL           | NotPass   |
            | 8H           | 39         | DL           | NotPass   |
            | 8H           | 49         | DL           | NotPass   |
            | 8H           | 59         | DL           | NotPass   |
            | 8H           | 69         | DL           | NotPass   |
            | 8H           | 79         | DL           | NotPass   |
            | 8H           | 89         | DL           | NotPass   |
            | 8H           | 99         | DL           | NotPass   |
            # 牙位為區域型態
            | 8H           | FM         | DL           | NotPass   |
            | 8H           | UR         | DL           | NotPass   |
            | 8H           | UL         | DL           | NotPass   |
            | 8H           | UA         | DL           | NotPass   |
            | 8H           | UB         | DL           | NotPass   |
            | 8H           | LR         | DL           | NotPass   |
            | 8H           | LL         | DL           | NotPass   |
            | 8H           | LA         | DL           | NotPass   |
            | 8H           | LB         | DL           | NotPass   |
            # 非法牙位
            | 8H           | 00         | DL           | NotPass   |
            | 8H           | 01         | DL           | NotPass   |
            | 8H           | 10         | DL           | NotPass   |
            | 8H           | 56         | DL           | NotPass   |
            | 8H           | 66         | DL           | NotPass   |
            | 8H           | 76         | DL           | NotPass   |
            | 8H           | 86         | DL           | NotPass   |
            | 8H           | 91         | DL           | NotPass   |

    Scenario Outline: （Disposal）8H 終生只能申報一次
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
            | 8H           | 46         | MOB          | 8H               | 46             | MOB              | NotPass   |
            | 8H           | 46         | MOB          | 01271C           | 46             | MOB              | Pass      |

    Scenario Outline: （HIS-Today）8H 終生只能申報一次
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
            | 8H           | 46         | FM           | 8H               | 46             | MO               | NotPass   |
            | 8H           | 46         | FM           | 01271C           | 46             | MO               | Pass      |

    Scenario Outline: （IC-Today）8H 終生只能申報一次
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
            | 8H           | 46         | FM           | 8H             | 46           | NotPass   |
            | 8H           | 46         | FM           | 01271C         | 46           | Pass      |

    Scenario Outline: （HIS）8H 終生只能申報一次
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
            | 8H           | 46         | FM           | 8H               | 46             | MO               | NotPass   |
            | 8H           | 46         | FM           | 01271C           | 46             | MO               | Pass      |

    Scenario Outline: （IC）8H 終生只能申報一次
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
            | 8H           | 46         | FM           | 8H             | 46           | NotPass   |
            | 8H           | 46         | FM           | 01271C         | 46           | Pass      |
