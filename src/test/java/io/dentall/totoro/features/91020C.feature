@nhi @nhi-91-series @part3
Feature: 91020C 牙菌斑去除照護

    Scenario Outline: 全部檢核成功
        Given 建立醫師
        Given Kelly 11 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 確認診療代碼 <IssueNhiCode> ，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            | 91020C       | FM         | MOB          | Pass      |

    Scenario Outline: （HIS）180天內，不應有 91020C 診療項目
        Given 建立醫師
        Given Kelly 11 歲病人
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
            | 91020C       | FM         | DL           | 179               | 91020C           | FM             | 180    | NotPass   |
            | 91020C       | FM         | DL           | 180               | 91020C           | FM             | 180    | NotPass   |
            | 91020C       | FM         | DL           | 181               | 91020C           | FM             | 180    | Pass      |

    Scenario Outline: （IC）180天內，不應有 91020C 診療項目
        Given 建立醫師
        Given Kelly 11 歲病人
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
            | 91020C       | FM         | DL           | 179             | 91020C         | FM           | 180    | NotPass   |
            | 91020C       | FM         | DL           | 180             | 91020C         | FM           | 180    | NotPass   |
            | 91020C       | FM         | DL           | 181             | 91020C         | FM           | 180    | Pass      |

    Scenario Outline: 病患診療當下得小於 12 歲
        Given 建立醫師
        Given Kelly <PatientAge> 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 病患是否在診療 <IssueNhiCode> 當下年紀未滿 12 歲，確認結果是否為 <PassOrNot>
        Examples:
            | PatientAge | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            | 11         | 91020C       | FM         | DL           | Pass      |
            | 12         | 91020C       | FM         | DL           | NotPass   |
            | 13         | 91020C       | FM         | DL           | NotPass   |

    Scenario Outline: 檢查治療的牙位是否為 FULL_ZONE
        Given 建立醫師
        Given Kelly 11 歲病人
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
            | 91020C       | 51         | DL           | NotPass   |
            | 91020C       | 52         | DL           | NotPass   |
            | 91020C       | 53         | DL           | NotPass   |
            | 91020C       | 54         | DL           | NotPass   |
            | 91020C       | 55         | DL           | NotPass   |
            | 91020C       | 61         | DL           | NotPass   |
            | 91020C       | 62         | DL           | NotPass   |
            | 91020C       | 63         | DL           | NotPass   |
            | 91020C       | 64         | DL           | NotPass   |
            | 91020C       | 65         | DL           | NotPass   |
            | 91020C       | 71         | DL           | NotPass   |
            | 91020C       | 72         | DL           | NotPass   |
            | 91020C       | 73         | DL           | NotPass   |
            | 91020C       | 74         | DL           | NotPass   |
            | 91020C       | 75         | DL           | NotPass   |
            | 91020C       | 81         | DL           | NotPass   |
            | 91020C       | 82         | DL           | NotPass   |
            | 91020C       | 83         | DL           | NotPass   |
            | 91020C       | 84         | DL           | NotPass   |
            | 91020C       | 85         | DL           | NotPass   |
            # 恆牙
            | 91020C       | 11         | DL           | NotPass   |
            | 91020C       | 12         | DL           | NotPass   |
            | 91020C       | 13         | DL           | NotPass   |
            | 91020C       | 14         | DL           | NotPass   |
            | 91020C       | 15         | DL           | NotPass   |
            | 91020C       | 16         | DL           | NotPass   |
            | 91020C       | 17         | DL           | NotPass   |
            | 91020C       | 18         | DL           | NotPass   |
            | 91020C       | 21         | DL           | NotPass   |
            | 91020C       | 22         | DL           | NotPass   |
            | 91020C       | 23         | DL           | NotPass   |
            | 91020C       | 24         | DL           | NotPass   |
            | 91020C       | 25         | DL           | NotPass   |
            | 91020C       | 26         | DL           | NotPass   |
            | 91020C       | 27         | DL           | NotPass   |
            | 91020C       | 28         | DL           | NotPass   |
            | 91020C       | 31         | DL           | NotPass   |
            | 91020C       | 32         | DL           | NotPass   |
            | 91020C       | 33         | DL           | NotPass   |
            | 91020C       | 34         | DL           | NotPass   |
            | 91020C       | 35         | DL           | NotPass   |
            | 91020C       | 36         | DL           | NotPass   |
            | 91020C       | 37         | DL           | NotPass   |
            | 91020C       | 38         | DL           | NotPass   |
            | 91020C       | 41         | DL           | NotPass   |
            | 91020C       | 42         | DL           | NotPass   |
            | 91020C       | 43         | DL           | NotPass   |
            | 91020C       | 44         | DL           | NotPass   |
            | 91020C       | 45         | DL           | NotPass   |
            | 91020C       | 46         | DL           | NotPass   |
            | 91020C       | 47         | DL           | NotPass   |
            | 91020C       | 48         | DL           | NotPass   |
            # 無牙
            | 91020C       |            | DL           | NotPass   |
            #
            | 91020C       | 19         | DL           | NotPass   |
            | 91020C       | 29         | DL           | NotPass   |
            | 91020C       | 39         | DL           | NotPass   |
            | 91020C       | 49         | DL           | NotPass   |
            | 91020C       | 59         | DL           | NotPass   |
            | 91020C       | 69         | DL           | NotPass   |
            | 91020C       | 79         | DL           | NotPass   |
            | 91020C       | 89         | DL           | NotPass   |
            | 91020C       | 99         | DL           | NotPass   |
            # 牙位為區域型態
            | 91020C       | FM         | DL           | Pass      |
            | 91020C       | UR         | DL           | NotPass   |
            | 91020C       | UL         | DL           | NotPass   |
            | 91020C       | UA         | DL           | NotPass   |
            | 91020C       | UB         | DL           | NotPass   |
            | 91020C       | LR         | DL           | NotPass   |
            | 91020C       | LL         | DL           | NotPass   |
            | 91020C       | LA         | DL           | NotPass   |
            | 91020C       | LB         | DL           | NotPass   |
            # 非法牙位
            | 91020C       | 00         | DL           | NotPass   |
            | 91020C       | 01         | DL           | NotPass   |
            | 91020C       | 10         | DL           | NotPass   |
            | 91020C       | 56         | DL           | NotPass   |
            | 91020C       | 66         | DL           | NotPass   |
            | 91020C       | 76         | DL           | NotPass   |
            | 91020C       | 86         | DL           | NotPass   |
            | 91020C       | 91         | DL           | NotPass   |
