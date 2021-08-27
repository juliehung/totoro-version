package io.dentall.totoro.business.service.nhi;

import io.dentall.totoro.business.service.nhi.util.NhiProcedureSalaryType;
import io.dentall.totoro.business.service.nhi.util.NhiProcedureUtil;
import io.dentall.totoro.business.service.nhi.util.ToothConstraint;
import io.dentall.totoro.business.service.nhi.util.ToothUtil;
import io.dentall.totoro.business.vm.nhi.NhiMetricBaseVM;
import io.dentall.totoro.business.vm.nhi.NhiNorthQuickPassMetricVM;
import io.dentall.totoro.business.vm.nhi.NhiStatisticDashboard;
import io.dentall.totoro.config.TimeConfig;
import io.dentall.totoro.domain.User;
import io.dentall.totoro.repository.NhiExtendDisposalRepository;
import io.dentall.totoro.repository.UserRepository;
import io.dentall.totoro.service.dto.CalculateBaseData;
import io.dentall.totoro.service.dto.NhiIndexEndoDTO;
import io.dentall.totoro.service.util.DateTimeUtil;
import io.dentall.totoro.web.rest.vm.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.ejb.Timeout;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static io.dentall.totoro.business.service.nhi.util.ToothUtil.getToothCount;

@Service
@Transactional
public class NhiStatisticService {
    private final List<String> odCodes = Arrays.asList(
        "89001C",
        "89002C",
        "89003C",
        "89004C",
        "89005C",
        "89008C",
        "89009C",
        "89010C",
        "89011C",
        "89012C"
    );

    private final NhiExtendDisposalRepository nhiExtendDisposalRepository;

    private final UserRepository userRepository;

    public NhiStatisticService(
        NhiExtendDisposalRepository nhiExtendDisposalRepository,
        UserRepository userRepository
    ) {
        this.nhiExtendDisposalRepository = nhiExtendDisposalRepository;
        this.userRepository = userRepository;
    }

    public List<NhiStatisticDashboard> calculate(YearMonth ym) {

        Map<String, Long> docMap = new HashMap<>();
        NhiStatisticDashboard summaryDashboard = new NhiStatisticDashboard();
        Map<String, NhiStatisticDashboard> docDashboardMap = new HashMap<>();
        docDashboardMap.put("nhi_statistic_summary", summaryDashboard);

        nhiExtendDisposalRepository.findSp(
            ym.atDay(1),
            ym.atEndOfMonth()
        ).forEach(spDTO -> {
                String docLogin = spDTO.getCreatedBy();

                NhiStatisticDashboard dashboard = null;
                if (!docMap.containsKey(docLogin)) {
                    Optional<User> doc = userRepository.findOneByLogin(docLogin);
                    if (doc.isPresent()) {
                        long docId = doc.get().getId();
                        dashboard = new NhiStatisticDashboard().doctorId(docId);
                        docMap.put(docLogin, docId);
                        docDashboardMap.put(docLogin, dashboard);
                    }
                } else {
                    dashboard = docDashboardMap.get(docLogin);
                }

                String specificCode = dashboard.getCircleMap()
                    .containsKey(spDTO.getSpecificCode())
                    ? spDTO.getSpecificCode()
                    : "other";
                int points = spDTO.getPoint();

                summaryDashboard.getSummaryCircle().incrementCase().incrementPoints(points);
                summaryDashboard.getCircleMap().get(specificCode).incrementCase().incrementPoints(points);
                dashboard.getSummaryCircle().incrementCase().incrementPoints(points);
                dashboard.getCircleMap().get(specificCode).incrementCase().incrementPoints(points);

                summaryDashboard.incrementTotalCases();
                dashboard.incrementTotalCases();
                switch (specificCode) {
                    case "P1" :
                    case "P5" :
                        dashboard.incrementEndoCases();
                        summaryDashboard.incrementEndoCases();
                        break;
                    case "P2" :
                    case "P3" :
                        dashboard.incrementGvCases();
                        summaryDashboard.incrementGvCases();
                        break;
                    case "P4" :
                    case "P8" :
                        dashboard.incrementPeriCases();
                        summaryDashboard.incrementPeriCases();
                        break;
                    case "P6" :
                    case "P7" :
                    case "other" :
                    default :
                        dashboard.incrementOtherCases();
                        summaryDashboard.incrementOtherCases();
                        break;
                }
            });

        docDashboardMap.forEach((k, v) -> v.calculateRatio());

        return new ArrayList<>(docDashboardMap.values());
    }

    public List<NhiIndexOdVM> calculateOdIndex(Instant begin, Instant end, List<Long> excludeDisposalId) {
        if (excludeDisposalId == null || excludeDisposalId.size() == 0) {
            excludeDisposalId = Arrays.asList(0L);
        }

        return nhiExtendDisposalRepository.calculateOdIndex(begin, end, excludeDisposalId);
    }

    public List<NhiIndexToothCleanVM> calculateToothCleanIndex(Instant begin, Instant end, List<Long> excludeDisposalId) {
        if (excludeDisposalId == null || excludeDisposalId.size() == 0) {
            excludeDisposalId = Arrays.asList(0L);
        }

        return nhiExtendDisposalRepository.calculateToothCleanIndex(begin, end, excludeDisposalId);
    }

    public List<NhiIndexEndoVM> calculateEndoIndex(Instant begin, Instant end, List<Long> excludeDisposalIds) {
        if (excludeDisposalIds == null || excludeDisposalIds.size() == 0) {
            excludeDisposalIds = Arrays.asList(0L);
        }

        List<NhiIndexEndoDTO> rawData = nhiExtendDisposalRepository.findEndoIndexRawData(begin, end, NhiProcedureSalaryType.ENDO.getCodeListAtSalary(), excludeDisposalIds);

        Map<Long, NhiIndexEndoVM> mapResult = rawData.stream()
                .reduce(new HashMap<Long, NhiIndexEndoVM>(), (map, dto) -> {
                    map.compute(dto.getDid(), (did, vm) -> {
                        if (vm == null) {
                            vm = new NhiIndexEndoVM();
                            vm.setDid(did);
                            vm.setPreOperationNumber(0L);
                            vm.setPostOperationNumber(0L);
                        }

                        String a73 = dto.getA73();
                        long toothCount = getToothCount(ToothConstraint.SPECIFIC_TOOTH, dto.getA74());

                        if ("90015C".equals(a73)) {
                            vm.setPreOperationNumber(toothCount + vm.getPreOperationNumber());
                        } else {
                            vm.setPostOperationNumber(toothCount + vm.getPostOperationNumber());
                        }
                        return vm;
                    });

                    return map;
                }, (accMap, map) -> {
                    accMap.putAll(map); // 這邊不會執行到
                    return accMap;
                });

        Collection<NhiIndexEndoVM> result = mapResult.values();

        result.stream().forEach(vm -> {
            BigDecimal preOpeNumb = BigDecimal.valueOf(vm.getPreOperationNumber());
            BigDecimal postOpeNumb = BigDecimal.valueOf(vm.getPostOperationNumber());
            if (!preOpeNumb.equals(BigDecimal.ZERO)) {
                vm.completedRate(postOpeNumb.divide(preOpeNumb, 2, RoundingMode.HALF_UP));
            }
        });

        return new ArrayList<>(result);
    }

