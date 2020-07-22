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

import java.time.LocalDate;
import java.time.YearMonth;
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
        YearMonth ym = YearMonth.now(TimeConfig.ZONE_OFF_SET);
        String currentYm = ym.toString();
        Optional<NhiMonthDeclaration> o = nhiMonthDeclarationService.findByYM(currentYm);
        if (!o.isPresent()) {
            nhiMonthDeclarationService.save(new NhiMonthDeclaration().yearMonth(currentYm));
        }

        // 避免 `轉換下` 月底啟用伺服器，沒有自動產生當月月申報，可於次日被檢查並產生
        // Example，
        // 6/30 啟動機器，此時 hit initial delay 發現轉換有當月資料，則不會有任何動作，會預計於 24 小時後會檢查。
        // 7/1 若再啟動機器的時間點，未滿 24 小時，此段時間則會缺失 7 月份的清單，但會於達到 24 小時後自動新增。
        // 為避免此狀況，增加下列代碼
        String nextYm = ym.plusMonths(1L).toString();
        if (LocalDate.now(TimeConfig.ZONE_OFF_SET).isEqual(ym.atEndOfMonth()) &&
            !nhiMonthDeclarationService.findByYM(nextYm).isPresent()
        ) {
            nhiMonthDeclarationService.save(new NhiMonthDeclaration().yearMonth(nextYm));
        }
    }
}
