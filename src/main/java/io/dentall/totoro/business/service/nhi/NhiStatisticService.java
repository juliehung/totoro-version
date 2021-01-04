package io.dentall.totoro.business.service.nhi;

import io.dentall.totoro.business.vm.nhi.NhiStatisticDashboard;
import io.dentall.totoro.domain.User;
import io.dentall.totoro.repository.NhiExtendDisposalRepository;
import io.dentall.totoro.repository.UserRepository;
import io.dentall.totoro.service.dto.CalculateBaseData;
import io.dentall.totoro.web.rest.vm.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class NhiStatisticService {
    private final NhiExtendDisposalRepository nhiExtendDisposalRepository;

    private final UserRepository userRepository;

    private final List<String> infectionExaminationCodes = Arrays.asList("00315C", "00316C", "00317C", "00307C", "00308C");

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

    private static final List<String> endoPostTreatmentList =
            Arrays.asList(
                    "90001C",
                    "90002C",
                    "90003C",
                    "90016C",
                    "90018C",
                    "90019C",
                    "90020C");

    public List<NhiIndexEndoVM> calculateEndoIndex(Instant begin, Instant end, List<Long> excludeDisposalId) {
        if (excludeDisposalId == null || excludeDisposalId.size() == 0) {
            excludeDisposalId = Arrays.asList(0L);
        }

        return nhiExtendDisposalRepository.calculateEndoIndex(begin, end, endoPostTreatmentList, excludeDisposalId).stream()
                .map(endoDto -> {
                    NhiIndexEndoVM endo = new NhiIndexEndoVM()
                        .did(endoDto.getDid())
                        .preOperationNumber(endoDto.getPreOperationNumber())
                        .postOperationNumber(endoDto.getPostOperationNumber());

                    BigDecimal preOpeNumb = BigDecimal.valueOf(endo.getPreOperationNumber());
                    BigDecimal postOpeNumb = BigDecimal.valueOf(endo.getPostOperationNumber());
                    return endo.uncompletedRate(preOpeNumb.subtract(postOpeNumb).divide(preOpeNumb, 2, RoundingMode.HALF_UP));
                })
                .collect(Collectors.toList());
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

    private void calculateSalary(NhiStatisticDoctorSalary o, CalculateBaseData e) {
        long total = 0L;
        int examPoint = e.getExaminationPoint() != null ? e.getExaminationPoint() : 0;
        long txPoint = e.getTxPoint() != null ? e.getTxPoint().longValue() : 0;
        total += examPoint;
        total += txPoint;

        // 感染或一般診察 並總和 診察 點數
        if (infectionExaminationCodes.contains(e.getExaminationCode())) {
            o.setInfectionExaminationPoint(Long.sum(o.getInfectionExaminationPoint(), examPoint));
        } else {
            o.setRegularExaminationPoint(Long.sum(o.getRegularExaminationPoint(), examPoint));
        }

        // 感染會存在於 treatment procedure，在上述以加總，在此應忽略不計
        if (!infectionExaminationCodes.contains(e.getTxCode())) {
            o.setTreatmentPoint(Long.sum(o.getTreatmentPoint(), txPoint));
        }

        // 區別計算 treamtent 各自專科別
        if (e.getSpecificCode() != null) {
            switch (e.getSpecificCode()) {
            case "P1":
            case "P5":
                o.setEndoPoint(Long.sum(o.getEndoPoint(), txPoint));
                break;
            case "P2":
            case "P3":
                o.setPedoPoint(Long.sum(o.getPedoPoint(), txPoint));
                break;
            case "P4":
            case "P8":
                o.setPerioPoint(Long.sum(o.getPerioPoint(), txPoint));
                break;
            case "P6":
            case "P7":
            case "other":
            default:
                break;
            }
        }

        // 總點數
        o.setTotal(Long.sum(o.getTotal(), total));
        // 總處置數
        o.setTotalDisposal(Long.sum(o.getTotalDisposal(), 1L));
        // 部分負擔
        o.setCopayment(Long.sum(o.getCopayment(), e.getCopayment() != null ? Long.parseLong(e.getCopayment()) : 0L));
    }

    public Collection<NhiStatisticDoctorSalary> getDoctorSalaryPresentByDisposalDate(LocalDate begin, LocalDate end,
            List<Long> excludeDisposalId) {
        Map<Instant, NhiStatisticDoctorSalary> m = new HashMap<>();

        if (excludeDisposalId == null || excludeDisposalId.size() == 0) {
            excludeDisposalId = Arrays.asList(0L);
        }

        // 這邊特別將時間轉成台北時區後，再進行Group By，因為沒有這樣處理的話，有可能會出現小於begin或大於end的日期
        // 雖然特別將時區轉成台北時區後，可是因為NhiStatisticDoctorSalary.disposalDate為Instant型態，所以只好特別從localDateTime轉回Instant，但要時區要指定成UTC，避免時間又被減掉8小時
        nhiExtendDisposalRepository.findCalculateBaseDataByDate(begin, end, excludeDisposalId, infectionExaminationCodes).stream()
                .collect(Collectors
                        .groupingBy(obj -> obj.getDisposalDate().atZone(ZoneId.of("Asia/Taipei")).toLocalDateTime().truncatedTo(ChronoUnit.DAYS).toInstant(ZoneOffset.UTC)))
                .forEach((k, v) -> {
                    v.forEach(e -> {
                        m.compute(k, (kk, o) -> {
                            if (o == null) {
                                o = new NhiStatisticDoctorSalary();
                                o.setDisposalDate(k);
                            }
                            calculateSalary(o, e);
                            return o;
                        });
                    });
                });

        return m.values();
    }

    public Map<Long, NhiStatisticDoctorSalary> getDoctorSalary(LocalDate begin, LocalDate end,
            List<Long> excludeDisposalId) {
        Map<Long, NhiStatisticDoctorSalary> m = new HashMap<>();
        ArrayList<Long> disposalList = new ArrayList<>();

        if (excludeDisposalId == null || excludeDisposalId.size() == 0) {
            excludeDisposalId = Arrays.asList(0L);
        }

        nhiExtendDisposalRepository.findCalculateBaseDataByDate(begin, end, excludeDisposalId, infectionExaminationCodes).stream()
            .collect(Collectors.groupingBy(CalculateBaseData::getDoctorId))
            .forEach((k, v) -> {
                v.forEach(e -> {
                    m.compute(k, (kk, o) -> {
                        long total = 0L;
                        int examPoint = e.getExaminationPoint() != null ? e.getExaminationPoint() : 0;
                        long txPoint = e.getTxPoint() != null ? e.getTxPoint().longValue() : 0;
                        total += examPoint;
                        total += txPoint;

                        if (o == null) {
                            o = new NhiStatisticDoctorSalary();
                        }

                        // 感染或一般診察 並總和 診察 點數
                        if (infectionExaminationCodes.contains(e.getExaminationCode())) {
                            o.setInfectionExaminationPoint(Long.sum(o.getInfectionExaminationPoint(), examPoint));
                        } else {
                            o.setRegularExaminationPoint(Long.sum(o.getRegularExaminationPoint(), examPoint));
                        }

                        // 感染會存在於 treatment procedure，在上述以加總，在此應忽略不計
                        if (!infectionExaminationCodes.contains(e.getTxCode())) {
                            o.setTreatmentPoint(Long.sum(o.getTreatmentPoint(), txPoint));
                        }

                        // 區別計算 treamtent 各自專科別
                        if (e.getSpecificCode() != null) {
                            switch (e.getSpecificCode()) {
                                case "P1":
                                case "P5":
                                    o.setEndoPoint(Long.sum(o.getEndoPoint(), txPoint));
                                    break;
                                case "P2":
                                case "P3":
                                    o.setPedoPoint(Long.sum(o.getPedoPoint(), txPoint));
                                    break;
                                case "P4":
                                case "P8":
                                    o.setPerioPoint(Long.sum(o.getPerioPoint(), txPoint));
                                    break;
                                case "P6":
                                case "P7":
                                case "other":
                                default:
                                    break;
                            }
                        }

                        // 總點數
                        o.setTotal(Long.sum(o.getTotal(), total));
                        // 總處置數
                        if (!disposalList.contains(e.getDisposalId())) {
                            o.setTotalDisposal(Long.sum(o.getTotalDisposal(), 1L));
                            disposalList.add(e.getDisposalId());
                        }
                        // 部分負擔
                        o.setCopayment(Long.sum(o.getCopayment(), e.getCopayment() != null ? Long.parseLong(e.getCopayment()) : 0L));

                        return o;
                    });
                });
            });

        return m;
    }

    public Map<Long, NhiStatisticDoctorSalary> getDoctorSalaryExpand(LocalDate begin, LocalDate end, Long doctorId,
            List<Long> excludeDisposalId) {
        Map<Long, NhiStatisticDoctorSalary> m = new HashMap<>();

        if (excludeDisposalId == null || excludeDisposalId.size() == 0) {
            excludeDisposalId = Arrays.asList(0L);
        }

        nhiExtendDisposalRepository.findCalculateBaseDataByDateAndDoctorId(begin, end, doctorId, excludeDisposalId, infectionExaminationCodes).stream()
            .collect(Collectors.groupingBy(CalculateBaseData::getDisposalId))
            .forEach((k, v) -> {
                v.forEach(e -> {
                    m.compute(k, (kk, o) -> {
                        long total = 0L;
                        int examPoint = e.getExaminationPoint() != null ? e.getExaminationPoint() : 0;
                        long txPoint = e.getTxPoint() != null ? e.getTxPoint().longValue() : 0;
                        total += examPoint;
                        total += txPoint;

                        if (o == null) {
                            o = new NhiStatisticDoctorSalary();
                        }

                        // 感染或一般診察 並總和 診察 點數
                        if (infectionExaminationCodes.contains(e.getExaminationCode())) {
                            o.setInfectionExaminationPoint(Long.sum(o.getInfectionExaminationPoint(), examPoint));
                        } else {
                            o.setRegularExaminationPoint(Long.sum(o.getRegularExaminationPoint(), examPoint));
                        }

                        // 感染會存在於 treatment procedure，在上述以加總，在此應忽略不計
                        if (!infectionExaminationCodes.contains(e.getTxCode())) {
                            o.setTreatmentPoint(Long.sum(o.getTreatmentPoint(), txPoint));
                        }

                        // 區別計算 treamtent 各自專科別
                        if (e.getSpecificCode() != null) {
                            switch (e.getSpecificCode()) {
                                case "P1":
                                case "P5":
                                    o.setEndoPoint(Long.sum(o.getEndoPoint(), txPoint));
                                    break;
                                case "P2":
                                case "P3":
                                    o.setPedoPoint(Long.sum(o.getPedoPoint(), txPoint));
                                    break;
                                case "P4":
                                case "P8":
                                    o.setPerioPoint(Long.sum(o.getPerioPoint(), txPoint));
                                    break;
                                case "P6":
                                case "P7":
                                case "other":
                                default:
                                    break;
                            }
                        }

                        // 總點數
                        o.setTotal(Long.sum(o.getTotal(), total));
                        // 總處置數
                        o.setTotalDisposal(Long.sum(o.getTotalDisposal(), 1L));
                        // 部分負擔
                        o.setCopayment(e.getCopayment() != null  &&
                                o.getCopayment() == 0L ||
                                o.getCopayment() == null
                            ? Long.parseLong(e.getCopayment())
                            : 0L);
                        // 治療時間
                        o.setDisposalDate(e.getDisposalDate());
                        // 病患資料
                        o.setPatientId(e.getPatientId());
                        o.setPatientName(e.getPatientName());

                        return o;
                    });
                });
            });

        return m;
    }

}
