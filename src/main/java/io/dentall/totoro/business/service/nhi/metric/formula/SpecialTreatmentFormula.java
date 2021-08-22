package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.NhiSpecialCode;
import io.dentall.totoro.business.service.nhi.metric.dto.SpecialTreatmentAnalysisDto;
import io.dentall.totoro.business.service.nhi.metric.meta.SpecialTreatment;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.Source;
import io.dentall.totoro.business.service.nhi.metric.source.SpecialCodeMonthSelectedSource;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.util.List;
import java.util.Map;

/**
 * 特定治療項目
 */
public class SpecialTreatmentFormula extends AbstractFormula<SpecialTreatmentAnalysisDto> {

    private final Source<NhiMetricRawVM, Map<NhiSpecialCode, List<NhiMetricRawVM>>> source;


    public SpecialTreatmentFormula(MetricConfig metricConfig) {
        super(metricConfig);
        this.source = new SpecialCodeMonthSelectedSource(metricConfig);
    }

    @Override
    public SpecialTreatmentAnalysisDto doCalculate(MetricConfig metricConfig) {
        SpecialTreatment specialTreatment = new SpecialTreatment(metricConfig, source).apply();
        return specialTreatment.getResult();
    }
}
