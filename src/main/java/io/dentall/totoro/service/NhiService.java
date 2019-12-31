package io.dentall.totoro.service;

import com.univocity.parsers.annotations.BooleanString;
import com.univocity.parsers.annotations.Convert;
import com.univocity.parsers.annotations.Parsed;
import com.univocity.parsers.conversions.Conversion;
import com.univocity.parsers.csv.CsvParserSettings;
import com.univocity.parsers.csv.CsvRoutines;
import io.dentall.totoro.domain.NhiExtendDisposal;
import io.dentall.totoro.domain.NhiExtendTreatmentProcedure;
import io.dentall.totoro.repository.NhiExtendDisposalRepository;
import io.dentall.totoro.repository.NhiExtendPatientRepository;
import io.dentall.totoro.service.util.DateTimeUtil;
import io.dentall.totoro.service.util.StreamUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
public class NhiService {

    @FunctionalInterface
    interface TriConsumer<T, U, V> {
        void accept(T t, U u, V v);
    }

    private final Logger log = LoggerFactory.getLogger(NhiService.class);

    private final NhiExtendDisposalRepository nhiExtendDisposalRepository;

    private final NhiExtendPatientRepository nhiExtendPatientRepository;

    private static Map<String, Rule> rules;

    private static Map<String, String> surfaceLimitMap = new HashMap();

    private static Map<String, String> surfaceLimitErrorResponseMap = new HashMap();

    @Value("classpath:nhi_rule.csv")
    private Resource resourceFile;

    @Value("${nhi.rule:#{null}}")
    private String ruleFile;

    public NhiService(NhiExtendDisposalRepository nhiExtendDisposalRepository, NhiExtendPatientRepository nhiExtendPatientRepository) {
        this.nhiExtendDisposalRepository = nhiExtendDisposalRepository;
        this.nhiExtendPatientRepository = nhiExtendPatientRepository;

        surfaceLimitMap.put("VALIDATED_ONLY", "11,12,13,14,15,16,17,18,21,22,23,24,25,26,27,28,31,32,33,34,35,36,37,38,41,42,43,44,45,46,47,48,51,52,53,54,55,61,62,63,64,65,71,72,73,74,75,81,82,83,84,85,19,29,39,49,99,UB,LB,UR,UL,LR,LL,UA,LA,FM,");
        surfaceLimitMap.put("BLANK_ONLY", "");
        surfaceLimitMap.put("FM_ONLY", "FM,");
        surfaceLimitMap.put("TOOTH_AREA_ONLY", "11,12,13,14,15,16,17,18,21,22,23,24,25,26,27,28,31,32,33,34,35,36,37,38,41,42,43,44,45,46,47,48,51,52,53,54,55,61,62,63,64,65,71,72,73,74,75,81,82,83,84,85,19,29,39,49,99,UB,LB,UR,UL,LR,LL,UA,LA,");
        surfaceLimitMap.put("TOOTH_ONLY", "11,12,13,14,15,16,17,18,21,22,23,24,25,26,27,28,31,32,33,34,35,36,37,38,41,42,43,44,45,46,47,48,51,52,53,54,55,61,62,63,64,65,71,72,73,74,75,81,82,83,84,85,19,29,39,49,99,");
        surfaceLimitMap.put("DECIDUOUS_ONLY", "51,52,53,54,55,61,62,63,64,65,71,72,73,74,75,81,82,83,84,85,19,29,39,49,99,");
        surfaceLimitMap.put("PERMANENT_ONLY", "11,12,13,14,15,16,17,18,21,22,23,24,25,26,27,28,31,32,33,34,35,36,37,38,41,42,43,44,45,46,47,48,19,29,39,49,99,");
        surfaceLimitMap.put("FRONT_ONLY", "11,12,13,21,22,23,31,32,33,41,42,43,51,52,53,61,62,63,71,72,73,81,82,83,19,29,39,49,99,");
        surfaceLimitMap.put("BACK_ONLY", "14,15,16,17,18,24,25,26,27,28,34,35,36,37,38,44,45,46,47,48,54,55,64,65,74,75,84,85,19,29,39,49,99,");
        surfaceLimitMap.put("PERMANENT_MOLAR_ONLY", "16,17,18,24,25,26,27,28,36,37,38,46,47,48,19,29,39,49,99,");
        surfaceLimitMap.put("SPECIFIC_AREA_ONLY", "");
        surfaceLimitMap.put("SPECIFIC_TOOTH_ONLY", "");

        surfaceLimitErrorResponseMap.put("BLANK_ONLY", "不應填寫牙位");
        surfaceLimitErrorResponseMap.put("VALIDATED_ONLY", "限填合法一般、區域、全域牙位");
        surfaceLimitErrorResponseMap.put("FM_ONLY", "限填全域牙位");
        surfaceLimitErrorResponseMap.put("TOOTH_ONLY", "限填一般牙位");
        surfaceLimitErrorResponseMap.put("TOOTH_AREA_ONLY", "限填一般、區域牙位");
        surfaceLimitErrorResponseMap.put("DECIDUOUS_ONLY", "限填乳牙牙位");
        surfaceLimitErrorResponseMap.put("PERMANENT_ONLY", "限填恆牙牙位");
        surfaceLimitErrorResponseMap.put("FRONT_ONLY", "限填前牙牙位");
        surfaceLimitErrorResponseMap.put("BACK_ONLY", "限填後牙牙位");
        surfaceLimitErrorResponseMap.put("PERMANENT_MOLAR_ONLY", "限填恆牙大臼齒牙位");
        surfaceLimitErrorResponseMap.put("SPECIFIC_AREA_ONLY", "限填指定區域");
        surfaceLimitErrorResponseMap.put("SPECIFIC_TOOTH_ONLY", "限填指定牙位");
    }

