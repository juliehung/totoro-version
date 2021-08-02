package io.dentall.totoro.business.service.nhi.metric.filter;

import static io.dentall.totoro.business.service.nhi.metric.filter.FilterKey.OdDeciduousOneYearNear;

public class OdDeciduousOneYearNearByPatientSource extends OdDeciduousSource {

    @Override
    public String inputKey() {
        return OdDeciduousOneYearNear.input();
    }

    @Override
    public String outputKey() {
        return OdDeciduousOneYearNear.output();
    }
}
