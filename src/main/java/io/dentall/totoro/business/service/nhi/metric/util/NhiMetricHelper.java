package io.dentall.totoro.business.service.nhi.metric.util;

import io.dentall.totoro.business.service.nhi.metric.dto.MetricDisposal;
import io.dentall.totoro.business.service.nhi.metric.dto.MetricTooth;
import io.dentall.totoro.business.service.nhi.metric.meta.Exclude;
import io.dentall.totoro.business.service.nhi.metric.meta.MetaConfig;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConstants;
import io.dentall.totoro.domain.Holiday;
import io.dentall.totoro.service.HolidayService;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.Period;
import java.time.Year;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.lang.Long.parseLong;
import static java.util.Comparator.comparing;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.*;
import static org.apache.commons.lang3.StringUtils.*;

public class NhiMetricHelper {

    private NhiMetricHelper() {
    }

    public static Long applyNewExamPoint(MetricDisposal vm, MetaConfig config, Map<LocalDate, Optional<Holiday>> holidayMap) {
        long point = ofNullable(vm.getExamPoint()).filter(StringUtils::isNotBlank).map(Long::parseLong).orElse(0L);

        if (point == 0L) {
            return point;
        }

        if (config.isExcludeHideoutPoint() && MetricConstants.CodesByHideout.contains(vm.getExamCode())) {
            // 山地離島的健保代碼要扣除多出來的點數
            // 30點差額是00125C(260 point)、00126C(260 pont)扣除00121C(230 point)的差額與00309C(385 point)、00310C(385 pont)扣除00305C(355 point)的差額
            point -= 30L;
        }

        if (config.isUse00121CPoint()) {
            point = 230L;
        }

        if (!config.isExclude20000Point1ByDay() && config.isExcludeHolidayPoint() && isHoliday(vm.getDisposalDate(), holidayMap)) {
            // 國定假日無論有無放假，都要扣除健保代碼點數
            point = 0L;
        }

        return point;
    }

    public static Long calculatePurge(Stream<MetricDisposal> stream, MetaConfig config, Map<LocalDate, Optional<Holiday>> holidayMap) {
        return stream
            .filter(vm -> isNotBlank(vm.getExamPoint()))
            .map(vm -> purgeExamPoint(vm.getExamCode(), parseLong(vm.getExamPoint())))
            .reduce(Long::sum)
            .orElse(0L);
    }

    public static Long calculateExam(Stream<MetricDisposal> stream, MetaConfig config, Map<LocalDate, Optional<Holiday>> holidayMap) {
        return stream
            .map(vm -> applyNewExamPoint(vm, config, holidayMap))
            .reduce(Long::sum)
            .orElse(0L);
    }

    private static Stream<MetricDisposal> checkExamValue(List<MetricDisposal> source, List<String> codes) {
        return source.stream()
            .filter(vm -> isNotBlank(vm.getExamPoint()))
            .filter(vm -> codes.contains(vm.getExamCode()));
    }

    public static Long calculatePurge(List<MetricDisposal> source, List<String> codes, MetaConfig config, Map<LocalDate, Optional<Holiday>> holidayMap) {
        Stream<MetricDisposal> stream = checkExamValue(source, codes);
        return calculatePurge(stream, config, holidayMap);
    }

    public static Long calculateExamRegular(List<MetricDisposal> source, List<String> codes, MetaConfig config, Map<LocalDate, Optional<Holiday>> holidayMap) {
        Stream<MetricDisposal> stream = checkExamValue(source, codes);
        return calculateExam(stream, config, holidayMap);
    }

    public static Long calculateExamDifference(List<MetricDisposal> source, List<String> codes) {
        return checkExamValue(source, codes)
            .map(vm -> parseLong(vm.getExamPoint()) - 230L)
            .reduce(Long::sum)
            .orElse(0L);
    }

    public static Map<Long, Long> calculateExamByClassifier(
        List<MetricDisposal> source,
        List<String> codes,
        Function<MetricDisposal, Long> classifier,
        MetaConfig config,
        Map<LocalDate, Optional<Holiday>> holidayMap) {
        return source.stream()
            .filter(vm -> isNotBlank(vm.getExamPoint()))
            .filter(vm -> codes.contains(vm.getExamCode()))
            .collect(groupingBy(classifier))
            .entrySet().stream()
            .reduce(new HashMap<>(),
                (map, entry) -> {
                    long keyId = entry.getKey();

                    map.compute(keyId, (key, point) -> {
                        List<MetricDisposal> subMap = entry.getValue();
                        Stream<MetricDisposal> stream = subMap.stream();
                        Long examPoint = calculateExam(stream, config, holidayMap);
                        return ofNullable(point).orElse(0L) + examPoint;
                    });

                    return map;
                },
                (accMap, map) -> {
                    accMap.putAll(map);
                    return accMap;
                });
    }

