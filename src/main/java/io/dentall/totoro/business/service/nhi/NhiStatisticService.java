package io.dentall.totoro.business.service.nhi;

import io.dentall.totoro.business.service.nhi.util.NhiProcedureSalaryType;
import io.dentall.totoro.business.service.nhi.util.NhiProcedureUtil;
import io.dentall.totoro.business.service.nhi.util.ToothConstraint;
import io.dentall.totoro.business.vm.nhi.NhiStatisticDashboard;
import io.dentall.totoro.domain.User;
import io.dentall.totoro.repository.NhiExtendDisposalRepository;
import io.dentall.totoro.repository.UserRepository;
import io.dentall.totoro.service.dto.CalculateBaseData;
import io.dentall.totoro.service.dto.NhiIndexEndoDTO;
import io.dentall.totoro.web.rest.vm.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import static io.dentall.totoro.business.service.nhi.util.ToothUtil.getToothCount;

@Service
@Transactional
public class NhiStatisticService {
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
                    ? o.getTotal() + e.getExaminationPoint()
                    : e.getExaminationPoint()
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
                    v.forEach(e -> {
                        m.compute(k, (kk, o) -> {
                            if (o == null) {
                                o = new NhiStatisticDoctorSalary();
                            }

                            this.disposalCounting(o, e, disposalList);
                            this.txCounting(o, e, multipleExaminationDisposalList);

                            return o;
                        });
                    });
                });
        } else {
            nhiExtendDisposalRepository.findCalculateBaseDataByDateAndDoctorId(begin, end, doctorId, excludeDisposalId).stream()
                .filter(e -> e != null && e.getDisposalId() != null)
                .collect(Collectors.groupingBy(CalculateBaseData::getDisposalId))
                .forEach((k, v) -> {
                    v.forEach(e -> {
                        m.compute(k, (kk, o) -> {
                            if (o == null) {
                                o = new NhiStatisticDoctorSalary();
                            }

                            this.disposalCounting(o, e, disposalList);
                            this.txCounting(o, e, multipleExaminationDisposalList);

                            return o;
                        });
                    });
                });
        }

        return m;
    }

}
