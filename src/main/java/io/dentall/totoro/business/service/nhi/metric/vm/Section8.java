package io.dentall.totoro.business.service.nhi.metric.vm;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Section8 {

    private NameValue L9;

    private NameValue L10;

    @JsonProperty("Lp9")
    public NameValue getL9() {
        return L9;
    }

    public void setL9(NameValue l9) {
        L9 = l9;
    }

    @JsonProperty("Lp10")
    public NameValue getL10() {
        return L10;
    }

    public void setL10(NameValue l10) {
        L10 = l10;
    }
}
