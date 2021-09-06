@metric @meta
Feature: 卡數 處置單有診察費且有卡號或異常代碼，不重複計算

    Scenario: 計算Ic1卡數 (CardNumber為數字)
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        Given 設定病人 Danny 24 歲
        Given 設定病人 Jun 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            | 1          | 2020-04-01   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            | 2          | 2020-04-02   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            | 3          | 2020-04-03   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | 001        |             | Jun         | 50            |                 |              |
            | 4          | 2020-04-30   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | 002        |             | Jun         | 50            |                 |              |
            | 5          | 2020-04-16   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            | 6          | 2020-05-01   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            | 7          | 2020-05-31   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | 002        |             | Jun         | 50            |                 |              |
            | 8          | 2020-06-01   | 00317C   |           | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            | 9          | 2020-06-30   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
        Then 指定執行日期 2020-05-01，來源資料使用 QuarterSource，檢查 Ic1，計算結果數值應為 8


    Scenario: 計算Ic1卡數 (CardNumber為Prevention Code)
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        Given 設定病人 Danny 24 歲
        Given 設定病人 Jun 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            | 1          | 2020-04-01   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | IC001      |             | Jerry       | 50            |                 |              |
            | 2          | 2020-04-02   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | IC001      |             | Danny       | 50            |                 |              |
            | 3          | 2020-04-03   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | IC001      |             | Jun         | 50            |                 |              |
            | 4          | 2020-04-30   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | IC002      |             | Jun         | 50            |                 |              |
            | 5          | 2020-04-16   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | IC001      |             | Jerry       | 50            |                 |              |
            | 6          | 2020-05-01   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | IC001      |             | Danny       | 50            |                 |              |
            | 7          | 2020-05-31   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | IC002      |             | Jun         | 50            |                 |              |
            | 8          | 2020-06-01   | 00317C   |           | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | IC001      |             | Jerry       | 50            |                 |              |
            | 9          | 2020-06-30   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | IC001      |             | Jerry       | 50            |                 |              |
        Then 指定執行日期 2020-05-01，來源資料使用 QuarterSource，檢查 Ic1，計算結果數值應為 8


    Scenario: 計算Ic1卡數 (CardNumber為Error Code)
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        Given 設定病人 Danny 24 歲
        Given 設定病人 Jun 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            | 1          | 2020-04-01   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | D001       |             | Jerry       | 50            |                 |              |
            | 2          | 2020-04-02   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | D001       |             | Danny       | 50            |                 |              |
            | 3          | 2020-04-03   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | D001       |             | Jun         | 50            |                 |              |
            | 4          | 2020-04-30   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | D002       |             | Jun         | 50            |                 |              |
            | 5          | 2020-04-16   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | D001       |             | Jerry       | 50            |                 |              |
            | 6          | 2020-05-01   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | D001       |             | Danny       | 50            |                 |              |
            | 7          | 2020-05-31   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | D002       |             | Jun         | 50            |                 |              |
            | 8          | 2020-06-01   | 00317C   |           | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | D001       |             | Jerry       | 50            |                 |              |
            | 9          | 2020-06-30   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | D001       |             | Jerry       | 50            |                 |              |
        Then 指定執行日期 2020-05-01，來源資料使用 QuarterSource，檢查 Ic1，計算結果數值應為 8
