package io.dentall.totoro.business.service.nhi.metric.filter;

public enum FilterKey {

    Subject(FilterKey.initial, FilterKey.subject),
    MonthSelected(Subject.output, FilterKey.monthSelected),
    Quarter(Subject.output, FilterKey.quarter),
    ThreeMonthNear(Subject.output, FilterKey.threeMonthNear),
    OneYearNear(Subject.output, FilterKey.oneYearNear),
    HalfYearNear(Subject.output, FilterKey.halfYearNear);

    private static final String initial = "initial";
    private static final String subject = "subject";
    private static final String monthSelected = "monthSelected";
    private static final String quarter = "quarter";
    private static final String threeMonthNear = "threeMonthNear";
    private static final String oneYearNear= "oneYearNear";
    private static final String halfYearNear= "halfYearNear";

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
