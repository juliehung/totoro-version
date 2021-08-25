package io.dentall.totoro.business.service.nhi.metric.source;

public abstract class AbstractSource<S, R> implements Source<S, R> {

    private final Source<?, ?> inputSource;

    private final SourceKey key;

    public AbstractSource(Source<?, ?> inputSource) {
        this.inputSource = inputSource;
        this.key = new SourceKey(this);
    }

    @Override
    public SourceKey key() {
        return key;
    }

    @Override
    public Source<?, ?> getInputSource() {
        return this.inputSource;
    }

}
