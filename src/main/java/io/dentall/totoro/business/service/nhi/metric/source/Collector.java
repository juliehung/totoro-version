package io.dentall.totoro.business.service.nhi.metric.source;

import io.dentall.totoro.business.service.nhi.metric.meta.Meta;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;
import io.dentall.totoro.domain.Holiday;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public class Collector {

    private final Map<InputSource<?>, List<?>> cached = new HashMap<>();

    private final Map<String, Meta<?>> metaMap = new HashMap<>();

    private final Map<LocalDate, Optional<Holiday>> holidayMap = new HashMap<>(500);

    public Collector(InputSource<?> inputSource, List<NhiMetricRawVM> nhiMetricRawVMList) {
        this.cached.put(inputSource, nhiMetricRawVMList);
    }

    public Collector apply(Source<?, ?> source) {
        if (!isSourceExist(source.asInputSource())) {
            if (!isSourceExist(source.getInputSource()) && Source.class.isAssignableFrom(source.getInputSource().getClass())) {
                apply((Source<?, ?>) source.getInputSource());
            }

            List<?> filtered = source.doFilter(retrieveSource(source.getInputSource()));
            cacheSource(source.asInputSource(), filtered);
        }
        return this;
    }

    public <T> List<T> retrieveSource(InputSource<?> inputSource) {
        return (List<T>) this.cached.get(inputSource);
    }

    public void cacheSource(InputSource<?> inputSource, List<?> value) {
        this.cached.put(inputSource, value);
    }

    public boolean isSourceExist(InputSource<?> inputSource) {
        return this.cached.containsKey(inputSource);
    }

    public <T> Meta<T> retrieveMeta(String key) {
        return (Meta<T>) this.metaMap.get(key);
    }

    public void storeMeta(String key, Meta<?> meta) {
        this.metaMap.put(key, meta);
    }

    public boolean isMetaExist(String key) {
        return this.metaMap.containsKey(key);
    }

    public void applyHolidayMap(Map<LocalDate, Optional<Holiday>> holidayMap) {
        this.holidayMap.putAll(holidayMap);
    }

    public Map<LocalDate, Optional<Holiday>> getHolidayMap() {
        return this.holidayMap;
    }

}
