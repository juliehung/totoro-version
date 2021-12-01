Feature: 筆記邏輯
    Background:
        Given 建立 Alice 醫師
        Given 建立 Bob 醫師
        Given 建立 Goose 醫師
        Given Cathy 24 歲病人
        Given Duke 24 歲病人
        Given Ema 24 歲病人
        Given Faker 24 歲病人
        Given Hank 24 歲病人

    Scenario: 簡單建立病患筆記
        Given 建立筆記
            | type | content | doctorName | patientName |
            | DOCTOR | content_Alice_Cathy  | Alice | Cathy |
            | DOCTOR | content_Alice_Duke  | Alice | Duke |
            | DOCTOR | content_Alice_Ema  | Alice | Ema |
            | DOCTOR | content_Alice_Duke  | Bob | Cathy |
            | DOCTOR | content_Alice_Duke  | Bob | Duke |
            | SHARED | shared_content | Null | Hank |
            | SERVICE | service_content | Null | Hank |
        Then 查詢醫生 Alice 的筆記
        Then 查詢有關病患 Cathy 的筆記
        Then 查詢有關病患 Hank 的筆記

    Scenario: 查無病患筆記
        Then 應當查無病患 Faker 的筆記

    Scenario: 查無醫師筆記
        Then 應當查無醫師 Goose 的筆記

    Scenario: 產生一筆不存在病患的筆記
        Then 建立筆記失敗
            | type | content | doctorName | patientName |
            | DOCTOR | content_Alice_NotExist  | Alice | NotExist |

    Scenario: 產生一筆不存在醫師的筆記
        Then 建立筆記失敗
            | type | content | doctorName | patientName |
            | DOCTOR | content_NotExist_Cathy  | NotExist | Cathy |
