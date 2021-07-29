package io.dentall.totoro.business.service.nhi.metric.filter;

public enum FilterKey {

    Subject(FilterKey.initial, FilterKey.subject),
    MonthSelected(Subject.output, FilterKey.monthSelected);

    private static final String initial = "initial";
    private static final String subject = "subject";
    private static final String monthSelected = "monthSelected";

    private String input;
    private String output;

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
