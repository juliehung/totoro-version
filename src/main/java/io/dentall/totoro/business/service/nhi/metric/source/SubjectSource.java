package io.dentall.totoro.business.service.nhi.metric.source;

import io.dentall.totoro.business.service.nhi.metric.dto.MetricTooth;
import io.dentall.totoro.business.service.nhi.metric.meta.Exclude;

import java.util.List;
import java.util.stream.Stream;

import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;

public abstract class SubjectSource extends AbstractSource<MetricTooth> {

    private final MetricConfig metricConfig;

    private final MetricSubject metricSubject;

    public SubjectSource(MetricConfig metricConfig) {
        super(metricConfig.getToothSource());
        this.metricConfig = metricConfig;
        this.metricSubject = metricConfig.getMetricSubject();
    }

    @Override
    public List<MetricTooth> doFilter(Stream<MetricTooth> stream) {
        return this.metricSubject.getFilterFunction().apply(stream).filter(data -> nonNull(data.getTreatmentProcedureCode())).collect(toList());
    }

    @Override
    public void setExclude(Exclude exclude) {
        // 當有被設定Exclude時，將SubjectSource的InputSource改為沒有被設定Exclude的SubjectSource
        // 這樣可以拿到直接過濾好的資料，不用再拿ToothSource重複過濾
        super.setExclude(exclude);
        changeInputSource(metricConfig.getSubjectSource());
    }

}
