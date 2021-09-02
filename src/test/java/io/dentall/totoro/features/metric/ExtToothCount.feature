@metric @meta
Feature: Ext-ToothCount

  Scenario: 計算Ext-ToothCount
    Given 設定指標主體類型為醫師 Stan
    Given 設定病人 Jerry 24 歲
    When 設定指標資料
      | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
      | 1          | 2020-05-01   | 00315C   | 635       | 92013C | 510   | 510         | 1112  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
      | 2          | 2020-05-02   | 00315C   | 635       | 92014C | 900   | 900         | 2122  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
      | 3          | 2020-05-03   | 00316C   | 635       | 92015C | 2730  | 2730        | 3132  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
      | 4          | 2020-05-04   | 00317C   | 635       | 92016C | 4300  | 4300        | 4142  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
    Then 指定執行日期 2020-05-01，來源資料使用 ExtQuarterSource，檢查 ExtToothCount，計算結果數值應為 8

