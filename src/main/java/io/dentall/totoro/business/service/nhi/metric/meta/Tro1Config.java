package io.dentall.totoro.business.service.nhi.metric.meta;

import static io.dentall.totoro.business.service.nhi.metric.meta.Exclude.Tro1;

public class Tro1Config extends MetaConfig {

    public Tro1Config() {
        this.setUse00121CPoint(true);
        this.setUseOriginPoint(true);
        this.setExcludeHolidayPoint(true);
        this.setExclude(Tro1);
    }

}
