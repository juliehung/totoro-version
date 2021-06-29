Feature: 89007C 釘強化術（每支）

    Scenario Outline: 全部檢核成功
        Given 建立醫師
        Given Wind 24 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | 89001C         | <IssueTeeth> | <IssueSurface> |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 確認診療代碼 <IssueNhiCode> ，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            | 89007C       | 14         | MOB          | Pass      |

    Scenario Outline: 提醒須檢附影像
        Given 建立醫師
        Given Wind 24 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | 89001C         | <IssueTeeth> | <IssueSurface> |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 提醒"須檢附影像"，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            | 89007C       | 14         | MOB          | Pass      |

    Scenario Outline: 檢查治療的牙位是否為 PERMANENT_TOOTH
        Given 建立醫師
        Given Wind 24 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | 89001C         | <IssueTeeth> | <IssueSurface> |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 檢查 <IssueTeeth> 牙位，依 PERMANENT_TOOTH 判定是否為核可牙位，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            # 乳牙
            | 89007C       | 51         | DL           | NotPass   |
            | 89007C       | 52         | DL           | NotPass   |
            | 89007C       | 53         | DL           | NotPass   |
            | 89007C       | 54         | DL           | NotPass   |
            | 89007C       | 55         | DL           | NotPass   |
            | 89007C       | 61         | DL           | NotPass   |
            | 89007C       | 62         | DL           | NotPass   |
            | 89007C       | 63         | DL           | NotPass   |
            | 89007C       | 64         | DL           | NotPass   |
            | 89007C       | 65         | DL           | NotPass   |
            | 89007C       | 71         | DL           | NotPass   |
            | 89007C       | 72         | DL           | NotPass   |
            | 89007C       | 73         | DL           | NotPass   |
            | 89007C       | 74         | DL           | NotPass   |
            | 89007C       | 75         | DL           | NotPass   |
            | 89007C       | 81         | DL           | NotPass   |
            | 89007C       | 82         | DL           | NotPass   |
            | 89007C       | 83         | DL           | NotPass   |
            | 89007C       | 84         | DL           | NotPass   |
            | 89007C       | 85         | DL           | NotPass   |
            # 恆牙
            | 89007C       | 11         | DL           | Pass      |
            | 89007C       | 12         | DL           | Pass      |
            | 89007C       | 13         | DL           | Pass      |
            | 89007C       | 14         | DL           | Pass      |
            | 89007C       | 15         | DL           | Pass      |
            | 89007C       | 16         | DL           | Pass      |
            | 89007C       | 17         | DL           | Pass      |
            | 89007C       | 18         | DL           | Pass      |
            | 89007C       | 21         | DL           | Pass      |
            | 89007C       | 22         | DL           | Pass      |
            | 89007C       | 23         | DL           | Pass      |
            | 89007C       | 24         | DL           | Pass      |
            | 89007C       | 25         | DL           | Pass      |
            | 89007C       | 26         | DL           | Pass      |
            | 89007C       | 27         | DL           | Pass      |
            | 89007C       | 28         | DL           | Pass      |
            | 89007C       | 31         | DL           | Pass      |
            | 89007C       | 32         | DL           | Pass      |
            | 89007C       | 33         | DL           | Pass      |
            | 89007C       | 34         | DL           | Pass      |
            | 89007C       | 35         | DL           | Pass      |
            | 89007C       | 36         | DL           | Pass      |
            | 89007C       | 37         | DL           | Pass      |
            | 89007C       | 38         | DL           | Pass      |
            | 89007C       | 41         | DL           | Pass      |
            | 89007C       | 42         | DL           | Pass      |
            | 89007C       | 43         | DL           | Pass      |
            | 89007C       | 44         | DL           | Pass      |
            | 89007C       | 45         | DL           | Pass      |
            | 89007C       | 46         | DL           | Pass      |
            | 89007C       | 47         | DL           | Pass      |
            | 89007C       | 48         | DL           | Pass      |
            # 無牙
            | 89007C       |            | DL           | NotPass   |
            #
            | 89007C       | 19         | DL           | Pass      |
            | 89007C       | 29         | DL           | Pass      |
            | 89007C       | 39         | DL           | Pass      |
            | 89007C       | 49         | DL           | Pass      |
            | 89007C       | 59         | DL           | NotPass   |
            | 89007C       | 69         | DL           | NotPass   |
            | 89007C       | 79         | DL           | NotPass   |
            | 89007C       | 89         | DL           | NotPass   |
            | 89007C       | 99         | DL           | Pass      |
            # 牙位為區域型態
            | 89007C       | FM         | DL           | NotPass   |
            | 89007C       | UR         | DL           | NotPass   |
            | 89007C       | UL         | DL           | NotPass   |
            | 89007C       | UA         | DL           | NotPass   |
            | 89007C       | LR         | DL           | NotPass   |
            | 89007C       | LL         | DL           | NotPass   |
            | 89007C       | LA         | DL           | NotPass   |
            # 非法牙位
            | 89007C       | 00         | DL           | NotPass   |
            | 89007C       | 01         | DL           | NotPass   |
            | 89007C       | 10         | DL           | NotPass   |
            | 89007C       | 56         | DL           | NotPass   |
            | 89007C       | 66         | DL           | NotPass   |
            | 89007C       | 76         | DL           | NotPass   |
            | 89007C       | 86         | DL           | NotPass   |
            | 89007C       | 91         | DL           | NotPass   |

    Scenario Outline: （Disposal）應與89001C~89015C、89101C~89115C同時申報
        Given 建立醫師
        Given Wind 24 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode         | NewTeeth     | NewSurface     |
            |         |       |         | <TreatmentNhiCode> | <IssueTeeth> | <IssueSurface> |
            |         |       |         | <IssueNhiCode>     | <IssueTeeth> | <IssueSurface> |
        Then 檢查同一處置單，必須包含 89001C~89015C/89101C~89115C 其中之一的診療項目，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | TreatmentNhiCode | PassOrNot |
            | 89007C       | 14         | MOB          | 01271C           | NotPass   |
            # 89001C~89015C
            | 89007C       | 14         | MOB          | 89001C           | Pass      |
            | 89007C       | 14         | MOB          | 89002C           | Pass      |
            | 89007C       | 14         | MOB          | 89003C           | Pass      |
            | 89007C       | 14         | MOB          | 89004C           | Pass      |
            | 89007C       | 14         | MOB          | 89005C           | Pass      |
            | 89007C       | 14         | MOB          | 89006C           | Pass      |
            | 89007C       | 14         | MOB          | 89007C           | Pass      |
            | 89007C       | 14         | MOB          | 89008C           | Pass      |
            | 89007C       | 14         | MOB          | 89009C           | Pass      |
            | 89007C       | 14         | MOB          | 89010C           | Pass      |
            | 89007C       | 14         | MOB          | 89011C           | Pass      |
            | 89007C       | 14         | MOB          | 89012C           | Pass      |
            | 89007C       | 14         | MOB          | 89013C           | Pass      |
            | 89007C       | 14         | MOB          | 89014C           | Pass      |
            | 89007C       | 14         | MOB          | 89015C           | Pass      |
            # 89101C~89115C
            | 89007C       | 14         | MOB          | 89101C           | Pass      |
            | 89007C       | 14         | MOB          | 89102C           | Pass      |
            | 89007C       | 14         | MOB          | 89103C           | Pass      |
            | 89007C       | 14         | MOB          | 89104C           | Pass      |
            | 89007C       | 14         | MOB          | 89105C           | Pass      |
            | 89007C       | 14         | MOB          | 89106C           | Pass      |
            | 89007C       | 14         | MOB          | 89107C           | Pass      |
            | 89007C       | 14         | MOB          | 89108C           | Pass      |
            | 89007C       | 14         | MOB          | 89109C           | Pass      |
            | 89007C       | 14         | MOB          | 89110C           | Pass      |
            | 89007C       | 14         | MOB          | 89111C           | Pass      |
            | 89007C       | 14         | MOB          | 89112C           | Pass      |
            | 89007C       | 14         | MOB          | 89113C           | Pass      |
            | 89007C       | 14         | MOB          | 89114C           | Pass      |
            | 89007C       | 14         | MOB          | 89115C           | Pass      |

    Scenario Outline: （HIS-Today）應與89001C~89015C、89101C~89115C同時申報
        Given 建立醫師
        Given Wind 24 歲病人
        Given 在 當日 ，建立預約
        Given 在 當日 ，建立掛號
        Given 在 當日 ，產生診療計畫
        And 新增診療代碼:
            | PastDate | A72 | A73                | A74          | A75            | A76 | A77 | A78 | A79 |
            | 當日       | 3   | <TreatmentNhiCode> | <IssueTeeth> | <IssueSurface> | 0   | 1.0 | 03  |     |
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 檢查同一處置單，必須包含 89001C~89015C/89101C~89115C 其中之一的診療項目，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | TreatmentNhiCode | PassOrNot |
            | 89007C       | 14         | MOB          | 01271C           | NotPass   |
            # 89001C~89015C
            | 89007C       | 14         | MOB          | 89001C           | Pass      |
            | 89007C       | 14         | MOB          | 89002C           | Pass      |
            | 89007C       | 14         | MOB          | 89003C           | Pass      |
            | 89007C       | 14         | MOB          | 89004C           | Pass      |
            | 89007C       | 14         | MOB          | 89005C           | Pass      |
            | 89007C       | 14         | MOB          | 89006C           | Pass      |
            | 89007C       | 14         | MOB          | 89007C           | Pass      |
            | 89007C       | 14         | MOB          | 89008C           | Pass      |
            | 89007C       | 14         | MOB          | 89009C           | Pass      |
            | 89007C       | 14         | MOB          | 89010C           | Pass      |
            | 89007C       | 14         | MOB          | 89011C           | Pass      |
            | 89007C       | 14         | MOB          | 89012C           | Pass      |
            | 89007C       | 14         | MOB          | 89013C           | Pass      |
            | 89007C       | 14         | MOB          | 89014C           | Pass      |
            | 89007C       | 14         | MOB          | 89015C           | Pass      |
            # 89101C~89115C
            | 89007C       | 14         | MOB          | 89101C           | Pass      |
            | 89007C       | 14         | MOB          | 89102C           | Pass      |
            | 89007C       | 14         | MOB          | 89103C           | Pass      |
            | 89007C       | 14         | MOB          | 89104C           | Pass      |
            | 89007C       | 14         | MOB          | 89105C           | Pass      |
            | 89007C       | 14         | MOB          | 89106C           | Pass      |
            | 89007C       | 14         | MOB          | 89107C           | Pass      |
            | 89007C       | 14         | MOB          | 89108C           | Pass      |
            | 89007C       | 14         | MOB          | 89109C           | Pass      |
            | 89007C       | 14         | MOB          | 89110C           | Pass      |
            | 89007C       | 14         | MOB          | 89111C           | Pass      |
            | 89007C       | 14         | MOB          | 89112C           | Pass      |
            | 89007C       | 14         | MOB          | 89113C           | Pass      |
            | 89007C       | 14         | MOB          | 89114C           | Pass      |
            | 89007C       | 14         | MOB          | 89115C           | Pass      |

