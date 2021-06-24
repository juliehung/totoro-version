Feature:
    測試的基準日為「2020-06-15」，請以該日為基準往前推算想要的日期

    Scenario Outline: 測試日期語義
        Given 測試日期語義 <DateSemantic>
        Then 確認 <DateSemantic> 是否與 <DateExpected> 一樣
        Examples:
            | DateSemantic | DateExpected |
            | 當日           | 2020-06-15   |
            | 昨日           | 2020-06-14   |
            | 前日           | 2020-06-13   |
            | 1個月前         | 2020-05-15   |
            | 2個月前         | 2020-04-15   |
            | 3個月前         | 2020-03-15   |
            | 上個月          | 2020-05-15   |
            | 上上個月         | 2020-04-15   |
            | 上上上個月        | 2020-03-15   |
            | 上個月月初        | 2020-05-01   |
            | 上個月月中        | 2020-05-16   |
            | 上個月月底        | 2020-05-31   |
            | 上上個月月初       | 2020-04-01   |
            | 上上個月月中       | 2020-04-16   |
            | 上上個月月底       | 2020-04-30   |
            | 3月19號        | 2020-03-19   |
            | 3個月前19號      | 2020-03-19   |
            | 上上上個月19號     | 2020-03-19   |
            | 今年           | 2020-06-15   |
            | 去年           | 2019-06-15   |
            | 前年           | 2018-06-15   |
            | 今年初          | 2020-01-01   |
            | 今年中          | 2020-07-01   |
            | 今年底          | 2020-12-31   |
            | 去年初          | 2019-01-01   |
            | 去年中          | 2019-07-01   |
            | 去年底          | 2019-12-31   |
            | 2019年6月15號   | 2019-06-15   |
            | 10年前         | 2010-06-15   |

