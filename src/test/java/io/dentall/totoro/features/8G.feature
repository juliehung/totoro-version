@nhi @nhi-8-series @part1
Feature: 8G 一、施作牙位：36二、服務項目1.恆牙第一大臼齒窩溝封填2.一般口腔檢查、衛教指導三、補助對象1.山地原住民鄉及離島地區之國小一、二年級學童補助條件：依兒童戶籍資料之所在地或學校所在地認定2.身心障礙之國小一、二年級補助條件：持有社政主管機關核發之身心障礙手冊或身心障礙證明

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
            | 8G           | 36         | FM           | Pass      |

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
            | 8G           | 51         | DL           | NotPass   |
            | 8G           | 52         | DL           | NotPass   |
            | 8G           | 53         | DL           | NotPass   |
            | 8G           | 54         | DL           | NotPass   |
            | 8G           | 55         | DL           | NotPass   |
            | 8G           | 61         | DL           | NotPass   |
            | 8G           | 62         | DL           | NotPass   |
            | 8G           | 63         | DL           | NotPass   |
            | 8G           | 64         | DL           | NotPass   |
            | 8G           | 65         | DL           | NotPass   |
            | 8G           | 71         | DL           | NotPass   |
            | 8G           | 72         | DL           | NotPass   |
            | 8G           | 73         | DL           | NotPass   |
            | 8G           | 74         | DL           | NotPass   |
            | 8G           | 75         | DL           | NotPass   |
            | 8G           | 81         | DL           | NotPass   |
            | 8G           | 82         | DL           | NotPass   |
            | 8G           | 83         | DL           | NotPass   |
            | 8G           | 84         | DL           | NotPass   |
            | 8G           | 85         | DL           | NotPass   |
            # 恆牙
            | 8G           | 11         | DL           | NotPass   |
            | 8G           | 12         | DL           | NotPass   |
            | 8G           | 13         | DL           | NotPass   |
            | 8G           | 14         | DL           | NotPass   |
            | 8G           | 15         | DL           | NotPass   |
            | 8G           | 16         | DL           | NotPass   |
            | 8G           | 17         | DL           | NotPass   |
            | 8G           | 18         | DL           | NotPass   |
            | 8G           | 21         | DL           | NotPass   |
            | 8G           | 22         | DL           | NotPass   |
            | 8G           | 23         | DL           | NotPass   |
            | 8G           | 24         | DL           | NotPass   |
            | 8G           | 25         | DL           | NotPass   |
            | 8G           | 26         | DL           | NotPass   |
            | 8G           | 27         | DL           | NotPass   |
            | 8G           | 28         | DL           | NotPass   |
            | 8G           | 31         | DL           | NotPass   |
            | 8G           | 32         | DL           | NotPass   |
            | 8G           | 33         | DL           | NotPass   |
            | 8G           | 34         | DL           | NotPass   |
            | 8G           | 35         | DL           | NotPass   |
            | 8G           | 36         | DL           | Pass      |
            | 8G           | 37         | DL           | NotPass   |
            | 8G           | 38         | DL           | NotPass   |
            | 8G           | 41         | DL           | NotPass   |
            | 8G           | 42         | DL           | NotPass   |
            | 8G           | 43         | DL           | NotPass   |
            | 8G           | 44         | DL           | NotPass   |
            | 8G           | 45         | DL           | NotPass   |
            | 8G           | 46         | DL           | NotPass   |
            | 8G           | 47         | DL           | NotPass   |
            | 8G           | 48         | DL           | NotPass   |
            # 無牙
            | 8G           |            | DL           | NotPass   |
            #
            | 8G           | 19         | DL           | NotPass   |
            | 8G           | 29         | DL           | NotPass   |
            | 8G           | 39         | DL           | NotPass   |
            | 8G           | 49         | DL           | NotPass   |
            | 8G           | 59         | DL           | NotPass   |
            | 8G           | 69         | DL           | NotPass   |
            | 8G           | 79         | DL           | NotPass   |
            | 8G           | 89         | DL           | NotPass   |
            | 8G           | 99         | DL           | NotPass   |
            # 牙位為區域型態
            | 8G           | FM         | DL           | NotPass   |
            | 8G           | UR         | DL           | NotPass   |
            | 8G           | UL         | DL           | NotPass   |
            | 8G           | UA         | DL           | NotPass   |
            | 8G           | UB         | DL           | NotPass   |
            | 8G           | LR         | DL           | NotPass   |
            | 8G           | LL         | DL           | NotPass   |
            | 8G           | LA         | DL           | NotPass   |
            | 8G           | LB         | DL           | NotPass   |
            # 非法牙位
            | 8G           | 00         | DL           | NotPass   |
            | 8G           | 01         | DL           | NotPass   |
            | 8G           | 10         | DL           | NotPass   |
            | 8G           | 56         | DL           | NotPass   |
            | 8G           | 66         | DL           | NotPass   |
            | 8G           | 76         | DL           | NotPass   |
            | 8G           | 86         | DL           | NotPass   |
            | 8G           | 91         | DL           | NotPass   |

    Scenario Outline: （Disposal）8G 終生只能申報一次
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
            | 8G           | 36         | MOB          | 8G               | 36             | MOB              | NotPass   |
            | 8G           | 36         | MOB          | 01271C           | 36             | MOB              | Pass      |

    Scenario Outline: （HIS-Today）8G 終生只能申報一次
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
            | 8G           | 36         | FM           | 8G               | 36             | MO               | NotPass   |
            | 8G           | 36         | FM           | 01271C           | 36             | MO               | Pass      |

    Scenario Outline: （IC-Today）8G 終生只能申報一次
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
            | 8G           | 36         | FM           | 8G             | 36           | NotPass   |
            | 8G           | 36         | FM           | 01271C         | 36           | Pass      |

    Scenario Outline: （HIS）8G 終生只能申報一次
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
            | 8G           | 36         | FM           | 8G               | 36             | MO               | NotPass   |
            | 8G           | 36         | FM           | 01271C           | 36             | MO               | Pass      |

    Scenario Outline: （IC）8G 終生只能申報一次
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
            | 8G           | 36         | FM           | 8G             | 36           | NotPass   |
            | 8G           | 36         | FM           | 01271C         | 36           | Pass      |
