package io.dentall.totoro.business.service.nhi.metric.source;

import io.dentall.totoro.business.service.nhi.metric.dto.MetricDisposal;
import io.dentall.totoro.business.service.nhi.metric.meta.Exclude;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public abstract class SubjectDisposalSource extends AbstractDisposalSource<MetricDisposal> {

    private final MetricConfig metricConfig;

    private final MetricSubject metricSubject;

    public SubjectDisposalSource(MetricConfig metricConfig) {
        super(metricConfig.getDisposalSource());
        this.metricConfig = metricConfig;
        this.metricSubject = metricConfig.getMetricSubject();
    }

    @Override
    public List<MetricDisposal> doFilter(Stream<MetricDisposal> stream) {
        return this.metricSubject.getDisposalFilterFunction().apply(stream).collect(toList());
    }

    @Override
    public void setExclude(Exclude exclude) {
        // 當有被設定Exclude時，將SubjectSource的InputSource改為沒有被設定Exclude的SubjectDisposalSource
        // 這樣可以拿到直接過濾好的資料，不用再拿DisposalSource重複過濾
        super.setExclude(exclude);
        changeInputSource(metricConfig.getSubjectDisposalSource());
    }

}
