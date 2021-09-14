@metric @meta
Feature: 符合牙醫門診加強感染管制實施方案之牙科門診診察費(Xray)

    Scenario Outline: 計算Exam4點數
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2020-05-13   | 00315C   | 635       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-13   | 00316C   | 635       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-13   | 00317C   | 635       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
        Then 指定執行日期 2020-05-01，來源資料使用 OdMonthSelectedSource，檢查 <Meta>，計算結果數值應為 <Value>
        Examples:
            | Meta  | Value |
            | Exam1 | 0     |
            | Exam2 | 0     |
            | Exam3 | 0     |
            | Exam4 | 1905  |

    Scenario Outline: 計算Exam4點數，使用00121C點數計算
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2020-05-13   | 00315C   | 635       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-13   | 00316C   | 635       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-13   | 00317C   | 635       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
        And 設定使用00121C點數計算
        Then 指定執行日期 2020-05-01，來源資料使用 OdMonthSelectedSource，檢查 <Meta>，計算結果數值應為 <Value>
        Examples:
            | Meta  | Value |
            | Exam1 | 0     |
            | Exam2 | 0     |
            | Exam3 | 0     |
            | Exam4 | 690   |

    Scenario Outline: 計算Exam4點數，使用00121C點數計算/Exam2和Exam4，超過1200萬點納入計算
        Given 設定指標主體類型為醫師 Stan
        Given 設定使用00121C點數計算
        Given 設定病人 Jerry 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2020-05-13   | 00315C   | 635       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-13   | 00316C   | 635       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-13   | 00317C   | 12000230  | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
        And 設定Exam2和Exam4，超過1200萬點納入計算
        Then 指定執行日期 2020-05-01，來源資料使用 OdMonthSelectedSource，檢查 <Meta>，計算結果數值應為 <Value>
        Examples:
            | Meta  | Value    |
            | Exam1 | 0        |
            | Exam2 | 0        |
            | Exam3 | 0        |
            | Exam4 | 12001500 |

    # 離島代碼只有Exam1和Exam3才有，如要查看排除山地離島診察費差額效果，請至Exam1.feature和Exam3.feature
    Scenario Outline: 計算Exam4點數，排除山地離島診察費差額
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2020-05-13   | 00315C   | 635       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-13   | 00316C   | 635       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-13   | 00317C   | 635       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
        And 設定排除山地離島診察費差額
        Then 指定執行日期 2020-05-01，來源資料使用 OdMonthSelectedSource，檢查 <Meta>，計算結果數值應為 <Value>
        Examples:
            | Meta  | Value |
            | Exam1 | 0     |
            | Exam2 | 0     |
            | Exam3 | 0     |
            | Exam4 | 1905  |

    Scenario Outline: 計算Exam4點數，排除國定假日點數
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2020-05-01   | 00315C   | 635       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-01   | 00316C   | 635       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-01   | 00317C   | 635       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
        And 排除國定假日點數
        Then 指定執行日期 2020-05-01，來源資料使用 OdMonthSelectedSource，檢查 <Meta>，計算結果數值應為 <Value>
        Examples:
            | Meta  | Value |
            | Exam1 | 0     |
            | Exam2 | 0     |
            | Exam3 | 0     |
            | Exam4 | 0     |

    Scenario: 計算Exam4每日點數
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        Given 設定病人 Danny 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2020-05-01   | 00315C   | 635       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-01   | 00316C   | 635       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-05-03   | 00317C   | 635       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
        Then 指定執行日期 2020-05-01，來源資料使用 DailyByMonthSelectedSource，檢查 Exam4ByDaily，每日數值
            | Date       | Value |
            | 2020-05-01 | 1270  |
            | 2020-05-03 | 635   |

    Scenario: 計算Exam4每日點數，使用00121C點數計算
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        Given 設定病人 Danny 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2020-05-01   | 00315C   | 635       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-01   | 00316C   | 635       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-05-03   | 00317C   | 635       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
        And 設定使用00121C點數計算
        Then 指定執行日期 2020-05-01，來源資料使用 DailyByMonthSelectedSource，檢查 Exam4ByDaily，每日數值
            | Date       | Value |
            | 2020-05-01 | 460   |
            | 2020-05-03 | 230   |

    # 離島代碼只有Exam1和Exam3才有，如要查看排除山地離島診察費差額效果，請至Exam1.feature和Exam3.feature
    Scenario: 計算Exam4每日點數，排除山地離島診察費差額
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        Given 設定病人 Danny 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2020-05-01   | 00315C   | 635       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-01   | 00316C   | 635       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-05-03   | 00317C   | 635       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
        And 設定排除山地離島診察費差額
        Then 指定執行日期 2020-05-01，來源資料使用 DailyByMonthSelectedSource，檢查 Exam4ByDaily，每日數值
            | Date       | Value |
            | 2020-05-01 | 1270  |
            | 2020-05-03 | 635   |

    Scenario: 計算Exam4每日點數，排除國定假日點數
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        Given 設定病人 Danny 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2020-05-01   | 00315C   | 635       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-01   | 00316C   | 635       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-05-03   | 00317C   | 635       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
        And 排除國定假日點數
        Then 指定執行日期 2020-05-01，來源資料使用 DailyByMonthSelectedSource，檢查 Exam4ByDaily，每日數值
            | Date       | Value |
            | 2020-05-01 | 0     |
            | 2020-05-03 | 0     |
