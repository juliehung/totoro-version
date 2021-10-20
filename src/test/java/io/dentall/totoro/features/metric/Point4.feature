@metric @meta
Feature: 部分負擔點數

    Scenario: 計算Point4點數
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2020-05-13   | 00315C   | 635       | 89110C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-13   | 00316C   | 635       | 89111C | 400   | 400         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-13   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
        Then 指定執行日期 2020-05-01，來源資料使用 MonthSelectedDisposalSource，檢查 Point4，計算結果數值應為 150

    Scenario: 計算Point4點數 部份負擔為0
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2020-05-13   | 00315C   | 635       | 89110C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 0             |                 |              |
            |            | 2020-05-13   | 00316C   | 635       | 89111C | 400   | 400         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 0             |                 |              |
            |            | 2020-05-13   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 0             |                 |              |
        Then 指定執行日期 2020-05-01，來源資料使用 MonthSelectedDisposalSource，檢查 Point4，計算結果數值應為 0

    Scenario: 計算Point4ByDaily點數
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
        Then 指定執行日期 2020-05-01，來源資料使用 DailyByMonthSelectedDisposalSource，檢查 Point4ByDaily，每日數值
            | Date       | Value |
            | 2020-05-01 | 100   |
            | 2020-05-02 | 100   |
            | 2020-05-03 | 100   |
