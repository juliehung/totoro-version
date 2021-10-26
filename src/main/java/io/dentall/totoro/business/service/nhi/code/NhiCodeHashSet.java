package io.dentall.totoro.business.service.nhi.code;

import java.util.HashSet;

public class NhiCodeHashSet extends HashSet<NhiCode> {

    public boolean contains(String o) {
        return this.contains((Object) o);
    }

    @Override
    public boolean contains(Object o) {
        if (o instanceof String) {
            for (NhiCode nhiCode : this) {
                if (o.equals(nhiCode.name())) {
                    return true;
                }
            }
            return false;
        }
        return super.contains(o);
    }
}
