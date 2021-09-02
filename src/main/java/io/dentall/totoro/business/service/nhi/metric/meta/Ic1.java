package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.Source;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.util.List;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * 卡數
 * <p>
 * "處置單有診察費且有卡號或異常代碼，不重複計算
 * >> 不重複計算指：一張處置單卡號 0001, 內容有 89001C, 89002C. 若計算補牙之卡數，則為 1."
 */
public class Ic1 extends SingleSourceMetaCalculator<Long> {

    public Ic1(MetricConfig metricConfig, Source<?, ?> source) {
        this(metricConfig, null, source);
    }

    public Ic1(MetricConfig metricConfig, MetaConfig metaConfig, Source<?, ?> source) {
        super(metricConfig, metaConfig, source);
    }

    @Override
    public Long doCalculate(MetricConfig metricConfig) {
        List<NhiMetricRawVM> nhiMetricRawVMList = metricConfig.retrieveSource(source().key());

        return nhiMetricRawVMList.stream()
            .filter(vm -> isNotBlank(vm.getExamPoint()))
            .filter(vm -> isNotBlank(vm.getCardNumber()))
            .count();
    }

    @Override
    public MetaType metaType() {
        return MetaType.Ic1;
    }
}
