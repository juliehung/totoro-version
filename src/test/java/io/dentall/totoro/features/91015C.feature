@nhi @nhi-91-series @part2
Feature: 91015C 特定牙周保存治療-全口總齒數9-15顆

    Scenario Outline: 全部檢核成功
        Given 建立醫師
        Given Kelly 24 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 確認診療代碼 <IssueNhiCode> ，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            | 91015C       | FM         | MOB          | Pass      |

    Scenario Outline: （HIS）90天內，不應有 91006C~91008C/91015C/91018C 診療項目
        Given 建立醫師
        Given Kelly 24 歲病人
        Given 在過去第 <PastTreatmentDays> 天，建立預約
        Given 在過去第 <PastTreatmentDays> 天，建立掛號
        Given 在過去第 <PastTreatmentDays> 天，產生診療計畫
        And 新增診療代碼:
            | PastDays            | A72 | A73                | A74              | A75                | A76 | A77 | A78 | A79 |
            | <PastTreatmentDays> | 3   | <TreatmentNhiCode> | <TreatmentTeeth> | <TreatmentSurface> | 0   | 1.0 | 03  |     |
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 檢查 <IssueNhiCode> 診療項目，在病患過去 <GapDay> 天紀錄中，不應包含特定的 <TreatmentNhiCode> 診療代碼，確認結果是否為 <PassOrNot> 且檢查訊息類型為 D1_2
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastTreatmentDays | TreatmentNhiCode | TreatmentTeeth | TreatmentSurface | GapDay | PassOrNot |
            | 91015C       | FM         | MOB          | 89                | 91006C           | FM             | MOB              | 90     | NotPass   |
            | 91015C       | FM         | MOB          | 90                | 91006C           | FM             | MOB              | 90     | NotPass   |
            | 91015C       | FM         | MOB          | 91                | 91006C           | FM             | MOB              | 90     | Pass      |
            | 91015C       | FM         | MOB          | 89                | 91007C           | FM             | MOB              | 90     | NotPass   |
            | 91015C       | FM         | MOB          | 90                | 91007C           | FM             | MOB              | 90     | NotPass   |
            | 91015C       | FM         | MOB          | 91                | 91007C           | FM             | MOB              | 90     | Pass      |
            | 91015C       | FM         | MOB          | 89                | 91008C           | FM             | MOB              | 90     | NotPass   |
            | 91015C       | FM         | MOB          | 90                | 91008C           | FM             | MOB              | 90     | NotPass   |
            | 91015C       | FM         | MOB          | 91                | 91008C           | FM             | MOB              | 90     | Pass      |
            | 91015C       | FM         | MOB          | 89                | 91015C           | FM             | MOB              | 90     | NotPass   |
            | 91015C       | FM         | MOB          | 90                | 91015C           | FM             | MOB              | 90     | NotPass   |
            | 91015C       | FM         | MOB          | 91                | 91015C           | FM             | MOB              | 90     | Pass      |
            | 91015C       | FM         | MOB          | 89                | 91018C           | FM             | MOB              | 90     | NotPass   |
            | 91015C       | FM         | MOB          | 90                | 91018C           | FM             | MOB              | 90     | NotPass   |
            | 91015C       | FM         | MOB          | 91                | 91018C           | FM             | MOB              | 90     | Pass      |

    Scenario Outline: （IC）90天內，不應有 91006C~91008C/91015C/91018C 診療項目
        Given 建立醫師
        Given Kelly 24 歲病人
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
            | 91015C       | FM         | MOB          | 89              | 91006C         | FM           | 90     | NotPass   |
            | 91015C       | FM         | MOB          | 90              | 91006C         | FM           | 90     | NotPass   |
            | 91015C       | FM         | MOB          | 91              | 91006C         | FM           | 90     | Pass      |
            | 91015C       | FM         | MOB          | 89              | 91007C         | FM           | 90     | NotPass   |
            | 91015C       | FM         | MOB          | 90              | 91007C         | FM           | 90     | NotPass   |
            | 91015C       | FM         | MOB          | 91              | 91007C         | FM           | 90     | Pass      |
            | 91015C       | FM         | MOB          | 89              | 91008C         | FM           | 90     | NotPass   |
            | 91015C       | FM         | MOB          | 90              | 91008C         | FM           | 90     | NotPass   |
            | 91015C       | FM         | MOB          | 91              | 91008C         | FM           | 90     | Pass      |
            | 91015C       | FM         | MOB          | 89              | 91015C         | FM           | 90     | NotPass   |
            | 91015C       | FM         | MOB          | 90              | 91015C         | FM           | 90     | NotPass   |
            | 91015C       | FM         | MOB          | 91              | 91015C         | FM           | 90     | Pass      |
            | 91015C       | FM         | MOB          | 89              | 91018C         | FM           | 90     | NotPass   |
            | 91015C       | FM         | MOB          | 90              | 91018C         | FM           | 90     | NotPass   |
            | 91015C       | FM         | MOB          | 91              | 91018C         | FM           | 90     | Pass      |

    Scenario Outline: 提醒須檢附影像
        Given 建立醫師
        Given Kelly 24 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 提醒"須檢附影像"，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            | 91015C       | FM         | MOB          | Pass      |

    Scenario Outline: 提醒需檢附牙菌斑控制紀錄表、牙周病檢查紀錄表
        Given 建立醫師
        Given Kelly 24 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 提醒"需檢附牙菌斑控制紀錄表、牙周病檢查紀錄表"，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            | 91015C       | FM         | MOB          | Pass      |

    Scenario Outline: 檢查治療的牙位是否為 FULL_ZONE
        Given 建立醫師
        Given Kelly 24 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        And 新增診療代碼:
            | A72 | A73    | A74 | A75 | A76 | A77 | A78 | A79 |
            | 3   | 91004C | FM  | MOB | 0   | 1.0 | 03  |     |
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            | 91004C  | FM    | MOB     |                |              |                |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 檢查 <IssueTeeth> 牙位，依 FULL_ZONE 判定是否為核可牙位，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            # 乳牙
            | 91015C       | 51         | DL           | NotPass   |
            | 91015C       | 52         | DL           | NotPass   |
            | 91015C       | 53         | DL           | NotPass   |
            | 91015C       | 54         | DL           | NotPass   |
            | 91015C       | 55         | DL           | NotPass   |
            | 91015C       | 61         | DL           | NotPass   |
            | 91015C       | 62         | DL           | NotPass   |
            | 91015C       | 63         | DL           | NotPass   |
            | 91015C       | 64         | DL           | NotPass   |
            | 91015C       | 65         | DL           | NotPass   |
            | 91015C       | 71         | DL           | NotPass   |
            | 91015C       | 72         | DL           | NotPass   |
            | 91015C       | 73         | DL           | NotPass   |
            | 91015C       | 74         | DL           | NotPass   |
            | 91015C       | 75         | DL           | NotPass   |
            | 91015C       | 81         | DL           | NotPass   |
            | 91015C       | 82         | DL           | NotPass   |
            | 91015C       | 83         | DL           | NotPass   |
            | 91015C       | 84         | DL           | NotPass   |
            | 91015C       | 85         | DL           | NotPass   |
            # 恆牙
            | 91015C       | 11         | DL           | NotPass   |
            | 91015C       | 12         | DL           | NotPass   |
            | 91015C       | 13         | DL           | NotPass   |
            | 91015C       | 14         | DL           | NotPass   |
            | 91015C       | 15         | DL           | NotPass   |
            | 91015C       | 16         | DL           | NotPass   |
            | 91015C       | 17         | DL           | NotPass   |
            | 91015C       | 18         | DL           | NotPass   |
            | 91015C       | 21         | DL           | NotPass   |
            | 91015C       | 22         | DL           | NotPass   |
            | 91015C       | 23         | DL           | NotPass   |
            | 91015C       | 24         | DL           | NotPass   |
            | 91015C       | 25         | DL           | NotPass   |
            | 91015C       | 26         | DL           | NotPass   |
            | 91015C       | 27         | DL           | NotPass   |
            | 91015C       | 28         | DL           | NotPass   |
            | 91015C       | 31         | DL           | NotPass   |
            | 91015C       | 32         | DL           | NotPass   |
            | 91015C       | 33         | DL           | NotPass   |
            | 91015C       | 34         | DL           | NotPass   |
            | 91015C       | 35         | DL           | NotPass   |
            | 91015C       | 36         | DL           | NotPass   |
            | 91015C       | 37         | DL           | NotPass   |
            | 91015C       | 38         | DL           | NotPass   |
            | 91015C       | 41         | DL           | NotPass   |
            | 91015C       | 42         | DL           | NotPass   |
            | 91015C       | 43         | DL           | NotPass   |
            | 91015C       | 44         | DL           | NotPass   |
            | 91015C       | 45         | DL           | NotPass   |
            | 91015C       | 46         | DL           | NotPass   |
            | 91015C       | 47         | DL           | NotPass   |
            | 91015C       | 48         | DL           | NotPass   |
            # 無牙
            | 91015C       |            | DL           | NotPass   |
            #
            | 91015C       | 19         | DL           | NotPass   |
            | 91015C       | 29         | DL           | NotPass   |
            | 91015C       | 39         | DL           | NotPass   |
            | 91015C       | 49         | DL           | NotPass   |
            | 91015C       | 59         | DL           | NotPass   |
            | 91015C       | 69         | DL           | NotPass   |
            | 91015C       | 79         | DL           | NotPass   |
            | 91015C       | 89         | DL           | NotPass   |
            | 91015C       | 99         | DL           | NotPass   |
            # 牙位為區域型態
            | 91015C       | FM         | DL           | Pass      |
            | 91015C       | UR         | DL           | NotPass   |
            | 91015C       | UL         | DL           | NotPass   |
            | 91015C       | UA         | DL           | NotPass   |
            | 91015C       | UB         | DL           | NotPass   |
            | 91015C       | LL         | DL           | NotPass   |
            | 91015C       | LR         | DL           | NotPass   |
            | 91015C       | LA         | DL           | NotPass   |
            | 91015C       | LB         | DL           | NotPass   |
            # 非法牙位
            | 91015C       | 00         | DL           | NotPass   |
            | 91015C       | 01         | DL           | NotPass   |
            | 91015C       | 10         | DL           | NotPass   |
            | 91015C       | 56         | DL           | NotPass   |
            | 91015C       | 66         | DL           | NotPass   |
            | 91015C       | 76         | DL           | NotPass   |
            | 91015C       | 86         | DL           | NotPass   |
            | 91015C       | 91         | DL           | NotPass   |
