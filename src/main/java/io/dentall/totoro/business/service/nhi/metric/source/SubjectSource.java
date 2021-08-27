package io.dentall.totoro.business.service.nhi.metric.source;

import io.dentall.totoro.business.service.nhi.metric.meta.Exclude;

public abstract class SubjectSource<S, R> extends AbstractSource<S, R> {

    private final MetricConfig metricConfig;

    private SourceKey key;

    private Exclude exclude;

    public SubjectSource(MetricConfig metricConfig) {
        super(metricConfig.getInitialSource());
        this.metricConfig = metricConfig;
        this.key = new SourceKey(this);
    }

    @Override
    public SourceKey key() {
        return this.key;
    }

    @Override
    public void setExclude(Exclude exclude) {
        // 當有被設定Exclude時，將SubjectSource的InputSource改為沒有被設定Exclude的SubjectSource
        // 這樣可以拿到直接過濾好的資料，不用再拿InitialSource重複過濾
        changeInputSource(metricConfig.getSubjectSource());
        this.exclude = exclude;
        this.key = new SourceKey(this);
    }

    @Override
    public Exclude getExclude() {
        return this.exclude;
    }
}
