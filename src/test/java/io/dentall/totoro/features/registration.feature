Feature:

    Background:
        Given 建立醫師
        Given Peter 24 歲病人
        Given 建立預約

    Scenario: 簡單產生一個掛號
        Given 建立掛號
        Then 預約建立成功

