package io.dentall.totoro.business.service.nhi;

import io.dentall.totoro.business.vm.nhi.NhiStatisticDashboard;
import io.dentall.totoro.domain.User;
import io.dentall.totoro.repository.NhiExtendDisposalRepository;
import io.dentall.totoro.repository.UserRepository;
import io.dentall.totoro.web.rest.vm.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.YearMonth;
import java.util.*;

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

    public List<NhiIndexOdVM> calculateOdIndex(Instant begin, Instant end) {
        return nhiExtendDisposalRepository.calculateOdIndex(begin, end);
    }

    public List<NhiIndexToothCleanVM> calculateToothCleanIndex(Instant begin, Instant end, List<Long> excludeDisposalId) {
        if (excludeDisposalId == null || excludeDisposalId.size() == 0) {
            excludeDisposalId = Arrays.asList(0L);
        }

        return nhiExtendDisposalRepository.calculateToothCleanIndex(begin, end, excludeDisposalId);
    }

    public List<NhiDoctorTxVM> calculateDoctorTx(Instant begin, Instant end) {
       return nhiExtendDisposalRepository.calculateDoctorNhiTx(begin, end);
    }

    public List<NhiDoctorExamVM> calculateDoctorNhiExam(Instant begin, Instant end) {
        return nhiExtendDisposalRepository.calculateDoctorNhiExam(begin, end);
    }

    public List<NhiIndexTreatmentProcedureVM> getNhiIndexTreatmentProcedures(Instant begin, Instant end) {
        return nhiExtendDisposalRepository.findNhiIndexTreatmentProcedures(begin, end);
    }
}
