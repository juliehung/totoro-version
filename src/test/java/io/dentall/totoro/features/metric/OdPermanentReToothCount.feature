@metric @meta
Feature: OD-Permanent-ReToothCount

  Scenario: 計算OD-Permanent-ReToothCount 單人 單顆
    Given 設定指標主體類型為醫師 Stan
    Given 設定病人 Jerry 24 歲
    When 設定指標資料
      | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
      | 1          | 2020-05-01   | 00315C   | 635       | 89001C | 450   | 450         | 1112  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
      | -1         | 2019-05-02   | 00315C   | 635       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
    Then 指定執行日期 2020-05-01，補牙時間範圍 OdPermanentQuarterByPatientSource／重補時間範圍 OdPermanentOneYearNearByPatientSource，檢查 OdPermanentReToothCount，計算結果數值應為 1

  Scenario: 計算OD-Permanent-ReToothCount 多人 單顆
    Given 設定指標主體類型為醫師 Stan
    Given 設定病人 Jerry 24 歲
    Given 設定病人 Danny 24 歲
    Given 設定病人 Jun 24 歲
    When 設定指標資料
      | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
      | 1          | 2020-05-01   | 00315C   | 635       | 89001C | 450   | 450         | 1112  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
      | 2          | 2020-05-01   | 00315C   | 635       | 89001C | 450   | 450         | 1112  | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
      | 3          | 2020-05-01   | 00315C   | 635       | 89001C | 450   | 450         | 1112  | MOD     | OTHER        | 001        |             | Jun         | 50            |                 |              |
      | -1         | 2019-05-02   | 00315C   | 635       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
      | -2         | 2019-05-02   | 00315C   | 635       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
      | -3         | 2019-05-02   | 00315C   | 635       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jun         | 50            |                 |              |
    Then 指定執行日期 2020-05-01，補牙時間範圍 OdPermanentQuarterByPatientSource／重補時間範圍 OdPermanentOneYearNearByPatientSource，檢查 OdPermanentReToothCount，計算結果數值應為 3

  Scenario: 計算OD-Permanent-ReToothCount 多人 多顆
    Given 設定指標主體類型為醫師 Stan
    Given 設定病人 Jerry 24 歲
    Given 設定病人 Danny 24 歲
    Given 設定病人 Jun 24 歲
    When 設定指標資料
      | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
      | 1          | 2020-05-01   | 00315C   | 635       | 89001C | 450   | 450         | 1112  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
      | 2          | 2020-05-01   | 00315C   | 635       | 89001C | 450   | 450         | 1112  | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
      | 3          | 2020-05-01   | 00315C   | 635       | 89001C | 450   | 450         | 1112  | MOD     | OTHER        | 001        |             | Jun         | 50            |                 |              |
      | -1         | 2019-05-02   | 00315C   | 635       | 89001C | 450   | 450         | 1112  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
      | -2         | 2019-05-02   | 00315C   | 635       | 89001C | 450   | 450         | 1112  | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
      | -3         | 2019-05-02   | 00315C   | 635       | 89001C | 450   | 450         | 1112  | MOD     | OTHER        | 001        |             | Jun         | 50            |                 |              |
    Then 指定執行日期 2020-05-01，補牙時間範圍 OdPermanentQuarterByPatientSource／重補時間範圍 OdPermanentOneYearNearByPatientSource，檢查 OdPermanentReToothCount，計算結果數值應為 6
