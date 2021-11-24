package io.dentall.totoro.step_definitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.DataTableType;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.dentall.totoro.business.service.nhi.util.NhiRuleCheckFormat;
import io.dentall.totoro.business.service.nhi.util.NhiRuleCheckUtil;
import io.dentall.totoro.business.service.nhi.util.ToothConstraint;
import io.dentall.totoro.business.service.nhi.util.ToothUtil.ToothPhase;
import io.dentall.totoro.business.vm.nhi.NhiRuleCheckBody;
import io.dentall.totoro.business.vm.nhi.NhiRuleCheckTxSnapshot;
import io.dentall.totoro.domain.*;
import io.dentall.totoro.repository.NhiExtendTreatmentProcedureRepository;
import io.dentall.totoro.service.util.DateTimeUtil;
import io.dentall.totoro.step_definitions.holders.*;
import io.dentall.totoro.test.TestUtils;
import io.dentall.totoro.test.dao.NhiTreatment;
import io.dentall.totoro.test.mapper.NhiTreatmentTestMapper;
import io.dentall.totoro.web.rest.NhiRuleCheckResource;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;
import java.util.function.BiFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.dentall.totoro.business.service.NhiRuleCheckSourceType.*;
import static io.dentall.totoro.business.service.nhi.util.NhiRuleCheckFormat.*;
import static io.dentall.totoro.business.service.nhi.util.ToothUtil.markAsPhase;
import static io.dentall.totoro.business.service.nhi.util.ToothUtil.multipleToothToDisplay;
import static io.dentall.totoro.service.util.DateTimeUtil.*;
import static io.dentall.totoro.step_definitions.StepDefinitionUtil.*;
import static io.dentall.totoro.test.TestUtils.parseMonthGap;
import static io.dentall.totoro.web.rest.TestUtil.createFormattingConversionService;
import static java.lang.String.join;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static java.util.Comparator.reverseOrder;
import static java.util.stream.Collectors.joining;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


public class NhiRuleCheckStepDefinition extends AbstractStepDefinition {

    @Autowired
    private UserTestInfoHolder userTestInfoHolder;

    @Autowired
    private PatientTestInfoHolder patientTestInfoHolder;

    @Autowired
    private DisposalTestInfoHolder disposalTestInfoHolder;

    @Autowired
    private NhiRuleCheckTestInfoHolder nhiRuleCheckTestInfoHolder;

    @Autowired
    private NhiMedicalRecordTestInfoHolder nhiMedicalRecordTestInfoHolder;

    @Autowired
    private NhiRuleCheckUtil nhiRuleCheckUtil;

    @Autowired
    private NhiExtendTreatmentProcedureRepository nhiExtendTreatmentProcedureRepository;

    @Autowired
    private ApplicationContext applicationContext;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NhiRuleCheckResource resource = new NhiRuleCheckResource(
            nhiRuleCheckUtil,
            applicationContext
        );
        this.mvc = MockMvcBuilders.standaloneSetup(resource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .build();
    }

    private BiFunction<NhiRuleCheckFormat, Object[], String> formatMsg() {
        return formatMsg(true);
    }

    private BiFunction<NhiRuleCheckFormat, Object[], String> formatMsg(final boolean showMsg) {
        return (format, args) -> showMsg ? String.format(format.getFormat(), args) : null;
    }

    private NhiRuleCheckTxSnapshot toOriginNetpSnapshot(Map<String, String> data) {
        String nhiCode = data.get("NhiCode");
        String teeth = data.get("Teeth");
        String surface = data.get("Surface");

        NhiRuleCheckTxSnapshot nhiRuleCheckTxSnapshot = new NhiRuleCheckTxSnapshot();
        nhiRuleCheckTxSnapshot.setNhiCode(nhiCode);
        nhiRuleCheckTxSnapshot.setTeeth(teeth);
        nhiRuleCheckTxSnapshot.setSurface(surface);

        return nhiRuleCheckTxSnapshot;
    }

    private NhiRuleCheckTxSnapshot toNewNetpSnapshot(Map<String, String> data) {
        String nhiCode = data.get("NewNhiCode");
        String teeth = data.get("NewTeeth");
        String surface = data.get("NewSurface");

        NhiRuleCheckTxSnapshot nhiRuleCheckTxSnapshot = new NhiRuleCheckTxSnapshot();
        nhiRuleCheckTxSnapshot.setNhiCode(nhiCode);
        nhiRuleCheckTxSnapshot.setTeeth(teeth);
        nhiRuleCheckTxSnapshot.setSurface(surface);

        return nhiRuleCheckTxSnapshot;
    }

    @ParameterType(value = "Pass|NotPass")
    public Boolean passOrNot(String passOrNot) {
        return TestUtils.valueOfPassOrNot(passOrNot);
    }

    @ParameterType(value = "顯示|不顯示", preferForRegexMatch = true)
    public Boolean showOrNot(String showOrNot) {
        return TestUtils.valueOfShowOrNot(showOrNot);
    }

    @ParameterType(value = ".+")
    public NhiRuleCheckFormat msgFormat(String msgType) {
        return NhiRuleCheckFormat.valueOf(msgType);
    }

    @ParameterType(value = ".+")
    public ToothConstraint toothConstraint(String toothConstraint) {
        return ToothConstraint.valueOf(toothConstraint);
    }

    @ParameterType(value = ".+")
    public List<String> nhiCodeList(String nhiCodes) {
        return asList(nhiCodes.split("/"));
    }

