@nhi-89-series
Feature: 89006C 覆髓

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
            | 89006C       | 14         | MOB          | Pass      |

    Scenario Outline: （HIS）180天內，同顆牙限申報1次89006C
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
        Then 在 <DayGap> 天中，不應該有同顆牙 <IssueTeeth> 的 <IssueNhiCode> 診療項目，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | DayGap | PastTreatmentDays | TreatmentNhiCode | TreatmentTeeth | PassOrNot |
            # 同一個診療項目、同顆牙
            | 89006C       | 11         | DL           | 180    | 0                 | 89006C           | 11             | NotPass   |
            | 89006C       | 11         | DL           | 180    | 180               | 89006C           | 11             | NotPass   |
            | 89006C       | 11         | DL           | 180    | 181               | 89006C           | 11             | Pass      |
            # 同一個診療項目、不同顆牙
            | 89006C       | 11         | DL           | 180    | 0                 | 89006C           | 49             | Pass      |
            | 89006C       | 11         | DL           | 180    | 180               | 89006C           | 49             | Pass      |
            | 89006C       | 11         | DL           | 180    | 181               | 89006C           | 49             | Pass      |
            # 非同一個診療項目
            | 89006C       | 11         | DL           | 180    | 0                 | 01271C           | 11             | Pass      |
            | 89006C       | 11         | DL           | 180    | 180               | 01271C           | 11             | Pass      |
            | 89006C       | 11         | DL           | 180    | 181               | 01271C           | 11             | Pass      |

    Scenario Outline: （IC）180天內，同顆牙限申報1次89006C
        Given 建立醫師
        Given Wind 24 歲病人
        Given 新增健保醫療:
            | PastDays          | NhiCode          | Teeth          |
            | <PastMedicalDays> | <MedicalNhiCode> | <MedicalTeeth> |
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 在 <DayGap> 天中，不應該有同顆牙 <IssueTeeth> 的 <IssueNhiCode> 診療項目，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | DayGap | PastMedicalDays | MedicalNhiCode | MedicalTeeth | PassOrNot |
            # 同一個診療項目、同顆牙
            | 89006C       | 11         | DL           | 180    | 0               | 89006C         | 11           | NotPass   |
            | 89006C       | 11         | DL           | 180    | 180             | 89006C         | 11           | NotPass   |
            | 89006C       | 11         | DL           | 180    | 181             | 89006C         | 11           | Pass      |
            # 同一個診療項目、不同顆牙
            | 89006C       | 11         | DL           | 180    | 0               | 89006C         | 49           | Pass      |
            | 89006C       | 11         | DL           | 180    | 180             | 89006C         | 49           | Pass      |
            | 89006C       | 11         | DL           | 180    | 181             | 89006C         | 49           | Pass      |
            # 非同一個診療項目
            | 89006C       | 11         | DL           | 180    | 0               | 01271C         | 11           | Pass      |
            | 89006C       | 11         | DL           | 180    | 180             | 01271C         | 11           | Pass      |
            | 89006C       | 11         | DL           | 180    | 181             | 01271C         | 11           | Pass      |

    Scenario Outline: 檢查治療的牙位是否為 PERMANENT_TOOTH
        Given 建立醫師
        Given Wind 24 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 檢查 <IssueTeeth> 牙位，依 PERMANENT_TOOTH 判定是否為核可牙位，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            # 乳牙
            | 89006C       | 51         | DL           | NotPass   |
            | 89006C       | 52         | DL           | NotPass   |
            | 89006C       | 53         | DL           | NotPass   |
            | 89006C       | 54         | DL           | NotPass   |
            | 89006C       | 55         | DL           | NotPass   |
            | 89006C       | 61         | DL           | NotPass   |
            | 89006C       | 62         | DL           | NotPass   |
            | 89006C       | 63         | DL           | NotPass   |
            | 89006C       | 64         | DL           | NotPass   |
            | 89006C       | 65         | DL           | NotPass   |
            | 89006C       | 71         | DL           | NotPass   |
            | 89006C       | 72         | DL           | NotPass   |
            | 89006C       | 73         | DL           | NotPass   |
            | 89006C       | 74         | DL           | NotPass   |
            | 89006C       | 75         | DL           | NotPass   |
            | 89006C       | 81         | DL           | NotPass   |
            | 89006C       | 82         | DL           | NotPass   |
            | 89006C       | 83         | DL           | NotPass   |
            | 89006C       | 84         | DL           | NotPass   |
            | 89006C       | 85         | DL           | NotPass   |
            # 恆牙
            | 89006C       | 11         | DL           | Pass      |
            | 89006C       | 12         | DL           | Pass      |
            | 89006C       | 13         | DL           | Pass      |
            | 89006C       | 14         | DL           | Pass      |
            | 89006C       | 15         | DL           | Pass      |
            | 89006C       | 16         | DL           | Pass      |
            | 89006C       | 17         | DL           | Pass      |
            | 89006C       | 18         | DL           | Pass      |
            | 89006C       | 21         | DL           | Pass      |
            | 89006C       | 22         | DL           | Pass      |
            | 89006C       | 23         | DL           | Pass      |
            | 89006C       | 24         | DL           | Pass      |
            | 89006C       | 25         | DL           | Pass      |
            | 89006C       | 26         | DL           | Pass      |
            | 89006C       | 27         | DL           | Pass      |
            | 89006C       | 28         | DL           | Pass      |
            | 89006C       | 31         | DL           | Pass      |
            | 89006C       | 32         | DL           | Pass      |
            | 89006C       | 33         | DL           | Pass      |
            | 89006C       | 34         | DL           | Pass      |
            | 89006C       | 35         | DL           | Pass      |
            | 89006C       | 36         | DL           | Pass      |
            | 89006C       | 37         | DL           | Pass      |
            | 89006C       | 38         | DL           | Pass      |
            | 89006C       | 41         | DL           | Pass      |
            | 89006C       | 42         | DL           | Pass      |
            | 89006C       | 43         | DL           | Pass      |
            | 89006C       | 44         | DL           | Pass      |
            | 89006C       | 45         | DL           | Pass      |
            | 89006C       | 46         | DL           | Pass      |
            | 89006C       | 47         | DL           | Pass      |
            | 89006C       | 48         | DL           | Pass      |
            # 無牙
            | 89006C       |            | DL           | NotPass   |
            #
            | 89006C       | 19         | DL           | Pass      |
            | 89006C       | 29         | DL           | Pass      |
            | 89006C       | 39         | DL           | Pass      |
            | 89006C       | 49         | DL           | Pass      |
            | 89006C       | 59         | DL           | NotPass   |
            | 89006C       | 69         | DL           | NotPass   |
            | 89006C       | 79         | DL           | NotPass   |
            | 89006C       | 89         | DL           | NotPass   |
            | 89006C       | 99         | DL           | Pass      |
            # 牙位為區域型態
            | 89006C       | FM         | DL           | NotPass   |
            | 89006C       | UR         | DL           | NotPass   |
            | 89006C       | UL         | DL           | NotPass   |
            | 89006C       | UA         | DL           | NotPass   |
            | 89006C       | LR         | DL           | NotPass   |
            | 89006C       | LL         | DL           | NotPass   |
            | 89006C       | LA         | DL           | NotPass   |
            # 非法牙位
            | 89006C       | 00         | DL           | NotPass   |
            | 89006C       | 01         | DL           | NotPass   |
            | 89006C       | 10         | DL           | NotPass   |
            | 89006C       | 56         | DL           | NotPass   |
            | 89006C       | 66         | DL           | NotPass   |
            | 89006C       | 76         | DL           | NotPass   |
            | 89006C       | 86         | DL           | NotPass   |
            | 89006C       | 91         | DL           | NotPass   |
