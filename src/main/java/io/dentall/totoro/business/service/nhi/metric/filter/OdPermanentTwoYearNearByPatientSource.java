package io.dentall.totoro.business.service.nhi.metric.filter;

import static io.dentall.totoro.business.service.nhi.metric.filter.FilterKey.OdPermanentTwoYearNear;

public class OdPermanentTwoYearNearByPatientSource extends OdPermanentSource {

    @Override
    public String inputKey() {
        return OdPermanentTwoYearNear.input();
    }

    @Override
    public String outputKey() {
        return OdPermanentTwoYearNear.output();
    }
}
