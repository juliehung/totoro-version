package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.Source;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.util.List;
import java.util.Objects;

import static io.dentall.totoro.business.service.nhi.metric.util.NhiMetricHelper.applyNewTreatmentPoint;
import static io.dentall.totoro.business.service.nhi.util.NhiProcedureUtil.isExaminationCodeAtSalary;

/**
 * (@Exam-2@ 的代碼點數-00121C 點數)+(@Exam-4@ 的代碼點數-00305C 點數)
 */
public class Point6 extends SingleSourceMetaCalculator<Long> {

    public Point6(MetricConfig metricConfig, Source<?, ?> source) {
        this(metricConfig, null, source);
    }

    public Point6(MetricConfig metricConfig, MetaConfig config, Source<?, ?> source) {
        super(metricConfig, config, source);
    }

    @Override
    public Long doCalculate(MetricConfig metricConfig) {
        MetaConfig config = getConfig();
        Exam2Difference exam2Difference = new Exam2Difference(metricConfig, config, source()).apply();
        Exam4Difference exam4Difference = new Exam4Difference(metricConfig, config, source()).apply();
        return exam2Difference.getResult() + exam4Difference.getResult();
    }

    @Override
    public MetaType metaType() {
        return MetaType.Point6;
    }

}
