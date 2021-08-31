@metric @meta
Feature: 符合牙醫門診加強感染管制實施方案之牙科門診診察費(Xray)

  Scenario: 計算Exam4點數
    Given 設定指標主體類型為醫師 Stan
    Given 設定病人 Jerry 24 歲
    When 設定指標資料
      | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
      | 1          | 2020-05-13   | 00315C   | 635       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
      | 2          | 2020-05-13   | 00316C   | 635       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
      | 3          | 2020-05-13   | 00317C   | 635       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
    Then 指定執行日期 2020-05-01，來源資料使用 1個月，檢查 Exam1，計算結果數值應為 0
    Then 指定執行日期 2020-05-01，來源資料使用 1個月，檢查 Exam2，計算結果數值應為 0
    Then 指定執行日期 2020-05-01，來源資料使用 1個月，檢查 Exam3，計算結果數值應為 0
    Then 指定執行日期 2020-05-01，來源資料使用 1個月，檢查 Exam4，計算結果數值應為 1905

  Scenario: 計算Exam4點數，使用00121C點數計算
    Given 設定指標主體類型為醫師 Stan
    Given 設定使用00121C點數計算
    Given 設定病人 Jerry 24 歲
    When 設定指標資料
      | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
      | 1          | 2020-05-13   | 00315C   | 635       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
      | 2          | 2020-05-13   | 00316C   | 635       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
      | 3          | 2020-05-13   | 00317C   | 635       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
    Then 指定執行日期 2020-05-01，來源資料使用 1個月，檢查 Exam1，計算結果數值應為 0
    Then 指定執行日期 2020-05-01，來源資料使用 1個月，檢查 Exam2，計算結果數值應為 0
    Then 指定執行日期 2020-05-01，來源資料使用 1個月，檢查 Exam3，計算結果數值應為 0
    Then 指定執行日期 2020-05-01，來源資料使用 1個月，檢查 Exam4，計算結果數值應為 690

  Scenario: 計算Exam4點數，使用00121C點數計算/Exam2和Exam4，超過1200萬點納入計算
    Given 設定指標主體類型為醫師 Stan
    Given 設定使用00121C點數計算
    Given 設定Exam2和Exam4，超過1200萬點納入計算
    Given 設定病人 Jerry 24 歲
    When 設定指標資料
      | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
      | 1          | 2020-05-13   | 00315C   | 635       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
      | 2          | 2020-05-13   | 00316C   | 635       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
      | 3          | 2020-05-13   | 00317C   | 12000230  | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
    Then 指定執行日期 2020-05-01，來源資料使用 1個月，檢查 Exam1，計算結果數值應為 0
    Then 指定執行日期 2020-05-01，來源資料使用 1個月，檢查 Exam2，計算結果數值應為 0
    Then 指定執行日期 2020-05-01，來源資料使用 1個月，檢查 Exam3，計算結果數值應為 0
    Then 指定執行日期 2020-05-01，來源資料使用 1個月，檢查 Exam4，計算結果數值應為 12001500

  Scenario: 計算Exam4點數，排除山地離島診察費差額
    Given 設定指標主體類型為醫師 Stan
    Given 設定排除山地離島診察費差額
    Given 設定病人 Jerry 24 歲
    When 設定指標資料
      | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
      | 1          | 2020-05-13   | 00315C   | 635       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
      | 2          | 2020-05-13   | 00316C   | 635       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
      | 3          | 2020-05-13   | 00317C   | 635       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
    Then 指定執行日期 2020-05-01，來源資料使用 1個月，檢查 Exam1，計算結果數值應為 0
    Then 指定執行日期 2020-05-01，來源資料使用 1個月，檢查 Exam2，計算結果數值應為 0
    Then 指定執行日期 2020-05-01，來源資料使用 1個月，檢查 Exam3，計算結果數值應為 0
    Then 指定執行日期 2020-05-01，來源資料使用 1個月，檢查 Exam4，計算結果數值應為 1905

  Scenario: 計算Exam4每日點數
    Given 設定指標主體類型為醫師 Stan
    Given 設定病人 Jerry 24 歲
    Given 設定病人 Danny 24 歲
    When 設定指標資料
      | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
      | 1          | 2020-05-01   | 00315C   | 635       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
      | 2          | 2020-05-01   | 00316C   | 635       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
      | 3          | 2020-05-03   | 00317C   | 635       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
    Then 指定執行日期 2020-05-01，來源資料使用 每日1個月，檢查 Exam4ByDaily，每日數值
      | Date       | Value |
      | 2020-05-01 | 1270  |
      | 2020-05-03 | 635   |

  Scenario: 計算Exam4每日點數，使用00121C點數計算
    Given 設定指標主體類型為醫師 Stan
    Given 設定使用00121C點數計算
    Given 設定病人 Jerry 24 歲
    Given 設定病人 Danny 24 歲
    When 設定指標資料
      | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
      | 1          | 2020-05-01   | 00315C   | 635       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
      | 2          | 2020-05-01   | 00316C   | 635       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
      | 3          | 2020-05-03   | 00317C   | 635       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
    Then 指定執行日期 2020-05-01，來源資料使用 每日1個月，檢查 Exam4ByDaily，每日數值
      | Date       | Value |
      | 2020-05-01 | 460   |
      | 2020-05-03 | 230   |

  Scenario: 計算Exam4每日點數，排除山地離島診察費差額
    Given 設定指標主體類型為醫師 Stan
    Given 設定排除山地離島診察費差額
    Given 設定病人 Jerry 24 歲
    Given 設定病人 Danny 24 歲
    When 設定指標資料
      | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
      | 1          | 2020-05-01   | 00315C   | 635       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
      | 2          | 2020-05-01   | 00316C   | 635       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
      | 3          | 2020-05-03   | 00317C   | 635       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
    Then 指定執行日期 2020-05-01，來源資料使用 每日1個月，檢查 Exam4ByDaily，每日數值
      | Date       | Value |
      | 2020-05-01 | 1270  |
      | 2020-05-03 | 635   |
