package io.dentall.totoro.business.service.report;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dentall.totoro.TotoroApp;
import io.dentall.totoro.aop.ReportAspect;
import io.dentall.totoro.business.service.ImageGcsBusinessService;
import io.dentall.totoro.business.service.report.treatment.DailyNhiReportSetting;
import io.dentall.totoro.business.service.report.treatment.TreatmentBookSetting;
import io.dentall.totoro.repository.ReportRecordRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.time.LocalDate;

import static io.dentall.totoro.business.service.report.context.ReportCategory.FOLLOWUP;
import static io.dentall.totoro.business.service.report.context.ReportCategory.TREATMENT;
import static io.dentall.totoro.domain.enumeration.BatchStatus.LOCK;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TotoroApp.class)
@AutoConfigureMockMvc
@WithMockUser(username = "admin", password = "admin")
public class ReportAspectTest {

    @SpyBean
    private ReportAspect reportAspect;

    @MockBean
    private ReportRecordRepository reportRecordRepository;

    @MockBean
    private ImageGcsBusinessService imageGcsBusinessService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testTreatmentReportRunningCheck() throws Exception {
        when(reportRecordRepository.countByCategoryAndStatus(TREATMENT, LOCK)).thenReturn(1);

        DailyNhiReportSetting setting = new DailyNhiReportSetting();
        setting.setBeginDate(LocalDate.now().minusMonths(3));
        setting.setEndDate(LocalDate.now());
        TreatmentBookSetting bookSetting = new TreatmentBookSetting();
        bookSetting.setDailyNhiReportSetting(setting);

        MockHttpServletRequestBuilder requestBuilder = post("/api/report/treatment")
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsBytes(setting));

        ResultActions resultActions = mockMvc.perform(requestBuilder);
        Assertions.assertThat(resultActions.andReturn().getResponse().getContentAsString()).isEqualTo("TREATMENT current export amount is 1, maximum export limit is 1");
    }

    @Test
    public void testFollowupReportRunningCheck() throws Exception {
        when(reportRecordRepository.countByCategoryAndStatus(FOLLOWUP, LOCK)).thenReturn(1);

        DailyNhiReportSetting setting = new DailyNhiReportSetting();
        setting.setBeginDate(LocalDate.now().minusMonths(3));
        setting.setEndDate(LocalDate.now());
        TreatmentBookSetting bookSetting = new TreatmentBookSetting();
        bookSetting.setDailyNhiReportSetting(setting);

        MockHttpServletRequestBuilder requestBuilder = post("/api/report/followup")
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsBytes(setting));

        ResultActions resultActions = mockMvc.perform(requestBuilder);
        Assertions.assertThat(resultActions.andReturn().getResponse().getContentAsString()).isEqualTo("FOLLOWUP current export amount is 1, maximum export limit is 1");
    }
}
