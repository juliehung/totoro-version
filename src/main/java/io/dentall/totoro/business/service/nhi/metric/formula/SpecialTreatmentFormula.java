package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.NhiSpecialCode;
import io.dentall.totoro.business.service.nhi.metric.dto.SpecialTreatmentAnalysis;
import io.dentall.totoro.business.service.nhi.metric.filter.Collector;
import io.dentall.totoro.business.service.nhi.metric.filter.Source;
import io.dentall.totoro.business.service.nhi.metric.meta.SpecialTreatment;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.util.List;
import java.util.Map;

/**
 * 特定治療項目
 */
public class SpecialTreatmentFormula extends AbstractFormula<SpecialTreatmentAnalysis> {

    private final Source<NhiMetricRawVM, Map<NhiSpecialCode, List<NhiMetricRawVM>>> source;


    public SpecialTreatmentFormula(Collector collector,
                                   Source<NhiMetricRawVM, Map<NhiSpecialCode, List<NhiMetricRawVM>>> source) {
        super(collector);
        this.source = source;
    }

    @Override
    public SpecialTreatmentAnalysis doCalculate(Collector collector) {
        SpecialTreatment specialTreatment = new SpecialTreatment(collector, source.outputKey()).apply();
        return specialTreatment.getResult();
    }
}
