package io.dentall.totoro.business.service.nhi.metric.vm;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Section8 {

    @JsonProperty("Lp9")
    private NameValue l9;

    @JsonProperty("Lp10")
    private NameValue l10;

    public NameValue getL9() {
        return l9;
    }

    public void setL9(NameValue l9) {
        this.l9 = l9;
    }

    public NameValue getL10() {
        return l10;
    }

    public void setL10(NameValue l10) {
        this.l10 = l10;
    }
}
