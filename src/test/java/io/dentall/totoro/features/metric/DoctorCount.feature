@metric @meta
Feature: 醫師人數

    Scenario: 計算DoctorCount
        Given 設定指標主體類型為診所
        Given 設定病人 Jerry 24 歲
        Given 設定醫師 Kevin
        Given 設定醫師 Stan
        Given 設定醫師 Sam
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | DoctorName | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2020-05-13   | 00315C   | 635       | 89110C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        |             | Kevin      | Jerry       | 50            |                 |              |
            |            | 2020-05-13   | 00316C   | 635       | 89111C | 400   | 400         | 11    | MOD     | OTHER        | 001        |             | Stan       | Jerry       | 50            |                 |              |
            |            | 2020-05-13   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | 001        |             | Sam        | Jerry       | 50            |                 |              |
        Then 指定執行日期 2020-05-01，來源資料使用 MonthSelectedSource，檢查 DoctorCount，計算結果數值應為 3

