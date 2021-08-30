package io.dentall.totoro.business.service.nhi.metric.util;

import io.dentall.totoro.business.service.nhi.metric.dto.OdDto;
import io.dentall.totoro.business.service.nhi.metric.meta.Exclude;
import io.dentall.totoro.business.service.nhi.metric.meta.MetaConfig;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConstants;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;
import io.dentall.totoro.domain.Holiday;
import io.dentall.totoro.service.HolidayService;

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

    public static Long applyNewExamPoint(NhiMetricRawVM vm, MetaConfig config) {
        if (config.isExcludeHideoutPoint() && MetricConstants.CodesByHideout.contains(vm.getExamCode())) {
            // 山地離島的健保代碼要扣除多出來的點數
            // 30點差額是00125C(260 point)、00126C(260 pont)扣除00121C(230 point)的差額與00309C(385 point)、00310C(385 pont)扣除00305C(355 point)的差額
            return parseLong(vm.getExamPoint()) - 30L;
        }

        if (config.isUse00121CPoint()) {
            return 230L;
        }

        return parseLong(vm.getExamPoint());
    }

    public static Long calculateExam(Stream<Optional<NhiMetricRawVM>> stream, MetaConfig config) {
        return stream.filter(Optional::isPresent)
            .map(Optional::get)
            .map(vm -> applyNewExamPoint(vm, config))
            .reduce(Long::sum)
            .orElse(0L);
    }

    private static Stream<Optional<NhiMetricRawVM>> groupByDisposal(List<NhiMetricRawVM> source, List<String> codes) {
        return source.stream()
            .filter(vm -> isNotBlank(vm.getExamPoint()))
            .filter(vm -> codes.contains(vm.getExamCode()))
            .collect(groupingBy(NhiMetricRawVM::getDisposalId, maxBy(comparing(NhiMetricRawVM::getDisposalId))))
            .values().stream();
    }

    public static Long calculateExamRegular(List<NhiMetricRawVM> source, List<String> codes, MetaConfig config) {
        Stream<Optional<NhiMetricRawVM>> stream = groupByDisposal(source, codes);
        return calculateExam(stream, config);
    }

    public static Long calculateExamDifference(List<NhiMetricRawVM> source, List<String> codes) {
        return groupByDisposal(source, codes)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(vm -> parseLong(vm.getExamPoint()) - 230L)
            .reduce(Long::sum)
            .orElse(0L);
    }

    public static Map<Long, Long> calculateExamByClassifier(List<NhiMetricRawVM> source, List<String> codes, Function<NhiMetricRawVM, Long> classifier, MetaConfig config) {
        return source.stream()
            .filter(vm -> isNotBlank(vm.getExamPoint()))
            .filter(vm -> codes.contains(vm.getExamCode()))
            .collect(groupingBy(classifier, groupingBy(NhiMetricRawVM::getDisposalId, maxBy(comparing(NhiMetricRawVM::getDisposalId)))))
            .entrySet().stream()
            .reduce(new HashMap<>(),
                (map, entry) -> {
                    long keyId = entry.getKey();

                    map.compute(keyId, (key, point) -> {
                        Map<Long, Optional<NhiMetricRawVM>> subMap = entry.getValue();
                        Stream<Optional<NhiMetricRawVM>> stream = subMap.values().stream();
                        Long examPoint = calculateExam(stream, config);
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

    public static Long applyNewTreatmentPoint(NhiMetricRawVM vm, MetaConfig config) {
        if (config.isUseOriginPoint()) {
            return vm.getNhiOriginPoint();
        }

        if (config.isExcludeHolidayPoint()) {
            // 國定假日無論有無放假，都要扣除健保代碼點數
            return isHoliday(vm.getDisposalDate(), config.getHolidayMap()) ? 0L : vm.getTreatmentProcedureTotal();
        }

        return vm.getTreatmentProcedureTotal();
    }

    public static Long applyNewTreatmentPoint(OdDto dto, MetaConfig config) {
        if (config.isUseOriginPoint()) {
            return dto.getNhiOriginPoint();
        }

        if (config.isExcludeHolidayPoint()) {
            // 國定假日無論有無放假，都要扣除健保代碼點數
            return isHoliday(dto.getDisposalDate(), config.getHolidayMap()) ? 0L : dto.getTreatmentProcedureTotal();
        }

        return dto.getTreatmentProcedureTotal();
    }

    public static boolean isDayOffHoliday(LocalDate date, Map<LocalDate, Optional<Holiday>> holidayMap) {
        return ofNullable(holidayMap.get(date)).filter(Optional::isPresent).map(Optional::get).map(h -> h.getDayOff().equals("Y")).orElse(false);
    }

    public static boolean isHoliday(LocalDate date, Map<LocalDate, Optional<Holiday>> holidayMap) {
        return ofNullable(holidayMap.get(date)).map(Optional::isPresent).orElse(false);
    }

    public static Predicate<NhiMetricRawVM> applyExcludeByVM(Exclude exclude) {
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

    public static BiFunction<Long, NhiMetricRawVM, Long> calculatePt() {
        List<String> existPatientCardNumber = new ArrayList<>();
        return (count, vm) -> calculatePtCount(vm.getDisposalDate(), vm.getPatientId(), vm.getCardNumber(), count, existPatientCardNumber);
    }

    public static BiFunction<Long, OdDto, Long> calculateOdPt() {
        List<String> existPatientCardNumber = new ArrayList<>();
        return (count, dto) -> calculatePtCount(dto.getDisposalDate(), dto.getPatientId(), dto.getCardNumber(), count, existPatientCardNumber);
    }

    private static long calculatePtCount(LocalDate date, long patientId, String cardNumber, long count, List<String> existPatientCardNumber) {
        if (isBlank(cardNumber) || date == null) {
            return count;
        }

        String key = date.getMonth().getValue() + "_" + patientId + "_" + cardNumber;
        boolean isNumeric = isNumericCardNumber(cardNumber);

        if (isNumeric) {
            if (existPatientCardNumber.contains(key)) {
                return count;
            } else {
                existPatientCardNumber.add(key);
            }
        }

        return count + 1L;
    }

    public static Predicate<NhiMetricRawVM> applyLegalAge(int bottom, int upper) {
        return vm -> {
            int ages = Period.between(vm.getPatientBirth(), vm.getDisposalDate()).getYears();
            return ages >= bottom && ages <= upper;
        };
    }

}
