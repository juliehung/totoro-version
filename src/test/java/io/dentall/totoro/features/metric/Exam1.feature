@metric @meta
Feature: 一般牙科門診診察費(不含Xray)

    Scenario Outline: 計算Exam1點數
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2020-05-13   | 00121C   | 230       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-13   | 00122C   | 230       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-13   | 00123C   | 120       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-13   | 00124C   | 120       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-13   | 00125C   | 260       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-13   | 00126C   | 260       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-13   | 00128C   | 520       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-13   | 00129C   | 320       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-13   | 00130C   | 320       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-13   | 00133C   | 350       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-13   | 00134C   | 350       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-13   | 00301C   | 420       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-13   | 00302C   | 320       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-13   | 00303C   | 320       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-13   | 00304C   | 200       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
        Then 指定執行日期 2020-05-01，來源資料使用 MonthSelectedDisposalSource，檢查 <Meta>，計算結果數值應為 <Value>
        Examples:
            | Meta  | Value |
            | Exam1 | 4340  |
            | Exam2 | 0     |
            | Exam3 | 0     |
            | Exam4 | 0     |

    Scenario Outline: 計算Exam1點數，使用00121C點數計算
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2020-05-13   | 00121C   | 230       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-13   | 00122C   | 230       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-13   | 00123C   | 120       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-13   | 00124C   | 120       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-13   | 00125C   | 260       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-13   | 00126C   | 260       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-13   | 00128C   | 520       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-13   | 00129C   | 320       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-13   | 00130C   | 320       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-13   | 00133C   | 350       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-13   | 00134C   | 350       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-13   | 00301C   | 420       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-13   | 00302C   | 320       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-13   | 00303C   | 320       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-13   | 00304C   | 200       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
        And 設定使用00121C點數計算
        Then 指定執行日期 2020-05-01，來源資料使用 MonthSelectedDisposalSource，檢查 <Meta>，計算結果數值應為 <Value>
        Examples:
            | Meta  | Value |
            | Exam1 | 3450  |
            | Exam2 | 0     |
            | Exam3 | 0     |
            | Exam4 | 0     |

    Scenario Outline: 計算Exam1點數，排除山地離島診察費差額
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2020-05-13   | 00121C   | 230       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-13   | 00122C   | 230       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-13   | 00123C   | 120       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-13   | 00124C   | 120       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-13   | 00125C   | 260       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-13   | 00126C   | 260       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-13   | 00128C   | 520       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-13   | 00129C   | 320       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-13   | 00130C   | 320       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-13   | 00133C   | 350       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-13   | 00134C   | 350       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-13   | 00301C   | 420       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-13   | 00302C   | 320       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-13   | 00303C   | 320       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-13   | 00304C   | 200       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
        And 設定排除山地離島診察費差額
        Then 指定執行日期 2020-05-01，來源資料使用 MonthSelectedDisposalSource，檢查 <Meta>，計算結果數值應為 <Value>
        Examples:
            | Meta  | Value |
            # 230 + 230 + 120 + 120 + (260 - 30) + (260 - 30) + 520 + 320 + 320 + 350 + 350 + 420 + 320 + 320 + 200
            | Exam1 | 4280  |
            | Exam2 | 0     |
            | Exam3 | 0     |
            | Exam4 | 0     |

    Scenario Outline: 計算Exam1點數，排除國定假日點數
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2020-05-01   | 00121C   | 230       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-01   | 00122C   | 230       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-01   | 00123C   | 120       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-01   | 00124C   | 120       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-01   | 00125C   | 260       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-01   | 00126C   | 260       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-01   | 00128C   | 520       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-01   | 00129C   | 320       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-01   | 00130C   | 320       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-01   | 00133C   | 350       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-01   | 00134C   | 350       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-01   | 00301C   | 420       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-01   | 00302C   | 320       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-01   | 00303C   | 320       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-01   | 00304C   | 200       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
        And 排除國定假日點數
        Then 指定執行日期 2020-05-01，來源資料使用 MonthSelectedDisposalSource，檢查 <Meta>，計算結果數值應為 <Value>
        Examples:
            | Meta  | Value |
            | Exam1 | 0     |
            | Exam2 | 0     |
            | Exam3 | 0     |
            | Exam4 | 0     |

    Scenario: 計算Exam1每日點數
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        Given 設定病人 Danny 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2020-05-01   | 00121C   | 230       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-01   | 00122C   | 230       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-05-03   | 00123C   | 120       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-03   | 00124C   | 120       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-05-05   | 00125C   | 260       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-05   | 00126C   | 260       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-05-07   | 00128C   | 520       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-07   | 00129C   | 320       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-05-09   | 00130C   | 320       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-09   | 00133C   | 350       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-05-11   | 00134C   | 350       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-11   | 00301C   | 420       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-05-13   | 00302C   | 320       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-13   | 00303C   | 320       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-05-15   | 00304C   | 200       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-15   | 00304C   | 200       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
        Then 指定執行日期 2020-05-01，來源資料使用 DailyByMonthSelectedDisposalSource，檢查 Exam1ByDaily，每日數值
            | Date       | Value |
            | 2020-05-01 | 460   |
            | 2020-05-03 | 240   |
            | 2020-05-05 | 520   |
            | 2020-05-07 | 840   |
            | 2020-05-09 | 670   |
            | 2020-05-11 | 770   |
            | 2020-05-13 | 640   |
            | 2020-05-15 | 400   |

    Scenario: 計算Exam1每日點數，使用00121C點數計算
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        Given 設定病人 Danny 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            | 1          | 2020-05-01   | 00121C   | 230       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            | 2          | 2020-05-01   | 00122C   | 230       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            | 3          | 2020-05-03   | 00123C   | 120       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            | 4          | 2020-05-03   | 00124C   | 120       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            | 5          | 2020-05-05   | 00125C   | 260       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            | 6          | 2020-05-05   | 00126C   | 260       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            | 7          | 2020-05-07   | 00128C   | 520       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            | 8          | 2020-05-07   | 00129C   | 320       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            | 9          | 2020-05-09   | 00130C   | 320       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            | 10         | 2020-05-09   | 00133C   | 350       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            | 11         | 2020-05-11   | 00134C   | 350       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            | 12         | 2020-05-11   | 00301C   | 420       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            | 13         | 2020-05-13   | 00302C   | 320       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            | 14         | 2020-05-13   | 00303C   | 320       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            | 15         | 2020-05-15   | 00304C   | 200       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            | 16         | 2020-05-15   | 00304C   | 200       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
        And 設定使用00121C點數計算
        Then 指定執行日期 2020-05-01，來源資料使用 DailyByMonthSelectedDisposalSource，檢查 Exam1ByDaily，每日數值
            | Date       | Value |
            | 2020-05-01 | 460   |
            | 2020-05-03 | 460   |
            | 2020-05-05 | 460   |
            | 2020-05-07 | 460   |
            | 2020-05-09 | 460   |
            | 2020-05-11 | 460   |
            | 2020-05-13 | 460   |
            | 2020-05-15 | 460   |

    Scenario: 計算Exam1每日點數，排除山地離島診察費差額
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        Given 設定病人 Danny 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2020-05-01   | 00121C   | 230       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-01   | 00122C   | 230       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-05-03   | 00123C   | 120       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-03   | 00124C   | 120       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-05-05   | 00125C   | 260       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-05   | 00126C   | 260       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-05-07   | 00128C   | 520       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-07   | 00129C   | 320       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-05-09   | 00130C   | 320       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-09   | 00133C   | 350       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-05-11   | 00134C   | 350       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-11   | 00301C   | 420       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-05-13   | 00302C   | 320       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-13   | 00303C   | 320       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-05-15   | 00304C   | 200       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-15   | 00304C   | 200       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
        And 設定排除山地離島診察費差額
        Then 指定執行日期 2020-05-01，來源資料使用 DailyByMonthSelectedDisposalSource，檢查 Exam1ByDaily，每日數值
            | Date       | Value |
            | 2020-05-01 | 460   |
            | 2020-05-03 | 240   |
            # (260 - 30) + (260 - 30)
            | 2020-05-05 | 460   |
            | 2020-05-07 | 840   |
            | 2020-05-09 | 670   |
            | 2020-05-11 | 770   |
            | 2020-05-13 | 640   |
            | 2020-05-15 | 400   |

    Scenario: 計算Exam1每日點數，排除國定假日點數
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        Given 設定病人 Danny 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2020-05-01   | 00121C   | 230       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-01   | 00122C   | 230       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-05-03   | 00123C   | 120       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-03   | 00124C   | 120       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-05-05   | 00125C   | 260       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-05   | 00126C   | 260       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-05-07   | 00128C   | 520       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-07   | 00129C   | 320       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-05-09   | 00130C   | 320       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-09   | 00133C   | 350       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-05-11   | 00134C   | 350       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-11   | 00301C   | 420       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-05-13   | 00302C   | 320       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-13   | 00303C   | 320       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-05-15   | 00304C   | 200       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-15   | 00304C   | 200       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
        And 排除國定假日點數
        Then 指定執行日期 2020-05-01，來源資料使用 DailyByMonthSelectedDisposalSource，檢查 Exam1ByDaily，每日數值
            | Date       | Value |
            | 2020-05-01 | 0     |
            | 2020-05-03 | 0     |
            | 2020-05-05 | 520   |
            | 2020-05-07 | 840   |
            | 2020-05-09 | 0     |
            | 2020-05-11 | 770   |
            | 2020-05-13 | 640   |
            | 2020-05-15 | 400   |
