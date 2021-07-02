@nhi-90-series
Feature: 90018C 乳牙多根管治療

    Scenario Outline: 全部檢核成功
        Given 建立醫師
        Given Wind 24 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 確認診療代碼 <IssueNhiCode> ，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            | 90018C       | 51         | MOB          | Pass      |

    Scenario Outline: 提醒須檢附影像
        Given 建立醫師
        Given Wind 24 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 提醒"須檢附影像"，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            | 90018C       | 11         | MOB          | Pass      |

    Scenario Outline: （HIS）90天內，不應有 90018C 診療項目
        Given 建立醫師
        Given Kelly 24 歲病人
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
        Then 檢查 <IssueNhiCode> 診療項目，在病患過去 <GapDay> 天紀錄中，不應包含特定的 <TreatmentNhiCode> 診療代碼，確認結果是否為 <PassOrNot> 且檢查訊息類型為 D4_1
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastTreatmentDays | TreatmentNhiCode | TreatmentTeeth | GapDay | PassOrNot |
            | 90018C       | 51         | DL           | 0                 | 90018C           | 51             | 90     | NotPass   |
            | 90018C       | 51         | DL           | 90                | 90018C           | 51             | 90     | NotPass   |
            | 90018C       | 51         | DL           | 91                | 90018C           | 51             | 90     | Pass      |

    Scenario Outline: （IC）90天內，不應有 90018C 診療項目
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
        Then 檢查 <IssueNhiCode> 診療項目，在病患過去 <GapDay> 天紀錄中，不應包含特定的 <MedicalNhiCode> 診療代碼，確認結果是否為 <PassOrNot> 且檢查訊息類型為 D4_1
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastMedicalDays | MedicalNhiCode | MedicalTeeth | GapDay | PassOrNot |
            | 90018C       | 51         | DL           | 0               | 90018C         | 51           | 90     | NotPass   |
            | 90018C       | 51         | DL           | 90              | 90018C         | 51           | 90     | NotPass   |
            | 90018C       | 51         | DL           | 91              | 90018C         | 51           | 90     | Pass      |

    Scenario Outline: （HIS）同牙未曾申報過 92013C~92015C
        Given 建立醫師
        Given Wind 24 歲病人
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
        Then 同牙 <IssueTeeth> 未曾申報過，指定代碼 <TreatmentNhiCode> ，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastTreatmentDays | TreatmentNhiCode | TreatmentTeeth | PassOrNot |
            # 測試同牙
            | 90018C       | 51         | MOB          | 3650              | 92013C           | 51             | NotPass   |
            | 90018C       | 51         | MOB          | 3650              | 92014C           | 51             | NotPass   |
            | 90018C       | 51         | MOB          | 3650              | 92015C           | 51             | NotPass   |
            # 測試不同牙
            | 90018C       | 51         | MOB          | 3650              | 92013C           | 61             | Pass      |
            | 90018C       | 51         | MOB          | 3650              | 92014C           | 61             | Pass      |
            | 90018C       | 51         | MOB          | 3650              | 92015C           | 61             | Pass      |

    Scenario Outline: （IC）同牙未曾申報過 92013C~92015C
        Given 建立醫師
        Given Wind 24 歲病人
        Given 新增健保醫療:
            | PastDays          | NhiCode          | Teeth            |
            | <PastMedicalDays> | <MedicalNhiCode> | <TreatmentTeeth> |
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 同牙 <IssueTeeth> 未曾申報過，指定代碼 <MedicalNhiCode> ，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastMedicalDays | MedicalNhiCode | TreatmentTeeth | PassOrNot |
            # 測試同牙
            | 90018C       | 51         | MOB          | 3650            | 92013C         | 51             | NotPass   |
            | 90018C       | 51         | MOB          | 3650            | 92014C         | 51             | NotPass   |
            | 90018C       | 51         | MOB          | 3650            | 92015C         | 51             | NotPass   |
            # 測試不同牙
            | 90018C       | 51         | MOB          | 3650            | 92013C         | 61             | Pass      |
            | 90018C       | 51         | MOB          | 3650            | 92014C         | 61             | Pass      |
            | 90018C       | 51         | MOB          | 3650            | 92015C         | 61             | Pass      |

    Scenario Outline: 檢查治療的牙位是否為 DECIDUOUS_TOOTH
        Given 建立醫師
        Given Wind 24 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 檢查 <IssueTeeth> 牙位，依 DECIDUOUS_TOOTH 判定是否為核可牙位，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            # 乳牙
            | 90018C       | 51         | DL           | Pass      |
            | 90018C       | 52         | DL           | Pass      |
            | 90018C       | 53         | DL           | Pass      |
            | 90018C       | 54         | DL           | Pass      |
            | 90018C       | 55         | DL           | Pass      |
            | 90018C       | 61         | DL           | Pass      |
            | 90018C       | 62         | DL           | Pass      |
            | 90018C       | 63         | DL           | Pass      |
            | 90018C       | 64         | DL           | Pass      |
            | 90018C       | 65         | DL           | Pass      |
            | 90018C       | 71         | DL           | Pass      |
            | 90018C       | 72         | DL           | Pass      |
            | 90018C       | 73         | DL           | Pass      |
            | 90018C       | 74         | DL           | Pass      |
            | 90018C       | 75         | DL           | Pass      |
            | 90018C       | 81         | DL           | Pass      |
            | 90018C       | 82         | DL           | Pass      |
            | 90018C       | 83         | DL           | Pass      |
            | 90018C       | 84         | DL           | Pass      |
            | 90018C       | 85         | DL           | Pass      |
            # 恆牙
            | 90018C       | 11         | DL           | NotPass   |
            | 90018C       | 12         | DL           | NotPass   |
            | 90018C       | 13         | DL           | NotPass   |
            | 90018C       | 14         | DL           | NotPass   |
            | 90018C       | 15         | DL           | NotPass   |
            | 90018C       | 16         | DL           | NotPass   |
            | 90018C       | 17         | DL           | NotPass   |
            | 90018C       | 18         | DL           | NotPass   |
            | 90018C       | 21         | DL           | NotPass   |
            | 90018C       | 22         | DL           | NotPass   |
            | 90018C       | 23         | DL           | NotPass   |
            | 90018C       | 24         | DL           | NotPass   |
            | 90018C       | 25         | DL           | NotPass   |
            | 90018C       | 26         | DL           | NotPass   |
            | 90018C       | 27         | DL           | NotPass   |
            | 90018C       | 28         | DL           | NotPass   |
            | 90018C       | 31         | DL           | NotPass   |
            | 90018C       | 32         | DL           | NotPass   |
            | 90018C       | 33         | DL           | NotPass   |
            | 90018C       | 34         | DL           | NotPass   |
            | 90018C       | 35         | DL           | NotPass   |
            | 90018C       | 36         | DL           | NotPass   |
            | 90018C       | 37         | DL           | NotPass   |
            | 90018C       | 38         | DL           | NotPass   |
            | 90018C       | 41         | DL           | NotPass   |
            | 90018C       | 42         | DL           | NotPass   |
            | 90018C       | 43         | DL           | NotPass   |
            | 90018C       | 44         | DL           | NotPass   |
            | 90018C       | 45         | DL           | NotPass   |
            | 90018C       | 46         | DL           | NotPass   |
            | 90018C       | 47         | DL           | NotPass   |
            | 90018C       | 48         | DL           | NotPass   |
            # 無牙
            | 90018C       |            | DL           | NotPass   |
            #
            | 90018C       | 19         | DL           | NotPass   |
            | 90018C       | 29         | DL           | NotPass   |
            | 90018C       | 39         | DL           | NotPass   |
            | 90018C       | 49         | DL           | NotPass   |
            | 90018C       | 59         | DL           | NotPass   |
            | 90018C       | 69         | DL           | NotPass   |
            | 90018C       | 79         | DL           | NotPass   |
            | 90018C       | 89         | DL           | NotPass   |
            | 90018C       | 99         | DL           | Pass      |
            # 牙位為區域型態
            | 90018C       | FM         | DL           | NotPass   |
            | 90018C       | UR         | DL           | NotPass   |
            | 90018C       | UL         | DL           | NotPass   |
            | 90018C       | UA         | DL           | NotPass   |
            | 90018C       | LL         | DL           | NotPass   |
            | 90018C       | LR         | DL           | NotPass   |
            | 90018C       | LA         | DL           | NotPass   |
            # 非法牙位
            | 90018C       | 00         | DL           | NotPass   |
            | 90018C       | 01         | DL           | NotPass   |
            | 90018C       | 10         | DL           | NotPass   |
            | 90018C       | 56         | DL           | NotPass   |
            | 90018C       | 66         | DL           | NotPass   |
            | 90018C       | 76         | DL           | NotPass   |
            | 90018C       | 86         | DL           | NotPass   |
            | 90018C       | 91         | DL           | NotPass   |
