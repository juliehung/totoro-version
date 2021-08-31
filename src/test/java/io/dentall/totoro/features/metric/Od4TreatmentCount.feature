@metric @meta
Feature: OD4-TreatmentCount

  Scenario: 計算OD4-TreatmentCount 單人
    Given 設定指標主體類型為醫師 Stan
    Given 設定病人 Jerry 24 歲
    When 設定指標資料
      | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
      | 1          | 2020-05-01   | 00315C   | 635       | 89001C | 450   | 450         | 1112  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
      | 1          | 2020-05-01   | 00315C   | 635       | 89004C | 500   | 500         | 1112  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
      | 3          | 2020-05-02   | 00316C   | 635       | 89008C | 600   | 600         | 1112  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
      | 4          | 2020-05-03   | 00317C   | 635       | 89011C | 400   | 400         | 1112  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
      | 4          | 2020-05-03   | 00317C   | 635       | 89011C | 400   | 400         | 1314  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
    Then 指定執行日期 2020-05-01，來源資料使用 OD1個月，檢查 Od4TreatmentCount，計算結果數值應為 5
    Then 指定執行日期 2020-05-01，來源資料使用 OD1個月，檢查 Od5TreatmentCount，計算結果數值應為 0
    Then 指定執行日期 2020-05-01，來源資料使用 OD1個月，檢查 Od6TreatmentCount，計算結果數值應為 0

  Scenario: 計算OD4-TreatmentCount 多人
    Given 設定指標主體類型為醫師 Stan
    Given 設定病人 Jerry 24 歲
    Given 設定病人 Dany 24 歲
    When 設定指標資料
      | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
      | 1          | 2020-05-01   | 00315C   | 635       | 89001C | 450   | 450         | 1112  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
      | 1          | 2020-05-01   | 00315C   | 635       | 89004C | 500   | 500         | 1112  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
      | 3          | 2020-05-02   | 00316C   | 635       | 89008C | 600   | 600         | 1112  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
      | 4          | 2020-05-03   | 00317C   | 635       | 89011C | 400   | 400         | 1112  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
      | 4          | 2020-05-03   | 00317C   | 635       | 89011C | 400   | 400         | 1314  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
      | 11         | 2020-05-11   | 00315C   | 635       | 89001C | 450   | 450         | 1112  | MOD     | OTHER        | 001        |             | Dany        | 50            |                 |              |
      | 11         | 2020-05-11   | 00315C   | 635       | 89004C | 500   | 500         | 1112  | MOD     | OTHER        | 001        |             | Dany        | 50            |                 |              |
      | 13         | 2020-05-12   | 00316C   | 635       | 89008C | 600   | 600         | 1112  | MOD     | OTHER        | 001        |             | Dany        | 50            |                 |              |
      | 14         | 2020-05-13   | 00317C   | 635       | 89011C | 400   | 400         | 1112  | MOD     | OTHER        | 001        |             | Dany        | 50            |                 |              |
      | 14         | 2020-05-13   | 00317C   | 635       | 89011C | 400   | 400         | 1314  | MOD     | OTHER        | 001        |             | Dany        | 50            |                 |              |
    Then 指定執行日期 2020-05-01，來源資料使用 OD1個月，檢查 Od4TreatmentCount，計算結果數值應為 10
    Then 指定執行日期 2020-05-01，來源資料使用 OD1個月，檢查 Od5TreatmentCount，計算結果數值應為 0
    Then 指定執行日期 2020-05-01，來源資料使用 OD1個月，檢查 Od6TreatmentCount，計算結果數值應為 0

