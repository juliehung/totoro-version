@metric @meta
Feature: 看診人數/就醫人數 不重複病患數量

    Scenario: 計算PT1看診人數 (CardNumber為數字)
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        Given 設定病人 Danny 24 歲
        Given 設定病人 Jun 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            | 1          | 2020-04-01   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            | 1          | 2020-04-01   | 00317C   | 635       | 89112C | 1050  | 1050        | 21    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-04-02   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-04-03   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | 001        |             | Jun         | 50            |                 |              |
            |            | 2020-04-30   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | 002        |             | Jun         | 50            |                 |              |
            |            | 2020-04-16   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-01   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-05-31   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | 002        |             | Jun         | 50            |                 |              |
            |            | 2020-06-01   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        |            |             | Jerry       | 50            |                 |              |
            |            | 2020-06-30   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
        Then 指定執行日期 2020-05-01，來源資料使用 QuarterSource，檢查 Pt1，計算結果數值應為 7


    Scenario: 計算PT1看診人數 (CardNumber為Prevention Code)
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        Given 設定病人 Danny 24 歲
        Given 設定病人 Jun 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            | 1          | 2020-04-01   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | IC001      |             | Jerry       | 50            |                 |              |
            | 1          | 2020-04-01   | 00317C   | 635       | 89112C | 1050  | 1050        | 21    | MOD     | OTHER        | IC001      |             | Jerry       | 50            |                 |              |
            |            | 2020-04-02   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | IC001      |             | Danny       | 50            |                 |              |
            |            | 2020-04-03   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | IC001      |             | Jun         | 50            |                 |              |
            |            | 2020-04-30   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | IC002      |             | Jun         | 50            |                 |              |
            |            | 2020-04-16   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | IC001      |             | Jerry       | 50            |                 |              |
            |            | 2020-05-01   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | IC001      |             | Danny       | 50            |                 |              |
            |            | 2020-05-31   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | IC002      |             | Jun         | 50            |                 |              |
            |            | 2020-06-01   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        |            |             | Jerry       | 50            |                 |              |
            |            | 2020-06-30   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | IC001      |             | Jerry       | 50            |                 |              |
        Then 指定執行日期 2020-05-01，來源資料使用 QuarterSource，檢查 Pt1，計算結果數值應為 8


    Scenario: 計算PT1看診人數 (CardNumber為Error Code)
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        Given 設定病人 Danny 24 歲
        Given 設定病人 Jun 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            | 1          | 2020-04-01   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | D001       |             | Jerry       | 50            |                 |              |
            | 1          | 2020-04-01   | 00317C   | 635       | 89112C | 1050  | 1050        | 21    | MOD     | OTHER        | D001       |             | Jerry       | 50            |                 |              |
            |            | 2020-04-02   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | D001       |             | Danny       | 50            |                 |              |
            |            | 2020-04-03   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | D001       |             | Jun         | 50            |                 |              |
            |            | 2020-04-30   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | D002       |             | Jun         | 50            |                 |              |
            |            | 2020-04-16   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | D001       |             | Jerry       | 50            |                 |              |
            |            | 2020-05-01   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | D001       |             | Danny       | 50            |                 |              |
            |            | 2020-05-31   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | D002       |             | Jun         | 50            |                 |              |
            |            | 2020-06-01   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        |            |             | Jerry       | 50            |                 |              |
            |            | 2020-06-30   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | D001       |             | Jerry       | 50            |                 |              |
        Then 指定執行日期 2020-05-01，來源資料使用 QuarterSource，檢查 Pt1，計算結果數值應為 8
