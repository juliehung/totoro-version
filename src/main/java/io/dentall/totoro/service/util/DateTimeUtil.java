package io.dentall.totoro.service.util;

import io.dentall.totoro.config.TimeConfig;

import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.function.Supplier;

public final class DateTimeUtil {

    public static Supplier<Instant> localTimeMin = () -> OffsetDateTime.now(TimeConfig.ZONE_OFF_SET).toZonedDateTime().with(LocalTime.MIN).toInstant();
    public static Supplier<Instant> localTimeMax = () -> OffsetDateTime.now(TimeConfig.ZONE_OFF_SET).toZonedDateTime().with(LocalTime.MAX).toInstant();

    public static Supplier<LocalDate> localMonthFirstDay = () -> OffsetDateTime.now(TimeConfig.ZONE_OFF_SET).with(TemporalAdjusters.firstDayOfMonth()).toLocalDate();
    public static Supplier<LocalDate> localMonthLastDay = () -> OffsetDateTime.now(TimeConfig.ZONE_OFF_SET).with(TemporalAdjusters.lastDayOfMonth()).toLocalDate();

    public static LocalDate transformROCDateToLocalDate(String rocDate) {
        int year = Integer.parseInt(rocDate.substring(0, 3)) + 1911;
        int month = Integer.parseInt(rocDate.substring(3, 5));
        int day = Integer.parseInt(rocDate.substring(5, 7));

        return LocalDate.of(year, month, day);
    }

    public static Integer getAge(LocalDate birthday, LocalDate now)
    {
        int yearGap = now.getYear() - birthday.getYear();
        LocalDate birthDateThisYear = birthday.plusYears(yearGap);
        return now.isAfter(birthDateThisYear) || now.isEqual(birthDateThisYear)
            ? yearGap
            : yearGap - 1;
    }

    public static String formatDays(Long day) {
        String format;
        String stringDay = day.toString();
        switch (stringDay) {
            case "1095":
                format = "三年";
                break;
            case "730":
                format = "兩年";
                break;
            case "365":
                format = "一年";
                break;
            case "545":
                format = "一年半";
                break;
            case "180":
                format = "半年";
                break;
            case "60":
                format = "六十天";
                break;
            case "90":
                format = "九十天";
                break;
            case "30":
                format = "三十天";
                break;
            case "7":
                format = "七天";
                break;
            case "360":
                format = "三百六十天";
                break;
            default:
                format = stringDay + " 天";
                break;
        }

        return format;
    };


}
