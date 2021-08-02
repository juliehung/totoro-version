package io.dentall.totoro.business.service.nhi.metric.filter;

import static io.dentall.totoro.business.service.nhi.metric.filter.FilterKey.OdPermanentOneYearNear;

public class OdPermanentOneYearNearByPatientSource extends OdPermanentSource {

    @Override
    public String inputKey() {
        return OdPermanentOneYearNear.input();
    }

    @Override
    public String outputKey() {
        return OdPermanentOneYearNear.output();
    }
}
