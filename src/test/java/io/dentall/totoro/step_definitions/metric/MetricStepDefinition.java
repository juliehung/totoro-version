package io.dentall.totoro.step_definitions.metric;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.DataTableType;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import io.dentall.totoro.business.service.nhi.metric.dto.MetricTreatment;
import io.dentall.totoro.business.service.nhi.metric.meta.*;
import io.dentall.totoro.business.service.nhi.metric.source.*;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;
import io.dentall.totoro.config.TimeConfig;
import io.dentall.totoro.domain.Holiday;
import io.dentall.totoro.domain.Patient;
import io.dentall.totoro.domain.User;
import io.dentall.totoro.service.NhiMetricServiceTest;
import io.dentall.totoro.step_definitions.holders.MetricTestInfoHolder;
import io.dentall.totoro.test.mapper.MetricTestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

import static com.fasterxml.jackson.databind.DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS;
import static com.fasterxml.jackson.databind.node.JsonNodeFactory.withExactBigDecimals;
import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.Comparator.comparing;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.*;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.assertj.core.api.Assertions.assertThat;

@CucumberContextConfiguration
@ContextConfiguration(classes = {NhiMetricServiceTest.class, MetricTestInfoHolder.class, TimeConfig.class}, initializers = MetricStepDefinition.Initializer.class)
public class MetricStepDefinition {

