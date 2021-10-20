@metric @meta
Feature: 診療費 病患點數(最高者)

  Scenario: 計算HighestPoint1Patient
    Given 設定指標主體類型為醫師 Stan
    Given 設定病人 Jerry 24 歲
    Given 設定病人 Danny 24 歲
    Given 設定病人 Jun 24 歲
    When 設定指標資料
        | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
        |            | 2020-05-13   | 00315C   | 635       | 89110C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
        |            | 2020-05-13   | 00315C   | 635       | 89110C | 2000  | 1000        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
        |            | 2020-05-13   | 00316C   | 635       | 89111C | 400   | 400         | 11    | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
        |            | 2020-05-13   | 00316C   | 635       | 89111C | 500   | 400         | 11    | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
        |            | 2020-05-13   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | 001        |             | Jun         | 50            |                 |              |
        |            | 2020-05-13   | 00317C   | 635       | 89112C | 2050  | 1050        | 11    | MOD     | OTHER        | 001        |             | Jun         | 50            |                 |              |
      Then 指定執行日期 2020-05-01，來源資料使用 MonthSelectedSource／MonthSelectedDisposalSource，檢查 HighestPoint1Patient，計算結果應為 { "value" : 40.43 }
