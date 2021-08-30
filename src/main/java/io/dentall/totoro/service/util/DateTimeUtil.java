package io.dentall.totoro.service.util;

import io.dentall.totoro.config.TimeConfig;

import javax.validation.constraints.NotNull;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public final class DateTimeUtil {

    public static class BeginEnd {
        private Instant begin;
        private Instant end;

        public Instant getBegin() {
            return begin;
        }

        public void setBegin(Instant begin) {
            this.begin = begin;
        }

        public Instant getEnd() {
            return end;
        }

        public void setEnd(Instant end) {
            this.end = end;
        }
    }

    public static enum QuarterMonth {
        SEASON_1(0, 1, 2, 3),
        SEASON_2(1, 4, 5,6),
        SEASON_3(2, 7, 8, 9),
        SEASON_4(3, 10, 11, 12),
        ;

        QuarterMonth(Integer quotientValue, Integer firstMonth, Integer middleMonth, Integer lastMonth) {
            this.quotientValue = quotientValue;
            this.firstMonth = firstMonth;
            this.middleMonth = middleMonth;
            this.lastMonth = lastMonth;
        }

        private Integer quotientValue;

        private Integer firstMonth;

        private Integer middleMonth;

        private Integer lastMonth;

        public Integer getQuotientValue() {
            return quotientValue;
        }

        public Integer getFirstMonth() {
            return firstMonth;
        }

        public Integer getMiddleMonth() {
            return middleMonth;
        }

        public Integer getLastMonth() {
            return lastMonth;
        }
    };

    public static final Period NHI_0_DAY = Period.ofDays(0);

    public static final Period NHI_3_DAY = Period.ofDays(3);

    public static final Period NHI_7_DAY = Period.ofDays(7);

    public static final Period NHI_30_DAY = Period.ofDays(30);

    public static final Period NHI_60_DAY = Period.ofDays(60);

    public static final Period NHI_90_DAY = Period.ofDays(90);

    public static final Period NHI_152_DAY = Period.ofDays(152);

    public static final Period NHI_180_DAY = Period.ofDays(180);

    public static final Period NHI_360_DAY = Period.ofDays(360);

    public static final Period NHI_365_DAY = Period.ofDays(365);

    public static final Period NHI_545_DAY = Period.ofDays(545);

    public static final Period NHI_730_DAY = Period.ofDays(730);

    public static final Period NHI_1095_DAY = Period.ofDays(1095);

    @Deprecated
    public static final Period NHI_1_WEEK = Period.ofDays(7);

    @Deprecated
    public static final Period NHI_1_MONTH = Period.ofDays(30);

    @Deprecated
    public static final Period NHI_2_MONTH = Period.ofDays(60);

    @Deprecated
    public static final Period NHI_3_MONTH = Period.ofDays(90);

    @Deprecated
    public static final Period NHI_6_MONTH = Period.ofDays(180);

    @Deprecated
    public static final Period NHI_12_MONTH = Period.ofDays(365);

    @Deprecated
    public static final Period NHI_18_MONTH = Period.ofDays(545);

    @Deprecated
    public static final Period NHI_24_MONTH = Period.ofDays(730);

    @Deprecated
    public static final Period NHI_36_MONTH = Period.ofDays(1095);

    public static final long NUMBERS_OF_MONTH_1 = 1L;

    public static final long NUMBERS_OF_MONTH_3 = 3L;

    public static final long NUMBERS_OF_MONTH_6 = 6L;

    public static final long NUMBERS_OF_MONTH_12 = 12L;

    public static final long NUMBERS_OF_YEAR_2 = 24L;

    public static final long NUMBERS_OF_YEAR_1 = 12L;

    public static final DateTimeFormatter localDateDisplayFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    public static Supplier<Instant> localTimeMin = () -> OffsetDateTime.now(TimeConfig.ZONE_OFF_SET).toZonedDateTime().with(LocalTime.MIN).toInstant();
    public static Supplier<Instant> localTimeMax = () -> OffsetDateTime.now(TimeConfig.ZONE_OFF_SET).toZonedDateTime().with(LocalTime.MAX).toInstant();

    public static Supplier<LocalDate> localMonthFirstDay = () -> OffsetDateTime.now(TimeConfig.ZONE_OFF_SET).with(TemporalAdjusters.firstDayOfMonth()).toLocalDate();
    public static Supplier<LocalDate> localMonthLastDay = () -> OffsetDateTime.now(TimeConfig.ZONE_OFF_SET).with(TemporalAdjusters.lastDayOfMonth()).toLocalDate();

    public static String transformA71ToDisplayWithTime(String a71) {
        String result = "";

        if (
            a71 != null &&
            a71.length() > 11
        ) {
            result = result.concat(a71.substring(0, 3))
                .concat("/")
                .concat(a71.substring(3, 5))
                .concat("/")
                .concat(a71.substring(5, 7))
                .concat(" ")
                .concat(a71.substring(7, 9))
                .concat(":")
                .concat(a71.substring(9, 11));
        }

        return result;
    }

    public static String transformA71ToDisplay(String a71) {
        return String.format("%s/%s/%s", a71.substring(0, 3), a71.substring(3, 5), a71.substring(5, 7));
    }

    public static String transformLocalDateToRocDateForDisplay(LocalDate dateTime) {
        return dateTime
            .minus(1911, ChronoUnit.YEARS)
            .format(
                DateTimeFormatter.ofPattern("yyyy/MM/dd")
            )
            .substring(1);
    }

    public static String transformInstantToRocDateTimeForDisplay(Instant dateTime) {
        return dateTime
            .atZone(ZoneId.of("Asia/Taipei"))
            .minus(1911, ChronoUnit.YEARS)
            .format(
                DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")
            )
            .substring(1);
    }

    public static String transformLocalDateToRocDateForDisplay(Instant dateTime) {
        return dateTime
            .atOffset(TimeConfig.ZONE_OFF_SET)
            .minus(1911, ChronoUnit.YEARS)
            .format(
                DateTimeFormatter.ofPattern("yyyy/MM/dd")
            )
            .substring(1);
    }

    public static String transformLocalDateToRocDate(Instant dateTime) {
        return dateTime
            .atOffset(TimeConfig.ZONE_OFF_SET)
            .minus(1911, ChronoUnit.YEARS)
            .format(
                DateTimeFormatter.ofPattern("yyyyMMddHHmm"))
            .substring(1);
    }

    public static LocalDate transformROCDateToLocalDate(String rocDate) {
        int year = Integer.parseInt(rocDate.substring(0, 3)) + 1911;
        int month = Integer.parseInt(rocDate.substring(3, 5));
        int day = Integer.parseInt(rocDate.substring(5, 7));

        return LocalDate.of(year, month, day);
    }

    public static Integer getAge(LocalDate birthday, LocalDate now) {
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
    }

    public static Period startDayOfMonthDiff(LocalDate current) {
        return Period.between(LocalDate.of(current.getYear(), current.getMonth(), 1), current);
    }

    public static QuarterMonth getCurrentQuarter(Instant currentTime) {
        QuarterMonth result = null;

        int quotientValue = (currentTime.atOffset(TimeConfig.ZONE_OFF_SET).getMonth().getValue() - 1) / 3;

        switch (quotientValue) {
            case 0:
                result = QuarterMonth.SEASON_1;
                break;
            case 1:
                result = QuarterMonth.SEASON_2;
                break;
            case 2:
                result = QuarterMonth.SEASON_3;
                break;
            case 3:
                result = QuarterMonth.SEASON_4;
                break;
            default:
                result = QuarterMonth.SEASON_1;
                break;
        }

        return result;
    }

    public static DateTimeUtil.BeginEnd getCurrentQuarterMonthsRangeInstant(
        @NotNull Instant currentTime
    ) {
        DateTimeUtil.BeginEnd result = new DateTimeUtil.BeginEnd();
        QuarterMonth currentQuarter = DateTimeUtil.getCurrentQuarter(currentTime);
        YearMonth lym = YearMonth.of(
            currentTime.atOffset(TimeConfig.ZONE_OFF_SET).getYear(),
            currentQuarter.lastMonth
        );

        result.setBegin(
            Instant.now()
                .atOffset(TimeConfig.ZONE_OFF_SET)
                .with(LocalTime.MIN)
                .withYear(lym.getYear())
                .withMonth(currentQuarter.firstMonth)
                .withDayOfMonth(1)
                .toInstant()
        );
        result.setEnd(
            Instant.now()
                .atOffset(TimeConfig.ZONE_OFF_SET)
                .with(LocalTime.MAX)
                .withYear(lym.getYear())
                .withMonth(currentQuarter.lastMonth)
                .withDayOfMonth(lym.atEndOfMonth().getDayOfMonth())
                .toInstant()
        );

        return result;
    }

    public static Instant convertLocalDateToBeginOfDayInstant(LocalDate date) {
        return Instant.now()
            .atOffset(TimeConfig.ZONE_OFF_SET)
            .with(LocalDateTime.MIN)
            .withYear(date.getYear())
            .withMonth(date.getMonthValue())
            .withDayOfMonth(date.getDayOfMonth())
            .toInstant();
    }

    public static Instant convertLocalDateToEndOfDayInstant(LocalDate date) {
        return Instant.now()
            .atOffset(TimeConfig.ZONE_OFF_SET)
            .with(LocalDateTime.MAX)
            .withYear(date.getYear())
            .withMonth(date.getMonthValue())
            .withDayOfMonth(date.getDayOfMonth())
            .toInstant();
    }

    public static Instant pastInstant(int pastDays) {
        return Instant.now().minus(pastDays, ChronoUnit.DAYS);
    }
}