    public List<NhiDoctorTxVM> calculateDoctorTx(Instant begin, Instant end, List<Long> excludeDisposalId) {
        if (excludeDisposalId == null || excludeDisposalId.size() == 0) {
            excludeDisposalId = Arrays.asList(0L);
        }

        return nhiExtendDisposalRepository.calculateDoctorNhiTx(begin, end, excludeDisposalId);
    }

    public List<NhiDoctorExamVM> calculateDoctorNhiExam(Instant begin, Instant end, List<Long> excludeDisposalId) {
        if (excludeDisposalId == null || excludeDisposalId.size() == 0) {
            excludeDisposalId = Arrays.asList(0L);
        }

        return nhiExtendDisposalRepository.calculateDoctorNhiExam(begin, end, excludeDisposalId);
    }

    public List<NhiIndexTreatmentProcedureVM> getNhiIndexTreatmentProcedures(Instant begin, Instant end, List<Long> excludeDisposalId) {
        if (excludeDisposalId == null || excludeDisposalId.size() == 0) {
            excludeDisposalId = Arrays.asList(0L);
        }

        return nhiExtendDisposalRepository.findNhiIndexTreatmentProcedures(begin, end, excludeDisposalId);
    }

    public Collection<NhiStatisticDoctorSalary> getDoctorSalaryPresentByDisposalDate(LocalDate begin, LocalDate end,
            List<Long> excludeDisposalId) {
        Map<Instant, NhiStatisticDoctorSalary> m = new HashMap<>();

        List<Long> disposalList = new ArrayList<>();
        List<Long> multipleExaminationDisposalList = new ArrayList<>();

        if (excludeDisposalId == null || excludeDisposalId.size() == 0) {
            excludeDisposalId = Arrays.asList(0L);
        }

        // 這邊特別將時間轉成台北時區後，再進行Group By，因為沒有這樣處理的話，有可能會出現小於begin或大於end的日期
        // 雖然特別將時區轉成台北時區後，可是因為NhiStatisticDoctorSalary.disposalDate為Instant型態，所以只好特別從localDateTime轉回Instant，但要時區要指定成UTC，避免時間又被減掉8小時
        nhiExtendDisposalRepository.findCalculateBaseDataByDate(begin, end, excludeDisposalId).stream()
                .filter(e -> e != null && e.getDisposalDate() != null)
                .collect(Collectors
                        .groupingBy(obj -> obj.getDisposalDate().atZone(ZoneId.of("Asia/Taipei")).toLocalDateTime().truncatedTo(ChronoUnit.DAYS).toInstant(ZoneOffset.UTC)))
                .forEach((k, v) -> {
                    v.forEach(e -> {
                        m.compute(k, (kk, o) -> {
                            if (o == null) {
                                o = new NhiStatisticDoctorSalary();
                                o.setDisposalDate(k);
                            }

                            this.disposalCounting(o, e, disposalList);
                            this.txCounting(o, e, multipleExaminationDisposalList);

                            return o;
                        });
                    });
                });

        return m.values();
    }

    private void examCounting(NhiStatisticDoctorSalary o, CalculateBaseData e) {
        if (NhiProcedureSalaryType.INFECTION_EXAMINATION_CODE.getCodeListAtSalary().contains(e.getExaminationCode())) {
            o.setInfectionExaminationPoint(
                o.getInfectionExaminationPoint() != null
                    ? o.getInfectionExaminationPoint() + e.getExaminationPoint()
                    : e.getExaminationPoint()
            );
        } else {
            o.setRegularExaminationPoint(
                o.getRegularExaminationPoint() != null
                    ? o.getRegularExaminationPoint() + e.getExaminationPoint()
                    : e.getExaminationPoint()
            );
        }

    }