    @DataTableType
    public List<NhiRuleCheckTxSnapshot> convertToNhiRuleCheckTxSnapshot(DataTable dataTable) {
        Disposal disposal = disposalTestInfoHolder.getDisposal();
        HashMap<Long, NhiExtendTreatmentProcedure> originNetpMap = new HashMap<>();
        nhiExtendTreatmentProcedureRepository.findNhiExtendTreatmentProcedureByTreatmentProcedure_Disposal_Id(disposal.getId())
            .forEach(netp -> originNetpMap.put(netp.getId(), netp));

        //先用原本的nhiCode、teeth、surface找到原id
        List<Map<String, String>> datalist = dataTable.asMaps();

        return datalist.stream().map(data -> {
            NhiRuleCheckTxSnapshot originNetpSnapshot = toOriginNetpSnapshot(data);
            NhiRuleCheckTxSnapshot newNetpSnapshot = toNewNetpSnapshot(data);

            // 沒有原本的netp，代表為新增，直接回傳newNetpSnapshot
            if (originNetpSnapshot.getNhiCode() == null) {
                return newNetpSnapshot;
            }

            // 用originNetpSnapshot一定要找得到Netp，不然就拋錯，代表dataTable的值填錯了
            NhiExtendTreatmentProcedure netp = originNetpMap.values().stream().filter(originNetpSnapshot::equalsNhiExtendTreatmentProcedure).findFirst().get();

            if (newNetpSnapshot.getNhiCode() != null) {
                // 代表原本的netp被修改過了
                newNetpSnapshot.setId(netp.getId());
                return newNetpSnapshot;
            } else {
                // 代表原本的netp原封不動
                originNetpSnapshot.setId(netp.getId());
                return originNetpSnapshot;
            }
        }).collect(Collectors.toList());
    }

    @When("執行診療代碼 {word} 檢查:")
    public void checkNhiCode(String code, List<NhiRuleCheckTxSnapshot> nhiRuleCheckTxSnapshotList) throws Exception {
        Disposal disposal = disposalTestInfoHolder.getDisposal();
        Patient patient = patientTestInfoHolder.getPatient();
        User doctor = userTestInfoHolder.getUser();

        NhiRuleCheckBody nhiRuleCheckBody = new NhiRuleCheckBody();
        nhiRuleCheckBody.setDisposalId(disposal.getId());
        nhiRuleCheckBody.setPatientId(patient.getId());
        nhiRuleCheckBody.setTxSnapshots(nhiRuleCheckTxSnapshotList);
        nhiRuleCheckBody.setDisposalTime(transformLocalDateToRocDate(disposal.getDateTime()));
        nhiRuleCheckBody.setDoctorId(doctor.getId());

        String apiPath = "/api/validation";
//        String fullApiPath = apiPath + "/" + code;
        MockHttpServletRequestBuilder requestBuilder = post(apiPath)
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsBytes(nhiRuleCheckBody));

        ResultActions resultActions = mvc.perform(requestBuilder);
//        resultActions.andDo(print());