    @PostConstruct
    public void afterPropertiesSet() {
        try {
            if (ruleFile == null) {
                rules = loadRules(resourceFile.getInputStream());
            } else {
                log.debug("file: {}", ruleFile);
                rules = loadRules(new FileSystemResource(ruleFile).getInputStream());
            }
        } catch (IOException e) {
            rules = new HashMap<>();
        }
    }

    public void checkNhiExtendTreatmentProcedures(Set<NhiExtendTreatmentProcedure> nhiExtendTreatmentProcedures) {
        if (!rules.isEmpty()) {
            nhiExtendTreatmentProcedures.forEach(nhiExtendTreatmentProcedure -> {
                if (rules.getOrDefault(nhiExtendTreatmentProcedure.getA73(), Rule.allPass()).getCode() != null) {
                    nhiExtendTreatmentProcedure.setCheck("");

                    Consumer<NhiExtendTreatmentProcedure> wrapCheckExclude = nhiExtendTxP -> checkExclude.accept(nhiExtendTxP, nhiExtendTreatmentProcedures);

                    checkXRay
                        .andThen(checkMedicalRecord)
                        .andThen(wrapCheckExclude)
                        .andThen(checkInterval)
                        .andThen(checkSurfaceLimit)
                        .accept(nhiExtendTreatmentProcedure);
                }
            });
        }
    }

    public Consumer<NhiExtendTreatmentProcedure> checkSurfaceLimit = nhiExtendTreatmentProcedure -> {
        String code = nhiExtendTreatmentProcedure.getA73();
        String[] surfaceLimit = rules.get(code).getSurfaceLimit();
        String limitType = surfaceLimit.length > 0 ?surfaceLimit[0] :"NO_SUCH_SURFACE_TYPE";
        boolean checkFail = false;
        String specificPosition = "";

        switch (limitType) {
            case "VALIDATED_ONLY":
            case "FM_ONLY":
            case "TOOTH_AREA_ONLY":
            case "TOOTH_ONLY":
            case "DECIDUOUS_ONLY":
            case "PERMANENT_ONLY":
            case "FRONT_ONLY":
            case "BACK_ONLY":
            case "PERMANENT_MOLAR_ONLY": {
                checkFail = nhiExtendTreatmentProcedure.getTreatmentProcedure().getTeeth().stream()
                    .anyMatch(tooth -> !surfaceLimitMap.get(limitType).contains(tooth.getPosition().concat(",")));
                break;
            }
            case "BLANK_ONLY": {
                checkFail = nhiExtendTreatmentProcedure.getTreatmentProcedure().getTeeth().size() > 0;
                break;
            }
            case "SPECIFIC_TOOTH_ONLY":
            case "SPECIFIC_AREA_ONLY": {
                if (surfaceLimit.length < 2) {
                    break;
                }

                String specificSurfaceLimit = surfaceLimit[1];
                specificPosition = specificSurfaceLimit;
                checkFail = nhiExtendTreatmentProcedure.getTreatmentProcedure().getTeeth().stream()
                    .anyMatch(tooth -> !specificSurfaceLimit.contains(tooth.getPosition().concat(",")));
                break;
            }
            default:
                break;
        }

        if (checkFail) {
            nhiExtendTreatmentProcedure.setCheck(nhiExtendTreatmentProcedure.getCheck() + code + " " + surfaceLimitErrorResponseMap.get(limitType) + " " + specificPosition + "\n");
        }
    };

