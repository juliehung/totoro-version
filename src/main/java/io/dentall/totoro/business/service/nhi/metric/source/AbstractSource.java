package io.dentall.totoro.business.service.nhi.metric.source;

import io.dentall.totoro.business.service.nhi.metric.meta.Exclude;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.util.List;
import java.util.stream.Stream;

import static io.dentall.totoro.business.service.nhi.metric.util.NhiMetricHelper.applyExcludeByVM;

public abstract class AbstractSource<S extends NhiMetricRawVM, R> implements Source<S, R> {

    private Source<?, ?> inputSource;

    private SourceKey key;

    private Exclude exclude;

    public AbstractSource(Source<?, ?> inputSource) {
        this.inputSource = inputSource;
        this.key = new SourceKey(this);
    }

    protected void changeInputSource(Source<?, ?> inputSource) {
        this.inputSource = inputSource;
    }

    public abstract List<R> doFilter(Stream<S> stream);

    @Override
    public List<R> filter(List<S> source) {
        Stream<S> stream = source.stream().parallel().filter(applyExcludeByVM(getExclude()));
        return doFilter(stream);
    }

    @Override
    public SourceKey key() {
        return key;
    }

    @Override
    public Source<?, ?> getInputSource() {
        return this.inputSource;
    }

    @Override
    public void setExclude(Exclude exclude) {
        this.exclude = exclude;
        this.key = new SourceKey(this);
    }

    @Override
    public Exclude getExclude() {
        return exclude;
    }

}
