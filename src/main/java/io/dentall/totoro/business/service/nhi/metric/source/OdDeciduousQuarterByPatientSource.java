package io.dentall.totoro.business.service.nhi.metric.source;

import static io.dentall.totoro.business.service.nhi.metric.source.SourceId.OdDeciduousQuarterByPatient;

public class OdDeciduousQuarterByPatientSource extends OdDeciduousSource {

    @Override
    public String inputKey() {
        return OdDeciduousQuarterByPatient.input();
    }

    @Override
    public String outputKey() {
        return OdDeciduousQuarterByPatient.output();
    }
}
