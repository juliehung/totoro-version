package io.dentall.totoro.business.service.nhi.metric.source;

import static io.dentall.totoro.business.service.nhi.metric.source.SourceId.OdPermanentThreeYearNearByPatient;

public class OdPermanentThreeYearNearByPatientSource extends OdPermanentSource {

    @Override
    public String inputKey() {
        return OdPermanentThreeYearNearByPatient.input();
    }

    @Override
    public String outputKey() {
        return OdPermanentThreeYearNearByPatient.output();
    }
}
