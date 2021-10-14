@metric @meta
Feature: 合計點數

    Scenario: 計算Point1點數
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2020-05-13   | 00315C   | 635       | 89110C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-13   | 00316C   | 635       | 89111C | 400   | 400         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-13   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
        Then 指定執行日期 2020-05-01，來源資料使用 MonthSelectedSource，檢查 Point1，計算結果數值應為 4355

    Scenario: 計算Point1ByDaily點數
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
        Then 指定執行日期 2020-05-01，來源資料使用 DailyByMonthSelectedSource，檢查 Point1ByDaily，每日數值
            | Date       | Value |
            | 2020-05-01 | 2200  |
            | 2020-05-02 | 1000  |
            | 2020-05-03 | 2300  |

    Scenario: 計算Point1ByDaily點數 國定假日排除點數上限20,000點
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2020-05-02   | 00315C   | 100       | 89110C | 19900 | 1000        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            | 1          | 2020-05-09   | 00316C   | 100       | 89111C | 9901  | 400         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            | 1          | 2020-05-09   | 00316C   | 100       | 89112C | 10000 | 400         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
        And 國定假日排除點數上限20,000點
        Then 指定執行日期 2020-05-01，來源資料使用 DailyByMonthSelectedSource，檢查 Point1ByDaily，每日數值
            | Date       | Value |
            | 2020-05-02 | 0     |
            | 2020-05-09 | 1     |
