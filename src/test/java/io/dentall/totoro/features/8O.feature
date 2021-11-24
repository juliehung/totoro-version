@nhi @nhi-8-series @part2
Feature: 8O 一、施作牙位：36二、服務項目1.恆牙第一大臼齒窩溝封填評估或脫落補施作2.一般口腔檢查、口腔保健衛教指導三、補助對象第二次評估檢查（同一牙位窩溝封填施作間隔12個月(含)以上，且與第一次評估檢查間隔6個月(含)以上）

    Scenario Outline: 全部檢核成功
        Given 建立醫師
        Given Kelly 11 歲病人
        Given 在 12個月前 ，建立預約
        Given 在 12個月前 ，建立掛號
        Given 在 12個月前 ，產生診療計畫
        And 新增診療代碼:
            | PastDate | A72 | A73 | A74          | A75            | A76 | A77 | A78 | A79 |
            | 12個月前    | 3   | 8C  | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
        Given 在 6個月前 ，建立預約
        Given 在 6個月前 ，建立掛號
        Given 在 6個月前 ，產生診療計畫
        And 新增診療代碼:
            | PastDate | A72 | A73 | A74          | A75            | A76 | A77 | A78 | A79 |
            | 6個月前     | 3   | 8K  | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 確認診療代碼 <IssueNhiCode> ，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            | 8O           | 36         | FM           | Pass      |

    Scenario Outline: 檢查治療的牙位是否為 ONLY_36
        Given 建立醫師
        Given Kelly 11 歲病人
        Given 在 12個月前 ，建立預約
        Given 在 12個月前 ，建立掛號
        Given 在 12個月前 ，產生診療計畫
        And 新增診療代碼:
            | PastDate | A72 | A73 | A74          | A75            | A76 | A77 | A78 | A79 |
            | 12個月前    | 3   | 8C  | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
        Given 在 6個月前 ，建立預約
        Given 在 6個月前 ，建立掛號
        Given 在 6個月前 ，產生診療計畫
        And 新增診療代碼:
            | PastDate | A72 | A73 | A74          | A75            | A76 | A77 | A78 | A79 |
            | 6個月前     | 3   | 8K  | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
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
            | 8O           | 51         | DL           | NotPass   |
            | 8O           | 52         | DL           | NotPass   |
            | 8O           | 53         | DL           | NotPass   |
            | 8O           | 54         | DL           | NotPass   |
            | 8O           | 55         | DL           | NotPass   |
            | 8O           | 61         | DL           | NotPass   |
            | 8O           | 62         | DL           | NotPass   |
            | 8O           | 63         | DL           | NotPass   |
            | 8O           | 64         | DL           | NotPass   |
            | 8O           | 65         | DL           | NotPass   |
            | 8O           | 71         | DL           | NotPass   |
            | 8O           | 72         | DL           | NotPass   |
            | 8O           | 73         | DL           | NotPass   |
            | 8O           | 74         | DL           | NotPass   |
            | 8O           | 75         | DL           | NotPass   |
            | 8O           | 81         | DL           | NotPass   |
            | 8O           | 82         | DL           | NotPass   |
            | 8O           | 83         | DL           | NotPass   |
            | 8O           | 84         | DL           | NotPass   |
            | 8O           | 85         | DL           | NotPass   |
            # 恆牙
            | 8O           | 11         | DL           | NotPass   |
            | 8O           | 12         | DL           | NotPass   |
            | 8O           | 13         | DL           | NotPass   |
            | 8O           | 14         | DL           | NotPass   |
            | 8O           | 15         | DL           | NotPass   |
            | 8O           | 16         | DL           | NotPass   |
            | 8O           | 17         | DL           | NotPass   |
            | 8O           | 18         | DL           | NotPass   |
            | 8O           | 21         | DL           | NotPass   |
            | 8O           | 22         | DL           | NotPass   |
            | 8O           | 23         | DL           | NotPass   |
            | 8O           | 24         | DL           | NotPass   |
            | 8O           | 25         | DL           | NotPass   |
            | 8O           | 26         | DL           | NotPass   |
            | 8O           | 27         | DL           | NotPass   |
            | 8O           | 28         | DL           | NotPass   |
            | 8O           | 31         | DL           | NotPass   |
            | 8O           | 32         | DL           | NotPass   |
            | 8O           | 33         | DL           | NotPass   |
            | 8O           | 34         | DL           | NotPass   |
            | 8O           | 35         | DL           | NotPass   |
            | 8O           | 36         | DL           | Pass      |
            | 8O           | 37         | DL           | NotPass   |
            | 8O           | 38         | DL           | NotPass   |
            | 8O           | 41         | DL           | NotPass   |
            | 8O           | 42         | DL           | NotPass   |
            | 8O           | 43         | DL           | NotPass   |
            | 8O           | 44         | DL           | NotPass   |
            | 8O           | 45         | DL           | NotPass   |
            | 8O           | 46         | DL           | NotPass   |
            | 8O           | 47         | DL           | NotPass   |
            | 8O           | 48         | DL           | NotPass   |
            # 無牙
            | 8O           |            | DL           | NotPass   |
            #
            | 8O           | 19         | DL           | NotPass   |
            | 8O           | 29         | DL           | NotPass   |
            | 8O           | 39         | DL           | NotPass   |
            | 8O           | 49         | DL           | NotPass   |
            | 8O           | 59         | DL           | NotPass   |
            | 8O           | 69         | DL           | NotPass   |
            | 8O           | 79         | DL           | NotPass   |
            | 8O           | 89         | DL           | NotPass   |
            | 8O           | 99         | DL           | NotPass   |
            # 牙位為區域型態
            | 8O           | FM         | DL           | NotPass   |
            | 8O           | UR         | DL           | NotPass   |
            | 8O           | UL         | DL           | NotPass   |
            | 8O           | UA         | DL           | NotPass   |
            | 8O           | UB         | DL           | NotPass   |
            | 8O           | LR         | DL           | NotPass   |
            | 8O           | LL         | DL           | NotPass   |
            | 8O           | LA         | DL           | NotPass   |
            | 8O           | LB         | DL           | NotPass   |
            # 非法牙位
            | 8O           | 00         | DL           | NotPass   |
            | 8O           | 01         | DL           | NotPass   |
            | 8O           | 10         | DL           | NotPass   |
            | 8O           | 56         | DL           | NotPass   |
            | 8O           | 66         | DL           | NotPass   |
            | 8O           | 76         | DL           | NotPass   |
            | 8O           | 86         | DL           | NotPass   |
            | 8O           | 91         | DL           | NotPass   |

    Scenario Outline: （Disposal）8O 終生只能申報一次
        Given 建立醫師
        Given Kelly 11 歲病人
        Given 在 12個月前 ，建立預約
        Given 在 12個月前 ，建立掛號
        Given 在 12個月前 ，產生診療計畫
        And 新增診療代碼:
            | PastDate | A72 | A73 | A74          | A75            | A76 | A77 | A78 | A79 |
            | 12個月前    | 3   | 8C  | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
        Given 在 6個月前 ，建立預約
        Given 在 6個月前 ，建立掛號
        Given 在 6個月前 ，產生診療計畫
        And 新增診療代碼:
            | PastDate | A72 | A73 | A74          | A75            | A76 | A77 | A78 | A79 |
            | 6個月前     | 3   | 8K  | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
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
            | 8O           | 36         | MOB          | 8O               | 36             | MOB              | NotPass   |
            | 8O           | 36         | MOB          | 01271C           | 36             | MOB              | Pass      |

    Scenario Outline: （HIS-Today）8O 終生只能申報一次
        Given 建立醫師
        Given Kelly 11 歲病人
        Given 在 12個月前 ，建立預約
        Given 在 12個月前 ，建立掛號
        Given 在 12個月前 ，產生診療計畫
        And 新增診療代碼:
            | PastDate | A72 | A73 | A74          | A75            | A76 | A77 | A78 | A79 |
            | 12個月前    | 3   | 8C  | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
        Given 在 6個月前 ，建立預約
        Given 在 6個月前 ，建立掛號
        Given 在 6個月前 ，產生診療計畫
        And 新增診療代碼:
            | PastDate | A72 | A73 | A74          | A75            | A76 | A77 | A78 | A79 |
            | 6個月前     | 3   | 8K  | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
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
            | 8O           | 36         | FM           | 8O               | 36             | MO               | NotPass   |
            | 8O           | 36         | FM           | 01271C           | 36             | MO               | Pass      |

    Scenario Outline: （IC-Today）8O 終生只能申報一次
        Given 建立醫師
        Given Kelly 11 歲病人
        Given 在 12個月前 ，建立預約
        Given 在 12個月前 ，建立掛號
        Given 在 12個月前 ，產生診療計畫
        And 新增診療代碼:
            | PastDate | A72 | A73 | A74          | A75            | A76 | A77 | A78 | A79 |
            | 12個月前    | 3   | 8C  | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
        Given 在 6個月前 ，建立預約
        Given 在 6個月前 ，建立掛號
        Given 在 6個月前 ，產生診療計畫
        And 新增診療代碼:
            | PastDate | A72 | A73 | A74          | A75            | A76 | A77 | A78 | A79 |
            | 6個月前     | 3   | 8K  | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
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
            | 8O           | 36         | FM           | 8O             | 36           | NotPass   |
            | 8O           | 36         | FM           | 01271C         | 36           | Pass      |

    Scenario Outline: （HIS）8O 終生只能申報一次
        Given 建立醫師
        Given Kelly 11 歲病人
        Given 在 12個月前 ，建立預約
        Given 在 12個月前 ，建立掛號
        Given 在 12個月前 ，產生診療計畫
        And 新增診療代碼:
            | PastDate | A72 | A73 | A74          | A75            | A76 | A77 | A78 | A79 |
            | 12個月前    | 3   | 8C  | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
        Given 在 6個月前 ，建立預約
        Given 在 6個月前 ，建立掛號
        Given 在 6個月前 ，產生診療計畫
        And 新增診療代碼:
            | PastDate | A72 | A73 | A74          | A75            | A76 | A77 | A78 | A79 |
            | 6個月前     | 3   | 8K  | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
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
            | 8O           | 36         | FM           | 8O               | 36             | MO               | NotPass   |
            | 8O           | 36         | FM           | 01271C           | 36             | MO               | Pass      |

    Scenario Outline: （IC）8O 終生只能申報一次
        Given 建立醫師
        Given Kelly 11 歲病人
        Given 在 12個月前 ，建立預約
        Given 在 12個月前 ，建立掛號
        Given 在 12個月前 ，產生診療計畫
        And 新增診療代碼:
            | PastDate | A72 | A73 | A74          | A75            | A76 | A77 | A78 | A79 |
            | 12個月前    | 3   | 8C  | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
        Given 在 6個月前 ，建立預約
        Given 在 6個月前 ，建立掛號
        Given 在 6個月前 ，產生診療計畫
        And 新增診療代碼:
            | PastDate | A72 | A73 | A74          | A75            | A76 | A77 | A78 | A79 |
            | 6個月前     | 3   | 8K  | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
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
            | 8O           | 36         | FM           | 8O             | 36           | NotPass   |
            | 8O           | 36         | FM           | 01271C         | 36           | Pass      |

    Scenario Outline: （HIS）12個月內，不應有 8C/8G 診療項目
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
            | 6個月前     | 3   | 8K  | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 檢查 <IssueNhiCode> 診療項目，在病患過去 <GapMonth> 紀錄中，不應包含特定的 <TreatmentNhiCode> 診療代碼，確認結果是否為 <PassOrNot> 且檢查訊息類型為 D1_2_2
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastTreatmentDate | TreatmentNhiCode | TreatmentTeeth | TreatmentSurface | GapMonth | PassOrNot |
            | 8O           | 36         | DL           | 12個月前             | 8C               | 36             | DL               | 12個月     | Pass      |
            | 8O           | 36         | DL           | 11個月前             | 8C               | 36             | DL               | 12個月     | NotPass   |
            | 8O           | 36         | DL           | 10個月前             | 8C               | 36             | DL               | 12個月     | NotPass   |
            | 8O           | 36         | DL           | 9個月前              | 8C               | 36             | DL               | 12個月     | NotPass   |
            | 8O           | 36         | DL           | 8個月前              | 8C               | 36             | DL               | 12個月     | NotPass   |
            | 8O           | 36         | DL           | 7個月前              | 8C               | 36             | DL               | 12個月     | NotPass   |
            | 8O           | 36         | DL           | 6個月前              | 8C               | 36             | DL               | 12個月     | NotPass   |
            | 8O           | 36         | DL           | 5個月前              | 8C               | 36             | DL               | 12個月     | NotPass   |
            | 8O           | 36         | DL           | 4個月前              | 8C               | 36             | DL               | 12個月     | NotPass   |
            | 8O           | 36         | DL           | 3個月前              | 8C               | 36             | DL               | 12個月     | NotPass   |
            | 8O           | 36         | DL           | 2個月前              | 8C               | 36             | DL               | 12個月     | NotPass   |
            | 8O           | 36         | DL           | 1個月前              | 8C               | 36             | DL               | 12個月     | NotPass   |
            | 8O           | 36         | DL           | 當月                | 8C               | 36             | DL               | 12個月     | NotPass   |
            | 8O           | 36         | DL           | 12個月前             | 8G               | 36             | DL               | 12個月     | Pass      |
            | 8O           | 36         | DL           | 11個月前             | 8G               | 36             | DL               | 12個月     | NotPass   |
            | 8O           | 36         | DL           | 10個月前             | 8G               | 36             | DL               | 12個月     | NotPass   |
            | 8O           | 36         | DL           | 9個月前              | 8G               | 36             | DL               | 12個月     | NotPass   |
            | 8O           | 36         | DL           | 8個月前              | 8G               | 36             | DL               | 12個月     | NotPass   |
            | 8O           | 36         | DL           | 7個月前              | 8G               | 36             | DL               | 12個月     | NotPass   |
            | 8O           | 36         | DL           | 6個月前              | 8G               | 36             | DL               | 12個月     | NotPass   |
            | 8O           | 36         | DL           | 5個月前              | 8G               | 36             | DL               | 12個月     | NotPass   |
            | 8O           | 36         | DL           | 4個月前              | 8G               | 36             | DL               | 12個月     | NotPass   |
            | 8O           | 36         | DL           | 3個月前              | 8G               | 36             | DL               | 12個月     | NotPass   |
            | 8O           | 36         | DL           | 2個月前              | 8G               | 36             | DL               | 12個月     | NotPass   |
            | 8O           | 36         | DL           | 1個月前              | 8G               | 36             | DL               | 12個月     | NotPass   |
            | 8O           | 36         | DL           | 當月                | 8G               | 36             | DL               | 12個月     | NotPass   |

    Scenario Outline: （IC）12個月內，不應有 8C/8G 診療項目
        Given 建立醫師
        Given Kelly 5 歲病人
        Given 新增健保醫療:
            | PastDate          | NhiCode          | Teeth          |
            | <PastMedicalDate> | <MedicalNhiCode> | <MedicalTeeth> |
        Given 在 6個月前 ，建立預約
        Given 在 6個月前 ，建立掛號
        Given 在 6個月前 ，產生診療計畫
        And 新增診療代碼:
            | PastDate | A72 | A73 | A74          | A75            | A76 | A77 | A78 | A79 |
            | 6個月前     | 3   | 8K  | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 檢查 <IssueNhiCode> 診療項目，在病患過去 <GapMonth> 紀錄中，不應包含特定的 <MedicalNhiCode> 診療代碼，確認結果是否為 <PassOrNot> 且檢查訊息類型為 D1_2_2
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastMedicalDate | MedicalNhiCode | MedicalTeeth | GapMonth | PassOrNot |
            | 8O           | 36         | DL           | 12個月前           | 8C             | 36           | 12個月     | Pass      |
            | 8O           | 36         | DL           | 11個月前           | 8C             | 36           | 12個月     | NotPass   |
            | 8O           | 36         | DL           | 10個月前           | 8C             | 36           | 12個月     | NotPass   |
            | 8O           | 36         | DL           | 9個月前            | 8C             | 36           | 12個月     | NotPass   |
            | 8O           | 36         | DL           | 8個月前            | 8C             | 36           | 12個月     | NotPass   |
            | 8O           | 36         | DL           | 7個月前            | 8C             | 36           | 12個月     | NotPass   |
            | 8O           | 36         | DL           | 6個月前            | 8C             | 36           | 12個月     | NotPass   |
            | 8O           | 36         | DL           | 5個月前            | 8C             | 36           | 12個月     | NotPass   |
            | 8O           | 36         | DL           | 4個月前            | 8C             | 36           | 12個月     | NotPass   |
            | 8O           | 36         | DL           | 3個月前            | 8C             | 36           | 12個月     | NotPass   |
            | 8O           | 36         | DL           | 2個月前            | 8C             | 36           | 12個月     | NotPass   |
            | 8O           | 36         | DL           | 1個月前            | 8C             | 36           | 12個月     | NotPass   |
            | 8O           | 36         | DL           | 當月              | 8C             | 36           | 12個月     | NotPass   |
            | 8O           | 36         | DL           | 12個月前           | 8G             | 36           | 12個月     | Pass      |
            | 8O           | 36         | DL           | 11個月前           | 8G             | 36           | 12個月     | NotPass   |
            | 8O           | 36         | DL           | 10個月前           | 8G             | 36           | 12個月     | NotPass   |
            | 8O           | 36         | DL           | 9個月前            | 8G             | 36           | 12個月     | NotPass   |
            | 8O           | 36         | DL           | 8個月前            | 8G             | 36           | 12個月     | NotPass   |
            | 8O           | 36         | DL           | 7個月前            | 8G             | 36           | 12個月     | NotPass   |
            | 8O           | 36         | DL           | 6個月前            | 8G             | 36           | 12個月     | NotPass   |
            | 8O           | 36         | DL           | 5個月前            | 8G             | 36           | 12個月     | NotPass   |
            | 8O           | 36         | DL           | 4個月前            | 8G             | 36           | 12個月     | NotPass   |
            | 8O           | 36         | DL           | 3個月前            | 8G             | 36           | 12個月     | NotPass   |
            | 8O           | 36         | DL           | 2個月前            | 8G             | 36           | 12個月     | NotPass   |
            | 8O           | 36         | DL           | 1個月前            | 8G             | 36           | 12個月     | NotPass   |
            | 8O           | 36         | DL           | 當月              | 8G             | 36           | 12個月     | NotPass   |

    Scenario Outline: （HIS）6個月內，不應有 8K 診療項目
        Given 建立醫師
        Given Kelly 5 歲病人
        Given 在 12個月前 ，建立預約
        Given 在 12個月前 ，建立掛號
        Given 在 12個月前 ，產生診療計畫
        And 新增診療代碼:
            | PastDate | A72 | A73 | A74          | A75            | A76 | A77 | A78 | A79 |
            | 12個月前    | 3   | 8C  | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
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
            | 8O           | 36         | DL           | 6個月前              | 8K               | 36             | DL               | 6個月      | Pass      |
            | 8O           | 36         | DL           | 5個月前              | 8K               | 36             | DL               | 6個月      | NotPass   |
            | 8O           | 36         | DL           | 4個月前              | 8K               | 36             | DL               | 6個月      | NotPass   |
            | 8O           | 36         | DL           | 3個月前              | 8K               | 36             | DL               | 6個月      | NotPass   |
            | 8O           | 36         | DL           | 2個月前              | 8K               | 36             | DL               | 6個月      | NotPass   |
            | 8O           | 36         | DL           | 1個月前              | 8K               | 36             | DL               | 6個月      | NotPass   |
            | 8O           | 36         | DL           | 當月                | 8K               | 36             | DL               | 6個月      | NotPass   |

    Scenario Outline: （IC）6個月內，不應有 8K 診療項目
        Given 建立醫師
        Given Kelly 5 歲病人
        Given 在 12個月前 ，建立預約
        Given 在 12個月前 ，建立掛號
        Given 在 12個月前 ，產生診療計畫
        And 新增診療代碼:
            | PastDate | A72 | A73 | A74          | A75            | A76 | A77 | A78 | A79 |
            | 12個月前    | 3   | 8C  | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
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
            | 8O           | 36         | DL           | 6個月前            | 8K             | 36           | 6個月      | Pass      |
            | 8O           | 36         | DL           | 5個月前            | 8K             | 36           | 6個月      | NotPass   |
            | 8O           | 36         | DL           | 4個月前            | 8K             | 36           | 6個月      | NotPass   |
            | 8O           | 36         | DL           | 3個月前            | 8K             | 36           | 6個月      | NotPass   |
            | 8O           | 36         | DL           | 2個月前            | 8K             | 36           | 6個月      | NotPass   |
            | 8O           | 36         | DL           | 1個月前            | 8K             | 36           | 6個月      | NotPass   |
            | 8O           | 36         | DL           | 當月              | 8K             | 36           | 6個月      | NotPass   |

    Scenario Outline: （HIS）8C/8G 曾經申報過
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
            | 6個月前     | 3   | 8K  | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 需曾經申報過 8C/8G，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | TreatmentNhiCode | TreatmentTeeth | TreatmentSurface | PassOrNot |
            | 8O           | 36         | FM           | 8C               | 36             | MO               | Pass      |
            | 8O           | 36         | FM           | 8G               | 36             | MO               | Pass      |
            | 8O           | 36         | FM           | 01271C           | 36             | MO               | NotPass   |

    Scenario Outline: （IC）8C/8G 曾經申報過
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
            | 6個月前     | 3   | 8K  | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 需曾經申報過 8C/8G，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | MedicalNhiCode | MedicalTeeth | PassOrNot |
            | 8O           | 36         | FM           | 8C             | 36           | Pass      |
            | 8O           | 36         | FM           | 8G             | 36           | Pass      |
            | 8O           | 36         | FM           | 01271C         | 36           | NotPass   |

    Scenario Outline: （HIS）8K 曾經申報過
        Given 建立醫師
        Given Kelly 11 歲病人
        Given 在 12個月前 ，建立預約
        Given 在 12個月前 ，建立掛號
        Given 在 12個月前 ，產生診療計畫
        And 新增診療代碼:
            | PastDate | A72 | A73 | A74          | A75            | A76 | A77 | A78 | A79 |
            | 12個月前    | 3   | 8C  | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
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
        Then 需曾經申報過 8K，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | TreatmentNhiCode | TreatmentTeeth | TreatmentSurface | PassOrNot |
            | 8O           | 36         | FM           | 8K               | 36             | MO               | Pass      |
            | 8O           | 36         | FM           | 01271C           | 36             | MO               | NotPass   |

    Scenario Outline: （IC）8K 曾經申報過
        Given 建立醫師
        Given Kelly 11 歲病人
        Given 在 12個月前 ，建立預約
        Given 在 12個月前 ，建立掛號
        Given 在 12個月前 ，產生診療計畫
        And 新增診療代碼:
            | PastDate | A72 | A73 | A74          | A75            | A76 | A77 | A78 | A79 |
            | 12個月前    | 3   | 8C  | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
        Given 新增健保醫療:
            | PastDate | NhiCode          | Teeth          |
            | 6個月前     | <MedicalNhiCode> | <MedicalTeeth> |
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 需曾經申報過 8K，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | MedicalNhiCode | MedicalTeeth | PassOrNot |
            | 8O           | 36         | FM           | 8K             | 36           | Pass      |
            | 8O           | 36         | FM           | 01271C         | 36           | NotPass   |
