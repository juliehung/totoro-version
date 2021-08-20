package io.dentall.totoro.business.service.nhi.metric.source;

import static io.dentall.totoro.business.service.nhi.metric.source.SourceId.OdPermanentTwoYearNearByPatient;

public class OdPermanentTwoYearNearByPatientSource extends OdPermanentSource {

    @Override
    public String inputKey() {
        return OdPermanentTwoYearNearByPatient.input();
    }

    @Override
    public String outputKey() {
        return OdPermanentTwoYearNearByPatient.output();
    }
}
