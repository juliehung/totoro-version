package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.domain.Holiday;

import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;
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

    // 國定假日排除點數上限 20,000 點 (超過部分要算入點數)
    private boolean exclude20000Point1ByDay = false;

    private final Map<LocalDate, Optional<Holiday>> holidayMap;


    public MetaConfig(MetricConfig metricConfig) {
        this.holidayMap = metricConfig.getHolidayMap();
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

    public boolean isExclude20000Point1ByDay() {
        return exclude20000Point1ByDay;
    }

    public void setExclude20000Point1ByDay(boolean exclude20000Point1ByDay) {
        this.exclude20000Point1ByDay = exclude20000Point1ByDay;
    }

    public boolean isExcludeHolidayPoint() {
        return excludeHolidayPoint;
    }

    public void setExcludeHolidayPoint(boolean excludeHolidayPoint) {
        this.excludeHolidayPoint = excludeHolidayPoint;
    }

    public Map<LocalDate, Optional<Holiday>> getHolidayMap() {
        return holidayMap;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MetaConfig that = (MetaConfig) o;
        return use00121CPoint == that.use00121CPoint &&
            excludeHideoutPoint == that.excludeHideoutPoint &&
            useOriginPoint == that.useOriginPoint &&
            excludeHolidayPoint == that.excludeHolidayPoint &&
            exclude20000Point1ByDay == that.exclude20000Point1ByDay &&
            Objects.equals(holidayMap, that.holidayMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(use00121CPoint, excludeHideoutPoint, useOriginPoint, excludeHolidayPoint, exclude20000Point1ByDay, holidayMap);
    }
}
