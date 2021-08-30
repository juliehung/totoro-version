package io.dentall.totoro.business.service.nhi.metric.source;

import io.dentall.totoro.business.service.nhi.metric.meta.Exclude;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

public abstract class SubjectSource<S extends NhiMetricRawVM, R> extends AbstractSource<S, R> {

    private final MetricConfig metricConfig;

    public SubjectSource(MetricConfig metricConfig) {
        super(metricConfig.getInitialSource());
        this.metricConfig = metricConfig;
    }

    @Override
    public void setExclude(Exclude exclude) {
        // 當有被設定Exclude時，將SubjectSource的InputSource改為沒有被設定Exclude的SubjectSource
        // 這樣可以拿到直接過濾好的資料，不用再拿InitialSource重複過濾
        super.setExclude(exclude);
        changeInputSource(metricConfig.getSubjectSource());
    }

}
