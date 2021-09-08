@metric @meta
Feature: 診療費

    Scenario: 計算Point3點數
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2020-05-13   | 00315C   | 635       | 89110C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-13   | 00316C   | 635       | 89111C | 400   | 400         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-13   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
        Then 指定執行日期 2020-05-01，來源資料使用 MonthSelectedSource，檢查 Point3，計算結果數值應為 2450

    Scenario: 計算Point3點數 使用原始點數計算
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2020-05-13   | 00315C   | 635       | 89110C | 2000  | 1000        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-13   | 00316C   | 635       | 89111C | 800   | 400         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-13   | 00317C   | 635       | 89112C | 2100  | 1050        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
        And 使用原始點數計算
        Then 指定執行日期 2020-05-01，來源資料使用 MonthSelectedSource，檢查 Point3，計算結果數值應為 2450

    Scenario: 計算Point3點數 排除國定假日點數
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2020-05-16   | 00315C   | 635       | 89110C | 2000  | 1000        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-16   | 00316C   | 635       | 89111C | 800   | 400         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-16   | 00317C   | 635       | 89112C | 2100  | 1050        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
        And 排除國定假日點數
        Then 指定執行日期 2020-05-01，來源資料使用 MonthSelectedSource，檢查 Point3，計算結果數值應為 0

    Scenario: 計算Point3ByDaily點數
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2020-05-01   | 00315C   | 100       | 89110C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-01   | 00315C   | 100       | 89110C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-02   | 00316C   | 100       | 89111C | 400   | 400         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-02   | 00316C   | 100       | 89111C | 400   | 400         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-03   | 00317C   | 100       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-03   | 00317C   | 100       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
        Then 指定執行日期 2020-05-01，來源資料使用 DailyByMonthSelectedSource，檢查 Point3ByDaily，每日數值
            | Date       | Value |
            | 2020-05-01 | 2000  |
            | 2020-05-02 | 800   |
            | 2020-05-03 | 2100  |
