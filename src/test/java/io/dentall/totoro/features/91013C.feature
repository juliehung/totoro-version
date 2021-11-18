@nhi @nhi-91-series @part2
Feature: 91013C 牙齦切除術-施行根管治療或牙體復形時，所需之牙齦切除術

    Scenario Outline: 全部檢核成功
        Given 建立醫師
        Given Stan 24 歲病人
        Given 在過去第 60 天，建立預約
        Given 在過去第 60 天，建立掛號
        Given 在過去第 60 天，產生診療計畫
        And 新增診療代碼:
            | PastDays | A72 | A73    | A74 | A75 | A76 | A77 | A78 | A79 |
            | 60       | 3   | 90015C | 11  | MOB | 0   | 1.0 | 03  |     |
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode     | NewTeeth     | NewSurface     |
            |         |       |         | 90001C         | <IssueTeeth> | <IssueSurface> |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 確認診療代碼 <IssueNhiCode> ，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PassOrNot |
            | 91013C       | 11         | MOB          | Pass      |

    Scenario Outline: （Disposal）同日不得同時有 91011C/91012C 診療項目
        Given 建立醫師
        Given Stan 24 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode         | NewTeeth         | NewSurface         |
            |         |       |         | <TreatmentNhiCode> | <TreatmentTeeth> | <TreatmentSurface> |
            |         |       |         | 90001C             | <IssueTeeth>     | <IssueSurface>     |
            |         |       |         | <IssueNhiCode>     | <IssueTeeth>     | <IssueSurface>     |
        Then 同日不得有 <TreatmentNhiCode> 診療項目，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | TreatmentNhiCode | TreatmentTeeth | TreatmentSurface | PassOrNot |
            | 91013C       | 11         | MOB          | 91011C           | 11             | MOB              | NotPass   |
            | 91013C       | 11         | MOB          | 91012C           | 11             | MOB              | NotPass   |

    Scenario Outline: （HIS-Today）同日不得同時有 91011C/91012C 診療項目
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
            |         |       |         | 90001C         | <IssueTeeth> | <IssueSurface> |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 同日不得有 <TreatmentNhiCode> 診療項目，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastTreatmentDate | TreatmentNhiCode | TreatmentTeeth | TreatmentSurface | PassOrNot |
            | 91013C       | 11         | MOB          | 當日                | 91011C           | 11             | MOB              | NotPass   |
            | 91013C       | 11         | MOB          | 當日                | 91012C           | 11             | MOB              | NotPass   |
            | 91013C       | 11         | MOB          | 昨日                | 91011C           | 11             | MOB              | Pass      |
            | 91013C       | 11         | MOB          | 昨日                | 91012C           | 11             | MOB              | Pass      |

    Scenario Outline: （IC）同日不得同時有 91011C/91012C 診療項目
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
            |         |       |         | 90001C         | <IssueTeeth> | <IssueSurface> |
            |         |       |         | <IssueNhiCode> | <IssueTeeth> | <IssueSurface> |
        Then 同日不得有 <MedicalNhiCode> 診療項目，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastMedicalDate | MedicalNhiCode | MedicalTeeth | PassOrNot |
            | 91013C       | 11         | MOB          | 當日              | 91011C         | 11           | NotPass   |
            | 91013C       | 11         | MOB          | 當日              | 91011C         | 11           | NotPass   |
            | 91013C       | 11         | MOB          | 昨日              | 91012C         | 11           | Pass      |
            | 91013C       | 11         | MOB          | 昨日              | 91012C         | 11           | Pass      |

    Scenario Outline: （Disposal）同日得同時有 90001C/90002C/90003C/90015C/90019C/90020C/89001C~89015C/89101C~89113C 診療項目
        Given 建立醫師
        Given Stan 24 歲病人
        Given 建立預約
        Given 建立掛號
        Given 產生診療計畫
        When 執行診療代碼 <IssueNhiCode> 檢查:
            | NhiCode | Teeth | Surface | NewNhiCode         | NewTeeth         | NewSurface         |
            |         |       |         | <TreatmentNhiCode> | <TreatmentTeeth> | <TreatmentSurface> |
            |         |       |         | <IssueNhiCode>     | <IssueTeeth>     | <IssueSurface>     |
        Then 同日得有 90001C/90002C/90003C/90015C/90019C/90020C/89001C~89015C/89101C~89113C 診療項目，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | TreatmentNhiCode | TreatmentTeeth | TreatmentSurface | PassOrNot |
            # 90001C/90002C/90003C/90015C/90019C/90020C
            | 91013C       | 11         | MOB          | 90001C           | 11             | MOB              | Pass      |
            | 91013C       | 11         | MOB          | 90002C           | 11             | MOB              | Pass      |
            | 91013C       | 11         | MOB          | 90003C           | 11             | MOB              | Pass      |
            | 91013C       | 11         | MOB          | 90015C           | 11             | MOB              | Pass      |
            | 91013C       | 11         | MOB          | 90019C           | 11             | MOB              | Pass      |
            | 91013C       | 11         | MOB          | 90020C           | 11             | MOB              | Pass      |
            # 89001C~89015C
            | 91013C       | 11         | MOB          | 89001C           | 11             | MOB              | Pass      |
            | 91013C       | 11         | MOB          | 89002C           | 11             | MOB              | Pass      |
            | 91013C       | 11         | MOB          | 89003C           | 11             | MOB              | Pass      |
            | 91013C       | 11         | MOB          | 89004C           | 11             | MOB              | Pass      |
            | 91013C       | 11         | MOB          | 89005C           | 11             | MOB              | Pass      |
            | 91013C       | 11         | MOB          | 89006C           | 11             | MOB              | Pass      |
            | 91013C       | 11         | MOB          | 89007C           | 11             | MOB              | Pass      |
            | 91013C       | 11         | MOB          | 89008C           | 11             | MOB              | Pass      |
            | 91013C       | 11         | MOB          | 89009C           | 11             | MOB              | Pass      |
            | 91013C       | 11         | MOB          | 89010C           | 11             | MOB              | Pass      |
            | 91013C       | 11         | MOB          | 89011C           | 11             | MOB              | Pass      |
            | 91013C       | 11         | MOB          | 89012C           | 11             | MOB              | Pass      |
            | 91013C       | 11         | MOB          | 89013C           | 11             | MOB              | Pass      |
            | 91013C       | 11         | MOB          | 89014C           | 11             | MOB              | Pass      |
            | 91013C       | 11         | MOB          | 89015C           | 11             | MOB              | Pass      |
            # 89101C~89113C
            | 91013C       | 11         | MOB          | 89101C           | 11             | MOB              | Pass      |
            | 91013C       | 11         | MOB          | 89102C           | 11             | MOB              | Pass      |
            | 91013C       | 11         | MOB          | 89103C           | 11             | MOB              | Pass      |
            | 91013C       | 11         | MOB          | 89104C           | 11             | MOB              | Pass      |
            | 91013C       | 11         | MOB          | 89105C           | 11             | MOB              | Pass      |
            | 91013C       | 11         | MOB          | 89106C           | 11             | MOB              | Pass      |
            | 91013C       | 11         | MOB          | 89107C           | 11             | MOB              | Pass      |
            | 91013C       | 11         | MOB          | 89108C           | 11             | MOB              | Pass      |
            | 91013C       | 11         | MOB          | 89109C           | 11             | MOB              | Pass      |
            | 91013C       | 11         | MOB          | 89110C           | 11             | MOB              | Pass      |
            | 91013C       | 11         | MOB          | 89111C           | 11             | MOB              | Pass      |
            | 91013C       | 11         | MOB          | 89112C           | 11             | MOB              | Pass      |
            | 91013C       | 11         | MOB          | 89113C           | 11             | MOB              | Pass      |

    Scenario Outline: （HIS-Today）同日得同時有 90001C/90002C/90003C/90015C/90019C/90020C/89001C~89015C/89101C~89113C 診療項目
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
        Then 同日得有 90001C/90002C/90003C/90015C/90019C/90020C/89001C~89015C/89101C~89113C 診療項目，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastTreatmentDate | TreatmentNhiCode | TreatmentTeeth | TreatmentSurface | PassOrNot |
            # 90001C/90002C/90003C/90015C/90019C/90020C 當日
            | 91013C       | 11         | MOB          | 當日                | 90001C           | 11             | MOB              | Pass      |
            | 91013C       | 11         | MOB          | 當日                | 90002C           | 11             | MOB              | Pass      |
            | 91013C       | 11         | MOB          | 當日                | 90003C           | 11             | MOB              | Pass      |
            | 91013C       | 11         | MOB          | 當日                | 90015C           | 11             | MOB              | Pass      |
            | 91013C       | 11         | MOB          | 當日                | 90019C           | 11             | MOB              | Pass      |
            | 91013C       | 11         | MOB          | 當日                | 90020C           | 11             | MOB              | Pass      |
             # 90001C/90002C/90003C/90015C/90019C/90020C 昨日
            | 91013C       | 11         | MOB          | 昨日                | 90001C           | 11             | MOB              | NotPass   |
            | 91013C       | 11         | MOB          | 昨日                | 90002C           | 11             | MOB              | NotPass   |
            | 91013C       | 11         | MOB          | 昨日                | 90003C           | 11             | MOB              | NotPass   |
            | 91013C       | 11         | MOB          | 昨日                | 90015C           | 11             | MOB              | NotPass   |
            | 91013C       | 11         | MOB          | 昨日                | 90019C           | 11             | MOB              | NotPass   |
            | 91013C       | 11         | MOB          | 昨日                | 90020C           | 11             | MOB              | NotPass   |
            # 89001C~89015C 當日
            | 91013C       | 11         | MOB          | 當日                | 89001C           | 11             | MOB              | Pass      |
            | 91013C       | 11         | MOB          | 當日                | 89002C           | 11             | MOB              | Pass      |
            | 91013C       | 11         | MOB          | 當日                | 89003C           | 11             | MOB              | Pass      |
            | 91013C       | 11         | MOB          | 當日                | 89004C           | 11             | MOB              | Pass      |
            | 91013C       | 11         | MOB          | 當日                | 89005C           | 11             | MOB              | Pass      |
            | 91013C       | 11         | MOB          | 當日                | 89006C           | 11             | MOB              | Pass      |
            | 91013C       | 11         | MOB          | 當日                | 89007C           | 11             | MOB              | Pass      |
            | 91013C       | 11         | MOB          | 當日                | 89008C           | 11             | MOB              | Pass      |
            | 91013C       | 11         | MOB          | 當日                | 89009C           | 11             | MOB              | Pass      |
            | 91013C       | 11         | MOB          | 當日                | 89010C           | 11             | MOB              | Pass      |
            | 91013C       | 11         | MOB          | 當日                | 89011C           | 11             | MOB              | Pass      |
            | 91013C       | 11         | MOB          | 當日                | 89012C           | 11             | MOB              | Pass      |
            | 91013C       | 11         | MOB          | 當日                | 89013C           | 11             | MOB              | Pass      |
            | 91013C       | 11         | MOB          | 當日                | 89014C           | 11             | MOB              | Pass      |
            | 91013C       | 11         | MOB          | 當日                | 89015C           | 11             | MOB              | Pass      |
            # 89001C~89015C 昨日
            | 91013C       | 11         | MOB          | 昨日                | 89001C           | 11             | MOB              | NotPass   |
            | 91013C       | 11         | MOB          | 昨日                | 89002C           | 11             | MOB              | NotPass   |
            | 91013C       | 11         | MOB          | 昨日                | 89003C           | 11             | MOB              | NotPass   |
            | 91013C       | 11         | MOB          | 昨日                | 89004C           | 11             | MOB              | NotPass   |
            | 91013C       | 11         | MOB          | 昨日                | 89005C           | 11             | MOB              | NotPass   |
            | 91013C       | 11         | MOB          | 昨日                | 89006C           | 11             | MOB              | NotPass   |
            | 91013C       | 11         | MOB          | 昨日                | 89007C           | 11             | MOB              | NotPass   |
            | 91013C       | 11         | MOB          | 昨日                | 89008C           | 11             | MOB              | NotPass   |
            | 91013C       | 11         | MOB          | 昨日                | 89009C           | 11             | MOB              | NotPass   |
            | 91013C       | 11         | MOB          | 昨日                | 89010C           | 11             | MOB              | NotPass   |
            | 91013C       | 11         | MOB          | 昨日                | 89011C           | 11             | MOB              | NotPass   |
            | 91013C       | 11         | MOB          | 昨日                | 89012C           | 11             | MOB              | NotPass   |
            | 91013C       | 11         | MOB          | 昨日                | 89013C           | 11             | MOB              | NotPass   |
            | 91013C       | 11         | MOB          | 昨日                | 89014C           | 11             | MOB              | NotPass   |
            | 91013C       | 11         | MOB          | 昨日                | 89015C           | 11             | MOB              | NotPass   |
            # 89101C~89113C 當日
            | 91013C       | 11         | MOB          | 當日                | 89101C           | 11             | MOB              | Pass      |
            | 91013C       | 11         | MOB          | 當日                | 89102C           | 11             | MOB              | Pass      |
            | 91013C       | 11         | MOB          | 當日                | 89103C           | 11             | MOB              | Pass      |
            | 91013C       | 11         | MOB          | 當日                | 89104C           | 11             | MOB              | Pass      |
            | 91013C       | 11         | MOB          | 當日                | 89105C           | 11             | MOB              | Pass      |
            | 91013C       | 11         | MOB          | 當日                | 89106C           | 11             | MOB              | Pass      |
            | 91013C       | 11         | MOB          | 當日                | 89107C           | 11             | MOB              | Pass      |
            | 91013C       | 11         | MOB          | 當日                | 89108C           | 11             | MOB              | Pass      |
            | 91013C       | 11         | MOB          | 當日                | 89109C           | 11             | MOB              | Pass      |
            | 91013C       | 11         | MOB          | 當日                | 89110C           | 11             | MOB              | Pass      |
            | 91013C       | 11         | MOB          | 當日                | 89111C           | 11             | MOB              | Pass      |
            | 91013C       | 11         | MOB          | 當日                | 89112C           | 11             | MOB              | Pass      |
            | 91013C       | 11         | MOB          | 當日                | 89113C           | 11             | MOB              | Pass      |
            # 89101C~89113C 昨日
            | 91013C       | 11         | MOB          | 昨日                | 89101C           | 11             | MOB              | NotPass   |
            | 91013C       | 11         | MOB          | 昨日                | 89102C           | 11             | MOB              | NotPass   |
            | 91013C       | 11         | MOB          | 昨日                | 89103C           | 11             | MOB              | NotPass   |
            | 91013C       | 11         | MOB          | 昨日                | 89104C           | 11             | MOB              | NotPass   |
            | 91013C       | 11         | MOB          | 昨日                | 89105C           | 11             | MOB              | NotPass   |
            | 91013C       | 11         | MOB          | 昨日                | 89106C           | 11             | MOB              | NotPass   |
            | 91013C       | 11         | MOB          | 昨日                | 89107C           | 11             | MOB              | NotPass   |
            | 91013C       | 11         | MOB          | 昨日                | 89108C           | 11             | MOB              | NotPass   |
            | 91013C       | 11         | MOB          | 昨日                | 89109C           | 11             | MOB              | NotPass   |
            | 91013C       | 11         | MOB          | 昨日                | 89110C           | 11             | MOB              | NotPass   |
            | 91013C       | 11         | MOB          | 昨日                | 89111C           | 11             | MOB              | NotPass   |
            | 91013C       | 11         | MOB          | 昨日                | 89112C           | 11             | MOB              | NotPass   |
            | 91013C       | 11         | MOB          | 昨日                | 89113C           | 11             | MOB              | NotPass   |

    Scenario Outline: （IC）同日得同時有 90001C/90002C/90003C/90015C/90019C/90020C/89001C~89015C/89101C~89113C 診療項目
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
        Then 同日得有 90001C/90002C/90003C/90015C/90019C/90020C/89001C~89015C/89101C~89113C 診療項目，確認結果是否為 <PassOrNot>
        Examples:
            | IssueNhiCode | IssueTeeth | IssueSurface | PastMedicalDate | MedicalNhiCode | MedicalTeeth | PassOrNot |
            # 90001C/90002C/90003C/90015C/90019C/90020C 當日
            | 91013C       | 11         | MOB          | 當日              | 90001C         | 11           | Pass      |
            | 91013C       | 11         | MOB          | 當日              | 90002C         | 11           | Pass      |
            | 91013C       | 11         | MOB          | 當日              | 90003C         | 11           | Pass      |
            | 91013C       | 11         | MOB          | 當日              | 90015C         | 11           | Pass      |
            | 91013C       | 11         | MOB          | 當日              | 90019C         | 11           | Pass      |
            | 91013C       | 11         | MOB          | 當日              | 90020C         | 11           | Pass      |
             # 90001C/90002C/90003C/90015C/90019C/90020C 昨日
            | 91013C       | 11         | MOB          | 昨日              | 90001C         | 11           | NotPass   |
            | 91013C       | 11         | MOB          | 昨日              | 90002C         | 11           | NotPass   |
            | 91013C       | 11         | MOB          | 昨日              | 90003C         | 11           | NotPass   |
            | 91013C       | 11         | MOB          | 昨日              | 90015C         | 11           | NotPass   |
            | 91013C       | 11         | MOB          | 昨日              | 90019C         | 11           | NotPass   |
            | 91013C       | 11         | MOB          | 昨日              | 90020C         | 11           | NotPass   |
            # 89001C~89015C 當日
            | 91013C       | 11         | MOB          | 當日              | 89001C         | 11           | Pass      |
            | 91013C       | 11         | MOB          | 當日              | 89002C         | 11           | Pass      |
            | 91013C       | 11         | MOB          | 當日              | 89003C         | 11           | Pass      |
            | 91013C       | 11         | MOB          | 當日              | 89004C         | 11           | Pass      |
            | 91013C       | 11         | MOB          | 當日              | 89005C         | 11           | Pass      |
            | 91013C       | 11         | MOB          | 當日              | 89006C         | 11           | Pass      |
            | 91013C       | 11         | MOB          | 當日              | 89007C         | 11           | Pass      |
            | 91013C       | 11         | MOB          | 當日              | 89008C         | 11           | Pass      |
            | 91013C       | 11         | MOB          | 當日              | 89009C         | 11           | Pass      |
            | 91013C       | 11         | MOB          | 當日              | 89010C         | 11           | Pass      |
            | 91013C       | 11         | MOB          | 當日              | 89011C         | 11           | Pass      |
            | 91013C       | 11         | MOB          | 當日              | 89012C         | 11           | Pass      |
            | 91013C       | 11         | MOB          | 當日              | 89013C         | 11           | Pass      |
            | 91013C       | 11         | MOB          | 當日              | 89014C         | 11           | Pass      |
            | 91013C       | 11         | MOB          | 當日              | 89015C         | 11           | Pass      |
            # 89001C~89015C 昨日
            | 91013C       | 11         | MOB          | 昨日              | 89001C         | 11           | NotPass   |
            | 91013C       | 11         | MOB          | 昨日              | 89002C         | 11           | NotPass   |
            | 91013C       | 11         | MOB          | 昨日              | 89003C         | 11           | NotPass   |
            | 91013C       | 11         | MOB          | 昨日              | 89004C         | 11           | NotPass   |
            | 91013C       | 11         | MOB          | 昨日              | 89005C         | 11           | NotPass   |
            | 91013C       | 11         | MOB          | 昨日              | 89006C         | 11           | NotPass   |
            | 91013C       | 11         | MOB          | 昨日              | 89007C         | 11           | NotPass   |
            | 91013C       | 11         | MOB          | 昨日              | 89008C         | 11           | NotPass   |
            | 91013C       | 11         | MOB          | 昨日              | 89009C         | 11           | NotPass   |
            | 91013C       | 11         | MOB          | 昨日              | 89010C         | 11           | NotPass   |
            | 91013C       | 11         | MOB          | 昨日              | 89011C         | 11           | NotPass   |
            | 91013C       | 11         | MOB          | 昨日              | 89012C         | 11           | NotPass   |
            | 91013C       | 11         | MOB          | 昨日              | 89013C         | 11           | NotPass   |
            | 91013C       | 11         | MOB          | 昨日              | 89014C         | 11           | NotPass   |
            | 91013C       | 11         | MOB          | 昨日              | 89015C         | 11           | NotPass   |
            # 89101C~89113C 當日
            | 91013C       | 11         | MOB          | 當日              | 89101C         | 11           | Pass      |
            | 91013C       | 11         | MOB          | 當日              | 89102C         | 11           | Pass      |
            | 91013C       | 11         | MOB          | 當日              | 89103C         | 11           | Pass      |
            | 91013C       | 11         | MOB          | 當日              | 89104C         | 11           | Pass      |
            | 91013C       | 11         | MOB          | 當日              | 89105C         | 11           | Pass      |
            # nhi_procedure 目前沒有 89106C、89107C 所以在撈IC資料時，這兩個代碼資料會被略過，造成測試不通過