    public static long purgeExamPoint(String code, long examPoint) {
        if ("00305C".equals(code) || "00306C".equals(code)) {
            return 230L;
        } else if ("00307C".equals(code) || "00308C".equals(code)) {
            return 120L;
        } else if ("00309C".equals(code) || "00310C".equals(code)) {
            return 260L;
        } else if ("00311C".equals(code)) {
            return 520L;
        } else if ("00312C".equals(code)) {
            return 420L;
        } else if ("00313C".equals(code)) {
            return 320L;
        } else if ("00314C".equals(code)) {
            return 320L;
        } else if ("00315C".equals(code)) {
            return 600L;
        } else if ("00316C".equals(code)) {
            return 600L;
        } else if ("00317C".equals(code)) {
            return 600L;
        }
        return examPoint;
    }

    public static Long applyNewTreatmentPoint(MetricTooth vm, MetaConfig config, Map<LocalDate, Optional<Holiday>> holidayMap) {
        Long point = vm.getTreatmentProcedureTotal();

        if (config.isUseOriginPoint()) {
            point = vm.getNhiOriginPoint();
        }

        if (!config.isExclude20000Point1ByDay() && config.isExcludeHolidayPoint() && isHoliday(vm.getDisposalDate(), holidayMap)) {
            // 國定假日無論有無放假，都要扣除健保代碼點數
            point = 0L;
        }

        return ofNullable(point).orElse(0L);
    }

    public static boolean isDayOffHoliday(LocalDate date, Map<LocalDate, Optional<Holiday>> holidayMap) {
        return ofNullable(holidayMap.get(date)).filter(Optional::isPresent).map(Optional::get).map(h -> h.getDayOff().equals("Y")).orElse(false);
    }

    public static boolean isHoliday(LocalDate date, Map<LocalDate, Optional<Holiday>> holidayMap) {
        return ofNullable(holidayMap).map(map -> map.get(date)).map(Optional::isPresent).orElse(false);
    }

    public static Predicate<MetricTooth> applyExcludeByVM(Exclude exclude) {
        return vm -> ofNullable(exclude).map(exclude1 -> exclude1.test(vm)).orElse(true);
    }

    public static Map<LocalDate, Optional<Holiday>> getHolidayMap(HolidayService holidayService, int... years) {
        return Arrays.stream(years)
            .mapToObj(Year::of)
            .map(holidayService::getHolidays)
            .flatMap(Collection::stream)
            .collect(toList())
            .stream()
            .collect(groupingBy(Holiday::getDate, maxBy(comparing(Holiday::getDate))));
    }

    public static boolean isNumericCardNumber(String cardNumber) {
        return isNumeric(cardNumber);
    }

    public static boolean isErrorCardNumber(String cardNumber) {
        return !isNumericCardNumber(cardNumber) && isPreventionCardNumber(cardNumber);
    }

    public static boolean isPreventionCardNumber(String cardNumber) {
        try {
            return cardNumber.startsWith("IC");
        } catch (Exception e) {
            return false;
        }
    }

    public static BiFunction<Long, MetricTooth, Long> calculatePt() {
        Set<String> existPatientCardNumber = new HashSet<>();
        Set<Long> existDisposal = new HashSet<>();
        return (count, vm) -> calculatePtCount(vm.getDisposalId(), vm.getDisposalDate(), vm.getPatientId(), vm.getCardNumber(), count, existPatientCardNumber, existDisposal);
    }

    private static long calculatePtCount(
        Long disposalId,
        LocalDate date,
        long patientId, String cardNumber,
        long count,
        Set<String> existPatientCardNumber,
        Set<Long> existDisposal
    ) {
        if (isBlank(cardNumber) || date == null || disposalId == null) {
            return count;
        }

        if (existDisposal.contains(disposalId)) {
            return count;
        } else {
            existDisposal.add(disposalId);
        }

        boolean isNumeric = isNumericCardNumber(cardNumber);

        if (isNumeric) {
            String key = date.getMonth().getValue() + "_" + patientId + "_" + cardNumber;
            if (existPatientCardNumber.contains(key)) {
                return count;
            } else {
                existPatientCardNumber.add(key);
            }
        }

        return count + 1L;
    }

    public static Predicate<MetricTooth> applyLegalAge(int bottom, int upper) {
        return vm -> {
            int ages = Period.between(vm.getPatientBirth(), vm.getDisposalDate()).getYears();
            return ages >= bottom && ages <= upper;
        };
    }

}
