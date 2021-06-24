Feature: 8K 一、施作牙位：36二、服務項目1.恆牙第一大臼齒窩溝封填評估或脫落補施作2.一般口腔檢查、口腔保健衛教指導三、補助對象第一次評估檢查（同一牙位窩溝封填施作間隔6個月(含)以上）

    Scenario Outline: 全部檢核成功
        Given 建立醫師
        Given Kelly 11 歲病人
        Given 在 6個月前 ，建立預約
        Given 在 6個月前 ，建立掛號
        Given 在 6個月前 ，產生診療計畫
        And 新增診療代碼:
            | PastDate | A72 | A73 | A74          | A75            | A76 | A77 | A78 | A79 |
            | 6個月前     | 3   | 8C  | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 確認診療代碼 <IssueNhiCode> ，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            | 8K           | 36         | FM           | Pass      |

    Scenario Outline: 檢查治療的牙位是否為 ONLY_36
        Given 建立醫師
        Given Kelly 11 歲病人
        Given 在 6個月前 ，建立預約
        Given 在 6個月前 ，建立掛號
        Given 在 6個月前 ，產生診療計畫
        And 新增診療代碼:
            | PastDate | A72 | A73 | A74          | A75            | A76 | A77 | A78 | A79 |
            | 6個月前     | 3   | 8C  | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
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
            | 8K           | 51         | DL           | NotPass   |
            | 8K           | 52         | DL           | NotPass   |
            | 8K           | 53         | DL           | NotPass   |
            | 8K           | 54         | DL           | NotPass   |
            | 8K           | 55         | DL           | NotPass   |
            | 8K           | 61         | DL           | NotPass   |
            | 8K           | 62         | DL           | NotPass   |
            | 8K           | 63         | DL           | NotPass   |
            | 8K           | 64         | DL           | NotPass   |
            | 8K           | 65         | DL           | NotPass   |
            | 8K           | 71         | DL           | NotPass   |
            | 8K           | 72         | DL           | NotPass   |
            | 8K           | 73         | DL           | NotPass   |
            | 8K           | 74         | DL           | NotPass   |
            | 8K           | 75         | DL           | NotPass   |
            | 8K           | 81         | DL           | NotPass   |
            | 8K           | 82         | DL           | NotPass   |
            | 8K           | 83         | DL           | NotPass   |
            | 8K           | 84         | DL           | NotPass   |
            | 8K           | 85         | DL           | NotPass   |
            # 恆牙
            | 8K           | 11         | DL           | NotPass   |
            | 8K           | 12         | DL           | NotPass   |
            | 8K           | 13         | DL           | NotPass   |
            | 8K           | 14         | DL           | NotPass   |
            | 8K           | 15         | DL           | NotPass   |
            | 8K           | 16         | DL           | NotPass   |
            | 8K           | 17         | DL           | NotPass   |
            | 8K           | 18         | DL           | NotPass   |
            | 8K           | 21         | DL           | NotPass   |
            | 8K           | 22         | DL           | NotPass   |
            | 8K           | 23         | DL           | NotPass   |
            | 8K           | 24         | DL           | NotPass   |
            | 8K           | 25         | DL           | NotPass   |
            | 8K           | 26         | DL           | NotPass   |
            | 8K           | 27         | DL           | NotPass   |
            | 8K           | 28         | DL           | NotPass   |
            | 8K           | 31         | DL           | NotPass   |
            | 8K           | 32         | DL           | NotPass   |
            | 8K           | 33         | DL           | NotPass   |
            | 8K           | 34         | DL           | NotPass   |
            | 8K           | 35         | DL           | NotPass   |
            | 8K           | 36         | DL           | Pass      |
            | 8K           | 37         | DL           | NotPass   |
            | 8K           | 38         | DL           | NotPass   |
            | 8K           | 41         | DL           | NotPass   |
            | 8K           | 42         | DL           | NotPass   |
            | 8K           | 43         | DL           | NotPass   |
            | 8K           | 44         | DL           | NotPass   |
            | 8K           | 45         | DL           | NotPass   |
            | 8K           | 46         | DL           | NotPass   |
            | 8K           | 47         | DL           | NotPass   |
            | 8K           | 48         | DL           | NotPass   |
            # 無牙
            | 8K           |            | DL           | NotPass   |
            #
            | 8K           | 19         | DL           | NotPass   |
            | 8K           | 29         | DL           | NotPass   |
            | 8K           | 39         | DL           | NotPass   |
            | 8K           | 49         | DL           | NotPass   |
            | 8K           | 59         | DL           | NotPass   |
            | 8K           | 69         | DL           | NotPass   |
            | 8K           | 79         | DL           | NotPass   |
            | 8K           | 89         | DL           | NotPass   |
            | 8K           | 99         | DL           | NotPass   |
            # 牙位為區域型態
            | 8K           | FM         | DL           | NotPass   |
            | 8K           | UR         | DL           | NotPass   |
            | 8K           | UL         | DL           | NotPass   |
            | 8K           | LR         | DL           | NotPass   |
            | 8K           | LL         | DL           | NotPass   |
            | 8K           | UB         | DL           | NotPass   |
            | 8K           | LB         | DL           | NotPass   |
            # 非法牙位
            | 8K           | 00         | DL           | NotPass   |
            | 8K           | 01         | DL           | NotPass   |
            | 8K           | 10         | DL           | NotPass   |
            | 8K           | 56         | DL           | NotPass   |
            | 8K           | 66         | DL           | NotPass   |
            | 8K           | 76         | DL           | NotPass   |
            | 8K           | 86         | DL           | NotPass   |
            | 8K           | 91         | DL           | NotPass   |

    Scenario Outline: （Disposal）8K 終生只能申報一次
        Given 建立醫師
        Given Kelly 11 歲病人
        Given 在 6個月前 ，建立預約
        Given 在 6個月前 ，建立掛號
        Given 在 6個月前 ，產生診療計畫
        And 新增診療代碼:
            | PastDate | A72 | A73 | A74          | A75            | A76 | A77 | A78 | A79 |
            | 6個月前     | 3   | 8C  | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
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
            | 8K           | 36         | MOB          | 8K               | 36             | MOB              | NotPass   |
            | 8K           | 36         | MOB          | 01271C           | 36             | MOB              | Pass      |

    Scenario Outline: （HIS-Today）8K 終生只能申報一次
        Given 建立醫師
        Given Kelly 11 歲病人
        Given 在 6個月前 ，建立預約
        Given 在 6個月前 ，建立掛號
        Given 在 6個月前 ，產生診療計畫
        And 新增診療代碼:
            | PastDate | A72 | A73 | A74          | A75            | A76 | A77 | A78 | A79 |
            | 6個月前     | 3   | 8C  | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
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
            | 8K           | 36         | FM           | 8K               | 36             | MO               | NotPass   |
            | 8K           | 36         | FM           | 01271C           | 36             | MO               | Pass      |

    Scenario Outline: （IC-Today）8K 終生只能申報一次
        Given 建立醫師
        Given Kelly 11 歲病人
        Given 在 6個月前 ，建立預約
        Given 在 6個月前 ，建立掛號
        Given 在 6個月前 ，產生診療計畫
        And 新增診療代碼:
            | PastDate | A72 | A73 | A74          | A75            | A76 | A77 | A78 | A79 |
            | 6個月前     | 3   | 8C  | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
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
            | 8K           | 36         | FM           | 8K             | 36           | NotPass   |
            | 8K           | 36         | FM           | 01271C         | 36           | Pass      |

    Scenario Outline: （HIS）8K 終生只能申報一次
        Given 建立醫師
        Given Kelly 11 歲病人
        Given 在 6個月前 ，建立預約
        Given 在 6個月前 ，建立掛號
        Given 在 6個月前 ，產生診療計畫
        And 新增診療代碼:
            | PastDate | A72 | A73 | A74          | A75            | A76 | A77 | A78 | A79 |
            | 6個月前     | 3   | 8C  | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
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
            | 8K           | 36         | FM           | 8K               | 36             | MO               | NotPass   |
            | 8K           | 36         | FM           | 01271C           | 36             | MO               | Pass      |

    Scenario Outline: （IC）8K 終生只能申報一次
        Given 建立醫師
        Given Kelly 11 歲病人
        Given 在 6個月前 ，建立預約
        Given 在 6個月前 ，建立掛號
        Given 在 6個月前 ，產生診療計畫
        And 新增診療代碼:
            | PastDate | A72 | A73 | A74          | A75            | A76 | A77 | A78 | A79 |
            | 6個月前     | 3   | 8C  | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
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
            | 8K           | 36         | FM           | 8K             | 36           | NotPass   |
            | 8K           | 36         | FM           | 01271C         | 36           | Pass      |

    Scenario Outline: （HIS）6個月內，不應有 8C/8G 診療項目
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
        Then （HIS）檢查 <IssueNhiCode> 診療項目，在病患過去 <GapMonth> 紀錄中，不應包含特定的 <TreatmentNhiCode> 診療代碼，確認結果是否為 <PassOrNot> 且檢查訊息類型為 D1_2_2
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastTreatmentDate | TreatmentNhiCode | TreatmentTeeth | TreatmentSurface | GapMonth | PassOrNot |
            | 8K           | 36         | DL           | 6個月前              | 8C               | 36             | DL               | 6個月      | Pass      |
            | 8K           | 36         | DL           | 5個月前              | 8C               | 36             | DL               | 6個月      | NotPass   |
            | 8K           | 36         | DL           | 4個月前              | 8C               | 36             | DL               | 6個月      | NotPass   |
            | 8K           | 36         | DL           | 3個月前              | 8C               | 36             | DL               | 6個月      | NotPass   |
            | 8K           | 36         | DL           | 2個月前              | 8C               | 36             | DL               | 6個月      | NotPass   |
            | 8K           | 36         | DL           | 1個月前              | 8C               | 36             | DL               | 6個月      | NotPass   |
            | 8K           | 36         | DL           | 當月                | 8C               | 36             | DL               | 6個月      | NotPass   |
            | 8K           | 36         | DL           | 6個月前              | 8G               | 36             | DL               | 6個月      | Pass      |
            | 8K           | 36         | DL           | 5個月前              | 8G               | 36             | DL               | 6個月      | NotPass   |
            | 8K           | 36         | DL           | 4個月前              | 8G               | 36             | DL               | 6個月      | NotPass   |
            | 8K           | 36         | DL           | 3個月前              | 8G               | 36             | DL               | 6個月      | NotPass   |
            | 8K           | 36         | DL           | 2個月前              | 8G               | 36             | DL               | 6個月      | NotPass   |
            | 8K           | 36         | DL           | 1個月前              | 8G               | 36             | DL               | 6個月      | NotPass   |
            | 8K           | 36         | DL           | 當月                | 8G               | 36             | DL               | 6個月      | NotPass   |

    Scenario Outline: （IC）6個月內，不應有 8C/8G 診療項目
        Given 建立醫師
        Given Kelly 5 歲病人
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
            | 8K           | 36         | DL           | 6個月前            | 8C             | 36           | 6個月      | Pass      |
            | 8K           | 36         | DL           | 5個月前            | 8C             | 36           | 6個月      | NotPass   |
            | 8K           | 36         | DL           | 4個月前            | 8C             | 36           | 6個月      | NotPass   |
            | 8K           | 36         | DL           | 3個月前            | 8C             | 36           | 6個月      | NotPass   |
            | 8K           | 36         | DL           | 2個月前            | 8C             | 36           | 6個月      | NotPass   |
            | 8K           | 36         | DL           | 1個月前            | 8C             | 36           | 6個月      | NotPass   |
            | 8K           | 36         | DL           | 當月              | 8C             | 36           | 6個月      | NotPass   |
            | 8K           | 36         | DL           | 6個月前            | 8G             | 36           | 6個月      | Pass      |
            | 8K           | 36         | DL           | 5個月前            | 8G             | 36           | 6個月      | NotPass   |
            | 8K           | 36         | DL           | 4個月前            | 8G             | 36           | 6個月      | NotPass   |
            | 8K           | 36         | DL           | 3個月前            | 8G             | 36           | 6個月      | NotPass   |
            | 8K           | 36         | DL           | 2個月前            | 8G             | 36           | 6個月      | NotPass   |
            | 8K           | 36         | DL           | 1個月前            | 8G             | 36           | 6個月      | NotPass   |
            | 8K           | 36         | DL           | 當月              | 8G             | 36           | 6個月      | NotPass   |

    Scenario Outline: （HIS）8C/8G 曾經申報過
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
            | 8K           | 36         | FM           | 8C               | 36             | MO               | Pass      |
            | 8K           | 36         | FM           | 8G               | 36             | MO               | Pass      |
            | 8K           | 36         | FM           | 01271C           | 36             | MO               | NotPass   |

    Scenario Outline: （IC）8C/8G 曾經申報過
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
            | 8K           | 36         | FM           | 8C             | 36           | Pass      |
            | 8K           | 36         | FM           | 8G             | 36           | Pass      |
            | 8K           | 36         | FM           | 01271C         | 36           | NotPass   |
