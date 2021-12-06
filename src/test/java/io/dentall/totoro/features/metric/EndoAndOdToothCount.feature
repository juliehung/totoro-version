@metric @meta
Feature: 根管與補牙 EndoAndOdToothCount

    Scenario: 計算EndoAndOdToothCount 無拔牙處置
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            # ENDO
            |            | 2020-01-01   | 00315C   | 635       | 90001C | 1210  | 1210        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-01-02   | 00316C   | 635       | 90002C | 2410  | 2410        | 12    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-01-03   | 00317C   | 635       | 90003C | 3610  | 3610        | 13    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            # OD
            |            | 2020-02-01   | 00315C   | 635       | 89001C | 450   | 450         | 22    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-02-01   | 00315C   | 635       | 89001C | 450   | 450         | 21    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-02-02   | 00316C   | 635       | 89002C | 600   | 600         | 23    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
        Then 指定執行日期 2020-05-01，拔牙時間範圍 ExtQuarterByPatientSource／根管與補牙時間範圍 EndoAndOdHalfYearNearByPatientSource，檢查 EndoAndOdToothCount，計算結果數值應為 0

    Scenario: 計算EndoAndOdToothCount 有拔牙處置 單人
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            # ENDO
            |            | 2020-01-01   | 00315C   | 635       | 90001C | 1210  | 1210        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-01-02   | 00316C   | 635       | 90002C | 2410  | 2410        | 12    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-01-03   | 00317C   | 635       | 90003C | 3610  | 3610        | 13    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            # OD
            |            | 2020-02-01   | 00315C   | 635       | 89001C | 450   | 450         | 21    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-02-01   | 00315C   | 635       | 89001C | 450   | 450         | 22    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-02-02   | 00316C   | 635       | 89002C | 600   | 600         | 23    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
             # EXT
            |            | 2020-04-01   | 00315C   | 635       | 92013C | 510   | 510         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-01   | 00315C   | 635       | 92014C | 900   | 900         | 12    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-31   | 00316C   | 635       | 92015C | 2730  | 2730        | 21    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-06-30   | 00316C   | 635       | 92016C | 4300  | 4300        | 22    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
        Then 指定執行日期 2020-05-01，拔牙時間範圍 ExtQuarterByPatientSource／根管與補牙時間範圍 EndoAndOdHalfYearNearByPatientSource，檢查 EndoAndOdToothCount，計算結果數值應為 4

    Scenario: 計算EndoAndOdToothCount 有拔牙處置 多人
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        Given 設定病人 Danny 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            # ENDO
            |            | 2020-01-01   | 00315C   | 635       | 90001C | 1210  | 1210        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-01-02   | 00316C   | 635       | 90002C | 2410  | 2410        | 12    | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-01-03   | 00317C   | 635       | 90003C | 3610  | 3610        | 13    | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            # OD
            |            | 2020-02-01   | 00315C   | 635       | 89001C | 450   | 450         | 21    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-02-01   | 00315C   | 635       | 89001C | 450   | 450         | 22    | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-02-02   | 00316C   | 635       | 89002C | 600   | 600         | 23    | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            # EXT
            |            | 2020-04-01   | 00315C   | 635       | 92013C | 510   | 510         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-01   | 00315C   | 635       | 92014C | 900   | 900         | 12    | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-05-31   | 00316C   | 635       | 92015C | 2730  | 2730        | 21    | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-06-30   | 00316C   | 635       | 92016C | 4300  | 4300        | 22    | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
        Then 指定執行日期 2020-05-01，拔牙時間範圍 ExtQuarterByPatientSource／根管與補牙時間範圍 EndoAndOdHalfYearNearByPatientSource，檢查 EndoAndOdToothCount，計算結果數值應為 3

    Scenario: 計算EndoAndOdToothCount 有拔牙處置 多人 多齒
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        Given 設定病人 Danny 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            # ENDO
            |            | 2020-01-01   | 00315C   | 635       | 90001C | 1210  | 1210        | 1131  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-01-02   | 00316C   | 635       | 90002C | 2410  | 2410        | 1232  | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-01-03   | 00317C   | 635       | 90003C | 3610  | 3610        | 1333  | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            # OD
            |            | 2020-02-01   | 00315C   | 635       | 89001C | 450   | 450         | 2141  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-02-01   | 00315C   | 635       | 89001C | 450   | 450         | 2242  | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-02-02   | 00316C   | 635       | 89002C | 600   | 600         | 2343  | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            # EXT
            |            | 2020-04-01   | 00315C   | 635       | 92013C | 510   | 510         | 1131  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-01   | 00315C   | 635       | 92014C | 900   | 900         | 12    | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-05-31   | 00316C   | 635       | 92015C | 2730  | 2730        | 2142  | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-06-30   | 00316C   | 635       | 92016C | 4300  | 4300        | 22    | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
        Then 指定執行日期 2020-05-01，拔牙時間範圍 ExtQuarterByPatientSource／根管與補牙時間範圍 EndoAndOdHalfYearNearByPatientSource，檢查 EndoAndOdToothCount，計算結果數值應為 5
