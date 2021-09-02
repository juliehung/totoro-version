@metric @meta
Feature: 根管EndoReTreatmentByTooth

  Scenario: 計算EndoReTreatmentByTooth 單人
    Given 設定指標主體類型為醫師 Stan
    Given 設定病人 Jerry 24 歲
    When 設定指標資料
      | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
      | 1          | 2020-05-01   | 00315C   | 635       | 90001C | 1210  | 1210        | 1112  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
      | 2          | 2020-05-02   | 00316C   | 635       | 90002C | 2410  | 2410        | 1112  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
      | 3          | 2020-05-03   | 00317C   | 635       | 90003C | 3610  | 3610        | 1112  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
      | 4          | 2020-05-08   | 00317C   | 635       | 90020C | 6010  | 6010        | 1112  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
      | 5          | 2020-05-04   | 00317C   | 635       | 90015C | 600   | 600         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
      | 6          | 2020-05-05   | 00317C   | 635       | 90001C | 1210  | 1210        | 1112  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
      | 7          | 2020-05-06   | 00317C   | 635       | 90002C | 2410  | 2410        | 1112  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
      | 8          | 2020-05-07   | 00317C   | 635       | 90003C | 3610  | 3610        | 1112  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
      | 9          | 2020-05-08   | 00317C   | 635       | 90020C | 6010  | 6010        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
    Then 指定執行日期 2020-05-01，來源資料使用 MonthSelectedSource，檢查 EndoReTreatmentByTooth，計算結果數值應為 7


  Scenario: 計算EndoReTreatmentByTooth 多人
    Given 設定指標主體類型為醫師 Stan
    Given 設定病人 Jerry 24 歲
    Given 設定病人 Danny 24 歲
    When 設定指標資料
      | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
      | 1          | 2020-05-01   | 00315C   | 635       | 90001C | 1210  | 1210        | 1112  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
      | 2          | 2020-05-02   | 00316C   | 635       | 90002C | 2410  | 2410        | 1112  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
      | 3          | 2020-05-03   | 00317C   | 635       | 90003C | 3610  | 3610        | 1112  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
      | 4          | 2020-05-08   | 00317C   | 635       | 90020C | 6010  | 6010        | 1112  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
      | 5          | 2020-05-04   | 00317C   | 635       | 90015C | 600   | 600         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
      | 6          | 2020-05-05   | 00317C   | 635       | 90001C | 1210  | 1210        | 1112  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
      | 7          | 2020-05-06   | 00317C   | 635       | 90002C | 2410  | 2410        | 1112  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
      | 8          | 2020-05-07   | 00317C   | 635       | 90003C | 3610  | 3610        | 1112  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
      | 9          | 2020-05-08   | 00317C   | 635       | 90020C | 6010  | 6010        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
      | 11         | 2020-05-01   | 00315C   | 635       | 90001C | 1210  | 1210        | 1112  | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
      | 12         | 2020-05-02   | 00316C   | 635       | 90002C | 2410  | 2410        | 1112  | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
      | 13         | 2020-05-03   | 00317C   | 635       | 90003C | 3610  | 3610        | 1112  | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
      | 14         | 2020-05-08   | 00317C   | 635       | 90020C | 6010  | 6010        | 1112  | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
      | 15         | 2020-05-04   | 00317C   | 635       | 90015C | 600   | 600         | 11    | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
      | 16         | 2020-05-05   | 00317C   | 635       | 90001C | 1210  | 1210        | 1112  | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
      | 17         | 2020-05-06   | 00317C   | 635       | 90002C | 2410  | 2410        | 1112  | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
      | 18         | 2020-05-07   | 00317C   | 635       | 90003C | 3610  | 3610        | 1112  | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
      | 19         | 2020-05-08   | 00317C   | 635       | 90020C | 6010  | 6010        | 11    | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
    Then 指定執行日期 2020-05-01，來源資料使用 MonthSelectedSource，檢查 EndoReTreatmentByTooth，計算結果數值應為 14
