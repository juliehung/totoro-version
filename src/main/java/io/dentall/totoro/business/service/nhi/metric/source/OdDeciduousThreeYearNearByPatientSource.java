package io.dentall.totoro.business.service.nhi.metric.source;

import static io.dentall.totoro.business.service.nhi.metric.source.SourceId.OdDeciduousThreeYearNearByPatient;

public class OdDeciduousThreeYearNearByPatientSource extends OdDeciduousSource {

    @Override
    public String inputKey() {
        return OdDeciduousThreeYearNearByPatient.input();
    }

    @Override
    public String outputKey() {
        return OdDeciduousThreeYearNearByPatient.output();
    }
}
