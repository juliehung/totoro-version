package io.dentall.totoro.business.service.nhi.metric.meta;

public class Meta {

    private final MetaType name;

    private final Long value;

    public Meta(MetaType name, Long value) {
        this.name = name;
        this.value = value;
    }

    public Long value() {
        return this.value;
    }

}
