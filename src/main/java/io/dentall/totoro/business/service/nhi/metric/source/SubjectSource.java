package io.dentall.totoro.business.service.nhi.metric.source;

public abstract class SubjectSource<S, R> extends AbstractSource<S, R> {

    public SubjectSource(Source<?, ?> inputSource) {
        super(inputSource);
    }
}