    public Consumer<NhiExtendTreatmentProcedure> checkXRay = nhiExtendTreatmentProcedure -> {
        String code = nhiExtendTreatmentProcedure.getA73();
        if (rules.get(code).getXRay()) {
            nhiExtendTreatmentProcedure.setCheck(nhiExtendTreatmentProcedure.getCheck() + code + " 需要檢附 X 片\n");
        }
    };

    public Consumer<NhiExtendTreatmentProcedure> checkMedicalRecord = nhiExtendTreatmentProcedure -> {
        String code = nhiExtendTreatmentProcedure.getA73();
        if (rules.get(code).getMedicalRecord()) {
            nhiExtendTreatmentProcedure.setCheck(nhiExtendTreatmentProcedure.getCheck() + code + " 病歷須記載\n");
        }
    };

    public BiConsumer<NhiExtendTreatmentProcedure, Set<NhiExtendTreatmentProcedure>> checkExclude = (nhiExtendTreatmentProcedure, nhiExtendTreatmentProcedures) -> {
        String code = nhiExtendTreatmentProcedure.getA73();
        String[] excludes = rules.get(code).getExclude();
        if (excludes.length > 0) {
            StringBuilder check = new StringBuilder(nhiExtendTreatmentProcedure.getCheck());
            Set<String> otherProcedureCodes = nhiExtendTreatmentProcedures
                .stream()
                .filter(procedure -> procedure != nhiExtendTreatmentProcedure)
                .map(NhiExtendTreatmentProcedure::getA73)
                .collect(Collectors.toSet());
            Arrays.stream(excludes).forEach(exclude -> {
                if (otherProcedureCodes.contains(exclude)) {
                    check.append(code).append(" 不得與 ").append(exclude).append(" 同時申報\n");
                }
            });

            nhiExtendTreatmentProcedure.setCheck(check.toString());
        }
    };

    private BiConsumer<String, NhiExtendTreatmentProcedure> checkIntervalTime = (interval, nhiExtendTreatmentProcedure) -> {
        if (interval.contains("<=")) {
            checkIntervalLessOrEqualThan(interval, nhiExtendTreatmentProcedure);
        } else if (interval.contains("MMx")) {
            checkIntervalMonth(interval, nhiExtendTreatmentProcedure);
        } else if (interval.contains("YYYYx")) {
            checkIntervalYear(interval, nhiExtendTreatmentProcedure);
        } else if (interval.contains("LT")) {
            checkIntervalLifetime(interval, nhiExtendTreatmentProcedure);
        }
    };

    private BiConsumer<String, NhiExtendTreatmentProcedure> checkIntervalIdentity = (interval, nhiExtendTreatmentProcedure) -> {
        if (interval.contains("@")) {
            String identity = nhiExtendTreatmentProcedure.getNhiExtendDisposal().getPatientIdentity();
            String[] intervals = interval.split("-");
            Arrays.stream(intervals)
                .filter(info -> info.split("@")[0].equals(identity))
                .forEach(info -> checkIntervalTime.accept(info.split("@")[1], nhiExtendTreatmentProcedure));
        } else {
            checkIntervalTime.accept(interval, nhiExtendTreatmentProcedure);
        }
    };

    public Consumer<NhiExtendTreatmentProcedure> checkInterval = nhiExtendTreatmentProcedure -> {
        String interval = rules.get(nhiExtendTreatmentProcedure.getA73()).getInterval();
        if (interval != null) {
            if (interval.contains("|")) {
                String[] intervals = interval.split("\\|");
                Arrays.stream(intervals)
                    .forEach(info -> {
                        if (nhiExtendTreatmentProcedure.getCheck().equals("")) {
                            checkIntervalIdentity.accept(info, nhiExtendTreatmentProcedure);
                        }
                    });
            } else if (interval.contains("&")) {
                String[] intervals = interval.split("&");
                Arrays.stream(intervals)
                    .forEach(info -> checkIntervalIdentity.accept(info, nhiExtendTreatmentProcedure));
            } else {
                checkIntervalIdentity.accept(interval, nhiExtendTreatmentProcedure);
            }
        }
    };

    private List<NhiExtendTreatmentProcedure> getSameCodeNhiExtendTreatmentProceduresExcludeSelf(
        List<NhiExtendDisposal> nhiExtendDisposals,
        NhiExtendTreatmentProcedure nhiExtendTreatmentProcedure
    ) {
        return nhiExtendDisposals
            .stream()
            .flatMap(nhiExtendDisposal -> StreamUtil.asStream(nhiExtendDisposal.getNhiExtendTreatmentProcedures()))
            .filter(nhiExtTxP -> nhiExtTxP != nhiExtendTreatmentProcedure)
            .filter(nhiExtTxP -> nhiExtTxP.getA73().equals(nhiExtendTreatmentProcedure.getA73()))
            .sorted(Comparator.<NhiExtendTreatmentProcedure, LocalDate>comparing(nhiExtTxP -> nhiExtTxP.getNhiExtendDisposal().getDate()).reversed())
            .collect(Collectors.toList());
    }

