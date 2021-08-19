package io.dentall.totoro.business.service.nhi.metric.util;

import io.dentall.totoro.business.service.nhi.metric.dto.OdDto;
import io.dentall.totoro.business.service.nhi.metric.mapper.NhiMetricRawMapper;
import io.dentall.totoro.business.service.nhi.metric.meta.Exclude;
import io.dentall.totoro.business.service.nhi.metric.meta.MetaConfig;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;
import io.dentall.totoro.domain.Holiday;
import io.dentall.totoro.service.HolidayService;

import java.time.LocalDate;
import java.time.Year;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.lang.Long.parseLong;
import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;
import static java.util.Comparator.comparing;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.*;
import static org.apache.commons.lang3.StringUtils.*;

public class NhiMetricHelper {

    public static final List<String> codesByExam1 = unmodifiableList(asList(
        "00121C", "00122C", "00123C", "00124C", "00125C", "00126C", "00128C", "00129C", "00130C", "00133C", "00134C", "00301C", "00302C", "00303C", "00304C"
    ));

    public static final List<String> codesByExam2 = unmodifiableList(asList(
        "01271C", "01272C", "01273C"
    ));

    public static final List<String> codesByExam3 = unmodifiableList(asList(
        "00305C", "00306C", "00307C", "00308C", "00309C", "00310C", "00311C", "00312C", "00313C", "00314C"
    ));

    public static final List<String> codesByExam4 = unmodifiableList(asList(
        "00315C", "00316C", "00317C"
    ));

    // 山地離島診察代碼
    public static final List<String> codesByHideout = unmodifiableList(asList(
        "00125C", "00126C", "00309C", "00310C"
    ));

    private NhiMetricHelper() {
    }

    public static Long applyNewExamPoint(NhiMetricRawVM vm, MetaConfig config) {
        if (config.isExcludeHideoutPoint() && codesByHideout.contains(vm.getExamCode())) {
            return 0L;
        }

        if (config.isUse00121CPoint()) {
            return 30L;
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

    public static Long calculateExamRegular(List<NhiMetricRawVM> source, List<String> codes, MetaConfig config) {
        Stream<Optional<NhiMetricRawVM>> stream = source.stream()
            .filter(vm -> isNotBlank(vm.getExamPoint()))
            .filter(vm -> codes.contains(vm.getExamCode()))
            .collect(groupingBy(NhiMetricRawVM::getDisposalId, maxBy(comparing(NhiMetricRawVM::getDisposalId))))
            .values().stream();
        return calculateExam(stream, config);
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
            return isDayOffHoliday(vm.getDisposalDate(), config.getHolidayMap()) ? 0L : vm.getTreatmentProcedureTotal();
        }

        return vm.getTreatmentProcedureTotal();
    }

    public static boolean isDayOffHoliday(LocalDate date, Map<LocalDate, Optional<Holiday>> holidayMap) {
        return ofNullable(holidayMap.get(date)).filter(Optional::isPresent).map(Optional::get).map(h -> h.getDayOff().equals("Y")).orElse(false);
    }

    public static Predicate<NhiMetricRawVM> applyExcludeByVM(Exclude exclude) {
        return vm -> ofNullable(exclude).map(exclude1 -> exclude1.test(NhiMetricRawMapper.INSTANCE.mapToExcludeDto(vm))).orElse(true);
    }

    public static Predicate<OdDto> applyExcludeByDto(Exclude exclude) {
        return vm -> ofNullable(exclude).map(exclude1 -> exclude1.test(NhiMetricRawMapper.INSTANCE.mapToExcludeDto(vm))).orElse(true);
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

        String key = date.getMonth().getValue() + patientId + cardNumber;
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

}
