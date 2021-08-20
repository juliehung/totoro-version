package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.source.Collector;
import io.dentall.totoro.domain.Holiday;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

public class MetaConfig {

    // exam 相關
    private boolean use00121CPoint = false;

    // exam 相關，山地離島診察費差額
    private boolean excludeHideoutPoint = false;

    // treatment 相關
    private boolean useOriginPoint = false;

    // treatment 相關
    private boolean excludeHolidayPoint = false;

    private Exclude exclude;

    private Map<LocalDate, Optional<Holiday>> holidayMap;


    public MetaConfig(Collector collector) {
        this.holidayMap = collector.getHolidayMap();
    }

    public boolean isUse00121CPoint() {
        return use00121CPoint;
    }

    public MetaConfig setUse00121CPoint(boolean use00121CPoint) {
        this.use00121CPoint = use00121CPoint;
        return this;
    }

    public boolean isExcludeHideoutPoint() {
        return excludeHideoutPoint;
    }

    public void setExcludeHideoutPoint(boolean excludeHideoutPoint) {
        this.excludeHideoutPoint = excludeHideoutPoint;
    }

    public boolean isUseOriginPoint() {
        return useOriginPoint;
    }

    public MetaConfig setUseOriginPoint(boolean useOriginPoint) {
        this.useOriginPoint = useOriginPoint;
        return this;
    }

    public boolean isExcludeHolidayPoint() {
        return excludeHolidayPoint;
    }

    public void setExcludeHolidayPoint(boolean excludeHolidayPoint) {
        this.excludeHolidayPoint = excludeHolidayPoint;
    }

    public Exclude getExclude() {
        return exclude;
    }

    public MetaConfig setExclude(Exclude exclude) {
        this.exclude = exclude;
        return this;
    }

    public Map<LocalDate, Optional<Holiday>> getHolidayMap() {
        return holidayMap;
    }

}