    private void txCounting(NhiStatisticDoctorSalary o, CalculateBaseData e, List<Long> multipleExaminationDisposalList) {

        if (NhiProcedureUtil.isPeriodAtSalary(e.getTxCode())) {
            // dentall 定義之薪水類行為牙周
            o.setPerioPoint(
                o.getPerioPoint() != null
                    ? o.getPerioPoint() + e.getTxPoint().longValue()
                    : e.getTxPoint().longValue()
            );
        } else if (NhiProcedureUtil.isEndoAtSalary(e.getTxCode())) {
            // dentall 定義之薪水類行為根管
            o.setEndoPoint(
                o.getEndoPoint() != null
                    ? o.getEndoPoint() + e.getTxPoint().longValue()
                    : e.getTxPoint().longValue()
            );
        }

        if (!NhiProcedureUtil.isExaminationCodeAtSalary(e.getTxCode())) {
            // 診療 費用累計
            o.setTreatmentPoint(
                o.getTreatmentPoint() != null
                    ? o.getTreatmentPoint() + e.getTxPoint().longValue()
                    : e.getTxPoint().longValue()
            );

            NhiSpecialCode specificCode = e.getSpecificCode() != null
                ? e.getSpecificCode()
                : NhiSpecialCode.OTHER;

            switch (specificCode) {
                case P1:
                    o.setCaseNumberOfSpecialTreatmentP1(
                        o.getCaseNumberOfSpecialTreatmentP1().add(BigDecimal.ONE)
                    );
                    o.setPointsOfSpecialTreatmentP1(
                        o.getPointsOfSpecialTreatmentP1().add(new BigDecimal(e.getTxPoint()))
                    );
                    break;
                case P2:
                    o.setCaseNumberOfSpecialTreatmentP2(
                        o.getCaseNumberOfSpecialTreatmentP2().add(BigDecimal.ONE)
                    );
                    o.setPointsOfSpecialTreatmentP2(
                        o.getPointsOfSpecialTreatmentP2().add(new BigDecimal(e.getTxPoint()))
                    );
                    break;
                case P3:
                    o.setCaseNumberOfSpecialTreatmentP3(
                        o.getCaseNumberOfSpecialTreatmentP3().add(BigDecimal.ONE)
                    );
                    o.setPointsOfSpecialTreatmentP3(
                        o.getPointsOfSpecialTreatmentP3().add(new BigDecimal(e.getTxPoint()))
                    );
                    break;
                case P4:
                    o.setCaseNumberOfSpecialTreatmentP4(
                        o.getCaseNumberOfSpecialTreatmentP4().add(BigDecimal.ONE)
                    );
                    o.setPointsOfSpecialTreatmentP4(
                        o.getPointsOfSpecialTreatmentP4().add(new BigDecimal(e.getTxPoint()))
                    );
                    break;
                case P5:
                    o.setCaseNumberOfSpecialTreatmentP5(
                        o.getCaseNumberOfSpecialTreatmentP5().add(BigDecimal.ONE)
                    );
                    o.setPointsOfSpecialTreatmentP5(
                        o.getPointsOfSpecialTreatmentP5().add(new BigDecimal(e.getTxPoint()))
                    );
                    break;
                case P6:
                    o.setCaseNumberOfSpecialTreatmentP6(
                        o.getCaseNumberOfSpecialTreatmentP6().add(BigDecimal.ONE)
                    );
                    o.setPointsOfSpecialTreatmentP6(
                        o.getPointsOfSpecialTreatmentP6().add(new BigDecimal(e.getTxPoint()))
                    );
                    break;
                case P7:
                    o.setCaseNumberOfSpecialTreatmentP7(
                        o.getCaseNumberOfSpecialTreatmentP7().add(BigDecimal.ONE)
                    );
                    o.setPointsOfSpecialTreatmentP7(
                        o.getPointsOfSpecialTreatmentP7().add(new BigDecimal(e.getTxPoint()))
                    );
                    break;
                case P8:
                    o.setCaseNumberOfSpecialTreatmentP8(
                        o.getCaseNumberOfSpecialTreatmentP8().add(BigDecimal.ONE)
                    );
                    o.setPointsOfSpecialTreatmentP8(
                        o.getPointsOfSpecialTreatmentP8().add(new BigDecimal(e.getTxPoint()))
                    );
                    break;
                default:
                    o.setCaseNumberOfSpecialTreatmentNone(
                        o.getCaseNumberOfSpecialTreatmentNone().add(BigDecimal.ONE)
                    );
                    o.setPointsOfSpecialTreatmentNone(
                        o.getPointsOfSpecialTreatmentNone().add(new BigDecimal(e.getTxPoint()))
                    );
                    break;
            }
        } else {
            // 多筆 診察 費用扣一次重複的點數
            if (!multipleExaminationDisposalList.contains(e.getDisposalId())) {
                if (NhiProcedureUtil.isInfectionExaminationCodeAtSalary(e.getTxCode())) {
                    o.setInfectionExaminationPoint(o.getInfectionExaminationPoint() - e.getExaminationPoint());
                } else {
                    o.setRegularExaminationPoint(o.getRegularExaminationPoint() - e.getExaminationPoint());
                }

                if (o.getTotal() != null) {
                    o.setTotal(o.getTotal() - e.getExaminationPoint());
                }

                multipleExaminationDisposalList.add(e.getDisposalId());
            }

            // 診察 費用累計
            this.examCounting(o, e);
        }

        o.setTotal(
            o.getTotal() != null
                ? o.getTotal() + e.getTxPoint().longValue()
                : e.getExaminationPoint()
        );
    }

    private void disposalCounting(NhiStatisticDoctorSalary o, CalculateBaseData e, List<Long> countedList) {
        if (!countedList.contains(e.getDisposalId())) {
            o.setTotal(
                o.getTotal() != null
                    ? o.getTotal() + e.getExaminationPoint() + Long.parseLong(e.getCopayment())
                    : e.getExaminationPoint() + Long.parseLong(e.getCopayment())
            );

            this.examCounting(o, e);

            o.setCopayment(
                o.getCopayment() != null
                    ? o.getCopayment() + Long.parseLong(e.getCopayment())
                    : Long.parseLong(e.getCopayment())
            );

            o.setPatientId(e.getPatientId());
            o.setPatientName(e.getPatientName());
            o.setDisposalDate(e.getDisposalDate());
            o.setVipPatient(e.getVipPatient());

            countedList.add(e.getDisposalId());

            o.setTotalDisposal(
                o.getTotalDisposal() != null
                    ? o.getTotalDisposal() + 1L
                    : 1L
            );

        }
    }

