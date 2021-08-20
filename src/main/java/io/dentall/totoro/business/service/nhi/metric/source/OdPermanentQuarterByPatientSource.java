package io.dentall.totoro.business.service.nhi.metric.source;

import static io.dentall.totoro.business.service.nhi.metric.source.SourceId.OdPermanentQuarterByPatient;

public class OdPermanentQuarterByPatientSource extends OdPermanentSource {

    @Override
    public String inputKey() {
        return OdPermanentQuarterByPatient.input();
    }

    @Override
    public String outputKey() {
        return OdPermanentQuarterByPatient.output();
    }
}
