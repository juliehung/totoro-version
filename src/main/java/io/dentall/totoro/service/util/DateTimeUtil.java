package io.dentall.totoro.service.util;

import io.dentall.totoro.config.TimeConfig;

import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.function.Function;
import java.util.function.Supplier;

public final class DateTimeUtil {

    public static Supplier<Instant> localTimeMin = () -> OffsetDateTime.now(TimeConfig.ZONE_OFF_SET).toZonedDateTime().with(LocalTime.MIN).toInstant();
    public static Supplier<Instant> localTimeMax = () -> OffsetDateTime.now(TimeConfig.ZONE_OFF_SET).toZonedDateTime().with(LocalTime.MAX).toInstant();

    public static Function<Integer, LocalDate> getGreaterThanEqualDate = days ->
        OffsetDateTime.now(TimeConfig.ZONE_OFF_SET).toLocalDate().minusDays(days);

    public static Supplier<LocalDate> localMonthFirstDay = () -> OffsetDateTime.now(TimeConfig.ZONE_OFF_SET).with(TemporalAdjusters.firstDayOfMonth()).toLocalDate();
    public static Supplier<LocalDate> localMonthLastDay = () -> OffsetDateTime.now(TimeConfig.ZONE_OFF_SET).with(TemporalAdjusters.lastDayOfMonth()).toLocalDate();

    public static Supplier<LocalDate> localYearFirstDay = () -> OffsetDateTime.now(TimeConfig.ZONE_OFF_SET).with(TemporalAdjusters.firstDayOfYear()).toLocalDate();
    public static Supplier<LocalDate> localYearLastDay = () -> OffsetDateTime.now(TimeConfig.ZONE_OFF_SET).with(TemporalAdjusters.lastDayOfYear()).toLocalDate();
}
