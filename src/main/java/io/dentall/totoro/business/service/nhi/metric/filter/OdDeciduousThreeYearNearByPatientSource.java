package io.dentall.totoro.business.service.nhi.metric.filter;

import static io.dentall.totoro.business.service.nhi.metric.filter.FilterKey.OdDeciduousThreeYearNear;

public class OdDeciduousThreeYearNearByPatientSource extends OdDeciduousSource {

    @Override
    public String inputKey() {
        return OdDeciduousThreeYearNear.input();
    }

    @Override
    public String outputKey() {
        return OdDeciduousThreeYearNear.output();
    }
}
