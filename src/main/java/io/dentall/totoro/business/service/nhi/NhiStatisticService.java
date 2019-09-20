package io.dentall.totoro.business.service.nhi;

import io.dentall.totoro.business.vm.nhi.NhiStatisticDashboard;
import io.dentall.totoro.domain.NhiExtendDisposal;
import io.dentall.totoro.domain.NhiProcedure;
import io.dentall.totoro.domain.User;
import io.dentall.totoro.repository.NhiExtendDisposalRepository;
import io.dentall.totoro.repository.UserRepository;
import io.dentall.totoro.service.util.StreamUtil;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.*;

@Service
public class NhiStatisticService {
    private NhiExtendDisposalRepository nhiExtendDisposalRepository;

    private UserRepository userRepository;

    public NhiStatisticService(
        NhiExtendDisposalRepository nhiExtendDisposalRepository,
        UserRepository userRepository
    ) {
        this.nhiExtendDisposalRepository = nhiExtendDisposalRepository;
        this.userRepository = userRepository;
    }

    public List<NhiStatisticDashboard> calculate(YearMonth ym) {
        List<NhiExtendDisposal> nhiExtendDisposals = nhiExtendDisposalRepository
            .findByDateBetweenAndUploadStatusNotNone(
                ym.atDay(1),
                ym.atEndOfMonth()
            );

        Map<String, Long> docMap = new HashMap<>();
        NhiStatisticDashboard summaryDashboard = new NhiStatisticDashboard();
        Map<String, NhiStatisticDashboard> docDashboardMap = new HashMap<>();
        docDashboardMap.put("nhi_statistic_summary", summaryDashboard);

        nhiExtendDisposals.stream()
            .flatMap(nhiExtendTreatmentDisposal -> StreamUtil.asStream(nhiExtendTreatmentDisposal.getNhiExtendTreatmentProcedures()))
            .forEach(nhiTx -> {
                String docLogin = nhiTx.getNhiExtendDisposal().getDisposal().getCreatedBy();
                if (!docMap.containsKey(docLogin)) {
                    Optional<User> doc = userRepository.findOneByLogin(docLogin);
                    if (doc.isPresent()) {
                        long docId = doc.get().getId();
                        docMap.put(docLogin, docId);
                        docDashboardMap.put(docLogin, new NhiStatisticDashboard().doctorId(docId));
                    }
                } else {
                    NhiProcedure nhiProc = nhiTx.getTreatmentProcedure().getNhiProcedure();
                    String specificCode = docDashboardMap.containsKey(nhiProc.getSpecificCode())?nhiProc.getSpecificCode():"other";
                    int points = nhiProc.getPoint();

                    NhiStatisticDashboard dashboard = docDashboardMap.get(docLogin);

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
                            break;
                        case "P2" :
                        case "P3" :
                            dashboard.incrementGvCases();
                            break;
                        case "P4" :
                        case "P8" :
                            dashboard.incrementPeriCases();
                            break;
                        case "P6" :
                        case "P7" :
                        case "other" :
                        default :
                            dashboard.incrementOtherCases();
                            break;
                    }
                }
            });

        return new ArrayList<>(docDashboardMap.values());
    }
}
