package io.dentall.totoro.service;

import io.dentall.totoro.TotoroApp;
import io.dentall.totoro.business.service.nhi.NhiStatisticService;
import io.dentall.totoro.repository.NhiExtendDisposalRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.YearMonth;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TotoroApp.class)
public class NhiStatisticServiceTest {

    @Autowired
    private NhiExtendDisposalRepository nhiExtendDisposalRepository;

    @Autowired
    private NhiStatisticService nhiStatisticService;

    @Test
    public void testStatistic() {
        YearMonth ym = YearMonth.now();
        nhiStatisticService.calculate(ym);
    }
}
