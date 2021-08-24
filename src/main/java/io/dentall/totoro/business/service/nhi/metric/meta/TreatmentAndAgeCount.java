package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.Source;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.util.Arrays;
import java.util.List;

import static io.dentall.totoro.business.service.nhi.metric.util.NhiMetricHelper.applyExcludeByVM;
import static io.dentall.totoro.business.service.nhi.metric.util.NhiMetricHelper.applyLegalAge;
import static java.util.Arrays.asList;

/**
 * 限定年齡範圍下的醫令數
 */
public class TreatmentAndAgeCount extends SingleSourceMetaCalculator<Long> {

    private final List<String> codes;

    private int upperAge = Integer.MAX_VALUE;

    private int bottomAge = Integer.MIN_VALUE;

    public TreatmentAndAgeCount(MetricConfig metricConfig, Source<?, ?> source, List<String> codes) {
        this(metricConfig, null, source, codes);
    }

    public TreatmentAndAgeCount(MetricConfig metricConfig, MetaConfig config, Source<?, ?> source, List<String> codes) {
        super(metricConfig, config, source);
        this.codes = codes;
    }

    @Override
    public Long doCalculate(MetricConfig metricConfig) {
        List<NhiMetricRawVM> source = metricConfig.retrieveSource(source().key());
        Exclude exclude = getExclude();
        return source.stream()
            .filter(applyLegalAge(bottomAge, upperAge))
            .filter(vm -> codes.contains(vm.getTreatmentProcedureCode()))
            .filter(applyExcludeByVM(exclude))
            .count();
    }

    @Override
    public MetaType metaType() {
        return MetaType.TreatmentAndAgeCount;
    }

    @Override
    public List<?> getExtraKeyAttribute() {
        return asList(this.codes, this.bottomAge, this.upperAge);
    }

    public TreatmentAndAgeCount setUpperAge(Integer upperAgeInclude) {
        if (upperAgeInclude != null) {
            this.upperAge = upperAgeInclude;
        }
        return this;
    }

    public TreatmentAndAgeCount setBottomAge(Integer bottomAgeInclude) {
        if (bottomAgeInclude != null) {
            this.bottomAge = bottomAgeInclude;
        }
        return this;
    }
}