    private void checkIntervalLessOrEqualThan(String interval, NhiExtendTreatmentProcedure nhiExtendTreatmentProcedure) {
        String[] info = interval.split("<=");
        int days;
        int times;
        String[] conditions;

        if (info[1].contains(";")) {
            conditions = info[1].split(";");
            if (conditions[0].contains("x")) {
                String[] nums = conditions[0].split("x");
                days = Integer.parseInt(nums[0]);
                times = Integer.parseInt(nums[1]);
            } else {
                days = Integer.parseInt(conditions[0]);
                times = 1;
            }
        } else {
            conditions = null;
            if (info[1].contains("x")) {
                String[] nums = info[1].split("x");
                days = Integer.parseInt(nums[0]);
                times = Integer.parseInt(nums[1]);
            } else {
                days = Integer.parseInt(info[1]);
                times = 1;
            }
        }

        LocalDate date = DateTimeUtil.getGreaterThanEqualDate.apply(days);
        List<NhiExtendTreatmentProcedure> nhiExtTxPs = getSameCodeNhiExtendTreatmentProceduresExcludeSelf(
            nhiExtendDisposalRepository.findByDateGreaterThanEqualAndPatientIdOrderByDateDesc(date, nhiExtendTreatmentProcedure.getNhiExtendDisposal().getPatientId()),
            nhiExtendTreatmentProcedure
        );

        if (conditions == null) {
            StringBuilder check = new StringBuilder(nhiExtendTreatmentProcedure.getCheck());
            // 由於當前資料會包含 當天 資料，所以扣掉 1 (當天治療)
            if (nhiExtTxPs.size() - 1 >= times) {
                StreamUtil.asStream(nhiExtTxPs).findFirst().ifPresent(first ->
                    // {健保代碼} {天數} 內不得重複申報 {次數} 次
                    check
                        .append(nhiExtendTreatmentProcedure.getA73())
                        .append(" ")
                        .append(days)
                        .append(" 天內不得重複申報 ")
                        .append(times)
                        .append(" 次\n")
                );
            }

            nhiExtendTreatmentProcedure.setCheck(check.toString());
        } else {
            Arrays.stream(conditions).skip(1).forEach(condition -> checkCondition(condition, nhiExtTxPs, nhiExtendTreatmentProcedure));
        }
    }

    private void checkIntervalMonth(String interval, NhiExtendTreatmentProcedure nhiExtendTreatmentProcedure) {
        String[] info = interval.split("MMx");
        int times;
        String[] conditions;

        if (info[1].contains(";")) {
            conditions = info[1].split(";");
            times = Integer.parseInt(conditions[0]);
        } else {
            conditions = null;
            times = Integer.parseInt(info[1]);
        }

        List<NhiExtendTreatmentProcedure> nhiExtTxPs = getSameCodeNhiExtendTreatmentProceduresExcludeSelf(
            nhiExtendDisposalRepository.findByDateBetweenAndPatientId(DateTimeUtil.localMonthFirstDay.get(), DateTimeUtil.localMonthLastDay.get(), nhiExtendTreatmentProcedure.getNhiExtendDisposal().getPatientId()),
            nhiExtendTreatmentProcedure
        );

        if (conditions == null) {
            StringBuilder check = new StringBuilder(nhiExtendTreatmentProcedure.getCheck());
            if (nhiExtTxPs.size() >= times) {
                // {健保代碼} 1 個月內不得重複申報 {次數} 次
                check.append(nhiExtendTreatmentProcedure.getA73()).append(" 1 個月內不得重複申報 ").append(times).append(" 次\n");
            }

            nhiExtendTreatmentProcedure.setCheck(check.toString());
        } else {
            Arrays.stream(conditions).skip(1).forEach(condition -> checkCondition(condition, nhiExtTxPs, nhiExtendTreatmentProcedure));
        }
    }

    private void checkIntervalYear(String interval, NhiExtendTreatmentProcedure nhiExtendTreatmentProcedure) {
        String[] info = interval.split("YYYYx");
        int times = Integer.parseInt(info[1]);

        List<NhiExtendTreatmentProcedure> nhiExtTxPs = getSameCodeNhiExtendTreatmentProceduresExcludeSelf(
            nhiExtendDisposalRepository.findByDateBetweenAndPatientId(DateTimeUtil.localYearFirstDay.get(), DateTimeUtil.localYearLastDay.get(), nhiExtendTreatmentProcedure.getNhiExtendDisposal().getPatientId()),
            nhiExtendTreatmentProcedure
        );

        StringBuilder check = new StringBuilder(nhiExtendTreatmentProcedure.getCheck());
        if (nhiExtTxPs.size() >= times) {
            // {健保代碼} 年度不得重複申報 {次數} 次
            check.append(nhiExtendTreatmentProcedure.getA73()).append(" 年度不得重複申報 ").append(times).append(" 次\n");
        }

        nhiExtendTreatmentProcedure.setCheck(check.toString());
    }

