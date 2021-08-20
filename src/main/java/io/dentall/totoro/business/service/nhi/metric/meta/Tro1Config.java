package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.source.Collector;

import static io.dentall.totoro.business.service.nhi.metric.meta.Exclude.Tro1;

public class Tro1Config extends MetaConfig {

    public Tro1Config(Collector collector) {
        super(collector);
        this.setUse00121CPoint(true);
        this.setExcludeHideoutPoint(true);
        this.setUseOriginPoint(true);
        this.setExcludeHolidayPoint(true);
        this.setExclude(Tro1);
    }

}
