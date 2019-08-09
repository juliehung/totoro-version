package io.dentall.totoro.scheduler;

import io.dentall.totoro.config.TimeConfig;
import io.dentall.totoro.domain.NhiMonthDeclaration;
import io.dentall.totoro.service.NhiMonthDeclarationService;
import io.github.jhipster.config.JHipsterConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Profile("!" + JHipsterConstants.SPRING_PROFILE_TEST)
@Component
public class NhiMonthDeclarationTasks {

    private final Logger log = LoggerFactory.getLogger(NhiMonthDeclarationTasks.class);

    private final NhiMonthDeclarationService nhiMonthDeclarationService;

    public NhiMonthDeclarationTasks(NhiMonthDeclarationService nhiMonthDeclarationService) {
        this.nhiMonthDeclarationService = nhiMonthDeclarationService;
    }

    @Scheduled(initialDelayString = "${scheduler.initialDelay}", fixedDelay = 86400000) // one day
    public void createYearMonth() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        String ym = OffsetDateTime.now(TimeConfig.ZONE_OFF_SET).format(formatter);
        Optional o = nhiMonthDeclarationService.findByYM(ym);
        if (!o.isPresent()) {
            nhiMonthDeclarationService.save(new NhiMonthDeclaration().yearMonth(ym));
        }
    }
}