        nhiRuleCheckTestInfoHolder.setNhiCode(code);
        nhiRuleCheckTestInfoHolder.setResultActions(resultActions);
        nhiRuleCheckTestInfoHolder.setNhiRuleCheckTxSnapshotList(nhiRuleCheckTxSnapshotList);
    }

    @Then("確認診療代碼 {word} ，確認結果是否為 {passOrNot}")
    public void confirmNhiCode(String code, Boolean passOrNot) throws Exception {
        ResultActions resultActions = nhiRuleCheckTestInfoHolder.getResultActions();
        checkValidatedResult(resultActions, passOrNot);
    }

    @Then("在過去 {int} 天，應沒有任何治療紀錄，確認結果是否為 {passOrNot} 且檢查訊息類型為 {msgFormat}")
    public void checkNoTreatmentInPeriod(int dayGap, Boolean passOrNot, NhiRuleCheckFormat msgFormat) throws Exception {
        String nhiCode = nhiRuleCheckTestInfoHolder.getNhiCode();
        ResultActions resultActions = nhiRuleCheckTestInfoHolder.getResultActions();
        List<NhiTreatment> nhiTreatments = findNhiTreatment();
        Object[] msgArgs = null;
        String message = null;

        if (nhiTreatments.size() > 0) {
            NhiTreatment violationNhiTreatment = nhiTreatments.stream().min((nt1, nt2) -> nt2.getDatetime().compareTo(nt1.getDatetime())).get();
            String type = findSourceType(violationNhiTreatment);
            String violationNhiCode = violationNhiTreatment.getCode();
            String date = transformA71ToDisplay(violationNhiTreatment.getDatetime());
            String tooth = multipleToothToDisplay(violationNhiTreatment.getTooth());

            if (msgFormat == D1_2) {
                msgArgs = new Object[]{nhiCode, violationNhiCode, type, date, dayGap, nhiCode};
            } else if (msgFormat == D1_3) {
                msgArgs = new Object[]{nhiCode, tooth, nhiCode, type, date};
            } else if (msgFormat == D1_5) {
                msgArgs = new Object[]{nhiCode, dayGap};
            }

            message = formatMsg().apply(msgFormat, msgArgs);
        }

        checkResult(resultActions, passOrNot, message);
    }

    @Then("病患的牙齒 {word} 在 {int} 天前，被申報 {word} 健保代碼，而現在病患的牙齒 {word} 要被申報 {word} 健保代碼，是否抵觸同顆牙齒在 {int} 天內不得申報指定健保代碼，確認結果是否為 {passOrNot} 且檢查訊息類型為 {msgFormat}")
    public void checkPatientToothAtCodesBeforePeriod(String pastTeeth, int pastDays, String pastNhiCode, String issueTeeth, String issueNhiCode, int dayRange, Boolean passOrNot, NhiRuleCheckFormat msgFormat) throws Exception {
        doCheckPatientToothAtCodesBeforePeriod(pastTeeth, pastDays, pastNhiCode, issueTeeth, issueNhiCode, dayRange, passOrNot, passOrNot, msgFormat);
    }

    @Then("病患的牙齒 {word} 在 {int} 天前，被申報 {word} 健保代碼，而現在病患的牙齒 {word} 要被申報 {word} 健保代碼，是否抵觸同顆牙齒在 {int} 天內不得申報指定健保代碼，確認主要結果是否為 {passOrNot} 和細項結果是否為 {passOrNot} 且檢查訊息類型為 {msgFormat}")
    public void checkPatientToothAtCodesBeforePeriod2(String pastTeeth, int pastDays, String pastNhiCode, String issueTeeth, String issueNhiCode, int dayRange, Boolean passOrNot, Boolean innerPassOrNot, NhiRuleCheckFormat msgFormat) throws Exception {
        doCheckPatientToothAtCodesBeforePeriod(pastTeeth, pastDays, pastNhiCode, issueTeeth, issueNhiCode, dayRange, passOrNot, innerPassOrNot, msgFormat);
    }

    public void doCheckPatientToothAtCodesBeforePeriod(String pastTeeth, int pastDays, String pastNhiCode, String issueTeeth, String issueNhiCode, int dayRange, Boolean passOrNot, Boolean innerPassOrNot, NhiRuleCheckFormat msgFormat) throws Exception {
        String nhiCode = nhiRuleCheckTestInfoHolder.getNhiCode();
        ResultActions resultActions = nhiRuleCheckTestInfoHolder.getResultActions();
        NhiTreatment nhiTreatment = findLastNhiTreatment(pastNhiCode, asList(pastTeeth)).get();
        String type = findSourceType(nhiTreatment);
        String date = transformLocalDateToRocDateForDisplay(pastInstant(pastDays));
        Object[] msgArgs = null;

        if (msgFormat == D1_3) {
            msgArgs = new Object[]{nhiCode, issueTeeth, pastNhiCode, type, date};
        } else if (msgFormat == D7_2 || msgFormat == W6_1) {
            msgArgs = new Object[]{nhiCode, dayRange, pastNhiCode, type, date, pastTeeth};
        } else if (msgFormat == D1_2) {
            msgArgs = new Object[]{nhiCode, pastNhiCode, type, date, dayRange, nhiCode};
        }

        String message = formatMsg().apply(msgFormat, msgArgs);
        checkResult(resultActions, passOrNot, innerPassOrNot, message);
    }

    @Then("限制牙面在 {int} 以上，確認結果是否為 {passOrNot}")
    public void checkAllLimitedSurface(int surfaceCount, Boolean passOrNot) throws Exception {
        String nhiCode = nhiRuleCheckTestInfoHolder.getNhiCode();
        ResultActions resultActions = nhiRuleCheckTestInfoHolder.getResultActions();
        String message;
        if (surfaceCount == 4) {
            message = passOrNot ? null : nhiCode + ": 牙面面數應為4面";
        } else {
            message = passOrNot ? null : nhiCode + ": 牙面數不可小於 " + surfaceCount;
        }
        checkResult(resultActions, passOrNot, message);
    }

    // 2021-11-24: 醫管發現後統一應由 D1_3 作為回覆，此項將不應再被使用
    @Deprecated
    @Then("任意時間點未曾申報過指定代碼 {word}，確認結果是否為 {passOrNot}")
    public void checkNoTreatment(String treatmentNhiCode, Boolean passOrNot) throws Exception {
        String nhiCode = nhiRuleCheckTestInfoHolder.getNhiCode();
        ResultActions resultActions = nhiRuleCheckTestInfoHolder.getResultActions();
        Optional<NhiTreatment> nhiTreatmentOpt = findLastNhiTreatment(treatmentNhiCode);
        String message = null;

        if (nhiTreatmentOpt.isPresent()) {
            NhiTreatment nhiTreatment = nhiTreatmentOpt.get();
            String type = findSourceType(nhiTreatment);
            String date = transformA71ToDisplay(nhiTreatment.getDatetime());
            message = formatMsg().apply(D1_1, new Object[]{nhiCode, treatmentNhiCode, type, date, nhiCode});
        }

        checkResult(resultActions, passOrNot, message);
    }

    @Then("任意時間點牙位 {word} 未曾申報過指定代碼 {word}，確認結果是否為 {passOrNot}")
    public void checkNoTreatment2(String tooth, String treatmentNhiCode, Boolean passOrNot) throws Exception {
        String nhiCode = nhiRuleCheckTestInfoHolder.getNhiCode();
        ResultActions resultActions = nhiRuleCheckTestInfoHolder.getResultActions();
        Optional<NhiTreatment> nhiTreatmentOpt = findLastNhiTreatment(treatmentNhiCode);
        String message = null;

        if (nhiTreatmentOpt.isPresent()) {
            NhiTreatment nhiTreatment = nhiTreatmentOpt.get();
            String type = findSourceType(nhiTreatment);
            String date = transformA71ToDisplay(nhiTreatment.getDatetime());
            message = formatMsg().apply(D1_3, new Object[]{nhiCode, tooth, treatmentNhiCode, type, date});
        }

        checkResult(resultActions, passOrNot, message);
    }

    @Then("89006C 在前 {int} 天建立， {word} 在前 {int} 天建立，確認結果是否為 {passOrNot}")
    public void checkSpecificRule_1_for89XXXC(int _89006CTreatmentDay, String treatmentNhiCode, int pastTreatmentDays, Boolean passOrNot) throws Exception {
        String nhiCode = nhiRuleCheckTestInfoHolder.getNhiCode();
        ResultActions resultActions = nhiRuleCheckTestInfoHolder.getResultActions();
        NhiTreatment violationNhiTreatment = findLastNhiTreatment("89006C").get();
        String type = findSourceType(violationNhiTreatment);
        String date = transformLocalDateToRocDateForDisplay(pastInstant(_89006CTreatmentDay));
        String message = formatMsg().apply(D1_2, new Object[]{nhiCode, "89006C", type, date, "30", nhiCode});
        checkResult(resultActions, passOrNot, message);
    }

    @Then("89006C 在前 {int} 天建立， {word} 在前 {int} 天建立，治療牙位為 {word} ，確認結果是否為 {passOrNot}")
    public void checkSpecificRule_1_for89XXXC(int _89006CTreatmentDay, String treatmentNhiCode, int pastTreatmentDays, String tooth, Boolean passOrNot) throws Exception {
        String nhiCode = nhiRuleCheckTestInfoHolder.getNhiCode();
        ResultActions resultActions = nhiRuleCheckTestInfoHolder.getResultActions();
        NhiTreatment violationNhiTreatment = findLastNhiTreatment("89006C").get();
        String type = findSourceType(violationNhiTreatment);
        String date = transformLocalDateToRocDateForDisplay(pastInstant(_89006CTreatmentDay));
        String message = formatMsg().apply(D1_3, new Object[]{nhiCode, tooth, "89006C", type, date});
        checkResult(resultActions, passOrNot, message);
    }

    @Then("同牙 {word} 未曾申報過，指定代碼 {word} ，確認結果是否為 {passOrNot}")
    public void checkNoTreatmentAtSpecificTooth(String issueTeeth, String treatmentNhiCode, Boolean passOrNot) throws Exception {
        String nhiCode = nhiRuleCheckTestInfoHolder.getNhiCode();
        ResultActions resultActions = nhiRuleCheckTestInfoHolder.getResultActions();
        NhiTreatment violationNhiTreatment = findLastNhiTreatment(treatmentNhiCode).get();
        String type = findSourceType(violationNhiTreatment);
        String date = transformA71ToDisplay(violationNhiTreatment.getDatetime());
        String message = formatMsg().apply(D1_3, new Object[]{nhiCode, issueTeeth, treatmentNhiCode, type, date});
        checkResult(resultActions, passOrNot, message);
    }

    @Then("檢查 {} 牙位，依 {toothConstraint} 判定是否為核可牙位，確認結果是否為 {passOrNot}")
    public void checkAllLimitedTooth(String issueTeeth, ToothConstraint toothConstraint, Boolean passOrNot) throws Exception {
        String nhiCode = nhiRuleCheckTestInfoHolder.getNhiCode();
        ResultActions resultActions = nhiRuleCheckTestInfoHolder.getResultActions();
        String message = passOrNot ? null : nhiCode + ": " + toothConstraint.getMessage();
        checkResult(resultActions, passOrNot, message);
    }

    @Then("提醒{string}，確認結果是否為 {passOrNot}")
    public void checkAddNotification(String notification, Boolean passOrNot) throws Exception {
        String nhiCode = nhiRuleCheckTestInfoHolder.getNhiCode();
        ResultActions resultActions = nhiRuleCheckTestInfoHolder.getResultActions();
        String message = nhiCode + ": " + notification;
        checkNoticeResult(resultActions, passOrNot, message);
    }

    @Then("提醒{string}，確認結果是否為 {passOrNot}，且檢核訊息應 {showOrNot}")
    public void checkAddNotification(String notification, Boolean passOrNot, Boolean showOrNot) throws Exception {
        String nhiCode = nhiRuleCheckTestInfoHolder.getNhiCode();
        ResultActions resultActions = nhiRuleCheckTestInfoHolder.getResultActions();
        String message = nhiCode + ": " + notification;
        checkNoticeResult(resultActions, passOrNot, message, showOrNot);
    }

    @Then("{int} 歲以下，提醒{string}，確認結果是否為 {passOrNot}，且檢核訊息應 {showOrNot}")
    public void checkNotificationWithClause(int age, String notification, Boolean passOrNot, Boolean showOrNot) throws Exception {
        String nhiCode = nhiRuleCheckTestInfoHolder.getNhiCode();
        ResultActions resultActions = nhiRuleCheckTestInfoHolder.getResultActions();
        String message = showOrNot ? nhiCode + ": " + notification : null;
        checkNoticeResult(resultActions, passOrNot, message, showOrNot);
    }

    @Then("檢查同一處置單，必須包含 {nhiCodeList} 其中之一的診療項目，確認結果是否為 {passOrNot}")
    public void checkMustIncludeNhiCode(List<String> nhiCodeList, Boolean passOrNot) throws Exception {
        ResultActions resultActions = nhiRuleCheckTestInfoHolder.getResultActions();
        String nhiCode = nhiRuleCheckTestInfoHolder.getNhiCode();
        String nhiCodesStr = join("/", parseNhiCode(nhiCodeList));
        String message = formatMsg().apply(W3_1, new Object[]{nhiCode, nhiCodesStr});
        checkResult(resultActions, passOrNot, message);
    }

    @Then("在 {int} 天中，不應該有同顆牙 {word} 的 {word} 診療項目，確認結果是否為 {passOrNot}")
    public void check(int dayGap, String issueTeeth, String issueNhiCode, Boolean passOrNot) throws Exception {
        ResultActions resultActions = nhiRuleCheckTestInfoHolder.getResultActions();
        Optional<NhiTreatment> violationNhiTreatmentOpt = findLastNhiTreatment(issueNhiCode);
        String message = null;

        if (violationNhiTreatmentOpt.isPresent()) {
            NhiTreatment violationNhiTreatment = violationNhiTreatmentOpt.get();
            String type = findSourceType(violationNhiTreatment);
            String date = transformA71ToDisplay(violationNhiTreatment.getDatetime());
            message = formatMsg().apply(D4_1, new Object[]{issueNhiCode, type, date});
        }

        checkResult(resultActions, passOrNot, message);
    }

    @Then("檢查 {word} 診療項目，在病患過去 {int} 天紀錄中，不應包含特定的 {word} 診療代碼，確認結果是否為 {passOrNot} 且檢查訊息類型為 {msgFormat}")
    public void checkCodeBeforeDate(String issueNhiCode, int dayGap, String treatmentNhiCode, Boolean passOrNot, NhiRuleCheckFormat msgFormat) throws Exception {
        ResultActions resultActions = nhiRuleCheckTestInfoHolder.getResultActions();
        Disposal issueDisposal = disposalTestInfoHolder.getDisposal();
        NhiTreatment violationNhiTreatment = findLastNhiTreatment(treatmentNhiCode).get();
        String type = findSourceType(violationNhiTreatment);
        String pastTreatmentDate = transformA71ToDisplay(violationNhiTreatment.getDatetime());
        Object[] msgArgs = null;

        if (msgFormat == D1_2) {
            msgArgs = new Object[]{issueNhiCode, treatmentNhiCode, type, pastTreatmentDate, dayGap, issueNhiCode};
        } else if (msgFormat == D4_1) {
            msgArgs = new Object[]{issueNhiCode, type, pastTreatmentDate};
        } else if (msgFormat == D7_1) {
            msgArgs = new Object[]{issueNhiCode, dayGap, type, pastTreatmentDate};
        } else if (msgFormat == W1_1) {
            msgArgs = new Object[]{issueNhiCode, treatmentNhiCode, type, pastTreatmentDate, dayGap, issueNhiCode, transformLocalDateToRocDateForDisplay(issueDisposal.getDateTime())};
        } else if (msgFormat == PERIO_1) {
            msgArgs = new Object[]{issueNhiCode};
        }

        String message = formatMsg().apply(msgFormat, msgArgs);
        checkResult(resultActions, passOrNot, message);
    }

    @Then("在同月份中，不得申報 {word} 診療代碼，確認結果是否為 {passOrNot} 且檢查訊息類型為 {msgFormat}")
    public void checkCodeBeforeDate2(String treatmentNhiCode, Boolean passOrNot, NhiRuleCheckFormat msgFormat) throws Exception {
        String nhiCode = nhiRuleCheckTestInfoHolder.getNhiCode();
        ResultActions resultActions = nhiRuleCheckTestInfoHolder.getResultActions();
        NhiTreatment violationNhiTreatment = findLastNhiTreatment(treatmentNhiCode).get();
        String type = findSourceType(violationNhiTreatment);
        String pastTreatmentDate = transformA71ToDisplay(violationNhiTreatment.getDatetime());
        Object[] msgArgs = null;

        if (msgFormat == W4_1) {
            msgArgs = new Object[]{nhiCode, treatmentNhiCode, type, pastTreatmentDate};
        }

        String message = formatMsg().apply(msgFormat, msgArgs);
        checkResult(resultActions, passOrNot, message);
    }

    @Then("同日或同處置單不得申報 {word} 診療代碼，確認結果是否為 {passOrNot} 且檢查訊息類型為 {msgFormat}")
    public void checkCurrentDateHasCode(String treatmentNhiCode, Boolean passOrNot, NhiRuleCheckFormat msgFormat) throws Exception {
        String nhiCode = nhiRuleCheckTestInfoHolder.getNhiCode();
        ResultActions resultActions = nhiRuleCheckTestInfoHolder.getResultActions();
        NhiTreatment violationNhiTreatment = findLastNhiTreatment(treatmentNhiCode).get();
        String type = findSourceType(violationNhiTreatment);
        String pastTreatmentDate = transformA71ToDisplay(violationNhiTreatment.getDatetime());
        Object[] msgArgs = null;

        if (msgFormat == W4_1) {
            msgArgs = new Object[]{nhiCode, treatmentNhiCode, type, pastTreatmentDate};
        }

        String message = formatMsg().apply(msgFormat, msgArgs);
        checkResult(resultActions, passOrNot, message);
    }

    @Then("檢查同一處置單，是否沒有健保定義的 {word} 重複診療衝突，確認結果是否為 {passOrNot}")
    public void checkNoSelfConflictNhiCode(String treatmentNhiCode, Boolean passOrNot) throws Exception {
        String nhiCode = nhiRuleCheckTestInfoHolder.getNhiCode();
        ResultActions resultActions = nhiRuleCheckTestInfoHolder.getResultActions();
        String message = passOrNot ? null : "同處置單已有 " + nhiCode;
        checkResult(resultActions, passOrNot, message);
    }

    @Then("病患是否在診療 {word} 當下年紀未滿 6 歲，確認結果是否為 {passOrNot}")
    public void checkLessThanAge6(String issueNhiCode, Boolean passOrNot) throws Exception {
        String nhiCode = nhiRuleCheckTestInfoHolder.getNhiCode();
        ResultActions resultActions = nhiRuleCheckTestInfoHolder.getResultActions();
        String message = formatMsg().apply(D3_1, new Object[]{nhiCode, "未滿六歲"});
        checkResult(resultActions, passOrNot, message);
    }

    @Then("病患是否在診療 {word} 當下年紀未滿 12 歲，確認結果是否為 {passOrNot}")
    public void checkLessThanAge12(String issueNhiCode, Boolean passOrNot) throws Exception {
        String nhiCode = nhiRuleCheckTestInfoHolder.getNhiCode();
        ResultActions resultActions = nhiRuleCheckTestInfoHolder.getResultActions();
        String message = formatMsg().apply(D3_1, new Object[]{nhiCode, "未滿十二歲"});
        checkResult(resultActions, passOrNot, message);
    }

    @Then("病患是否在診療 {word} 當下年紀年滿 30 歲，確認結果是否為 {passOrNot}")
    public void checkLessThanAge30(String issueNhiCode, Boolean passOrNot) throws Exception {
        String nhiCode = nhiRuleCheckTestInfoHolder.getNhiCode();
        ResultActions resultActions = nhiRuleCheckTestInfoHolder.getResultActions();
        String message = formatMsg().apply(D3_1, new Object[]{nhiCode, "年滿三十歲"});
        checkResult(resultActions, passOrNot, message);
    }

    @Then("病患是否在診療 {word} 當下年紀未滿 30 歲及大於 17 歲，確認結果是否為 {passOrNot}")
    public void checkLessThanAge30AndGreatThanAge17(String issueNhiCode, Boolean passOrNot) throws Exception {
        String nhiCode = nhiRuleCheckTestInfoHolder.getNhiCode();
        ResultActions resultActions = nhiRuleCheckTestInfoHolder.getResultActions();
        String message = formatMsg().apply(D3_1, new Object[]{nhiCode, "年滿十八歲且未滿三十歲"});
        checkResult(resultActions, passOrNot, message);
    }

    @Then("確認診療代碼 {word} 是否有自身健保診療重複突衝 ，確認結果是否為 {passOrNot}")
    public void checkNoDuplicatedNhiCodes(String issueNhiCode, Boolean passOrNot) throws Exception {
        ResultActions resultActions = nhiRuleCheckTestInfoHolder.getResultActions();
        String message = "同處置單已有 " + issueNhiCode;
        checkResult(resultActions, passOrNot, message);
    }

    @Then("在 {word} 的紀錄中，牙位 {word} 在同一象限中，最多只能申報 {int} 次 {word} 健保代碼，確認結果是否為 {passOrNot}")
    public void checkNoSameNhiCodesInLimitDaysAndSamePhase(String period, String issueTeeth, int times, String issueNhiCode, Boolean passOrNot) throws Exception {
        ResultActions resultActions = nhiRuleCheckTestInfoHolder.getResultActions();
        NhiTreatment violationNhiTreatment = findLastNhiTreatment(issueNhiCode).get();
        String type = findSourceType(violationNhiTreatment);
        ToothPhase toothPhase = markAsPhase(singletonList(violationNhiTreatment.getTooth())).get(0);
        String issueDate = transformA71ToDisplay(violationNhiTreatment.getDatetime());
        String message = formatMsg().apply(D4_2, new Object[]{issueNhiCode, type, issueDate, toothPhase.getNameOfPhase()});
        checkResult(resultActions, passOrNot, message);
    }

    @Then("檢查 {word} 診療項目，在病患過去 {word} 紀錄中，不應包含特定的 {word} 診療代碼，確認結果是否為 {passOrNot} 且檢查訊息類型為 {msgFormat}")
    public void checkCodeBetweenDuration(String issueNhiCode, String gapMonth, String treatmentNhiCode, Boolean passOrNot, NhiRuleCheckFormat msgFormat) throws Exception {
        String nhiCode = nhiRuleCheckTestInfoHolder.getNhiCode();
        ResultActions resultActions = nhiRuleCheckTestInfoHolder.getResultActions();
        NhiTreatment violationNhiTreatment = findLastNhiTreatment(treatmentNhiCode).get();
        String type = findSourceType(violationNhiTreatment);
        String pastTreatmentDate = transformA71ToDisplay(violationNhiTreatment.getDatetime());
        Object[] msgArgs = null;

        if (msgFormat == D4_1) {
            msgArgs = new Object[]{nhiCode, type, pastTreatmentDate};
        } else if (msgFormat == D1_2_2) {
            msgArgs = new Object[]{issueNhiCode, treatmentNhiCode, type, pastTreatmentDate, parseMonthGap(gapMonth), issueNhiCode};
        }

        String message = formatMsg().apply(msgFormat, msgArgs);
        checkResult(resultActions, passOrNot, message);
    }

    @Then("在 {word} 的記錄中，{word} 診療代碼最多只能 {int} 次，確認結果是否為 {passOrNot}")
    public void checkCodeBeforeDateWithMaxTimes(String period, String issueNhiCode, int times, Boolean passOrNot) throws Exception {
        boolean presentInSnapshot = nhiRuleCheckTestInfoHolder.getNhiRuleCheckTxSnapshotList().stream().filter(snapshot -> snapshot.getNhiCode().equals(issueNhiCode)).findFirst().isPresent();
        ResultActions resultActions = nhiRuleCheckTestInfoHolder.getResultActions();
        String dates = findSpecificNhiTreatment(asList(issueNhiCode))
            .stream()
            .map(NhiTreatment::getDatetime)
            .sorted(reverseOrder())
            .map(DateTimeUtil::transformA71ToDisplay)
            .collect(joining(", "));

        if (presentInSnapshot) {
            String current = DateTimeUtil.transformLocalDateToRocDateForDisplay(disposalTestInfoHolder.getDisposal().getDateTime());
            if (!dates.contains(current)) {
                dates = current.concat(", ").concat(dates);
            }
        }

        String message = formatMsg().apply(D5_1, new Object[]{issueNhiCode, dates});
        checkResult(resultActions, passOrNot, message);
    }

    @Then("在 {word} 年內的記錄中，{word} 診療代碼已達 {int} 次以上，不得申報 {word}，確認結果是否為 {passOrNot}")
    public void checkCodeBeforeDateWithMaxTimes2(String period, String treatmentNhiCode, int times, String issueNhiCode, Boolean passOrNot) throws Exception {
        ResultActions resultActions = nhiRuleCheckTestInfoHolder.getResultActions();
        NhiTreatment violationNhiTreatment = findLastNhiTreatment(treatmentNhiCode).get();
        String type = findSourceType(violationNhiTreatment);
        String date = transformA71ToDisplay(violationNhiTreatment.getDatetime());
        String message = formatMsg().apply(D1_2_3, new Object[]{issueNhiCode, treatmentNhiCode, type, date, period, issueNhiCode});
        checkResult(resultActions, passOrNot, message);
    }

    @Then("需曾經申報過 {nhiCodeList}，確認結果是否為 {passOrNot}")
    public void checkTreatmentDependOnCode(List<String> treatmentNhiCodes, Boolean passOrNot) throws Exception {
        String nhiCode = nhiRuleCheckTestInfoHolder.getNhiCode();
        ResultActions resultActions = nhiRuleCheckTestInfoHolder.getResultActions();
        String message = formatMsg().apply(D8_2, new Object[]{nhiCode, join("/", treatmentNhiCodes)});
        checkResult(resultActions, passOrNot, message);
    }

    @Then("檢查 {int} 天內，應有 {word} 診療項目存在，確認結果是否為 {passOrNot}")
    public void checkTreatmentDependOnCodeInDuration(Integer dayRange, String treatmentNhiCode, Boolean passOrNot) throws Exception {
        String nhiCode = nhiRuleCheckTestInfoHolder.getNhiCode();
        ResultActions resultActions = nhiRuleCheckTestInfoHolder.getResultActions();
        String message = formatMsg().apply(D8_1, new Object[]{nhiCode, dayRange, treatmentNhiCode});
        checkResult(resultActions, passOrNot, message);
    }

    @Then("檢查 {int} 天內，應有 {word} 診療項目存在，確認結果是否為 {passOrNot} 且檢查訊息類型為 {msgFormat}")
    public void checkTreatmentDependOnCodeInDuration2(Integer dayRange, String treatmentNhiCode, Boolean passOrNot, NhiRuleCheckFormat msgFormat) throws Exception {
        String nhiCode = nhiRuleCheckTestInfoHolder.getNhiCode();
        ResultActions resultActions = nhiRuleCheckTestInfoHolder.getResultActions();
        Object[] msgArgs = null;

        if (msgFormat == PERIO_1) {
            msgArgs = new Object[]{nhiCode};
        } else if (msgFormat == D8_1) {
            msgArgs = new Object[]{nhiCode, dayRange, treatmentNhiCode};
        }

        String message = formatMsg().apply(msgFormat, msgArgs);
        checkResult(resultActions, passOrNot, message);
    }

    @Then("同日不得有 {nhiCodeList} 診療項目，確認結果是否為 {passOrNot}")
    public void checkNoTreatmentOnCodeToday(List<String> forbiddenNhiCodes, Boolean passOrNot) throws Exception {
        ResultActions resultActions = nhiRuleCheckTestInfoHolder.getResultActions();
        String issueNhiCode = nhiRuleCheckTestInfoHolder.getNhiCode();
        String message = formatMsg().apply(D6_1, new Object[]{issueNhiCode, join("/", forbiddenNhiCodes)});
        checkResult(resultActions, passOrNot, message);
    }

    @Then("同日得有 {nhiCodeList} 診療項目，確認結果是否為 {passOrNot}")
    public void checkTreatmentDependOnCodeToday(List<String> dependNhiCodes, Boolean passOrNot) throws Exception {
        ResultActions resultActions = nhiRuleCheckTestInfoHolder.getResultActions();
        String issueNhiCode = nhiRuleCheckTestInfoHolder.getNhiCode();
        String message = formatMsg().apply(W3_1, new Object[]{issueNhiCode, join("/", parseNhiCode(dependNhiCodes))});
        checkResult(resultActions, passOrNot, message);
    }

    @Then("{word} 不得單獨申報，確認結果是否為 {passOrNot}")
    public void checkAnyOtherTreatment(String issueNhiCode, Boolean passOrNot) throws Exception {
        ResultActions resultActions = nhiRuleCheckTestInfoHolder.getResultActions();
        String message = formatMsg().apply(W5_1, new Object[]{issueNhiCode});
        checkResult(resultActions, passOrNot, message);
    }

    @Then("{word} 終生只能申報一次，確認結果是否為 {passOrNot} 且檢查訊息類型為 {msgFormat}")
    public void checkOnceInWholeLife(String issueNhiCode, Boolean passOrNot, NhiRuleCheckFormat msgFormat) throws Exception {
        ResultActions resultActions = nhiRuleCheckTestInfoHolder.getResultActions();
        Optional<NhiTreatment> violationNhiTreatmentOpt = findLastNhiTreatment(issueNhiCode);
        String message = null;

        if (violationNhiTreatmentOpt.isPresent()) {
            NhiTreatment violationNhiTreatment = violationNhiTreatmentOpt.get();
            String date = transformA71ToDisplay(violationNhiTreatment.getDatetime());
            String type = findSourceType(violationNhiTreatment);
            Object[] msgArgs = null;

            if (msgFormat == D2_1) {
                msgArgs = new Object[]{issueNhiCode, type, date};
            } else if (msgFormat == D2_2) {
                msgArgs = new Object[]{issueNhiCode, type, date};
            }

            message = formatMsg().apply(msgFormat, msgArgs);
        }

        checkResult(resultActions, passOrNot, message);
    }

    @Then("每位醫生限申報一次 {word} 代碼，確認結果是否為 {passOrNot}")
    public void checkOnceInWholeLifeAtDoctor(String issueNhiCode, Boolean passOrNot) throws Exception {
        ResultActions resultActions = nhiRuleCheckTestInfoHolder.getResultActions();
        Optional<NhiTreatment> violationNhiTreatmentOpt = findLastNhiTreatment(issueNhiCode);
        String message = null;

        if (violationNhiTreatmentOpt.isPresent()) {
            NhiTreatment violationNhiTreatment = violationNhiTreatmentOpt.get();
            String type = findSourceType(violationNhiTreatment);
            String date = transformA71ToDisplay(violationNhiTreatment.getDatetime());
            message = formatMsg().apply(D2_3, new Object[]{issueNhiCode, type, date});
        }

        checkResult(resultActions, passOrNot, message);
    }

    private void checkNoticeResult(ResultActions resultActions, boolean passOrNot, String message) throws Exception {
        checkNoticeResult(resultActions, passOrNot, message, true);
    }

    private void checkNoticeResult(ResultActions resultActions, boolean passOrNot, String message, boolean show) throws Exception {
        String innerPath = "$.checkHistory[?(@.message == '" + message + "')]";
        resultActions.andExpect(jsonPath("$.validated").value(equalTo(passOrNot)));

        if (passOrNot && show) {
            resultActions.andExpect(jsonPath(innerPath + ".message").value(hasItem(message)));
        } else if (!show) {
            resultActions.andExpect(jsonPath(innerPath + ".message").value(empty()));
        }
    }

    private void checkResult(ResultActions resultActions, boolean passOrNot, String message) throws Exception {
        checkResult(resultActions, passOrNot, passOrNot, message);
    }

    private void checkResult(ResultActions resultActions, boolean passOrNot, boolean innerPassOrNot, String message) throws Exception {
        String innerPath = "$.checkHistory[?(@.message == '" + message + "')]";
//        暫不檢查 validated，因為目前代碼檢查機制是每項檢核有錯誤，都會讓validated為false，所以即使測試檢核A通過，但也有可能被檢核B測試不通過影響到
//        resultActions.andExpect(jsonPath("$.validated").value(equalTo(passOrNot)));

        if (innerPassOrNot == false) {
            assertThat(message).isNotEmpty();
            resultActions.andExpect(jsonPath(innerPath + ".message").value(hasItem(message)));
            resultActions.andExpect(jsonPath(innerPath + ".validated").value(hasItem(false)));
        } else {
            // 這邊檢查會有盲點，因為參數 message 有可能與回傳的檢查訊息本來就是不一樣，所以測試會照樣通過，所以測案至少要有反例最好
            resultActions.andExpect(jsonPath(innerPath).value(empty()));
        }
    }

    private void checkValidatedResult(ResultActions resultActions, boolean passOrNot) throws Exception {
        resultActions.andExpect(jsonPath("$.validated").value(equalTo(passOrNot)));
    }

    private List<NhiTreatment> mergeTreatmentAndMedical() {
        List<Disposal> disposals = disposalTestInfoHolder.getDisposalHistoryList();
        List<NhiMedicalRecord> nhiMedicalRecords = nhiMedicalRecordTestInfoHolder.getNhiMedicalRecordList();

        Stream<NhiTreatment> stream1 = disposals.stream()
            .flatMap(disposal -> disposal.getTreatmentProcedures().stream())
            .map(TreatmentProcedure::getNhiExtendTreatmentProcedure)
            .map(NhiTreatmentTestMapper.INSTANCE::netpToNhiTreatment);

        Stream<NhiTreatment> stream2 = nhiMedicalRecords.stream()
            .map(NhiTreatmentTestMapper.INSTANCE::medicalToNhiTreatment);

        return Stream.of(stream1, stream2).flatMap(stream -> stream).collect(Collectors.toList());
    }

    private List<NhiTreatment> nhiSnapshotToNhiTreatment() {
        return nhiRuleCheckTestInfoHolder.getNhiRuleCheckTxSnapshotList().stream()
            .map(NhiTreatmentTestMapper.INSTANCE::snapshotToNhiTreatment)
            .collect(Collectors.toList());
    }

    private List<NhiTreatment> nhiSnapshotToNhiTreatmentExcludeValidationNhiCode(List<String> teeth) {
        String validationNhiCode = nhiRuleCheckTestInfoHolder.getNhiCode();
        List<NhiTreatment> snapshot = nhiSnapshotToNhiTreatment();
        Iterator<NhiTreatment> itor = snapshot.iterator();
        while (itor.hasNext()) {
            NhiTreatment nt = itor.next();
            if (nt.getCode().equals(validationNhiCode)) {
                if (teeth.size() == 0 || teeth.stream().anyMatch(tooth -> nt.getTooth().contains(tooth))) {
                    itor.remove();
                    break;
                }
            }
        }
        return snapshot;
    }

    private List<NhiTreatment> findNhiTreatment() {
        return findNhiTreatment(new ArrayList<>(0));
    }

    private List<NhiTreatment> findNhiTreatment(List<String> teeth) {
        List<NhiTreatment> snapshot = nhiSnapshotToNhiTreatmentExcludeValidationNhiCode(teeth).stream()
            .filter(nt -> nt.getNhiExtendTreatmentProcedureId() == null)
            .collect(Collectors.toList());

        List<NhiTreatment> history = mergeTreatmentAndMedical().stream()
            .filter(nt -> !disposalTestInfoHolder.getDisposal().getId().equals(nt.getDisposalId()))
            .collect(Collectors.toList());

        snapshot.addAll(history);

        return snapshot;
    }

    private List<NhiTreatment> findSpecificNhiTreatment(List<String> nhiCodes) {
        return findSpecificNhiTreatment(nhiCodes, new ArrayList<>(0));
    }

    private List<NhiTreatment> findSpecificNhiTreatment(List<String> nhiCodes, List<String> teeth) {
        return findNhiTreatment(teeth).stream()
            .filter(nt -> nhiCodes.contains(nt.getCode()))
            .filter(nt -> teeth.size() == 0 || teeth.stream().anyMatch(tooth -> nt.getTooth().contains(tooth)))
            .collect(Collectors.toList());
    }

    private Optional<NhiTreatment> findLastNhiTreatment(String nhiCode) {
        return findLastNhiTreatment(nhiCode, new ArrayList<>(0));
    }

    private Optional<NhiTreatment> findLastNhiTreatment(String nhiCode, List<String> teeth) {
        List<NhiTreatment> violations = findSpecificNhiTreatment(asList(nhiCode), teeth);
        return violations.stream().min((nt1, nt2) -> nt2.getDatetime().compareTo(nt1.getDatetime()));
    }

    private String findSourceType(NhiTreatment nhiTreatment) {
        Disposal issueDisposal = disposalTestInfoHolder.getDisposal();

        if (IC_SourceType.equals(nhiTreatment.getSourceType())) {
            return NHI_CARD_RECORD.getValue();
        } else if (Snapshot_SourceType.equals(nhiTreatment.getSourceType())) {
            return CURRENT_DISPOSAL.getValue();
        } else if (transformLocalDateToRocDateForDisplay(issueDisposal.getDateTime()).equals(transformA71ToDisplay(nhiTreatment.getDatetime()))) {
            return TODAY_OTHER_DISPOSAL.getValue();
        } else if (HIS_SourceType.equals(nhiTreatment.getSourceType())) {
            return SYSTEM_RECORD.getValue();
        } else {
            return "SourceTypeNotFound";
        }
    }

    public List<String> parseNhiCode(List<String> nhiCodes) {
        List<String> result = new ArrayList<>();

        if (nhiCodes == null ||
            nhiCodes.isEmpty()
        ) {
            return result;
        }

        for (String code : nhiCodes) {
            try {
                String[] tildeCodes = code.split("~");
                if (tildeCodes.length != 2) {
                    result.add(code);
                    continue;
                }

                Pattern pattern = Pattern.compile("(^([1-9][0-9]{0,4})([A-Z])$|^([1-9][0-9]{0,5})$)");

                Matcher lowCodeMatcher = pattern.matcher(tildeCodes[0]);
                Matcher highCodeMatcher = pattern.matcher(tildeCodes[1]);

                if (lowCodeMatcher.matches() &&
                    highCodeMatcher.matches() &&
                    lowCodeMatcher.group(2) != null &&
                    lowCodeMatcher.group(3) != null &&
                    highCodeMatcher.group(2) != null &&
                    highCodeMatcher.group(3) != null
                ) {
                    Integer lowCodeNumber = Integer.parseInt(lowCodeMatcher.group(2));
                    String lowCodeAlpha = lowCodeMatcher.group(3);
                    Integer highCodeNumber = Integer.parseInt(highCodeMatcher.group(2));
                    String highCodeAlpha = highCodeMatcher.group(3);

                    if (lowCodeAlpha.equals(highCodeAlpha) &&
                        lowCodeNumber < highCodeNumber
                    ) {
                        for (int i = lowCodeNumber; i <= highCodeNumber; i++) {
                            result.add(String.valueOf(i).concat(lowCodeAlpha));
                        }
                    } else {
                        result.add(code);
                    }

                } else if (lowCodeMatcher.matches() &&
                    highCodeMatcher.matches() &&
                    lowCodeMatcher.group(4) != null &&
                    highCodeMatcher.group(4) != null
                ) {
                    Integer lowCodeNumber = Integer.parseInt(lowCodeMatcher.group(4));
                    Integer highCodeNumber = Integer.parseInt(highCodeMatcher.group(4));

                    for (int i = lowCodeNumber; i <= highCodeNumber; i++) {
                        result.add(String.valueOf(i));
                    }

                } else {
                    result.add(code);
                    continue;
                }


            } catch (NumberFormatException e) {
                // do nothing
            }
        }

        return result;
    }
}
