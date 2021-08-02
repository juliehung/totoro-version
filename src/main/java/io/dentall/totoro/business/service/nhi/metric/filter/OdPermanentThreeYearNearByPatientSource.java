package io.dentall.totoro.business.service.nhi.metric.filter;

import static io.dentall.totoro.business.service.nhi.metric.filter.FilterKey.OdPermanentThreeYearNear;

public class OdPermanentThreeYearNearByPatientSource extends OdPermanentSource {

    @Override
    public String inputKey() {
        return OdPermanentThreeYearNear.input();
    }

    @Override
    public String outputKey() {
        return OdPermanentThreeYearNear.output();
    }
}
