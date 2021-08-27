package io.dentall.totoro.test;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.springframework.util.StringUtils.countOccurrencesOf;

public class TestUtils {

    private static Pattern numPattern = Pattern.compile("(\\d+)");

    public static boolean valueOfPassOrNot(String str) {
        return "Pass".equals(str);
    }

    public static boolean valueOfShowOrNot(String str) {
        return "顯示".equals(str);
    }

    public static int parseDays(String yearStr, String monthStr, String dateStr) {
        return parseDays(parseDate(yearStr, monthStr, dateStr));
    }

    public static int parseDays(LocalDate date) {
        return (int) ChronoUnit.DAYS.between(date, LocalDate.now());
    }

    public static LocalDate parseDate(String yearStr, String monthStr, String dateStr) {
        return parseDate(LocalDate.now(), yearStr, monthStr, dateStr);
    }

    public static LocalDate parseDate(LocalDate baseDate, String yearStr, String monthStr, String dateStr) {
        yearStr = Optional.ofNullable(yearStr).orElse("");
        monthStr = Optional.ofNullable(monthStr).orElse("");
        dateStr = Optional.ofNullable(dateStr).orElse("");

        int yearGap = TestUtils.parseYearGap(yearStr);
        int monthGap = TestUtils.parseMonthGap(monthStr);
        int dayGap = TestUtils.parseDayGap(dateStr);
        LocalDate date = Optional.ofNullable(baseDate).orElse(LocalDate.now());

        if (yearGap <= 0) {
            date = date.plusYears(yearGap);
        } else {
            date = date.withYear(yearGap);
        }

        if (monthGap <= 0) {
            date = date.plusMonths(monthGap);
        } else {
            date = date.withMonth(monthGap);
        }

        if (dayGap <= 0) {
            date = date.plusDays(dayGap);
        } else {
            date = date.withDayOfMonth(dayGap);
        }

        if (monthStr.contains("月初")) {
            date = date.withDayOfMonth(1);
        } else if (monthStr.contains("月中")) {
            date = date.withDayOfMonth(16);
        } else if (monthStr.contains("月底")) {
            date = date.withDayOfMonth(date.getMonth().length(date.isLeapYear()));
        } else if (yearStr.contains("年初")) {
            date = date.withMonth(1).withDayOfMonth(1);
        } else if (yearStr.contains("年中")) {
            date = date.withMonth(7).withDayOfMonth(1);
        } else if (yearStr.contains("年底")) {
            date = date.withMonth(12).withDayOfMonth(31);
        }

        return date;
    }

    public static int parseYearGap(String yearStr) {
        int yearGap = 0;

        if (yearStr != null) {
            Matcher matcher = numPattern.matcher(yearStr);
            if (matcher.find()) {
                yearGap = Integer.parseInt(matcher.group(1));
            }

            if (yearStr.endsWith("前")) {
                yearGap = -yearGap;
            }
        }

        if (yearStr.startsWith("當") || yearStr.startsWith("今")) {
            yearGap = 0;
        } else if (yearStr.startsWith("去")) {
            yearGap = -1;
        } else if (yearStr.startsWith("前")) {
            yearGap = -2;
        }

        return yearGap;
    }

    public static int parseMonthGap(String monthStr) {
        int monthGap = 0;

        if (monthStr.indexOf("上") > -1) {
            monthGap = -countOccurrencesOf(monthStr, "上");
        } else if (monthStr.indexOf("當月") > -1) {
            monthGap = 0;
        } else {
            Matcher matcher = numPattern.matcher(monthStr);
            if (matcher.find()) {
                monthGap = Integer.parseInt(matcher.group(1));
            }

            if (monthStr.indexOf("前") > -1) {
                monthGap = -monthGap;
            }
        }

        return monthGap;
    }

    public static int parseDayGap(String dateStr) {
        int dayGap = 0;

        if (dateStr != null) {
            Matcher matcher = numPattern.matcher(dateStr);
            if (matcher.find()) {
                dayGap = Integer.parseInt(matcher.group(1));
            }
        }

        if (dateStr.contains("當") || dateStr.contains("今")) {
            dayGap = 0;
        } else if (dateStr.contains("昨")) {
            dayGap = -1;
        } else if (dateStr.contains("前")) {
            dayGap = -2;
        }

        return dayGap;
    }
}
