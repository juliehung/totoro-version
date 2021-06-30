@nhi-91-series
Feature: 91007C 齒齦下括除術(含牙根整平術)-1/2 顎

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
            | 91007C       | UR         | MOB          | Pass      |

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
        Then （HIS）檢查 <IssueNhiCode> 診療項目，在病患過去 <GapDay> 天紀錄中，不應包含特定的 <TreatmentNhiCode> 診療代碼，確認結果是否為 <PassOrNot> 且檢查訊息類型為 D1_2
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastTreatmentDays | TreatmentNhiCode | TreatmentTeeth | GapDay | PassOrNot |
            | 91007C       | UR         | DL           | 89                | 91015C           | UR             | 90     | NotPass   |
            | 91007C       | UR         | DL           | 90                | 91015C           | UR             | 90     | NotPass   |
            | 91007C       | UR         | DL           | 91                | 91015C           | UR             | 90     | Pass      |
            | 91007C       | UR         | DL           | 89                | 91016C           | UR             | 90     | NotPass   |
            | 91007C       | UR         | DL           | 90                | 91016C           | UR             | 90     | NotPass   |
            | 91007C       | UR         | DL           | 91                | 91016C           | UR             | 90     | Pass      |
            | 91007C       | UR         | DL           | 89                | 91018C           | UR             | 90     | NotPass   |
            | 91007C       | UR         | DL           | 90                | 91018C           | UR             | 90     | NotPass   |
            | 91007C       | UR         | DL           | 91                | 91018C           | UR             | 90     | Pass      |

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
        Then （IC）檢查 <IssueNhiCode> 診療項目，在病患過去 <GapDay> 天紀錄中，不應包含特定的 <MedicalNhiCode> 診療代碼，確認結果是否為 <PassOrNot> 且檢查訊息類型為 D1_2
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastMedicalDays | MedicalNhiCode | MedicalTeeth | GapDay | PassOrNot |
            | 91007C       | UR         | DL           | 89              | 91015C         | UR           | 90     | NotPass   |
            | 91007C       | UR         | DL           | 90              | 91015C         | UR           | 90     | NotPass   |
            | 91007C       | UR         | DL           | 91              | 91015C         | UR           | 90     | Pass      |
            | 91007C       | UR         | DL           | 89              | 91016C         | UR           | 90     | NotPass   |
            | 91007C       | UR         | DL           | 90              | 91016C         | UR           | 90     | NotPass   |
            | 91007C       | UR         | DL           | 91              | 91016C         | UR           | 90     | Pass      |
            | 91007C       | UR         | DL           | 89              | 91018C         | UR           | 90     | NotPass   |
            | 91007C       | UR         | DL           | 90              | 91018C         | UR           | 90     | NotPass   |
            | 91007C       | UR         | DL           | 91              | 91018C         | UR           | 90     | Pass      |

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
            | 91007C       | UR         | MOB          | Pass      |

    Scenario Outline: 檢查治療的牙位是否為 PARTIAL_ZONE
        Given 建立醫師
        Given Stan 24 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 檢查 <IssueTeeth> 牙位，依 PARTIAL_ZONE 判定是否為核可牙位，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            # 乳牙
            | 91007C       | 51         | DL           | NotPass   |
            | 91007C       | 52         | DL           | NotPass   |
            | 91007C       | 53         | DL           | NotPass   |
            | 91007C       | 54         | DL           | NotPass   |
            | 91007C       | 55         | DL           | NotPass   |
            | 91007C       | 61         | DL           | NotPass   |
            | 91007C       | 62         | DL           | NotPass   |
            | 91007C       | 63         | DL           | NotPass   |
            | 91007C       | 64         | DL           | NotPass   |
            | 91007C       | 65         | DL           | NotPass   |
            | 91007C       | 71         | DL           | NotPass   |
            | 91007C       | 72         | DL           | NotPass   |
            | 91007C       | 73         | DL           | NotPass   |
            | 91007C       | 74         | DL           | NotPass   |
            | 91007C       | 75         | DL           | NotPass   |
            | 91007C       | 81         | DL           | NotPass   |
            | 91007C       | 82         | DL           | NotPass   |
            | 91007C       | 83         | DL           | NotPass   |
            | 91007C       | 84         | DL           | NotPass   |
            | 91007C       | 85         | DL           | NotPass   |
            # 恆牙
            | 91007C       | 11         | DL           | NotPass   |
            | 91007C       | 12         | DL           | NotPass   |
            | 91007C       | 13         | DL           | NotPass   |
            | 91007C       | 14         | DL           | NotPass   |
            | 91007C       | 15         | DL           | NotPass   |
            | 91007C       | 16         | DL           | NotPass   |
            | 91007C       | 17         | DL           | NotPass   |
            | 91007C       | 18         | DL           | NotPass   |
            | 91007C       | 21         | DL           | NotPass   |
            | 91007C       | 22         | DL           | NotPass   |
            | 91007C       | 23         | DL           | NotPass   |
            | 91007C       | 24         | DL           | NotPass   |
            | 91007C       | 25         | DL           | NotPass   |
            | 91007C       | 26         | DL           | NotPass   |
            | 91007C       | 27         | DL           | NotPass   |
            | 91007C       | 28         | DL           | NotPass   |
            | 91007C       | 31         | DL           | NotPass   |
            | 91007C       | 32         | DL           | NotPass   |
            | 91007C       | 33         | DL           | NotPass   |
            | 91007C       | 34         | DL           | NotPass   |
            | 91007C       | 35         | DL           | NotPass   |
            | 91007C       | 36         | DL           | NotPass   |
            | 91007C       | 37         | DL           | NotPass   |
            | 91007C       | 38         | DL           | NotPass   |
            | 91007C       | 41         | DL           | NotPass   |
            | 91007C       | 42         | DL           | NotPass   |
            | 91007C       | 43         | DL           | NotPass   |
            | 91007C       | 44         | DL           | NotPass   |
            | 91007C       | 45         | DL           | NotPass   |
            | 91007C       | 46         | DL           | NotPass   |
            | 91007C       | 47         | DL           | NotPass   |
            | 91007C       | 48         | DL           | NotPass   |
            # 無牙
            | 91007C       |            | DL           | NotPass   |
            #
            | 91007C       | 19         | DL           | NotPass   |
            | 91007C       | 29         | DL           | NotPass   |
            | 91007C       | 39         | DL           | NotPass   |
            | 91007C       | 49         | DL           | NotPass   |
            | 91007C       | 59         | DL           | NotPass   |
            | 91007C       | 69         | DL           | NotPass   |
            | 91007C       | 79         | DL           | NotPass   |
            | 91007C       | 89         | DL           | NotPass   |
            | 91007C       | 99         | DL           | NotPass   |
            # 牙位為區域型態
            | 91007C       | FM         | DL           | NotPass   |
            | 91007C       | UR         | DL           | Pass      |
            | 91007C       | UL         | DL           | Pass      |
            | 91007C       | UA         | DL           | Pass      |
            | 91007C       | UB         | DL           | NotPass   |
            | 91007C       | LL         | DL           | Pass      |
            | 91007C       | LR         | DL           | Pass      |
            | 91007C       | LA         | DL           | Pass      |
            | 91007C       | LB         | DL           | NotPass   |
            # 非法牙位
            | 91007C       | 00         | DL           | NotPass   |
            | 91007C       | 01         | DL           | NotPass   |
            | 91007C       | 10         | DL           | NotPass   |
            | 91007C       | 56         | DL           | NotPass   |
            | 91007C       | 66         | DL           | NotPass   |
            | 91007C       | 76         | DL           | NotPass   |
            | 91007C       | 86         | DL           | NotPass   |
            | 91007C       | 91         | DL           | NotPass   |
