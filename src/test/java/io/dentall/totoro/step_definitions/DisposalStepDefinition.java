package io.dentall.totoro.step_definitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.DataTableType;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.dentall.totoro.business.service.nhi.util.NhiRuleCheckUtil;
import io.dentall.totoro.domain.*;
import io.dentall.totoro.domain.enumeration.DisposalStatus;
import io.dentall.totoro.domain.enumeration.NhiExtendDisposalUploadStatus;
import io.dentall.totoro.domain.enumeration.TreatmentProcedureStatus;
import io.dentall.totoro.repository.*;
import io.dentall.totoro.service.*;
import io.dentall.totoro.step_definitions.holders.*;
import io.dentall.totoro.test.mapper.NhiMedicalRecordTestMapper;
import io.dentall.totoro.test.mapper.TreatmentProcedureTestMapper;
import io.dentall.totoro.web.rest.DisposalResource;
import io.dentall.totoro.web.rest.NhiExtendDisposalResource;
import io.dentall.totoro.web.rest.NhiMedicalRecordResource;
import io.dentall.totoro.web.rest.TreatmentProcedureResource;
import lombok.extern.slf4j.Slf4j;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static io.dentall.totoro.service.util.DateTimeUtil.pastInstant;
import static io.dentall.totoro.service.util.DateTimeUtil.transformLocalDateToRocDate;
import static io.dentall.totoro.step_definitions.StepDefinitionUtil.DateParamTypeRegularExpression;
import static io.dentall.totoro.test.TestUtils.parseDays;
import static io.dentall.totoro.web.rest.TestUtil.createFormattingConversionService;
import static java.util.Arrays.asList;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@Slf4j
public class DisposalStepDefinition extends AbstractStepDefinition {

    @Autowired
    private DisposalTestInfoHolder disposalTestInfoHolder;

    @Autowired
    private RegistrationTestInfoHolder registrationTestInfoHolder;

    @Autowired
    private AppointmentTestInfoHolder appointmentTestInfoHolder;

    @Autowired
    private UserTestInfoHolder userTestInfoHolder;

    @Autowired
    private PatientTestInfoHolder patientTestInfoHolder;

    @Autowired
    private NhiMedicalRecordTestInfoHolder nhiMedicalRecordTestInfoHolder;

    @Autowired
    private DisposalService disposalService;

    @Autowired
    private DisposalQueryService disposalQueryService;

    @Autowired
    private NhiService nhiService;

    @Autowired
    private DisposalRepository disposalRepository;

    @Autowired
    private NhiRuleCheckUtil nhiRuleCheckUtil;

    @Autowired
    private NhiExtendDisposalRepository nhiExtendDisposalRepository;

    @Autowired
    private NhiExtendDisposalService nhiExtendDisposalService;

    @Autowired
    private TreatmentProcedureService treatmentProcedureService;

    @Autowired
    private TreatmentProcedureQueryService treatmentProcedureQueryService;

    @Autowired
    private NhiExtendPatientService nhiExtendPatientService;

    @Autowired
    private NhiMedicalRecordService nhiMedicalRecordService;

    @Autowired
    private NhiMedicalRecordQueryService nhiMedicalRecordQueryService;

    @Autowired
    private NhiTxRepository nhiTxRepository;

    @Autowired
    private NhiMedicineRepository nhiMedicineRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AppointmentStepDefinition appointmentStepDefinition;

    @Autowired
    private RegistrationStepDefinition registrationStepDefinition;

    @Autowired
    private NhiExtendDisposalRepository nhiExtendDisposalRepository;

    private String disposalApiPath = "/api/disposals";

    private String nhiExtendsDisposalApiPath = "/api/nhi-extend-disposals";

    private String treatmentProcedureApiPath = "/api/treatment-procedures";

    private String nhiMedicalRecordApiPath = "/api/nhi-medical-records";

