@metric @meta
Feature: 符合牙醫門診加強感染管制實施方案之牙科門診診察費(不含Xray)

    Scenario: 計算Exam3點數
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            | 1          | 2020-05-13   | 00305C   | 355       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            | 2          | 2020-05-13   | 00306C   | 355       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            | 3          | 2020-05-13   | 00307C   | 155       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            | 4          | 2020-05-13   | 00308C   | 155       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            | 5          | 2020-05-13   | 00309C   | 385       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            | 6          | 2020-05-13   | 00310C   | 385       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            | 7          | 2020-05-13   | 00311C   | 520       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            | 8          | 2020-05-13   | 00312C   | 320       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            | 9          | 2020-05-13   | 00313C   | 320       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            | 10         | 2020-05-13   | 00314C   | 350       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
        Then 指定執行日期 2020-05-01，來源資料使用 MonthSelectedSource，檢查 Exam1，計算結果數值應為 0
        Then 指定執行日期 2020-05-01，來源資料使用 MonthSelectedSource，檢查 Exam2，計算結果數值應為 0
        Then 指定執行日期 2020-05-01，來源資料使用 MonthSelectedSource，檢查 Exam3，計算結果數值應為 3300
        Then 指定執行日期 2020-05-01，來源資料使用 MonthSelectedSource，檢查 Exam4，計算結果數值應為 0

    Scenario: 計算Exam3點數，使用00121C點數計算
        Given 設定指標主體類型為醫師 Stan
        Given 設定使用00121C點數計算
        Given 設定病人 Jerry 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            | 1          | 2020-05-13   | 00305C   | 355       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            | 2          | 2020-05-13   | 00306C   | 355       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            | 3          | 2020-05-13   | 00307C   | 155       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            | 4          | 2020-05-13   | 00308C   | 155       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            | 5          | 2020-05-13   | 00309C   | 385       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            | 6          | 2020-05-13   | 00310C   | 385       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            | 7          | 2020-05-13   | 00311C   | 520       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            | 8          | 2020-05-13   | 00312C   | 320       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            | 9          | 2020-05-13   | 00313C   | 320       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            | 10         | 2020-05-13   | 00314C   | 350       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
        Then 指定執行日期 2020-05-01，來源資料使用 MonthSelectedSource，檢查 Exam1，計算結果數值應為 0
        Then 指定執行日期 2020-05-01，來源資料使用 MonthSelectedSource，檢查 Exam2，計算結果數值應為 0
        Then 指定執行日期 2020-05-01，來源資料使用 MonthSelectedSource，檢查 Exam3，計算結果數值應為 2300
        Then 指定執行日期 2020-05-01，來源資料使用 MonthSelectedSource，檢查 Exam4，計算結果數值應為 0

    Scenario: 計算Exam3點數，排除山地離島診察費差額
        Given 設定指標主體類型為醫師 Stan
        Given 設定排除山地離島診察費差額
        Given 設定病人 Jerry 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            | 1          | 2020-05-13   | 00305C   | 355       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            | 2          | 2020-05-13   | 00306C   | 355       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            | 3          | 2020-05-13   | 00307C   | 155       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            | 4          | 2020-05-13   | 00308C   | 155       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            | 5          | 2020-05-13   | 00309C   | 385       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            | 6          | 2020-05-13   | 00310C   | 385       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            | 7          | 2020-05-13   | 00311C   | 520       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            | 8          | 2020-05-13   | 00312C   | 320       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            | 9          | 2020-05-13   | 00313C   | 320       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            | 10         | 2020-05-13   | 00314C   | 350       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
        Then 指定執行日期 2020-05-01，來源資料使用 MonthSelectedSource，檢查 Exam1，計算結果數值應為 0
        Then 指定執行日期 2020-05-01，來源資料使用 MonthSelectedSource，檢查 Exam2，計算結果數值應為 0
        Then 指定執行日期 2020-05-01，來源資料使用 MonthSelectedSource，檢查 Exam3，計算結果數值應為 3240
        Then 指定執行日期 2020-05-01，來源資料使用 MonthSelectedSource，檢查 Exam4，計算結果數值應為 0

    Scenario: 計算Exam3每日點數
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        Given 設定病人 Danny 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            | 1          | 2020-05-01   | 00305C   | 355       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            | 2          | 2020-05-01   | 00306C   | 355       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            | 3          | 2020-05-03   | 00307C   | 155       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            | 4          | 2020-05-03   | 00308C   | 155       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            | 5          | 2020-05-05   | 00309C   | 385       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            | 6          | 2020-05-05   | 00310C   | 385       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            | 7          | 2020-05-07   | 00311C   | 520       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            | 8          | 2020-05-07   | 00312C   | 320       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            | 9          | 2020-05-09   | 00313C   | 320       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            | 10         | 2020-05-09   | 00314C   | 350       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
        Then 指定執行日期 2020-05-01，來源資料使用 DailyByMonthSelectedSource，檢查 Exam3ByDaily，每日數值
            | Date       | Value |
            | 2020-05-01 | 710   |
            | 2020-05-03 | 310   |
            | 2020-05-05 | 770   |
            | 2020-05-07 | 840   |
            | 2020-05-09 | 670   |

    Scenario: 計算Exam3每日點數，使用00121C點數計算
        Given 設定指標主體類型為醫師 Stan
        Given 設定使用00121C點數計算
        Given 設定病人 Jerry 24 歲
        Given 設定病人 Danny 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            | 1          | 2020-05-01   | 00305C   | 355       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            | 2          | 2020-05-01   | 00306C   | 355       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            | 3          | 2020-05-03   | 00307C   | 155       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            | 4          | 2020-05-03   | 00308C   | 155       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            | 5          | 2020-05-05   | 00309C   | 385       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            | 6          | 2020-05-05   | 00310C   | 385       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            | 7          | 2020-05-07   | 00311C   | 520       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            | 8          | 2020-05-07   | 00312C   | 320       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            | 9          | 2020-05-09   | 00313C   | 320       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            | 10         | 2020-05-09   | 00314C   | 350       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
        Then 指定執行日期 2020-05-01，來源資料使用 DailyByMonthSelectedSource，檢查 Exam3ByDaily，每日數值
            | Date       | Value |
            | 2020-05-01 | 460   |
            | 2020-05-03 | 460   |
            | 2020-05-05 | 460   |
            | 2020-05-07 | 460   |
            | 2020-05-09 | 460   |

    Scenario: 計算Exam3每日點數，排除山地離島診察費差額
        Given 設定指標主體類型為醫師 Stan
        Given 設定排除山地離島診察費差額
        Given 設定病人 Jerry 24 歲
        Given 設定病人 Danny 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            | 1          | 2020-05-01   | 00305C   | 355       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            | 2          | 2020-05-01   | 00306C   | 355       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            | 3          | 2020-05-03   | 00307C   | 155       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            | 4          | 2020-05-03   | 00308C   | 155       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            | 5          | 2020-05-05   | 00309C   | 385       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            | 6          | 2020-05-05   | 00310C   | 385       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            | 7          | 2020-05-07   | 00311C   | 520       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            | 8          | 2020-05-07   | 00312C   | 320       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            | 9          | 2020-05-09   | 00313C   | 320       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            | 10         | 2020-05-09   | 00314C   | 350       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
        Then 指定執行日期 2020-05-01，來源資料使用 DailyByMonthSelectedSource，檢查 Exam3ByDaily，每日數值
            | Date       | Value |
            | 2020-05-01 | 710   |
            | 2020-05-03 | 310   |
            | 2020-05-05 | 710   |
            | 2020-05-07 | 840   |
            | 2020-05-09 | 670   |
