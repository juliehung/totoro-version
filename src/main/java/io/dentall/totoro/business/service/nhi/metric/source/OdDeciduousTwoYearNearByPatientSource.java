package io.dentall.totoro.business.service.nhi.metric.source;

import static io.dentall.totoro.business.service.nhi.metric.source.SourceId.OdDeciduousTwoYearNearByPatient;

public class OdDeciduousTwoYearNearByPatientSource extends OdDeciduousSource {

    @Override
    public String inputKey() {
        return OdDeciduousTwoYearNearByPatient.input();
    }

    @Override
    public String outputKey() {
        return OdDeciduousTwoYearNearByPatient.output();
    }
}
