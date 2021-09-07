@metric @exclude
Feature: Exclude 89013C

    Scenario: 資料無被排除的條件
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | DoctorName | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2020-05-01   | 00315C   | 635       | 89110C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        |             |            | Jerry       | 50            |                 |              |
        And 設定排除 N89013C
        Then 指定執行日期 2020-05-01，來源資料使用 MonthSelectedSource，預期筆數應為 1

    Scenario: 檢查應排除 Code 89013C
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | DoctorName | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2020-05-01   | 00315C   | 635       | 89013C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        |             |            | Jerry       | 50            |                 |              |
        And 設定排除 N89013C
        Then 指定執行日期 2020-05-01，來源資料使用 MonthSelectedSource，預期筆數應為 0

