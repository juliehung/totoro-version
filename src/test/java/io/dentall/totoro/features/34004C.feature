Feature: 34004C 齒顎全景 X光片攝影

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
            | 34004C       | 11         | MO           | Pass      |

    Scenario Outline: （HIS）未曾申報過 34004C 代碼
        Given 建立醫師
        Given Scott 24 歲病人
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
        Then （HIS）任意時間點未曾申報過指定代碼 <TreatmentNhiCode>，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastTreatmentDays | TreatmentNhiCode | TreatmentTeeth | TreatmentSurface | PassOrNot |
            | 34004C       | 11         | MOB          | 30                | 34004C           | 11             | MOB              | NotPass   |
            | 34004C       | 11         | MOB          | 30                | 01271C           | 11             | MOB              | Pass      |

    Scenario Outline: （IC）未曾申報過 34004C 代碼
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
        Then （IC）任意時間點未曾申報過指定代碼 <MedicalNhiCode>，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastMedicalDays | MedicalNhiCode | MedicalTeeth | PassOrNot |
            | 34004C       | 11         | MOB          | 30              | 34004C         | 11           | NotPass   |
            | 34004C       | 11         | MOB          | 30              | 01271C         | 11           | Pass      |

    Scenario Outline: 同一處置單裡應只有單項 34004C 代碼
        Given 建立醫師
        Given Scott 24 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        And 新增診療代碼:
            | A72 | A73            | A74          | A75            | A76 | A77 | A78 | A79 |
            | 3   | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode        | Teeth        | Surface        | NewNhiCode     | NewTeeth     | NewSurface     |
            | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |                |              |                |
            |                |              |                | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 檢查同一處置單，是否沒有健保定義的 <IssueNhiCode> 重複診療衝突，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            | 34004C       | 11         | MOB          | NotPass   |

    Scenario Outline: 檢查治療的牙位是否為 VALIDATED_ALL
        Given 建立醫師
        Given Scott 24 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 檢查 <IssueTeeth> 牙位，依 VALIDATED_ALL 判定是否為核可牙位，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            # 後恆牙
            | 34004C       | 14         | DL           | Pass      |
            | 34004C       | 15         | DL           | Pass      |
            | 34004C       | 16         | DL           | Pass      |
            | 34004C       | 17         | DL           | Pass      |
            | 34004C       | 18         | DL           | Pass      |
            | 34004C       | 24         | DL           | Pass      |
            | 34004C       | 25         | DL           | Pass      |
            | 34004C       | 26         | DL           | Pass      |
            | 34004C       | 27         | DL           | Pass      |
            | 34004C       | 28         | DL           | Pass      |
            | 34004C       | 34         | DL           | Pass      |
            | 34004C       | 35         | DL           | Pass      |
            | 34004C       | 36         | DL           | Pass      |
            | 34004C       | 37         | DL           | Pass      |
            | 34004C       | 38         | DL           | Pass      |
            | 34004C       | 44         | DL           | Pass      |
            | 34004C       | 45         | DL           | Pass      |
            | 34004C       | 46         | DL           | Pass      |
            | 34004C       | 47         | DL           | Pass      |
            | 34004C       | 48         | DL           | Pass      |
            # 後乳牙
            | 34004C       | 54         | DL           | Pass      |
            | 34004C       | 55         | DL           | Pass      |
            | 34004C       | 64         | DL           | Pass      |
            | 34004C       | 65         | DL           | Pass      |
            | 34004C       | 74         | DL           | Pass      |
            | 34004C       | 75         | DL           | Pass      |
            | 34004C       | 84         | DL           | Pass      |
            | 34004C       | 85         | DL           | Pass      |
            # 前恆牙
            | 34004C       | 11         | DL           | Pass      |
            | 34004C       | 12         | DL           | Pass      |
            | 34004C       | 13         | DL           | Pass      |
            | 34004C       | 21         | DL           | Pass      |
            | 34004C       | 22         | DL           | Pass      |
            | 34004C       | 23         | DL           | Pass      |
            | 34004C       | 31         | DL           | Pass      |
            | 34004C       | 32         | DL           | Pass      |
            | 34004C       | 33         | DL           | Pass      |
            | 34004C       | 41         | DL           | Pass      |
            | 34004C       | 42         | DL           | Pass      |
            | 34004C       | 43         | DL           | Pass      |
            # 前乳牙
            | 34004C       | 51         | DL           | Pass      |
            | 34004C       | 52         | DL           | Pass      |
            | 34004C       | 53         | DL           | Pass      |
            | 34004C       | 61         | DL           | Pass      |
            | 34004C       | 62         | DL           | Pass      |
            | 34004C       | 63         | DL           | Pass      |
            | 34004C       | 71         | DL           | Pass      |
            | 34004C       | 72         | DL           | Pass      |
            | 34004C       | 73         | DL           | Pass      |
            | 34004C       | 81         | DL           | Pass      |
            | 34004C       | 82         | DL           | Pass      |
            | 34004C       | 83         | DL           | Pass      |
            # 無牙
            | 34004C       |            | DL           | NotPass   |
            #
            | 34004C       | 19         | DL           | Pass      |
            | 34004C       | 29         | DL           | Pass      |
            | 34004C       | 39         | DL           | Pass      |
            | 34004C       | 49         | DL           | Pass      |
            | 34004C       | 59         | DL           | Pass      |
            | 34004C       | 69         | DL           | Pass      |
            | 34004C       | 79         | DL           | Pass      |
            | 34004C       | 89         | DL           | Pass      |
            | 34004C       | 99         | DL           | Pass      |
            # 牙位為區域型態
            | 34004C       | FM         | DL           | Pass      |
            | 34004C       | UR         | DL           | Pass      |
            | 34004C       | UL         | DL           | Pass      |
            | 34004C       | LL         | DL           | Pass      |
            | 34004C       | LR         | DL           | Pass      |
            | 34004C       | UA         | DL           | Pass      |
            | 34004C       | LA         | DL           | Pass      |
            # 非法牙位
            | 34004C       | 00         | DL           | NotPass   |
            | 34004C       | 01         | DL           | NotPass   |
            | 34004C       | 10         | DL           | NotPass   |
            | 34004C       | 56         | DL           | NotPass   |
            | 34004C       | 66         | DL           | NotPass   |
            | 34004C       | 76         | DL           | NotPass   |
            | 34004C       | 86         | DL           | NotPass   |
            | 34004C       | 91         | DL           | NotPass   |
