package io.dentall.totoro.business.service.nhi.metric.meta;

public class Meta<T> {

    private final MetaType name;

    private final T value;

    public Meta(MetaType name, T value) {
        this.name = name;
        this.value = value;
    }

    public T value() {
        return this.value;
    }

}
