package io.dentall.totoro.business.service.nhi.metric.filter;

public enum FilterKey {

    Subject(FilterKey.initial, FilterKey.subject),
    ThreeYearNear(Subject.output, FilterKey.threeYearNear),
    TwoYearNear(ThreeYearNear.output, FilterKey.twoYearNear),
    OneYearNear(TwoYearNear.output, FilterKey.oneYearNear),
    HalfYearNear(OneYearNear.output, FilterKey.halfYearNear),
    Quarter(Subject.output, FilterKey.quarter),
    ThreeMonthNear(HalfYearNear.output, FilterKey.threeMonthNear),
    MonthSelected(Quarter.output, FilterKey.monthSelected),
    DailyByMonthSelected(MonthSelected.output, FilterKey.dailyByMonthSelected),

    SpecialCodeMonthSelected(MonthSelected.output, FilterKey.specialCodeMonthSelected),

    OdThreeYearNear(Subject.output, FilterKey.odThreeYearNear),
    OdPermanentThreeYearNear(OdThreeYearNear.output, FilterKey.odPermanentThreeYearNear),
    OdDeciduousThreeYearNear(OdThreeYearNear.output, FilterKey.odDeciduousThreeYearNear),

    OdTwoYearNear(OdThreeYearNear.output, FilterKey.odTwoYearNear),
    OdPermanentTwoYearNear(OdTwoYearNear.output, FilterKey.odPermanentTwoYearNear),
    OdDeciduousTwoYearNear(OdTwoYearNear.output, FilterKey.odDeciduousTwoYearNear),

    OdOneYearNear(OdTwoYearNear.output, FilterKey.odOneYearNear),
    OdPermanentOneYearNear(OdOneYearNear.output, FilterKey.odPermanentOneYearNear),
    OdDeciduousOneYearNear(OdOneYearNear.output, FilterKey.odDeciduousOneYearNear),

    OdQuarter(Quarter.output, FilterKey.odQuarter),
    OdPermanentQuarter(OdQuarter.output, FilterKey.odPermanentQuarter),
    OdDeciduousQuarter(OdQuarter.output, FilterKey.odDeciduousQuarter),

    OdMonthSelected(OdQuarter.output, FilterKey.odMonthSelected);

    private static final String initial = "initial";
    private static final String subject = "subject";
    private static final String monthSelected = "monthSelected";
    private static final String dailyByMonthSelected = "dailyByMonthSelected";
    private static final String quarter = "quarter";
    private static final String threeMonthNear = "threeMonthNear";
    private static final String oneYearNear = "oneYearNear";
    private static final String twoYearNear = "twoYearNear";
    private static final String threeYearNear = "threeYearNear";
    private static final String halfYearNear = "halfYearNear";

    private static final String specialCodeMonthSelected = "specialCodeMonthSelected";

    private static final String odOneYearNear = "odOneYearNear";
    private static final String odPermanentOneYearNear = "odPermanentOneYearNear";
    private static final String odDeciduousOneYearNear = "odDeciduousOneYearNear";

    private static final String odTwoYearNear = "odTwoYearNear";
    private static final String odDeciduousTwoYearNear = "odDeciduousTwoYearNear";
    private static final String odPermanentTwoYearNear = "odPermanentTwoYearNear";

    private static final String odThreeYearNear = "odThreeYearNear";
    private static final String odDeciduousThreeYearNear = "odDeciduousThreeYearNear";
    private static final String odPermanentThreeYearNear = "odPermanentThreeYearNear";

    private static final String odQuarter = "odQuarter";
    private static final String odPermanentQuarter = "odPermanentQuarter";
    private static final String odDeciduousQuarter = "odDeciduousQuarter";

    private static final String odMonthSelected = "odMonthSelected";

    private final String input;
    private final String output;

    FilterKey(String input, String output) {
        this.input = input;
        this.output = output;
    }

    public String input() {
        return input;
    }

    public String output() {
        return output;
    }
}
