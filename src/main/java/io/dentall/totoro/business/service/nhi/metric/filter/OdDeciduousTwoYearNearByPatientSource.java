package io.dentall.totoro.business.service.nhi.metric.filter;

import static io.dentall.totoro.business.service.nhi.metric.filter.FilterKey.OdDeciduousTwoYearNear;

public class OdDeciduousTwoYearNearByPatientSource extends OdDeciduousSource {

    @Override
    public String inputKey() {
        return OdDeciduousTwoYearNear.input();
    }

    @Override
    public String outputKey() {
        return OdDeciduousTwoYearNear.output();
    }
}
