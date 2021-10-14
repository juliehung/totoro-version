@metric @exclude
Feature: Exclude Tro5

    Scenario: 資料無被排除的條件
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | DoctorName | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2020-05-01   | 00315C   | 635       | 89110C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        |             |            | Jerry       | 50            |                 |              |
        And 設定排除 Tro5
        Then 指定執行日期 2020-05-01，來源資料使用 MonthSelectedSource，預期筆數應為 1

    Scenario: 檢查應排除 Category 14
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | DoctorName | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2020-05-01   | 00315C   | 635       | 89110C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        | 14          |            | Jerry       | 50            |                 |              |
        And 設定排除 Tro5
        Then 指定執行日期 2020-05-01，來源資料使用 MonthSelectedSource，預期筆數應為 0

    Scenario: 檢查應排除 Category 16
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | DoctorName | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2020-05-01   | 00315C   | 635       | 89110C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        | 16          |            | Jerry       | 50            |                 |              |
        And 設定排除 Tro5
        Then 指定執行日期 2020-05-01，來源資料使用 MonthSelectedSource，預期筆數應為 0

    Scenario: 檢查應排除 Category A3
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | DoctorName | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2020-05-01   | 00315C   | 635       | 89110C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        | A3          |            | Jerry       | 50            |                 |              |
        And 設定排除 Tro5
        Then 指定執行日期 2020-05-01，來源資料使用 MonthSelectedSource，預期筆數應為 0

    Scenario: 檢查應排除 Code P4001C by Perio1
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | DoctorName | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2020-05-01   | 00315C   | 635       | P4001C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        |             |            | Jerry       | 50            |                 |              |
        And 設定排除 Tro5
        Then 指定執行日期 2020-05-01，來源資料使用 MonthSelectedSource，預期筆數應為 0

    Scenario: 檢查應排除 Code P4002C by Perio1
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | DoctorName | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2020-05-01   | 00315C   | 635       | P4002C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        |             |            | Jerry       | 50            |                 |              |
        And 設定排除 Tro5
        Then 指定執行日期 2020-05-01，來源資料使用 MonthSelectedSource，預期筆數應為 0

    Scenario: 檢查應排除 Code P4003C by Perio1
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | DoctorName | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2020-05-01   | 00315C   | 635       | P4003C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        |             |            | Jerry       | 50            |                 |              |
        And 設定排除 Tro5
        Then 指定執行日期 2020-05-01，來源資料使用 MonthSelectedSource，預期筆數應為 0

    Scenario: 檢查應排除 Code 91021C by Perio1
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | DoctorName | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2020-05-01   | 00315C   | 635       | 91021C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        |             |            | Jerry       | 50            |                 |              |
        And 設定排除 Tro5
        Then 指定執行日期 2020-05-01，來源資料使用 MonthSelectedSource，預期筆數應為 0

    Scenario: 檢查應排除 Code 91022C by Perio1
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | DoctorName | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2020-05-01   | 00315C   | 635       | 91022C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        |             |            | Jerry       | 50            |                 |              |
        And 設定排除 Tro5
        Then 指定執行日期 2020-05-01，來源資料使用 MonthSelectedSource，預期筆數應為 0

    Scenario: 檢查應排除 Code 91023C by Perio1
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | DoctorName | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2020-05-01   | 00315C   | 635       | 91023C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        |             |            | Jerry       | 50            |                 |              |
        And 設定排除 Tro5
        Then 指定執行日期 2020-05-01，來源資料使用 MonthSelectedSource，預期筆數應為 0

    Scenario: 檢查應排除 Code 91015C by Perio2
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | DoctorName | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2020-05-01   | 00315C   | 635       | 91015C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        |             |            | Jerry       | 50            |                 |              |
        And 設定排除 Tro5
        Then 指定執行日期 2020-05-01，來源資料使用 MonthSelectedSource，預期筆數應為 0

    Scenario: 檢查應排除 Code 91016C by Perio2
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | DoctorName | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2020-05-01   | 00315C   | 635       | 91016C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        |             |            | Jerry       | 50            |                 |              |
        And 設定排除 Tro5
        Then 指定執行日期 2020-05-01，來源資料使用 MonthSelectedSource，預期筆數應為 0

    Scenario: 檢查應排除 Code 91018C by Perio2
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | DoctorName | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2020-05-01   | 00315C   | 635       | 91018C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        |             |            | Jerry       | 50            |                 |              |
        And 設定排除 Tro5
        Then 指定執行日期 2020-05-01，來源資料使用 MonthSelectedSource，預期筆數應為 0

    Scenario: 檢查應排除 Code 91014C
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | DoctorName | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2020-05-01   | 00315C   | 635       | 91014C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        |             |            | Jerry       | 50            |                 |              |
        And 設定排除 Tro5
        Then 指定執行日期 2020-05-01，來源資料使用 MonthSelectedSource，預期筆數應為 0

    Scenario: 檢查應排除 Special Code G9
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | DoctorName | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2020-05-01   | 00315C   | 635       | 89110C | 1000  | 1000        | 11    | MOD     | G9           | 001        |             |            | Jerry       | 50            |                 |              |
        And 設定排除 Tro5
        Then 指定執行日期 2020-05-01，來源資料使用 MonthSelectedSource，預期筆數應為 0

    Scenario: 檢查應排除 Special Code JA
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | DoctorName | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2020-05-01   | 00315C   | 635       | 89110C | 1000  | 1000        | 11    | MOD     | JA           | 001        |             |            | Jerry       | 50            |                 |              |
        And 設定排除 Tro5
        Then 指定執行日期 2020-05-01，來源資料使用 MonthSelectedSource，預期筆數應為 0
