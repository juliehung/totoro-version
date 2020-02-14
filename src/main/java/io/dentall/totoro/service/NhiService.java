package io.dentall.totoro.service;

import com.univocity.parsers.conversions.Conversion;
import com.univocity.parsers.csv.CsvParserSettings;
import com.univocity.parsers.csv.CsvRoutines;
import io.dentall.totoro.business.dto.*;
import io.dentall.totoro.domain.NhiExtendDisposal;
import io.dentall.totoro.domain.NhiExtendTreatmentProcedure;
import io.dentall.totoro.domain.Treatment;
import io.dentall.totoro.domain.TreatmentProcedure;
import io.dentall.totoro.repository.NhiExtendDisposalRepository;
import io.dentall.totoro.repository.NhiExtendPatientRepository;
import io.dentall.totoro.service.dto.TreatmentCriteria;
import io.dentall.totoro.service.util.StreamUtil;
import io.github.jhipster.service.filter.LongFilter;
import org.apache.commons.lang3.StringUtils;
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
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
@Transactional
public class NhiService {

    @FunctionalInterface
    interface TriConsumer<T, U, V> {
        void accept(T t, U u, V v);
    }

    private final Logger log = LoggerFactory.getLogger(NhiService.class);

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    private final NhiExtendDisposalRepository nhiExtendDisposalRepository;

    private final NhiExtendPatientRepository nhiExtendPatientRepository;

    private final TreatmentQueryService treatmentQueryService;

    private static Map<String, Rule> rules;

    private static Map<String, String> positionLimitMap = new HashMap<>();

    private static Map<String, String> positionLimitErrorResponseMap = new HashMap<>();

    private static Map<String, String> surfaceLimitMap = new HashMap<>();

    private static Map<String, String> surfaceLimitErrorResponseMap = new HashMap<>();

    @Value("classpath:nhi_rule.csv")
    private Resource resourceFile;

    @Value("${nhi.rule:#{null}}")
    private String ruleFile;

