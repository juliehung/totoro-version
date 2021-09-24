@nhi @nhi-91-series
Feature: 91008C 齒齦下括除術(含牙根整平術)-局部(3齒以內)

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
            | 91008C       | 11         | MOB          | Pass      |

    Scenario Outline: （HIS）90天內，不應有 91015C/91016C/91018C 診療項目
        Given 建立醫師
        Given Stan 24 歲病人
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
        Then 檢查 <IssueNhiCode> 診療項目，在病患過去 <GapDay> 天紀錄中，不應包含特定的 <TreatmentNhiCode> 診療代碼，確認結果是否為 <PassOrNot> 且檢查訊息類型為 D1_2
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastTreatmentDays | TreatmentNhiCode | TreatmentTeeth | GapDay | PassOrNot |
            | 91008C       | 11         | DL           | 89                | 91015C           | 11             | 90     | NotPass   |
            | 91008C       | 11         | DL           | 90                | 91015C           | 11             | 90     | NotPass   |
            | 91008C       | 11         | DL           | 91                | 91015C           | 11             | 90     | Pass      |
            | 91008C       | 11         | DL           | 89                | 91016C           | 11             | 90     | NotPass   |
            | 91008C       | 11         | DL           | 90                | 91016C           | 11             | 90     | NotPass   |
            | 91008C       | 11         | DL           | 91                | 91016C           | 11             | 90     | Pass      |
            | 91008C       | 11         | DL           | 89                | 91018C           | 11             | 90     | NotPass   |
            | 91008C       | 11         | DL           | 90                | 91018C           | 11             | 90     | NotPass   |
            | 91008C       | 11         | DL           | 91                | 91018C           | 11             | 90     | Pass      |

    Scenario Outline: （IC）90天內，不應有 91015C/91016C/91018C 診療項目
        Given 建立醫師
        Given Stan 24 歲病人
        Given 新增健保醫療:
            | PastDays          | NhiCode          | Teeth          |
            | <PastMedicalDays> | <MedicalNhiCode> | <MedicalTeeth> |
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 檢查 <IssueNhiCode> 診療項目，在病患過去 <GapDay> 天紀錄中，不應包含特定的 <MedicalNhiCode> 診療代碼，確認結果是否為 <PassOrNot> 且檢查訊息類型為 D1_2
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastMedicalDays | MedicalNhiCode | MedicalTeeth | GapDay | PassOrNot |
            | 91008C       | 11         | DL           | 89              | 91015C         | 11           | 90     | NotPass   |
            | 91008C       | 11         | DL           | 90              | 91015C         | 11           | 90     | NotPass   |
            | 91008C       | 11         | DL           | 91              | 91015C         | 11           | 90     | Pass      |
            | 91008C       | 11         | DL           | 89              | 91016C         | 11           | 90     | NotPass   |
            | 91008C       | 11         | DL           | 90              | 91016C         | 11           | 90     | NotPass   |
            | 91008C       | 11         | DL           | 91              | 91016C         | 11           | 90     | Pass      |
            | 91008C       | 11         | DL           | 89              | 91018C         | 11           | 90     | NotPass   |
            | 91008C       | 11         | DL           | 90              | 91018C         | 11           | 90     | NotPass   |
            | 91008C       | 11         | DL           | 91              | 91018C         | 11           | 90     | Pass      |

    Scenario Outline: 提醒須檢附影像
        Given 建立醫師
        Given Stan 24 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 提醒"須檢附影像"，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            | 91008C       | 11         | MOB          | Pass      |

    Scenario Outline: 檢查治療的牙位是否為 FOUR_PHASE_ZONE_AND_PERMANENT_TOOTH
        Given 建立醫師
        Given Stan 24 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 檢查 <IssueTeeth> 牙位，依 FOUR_PHASE_ZONE_AND_PERMANENT_TOOTH 判定是否為核可牙位，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            # 乳牙
            | 91008C       | 51         | DL           | NotPass   |
            | 91008C       | 52         | DL           | NotPass   |
            | 91008C       | 53         | DL           | NotPass   |
            | 91008C       | 54         | DL           | NotPass   |
            | 91008C       | 55         | DL           | NotPass   |
            | 91008C       | 61         | DL           | NotPass   |
            | 91008C       | 62         | DL           | NotPass   |
            | 91008C       | 63         | DL           | NotPass   |
            | 91008C       | 64         | DL           | NotPass   |
            | 91008C       | 65         | DL           | NotPass   |
            | 91008C       | 71         | DL           | NotPass   |
            | 91008C       | 72         | DL           | NotPass   |
            | 91008C       | 73         | DL           | NotPass   |
            | 91008C       | 74         | DL           | NotPass   |
            | 91008C       | 75         | DL           | NotPass   |
            | 91008C       | 81         | DL           | NotPass   |
            | 91008C       | 82         | DL           | NotPass   |
            | 91008C       | 83         | DL           | NotPass   |
            | 91008C       | 84         | DL           | NotPass   |
            | 91008C       | 85         | DL           | NotPass   |
            # 恆牙
            | 91008C       | 11         | DL           | Pass      |
            | 91008C       | 12         | DL           | Pass      |
            | 91008C       | 13         | DL           | Pass      |
            | 91008C       | 14         | DL           | Pass      |
            | 91008C       | 15         | DL           | Pass      |
            | 91008C       | 16         | DL           | Pass      |
            | 91008C       | 17         | DL           | Pass      |
            | 91008C       | 18         | DL           | Pass      |
            | 91008C       | 21         | DL           | Pass      |
            | 91008C       | 22         | DL           | Pass      |
            | 91008C       | 23         | DL           | Pass      |
            | 91008C       | 24         | DL           | Pass      |
            | 91008C       | 25         | DL           | Pass      |
            | 91008C       | 26         | DL           | Pass      |
            | 91008C       | 27         | DL           | Pass      |
            | 91008C       | 28         | DL           | Pass      |
            | 91008C       | 31         | DL           | Pass      |
            | 91008C       | 32         | DL           | Pass      |
            | 91008C       | 33         | DL           | Pass      |
            | 91008C       | 34         | DL           | Pass      |
            | 91008C       | 35         | DL           | Pass      |
            | 91008C       | 36         | DL           | Pass      |
            | 91008C       | 37         | DL           | Pass      |
            | 91008C       | 38         | DL           | Pass      |
            | 91008C       | 41         | DL           | Pass      |
            | 91008C       | 42         | DL           | Pass      |
            | 91008C       | 43         | DL           | Pass      |
            | 91008C       | 44         | DL           | Pass      |
            | 91008C       | 45         | DL           | Pass      |
            | 91008C       | 46         | DL           | Pass      |
            | 91008C       | 47         | DL           | Pass      |
            | 91008C       | 48         | DL           | Pass      |
            # 無牙
            | 91008C       |            | DL           | NotPass   |
            #
            | 91008C       | 19         | DL           | Pass      |
            | 91008C       | 29         | DL           | Pass      |
            | 91008C       | 39         | DL           | Pass      |
            | 91008C       | 49         | DL           | Pass      |
            | 91008C       | 59         | DL           | NotPass   |
            | 91008C       | 69         | DL           | NotPass   |
            | 91008C       | 79         | DL           | NotPass   |
            | 91008C       | 89         | DL           | NotPass   |
            | 91008C       | 99         | DL           | Pass      |
            # 牙位為區域型態
            | 91008C       | FM         | DL           | NotPass   |
            | 91008C       | UR         | DL           | Pass      |
            | 91008C       | UL         | DL           | Pass      |
            | 91008C       | UA         | DL           | NotPass   |
            | 91008C       | UB         | DL           | NotPass   |
            | 91008C       | LL         | DL           | Pass      |
            | 91008C       | LR         | DL           | Pass      |
            | 91008C       | LA         | DL           | NotPass   |
            | 91008C       | LB         | DL           | NotPass   |
            # 非法牙位
            | 91008C       | 00         | DL           | NotPass   |
            | 91008C       | 01         | DL           | NotPass   |
            | 91008C       | 10         | DL           | NotPass   |
            | 91008C       | 56         | DL           | NotPass   |
            | 91008C       | 66         | DL           | NotPass   |
            | 91008C       | 76         | DL           | NotPass   |
            | 91008C       | 86         | DL           | NotPass   |
            | 91008C       | 91         | DL           | NotPass   |
