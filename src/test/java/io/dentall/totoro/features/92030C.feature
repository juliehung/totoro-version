Feature: 92030C 前齒根尖切除術

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
            | 92030C       | 11         | DL           | Pass      |

    Scenario Outline: 提醒須檢附影像
        Given 建立醫師
        Given Scott 24 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 提醒"須檢附影像"，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            | 92030C       | 11         | MOB          | Pass      |

    Scenario Outline: 檢查治療的牙位是否為 PERMANENT_FRONT_TOOTH
        Given 建立醫師
        Given Scott 24 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 檢查 <IssueTeeth> 牙位，依 PERMANENT_FRONT_TOOTH 判定是否為核可牙位，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            # 乳牙
            | 92030C       | 51         | DL           | NotPass   |
            | 92030C       | 52         | DL           | NotPass   |
            | 92030C       | 53         | DL           | NotPass   |
            | 92030C       | 54         | DL           | NotPass   |
            | 92030C       | 55         | DL           | NotPass   |
            | 92030C       | 61         | DL           | NotPass   |
            | 92030C       | 62         | DL           | NotPass   |
            | 92030C       | 63         | DL           | NotPass   |
            | 92030C       | 64         | DL           | NotPass   |
            | 92030C       | 65         | DL           | NotPass   |
            | 92030C       | 71         | DL           | NotPass   |
            | 92030C       | 72         | DL           | NotPass   |
            | 92030C       | 73         | DL           | NotPass   |
            | 92030C       | 74         | DL           | NotPass   |
            | 92030C       | 75         | DL           | NotPass   |
            | 92030C       | 81         | DL           | NotPass   |
            | 92030C       | 82         | DL           | NotPass   |
            | 92030C       | 83         | DL           | NotPass   |
            | 92030C       | 84         | DL           | NotPass   |
            | 92030C       | 85         | DL           | NotPass   |
            # 恆牙
            | 92030C       | 11         | DL           | Pass      |
            | 92030C       | 12         | DL           | Pass      |
            | 92030C       | 13         | DL           | Pass      |
            | 92030C       | 14         | DL           | NotPass   |
            | 92030C       | 15         | DL           | NotPass   |
            | 92030C       | 16         | DL           | NotPass   |
            | 92030C       | 17         | DL           | NotPass   |
            | 92030C       | 18         | DL           | NotPass   |
            | 92030C       | 21         | DL           | Pass      |
            | 92030C       | 22         | DL           | Pass      |
            | 92030C       | 23         | DL           | Pass      |
            | 92030C       | 24         | DL           | NotPass   |
            | 92030C       | 25         | DL           | NotPass   |
            | 92030C       | 26         | DL           | NotPass   |
            | 92030C       | 27         | DL           | NotPass   |
            | 92030C       | 28         | DL           | NotPass   |
            | 92030C       | 31         | DL           | Pass      |
            | 92030C       | 32         | DL           | Pass      |
            | 92030C       | 33         | DL           | Pass      |
            | 92030C       | 34         | DL           | NotPass   |
            | 92030C       | 35         | DL           | NotPass   |
            | 92030C       | 36         | DL           | NotPass   |
            | 92030C       | 37         | DL           | NotPass   |
            | 92030C       | 38         | DL           | NotPass   |
            | 92030C       | 41         | DL           | Pass      |
            | 92030C       | 42         | DL           | Pass      |
            | 92030C       | 43         | DL           | Pass      |
            | 92030C       | 44         | DL           | NotPass   |
            | 92030C       | 45         | DL           | NotPass   |
            | 92030C       | 46         | DL           | NotPass   |
            | 92030C       | 47         | DL           | NotPass   |
            | 92030C       | 48         | DL           | NotPass   |
            # 無牙
            | 92030C       |            | DL           | NotPass   |
            #
            | 92030C       | 19         | DL           | Pass      |
            | 92030C       | 29         | DL           | Pass      |
            | 92030C       | 39         | DL           | Pass      |
            | 92030C       | 49         | DL           | Pass      |
            | 92030C       | 59         | DL           | NotPass   |
            | 92030C       | 69         | DL           | NotPass   |
            | 92030C       | 79         | DL           | NotPass   |
            | 92030C       | 89         | DL           | NotPass   |
            | 92030C       | 99         | DL           | Pass      |
            # 牙位為區域型態
            | 92030C       | FM         | DL           | NotPass   |
            | 92030C       | UR         | DL           | NotPass   |
            | 92030C       | UL         | DL           | NotPass   |
            | 92030C       | LL         | DL           | NotPass   |
            | 92030C       | LR         | DL           | NotPass   |
            | 92030C       | UA         | DL           | NotPass   |
            | 92030C       | LA         | DL           | NotPass   |
            # 非法牙位
            | 92030C       | 00         | DL           | NotPass   |
            | 92030C       | 01         | DL           | NotPass   |
            | 92030C       | 10         | DL           | NotPass   |
            | 92030C       | 56         | DL           | NotPass   |
            | 92030C       | 66         | DL           | NotPass   |
            | 92030C       | 76         | DL           | NotPass   |
            | 92030C       | 86         | DL           | NotPass   |
            | 92030C       | 91         | DL           | NotPass   |

    Scenario Outline: （HIS）545天內，不應有 92030C 診療項目
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
        Then （HIS）檢查 <IssueNhiCode> 診療項目，在病患過去 <GapDay> 天紀錄中，不應包含特定的 <TreatmentNhiCode> 診療代碼，確認結果是否為 <PassOrNot> 且檢查訊息類型為 D4_1
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastTreatmentDays | TreatmentNhiCode | TreatmentTeeth | GapDay | PassOrNot |
            | 92030C       | 11         | DL           | 0                 | 92030C           | 11             | 545    | NotPass   |
            | 92030C       | 11         | DL           | 544               | 92030C           | 11             | 545    | NotPass   |
            | 92030C       | 11         | DL           | 545               | 92030C           | 11             | 545    | NotPass   |
            | 92030C       | 11         | DL           | 546               | 92030C           | 11             | 545    | Pass      |
