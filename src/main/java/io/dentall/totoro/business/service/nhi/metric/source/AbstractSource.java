package io.dentall.totoro.business.service.nhi.metric.source;

import io.dentall.totoro.business.service.nhi.metric.dto.MetricTooth;
import io.dentall.totoro.business.service.nhi.metric.meta.Exclude;

import java.util.List;
import java.util.stream.Stream;

import static io.dentall.totoro.business.service.nhi.metric.util.NhiMetricHelper.applyExcludeByVM;

public abstract class AbstractSource<R> implements Source<MetricTooth, R> {

    private Source<?, ?> inputSource;

    private Exclude exclude;

    public AbstractSource(Source<MetricTooth, ?> inputSource) {
        this.inputSource = inputSource;
    }

    protected void changeInputSource(Source<MetricTooth, ?> inputSource) {
        this.inputSource = inputSource;
    }

    public abstract List<R> doFilter(Stream<MetricTooth> stream);

    @Override
    public List<R> filter(List<MetricTooth> source) {
        Stream<MetricTooth> stream = source.stream().parallel().filter(applyExcludeByVM(getExclude()));
        return doFilter(stream);
    }

    @Override
    public SourceKey key() {
        return new SourceKey(this);
    }

    @Override
    public Source<?, ?> getInputSource() {
        return this.inputSource;
    }

    @Override
    public void setExclude(Exclude exclude) {
        this.exclude = exclude;
    }

    @Override
    public Exclude getExclude() {
        return exclude;
    }

}
