@nhi @nhi-8-series
Feature: 8N 一、施作牙位：26二、服務項目1.恆牙第一大臼齒窩溝封填評估或脫落補施作2.一般口腔檢查、口腔保健衛教指導三、補助對象第二次評估檢查（同一牙位窩溝封填施作間隔12個月(含)以上，且與第一次評估檢查間隔6個月(含)以上）

    Scenario Outline: 全部檢核成功
        Given 建立醫師
        Given Kelly 11 歲病人
        Given 在 12個月前 ，建立預約
        Given 在 12個月前 ，建立掛號
        Given 在 12個月前 ，產生診療計畫
        And 新增診療代碼:
            | PastDate | A72 | A73 | A74          | A75            | A76 | A77 | A78 | A79 |
            | 12個月前    | 3   | 8B  | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
        Given 在 6個月前 ，建立預約
        Given 在 6個月前 ，建立掛號
        Given 在 6個月前 ，產生診療計畫
        And 新增診療代碼:
            | PastDate | A72 | A73 | A74          | A75            | A76 | A77 | A78 | A79 |
            | 6個月前     | 3   | 8J  | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 確認診療代碼 <IssueNhiCode> ，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            | 8N           | 26         | FM           | Pass      |

    Scenario Outline: 檢查治療的牙位是否為 ONLY_26
        Given 建立醫師
        Given Kelly 11 歲病人
        Given 在 12個月前 ，建立預約
        Given 在 12個月前 ，建立掛號
        Given 在 12個月前 ，產生診療計畫
        And 新增診療代碼:
            | PastDate | A72 | A73 | A74          | A75            | A76 | A77 | A78 | A79 |
            | 12個月前    | 3   | 8B  | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
        Given 在 6個月前 ，建立預約
        Given 在 6個月前 ，建立掛號
        Given 在 6個月前 ，產生診療計畫
        And 新增診療代碼:
            | PastDate | A72 | A73 | A74          | A75            | A76 | A77 | A78 | A79 |
            | 6個月前     | 3   | 8J  | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
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
            | 8N           | 51         | DL           | NotPass   |
            | 8N           | 52         | DL           | NotPass   |
            | 8N           | 53         | DL           | NotPass   |
            | 8N           | 54         | DL           | NotPass   |
            | 8N           | 55         | DL           | NotPass   |
            | 8N           | 61         | DL           | NotPass   |
            | 8N           | 62         | DL           | NotPass   |
            | 8N           | 63         | DL           | NotPass   |
            | 8N           | 64         | DL           | NotPass   |
            | 8N           | 65         | DL           | NotPass   |
            | 8N           | 71         | DL           | NotPass   |
            | 8N           | 72         | DL           | NotPass   |
            | 8N           | 73         | DL           | NotPass   |
            | 8N           | 74         | DL           | NotPass   |
            | 8N           | 75         | DL           | NotPass   |
            | 8N           | 81         | DL           | NotPass   |
            | 8N           | 82         | DL           | NotPass   |
            | 8N           | 83         | DL           | NotPass   |
            | 8N           | 84         | DL           | NotPass   |
            | 8N           | 85         | DL           | NotPass   |
            # 恆牙
            | 8N           | 11         | DL           | NotPass   |
            | 8N           | 12         | DL           | NotPass   |
            | 8N           | 13         | DL           | NotPass   |
            | 8N           | 14         | DL           | NotPass   |
            | 8N           | 15         | DL           | NotPass   |
            | 8N           | 16         | DL           | NotPass   |
            | 8N           | 17         | DL           | NotPass   |
            | 8N           | 18         | DL           | NotPass   |
            | 8N           | 21         | DL           | NotPass   |
            | 8N           | 22         | DL           | NotPass   |
            | 8N           | 23         | DL           | NotPass   |
            | 8N           | 24         | DL           | NotPass   |
            | 8N           | 25         | DL           | NotPass   |
            | 8N           | 26         | DL           | Pass      |
            | 8N           | 27         | DL           | NotPass   |
            | 8N           | 28         | DL           | NotPass   |
            | 8N           | 31         | DL           | NotPass   |
            | 8N           | 32         | DL           | NotPass   |
            | 8N           | 33         | DL           | NotPass   |
            | 8N           | 34         | DL           | NotPass   |
            | 8N           | 35         | DL           | NotPass   |
            | 8N           | 36         | DL           | NotPass   |
            | 8N           | 37         | DL           | NotPass   |
            | 8N           | 38         | DL           | NotPass   |
            | 8N           | 41         | DL           | NotPass   |
            | 8N           | 42         | DL           | NotPass   |
            | 8N           | 43         | DL           | NotPass   |
            | 8N           | 44         | DL           | NotPass   |
            | 8N           | 45         | DL           | NotPass   |
            | 8N           | 46         | DL           | NotPass   |
            | 8N           | 47         | DL           | NotPass   |
            | 8N           | 48         | DL           | NotPass   |
            # 無牙
            | 8N           |            | DL           | NotPass   |
            #
            | 8N           | 19         | DL           | NotPass   |
            | 8N           | 29         | DL           | NotPass   |
            | 8N           | 39         | DL           | NotPass   |
            | 8N           | 49         | DL           | NotPass   |
            | 8N           | 59         | DL           | NotPass   |
            | 8N           | 69         | DL           | NotPass   |
            | 8N           | 79         | DL           | NotPass   |
            | 8N           | 89         | DL           | NotPass   |
            | 8N           | 99         | DL           | NotPass   |
            # 牙位為區域型態
            | 8N           | FM         | DL           | NotPass   |
            | 8N           | UR         | DL           | NotPass   |
            | 8N           | UL         | DL           | NotPass   |
            | 8N           | UA         | DL           | NotPass   |
            | 8N           | UB         | DL           | NotPass   |
            | 8N           | LR         | DL           | NotPass   |
            | 8N           | LL         | DL           | NotPass   |
            | 8N           | LA         | DL           | NotPass   |
            | 8N           | LB         | DL           | NotPass   |
            # 非法牙位
            | 8N           | 00         | DL           | NotPass   |
            | 8N           | 01         | DL           | NotPass   |
            | 8N           | 10         | DL           | NotPass   |
            | 8N           | 56         | DL           | NotPass   |
            | 8N           | 66         | DL           | NotPass   |
            | 8N           | 76         | DL           | NotPass   |
            | 8N           | 86         | DL           | NotPass   |
            | 8N           | 91         | DL           | NotPass   |

    Scenario Outline: （Disposal）8N 終生只能申報一次
        Given 建立醫師
        Given Kelly 11 歲病人
        Given 在 12個月前 ，建立預約
        Given 在 12個月前 ，建立掛號
        Given 在 12個月前 ，產生診療計畫
        And 新增診療代碼:
            | PastDate | A72 | A73 | A74          | A75            | A76 | A77 | A78 | A79 |
            | 12個月前    | 3   | 8B  | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
        Given 在 6個月前 ，建立預約
        Given 在 6個月前 ，建立掛號
        Given 在 6個月前 ，產生診療計畫
        And 新增診療代碼:
            | PastDate | A72 | A73 | A74          | A75            | A76 | A77 | A78 | A79 |
            | 6個月前     | 3   | 8J  | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
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
            | 8N           | 26         | MOB          | 8N               | 26             | MOB              | NotPass   |
            | 8N           | 26         | MOB          | 01271C           | 26             | MOB              | Pass      |

    Scenario Outline: （HIS-Today）8N 終生只能申報一次
        Given 建立醫師
        Given Kelly 11 歲病人
        Given 在 12個月前 ，建立預約
        Given 在 12個月前 ，建立掛號
        Given 在 12個月前 ，產生診療計畫
        And 新增診療代碼:
            | PastDate | A72 | A73 | A74          | A75            | A76 | A77 | A78 | A79 |
            | 12個月前    | 3   | 8B  | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
        Given 在 6個月前 ，建立預約
        Given 在 6個月前 ，建立掛號
        Given 在 6個月前 ，產生診療計畫
        And 新增診療代碼:
            | PastDate | A72 | A73 | A74          | A75            | A76 | A77 | A78 | A79 |
            | 6個月前     | 3   | 8J  | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
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
            | 8N           | 26         | FM           | 8N               | 26             | MO               | NotPass   |
            | 8N           | 26         | FM           | 01271C           | 26             | MO               | Pass      |

    Scenario Outline: （IC-Today）8N 終生只能申報一次
        Given 建立醫師
        Given Kelly 11 歲病人
        Given 在 12個月前 ，建立預約
        Given 在 12個月前 ，建立掛號
        Given 在 12個月前 ，產生診療計畫
        And 新增診療代碼:
            | PastDate | A72 | A73 | A74          | A75            | A76 | A77 | A78 | A79 |
            | 12個月前    | 3   | 8B  | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
        Given 在 6個月前 ，建立預約
        Given 在 6個月前 ，建立掛號
        Given 在 6個月前 ，產生診療計畫
        And 新增診療代碼:
            | PastDate | A72 | A73 | A74          | A75            | A76 | A77 | A78 | A79 |
            | 6個月前     | 3   | 8J  | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
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
            | 8N           | 26         | FM           | 8N             | 26           | NotPass   |
            | 8N           | 26         | FM           | 01271C         | 26           | Pass      |

    Scenario Outline: （HIS）8N 終生只能申報一次
        Given 建立醫師
        Given Kelly 11 歲病人
        Given 在 12個月前 ，建立預約
        Given 在 12個月前 ，建立掛號
        Given 在 12個月前 ，產生診療計畫
        And 新增診療代碼:
            | PastDate | A72 | A73 | A74          | A75            | A76 | A77 | A78 | A79 |
            | 12個月前    | 3   | 8B  | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
        Given 在 6個月前 ，建立預約
        Given 在 6個月前 ，建立掛號
        Given 在 6個月前 ，產生診療計畫
        And 新增診療代碼:
            | PastDate | A72 | A73 | A74          | A75            | A76 | A77 | A78 | A79 |
            | 6個月前     | 3   | 8J  | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
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
            | 8N           | 26         | FM           | 8N               | 26             | MO               | NotPass   |
            | 8N           | 26         | FM           | 01271C           | 26             | MO               | Pass      |

    Scenario Outline: （IC）8N 終生只能申報一次
        Given 建立醫師
        Given Kelly 11 歲病人
        Given 在 12個月前 ，建立預約
        Given 在 12個月前 ，建立掛號
        Given 在 12個月前 ，產生診療計畫
        And 新增診療代碼:
            | PastDate | A72 | A73 | A74          | A75            | A76 | A77 | A78 | A79 |
            | 12個月前    | 3   | 8B  | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
        Given 在 6個月前 ，建立預約
        Given 在 6個月前 ，建立掛號
        Given 在 6個月前 ，產生診療計畫
        And 新增診療代碼:
            | PastDate | A72 | A73 | A74          | A75            | A76 | A77 | A78 | A79 |
            | 6個月前     | 3   | 8J  | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
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
            | 8N           | 26         | FM           | 8N             | 26           | NotPass   |
            | 8N           | 26         | FM           | 01271C         | 26           | Pass      |

    Scenario Outline: （HIS）12個月內，不應有 8B/8F 診療項目
        Given 建立醫師
        Given Kelly 5 歲病人
        Given 在 <PastTreatmentDate> ，建立預約
        Given 在 <PastTreatmentDate> ，建立掛號
        Given 在 <PastTreatmentDate> ，產生診療計畫
        And 新增診療代碼:
            | PastDate            | A72 | A73                | A74              | A75                | A76 | A77 | A78 | A79 |
            | <PastTreatmentDate> | 3   | <TreatmentNhiCode> | <TreatmentTeeth> | <TreatmentSurface> | 0   | 1.0 | 03  |     |
        Given 在 6個月前 ，建立預約
        Given 在 6個月前 ，建立掛號
        Given 在 6個月前 ，產生診療計畫
        And 新增診療代碼:
            | PastDate | A72 | A73 | A74          | A75            | A76 | A77 | A78 | A79 |
            | 6個月前     | 3   | 8J  | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 檢查 <IssueNhiCode> 診療項目，在病患過去 <GapMonth> 紀錄中，不應包含特定的 <TreatmentNhiCode> 診療代碼，確認結果是否為 <PassOrNot> 且檢查訊息類型為 D1_2_2
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastTreatmentDate | TreatmentNhiCode | TreatmentTeeth | TreatmentSurface | GapMonth | PassOrNot |
            | 8N           | 26         | DL           | 12個月前             | 8B               | 26             | DL               | 12個月     | Pass      |
            | 8N           | 26         | DL           | 11個月前             | 8B               | 26             | DL               | 12個月     | NotPass   |
            | 8N           | 26         | DL           | 10個月前             | 8B               | 26             | DL               | 12個月     | NotPass   |
            | 8N           | 26         | DL           | 9個月前              | 8B               | 26             | DL               | 12個月     | NotPass   |
            | 8N           | 26         | DL           | 8個月前              | 8B               | 26             | DL               | 12個月     | NotPass   |
            | 8N           | 26         | DL           | 7個月前              | 8B               | 26             | DL               | 12個月     | NotPass   |
            | 8N           | 26         | DL           | 6個月前              | 8B               | 26             | DL               | 12個月     | NotPass   |
            | 8N           | 26         | DL           | 5個月前              | 8B               | 26             | DL               | 12個月     | NotPass   |
            | 8N           | 26         | DL           | 4個月前              | 8B               | 26             | DL               | 12個月     | NotPass   |
            | 8N           | 26         | DL           | 3個月前              | 8B               | 26             | DL               | 12個月     | NotPass   |
            | 8N           | 26         | DL           | 2個月前              | 8B               | 26             | DL               | 12個月     | NotPass   |
            | 8N           | 26         | DL           | 1個月前              | 8B               | 26             | DL               | 12個月     | NotPass   |
            | 8N           | 26         | DL           | 當月                | 8B               | 26             | DL               | 12個月     | NotPass   |
            | 8N           | 26         | DL           | 12個月前             | 8F               | 26             | DL               | 12個月     | Pass      |
            | 8N           | 26         | DL           | 11個月前             | 8F               | 26             | DL               | 12個月     | NotPass   |
            | 8N           | 26         | DL           | 10個月前             | 8F               | 26             | DL               | 12個月     | NotPass   |
            | 8N           | 26         | DL           | 9個月前              | 8F               | 26             | DL               | 12個月     | NotPass   |
            | 8N           | 26         | DL           | 8個月前              | 8F               | 26             | DL               | 12個月     | NotPass   |
            | 8N           | 26         | DL           | 7個月前              | 8F               | 26             | DL               | 12個月     | NotPass   |
            | 8N           | 26         | DL           | 6個月前              | 8F               | 26             | DL               | 12個月     | NotPass   |
            | 8N           | 26         | DL           | 5個月前              | 8F               | 26             | DL               | 12個月     | NotPass   |
            | 8N           | 26         | DL           | 4個月前              | 8F               | 26             | DL               | 12個月     | NotPass   |
            | 8N           | 26         | DL           | 3個月前              | 8F               | 26             | DL               | 12個月     | NotPass   |
            | 8N           | 26         | DL           | 2個月前              | 8F               | 26             | DL               | 12個月     | NotPass   |
            | 8N           | 26         | DL           | 1個月前              | 8F               | 26             | DL               | 12個月     | NotPass   |
            | 8N           | 26         | DL           | 當月                | 8F               | 26             | DL               | 12個月     | NotPass   |

    Scenario Outline: （IC）12個月內，不應有 8B/8F 診療項目
        Given 建立醫師
        Given Kelly 5 歲病人
        Given 新增健保醫療:
            | PastDate          | NhiCode          | Teeth          |
            | <PastMedicalDays> | <MedicalNhiCode> | <MedicalTeeth> |
        Given 在 6個月前 ，建立預約
        Given 在 6個月前 ，建立掛號
        Given 在 6個月前 ，產生診療計畫
        And 新增診療代碼:
            | PastDate | A72 | A73 | A74          | A75            | A76 | A77 | A78 | A79 |
            | 6個月前     | 3   | 8J  | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 檢查 <IssueNhiCode> 診療項目，在病患過去 <GapMonth> 紀錄中，不應包含特定的 <MedicalNhiCode> 診療代碼，確認結果是否為 <PassOrNot> 且檢查訊息類型為 D1_2_2
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastMedicalDays | MedicalNhiCode | MedicalTeeth | GapMonth | PassOrNot |
            | 8N           | 26         | DL           | 12個月前           | 8B             | 26           | 12個月     | Pass      |
            | 8N           | 26         | DL           | 11個月前           | 8B             | 26           | 12個月     | NotPass   |
            | 8N           | 26         | DL           | 10個月前           | 8B             | 26           | 12個月     | NotPass   |
            | 8N           | 26         | DL           | 9個月前            | 8B             | 26           | 12個月     | NotPass   |
            | 8N           | 26         | DL           | 8個月前            | 8B             | 26           | 12個月     | NotPass   |
            | 8N           | 26         | DL           | 7個月前            | 8B             | 26           | 12個月     | NotPass   |
            | 8N           | 26         | DL           | 6個月前            | 8B             | 26           | 12個月     | NotPass   |
            | 8N           | 26         | DL           | 5個月前            | 8B             | 26           | 12個月     | NotPass   |
            | 8N           | 26         | DL           | 4個月前            | 8B             | 26           | 12個月     | NotPass   |
            | 8N           | 26         | DL           | 3個月前            | 8B             | 26           | 12個月     | NotPass   |
            | 8N           | 26         | DL           | 2個月前            | 8B             | 26           | 12個月     | NotPass   |
            | 8N           | 26         | DL           | 1個月前            | 8B             | 26           | 12個月     | NotPass   |
            | 8N           | 26         | DL           | 當月              | 8B             | 26           | 12個月     | NotPass   |
            | 8N           | 26         | DL           | 12個月前           | 8F             | 26           | 12個月     | Pass      |
            | 8N           | 26         | DL           | 11個月前           | 8F             | 26           | 12個月     | NotPass   |
            | 8N           | 26         | DL           | 10個月前           | 8F             | 26           | 12個月     | NotPass   |
            | 8N           | 26         | DL           | 9個月前            | 8F             | 26           | 12個月     | NotPass   |
            | 8N           | 26         | DL           | 8個月前            | 8F             | 26           | 12個月     | NotPass   |
            | 8N           | 26         | DL           | 7個月前            | 8F             | 26           | 12個月     | NotPass   |
            | 8N           | 26         | DL           | 6個月前            | 8F             | 26           | 12個月     | NotPass   |
            | 8N           | 26         | DL           | 5個月前            | 8F             | 26           | 12個月     | NotPass   |
            | 8N           | 26         | DL           | 4個月前            | 8F             | 26           | 12個月     | NotPass   |
            | 8N           | 26         | DL           | 3個月前            | 8F             | 26           | 12個月     | NotPass   |
            | 8N           | 26         | DL           | 2個月前            | 8F             | 26           | 12個月     | NotPass   |
            | 8N           | 26         | DL           | 1個月前            | 8F             | 26           | 12個月     | NotPass   |
            | 8N           | 26         | DL           | 當月              | 8F             | 26           | 12個月     | NotPass   |

    Scenario Outline: （HIS）6個月內，不應有 8J 診療項目
        Given 建立醫師
        Given Kelly 5 歲病人
        Given 在 12個月前 ，建立預約
        Given 在 12個月前 ，建立掛號
        Given 在 12個月前 ，產生診療計畫
        And 新增診療代碼:
            | PastDate | A72 | A73 | A74          | A75            | A76 | A77 | A78 | A79 |
            | 12個月前    | 3   | 8B  | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
        Given 在 <PastTreatmentDate> ，建立預約
        Given 在 <PastTreatmentDate> ，建立掛號
        Given 在 <PastTreatmentDate> ，產生診療計畫
        And 新增診療代碼:
            | PastDate            | A72 | A73                | A74              | A75                | A76 | A77 | A78 | A79 |
            | <PastTreatmentDate> | 3   | <TreatmentNhiCode> | <TreatmentTeeth> | <TreatmentSurface> | 0   | 1.0 | 03  |     |
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 檢查 <IssueNhiCode> 診療項目，在病患過去 <GapMonth> 紀錄中，不應包含特定的 <TreatmentNhiCode> 診療代碼，確認結果是否為 <PassOrNot> 且檢查訊息類型為 D1_2_2
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastTreatmentDate | TreatmentNhiCode | TreatmentTeeth | TreatmentSurface | GapMonth | PassOrNot |
            | 8N           | 26         | DL           | 6個月前              | 8J               | 26             | DL               | 6個月      | Pass      |
            | 8N           | 26         | DL           | 5個月前              | 8J               | 26             | DL               | 6個月      | NotPass   |
            | 8N           | 26         | DL           | 4個月前              | 8J               | 26             | DL               | 6個月      | NotPass   |
            | 8N           | 26         | DL           | 3個月前              | 8J               | 26             | DL               | 6個月      | NotPass   |
            | 8N           | 26         | DL           | 2個月前              | 8J               | 26             | DL               | 6個月      | NotPass   |
            | 8N           | 26         | DL           | 1個月前              | 8J               | 26             | DL               | 6個月      | NotPass   |
            | 8N           | 26         | DL           | 當月                | 8J               | 26             | DL               | 6個月      | NotPass   |

    Scenario Outline: （IC）6個月內，不應有 8J 診療項目
        Given 建立醫師
        Given Kelly 5 歲病人
        Given 在 12個月前 ，建立預約
        Given 在 12個月前 ，建立掛號
        Given 在 12個月前 ，產生診療計畫
        And 新增診療代碼:
            | PastDate | A72 | A73 | A74          | A75            | A76 | A77 | A78 | A79 |
            | 12個月前    | 3   | 8B  | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
        Given 新增健保醫療:
            | PastDate          | NhiCode          | Teeth          |
            | <PastMedicalDate> | <MedicalNhiCode> | <MedicalTeeth> |
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 檢查 <IssueNhiCode> 診療項目，在病患過去 <GapMonth> 紀錄中，不應包含特定的 <MedicalNhiCode> 診療代碼，確認結果是否為 <PassOrNot> 且檢查訊息類型為 D1_2_2
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastMedicalDate | MedicalNhiCode | MedicalTeeth | GapMonth | PassOrNot |
            | 8N           | 26         | DL           | 6個月前            | 8J             | 26           | 6個月      | Pass      |
            | 8N           | 26         | DL           | 5個月前            | 8J             | 26           | 6個月      | NotPass   |
            | 8N           | 26         | DL           | 4個月前            | 8J             | 26           | 6個月      | NotPass   |
            | 8N           | 26         | DL           | 3個月前            | 8J             | 26           | 6個月      | NotPass   |
            | 8N           | 26         | DL           | 2個月前            | 8J             | 26           | 6個月      | NotPass   |
            | 8N           | 26         | DL           | 1個月前            | 8J             | 26           | 6個月      | NotPass   |
            | 8N           | 26         | DL           | 當月              | 8J             | 26           | 6個月      | NotPass   |

    Scenario Outline: （HIS）8B/8F 曾經申報過
        Given 建立醫師
        Given Kelly 11 歲病人
        Given 在 12個月前 ，建立預約
        Given 在 12個月前 ，建立掛號
        Given 在 12個月前 ，產生診療計畫
        And 新增診療代碼:
            | PastDate | A72 | A73                | A74              | A75                | A76 | A77 | A78 | A79 |
            | 12個月前    | 3   | <TreatmentNhiCode> | <TreatmentTeeth> | <TreatmentSurface> | 0   | 1.0 | 03  |     |
        Given 在 6個月前 ，建立預約
        Given 在 6個月前 ，建立掛號
        Given 在 6個月前 ，產生診療計畫
        And 新增診療代碼:
            | PastDate | A72 | A73 | A74          | A75            | A76 | A77 | A78 | A79 |
            | 6個月前     | 3   | 8J  | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 需曾經申報過 8B/8F，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | TreatmentNhiCode | TreatmentTeeth | TreatmentSurface | PassOrNot |
            | 8N           | 26         | FM           | 8B               | 26             | MO               | Pass      |
            | 8N           | 26         | FM           | 8F               | 26             | MO               | Pass      |
            | 8N           | 26         | FM           | 01271C           | 26             | MO               | NotPass   |

    Scenario Outline: （IC）8B/8F 曾經申報過
        Given 建立醫師
        Given Kelly 11 歲病人
        Given 新增健保醫療:
            | PastDate | NhiCode          | Teeth          |
            | 12個月前    | <MedicalNhiCode> | <MedicalTeeth> |
        Given 在 6個月前 ，建立預約
        Given 在 6個月前 ，建立掛號
        Given 在 6個月前 ，產生診療計畫
        And 新增診療代碼:
            | PastDate | A72 | A73 | A74          | A75            | A76 | A77 | A78 | A79 |
            | 6個月前     | 3   | 8J  | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 需曾經申報過 8B/8F，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | MedicalNhiCode | MedicalTeeth | PassOrNot |
            | 8N           | 26         | FM           | 8B             | 26           | Pass      |
            | 8N           | 26         | FM           | 8F             | 26           | Pass      |
            | 8N           | 26         | FM           | 01271C         | 26           | NotPass   |

    Scenario Outline: （HIS）8J 曾經申報過
        Given 建立醫師
        Given Kelly 11 歲病人
        Given 在 12個月前 ，建立預約
        Given 在 12個月前 ，建立掛號
        Given 在 12個月前 ，產生診療計畫
        And 新增診療代碼:
            | PastDate | A72 | A73 | A74          | A75            | A76 | A77 | A78 | A79 |
            | 12個月前    | 3   | 8B  | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
        Given 在 6個月前 ，建立預約
        Given 在 6個月前 ，建立掛號
        Given 在 6個月前 ，產生診療計畫
        And 新增診療代碼:
            | PastDate | A72 | A73                | A74              | A75                | A76 | A77 | A78 | A79 |
            | 6個月前     | 3   | <TreatmentNhiCode> | <TreatmentTeeth> | <TreatmentSurface> | 0   | 1.0 | 03  |     |
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 需曾經申報過 8J，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | TreatmentNhiCode | TreatmentTeeth | TreatmentSurface | PassOrNot |
            | 8N           | 26         | FM           | 8J               | 26             | MO               | Pass      |
            | 8N           | 26         | FM           | 01271C           | 26             | MO               | NotPass   |

    Scenario Outline: （IC）8J 曾經申報過
        Given 建立醫師
        Given Kelly 11 歲病人
        Given 在 12個月前 ，建立預約
        Given 在 12個月前 ，建立掛號
        Given 在 12個月前 ，產生診療計畫
        And 新增診療代碼:
            | PastDate | A72 | A73 | A74          | A75            | A76 | A77 | A78 | A79 |
            | 12個月前    | 3   | 8B  | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
        Given 新增健保醫療:
            | PastDate | NhiCode          | Teeth          |
            | 6個月前     | <MedicalNhiCode> | <MedicalTeeth> |
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 需曾經申報過 8J，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | MedicalNhiCode | MedicalTeeth | PassOrNot |
            | 8N           | 26         | FM           | 8J             | 26           | Pass      |
            | 8N           | 26         | FM           | 01271C         | 26           | NotPass   |
