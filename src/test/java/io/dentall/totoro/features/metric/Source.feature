@metric @meta
Feature: 資料源

  #  主體資料
  Scenario: 檢查 ClinicSource
    Given 設定指標主體類型為醫師 Stan
    Given 設定病人 Jerry 24 歲
    When 設定指標資料
      | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | DoctorName | PatientName | PartialBurden | PatientIdentity | SerialNumber |
      | 1          | 2020-05-01   | 00315C   | 635       | 89110C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        |             | Kevin      | Jerry       | 50            |                 |              |
      | 2          | 2020-05-01   | 00315C   | 635       | 89110C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        |             |            | Jerry       | 50            |                 |              |
      | 3          | 2020-05-31   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | 001        |             |            | Jerry       | 50            |                 |              |
      | 4          | 2020-05-31   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | 001        |             | Kevin      | Jerry       | 50            |                 |              |
    Then 指定執行日期 2020-05-01，來源資料使用 ClinicSource，預期筆數應為 4

  Scenario: 檢查 DoctorSource
    Given 設定指標主體類型為醫師 Stan
    Given 設定病人 Jerry 24 歲
    When 設定指標資料
      | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | DoctorName | PatientName | PartialBurden | PatientIdentity | SerialNumber |
      | 1          | 2020-05-01   | 00315C   | 635       | 89110C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        |             | Kevin      | Jerry       | 50            |                 |              |
      | 2          | 2020-05-01   | 00315C   | 635       | 89110C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        |             |            | Jerry       | 50            |                 |              |
      | 3          | 2020-05-31   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | 001        |             |            | Jerry       | 50            |                 |              |
      | 4          | 2020-05-31   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | 001        |             | Kevin      | Jerry       | 50            |                 |              |
    Then 指定執行日期 2020-05-01，來源資料使用 DoctorSource，預期筆數應為 2

  # 時間區間資料
  Scenario: 檢查 MonthSelectedSource
    Given 設定指標主體類型為醫師 Stan
    Given 設定病人 Jerry 24 歲
    When 設定指標資料
      | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
      | 1          | 2020-04-30   | 00315C   | 635       | 89110C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
      | 2          | 2020-05-01   | 00315C   | 635       | 89110C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
      | 3          | 2020-05-31   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
      | 4          | 2020-06-01   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
    Then 指定執行日期 2020-05-01，來源資料使用 MonthSelectedSource，預期筆數應為 2

  Scenario: 檢查 ThreeMonthNearSource
    Given 設定指標主體類型為醫師 Stan
    Given 設定病人 Jerry 24 歲
    When 設定指標資料
      | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
      | 1          | 2020-02-28   | 00315C   | 635       | 89110C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
      | 2          | 2020-03-01   | 00315C   | 635       | 89110C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
      | 3          | 2020-05-31   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
      | 4          | 2020-06-01   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
    Then 指定執行日期 2020-05-01，來源資料使用 ThreeMonthNearSource，預期筆數應為 2

  Scenario: 檢查 QuarterSource
    Given 設定指標主體類型為醫師 Stan
    Given 設定病人 Jerry 24 歲
    When 設定指標資料
      | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
      | 1          | 2020-03-31   | 00315C   | 635       | 89110C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
      | 2          | 2020-04-01   | 00315C   | 635       | 89110C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
      | 3          | 2020-06-30   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
      | 4          | 2020-07-01   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
    Then 指定執行日期 2020-05-01，來源資料使用 QuarterSource，預期筆數應為 2

  Scenario: 檢查 QuarterOfLastYearSource
    Given 設定指標主體類型為醫師 Stan
    Given 設定病人 Jerry 24 歲
    When 設定指標資料
      | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
      | 1          | 2019-03-31   | 00315C   | 635       | 89110C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
      | 2          | 2019-04-01   | 00315C   | 635       | 89110C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
      | 3          | 2019-06-30   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
      | 4          | 2019-07-01   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
    Then 指定執行日期 2020-05-01，來源資料使用 QuarterOfLastYearSource，預期筆數應為 2

  Scenario: 檢查 HalfYearNearSource
    Given 設定指標主體類型為醫師 Stan
    Given 設定病人 Jerry 24 歲
    When 設定指標資料
      | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
      | 1          | 2019-12-02   | 00315C   | 635       | 89110C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
      | 2          | 2019-12-03   | 00315C   | 635       | 89110C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
      | 3          | 2020-05-31   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
      | 4          | 2020-06-01   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
    Then 指定執行日期 2020-05-01，來源資料使用 HalfYearNearSource，預期筆數應為 2

  Scenario: 檢查 OneYearNearSource
    Given 設定指標主體類型為醫師 Stan
    Given 設定病人 Jerry 24 歲
    When 設定指標資料
      | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
      | 1          | 2019-05-31   | 00315C   | 635       | 89110C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
      | 2          | 2019-06-01   | 00315C   | 635       | 89110C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
      | 3          | 2020-05-31   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
      | 4          | 2020-06-01   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
    Then 指定執行日期 2020-05-01，來源資料使用 OneYearNearSource，預期筆數應為 2

  Scenario: 檢查 TwoYearNearSource
    Given 設定指標主體類型為醫師 Stan
    Given 設定病人 Jerry 24 歲
    When 設定指標資料
      | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
      | 1          | 2018-05-31   | 00315C   | 635       | 89110C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
      | 2          | 2018-06-01   | 00315C   | 635       | 89110C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
      | 3          | 2020-05-31   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
      | 4          | 2020-06-01   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
    Then 指定執行日期 2020-05-01，來源資料使用 TwoYearNearSource，預期筆數應為 2

  Scenario: 檢查 ThreeYearNearSource
    Given 設定指標主體類型為醫師 Stan
    Given 設定病人 Jerry 24 歲
    When 設定指標資料
      | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
      | 1          | 2017-05-31   | 00315C   | 635       | 89110C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
      | 2          | 2017-06-01   | 00315C   | 635       | 89110C | 1000  | 1000        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
      | 3          | 2020-05-31   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
      | 4          | 2020-06-01   | 00317C   | 635       | 89112C | 1050  | 1050        | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
    Then 指定執行日期 2020-05-01，來源資料使用 ThreeYearNearSource，預期筆數應為 2

  # 拔牙資料
  Scenario: 檢查 ExtQuarterSource
    Given 設定指標主體類型為醫師 Stan
    Given 設定病人 Jerry 24 歲
    When 設定指標資料
      | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
      | 1          | 2020-03-31   | 00315C   | 635       | 92013C | 1000  | 1000        | 1112  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
      | 2          | 2020-04-01   | 00315C   | 635       | 92014C | 1000  | 1000        | 1112  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
      | 3          | 2020-06-30   | 00317C   | 635       | 89112C | 1050  | 1050        | 1112  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
      | 4          | 2020-07-01   | 00317C   | 635       | 92016C | 1050  | 1050        | 1112  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
    Then 指定執行日期 2020-05-01，來源資料使用 ExtQuarterSource，預期筆數應為 2

  Scenario: 檢查 ExtQuarterByPatientSource
    Given 設定指標主體類型為醫師 Stan
    Given 設定病人 Jerry 24 歲
    Given 設定病人 Danny 24 歲
    When 設定指標資料
      | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
      | 1          | 2020-03-31   | 00315C   | 635       | 92013C | 1000  | 1000        | 1112  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
      | 2          | 2020-04-01   | 00315C   | 635       | 92014C | 1000  | 1000        | 1112  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
      | 3          | 2020-06-30   | 00317C   | 635       | 92015C | 1050  | 1050        | 1112  | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
      | 4          | 2020-07-01   | 00317C   | 635       | 92016C | 1050  | 1050        | 1112  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
    Then 指定執行日期 2020-05-01，來源資料使用 ExtQuarterByPatientSource，預期筆數應為 2

  # OD
  Scenario: 檢查 OdDeciduousQuarterByPatientSource
    Given 設定指標主體類型為醫師 Stan
    Given 設定病人 Jerry 24 歲
    Given 設定病人 Danny 24 歲
    When 設定指標資料
      | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
      | 1          | 2020-03-31   | 00315C   | 635       | 89001C | 1000  | 1000        | 5152  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
      | 2          | 2020-04-01   | 00315C   | 635       | 89002C | 1000  | 1000        | 5152  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
      | 3          | 2020-05-15   | 00315C   | 635       | 90001C | 1000  | 1000        | 5152  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
      | 4          | 2020-06-30   | 00317C   | 635       | 89003C | 1050  | 1050        | 6162  | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
      | 5          | 2020-07-01   | 00317C   | 635       | 89004C | 1050  | 1050        | 6162  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
    Then 指定執行日期 2020-05-01，來源資料使用 OdDeciduousQuarterByPatientSource，預期筆數應為 2

  # Endo And OD
  Scenario: 檢查 EndoAndOdHalfYearNearSource
    Given 設定指標主體類型為醫師 Stan
    Given 設定病人 Jerry 24 歲
    When 設定指標資料
      | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
      | 1          | 2019-10-03   | 00315C   | 635       | 90001C | 1000  | 1000        | 1112  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
      | 2          | 2019-10-04   | 00315C   | 635       | 90001C | 1000  | 1000        | 1112  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
      | 3          | 2020-04-01   | 00315C   | 635       | 92013C | 1000  | 1000        | 1112  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
      | 4          | 2020-06-30   | 00317C   | 635       | 89001C | 1050  | 1050        | 1112  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
      | 5          | 2020-07-01   | 00317C   | 635       | 89001C | 1050  | 1050        | 1112  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
    Then 指定執行日期 2020-05-01，來源資料使用 EndoAndOdHalfYearNearSource，預期筆數應為 4
