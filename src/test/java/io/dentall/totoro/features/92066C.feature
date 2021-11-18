@nhi @nhi-92-series @part2
Feature: 92066C 特定局部治療

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
            | 92066C       | 11         | DL           | Pass      |

    Scenario Outline: （HIS）3天內有 92066C 診療項目，就醫類別請選 AB
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
            | 92066C       | 11         | DL           | 2                 | 92066C           | 11             | 3      | NotPass   |
            | 92066C       | 11         | DL           | 3                 | 92066C           | 11             | 3      | NotPass   |
            | 92066C       | 11         | DL           | 4                 | 92066C           | 11             | 3      | Pass      |

    Scenario Outline: （IC）3天內有 92066C 診療項目，就醫類別請選 AB
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
            | 92066C       | 11         | DL           | 2               | 92066C         | 11           | 3      | NotPass   |
            | 92066C       | 11         | DL           | 3               | 92066C         | 11           | 3      | NotPass   |
            | 92066C       | 11         | DL           | 4               | 92066C         | 11           | 3      | Pass      |

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
            | 92066C       | 51         | DL           | Pass      |
            | 92066C       | 52         | DL           | Pass      |
            | 92066C       | 53         | DL           | Pass      |
            | 92066C       | 54         | DL           | Pass      |
            | 92066C       | 55         | DL           | Pass      |
            | 92066C       | 61         | DL           | Pass      |
            | 92066C       | 62         | DL           | Pass      |
            | 92066C       | 63         | DL           | Pass      |
            | 92066C       | 64         | DL           | Pass      |
            | 92066C       | 65         | DL           | Pass      |
            | 92066C       | 71         | DL           | Pass      |
            | 92066C       | 72         | DL           | Pass      |
            | 92066C       | 73         | DL           | Pass      |
            | 92066C       | 74         | DL           | Pass      |
            | 92066C       | 75         | DL           | Pass      |
            | 92066C       | 81         | DL           | Pass      |
            | 92066C       | 82         | DL           | Pass      |
            | 92066C       | 83         | DL           | Pass      |
            | 92066C       | 84         | DL           | Pass      |
            | 92066C       | 85         | DL           | Pass      |
            # 恆牙
            | 92066C       | 11         | DL           | Pass      |
            | 92066C       | 12         | DL           | Pass      |
            | 92066C       | 13         | DL           | Pass      |
            | 92066C       | 14         | DL           | Pass      |
            | 92066C       | 15         | DL           | Pass      |
            | 92066C       | 16         | DL           | Pass      |
            | 92066C       | 17         | DL           | Pass      |
            | 92066C       | 18         | DL           | Pass      |
            | 92066C       | 21         | DL           | Pass      |
            | 92066C       | 22         | DL           | Pass      |
            | 92066C       | 23         | DL           | Pass      |
            | 92066C       | 24         | DL           | Pass      |
            | 92066C       | 25         | DL           | Pass      |
            | 92066C       | 26         | DL           | Pass      |
            | 92066C       | 27         | DL           | Pass      |
            | 92066C       | 28         | DL           | Pass      |
            | 92066C       | 31         | DL           | Pass      |
            | 92066C       | 32         | DL           | Pass      |
            | 92066C       | 33         | DL           | Pass      |
            | 92066C       | 34         | DL           | Pass      |
            | 92066C       | 35         | DL           | Pass      |
            | 92066C       | 36         | DL           | Pass      |
            | 92066C       | 37         | DL           | Pass      |
            | 92066C       | 38         | DL           | Pass      |
            | 92066C       | 41         | DL           | Pass      |
            | 92066C       | 42         | DL           | Pass      |
            | 92066C       | 43         | DL           | Pass      |
            | 92066C       | 44         | DL           | Pass      |
            | 92066C       | 45         | DL           | Pass      |
            | 92066C       | 46         | DL           | Pass      |
            | 92066C       | 47         | DL           | Pass      |
            | 92066C       | 48         | DL           | Pass      |
            # 無牙
            | 92066C       |            | DL           | NotPass   |
            #
            | 92066C       | 19         | DL           | Pass      |
            | 92066C       | 29         | DL           | Pass      |
            | 92066C       | 39         | DL           | Pass      |
            | 92066C       | 49         | DL           | Pass      |
            | 92066C       | 59         | DL           | NotPass   |
            | 92066C       | 69         | DL           | NotPass   |
            | 92066C       | 79         | DL           | NotPass   |
            | 92066C       | 89         | DL           | NotPass   |
            | 92066C       | 99         | DL           | Pass      |
            # 牙位為區域型態
            | 92066C       | FM         | DL           | NotPass   |
            | 92066C       | UR         | DL           | Pass      |
            | 92066C       | UL         | DL           | Pass      |
            | 92066C       | UA         | DL           | Pass      |
            | 92066C       | UB         | DL           | NotPass   |
            | 92066C       | LL         | DL           | Pass      |
            | 92066C       | LR         | DL           | Pass      |
            | 92066C       | LA         | DL           | Pass      |
            | 92066C       | LB         | DL           | NotPass   |
            # 非法牙位
            | 92066C       | 00         | DL           | NotPass   |
            | 92066C       | 01         | DL           | NotPass   |
            | 92066C       | 10         | DL           | NotPass   |
            | 92066C       | 56         | DL           | NotPass   |
            | 92066C       | 66         | DL           | NotPass   |
            | 92066C       | 76         | DL           | NotPass   |
            | 92066C       | 86         | DL           | NotPass   |
            | 92066C       | 91         | DL           | NotPass   |
