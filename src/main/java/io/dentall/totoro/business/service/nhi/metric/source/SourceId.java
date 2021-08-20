package io.dentall.totoro.business.service.nhi.metric.source;

public enum SourceId {

    Initial(),
    Subject(Initial),
    ThreeYearNear(Subject),
    TwoYearNear(ThreeYearNear),
    OneYearNear(TwoYearNear),
    HalfYearNear(OneYearNear),
    Quarter(Subject),
    ThreeMonthNear(HalfYearNear),
    MonthSelected(Quarter),
    DailyByMonthSelected(MonthSelected),

    SpecialCodeMonthSelected(MonthSelected),

    OdThreeYearNear(Subject),
    OdThreeYearNearByPatient(OdThreeYearNear),
    OdPermanentThreeYearNearByPatient(OdThreeYearNear),
    OdDeciduousThreeYearNearByPatient(OdThreeYearNear),

    OdTwoYearNear(OdThreeYearNear),
    OdPermanentTwoYearNearByPatient(OdTwoYearNear),
    OdDeciduousTwoYearNearByPatient(OdTwoYearNear),

    OdOneYearNear(OdTwoYearNear),
    OdPermanentOneYearNearByPatient(OdOneYearNear),
    OdDeciduousOneYearNearByPatient(OdOneYearNear),

    OdQuarter(Quarter),
    OdQuarterByPatient(OdQuarter),
    OdPermanentQuarterByPatient(OdQuarter),
    OdDeciduousQuarterByPatient(OdQuarter),

    OdMonthSelected(OdQuarter);

    private final SourceId input;

    SourceId() {
        this.input = this;
    }

    SourceId(SourceId input) {
        this.input = input;
    }

    public String input() {
        return input.name();
    }

    public String output() {
        return this.name();
    }
}
