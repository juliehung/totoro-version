package io.dentall.totoro.business.service.nhi.metric.source;

import io.dentall.totoro.business.service.nhi.metric.meta.Exclude;

public abstract class AbstractSource<S, R> implements Source<S, R> {

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
        if (this.inputSource != null) {
            this.inputSource.setExclude(this.exclude);
        }
    }

    @Override
    public Exclude getExclude() {
        return exclude;
    }

}
