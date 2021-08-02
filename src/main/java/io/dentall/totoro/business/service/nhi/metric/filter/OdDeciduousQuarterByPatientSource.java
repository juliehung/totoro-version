package io.dentall.totoro.business.service.nhi.metric.filter;

import static io.dentall.totoro.business.service.nhi.metric.filter.FilterKey.OdDeciduousQuarter;

public class OdDeciduousQuarterByPatientSource extends OdDeciduousSource {

    @Override
    public String inputKey() {
        return OdDeciduousQuarter.input();
    }

    @Override
    public String outputKey() {
        return OdDeciduousQuarter.output();
    }
}
