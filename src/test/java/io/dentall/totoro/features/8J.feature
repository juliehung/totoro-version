Feature: 8J 一、施作牙位：26二、服務項目1.恆牙第一大臼齒窩溝封填評估或脫落補施作2.一般口腔檢查、口腔保健衛教指導三、補助對象第一次評估檢查（同一牙位窩溝封填施作間隔6個月(含)以上）

    Scenario Outline: 全部檢核成功
        Given 建立醫師
        Given Kelly 11 歲病人
        Given 在 6個月前 ，建立預約
        Given 在 6個月前 ，建立掛號
        Given 在 6個月前 ，產生診療計畫
        And 新增診療代碼:
            | PastDate | A72 | A73 | A74          | A75            | A76 | A77 | A78 | A79 |
            | 6個月前     | 3   | 8B  | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 確認診療代碼 <IssueNhiCode> ，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            | 8J           | 26         | FM           | Pass      |

    Scenario Outline: 檢查治療的牙位是否為 ONLY_26
        Given 建立醫師
        Given Kelly 11 歲病人
        Given 在 6個月前 ，建立預約
        Given 在 6個月前 ，建立掛號
        Given 在 6個月前 ，產生診療計畫
        And 新增診療代碼:
            | PastDate | A72 | A73 | A74          | A75            | A76 | A77 | A78 | A79 |
            | 6個月前     | 3   | 8B  | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
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
            | 8J           | 51         | DL           | NotPass   |
            | 8J           | 52         | DL           | NotPass   |
            | 8J           | 53         | DL           | NotPass   |
            | 8J           | 54         | DL           | NotPass   |
            | 8J           | 55         | DL           | NotPass   |
            | 8J           | 61         | DL           | NotPass   |
            | 8J           | 62         | DL           | NotPass   |
            | 8J           | 63         | DL           | NotPass   |
            | 8J           | 64         | DL           | NotPass   |
            | 8J           | 65         | DL           | NotPass   |
            | 8J           | 71         | DL           | NotPass   |
            | 8J           | 72         | DL           | NotPass   |
            | 8J           | 73         | DL           | NotPass   |
            | 8J           | 74         | DL           | NotPass   |
            | 8J           | 75         | DL           | NotPass   |
            | 8J           | 81         | DL           | NotPass   |
            | 8J           | 82         | DL           | NotPass   |
            | 8J           | 83         | DL           | NotPass   |
            | 8J           | 84         | DL           | NotPass   |
            | 8J           | 85         | DL           | NotPass   |
            # 恆牙
            | 8J           | 11         | DL           | NotPass   |
            | 8J           | 12         | DL           | NotPass   |
            | 8J           | 13         | DL           | NotPass   |
            | 8J           | 14         | DL           | NotPass   |
            | 8J           | 15         | DL           | NotPass   |
            | 8J           | 16         | DL           | NotPass   |
            | 8J           | 17         | DL           | NotPass   |
            | 8J           | 18         | DL           | NotPass   |
            | 8J           | 21         | DL           | NotPass   |
            | 8J           | 22         | DL           | NotPass   |
            | 8J           | 23         | DL           | NotPass   |
            | 8J           | 24         | DL           | NotPass   |
            | 8J           | 25         | DL           | NotPass   |
            | 8J           | 26         | DL           | Pass      |
            | 8J           | 27         | DL           | NotPass   |
            | 8J           | 28         | DL           | NotPass   |
            | 8J           | 31         | DL           | NotPass   |
            | 8J           | 32         | DL           | NotPass   |
            | 8J           | 33         | DL           | NotPass   |
            | 8J           | 34         | DL           | NotPass   |
            | 8J           | 35         | DL           | NotPass   |
            | 8J           | 36         | DL           | NotPass   |
            | 8J           | 37         | DL           | NotPass   |
            | 8J           | 38         | DL           | NotPass   |
            | 8J           | 41         | DL           | NotPass   |
            | 8J           | 42         | DL           | NotPass   |
            | 8J           | 43         | DL           | NotPass   |
            | 8J           | 44         | DL           | NotPass   |
            | 8J           | 45         | DL           | NotPass   |
            | 8J           | 46         | DL           | NotPass   |
            | 8J           | 47         | DL           | NotPass   |
            | 8J           | 48         | DL           | NotPass   |
            # 無牙
            | 8J           |            | DL           | NotPass   |
            #
            | 8J           | 19         | DL           | NotPass   |
            | 8J           | 29         | DL           | NotPass   |
            | 8J           | 39         | DL           | NotPass   |
            | 8J           | 49         | DL           | NotPass   |
            | 8J           | 59         | DL           | NotPass   |
            | 8J           | 69         | DL           | NotPass   |
            | 8J           | 79         | DL           | NotPass   |
            | 8J           | 89         | DL           | NotPass   |
            | 8J           | 99         | DL           | NotPass   |
            # 牙位為區域型態
            | 8J           | FM         | DL           | NotPass   |
            | 8J           | UR         | DL           | NotPass   |
            | 8J           | UL         | DL           | NotPass   |
            | 8J           | LR         | DL           | NotPass   |
            | 8J           | LL         | DL           | NotPass   |
            | 8J           | UB         | DL           | NotPass   |
            | 8J           | LB         | DL           | NotPass   |
            # 非法牙位
            | 8J           | 00         | DL           | NotPass   |
            | 8J           | 01         | DL           | NotPass   |
            | 8J           | 10         | DL           | NotPass   |
            | 8J           | 56         | DL           | NotPass   |
            | 8J           | 66         | DL           | NotPass   |
            | 8J           | 76         | DL           | NotPass   |
            | 8J           | 86         | DL           | NotPass   |
            | 8J           | 91         | DL           | NotPass   |

    Scenario Outline: （Disposal）8J 終生只能申報一次
        Given 建立醫師
        Given Kelly 11 歲病人
        Given 在 6個月前 ，建立預約
        Given 在 6個月前 ，建立掛號
        Given 在 6個月前 ，產生診療計畫
        And 新增診療代碼:
            | PastDate | A72 | A73 | A74          | A75            | A76 | A77 | A78 | A79 |
            | 6個月前     | 3   | 8B  | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
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
            | 8J           | 26         | MOB          | 8J               | 26             | MOB              | NotPass   |
            | 8J           | 26         | MOB          | 01271C           | 26             | MOB              | Pass      |

    Scenario Outline: （HIS-Today）8J 終生只能申報一次
        Given 建立醫師
        Given Kelly 11 歲病人
        Given 在 6個月前 ，建立預約
        Given 在 6個月前 ，建立掛號
        Given 在 6個月前 ，產生診療計畫
        And 新增診療代碼:
            | PastDate | A72 | A73 | A74          | A75            | A76 | A77 | A78 | A79 |
            | 6個月前     | 3   | 8B  | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
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
            | 8J           | 26         | FM           | 8J               | 26             | MO               | NotPass   |
            | 8J           | 26         | FM           | 01271C           | 26             | MO               | Pass      |

    Scenario Outline: （IC-Today）8J 終生只能申報一次
        Given 建立醫師
        Given Kelly 11 歲病人
        Given 在 6個月前 ，建立預約
        Given 在 6個月前 ，建立掛號
        Given 在 6個月前 ，產生診療計畫
        And 新增診療代碼:
            | PastDate | A72 | A73 | A74          | A75            | A76 | A77 | A78 | A79 |
            | 6個月前     | 3   | 8B  | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
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
            | 8J           | 26         | FM           | 8J             | 26           | NotPass   |
            | 8J           | 26         | FM           | 01271C         | 26           | Pass      |

    Scenario Outline: （HIS）8J 終生只能申報一次
        Given 建立醫師
        Given Kelly 11 歲病人
        Given 在 6個月前 ，建立預約
        Given 在 6個月前 ，建立掛號
        Given 在 6個月前 ，產生診療計畫
        And 新增診療代碼:
            | PastDate | A72 | A73 | A74          | A75            | A76 | A77 | A78 | A79 |
            | 6個月前     | 3   | 8B  | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
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
            | 8J           | 26         | FM           | 8J               | 26             | MO               | NotPass   |
            | 8J           | 26         | FM           | 01271C           | 26             | MO               | Pass      |

    Scenario Outline: （IC）8J 終生只能申報一次
        Given 建立醫師
        Given Kelly 11 歲病人
        Given 在 6個月前 ，建立預約
        Given 在 6個月前 ，建立掛號
        Given 在 6個月前 ，產生診療計畫
        And 新增診療代碼:
            | PastDate | A72 | A73 | A74          | A75            | A76 | A77 | A78 | A79 |
            | 6個月前     | 3   | 8B  | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
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
            | 8J           | 26         | FM           | 8J             | 26           | NotPass   |
            | 8J           | 26         | FM           | 01271C         | 26           | Pass      |

    Scenario Outline: （HIS）6個月內，不應有 8B/8F 診療項目
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
            | 8J           | 26         | DL           | 6個月前              | 8B               | 26             | DL               | 6個月      | Pass      |
            | 8J           | 26         | DL           | 5個月前              | 8B               | 26             | DL               | 6個月      | NotPass   |
            | 8J           | 26         | DL           | 4個月前              | 8B               | 26             | DL               | 6個月      | NotPass   |
            | 8J           | 26         | DL           | 3個月前              | 8B               | 26             | DL               | 6個月      | NotPass   |
            | 8J           | 26         | DL           | 2個月前              | 8B               | 26             | DL               | 6個月      | NotPass   |
            | 8J           | 26         | DL           | 1個月前              | 8B               | 26             | DL               | 6個月      | NotPass   |
            | 8J           | 26         | DL           | 當月                | 8B               | 26             | DL               | 6個月      | NotPass   |
            | 8J           | 26         | DL           | 6個月前              | 8F               | 26             | DL               | 6個月      | Pass      |
            | 8J           | 26         | DL           | 5個月前              | 8F               | 26             | DL               | 6個月      | NotPass   |
            | 8J           | 26         | DL           | 4個月前              | 8F               | 26             | DL               | 6個月      | NotPass   |
            | 8J           | 26         | DL           | 3個月前              | 8F               | 26             | DL               | 6個月      | NotPass   |
            | 8J           | 26         | DL           | 2個月前              | 8F               | 26             | DL               | 6個月      | NotPass   |
            | 8J           | 26         | DL           | 1個月前              | 8F               | 26             | DL               | 6個月      | NotPass   |
            | 8J           | 26         | DL           | 當月                | 8F               | 26             | DL               | 6個月      | NotPass   |

    Scenario Outline: （IC）6個月內，不應有 8B/8F 診療項目
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
        Then 檢查 <IssueNhiCode> 診療項目，在病患過去 <GapMonth> 紀錄中，不應包含特定的 <MedicalNhiCode> 診療代碼，確認結果是否為 <PassOrNot> 且檢查訊息類型為 D1_2_2
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastMedicalDays | MedicalNhiCode | MedicalTeeth | GapMonth | PassOrNot |
            | 8J           | 26         | DL           | 6個月前            | 8B             | 26           | 6個月      | Pass      |
            | 8J           | 26         | DL           | 5個月前            | 8B             | 26           | 6個月      | NotPass   |
            | 8J           | 26         | DL           | 4個月前            | 8B             | 26           | 6個月      | NotPass   |
            | 8J           | 26         | DL           | 3個月前            | 8B             | 26           | 6個月      | NotPass   |
            | 8J           | 26         | DL           | 2個月前            | 8B             | 26           | 6個月      | NotPass   |
            | 8J           | 26         | DL           | 1個月前            | 8B             | 26           | 6個月      | NotPass   |
            | 8J           | 26         | DL           | 當月              | 8B             | 26           | 6個月      | NotPass   |
            | 8J           | 26         | DL           | 6個月前            | 8F             | 26           | 6個月      | Pass      |
            | 8J           | 26         | DL           | 5個月前            | 8F             | 26           | 6個月      | NotPass   |
            | 8J           | 26         | DL           | 4個月前            | 8F             | 26           | 6個月      | NotPass   |
            | 8J           | 26         | DL           | 3個月前            | 8F             | 26           | 6個月      | NotPass   |
            | 8J           | 26         | DL           | 2個月前            | 8F             | 26           | 6個月      | NotPass   |
            | 8J           | 26         | DL           | 1個月前            | 8F             | 26           | 6個月      | NotPass   |
            | 8J           | 26         | DL           | 當月              | 8F             | 26           | 6個月      | NotPass   |

    Scenario Outline: （HIS）8B/8F 曾經申報過
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
            | 8J           | 26         | FM           | 8B               | 26             | MO               | Pass      |
            | 8J           | 26         | FM           | 8F               | 26             | MO               | Pass      |
            | 8J           | 26         | FM           | 01271C           | 26             | MO               | NotPass   |

    Scenario Outline: （IC）8B/8F 曾經申報過
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
            | 8J           | 26         | FM           | 8B             | 26           | Pass      |
            | 8J           | 26         | FM           | 8F             | 26           | Pass      |
            | 8J           | 26         | FM           | 01271C         | 26           | NotPass   |