    private void checkIntervalLifetime(String interval, NhiExtendTreatmentProcedure nhiExtendTreatmentProcedure) {
        String[] conditions = interval.split(";");

        long id = nhiExtendTreatmentProcedure.getNhiExtendDisposal().getPatientId();
        nhiExtendPatientRepository
            .findById(id)
            .filter(nhiExtendPatient -> nhiExtendPatient.getLifetime() != null)
            .ifPresent(nhiExtendPatient -> {
                Map<String, Object> lifetime = nhiExtendPatient.getLifetime();
                String code = nhiExtendTreatmentProcedure.getA73();
                if (lifetime.get(code) != null) {
                    if (conditions.length > 1) {
                        Arrays.stream(conditions).skip(1).forEach(condition -> checkLifetimeCondition(condition, nhiExtendTreatmentProcedure));
                    }
                }
            });
    }

    private void checkCondition(String condition, List<NhiExtendTreatmentProcedure> nhiExtTxPs, NhiExtendTreatmentProcedure nhiExtendTreatmentProcedure) {
        if (condition.contains("Qx")) {
            String[] nums = condition.split("Qx");
            int limit = Integer.parseInt(nums[1]);
            checkQuadrants(nhiExtTxPs, limit, nhiExtendTreatmentProcedure);
        } else if (condition.contains("PTx")) {
            String[] nums = condition.split("PTx");
            int limit = Integer.parseInt(nums[1]);
            checkTeeth(nhiExtTxPs, limit, nhiExtendTreatmentProcedure, Rule.emptyPermanentTeethCount.get(), Rule.getPermanentTeeth);
        } else if (condition.contains("DTx")) {
            String[] nums = condition.split("DTx");
            int limit = Integer.parseInt(nums[1]);
            checkTeeth(nhiExtTxPs, limit, nhiExtendTreatmentProcedure, Rule.emptyDeciduousTeethCount.get(), Rule.getDeciduousTeeth);
        } else if (condition.contains("SHx")) {
            String[] nums = condition.split("SHx");
            int limit = Integer.parseInt(nums[1]);
            checkSameHospital(nhiExtTxPs, limit, nhiExtendTreatmentProcedure);
        }
    }

    private void checkLifetimeCondition(String condition, NhiExtendTreatmentProcedure nhiExtendTreatmentProcedure) {
        if (condition.contains("PTx")) {
            String[] nums = condition.split("PTx");
            int limit = Integer.parseInt(nums[1]);
            checkLifetimeTeeth(limit, nhiExtendTreatmentProcedure, Rule.emptyPermanentTeethCount.get(), Rule.getPermanentTeeth);
        } else if (condition.contains("DTx")) {
            String[] nums = condition.split("DTx");
            int limit = Integer.parseInt(nums[1]);
            checkLifetimeTeeth(limit, nhiExtendTreatmentProcedure, Rule.emptyDeciduousTeethCount.get(), Rule.getDeciduousTeeth);
        }
    }

    private void checkQuadrants(List<NhiExtendTreatmentProcedure> nhiExtTxPs, int limit, NhiExtendTreatmentProcedure nhiExtendTreatmentProcedure) {
        Map<Integer, Integer> quadrantsCount = Rule.emptyQuadrantsCount.get();

        Stream.concat(Stream.of(nhiExtendTreatmentProcedure), nhiExtTxPs.stream()).forEach(nhiExtTxP -> {
            String[] positions = nhiExtTxP.getA74().split("(?<=\\G.{2})");
            Arrays.stream(positions).forEach(position ->
                Rule.getQuadrants(position).forEach(quadrant ->
                    quadrantsCount.put(quadrant, quadrantsCount.get(quadrant) + 1)
                )
            );
        });

        StringBuilder check = new StringBuilder(nhiExtendTreatmentProcedure.getCheck());
        boolean any = quadrantsCount.values().stream().anyMatch(count -> count > limit);
        if (any) {
            // {健保代碼} 同象限不得重複申報 {次數} 次
            check.append(nhiExtendTreatmentProcedure.getA73()).append(" 同象限不得重複申報 ").append(limit).append(" 次\n");
        }

        nhiExtendTreatmentProcedure.setCheck(check.toString());
    }

