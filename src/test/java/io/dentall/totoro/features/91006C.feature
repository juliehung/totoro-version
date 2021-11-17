@nhi @nhi-91-series @part1
Feature: 91006C 齒齦下括除術(含牙根整平術)-全口

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
            | 91006C       | FM         | MOB          | Pass      |

    Scenario Outline: （HIS）90天內，不應有 91006C~91008/91015C/91016C/91018C/91089C 診療項目
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
            | 91006C       | FM         | DL           | 89                | 91006C           | FM             | 90     | NotPass   |
            | 91006C       | FM         | DL           | 90                | 91006C           | FM             | 90     | NotPass   |
            | 91006C       | FM         | DL           | 91                | 91006C           | FM             | 90     | Pass      |
            | 91006C       | FM         | DL           | 89                | 91007C           | FM             | 90     | NotPass   |
            | 91006C       | FM         | DL           | 90                | 91007C           | FM             | 90     | NotPass   |
            | 91006C       | FM         | DL           | 91                | 91007C           | FM             | 90     | Pass      |
            | 91006C       | FM         | DL           | 89                | 91008C           | FM             | 90     | NotPass   |
            | 91006C       | FM         | DL           | 90                | 91008C           | FM             | 90     | NotPass   |
            | 91006C       | FM         | DL           | 91                | 91008C           | FM             | 90     | Pass      |
            | 91006C       | FM         | DL           | 89                | 91015C           | FM             | 90     | NotPass   |
            | 91006C       | FM         | DL           | 90                | 91015C           | FM             | 90     | NotPass   |
            | 91006C       | FM         | DL           | 91                | 91015C           | FM             | 90     | Pass      |
            | 91006C       | FM         | DL           | 89                | 91016C           | FM             | 90     | NotPass   |
            | 91006C       | FM         | DL           | 90                | 91016C           | FM             | 90     | NotPass   |
            | 91006C       | FM         | DL           | 91                | 91016C           | FM             | 90     | Pass      |
            | 91006C       | FM         | DL           | 89                | 91018C           | FM             | 90     | NotPass   |
            | 91006C       | FM         | DL           | 90                | 91018C           | FM             | 90     | NotPass   |
            | 91006C       | FM         | DL           | 91                | 91018C           | FM             | 90     | Pass      |
            | 91006C       | FM         | DL           | 89                | 91089C           | FM             | 90     | NotPass   |
            | 91006C       | FM         | DL           | 90                | 91089C           | FM             | 90     | NotPass   |
            | 91006C       | FM         | DL           | 91                | 91089C           | FM             | 90     | Pass      |

    Scenario Outline: （IC）90天內，不應有 91006C~91008/91015C/91016C/91018C/91089C 診療項目
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
            | 91006C       | FM         | DL           | 89              | 91006C         | FM           | 90     | NotPass   |
            | 91006C       | FM         | DL           | 90              | 91006C         | FM           | 90     | NotPass   |
            | 91006C       | FM         | DL           | 91              | 91006C         | FM           | 90     | Pass      |
            | 91006C       | FM         | DL           | 89              | 91007C         | FM           | 90     | NotPass   |
            | 91006C       | FM         | DL           | 90              | 91007C         | FM           | 90     | NotPass   |
            | 91006C       | FM         | DL           | 91              | 91007C         | FM           | 90     | Pass      |
            | 91006C       | FM         | DL           | 89              | 91008C         | FM           | 90     | NotPass   |
            | 91006C       | FM         | DL           | 90              | 91008C         | FM           | 90     | NotPass   |
            | 91006C       | FM         | DL           | 91              | 91008C         | FM           | 90     | Pass      |
            | 91006C       | FM         | DL           | 89              | 91015C         | FM           | 90     | NotPass   |
            | 91006C       | FM         | DL           | 90              | 91015C         | FM           | 90     | NotPass   |
            | 91006C       | FM         | DL           | 91              | 91015C         | FM           | 90     | Pass      |
            | 91006C       | FM         | DL           | 89              | 91016C         | FM           | 90     | NotPass   |
            | 91006C       | FM         | DL           | 90              | 91016C         | FM           | 90     | NotPass   |
            | 91006C       | FM         | DL           | 91              | 91016C         | FM           | 90     | Pass      |
            | 91006C       | FM         | DL           | 89              | 91018C         | FM           | 90     | NotPass   |
            | 91006C       | FM         | DL           | 90              | 91018C         | FM           | 90     | NotPass   |
            | 91006C       | FM         | DL           | 91              | 91018C         | FM           | 90     | Pass      |
            | 91006C       | FM         | DL           | 89              | 91089C         | FM           | 90     | NotPass   |
            | 91006C       | FM         | DL           | 90              | 91089C         | FM           | 90     | NotPass   |
            | 91006C       | FM         | DL           | 91              | 91089C         | FM           | 90     | Pass      |

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
            | 91006C       | FM         | MOB          | Pass      |

    Scenario Outline: 檢查治療的牙位是否為 FULL_ZONE
        Given 建立醫師
        Given Stan 24 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 檢查 <IssueTeeth> 牙位，依 FULL_ZONE 判定是否為核可牙位，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            # 乳牙
            | 91006C       | 51         | DL           | NotPass   |
            | 91006C       | 52         | DL           | NotPass   |
            | 91006C       | 53         | DL           | NotPass   |
            | 91006C       | 54         | DL           | NotPass   |
            | 91006C       | 55         | DL           | NotPass   |
            | 91006C       | 61         | DL           | NotPass   |
            | 91006C       | 62         | DL           | NotPass   |
            | 91006C       | 63         | DL           | NotPass   |
            | 91006C       | 64         | DL           | NotPass   |
            | 91006C       | 65         | DL           | NotPass   |
            | 91006C       | 71         | DL           | NotPass   |
            | 91006C       | 72         | DL           | NotPass   |
            | 91006C       | 73         | DL           | NotPass   |
            | 91006C       | 74         | DL           | NotPass   |
            | 91006C       | 75         | DL           | NotPass   |
            | 91006C       | 81         | DL           | NotPass   |
            | 91006C       | 82         | DL           | NotPass   |
            | 91006C       | 83         | DL           | NotPass   |
            | 91006C       | 84         | DL           | NotPass   |
            | 91006C       | 85         | DL           | NotPass   |
            # 恆牙
            | 91006C       | 11         | DL           | NotPass   |
            | 91006C       | 12         | DL           | NotPass   |
            | 91006C       | 13         | DL           | NotPass   |
            | 91006C       | 14         | DL           | NotPass   |
            | 91006C       | 15         | DL           | NotPass   |
            | 91006C       | 16         | DL           | NotPass   |
            | 91006C       | 17         | DL           | NotPass   |
            | 91006C       | 18         | DL           | NotPass   |
            | 91006C       | 21         | DL           | NotPass   |
            | 91006C       | 22         | DL           | NotPass   |
            | 91006C       | 23         | DL           | NotPass   |
            | 91006C       | 24         | DL           | NotPass   |
            | 91006C       | 25         | DL           | NotPass   |
            | 91006C       | 26         | DL           | NotPass   |
            | 91006C       | 27         | DL           | NotPass   |
            | 91006C       | 28         | DL           | NotPass   |
            | 91006C       | 31         | DL           | NotPass   |
            | 91006C       | 32         | DL           | NotPass   |
            | 91006C       | 33         | DL           | NotPass   |
            | 91006C       | 34         | DL           | NotPass   |
            | 91006C       | 35         | DL           | NotPass   |
            | 91006C       | 36         | DL           | NotPass   |
            | 91006C       | 37         | DL           | NotPass   |
            | 91006C       | 38         | DL           | NotPass   |
            | 91006C       | 41         | DL           | NotPass   |
            | 91006C       | 42         | DL           | NotPass   |
            | 91006C       | 43         | DL           | NotPass   |
            | 91006C       | 44         | DL           | NotPass   |
            | 91006C       | 45         | DL           | NotPass   |
            | 91006C       | 46         | DL           | NotPass   |
            | 91006C       | 47         | DL           | NotPass   |
            | 91006C       | 48         | DL           | NotPass   |
            # 無牙
            | 91006C       |            | DL           | NotPass   |
            #
            | 91006C       | 19         | DL           | NotPass   |
            | 91006C       | 29         | DL           | NotPass   |
            | 91006C       | 39         | DL           | NotPass   |
            | 91006C       | 49         | DL           | NotPass   |
            | 91006C       | 59         | DL           | NotPass   |
            | 91006C       | 69         | DL           | NotPass   |
            | 91006C       | 79         | DL           | NotPass   |
            | 91006C       | 89         | DL           | NotPass   |
            | 91006C       | 99         | DL           | NotPass   |
            # 牙位為區域型態
            | 91006C       | FM         | DL           | Pass      |
            | 91006C       | UR         | DL           | NotPass   |
            | 91006C       | UL         | DL           | NotPass   |
            | 91006C       | UA         | DL           | NotPass   |
            | 91006C       | UB         | DL           | NotPass   |
            | 91006C       | LL         | DL           | NotPass   |
            | 91006C       | LR         | DL           | NotPass   |
            | 91006C       | LA         | DL           | NotPass   |
            | 91006C       | LB         | DL           | NotPass   |
            # 非法牙位
            | 91006C       | 00         | DL           | NotPass   |
            | 91006C       | 01         | DL           | NotPass   |
            | 91006C       | 10         | DL           | NotPass   |
            | 91006C       | 56         | DL           | NotPass   |
            | 91006C       | 66         | DL           | NotPass   |
            | 91006C       | 76         | DL           | NotPass   |
            | 91006C       | 86         | DL           | NotPass   |
            | 91006C       | 91         | DL           | NotPass   |
