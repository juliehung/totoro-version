Feature: 8F 一、施作牙位：26二、服務項目1.恆牙第一大臼齒窩溝封填2.一般口腔檢查、衛教指導三、補助對象1.山地原住民鄉及離島地區之國小一、二年級學童補助條件：依兒童戶籍資料之所在地或學校所在地認定2.身心障礙之國小一、二年級補助條件：持有社政主管機關核發之身心障礙手冊或身心障礙證明

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
            | 8F           | 26         | FM           | Pass      |

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
            | 8F           | 51         | DL           | NotPass   |
            | 8F           | 52         | DL           | NotPass   |
            | 8F           | 53         | DL           | NotPass   |
            | 8F           | 54         | DL           | NotPass   |
            | 8F           | 55         | DL           | NotPass   |
            | 8F           | 61         | DL           | NotPass   |
            | 8F           | 62         | DL           | NotPass   |
            | 8F           | 63         | DL           | NotPass   |
            | 8F           | 64         | DL           | NotPass   |
            | 8F           | 65         | DL           | NotPass   |
            | 8F           | 71         | DL           | NotPass   |
            | 8F           | 72         | DL           | NotPass   |
            | 8F           | 73         | DL           | NotPass   |
            | 8F           | 74         | DL           | NotPass   |
            | 8F           | 75         | DL           | NotPass   |
            | 8F           | 81         | DL           | NotPass   |
            | 8F           | 82         | DL           | NotPass   |
            | 8F           | 83         | DL           | NotPass   |
            | 8F           | 84         | DL           | NotPass   |
            | 8F           | 85         | DL           | NotPass   |
            # 恆牙
            | 8F           | 11         | DL           | NotPass   |
            | 8F           | 12         | DL           | NotPass   |
            | 8F           | 13         | DL           | NotPass   |
            | 8F           | 14         | DL           | NotPass   |
            | 8F           | 15         | DL           | NotPass   |
            | 8F           | 16         | DL           | NotPass   |
            | 8F           | 17         | DL           | NotPass   |
            | 8F           | 18         | DL           | NotPass   |
            | 8F           | 21         | DL           | NotPass   |
            | 8F           | 22         | DL           | NotPass   |
            | 8F           | 23         | DL           | NotPass   |
            | 8F           | 24         | DL           | NotPass   |
            | 8F           | 25         | DL           | NotPass   |
            | 8F           | 26         | DL           | Pass      |
            | 8F           | 27         | DL           | NotPass   |
            | 8F           | 28         | DL           | NotPass   |
            | 8F           | 31         | DL           | NotPass   |
            | 8F           | 32         | DL           | NotPass   |
            | 8F           | 33         | DL           | NotPass   |
            | 8F           | 34         | DL           | NotPass   |
            | 8F           | 35         | DL           | NotPass   |
            | 8F           | 36         | DL           | NotPass   |
            | 8F           | 37         | DL           | NotPass   |
            | 8F           | 38         | DL           | NotPass   |
            | 8F           | 41         | DL           | NotPass   |
            | 8F           | 42         | DL           | NotPass   |
            | 8F           | 43         | DL           | NotPass   |
            | 8F           | 44         | DL           | NotPass   |
            | 8F           | 45         | DL           | NotPass   |
            | 8F           | 46         | DL           | NotPass   |
            | 8F           | 47         | DL           | NotPass   |
            | 8F           | 48         | DL           | NotPass   |
            # 無牙
            | 8F           |            | DL           | NotPass   |
            #
            | 8F           | 19         | DL           | NotPass   |
            | 8F           | 29         | DL           | NotPass   |
            | 8F           | 39         | DL           | NotPass   |
            | 8F           | 49         | DL           | NotPass   |
            | 8F           | 59         | DL           | NotPass   |
            | 8F           | 69         | DL           | NotPass   |
            | 8F           | 79         | DL           | NotPass   |
            | 8F           | 89         | DL           | NotPass   |
            | 8F           | 99         | DL           | NotPass   |
            # 牙位為區域型態
            | 8F           | FM         | DL           | NotPass   |
            | 8F           | UR         | DL           | NotPass   |
            | 8F           | UL         | DL           | NotPass   |
            | 8F           | UA         | DL           | NotPass   |
            | 8F           | UB         | DL           | NotPass   |
            | 8F           | LR         | DL           | NotPass   |
            | 8F           | LL         | DL           | NotPass   |
            | 8F           | LA         | DL           | NotPass   |
            | 8F           | LB         | DL           | NotPass   |
            # 非法牙位
            | 8F           | 00         | DL           | NotPass   |
            | 8F           | 01         | DL           | NotPass   |
            | 8F           | 10         | DL           | NotPass   |
            | 8F           | 56         | DL           | NotPass   |
            | 8F           | 66         | DL           | NotPass   |
            | 8F           | 76         | DL           | NotPass   |
            | 8F           | 86         | DL           | NotPass   |
            | 8F           | 91         | DL           | NotPass   |

    Scenario Outline: （Disposal）8F 終生只能申報一次
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
            | 8F           | 26         | MOB          | 8F               | 16             | MOB              | NotPass   |
            | 8F           | 26         | MOB          | 01271C           | 16             | MOB              | Pass      |

    Scenario Outline: （HIS-Today）8F 終生只能申報一次
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
            | 8F           | 26         | FM           | 8F               | 16             | MO               | NotPass   |
            | 8F           | 26         | FM           | 01271C           | 16             | MO               | Pass      |

    Scenario Outline: （IC-Today）8F 終生只能申報一次
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
            | 8F           | 26         | FM           | 8F             | 16           | NotPass   |
            | 8F           | 26         | FM           | 01271C         | 16           | Pass      |

    Scenario Outline: （HIS）8F 終生只能申報一次
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
            | 8F           | 26         | FM           | 8F               | 16             | MO               | NotPass   |
            | 8F           | 26         | FM           | 01271C           | 16             | MO               | Pass      |

    Scenario Outline: （IC）8F 終生只能申報一次
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
            | 8F           | 26         | FM           | 8F             | 16           | NotPass   |
            | 8F           | 26         | FM           | 01271C         | 16           | Pass      |
