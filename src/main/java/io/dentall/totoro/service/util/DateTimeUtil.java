package io.dentall.totoro.service.util;

import io.dentall.totoro.config.TimeConfig;
import io.dentall.totoro.web.rest.errors.BadRequestAlertException;

import javax.validation.constraints.NotNull;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
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
        SEASON_2(1, 4, 5, 6),
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
    }

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

    public static String transformLocalDateToRocDateTimeForFileName(LocalDate dateTime) {
        return dateTime
            .minus(1911, ChronoUnit.YEARS)
            .format(
                DateTimeFormatter.ofPattern("yyyyMMdd")
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

    public static Instant transformROCDateToInstant(String rocDateTim) {
        if (rocDateTim.length() != 13) {
            return Instant.now();
        }

        int year = Integer.parseInt(rocDateTim.substring(0, 3)) + 1911;
        int month = Integer.parseInt(rocDateTim.substring(3, 5));
        int day = Integer.parseInt(rocDateTim.substring(5, 7));
        int hour = Integer.parseInt(rocDateTim.substring(7, 9));
        int minute = Integer.parseInt(rocDateTim.substring(9, 11));
        int second = Integer.parseInt(rocDateTim.substring(11, 13));

        return Instant.parse(
            "".concat(String.valueOf(year))
                .concat("-")
                .concat(
                    month > 10 ? String.valueOf(month) : "0" + String.valueOf(month)
                )
                .concat("-")
                .concat(
                    day > 10 ? String.valueOf(day) : "0" + String.valueOf(day)
                )
                .concat("T")
                .concat(
                    hour > 10 ? String.valueOf(hour) : "0" + String.valueOf(hour)
                )
                .concat(":")
                .concat(
                    minute > 10 ? String.valueOf(minute) : "0" + String.valueOf(minute)
                )
                .concat(":")
                .concat(
                    second > 10 ? String.valueOf(second) : "0" + String.valueOf(second)
                )
                .concat("Z")
        );
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

    public static BeginEnd convertYearMonth(String yearMonth) {
        BeginEnd be = new BeginEnd();
        Instant begin = null;
        Instant end = null;

        try {
            YearMonth ym = YearMonth.parse(yearMonth);
            begin = ym.atDay(1).atStartOfDay().toInstant(TimeConfig.ZONE_OFF_SET);
            end = ym.atEndOfMonth().atTime(LocalTime.MAX).toInstant(TimeConfig.ZONE_OFF_SET);
        } catch(Exception e) {
            begin = Instant.now();
            end = Instant.now();
        }

        be.setBegin(begin);
        be.setEnd(end);

        return be;
    }

    public static LocalDate beginOfMonth(LocalDate date) {
        return Instant.now()
            .atOffset(TimeConfig.ZONE_OFF_SET)
            .with(LocalTime.MIN)
            .withYear(date.getYear())
            .withMonth(date.getMonthValue())
            .withDayOfMonth(1)
            .toLocalDate();
    }

    public static LocalDate endOfMonth(LocalDate date) {
        return Instant.now()
            .atOffset(TimeConfig.ZONE_OFF_SET)
            .with(LocalTime.MAX)
            .withYear(date.getYear())
            .withMonth(date.getMonthValue())
            .withDayOfMonth(date.lengthOfMonth())
            .toLocalDate();
    }

    public static LocalDate toLocalDate(Instant instant) {
        return instant.atOffset(TimeConfig.ZONE_OFF_SET).toLocalDate();
    }

    public static boolean isSameMonth(LocalDate date) {
        return LocalDate.now().getYear() == date.getYear() && LocalDate.now().getMonth() == date.getMonth();
    }

    public static String transformIntYyyymmToFormatedStringYyyymm(Integer yyyymm) {
        String partialACDateTimeString = String.valueOf(yyyymm);

        if (partialACDateTimeString.length() == 6) {
            yyyymm = yyyymm - 191100;
        } else {
            throw new BadRequestAlertException(
                "year month must be 6 digits",
                "VALIDATION",
                "as title"
            );
        }

        partialACDateTimeString = partialACDateTimeString.substring(0, 4)
            .concat("-")
            .concat(
                partialACDateTimeString.substring(4, 6)
            );

        return partialACDateTimeString;
    }

    public static String transformLocalDateToFormatedStringYyyymm(LocalDate date) {
        return date.format(
            DateTimeFormatter.ofPattern(
                "yyyy-MM"
            )
        );
    }
}