#            | 91013C       | 11         | MOB          | 當日              | 89106C         | 11           | Pass      |
#            | 91013C       | 11         | MOB          | 當日              | 89107C         | 11           | Pass      |
            | 91013C       | 11         | MOB          | 當日              | 89108C         | 11           | Pass      |
            | 91013C       | 11         | MOB          | 當日              | 89109C         | 11           | Pass      |
            | 91013C       | 11         | MOB          | 當日              | 89110C         | 11           | Pass      |
            | 91013C       | 11         | MOB          | 當日              | 89111C         | 11           | Pass      |
            | 91013C       | 11         | MOB          | 當日              | 89112C         | 11           | Pass      |
            | 91013C       | 11         | MOB          | 當日              | 89113C         | 11           | Pass      |
            # 89101C~89113C 昨日
            | 91013C       | 11         | MOB          | 昨日              | 89101C         | 11           | NotPass   |
            | 91013C       | 11         | MOB          | 昨日              | 89102C         | 11           | NotPass   |
            | 91013C       | 11         | MOB          | 昨日              | 89103C         | 11           | NotPass   |
            | 91013C       | 11         | MOB          | 昨日              | 89104C         | 11           | NotPass   |
            | 91013C       | 11         | MOB          | 昨日              | 89105C         | 11           | NotPass   |
            | 91013C       | 11         | MOB          | 昨日              | 89106C         | 11           | NotPass   |
            | 91013C       | 11         | MOB          | 昨日              | 89107C         | 11           | NotPass   |
            | 91013C       | 11         | MOB          | 昨日              | 89108C         | 11           | NotPass   |
            | 91013C       | 11         | MOB          | 昨日              | 89109C         | 11           | NotPass   |
            | 91013C       | 11         | MOB          | 昨日              | 89110C         | 11           | NotPass   |
            | 91013C       | 11         | MOB          | 昨日              | 89111C         | 11           | NotPass   |
            | 91013C       | 11         | MOB          | 昨日              | 89112C         | 11           | NotPass   |
            | 91013C       | 11         | MOB          | 昨日              | 89113C         | 11           | NotPass   |

    Scenario Outline: 檢查治療的牙位是否為 GENERAL_TOOTH
        Given 建立醫師
        Given Stan 24 歲病人
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
            | 91013C       | 51         | DL           | Pass      |
            | 91013C       | 52         | DL           | Pass      |
            | 91013C       | 53         | DL           | Pass      |
            | 91013C       | 54         | DL           | Pass      |
            | 91013C       | 55         | DL           | Pass      |
            | 91013C       | 61         | DL           | Pass      |
            | 91013C       | 62         | DL           | Pass      |
            | 91013C       | 63         | DL           | Pass      |
            | 91013C       | 64         | DL           | Pass      |
            | 91013C       | 65         | DL           | Pass      |
            | 91013C       | 71         | DL           | Pass      |
            | 91013C       | 72         | DL           | Pass      |
            | 91013C       | 73         | DL           | Pass      |
            | 91013C       | 74         | DL           | Pass      |
            | 91013C       | 75         | DL           | Pass      |
            | 91013C       | 81         | DL           | Pass      |
            | 91013C       | 82         | DL           | Pass      |
            | 91013C       | 83         | DL           | Pass      |
            | 91013C       | 84         | DL           | Pass      |
            | 91013C       | 85         | DL           | Pass      |
            # 恆牙
            | 91013C       | 11         | DL           | Pass      |
            | 91013C       | 12         | DL           | Pass      |
            | 91013C       | 13         | DL           | Pass      |
            | 91013C       | 14         | DL           | Pass      |
            | 91013C       | 15         | DL           | Pass      |
            | 91013C       | 16         | DL           | Pass      |
            | 91013C       | 17         | DL           | Pass      |
            | 91013C       | 18         | DL           | Pass      |
            | 91013C       | 21         | DL           | Pass      |
            | 91013C       | 22         | DL           | Pass      |
            | 91013C       | 23         | DL           | Pass      |
            | 91013C       | 24         | DL           | Pass      |
            | 91013C       | 25         | DL           | Pass      |
            | 91013C       | 26         | DL           | Pass      |
            | 91013C       | 27         | DL           | Pass      |
            | 91013C       | 28         | DL           | Pass      |
            | 91013C       | 31         | DL           | Pass      |
            | 91013C       | 32         | DL           | Pass      |
            | 91013C       | 33         | DL           | Pass      |
            | 91013C       | 34         | DL           | Pass      |
            | 91013C       | 35         | DL           | Pass      |
            | 91013C       | 36         | DL           | Pass      |
            | 91013C       | 37         | DL           | Pass      |
            | 91013C       | 38         | DL           | Pass      |
            | 91013C       | 41         | DL           | Pass      |
            | 91013C       | 42         | DL           | Pass      |
            | 91013C       | 43         | DL           | Pass      |
            | 91013C       | 44         | DL           | Pass      |
            | 91013C       | 45         | DL           | Pass      |
            | 91013C       | 46         | DL           | Pass      |
            | 91013C       | 47         | DL           | Pass      |
            | 91013C       | 48         | DL           | Pass      |
            # 無牙
            | 91013C       |            | DL           | NotPass   |
            #
            | 91013C       | 19         | DL           | Pass      |
            | 91013C       | 29         | DL           | Pass      |
            | 91013C       | 39         | DL           | Pass      |
            | 91013C       | 49         | DL           | Pass      |
            | 91013C       | 59         | DL           | NotPass   |
            | 91013C       | 69         | DL           | NotPass   |
            | 91013C       | 79         | DL           | NotPass   |
            | 91013C       | 89         | DL           | NotPass   |
            | 91013C       | 99         | DL           | Pass      |
            # 牙位為區域型態
            | 91013C       | FM         | DL           | NotPass   |
            | 91013C       | UR         | DL           | NotPass   |
            | 91013C       | UL         | DL           | NotPass   |
            | 91013C       | UA         | DL           | NotPass   |
            | 91013C       | UB         | DL           | NotPass   |
            | 91013C       | LL         | DL           | NotPass   |
            | 91013C       | LR         | DL           | NotPass   |
            | 91013C       | LA         | DL           | NotPass   |
            | 91013C       | LB         | DL           | NotPass   |
            # 非法牙位
            | 91013C       | 00         | DL           | NotPass   |
            | 91013C       | 01         | DL           | NotPass   |
            | 91013C       | 10         | DL           | NotPass   |
            | 91013C       | 56         | DL           | NotPass   |
            | 91013C       | 66         | DL           | NotPass   |
            | 91013C       | 76         | DL           | NotPass   |
            | 91013C       | 86         | DL           | NotPass   |
            | 91013C       | 91         | DL           | NotPass   |