    private void checkTeeth(
        List<NhiExtendTreatmentProcedure> nhiExtTxPs,
        int limit,
        NhiExtendTreatmentProcedure nhiExtendTreatmentProcedure,
        Map<String, Integer> teethCount,
        Function<String, List<String>> getTeeth
    ) {
        Stream.concat(Stream.of(nhiExtendTreatmentProcedure), nhiExtTxPs.stream()).forEach(nhiExtTxP -> {
            String[] positions = nhiExtTxP.getA74().split("(?<=\\G.{2})");
            Arrays.stream(positions).forEach(position ->
                getTeeth.apply(position).forEach(tooth ->
                    teethCount.put(tooth, teethCount.get(tooth) + 1)
                )
            );
        });

        StringBuilder check = new StringBuilder(nhiExtendTreatmentProcedure.getCheck());
        boolean any = teethCount.values().stream().anyMatch(count -> count > limit);
        if (any) {
            // {健保代碼} 同顆牙不得重複申報 {次數} 次
            check.append(nhiExtendTreatmentProcedure.getA73()).append(" 同顆牙不得重複申報 ").append(limit).append(" 次\n");
        }

        nhiExtendTreatmentProcedure.setCheck(check.toString());
    }

    private void checkLifetimeTeeth(
        int limit,
        NhiExtendTreatmentProcedure nhiExtendTreatmentProcedure,
        Map<String, Integer> teethCount,
        Function<String, List<String>> getTeeth
    ) {
        long id = nhiExtendTreatmentProcedure.getNhiExtendDisposal().getPatientId();
        nhiExtendPatientRepository.findById(id).ifPresent(nhiExtendPatient -> {
            Map<String, Object> lifetime = nhiExtendPatient.getLifetime();
            String code = nhiExtendTreatmentProcedure.getA73();
            if (lifetime.get(code) != null) {
                String[] teeth = ((String) lifetime.get(code)).split(",");
                Arrays.stream(teeth).forEach(position ->
                    getTeeth.apply(position).forEach(tooth ->
                        teethCount.put(tooth, teethCount.get(tooth) + 1)
                    )
                );

                teeth = nhiExtendTreatmentProcedure.getA74().split("(?<=\\G.{2})");
                StringBuilder check = new StringBuilder(nhiExtendTreatmentProcedure.getCheck());
                Arrays.stream(teeth).forEach(position -> {
                    int count = teethCount.get(position);
                    if (count >= limit) {
                        // {健保代碼} 同顆牙不得重複申報 {次數} 次
                        check.append(code).append(" 同顆牙不得重複申報 ").append(limit).append(" 次\n");
                    }
                });
                nhiExtendTreatmentProcedure.setCheck(check.toString());
            }
        });
    }

    private void checkSameHospital(List<NhiExtendTreatmentProcedure> nhiExtTxPs, int limit, NhiExtendTreatmentProcedure nhiExtendTreatmentProcedure) {
        long count = nhiExtTxPs
            .stream()
            .filter(nhiExtTxP -> nhiExtTxP.getNhiExtendDisposal().getA14().equals(nhiExtendTreatmentProcedure.getNhiExtendDisposal().getA14()))
            .count();

        StringBuilder check = new StringBuilder(nhiExtendTreatmentProcedure.getCheck());
        if (count >= limit) {
            // {健保代碼} 同一院所不得重複申報 {次數} 次
            check.append(nhiExtendTreatmentProcedure.getA73()).append(" 同一院所不得重複申報 ").append(limit).append(" 次\n");
        }

        nhiExtendTreatmentProcedure.setCheck(check.toString());
    }

    private Map<String, Rule> loadRules(InputStream input) {
        CsvParserSettings parserSettings = new CsvParserSettings();
        parserSettings.getFormat().setDelimiter(',');
        parserSettings.getFormat().setLineSeparator("\n");
        parserSettings.setHeaderExtractionEnabled(true);

        CsvRoutines routines = new CsvRoutines(parserSettings);
        Map<String, Rule> rules = new HashMap<>();
        for (Rule rule : routines.iterate(Rule.class, input, "UTF-8")) {
            rules.put(rule.getCode(), rule);
        }

        return rules;
    }

    public static class Rule {

        @Parsed
        private String code;

        @BooleanString(falseStrings = {""}, trueStrings = {"y", "Y"})
        @Parsed(field = "x_ray", defaultNullRead = "")
        private Boolean xRay;

        @BooleanString(falseStrings = {""}, trueStrings = {"y", "Y"})
        @Parsed(field = "medical_record", defaultNullRead = "")
        private Boolean medicalRecord;

        @Parsed
        @Convert(conversionClass = Splitter.class, args = ",")
        private String[] exclude;

        @Parsed
        private String interval;

