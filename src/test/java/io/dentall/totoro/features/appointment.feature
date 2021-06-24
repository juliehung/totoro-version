Feature:

    Background:
        Given 建立醫師
        Given Peter 24 歲病人

    Scenario: 簡單產生一個預約
        Given 建立預約
        Then 預約建立成功

    Scenario:
        Given 在過去第 10 天，建立預約
        Then 確定預約是建立在過去第 10 天
