@nhi @nhi-P6-series
Feature: P6704C 嚴重齲齒兒童口腔健康照護複診治療-第二次

    Scenario Outline: 全部檢核成功
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
        Then 確認診療代碼 <IssueNhiCode> ，確認結果是否為 <PassOrNot>
        Examples:
            | PastTreatmentDays | TreatmentNhiCode | TreatmentTeeth | TreatmentSurface | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            | 91                | P6703C           | 11             | MOB              | P6704C       | 11         | MOB          | Pass      |

    Scenario Outline: （HIS）90天內，不應有 P6703C 診療項目
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
        Then 檢查 <IssueNhiCode> 診療項目，在病患過去 <GapDay> 天紀錄中，不應包含特定的 <TreatmentNhiCode> 診療代碼，確認結果是否為 <PassOrNot> 且檢查訊息類型為 D8_1
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastTreatmentDays | TreatmentNhiCode | TreatmentTeeth | GapDay | PassOrNot |
            | P6704C       | FM         | DL           | 89                | P6703C           | FM             | 90     | NotPass   |
            | P6704C       | FM         | DL           | 90                | P6703C           | FM             | 90     | NotPass   |
            | P6704C       | FM         | DL           | 91                | P6703C           | FM             | 90     | Pass      |

    Scenario Outline: （IC）90天內，不應有 P6703C 診療項目
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
        Then 檢查 <IssueNhiCode> 診療項目，在病患過去 <GapDay> 天紀錄中，不應包含特定的 <MedicalNhiCode> 診療代碼，確認結果是否為 <PassOrNot> 且檢查訊息類型為 D8_1
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastMedicalDays | MedicalNhiCode | MedicalTeeth | GapDay | PassOrNot |
            | P6704C       | FM         | DL           | 89                | P6703C           | FM             | 90     | NotPass   |
            | P6704C       | FM         | DL           | 90                | P6703C           | FM             | 90     | NotPass   |
            | P6704C       | FM         | DL           | 91                | P6703C           | FM             | 90     | Pass      |
            | P6704C       | FM         | DL           | 89                | P6703C           | FM             | 90     | NotPass   |
            | P6704C       | FM         | DL           | 90                | P6703C           | FM             | 90     | NotPass   |
            | P6704C       | FM         | DL           | 91                | P6703C           | FM             | 90     | Pass      |

    Scenario Outline: （Disposal）不得與91014C、91020C、91114C、92051B、92072C、P30002、81、87同時申報
        Given 建立醫師
        Given Stan 24 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode         | NewTeeth         | NewSurface         |
            |         |       |         | <TreatmentNhiCode> | <TreatmentTeeth> | <TreatmentSurface> |
            |         |       |         | <IssueNhiCode>     | <IssueTeeth>     | <IssueSurface>     |
        Then 同日不得有 <TreatmentNhiCode> 診療項目，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | TreatmentNhiCode | TreatmentTeeth | TreatmentSurface | PassOrNot |
            | P6704C       | 11         | MOB          | 91014C           | 11             | MOB              | NotPass   |
            | P6704C       | 11         | MOB          | 91020C           | 11             | MOB              | NotPass   |
            | P6704C       | 11         | MOB          | 91114C           | 11             | MOB              | NotPass   |
            | P6704C       | 11         | MOB          | 92051B           | 11             | MOB              | NotPass   |
            | P6704C       | 11         | MOB          | 92072C           | 11             | MOB              | NotPass   |
            | P6704C       | 11         | MOB          | P30002           | 11             | MOB              | NotPass   |
            | P6704C       | 11         | MOB          | 81           | 11             | MOB              | NotPass   |
            | P6704C       | 11         | MOB          | 87           | 11             | MOB              | NotPass   |

    Scenario Outline: （HIS-Today）不得與91014C、91020C、91114C、92051B、92072C、P30002、81、87同時申報
        Given 建立醫師
        Given Stan 24 歲病人
        Given 在 <PastTreatmentDate> ，建立預約
        Given 在 <PastTreatmentDate> ，建立掛號
        Given 在 <PastTreatmentDate> ，產生診療計畫
        And 新增診療代碼:
            | PastDate            | A72 | A73                | A74              | A75                | A76 | A77 | A78 | A79 |
            | <PastTreatmentDate> | 3   | <TreatmentNhiCode> | <TreatmentTeeth> | <TreatmentSurface> | 0   | 1.0 | 03  |     |
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 同日不得有 <TreatmentNhiCode> 診療項目，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastTreatmentDate | TreatmentNhiCode | TreatmentTeeth | TreatmentSurface | PassOrNot |
            | P6704C       | 11         | MOB          | 當日                | 91014C           | 11             | MOB              | NotPass   |
            | P6704C       | 11         | MOB          | 當日                | 91020C           | 11             | MOB              | NotPass   |
            | P6704C       | 11         | MOB          | 當日                | 91114C           | 11             | MOB              | NotPass   |
            | P6704C       | 11         | MOB          | 當日                | 92051B           | 11             | MOB              | NotPass   |
            | P6704C       | 11         | MOB          | 當日                | 92072C           | 11             | MOB              | NotPass   |
            | P6704C       | 11         | MOB          | 當日                | P30002           | 11             | MOB              | NotPass   |
            | P6704C       | 11         | MOB          | 當日                | 81               | 11             | MOB              | NotPass   |
            | P6704C       | 11         | MOB          | 當日                | 87               | 11             | MOB              | NotPass   |
            | P6704C       | 11         | MOB          | 昨日                | 91014C           | 11             | MOB              | Pass      |
            | P6704C       | 11         | MOB          | 昨日                | 91020C           | 11             | MOB              | Pass      |
            | P6704C       | 11         | MOB          | 昨日                | 91114C           | 11             | MOB              | Pass      |
            | P6704C       | 11         | MOB          | 昨日                | 92051B           | 11             | MOB              | Pass      |
            | P6704C       | 11         | MOB          | 昨日                | 92072C           | 11             | MOB              | Pass      |
            | P6704C       | 11         | MOB          | 昨日                | P30002           | 11             | MOB              | Pass      |
            | P6704C       | 11         | MOB          | 昨日                | 81               | 11             | MOB              | Pass      |
            | P6704C       | 11         | MOB          | 昨日                | 87               | 11             | MOB              | Pass      |

    Scenario Outline: （IC）不得與91014C、91020C、91114C、92051B、92072C、P30002、81、87同時申報
        Given 建立醫師
        Given Stan 24 歲病人
        Given 新增健保醫療:
            | PastDate          | NhiCode          | Teeth          |
            | <PastMedicalDate> | <MedicalNhiCode> | <MedicalTeeth> |
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 同日不得有 <MedicalNhiCode> 診療項目，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastMedicalDate | MedicalNhiCode | MedicalTeeth | PassOrNot |
            | P6704C       | 11         | MOB          | 當日                | 91014C           | 11           | NotPass   |
            | P6704C       | 11         | MOB          | 當日                | 91020C           | 11           | NotPass   |
            | P6704C       | 11         | MOB          | 當日                | 91114C           | 11           | NotPass   |
            | P6704C       | 11         | MOB          | 當日                | 92051B           | 11           | NotPass   |
            | P6704C       | 11         | MOB          | 當日                | 92072C           | 11           | NotPass   |
            | P6704C       | 11         | MOB          | 當日                | P30002           | 11           | NotPass   |
            | P6704C       | 11         | MOB          | 當日                | 81               | 11           | NotPass   |
            | P6704C       | 11         | MOB          | 當日                | 87               | 11           | NotPass   |
            | P6704C       | 11         | MOB          | 昨日                | 91014C           | 11           | Pass      |
            | P6704C       | 11         | MOB          | 昨日                | 91020C           | 11           | Pass      |
            | P6704C       | 11         | MOB          | 昨日                | 91114C           | 11           | Pass      |
            | P6704C       | 11         | MOB          | 昨日                | 92051B           | 11           | Pass      |
            | P6704C       | 11         | MOB          | 昨日                | 92072C           | 11           | Pass      |
            | P6704C       | 11         | MOB          | 昨日                | P30002           | 11           | Pass      |
            | P6704C       | 11         | MOB          | 昨日                | 81               | 11           | Pass      |
            | P6704C       | 11         | MOB          | 昨日                | 87               | 11           | Pass      |
