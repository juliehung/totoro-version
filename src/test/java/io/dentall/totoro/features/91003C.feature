@nhi @nhi-91-series
Feature: 91003C 牙結石清除－局部

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
            | 91003C       | UR         | MOB          | Pass      |

    Scenario Outline: （HIS）180天內牙位每個象限只能申報1次 91003C 健保代碼
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
        Then 在 180天 的紀錄中，牙位 <IssueTeeth> 在同一象限中，最多只能申報 1 次 <IssueNhiCode> 健保代碼，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastTreatmentDays | TreatmentNhiCode | TreatmentTeeth | PassOrNot |
            # 象限一
            | 91003C       | UR         | MOB          | 179               | 91003C           | UR             | NotPass   |
            | 91003C       | UR         | MOB          | 180               | 91003C           | UR             | NotPass   |
            | 91003C       | UR         | MOB          | 181               | 91003C           | UR             | Pass      |
            | 91003C       | UR         | MOB          | 179               | 91003C           | UL             | Pass      |
            | 91003C       | UR         | MOB          | 180               | 91003C           | UL             | Pass      |
            | 91003C       | UR         | MOB          | 181               | 91003C           | UL             | Pass      |
            # 象限二
            | 91003C       | UL         | MOB          | 179               | 91003C           | UL             | NotPass   |
            | 91003C       | UL         | MOB          | 180               | 91003C           | UL             | NotPass   |
            | 91003C       | UL         | MOB          | 181               | 91003C           | UL             | Pass      |
            | 91003C       | UL         | MOB          | 179               | 91003C           | UR             | Pass      |
            | 91003C       | UL         | MOB          | 180               | 91003C           | UR             | Pass      |
            | 91003C       | UL         | MOB          | 181               | 91003C           | UR             | Pass      |
            # 象限三
            | 91003C       | LL         | MOB          | 179               | 91003C           | LL             | NotPass   |
            | 91003C       | LL         | MOB          | 180               | 91003C           | LL             | NotPass   |
            | 91003C       | LL         | MOB          | 181               | 91003C           | LL             | Pass      |
            | 91003C       | LL         | MOB          | 179               | 91003C           | LR             | Pass      |
            | 91003C       | LL         | MOB          | 180               | 91003C           | LR             | Pass      |
            | 91003C       | LL         | MOB          | 181               | 91003C           | LR             | Pass      |
            # 象限四
            | 91003C       | LR         | MOB          | 179               | 91003C           | LR             | NotPass   |
            | 91003C       | LR         | MOB          | 180               | 91003C           | LR             | NotPass   |
            | 91003C       | LR         | MOB          | 181               | 91003C           | LR             | Pass      |
            | 91003C       | LR         | MOB          | 179               | 91003C           | LL             | Pass      |
            | 91003C       | LR         | MOB          | 180               | 91003C           | LL             | Pass      |
            | 91003C       | LR         | MOB          | 181               | 91003C           | LL             | Pass      |

    Scenario Outline: （IC）180天內牙位每個象限只能申報1次 91003C 健保代碼
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
        Then 在 180天 的紀錄中，牙位 <IssueTeeth> 在同一象限中，最多只能申報 1 次 <IssueNhiCode> 健保代碼，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastMedicalDays | MedicalNhiCode | MedicalTeeth | PassOrNot |
            # 象限一
            | 91003C       | UR         | MOB          | 179             | 91003C         | UR           | NotPass   |
            | 91003C       | UR         | MOB          | 180             | 91003C         | UR           | NotPass   |
            | 91003C       | UR         | MOB          | 181             | 91003C         | UR           | Pass      |
            | 91003C       | UR         | MOB          | 179             | 91003C         | UL           | Pass      |
            | 91003C       | UR         | MOB          | 180             | 91003C         | UL           | Pass      |
            | 91003C       | UR         | MOB          | 181             | 91003C         | UL           | Pass      |
            # 象限二
            | 91003C       | UL         | MOB          | 179             | 91003C         | UL           | NotPass   |
            | 91003C       | UL         | MOB          | 180             | 91003C         | UL           | NotPass   |
            | 91003C       | UL         | MOB          | 181             | 91003C         | UL           | Pass      |
            | 91003C       | UL         | MOB          | 179             | 91003C         | UR           | Pass      |
            | 91003C       | UL         | MOB          | 180             | 91003C         | UR           | Pass      |
            | 91003C       | UL         | MOB          | 181             | 91003C         | UR           | Pass      |
            # 象限三
            | 91003C       | LL         | MOB          | 179             | 91003C         | LL           | NotPass   |
            | 91003C       | LL         | MOB          | 180             | 91003C         | LL           | NotPass   |
            | 91003C       | LL         | MOB          | 181             | 91003C         | LL           | Pass      |
            | 91003C       | LL         | MOB          | 179             | 91003C         | LR           | Pass      |
            | 91003C       | LL         | MOB          | 180             | 91003C         | LR           | Pass      |
            | 91003C       | LL         | MOB          | 181             | 91003C         | LR           | Pass      |
            # 象限四
            | 91003C       | LR         | MOB          | 179             | 91003C         | LR           | NotPass   |
            | 91003C       | LR         | MOB          | 180             | 91003C         | LR           | NotPass   |
            | 91003C       | LR         | MOB          | 181             | 91003C         | LR           | Pass      |
            | 91003C       | LR         | MOB          | 179             | 91003C         | LL           | Pass      |
            | 91003C       | LR         | MOB          | 180             | 91003C         | LL           | Pass      |
            | 91003C       | LR         | MOB          | 181             | 91003C         | LL           | Pass      |

    Scenario Outline: 檢查治療的牙位是否為 FOUR_PHASE_ZONE
        Given 建立醫師
        Given Stan 24 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 檢查 <IssueTeeth> 牙位，依 FOUR_PHASE_ZONE 判定是否為核可牙位，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            # 乳牙
            | 91003C       | 51         | DL           | NotPass   |
            | 91003C       | 52         | DL           | NotPass   |
            | 91003C       | 53         | DL           | NotPass   |
            | 91003C       | 54         | DL           | NotPass   |
            | 91003C       | 55         | DL           | NotPass   |
            | 91003C       | 61         | DL           | NotPass   |
            | 91003C       | 62         | DL           | NotPass   |
            | 91003C       | 63         | DL           | NotPass   |
            | 91003C       | 64         | DL           | NotPass   |
            | 91003C       | 65         | DL           | NotPass   |
            | 91003C       | 71         | DL           | NotPass   |
            | 91003C       | 72         | DL           | NotPass   |
            | 91003C       | 73         | DL           | NotPass   |
            | 91003C       | 74         | DL           | NotPass   |
            | 91003C       | 75         | DL           | NotPass   |
            | 91003C       | 81         | DL           | NotPass   |
            | 91003C       | 82         | DL           | NotPass   |
            | 91003C       | 83         | DL           | NotPass   |
            | 91003C       | 84         | DL           | NotPass   |
            | 91003C       | 85         | DL           | NotPass   |
            # 恆牙
            | 91003C       | 11         | DL           | NotPass   |
            | 91003C       | 12         | DL           | NotPass   |
            | 91003C       | 13         | DL           | NotPass   |
            | 91003C       | 14         | DL           | NotPass   |
            | 91003C       | 15         | DL           | NotPass   |
            | 91003C       | 16         | DL           | NotPass   |
            | 91003C       | 17         | DL           | NotPass   |
            | 91003C       | 18         | DL           | NotPass   |
            | 91003C       | 21         | DL           | NotPass   |
            | 91003C       | 22         | DL           | NotPass   |
            | 91003C       | 23         | DL           | NotPass   |
            | 91003C       | 24         | DL           | NotPass   |
            | 91003C       | 25         | DL           | NotPass   |
            | 91003C       | 26         | DL           | NotPass   |
            | 91003C       | 27         | DL           | NotPass   |
            | 91003C       | 28         | DL           | NotPass   |
            | 91003C       | 31         | DL           | NotPass   |
            | 91003C       | 32         | DL           | NotPass   |
            | 91003C       | 33         | DL           | NotPass   |
            | 91003C       | 34         | DL           | NotPass   |
            | 91003C       | 35         | DL           | NotPass   |
            | 91003C       | 36         | DL           | NotPass   |
            | 91003C       | 37         | DL           | NotPass   |
            | 91003C       | 38         | DL           | NotPass   |
            | 91003C       | 41         | DL           | NotPass   |
            | 91003C       | 42         | DL           | NotPass   |
            | 91003C       | 43         | DL           | NotPass   |
            | 91003C       | 44         | DL           | NotPass   |
            | 91003C       | 45         | DL           | NotPass   |
            | 91003C       | 46         | DL           | NotPass   |
            | 91003C       | 47         | DL           | NotPass   |
            | 91003C       | 48         | DL           | NotPass   |
            # 無牙
            | 91003C       |            | DL           | NotPass   |
            #
            | 91003C       | 19         | DL           | NotPass   |
            | 91003C       | 29         | DL           | NotPass   |
            | 91003C       | 39         | DL           | NotPass   |
            | 91003C       | 49         | DL           | NotPass   |
            | 91003C       | 59         | DL           | NotPass   |
            | 91003C       | 69         | DL           | NotPass   |
            | 91003C       | 79         | DL           | NotPass   |
            | 91003C       | 89         | DL           | NotPass   |
            | 91003C       | 99         | DL           | NotPass   |
            # 牙位為區域型態
            | 91003C       | FM         | DL           | NotPass   |
            | 91003C       | UR         | DL           | Pass      |
            | 91003C       | UL         | DL           | Pass      |
            | 91003C       | UA         | DL           | NotPass   |
            | 91003C       | UB         | DL           | NotPass   |
            | 91003C       | LL         | DL           | Pass      |
            | 91003C       | LR         | DL           | Pass      |
            | 91003C       | LA         | DL           | NotPass   |
            | 91003C       | LB         | DL           | NotPass   |
            # 非法牙位
            | 91003C       | 00         | DL           | NotPass   |
            | 91003C       | 01         | DL           | NotPass   |
            | 91003C       | 10         | DL           | NotPass   |
            | 91003C       | 56         | DL           | NotPass   |
            | 91003C       | 66         | DL           | NotPass   |
            | 91003C       | 76         | DL           | NotPass   |
            | 91003C       | 86         | DL           | NotPass   |
            | 91003C       | 91         | DL           | NotPass   |

    Scenario Outline: 提醒12 歲以下，須檢附影像
        Given 建立醫師
        Given Stan <Age> 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 12 歲以下，提醒"須檢附影像"，確認結果是否為 <PassOrNot>，且檢核訊息應 <ShowOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | Age | PassOrNot | ShowOrNot |
            | 91003C       | UR         | MOB          | 11  | Pass      | 顯示        |
            | 91003C       | UR         | MOB          | 12  | Pass      | 不顯示       |
            | 91003C       | UR         | MOB          | 13  | Pass      | 不顯示       |

    Scenario Outline: （HIS）90天內，不應有 91003C/91005C/91015C~91018C/91103C~91105C 診療項目
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
            | 91003C       | UR         | DL           | 89                | 91003C           | UL             | 90     | NotPass   |
            | 91003C       | UR         | DL           | 90                | 91003C           | UL             | 90     | NotPass   |
            | 91003C       | UR         | DL           | 91                | 91003C           | UL             | 90     | Pass      |
            | 91003C       | UR         | DL           | 89                | 91005C           | UL             | 90     | NotPass   |
            | 91003C       | UR         | DL           | 90                | 91005C           | UL             | 90     | NotPass   |
            | 91003C       | UR         | DL           | 91                | 91005C           | UL             | 90     | Pass      |
            | 91003C       | UR         | DL           | 89                | 91015C           | UL             | 90     | NotPass   |
            | 91003C       | UR         | DL           | 90                | 91015C           | UL             | 90     | NotPass   |
            | 91003C       | UR         | DL           | 91                | 91015C           | UL             | 90     | Pass      |
            | 91003C       | UR         | DL           | 89                | 91016C           | UL             | 90     | NotPass   |
            | 91003C       | UR         | DL           | 90                | 91016C           | UL             | 90     | NotPass   |
            | 91003C       | UR         | DL           | 91                | 91016C           | UL             | 90     | Pass      |
            | 91003C       | UR         | DL           | 89                | 91017C           | UL             | 90     | NotPass   |
            | 91003C       | UR         | DL           | 90                | 91017C           | UL             | 90     | NotPass   |
            | 91003C       | UR         | DL           | 91                | 91017C           | UL             | 90     | Pass      |
            | 91003C       | UR         | DL           | 89                | 91018C           | UL             | 90     | NotPass   |
            | 91003C       | UR         | DL           | 90                | 91018C           | UL             | 90     | NotPass   |
            | 91003C       | UR         | DL           | 91                | 91018C           | UL             | 90     | Pass      |
            | 91003C       | UR         | DL           | 89                | 91103C           | UL             | 90     | NotPass   |
            | 91003C       | UR         | DL           | 90                | 91103C           | UL             | 90     | NotPass   |
            | 91003C       | UR         | DL           | 91                | 91103C           | UL             | 90     | Pass      |
            | 91003C       | UR         | DL           | 89                | 91104C           | UL             | 90     | NotPass   |
            | 91003C       | UR         | DL           | 90                | 91104C           | UL             | 90     | NotPass   |
            | 91003C       | UR         | DL           | 91                | 91104C           | UL             | 90     | Pass      |

    Scenario Outline: （IC）90天內，不應有 91003C/91005C/91015C~91018C/91103C~91105C 診療項目
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
            | 91003C       | UR         | DL           | 89              | 91003C         | UL           | 90     | NotPass   |
            | 91003C       | UR         | DL           | 90              | 91003C         | UL           | 90     | NotPass   |
            | 91003C       | UR         | DL           | 91              | 91003C         | UL           | 90     | Pass      |
            | 91003C       | UR         | DL           | 89              | 91005C         | UL           | 90     | NotPass   |
            | 91003C       | UR         | DL           | 90              | 91005C         | UL           | 90     | NotPass   |
            | 91003C       | UR         | DL           | 91              | 91005C         | UL           | 90     | Pass      |
            | 91003C       | UR         | DL           | 89              | 91015C         | UL           | 90     | NotPass   |
            | 91003C       | UR         | DL           | 90              | 91015C         | UL           | 90     | NotPass   |
            | 91003C       | UR         | DL           | 91              | 91015C         | UL           | 90     | Pass      |
            | 91003C       | UR         | DL           | 89              | 91016C         | UL           | 90     | NotPass   |
            | 91003C       | UR         | DL           | 90              | 91016C         | UL           | 90     | NotPass   |
            | 91003C       | UR         | DL           | 91              | 91016C         | UL           | 90     | Pass      |
            | 91003C       | UR         | DL           | 89              | 91017C         | UL           | 90     | NotPass   |
            | 91003C       | UR         | DL           | 90              | 91017C         | UL           | 90     | NotPass   |
            | 91003C       | UR         | DL           | 91              | 91017C         | UL           | 90     | Pass      |
            | 91003C       | UR         | DL           | 89              | 91018C         | UL           | 90     | NotPass   |
            | 91003C       | UR         | DL           | 90              | 91018C         | UL           | 90     | NotPass   |
            | 91003C       | UR         | DL           | 91              | 91018C         | UL           | 90     | Pass      |
            | 91003C       | UR         | DL           | 89              | 91103C         | UL           | 90     | NotPass   |
            | 91003C       | UR         | DL           | 90              | 91103C         | UL           | 90     | NotPass   |
            | 91003C       | UR         | DL           | 91              | 91103C         | UL           | 90     | Pass      |
            | 91003C       | UR         | DL           | 89              | 91104C         | UL           | 90     | NotPass   |
            | 91003C       | UR         | DL           | 90              | 91104C         | UL           | 90     | NotPass   |
            | 91003C       | UR         | DL           | 91              | 91104C         | UL           | 90     | Pass      |