        @Parsed
        @Convert(conversionClass = Splitter.class, args = ",")
        private String[] fdi;

        @Parsed(field = "surface_limit", defaultNullRead = "")
        @Convert(conversionClass = Splitter.class, args = "=")
        private String[] surfaceLimit;

        static Rule allPass() {
            Rule allPass = new Rule();
            allPass.setCode(null);

            return allPass;
        }

        public String[] getSurfaceLimit() {
            return surfaceLimit;
        }

        public void setSurfaceLimit(String[] surfaceLimit) {
            this.surfaceLimit = surfaceLimit;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }

        public void setXRay(Boolean xRay) {
            this.xRay = xRay;
        }

        public Boolean getXRay() {
            return xRay;
        }

        public void setMedicalRecord(Boolean medicalRecord) {
            this.medicalRecord = medicalRecord;
        }

        public Boolean getMedicalRecord() {
            return medicalRecord;
        }

        public void setExclude(String[] exclude) {
            this.exclude = exclude;
        }

        public String[] getExclude() {
            return exclude;
        }

        public void setInterval(String interval) {
            this.interval = interval;
        }

        public String getInterval() {
            return interval;
        }

        public void setFdi(String[] fdi) {
            this.fdi = fdi;
        }

        public String[] getFdi() {
            return fdi;
        }

        static Supplier<Map<Integer, Integer>> emptyQuadrantsCount = () -> new HashMap<Integer, Integer>() {{
            put(1, 0);
            put(2, 0);
            put(3, 0);
            put(4, 0);
        }};

        static List<Integer> getQuadrants(String val) {
            List<Integer> quadrants = new ArrayList<>();
            switch(val) {
                case "FM":
                    quadrants.addAll(Arrays.asList(1, 2, 3, 4));
                    break;
                case "UB":
                case "UA":
                    quadrants.addAll(Arrays.asList(1, 2));
                    break;
                case "UR":
                case "11":
                case "12":
                case "13":
                case "14":
                case "15":
                case "16":
                case "17":
                case "18":
                case "51":
                case "52":
                case "53":
                case "54":
                case "55":
                    quadrants.add(1);
                    break;
                case "UL":
                case "21":
                case "22":
                case "23":
                case "24":
                case "25":
                case "26":
                case "27":
                case "28":
                case "61":
                case "62":
                case "63":
                case "64":
                case "65":
                    quadrants.add(2);
                    break;
                case "LB":
                case "LA":
                    quadrants.addAll(Arrays.asList(3, 4));
                    break;
                case "LR":
                case "41":
                case "42":
                case "43":
                case "44":
                case "45":
                case "46":
                case "47":
                case "48":
                case "81":
                case "82":
                case "83":
                case "84":
                case "85":
                    quadrants.add(4);
                    break;
                case "LL":
                case "31":
                case "32":
                case "33":
                case "34":
                case "35":
                case "36":
                case "37":
                case "38":
                case "71":
                case "72":
                case "73":
                case "74":
                case "75":
                    quadrants.add(3);
                    break;
            }

            return quadrants;
        }

        static Supplier<Map<String, Integer>> emptyPermanentTeethCount = () -> new HashMap<String, Integer>() {{
            put("11", 0); put("12", 0); put("13", 0); put("14", 0); put("15", 0); put("16", 0); put("17", 0); put("18", 0);
            put("21", 0); put("22", 0); put("23", 0); put("24", 0); put("25", 0); put("26", 0); put("27", 0); put("28", 0);
            put("31", 0); put("32", 0); put("33", 0); put("34", 0); put("35", 0); put("36", 0); put("37", 0); put("38", 0);
            put("41", 0); put("42", 0); put("43", 0); put("44", 0); put("45", 0); put("46", 0); put("47", 0); put("48", 0);
        }};

