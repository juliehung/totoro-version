Feature: 8M 一、施作牙位：16二、服務項目1.恆牙第一大臼齒窩溝封填評估或脫落補施作2.一般口腔檢查、口腔保健衛教指導三、補助對象第二次評估檢查（同一牙位窩溝封填施作間隔12個月(含)以上，且與第一次評估檢查間隔6個月(含)以上）

    Scenario Outline: 全部檢核成功
        Given 建立醫師
        Given Kelly 11 歲病人
        Given 在 12個月前 ，建立預約
        Given 在 12個月前 ，建立掛號
        Given 在 12個月前 ，產生診療計畫
        And 新增診療代碼:
            | PastDate | A72 | A73 | A74          | A75            | A76 | A77 | A78 | A79 |
            | 12個月前    | 3   | 8A  | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
        Given 在 6個月前 ，建立預約
        Given 在 6個月前 ，建立掛號
        Given 在 6個月前 ，產生診療計畫
        And 新增診療代碼:
            | PastDate | A72 | A73 | A74          | A75            | A76 | A77 | A78 | A79 |
            | 6個月前     | 3   | 8I  | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 確認診療代碼 <IssueNhiCode> ，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            | 8M           | 16         | FM           | Pass      |

    Scenario Outline: 檢查治療的牙位是否為 ONLY_16
        Given 建立醫師
        Given Kelly 11 歲病人
        Given 在 12個月前 ，建立預約
        Given 在 12個月前 ，建立掛號
        Given 在 12個月前 ，產生診療計畫
        And 新增診療代碼:
            | PastDate | A72 | A73 | A74          | A75            | A76 | A77 | A78 | A79 |
            | 12個月前    | 3   | 8A  | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
        Given 在 6個月前 ，建立預約
        Given 在 6個月前 ，建立掛號
        Given 在 6個月前 ，產生診療計畫
        And 新增診療代碼:
            | PastDate | A72 | A73 | A74          | A75            | A76 | A77 | A78 | A79 |
            | 6個月前     | 3   | 8I  | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
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
            | 8M           | 51         | DL           | NotPass   |
            | 8M           | 52         | DL           | NotPass   |
            | 8M           | 53         | DL           | NotPass   |
            | 8M           | 54         | DL           | NotPass   |
            | 8M           | 55         | DL           | NotPass   |
            | 8M           | 61         | DL           | NotPass   |
            | 8M           | 62         | DL           | NotPass   |
            | 8M           | 63         | DL           | NotPass   |
            | 8M           | 64         | DL           | NotPass   |
            | 8M           | 65         | DL           | NotPass   |
            | 8M           | 71         | DL           | NotPass   |
            | 8M           | 72         | DL           | NotPass   |
            | 8M           | 73         | DL           | NotPass   |
            | 8M           | 74         | DL           | NotPass   |
            | 8M           | 75         | DL           | NotPass   |
            | 8M           | 81         | DL           | NotPass   |
            | 8M           | 82         | DL           | NotPass   |
            | 8M           | 83         | DL           | NotPass   |
            | 8M           | 84         | DL           | NotPass   |
            | 8M           | 85         | DL           | NotPass   |
            # 恆牙
            | 8M           | 11         | DL           | NotPass   |
            | 8M           | 12         | DL           | NotPass   |
            | 8M           | 13         | DL           | NotPass   |
            | 8M           | 14         | DL           | NotPass   |
            | 8M           | 15         | DL           | NotPass   |
            | 8M           | 16         | DL           | Pass      |
            | 8M           | 17         | DL           | NotPass   |
            | 8M           | 18         | DL           | NotPass   |
            | 8M           | 21         | DL           | NotPass   |
            | 8M           | 22         | DL           | NotPass   |
            | 8M           | 23         | DL           | NotPass   |
            | 8M           | 24         | DL           | NotPass   |
            | 8M           | 25         | DL           | NotPass   |
            | 8M           | 26         | DL           | NotPass   |
            | 8M           | 27         | DL           | NotPass   |
            | 8M           | 28         | DL           | NotPass   |
            | 8M           | 31         | DL           | NotPass   |
            | 8M           | 32         | DL           | NotPass   |
            | 8M           | 33         | DL           | NotPass   |
            | 8M           | 34         | DL           | NotPass   |
            | 8M           | 35         | DL           | NotPass   |
            | 8M           | 36         | DL           | NotPass   |
            | 8M           | 37         | DL           | NotPass   |
            | 8M           | 38         | DL           | NotPass   |
            | 8M           | 41         | DL           | NotPass   |
            | 8M           | 42         | DL           | NotPass   |
            | 8M           | 43         | DL           | NotPass   |
            | 8M           | 44         | DL           | NotPass   |
            | 8M           | 45         | DL           | NotPass   |
            | 8M           | 46         | DL           | NotPass   |
            | 8M           | 47         | DL           | NotPass   |
            | 8M           | 48         | DL           | NotPass   |
            # 無牙
            | 8M           |            | DL           | NotPass   |
            #
            | 8M           | 19         | DL           | NotPass   |
            | 8M           | 29         | DL           | NotPass   |
            | 8M           | 39         | DL           | NotPass   |
            | 8M           | 49         | DL           | NotPass   |
            | 8M           | 59         | DL           | NotPass   |
            | 8M           | 69         | DL           | NotPass   |
            | 8M           | 79         | DL           | NotPass   |
            | 8M           | 89         | DL           | NotPass   |
            | 8M           | 99         | DL           | NotPass   |
            # 牙位為區域型態
            | 8M           | FM         | DL           | NotPass   |
            | 8M           | UR         | DL           | NotPass   |
            | 8M           | UL         | DL           | NotPass   |
            | 8M           | LR         | DL           | NotPass   |
            | 8M           | LL         | DL           | NotPass   |
            | 8M           | UB         | DL           | NotPass   |
            | 8M           | LB         | DL           | NotPass   |
            # 非法牙位
            | 8M           | 00         | DL           | NotPass   |
            | 8M           | 01         | DL           | NotPass   |
            | 8M           | 10         | DL           | NotPass   |
            | 8M           | 56         | DL           | NotPass   |
            | 8M           | 66         | DL           | NotPass   |
            | 8M           | 76         | DL           | NotPass   |
            | 8M           | 86         | DL           | NotPass   |
            | 8M           | 91         | DL           | NotPass   |

    Scenario Outline: （Disposal）8M 終生只能申報一次
        Given 建立醫師
        Given Kelly 11 歲病人
        Given 在 12個月前 ，建立預約
        Given 在 12個月前 ，建立掛號
        Given 在 12個月前 ，產生診療計畫
        And 新增診療代碼:
            | PastDate | A72 | A73 | A74          | A75            | A76 | A77 | A78 | A79 |
            | 12個月前    | 3   | 8A  | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
        Given 在 6個月前 ，建立預約
        Given 在 6個月前 ，建立掛號
        Given 在 6個月前 ，產生診療計畫
        And 新增診療代碼:
            | PastDate | A72 | A73 | A74          | A75            | A76 | A77 | A78 | A79 |
            | 6個月前     | 3   | 8I  | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
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
            | 8M           | 16         | MOB          | 8M               | 16             | MOB              | NotPass   |
            | 8M           | 16         | MOB          | 01271C           | 16             | MOB              | Pass      |

    Scenario Outline: （HIS-Today）8M 終生只能申報一次
        Given 建立醫師
        Given Kelly 11 歲病人
        Given 在 12個月前 ，建立預約
        Given 在 12個月前 ，建立掛號
        Given 在 12個月前 ，產生診療計畫
        And 新增診療代碼:
            | PastDate | A72 | A73 | A74          | A75            | A76 | A77 | A78 | A79 |
            | 12個月前    | 3   | 8A  | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
        Given 在 6個月前 ，建立預約
        Given 在 6個月前 ，建立掛號
        Given 在 6個月前 ，產生診療計畫
        And 新增診療代碼:
            | PastDate | A72 | A73 | A74          | A75            | A76 | A77 | A78 | A79 |
            | 6個月前     | 3   | 8I  | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
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
            | 8M           | 16         | FM           | 8M               | 16             | MO               | NotPass   |
            | 8M           | 16         | FM           | 01271C           | 16             | MO               | Pass      |

    Scenario Outline: （IC-Today）8M 終生只能申報一次
        Given 建立醫師
        Given Kelly 11 歲病人
        Given 在 12個月前 ，建立預約
        Given 在 12個月前 ，建立掛號
        Given 在 12個月前 ，產生診療計畫
        And 新增診療代碼:
            | PastDate | A72 | A73 | A74          | A75            | A76 | A77 | A78 | A79 |
            | 12個月前    | 3   | 8A  | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
        Given 在 6個月前 ，建立預約
        Given 在 6個月前 ，建立掛號
        Given 在 6個月前 ，產生診療計畫
        And 新增診療代碼:
            | PastDate | A72 | A73 | A74          | A75            | A76 | A77 | A78 | A79 |
            | 6個月前     | 3   | 8I  | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
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
            | 8M           | 16         | FM           | 8M             | 16           | NotPass   |
            | 8M           | 16         | FM           | 01271C         | 16           | Pass      |

    Scenario Outline: （HIS）8M 終生只能申報一次
        Given 建立醫師
        Given Kelly 11 歲病人
        Given 在 12個月前 ，建立預約
        Given 在 12個月前 ，建立掛號
        Given 在 12個月前 ，產生診療計畫
        And 新增診療代碼:
            | PastDate | A72 | A73 | A74          | A75            | A76 | A77 | A78 | A79 |
            | 12個月前    | 3   | 8A  | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
        Given 在 6個月前 ，建立預約
        Given 在 6個月前 ，建立掛號
        Given 在 6個月前 ，產生診療計畫
        And 新增診療代碼:
            | PastDate | A72 | A73 | A74          | A75            | A76 | A77 | A78 | A79 |
            | 6個月前     | 3   | 8I  | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
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
            | 8M           | 16         | FM           | 8M               | 16             | MO               | NotPass   |
            | 8M           | 16         | FM           | 01271C           | 16             | MO               | Pass      |

    Scenario Outline: （IC）8M 終生只能申報一次
        Given 建立醫師
        Given Kelly 11 歲病人
        Given 在 12個月前 ，建立預約
        Given 在 12個月前 ，建立掛號
        Given 在 12個月前 ，產生診療計畫
        And 新增診療代碼:
            | PastDate | A72 | A73 | A74          | A75            | A76 | A77 | A78 | A79 |
            | 12個月前    | 3   | 8A  | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
        Given 在 6個月前 ，建立預約
        Given 在 6個月前 ，建立掛號
        Given 在 6個月前 ，產生診療計畫
        And 新增診療代碼:
            | PastDate | A72 | A73 | A74          | A75            | A76 | A77 | A78 | A79 |
            | 6個月前     | 3   | 8I  | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
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
            | 8M           | 16         | FM           | 8M             | 16           | NotPass   |
            | 8M           | 16         | FM           | 01271C         | 16           | Pass      |

    Scenario Outline: （HIS）12個月內，不應有 8A/8E 診療項目
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
            | 6個月前     | 3   | 8I  | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then （HIS）檢查 <IssueNhiCode> 診療項目，在病患過去 <GapMonth> 紀錄中，不應包含特定的 <TreatmentNhiCode> 診療代碼，確認結果是否為 <PassOrNot> 且檢查訊息類型為 D1_2_2
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastTreatmentDate | TreatmentNhiCode | TreatmentTeeth | TreatmentSurface | GapMonth | PassOrNot |
            | 8M           | 16         | DL           | 12個月前             | 8A               | 16             | DL               | 12個月     | Pass      |
            | 8M           | 16         | DL           | 11個月前             | 8A               | 16             | DL               | 12個月     | NotPass   |
            | 8M           | 16         | DL           | 10個月前             | 8A               | 16             | DL               | 12個月     | NotPass   |
            | 8M           | 16         | DL           | 9個月前              | 8A               | 16             | DL               | 12個月     | NotPass   |
            | 8M           | 16         | DL           | 8個月前              | 8A               | 16             | DL               | 12個月     | NotPass   |
            | 8M           | 16         | DL           | 7個月前              | 8A               | 16             | DL               | 12個月     | NotPass   |
            | 8M           | 16         | DL           | 6個月前              | 8A               | 16             | DL               | 12個月     | NotPass   |
            | 8M           | 16         | DL           | 5個月前              | 8A               | 16             | DL               | 12個月     | NotPass   |
            | 8M           | 16         | DL           | 4個月前              | 8A               | 16             | DL               | 12個月     | NotPass   |
            | 8M           | 16         | DL           | 3個月前              | 8A               | 16             | DL               | 12個月     | NotPass   |
            | 8M           | 16         | DL           | 2個月前              | 8A               | 16             | DL               | 12個月     | NotPass   |
            | 8M           | 16         | DL           | 1個月前              | 8A               | 16             | DL               | 12個月     | NotPass   |
            | 8M           | 16         | DL           | 當月                | 8A               | 16             | DL               | 12個月     | NotPass   |
            | 8M           | 16         | DL           | 12個月前             | 8E               | 16             | DL               | 12個月     | Pass      |
            | 8M           | 16         | DL           | 11個月前             | 8E               | 16             | DL               | 12個月     | NotPass   |
            | 8M           | 16         | DL           | 10個月前             | 8E               | 16             | DL               | 12個月     | NotPass   |
            | 8M           | 16         | DL           | 9個月前              | 8E               | 16             | DL               | 12個月     | NotPass   |
            | 8M           | 16         | DL           | 8個月前              | 8E               | 16             | DL               | 12個月     | NotPass   |
            | 8M           | 16         | DL           | 7個月前              | 8E               | 16             | DL               | 12個月     | NotPass   |
            | 8M           | 16         | DL           | 6個月前              | 8E               | 16             | DL               | 12個月     | NotPass   |
            | 8M           | 16         | DL           | 5個月前              | 8E               | 16             | DL               | 12個月     | NotPass   |
            | 8M           | 16         | DL           | 4個月前              | 8E               | 16             | DL               | 12個月     | NotPass   |
            | 8M           | 16         | DL           | 3個月前              | 8E               | 16             | DL               | 12個月     | NotPass   |
            | 8M           | 16         | DL           | 2個月前              | 8E               | 16             | DL               | 12個月     | NotPass   |
            | 8M           | 16         | DL           | 1個月前              | 8E               | 16             | DL               | 12個月     | NotPass   |
            | 8M           | 16         | DL           | 當月                | 8E               | 16             | DL               | 12個月     | NotPass   |

    Scenario Outline: （IC）12個月內，不應有 8A/8E 診療項目
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
            | 6個月前     | 3   | 8I  | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then （IC）檢查 <IssueNhiCode> 診療項目，在病患過去 <GapMonth> 紀錄中，不應包含特定的 <MedicalNhiCode> 診療代碼，確認結果是否為 <PassOrNot> 且檢查訊息類型為 D1_2_2
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastMedicalDays | MedicalNhiCode | MedicalTeeth | GapMonth | PassOrNot |
            | 8M           | 16         | DL           | 12個月前           | 8A             | 16           | 12個月     | Pass      |
            | 8M           | 16         | DL           | 11個月前           | 8A             | 16           | 12個月     | NotPass   |
            | 8M           | 16         | DL           | 10個月前           | 8A             | 16           | 12個月     | NotPass   |
            | 8M           | 16         | DL           | 9個月前            | 8A             | 16           | 12個月     | NotPass   |
            | 8M           | 16         | DL           | 8個月前            | 8A             | 16           | 12個月     | NotPass   |
            | 8M           | 16         | DL           | 7個月前            | 8A             | 16           | 12個月     | NotPass   |
            | 8M           | 16         | DL           | 6個月前            | 8A             | 16           | 12個月     | NotPass   |
            | 8M           | 16         | DL           | 5個月前            | 8A             | 16           | 12個月     | NotPass   |
            | 8M           | 16         | DL           | 4個月前            | 8A             | 16           | 12個月     | NotPass   |
            | 8M           | 16         | DL           | 3個月前            | 8A             | 16           | 12個月     | NotPass   |
            | 8M           | 16         | DL           | 2個月前            | 8A             | 16           | 12個月     | NotPass   |
            | 8M           | 16         | DL           | 1個月前            | 8A             | 16           | 12個月     | NotPass   |
            | 8M           | 16         | DL           | 當月              | 8A             | 16           | 12個月     | NotPass   |
            | 8M           | 16         | DL           | 12個月前           | 8E             | 16           | 12個月     | Pass      |
            | 8M           | 16         | DL           | 11個月前           | 8E             | 16           | 12個月     | NotPass   |
            | 8M           | 16         | DL           | 10個月前           | 8E             | 16           | 12個月     | NotPass   |
            | 8M           | 16         | DL           | 9個月前            | 8E             | 16           | 12個月     | NotPass   |
            | 8M           | 16         | DL           | 8個月前            | 8E             | 16           | 12個月     | NotPass   |
            | 8M           | 16         | DL           | 7個月前            | 8E             | 16           | 12個月     | NotPass   |
            | 8M           | 16         | DL           | 6個月前            | 8E             | 16           | 12個月     | NotPass   |
            | 8M           | 16         | DL           | 5個月前            | 8E             | 16           | 12個月     | NotPass   |
            | 8M           | 16         | DL           | 4個月前            | 8E             | 16           | 12個月     | NotPass   |
            | 8M           | 16         | DL           | 3個月前            | 8E             | 16           | 12個月     | NotPass   |
            | 8M           | 16         | DL           | 2個月前            | 8E             | 16           | 12個月     | NotPass   |
            | 8M           | 16         | DL           | 1個月前            | 8E             | 16           | 12個月     | NotPass   |
            | 8M           | 16         | DL           | 當月              | 8E             | 16           | 12個月     | NotPass   |

    Scenario Outline: （HIS）6個月內，不應有 8I 診療項目
        Given 建立醫師
        Given Kelly 5 歲病人
        Given 在 12個月前 ，建立預約
        Given 在 12個月前 ，建立掛號
        Given 在 12個月前 ，產生診療計畫
        And 新增診療代碼:
            | PastDate | A72 | A73 | A74          | A75            | A76 | A77 | A78 | A79 |
            | 12個月前    | 3   | 8A  | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
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
        Then （HIS）檢查 <IssueNhiCode> 診療項目，在病患過去 <GapMonth> 紀錄中，不應包含特定的 <TreatmentNhiCode> 診療代碼，確認結果是否為 <PassOrNot> 且檢查訊息類型為 D1_2_2
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastTreatmentDate | TreatmentNhiCode | TreatmentTeeth | TreatmentSurface | GapMonth | PassOrNot |
            | 8M           | 16         | DL           | 6個月前              | 8I               | 16             | DL               | 6個月      | Pass      |
            | 8M           | 16         | DL           | 5個月前              | 8I               | 16             | DL               | 6個月      | NotPass   |
            | 8M           | 16         | DL           | 4個月前              | 8I               | 16             | DL               | 6個月      | NotPass   |
            | 8M           | 16         | DL           | 3個月前              | 8I               | 16             | DL               | 6個月      | NotPass   |
            | 8M           | 16         | DL           | 2個月前              | 8I               | 16             | DL               | 6個月      | NotPass   |
            | 8M           | 16         | DL           | 1個月前              | 8I               | 16             | DL               | 6個月      | NotPass   |
            | 8M           | 16         | DL           | 當月                | 8I               | 16             | DL               | 6個月      | NotPass   |

    Scenario Outline: （IC）6個月內，不應有 8I 診療項目
        Given 建立醫師
        Given Kelly 5 歲病人
        Given 在 12個月前 ，建立預約
        Given 在 12個月前 ，建立掛號
        Given 在 12個月前 ，產生診療計畫
        And 新增診療代碼:
            | PastDate | A72 | A73 | A74          | A75            | A76 | A77 | A78 | A79 |
            | 12個月前    | 3   | 8A  | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
        Given 新增健保醫療:
            | PastDate          | NhiCode          | Teeth          |
            | <PastMedicalDays> | <MedicalNhiCode> | <MedicalTeeth> |
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then （IC）檢查 <IssueNhiCode> 診療項目，在病患過去 <GapMonth> 紀錄中，不應包含特定的 <MedicalNhiCode> 診療代碼，確認結果是否為 <PassOrNot> 且檢查訊息類型為 D1_2_2
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastMedicalDays | MedicalNhiCode | MedicalTeeth | GapMonth | PassOrNot |
            | 8M           | 16         | DL           | 6個月前            | 8I             | 16           | 6個月      | Pass      |
            | 8M           | 16         | DL           | 5個月前            | 8I             | 16           | 6個月      | NotPass   |
            | 8M           | 16         | DL           | 4個月前            | 8I             | 16           | 6個月      | NotPass   |
            | 8M           | 16         | DL           | 3個月前            | 8I             | 16           | 6個月      | NotPass   |
            | 8M           | 16         | DL           | 2個月前            | 8I             | 16           | 6個月      | NotPass   |
            | 8M           | 16         | DL           | 1個月前            | 8I             | 16           | 6個月      | NotPass   |
            | 8M           | 16         | DL           | 當月              | 8I             | 16           | 6個月      | NotPass   |

    Scenario Outline: （HIS）8A/8E 曾經申報過
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
            | 6個月前     | 3   | 8I  | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then <TreatmentNhiCode> 診療項目曾經申報過，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | TreatmentNhiCode | TreatmentTeeth | TreatmentSurface | PassOrNot |
            | 8M           | 16         | FM           | 8A               | 16             | MO               | Pass      |
            | 8M           | 16         | FM           | 8E               | 16             | MO               | Pass      |
            | 8M           | 16         | FM           | 01271C           | 16             | MO               | NotPass   |

    Scenario Outline: （IC）8A/8E 曾經申報過
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
            | 6個月前     | 3   | 8I  | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then <MedicalNhiCode> 診療項目曾經申報過，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | MedicalNhiCode | MedicalTeeth | PassOrNot |
            | 8M           | 16         | FM           | 8A             | 16           | Pass      |
            | 8M           | 16         | FM           | 8E             | 16           | Pass      |
            | 8M           | 16         | FM           | 01271C         | 16           | NotPass   |

    Scenario Outline: （HIS）8I 曾經申報過
        Given 建立醫師
        Given Kelly 11 歲病人
        Given 在 12個月前 ，建立預約
        Given 在 12個月前 ，建立掛號
        Given 在 12個月前 ，產生診療計畫
        And 新增診療代碼:
            | PastDate | A72 | A73 | A74          | A75            | A76 | A77 | A78 | A79 |
            | 12個月前    | 3   | 8A  | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
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
        Then <TreatmentNhiCode> 診療項目曾經申報過，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | TreatmentNhiCode | TreatmentTeeth | TreatmentSurface | PassOrNot |
            | 8M           | 16         | FM           | 8I               | 16             | MO               | Pass      |
            | 8M           | 16         | FM           | 01271C           | 16             | MO               | NotPass   |

    Scenario Outline: （IC）8I 曾經申報過
        Given 建立醫師
        Given Kelly 11 歲病人
        Given 在 12個月前 ，建立預約
        Given 在 12個月前 ，建立掛號
        Given 在 12個月前 ，產生診療計畫
        And 新增診療代碼:
            | PastDate | A72 | A73 | A74          | A75            | A76 | A77 | A78 | A79 |
            | 12個月前    | 3   | 8A  | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
        Given 新增健保醫療:
            | PastDate | NhiCode          | Teeth          |
            | 6個月前     | <MedicalNhiCode> | <MedicalTeeth> |
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then <MedicalNhiCode> 診療項目曾經申報過，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | MedicalNhiCode | MedicalTeeth | PassOrNot |
            | 8M           | 16         | FM           | 8I             | 16           | Pass      |
            | 8M           | 16         | FM           | 01271C         | 16           | NotPass   |
