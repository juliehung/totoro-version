package io.dentall.totoro.business.service.nhi;

import io.dentall.totoro.business.vm.nhi.NhiStatisticDashboard;
import io.dentall.totoro.domain.NhiExtendDisposal;
import io.dentall.totoro.domain.NhiProcedure;
import io.dentall.totoro.domain.User;
import io.dentall.totoro.repository.NhiExtendDisposalRepository;
import io.dentall.totoro.repository.UserRepository;
import io.dentall.totoro.service.DisposalService;
import io.dentall.totoro.service.mapper.NhiExtendDisposalMapper;
import io.dentall.totoro.service.util.StreamUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class NhiStatisticService {
    private final NhiExtendDisposalRepository nhiExtendDisposalRepository;

    private final UserRepository userRepository;

    private final NhiExtendDisposalMapper nhiExtendDisposalMapper;

    private final DisposalService disposalService;

    public NhiStatisticService(
        NhiExtendDisposalRepository nhiExtendDisposalRepository,
        UserRepository userRepository,
        NhiExtendDisposalMapper nhiExtendDisposalMapper,
        DisposalService disposalService
    ) {
        this.nhiExtendDisposalRepository = nhiExtendDisposalRepository;
        this.userRepository = userRepository;
        this.nhiExtendDisposalMapper = nhiExtendDisposalMapper;
        this.disposalService = disposalService;
    }

    @Transactional
    public List<NhiStatisticDashboard> calculate(YearMonth ym) {
        List<NhiExtendDisposal> nhiExtendDisposals =
            nhiExtendDisposalRepository.findNhiExtendDisposalByDateBetweenAndReplenishmentDateIsNullOrReplenishmentDateBetweenAndA19Equals(
                ym.atDay(1),
                ym.atEndOfMonth(),
                ym.atDay(1),
                ym.atEndOfMonth(),
                "2"
            ).stream()
                .map(nhiExtendDisposalTable -> disposalService.getDisposalByProjection(nhiExtendDisposalTable.getDisposal_Id()).getNhiExtendDisposals().iterator().next())
                .collect(Collectors.toList());

        Map<String, Long> docMap = new HashMap<>();
        NhiStatisticDashboard summaryDashboard = new NhiStatisticDashboard();
        Map<String, NhiStatisticDashboard> docDashboardMap = new HashMap<>();
        docDashboardMap.put("nhi_statistic_summary", summaryDashboard);

        nhiExtendDisposals.stream()
            .flatMap(nhiExtendTreatmentDisposal -> StreamUtil.asStream(nhiExtendTreatmentDisposal.getNhiExtendTreatmentProcedures()))
            .forEach(nhiTx -> {
                String docLogin = nhiTx.getNhiExtendDisposal().getDisposal().getCreatedBy();

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

                NhiProcedure nhiProc = nhiTx.getTreatmentProcedure().getNhiProcedure();
                String specificCode = dashboard.getCircleMap().containsKey(nhiProc.getSpecificCode())?nhiProc.getSpecificCode():"other";
                int points = nhiProc.getPoint();

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
}
