package io.dentall.totoro.business.service.nhi.metric.meta;

public class Meta<T> {

    private final String name;

    private final T value;

    public Meta(String name, T value) {
        this.name = name;
        this.value = value;
    }

    public T value() {
        return this.value;
    }

}
