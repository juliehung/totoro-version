@metric @meta
Feature: OD1-ReToothCount

    Scenario: 計算OD1-ReToothCount 單人 單顆
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2020-05-01   | 00315C   | 635       | 89001C | 450   | 450         | 1112  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2019-05-02   | 00315C   | 635       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
        Then 指定執行日期 2020-05-01，補牙時間範圍 OdQuarterByPatientSource／重補時間範圍 OdQuarterPlusOneYearNearByPatientSource，檢查 Od1ReToothCount，計算結果數值應為 1


    Scenario: 計算OD1-ReToothCount 多人 單顆
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        Given 設定病人 Danny 24 歲
        Given 設定病人 Jun 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2020-05-01   | 00315C   | 635       | 89001C | 450   | 450         | 1112  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-01   | 00315C   | 635       | 89001C | 450   | 450         | 1112  | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-05-01   | 00315C   | 635       | 89001C | 450   | 450         | 1112  | MOD     | OTHER        | 001        |             | Jun         | 50            |                 |              |
            |            | 2019-05-02   | 00315C   | 635       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2019-05-02   | 00315C   | 635       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            |            | 2019-05-02   | 00315C   | 635       | 89001C | 450   | 450         | 11    | MOD     | OTHER        | 001        |             | Jun         | 50            |                 |              |
        Then 指定執行日期 2020-05-01，補牙時間範圍 OdQuarterByPatientSource／重補時間範圍 OdQuarterPlusOneYearNearByPatientSource，檢查 Od1ReToothCount，計算結果數值應為 3

    Scenario: 計算OD1-ReToothCount 多人 多顆
        Given 設定指標主體類型為醫師 Stan
        Given 設定病人 Jerry 24 歲
        Given 設定病人 Danny 24 歲
        Given 設定病人 Jun 24 歲
        When 設定指標資料
            | DisposalId | DisposalDate | ExamCode | ExamPoint | Code   | Point | OriginPoint | Tooth | Surface | SpecificCode | CardNumber | NhiCategory | PatientName | PartialBurden | PatientIdentity | SerialNumber |
            |            | 2020-05-01   | 00315C   | 635       | 89001C | 450   | 450         | 1112  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2020-05-01   | 00315C   | 635       | 89001C | 450   | 450         | 1112  | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            |            | 2020-05-01   | 00315C   | 635       | 89001C | 450   | 450         | 1112  | MOD     | OTHER        | 001        |             | Jun         | 50            |                 |              |
            |            | 2019-05-02   | 00315C   | 635       | 89001C | 450   | 450         | 1112  | MOD     | OTHER        | 001        |             | Jerry       | 50            |                 |              |
            |            | 2019-05-02   | 00315C   | 635       | 89001C | 450   | 450         | 1112  | MOD     | OTHER        | 001        |             | Danny       | 50            |                 |              |
            |            | 2019-05-02   | 00315C   | 635       | 89001C | 450   | 450         | 1112  | MOD     | OTHER        | 001        |             | Jun         | 50            |                 |              |
        Then 指定執行日期 2020-05-01，補牙時間範圍 OdQuarterByPatientSource／重補時間範圍 OdQuarterPlusOneYearNearByPatientSource，檢查 Od1ReToothCount，計算結果數值應為 6