    private final ObjectMapper objectMapper = new ObjectMapper()
        .setNodeFactory(withExactBigDecimals(true))
        .enable(USE_BIG_DECIMAL_FOR_FLOATS);

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                "zoneOffset=+08:00"
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }

    @Autowired
    private MetricTestInfoHolder metricTestInfoHolder;

    @Autowired
    private NhiMetricServiceTest nhiMetricService;

    private final String metaClassPackage = "io.dentall.totoro.business.service.nhi.metric.meta";

    private final String formulaClassPackage = "io.dentall.totoro.business.service.nhi.metric.formula";

    private final String sourceClassPackage = "io.dentall.totoro.business.service.nhi.metric.source";

    private final int defaultPatientAge = 24;

    @Before
    public void setup() {
    }

    @ParameterType(value = "\\d{4}-\\d{2}-\\d{2}")
    public LocalDate baseDate(String date) {
        return LocalDate.parse(date);
    }

    @ParameterType(value = ".+Source")
    public Class<? extends Source<? extends NhiMetricRawVM, ?>> sourceDateRange(String range) throws ClassNotFoundException {
        return (Class<? extends Source<? extends NhiMetricRawVM, ?>>) Class.forName(sourceClassPackage + "." + range);
    }

    private static class AgeRage {
        private int upper = Integer.MAX_VALUE;
        private int bottom = Integer.MIN_VALUE;

        public int getUpper() {
            return upper;
        }

        public void setUpper(int upper) {
            this.upper = upper;
        }

        public int getBottom() {
            return bottom;
        }

        public void setBottom(int bottom) {
            this.bottom = bottom;
        }
    }

    @ParameterType(".*~.*")
    public AgeRage ageRange(String value) {
        AgeRage ageRage = new AgeRage();
        String[] tmp = value.split("~");

        if (tmp.length > 0 && isNotBlank(tmp[0])) {
            ageRage.setBottom(Integer.parseInt(tmp[0]));
        }

        if (tmp.length > 1 && isNotBlank(tmp[1])) {
            ageRage.setUpper(Integer.parseInt(tmp[1]));
        }

        return ageRage;
    }

    @ParameterType("\\d+C")
    public String treatment(String value) {
        return value;
    }

    private static class ReDateRange {
        private int begin;
        private int end;

        public ReDateRange(int begin, int end) {
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

    private ReDateRange getOdReDateRange(Class<? extends Source<? extends NhiMetricRawVM, ?>> sourceType) {
        if (OdOneYearNearByPatientSource.class.isAssignableFrom(sourceType) ||
            OdDeciduousOneYearNearByPatientSource.class.isAssignableFrom(sourceType) ||
            OdPermanentOneYearNearByPatientSource.class.isAssignableFrom(sourceType)) {
            return new ReDateRange(1, 365);
        } else if (EndoAndOdHalfYearNearByPatientSource.class.isAssignableFrom(sourceType)) {
            return new ReDateRange(1, 180);
        }

        throw new UnsupportedOperationException("Unsupported Source Type: " + sourceType.getName());
    }

    @ParameterType(value = ".+")
    public Class<? extends MetaCalculator<?>> metaType(String value) throws ClassNotFoundException {
        return (Class<? extends MetaCalculator<?>>) Class.forName(metaClassPackage + "." + value);
    }

    @ParameterType(value = ".+Formula")
    public Class<? extends Calculator<?>> formulaType(String value) throws ClassNotFoundException {
        return (Class<? extends Calculator<?>>) Class.forName(formulaClassPackage + "." + value);
    }

    @ParameterType(value = "\\{.+\\}")
    public JsonNode objectValue(String jsonValue) throws JsonProcessingException {
        return objectMapper.readTree(jsonValue);
    }

    @ParameterType(value = "\\d+")
    public long metaValue(String value) {
        return Long.parseLong(value);
    }

    @ParameterType(value = "[\\d\\.]+")
    public BigDecimal metricValue(String value) {
        return new BigDecimal(value);
    }

    @ParameterType(value = "\\d+")
    public long sourceCount(String value) {
        return Long.parseLong(value);
    }

    @DataTableType
    public List<? extends NhiMetricRawVM> convertToNhiMetricRawVM(DataTable dataTable) {
        List<Map<String, String>> datalist = dataTable.asMaps();
        Consumer<MetricTreatment> consumer = metricTreatmentConsumer();

        return datalist.stream().map(data -> {
            MetricTreatment metricTreatment = MetricTestMapper.INSTANCE.mapToMetricTreatment(data);
            consumer.accept(metricTreatment);
            return metricTreatment;
        }).collect(toList());
    }

    private Consumer<MetricTreatment> metricTreatmentConsumer() {
        MetricSubject subject = metricTestInfoHolder.getSubject();
        List<User> doctors = metricTestInfoHolder.getDoctors();
        List<Patient> patients = metricTestInfoHolder.getPatients();
        Map<Long, Long> disposalIdMap = new HashMap<>();
        AtomicLong disposalIdIncremental = new AtomicLong(0L);

        return (metricTreatment) -> {
            Long disposalId = metricTreatment.getDisposalId();

            if (disposalId == null) {
                metricTreatment.setDisposalId(disposalIdIncremental.incrementAndGet());
            } else {
                if (!disposalIdMap.containsKey(disposalId)) {
                    disposalIdMap.put(disposalId, disposalIdIncremental.incrementAndGet());
                }
                metricTreatment.setDisposalId(disposalIdMap.get(disposalId));
            }

            if (metricTreatment.getDoctorName() == null) {
                ofNullable(subject).ifPresent(s -> {
                    if (s.getClass().isAssignableFrom(DoctorSubject.class)) {
                        metricTreatment.setDoctorId(subject.getId());
                        metricTreatment.setDoctorName(subject.getName());
                    }
                });
            } else {
                doctors.stream()
                    .filter(d -> d.getFirstName().equals(metricTreatment.getDoctorName()))
                    .findAny()
                    .ifPresent(d -> metricTreatment.setDoctorId(d.getId()));
            }

            patients.stream()
                .filter(p -> p.getName().equals(metricTreatment.getPatientName()))
                .findFirst()
                .ifPresent(p -> {
                    metricTreatment.setPatientId(p.getId());
                    metricTreatment.setPatientBirth(p.getBirth());
                });

        };
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

    @DataTableType
    public Map<String, JsonNode> convertToJsonNodeMap(DataTable dataTable) {
        List<Map<String, String>> datalist = dataTable.asMaps();
        return datalist.stream().reduce(
            new HashMap<>(),
            (map, data) -> {
                String fieldName = data.get("FieldName");
                JsonNode value = null;
                try {
                    value = objectMapper.readTree(data.get("JsonValue"));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                map.put(fieldName, value);
                return map;
            },
            (m1, m2) -> {
                m1.putAll(m2);
                return m1;
            });
    }

    private Calculator<?> toFormulaInstance(
        Class<? extends Calculator<?>> formulaType,
        MetricConfig metricConfig) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        return formulaType.getDeclaredConstructor(MetricConfig.class).newInstance(metricConfig);
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

    private Calculator<?> toTreatmentAngAgeMetaInstance(
        Class<? extends MetaCalculator<?>> metaType,
        MetricConfig metricConfig,
        Source<? extends NhiMetricRawVM, ?> source,
        String treatment) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        MetaConfig metaConfig = metricTestInfoHolder.getMetaConfig();
        return metaType.getDeclaredConstructor(MetricConfig.class, MetaConfig.class, Source.class, List.class)
            .newInstance(metricConfig, metaConfig, source, asList(treatment));
    }

    private Source<? extends NhiMetricRawVM, ?> toSourceInstance(
        Class<? extends Source<? extends NhiMetricRawVM, ?>> sourceType,
        MetricConfig metricConfig) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Exclude exclude = metricTestInfoHolder.getExclude();
        Source<? extends NhiMetricRawVM, ?> source = sourceType.getDeclaredConstructor(MetricConfig.class).newInstance(metricConfig);
        source.setExclude(exclude);
        return source;
    }

    private MetricConfig getMetricConfig(LocalDate date) {
        MetricSubject subject = metricTestInfoHolder.getSubject();
        List<? extends NhiMetricRawVM> source = metricTestInfoHolder.getSource();
        Map<LocalDate, Optional<Holiday>> holidayMap =
            nhiMetricService.loadHolidayDataSet().stream().collect(groupingBy(Holiday::getDate, maxBy(comparing(Holiday::getDate))));
        return new MetricConfig(subject, date, source).applyHolidayMap(holidayMap);
    }

    @Then("載入指定檔案資料集 {word}")
    public void loadDataSet(String filePath) {
        Consumer<MetricTreatment> consumer = metricTreatmentConsumer();
        metricTestInfoHolder.setSource(
            nhiMetricService.loadDataSet("metric" + File.separator + filePath).stream()
                .map(data -> {
                    MetricTreatment metricTreatment = MetricTestMapper.INSTANCE.mapToMetricTreatment(data);
                    createPatient(metricTreatment.getPatientName(), ofNullable(metricTreatment.getPatientBirth()).map(LocalDate::getYear).orElse(defaultPatientAge));
                    consumer.accept(metricTreatment);
                    return metricTreatment;
                }).collect(toList()));
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

    @Given("使用原始點數計算")
    public void setUseOriginPoint() {
        MetaConfig metaConfig = metricTestInfoHolder.getMetaConfig();
        metaConfig.setUseOriginPoint(true);
    }

    @Given("排除國定假日點數")
    public void setExcludeHolidayPoint() {
        MetaConfig metaConfig = metricTestInfoHolder.getMetaConfig();
        metaConfig.setExcludeHolidayPoint(true);
    }

    @Given("國定假日排除點數上限20,000點")
    public void setExclude20000Point1ByDay() {
        MetaConfig metaConfig = metricTestInfoHolder.getMetaConfig();
        metaConfig.setExclude20000Point1ByDay(true);
    }

    @And("設定排除 {word}")
    public void setExclude(String text) {
        Exclude exclude = Exclude.valueOf(text);
        metricTestInfoHolder.setExclude(exclude);
    }

    @Given("設定指標主體類型為病人 {word} {int} 歲")
    public void createPatientSubject(String patientName, int age) {
        createPatient(patientName, age);
        Patient patient = metricTestInfoHolder.getPatients().stream().filter(p -> p.getName().equals(patientName))
            .findFirst().get();
        metricTestInfoHolder.setSubject(new PatientSubject(patient));
    }

    @Given("設定指標主體類型為病人 {word}")
    public void createPatientSubject(String patientName) {
        createPatientSubject(patientName, defaultPatientAge);
    }

    @Given("設定指標主體類型為醫師 {word}")
    public void createDoctorSubject(String doctorName) {
        User subject = new User();
        subject.setId(0L);
        subject.setFirstName(doctorName);
        metricTestInfoHolder.setSubject(new DoctorSubject(subject));
    }

    @Given("設定指標主體類型為醫師 {word} id {word}")
    public void createSubject(String doctorName, String id) {
        User subject = new User();
        subject.setId(Long.parseLong(id));
        subject.setFirstName(doctorName);
        metricTestInfoHolder.setSubject(new DoctorSubject(subject));
    }

    @Given("設定醫師 {word}")
    public void createDoctor(String doctorName) {
        List<User> doctors = metricTestInfoHolder.getDoctors();
        User doctor = new User();
        doctor.setId(doctors.size() + 100L);
        doctor.setFirstName(doctorName);
        doctors.add(doctor);
    }

    @Given("設定指標主體類型為診所")
    public void createClinicSubject() {
        metricTestInfoHolder.setSubject(new ClinicSubject());
    }

    @Given("設定病人 {word} {int} 歲")
    public void createPatient(String name, int age) {
        Optional<Patient> existPatient =
            metricTestInfoHolder.getPatients().stream().filter(p -> p.getName().equals(name)).findFirst();

        if (existPatient.isPresent()) {
            return;
        }

        LocalDate birthDate = LocalDate.now().minus(age, ChronoUnit.YEARS);
        Patient patient = new Patient();
        patient.setId(metricTestInfoHolder.getPatients().size() + 100L);
        patient.setName(name);
        patient.setDisplayName(name);
        patient.setBirth(birthDate);
        patient.setNationalId(randomAlphabetic(10));
        metricTestInfoHolder.getPatients().add(patient);
    }

    @When("設定指標資料")
    public void setupSource(List<? extends NhiMetricRawVM> source) {
        metricTestInfoHolder.setSource(source);
    }

    @Then("指定執行日期 {baseDate}，檢查指標 {formulaType}，計算結果數值應為 {metricValue}")
    public void checkFormula(
        LocalDate baseDate,
        Class<? extends Calculator<?>> formulaType,
        BigDecimal expectedMetricValue) throws Exception {

        MetricConfig metricConfig = getMetricConfig(baseDate);
        Calculator<?> formulaInstance = toFormulaInstance(formulaType, metricConfig);
        BigDecimal result = (BigDecimal) formulaInstance.calculate();

        assertThat(result).isEqualTo(expectedMetricValue);
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

    @Then("指定執行日期 {baseDate}，來源資料使用 {sourceDateRange}，檢查 {metaType}，計算結果應為 {objectValue}")
    public void checkObject(
        LocalDate baseDate,
        Class<? extends Source<? extends NhiMetricRawVM, ?>> sourceType,
        Class<? extends MetaCalculator<?>> metaType,
        JsonNode expectedValue) throws Exception {

        MetricConfig metricConfig = getMetricConfig(baseDate);
        Source<? extends NhiMetricRawVM, ?> sourceInstance = toSourceInstance(sourceType, metricConfig);
        Calculator<?> metaCalculatorInstance = toMetaInstance(metaType, metricConfig, sourceInstance);
        Meta<?> result = (Meta<?>) metaCalculatorInstance.calculate();
        JsonNode resultJsonNode = objectMapper.valueToTree(result.value());
        Iterator<Map.Entry<String, JsonNode>> fields = expectedValue.fields();

        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> fieldEntry = fields.next();
            String key = fieldEntry.getKey();
            String value = fieldEntry.getValue().asText();
            assertThat(resultJsonNode.has(key)).isTrue();

            if (resultJsonNode.get(key).isNumber()) {
                assertThat(resultJsonNode.get(key).decimalValue()).isEqualTo(new BigDecimal(value));
            } else {
                assertThat(resultJsonNode.get(key).asText()).isEqualTo(value);
            }
        }
    }

    @Then("指定執行日期 {baseDate}，來源資料使用 {sourceDateRange}，檢查 {metaType}，計算結果應為")
    public void checkObjects(
        LocalDate baseDate,
        Class<? extends Source<? extends NhiMetricRawVM, ?>> sourceType,
        Class<? extends MetaCalculator<?>> metaType,
        Map<String, JsonNode> expectedValueMap) throws Exception {

        MetricConfig metricConfig = getMetricConfig(baseDate);
        Source<? extends NhiMetricRawVM, ?> sourceInstance = toSourceInstance(sourceType, metricConfig);
        Calculator<?> metaCalculatorInstance = toMetaInstance(metaType, metricConfig, sourceInstance);
        Meta<?> result = (Meta<?>) metaCalculatorInstance.calculate();
        JsonNode resultJsonNode = objectMapper.valueToTree(result.value());

        expectedValueMap.forEach((fieldName, expectedJsonNode) -> {
            JsonNode jsonNode = resultJsonNode.findPath(fieldName);
            assertThat(jsonNode.isMissingNode()).isFalse();
            try {
                String actual = objectMapper.writeValueAsString(jsonNode);
                String expected = objectMapper.writeValueAsString(expectedJsonNode);
                assertThat(fieldName + "=" + actual).isEqualTo(fieldName + "=" + expected);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });

    }

    @Then("指定執行日期 {baseDate}，補牙時間範圍 {sourceDateRange}／重補時間範圍 {sourceDateRange}，檢查 {metaType}，計算結果數值應為 {metaValue}")
    @Then("指定執行日期 {baseDate}，拔牙時間範圍 {sourceDateRange}／根管與補牙時間範圍 {sourceDateRange}，檢查 {metaType}，計算結果數值應為 {metaValue}")
    public void checkOdRe(
        LocalDate baseDate,
        Class<? extends Source<? extends NhiMetricRawVM, ?>> sourceType1,
        Class<? extends Source<? extends NhiMetricRawVM, ?>> sourceType2,
        Class<? extends MetaCalculator<?>> metaType,
        long metaValue) throws Exception {

        MetricConfig metricConfig = getMetricConfig(baseDate);
        Source<? extends NhiMetricRawVM, ?> sourceInstance1 = toSourceInstance(sourceType1, metricConfig);
        Source<? extends NhiMetricRawVM, ?> sourceInstance2 = toSourceInstance(sourceType2, metricConfig);
        ReDateRange reDateRange = getOdReDateRange(sourceType2);
        Calculator<?> metaCalculatorInstance =
            toOdReMetaInstance(metaType, metricConfig, sourceInstance1, sourceInstance2, reDateRange.getBegin(), reDateRange.getEnd());
        Meta<Long> meta = (Meta<Long>) metaCalculatorInstance.calculate();

        long value = meta.value();
        assertThat(value).isEqualTo(metaValue);
    }

    @Then("指定執行日期 {baseDate}，來源資料使用 {sourceDateRange}／醫令 {treatment}，檢查 {metaType}，計算結果數值應為 {metaValue}")
    public void checkTreatmentCount(
        LocalDate baseDate,
        Class<? extends Source<? extends NhiMetricRawVM, ?>> sourceType,
        String treatment,
        Class<? extends MetaCalculator<?>> metaType,
        long metaValue) throws Exception {
        checkTreatmentAndAgeCount(baseDate, sourceType, treatment, new AgeRage(), metaType, metaValue);
    }

    @Then("指定執行日期 {baseDate}，來源資料使用 {sourceDateRange}／醫令 {treatment}／年齡 {ageRange}，檢查 {metaType}，計算結果數值應為 {metaValue}")
    public void checkTreatmentAndAgeCount(
        LocalDate baseDate,
        Class<? extends Source<? extends NhiMetricRawVM, ?>> sourceType,
        String treatment,
        AgeRage ageRage,
        Class<? extends MetaCalculator<?>> metaType,
        long metaValue) throws Exception {

        MetricConfig metricConfig = getMetricConfig(baseDate);
        Source<? extends NhiMetricRawVM, ?> sourceInstance = toSourceInstance(sourceType, metricConfig);
        Calculator<?> metaCalculatorInstance = toTreatmentAngAgeMetaInstance(metaType, metricConfig, sourceInstance, treatment);
        ((TreatmentAndAgeCount) metaCalculatorInstance).setBottomAge(ageRage.getBottom()).setUpperAge(ageRage.getUpper());
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

        expectedDailyValue.forEach((date, expectedValue) -> {
            Long actualValue = actualDailyValue.get(date);

            assertThat(actualValue).isNotNull();
            assertThat(format("%s %s", date, actualValue)).isEqualTo(format("%s %s", date, expectedValue));
        });

    }

    @Then("指定執行日期 {baseDate}，來源資料使用 {sourceDateRange}，預期筆數應為 {sourceCount}")
    public void checkSource(
        LocalDate baseDate,
        Class<? extends Source<? extends NhiMetricRawVM, ?>> sourceType,
        long sourceCount) throws Exception {

        MetricConfig metricConfig = getMetricConfig(baseDate);
        Source<? extends NhiMetricRawVM, ?> sourceInstance = toSourceInstance(sourceType, metricConfig);

        Type[] actualTypeArguments = getTypeArguments(sourceType);

        if (actualTypeArguments.length == 2 &&
            actualTypeArguments[1] instanceof ParameterizedType &&
            ((ParameterizedType) actualTypeArguments[1]).getRawType() == Map.class) {
            List<Map<?, ?>> result = metricConfig.retrieveSource(sourceInstance.key());
            assertThat(result.get(0).size()).isEqualTo(sourceCount);
        } else {
            List<NhiMetricRawVM> result = metricConfig.retrieveSource(sourceInstance.key());
            assertThat(result.size()).isEqualTo(sourceCount);
        }

    }

    private Type[] getTypeArguments(Class<?> clz) {
        Type genericSuperclass = clz.getGenericSuperclass();
        if (!(genericSuperclass instanceof ParameterizedType)) {
            return getTypeArguments(clz.getSuperclass());
        }
        ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
        return parameterizedType.getActualTypeArguments();
    }

}
