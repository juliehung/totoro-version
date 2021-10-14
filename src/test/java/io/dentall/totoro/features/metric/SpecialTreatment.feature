@metric @meta
Feature: 特定治療項目

    Scenario: SpecialTreatment
        Given 設定指標主體類型為診所
        Given 設定病人 Jerry 24 歲
        Given 設定醫師 Kevin
        Given 設定醫師 Stan
        Given 設定醫師 Sam
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | DoctorName | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2020-05-13   | 00315C   | 635       | 89110C | 100   | 100         | 11    | MOD     | P1           | 001        |             | Kevin      | Jerry       | 50            |                 |              |
            |            | 2020-05-13   | 00315C   | 635       | 89110C | 100   | 100         | 11    | MOD     | P1           | 001        |             | Stan       | Jerry       | 50            |                 |              |
            |            | 2020-05-13   | 00316C   | 635       | 89110C | 100   | 100         | 11    | MOD     | P1           | 001        |             | Sam        | Jerry       | 50            |                 |              |
            |            | 2020-05-13   | 00316C   | 635       | 89110C | 200   | 200         | 11    | MOD     | P2           | 001        |             | Kevin      | Jerry       | 50            |                 |              |
            |            | 2020-05-13   | 00317C   | 635       | 89110C | 200   | 200         | 11    | MOD     | P2           | 001        |             | Stan       | Jerry       | 50            |                 |              |
            |            | 2020-05-13   | 00317C   | 635       | 89110C | 200   | 200         | 11    | MOD     | P2           | 001        |             | Sam        | Jerry       | 50            |                 |              |
            |            | 2020-05-13   | 00316C   | 635       | 89110C | 300   | 300         | 11    | MOD     | P3           | 001        |             | Kevin      | Jerry       | 50            |                 |              |
            |            | 2020-05-13   | 00317C   | 635       | 89110C | 300   | 300         | 11    | MOD     | P3           | 001        |             | Stan       | Jerry       | 50            |                 |              |
            |            | 2020-05-13   | 00317C   | 635       | 89110C | 300   | 300         | 11    | MOD     | P3           | 001        |             | Sam        | Jerry       | 50            |                 |              |
            |            | 2020-05-13   | 00316C   | 635       | 89110C | 400   | 400         | 11    | MOD     | P4           | 001        |             | Kevin      | Jerry       | 50            |                 |              |
            |            | 2020-05-13   | 00317C   | 635       | 89110C | 400   | 400         | 11    | MOD     | P4           | 001        |             | Stan       | Jerry       | 50            |                 |              |
            |            | 2020-05-13   | 00317C   | 635       | 89110C | 400   | 400         | 11    | MOD     | P4           | 001        |             | Sam        | Jerry       | 50            |                 |              |
            |            | 2020-05-13   | 00316C   | 635       | 89110C | 500   | 500         | 11    | MOD     | P5           | 001        |             | Kevin      | Jerry       | 50            |                 |              |
            |            | 2020-05-13   | 00317C   | 635       | 89110C | 500   | 500         | 11    | MOD     | P5           | 001        |             | Stan       | Jerry       | 50            |                 |              |
            |            | 2020-05-13   | 00317C   | 635       | 89110C | 500   | 500         | 11    | MOD     | P5           | 001        |             | Sam        | Jerry       | 50            |                 |              |
            |            | 2020-05-13   | 00316C   | 635       | 89110C | 600   | 600         | 11    | MOD     | P6           | 001        |             | Kevin      | Jerry       | 50            |                 |              |
            |            | 2020-05-13   | 00317C   | 635       | 89110C | 600   | 600         | 11    | MOD     | P6           | 001        |             | Stan       | Jerry       | 50            |                 |              |
            |            | 2020-05-13   | 00317C   | 635       | 89110C | 600   | 600         | 11    | MOD     | P6           | 001        |             | Sam        | Jerry       | 50            |                 |              |
            |            | 2020-05-13   | 00316C   | 635       | 89110C | 700   | 700         | 11    | MOD     | P7           | 001        |             | Kevin      | Jerry       | 50            |                 |              |
            |            | 2020-05-13   | 00317C   | 635       | 89110C | 700   | 700         | 11    | MOD     | P7           | 001        |             | Stan       | Jerry       | 50            |                 |              |
            |            | 2020-05-13   | 00317C   | 635       | 89110C | 700   | 700         | 11    | MOD     | P7           | 001        |             | Sam        | Jerry       | 50            |                 |              |
            |            | 2020-05-13   | 00316C   | 635       | 89110C | 800   | 800         | 11    | MOD     | P8           | 001        |             | Kevin      | Jerry       | 50            |                 |              |
            |            | 2020-05-13   | 00317C   | 635       | 89110C | 800   | 800         | 11    | MOD     | P8           | 001        |             | Stan       | Jerry       | 50            |                 |              |
            |            | 2020-05-13   | 00317C   | 635       | 89110C | 800   | 800         | 11    | MOD     | P8           | 001        |             | Sam        | Jerry       | 50            |                 |              |
            |            | 2020-05-13   | 00316C   | 635       | 89110C | 900   | 900         | 11    | MOD     |              | 001        |             | Kevin      | Jerry       | 50            |                 |              |
            |            | 2020-05-13   | 00317C   | 635       | 89110C | 900   | 900         | 11    | MOD     |              | 001        |             | Stan       | Jerry       | 50            |                 |              |
            |            | 2020-05-13   | 00317C   | 635       | 89110C | 900   | 900         | 11    | MOD     |              | 001        |             | Sam        | Jerry       | 50            |                 |              |
        Then 指定執行日期 2020-05-01，來源資料使用 SpecialCodeMonthSelectedSource，檢查 SpecialTreatment，計算結果應為
            | FieldName | JsonValue                                                                                           |
            | p1        | { "caseCount": 3, "percentageOfCaseCount": 11.11, "points": 300, "percentageOfPoints": 2.22 }       |
            | p2        | { "caseCount": 3, "percentageOfCaseCount": 11.11, "points": 600, "percentageOfPoints": 4.44 }       |
            | p3        | { "caseCount": 3, "percentageOfCaseCount": 11.11, "points": 900, "percentageOfPoints": 6.67 }       |
            | p4        | { "caseCount": 3, "percentageOfCaseCount": 11.11, "points": 1200, "percentageOfPoints": 8.89 }      |
            | p5        | { "caseCount": 3, "percentageOfCaseCount": 11.11, "points": 1500, "percentageOfPoints": 11.11 }     |
            | p6        | { "caseCount": 3, "percentageOfCaseCount": 11.11, "points": 1800, "percentageOfPoints": 13.33 }     |
            | p7        | { "caseCount": 3, "percentageOfCaseCount": 11.11, "points": 2100, "percentageOfPoints": 15.56 }     |
            | p8        | { "caseCount": 3, "percentageOfCaseCount": 11.11, "points": 2400, "percentageOfPoints": 17.78 }     |
            | other     | { "caseCount": 3, "percentageOfCaseCount": 11.11, "points": 2700, "percentageOfPoints": 20.00 }     |
            | summary   | { "caseCount": 27, "percentageOfCaseCount": 100.00, "points": 13500, "percentageOfPoints": 100.00 } |
