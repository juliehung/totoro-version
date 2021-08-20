package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.dto.HighestPatientDto;
import io.dentall.totoro.business.service.nhi.metric.source.Collector;
import io.dentall.totoro.business.service.nhi.metric.source.Source;
import io.dentall.totoro.business.service.nhi.metric.meta.HighestPoint1Patient;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

/**
 * 病患最高總點數佔比 ＠date-15＠ 的 病患合計點數(最高者)/@Point-1@
 */
public class L10Formula extends AbstractFormula<HighestPatientDto> {

    private final Source<NhiMetricRawVM, NhiMetricRawVM> source;

    public L10Formula(Collector collector, Source<NhiMetricRawVM, NhiMetricRawVM> source) {
        super(collector);
        this.source = source;
    }

    @Override
    public HighestPatientDto doCalculate(Collector collector) {
        HighestPoint1Patient highestPoint1Patient = new HighestPoint1Patient(collector, source.outputKey()).apply();
        return highestPoint1Patient.getResult();
    }
}
