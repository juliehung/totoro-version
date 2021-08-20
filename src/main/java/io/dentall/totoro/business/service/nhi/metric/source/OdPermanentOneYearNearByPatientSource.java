package io.dentall.totoro.business.service.nhi.metric.source;

import static io.dentall.totoro.business.service.nhi.metric.source.SourceId.OdPermanentOneYearNearByPatient;

public class OdPermanentOneYearNearByPatientSource extends OdPermanentSource {

    @Override
    public String inputKey() {
        return OdPermanentOneYearNearByPatient.input();
    }

    @Override
    public String outputKey() {
        return OdPermanentOneYearNearByPatient.output();
    }
}
