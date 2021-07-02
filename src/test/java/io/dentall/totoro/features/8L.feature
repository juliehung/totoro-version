Feature: 8L 一、施作牙位：46二、服務項目1.恆牙第一大臼齒窩溝封填評估或脫落補施作2.一般口腔檢查、口腔保健衛教指導三、補助對象第一次評估檢查（同一牙位窩溝封填施作間隔6個月(含)以上）

    Scenario Outline: 全部檢核成功
        Given 建立醫師
        Given Kelly 11 歲病人
        Given 在 6個月前 ，建立預約
        Given 在 6個月前 ，建立掛號
        Given 在 6個月前 ，產生診療計畫
        And 新增診療代碼:
            | PastDate | A72 | A73 | A74          | A75            | A76 | A77 | A78 | A79 |
            | 6個月前     | 3   | 8D  | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 確認診療代碼 <IssueNhiCode> ，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            | 8L           | 46         | FM           | Pass      |

    Scenario Outline: 檢查治療的牙位是否為 ONLY_46
        Given 建立醫師
        Given Kelly 11 歲病人
        Given 在 6個月前 ，建立預約
        Given 在 6個月前 ，建立掛號
        Given 在 6個月前 ，產生診療計畫
        And 新增診療代碼:
            | PastDate | A72 | A73 | A74          | A75            | A76 | A77 | A78 | A79 |
            | 6個月前     | 3   | 8D  | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
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
            | 8L           | 51         | DL           | NotPass   |
            | 8L           | 52         | DL           | NotPass   |
            | 8L           | 53         | DL           | NotPass   |
            | 8L           | 54         | DL           | NotPass   |
            | 8L           | 55         | DL           | NotPass   |
            | 8L           | 61         | DL           | NotPass   |
            | 8L           | 62         | DL           | NotPass   |
            | 8L           | 63         | DL           | NotPass   |
            | 8L           | 64         | DL           | NotPass   |
            | 8L           | 65         | DL           | NotPass   |
            | 8L           | 71         | DL           | NotPass   |
            | 8L           | 72         | DL           | NotPass   |
            | 8L           | 73         | DL           | NotPass   |
            | 8L           | 74         | DL           | NotPass   |
            | 8L           | 75         | DL           | NotPass   |
            | 8L           | 81         | DL           | NotPass   |
            | 8L           | 82         | DL           | NotPass   |
            | 8L           | 83         | DL           | NotPass   |
            | 8L           | 84         | DL           | NotPass   |
            | 8L           | 85         | DL           | NotPass   |
            # 恆牙
            | 8L           | 11         | DL           | NotPass   |
            | 8L           | 12         | DL           | NotPass   |
            | 8L           | 13         | DL           | NotPass   |
            | 8L           | 14         | DL           | NotPass   |
            | 8L           | 15         | DL           | NotPass   |
            | 8L           | 16         | DL           | NotPass   |
            | 8L           | 17         | DL           | NotPass   |
            | 8L           | 18         | DL           | NotPass   |
            | 8L           | 21         | DL           | NotPass   |
            | 8L           | 22         | DL           | NotPass   |
            | 8L           | 23         | DL           | NotPass   |
            | 8L           | 24         | DL           | NotPass   |
            | 8L           | 25         | DL           | NotPass   |
            | 8L           | 26         | DL           | NotPass   |
            | 8L           | 27         | DL           | NotPass   |
            | 8L           | 28         | DL           | NotPass   |
            | 8L           | 31         | DL           | NotPass   |
            | 8L           | 32         | DL           | NotPass   |
            | 8L           | 33         | DL           | NotPass   |
            | 8L           | 34         | DL           | NotPass   |
            | 8L           | 35         | DL           | NotPass   |
            | 8L           | 36         | DL           | NotPass   |
            | 8L           | 37         | DL           | NotPass   |
            | 8L           | 38         | DL           | NotPass   |
            | 8L           | 41         | DL           | NotPass   |
            | 8L           | 42         | DL           | NotPass   |
            | 8L           | 43         | DL           | NotPass   |
            | 8L           | 44         | DL           | NotPass   |
            | 8L           | 45         | DL           | NotPass   |
            | 8L           | 46         | DL           | Pass      |
            | 8L           | 47         | DL           | NotPass   |
            | 8L           | 48         | DL           | NotPass   |
            # 無牙
            | 8L           |            | DL           | NotPass   |
            #
            | 8L           | 19         | DL           | NotPass   |
            | 8L           | 29         | DL           | NotPass   |
            | 8L           | 39         | DL           | NotPass   |
            | 8L           | 49         | DL           | NotPass   |
            | 8L           | 59         | DL           | NotPass   |
            | 8L           | 69         | DL           | NotPass   |
            | 8L           | 79         | DL           | NotPass   |
            | 8L           | 89         | DL           | NotPass   |
            | 8L           | 99         | DL           | NotPass   |
            # 牙位為區域型態
            | 8L           | FM         | DL           | NotPass   |
            | 8L           | UR         | DL           | NotPass   |
            | 8L           | UL         | DL           | NotPass   |
            | 8L           | UA         | DL           | NotPass   |
            | 8L           | UB         | DL           | NotPass   |
            | 8L           | LR         | DL           | NotPass   |
            | 8L           | LL         | DL           | NotPass   |
            | 8L           | LA         | DL           | NotPass   |
            | 8L           | LB         | DL           | NotPass   |
            # 非法牙位
            | 8L           | 00         | DL           | NotPass   |
            | 8L           | 01         | DL           | NotPass   |
            | 8L           | 10         | DL           | NotPass   |
            | 8L           | 56         | DL           | NotPass   |
            | 8L           | 66         | DL           | NotPass   |
            | 8L           | 76         | DL           | NotPass   |
            | 8L           | 86         | DL           | NotPass   |
            | 8L           | 91         | DL           | NotPass   |

    Scenario Outline: （Disposal）8L 終生只能申報一次
        Given 建立醫師
        Given Kelly 11 歲病人
        Given 在 6個月前 ，建立預約
        Given 在 6個月前 ，建立掛號
        Given 在 6個月前 ，產生診療計畫
        And 新增診療代碼:
            | PastDate | A72 | A73 | A74          | A75            | A76 | A77 | A78 | A79 |
            | 6個月前     | 3   | 8D  | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
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
            | 8L           | 46         | MOB          | 8L               | 46             | MOB              | NotPass   |
            | 8L           | 46         | MOB          | 01271C           | 46             | MOB              | Pass      |

    Scenario Outline: （HIS-Today）8L 終生只能申報一次
        Given 建立醫師
        Given Kelly 11 歲病人
        Given 在 6個月前 ，建立預約
        Given 在 6個月前 ，建立掛號
        Given 在 6個月前 ，產生診療計畫
        And 新增診療代碼:
            | PastDate | A72 | A73 | A74          | A75            | A76 | A77 | A78 | A79 |
            | 6個月前     | 3   | 8D  | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
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
            | 8L           | 46         | FM           | 8L               | 46             | MO               | NotPass   |
            | 8L           | 46         | FM           | 01271C           | 46             | MO               | Pass      |

    Scenario Outline: （IC-Today）8L 終生只能申報一次
        Given 建立醫師
        Given Kelly 11 歲病人
        Given 在 6個月前 ，建立預約
        Given 在 6個月前 ，建立掛號
        Given 在 6個月前 ，產生診療計畫
        And 新增診療代碼:
            | PastDate | A72 | A73 | A74          | A75            | A76 | A77 | A78 | A79 |
            | 6個月前     | 3   | 8D  | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
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
            | 8L           | 46         | FM           | 8L             | 46           | NotPass   |
            | 8L           | 46         | FM           | 01271C         | 46           | Pass      |

    Scenario Outline: （HIS）8L 終生只能申報一次
        Given 建立醫師
        Given Kelly 11 歲病人
        Given 在 6個月前 ，建立預約
        Given 在 6個月前 ，建立掛號
        Given 在 6個月前 ，產生診療計畫
        And 新增診療代碼:
            | PastDate | A72 | A73 | A74          | A75            | A76 | A77 | A78 | A79 |
            | 6個月前     | 3   | 8D  | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
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
            | 8L           | 46         | FM           | 8L               | 46             | MO               | NotPass   |
            | 8L           | 46         | FM           | 01271C           | 46             | MO               | Pass      |

    Scenario Outline: （IC）8L 終生只能申報一次
        Given 建立醫師
        Given Kelly 11 歲病人
        Given 在 6個月前 ，建立預約
        Given 在 6個月前 ，建立掛號
        Given 在 6個月前 ，產生診療計畫
        And 新增診療代碼:
            | PastDate | A72 | A73 | A74          | A75            | A76 | A77 | A78 | A79 |
            | 6個月前     | 3   | 8D  | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
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
            | 8L           | 46         | FM           | 8L             | 46           | NotPass   |
            | 8L           | 46         | FM           | 01271C         | 46           | Pass      |

    Scenario Outline: （HIS）6個月內，不應有 8D/8H 診療項目
        Given 建立醫師
        Given Kelly 5 歲病人
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
            | 8L           | 46         | DL           | 6個月前              | 8D               | 46             | DL               | 6個月      | Pass      |
            | 8L           | 46         | DL           | 5個月前              | 8D               | 46             | DL               | 6個月      | NotPass   |
            | 8L           | 46         | DL           | 4個月前              | 8D               | 46             | DL               | 6個月      | NotPass   |
            | 8L           | 46         | DL           | 3個月前              | 8D               | 46             | DL               | 6個月      | NotPass   |
            | 8L           | 46         | DL           | 2個月前              | 8D               | 46             | DL               | 6個月      | NotPass   |
            | 8L           | 46         | DL           | 1個月前              | 8D               | 46             | DL               | 6個月      | NotPass   |
            | 8L           | 46         | DL           | 當月                | 8D               | 46             | DL               | 6個月      | NotPass   |
            | 8L           | 46         | DL           | 6個月前              | 8H               | 46             | DL               | 6個月      | Pass      |
            | 8L           | 46         | DL           | 5個月前              | 8H               | 46             | DL               | 6個月      | NotPass   |
            | 8L           | 46         | DL           | 4個月前              | 8H               | 46             | DL               | 6個月      | NotPass   |
            | 8L           | 46         | DL           | 3個月前              | 8H               | 46             | DL               | 6個月      | NotPass   |
            | 8L           | 46         | DL           | 2個月前              | 8H               | 46             | DL               | 6個月      | NotPass   |
            | 8L           | 46         | DL           | 1個月前              | 8H               | 46             | DL               | 6個月      | NotPass   |
            | 8L           | 46         | DL           | 當月                | 8H               | 46             | DL               | 6個月      | NotPass   |

    Scenario Outline: （IC）6個月內，不應有 8D/8H 診療項目
        Given 建立醫師
        Given Kelly 5 歲病人
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
            | 8L           | 46         | DL           | 6個月前            | 8D             | 46           | 6個月      | Pass      |
            | 8L           | 46         | DL           | 5個月前            | 8D             | 46           | 6個月      | NotPass   |
            | 8L           | 46         | DL           | 4個月前            | 8D             | 46           | 6個月      | NotPass   |
            | 8L           | 46         | DL           | 3個月前            | 8D             | 46           | 6個月      | NotPass   |
            | 8L           | 46         | DL           | 2個月前            | 8D             | 46           | 6個月      | NotPass   |
            | 8L           | 46         | DL           | 1個月前            | 8D             | 46           | 6個月      | NotPass   |
            | 8L           | 46         | DL           | 當月              | 8D             | 46           | 6個月      | NotPass   |
            | 8L           | 46         | DL           | 6個月前            | 8H             | 46           | 6個月      | Pass      |
            | 8L           | 46         | DL           | 5個月前            | 8H             | 46           | 6個月      | NotPass   |
            | 8L           | 46         | DL           | 4個月前            | 8H             | 46           | 6個月      | NotPass   |
            | 8L           | 46         | DL           | 3個月前            | 8H             | 46           | 6個月      | NotPass   |
            | 8L           | 46         | DL           | 2個月前            | 8H             | 46           | 6個月      | NotPass   |
            | 8L           | 46         | DL           | 1個月前            | 8H             | 46           | 6個月      | NotPass   |
            | 8L           | 46         | DL           | 當月              | 8H             | 46           | 6個月      | NotPass   |

    Scenario Outline: （HIS）8D/8H 曾經申報過
        Given 建立醫師
        Given Kelly 11 歲病人
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
            | 8L           | 46         | FM           | 8D               | 46             | MO               | Pass      |
            | 8L           | 46         | FM           | 8H               | 46             | MO               | Pass      |
            | 8L           | 46         | FM           | 01271C           | 46             | MO               | NotPass   |

    Scenario Outline: （IC）8D/8H 曾經申報過
        Given 建立醫師
        Given Kelly 11 歲病人
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
            | 8L           | 46         | FM           | 8D             | 46           | Pass      |
            | 8L           | 46         | FM           | 8H             | 46           | Pass      |
            | 8L           | 46         | FM           | 01271C         | 46           | NotPass   |