    public Map<Long, NhiStatisticDoctorSalary> getSalary(
        LocalDate begin,
        LocalDate end,
        Long doctorId,
        List<Long> excludeDisposalId
    ) {
        Map<Long, NhiStatisticDoctorSalary> m = new HashMap<>();

        ArrayList<Long> disposalList = new ArrayList<>();
        List<Long> multipleExaminationDisposalList = new ArrayList<>();

        if (excludeDisposalId == null || excludeDisposalId.size() == 0) {
            excludeDisposalId = Arrays.asList(0L);
        }

        if (doctorId == null) {
            nhiExtendDisposalRepository.findCalculateBaseDataByDate(begin, end, excludeDisposalId).stream()
                .filter(e -> e != null && e.getDoctorId() != null)
                .collect(Collectors.groupingBy(CalculateBaseData::getDoctorId))
                .forEach((k, v) -> {
                    List<Long> doctorPatientList = new ArrayList<>();
                    List<LocalDate> doctorWorkdayList = new ArrayList<>();
                    v.forEach(e -> {
                        m.compute(k, (kk, o) -> {
                            if (o == null) {
                                o = new NhiStatisticDoctorSalary();
                            }

                            this.disposalCounting(o, e, disposalList);
                            this.txCounting(o, e, multipleExaminationDisposalList);

                            if (NhiCategory.AA.getValue().equals(e.getNhiCategory()) ||
                                NhiCategory.AB.getValue().equals(e.getNhiCategory())
                            ) {
                                o.setSumSameTreatmentCases(
                                    o.getSumSameTreatmentCases() != null && !BigDecimal.ZERO.equals(o.getSumSameTreatmentCases())
                                        ? o.getSumSameTreatmentCases().add(BigDecimal.ONE)
                                        : BigDecimal.ONE
                                );
                            }

                            if (!doctorPatientList.contains(e.getPatientId())) {
                                o.setSumVisitPatients(
                                    o.getSumVisitPatients().add(BigDecimal.ONE)
                                );
                                doctorPatientList.add(e.getPatientId());
                            }

                            LocalDate txLocalDate = e.getDisposalDate().atZone(TimeConfig.ZONE_OFF_SET).toLocalDate();
                            if (!doctorWorkdayList.contains(txLocalDate)) {
                                o.setSumWorkingDays(
                                    o.getSumWorkingDays().add(BigDecimal.ONE)
                                );
                                doctorWorkdayList.add(txLocalDate);
                            }

                            return o;
                        });
                    });
                });
        } else {
            nhiExtendDisposalRepository.findCalculateBaseDataByDateAndDoctorId(begin, end, doctorId, excludeDisposalId).stream()
                .filter(e -> e != null && e.getDisposalId() != null)
                .collect(Collectors.groupingBy(CalculateBaseData::getDisposalId))
                .forEach((k, v) -> {
                    List<Long> doctorPatientList = new ArrayList<>();
                    List<LocalDate> doctorWorkdayList = new ArrayList<>();
                    v.forEach(e -> {
                        m.compute(k, (kk, o) -> {
                            if (o == null) {
                                o = new NhiStatisticDoctorSalary();
                            }

                            this.disposalCounting(o, e, disposalList);
                            this.txCounting(o, e, multipleExaminationDisposalList);

                            if (NhiCategory.AA.getValue().equals(e.getNhiCategory()) ||
                                NhiCategory.AB.getValue().equals(e.getNhiCategory())
                            ) {
                                o.setSumSameTreatmentCases(
                                    o.getSumSameTreatmentCases() != null && !BigDecimal.ZERO.equals(o.getSumSameTreatmentCases())
                                        ? o.getSumSameTreatmentCases().add(BigDecimal.ONE)
                                        : BigDecimal.ONE
                                );
                            }

                            if (!doctorPatientList.contains(e.getPatientId())) {
                                o.setSumVisitPatients(
                                    o.getSumVisitPatients().add(BigDecimal.ONE)
                                );
                                doctorPatientList.add(e.getPatientId());
                            }

                            LocalDate txLocalDate = e.getDisposalDate().atZone(TimeConfig.ZONE_OFF_SET).toLocalDate();
                            if (!doctorWorkdayList.contains(txLocalDate)) {
                                o.setSumWorkingDays(
                                    o.getSumWorkingDays().add(BigDecimal.ONE)
                                );
                                doctorWorkdayList.add(txLocalDate);
                            }

                            return o;
                        });
                    });
                });
        }

        m.forEach((k, v) -> {
            v.setSumApplyPoints(
                new BigDecimal(v.getTotal() - v.getCopayment())
            );

            v.setSumExaminationPoints(
                new BigDecimal(v.getRegularExaminationPoint() + v.getInfectionExaminationPoint())
            );

            v.setPointsOfP1P5(
                v.getPointsOfSpecialTreatmentP1().add(
                    v.getPointsOfSpecialTreatmentP5()
                )
            );

            v.setCaseNumberOfP1P5(
                v.getCaseNumberOfSpecialTreatmentP1().add(
                    v.getCaseNumberOfSpecialTreatmentP5()
                )
            );

            v.setPointsOfP2P3(
                v.getPointsOfSpecialTreatmentP2().add(
                    v.getPointsOfSpecialTreatmentP3()
                )
            );

            v.setCaseNumberOfP2P3(
                v.getCaseNumberOfSpecialTreatmentP2().add(
                    v.getCaseNumberOfSpecialTreatmentP3()
                )
            );

            v.setPointsOfP4P8(
                v.getPointsOfSpecialTreatmentP4().add(
                    v.getPointsOfSpecialTreatmentP8()
                )
            );

            v.setCaseNumberOfP4P8(
                v.getCaseNumberOfSpecialTreatmentP4().add(
                    v.getCaseNumberOfSpecialTreatmentP8()
                )
            );

            v.setSumPointsOfSpecialTreatments(
                v.getPointsOfP1P5().add(
                    v.getPointsOfP2P3().add(
                        v.getPointsOfP4P8().add(
                            v.getPointsOfSpecialTreatmentP6().add(
                                v.getPointsOfSpecialTreatmentP7().add(
                                    v.getPointsOfSpecialTreatmentNone()
                                )
                            )
                        )
                    )
                )
            );

            v.setSumCaseNumberOfSpecialTreatments(
                v.getCaseNumberOfP1P5().add(
                    v.getCaseNumberOfP2P3().add(
                        v.getCaseNumberOfP4P8().add(
                            v.getCaseNumberOfSpecialTreatmentP6().add(
                                v.getCaseNumberOfSpecialTreatmentP7().add(
                                    v.getCaseNumberOfSpecialTreatmentNone()
                                )
                            )
                        )
                    )
                )
            );

            if (v.getTotalDisposal() != null && v.getTotalDisposal()  > 0L) {
                BigDecimal bdTotalDisposal = new BigDecimal(v.getTotalDisposal());
                v.setAvgApplyPointPerCase(
                    new BigDecimal((v.getTotal() - v.getCopayment())).divide(
                        bdTotalDisposal, 2, BigDecimal.ROUND_HALF_UP
                    )
                );

                v.setAvgTotalPointPerCase(
                    new BigDecimal(v.getTotal()).divide(
                        bdTotalDisposal, 2, BigDecimal.ROUND_HALF_UP
                    )
                );

            }

            if (v.getSumVisitPatients() != null && !v.getSumVisitPatients().equals(BigDecimal.ZERO)) {
                v.setAvgApplyPointPerPatient(
                    new BigDecimal(v.getTotal() - v.getCopayment()).divide(
                        v.getSumVisitPatients(), 2, BigDecimal.ROUND_HALF_UP
                    )
                );

                v.setAvgTotalPointPerPatient(
                    new BigDecimal(v.getTotal()).divide(
                        v.getSumVisitPatients(), 2, BigDecimal.ROUND_HALF_UP
                    )
                );

                v.setAvgCasePerPatient(
                    new BigDecimal(v.getTotalDisposal()).divide(
                        v.getSumVisitPatients(), 2, BigDecimal.ROUND_HALF_UP
                    )
                );
            }

            if (v.getSumWorkingDays() != null && !v.getSumWorkingDays().equals(BigDecimal.ZERO)) {
                v.setAvgCasePerDay(
                    new BigDecimal(v.getTotalDisposal()).divide(
                        v.getSumWorkingDays(), 2, BigDecimal.ROUND_HALF_UP
                    )
                );

                v.setAvgApplyPointPerDay(
                    new BigDecimal(v.getTotal() - v.getCopayment()).divide(
                        v.getSumWorkingDays(), 2, BigDecimal.ROUND_HALF_UP
                    )
                );
            }

            if (v.getTotal() != null && v.getTotal() != 0L) {
                v.setPercentageOfExaminationPointOfTotalPoint(
                    v.getSumExaminationPoints().divide(
                        new BigDecimal(v.getTotal()), 2, BigDecimal.ROUND_HALF_UP
                    )
                );

                v.setPercentageOfTreatmentPointOfTotalPoint(
                    new BigDecimal(v.getTreatmentPoint()).divide(
                        new BigDecimal(v.getTotal()), 2, BigDecimal.ROUND_HALF_UP
                    )
                );

                v.setPercentageOfCopaymentOfTotalPoint(
                    new BigDecimal(v.getCopayment()).divide(
                        new BigDecimal(v.getTotal()), 2, BigDecimal.ROUND_HALF_UP
                    )
                );
            }


            if (v.getSumPointsOfSpecialTreatments() != null &&
                !v.getSumPointsOfSpecialTreatments().equals(BigDecimal.ZERO) &&
                v.getSumCaseNumberOfSpecialTreatments() != null &&
                !v.getSumCaseNumberOfSpecialTreatments().equals(BigDecimal.ZERO)
            ) {
                Arrays.stream(NhiSpecialCode.values()).forEach(pxCode ->{
                    try {
                        BigDecimal pxPoints = (BigDecimal) NhiStatisticDoctorSalary.class
                            .getMethod(
                                "getPointsOfSpecialTreatment".concat(pxCode.getValue())
                            )
                            .invoke(v);
                        BigDecimal pxCases = (BigDecimal) NhiStatisticDoctorSalary.class
                            .getMethod(
                                "getCaseNumberOfSpecialTreatment".concat(pxCode.getValue())
                            )
                            .invoke(v);

                        NhiStatisticDoctorSalary.class
                            .getMethod(
                                "setPercentageOfPointsOfSpecialTreatment".concat(pxCode.getValue()),
                                BigDecimal.class
                            )
                            .invoke(v, pxPoints.divide(v.getSumPointsOfSpecialTreatments(), 2, BigDecimal.ROUND_HALF_UP));
                        NhiStatisticDoctorSalary.class
                            .getMethod(
                                "setPercentageOfCaseNumberOfSpecialTreatment".concat(pxCode.getValue()),
                                BigDecimal.class
                            )
                            .invoke(v, pxCases.divide(v.getSumCaseNumberOfSpecialTreatments(), 2, BigDecimal.ROUND_HALF_UP));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

                v.setPercentageOfCaseNumberOfP1P5(v.getCaseNumberOfP1P5().divide(v.getSumCaseNumberOfSpecialTreatments(), 2, BigDecimal.ROUND_HALF_UP));
                v.setPercentageOfPointsOfP1P5(v.getPointsOfP1P5().divide(v.getSumPointsOfSpecialTreatments(), 2, BigDecimal.ROUND_HALF_UP));

                v.setPercentageOfCaseNumberOfP2P3(v.getCaseNumberOfP2P3().divide(v.getSumCaseNumberOfSpecialTreatments(), 2, BigDecimal.ROUND_HALF_UP));
                v.setPercentageOfPointsOfP2P3(v.getPointsOfP2P3().divide(v.getSumPointsOfSpecialTreatments(), 2, BigDecimal.ROUND_HALF_UP));

                v.setPercentageOfCaseNumberOfP4P8(v.getCaseNumberOfP4P8().divide(v.getSumCaseNumberOfSpecialTreatments(), 2, BigDecimal.ROUND_HALF_UP));
                v.setPercentageOfPointsOfP4P8(v.getPointsOfP4P8().divide(v.getSumPointsOfSpecialTreatments(), 2, BigDecimal.ROUND_HALF_UP));
            }

        });

        return m;
    }

    /**
     * 北區快速通關指標
     */
    public NhiNorthQuickPassMetricVM getNorthQuickPassMetrics(
        Instant begin,
        Instant end,
        List<Long> excludeDisposalIds
    ) {
        NhiNorthQuickPassMetricVM result = new NhiNorthQuickPassMetricVM();

        if (excludeDisposalIds == null) {
            excludeDisposalIds = new ArrayList<>();
            excludeDisposalIds.add(0L);
        }

        if (excludeDisposalIds.size() == 0) {
            excludeDisposalIds.add(0L);
        }

        List<NhiMetricBaseVM> metricBaseResult = nhiExtendDisposalRepository.findMetricBases(
            begin,
            end,
            excludeDisposalIds
        );

        // a7
        List<NhiMetricBaseVM> a7Resource = metricBaseResult.stream()
            .filter(this.excludeNhiCategory1416)
            .filter(this.excludePerio1Codes)
            .filter(this.excludePerio2Codes)
            .filter(this.onlyDeclareCase)
            .collect(Collectors.toList());

        Map<Long, List<NhiMetricBaseVM>> a7ResourceGroupByDisposalId = a7Resource.stream()
            .collect(Collectors.groupingBy(NhiMetricBaseVM::getDisposalId));
        Set<String> a7ResourceNumberOfICCard = new HashSet<>();
        a7Resource.forEach(base -> {
            a7ResourceNumberOfICCard.add(
                base.getPatientId().toString()
                    .concat("_")
                    .concat(base.getCardNumber())
            );
        });
        BigDecimal a7CaseNumber = a7ResourceNumberOfICCard != null && a7ResourceNumberOfICCard.size() > 0
            ? new BigDecimal(a7ResourceNumberOfICCard.size())
            : BigDecimal.ZERO;

        BigDecimal a7TotalPoint = this.summaryTotalDisposalPoint(a7ResourceGroupByDisposalId);

        if (!BigDecimal.ZERO.equals(a7CaseNumber)) {
            result.setA7(
                a7TotalPoint.divide(
                    a7CaseNumber,
                    2,
                    BigDecimal.ROUND_HALF_UP
                )
            );
        }

        // a8
        Instant a8Begin = end
            .atOffset(TimeConfig.ZONE_OFF_SET)
            .with(LocalTime.MIN)
            .withYear(end.atOffset(TimeConfig.ZONE_OFF_SET).getYear())
            .withMonth(end.atOffset(TimeConfig.ZONE_OFF_SET).getMonthValue())
            .withDayOfMonth(end.atOffset(TimeConfig.ZONE_OFF_SET).getDayOfMonth())
            .minus(DateTimeUtil.NHI_12_MONTH)
            .toInstant();

        List<NhiMetricBaseVM> a8Resource = nhiExtendDisposalRepository.findMetricBases(
            a8Begin,
            end,
            excludeDisposalIds
        );

        BigDecimal a8Numerator = new BigDecimal(
            a8Resource.stream()
            .filter(this.onlyA8numerator)
            .count()
        );

        BigDecimal a8Denominator = new BigDecimal(
            a8Resource.stream()
                .filter(this.onlyA8Denominator)
                .count()
        );

        if (!BigDecimal.ZERO.equals(a8Denominator)) {
            result.setA8(
                a8Numerator.divide(
                    a8Denominator,
                    2,
                    BigDecimal.ROUND_HALF_UP
                )
                .subtract(
                    BigDecimal.ONE
                )
                .negate()
            );
        }

        // a9
        List<NhiMetricBaseVM> a9Numerator = metricBaseResult.stream()
            .filter(this.excludeNhiCategory1416)
            .filter(this.onlyODCode)
            .collect(Collectors.toList());
        List<NhiMetricBaseVM> a9Denominator = metricBaseResult.stream()
            .filter(this.excludeNhiCategory1416)
            .collect(Collectors.toList());


        Map<Long, List<NhiMetricBaseVM>> a9DenominatorByDisposalId = a9Denominator.stream()
            .collect(Collectors.groupingBy(NhiMetricBaseVM::getDisposalId));
        BigDecimal a9DenominatorByIdPointSummary = this.summaryTotalDisposalPoint(a9DenominatorByDisposalId);

        if (!BigDecimal.ZERO.equals(a9DenominatorByIdPointSummary)) {
            result.setA9(
                this.summaryTotalTreatmentPoint(
                    a9Numerator
                ).divide(
                    a9DenominatorByIdPointSummary,
                    2,
                    BigDecimal.ROUND_HALF_UP
                )
            );
        }

        // a10
        result.setA10(
            this.summaryApplyDisposalPointWithSameExamPoint(
                metricBaseResult.stream()
                    .filter(this.excludeNhiCategory1416)
                    .filter(this.excludeNhiCategoryA3)
                    .filter(this.exclude91014C)
                    .filter(this.excludePerio1Codes)
                    .filter(this.excludePerio2Codes)
                    .filter(this.excludeSpecificCodeG9)
                    .filter(this.excludeSpecificCodeJA)
                    .collect(Collectors.groupingBy(NhiMetricBaseVM::getDisposalId))
            )
        );

        // a14, CAUTION: 以起始時間的月份作為，切換成季的標準。
        Map<Long, List<String>> patientPermanentToothMap = new HashMap<>();
        Map<Long, List<String>> patientDeciduousToothMap = new HashMap<>();
        // Key will be patient_tooth
        Set<String> patientDuplicatedPermanentToothSet = new HashSet();
        Set<String> patientDuplicatedDeciduousToothSet = new HashSet();

        DateTimeUtil.BeginEnd quarterBeginEnd =
            DateTimeUtil.getCurrentQuarterMonthsRangeInstant(
                begin
            );

        nhiExtendDisposalRepository.findMetricBases(
            quarterBeginEnd.getBegin(),
            quarterBeginEnd.getEnd(),
            excludeDisposalIds,
            this.odCodes
        ).stream()
            .filter(this.excludeNhiCategory1416)
            .filter(this.excludeSpecificCodeG9)
            .forEach(base -> {
                ToothUtil.splitA74(base.getTreatmentProcedureTooth())
                    .forEach(tooth -> {
                        if (ToothUtil.validatedToothConstraint(ToothConstraint.PERMANENT_TOOTH, tooth)) {
                            this.recordDuplicatedPatientTooth(
                                patientPermanentToothMap,
                                patientDuplicatedPermanentToothSet,
                                base.getPatientId(),
                                tooth
                            );
                        } else {
                            this.recordDuplicatedPatientTooth(
                                patientDeciduousToothMap,
                                patientDuplicatedDeciduousToothSet,
                                base.getPatientId(),
                                tooth
                            );
                        }
                    });
            });

        nhiExtendDisposalRepository.findMetricBases(
            quarterBeginEnd.getBegin().minus(DateTimeUtil.NHI_24_MONTH),
            quarterBeginEnd.getEnd().minus(DateTimeUtil.NHI_24_MONTH),
            excludeDisposalIds,
            patientPermanentToothMap.keySet().size() != 0
                ? new ArrayList<>(patientPermanentToothMap.keySet())
                : Arrays.asList(0L),
            this.odCodes
        ).stream()
            .filter(this.excludeNhiCategory1416)
            .filter(this.excludeNhiCategoryA3)
            .filter(this.excludeNhiCategoryB6B7)
            .filter(this.excludeSpecificCodeG9)
            .filter(this.excludeSpecificCodeJA)
            .filter(this.excludeSpecificCodeJB)
            .forEach(base -> {
                Long patientId = base.getPatientId();
                ToothUtil.splitA74(base.getTreatmentProcedureTooth())
                    .forEach(tooth -> {
                        if (ToothUtil.validatedToothConstraint(ToothConstraint.PERMANENT_TOOTH, tooth) &&
                            patientPermanentToothMap.containsKey(patientId) &&
                            patientPermanentToothMap.get(patientId).contains(tooth)
                        ) {
                            patientDuplicatedPermanentToothSet.add(
                                this.generatePatientDuplicateToothKey(
                                    patientId,
                                    tooth
                                )
                            );
                        }
                    });
            });

        result.setA14(
            this.calculateReODRatio(
                patientPermanentToothMap,
                patientDuplicatedPermanentToothSet
            )
        );

        // a15
        Set<String> pastDeciduousToothList = new HashSet<>();
        nhiExtendDisposalRepository.findMetricBases(
            quarterBeginEnd.getBegin().minus(DateTimeUtil.NHI_18_MONTH),
            quarterBeginEnd.getEnd().minus(DateTimeUtil.NHI_18_MONTH),
            excludeDisposalIds,
            patientDeciduousToothMap.keySet().size() != 0
                ? new ArrayList<>(patientDeciduousToothMap.keySet())
                : Arrays.asList(0L),
            this.odCodes
        ).stream()
            .filter(this.excludeNhiCategory1416)
            .filter(this.excludeSpecificCodeG9)
            .forEach(base -> {
                Long patientId = base.getPatientId();
                ToothUtil.splitA74(base.getTreatmentProcedureTooth())
                    .forEach(tooth -> {
                        if (!ToothUtil.validatedToothConstraint(ToothConstraint.PERMANENT_TOOTH, tooth) &&
                            !patientDeciduousToothMap.containsKey(patientId) ||
                            !ToothUtil.validatedToothConstraint(ToothConstraint.PERMANENT_TOOTH, tooth) &&
                            patientDeciduousToothMap.containsKey(patientId) &&
                            !patientDeciduousToothMap.get(patientId).contains(tooth)
                        ) {
                            pastDeciduousToothList.add(
                                this.generatePatientDuplicateToothKey(
                                    patientId,
                                    tooth
                                )
                            );
                        }

                        if (!ToothUtil.validatedToothConstraint(ToothConstraint.PERMANENT_TOOTH, tooth) &&
                            patientDeciduousToothMap.containsKey(patientId) &&
                            patientDeciduousToothMap.get(patientId).contains(tooth)
                        ) {
                            patientDuplicatedDeciduousToothSet.add(
                                this.generatePatientDuplicateToothKey(
                                    patientId,
                                    tooth
                                )
                            );
                        }
                    });
            });

        result.setA15_1(
            this.calculateReODRatio(
                patientDeciduousToothMap,
                patientDuplicatedDeciduousToothSet
            )
        );

        result.setA15_2(
            new BigDecimal(
                pastDeciduousToothList.size()
            ).add(
                this.calculateReODNumbers(patientDeciduousToothMap)
            )
        );

        // a17
        BigDecimal a17CaseNumber = BigDecimal.ZERO;
        BigDecimal a17PatientElderThan50 = BigDecimal.ZERO;

        a17CaseNumber = a17CaseNumber.add(
            new BigDecimal(
                metricBaseResult.stream()
                    .filter(base -> "89013C".equals(base.getTreatmentProcedureCode()))
                    .collect(Collectors.groupingBy(NhiMetricBaseVM::getDisposalId))
                    .keySet()
                    .size()
            )
        );

        a17PatientElderThan50 = a17PatientElderThan50.add(
            new BigDecimal(
                metricBaseResult.stream()
                    .filter(base -> "89013C".equals(base.getTreatmentProcedureCode()))
                    .filter(base -> 50 > Period.between(
                        base.getPatientBirth(),
                        "1".equals(base.getCardReplenishment())
                            ? base.getDisposalDate()
                            : base.getCardReplenishmentDisposalDate()
                        ).getYears()
                    )
                    .collect(Collectors.groupingBy(NhiMetricBaseVM::getDisposalId))
                    .keySet()
                    .size()
            )
        );

        List<NhiMetricBaseVM> a17PastResult = nhiExtendDisposalRepository.findMetricBases(
            begin.minus(DateTimeUtil.NHI_2_MONTH),
            end.minus(DateTimeUtil.NHI_2_MONTH),
            excludeDisposalIds,
            Arrays.asList("89013C")
        );

        a17CaseNumber = a17CaseNumber.add(
            new BigDecimal(
                a17PastResult.stream()
                    .filter(base -> "89013C".equals(base.getTreatmentProcedureCode()))
                    .collect(Collectors.groupingBy(NhiMetricBaseVM::getDisposalId))
                    .keySet()
                    .size()
            )
        );

        a17PatientElderThan50 = a17PatientElderThan50.add(
            new BigDecimal(
                a17PastResult.stream()
                    .filter(base -> "89013C".equals(base.getTreatmentProcedureCode()))
                    .filter(base -> 50 > Period.between(
                        base.getPatientBirth(),
                        "1".equals(base.getCardReplenishment())
                            ? base.getDisposalDate()
                            : base.getCardReplenishmentDisposalDate()
                        ).getYears()
                    )
                    .collect(Collectors.groupingBy(NhiMetricBaseVM::getDisposalId))
                    .keySet()
                    .size()
            )
        );

        result.setA17_1(a17CaseNumber);
        if (BigDecimal.ZERO.equals(a17CaseNumber)) {
            result.setA17_2(
                a17PatientElderThan50.divide(
                    a17CaseNumber,
                    2,
                    BigDecimal.ROUND_HALF_UP
                )
            );
        }

        // a18
        BigDecimal a18b1 = BigDecimal.ZERO;
        BigDecimal a18b2 = BigDecimal.ZERO;

        for (NhiMetricBaseVM base
            : metricBaseResult.stream()
                .collect(Collectors.toList())
        ) {
            if ("91018C".equals(base.getTreatmentProcedureCode())) {
                a18b1 = a18b1.add(BigDecimal.ONE);
            }
            if ("91023C".equals(base.getTreatmentProcedureCode())) {
                a18b2 = a18b2.add(BigDecimal.ONE);
            }
        }

        result.setA18_1(a18b1);
        result.setA18_2(a18b2);

        return result;
    }


    private BigDecimal calculateReODNumbers(
        Map<Long, List<String>> patientToothMap
    ) {
        BigDecimal result = BigDecimal.ZERO;

        for (Long patientId : patientToothMap.keySet()) {
            result = result.add(
                new BigDecimal(
                    patientToothMap.get(patientId).size()
                )
            );
        }

        return result;
    }

    private BigDecimal calculateReODRatio(
        Map<Long, List<String>> patientToothMap,
        Set<String> patientDuplicatedTooth
    ) {
        BigDecimal result = BigDecimal.ZERO;
        BigDecimal denominator = this.calculateReODNumbers(patientToothMap);

        if (!BigDecimal.ZERO.equals(denominator)) {
            result = new BigDecimal(
                patientDuplicatedTooth.size()
            ).divide(
                denominator,
                2,
                BigDecimal.ROUND_HALF_UP
            );
        }

        return result;
    }

    private void recordDuplicatedPatientTooth(
        Map<Long, List<String>> patientTeethMap,
        Set<String> patientDuplicatedToothSet,
        Long patientId,
        String tooth
    ) {
        if (!patientTeethMap.containsKey(patientId)) {
            patientTeethMap.put(patientId, new ArrayList<>(Arrays.asList(tooth)));
        } else {
            if (!patientTeethMap.get(patientId)
                .contains(tooth)
            ) {
                patientTeethMap.get(patientId).add(tooth);
            } else {
                patientDuplicatedToothSet.add(
                    this.generatePatientDuplicateToothKey(
                        patientId,
                        tooth
                    )
                );
            }
        }
    }

    private String generatePatientDuplicateToothKey(Long patientId, String tooth) {
        return patientId.toString().concat("_").concat(tooth);
    }

    private BigDecimal summaryTotalTreatmentPoint(List<NhiMetricBaseVM> bs) {
        BigDecimal result = BigDecimal.ZERO;
        for (NhiMetricBaseVM b : bs) {
            result = result.add(
                new BigDecimal(
                    b.getTreatmentProcedureTotal()
                )
            );
        }

        return result;
    }

    /**
     * 診察點數 + 診療項目點數 + 部分負擔
     */
    private BigDecimal summaryTotalDisposalPoint(Map<Long, List<NhiMetricBaseVM>> m) {
        BigDecimal result = BigDecimal.ZERO;
        for (List<NhiMetricBaseVM> tps : m.values()) {
            boolean duplicatedDisposalId = false;
            for (NhiMetricBaseVM tp : tps) {
                if (!duplicatedDisposalId) {
                    result = result.add(new BigDecimal(tp.getExamPoint()))
                        .add(new BigDecimal(tp.getPartialBurden()))
                    ;
                    duplicatedDisposalId = true;
                }
                result = result.add(new BigDecimal(tp.getTreatmentProcedureTotal()));
            }
        }

        return result;
    }

    /**
     * 診察代碼都以 00121C 為計算點數 + 診療項目點數
     */
    private BigDecimal summaryApplyDisposalPointWithSameExamPoint(Map<Long, List<NhiMetricBaseVM>> m) {
        BigDecimal result = BigDecimal.ZERO;
        for (List<NhiMetricBaseVM> tps : m.values()) {
            boolean duplicatedDisposalId = false;
            for (NhiMetricBaseVM tp : tps) {
                if (!duplicatedDisposalId &&
                    StringUtils.isNotBlank(tp.getExamCode())
                ) {
                    result = result.add(new BigDecimal(230));
                    duplicatedDisposalId = true;
                }
                result = result.add(new BigDecimal(tp.getTreatmentProcedureTotal()));
            }
        }

        return result;
    }

    private Predicate<NhiMetricBaseVM> excludeNhiCategory1416 = (base) -> {
        return !"14".equals(base.getNhiCategory()) &&
            !"16".equals(base.getNhiCategory());
    };

    private Predicate<NhiMetricBaseVM> excludeNhiCategoryA3 = (base) -> {
        return !"A3".equals(base.getNhiCategory());
    };

    private Predicate<NhiMetricBaseVM> excludeNhiCategoryB6B7 = (base) -> {
        return !"B6".equals(base.getNhiCategory()) &&
            !"B7".equals(base.getNhiCategory());
    };

    private Predicate<NhiMetricBaseVM> excludeNhiCategoryAB = (base) -> {
        return !"AB".equals(base.getNhiCategory());
    };

    private Predicate<NhiMetricBaseVM> excludePerio1Codes = (base) -> {
        List<String> excludeCodes = Arrays.asList(
            "P4001C",
            "P4002C",
            "P4003C",
            "91021C",
            "91022C",
            "91023C"
        );

        return !excludeCodes.contains(base.getTreatmentProcedureCode());
    };

    private Predicate<NhiMetricBaseVM> excludePerio2Codes = (base) -> {
        List<String> excludeCodes = Arrays.asList(
            "91015C",
            "91016C",
            "91018C"
        );

        return !excludeCodes.contains(base.getTreatmentProcedureCode());
    };

    private Predicate<NhiMetricBaseVM> excludeExam1Codes = (base) -> {
        List<String> excludeCodes = Arrays.asList(
            "00121C",
            "00122C",
            "00123C",
            "00124C",
            "00125C",
            "00126C",
            "00128C",
            "00129C",
            "00130C",
            "00133C",
            "00134C",
            "00301C",
            "00302C",
            "00303C",
            "00304C"
        );

        return !excludeCodes.contains(base.getTreatmentProcedureCode());
    };

    private Predicate<NhiMetricBaseVM> excludeExam2Codes = (base) -> {
        List<String> excludeCodes = Arrays.asList(
            "01271C",
            "01272C",
            "01273C"
        );

        return !excludeCodes.contains(base.getTreatmentProcedureCode());
    };

    private Predicate<NhiMetricBaseVM> exclude91014C = (base) -> {
        return "91014C".equals(base.getTreatmentProcedureCode());
    };

    private Predicate<NhiMetricBaseVM> onlyDeclareCase = (base) -> {
        return StringUtils.isNotBlank(base.getCardNumber());
    };

    private Predicate<NhiMetricBaseVM> onlyA8numerator = (base) -> {
        List<String> onlyCodes = Arrays.asList(
            "90001C",
            "90002C",
            "90003C",
            "90016C",
            "90018C",
            "90019C",
            "90020C"
        );

        return onlyCodes.contains(base.getTreatmentProcedureCode());
    };

    private Predicate<NhiMetricBaseVM> onlyA8Denominator = (base) -> {
        return "90015C".equals(base.getTreatmentProcedureCode());
    };

    private Predicate<NhiMetricBaseVM> onlyODCode = (base) -> {
        return this.odCodes.contains(base.getTreatmentProcedureCode());
    };

    private Predicate<NhiMetricBaseVM> excludeSpecificCodeG9 = (base) -> {
        return !"G9".equals(base.getTreatmentProcedureSpecificCode());
    };

    private Predicate<NhiMetricBaseVM> excludeSpecificCodeJA = (base) -> {
        return !"JA".equals(base.getTreatmentProcedureSpecificCode());
    };

    private Predicate<NhiMetricBaseVM> excludeSpecificCodeJB = (base) -> {
        return !"JB".equals(base.getTreatmentProcedureSpecificCode());
    };

}
