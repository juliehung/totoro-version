package io.dentall.totoro.step_definitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.DataTableType;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.dentall.totoro.business.service.nhi.metric.dto.MetricTreatment;
import io.dentall.totoro.business.service.nhi.metric.meta.Calculator;
import io.dentall.totoro.business.service.nhi.metric.meta.Meta;
import io.dentall.totoro.business.service.nhi.metric.meta.MetaCalculator;
import io.dentall.totoro.business.service.nhi.metric.meta.MetaConfig;
import io.dentall.totoro.business.service.nhi.metric.source.*;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;
import io.dentall.totoro.domain.ExtendUser;
import io.dentall.totoro.domain.Patient;
import io.dentall.totoro.domain.User;
import io.dentall.totoro.repository.PatientRepository;
import io.dentall.totoro.repository.TagRepository;
import io.dentall.totoro.repository.UserRepository;
import io.dentall.totoro.service.*;
import io.dentall.totoro.step_definitions.holders.MetricTestInfoHolder;
import io.dentall.totoro.test.mapper.MetricTestMapper;
import io.dentall.totoro.web.rest.PatientResource;
import io.dentall.totoro.web.rest.UserResource;
import io.dentall.totoro.web.rest.vm.ManagedUserVM;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.dentall.totoro.web.rest.TestUtil.createFormattingConversionService;
import static java.lang.String.format;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class MetricStepDefinition extends AbstractStepDefinition {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private MailService mailService;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private AvatarService avatarService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private BroadcastService broadcastService;

    @Autowired
    private MetricTestInfoHolder metricTestInfoHolder;

    private final String userApiPath = "/api/users";

    private final String apiPath = "/api/patients";

    private final String metaClassPackage = "io.dentall.totoro.business.service.nhi.metric.meta";

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UserResource userResource = new UserResource(userService, userRepository, mailService);
        final PatientResource patientResource = new PatientResource(patientRepository, tagRepository, avatarService, patientService, broadcastService);
        this.mvc = MockMvcBuilders.standaloneSetup(userResource, patientResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .build();
    }

    @ParameterType(value = ".+")
    public LocalDate baseDate(String date) {
        return LocalDate.parse(date);
    }

    @ParameterType(value = ".+")
    public Class<? extends Source<? extends NhiMetricRawVM, ?>> sourceDateRange(String range) {
        if ("1個月".equals(range)) {
            return MonthSelectedSource.class;
        } else if ("每日1個月".equals(range)) {
            return DailyByMonthSelectedSource.class;
        } else if ("季".equals(range)) {
            return QuarterSource.class;
        } else if ("OD1個月".equals(range)) {
            return OdMonthSelectedSource.class;
        } else if ("OD季".equals(range)) {
            return OdQuarterSource.class;
        } else if ("OD季牙齒".equals(range)) {
            return OdQuarterByPatientSource.class;
        } else if ("OD季乳牙".equals(range)) {
            return OdDeciduousQuarterByPatientSource.class;
        } else if ("OD季恆牙".equals(range)) {
            return OdPermanentQuarterByPatientSource.class;
        } else if ("OD1年牙齒".equals(range)) {
            return OdOneYearNearByPatientSource.class;
        } else if ("OD1年乳牙".equals(range)) {
            return OdDeciduousOneYearNearByPatientSource.class;
        } else if ("OD1年恆牙".equals(range)) {
            return OdPermanentOneYearNearByPatientSource.class;
        }

        throw new UnsupportedOperationException("Unsupported Source Range: " + range);
    }

    private static class OdReDateRange {
        private int begin;
        private int end;

        public OdReDateRange(int begin, int end) {
            this.begin = begin;
            this.end = end;
        }

        public int getBegin() {
            return begin;
        }

        public int getEnd() {
            return end;
        }
    }

    private OdReDateRange getOdReDateRange(Class<? extends Source<? extends NhiMetricRawVM, ?>> sourceType) {
        if (OdOneYearNearByPatientSource.class.isAssignableFrom(sourceType) ||
            OdDeciduousOneYearNearByPatientSource.class.isAssignableFrom(sourceType) ||
            OdPermanentOneYearNearByPatientSource.class.isAssignableFrom(sourceType)) {
            return new OdReDateRange(1, 365);
        }

        throw new UnsupportedOperationException("Unsupported Source Type: " + sourceType.getName());
    }

    @ParameterType(value = ".+")
    public Class<? extends MetaCalculator<?>> metaType(String value) throws ClassNotFoundException {
        return (Class<? extends MetaCalculator<?>>) Class.forName(metaClassPackage + "." + value);
    }

    @ParameterType(value = ".+")
    public long metaValue(String value) {
        return Long.parseLong(value);
    }

    @DataTableType
    public List<? extends NhiMetricRawVM> convertToNhiMetricRawVM(DataTable dataTable) {
        List<Map<String, String>> datalist = dataTable.asMaps();
        User subject = metricTestInfoHolder.getSubject();
        List<Patient> patients = metricTestInfoHolder.getPatients();

        return datalist.stream().map(data -> {
            MetricTreatment metricTreatment = MetricTestMapper.INSTANCE.mapToMetricTreatment(data);

            ofNullable(subject).ifPresent(s -> {
                metricTreatment.setDoctorId(subject.getId());
                metricTreatment.setDoctorName(subject.getFirstName());
            });

            patients.stream()
                .filter(p -> p.getName().equals(metricTreatment.getPatientName()))
                .findFirst()
                .ifPresent(p -> {
                    metricTreatment.setPatientId(p.getId());
                    metricTreatment.setPatientBirth(p.getBirth());
                });

            return metricTreatment;
        }).collect(toList());
    }

    @DataTableType
    public Map<LocalDate, Long> convertToDailyValue(DataTable dataTable) {
        List<Map<String, String>> datalist = dataTable.asMaps();
        return datalist.stream().reduce(
            new HashMap<>(),
            (map, data) -> {
                LocalDate date = LocalDate.parse(data.get("Date"));
                Long value = Long.parseLong(data.get("Value"));
                map.put(date, value);
                return map;
            },
            (m1, m2) -> {
                m1.putAll(m2);
                return m1;
            });
    }

    private Calculator<?> toMetaInstance(
        Class<? extends MetaCalculator<?>> metaType,
        MetricConfig metricConfig,
        Source<? extends NhiMetricRawVM, ?> source) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        MetaConfig metaConfig = metricTestInfoHolder.getMetaConfig();
        return toMetaInstance(metaType, metricConfig, metaConfig, source);
    }

    private Calculator<?> toMetaInstance(
        Class<? extends MetaCalculator<?>> metaType,
        MetricConfig metricConfig,
        MetaConfig metaConfig,
        Source<? extends NhiMetricRawVM, ?> source) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        return metaType.getDeclaredConstructor(MetricConfig.class, MetaConfig.class, Source.class).newInstance(metricConfig, metaConfig, source);
    }

    private Calculator<?> toOdReMetaInstance(
        Class<? extends MetaCalculator<?>> metaType,
        MetricConfig metricConfig,
        Source<? extends NhiMetricRawVM, ?> source1,
        Source<? extends NhiMetricRawVM, ?> source2,
        int dayShiftBegin,
        int dayShiftEnd) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        MetaConfig metaConfig = metricTestInfoHolder.getMetaConfig();
        return metaType.getDeclaredConstructor(MetricConfig.class, MetaConfig.class, Source.class, Source.class, int.class, int.class)
            .newInstance(metricConfig, metaConfig, source1, source2, dayShiftBegin, dayShiftEnd);
    }

    private Source<? extends NhiMetricRawVM, ?> toSourceInstance(
        Class<? extends Source<? extends NhiMetricRawVM, ?>> sourceType,
        MetricConfig metricConfig) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        return sourceType.getDeclaredConstructor(MetricConfig.class).newInstance(metricConfig);
    }

    private MetricConfig getMetricConfig(LocalDate date) {
        User subject = metricTestInfoHolder.getSubject();
        List<? extends NhiMetricRawVM> source = metricTestInfoHolder.getSource();
        return new MetricConfig(subject, date, source);
    }

    @Given("設定使用00121C點數計算")
    public void setUse00121CPoint() {
        MetaConfig metaConfig = metricTestInfoHolder.getMetaConfig();
        metaConfig.setUse00121CPoint(true);
    }

    @Given("設定排除山地離島診察費差額")
    public void setExcludeHideoutPoint() {
        MetaConfig metaConfig = metricTestInfoHolder.getMetaConfig();
        metaConfig.setExcludeHideoutPoint(true);
    }

    @Given("設定Exam2和Exam4，超過1200萬點納入計算")
    public void setIncludePoint6By12MPoints() {
        MetaConfig metaConfig = metricTestInfoHolder.getMetaConfig();
        metaConfig.setIncludePoint6By12MPoints(true);
    }

    @Given("設定指標主體類型為醫師 {word}")
    public void createUser(String doctorName) throws Exception {
        ExtendUser extendUser = new ExtendUser();
        extendUser.setNationalId(randomAlphabetic(10));
        ManagedUserVM newUser = new ManagedUserVM();
        newUser.setActivated(true);
        newUser.setEmail(randomAlphabetic(10) + "@dentall.io");
        newUser.setFirstName(doctorName);
        newUser.setLastName(doctorName);
        newUser.setLogin(randomAlphabetic(10));
        newUser.setPassword(randomAlphabetic(10));
        newUser.setExtendUser(extendUser);

        MockHttpServletRequestBuilder requestBuilder = post(userApiPath)
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsBytes(newUser));

        ResultActions resultActions = this.mvc.perform(requestBuilder);
        User user = objectMapper.readValue(resultActions.andReturn().getResponse().getContentAsByteArray(), User.class);
        metricTestInfoHolder.setSubject(user);
    }

    @Given("設定病人 {word} {int} 歲")
    public void createPatient(String name, int age) throws Exception {
        LocalDate birthDate = LocalDate.now().minus(age, ChronoUnit.YEARS);
        Patient patient = new Patient();
        patient.setName(name);
        patient.setDisplayName(name);
        patient.setPhone("0920333777");
        patient.setBirth(birthDate);
        patient.setNationalId(randomAlphabetic(10));

        MockHttpServletRequestBuilder requestBuilder = post(apiPath)
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsBytes(patient));

        ResultActions resultActions = this.mvc.perform(requestBuilder);
        Patient patientReturn = objectMapper.readValue(resultActions.andReturn().getResponse().getContentAsByteArray(), Patient.class);

        Patient saved = patientRepository.findById(patientReturn.getId()).get();
        metricTestInfoHolder.getPatients().add(saved);
    }

    @When("設定指標資料")
    public void checkExam1(List<? extends NhiMetricRawVM> source) {
        metricTestInfoHolder.setSource(source);
    }

    @Then("指定執行日期 {baseDate}，來源資料使用 {sourceDateRange}，檢查 {metaType}，計算結果數值應為 {metaValue}")
    public void checkRegular(
        LocalDate baseDate,
        Class<? extends Source<? extends NhiMetricRawVM, ?>> sourceType,
        Class<? extends MetaCalculator<?>> metaType,
        long metaValue) throws Exception {

        MetricConfig metricConfig = getMetricConfig(baseDate);
        Source<? extends NhiMetricRawVM, ?> sourceInstance = toSourceInstance(sourceType, metricConfig);
        Calculator<?> metaCalculatorInstance = toMetaInstance(metaType, metricConfig, sourceInstance);
        Meta<Long> meta = (Meta<Long>) metaCalculatorInstance.calculate();

        long value = meta.value();
        assertThat(value).isEqualTo(metaValue);
    }

    @Then("指定執行日期 {baseDate}，補牙時間範圍 {sourceDateRange}／重補時間範圍 {sourceDateRange}，檢查 {metaType}，計算結果數值應為 {metaValue}")
    public void checkOdRe(
        LocalDate baseDate,
        Class<? extends Source<? extends NhiMetricRawVM, ?>> sourceType1,
        Class<? extends Source<? extends NhiMetricRawVM, ?>> sourceType2,
        Class<? extends MetaCalculator<?>> metaType,
        long metaValue) throws Exception {

        MetricConfig metricConfig = getMetricConfig(baseDate);
        Source<? extends NhiMetricRawVM, ?> sourceInstance1 = toSourceInstance(sourceType1, metricConfig);
        Source<? extends NhiMetricRawVM, ?> sourceInstance2 = toSourceInstance(sourceType2, metricConfig);
        OdReDateRange odReDateRange = getOdReDateRange(sourceType2);
        Calculator<?> metaCalculatorInstance =
            toOdReMetaInstance(metaType, metricConfig, sourceInstance1, sourceInstance2, odReDateRange.getBegin(), odReDateRange.getEnd());
        Meta<Long> meta = (Meta<Long>) metaCalculatorInstance.calculate();

        long value = meta.value();
        assertThat(value).isEqualTo(metaValue);
    }

    @Then("指定執行日期 {baseDate}，來源資料使用 {sourceDateRange}，檢查 {metaType}，每日數值")
    public void checkRegularByDaily(
        LocalDate baseDate,
        Class<? extends Source<? extends NhiMetricRawVM, ?>> sourceType,
        Class<? extends MetaCalculator<?>> metaType,
        Map<LocalDate, Long> expectedDailyValue) throws Exception {

        MetricConfig metricConfig = getMetricConfig(baseDate);
        Source<? extends NhiMetricRawVM, ?> sourceInstance = toSourceInstance(sourceType, metricConfig);
        Calculator<?> metaCalculatorInstance = toMetaInstance(metaType, metricConfig, sourceInstance);
        Meta<Map<LocalDate, Long>> meta = (Meta<Map<LocalDate, Long>>) metaCalculatorInstance.calculate();

        Map<LocalDate, Long> actualDailyValue = meta.value();

        expectedDailyValue.entrySet().forEach(entry -> {
            LocalDate date = entry.getKey();
            Long expectedValue = entry.getValue();
            Long actualValue = actualDailyValue.get(date);

            assertThat(actualValue).isNotNull();
            assertThat(format("%s %s", date, actualValue)).isEqualTo(format("%s %s", date, expectedValue));
        });

    }
}
