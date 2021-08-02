package io.dentall.totoro.business.service.nhi.metric.filter;

import static io.dentall.totoro.business.service.nhi.metric.filter.FilterKey.OdPermanentQuarter;

public class OdPermanentQuarterByPatientSource extends OdPermanentSource {

    @Override
    public String inputKey() {
        return OdPermanentQuarter.input();
    }

    @Override
    public String outputKey() {
        return OdPermanentQuarter.output();
    }
}
