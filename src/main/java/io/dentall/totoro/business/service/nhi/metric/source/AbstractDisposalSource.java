package io.dentall.totoro.business.service.nhi.metric.source;

import io.dentall.totoro.business.service.nhi.metric.dto.MetricDisposal;
import io.dentall.totoro.business.service.nhi.metric.meta.Exclude;

import java.util.List;
import java.util.stream.Stream;

import static io.dentall.totoro.business.service.nhi.metric.util.NhiMetricHelper.applyExcludeByVM;

public abstract class AbstractDisposalSource<R> implements Source<MetricDisposal, R> {

    private Source<MetricDisposal, ?> inputSource;

    private Exclude exclude;

    public AbstractDisposalSource(Source<MetricDisposal, ?> inputSource) {
        this.inputSource = inputSource;
    }

    protected void changeInputSource(Source<MetricDisposal, ?> inputSource) {
        this.inputSource = inputSource;
    }

    public abstract List<R> doFilter(Stream<MetricDisposal> stream);

    @Override
    public List<R> filter(List<MetricDisposal> source) {
        Stream<MetricDisposal> stream =
            source.stream().parallel()
                .filter(disposal -> disposal.getToothList().size() == 0 || disposal.getToothList().stream().anyMatch(applyExcludeByVM(getExclude())));
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
