@metric @meta
Feature: 洗牙

    Scenario: 計算SC1洗牙點數
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            | 1          | 2020-05-01   | 00315C   | 635       | 91003C | 150   | 1000        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            | 2          | 2020-05-02   | 00316C   | 635       | 91004C | 600   | 400         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            | 3          | 2020-05-03   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
        Then 指定執行日期 2020-05-01，來源資料使用 MonthSelectedSource，檢查 Sc1Point，計算結果數值應為 750

