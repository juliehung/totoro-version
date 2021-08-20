package io.dentall.totoro.business.service.nhi.metric.source;

public abstract class AbstractSource<S, R> implements Source<S, R> {

    private final InputSource<S> inputSource;

    public AbstractSource(InputSource<S> inputSource) {
        this.inputSource = inputSource;
    }

    @Override
    public String name() {
        return this.getClass().getSimpleName();
    }

    @Override
    public InputSource<S> getInputSource() {
        return this.inputSource;
    }

    @Override
    public InputSource<R> asInputSource() {
        return (InputSource<R>) this;
    }

}
