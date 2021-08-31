@metric @meta
Feature: OD-Permanent-ToothCount

  Scenario: 計算OD-Permanent-ToothCount
    Given 設定指標主體類型為醫師 Stan
    Given 設定病人 Jerry 24 歲
    When 設定指標資料
      | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
      | 1          | 2020-05-01   | 00315C   | 635       | 89001C | 450   | 450         | 1112  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
      | 1          | 2020-05-01   | 00315C   | 635       | 89001C | 450   | 450         | 2122  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
      | 2          | 2020-05-02   | 00316C   | 635       | 89002C | 600   | 600         | 3132  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
      | 3          | 2020-05-03   | 00317C   | 635       | 89003C | 750   | 750         | 4142  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
    Then 指定執行日期 2020-05-01，來源資料使用 OD季恆牙，檢查 OdPermanentToothCount，計算結果數值應為 8

