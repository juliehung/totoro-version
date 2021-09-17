package io.dentall.totoro.business.service.nhi.metric.source;

import io.dentall.totoro.business.service.nhi.metric.meta.Meta;
import io.dentall.totoro.domain.Holiday;
import io.dentall.totoro.service.util.DateTimeUtil;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static io.dentall.totoro.business.service.nhi.metric.source.MetricSubjectType.clinic;
import static io.dentall.totoro.service.util.DateTimeUtil.convertLocalDateToBeginOfDayInstant;
import static io.dentall.totoro.service.util.DateTimeUtil.getCurrentQuarterMonthsRangeInstant;
import static java.util.Optional.ofNullable;


public class MetricConfig {

    private final Map<Key, List<?>> cached = new HashMap<>();

    private final Map<Key, Meta<?>> metaMap = new HashMap<>();

    private final Map<LocalDate, Optional<Holiday>> holidayMap = new HashMap<>(500);

    private final Source<?, ?> initialSource = new InitialSource();

    private final LocalDate baseDate;

    private final DateTimeUtil.BeginEnd quarterRange;

    private final MetricSubject metricSubject;

    private final MetricSubjectType subjectType;

    public MetricConfig(MetricSubject metricSubject, LocalDate baseDate, List<?> source) {
        if (metricSubject == null) {
            throw new IllegalArgumentException("metricSubject can not be null");
        }
        if (baseDate == null) {
            throw new IllegalArgumentException("baseDate can not be null");
        }
        if (source == null) {
            throw new IllegalArgumentException("source can not be null");
        }
        this.metricSubject = metricSubject;
        this.baseDate = baseDate;
        this.quarterRange = getCurrentQuarterMonthsRangeInstant(convertLocalDateToBeginOfDayInstant(baseDate));
        this.cached.put(this.initialSource.key(), source);
        this.subjectType = metricSubject.getSubjectType();
        apply(this.metricSubject.getSource(this));
    }

    public MetricConfig apply(Source<?, ?> source) {
        if (!isSourceExist(source.key())) {
            if (source.getInputSource() != null && !isSourceExist(source.getInputSource().key())) {
                apply(source.getInputSource());
            }

            SourceKey inputSourceKey = ofNullable(source.getInputSource()).map(Source::key).orElse(getSubjectSource().key());
            List<?> filtered = source.filter(retrieveSource(inputSourceKey));
            cacheSource(source.key(), filtered);
        }
        return this;
    }

    public Source<?, ?> getInitialSource() {
        return initialSource;
    }

    public MetricSubject getMetricSubject() {
        return metricSubject;
    }

    public Source<?, ?> getSubjectSource() {
        return this.metricSubject.getSource(this);
    }

    public MetricSubjectType getSubjectType() {
        return subjectType;
    }

    public LocalDate getBaseDate() {
        return baseDate;
    }

    public DateTimeUtil.BeginEnd getQuarterRange() {
        return quarterRange;
    }

    public <T> List<T> retrieveSource(SourceKey key) {
        if (!isSourceExist(key)) {
            apply(key.getSource());
        }
        return (List<T>) this.cached.get(key);
    }

    public void cacheSource(SourceKey key, List<?> value) {
        this.cached.put(key, value);
    }

    public boolean isSourceExist(SourceKey key) {
        return this.cached.containsKey(key);
    }

    public <T> Meta<T> retrieveMeta(Key key) {
        return (Meta<T>) this.metaMap.get(key);
    }

    public void storeMeta(Key key, Meta<?> meta) {
        this.metaMap.put(key, meta);
    }

    public boolean isMetaExist(Key key) {
        return this.metaMap.containsKey(key);
    }

    public MetricConfig applyHolidayMap(Map<LocalDate, Optional<Holiday>> holidayMap) {
        this.holidayMap.putAll(holidayMap);
        return this;
    }

    public Map<LocalDate, Optional<Holiday>> getHolidayMap() {
        return this.holidayMap;
    }

}