    public NhiService(NhiExtendDisposalRepository nhiExtendDisposalRepository, NhiExtendPatientRepository nhiExtendPatientRepository, TreatmentQueryService treatmentQueryService) {
        this.nhiExtendDisposalRepository = nhiExtendDisposalRepository;
        this.nhiExtendPatientRepository = nhiExtendPatientRepository;
        this.treatmentQueryService = treatmentQueryService;

        surfaceLimitMap.put("SURFACE_BLANK_ONLY", "");
        surfaceLimitMap.put("SURFACE_VALIDATED_ONLY", "M,D,L,B,O,P,I,F,C");
        surfaceLimitMap.put("SURFACE_SPECIFIC_1_ALPHABET_ONLY", "");
        surfaceLimitMap.put("SURFACE_SPECIFIC_4_ALPHABET_ONLY", "");

        surfaceLimitErrorResponseMap.put("SURFACE_BLANK_ONLY", "不須填牙面");
        surfaceLimitErrorResponseMap.put("SURFACE_VALIDATED_ONLY", "建議填");
        surfaceLimitErrorResponseMap.put("SURFACE_SPECIFIC_1_ALPHABET_ONLY", "建議填");
        surfaceLimitErrorResponseMap.put("SURFACE_SPECIFIC_4_ALPHABET_ONLY", "建議填");
        surfaceLimitErrorResponseMap.put("SURFACE_LIMIT_NUMBER", "申報面數不合");

        positionLimitMap.put("VALIDATED_ONLY", "11,12,13,14,15,16,17,18,21,22,23,24,25,26,27,28,31,32,33,34,35,36,37,38,41,42,43,44,45,46,47,48,51,52,53,54,55,61,62,63,64,65,71,72,73,74,75,81,82,83,84,85,19,29,39,49,99,UB,LB,UR,UL,LR,LL,UA,LA,FM,");
        positionLimitMap.put("BLANK_ONLY", "");
        positionLimitMap.put("FM_ONLY", "FM,");
        positionLimitMap.put("TOOTH_AREA_ONLY", "11,12,13,14,15,16,17,18,21,22,23,24,25,26,27,28,31,32,33,34,35,36,37,38,41,42,43,44,45,46,47,48,51,52,53,54,55,61,62,63,64,65,71,72,73,74,75,81,82,83,84,85,19,29,39,49,99,UB,LB,UR,UL,LR,LL,UA,LA,");
        positionLimitMap.put("TOOTH_ONLY", "11,12,13,14,15,16,17,18,21,22,23,24,25,26,27,28,31,32,33,34,35,36,37,38,41,42,43,44,45,46,47,48,51,52,53,54,55,61,62,63,64,65,71,72,73,74,75,81,82,83,84,85,19,29,39,49,99,");
        positionLimitMap.put("DECIDUOUS_ONLY", "51,52,53,54,55,61,62,63,64,65,71,72,73,74,75,81,82,83,84,85,99,");
        positionLimitMap.put("PERMANENT_ONLY", "11,12,13,14,15,16,17,18,21,22,23,24,25,26,27,28,31,32,33,34,35,36,37,38,41,42,43,44,45,46,47,48,19,29,39,49,99,");
        positionLimitMap.put("FRONT_ONLY", "11,12,13,21,22,23,31,32,33,41,42,43,51,52,53,61,62,63,71,72,73,81,82,83,19,29,39,49,99,");
        positionLimitMap.put("BACK_ONLY", "14,15,16,17,18,24,25,26,27,28,34,35,36,37,38,44,45,46,47,48,54,55,64,65,74,75,84,85,19,29,39,49,99,");
        positionLimitMap.put("PERMANENT_FRONT_ONLY", "11,12,13,21,22,23,31,32,33,41,42,43,19,29,39,49,99,");
        positionLimitMap.put("PERMANENT_BACK_ONLY", "14,15,16,17,18,24,25,26,27,28,34,35,36,37,38,44,45,46,47,48,19,29,39,49,99,");
        positionLimitMap.put("PERMANENT_PREMOLAR_ONLY", "14,15,24,25,34,35,44,45,19,29,39,49,99,");
        positionLimitMap.put("PERMANENT_MOLAR_ONLY", "16,17,18,24,25,26,27,28,36,37,38,46,47,48,19,29,39,49,99,");
        positionLimitMap.put("SPECIFIC_AREA_ONLY", "");
        positionLimitMap.put("SPECIFIC_TOOTH_ONLY", "");

        positionLimitErrorResponseMap.put("BLANK_ONLY", "不應填寫牙位");
        positionLimitErrorResponseMap.put("VALIDATED_ONLY", "建議填合法一般、區域、全域牙位");
        positionLimitErrorResponseMap.put("FM_ONLY", "建議填全域牙位");
        // 僅限 所有牙位
        positionLimitErrorResponseMap.put("TOOTH_ONLY", "建議填11-19,21-29,31-39,41-49,51-55,61-65,71-75,81-85,99");
        // 僅限 所有牙位，所有區域
        positionLimitErrorResponseMap.put("TOOTH_AREA_ONLY", "建議填11-19,21-29,31-39,41-49,51-55,61-65,71-75,81-85,99,UB,LB,UR,UL,LR,LL,UA,LA");
        // 僅限 乳牙牙位
        positionLimitErrorResponseMap.put("DECIDUOUS_ONLY", "建議填51-55,61-65,71-75,81-85,99");
        // 僅限 恆牙牙位
        positionLimitErrorResponseMap.put("PERMANENT_ONLY", "建議填11-19,21-29,31-39,41-49,51-55,99");
        // 僅限 前牙牙位
        positionLimitErrorResponseMap.put("FRONT_ONLY", "建議填11-13,21-23,31-33,41-43,51-53,61-63,71-73,81-83,99");
        // 僅限 後牙牙位
        positionLimitErrorResponseMap.put("BACK_ONLY", "建議填14-19,24-29,34-39,44-49,51-53,61-63,71-73,81-83,99");
        // 僅限 恆牙前牙牙位
        positionLimitErrorResponseMap.put("PERMANENT_FRONT_ONLY", "建議填11-13,21-23,31-33,41-43,99");
        // 僅限 恆牙後牙牙位
        positionLimitErrorResponseMap.put("PERMANENT_BACK_ONLY", "建議填14-19,24-29,34-39,44-49");
        // 僅限 恆牙小臼齒牙位
        positionLimitErrorResponseMap.put("PERMANENT_PREMOLAR_ONLY", "建議填14,15,24,25,34,35,44,45,19,29,39,49,99");
        // 僅限 恆牙大臼齒牙位
        positionLimitErrorResponseMap.put("PERMANENT_MOLAR_ONLY", "建議填16,17,18,24,25,26,27,28,36,37,38,46,47,48,19,29,39,49,99");
        // 僅限 指定牙位
        positionLimitErrorResponseMap.put("SPECIFIC_TOOTH_ONLY", "建議填指定牙位");
        // 僅限 指定區域
        positionLimitErrorResponseMap.put("SPECIFIC_AREA_ONLY", "建議填指定區域");
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

    public boolean isPermanentTooth(String tooth) {
        return positionLimitMap.get("PERMANENT_ONLY").contains(tooth.concat(","));
    }

    public boolean isDeciduousTooth(String tooth) {
        return positionLimitMap.get("DECIDUOUS_ONLY").contains(tooth.concat(","));
    }

    public static Stream<String> splitToothFromA74(String a74) {
        return Arrays.stream(a74.split("(?<=\\G..)"));
    }

    public PersonalNhiExtendTreatmentProcedureMap getSelfExcludedPersonalNhiExtendTreatmentProcedureMap(
        Long patientId,
        NhiExtendTreatmentProcedure targetNhiExtendTreatmentProcedure
    ) {
        // Query patient's all nhi treatment procedures
        TreatmentCriteria treatmentCriteria = new TreatmentCriteria();
        LongFilter lf = new LongFilter();
        lf.setEquals(patientId);
        treatmentCriteria.setPatientId(lf);
        Optional<Treatment> optionalTreatment = treatmentQueryService.findByCriteria(treatmentCriteria).stream().findFirst();

        if (optionalTreatment.isPresent()) {
            Treatment treatment = optionalTreatment.get();

            // Create map which personal all nhi treatment procedure record with key as a73(nhi procedure code)
            Set<NhiExtendTreatmentProcedure> peronalAllNhiTxProc = treatment.getTreatmentPlans().iterator().next()
                .getTreatmentTasks().iterator().next()
                .getTreatmentProcedures().stream()
                .map(TreatmentProcedure::getNhiExtendTreatmentProcedure)
                .filter(nhiExtendTreatmentProcedure -> !nhiExtendTreatmentProcedure.getId().equals(targetNhiExtendTreatmentProcedure.getId()))
                .collect(Collectors.toSet());

            return new PersonalNhiExtendTreatmentProcedureMap().nhiExtendTreatmentProcedure(peronalAllNhiTxProc);

        } else {
            return null;
        }

    }

    /**
     * Checking nhi treatment procedure main entrance
     *
     * @param nhiExtendTreatmentProcedures
     */
    public void checkNhiExtendTreatmentProcedures(Set<NhiExtendTreatmentProcedure> nhiExtendTreatmentProcedures) {
        if (!rules.isEmpty()) {

            Long patientId = nhiExtendTreatmentProcedures.iterator().next()
                .getTreatmentProcedure()
                .getTreatmentTask()
                .getTreatmentPlan()
                .getTreatment()
                .getPatient()
                .getId();

            nhiExtendTreatmentProcedures.forEach(targetNhiExtendTreatmentProcedure -> {
                if (rules.getOrDefault(targetNhiExtendTreatmentProcedure.getA73(), Rule.allPass()).getCode() != null) {
                    targetNhiExtendTreatmentProcedure.setCheck("");

                    PersonalNhiExtendTreatmentProcedureMap finalPersonalNhiExtendTreatmentProcedureMap =
                        getSelfExcludedPersonalNhiExtendTreatmentProcedureMap(patientId, targetNhiExtendTreatmentProcedure);

                    // Wrappers
                    Consumer<NhiExtendTreatmentProcedure> wrapCheckExclude = nhiExtendTxP -> checkExclude.accept(nhiExtendTxP, nhiExtendTreatmentProcedures);
                    Consumer<NhiExtendTreatmentProcedure> wrapCheckOtherCodeDelcarationConflict = nhiExtendTxP ->
                        checkOtherCodeDeclarationConflict.accept(nhiExtendTxP, finalPersonalNhiExtendTreatmentProcedureMap);

                    checkXRay
                        .andThen(wrapCheckExclude)
                        .andThen(wrapCheckOtherCodeDelcarationConflict)
                        .andThen(checkMedicalRecord)
                        .andThen(checkInterval)
                        .andThen(checkPositionLimit)
                        .andThen(checkSurfaceLimit)
                        .accept(targetNhiExtendTreatmentProcedure);

                }
            });
        }
    }

    public Consumer<NhiExtendTreatmentProcedure> checkSurfaceLimit = nhiExtendTreatmentProcedure -> {
        String code = nhiExtendTreatmentProcedure.getA73();
        String surface = nhiExtendTreatmentProcedure.getA75();
        String[] surfaceLimitRule = rules.get(code).getSurfaceLimit();

        String limitType = surfaceLimitRule.length > 0 ? surfaceLimitRule[0] : "NO_SUCH_SURFACE_TYPE";
        String specificSurface = "";
        int limitNumb = 0;
        boolean checkFail = false;
        boolean isOutOfLimitedSurfaceNumber = false;

        // Split limitNumber and replace ; with ""
        if (surfaceLimitRule.length > 1) {
            try {
                String[] ruleLimitNumb = surfaceLimitRule[1].split(";");
                specificSurface = ruleLimitNumb[0];
                limitNumb = Integer.parseInt(ruleLimitNumb[1]);
            } catch (NumberFormatException e) {
                // do nothing
            }
        }
        limitType = limitType.replace(";", "");

        // Surface string check
        switch (limitType) {
            case "SURFACE_BLANK_ONLY": {
                if (!surface.isEmpty()) {
                    checkFail = true;
                }
                break;
            }
            case "SURFACE_VALIDATED_ONLY": {
                // Cause lambda need final value,  it's modified by previous code. Thought, make a copy for it.
                String finalSpecificSurface = surfaceLimitMap.get("SURFACE_VALIDATED_ONLY").replace(",", "");
                specificSurface = surfaceLimitMap.get("SURFACE_VALIDATED_ONLY");
                limitNumb = finalSpecificSurface.length();
                if (surface.chars()
                    .mapToObj(c -> String.valueOf((char) c))
                    .anyMatch(c -> !finalSpecificSurface.contains(c))) {
                    checkFail = true;
                }

                if (surface.length() > limitNumb) {
                    checkFail = true;
                    isOutOfLimitedSurfaceNumber = true;
                }

                break;
            }
            case "SURFACE_SPECIFIC_1_ALPHABET_ONLY": {
                // Cause lambda need final value,  it's modified by previous code. Thought, make a copy for it.
                String finalSpecificSurface = specificSurface;
                if (surface.chars()
                    .mapToObj(c -> String.valueOf((char) c))
                    .anyMatch(c -> !finalSpecificSurface.contains(c))) {
                    checkFail = true;
                }

                if (surface.length() > limitNumb) {
                    checkFail = true;
                    isOutOfLimitedSurfaceNumber = true;
                }

                break;
            }
            case "SURFACE_SPECIFIC_4_ALPHABET_ONLY": {
                if (Arrays.stream(specificSurface.split(","))
                    .allMatch(fss -> !fss.equals(surface))
                ) {
                    checkFail = true;
                }

                if (surface.split(",").length > limitNumb) {
                    checkFail = true;
                    isOutOfLimitedSurfaceNumber = true;
                }

                break;
            }
            default:
                break;
        }

        if (checkFail) {
            if (isOutOfLimitedSurfaceNumber) {
                nhiExtendTreatmentProcedure.setCheck(nhiExtendTreatmentProcedure.getCheck() + " " + surfaceLimitErrorResponseMap.get("SURFACE_LIMIT_NUMBER") + "\n");
            } else {
                String withSpecification = StringUtils.isBlank(specificSurface)
                    ? ""
                    : specificSurface + " 牙面";
                nhiExtendTreatmentProcedure.setCheck(nhiExtendTreatmentProcedure.getCheck() + " " + surfaceLimitErrorResponseMap.get(limitType) + " " + withSpecification + "\n");
            }
        }
    };

    public Consumer<NhiExtendTreatmentProcedure> checkPositionLimit = nhiExtendTreatmentProcedure -> {
        String code = nhiExtendTreatmentProcedure.getA73();
        String[] positionLimit = rules.get(code).getPositionLimit();
        String limitType = positionLimit.length > 0 ? positionLimit[0] : "NO_SUCH_POSITION_TYPE";
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
            case "PERMANENT_FRONT_ONLY":
            case "PERMANENT_BACK_ONLY":
            case "PERMANENT_MOLAR_ONLY":
            case "PERMANENT_PREMOLAR_ONLY": {
                checkFail = nhiExtendTreatmentProcedure.getTreatmentProcedure().getTeeth().stream()
                    .anyMatch(tooth -> !positionLimitMap.get(limitType).contains(tooth.getPosition().concat(",")));
                break;
            }
            case "BLANK_ONLY": {
                checkFail = nhiExtendTreatmentProcedure.getTreatmentProcedure().getTeeth().size() > 0;
                break;
            }
            case "SPECIFIC_TOOTH_ONLY":
            case "SPECIFIC_AREA_ONLY": {
                if (positionLimit.length < 2) {
                    break;
                }

                String specificPositionLimit = positionLimit[1];
                specificPosition = specificPositionLimit;
                checkFail = nhiExtendTreatmentProcedure.getTreatmentProcedure().getTeeth().stream()
                    .anyMatch(tooth -> !specificPositionLimit.contains(tooth.getPosition().concat(",")));
                break;
            }
            default:
                break;
        }

        if (checkFail) {
            nhiExtendTreatmentProcedure.setCheck(nhiExtendTreatmentProcedure.getCheck() + code + " " + positionLimitErrorResponseMap.get(limitType) + " " + specificPosition + "\n");
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

    public BiConsumer<NhiExtendTreatmentProcedure, PersonalNhiExtendTreatmentProcedureMap> checkOtherToothDeclarationConflict = ((targetNhiExtendTreatmentProcedure, personalNhiExtendTreatmentProcedureMap) -> {
        NhiExtendDisposal targetNhiExtendDisposal = targetNhiExtendTreatmentProcedure.getTreatmentProcedure()
            .getDisposal()
            .getNhiExtendDisposals().iterator().next();
        LocalDate targetDate = targetNhiExtendDisposal.getReplenishmentDate() == null
            ? targetNhiExtendDisposal.getDate()
            : targetNhiExtendDisposal.getReplenishmentDate();
        String targetCode = targetNhiExtendTreatmentProcedure.getA73();
        List<String> targetTeeth = splitToothFromA74(targetNhiExtendTreatmentProcedure.getA74()).collect(Collectors.toList());
        if (rules.containsKey(targetCode) && rules.get(targetCode).getOtherToothDeclarationInterval() != null) {
            Arrays.stream(rules.get(targetCode).getOtherToothDeclarationInterval())
                .map(OtherToothDeclarationInterval::new)
                .forEach(r ->
                    r.getCode().stream()
                        .filter(personalNhiExtendTreatmentProcedureMap::containPersonalNhiExtendTreatmentProcedure)
                        .forEach(ruleCode ->
                            personalNhiExtendTreatmentProcedureMap.getPersonalNhiExtendTreatmentProcedure(ruleCode)
                                .getDeclarationDateAndTooth()
                                .entrySet()
                                .stream()
                                .filter(entry -> {
                                    LocalDate d = entry.getKey().plusDays(r.getInterval());
                                    return d.isAfter(targetDate) || d.isEqual(targetDate);
                                })
                                .forEach(entry -> {
                                    List<String> treatedTeeth = entry.getValue();
                                    targetTeeth.stream()
                                        .filter(targetTooth ->
                                            (isPermanentTooth(targetTooth) && r.getType().equals(OtherToothDeclaratioinType.PT)) ||
                                                (isDeciduousTooth(targetTooth) && r.getType().equals(OtherToothDeclaratioinType.DT))
                                        )
                                        .filter(targetTooth -> treatedTeeth.stream().anyMatch(targetTooth::equals))
                                        .forEach(targetTooth ->
                                            targetNhiExtendTreatmentProcedure.setCheck(targetNhiExtendTreatmentProcedure.getCheck() +
                                                    r.getInterval() +
                                                    " 天內 " +
                                                    targetTooth +
                                                    " 牙位已申報 " +
                                                    ruleCode +
                                                    " ，不得再申報此項。上次： " +
                                                    formatter.format(entry.getKey()) +
                                                    " ( " +
                                                    ChronoUnit.DAYS.between(entry.getKey(), targetDate) +
                                                    " 天前 )\n"
                                                )
                                        );
                                })
                        )
                );
        }
    });

    public BiConsumer<NhiExtendTreatmentProcedure, PersonalNhiExtendTreatmentProcedureMap> checkOtherCodeDeclarationConflict = ((targetNhiExtendTreatmentProcedure, personalNhiExtendTreatmentProcedureMap) -> {
        NhiExtendDisposal targetNhiExtendDisposal = targetNhiExtendTreatmentProcedure.getTreatmentProcedure()
            .getDisposal()
            .getNhiExtendDisposals().iterator().next();
        LocalDate targetDate = targetNhiExtendDisposal.getReplenishmentDate() == null
            ? targetNhiExtendDisposal.getDate()
            : targetNhiExtendDisposal.getReplenishmentDate();
        String targetCode = targetNhiExtendTreatmentProcedure.getA73();
        if (rules.containsKey(targetCode) && rules.get(targetCode).getOtherCodeDeclarationInterval() != null) {
            Arrays.stream(rules.get(targetCode).getOtherCodeDeclarationInterval())
                .map(OtherCodeDeclarationInterval::new)
                .forEach(r ->
                    r.getCode()
                        .forEach(ruleCode -> {
                            if (personalNhiExtendTreatmentProcedureMap.containPersonalNhiExtendTreatmentProcedure(ruleCode)) {
                                if (r.getRange() == null) {
                                    Optional<LocalDate> optionalLocalDate = personalNhiExtendTreatmentProcedureMap.getPersonalNhiExtendTreatmentProcedure(ruleCode)
                                        .getSortedDeclarationDates()
                                        .filter(localDate -> {
                                            LocalDate d = localDate.plusDays(r.getInterval());
                                            return d.isAfter(targetDate) || d.isEqual(targetDate);
                                        })
                                        .findAny();
                                    if (optionalLocalDate.isPresent()) {
                                        LocalDate conflictDate = optionalLocalDate.get();

                                        targetNhiExtendTreatmentProcedure.setCheck(targetNhiExtendTreatmentProcedure.getCheck() +
                                            r.getInterval() +
                                            " 天內已申報 " +
                                            ruleCode +
                                            " 不得申報此項。上次申報時間：" +
                                            formatter.format(conflictDate) +
                                            "( " +
                                            ChronoUnit.DAYS.between(conflictDate, targetDate) +
                                            " 天 )\n");
                                    }
                                } else if (OtherCodeDeclarationIntervalRange.MONTH.equals(r.getRange())) {
                                    Optional<LocalDate> optionalLocalDate = personalNhiExtendTreatmentProcedureMap.getPersonalNhiExtendTreatmentProcedure(ruleCode)
                                        .getSortedDeclarationDates()
                                        .filter(localDate -> {
                                            LocalDate start = targetDate.withDayOfMonth(1);
                                            LocalDate end = targetDate.withDayOfMonth(targetDate.lengthOfMonth());
                                            return end.isAfter(localDate) && start.isBefore(localDate) || end.isEqual(localDate) || start.isEqual(localDate);
                                        })
                                        .findAny();
                                    if (optionalLocalDate.isPresent()) {
                                        LocalDate conflictDate = optionalLocalDate.get();

                                        targetNhiExtendTreatmentProcedure.setCheck(targetNhiExtendTreatmentProcedure.getCheck() +
                                            "當月內已申報 " +
                                            ruleCode +
                                            " 不得申報此項。上次申報時間：" +
                                            formatter.format(conflictDate) +
                                            "( " +
                                            ChronoUnit.DAYS.between(conflictDate, targetDate) +
                                            " 天 )\n");
                                    }
                                } else if (OtherCodeDeclarationIntervalRange.YEAR.equals(r.getRange())) {
                                    Optional<LocalDate> optionalLocalDate = personalNhiExtendTreatmentProcedureMap.getPersonalNhiExtendTreatmentProcedure(ruleCode)
                                        .getSortedDeclarationDates()
                                        .filter(localDate -> {
                                            LocalDate start = targetDate.withDayOfYear(1);
                                            LocalDate end = targetDate.withDayOfYear(targetDate.lengthOfYear());
                                            return end.isAfter(localDate) && start.isBefore(localDate) || end.isEqual(localDate) || start.isEqual(localDate);
                                        })
                                        .findAny();
                                    if (optionalLocalDate.isPresent()) {
                                        LocalDate conflictDate = optionalLocalDate.get();

                                        targetNhiExtendTreatmentProcedure.setCheck(targetNhiExtendTreatmentProcedure.getCheck() +
                                            "當年內已申報 " +
                                            ruleCode +
                                            " 不得申報此項。上次申報時間：" +
                                            formatter.format(conflictDate) +
                                            "( " +
                                            ChronoUnit.DAYS.between(conflictDate, targetDate) +
                                            " 天 )\n");
                                    }
                                }
                            }
                        })
                );
        }
    });

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
                    check.append("不得與 ").append(exclude).append(" 同時申報\n");
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
            String identity = nhiExtendTreatmentProcedure.getTreatmentProcedure().getDisposal().getNhiExtendDisposals().iterator().next().getPatientIdentity();
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

    private List<NhiExtTxPDate> getSameCodeNhiExtendTreatmentProceduresExcludeSelf(
        List<NhiExtendDisposal> nhiExtendDisposals,
        NhiExtendTreatmentProcedure nhiExtendTreatmentProcedure
    ) {
        return nhiExtendDisposals
            .stream()
            .flatMap(nhiExtendDisposal -> StreamUtil.asStream(nhiExtendDisposal.getNhiExtendTreatmentProcedures()))
            // exclude self
            .filter(nhiExtTxP -> nhiExtTxP != nhiExtendTreatmentProcedure)
            .filter(nhiExtTxP -> nhiExtTxP.getA73().equals(nhiExtendTreatmentProcedure.getA73()))
            .map(NhiExtTxPDate::new)
            // order by date desc
            .sorted(Comparator.comparing(NhiExtTxPDate::getDate).reversed())
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

        LocalDate date = getNhiExtendDisposalDate(nhiExtendTreatmentProcedure);
        List<NhiExtTxPDate> nhiExtTxPDates = getSameCodeNhiExtendTreatmentProceduresExcludeSelf(
            nhiExtendDisposalRepository.findByDateBetweenAndPatientId(
                date.minusDays(days),
                date,
                nhiExtendTreatmentProcedure.getTreatmentProcedure().getTreatmentTask().getTreatmentPlan().getTreatment().getPatient().getId()
            ),
            nhiExtendTreatmentProcedure
        );

        if (conditions == null) {
            StringBuilder check = new StringBuilder(nhiExtendTreatmentProcedure.getCheck());
            if (nhiExtTxPDates.size() >= times) {
                LocalDate dateBefore = nhiExtTxPDates.get(0).getDate();
                String nhiExtTxPBefore = formatter.format(dateBefore) + "(" + DAYS.between(dateBefore, date) + " 天前)";
                if (times == 1) {
                    // {天數}內不得重複申報。上次：{日期}({天數} 天前)
                    check
                        .append(formatDays.apply(days))
                        .append("內不得重複申報。上次：")
                        .append(nhiExtTxPBefore)
                        .append("\n");
                } else {
                    // {天數}內不得申報超過 {次數} 次。上次：{日期}({天數} 天前)
                    check
                        .append(formatDays.apply(days))
                        .append("內不得申報超過 ")
                        .append(times)
                        .append(" 次。上次：")
                        .append(nhiExtTxPBefore)
                        .append("\n");
                }
            }

            nhiExtendTreatmentProcedure.setCheck(check.toString());
        } else {
            Arrays.stream(conditions).skip(1).forEach(condition ->
                checkCondition(
                    condition,
                    nhiExtTxPDates.stream().map(NhiExtTxPDate::getNhiExtendTreatmentProcedure).collect(Collectors.toList()),
                    nhiExtendTreatmentProcedure
                )
            );
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

        LocalDate date = getNhiExtendDisposalDate(nhiExtendTreatmentProcedure);
        List<NhiExtTxPDate> nhiExtTxPDates = getSameCodeNhiExtendTreatmentProceduresExcludeSelf(
            nhiExtendDisposalRepository.findByDateBetweenAndPatientId(
                date.with(TemporalAdjusters.firstDayOfMonth()),
                date,
                nhiExtendTreatmentProcedure.getTreatmentProcedure().getTreatmentTask().getTreatmentPlan().getTreatment().getPatient().getId()
            ),
            nhiExtendTreatmentProcedure
        );

        if (conditions == null) {
            StringBuilder check = new StringBuilder(nhiExtendTreatmentProcedure.getCheck());
            if (nhiExtTxPDates.size() >= times) {
                LocalDate dateBefore = nhiExtTxPDates.get(0).getDate();
                String nhiExtTxPBefore = formatter.format(dateBefore) + "(" + DAYS.between(dateBefore, date) + " 天前)";
                if (times == 1) {
                    // 同一月份內不得重複申報。上次：{日期}({天數} 天前)
                    check
                        .append("同一月份內不得重複申報。上次：")
                        .append(nhiExtTxPBefore)
                        .append("\n");
                } else {
                    // 同一月份內不得申報超過 {次數} 次。上次：{日期}({天數} 天前)
                    check
                        .append("同一月份內不得申報超過 ")
                        .append(times)
                        .append(" 次。上次：")
                        .append(nhiExtTxPBefore)
                        .append("\n");
                }
            }

            nhiExtendTreatmentProcedure.setCheck(check.toString());
        } else {
            Arrays.stream(conditions).skip(1).forEach(condition ->
                checkCondition(
                    condition,
                    nhiExtTxPDates.stream().map(NhiExtTxPDate::getNhiExtendTreatmentProcedure).collect(Collectors.toList()),
                    nhiExtendTreatmentProcedure
                )
            );
        }
    }

    private void checkIntervalYear(String interval, NhiExtendTreatmentProcedure nhiExtendTreatmentProcedure) {
        String[] info = interval.split("YYYYx");
        int times = Integer.parseInt(info[1]);

        LocalDate date = getNhiExtendDisposalDate(nhiExtendTreatmentProcedure);
        List<NhiExtTxPDate> nhiExtTxPDates = getSameCodeNhiExtendTreatmentProceduresExcludeSelf(
            nhiExtendDisposalRepository.findByDateBetweenAndPatientId(
                date.with(TemporalAdjusters.firstDayOfYear()),
                date,
                nhiExtendTreatmentProcedure.getTreatmentProcedure().getTreatmentTask().getTreatmentPlan().getTreatment().getPatient().getId()
            ),
            nhiExtendTreatmentProcedure
        );

        StringBuilder check = new StringBuilder(nhiExtendTreatmentProcedure.getCheck());
        if (nhiExtTxPDates.size() >= times) {
            LocalDate dateBefore = nhiExtTxPDates.get(0).getDate();
            String nhiExtTxPBefore = formatter.format(dateBefore) + "(" + DAYS.between(dateBefore, date) + " 天前)";
            if (times == 1) {
                // 同一年度內不得重複申報。上次：{日期}({天數} 天前)
                check
                    .append("同一年度內不得重複申報。上次：")
                    .append(nhiExtTxPBefore)
                    .append("\n");
            } else {
                // 同一年度內不得申報超過 {次數} 次。上次：{日期}({天數} 天前)
                check
                    .append("同一年度內不得申報超過 ")
                    .append(times)
                    .append(" 次。上次：")
                    .append(nhiExtTxPBefore)
                    .append("\n");
            }
        }

        nhiExtendTreatmentProcedure.setCheck(check.toString());
    }

    private void checkIntervalLifetime(String interval, NhiExtendTreatmentProcedure nhiExtendTreatmentProcedure) {
        String[] conditions = interval.split(";");

        long id = nhiExtendTreatmentProcedure.getTreatmentProcedure().getTreatmentTask().getTreatmentPlan().getTreatment().getPatient().getId();
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
            // 同象限不得重複申報 {次數} 次
            check.append("同象限不得重複申報 ").append(limit).append(" 次\n");
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
            // 同顆牙不得重複申報 {次數} 次
            check.append("同顆牙不得重複申報 ").append(limit).append(" 次\n");
        }

        nhiExtendTreatmentProcedure.setCheck(check.toString());
    }

    private void checkLifetimeTeeth(
        int limit,
        NhiExtendTreatmentProcedure nhiExtendTreatmentProcedure,
        Map<String, Integer> teethCount,
        Function<String, List<String>> getTeeth
    ) {
        long id = nhiExtendTreatmentProcedure.getTreatmentProcedure().getTreatmentTask().getTreatmentPlan().getTreatment().getPatient().getId();
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
                        // 同顆牙不得重複申報 {次數} 次
                        check.append("同顆牙不得重複申報 ").append(limit).append(" 次\n");
                    }
                });
                nhiExtendTreatmentProcedure.setCheck(check.toString());
            }
        });
    }

    private void checkSameHospital(List<NhiExtendTreatmentProcedure> nhiExtTxPs, int limit, NhiExtendTreatmentProcedure nhiExtendTreatmentProcedure) {
        long count = nhiExtTxPs
            .stream()
            .filter(nhiExtTxP -> nhiExtTxP.getTreatmentProcedure().getDisposal().getNhiExtendDisposals().iterator().next()
                .getA14().equals(nhiExtendTreatmentProcedure.getTreatmentProcedure().getDisposal().getNhiExtendDisposals().iterator().next().getA14())
            )
            .count();

        StringBuilder check = new StringBuilder(nhiExtendTreatmentProcedure.getCheck());
        if (count >= limit) {
            // 同一院所不得重複申報 {次數} 次
            check.append("同一院所不得重複申報 ").append(limit).append(" 次\n");
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

    private Function<Integer, String> formatDays = days -> {
        String format;
        switch (days) {
            case 1095:
                format = "三年";
                break;
            case 730:
                format = "兩年";
                break;
            case 365:
                format = "一年";
                break;
            case 545:
                format = "一年半";
                break;
            case 180:
                format = "半年";
                break;
            case 60:
                format = "六十天";
                break;
            case 90:
                format = "九十天";
                break;
            case 30:
                format = "三十天";
                break;
            case 7:
                format = "七天";
                break;
            case 360:
                format = "三百六十天";
                break;
            default:
                format = days + " 天";
                break;
        }

        return format;
    };

    private static LocalDate getNhiExtendDisposalDate(NhiExtendTreatmentProcedure nhiExtendTreatmentProcedure) {
        NhiExtendDisposal nhiExtendDisposal = nhiExtendTreatmentProcedure.getTreatmentProcedure().getDisposal().getNhiExtendDisposals().iterator().next();
        if (nhiExtendDisposal.getReplenishmentDate() != null) {
            return nhiExtendDisposal.getReplenishmentDate();
        }

        return nhiExtendDisposal.getDate();
    }

    // https://www.univocity.com/pages/java_beans.html#using-your-own-conversions-in-annotations
    public static class Splitter implements Conversion<String, String[]> {

        private String separator;

        public Splitter(String... args) {
            if (args.length == 0) {
                separator = ",";
            } else {
                separator = args[0];
            }
        }

        @Override
        public String[] execute(String input) {
            if (input == null) {
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

    private static class NhiExtTxPDate {

        private NhiExtendTreatmentProcedure nhiExtendTreatmentProcedure;

        private LocalDate date;

        public NhiExtTxPDate(NhiExtendTreatmentProcedure nhiExtendTreatmentProcedure) {
            this.nhiExtendTreatmentProcedure = nhiExtendTreatmentProcedure;
            date = getNhiExtendDisposalDate(nhiExtendTreatmentProcedure);
        }

        public NhiExtendTreatmentProcedure getNhiExtendTreatmentProcedure() {
            return nhiExtendTreatmentProcedure;
        }

        public LocalDate getDate() {
            return date;
        }
    }
}
