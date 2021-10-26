package io.dentall.totoro.business.service.nhi.code;

import java.util.HashSet;
import java.util.Set;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableSet;

public abstract class AbstractNhiCode implements NhiCode {

    private final Set<NhiCodeAttribute> attributes = new HashSet<>();

    AbstractNhiCode(NhiCodeAttribute... attributes) {
        this.attributes.addAll(asList(attributes));
    }

    @Override
    public Set<NhiCodeAttribute> getAttribute() {
        return unmodifiableSet(attributes);
    }
}
