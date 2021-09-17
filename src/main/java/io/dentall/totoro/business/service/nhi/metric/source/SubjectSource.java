package io.dentall.totoro.business.service.nhi.metric.source;

import io.dentall.totoro.business.service.nhi.metric.meta.Exclude;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.util.List;
import java.util.stream.Stream;

public abstract class SubjectSource extends AbstractSource<NhiMetricRawVM, NhiMetricRawVM> {

    private final MetricConfig metricConfig;

    private final MetricSubject metricSubject;

    public SubjectSource(MetricConfig metricConfig) {
        super(metricConfig.getInitialSource());
        this.metricConfig = metricConfig;
        this.metricSubject = metricConfig.getMetricSubject();
    }

    @Override
    public List<NhiMetricRawVM> doFilter(Stream<NhiMetricRawVM> stream) {
        return this.metricSubject.getFilterFunction().apply(stream);
    }

    @Override
    public void setExclude(Exclude exclude) {
        // 當有被設定Exclude時，將SubjectSource的InputSource改為沒有被設定Exclude的SubjectSource
        // 這樣可以拿到直接過濾好的資料，不用再拿InitialSource重複過濾
        super.setExclude(exclude);
        changeInputSource(metricConfig.getSubjectSource());
    }

}
