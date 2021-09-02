@metric @meta
Feature: OD-Deciduous-ToothCount

  Scenario: 計算OD-Deciduous-ToothCount
    Given 設定指標主體類型為醫師 Stan
    Given 設定病人 Jerry 24 歲
    When 設定指標資料
      | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
      | 1          | 2020-05-01   | 00315C   | 635       | 89001C | 450   | 450         | 5152  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
      | 1          | 2020-05-01   | 00315C   | 635       | 89001C | 450   | 450         | 6162  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
      | 2          | 2020-05-02   | 00316C   | 635       | 89002C | 600   | 600         | 7172  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
      | 3          | 2020-05-03   | 00317C   | 635       | 89003C | 750   | 750         | 8182  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
    Then 指定執行日期 2020-05-01，來源資料使用 OdDeciduousQuarterByPatientSource，檢查 OdDeciduousToothCount，計算結果數值應為 8

