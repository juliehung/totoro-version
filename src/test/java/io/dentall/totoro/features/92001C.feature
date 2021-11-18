@nhi @nhi-92-series @part1
Feature: 92001C 非特定局部治療

    Scenario Outline: 全部檢核成功
        Given 建立醫師
        Given Scott 24 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 確認診療代碼 <IssueNhiCode> ，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            | 92001C       | 11         | DL           | Pass      |

    Scenario Outline: （HIS）3天內，有 92001C 診療項目
        Given 建立醫師
        Given Scott 24 歲病人
        Given 在過去第 <PastTreatmentDays> 天，建立預約
        Given 在過去第 <PastTreatmentDays> 天，建立掛號
        Given 在過去第 <PastTreatmentDays> 天，產生診療計畫
        And 新增診療代碼:
            | PastDays            | A72 | A73                | A74              | A75 | A76 | A77 | A78 | A79 |
            | <PastTreatmentDays> | 3   | <TreatmentNhiCode> | <TreatmentTeeth> | MOB | 0   | 1.0 | 03  |     |
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 檢查 <IssueNhiCode> 診療項目，在病患過去 <GapDay> 天紀錄中，不應包含特定的 <TreatmentNhiCode> 診療代碼，確認結果是否為 <PassOrNot> 且檢查訊息類型為 D7_1
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastTreatmentDays | TreatmentNhiCode | TreatmentTeeth | GapDay | PassOrNot |
            | 92001C       | 11         | DL           | 2                 | 92001C           | 11             | 3      | NotPass   |
            | 92001C       | 11         | DL           | 3                 | 92001C           | 11             | 3      | NotPass   |
            | 92001C       | 11         | DL           | 4                 | 92001C           | 11             | 3      | Pass      |

    Scenario Outline: （IC）3天內，有 92001C 診療項目
        Given 建立醫師
        Given Scott 24 歲病人
        Given 新增健保醫療:
            | PastDays          | NhiCode          | Teeth          |
            | <PastMedicalDays> | <MedicalNhiCode> | <MedicalTeeth> |
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 檢查 <IssueNhiCode> 診療項目，在病患過去 <GapDay> 天紀錄中，不應包含特定的 <MedicalNhiCode> 診療代碼，確認結果是否為 <PassOrNot> 且檢查訊息類型為 D7_1
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastMedicalDays | MedicalNhiCode | MedicalTeeth | GapDay | PassOrNot |
            | 92001C       | 11         | DL           | 2               | 92001C         | 11           | 3      | NotPass   |
            | 92001C       | 11         | DL           | 3               | 92001C         | 11           | 3      | NotPass   |
            | 92001C       | 11         | DL           | 4               | 92001C         | 11           | 3      | Pass      |

    Scenario Outline: （HIS）30天內只能申報2次 92001C 健保代碼
        Given 建立醫師
        Given Scott 24 歲病人
        Given 新增 <Nums> 筆診療處置:
            | Id | PastDays | A72 | A73            | A74 | A75            | A76 | A77 | A78 | A79 |
            | 1  | 29       | 3   | <IssueNhiCode> | 21  | <IssueSurface> | 0   | 1.0 | 03  |     |
            | 2  | 30       | 3   | <IssueNhiCode> | 31  | <IssueSurface> | 0   | 1.0 | 03  |     |
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 在 30天 的記錄中，<IssueNhiCode> 診療代碼最多只能 2 次，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | Nums | PassOrNot |
            | 92001C       | 11         | DL           | 1    | Pass      |
            | 92001C       | 11         | DL           | 2    | NotPass   |

    Scenario Outline: （IC）30天內只能申報2次 92001C 健保代碼
        Given 建立醫師
        Given Scott 24 歲病人
        Given 新增 <Nums> 筆健保醫療:
            | PastDays | NhiCode        | Teeth |
            | 29       | <IssueNhiCode> | 21    |
            | 30       | <IssueNhiCode> | 31    |
        Given 在 當月底 ，建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 在 30天 的記錄中，<IssueNhiCode> 診療代碼最多只能 2 次，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | Nums | PassOrNot |
            | 92001C       | 11         | DL           | 1    | Pass      |
            | 92001C       | 11         | DL           | 2    | NotPass   |

    Scenario Outline: 檢查治療的牙位是否為 VALIDATED_ALL_EXCLUDE_FM
        Given 建立醫師
        Given Stan 24 歲病人
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
            | 92001C       | 51         | DL           | Pass      |
            | 92001C       | 52         | DL           | Pass      |
            | 92001C       | 53         | DL           | Pass      |
            | 92001C       | 54         | DL           | Pass      |
            | 92001C       | 55         | DL           | Pass      |
            | 92001C       | 61         | DL           | Pass      |
            | 92001C       | 62         | DL           | Pass      |
            | 92001C       | 63         | DL           | Pass      |
            | 92001C       | 64         | DL           | Pass      |
            | 92001C       | 65         | DL           | Pass      |
            | 92001C       | 71         | DL           | Pass      |
            | 92001C       | 72         | DL           | Pass      |
            | 92001C       | 73         | DL           | Pass      |
            | 92001C       | 74         | DL           | Pass      |
            | 92001C       | 75         | DL           | Pass      |
            | 92001C       | 81         | DL           | Pass      |
            | 92001C       | 82         | DL           | Pass      |
            | 92001C       | 83         | DL           | Pass      |
            | 92001C       | 84         | DL           | Pass      |
            | 92001C       | 85         | DL           | Pass      |
            # 恆牙
            | 92001C       | 11         | DL           | Pass      |
            | 92001C       | 12         | DL           | Pass      |
            | 92001C       | 13         | DL           | Pass      |
            | 92001C       | 14         | DL           | Pass      |
            | 92001C       | 15         | DL           | Pass      |
            | 92001C       | 16         | DL           | Pass      |
            | 92001C       | 17         | DL           | Pass      |
            | 92001C       | 18         | DL           | Pass      |
            | 92001C       | 21         | DL           | Pass      |
            | 92001C       | 22         | DL           | Pass      |
            | 92001C       | 23         | DL           | Pass      |
            | 92001C       | 24         | DL           | Pass      |
            | 92001C       | 25         | DL           | Pass      |
            | 92001C       | 26         | DL           | Pass      |
            | 92001C       | 27         | DL           | Pass      |
            | 92001C       | 28         | DL           | Pass      |
            | 92001C       | 31         | DL           | Pass      |
            | 92001C       | 32         | DL           | Pass      |
            | 92001C       | 33         | DL           | Pass      |
            | 92001C       | 34         | DL           | Pass      |
            | 92001C       | 35         | DL           | Pass      |
            | 92001C       | 36         | DL           | Pass      |
            | 92001C       | 37         | DL           | Pass      |
            | 92001C       | 38         | DL           | Pass      |
            | 92001C       | 41         | DL           | Pass      |
            | 92001C       | 42         | DL           | Pass      |
            | 92001C       | 43         | DL           | Pass      |
            | 92001C       | 44         | DL           | Pass      |
            | 92001C       | 45         | DL           | Pass      |
            | 92001C       | 46         | DL           | Pass      |
            | 92001C       | 47         | DL           | Pass      |
            | 92001C       | 48         | DL           | Pass      |
            # 無牙
            | 92001C       |            | DL           | NotPass   |
            #
            | 92001C       | 19         | DL           | Pass      |
            | 92001C       | 29         | DL           | Pass      |
            | 92001C       | 39         | DL           | Pass      |
            | 92001C       | 49         | DL           | Pass      |
            | 92001C       | 59         | DL           | NotPass   |
            | 92001C       | 69         | DL           | NotPass   |
            | 92001C       | 79         | DL           | NotPass   |
            | 92001C       | 89         | DL           | NotPass   |
            | 92001C       | 99         | DL           | Pass      |
            # 牙位為區域型態
            | 92001C       | FM         | DL           | NotPass   |
            | 92001C       | UR         | DL           | Pass      |
            | 92001C       | UL         | DL           | Pass      |
            | 92001C       | UA         | DL           | Pass      |
            | 92001C       | UB         | DL           | NotPass   |
            | 92001C       | LL         | DL           | Pass      |
            | 92001C       | LR         | DL           | Pass      |
            | 92001C       | LA         | DL           | Pass      |
            | 92001C       | LB         | DL           | NotPass   |
            # 非法牙位
            | 92001C       | 00         | DL           | NotPass   |
            | 92001C       | 01         | DL           | NotPass   |
            | 92001C       | 10         | DL           | NotPass   |
            | 92001C       | 56         | DL           | NotPass   |
            | 92001C       | 66         | DL           | NotPass   |
            | 92001C       | 76         | DL           | NotPass   |
            | 92001C       | 86         | DL           | NotPass   |
            | 92001C       | 91         | DL           | NotPass   |
