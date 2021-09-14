@metric @exclude
Feature: Exclude NhiCategory_SpecificCode_Group1

    Scenario: 無設定任何排除
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | DoctorName | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2020-05-01   | 00315C   | 635       | 89110C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        |             |            | Jerry       | 50            |                 |              |
        And 設定排除 NhiCategory_SpecificCode_Group1
        Then 指定執行日期 2020-05-01，來源資料使用 MonthSelectedSource，預期筆數應為 1

    Scenario: 檢查應排除 Category 14
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | DoctorName | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2020-05-01   | 00315C   | 635       | 89110C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        | 14          |            | Jerry       | 50            |                 |              |
        And 設定排除 NhiCategory_SpecificCode_Group1
        Then 指定執行日期 2020-05-01，來源資料使用 MonthSelectedSource，預期筆數應為 0

    Scenario: 檢查應排除 Category 16
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | DoctorName | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2020-05-01   | 00315C   | 635       | 89110C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        | 16          |            | Jerry       | 50            |                 |              |
        And 設定排除 NhiCategory_SpecificCode_Group1
        Then 指定執行日期 2020-05-01，來源資料使用 MonthSelectedSource，預期筆數應為 0

    Scenario: 檢查應排除 Special Code G9
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | DoctorName | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2020-05-01   | 00315C   | 635       | 89110C | 1000  | 1000        | 11    | MOD     | G9           | 001        |             |            | Jerry       | 50            |                 |              |
        And 設定排除 NhiCategory_SpecificCode_Group1
        Then 指定執行日期 2020-05-01，來源資料使用 MonthSelectedSource，預期筆數應為 0
