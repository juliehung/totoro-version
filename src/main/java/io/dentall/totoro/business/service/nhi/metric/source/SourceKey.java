package io.dentall.totoro.business.service.nhi.metric.source;

import java.util.Objects;

public class SourceKey implements Key {

    private final String keyName;

    private final Source<?, ?> source;

    public SourceKey(Source<?, ?> source) {
        this.source = source;
        this.keyName = source.getClass().getName();
    }

    public Source<?, ?> getSource() {
        return source;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SourceKey sourceKey = (SourceKey) o;
        return Objects.equals(keyName, sourceKey.keyName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(keyName);
    }
}
