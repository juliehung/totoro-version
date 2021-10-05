@metric @source
Feature: 資料源

    #  主體資料
    Scenario: 檢查 ClinicSource 單牙
        Given 設定指標主體類型為診所
        Given 設定病人 Jerry 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | DoctorName | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2020-05-01   | 00315C   | 635       | 89110C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        |             | Kevin      | Jerry       | 50            |                 |              |
            |            | 2020-05-01   | 00315C   | 635       | 89110C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        |             | Stan       | Jerry       | 50            |                 |              |
            |            | 2020-05-31   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | 001        |             | Stan       | Jerry       | 50            |                 |              |
            |            | 2020-05-31   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | 001        |             | Kevin      | Jerry       | 50            |                 |              |
        Then 指定執行日期 2020-05-01，來源資料使用 ClinicSource，預期筆數應為 4

    Scenario: 檢查 ClinicSource 多牙
        Given 設定指標主體類型為診所
        Given 設定病人 Jerry 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | DoctorName | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2020-05-01   | 00315C   | 635       | 89110C | 1000  | 1000        | 1112  | MOD     | OTHER        | 001        |             | Kevin      | Jerry       | 50            |                 |              |
            |            | 2020-05-01   | 00315C   | 635       | 89110C | 1000  | 1000        | 1112  | MOD     | OTHER        | 001        |             | Stan       | Jerry       | 50            |                 |              |
            |            | 2020-05-31   | 00317C   | 635       | 89112C | 1050  | 1050        | 1112  | MOD     | OTHER        | 001        |             | Stan       | Jerry       | 50            |                 |              |
            |            | 2020-05-31   | 00317C   | 635       | 89112C | 1050  | 1050        | 1112  | MOD     | OTHER        | 001        |             | Kevin      | Jerry       | 50            |                 |              |
        Then 指定執行日期 2020-05-01，來源資料使用 ClinicSource，預期筆數應為 8

    Scenario: 檢查 DoctorSource 單牙
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | DoctorName | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2020-05-01   | 00315C   | 635       | 89110C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        |             | Kevin      | Jerry       | 50            |                 |              |
            |            | 2020-05-01   | 00315C   | 635       | 89110C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        |             |            | Jerry       | 50            |                 |              |
            |            | 2020-05-31   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | 001        |             |            | Jerry       | 50            |                 |              |
            |            | 2020-05-31   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | 001        |             | Kevin      | Jerry       | 50            |                 |              |
        Then 指定執行日期 2020-05-01，來源資料使用 DoctorSource，預期筆數應為 2

    Scenario: 檢查 DoctorSource 多牙
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | DoctorName | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2020-05-01   | 00315C   | 635       | 89110C | 1000  | 1000        | 1112  | MOD     | OTHER        | 001        |             | Kevin      | Jerry       | 50            |                 |              |
            |            | 2020-05-01   | 00315C   | 635       | 89110C | 1000  | 1000        | 1112  | MOD     | OTHER        | 001        |             |            | Jerry       | 50            |                 |              |
            |            | 2020-05-31   | 00317C   | 635       | 89112C | 1050  | 1050        | 1112  | MOD     | OTHER        | 001        |             |            | Jerry       | 50            |                 |              |
            |            | 2020-05-31   | 00317C   | 635       | 89112C | 1050  | 1050        | 1112  | MOD     | OTHER        | 001        |             | Kevin      | Jerry       | 50            |                 |              |
        Then 指定執行日期 2020-05-01，來源資料使用 DoctorSource，預期筆數應為 4

    # 時間區間資料
    Scenario: 檢查 MonthSelectedSource 單牙
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2020-04-30   | 00315C   | 635       | 89110C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-01   | 00315C   | 635       | 89110C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-31   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-06-01   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
        Then 指定執行日期 2020-05-01，來源資料使用 MonthSelectedSource，預期筆數應為 2

    Scenario: 檢查 MonthSelectedSource 多牙
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2020-04-30   | 00315C   | 635       | 89110C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-01   | 00315C   | 635       | 89110C | 1000  | 1000        | 1112  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-31   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-06-01   | 00317C   | 635       | 89112C | 1050  | 1050        | 1112  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
        Then 指定執行日期 2020-05-01，來源資料使用 MonthSelectedSource，預期筆數應為 3

    Scenario: 檢查 DailyByMonthSelectedSource
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2020-05-01   | 00315C   | 635       | 89110C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
        Then 指定執行日期 2020-05-01，來源資料使用 DailyByMonthSelectedSource，預期筆數應為 31

    Scenario: 檢查 ThreeMonthNearSource 單牙
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2020-02-28   | 00315C   | 635       | 89110C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-03-01   | 00315C   | 635       | 89110C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-31   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-06-01   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
        Then 指定執行日期 2020-05-01，來源資料使用 ThreeMonthNearSource，預期筆數應為 2

    Scenario: 檢查 ThreeMonthNearSource 多牙
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2020-02-28   | 00315C   | 635       | 89110C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-03-01   | 00315C   | 635       | 89110C | 1000  | 1000        | 1112  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-31   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-06-01   | 00317C   | 635       | 89112C | 1050  | 1050        | 1112  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
        Then 指定執行日期 2020-05-01，來源資料使用 ThreeMonthNearSource，預期筆數應為 3

    Scenario: 檢查 QuarterSource 單牙
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2020-03-31   | 00315C   | 635       | 89110C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-04-01   | 00315C   | 635       | 89110C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-06-30   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-07-01   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
        Then 指定執行日期 2020-05-01，來源資料使用 QuarterSource，預期筆數應為 2

    Scenario: 檢查 QuarterSource 多牙
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2020-03-31   | 00315C   | 635       | 89110C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-04-01   | 00315C   | 635       | 89110C | 1000  | 1000        | 1112  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-06-30   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-07-01   | 00317C   | 635       | 89112C | 1050  | 1050        | 1112  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
        Then 指定執行日期 2020-05-01，來源資料使用 QuarterSource，預期筆數應為 3

    Scenario: 檢查 QuarterOfLastYearSource 單牙
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2019-03-31   | 00315C   | 635       | 89110C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2019-04-01   | 00315C   | 635       | 89110C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2019-06-30   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2019-07-01   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
        Then 指定執行日期 2020-05-01，來源資料使用 QuarterOfLastYearSource，預期筆數應為 2

    Scenario: 檢查 QuarterOfLastYearSource 多牙
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2019-03-31   | 00315C   | 635       | 89110C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2019-04-01   | 00315C   | 635       | 89110C | 1000  | 1000        | 1112  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2019-06-30   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2019-07-01   | 00317C   | 635       | 89112C | 1050  | 1050        | 1112  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
        Then 指定執行日期 2020-05-01，來源資料使用 QuarterOfLastYearSource，預期筆數應為 3

    Scenario: 檢查 HalfYearNearSource 單牙
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2019-12-02   | 00315C   | 635       | 89110C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2019-12-03   | 00315C   | 635       | 89110C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-31   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-06-01   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
        Then 指定執行日期 2020-05-01，來源資料使用 HalfYearNearSource，預期筆數應為 2

    Scenario: 檢查 HalfYearNearSource 多牙
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2019-12-02   | 00315C   | 635       | 89110C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2019-12-03   | 00315C   | 635       | 89110C | 1000  | 1000        | 1112  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-31   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-06-01   | 00317C   | 635       | 89112C | 1050  | 1050        | 1112  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
        Then 指定執行日期 2020-05-01，來源資料使用 HalfYearNearSource，預期筆數應為 3

    Scenario: 檢查 OneYearNearSource 單牙
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2019-05-31   | 00315C   | 635       | 89110C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2019-06-01   | 00315C   | 635       | 89110C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-31   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-06-01   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
        Then 指定執行日期 2020-05-01，來源資料使用 OneYearNearSource，預期筆數應為 2

    Scenario: 檢查 OneYearNearSource 多牙
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2019-05-31   | 00315C   | 635       | 89110C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2019-06-01   | 00315C   | 635       | 89110C | 1000  | 1000        | 1112  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-31   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-06-01   | 00317C   | 635       | 89112C | 1050  | 1050        | 1112  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
        Then 指定執行日期 2020-05-01，來源資料使用 OneYearNearSource，預期筆數應為 3

    Scenario: 檢查 OneAndHalfYearNearSource 單牙
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2019-03-07   | 00315C   | 635       | 89110C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2019-03-08   | 00315C   | 635       | 89110C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-31   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-06-01   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
        Then 指定執行日期 2020-05-01，來源資料使用 OneAndHalfYearNearSource，預期筆數應為 2

    Scenario: 檢查 OneAndHalfYearNearSource 多牙
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2019-03-07   | 00315C   | 635       | 89110C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2019-03-08   | 00315C   | 635       | 89110C | 1000  | 1000        | 1112  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-31   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-06-01   | 00317C   | 635       | 89112C | 1050  | 1050        | 1112  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
        Then 指定執行日期 2020-05-01，來源資料使用 OneAndHalfYearNearSource，預期筆數應為 3

    Scenario: 檢查 TwoYearNearSource 單牙
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2018-05-31   | 00315C   | 635       | 89110C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2018-06-01   | 00315C   | 635       | 89110C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-31   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-06-01   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
        Then 指定執行日期 2020-05-01，來源資料使用 TwoYearNearSource，預期筆數應為 2

    Scenario: 檢查 TwoYearNearSource 多牙
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2018-05-31   | 00315C   | 635       | 89110C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2018-06-01   | 00315C   | 635       | 89110C | 1000  | 1000        | 1112  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-31   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-06-01   | 00317C   | 635       | 89112C | 1050  | 1050        | 1112  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
        Then 指定執行日期 2020-05-01，來源資料使用 TwoYearNearSource，預期筆數應為 3

    Scenario: 檢查 ThreeYearNearSource 單牙
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2017-05-31   | 00315C   | 635       | 89110C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2017-06-01   | 00315C   | 635       | 89110C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-31   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-06-01   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
        Then 指定執行日期 2020-05-01，來源資料使用 ThreeYearNearSource，預期筆數應為 2

    Scenario: 檢查 ThreeYearNearSource 多牙
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2017-05-31   | 00315C   | 635       | 89110C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2017-06-01   | 00315C   | 635       | 89110C | 1000  | 1000        | 1112  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-31   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-06-01   | 00317C   | 635       | 89112C | 1050  | 1050        | 1112  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
        Then 指定執行日期 2020-05-01，來源資料使用 ThreeYearNearSource，預期筆數應為 3

    # 拔牙資料
    Scenario: 檢查 ExtQuarterSource 單牙
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        Given 設定病人 Danny 24 歲
        Given 設定病人 Jun 24 歲
        Given 設定病人 Kevin 24 歲
        Given 設定病人 Issac 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2020-03-31   | 00315C   | 635       | 92013C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        |             | Kevin       | 50            |                 |              |
            |            | 2020-04-01   | 00315C   | 635       | 92014C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-15   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | 001        |             | Issac       | 50            |                 |              |
            |            | 2020-06-30   | 00317C   | 635       | 92015C | 1050  | 1050        | 11    | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-07-01   | 00317C   | 635       | 92016C | 1050  | 1050        | 11    | MOD     | OTHER        | 001        |             | Jun         | 50            |                 |              |
        Then 指定執行日期 2020-05-01，來源資料使用 ExtQuarterSource，預期筆數應為 2

    Scenario: 檢查 ExtQuarterSource 多牙
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        Given 設定病人 Danny 24 歲
        Given 設定病人 Jun 24 歲
        Given 設定病人 Kevin 24 歲
        Given 設定病人 Issac 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2020-03-31   | 00315C   | 635       | 92013C | 1000  | 1000        | 1112  | MOD     | OTHER        | 001        |             | Kevin       | 50            |                 |              |
            |            | 2020-04-01   | 00315C   | 635       | 92014C | 1000  | 1000        | 1112  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-15   | 00317C   | 635       | 89112C | 1050  | 1050        | 1112  | MOD     | OTHER        | 001        |             | Issac       | 50            |                 |              |
            |            | 2020-06-30   | 00317C   | 635       | 92015C | 1050  | 1050        | 1112  | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-07-01   | 00317C   | 635       | 92016C | 1050  | 1050        | 1112  | MOD     | OTHER        | 001        |             | Jun         | 50            |                 |              |
        Then 指定執行日期 2020-05-01，來源資料使用 ExtQuarterSource，預期筆數應為 4

    Scenario: 檢查 ExtQuarterByPatientSource
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        Given 設定病人 Danny 24 歲
        Given 設定病人 Jun 24 歲
        Given 設定病人 Kevin 24 歲
        Given 設定病人 Issac 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2020-03-31   | 00315C   | 635       | 92013C | 1000  | 1000        | 1112  | MOD     | OTHER        | 001        |             | Kevin       | 50            |                 |              |
            |            | 2020-04-01   | 00315C   | 635       | 92014C | 1000  | 1000        | 1112  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-15   | 00317C   | 635       | 89112C | 1050  | 1050        | 1112  | MOD     | OTHER        | 001        |             | Issac       | 50            |                 |              |
            |            | 2020-06-30   | 00317C   | 635       | 92015C | 1050  | 1050        | 1112  | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-07-01   | 00317C   | 635       | 92016C | 1050  | 1050        | 1112  | MOD     | OTHER        | 001        |             | Jun         | 50            |                 |              |
        Then 指定執行日期 2020-05-01，來源資料使用 ExtQuarterByPatientSource，預期筆數應為 2

    # OD
    Scenario: 檢查 OdDeciduousMonthSelectedByPatientSource
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        Given 設定病人 Danny 24 歲
        Given 設定病人 Jun 24 歲
        Given 設定病人 Kevin 24 歲
        Given 設定病人 Issac 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2020-04-30   | 00315C   | 635       | 89001C | 1000  | 1000        | 51    | MOD     | OTHER        | 001        |             | Kevin       | 50            |                 |              |
            |            | 2020-05-01   | 00315C   | 635       | 89002C | 1000  | 1000        | 51    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-15   | 00315C   | 635       | 90001C | 1000  | 1000        | 51    | MOD     | OTHER        | 001        |             | Issac       | 50            |                 |              |
            |            | 2020-05-31   | 00317C   | 635       | 89003C | 1050  | 1050        | 51    | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-06-01   | 00317C   | 635       | 89004C | 1050  | 1050        | 51    | MOD     | OTHER        | 001        |             | Jun         | 50            |                 |              |
        Then 指定執行日期 2020-05-01，來源資料使用 OdDeciduousMonthSelectedByPatientSource，預期筆數應為 2

    Scenario: 檢查 OdDeciduousOneAndHalfYearNearByPatientSource
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        Given 設定病人 Danny 24 歲
        Given 設定病人 Jun 24 歲
        Given 設定病人 Kevin 24 歲
        Given 設定病人 Issac 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2019-03-07   | 00315C   | 635       | 89001C | 1000  | 1000        | 5152  | MOD     | OTHER        | 001        |             | Kevin       | 50            |                 |              |
            |            | 2019-03-08   | 00315C   | 635       | 89002C | 1000  | 1000        | 5152  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-15   | 00315C   | 635       | 90001C | 1000  | 1000        | 5152  | MOD     | OTHER        | 001        |             | Issac       | 50            |                 |              |
            |            | 2020-05-31   | 00317C   | 635       | 89003C | 1050  | 1050        | 6162  | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-06-01   | 00317C   | 635       | 89004C | 1050  | 1050        | 6162  | MOD     | OTHER        | 001        |             | Jun         | 50            |                 |              |
        Then 指定執行日期 2020-05-01，來源資料使用 OdDeciduousOneAndHalfYearNearByPatientSource，預期筆數應為 2

    Scenario: 檢查 OdDeciduousOneYearNearByPatientSource
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        Given 設定病人 Danny 24 歲
        Given 設定病人 Jun 24 歲
        Given 設定病人 Kevin 24 歲
        Given 設定病人 Issac 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2019-05-31   | 00315C   | 635       | 89001C | 1000  | 1000        | 5152  | MOD     | OTHER        | 001        |             | Kevin       | 50            |                 |              |
            |            | 2019-06-01   | 00315C   | 635       | 89002C | 1000  | 1000        | 5152  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-15   | 00315C   | 635       | 90001C | 1000  | 1000        | 5152  | MOD     | OTHER        | 001        |             | Issac       | 50            |                 |              |
            |            | 2020-05-31   | 00317C   | 635       | 89003C | 1050  | 1050        | 6162  | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-06-01   | 00317C   | 635       | 89004C | 1050  | 1050        | 6162  | MOD     | OTHER        | 001        |             | Jun         | 50            |                 |              |
        Then 指定執行日期 2020-05-01，來源資料使用 OdDeciduousOneYearNearByPatientSource，預期筆數應為 2

    Scenario: 檢查 OdDeciduousQuarterByPatientSource
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        Given 設定病人 Danny 24 歲
        Given 設定病人 Jun 24 歲
        Given 設定病人 Kevin 24 歲
        Given 設定病人 Issac 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2020-03-31   | 00315C   | 635       | 89001C | 1000  | 1000        | 5152  | MOD     | OTHER        | 001        |             | Kevin       | 50            |                 |              |
            |            | 2020-04-01   | 00315C   | 635       | 89002C | 1000  | 1000        | 5152  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-15   | 00315C   | 635       | 90001C | 1000  | 1000        | 5152  | MOD     | OTHER        | 001        |             | Issac       | 50            |                 |              |
            |            | 2020-06-30   | 00317C   | 635       | 89003C | 1050  | 1050        | 6162  | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-07-01   | 00317C   | 635       | 89004C | 1050  | 1050        | 6162  | MOD     | OTHER        | 001        |             | Jun         | 50            |                 |              |
        Then 指定執行日期 2020-05-01，來源資料使用 OdDeciduousQuarterByPatientSource，預期筆數應為 2

    Scenario: 檢查 OdDeciduousQuarterPlusOneAndHalfYearNearByPatientSource
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        Given 設定病人 Danny 24 歲
        Given 設定病人 Jun 24 歲
        Given 設定病人 Kevin 24 歲
        Given 設定病人 Issac 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2019-01-06   | 00315C   | 635       | 89001C | 1000  | 1000        | 5152  | MOD     | OTHER        | 001        |             | Kevin       | 50            |                 |              |
            |            | 2019-01-07   | 00315C   | 635       | 89002C | 1000  | 1000        | 5152  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-15   | 00315C   | 635       | 90001C | 1000  | 1000        | 5152  | MOD     | OTHER        | 001        |             | Issac       | 50            |                 |              |
            |            | 2020-06-30   | 00317C   | 635       | 89003C | 1050  | 1050        | 6162  | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-07-01   | 00317C   | 635       | 89004C | 1050  | 1050        | 6162  | MOD     | OTHER        | 001        |             | Jun         | 50            |                 |              |
        Then 指定執行日期 2020-05-01，來源資料使用 OdDeciduousQuarterPlusOneAndHalfYearNearByPatientSource，預期筆數應為 2

    Scenario: 檢查 OdDeciduousQuarterPlusOneYearNearByPatientSource
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        Given 設定病人 Danny 24 歲
        Given 設定病人 Jun 24 歲
        Given 設定病人 Kevin 24 歲
        Given 設定病人 Issac 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2019-04-01   | 00315C   | 635       | 89001C | 1000  | 1000        | 5152  | MOD     | OTHER        | 001        |             | Kevin       | 50            |                 |              |
            |            | 2019-04-02   | 00315C   | 635       | 89002C | 1000  | 1000        | 5152  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-15   | 00315C   | 635       | 90001C | 1000  | 1000        | 5152  | MOD     | OTHER        | 001        |             | Issac       | 50            |                 |              |
            |            | 2020-06-30   | 00317C   | 635       | 89003C | 1050  | 1050        | 6162  | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-07-01   | 00317C   | 635       | 89004C | 1050  | 1050        | 6162  | MOD     | OTHER        | 001        |             | Jun         | 50            |                 |              |
        Then 指定執行日期 2020-05-01，來源資料使用 OdDeciduousQuarterPlusOneYearNearByPatientSource，預期筆數應為 2

    Scenario: 檢查 OdDeciduousQuarterPlusTwoYearNearByPatientSource
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        Given 設定病人 Danny 24 歲
        Given 設定病人 Jun 24 歲
        Given 設定病人 Kevin 24 歲
        Given 設定病人 Issac 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2018-04-01   | 00315C   | 635       | 89001C | 1000  | 1000        | 5152  | MOD     | OTHER        | 001        |             | Kevin       | 50            |                 |              |
            |            | 2018-04-02   | 00315C   | 635       | 89002C | 1000  | 1000        | 5152  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-15   | 00315C   | 635       | 90001C | 1000  | 1000        | 5152  | MOD     | OTHER        | 001        |             | Issac       | 50            |                 |              |
            |            | 2020-06-30   | 00317C   | 635       | 89003C | 1050  | 1050        | 6162  | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-07-01   | 00317C   | 635       | 89004C | 1050  | 1050        | 6162  | MOD     | OTHER        | 001        |             | Jun         | 50            |                 |              |
        Then 指定執行日期 2020-05-01，來源資料使用 OdDeciduousQuarterPlusTwoYearNearByPatientSource，預期筆數應為 2

    Scenario: 檢查 OdDeciduousQuarterPlusThreeYearNearByPatientSource
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        Given 設定病人 Danny 24 歲
        Given 設定病人 Jun 24 歲
        Given 設定病人 Kevin 24 歲
        Given 設定病人 Issac 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2017-04-01   | 00315C   | 635       | 89001C | 1000  | 1000        | 5152  | MOD     | OTHER        | 001        |             | Kevin       | 50            |                 |              |
            |            | 2017-04-02   | 00315C   | 635       | 89002C | 1000  | 1000        | 5152  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-15   | 00315C   | 635       | 90001C | 1000  | 1000        | 5152  | MOD     | OTHER        | 001        |             | Issac       | 50            |                 |              |
            |            | 2020-06-30   | 00317C   | 635       | 89003C | 1050  | 1050        | 6162  | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-07-01   | 00317C   | 635       | 89004C | 1050  | 1050        | 6162  | MOD     | OTHER        | 001        |             | Jun         | 50            |                 |              |
        Then 指定執行日期 2020-05-01，來源資料使用 OdDeciduousQuarterPlusThreeYearNearByPatientSource，預期筆數應為 2

    Scenario: 檢查 OdMonthSelectedSource 單牙
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        Given 設定病人 Danny 24 歲
        Given 設定病人 Jun 24 歲
        Given 設定病人 Kevin 24 歲
        Given 設定病人 Issac 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2020-04-30   | 00315C   | 635       | 89001C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        |             | Kevin       | 50            |                 |              |
            |            | 2020-05-01   | 00315C   | 635       | 89002C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-15   | 00315C   | 635       | 90001C | 1000  | 1000        | 41    | MOD     | OTHER        | 001        |             | Issac       | 50            |                 |              |
            |            | 2020-05-31   | 00317C   | 635       | 89003C | 1050  | 1050        | 61    | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-06-01   | 00317C   | 635       | 89004C | 1050  | 1050        | 61    | MOD     | OTHER        | 001        |             | Jun         | 50            |                 |              |
        Then 指定執行日期 2020-05-01，來源資料使用 OdMonthSelectedSource，預期筆數應為 2

    Scenario: 檢查 OdMonthSelectedSource 多牙
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        Given 設定病人 Danny 24 歲
        Given 設定病人 Jun 24 歲
        Given 設定病人 Kevin 24 歲
        Given 設定病人 Issac 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2020-04-30   | 00315C   | 635       | 89001C | 1000  | 1000        | 1112  | MOD     | OTHER        | 001        |             | Kevin       | 50            |                 |              |
            |            | 2020-05-01   | 00315C   | 635       | 89002C | 1000  | 1000        | 1112  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-15   | 00315C   | 635       | 90001C | 1000  | 1000        | 4142  | MOD     | OTHER        | 001        |             | Issac       | 50            |                 |              |
            |            | 2020-05-31   | 00317C   | 635       | 89003C | 1050  | 1050        | 6162  | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-06-01   | 00317C   | 635       | 89004C | 1050  | 1050        | 6162  | MOD     | OTHER        | 001        |             | Jun         | 50            |                 |              |
        Then 指定執行日期 2020-05-01，來源資料使用 OdMonthSelectedSource，預期筆數應為 4

    Scenario: 檢查 OdMonthSelectedByPatientSource
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        Given 設定病人 Danny 24 歲
        Given 設定病人 Jun 24 歲
        Given 設定病人 Kevin 24 歲
        Given 設定病人 Issac 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2020-04-30   | 00315C   | 635       | 89001C | 1000  | 1000        | 1112  | MOD     | OTHER        | 001        |             | Kevin       | 50            |                 |              |
            |            | 2020-05-01   | 00315C   | 635       | 89002C | 1000  | 1000        | 1112  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-15   | 00315C   | 635       | 90001C | 1000  | 1000        | 4142  | MOD     | OTHER        | 001        |             | Issac       | 50            |                 |              |
            |            | 2020-05-31   | 00317C   | 635       | 89003C | 1050  | 1050        | 6162  | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-06-01   | 00317C   | 635       | 89004C | 1050  | 1050        | 6162  | MOD     | OTHER        | 001        |             | Jun         | 50            |                 |              |
        Then 指定執行日期 2020-05-01，來源資料使用 OdMonthSelectedByPatientSource，預期筆數應為 2

    Scenario: 檢查 OdOneAndHalfYearNearSource 單牙
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        Given 設定病人 Danny 24 歲
        Given 設定病人 Jun 24 歲
        Given 設定病人 Kevin 24 歲
        Given 設定病人 Issac 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2019-03-07   | 00315C   | 635       | 89001C | 1000  | 1000        | 51    | MOD     | OTHER        | 001        |             | Kevin       | 50            |                 |              |
            |            | 2019-03-08   | 00315C   | 635       | 89002C | 1000  | 1000        | 51    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-15   | 00315C   | 635       | 90001C | 1000  | 1000        | 51    | MOD     | OTHER        | 001        |             | Issac       | 50            |                 |              |
            |            | 2020-05-31   | 00317C   | 635       | 89003C | 1050  | 1050        | 61    | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-06-01   | 00317C   | 635       | 89004C | 1050  | 1050        | 61    | MOD     | OTHER        | 001        |             | Jun         | 50            |                 |              |
        Then 指定執行日期 2020-05-01，來源資料使用 OdOneAndHalfYearNearSource，預期筆數應為 2

    Scenario: 檢查 OdOneAndHalfYearNearSource 多牙
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        Given 設定病人 Danny 24 歲
        Given 設定病人 Jun 24 歲
        Given 設定病人 Kevin 24 歲
        Given 設定病人 Issac 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2019-03-07   | 00315C   | 635       | 89001C | 1000  | 1000        | 5152  | MOD     | OTHER        | 001        |             | Kevin       | 50            |                 |              |
            |            | 2019-03-08   | 00315C   | 635       | 89002C | 1000  | 1000        | 5152  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-15   | 00315C   | 635       | 90001C | 1000  | 1000        | 5152  | MOD     | OTHER        | 001        |             | Issac       | 50            |                 |              |
            |            | 2020-05-31   | 00317C   | 635       | 89003C | 1050  | 1050        | 6162  | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-06-01   | 00317C   | 635       | 89004C | 1050  | 1050        | 6162  | MOD     | OTHER        | 001        |             | Jun         | 50            |                 |              |
        Then 指定執行日期 2020-05-01，來源資料使用 OdOneAndHalfYearNearSource，預期筆數應為 4

    Scenario: 檢查 OdOneYearNearByPatientSource
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        Given 設定病人 Danny 24 歲
        Given 設定病人 Jun 24 歲
        Given 設定病人 Kevin 24 歲
        Given 設定病人 Issac 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2019-05-31   | 00315C   | 635       | 89001C | 1000  | 1000        | 1112  | MOD     | OTHER        | 001        |             | Kevin       | 50            |                 |              |
            |            | 2019-06-01   | 00315C   | 635       | 89002C | 1000  | 1000        | 1112  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-15   | 00315C   | 635       | 90001C | 1000  | 1000        | 4142  | MOD     | OTHER        | 001        |             | Issac       | 50            |                 |              |
            |            | 2020-05-31   | 00317C   | 635       | 89003C | 1050  | 1050        | 6162  | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-06-01   | 00317C   | 635       | 89004C | 1050  | 1050        | 6162  | MOD     | OTHER        | 001        |             | Jun         | 50            |                 |              |
        Then 指定執行日期 2020-05-01，來源資料使用 OdOneYearNearByPatientSource，預期筆數應為 2

    Scenario: 檢查 OdOneYearNearSource 單牙
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        Given 設定病人 Danny 24 歲
        Given 設定病人 Jun 24 歲
        Given 設定病人 Kevin 24 歲
        Given 設定病人 Issac 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2019-05-31   | 00315C   | 635       | 89001C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        |             | Kevin       | 50            |                 |              |
            |            | 2019-06-01   | 00315C   | 635       | 89002C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-15   | 00315C   | 635       | 90001C | 1000  | 1000        | 41    | MOD     | OTHER        | 001        |             | Issac       | 50            |                 |              |
            |            | 2020-05-31   | 00317C   | 635       | 89003C | 1050  | 1050        | 61    | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-06-01   | 00317C   | 635       | 89004C | 1050  | 1050        | 61    | MOD     | OTHER        | 001        |             | Jun         | 50            |                 |              |
        Then 指定執行日期 2020-05-01，來源資料使用 OdOneYearNearSource，預期筆數應為 2

    Scenario: 檢查 OdOneYearNearSource 多牙
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        Given 設定病人 Danny 24 歲
        Given 設定病人 Jun 24 歲
        Given 設定病人 Kevin 24 歲
        Given 設定病人 Issac 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2019-05-31   | 00315C   | 635       | 89001C | 1000  | 1000        | 1112  | MOD     | OTHER        | 001        |             | Kevin       | 50            |                 |              |
            |            | 2019-06-01   | 00315C   | 635       | 89002C | 1000  | 1000        | 1112  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-15   | 00315C   | 635       | 90001C | 1000  | 1000        | 4142  | MOD     | OTHER        | 001        |             | Issac       | 50            |                 |              |
            |            | 2020-05-31   | 00317C   | 635       | 89003C | 1050  | 1050        | 6162  | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-06-01   | 00317C   | 635       | 89004C | 1050  | 1050        | 6162  | MOD     | OTHER        | 001        |             | Jun         | 50            |                 |              |
        Then 指定執行日期 2020-05-01，來源資料使用 OdOneYearNearSource，預期筆數應為 4

    Scenario: 檢查 OdPermanentMonthSelectedByPatientSource
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        Given 設定病人 Danny 24 歲
        Given 設定病人 Jun 24 歲
        Given 設定病人 Kevin 24 歲
        Given 設定病人 Issac 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2020-04-30   | 00315C   | 635       | 89001C | 1000  | 1000        | 1112  | MOD     | OTHER        | 001        |             | Kevin       | 50            |                 |              |
            |            | 2020-05-01   | 00315C   | 635       | 89002C | 1000  | 1000        | 1112  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-15   | 00315C   | 635       | 90001C | 1000  | 1000        | 1112  | MOD     | OTHER        | 001        |             | Issac       | 50            |                 |              |
            |            | 2020-05-31   | 00317C   | 635       | 89003C | 1050  | 1050        | 2122  | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-06-01   | 00317C   | 635       | 89004C | 1050  | 1050        | 2122  | MOD     | OTHER        | 001        |             | Jun         | 50            |                 |              |
        Then 指定執行日期 2020-05-01，來源資料使用 OdPermanentMonthSelectedByPatientSource，預期筆數應為 2

    Scenario: 檢查 OdPermanentQuarterByPatientSource
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        Given 設定病人 Danny 24 歲
        Given 設定病人 Jun 24 歲
        Given 設定病人 Kevin 24 歲
        Given 設定病人 Issac 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2020-03-31   | 00315C   | 635       | 89001C | 1000  | 1000        | 1112  | MOD     | OTHER        | 001        |             | Kevin       | 50            |                 |              |
            |            | 2020-04-01   | 00315C   | 635       | 89002C | 1000  | 1000        | 1112  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-15   | 00315C   | 635       | 90001C | 1000  | 1000        | 1112  | MOD     | OTHER        | 001        |             | Issac       | 50            |                 |              |
            |            | 2020-06-30   | 00317C   | 635       | 89003C | 1050  | 1050        | 2122  | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-07-01   | 00317C   | 635       | 89004C | 1050  | 1050        | 2122  | MOD     | OTHER        | 001        |             | Jun         | 50            |                 |              |
        Then 指定執行日期 2020-05-01，來源資料使用 OdPermanentQuarterByPatientSource，預期筆數應為 2

    Scenario: 檢查 OdPermanentQuarterPlusOneAndHalfYearNearByPatientSource
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        Given 設定病人 Danny 24 歲
        Given 設定病人 Jun 24 歲
        Given 設定病人 Kevin 24 歲
        Given 設定病人 Issac 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2019-01-06   | 00315C   | 635       | 89001C | 1000  | 1000        | 1112  | MOD     | OTHER        | 001        |             | Kevin       | 50            |                 |              |
            |            | 2019-01-07   | 00315C   | 635       | 89002C | 1000  | 1000        | 1112  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-15   | 00315C   | 635       | 90001C | 1000  | 1000        | 1112  | MOD     | OTHER        | 001        |             | Issac       | 50            |                 |              |
            |            | 2020-06-30   | 00317C   | 635       | 89003C | 1050  | 1050        | 2122  | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-07-01   | 00317C   | 635       | 89004C | 1050  | 1050        | 2122  | MOD     | OTHER        | 001        |             | Jun         | 50            |                 |              |
        Then 指定執行日期 2020-05-01，來源資料使用 OdPermanentQuarterPlusOneAndHalfYearNearByPatientSource，預期筆數應為 2

    Scenario: 檢查 OdPermanentQuarterPlusOneYearNearByPatientSource
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        Given 設定病人 Danny 24 歲
        Given 設定病人 Jun 24 歲
        Given 設定病人 Kevin 24 歲
        Given 設定病人 Issac 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2019-04-01   | 00315C   | 635       | 89001C | 1000  | 1000        | 1112  | MOD     | OTHER        | 001        |             | Kevin       | 50            |                 |              |
            |            | 2019-04-02   | 00315C   | 635       | 89002C | 1000  | 1000        | 1112  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-15   | 00315C   | 635       | 90001C | 1000  | 1000        | 1112  | MOD     | OTHER        | 001        |             | Issac       | 50            |                 |              |
            |            | 2020-06-30   | 00317C   | 635       | 89003C | 1050  | 1050        | 2122  | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-07-01   | 00317C   | 635       | 89004C | 1050  | 1050        | 2122  | MOD     | OTHER        | 001        |             | Jun         | 50            |                 |              |
        Then 指定執行日期 2020-05-01，來源資料使用 OdPermanentQuarterPlusOneYearNearByPatientSource，預期筆數應為 2

    Scenario: 檢查 OdPermanentQuarterPlusTwoYearNearByPatientSource
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        Given 設定病人 Danny 24 歲
        Given 設定病人 Jun 24 歲
        Given 設定病人 Kevin 24 歲
        Given 設定病人 Issac 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2018-04-01   | 00315C   | 635       | 89001C | 1000  | 1000        | 1112  | MOD     | OTHER        | 001        |             | Kevin       | 50            |                 |              |
            |            | 2018-04-02   | 00315C   | 635       | 89002C | 1000  | 1000        | 1112  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-15   | 00315C   | 635       | 90001C | 1000  | 1000        | 1112  | MOD     | OTHER        | 001        |             | Issac       | 50            |                 |              |
            |            | 2020-06-30   | 00317C   | 635       | 89003C | 1050  | 1050        | 2122  | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-07-01   | 00317C   | 635       | 89004C | 1050  | 1050        | 2122  | MOD     | OTHER        | 001        |             | Jun         | 50            |                 |              |
        Then 指定執行日期 2020-05-01，來源資料使用 OdPermanentQuarterPlusTwoYearNearByPatientSource，預期筆數應為 2

    Scenario: 檢查 OdPermanentQuarterPlusThreeYearNearByPatientSource
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        Given 設定病人 Danny 24 歲
        Given 設定病人 Jun 24 歲
        Given 設定病人 Kevin 24 歲
        Given 設定病人 Issac 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2017-04-01   | 00315C   | 635       | 89001C | 1000  | 1000        | 1112  | MOD     | OTHER        | 001        |             | Kevin       | 50            |                 |              |
            |            | 2017-04-02   | 00315C   | 635       | 89002C | 1000  | 1000        | 1112  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-15   | 00315C   | 635       | 90001C | 1000  | 1000        | 1112  | MOD     | OTHER        | 001        |             | Issac       | 50            |                 |              |
            |            | 2020-06-30   | 00317C   | 635       | 89003C | 1050  | 1050        | 2122  | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-07-01   | 00317C   | 635       | 89004C | 1050  | 1050        | 2122  | MOD     | OTHER        | 001        |             | Jun         | 50            |                 |              |
        Then 指定執行日期 2020-05-01，來源資料使用 OdPermanentQuarterPlusThreeYearNearByPatientSource，預期筆數應為 2

    Scenario: 檢查 OdQuarterByPatientSource
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        Given 設定病人 Danny 24 歲
        Given 設定病人 Jun 24 歲
        Given 設定病人 Kevin 24 歲
        Given 設定病人 Issac 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2020-03-31   | 00315C   | 635       | 89001C | 1000  | 1000        | 1112  | MOD     | OTHER        | 001        |             | Kevin       | 50            |                 |              |
            |            | 2020-04-01   | 00315C   | 635       | 89002C | 1000  | 1000        | 1112  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-15   | 00315C   | 635       | 90001C | 1000  | 1000        | 4142  | MOD     | OTHER        | 001        |             | Issac       | 50            |                 |              |
            |            | 2020-06-30   | 00317C   | 635       | 89003C | 1050  | 1050        | 6162  | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-07-01   | 00317C   | 635       | 89004C | 1050  | 1050        | 6162  | MOD     | OTHER        | 001        |             | Jun         | 50            |                 |              |
        Then 指定執行日期 2020-05-01，來源資料使用 OdQuarterByPatientSource，預期筆數應為 2

    Scenario: 檢查 OdQuarterPlusOneAndHalfYearNearSource 單牙
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        Given 設定病人 Danny 24 歲
        Given 設定病人 Jun 24 歲
        Given 設定病人 Kevin 24 歲
        Given 設定病人 Issac 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2019-01-06   | 00315C   | 635       | 89001C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        |             | Kevin       | 50            |                 |              |
            |            | 2019-01-07   | 00315C   | 635       | 89002C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-15   | 00315C   | 635       | 90001C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        |             | Issac       | 50            |                 |              |
            |            | 2020-06-30   | 00317C   | 635       | 89003C | 1050  | 1050        | 21    | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-07-01   | 00317C   | 635       | 89004C | 1050  | 1050        | 21    | MOD     | OTHER        | 001        |             | Jun         | 50            |                 |              |
        Then 指定執行日期 2020-05-01，來源資料使用 OdQuarterPlusOneAndHalfYearNearSource，預期筆數應為 2

    Scenario: 檢查 OdQuarterPlusOneAndHalfYearNearSource 多牙
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        Given 設定病人 Danny 24 歲
        Given 設定病人 Jun 24 歲
        Given 設定病人 Kevin 24 歲
        Given 設定病人 Issac 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2019-01-06   | 00315C   | 635       | 89001C | 1000  | 1000        | 1112  | MOD     | OTHER        | 001        |             | Kevin       | 50            |                 |              |
            |            | 2019-01-07   | 00315C   | 635       | 89002C | 1000  | 1000        | 1112  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-15   | 00315C   | 635       | 90001C | 1000  | 1000        | 1112  | MOD     | OTHER        | 001        |             | Issac       | 50            |                 |              |
            |            | 2020-06-30   | 00317C   | 635       | 89003C | 1050  | 1050        | 2122  | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-07-01   | 00317C   | 635       | 89004C | 1050  | 1050        | 2122  | MOD     | OTHER        | 001        |             | Jun         | 50            |                 |              |
        Then 指定執行日期 2020-05-01，來源資料使用 OdQuarterPlusOneAndHalfYearNearSource，預期筆數應為 4

    Scenario: 檢查 OdQuarterPlusOneYearNearSource 單牙
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        Given 設定病人 Danny 24 歲
        Given 設定病人 Jun 24 歲
        Given 設定病人 Kevin 24 歲
        Given 設定病人 Issac 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2019-04-01   | 00315C   | 635       | 89001C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        |             | Kevin       | 50            |                 |              |
            |            | 2019-04-02   | 00315C   | 635       | 89002C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-15   | 00315C   | 635       | 90001C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        |             | Issac       | 50            |                 |              |
            |            | 2020-06-30   | 00317C   | 635       | 89003C | 1050  | 1050        | 21    | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-07-01   | 00317C   | 635       | 89004C | 1050  | 1050        | 21    | MOD     | OTHER        | 001        |             | Jun         | 50            |                 |              |
        Then 指定執行日期 2020-05-01，來源資料使用 OdQuarterPlusOneYearNearByPatientSource，預期筆數應為 2

    Scenario: 檢查 OdQuarterPlusOneYearNearSource 多牙
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        Given 設定病人 Danny 24 歲
        Given 設定病人 Jun 24 歲
        Given 設定病人 Kevin 24 歲
        Given 設定病人 Issac 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2019-04-01   | 00315C   | 635       | 89001C | 1000  | 1000        | 1112  | MOD     | OTHER        | 001        |             | Kevin       | 50            |                 |              |
            |            | 2019-04-02   | 00315C   | 635       | 89002C | 1000  | 1000        | 1112  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-15   | 00315C   | 635       | 90001C | 1000  | 1000        | 1112  | MOD     | OTHER        | 001        |             | Issac       | 50            |                 |              |
            |            | 2020-06-30   | 00317C   | 635       | 89003C | 1050  | 1050        | 2122  | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-07-01   | 00317C   | 635       | 89004C | 1050  | 1050        | 2122  | MOD     | OTHER        | 001        |             | Jun         | 50            |                 |              |
        Then 指定執行日期 2020-05-01，來源資料使用 OdQuarterPlusOneYearNearSource，預期筆數應為 4

    Scenario: 檢查 OdQuarterPlusOneYearNearByPatientSource
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        Given 設定病人 Danny 24 歲
        Given 設定病人 Jun 24 歲
        Given 設定病人 Kevin 24 歲
        Given 設定病人 Issac 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2019-04-01   | 00315C   | 635       | 89001C | 1000  | 1000        | 1112  | MOD     | OTHER        | 001        |             | Kevin       | 50            |                 |              |
            |            | 2019-04-02   | 00315C   | 635       | 89002C | 1000  | 1000        | 1112  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-15   | 00315C   | 635       | 90001C | 1000  | 1000        | 1112  | MOD     | OTHER        | 001        |             | Issac       | 50            |                 |              |
            |            | 2020-06-30   | 00317C   | 635       | 89003C | 1050  | 1050        | 2122  | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-07-01   | 00317C   | 635       | 89004C | 1050  | 1050        | 2122  | MOD     | OTHER        | 001        |             | Jun         | 50            |                 |              |
        Then 指定執行日期 2020-05-01，來源資料使用 OdQuarterPlusOneYearNearByPatientSource，預期筆數應為 2

    Scenario: 檢查 OdQuarterPlusTwoYearNearSource 單牙
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        Given 設定病人 Danny 24 歲
        Given 設定病人 Jun 24 歲
        Given 設定病人 Kevin 24 歲
        Given 設定病人 Issac 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2018-04-01   | 00315C   | 635       | 89001C | 1000  | 1000        | 51    | MOD     | OTHER        | 001        |             | Kevin       | 50            |                 |              |
            |            | 2018-04-02   | 00315C   | 635       | 89002C | 1000  | 1000        | 51    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-15   | 00315C   | 635       | 90001C | 1000  | 1000        | 51    | MOD     | OTHER        | 001        |             | Issac       | 50            |                 |              |
            |            | 2020-06-30   | 00317C   | 635       | 89003C | 1050  | 1050        | 61    | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-07-01   | 00317C   | 635       | 89004C | 1050  | 1050        | 61    | MOD     | OTHER        | 001        |             | Jun         | 50            |                 |              |
        Then 指定執行日期 2020-05-01，來源資料使用 OdQuarterPlusTwoYearNearSource，預期筆數應為 2

    Scenario: 檢查 OdQuarterPlusTwoYearNearSource 多牙
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        Given 設定病人 Danny 24 歲
        Given 設定病人 Jun 24 歲
        Given 設定病人 Kevin 24 歲
        Given 設定病人 Issac 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2018-04-01   | 00315C   | 635       | 89001C | 1000  | 1000        | 5152  | MOD     | OTHER        | 001        |             | Kevin       | 50            |                 |              |
            |            | 2018-04-02   | 00315C   | 635       | 89002C | 1000  | 1000        | 5152  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-15   | 00315C   | 635       | 90001C | 1000  | 1000        | 5152  | MOD     | OTHER        | 001        |             | Issac       | 50            |                 |              |
            |            | 2020-06-30   | 00317C   | 635       | 89003C | 1050  | 1050        | 6162  | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-07-01   | 00317C   | 635       | 89004C | 1050  | 1050        | 6162  | MOD     | OTHER        | 001        |             | Jun         | 50            |                 |              |
        Then 指定執行日期 2020-05-01，來源資料使用 OdQuarterPlusTwoYearNearSource，預期筆數應為 4

    Scenario: 檢查 OdQuarterPlusTwoYearNearByPatientSource
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        Given 設定病人 Danny 24 歲
        Given 設定病人 Jun 24 歲
        Given 設定病人 Kevin 24 歲
        Given 設定病人 Issac 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2018-04-01   | 00315C   | 635       | 89001C | 1000  | 1000        | 5152  | MOD     | OTHER        | 001        |             | Kevin       | 50            |                 |              |
            |            | 2018-04-02   | 00315C   | 635       | 89002C | 1000  | 1000        | 5152  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-15   | 00315C   | 635       | 90001C | 1000  | 1000        | 5152  | MOD     | OTHER        | 001        |             | Issac       | 50            |                 |              |
            |            | 2020-06-30   | 00317C   | 635       | 89003C | 1050  | 1050        | 6162  | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-07-01   | 00317C   | 635       | 89004C | 1050  | 1050        | 6162  | MOD     | OTHER        | 001        |             | Jun         | 50            |                 |              |
        Then 指定執行日期 2020-05-01，來源資料使用 OdQuarterPlusTwoYearNearByPatientSource，預期筆數應為 2

    Scenario: 檢查 OdQuarterPlusThreeYearNearSource 單牙
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        Given 設定病人 Danny 24 歲
        Given 設定病人 Jun 24 歲
        Given 設定病人 Kevin 24 歲
        Given 設定病人 Issac 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2017-04-01   | 00315C   | 635       | 89001C | 1000  | 1000        | 51    | MOD     | OTHER        | 001        |             | Kevin       | 50            |                 |              |
            |            | 2017-04-02   | 00315C   | 635       | 89002C | 1000  | 1000        | 51    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-15   | 00315C   | 635       | 90001C | 1000  | 1000        | 51    | MOD     | OTHER        | 001        |             | Issac       | 50            |                 |              |
            |            | 2020-06-30   | 00317C   | 635       | 89003C | 1050  | 1050        | 61    | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-07-01   | 00317C   | 635       | 89004C | 1050  | 1050        | 61    | MOD     | OTHER        | 001        |             | Jun         | 50            |                 |              |
        Then 指定執行日期 2020-05-01，來源資料使用 OdQuarterPlusThreeYearNearSource，預期筆數應為 2

    Scenario: 檢查 OdQuarterPlusThreeYearNearSource 多牙
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        Given 設定病人 Danny 24 歲
        Given 設定病人 Jun 24 歲
        Given 設定病人 Kevin 24 歲
        Given 設定病人 Issac 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2017-04-01   | 00315C   | 635       | 89001C | 1000  | 1000        | 5152  | MOD     | OTHER        | 001        |             | Kevin       | 50            |                 |              |
            |            | 2017-04-02   | 00315C   | 635       | 89002C | 1000  | 1000        | 5152  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-15   | 00315C   | 635       | 90001C | 1000  | 1000        | 5152  | MOD     | OTHER        | 001        |             | Issac       | 50            |                 |              |
            |            | 2020-06-30   | 00317C   | 635       | 89003C | 1050  | 1050        | 6162  | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-07-01   | 00317C   | 635       | 89004C | 1050  | 1050        | 6162  | MOD     | OTHER        | 001        |             | Jun         | 50            |                 |              |
        Then 指定執行日期 2020-05-01，來源資料使用 OdQuarterPlusThreeYearNearSource，預期筆數應為 4

    Scenario: 檢查 OdQuarterPlusThreeYearNearByPatientSource
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        Given 設定病人 Danny 24 歲
        Given 設定病人 Jun 24 歲
        Given 設定病人 Kevin 24 歲
        Given 設定病人 Issac 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2017-04-01   | 00315C   | 635       | 89001C | 1000  | 1000        | 5152  | MOD     | OTHER        | 001        |             | Kevin       | 50            |                 |              |
            |            | 2017-04-02   | 00315C   | 635       | 89002C | 1000  | 1000        | 5152  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-15   | 00315C   | 635       | 90001C | 1000  | 1000        | 5152  | MOD     | OTHER        | 001        |             | Issac       | 50            |                 |              |
            |            | 2020-06-30   | 00317C   | 635       | 89003C | 1050  | 1050        | 6162  | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-07-01   | 00317C   | 635       | 89004C | 1050  | 1050        | 6162  | MOD     | OTHER        | 001        |             | Jun         | 50            |                 |              |
        Then 指定執行日期 2020-05-01，來源資料使用 OdQuarterPlusThreeYearNearByPatientSource，預期筆數應為 2

    Scenario: 檢查 OdQuarterSource 單牙
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        Given 設定病人 Danny 24 歲
        Given 設定病人 Jun 24 歲
        Given 設定病人 Kevin 24 歲
        Given 設定病人 Issac 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2020-03-31   | 00315C   | 635       | 89001C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        |             | Kevin       | 50            |                 |              |
            |            | 2020-04-01   | 00315C   | 635       | 89002C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-15   | 00315C   | 635       | 90001C | 1000  | 1000        | 41    | MOD     | OTHER        | 001        |             | Issac       | 50            |                 |              |
            |            | 2020-06-30   | 00317C   | 635       | 89003C | 1050  | 1050        | 61    | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-07-01   | 00317C   | 635       | 89004C | 1050  | 1050        | 61    | MOD     | OTHER        | 001        |             | Jun         | 50            |                 |              |
        Then 指定執行日期 2020-05-01，來源資料使用 OdQuarterSource，預期筆數應為 2

    Scenario: 檢查 OdQuarterSource 多牙
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        Given 設定病人 Danny 24 歲
        Given 設定病人 Jun 24 歲
        Given 設定病人 Kevin 24 歲
        Given 設定病人 Issac 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2020-03-31   | 00315C   | 635       | 89001C | 1000  | 1000        | 1112  | MOD     | OTHER        | 001        |             | Kevin       | 50            |                 |              |
            |            | 2020-04-01   | 00315C   | 635       | 89002C | 1000  | 1000        | 1112  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-15   | 00315C   | 635       | 90001C | 1000  | 1000        | 4142  | MOD     | OTHER        | 001        |             | Issac       | 50            |                 |              |
            |            | 2020-06-30   | 00317C   | 635       | 89003C | 1050  | 1050        | 6162  | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-07-01   | 00317C   | 635       | 89004C | 1050  | 1050        | 6162  | MOD     | OTHER        | 001        |             | Jun         | 50            |                 |              |
        Then 指定執行日期 2020-05-01，來源資料使用 OdQuarterSource，預期筆數應為 4

    Scenario Outline: 檢查非 Deciduous Tooth
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        Given 設定病人 Danny 24 歲
        Given 設定病人 Jun 24 歲
        Given 設定病人 Kevin 24 歲
        Given 設定病人 Issac 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2020-05-01   | 00315C   | 635       | 89001C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        |             | Kevin       | 50            |                 |              |
            |            | 2020-05-01   | 00315C   | 635       | 89001C | 1000  | 1000        | 12    | MOD     | OTHER        | 001        |             | Kevin       | 50            |                 |              |
            |            | 2020-05-01   | 00315C   | 635       | 89001C | 1000  | 1000        | 13    | MOD     | OTHER        | 001        |             | Kevin       | 50            |                 |              |
            |            | 2020-05-01   | 00315C   | 635       | 89001C | 1000  | 1000        | 14    | MOD     | OTHER        | 001        |             | Kevin       | 50            |                 |              |
            |            | 2020-05-01   | 00315C   | 635       | 89001C | 1000  | 1000        | 15    | MOD     | OTHER        | 001        |             | Kevin       | 50            |                 |              |
            |            | 2020-05-01   | 00315C   | 635       | 89001C | 1000  | 1000        | 16    | MOD     | OTHER        | 001        |             | Kevin       | 50            |                 |              |
            |            | 2020-05-01   | 00315C   | 635       | 89001C | 1000  | 1000        | 17    | MOD     | OTHER        | 001        |             | Kevin       | 50            |                 |              |
            |            | 2020-05-01   | 00315C   | 635       | 89001C | 1000  | 1000        | 18    | MOD     | OTHER        | 001        |             | Kevin       | 50            |                 |              |
            |            | 2020-05-01   | 00315C   | 635       | 89001C | 1000  | 1000        | 19    | MOD     | OTHER        | 001        |             | Kevin       | 50            |                 |              |
            |            | 2020-05-01   | 00315C   | 635       | 89001C | 1000  | 1000        | 21    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-01   | 00315C   | 635       | 89001C | 1000  | 1000        | 22    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-01   | 00315C   | 635       | 89001C | 1000  | 1000        | 23    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-01   | 00315C   | 635       | 89001C | 1000  | 1000        | 24    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-01   | 00315C   | 635       | 89001C | 1000  | 1000        | 25    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-01   | 00315C   | 635       | 89001C | 1000  | 1000        | 26    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-01   | 00315C   | 635       | 89001C | 1000  | 1000        | 27    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-01   | 00315C   | 635       | 89001C | 1000  | 1000        | 28    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-01   | 00315C   | 635       | 89001C | 1000  | 1000        | 29    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-01   | 00315C   | 635       | 89001C | 1000  | 1000        | 31    | MOD     | OTHER        | 001        |             | Issac       | 50            |                 |              |
            |            | 2020-05-01   | 00315C   | 635       | 89001C | 1000  | 1000        | 32    | MOD     | OTHER        | 001        |             | Issac       | 50            |                 |              |
            |            | 2020-05-01   | 00315C   | 635       | 89001C | 1000  | 1000        | 33    | MOD     | OTHER        | 001        |             | Issac       | 50            |                 |              |
            |            | 2020-05-01   | 00315C   | 635       | 89001C | 1000  | 1000        | 34    | MOD     | OTHER        | 001        |             | Issac       | 50            |                 |              |
            |            | 2020-05-01   | 00315C   | 635       | 89001C | 1000  | 1000        | 35    | MOD     | OTHER        | 001        |             | Issac       | 50            |                 |              |
            |            | 2020-05-01   | 00315C   | 635       | 89001C | 1000  | 1000        | 36    | MOD     | OTHER        | 001        |             | Issac       | 50            |                 |              |
            |            | 2020-05-01   | 00315C   | 635       | 89001C | 1000  | 1000        | 37    | MOD     | OTHER        | 001        |             | Issac       | 50            |                 |              |
            |            | 2020-05-01   | 00315C   | 635       | 89001C | 1000  | 1000        | 38    | MOD     | OTHER        | 001        |             | Issac       | 50            |                 |              |
            |            | 2020-05-01   | 00315C   | 635       | 89001C | 1000  | 1000        | 39    | MOD     | OTHER        | 001        |             | Issac       | 50            |                 |              |
            |            | 2020-05-01   | 00317C   | 635       | 89001C | 1050  | 1050        | 41    | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-05-01   | 00317C   | 635       | 89001C | 1050  | 1050        | 42    | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-05-01   | 00317C   | 635       | 89001C | 1050  | 1050        | 43    | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-05-01   | 00317C   | 635       | 89001C | 1050  | 1050        | 44    | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-05-01   | 00317C   | 635       | 89001C | 1050  | 1050        | 45    | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-05-01   | 00317C   | 635       | 89001C | 1050  | 1050        | 46    | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-05-01   | 00317C   | 635       | 89001C | 1050  | 1050        | 47    | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-05-01   | 00317C   | 635       | 89001C | 1050  | 1050        | 48    | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-05-01   | 00317C   | 635       | 89001C | 1050  | 1050        | 49    | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-05-01   | 00317C   | 635       | 89001C | 1050  | 1050        | 99    | MOD     | OTHER        | 001        |             | Jun         | 50            |                 |              |
        Then 指定執行日期 2020-05-01，來源資料使用 <Source>，預期筆數應為 <Value>
        Examples:
            | Source                                                  | Value |
            | OdDeciduousMonthSelectedByPatientSource                 | 0     |
            | OdDeciduousOneAndHalfYearNearByPatientSource            | 0     |
            | OdDeciduousOneYearNearByPatientSource                   | 0     |
            | OdDeciduousQuarterByPatientSource                       | 0     |
            | OdDeciduousQuarterPlusOneAndHalfYearNearByPatientSource | 0     |
            | OdDeciduousQuarterPlusOneYearNearByPatientSource        | 0     |
            | OdDeciduousQuarterPlusThreeYearNearByPatientSource      | 0     |
            | OdDeciduousQuarterPlusTwoYearNearByPatientSource        | 0     |

    Scenario Outline: 檢查非 Permanent Tooth
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        Given 設定病人 Danny 24 歲
        Given 設定病人 Kevin 24 歲
        Given 設定病人 Issac 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2020-05-01   | 00315C   | 635       | 89001C | 1000  | 1000        | 51    | MOD     | OTHER        | 001        |             | Kevin       | 50            |                 |              |
            |            | 2020-05-01   | 00315C   | 635       | 89001C | 1000  | 1000        | 52    | MOD     | OTHER        | 001        |             | Kevin       | 50            |                 |              |
            |            | 2020-05-01   | 00315C   | 635       | 89001C | 1000  | 1000        | 53    | MOD     | OTHER        | 001        |             | Kevin       | 50            |                 |              |
            |            | 2020-05-01   | 00315C   | 635       | 89001C | 1000  | 1000        | 54    | MOD     | OTHER        | 001        |             | Kevin       | 50            |                 |              |
            |            | 2020-05-01   | 00315C   | 635       | 89001C | 1000  | 1000        | 55    | MOD     | OTHER        | 001        |             | Kevin       | 50            |                 |              |
            |            | 2020-05-01   | 00315C   | 635       | 89001C | 1000  | 1000        | 61    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-01   | 00315C   | 635       | 89001C | 1000  | 1000        | 62    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-01   | 00315C   | 635       | 89001C | 1000  | 1000        | 63    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-01   | 00315C   | 635       | 89001C | 1000  | 1000        | 64    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-01   | 00315C   | 635       | 89001C | 1000  | 1000        | 65    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-01   | 00315C   | 635       | 89001C | 1000  | 1000        | 71    | MOD     | OTHER        | 001        |             | Issac       | 50            |                 |              |
            |            | 2020-05-01   | 00315C   | 635       | 89001C | 1000  | 1000        | 72    | MOD     | OTHER        | 001        |             | Issac       | 50            |                 |              |
            |            | 2020-05-01   | 00315C   | 635       | 89001C | 1000  | 1000        | 73    | MOD     | OTHER        | 001        |             | Issac       | 50            |                 |              |
            |            | 2020-05-01   | 00315C   | 635       | 89001C | 1000  | 1000        | 74    | MOD     | OTHER        | 001        |             | Issac       | 50            |                 |              |
            |            | 2020-05-01   | 00315C   | 635       | 89001C | 1000  | 1000        | 75    | MOD     | OTHER        | 001        |             | Issac       | 50            |                 |              |
            |            | 2020-05-01   | 00317C   | 635       | 89001C | 1050  | 1050        | 81    | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-05-01   | 00317C   | 635       | 89001C | 1050  | 1050        | 82    | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-05-01   | 00317C   | 635       | 89001C | 1050  | 1050        | 83    | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-05-01   | 00317C   | 635       | 89001C | 1050  | 1050        | 84    | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-05-01   | 00317C   | 635       | 89001C | 1050  | 1050        | 85    | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
        Then 指定執行日期 2020-05-01，來源資料使用 <Source>，預期筆數應為 <Value>
        Examples:
            | Source                                                  | Value |
            | OdPermanentMonthSelectedByPatientSource                 | 0     |
            | OdPermanentQuarterByPatientSource                       | 0     |
            | OdPermanentQuarterPlusOneAndHalfYearNearByPatientSource | 0     |
            | OdPermanentQuarterPlusOneYearNearByPatientSource        | 0     |
            | OdPermanentQuarterPlusThreeYearNearByPatientSource      | 0     |
            | OdPermanentQuarterPlusTwoYearNearByPatientSource        | 0     |

    Scenario Outline: 檢查非 OD Treatment
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2017-04-01   | 00315C   | 635       | 90001C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2017-04-01   | 00315C   | 635       | 90001C | 1000  | 1000        | 51    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
        Then 指定執行日期 2020-05-01，來源資料使用 <Source>，預期筆數應為 <Value>
        Examples:
            | Source                                                  | Value |
            | OdDeciduousMonthSelectedByPatientSource                 | 0     |
            | OdDeciduousOneAndHalfYearNearByPatientSource            | 0     |
            | OdDeciduousOneYearNearByPatientSource                   | 0     |
            | OdDeciduousQuarterByPatientSource                       | 0     |
            | OdDeciduousQuarterPlusOneAndHalfYearNearByPatientSource | 0     |
            | OdDeciduousQuarterPlusOneYearNearByPatientSource        | 0     |
            | OdDeciduousQuarterPlusThreeYearNearByPatientSource      | 0     |
            | OdDeciduousQuarterPlusTwoYearNearByPatientSource        | 0     |
            | OdMonthSelectedByPatientSource                          | 0     |
            | OdMonthSelectedSource                                   | 0     |
            | OdOneAndHalfYearNearSource                              | 0     |
            | OdOneYearNearByPatientSource                            | 0     |
            | OdOneYearNearSource                                     | 0     |
            | OdPermanentMonthSelectedByPatientSource                 | 0     |
            | OdPermanentQuarterByPatientSource                       | 0     |
            | OdPermanentQuarterPlusOneAndHalfYearNearByPatientSource | 0     |
            | OdPermanentQuarterPlusOneYearNearByPatientSource        | 0     |
            | OdPermanentQuarterPlusThreeYearNearByPatientSource      | 0     |
            | OdPermanentQuarterPlusTwoYearNearByPatientSource        | 0     |
            | OdQuarterByPatientSource                                | 0     |
            | OdQuarterPlusOneAndHalfYearNearSource                   | 0     |
            | OdQuarterPlusOneYearNearByPatientSource                 | 0     |
            | OdQuarterPlusOneYearNearSource                          | 0     |
            | OdQuarterPlusThreeYearNearByPatientSource               | 0     |
            | OdQuarterPlusThreeYearNearSource                        | 0     |
            | OdQuarterPlusTwoYearNearByPatientSource                 | 0     |
            | OdQuarterPlusTwoYearNearSource                          | 0     |
            | OdQuarterSource                                         | 0     |

    # Endo And OD
    Scenario: 檢查 EndoAndOdHalfYearNearSource 單牙
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        Given 設定病人 Danny 24 歲
        Given 設定病人 Jun 24 歲
        Given 設定病人 Kevin 24 歲
        Given 設定病人 Issac 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2019-10-03   | 00315C   | 635       | 90001C | 1000  | 1000        | 1112  | MOD     | OTHER        | 001        |             | Kevin       | 50            |                 |              |
            |            | 2019-10-04   | 00315C   | 635       | 90001C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-04-01   | 00315C   | 635       | 92013C | 1000  | 1000        | 1112  | MOD     | OTHER        | 001        |             | Issac       | 50            |                 |              |
            |            | 2020-06-30   | 00317C   | 635       | 89001C | 1050  | 1050        | 11    | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-07-01   | 00317C   | 635       | 89001C | 1050  | 1050        | 1112  | MOD     | OTHER        | 001        |             | Jun         | 50            |                 |              |
        Then 指定執行日期 2020-05-01，來源資料使用 EndoAndOdHalfYearNearSource，預期筆數應為 2

    Scenario: 檢查 EndoAndOdHalfYearNearSource 多牙
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        Given 設定病人 Danny 24 歲
        Given 設定病人 Jun 24 歲
        Given 設定病人 Kevin 24 歲
        Given 設定病人 Issac 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2019-10-03   | 00315C   | 635       | 90001C | 1000  | 1000        | 1112  | MOD     | OTHER        | 001        |             | Kevin       | 50            |                 |              |
            |            | 2019-10-04   | 00315C   | 635       | 90001C | 1000  | 1000        | 1112  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-04-01   | 00315C   | 635       | 92013C | 1000  | 1000        | 1112  | MOD     | OTHER        | 001        |             | Issac       | 50            |                 |              |
            |            | 2020-06-30   | 00317C   | 635       | 89001C | 1050  | 1050        | 1112  | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-07-01   | 00317C   | 635       | 89001C | 1050  | 1050        | 1112  | MOD     | OTHER        | 001        |             | Jun         | 50            |                 |              |
        Then 指定執行日期 2020-05-01，來源資料使用 EndoAndOdHalfYearNearSource，預期筆數應為 4

    Scenario: 檢查 EndoAndOdHalfYearNearByPatientSource
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        Given 設定病人 Danny 24 歲
        Given 設定病人 Jun 24 歲
        Given 設定病人 Kevin 24 歲
        Given 設定病人 Issac 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2019-10-03   | 00315C   | 635       | 90001C | 1000  | 1000        | 1112  | MOD     | OTHER        | 001        |             | Kevin       | 50            |                 |              |
            |            | 2019-10-04   | 00315C   | 635       | 90001C | 1000  | 1000        | 1112  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-04-01   | 00315C   | 635       | 92013C | 1000  | 1000        | 1112  | MOD     | OTHER        | 001        |             | Issac       | 50            |                 |              |
            |            | 2020-06-30   | 00317C   | 635       | 89001C | 1050  | 1050        | 1112  | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-07-01   | 00317C   | 635       | 89001C | 1050  | 1050        | 1112  | MOD     | OTHER        | 001        |             | Jun         | 50            |                 |              |
        Then 指定執行日期 2020-05-01，來源資料使用 EndoAndOdHalfYearNearByPatientSource，預期筆數應為 2

    # Special Code
    Scenario: 檢查 SpecialCodeMonthSelectedSource
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        Given 設定病人 Danny 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2020-05-01   | 00315C   | 635       | 89110C | 1000  | 1000        | 11    | MOD     | P1           | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-02   | 00315C   | 635       | 89110C | 1000  | 1000        | 11    | MOD     | P2           | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-03   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | P3           | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-04   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | P4           | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-05   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | P5           | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-06   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | P6           | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-07   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | P7           | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-08   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | P8           | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-09   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     |              | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-01   | 00315C   | 635       | 89110C | 1000  | 1000        | 11    | MOD     | P1           | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-05-02   | 00315C   | 635       | 89110C | 1000  | 1000        | 11    | MOD     | P2           | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-05-03   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | P3           | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-05-04   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | P4           | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-05-05   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | P5           | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-05-06   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | P6           | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-05-07   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | P7           | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-05-08   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | P8           | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-05-09   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     |              | 001        |             | Danny       | 50            |                 |              |
        Then 指定執行日期 2020-05-01，來源資料使用 SpecialCodeMonthSelectedSource，預期筆數應為 9
