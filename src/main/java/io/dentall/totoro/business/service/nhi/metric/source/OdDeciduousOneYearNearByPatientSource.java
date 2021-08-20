package io.dentall.totoro.business.service.nhi.metric.source;

import static io.dentall.totoro.business.service.nhi.metric.source.SourceId.OdDeciduousOneYearNearByPatient;

public class OdDeciduousOneYearNearByPatientSource extends OdDeciduousSource {

    @Override
    public String inputKey() {
        return OdDeciduousOneYearNearByPatient.input();
    }

    @Override
    public String outputKey() {
        return OdDeciduousOneYearNearByPatient.output();
    }
}
