@metric @meta
Feature: OD6-TreatmentCount

    Scenario Outline: 計算OD6-TreatmentCount 單人
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            | 1          | 2020-05-01   | 00315C   | 635       | 89003C | 750   | 750         | 1112  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            | 1          | 2020-05-01   | 00315C   | 635       | 89010C | 1000  | 1000        | 1112  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-02   | 00316C   | 635       | 89010C | 1000  | 1000        | 1112  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            | 2          | 2020-05-03   | 00317C   | 635       | 89012C | 1050  | 1050        | 1112  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            | 2          | 2020-05-03   | 00317C   | 635       | 89012C | 1050  | 1050        | 1314  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
        Then 指定執行日期 2020-05-01，來源資料使用 OdMonthSelectedSource，檢查 <Meta>，計算結果數值應為 <Value>
        Examples:
            | Meta              | Value |
            | Od4TreatmentCount | 0     |
            | Od5TreatmentCount | 0     |
            | Od6TreatmentCount | 10    |

    Scenario Outline: 計算OD6-TreatmentCount 多人
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        Given 設定病人 Danny 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            | 1          | 2020-05-01   | 00315C   | 635       | 89003C | 750   | 750         | 1112  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            | 1          | 2020-05-01   | 00315C   | 635       | 89010C | 1000  | 1000        | 1112  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-02   | 00316C   | 635       | 89010C | 1000  | 1000        | 1112  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            | 2          | 2020-05-03   | 00317C   | 635       | 89012C | 1050  | 1050        | 1112  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            | 2          | 2020-05-03   | 00317C   | 635       | 89012C | 1050  | 1050        | 1314  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            | 3          | 2020-05-11   | 00315C   | 635       | 89003C | 750   | 750         | 1112  | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            | 3          | 2020-05-11   | 00315C   | 635       | 89010C | 1000  | 1000        | 1112  | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-05-12   | 00316C   | 635       | 89010C | 1000  | 1000        | 1112  | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            | 4          | 2020-05-13   | 00317C   | 635       | 89012C | 1050  | 1050        | 1112  | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            | 4          | 2020-05-13   | 00317C   | 635       | 89012C | 1050  | 1050        | 1314  | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
        Then 指定執行日期 2020-05-01，來源資料使用 OdMonthSelectedSource，檢查 <Meta>，計算結果數值應為 <Value>
        Examples:
            | Meta              | Value |
            | Od4TreatmentCount | 0     |
            | Od5TreatmentCount | 0     |
            | Od6TreatmentCount | 20    |
