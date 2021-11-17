package io.dentall.totoro.step_definitions;

import com.fasterxml.jackson.core.type.TypeReference;
import io.cucumber.java.Before;
import io.cucumber.java.DataTableType;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.dentall.totoro.business.service.ImageGcsBusinessService;
import io.dentall.totoro.domain.Ledger;
import io.dentall.totoro.domain.LedgerGroup;
import io.dentall.totoro.domain.LedgerReceipt;
import io.dentall.totoro.domain.LedgerReceiptPrintedRecord;
import io.dentall.totoro.domain.enumeration.LedgerReceiptRangeType;
import io.dentall.totoro.domain.enumeration.LedgerReceiptType;
import io.dentall.totoro.mapper.LedgerTestMapper;
import io.dentall.totoro.repository.*;
import io.dentall.totoro.service.*;
import io.dentall.totoro.service.util.DateTimeUtil;
import io.dentall.totoro.step_definitions.holders.LedgerTestInfoHolder;
import io.dentall.totoro.step_definitions.holders.PatientTestInfoHolder;
import io.dentall.totoro.step_definitions.holders.UserTestInfoHolder;
import io.dentall.totoro.web.rest.LedgerResource;
import io.dentall.totoro.web.rest.vm.LedgerReceiptCreateVM;
import io.dentall.totoro.web.rest.vm.LedgerReceiptVM;
import io.dentall.totoro.web.rest.vm.LedgerUnwrapGroupVM;
import io.dentall.totoro.web.rest.vm.LedgerVM;
import org.junit.Assert;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static io.dentall.totoro.step_definitions.StepDefinitionUtil.DurationParamTypeRegularExpression;
import static io.dentall.totoro.web.rest.TestUtil.createFormattingConversionService;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class LedgerStepDefinition extends AbstractStepDefinition {

    @Autowired
    private LedgerService ledgerService;

    @Autowired
    private LedgerQueryService ledgerQueryService;

    @Autowired
    private LedgerGroupRepository ledgerGroupRepository;

    @Autowired
    private PatientService patientService;

    @Autowired
    private LedgerReceiptRepository ledgerReceiptRepository;

    @Autowired
    private LedgerReceiptPrintedRecordRepository ledgerReceiptPrintedRecordRepository;

    @Autowired
    private ImageGcsBusinessService imageGcsBusinessService;

    @Autowired
    private LedgerTestInfoHolder ledgerTestInfoHolder;

    @Autowired
    private UserTestInfoHolder userTestInfoHolder;

    @Autowired
    private PatientTestInfoHolder patientTestInfoHolder;

    private String ledgerApiPath = "/api/ledgers";

    private String ledgerGroupApiPath = "/api/ledger-groups";

    private String ledgerReceiptApiPath = "/api/ledger-receipts";

    private String ledgerReceiptPrintedRecordApiPath = "/api/ledger-receipts/{id}/ledger-receipt-printed-records";

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LedgerResource resource = new LedgerResource(
            ledgerService,
            ledgerQueryService,
            ledgerGroupRepository,
            patientService,
            ledgerReceiptRepository,
            ledgerReceiptPrintedRecordRepository,
            imageGcsBusinessService
        );
        this.mvc = MockMvcBuilders.standaloneSetup(resource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .build();
    }

    @DataTableType
    public LedgerGroup ledgerGroup(Map<String, String> entry) {
        return LedgerTestMapper.INSTANCE.mapToLedgerGroup(entry);
    }

    @DataTableType
    public LedgerUnwrapGroupVM ledger(Map<String, String> entry) {
        return LedgerTestMapper.INSTANCE.mapToLedger(entry);
    }

    @DataTableType
    public LedgerVM ledgerVM(Map<String, String> entry) {
        LedgerVM vm = LedgerTestMapper.INSTANCE.mapToLedgerVM(entry);
        vm.setPatient(patientTestInfoHolder.getPatient());
        vm.setDoctorId(userTestInfoHolder.getUser().getId());

        return vm;
    }

    @DataTableType
    public LedgerReceipt ledgerReceipt(Map<String, String> entry) {
        return LedgerTestMapper.INSTANCE.mapToLedgerReceipt(entry);
    }

    @DataTableType
    public LedgerReceiptPrintedRecord ledgerReceiptPrintedRecord(Map<String, String> entry) {
        return LedgerTestMapper.INSTANCE.mapToLedgerReceiptPrintedRecord(entry);
    }

    @DataTableType
    public LedgerReceiptVM ledgerReceiptVM(Map<String, String> entry) {
        LedgerReceiptVM result = LedgerTestMapper.INSTANCE.mapToLedgerReceiptVM (entry);

        if (entry.containsKey("rangeBegin")) {
            result.setRangeBegin(
                Instant.parse(entry.get("rangeBegin").concat("T00:00:00Z"))
            );
        }
        if (entry.containsKey("rangeEnd")) {
            result.setRangeEnd(
                Instant.parse(entry.get("rangeEnd").concat("T00:00:00Z"))
            );
        }

        return result;
    }

    @ParameterType("包含|不包含")
    public boolean yesNo(String yesNo){
        if (yesNo.equals("包含")) {
            return true;
        } else if (yesNo.equals("不包含")) {
            return false;
        } else {
            return false;
        }
    }

    @ParameterType(
        name = "duration",
        value = DurationParamTypeRegularExpression
    )
    public DateTimeUtil.BeginEnd duration(
        String beginYear,
        String beginMonth,
        String beginDay,
        String endYear,
        String endMonth,
        String endDay
    ) {
        DateTimeUtil.BeginEnd result = new DateTimeUtil.BeginEnd();

        result.setBegin(
            Instant.parse(beginYear
                .concat("-")
                .concat(beginMonth)
                .concat("-")
                .concat(beginDay)
                .concat("T00:00:00Z")
            )
        );
        result.setEnd(
            Instant.parse(endYear
                .concat("-")
                .concat(endMonth)
                .concat("-")
                .concat(endDay)
                .concat("T00:00:00Z")
            )
        );

        return result;
    }

    @Given("為病患產生一個新的專案")
    public void createLedgerGroup(LedgerGroup ledgerGroup) throws Exception {
        ledgerGroup.setDoctorId(userTestInfoHolder.getUser().getId());
        ledgerGroup.setPatientId(patientTestInfoHolder.getPatient().getId());

        MockHttpServletRequestBuilder requestBuilder = post(ledgerGroupApiPath)
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsBytes(ledgerGroup))
            ;

        ResultActions resultActions = this.mvc.perform(requestBuilder);
        LedgerGroup responseBody = objectMapper.readValue(
            resultActions.andReturn().getResponse().getContentAsByteArray(),
            LedgerGroup.class
        );

        ledgerTestInfoHolder.setLedgerGroup(responseBody);
    }

    @Given("增加一筆收支到新專案")
    public void addLedgerToGroup(List<LedgerUnwrapGroupVM> ledgers) throws Exception {
        for (LedgerUnwrapGroupVM ledger : ledgers) {
            ledger.setDoctor(userTestInfoHolder.getUser().getId().toString());

            MockHttpServletRequestBuilder requestBuilder = post(ledgerApiPath)
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(ledger));

            ResultActions resultActions = this.mvc.perform(requestBuilder);
            Ledger responseBody = objectMapper.readValue(
                resultActions.andReturn().getResponse().getContentAsByteArray(),
                Ledger.class
            );

            ledgerTestInfoHolder.getLedgers().add(responseBody);
        };
    }

    @Given("增加當前限定收據，{yesNo}印花總繳")
    public void addCurrentLedgerToReceipt(boolean hasStampTax, boolean hasSigned) throws Exception {
        LedgerReceiptCreateVM ledgerReceiptCreateVM = new LedgerReceiptCreateVM();
        ledgerReceiptCreateVM.setGid(ledgerTestInfoHolder.getLedgerGroup().getId());
        ledgerReceiptCreateVM.setType(LedgerReceiptType.NONE);
        ledgerReceiptCreateVM.setRangeType(LedgerReceiptRangeType.CURRENT);
        ledgerReceiptCreateVM.setStampTax(hasStampTax);
        ledgerReceiptCreateVM.setSigned(hasSigned);
        ledgerReceiptCreateVM.setLedgers(
            Arrays.asList(
                ledgerTestInfoHolder.getLedgers().get(ledgerTestInfoHolder.getLedgerReceipts().size()-1)
            )
        );

        this.mvc.perform(
            post(ledgerReceiptApiPath)
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(ledgerReceiptCreateVM))
        );
        
        System.out.println("");
    }

    @Given("增加期間限定收據{duration}，{yesNo}印花總繳")
    public void addSpecificDurationLedgerToReceipt(DateTimeUtil.BeginEnd beginEnd, boolean yesNo) throws Exception {
        LedgerReceiptCreateVM ledgerReceiptCreateVM = new LedgerReceiptCreateVM();
        ledgerReceiptCreateVM.setGid(ledgerTestInfoHolder.getLedgerGroup().getId());
        ledgerReceiptCreateVM.setLedgers(ledgerTestInfoHolder.getLedgers());
        ledgerReceiptCreateVM.setType(LedgerReceiptType.RECREATE);
        ledgerReceiptCreateVM.setRangeType(LedgerReceiptRangeType.SPECIFIC_BEGIN_END);
        ledgerReceiptCreateVM.setRangeBegin(beginEnd.getBegin());
        ledgerReceiptCreateVM.setRangeEnd(beginEnd.getEnd());
        ledgerReceiptCreateVM.setTime(Instant.now());
        ledgerReceiptCreateVM.setStampTax(yesNo);

        this.mvc.perform(
            post(ledgerReceiptApiPath)
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(ledgerReceiptCreateVM))
        );
    }

    @Then("依專案 gid 查詢")
    public void getLedgerByGid(List<LedgerVM> expects) throws Exception {
        MockHttpServletRequestBuilder requestBuilder = get(ledgerApiPath)
            .contentType(APPLICATION_JSON)
            .param("gid.equals", expects.get(0).getGid().toString());

        ResultActions resultActions = this.mvc.perform(requestBuilder);

        List<LedgerVM> ledgers = objectMapper.readValue(
            resultActions.andReturn().getResponse().getContentAsString(),
            new TypeReference<List<LedgerVM>>() {}
        );

        Assert.assertEquals(ledgers.size(), expects.size());

        for (int i = 0; i < ledgers.size(); i++) {
            validateLedgerVM(ledgers.get(i), expects.get(i));
        }
    }

    @Then("依專案 patient id 查詢")
    public void getLedgerByPatientId(List<LedgerVM> expects) throws Exception {
        MockHttpServletRequestBuilder requestBuilder = get(ledgerApiPath)
            .contentType(APPLICATION_JSON)
            .param("patientId.equals", patientTestInfoHolder.getPatient().getId().toString());

        ResultActions resultActions = this.mvc.perform(requestBuilder);

        List<LedgerVM> ledgers = objectMapper.readValue(
            resultActions.andReturn().getResponse().getContentAsString(),
            new TypeReference<List<LedgerVM>>() {}
        );

        Assert.assertEquals(ledgers.size(), expects.size());

        for (int i = 0; i < ledgers.size(); i++) {
            validateLedgerVM(ledgers.get(i), expects.get(i));
        }
    }

    @Then("依專案 doctor id 查詢")
    public void getLedgerByDoctorId(List<LedgerVM> expects) throws Exception {
        MockHttpServletRequestBuilder requestBuilder = get(ledgerApiPath)
            .contentType(APPLICATION_JSON)
            .param("doctorId.equals", userTestInfoHolder.getUser().getId().toString())
            ;

        ResultActions resultActions = this.mvc.perform(requestBuilder);

        List<LedgerVM> ledgers = objectMapper.readValue(
            resultActions.andReturn().getResponse().getContentAsString(),
            new TypeReference<List<LedgerVM>>() {
            }
        );

        Assert.assertEquals(ledgers.size(), expects.size());

        for (int i = 0; i < ledgers.size(); i++) {
            validateLedgerVM(ledgers.get(i), expects.get(i));
        }
    }

    @Then("依專案 gid 查詢收據資料")
    public void getLedgerWithLedgerReceiptByGid(List<LedgerReceiptVM> receiptVM) throws Exception {
        MockHttpServletRequestBuilder requestBuilder = get(ledgerApiPath)
            .contentType(APPLICATION_JSON)
            .param("gid.equals", ledgerTestInfoHolder.getLedgerGroup().getId().toString());

        ResultActions resultActions = this.mvc.perform(requestBuilder);

        List<LedgerVM> ledgers = objectMapper.readValue(
            resultActions.andReturn().getResponse().getContentAsString(),
            new TypeReference<List<LedgerVM>>() {}
        );

        System.out.println("");
    }

    private void validateLedgerVM(LedgerVM target, LedgerVM expected) throws Exception {
        Assert.assertEquals(
            target.getGid(),
            expected.getGid()
        );
        Assert.assertEquals(
            target.getAmount(),
            expected.getAmount()
        );
        Assert.assertEquals(
            target.getDate(),
            expected.getDate()
        );
        Assert.assertEquals(
            target.getType(),
            expected.getType()
        );
        Assert.assertEquals(
            target.getProjectCode(),
            expected.getProjectCode()
        );
        Assert.assertEquals(
            target.getDisplayName(),
            expected.getDisplayName()
        );
        Assert.assertEquals(
            target.getCharge(),
            expected.getCharge()
        );
        Assert.assertEquals(
            target.getNote(),
            expected.getNote()
        );
        Assert.assertEquals(
            target.getIncludeStampTax(),
            expected.getIncludeStampTax()
        );
        Assert.assertEquals(
            target.getDoctorId(),
            expected.getDoctorId()
        );
    }

}
