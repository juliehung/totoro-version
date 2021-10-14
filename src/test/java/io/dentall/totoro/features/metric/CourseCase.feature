@metric @meta
Feature: 醫類別為 AA, AB 的處置單數量總和

    Scenario: 計算CourseCase
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            | 1          | 2020-05-13   | 00315C   | 635       | 89110C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        | AA          | Jerry       | 50            |                 |              |
            | 1          | 2020-05-13   | 00315C   | 635       | 89110C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        | AA          | Jerry       | 50            |                 |              |
            |            | 2020-05-13   | 00316C   | 635       | 89111C | 400   | 400         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-13   | 00316C   | 635       | 89111C | 400   | 400         | 11    | MOD     | OTHER        | 001        | GG          | Jerry       | 50            |                 |              |
            |            | 2020-05-13   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | 001        | AA          | Jerry       | 50            |                 |              |
        Then 指定執行日期 2020-05-01，來源資料使用 MonthSelectedSource，檢查 CourseCase，計算結果數值應為 2

    Scenario: 計算CourseCase
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            | 1          | 2020-05-13   | 00315C   | 635       | 89110C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        | AB          | Jerry       | 50            |                 |              |
            | 1          | 2020-05-13   | 00315C   | 635       | 89110C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        | AB          | Jerry       | 50            |                 |              |
            |            | 2020-05-13   | 00316C   | 635       | 89111C | 400   | 400         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-13   | 00316C   | 635       | 89111C | 400   | 400         | 11    | MOD     | OTHER        | 001        | GG          | Jerry       | 50            |                 |              |
            |            | 2020-05-13   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | 001        | AB          | Jerry       | 50            |                 |              |
        Then 指定執行日期 2020-05-01，來源資料使用 MonthSelectedSource，檢查 CourseCase，計算結果數值應為 2

    Scenario: 計算CourseCase
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            | 1          | 2020-05-13   | 00315C   | 635       | 89110C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        | AA          | Jerry       | 50            |                 |              |
            | 1          | 2020-05-13   | 00315C   | 635       | 89110C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        | AA          | Jerry       | 50            |                 |              |
            |            | 2020-05-13   | 00316C   | 635       | 89111C | 400   | 400         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-13   | 00316C   | 635       | 89111C | 400   | 400         | 11    | MOD     | OTHER        | 001        | GG          | Jerry       | 50            |                 |              |
            |            | 2020-05-13   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | 001        | AB          | Jerry       | 50            |                 |              |
        Then 指定執行日期 2020-05-01，來源資料使用 MonthSelectedSource，檢查 CourseCase，計算結果數值應為 2