        static Function<String, List<String>> getPermanentTeeth = val -> {
            List<String> teeth = new ArrayList<>();
            switch(val) {
                case "FM":
                    teeth.addAll(Arrays.asList(
                        "11", "12", "13", "14", "15", "16", "17", "18",
                        "21", "22", "23", "24", "25", "26", "27", "28",
                        "31", "32", "33", "34", "35", "36", "37", "38",
                        "41", "42", "43", "44", "45", "46", "47", "48"
                    ));
                    break;
                case "UB":
                    teeth.addAll(Arrays.asList(
                        "11", "12", "13", "14", "15", "16", "17", "18",
                        "21", "22", "23", "24", "25", "26", "27", "28"
                    ));
                    break;
                case "UA":
                    teeth.addAll(Arrays.asList(
                        "11", "12", "13",
                        "21", "22", "23"
                    ));
                    break;
                case "UR":
                    teeth.addAll(Arrays.asList(
                        "11", "12", "13", "14", "15", "16", "17", "18"
                    ));
                    break;
                case "UL":
                    teeth.addAll(Arrays.asList(
                        "21", "22", "23", "24", "25", "26", "27", "28"
                    ));
                    break;
                case "LB":
                    teeth.addAll(Arrays.asList(
                        "31", "32", "33", "34", "35", "36", "37", "38",
                        "41", "42", "43", "44", "45", "46", "47", "48"
                    ));
                    break;
                case "LA":
                    teeth.addAll(Arrays.asList(
                        "31", "32", "33",
                        "41", "42", "43"
                    ));
                case "LR":
                    teeth.addAll(Arrays.asList(
                        "41", "42", "43", "44", "45", "46", "47", "48"
                    ));
                    break;
                case "LL":
                    teeth.addAll(Arrays.asList(
                        "31", "32", "33", "34", "35", "36", "37", "38"
                    ));
                    break;
                case "11":
                case "12":
                case "13":
                case "14":
                case "15":
                case "16":
                case "17":
                case "18":
                case "21":
                case "22":
                case "23":
                case "24":
                case "25":
                case "26":
                case "27":
                case "28":
                case "31":
                case "32":
                case "33":
                case "34":
                case "35":
                case "36":
                case "37":
                case "38":
                case "41":
                case "42":
                case "43":
                case "44":
                case "45":
                case "46":
                case "47":
                case "48":
                    teeth.addAll(Collections.singletonList(val));
                    break;
            }

            return teeth;
        };

        static Supplier<Map<String, Integer>> emptyDeciduousTeethCount = () -> new HashMap<String, Integer>() {{
            put("51", 0); put("52", 0); put("53", 0); put("54", 0); put("55", 0);
            put("61", 0); put("62", 0); put("63", 0); put("64", 0); put("65", 0);
            put("71", 0); put("72", 0); put("73", 0); put("74", 0); put("75", 0);
            put("81", 0); put("82", 0); put("83", 0); put("84", 0); put("85", 0);
        }};

        static Function<String, List<String>> getDeciduousTeeth = val -> {
            List<String> teeth = new ArrayList<>();
            switch(val) {
                case "FM":
                    teeth.addAll(Arrays.asList(
                        "51", "52", "53", "54", "55",
                        "61", "62", "63", "64", "65",
                        "71", "72", "73", "74", "75",
                        "81", "82", "83", "84", "85"
                    ));
                    break;
                case "UB":
                    teeth.addAll(Arrays.asList(
                        "51", "52", "53", "54", "55",
                        "61", "62", "63", "64", "65"
                    ));
                    break;
                case "UA":
                    teeth.addAll(Arrays.asList(
                        "51", "52", "53",
                        "61", "62", "63"
                    ));
                    break;
                case "UR":
                    teeth.addAll(Arrays.asList(
                        "51", "52", "53", "54", "55"
                    ));
                    break;
                case "UL":
                    teeth.addAll(Arrays.asList(
                        "61", "62", "63", "64", "65"
                    ));
                    break;
                case "LB":
                    teeth.addAll(Arrays.asList(
                        "71", "72", "73", "74", "75",
                        "81", "82", "83", "84", "85"
                    ));
                    break;
                case "LA":
                    teeth.addAll(Arrays.asList(
                        "71", "72", "73",
                        "81", "82", "83"
                    ));
                case "LR":
                    teeth.addAll(Arrays.asList(
                        "81", "82", "83", "84", "85"
                    ));
                    break;
                case "LL":
                    teeth.addAll(Arrays.asList(
                        "71", "72", "73", "74", "75"
                    ));
                    break;
                case "51":
                case "52":
                case "53":
                case "54":
                case "55":
                case "61":
                case "62":
                case "63":
                case "64":
                case "65":
                case "71":
                case "72":
                case "73":
                case "74":
                case "75":
                case "81":
                case "82":
                case "83":
                case "84":
                case "85":
                    teeth.addAll(Collections.singletonList(val));
                    break;
            }

            return teeth;
        };
    }

    // https://www.univocity.com/pages/java_beans.html#using-your-own-conversions-in-annotations
    public static class Splitter implements Conversion<String, String[]> {

        private String separator;

        public Splitter(String... args) {
            if(args.length == 0){
                separator = ",";
            } else {
                separator = args[0];
            }
        }

        @Override
        public String[] execute(String input) {
            if(input == null){
                return new String[0];
            }
            return input.split(separator);
        }

        @Override
        public String revert(String[] input) {
            StringBuilder out = new StringBuilder();
            for (String value : input) {
                if (out.length() > 0) {
                    out.append(separator);
                }
                out.append(value);
            }
            return out.toString();
        }
    }
}
