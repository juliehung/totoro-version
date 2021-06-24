Feature: 8I 一、施作牙位：16二、服務項目1.恆牙第一大臼齒窩溝封填評估或脫落補施作2.一般口腔檢查、口腔保健衛教指導三、補助對象第一次評估檢查（同一牙位窩溝封填施作間隔6個月(含)以上）

    Scenario Outline: 全部檢核成功
        Given 建立醫師
        Given Kelly 11 歲病人
        Given 在 6個月前 ，建立預約
        Given 在 6個月前 ，建立掛號
        Given 在 6個月前 ，產生診療計畫
        And 新增診療代碼:
            | PastDate | A72 | A73 | A74          | A75            | A76 | A77 | A78 | A79 |
            | 6個月前     | 3   | 8A  | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 確認診療代碼 <IssueNhiCode> ，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            | 8I           | 16         | FM           | Pass      |

    Scenario Outline: 檢查治療的牙位是否為 ONLY_16
        Given 建立醫師
        Given Kelly 11 歲病人
        Given 在 6個月前 ，建立預約
        Given 在 6個月前 ，建立掛號
        Given 在 6個月前 ，產生診療計畫
        And 新增診療代碼:
            | PastDate | A72 | A73 | A74          | A75            | A76 | A77 | A78 | A79 |
            | 6個月前     | 3   | 8A  | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
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
            | 8I           | 51         | DL           | NotPass   |
            | 8I           | 52         | DL           | NotPass   |
            | 8I           | 53         | DL           | NotPass   |
            | 8I           | 54         | DL           | NotPass   |
            | 8I           | 55         | DL           | NotPass   |
            | 8I           | 61         | DL           | NotPass   |
            | 8I           | 62         | DL           | NotPass   |
            | 8I           | 63         | DL           | NotPass   |
            | 8I           | 64         | DL           | NotPass   |
            | 8I           | 65         | DL           | NotPass   |
            | 8I           | 71         | DL           | NotPass   |
            | 8I           | 72         | DL           | NotPass   |
            | 8I           | 73         | DL           | NotPass   |
            | 8I           | 74         | DL           | NotPass   |
            | 8I           | 75         | DL           | NotPass   |
            | 8I           | 81         | DL           | NotPass   |
            | 8I           | 82         | DL           | NotPass   |
            | 8I           | 83         | DL           | NotPass   |
            | 8I           | 84         | DL           | NotPass   |
            | 8I           | 85         | DL           | NotPass   |
            # 恆牙
            | 8I           | 11         | DL           | NotPass   |
            | 8I           | 12         | DL           | NotPass   |
            | 8I           | 13         | DL           | NotPass   |
            | 8I           | 14         | DL           | NotPass   |
            | 8I           | 15         | DL           | NotPass   |
            | 8I           | 16         | DL           | Pass      |
            | 8I           | 17         | DL           | NotPass   |
            | 8I           | 18         | DL           | NotPass   |
            | 8I           | 21         | DL           | NotPass   |
            | 8I           | 22         | DL           | NotPass   |
            | 8I           | 23         | DL           | NotPass   |
            | 8I           | 24         | DL           | NotPass   |
            | 8I           | 25         | DL           | NotPass   |
            | 8I           | 26         | DL           | NotPass   |
            | 8I           | 27         | DL           | NotPass   |
            | 8I           | 28         | DL           | NotPass   |
            | 8I           | 31         | DL           | NotPass   |
            | 8I           | 32         | DL           | NotPass   |
            | 8I           | 33         | DL           | NotPass   |
            | 8I           | 34         | DL           | NotPass   |
            | 8I           | 35         | DL           | NotPass   |
            | 8I           | 36         | DL           | NotPass   |
            | 8I           | 37         | DL           | NotPass   |
            | 8I           | 38         | DL           | NotPass   |
            | 8I           | 41         | DL           | NotPass   |
            | 8I           | 42         | DL           | NotPass   |
            | 8I           | 43         | DL           | NotPass   |
            | 8I           | 44         | DL           | NotPass   |
            | 8I           | 45         | DL           | NotPass   |
            | 8I           | 46         | DL           | NotPass   |
            | 8I           | 47         | DL           | NotPass   |
            | 8I           | 48         | DL           | NotPass   |
            # 無牙
            | 8I           |            | DL           | NotPass   |
            #
            | 8I           | 19         | DL           | NotPass   |
            | 8I           | 29         | DL           | NotPass   |
            | 8I           | 39         | DL           | NotPass   |
            | 8I           | 49         | DL           | NotPass   |
            | 8I           | 59         | DL           | NotPass   |
            | 8I           | 69         | DL           | NotPass   |
            | 8I           | 79         | DL           | NotPass   |
            | 8I           | 89         | DL           | NotPass   |
            | 8I           | 99         | DL           | NotPass   |
            # 牙位為區域型態
            | 8I           | FM         | DL           | NotPass   |
            | 8I           | UR         | DL           | NotPass   |
            | 8I           | UL         | DL           | NotPass   |
            | 8I           | LR         | DL           | NotPass   |
            | 8I           | LL         | DL           | NotPass   |
            | 8I           | UB         | DL           | NotPass   |
            | 8I           | LB         | DL           | NotPass   |
            # 非法牙位
            | 8I           | 00         | DL           | NotPass   |
            | 8I           | 01         | DL           | NotPass   |
            | 8I           | 10         | DL           | NotPass   |
            | 8I           | 56         | DL           | NotPass   |
            | 8I           | 66         | DL           | NotPass   |
            | 8I           | 76         | DL           | NotPass   |
            | 8I           | 86         | DL           | NotPass   |
            | 8I           | 91         | DL           | NotPass   |

    Scenario Outline: （Disposal）8I 終生只能申報一次
        Given 建立醫師
        Given Kelly 11 歲病人
        Given 在 6個月前 ，建立預約
        Given 在 6個月前 ，建立掛號
        Given 在 6個月前 ，產生診療計畫
        And 新增診療代碼:
            | PastDate | A72 | A73 | A74          | A75            | A76 | A77 | A78 | A79 |
            | 6個月前     | 3   | 8A  | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
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
            | 8I           | 16         | MOB          | 8I               | 16             | MOB              | NotPass   |
            | 8I           | 16         | MOB          | 01271C           | 16             | MOB              | Pass      |

    Scenario Outline: （HIS-Today）8I 終生只能申報一次
        Given 建立醫師
        Given Kelly 11 歲病人
        Given 在 6個月前 ，建立預約
        Given 在 6個月前 ，建立掛號
        Given 在 6個月前 ，產生診療計畫
        And 新增診療代碼:
            | PastDate | A72 | A73 | A74          | A75            | A76 | A77 | A78 | A79 |
            | 6個月前     | 3   | 8A  | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
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
            | 8I           | 16         | FM           | 8I               | 16             | MO               | NotPass   |
            | 8I           | 16         | FM           | 01271C           | 16             | MO               | Pass      |

    Scenario Outline: （IC-Today）8I 終生只能申報一次
        Given 建立醫師
        Given Kelly 11 歲病人
        Given 在 6個月前 ，建立預約
        Given 在 6個月前 ，建立掛號
        Given 在 6個月前 ，產生診療計畫
        And 新增診療代碼:
            | PastDate | A72 | A73 | A74          | A75            | A76 | A77 | A78 | A79 |
            | 6個月前     | 3   | 8A  | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
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
            | 8I           | 16         | FM           | 8I             | 16           | NotPass   |
            | 8I           | 16         | FM           | 01271C         | 16           | Pass      |

    Scenario Outline: （HIS）8I 終生只能申報一次
        Given 建立醫師
        Given Kelly 11 歲病人
        Given 在 6個月前 ，建立預約
        Given 在 6個月前 ，建立掛號
        Given 在 6個月前 ，產生診療計畫
        And 新增診療代碼:
            | PastDate | A72 | A73 | A74          | A75            | A76 | A77 | A78 | A79 |
            | 6個月前     | 3   | 8A  | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
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
            | 8I           | 16         | FM           | 8I               | 16             | MO               | NotPass   |
            | 8I           | 16         | FM           | 01271C           | 16             | MO               | Pass      |

    Scenario Outline: （IC）8I 終生只能申報一次
        Given 建立醫師
        Given Kelly 11 歲病人
        Given 在 6個月前 ，建立預約
        Given 在 6個月前 ，建立掛號
        Given 在 6個月前 ，產生診療計畫
        And 新增診療代碼:
            | PastDate | A72 | A73 | A74          | A75            | A76 | A77 | A78 | A79 |
            | 6個月前     | 3   | 8A  | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
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
            | 8I           | 16         | FM           | 8I             | 16           | NotPass   |
            | 8I           | 16         | FM           | 01271C         | 16           | Pass      |

    Scenario Outline: （HIS）6個月內，不應有 8A/8E 診療項目
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
            | 8I           | 16         | DL           | 6個月前              | 8A               | 16             | DL               | 6個月      | Pass      |
            | 8I           | 16         | DL           | 5個月前              | 8A               | 16             | DL               | 6個月      | NotPass   |
            | 8I           | 16         | DL           | 4個月前              | 8A               | 16             | DL               | 6個月      | NotPass   |
            | 8I           | 16         | DL           | 3個月前              | 8A               | 16             | DL               | 6個月      | NotPass   |
            | 8I           | 16         | DL           | 2個月前              | 8A               | 16             | DL               | 6個月      | NotPass   |
            | 8I           | 16         | DL           | 1個月前              | 8A               | 16             | DL               | 6個月      | NotPass   |
            | 8I           | 16         | DL           | 當月                | 8A               | 16             | DL               | 6個月      | NotPass   |
            | 8I           | 16         | DL           | 6個月前              | 8E               | 16             | DL               | 6個月      | Pass      |
            | 8I           | 16         | DL           | 5個月前              | 8E               | 16             | DL               | 6個月      | NotPass   |
            | 8I           | 16         | DL           | 4個月前              | 8E               | 16             | DL               | 6個月      | NotPass   |
            | 8I           | 16         | DL           | 3個月前              | 8E               | 16             | DL               | 6個月      | NotPass   |
            | 8I           | 16         | DL           | 2個月前              | 8E               | 16             | DL               | 6個月      | NotPass   |
            | 8I           | 16         | DL           | 1個月前              | 8E               | 16             | DL               | 6個月      | NotPass   |
            | 8I           | 16         | DL           | 當月                | 8E               | 16             | DL               | 6個月      | NotPass   |

    Scenario Outline: （IC）6個月內，不應有 8A/8E 診療項目
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
            | 8I           | 16         | DL           | 6個月前            | 8A             | 16           | 6個月      | Pass      |
            | 8I           | 16         | DL           | 5個月前            | 8A             | 16           | 6個月      | NotPass   |
            | 8I           | 16         | DL           | 4個月前            | 8A             | 16           | 6個月      | NotPass   |
            | 8I           | 16         | DL           | 3個月前            | 8A             | 16           | 6個月      | NotPass   |
            | 8I           | 16         | DL           | 2個月前            | 8A             | 16           | 6個月      | NotPass   |
            | 8I           | 16         | DL           | 1個月前            | 8A             | 16           | 6個月      | NotPass   |
            | 8I           | 16         | DL           | 當月              | 8A             | 16           | 6個月      | NotPass   |
            | 8I           | 16         | DL           | 6個月前            | 8E             | 16           | 6個月      | Pass      |
            | 8I           | 16         | DL           | 5個月前            | 8E             | 16           | 6個月      | NotPass   |
            | 8I           | 16         | DL           | 4個月前            | 8E             | 16           | 6個月      | NotPass   |
            | 8I           | 16         | DL           | 3個月前            | 8E             | 16           | 6個月      | NotPass   |
            | 8I           | 16         | DL           | 2個月前            | 8E             | 16           | 6個月      | NotPass   |
            | 8I           | 16         | DL           | 1個月前            | 8E             | 16           | 6個月      | NotPass   |
            | 8I           | 16         | DL           | 當月              | 8E             | 16           | 6個月      | NotPass   |

    Scenario Outline: （HIS）8A/8E 曾經申報過
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
            | 8I           | 16         | FM           | 8A               | 16             | MO               | Pass      |
            | 8I           | 16         | FM           | 8E               | 16             | MO               | Pass      |
            | 8I           | 16         | FM           | 01271C           | 16             | MO               | NotPass   |

    Scenario Outline: （IC）8A/8E 曾經申報過
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
            | 8I           | 16         | FM           | 8A             | 16           | Pass      |
            | 8I           | 16         | FM           | 8E             | 16           | Pass      |
            | 8I           | 16         | FM           | 01271C         | 16           | NotPass   |