    private final Pattern datePattern = Pattern.compile(DateParamTypeRegularExpression);

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DisposalResource resource1 = new DisposalResource(disposalService, disposalQueryService, nhiService, nhiExtendDisposalRepository, userRepository);
        final NhiExtendDisposalResource resource2 = new NhiExtendDisposalResource(nhiExtendDisposalService, disposalService);
        final TreatmentProcedureResource resource3 = new TreatmentProcedureResource(treatmentProcedureService, treatmentProcedureQueryService);
        final NhiMedicalRecordResource resource4 = new NhiMedicalRecordResource(nhiMedicalRecordService, nhiMedicalRecordQueryService, nhiTxRepository, nhiMedicineRepository, nhiExtendDisposalRepository);
        this.mvc = MockMvcBuilders.standaloneSetup(resource1, resource2, resource3, resource4)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .build();
    }

    @Given("產生診療計畫")
    public void createDisposal() throws Exception {
        Disposal disposal = generateDisposal(0);
        Disposal saved = doCreateDisposal(disposal);
        disposalTestInfoHolder.setDisposal(saved);
        disposalTestInfoHolder.addDisposal(saved);
    }

    /**
     * @param monthStr
     * @param dateStr
     * @return
     */
    @ParameterType(value = DateParamTypeRegularExpression)
    public int monthAndDate(String yearStr, String monthStr, String dateStr) {
        return parseDays(yearStr, monthStr, dateStr);
    }

    @Given("在(過去) {monthAndDate} ，產生診療計畫")
    @Given("在過去第 {int} 天，產生診療計畫")
    public void createDisposalAtPast(int pastDays) throws Exception {
        Disposal disposal = generateDisposal(pastDays);
        Disposal saved = doCreateDisposal(disposal);
        disposalTestInfoHolder.setDisposal(saved);
        disposalTestInfoHolder.addDisposal(saved);
    }

    private Disposal generateDisposal(int pastDays) {
        Patient patient = patientTestInfoHolder.getPatient();
        User user = userTestInfoHolder.getUser();
        NhiExtendDisposal nhiExtendDisposal = new NhiExtendDisposal();
        nhiExtendDisposal.setUploadStatus(NhiExtendDisposalUploadStatus.NORMAL);
        nhiExtendDisposal.setA17(transformLocalDateToRocDate(pastInstant(pastDays)));
        nhiExtendDisposal.setA12(patient.getNationalId());
        nhiExtendDisposal.setA15(user.getExtendUser().getNationalId());
        nhiExtendDisposal.setA18("WHATEVER");
        Appointment appointment = appointmentTestInfoHolder.getAppointment();
        Registration registration = registrationTestInfoHolder.getRegistration();
        registration.appointment(appointment);

        Disposal disposal = new Disposal();
        disposal.setDateTime(pastInstant(pastDays));
        disposal.setNhiExtendDisposals(new HashSet<>(asList(nhiExtendDisposal)));
        disposal.setStatus(DisposalStatus.PERMANENT);
        disposal.setRegistration(registration);
        return disposal;
    }

    private Disposal doCreateDisposal(Disposal disposal) throws Exception {
        MockHttpServletRequestBuilder requestBuilder = post(disposalApiPath)
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsBytes(disposal));

        ResultActions resultActions = this.mvc.perform(requestBuilder);
        Disposal disposalReturn = objectMapper.readValue(resultActions.andReturn().getResponse().getContentAsByteArray(), Disposal.class);

        NhiExtendDisposal nhiExtendDisposal = disposal.getNhiExtendDisposals().stream().findFirst().get();
        nhiExtendDisposal.setDisposal(disposalReturn);

        requestBuilder = post(nhiExtendsDisposalApiPath)
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsBytes(nhiExtendDisposal));

        this.mvc.perform(requestBuilder);

        return disposalRepository.findById(disposalReturn.getId()).get();
    }

    @And("新增診療代碼:")
    public void addNhiCode(Set<TreatmentProcedure> treatmentProcedures) {
        Disposal disposal = disposalTestInfoHolder.getDisposal();

        Set<TreatmentProcedure> treatmentProcedureSaved = treatmentProcedures.stream().map(
            tp -> {
                try {
                    tp = doCreateTreatmentProcedure(tp);
                    tp.setDisposal(disposal);
                    return tp;
                } catch (Exception e) {
                    log.error("Create Treatment Procedure Error", e);
                    return null;
                }
            }
        ).filter(Objects::nonNull).collect(Collectors.toSet());

        disposal.setTreatmentProcedures(treatmentProcedureSaved);
    }

    @Given("新增 {int} 筆診療處置:")
    public void addMultiDisposal(int nums, DataTable dataTable) throws Exception {
        Map<Integer, List<Map<String, String>>> dataMap = dataTable.asMaps().stream().collect(Collectors.groupingBy(data -> Integer.valueOf(data.get("Id"))));

        for (int i = 1; i <= nums; i++) {
            List<Map<String, String>> dataList = dataMap.get(i);
            Map<String, String> firstData = dataList.get(0);
            int pastDays = retrievePastDays(firstData);

            appointmentStepDefinition.createAppointmentPast(pastDays);
            registrationStepDefinition.createRegistration(pastDays);
            createDisposalAtPast(pastDays);

            Set<TreatmentProcedure> treatmentProcedures = doConvertToTreatmentProcedure(dataList);
            addNhiCode(treatmentProcedures);
        }
    }

    @DataTableType
    public Set<TreatmentProcedure> convertToTreatmentProcedure(DataTable dataTable) {
        List<Map<String, String>> dataList = dataTable.asMaps();
        return doConvertToTreatmentProcedure(dataList);
    }

    private Set<TreatmentProcedure> doConvertToTreatmentProcedure(List<Map<String, String>> dataList) {
        Appointment appointment = appointmentTestInfoHolder.getAppointment();
        Disposal disposal = disposalTestInfoHolder.getDisposal();
        User user = userTestInfoHolder.getUser();
        return dataList.stream().map(data -> newTreatmentProcedure(appointment, disposal, user, data)).collect(Collectors.toSet());
    }

    private int retrievePastDays(Map<String, String> data) {
        String pastDate = Optional.ofNullable(data.get("PastDate")).orElse(null);
        int pastDays = Integer.parseInt(Optional.ofNullable(data.get("PastDays")).orElse("0"));

        return Optional.ofNullable(pastDate)
            .map(past -> datePattern.matcher(pastDate))
            .filter(matcher -> matcher.matches())
            .map(matcher -> parseDays(matcher.group(1), matcher.group(2), matcher.group(3)))
            .orElse(pastDays);
    }

    private TreatmentProcedure newTreatmentProcedure(Appointment appointment, Disposal disposal, User user, Map<String, String> data) {
        int pastDays = retrievePastDays(data);
        TreatmentProcedure treatmentProcedure = new TreatmentProcedure();
        treatmentProcedure.setAppointment(appointment);
        treatmentProcedure.setDisposal(disposal);
        treatmentProcedure.setDoctor(user.getExtendUser());
        treatmentProcedure.setStatus(TreatmentProcedureStatus.IN_PROGRESS);

        NhiExtendTreatmentProcedure nhiExtendTreatmentProcedure = newNhiExtendTreatmentProcedure(data);
        nhiExtendTreatmentProcedure.setTreatmentProcedure(treatmentProcedure);
        nhiExtendTreatmentProcedure.setNhiExtendDisposal(disposal.getNhiExtendDisposals().stream().findFirst().get());
        nhiExtendTreatmentProcedure.setA71(transformLocalDateToRocDate(pastInstant(pastDays)));

        treatmentProcedure.setNhiExtendTreatmentProcedure(nhiExtendTreatmentProcedure);

        return treatmentProcedure;
    }

    private NhiExtendTreatmentProcedure newNhiExtendTreatmentProcedure(Map<String, String> data) {
        NhiExtendTreatmentProcedure nhiExtendTreatmentProcedure = new NhiExtendTreatmentProcedure();
        nhiExtendTreatmentProcedure.setA72(data.get("A72"));
        nhiExtendTreatmentProcedure.setA73(data.get("A73"));
        nhiExtendTreatmentProcedure.setA74(data.get("A74"));
        nhiExtendTreatmentProcedure.setA75(data.get("A75"));
        nhiExtendTreatmentProcedure.setA76(data.get("A76"));
        nhiExtendTreatmentProcedure.setA77(data.get("A77"));
        nhiExtendTreatmentProcedure.setA78(data.get("A78"));
        nhiExtendTreatmentProcedure.setA79(data.get("A79"));
        return nhiExtendTreatmentProcedure;
    }

    private TreatmentProcedure doCreateTreatmentProcedure(TreatmentProcedure treatmentProcedure) throws Exception {
        TreatmentProcedureTestOnly treatmentProcedureTestOnly = TreatmentProcedureTestMapper.INSTANCE.map(treatmentProcedure);
        MockHttpServletRequestBuilder requestBuilder = post(treatmentProcedureApiPath)
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsBytes(treatmentProcedureTestOnly));

        ResultActions resultActions = this.mvc.perform(requestBuilder);
        return objectMapper.readValue(resultActions.andReturn().getResponse().getContentAsByteArray(), TreatmentProcedure.class);
    }

    @And("新增健保醫療:")
    public void addNhiMedical(Set<NhiMedicalRecord> nhiMedicalRecords) throws Exception {
        Patient patient = patientTestInfoHolder.getPatient();
        for (NhiMedicalRecord nhiMedicalRecord : nhiMedicalRecords) {
            nhiMedicalRecord.setNhiExtendPatient(patient.getNhiExtendPatient());
            nhiMedicalRecordTestInfoHolder.getNhiMedicalRecordList().add(doCreateNhiMedicalRecord(nhiMedicalRecord));
        }
    }

    @And("新增 {int} 筆健保醫療:")
    public void addMultiNhiMedical(int nums, DataTable dataTable) throws Exception {
        List<Map<String, String>> dataList = dataTable.asMaps();
        Set<NhiMedicalRecord> nhiMedicalRecords = new HashSet<>(nums);

        for (int i = 0; i < nums; i++) {
            Map<String, String> dataMap = dataList.get(i);
            nhiMedicalRecords.add(newNhiMedicalRecord(dataMap));
        }

        addNhiMedical(nhiMedicalRecords);
    }

    @DataTableType
    public Set<NhiMedicalRecord> convertToNhiMedicalRecord(DataTable dataTable) {
        List<Map<String, String>> dataList = dataTable.asMaps();
        return dataList.stream().map(data -> newNhiMedicalRecord(data)).collect(Collectors.toSet());
    }

    private NhiMedicalRecord newNhiMedicalRecord(Map<String, String> data) {
        int pastDays = retrievePastDays(data);
        NhiMedicalRecord nhiMedicalRecord = new NhiMedicalRecord();
        nhiMedicalRecord.setNhiCode(data.get("NhiCode"));
        nhiMedicalRecord.setPart(data.get("Teeth"));
        nhiMedicalRecord.setDate(transformLocalDateToRocDate(pastInstant(pastDays)));
        return nhiMedicalRecord;
    }

    private NhiMedicalRecord doCreateNhiMedicalRecord(NhiMedicalRecord nhiMedicalRecord) throws Exception {
        NhiMedicalRecordTestOnly nhiMedicalRecordTestOnly = NhiMedicalRecordTestMapper.INSTANCE.map(nhiMedicalRecord);
        MockHttpServletRequestBuilder requestBuilder = post(nhiMedicalRecordApiPath)
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsBytes(nhiMedicalRecordTestOnly));

        ResultActions resultActions = this.mvc.perform(requestBuilder);
        return objectMapper.readValue(resultActions.andReturn().getResponse().getContentAsByteArray(), NhiMedicalRecord.class);
    }

}
