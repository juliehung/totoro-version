Feature: 90012C 橡皮障防濕裝置

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
            | 90012C       | 11         | MOB          | Pass      |

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
            | 90012C       | 11         | MOB          | Pass      |

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
        Then （HIS）同牙 <IssueTeeth> 未曾申報過，指定代碼 <TreatmentNhiCode> ，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastTreatmentDays | TreatmentNhiCode | TreatmentTeeth | PassOrNot |
            # 測試同牙
            | 90012C       | 11         | MOB          | 3650              | 92013C           | 11             | NotPass   |
            | 90012C       | 11         | MOB          | 3650              | 92014C           | 11             | NotPass   |
            | 90012C       | 11         | MOB          | 3650              | 92015C           | 11             | NotPass   |
            # 測試不同牙
            | 90012C       | 11         | MOB          | 3650              | 92013C           | 51             | Pass      |
            | 90012C       | 11         | MOB          | 3650              | 92014C           | 51             | Pass      |
            | 90012C       | 11         | MOB          | 3650              | 92015C           | 51             | Pass      |

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
        Then （IC）同牙 <IssueTeeth> 未曾申報過，指定代碼 <MedicalNhiCode> ，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastMedicalDays | MedicalNhiCode | TreatmentTeeth | PassOrNot |
            # 測試同牙
            | 90012C       | 51         | MOB          | 3650            | 92013C         | 51             | NotPass   |
            | 90012C       | 51         | MOB          | 3650            | 92014C         | 51             | NotPass   |
            | 90012C       | 51         | MOB          | 3650            | 92015C         | 51             | NotPass   |
            # 測試不同牙
            | 90012C       | 51         | MOB          | 3650            | 92013C         | 11             | Pass      |
            | 90012C       | 51         | MOB          | 3650            | 92014C         | 11             | Pass      |
            | 90012C       | 51         | MOB          | 3650            | 92015C         | 11             | Pass      |

    Scenario Outline: 檢查治療的牙位是否為 GENERAL_TOOTH
        Given 建立醫師
        Given Wind 24 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 檢查 <IssueTeeth> 牙位，依 GENERAL_TOOTH 判定是否為核可牙位，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            # 乳牙
            | 90012C       | 51         | DL           | Pass      |
            | 90012C       | 52         | DL           | Pass      |
            | 90012C       | 53         | DL           | Pass      |
            | 90012C       | 54         | DL           | Pass      |
            | 90012C       | 55         | DL           | Pass      |
            | 90012C       | 61         | DL           | Pass      |
            | 90012C       | 62         | DL           | Pass      |
            | 90012C       | 63         | DL           | Pass      |
            | 90012C       | 64         | DL           | Pass      |
            | 90012C       | 65         | DL           | Pass      |
            | 90012C       | 71         | DL           | Pass      |
            | 90012C       | 72         | DL           | Pass      |
            | 90012C       | 73         | DL           | Pass      |
            | 90012C       | 74         | DL           | Pass      |
            | 90012C       | 75         | DL           | Pass      |
            | 90012C       | 81         | DL           | Pass      |
            | 90012C       | 82         | DL           | Pass      |
            | 90012C       | 83         | DL           | Pass      |
            | 90012C       | 84         | DL           | Pass      |
            | 90012C       | 85         | DL           | Pass      |
            # 恆牙
            | 90012C       | 11         | DL           | Pass      |
            | 90012C       | 12         | DL           | Pass      |
            | 90012C       | 13         | DL           | Pass      |
            | 90012C       | 14         | DL           | Pass      |
            | 90012C       | 15         | DL           | Pass      |
            | 90012C       | 16         | DL           | Pass      |
            | 90012C       | 17         | DL           | Pass      |
            | 90012C       | 18         | DL           | Pass      |
            | 90012C       | 21         | DL           | Pass      |
            | 90012C       | 22         | DL           | Pass      |
            | 90012C       | 23         | DL           | Pass      |
            | 90012C       | 24         | DL           | Pass      |
            | 90012C       | 25         | DL           | Pass      |
            | 90012C       | 26         | DL           | Pass      |
            | 90012C       | 27         | DL           | Pass      |
            | 90012C       | 28         | DL           | Pass      |
            | 90012C       | 31         | DL           | Pass      |
            | 90012C       | 32         | DL           | Pass      |
            | 90012C       | 33         | DL           | Pass      |
            | 90012C       | 34         | DL           | Pass      |
            | 90012C       | 35         | DL           | Pass      |
            | 90012C       | 36         | DL           | Pass      |
            | 90012C       | 37         | DL           | Pass      |
            | 90012C       | 38         | DL           | Pass      |
            | 90012C       | 41         | DL           | Pass      |
            | 90012C       | 42         | DL           | Pass      |
            | 90012C       | 43         | DL           | Pass      |
            | 90012C       | 44         | DL           | Pass      |
            | 90012C       | 45         | DL           | Pass      |
            | 90012C       | 46         | DL           | Pass      |
            | 90012C       | 47         | DL           | Pass      |
            | 90012C       | 48         | DL           | Pass      |
            # 無牙
            | 90012C       |            | DL           | NotPass   |
            #
            | 90012C       | 19         | DL           | Pass      |
            | 90012C       | 29         | DL           | Pass      |
            | 90012C       | 39         | DL           | Pass      |
            | 90012C       | 49         | DL           | Pass      |
            | 90012C       | 59         | DL           | NotPass   |
            | 90012C       | 69         | DL           | NotPass   |
            | 90012C       | 79         | DL           | NotPass   |
            | 90012C       | 89         | DL           | NotPass   |
            | 90012C       | 99         | DL           | Pass      |
            # 牙位為區域型態
            | 90012C       | FM         | DL           | NotPass   |
            # 非法牙位
            | 90012C       | 00         | DL           | NotPass   |
            | 90012C       | 01         | DL           | NotPass   |
            | 90012C       | 10         | DL           | NotPass   |
            | 90012C       | 56         | DL           | NotPass   |
            | 90012C       | 66         | DL           | NotPass   |
            | 90012C       | 76         | DL           | NotPass   |
            | 90012C       | 86         | DL           | NotPass   |
            | 90012C       | 91         | DL           | NotPass   |


