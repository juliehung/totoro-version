@nhi @nhi-91-series
Feature: 91001C 牙周病緊急處置

    Scenario Outline: 全部檢核成功
        Given 建立醫師
        Given Stan 24 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 確認診療代碼 <IssueNhiCode> ，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            | 91001C       | 14         | MOB          | Pass      |

    Scenario Outline: （Disposal）每月只能申報2次 91001C 健保代碼
        Given 建立醫師
        Given Stan 24 歲病人
        Given 新增 <Nums> 筆診療處置:
            | Id | PastDate | A72 | A73            | A74 | A75            | A76 | A77 | A78 | A79 |
            | 1  | 當月1號     | 3   | <IssueNhiCode> | 21  | <IssueSurface> | 0   | 1.0 | 03  |     |
            | 2  | 當月10號    | 3   | <IssueNhiCode> | 31  | <IssueSurface> | 0   | 1.0 | 03  |     |
        Given 在 當月底 ，建立預約
        Given 在 當月底 ，建立掛號
        Given 在 當月底 ，產生診療計畫
        And 新增診療代碼:
            | PastDate | A72 | A73            | A74          | A75            | A76 | A77 | A78 | A79 |
            | 當月底      | 3   | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode        | Teeth        | Surface        | NewNhiCode | NewTeeth | NewSurface |
            | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |            |          |            |
        Then 在 每月 的記錄中，<IssueNhiCode> 診療代碼最多只能 2 次，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | Nums | PassOrNot |
            | 91001C       | 11         | MOB          | 1    | Pass      |
            | 91001C       | 11         | MOB          | 2    | NotPass   |

    Scenario Outline: （HIS）每月只能申報2次 91001C 健保代碼
        Given 建立醫師
        Given Stan 24 歲病人
        Given 新增 <Nums> 筆診療處置:
            | Id | PastDate | A72 | A73            | A74 | A75            | A76 | A77 | A78 | A79 |
            | 1  | 當月1號     | 3   | <IssueNhiCode> | 21  | <IssueSurface> | 0   | 1.0 | 03  |     |
            | 2  | 當月10號    | 3   | <IssueNhiCode> | 31  | <IssueSurface> | 0   | 1.0 | 03  |     |
        Given 在 當月底 ，建立預約
        Given 在 當月底 ，建立掛號
        Given 在 當月底 ，產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 在 每月 的記錄中，<IssueNhiCode> 診療代碼最多只能 2 次，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | Nums | PassOrNot |
            | 91001C       | 11         | MOB          | 1    | Pass      |
            | 91001C       | 11         | MOB          | 2    | NotPass   |

    Scenario Outline: （IC）每月只能申報2次 91001C 健保代碼
        Given 建立醫師
        Given Stan 24 歲病人
        Given 新增 <Nums> 筆健保醫療:
            | PastDate | NhiCode        | Teeth |
            | 當月1號     | <IssueNhiCode> | 21    |
            | 當月10號    | <IssueNhiCode> | 31    |
        Given 在 當月底 ，建立預約
        Given 在 當月底 ，建立掛號
        Given 在 當月底 ，產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 在 每月 的記錄中，<IssueNhiCode> 診療代碼最多只能 2 次，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | Nums | PassOrNot |
            | 91001C       | 11         | MOB          | 1    | Pass      |
            | 91001C       | 11         | MOB          | 2    | NotPass   |

    Scenario Outline: （HIS）每月牙位每個象限只能申報1次 91001C 健保代碼
        Given 建立醫師
        Given Stan 24 歲病人
        Given 在 <PastTreatmentDate> ，建立預約
        Given 在 <PastTreatmentDate> ，建立掛號
        Given 在 <PastTreatmentDate> ，產生診療計畫
        And 新增診療代碼:
            | PastDate            | A72 | A73                | A74              | A75 | A76 | A77 | A78 | A79 |
            | <PastTreatmentDate> | 3   | <TreatmentNhiCode> | <TreatmentTeeth> | MOB | 0   | 1.0 | 03  |     |
        Given 在 當月底 ，建立預約
        Given 在 當月底 ，建立掛號
        Given 在 當月底 ，產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 在 每月 的紀錄中，牙位 <IssueTeeth> 在同一象限中，最多只能申報 1 次 <IssueNhiCode> 健保代碼，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastTreatmentDate | TreatmentNhiCode | TreatmentTeeth | PassOrNot |
            # 象限一 恆牙
            | 91001C       | 11         | MOB          | 當月初               | 91001C           | 21             | Pass      |
            | 91001C       | 11         | MOB          | 當月初               | 91001C           | 11             | NotPass   |
            | 91001C       | 19         | MOB          | 當月初               | 91001C           | 21             | Pass      |
            | 91001C       | 19         | MOB          | 當月初               | 91001C           | 11             | NotPass   |
            # 象限二 恆牙
            | 91001C       | 21         | MOB          | 當月初               | 91001C           | 31             | Pass      |
            | 91001C       | 21         | MOB          | 當月初               | 91001C           | 21             | NotPass   |
            | 91001C       | 29         | MOB          | 當月初               | 91001C           | 31             | Pass      |
            | 91001C       | 29         | MOB          | 當月初               | 91001C           | 21             | NotPass   |
            # 象限三 恆牙
            | 91001C       | 31         | MOB          | 當月初               | 91001C           | 41             | Pass      |
            | 91001C       | 31         | MOB          | 當月初               | 91001C           | 31             | NotPass   |
            | 91001C       | 39         | MOB          | 當月初               | 91001C           | 41             | Pass      |
            | 91001C       | 39         | MOB          | 當月初               | 91001C           | 31             | NotPass   |
            # 象限四 恆牙
            | 91001C       | 41         | MOB          | 當月初               | 91001C           | 11             | Pass      |
            | 91001C       | 41         | MOB          | 當月初               | 91001C           | 41             | NotPass   |
            | 91001C       | 49         | MOB          | 當月初               | 91001C           | 11             | Pass      |
            | 91001C       | 49         | MOB          | 當月初               | 91001C           | 41             | NotPass   |
            # 象限一 乳牙
            | 91001C       | 51         | MOB          | 當月初               | 91001C           | 61             | Pass      |
            | 91001C       | 51         | MOB          | 當月初               | 91001C           | 51             | NotPass   |
            # 象限二 乳牙
            | 91001C       | 61         | MOB          | 當月初               | 91001C           | 71             | Pass      |
            | 91001C       | 61         | MOB          | 當月初               | 91001C           | 61             | NotPass   |
            # 象限三 乳牙
            | 91001C       | 71         | MOB          | 當月初               | 91001C           | 81             | Pass      |
            | 91001C       | 71         | MOB          | 當月初               | 91001C           | 71             | NotPass   |
            # 象限四 乳牙
            | 91001C       | 81         | MOB          | 當月初               | 91001C           | 51             | Pass      |
            | 91001C       | 81         | MOB          | 當月初               | 91001C           | 81             | NotPass   |

    Scenario Outline: （IC）每月牙位每個象限只能申報1次 91001C 健保代碼
        Given 建立醫師
        Given Wind 24 歲病人
        Given 新增健保醫療:
            | PastDate          | NhiCode          | Teeth          |
            | <PastMedicalDate> | <MedicalNhiCode> | <MedicalTeeth> |
        Given 在 當月底 ，建立預約
        Given 在 當月底 ，建立掛號
        Given 在 當月底 ，產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 在 每月 的紀錄中，牙位 <IssueTeeth> 在同一象限中，最多只能申報 1 次 <IssueNhiCode> 健保代碼，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastMedicalDate | MedicalNhiCode | MedicalTeeth | PassOrNot |
            # 象限一 恆牙
            | 91001C       | 11         | MOB          | 當月初             | 91001C         | 21           | Pass      |
            | 91001C       | 11         | MOB          | 當月初             | 91001C         | 11           | NotPass   |
            | 91001C       | 19         | MOB          | 當月初             | 91001C         | 21           | Pass      |
            | 91001C       | 19         | MOB          | 當月初             | 91001C         | 11           | NotPass   |
            # 象限二 恆牙
            | 91001C       | 21         | MOB          | 當月初             | 91001C         | 31           | Pass      |
            | 91001C       | 21         | MOB          | 當月初             | 91001C         | 21           | NotPass   |
            | 91001C       | 29         | MOB          | 當月初             | 91001C         | 31           | Pass      |
            | 91001C       | 29         | MOB          | 當月初             | 91001C         | 21           | NotPass   |
            # 象限三 恆牙
            | 91001C       | 31         | MOB          | 當月初             | 91001C         | 41           | Pass      |
            | 91001C       | 31         | MOB          | 當月初             | 91001C         | 31           | NotPass   |
            | 91001C       | 39         | MOB          | 當月初             | 91001C         | 41           | Pass      |
            | 91001C       | 39         | MOB          | 當月初             | 91001C         | 31           | NotPass   |
            # 象限四 恆牙
            | 91001C       | 41         | MOB          | 當月初             | 91001C         | 11           | Pass      |
            | 91001C       | 41         | MOB          | 當月初             | 91001C         | 41           | NotPass   |
            | 91001C       | 49         | MOB          | 當月初             | 91001C         | 11           | Pass      |
            | 91001C       | 49         | MOB          | 當月初             | 91001C         | 41           | NotPass   |
            # 象限一 乳牙
            | 91001C       | 51         | MOB          | 當月初             | 91001C         | 61           | Pass      |
            | 91001C       | 51         | MOB          | 當月初             | 91001C         | 51           | NotPass   |
            # 象限二 乳牙
            | 91001C       | 61         | MOB          | 當月初             | 91001C         | 71           | Pass      |
            | 91001C       | 61         | MOB          | 當月初             | 91001C         | 61           | NotPass   |
            # 象限三 乳牙
            | 91001C       | 71         | MOB          | 當月初             | 91001C         | 81           | Pass      |
            | 91001C       | 71         | MOB          | 當月初             | 91001C         | 71           | NotPass   |
            # 象限四 乳牙
            | 91001C       | 81         | MOB          | 當月初             | 91001C         | 51           | Pass      |
            | 91001C       | 81         | MOB          | 當月初             | 91001C         | 81           | NotPass   |

    Scenario Outline: （Disposal）同日不得同時有 91003C~91005C/91017C/91019C/91103C~91104C 診療項目
        Given 建立醫師
        Given Stan 24 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode         | NewTeeth         | NewSurface         |
            |         |       |         | <TreatmentNhiCode> | <TreatmentTeeth> | <TreatmentSurface> |
            |         |       |         | <IssueNhiCode>     | <IssueTeeth>     | <IssueSurface>     |
        Then 同日不得有 <TreatmentNhiCode> 診療項目，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | TreatmentNhiCode | TreatmentTeeth | TreatmentSurface | PassOrNot |
            | 91001C       | 11         | MOB          | 91003C           | 11             | MOB              | NotPass   |
            | 91001C       | 11         | MOB          | 91004C           | 11             | MOB              | NotPass   |
            | 91001C       | 11         | MOB          | 91005C           | 11             | MOB              | NotPass   |
            | 91001C       | 11         | MOB          | 91017C           | 11             | MOB              | NotPass   |
            | 91001C       | 11         | MOB          | 91019C           | 11             | MOB              | NotPass   |
            | 91001C       | 11         | MOB          | 91103C           | 11             | MOB              | NotPass   |
            | 91001C       | 11         | MOB          | 91104C           | 11             | MOB              | NotPass   |

    Scenario Outline: （HIS-Today）同日不得同時有 91003C~91005C/91017C/91019C/91103C~91104C 診療項目
        Given 建立醫師
        Given Stan 24 歲病人
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
        Then 同日不得有 <TreatmentNhiCode> 診療項目，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastTreatmentDate | TreatmentNhiCode | TreatmentTeeth | TreatmentSurface | PassOrNot |
            | 91001C       | 11         | MOB          | 當日                | 91003C           | 11             | MOB              | NotPass   |
            | 91001C       | 11         | MOB          | 當日                | 91004C           | 11             | MOB              | NotPass   |
            | 91001C       | 11         | MOB          | 當日                | 91005C           | 11             | MOB              | NotPass   |
            | 91001C       | 11         | MOB          | 當日                | 91017C           | 11             | MOB              | NotPass   |
            | 91001C       | 11         | MOB          | 當日                | 91019C           | 11             | MOB              | NotPass   |
            | 91001C       | 11         | MOB          | 當日                | 91103C           | 11             | MOB              | NotPass   |
            | 91001C       | 11         | MOB          | 當日                | 91104C           | 11             | MOB              | NotPass   |
            | 91001C       | 11         | MOB          | 昨日                | 91003C           | 11             | MOB              | Pass      |
            | 91001C       | 11         | MOB          | 昨日                | 91004C           | 11             | MOB              | Pass      |
            | 91001C       | 11         | MOB          | 昨日                | 91005C           | 11             | MOB              | Pass      |
            | 91001C       | 11         | MOB          | 昨日                | 91017C           | 11             | MOB              | Pass      |
            | 91001C       | 11         | MOB          | 昨日                | 91019C           | 11             | MOB              | Pass      |
            | 91001C       | 11         | MOB          | 昨日                | 91103C           | 11             | MOB              | Pass      |
            | 91001C       | 11         | MOB          | 昨日                | 91104C           | 11             | MOB              | Pass      |

    Scenario Outline: （IC）同日不得同時有 91003C~91005C/91017C/91019C/91103C~91104C 診療項目
        Given 建立醫師
        Given Stan 24 歲病人
        Given 新增健保醫療:
            | PastDate          | NhiCode          | Teeth          |
            | <PastMedicalDate> | <MedicalNhiCode> | <MedicalTeeth> |
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 同日不得有 <MedicalNhiCode> 診療項目，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastMedicalDate | MedicalNhiCode | MedicalTeeth | PassOrNot |
            | 91001C       | 11         | MOB          | 當日              | 91003C         | 11           | NotPass   |
            | 91001C       | 11         | MOB          | 當日              | 91004C         | 11           | NotPass   |
            | 91001C       | 11         | MOB          | 當日              | 91005C         | 11           | NotPass   |
            | 91001C       | 11         | MOB          | 當日              | 91017C         | 11           | NotPass   |
            | 91001C       | 11         | MOB          | 當日              | 91019C         | 11           | NotPass   |
            | 91001C       | 11         | MOB          | 當日              | 91103C         | 11           | NotPass   |
            | 91001C       | 11         | MOB          | 當日              | 91104C         | 11           | NotPass   |
            | 91001C       | 11         | MOB          | 昨日              | 91003C         | 11           | Pass      |
            | 91001C       | 11         | MOB          | 昨日              | 91004C         | 11           | Pass      |
            | 91001C       | 11         | MOB          | 昨日              | 91005C         | 11           | Pass      |
            | 91001C       | 11         | MOB          | 昨日              | 91017C         | 11           | Pass      |
            | 91001C       | 11         | MOB          | 昨日              | 91019C         | 11           | Pass      |
            | 91001C       | 11         | MOB          | 昨日              | 91103C         | 11           | Pass      |
            | 91001C       | 11         | MOB          | 昨日              | 91104C         | 11           | Pass      |

    Scenario Outline: 檢查治療的牙位是否為 VALIDATED_ALL_EXCLUDE_FM
        Given 建立醫師
        Given Wind 24 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 檢查 <IssueTeeth> 牙位，依 VALIDATED_ALL_EXCLUDE_FM 判定是否為核可牙位，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            # 乳牙
            | 91001C       | 51         | DL           | Pass      |
            | 91001C       | 52         | DL           | Pass      |
            | 91001C       | 53         | DL           | Pass      |
            | 91001C       | 54         | DL           | Pass      |
            | 91001C       | 55         | DL           | Pass      |
            | 91001C       | 61         | DL           | Pass      |
            | 91001C       | 62         | DL           | Pass      |
            | 91001C       | 63         | DL           | Pass      |
            | 91001C       | 64         | DL           | Pass      |
            | 91001C       | 65         | DL           | Pass      |
            | 91001C       | 71         | DL           | Pass      |
            | 91001C       | 72         | DL           | Pass      |
            | 91001C       | 73         | DL           | Pass      |
            | 91001C       | 74         | DL           | Pass      |
            | 91001C       | 75         | DL           | Pass      |
            | 91001C       | 81         | DL           | Pass      |
            | 91001C       | 82         | DL           | Pass      |
            | 91001C       | 83         | DL           | Pass      |
            | 91001C       | 84         | DL           | Pass      |
            | 91001C       | 85         | DL           | Pass      |
            # 恆牙
            | 91001C       | 11         | DL           | Pass      |
            | 91001C       | 12         | DL           | Pass      |
            | 91001C       | 13         | DL           | Pass      |
            | 91001C       | 14         | DL           | Pass      |
            | 91001C       | 15         | DL           | Pass      |
            | 91001C       | 16         | DL           | Pass      |
            | 91001C       | 17         | DL           | Pass      |
            | 91001C       | 18         | DL           | Pass      |
            | 91001C       | 21         | DL           | Pass      |
            | 91001C       | 22         | DL           | Pass      |
            | 91001C       | 23         | DL           | Pass      |
            | 91001C       | 24         | DL           | Pass      |
            | 91001C       | 25         | DL           | Pass      |
            | 91001C       | 26         | DL           | Pass      |
            | 91001C       | 27         | DL           | Pass      |
            | 91001C       | 28         | DL           | Pass      |
            | 91001C       | 31         | DL           | Pass      |
            | 91001C       | 32         | DL           | Pass      |
            | 91001C       | 33         | DL           | Pass      |
            | 91001C       | 34         | DL           | Pass      |
            | 91001C       | 35         | DL           | Pass      |
            | 91001C       | 36         | DL           | Pass      |
            | 91001C       | 37         | DL           | Pass      |
            | 91001C       | 38         | DL           | Pass      |
            | 91001C       | 41         | DL           | Pass      |
            | 91001C       | 42         | DL           | Pass      |
            | 91001C       | 43         | DL           | Pass      |
            | 91001C       | 44         | DL           | Pass      |
            | 91001C       | 45         | DL           | Pass      |
            | 91001C       | 46         | DL           | Pass      |
            | 91001C       | 47         | DL           | Pass      |
            | 91001C       | 48         | DL           | Pass      |
            # 無牙
            | 91001C       |            | DL           | NotPass   |
            #
            | 91001C       | 19         | DL           | Pass      |
            | 91001C       | 29         | DL           | Pass      |
            | 91001C       | 39         | DL           | Pass      |
            | 91001C       | 49         | DL           | Pass      |
            | 91001C       | 59         | DL           | NotPass   |
            | 91001C       | 69         | DL           | NotPass   |
            | 91001C       | 79         | DL           | NotPass   |
            | 91001C       | 89         | DL           | NotPass   |
            | 91001C       | 99         | DL           | Pass      |
            # 牙位為區域型態
            | 91001C       | FM         | DL           | NotPass   |
            | 91001C       | UR         | DL           | Pass      |
            | 91001C       | UL         | DL           | Pass      |
            | 91001C       | UA         | DL           | Pass      |
            | 91001C       | UB         | DL           | NotPass   |
            | 91001C       | LL         | DL           | Pass      |
            | 91001C       | LR         | DL           | Pass      |
            | 91001C       | LA         | DL           | Pass      |
            | 91001C       | LB         | DL           | NotPass   |
            # 非法牙位
            | 91001C       | 00         | DL           | NotPass   |
            | 91001C       | 01         | DL           | NotPass   |
            | 91001C       | 10         | DL           | NotPass   |
            | 91001C       | 56         | DL           | NotPass   |
            | 91001C       | 66         | DL           | NotPass   |
            | 91001C       | 76         | DL           | NotPass   |
            | 91001C       | 86         | DL           | NotPass   |
            | 91001C       | 91         | DL           | NotPass   |
