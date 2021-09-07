@metric @meta
Feature: 根管EndoTreatmentByTooth

    Scenario: 計算EndoTreatmentByTooth
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2020-05-01   | 00315C   | 635       | 90001C | 1210  | 1210        | 1112  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-02   | 00316C   | 635       | 90002C | 2410  | 2410        | 1112  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-03   | 00317C   | 635       | 90003C | 3610  | 3610        | 1112  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-04   | 00317C   | 635       | 90015C | 600   | 600         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-05   | 00317C   | 635       | 90016C | 1010  | 1010        | 1112  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-06   | 00317C   | 635       | 90018C | 1690  | 1690        | 1112  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-07   | 00317C   | 635       | 90019C | 4810  | 4810        | 1112  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-08   | 00317C   | 635       | 90020C | 6010  | 6010        | 1112  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
        Then 指定執行日期 2020-05-01，來源資料使用 MonthSelectedSource，檢查 EndoTreatmentByTooth，計算結果數值應為 14

