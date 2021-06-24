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
import io.dentall.totoro.business.service.nhi.util.ToothUtil;
import io.dentall.totoro.business.vm.nhi.NhiRuleCheckBody;
import io.dentall.totoro.business.vm.nhi.NhiRuleCheckTxSnapshot;
import io.dentall.totoro.domain.*;
import io.dentall.totoro.repository.NhiExtendTreatmentProcedureRepository;
import io.dentall.totoro.service.util.DateTimeUtil;
import io.dentall.totoro.step_definitions.holders.DisposalTestInfoHolder;
import io.dentall.totoro.step_definitions.holders.NhiMedicalRecordTestInfoHolder;
import io.dentall.totoro.step_definitions.holders.NhiRuleCheckTestInfoHolder;
import io.dentall.totoro.step_definitions.holders.PatientTestInfoHolder;
import io.dentall.totoro.test.TestUtils;
import io.dentall.totoro.test.dao.NhiTreatment;
import io.dentall.totoro.test.mapper.NhiTreatmentTestMapper;
import io.dentall.totoro.web.rest.NhiRuleCheckResource;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.dentall.totoro.business.service.NhiRuleCheckSourceType.*;
import static io.dentall.totoro.business.service.nhi.util.ToothUtil.multipleToothToDisplay;
import static io.dentall.totoro.service.util.DateTimeUtil.*;
import static io.dentall.totoro.test.TestUtils.parseMonthGap;
import static io.dentall.totoro.web.rest.TestUtil.createFormattingConversionService;
import static java.lang.String.join;
import static java.util.Collections.singletonList;
import static java.util.Comparator.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


public class NhiRuleCheckStepDefinition extends AbstractStepDefinition {

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

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NhiRuleCheckResource resource = new NhiRuleCheckResource(nhiRuleCheckUtil);
        this.mvc = MockMvcBuilders.standaloneSetup(resource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .build();
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
        return Arrays.asList(nhiCodes.split("/"));
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

        NhiRuleCheckBody nhiRuleCheckBody = new NhiRuleCheckBody();
        nhiRuleCheckBody.setDisposalId(disposal.getId());
        nhiRuleCheckBody.setPatientId(patient.getId());
        nhiRuleCheckBody.setTxSnapshots(nhiRuleCheckTxSnapshotList);
        nhiRuleCheckBody.setDisposalTime(transformLocalDateToRocDate(disposal.getDateTime()));

        String apiPath = "/api/validation";
        String fullApiPath = apiPath + "/" + code;
        MockHttpServletRequestBuilder requestBuilder = post(fullApiPath)
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsBytes(nhiRuleCheckBody));

        ResultActions resultActions = mvc.perform(requestBuilder);
        resultActions.andDo(print());

        nhiRuleCheckTestInfoHolder.setNhiCode(code);
        nhiRuleCheckTestInfoHolder.setResultActions(resultActions);
        nhiRuleCheckTestInfoHolder.setNhiRuleCheckTxSnapshotList(nhiRuleCheckTxSnapshotList);
    }

    @Then("確認診療代碼 {word} ，確認結果是否為 {passOrNot}")
    public void confirmNhiCode(String code, Boolean passOrNot) throws Exception {
        ResultActions resultActions = nhiRuleCheckTestInfoHolder.getResultActions();
        String message = "系統及健保卡皆無紀錄，請查詢雲端藥歷取得正確資訊";
        checkNoticeResult(resultActions, passOrNot, message);
    }

    @Then("（HIS）在過去 {int} 天，應沒有任何治療紀錄，確認結果是否為 {passOrNot}")
    public void checkNoTreatmentInPeriod(int pastDays, Boolean passOrNot) throws Exception {
        String nhiCode = nhiRuleCheckTestInfoHolder.getNhiCode();
        List<Disposal> disposalList = disposalTestInfoHolder.getDisposalHistoryList();
        ResultActions resultActions = nhiRuleCheckTestInfoHolder.getResultActions();

        TreatmentProcedure newestTreatmentProcedure =
            mergeDisposalToTreatmentProcedure(disposalList).stream().min(comparing(tp -> tp.getDisposal().getDateTime())).get();

        String message = formatMsg(!passOrNot).apply(NhiRuleCheckFormat.D1_3, new Object[]{nhiCode, multipleToothToDisplay(newestTreatmentProcedure.getNhiExtendTreatmentProcedure().getA74()), nhiCode, SYSTEM_RECORD.getValue(), transformLocalDateToRocDateForDisplay(pastInstant(pastDays))});
        checkResult(resultActions, passOrNot, message);
    }

    @Then("（HIS）病患的牙齒 {word} 在 {int} 天前，被申報 {word} 健保代碼，而現在病患的牙齒 {word} 要被申報 {word} 健保代碼，是否抵獨同顆牙齒在 {int} 天內不得申報指定健保代碼，確認結果是否為 {passOrNot} 且檢查訊息類型為 {msgFormat}")
    public void checkPatientToothAtCodesBeforePeriod(String pastTeeth, int pastDays, String pastNhiCode, String issueTeeth, String issueNhiCode, int dayRange, Boolean passOrNot, NhiRuleCheckFormat msgFormat) throws Exception {
        doCheckPatientToothAtCodesBeforePeriod(pastTeeth, pastDays, pastNhiCode, issueTeeth, issueNhiCode, dayRange, passOrNot, passOrNot, msgFormat);
    }

    @Then("（HIS）病患的牙齒 {word} 在 {int} 天前，被申報 {word} 健保代碼，而現在病患的牙齒 {word} 要被申報 {word} 健保代碼，是否抵獨同顆牙齒在 {int} 天內不得申報指定健保代碼，確認主要結果是否為 {passOrNot} 和細項結果是否為 {passOrNot} 且檢查訊息類型為 {msgFormat}")
    public void checkPatientToothAtCodesBeforePeriod2(String pastTeeth, int pastDays, String pastNhiCode, String issueTeeth, String issueNhiCode, int dayRange, Boolean passOrNot, Boolean innerPassOrNot, NhiRuleCheckFormat msgFormat) throws Exception {
        doCheckPatientToothAtCodesBeforePeriod(pastTeeth, pastDays, pastNhiCode, issueTeeth, issueNhiCode, dayRange, passOrNot, innerPassOrNot, msgFormat);
    }

    @Then("（IC）病患的牙齒 {word} 在 {int} 天前，被申報 {word} 健保代碼，而現在病患的牙齒 {word} 要被申報 {word} 健保代碼，是否抵獨同顆牙齒在 {int} 天內不得申報指定健保代碼，確認結果是否為 {passOrNot} 且檢查訊息類型為 {msgFormat}")
    public void checkPatientToothAtCodesBeforePeriodByNhiMedicalRecord(String pastTeeth, int pastDays, String pastNhiCode, String issueTeeth, String issueNhiCode, int dayRange, Boolean passOrNot, NhiRuleCheckFormat msgFormat) throws Exception {
        doCheckPatientToothAtCodesBeforePeriod(pastTeeth, pastDays, pastNhiCode, issueTeeth, issueNhiCode, dayRange, passOrNot, passOrNot, msgFormat);
    }

    @Then("（IC）病患的牙齒 {word} 在 {int} 天前，被申報 {word} 健保代碼，而現在病患的牙齒 {word} 要被申報 {word} 健保代碼，是否抵獨同顆牙齒在 {int} 天內不得申報指定健保代碼，確認主要結果是否為 {passOrNot} 和細項結果是否為 {passOrNot} 且檢查訊息類型為 {msgFormat}")
    public void checkPatientToothAtCodesBeforePeriodByNhiMedicalRecord(String pastTeeth, int pastDays, String pastNhiCode, String issueTeeth, String issueNhiCode, int dayRange, Boolean passOrNot, Boolean innerPassOrNot, NhiRuleCheckFormat msgFormat) throws Exception {
        doCheckPatientToothAtCodesBeforePeriod(pastTeeth, pastDays, pastNhiCode, issueTeeth, issueNhiCode, dayRange, passOrNot, innerPassOrNot, msgFormat);
    }

    public void doCheckPatientToothAtCodesBeforePeriod(String pastTeeth, int pastDays, String pastNhiCode, String issueTeeth, String issueNhiCode, int dayRange, Boolean passOrNot, Boolean innerPassOrNot, NhiRuleCheckFormat msgFormat) throws Exception {
        String nhiCode = nhiRuleCheckTestInfoHolder.getNhiCode();
        ResultActions resultActions = nhiRuleCheckTestInfoHolder.getResultActions();
        String type = SYSTEM_RECORD.getValue();
        List<NhiMedicalRecord> nhiMedicalRecords = nhiMedicalRecordTestInfoHolder.getNhiMedicalRecordList();

        if (pastDays == 0) {
            type = TODAY_OTHER_DISPOSAL.getValue();
        }

        if (nhiMedicalRecords.size() > 0) {
            type = NHI_CARD_RECORD.getValue();
        }

        Object[] args = new Object[0];

        if (msgFormat == NhiRuleCheckFormat.D1_3) {
            args = new Object[]{nhiCode, issueTeeth, pastNhiCode, type, transformLocalDateToRocDateForDisplay(pastInstant(pastDays))};
        } else if (msgFormat == NhiRuleCheckFormat.D7_2 || msgFormat == NhiRuleCheckFormat.W6_1) {
            args = new Object[]{nhiCode, dayRange, pastNhiCode, type, transformLocalDateToRocDateForDisplay(pastInstant(pastDays)), pastTeeth};
        } else if (msgFormat == NhiRuleCheckFormat.D1_2) {
            args = new Object[]{nhiCode, pastNhiCode, type, transformLocalDateToRocDateForDisplay(pastInstant(pastDays)), dayRange, nhiCode};
        }

        String message = formatMsg(!passOrNot).apply(msgFormat, args);
        checkResult(resultActions, passOrNot, innerPassOrNot, message);
    }

    @Then("限制牙面在 {int} 以上，確認結果是否為 {passOrNot}")
    public void checkAllLimitedSurface(int surfaceCount, Boolean passOrNot) throws Exception {
        ResultActions resultActions = nhiRuleCheckTestInfoHolder.getResultActions();
        String message = passOrNot ? null : "牙面數不可小於 " + surfaceCount;
        checkResult(resultActions, passOrNot, message);
    }

    @Then("（HIS）任意時間點未曾申報過指定代碼 {word}，確認結果是否為 {passOrNot}")
    public void checkNoTreatment(String specificNhiCode, Boolean passOrNot) throws Exception {
        String nhiCode = nhiRuleCheckTestInfoHolder.getNhiCode();
        List<Disposal> disposalList = disposalTestInfoHolder.getDisposalHistoryList();
        ResultActions resultActions = nhiRuleCheckTestInfoHolder.getResultActions();
        TreatmentProcedure newestTreatmentProcedure = mergeDisposalToTreatmentProcedure(disposalList).stream().min(comparing(tp -> tp.getDisposal().getDateTime())).get();
        String message = formatMsg(!passOrNot).apply(NhiRuleCheckFormat.D1_1, new Object[]{nhiCode, specificNhiCode, "系統", transformA71ToDisplay(newestTreatmentProcedure.getNhiExtendTreatmentProcedure().getA71()), nhiCode});
        checkResult(resultActions, passOrNot, message);
    }

    @Then("（IC）任意時間點未曾申報過指定代碼 {word}，確認結果是否為 {passOrNot}")
    public void checkNoTreatmentByNhiMedicalRecord(String specificNhiCode, Boolean passOrNot) throws Exception {
        String nhiCode = nhiRuleCheckTestInfoHolder.getNhiCode();
        List<NhiMedicalRecord> nhiMedicalRecordList = nhiMedicalRecordTestInfoHolder.getNhiMedicalRecordList();
        ResultActions resultActions = nhiRuleCheckTestInfoHolder.getResultActions();
        NhiMedicalRecord nhiMedicalRecord = nhiMedicalRecordList.stream().min(comparing(NhiMedicalRecord::getDate)).get();
        String message = formatMsg(!passOrNot).apply(NhiRuleCheckFormat.D1_1, new Object[]{nhiCode, specificNhiCode, NHI_CARD_RECORD.getValue(), transformA71ToDisplay(nhiMedicalRecord.getDate()), nhiCode});
        checkResult(resultActions, passOrNot, message);
    }

    @Then("89006C 在前 {int} 天建立， {word} 在前 {int} 天建立，確認結果是否為 {passOrNot}")
    public void checkSpecificRule_1_for89XXXC(int _89006CTreatmentDay, String treatmentNhiCode, int pastTreatmentDays, Boolean passOrNot) throws Exception {
        String nhiCode = nhiRuleCheckTestInfoHolder.getNhiCode();
        ResultActions resultActions = nhiRuleCheckTestInfoHolder.getResultActions();
        String message = formatMsg(!passOrNot).apply(NhiRuleCheckFormat.D1_2, new Object[]{nhiCode, "89006C", SYSTEM_RECORD.getValue(), transformLocalDateToRocDateForDisplay(pastInstant(_89006CTreatmentDay)), "30", nhiCode});
        checkResult(resultActions, passOrNot, message);
    }

    @Then("（HIS）同牙 {word} 未曾申報過，指定代碼 {word} ，確認結果是否為 {passOrNot}")
    public void checkNoTreatmentAtSpecificTooth(String issueTeeth, String treatmentNhiCode, Boolean passOrNot) throws Exception {
        String nhiCode = nhiRuleCheckTestInfoHolder.getNhiCode();
        List<Disposal> disposalList = disposalTestInfoHolder.getDisposalHistoryList();
        ResultActions resultActions = nhiRuleCheckTestInfoHolder.getResultActions();

        TreatmentProcedure newestTreatmentProcedure =
            mergeDisposalToTreatmentProcedure(disposalList).stream()
                .filter(tp -> treatmentNhiCode.equals(tp.getNhiExtendTreatmentProcedure().getA73()))
                .min((tp1, tp2) -> tp2.getDisposal().getDateTime().compareTo(tp1.getDisposal().getDateTime()))
                .get();

        String message = formatMsg(!passOrNot).apply(NhiRuleCheckFormat.D1_3, new Object[]{nhiCode, issueTeeth, treatmentNhiCode, SYSTEM_RECORD.getValue(), transformA71ToDisplay(newestTreatmentProcedure.getNhiExtendTreatmentProcedure().getA71())});
        checkResult(resultActions, passOrNot, message);
    }

    @Then("（IC）同牙 {word} 未曾申報過，指定代碼 {word} ，確認結果是否為 {passOrNot}")
    public void checkNoNhiMedicalRecordAtSpecificTooth(String issueTeeth, String treatmentNhiCode, Boolean passOrNot) throws Exception {
        String nhiCode = nhiRuleCheckTestInfoHolder.getNhiCode();
        List<NhiMedicalRecord> nhiMedicalRecordList = nhiMedicalRecordTestInfoHolder.getNhiMedicalRecordList();
        ResultActions resultActions = nhiRuleCheckTestInfoHolder.getResultActions();
        NhiMedicalRecord nhiMedicalRecord = nhiMedicalRecordList.stream().min(comparing(NhiMedicalRecord::getDate)).get();
        String message = formatMsg(!passOrNot).apply(NhiRuleCheckFormat.D1_3, new Object[]{nhiCode, issueTeeth, treatmentNhiCode, NHI_CARD_RECORD.getValue(), transformA71ToDisplay(nhiMedicalRecord.getDate())});
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
        String nhiCodesStr = join("/", nhiCodeList);
        String message = formatMsg(!passOrNot).apply(NhiRuleCheckFormat.W3_1, new Object[]{nhiCodesStr});
        checkResult(resultActions, passOrNot, message);
    }

    @Then("（HIS）在 {int} 天中，不應該有同顆牙 {word} 的 {word} 診療項目，確認結果是否為 {passOrNot}")
    public void check(int dayGap, String issueTeeth, String issueNhiCode, Boolean passOrNot) throws Exception {
        ResultActions resultActions = nhiRuleCheckTestInfoHolder.getResultActions();
        List<Disposal> disposals = disposalTestInfoHolder.getDisposalHistoryList();
        Disposal issueDisposal = disposalTestInfoHolder.getDisposal();
        NhiExtendTreatmentProcedure pastNhiExtendTreatmentProcedure = disposals.get(0).getTreatmentProcedures().stream().findFirst().get().getNhiExtendTreatmentProcedure();

        String type = SYSTEM_RECORD.getValue();

        if (transformLocalDateToRocDate(issueDisposal.getDateTime()).equals(pastNhiExtendTreatmentProcedure.getA71())) {
            type = TODAY_OTHER_DISPOSAL.getValue();
        }

        String message = formatMsg(!passOrNot).apply(NhiRuleCheckFormat.D4_1, new Object[]{issueNhiCode, type, transformA71ToDisplay(pastNhiExtendTreatmentProcedure.getA71())});
        checkResult(resultActions, passOrNot, message);
    }

    @Then("（HIS）檢查 {word} 診療項目，在病患過去 {int} 天紀錄中，不應包含特定的 {word} 診療代碼，確認結果是否為 {passOrNot} 且檢查訊息類型為 {msgFormat}")
    public void checkCodeBeforeDate(String issueNhiCode, int dayGap, String treatmentNhiCode, Boolean passOrNot, NhiRuleCheckFormat msgFormat) throws Exception {
        ResultActions resultActions = nhiRuleCheckTestInfoHolder.getResultActions();
        List<Disposal> disposals = disposalTestInfoHolder.getDisposalHistoryList();
        Disposal issueDisposal = disposalTestInfoHolder.getDisposal();
        NhiExtendTreatmentProcedure pastNhiExtendTreatmentProcedure = disposals.get(0).getTreatmentProcedures().stream().findFirst().get().getNhiExtendTreatmentProcedure();
        String type = SYSTEM_RECORD.getValue();
        String pastTreatmentDate = transformA71ToDisplay(pastNhiExtendTreatmentProcedure.getA71());

        if (transformLocalDateToRocDate(issueDisposal.getDateTime()).equals(pastNhiExtendTreatmentProcedure.getA71())) {
            type = TODAY_OTHER_DISPOSAL.getValue();
        }

        Object[] msgArgs = null;

        if (msgFormat == NhiRuleCheckFormat.D1_2) {
            msgArgs = new Object[]{issueNhiCode, treatmentNhiCode, type, pastTreatmentDate, dayGap, issueNhiCode};
        } else if (msgFormat == NhiRuleCheckFormat.D4_1) {
            msgArgs = new Object[]{issueNhiCode, type, pastTreatmentDate};
        } else if (msgFormat == NhiRuleCheckFormat.D7_1) {
            msgArgs = new Object[]{issueNhiCode, dayGap, treatmentNhiCode, type, pastTreatmentDate};
        } else if (msgFormat == NhiRuleCheckFormat.W1_1) {
            msgArgs = new Object[]{issueNhiCode, treatmentNhiCode, type, pastTreatmentDate, dayGap, issueNhiCode, transformLocalDateToRocDateForDisplay(issueDisposal.getDateTime())};
        } else if (msgFormat == NhiRuleCheckFormat.PERIO_1) {
            msgArgs = new Object[]{issueNhiCode};
        }

        String message = formatMsg(!passOrNot).apply(msgFormat, msgArgs);
        checkResult(resultActions, passOrNot, message);
    }

    @Then("（IC）檢查 {word} 診療項目，在病患過去 {int} 天紀錄中，不應包含特定的 {word} 診療代碼，確認結果是否為 {passOrNot} 且檢查訊息類型為 {msgFormat}")
    public void checkCodeBeforeDateByNhiMedicalRecord(String issueNhiCode, int dayGap, String treatmentNhiCode, Boolean passOrNot, NhiRuleCheckFormat msgFormat) throws Exception {
        ResultActions resultActions = nhiRuleCheckTestInfoHolder.getResultActions();
        List<NhiMedicalRecord> nhiMedicalRecords = nhiMedicalRecordTestInfoHolder.getNhiMedicalRecordList();
        String type = NHI_CARD_RECORD.getValue();
        String pastMedicalDate = transformA71ToDisplay(nhiMedicalRecords.get(0).getDate());
        Object[] msgArgs = null;

        if (msgFormat == NhiRuleCheckFormat.D1_2) {
            msgArgs = new Object[]{issueNhiCode, treatmentNhiCode, type, pastMedicalDate, dayGap, issueNhiCode};
        } else if (msgFormat == NhiRuleCheckFormat.D4_1) {
            msgArgs = new Object[]{issueNhiCode, type, pastMedicalDate};
        }

        String message = formatMsg(!passOrNot).apply(msgFormat, msgArgs);
        checkResult(resultActions, passOrNot, message);
    }

    @Then("（HIS）在同月份中，不得申報 {word} 診療代碼，確認結果是否為 {passOrNot} 且檢查訊息類型為 {msgFormat}")
    public void checkCodeBeforeDate2(String treatmentNhiCode, Boolean passOrNot, NhiRuleCheckFormat msgFormat) throws Exception {
        String nhiCode = nhiRuleCheckTestInfoHolder.getNhiCode();
        ResultActions resultActions = nhiRuleCheckTestInfoHolder.getResultActions();
        List<Disposal> disposals = disposalTestInfoHolder.getDisposalHistoryList();
        Disposal issueDisposal = disposalTestInfoHolder.getDisposal();
        NhiExtendTreatmentProcedure pastNhiExtendTreatmentProcedure = disposals.get(0).getTreatmentProcedures().stream().findFirst().get().getNhiExtendTreatmentProcedure();
        String type = SYSTEM_RECORD.getValue();
        String pastTreatmentDate = transformA71ToDisplay(pastNhiExtendTreatmentProcedure.getA71());

        if (transformLocalDateToRocDate(issueDisposal.getDateTime()).equals(pastNhiExtendTreatmentProcedure.getA71())) {
            type = TODAY_OTHER_DISPOSAL.getValue();
        }

        Object[] msgArgs = null;

        if (msgFormat == NhiRuleCheckFormat.W4_1) {
            msgArgs = new Object[]{nhiCode, treatmentNhiCode, type, pastTreatmentDate};
        }

        String message = formatMsg(!passOrNot).apply(msgFormat, msgArgs);
        checkResult(resultActions, passOrNot, message);
    }

    @Then("（IC）在同月份中，不得申報 {word} 診療代碼，確認結果是否為 {passOrNot} 且檢查訊息類型為 {msgFormat}")
    public void checkCodeBeforeDateByNhiMedicalRecord2(String treatmentNhiCode, Boolean passOrNot, NhiRuleCheckFormat msgFormat) throws Exception {
        String nhiCode = nhiRuleCheckTestInfoHolder.getNhiCode();
        ResultActions resultActions = nhiRuleCheckTestInfoHolder.getResultActions();
        List<NhiMedicalRecord> nhiMedicalRecords = nhiMedicalRecordTestInfoHolder.getNhiMedicalRecordList();
        String type = NHI_CARD_RECORD.getValue();
        String pastMedicalDate = transformA71ToDisplay(nhiMedicalRecords.get(0).getDate());
        Object[] msgArgs = null;

        if (msgFormat == NhiRuleCheckFormat.W4_1) {
            msgArgs = new Object[]{nhiCode, treatmentNhiCode, type, pastMedicalDate};
        }

        String message = formatMsg(!passOrNot).apply(msgFormat, msgArgs);
        checkResult(resultActions, passOrNot, message);
    }

    @Then("同日或同處置單不得申報 {word} 診療代碼，確認結果是否為 {passOrNot} 且檢查訊息類型為 {msgFormat}")
    public void checkCurrentDateHasCode(String treatmentNhiCode, Boolean passOrNot, NhiRuleCheckFormat msgFormat) throws Exception {
        String nhiCode = nhiRuleCheckTestInfoHolder.getNhiCode();
        ResultActions resultActions = nhiRuleCheckTestInfoHolder.getResultActions();
        List<Disposal> disposals = disposalTestInfoHolder.getDisposalHistoryList();
        Disposal issueDisposal = disposalTestInfoHolder.getDisposal();
        List<NhiRuleCheckTxSnapshot> nhiRuleCheckTxSnapshotList = nhiRuleCheckTestInfoHolder.getNhiRuleCheckTxSnapshotList();
        List<NhiRuleCheckTxSnapshot> snapshotsOfTreatmentNhiCode = nhiRuleCheckTxSnapshotList.stream().filter(snapshot -> treatmentNhiCode.equals(snapshot.getNhiCode())).collect(Collectors.toList());
        String type;
        Object[] msgArgs = null;

        List<Disposal> todayDisposals = disposals
            .stream()
            .filter(d -> !d.getId().equals(issueDisposal.getId()))
            .filter(d -> d.getNhiExtendDisposals().stream().findFirst().get().getA17().equals(issueDisposal.getNhiExtendDisposals().stream().findFirst().get().getA17()))
            .collect(Collectors.toList());

        String pastTreatmentDate = disposals
            .stream()
            .filter(d -> !d.getId().equals(issueDisposal.getId()))
            .map(d -> transformA71ToDisplay(d.getNhiExtendDisposals().stream().findFirst().get().getA17())).findFirst()
            .orElse(transformA71ToDisplay(issueDisposal.getNhiExtendDisposals().stream().findFirst().get().getA17()));

        if (snapshotsOfTreatmentNhiCode.size() > 0) {
            type = CURRENT_DISPOSAL.getValue();
        } else if (todayDisposals.size() > 0) {
            type = TODAY_OTHER_DISPOSAL.getValue();
        } else {
            type = SYSTEM_RECORD.getValue();
        }

        if (msgFormat == NhiRuleCheckFormat.W4_1) {
            msgArgs = new Object[]{nhiCode, treatmentNhiCode, type, pastTreatmentDate};
        }

        String message = formatMsg(!passOrNot).apply(msgFormat, msgArgs);
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
        String message = formatMsg(!passOrNot).apply(NhiRuleCheckFormat.D3_1, new Object[]{nhiCode, "未滿六歲"});
        checkResult(resultActions, passOrNot, message);
    }

    @Then("病患是否在診療 {word} 當下年紀未滿 12 歲，確認結果是否為 {passOrNot}")
    public void checkLessThanAge12(String issueNhiCode, Boolean passOrNot) throws Exception {
        String nhiCode = nhiRuleCheckTestInfoHolder.getNhiCode();
        ResultActions resultActions = nhiRuleCheckTestInfoHolder.getResultActions();
        String message = formatMsg(!passOrNot).apply(NhiRuleCheckFormat.D3_1, new Object[]{nhiCode, "未滿十二歲"});
        checkResult(resultActions, passOrNot, message);
    }

    @Then("確認診療代碼 {word} 是否有自身健保診療重複突衝 ，確認結果是否為 {passOrNot}")
    public void checkNoDuplicatedNhiCodes(String issueNhiCode, Boolean passOrNot) throws Exception {
        ResultActions resultActions = nhiRuleCheckTestInfoHolder.getResultActions();
        String message = "同處置單已有 " + issueNhiCode;
        checkResult(resultActions, passOrNot, message);
    }

    @Then("（HIS）{word} 的紀錄中，牙位 {word} 在同一象限中，最多只能申報 {int} 次 {word} 健保代碼，確認結果是否為 {passOrNot}")
    public void checkNoSameNhiCodesInLimitDaysAndSamePhase(String period, String issueTeeth, int times, String issueNhiCode, Boolean passOrNot) throws Exception {
        ResultActions resultActions = nhiRuleCheckTestInfoHolder.getResultActions();
        List<Disposal> disposalList = disposalTestInfoHolder.getDisposalHistoryList();
        TreatmentProcedure newestTreatmentProcedure = mergeDisposalToTreatmentProcedure(disposalList).stream().min(comparing(tp -> tp.getDisposal().getDateTime())).get();
        ToothUtil.ToothPhase toothPhase = ToothUtil.markAsPhase(singletonList(newestTreatmentProcedure.getNhiExtendTreatmentProcedure().getA74())).get(0);
        String issueDate = transformA71ToDisplay(newestTreatmentProcedure.getNhiExtendTreatmentProcedure().getA71());
        String message = formatMsg(!passOrNot).apply(NhiRuleCheckFormat.D4_2, new Object[]{issueNhiCode, SYSTEM_RECORD.getValue(), issueDate, toothPhase.getNameOfPhase()});
        checkResult(resultActions, passOrNot, message);
    }

    @Then("（IC）{word} 的紀錄中，牙位 {word} 在同一象限中，最多只能申報 {int} 次 {word} 健保代碼，確認結果是否為 {passOrNot}")
    public void checkNoSameNhiCodesInLimitDaysAndSamePhaseInIC(String period, String issueTeeth, int times, String issueNhiCode, Boolean passOrNot) throws Exception {
        ResultActions resultActions = nhiRuleCheckTestInfoHolder.getResultActions();
        List<NhiMedicalRecord> nhiMedicalRecords = nhiMedicalRecordTestInfoHolder.getNhiMedicalRecordList();
        ToothUtil.ToothPhase toothPhase = ToothUtil.markAsPhase(singletonList(nhiMedicalRecords.get(0).getPart())).get(0);
        String issueDate = transformA71ToDisplay(nhiMedicalRecords.get(0).getDate());
        String type = NHI_CARD_RECORD.getValue();
        String message = formatMsg(!passOrNot).apply(NhiRuleCheckFormat.D4_2, new Object[]{issueNhiCode, type, issueDate, toothPhase.getNameOfPhase()});
        checkResult(resultActions, passOrNot, message);
    }

    @Then("（HIS）檢查 {word} 診療項目，在病患過去 {word} 紀錄中，不應包含特定的 {word} 診療代碼，確認結果是否為 {passOrNot} 且檢查訊息類型為 {msgFormat}")
    public void checkCodeBetweenDuration(String issueNhiCode, String gapMonth, String treatmentNhiCode, Boolean passOrNot, NhiRuleCheckFormat msgFormat) throws Exception {
        String nhiCode = nhiRuleCheckTestInfoHolder.getNhiCode();
        ResultActions resultActions = nhiRuleCheckTestInfoHolder.getResultActions();
        List<Disposal> disposalList = disposalTestInfoHolder.getDisposalHistoryList();

        TreatmentProcedure newestTreatmentProcedure =
            mergeDisposalToTreatmentProcedure(disposalList).stream()
                .filter(tp -> treatmentNhiCode.equals(tp.getNhiExtendTreatmentProcedure().getA73())).min(comparing(tp -> tp.getDisposal().getDateTime()))
                .get();

        String pastTreatmentDate = transformA71ToDisplay(newestTreatmentProcedure.getNhiExtendTreatmentProcedure().getA71());
        Object[] msgArgs = null;
        String type = SYSTEM_RECORD.getValue();
        if (msgFormat == NhiRuleCheckFormat.D4_1) {
            msgArgs = new Object[]{nhiCode, type, pastTreatmentDate};
        } else if (msgFormat == NhiRuleCheckFormat.D1_2_2) {
            msgArgs = new Object[]{issueNhiCode, treatmentNhiCode, type, pastTreatmentDate, parseMonthGap(gapMonth), issueNhiCode};
        }

        String message = formatMsg(!passOrNot).apply(msgFormat, msgArgs);
        checkResult(resultActions, passOrNot, message);
    }

    @Then("（IC）檢查 {word} 診療項目，在病患過去 {word} 紀錄中，不應包含特定的 {word} 診療代碼，確認結果是否為 {passOrNot} 且檢查訊息類型為 {msgFormat}")
    public void checkCodeBetweenDurationByNhiMedicalRecord(String issueNhiCode, String gapMonth, String treatmentNhiCode, Boolean passOrNot, NhiRuleCheckFormat msgFormat) throws Exception {
        String nhiCode = nhiRuleCheckTestInfoHolder.getNhiCode();
        List<NhiMedicalRecord> nhiMedicalRecords = nhiMedicalRecordTestInfoHolder.getNhiMedicalRecordList();
        ResultActions resultActions = nhiRuleCheckTestInfoHolder.getResultActions();
        String pastTreatmentDate = transformA71ToDisplay(nhiMedicalRecords.stream().map(NhiMedicalRecord::getDate).max(naturalOrder()).get());
        Object[] msgArgs = null;
        String type = NHI_CARD_RECORD.getValue();

        if (msgFormat == NhiRuleCheckFormat.D4_1) {
            msgArgs = new Object[]{nhiCode, type, pastTreatmentDate};
        } else if (msgFormat == NhiRuleCheckFormat.D1_2_2) {
            msgArgs = new Object[]{issueNhiCode, treatmentNhiCode, type, pastTreatmentDate, parseMonthGap(gapMonth), issueNhiCode};
        }

        String message = formatMsg(!passOrNot).apply(msgFormat, msgArgs);
        checkResult(resultActions, passOrNot, message);
    }

    @Then("（HIS）{word} 的記錄中，{word} 診療代碼最多只能 {int} 次，確認結果是否為 {passOrNot}")
    public void checkCodeBeforeDateWithMaxTimes(String period, String issueNhiCode, int times, Boolean passOrNot) throws Exception {
        ResultActions resultActions = nhiRuleCheckTestInfoHolder.getResultActions();
        List<Disposal> disposalList = disposalTestInfoHolder.getDisposalHistoryList();
        Disposal disposal = disposalTestInfoHolder.getDisposal();

        String dates = mergeDisposalToTreatmentProcedure(disposalList)
            .stream()
            .filter(tp -> !disposal.getId().equals(tp.getDisposal().getId()))
            .map(tp -> tp.getNhiExtendTreatmentProcedure().getA71())
            .sorted(reverseOrder())
            .map(DateTimeUtil::transformA71ToDisplay)
            .collect(Collectors.joining(", "));

        String message = formatMsg(!passOrNot).apply(NhiRuleCheckFormat.D5_1, new Object[]{issueNhiCode, dates});
        checkResult(resultActions, passOrNot, message);
    }

    @Then("（IC）{word} 的記錄中，{word} 診療代碼最多只能 {int} 次，確認結果是否為 {passOrNot}")
    public void checkCodeBeforeDateByNhiMedicalRecordWithMaxTimes(String period, String issueNhiCode, int times, Boolean passOrNot) throws Exception {
        ResultActions resultActions = nhiRuleCheckTestInfoHolder.getResultActions();
        List<NhiMedicalRecord> nhiMedicalRecords = nhiMedicalRecordTestInfoHolder.getNhiMedicalRecordList();

        String dates = nhiMedicalRecords
            .stream()
            .map(NhiMedicalRecord::getDate)
            .sorted(reverseOrder())
            .map(DateTimeUtil::transformA71ToDisplay)
            .collect(Collectors.joining(", "));

        String message = formatMsg(!passOrNot).apply(NhiRuleCheckFormat.D5_1, new Object[]{issueNhiCode, dates});
        checkResult(resultActions, passOrNot, message);
    }

    @Then("（HIS）{word} 天內的記錄中，{word} 診療代碼已達 {int} 次以上，不得申報 {word}，確認結果是否為 {passOrNot}")
    public void checkCodeBeforeDateWithMaxTimes2(String period, String treatmentNhiCode, int times, String issueNhiCode, Boolean passOrNot) throws Exception {
        ResultActions resultActions = nhiRuleCheckTestInfoHolder.getResultActions();
        List<Disposal> disposalList = disposalTestInfoHolder.getDisposalHistoryList();
        Disposal disposal = disposalTestInfoHolder.getDisposal();

        List<String> dates = mergeDisposalToTreatmentProcedure(disposalList)
            .stream()
            .filter(tp -> !disposal.getId().equals(tp.getDisposal().getId()))
            .map(tp -> tp.getNhiExtendTreatmentProcedure().getA71())
            .sorted(reverseOrder())
            .map(DateTimeUtil::transformA71ToDisplay)
            .collect(Collectors.toList());

        String message = formatMsg(!passOrNot).apply(NhiRuleCheckFormat.D1_2, new Object[]{issueNhiCode, treatmentNhiCode, SYSTEM_RECORD.getValue(), dates.get(0), period, issueNhiCode});
        checkResult(resultActions, passOrNot, message);
    }

    @Then("（IC）{word} 天內的記錄中，{word} 診療代碼已達 {int} 次以上，不得申報 {word}，確認結果是否為 {passOrNot}")
    public void checkCodeBeforeDateByNhiMedicalRecordWithMaxTimes2(String period, String treatmentNhiCode, int times, String issueNhiCode, Boolean passOrNot) throws Exception {
        ResultActions resultActions = nhiRuleCheckTestInfoHolder.getResultActions();
        List<NhiMedicalRecord> nhiMedicalRecords = nhiMedicalRecordTestInfoHolder.getNhiMedicalRecordList();

        List<String> dates = nhiMedicalRecords
            .stream()
            .map(NhiMedicalRecord::getDate)
            .sorted(reverseOrder())
            .map(DateTimeUtil::transformA71ToDisplay)
            .collect(Collectors.toList());

        String message = formatMsg(!passOrNot).apply(NhiRuleCheckFormat.D1_2, new Object[]{issueNhiCode, treatmentNhiCode, NHI_CARD_RECORD.getValue(), dates.get(0), period, issueNhiCode});
        checkResult(resultActions, passOrNot, message);
    }

    @Then("{word} 診療項目曾經申報過，確認結果是否為 {passOrNot}")
    public void checkTreatmentDependOnCode(String treatmentNhiCode, Boolean passOrNot) throws Exception {
        String nhiCode = nhiRuleCheckTestInfoHolder.getNhiCode();
        ResultActions resultActions = nhiRuleCheckTestInfoHolder.getResultActions();
        String message = formatMsg(!passOrNot).apply(NhiRuleCheckFormat.D8_2, new Object[]{nhiCode, treatmentNhiCode});
        checkResult(resultActions, passOrNot, message);
    }

    @Then("檢查 {int} 天內，應有 {word} 診療項目存在，確認結果是否為 {passOrNot}")
    public void checkTreatmentDependOnCodeInDuration(Integer dayRange, String treatmentNhiCode, Boolean passOrNot) throws Exception {
        String nhiCode = nhiRuleCheckTestInfoHolder.getNhiCode();
        ResultActions resultActions = nhiRuleCheckTestInfoHolder.getResultActions();
        String message = formatMsg(!passOrNot).apply(NhiRuleCheckFormat.D8_1, new Object[]{nhiCode, dayRange, treatmentNhiCode});
        checkResult(resultActions, passOrNot, message);
    }

    @Then("（HIS）檢查 {int} 天內，應有 {word} 診療項目存在，確認結果是否為 {passOrNot} 且檢查訊息類型為 {msgFormat}")
    public void checkTreatmentDependOnCodeInDuration2(Integer dayRange, String treatmentNhiCode, Boolean passOrNot, NhiRuleCheckFormat msgFormat) throws Exception {
        String nhiCode = nhiRuleCheckTestInfoHolder.getNhiCode();
        ResultActions resultActions = nhiRuleCheckTestInfoHolder.getResultActions();
        Object[] msgArgs = null;

        if (msgFormat == NhiRuleCheckFormat.PERIO_1) {
            msgArgs = new Object[]{nhiCode};
        } else if (msgFormat == NhiRuleCheckFormat.D8_1) {
            msgArgs = new Object[]{nhiCode, dayRange, treatmentNhiCode};
        }

        String message = formatMsg(!passOrNot).apply(msgFormat, msgArgs);
        checkResult(resultActions, passOrNot, message);
    }

    @Then("（IC）檢查 {int} 天內，應有 {word} 診療項目存在，確認結果是否為 {passOrNot} 且檢查訊息類型為 {msgFormat}")
    public void checkNhiMedicalRecordDependOnCodeInDuration(Integer dayRange, String treatmentNhiCode, Boolean passOrNot, NhiRuleCheckFormat msgFormat) throws Exception {
        String nhiCode = nhiRuleCheckTestInfoHolder.getNhiCode();
        ResultActions resultActions = nhiRuleCheckTestInfoHolder.getResultActions();
        Object[] msgArgs = null;

        if (msgFormat == NhiRuleCheckFormat.PERIO_1) {
            msgArgs = new Object[]{nhiCode};
        } else if (msgFormat == NhiRuleCheckFormat.D8_1) {
            msgArgs = new Object[]{nhiCode, dayRange, treatmentNhiCode};
        }

        String message = formatMsg(!passOrNot).apply(msgFormat, msgArgs);
        checkResult(resultActions, passOrNot, message);
    }

    @Then("同日不得有 {nhiCodeList} 診療項目，確認結果是否為 {passOrNot}")
    public void checkNoTreatmentOnCodeToday(List<String> forbiddenNhiCodes, Boolean passOrNot) throws Exception {
        ResultActions resultActions = nhiRuleCheckTestInfoHolder.getResultActions();
        String message = formatMsg(!passOrNot).apply(NhiRuleCheckFormat.D6_1, new Object[]{join(", ", forbiddenNhiCodes)});
        checkResult(resultActions, passOrNot, message);
    }

    @Then("同日得有 {nhiCodeList} 診療項目，確認結果是否為 {passOrNot}")
    public void checkTreatmentDependOnCodeToday(List<String> dependNhiCodes, Boolean passOrNot) throws Exception {
        ResultActions resultActions = nhiRuleCheckTestInfoHolder.getResultActions();
        String message = formatMsg(!passOrNot).apply(NhiRuleCheckFormat.W3_1, new Object[]{join(", ", dependNhiCodes)});
        checkResult(resultActions, passOrNot, message);
    }

    @Then("{word} 不得單獨申報，確認結果是否為 {passOrNot}")
    public void checkAnyOtherTreatment(String issueNhiCode, Boolean passOrNot) throws Exception {
        ResultActions resultActions = nhiRuleCheckTestInfoHolder.getResultActions();
        String message = formatMsg(passOrNot).apply(NhiRuleCheckFormat.W5_1, new Object[]{issueNhiCode});
        checkResult(resultActions, passOrNot, message);
    }

    @Then("{word} 終生只能申報一次，確認結果是否為 {passOrNot}")
    public void checkOnceInWholeLife(String issueNhiCode, Boolean passOrNot) throws Exception {
        ResultActions resultActions = nhiRuleCheckTestInfoHolder.getResultActions();
        Disposal disposal = disposalTestInfoHolder.getDisposal();
        List<NhiMedicalRecord> nhiMedicalRecordList = nhiMedicalRecordTestInfoHolder.getNhiMedicalRecordList();
        String issueDisposalDate = disposal.getNhiExtendDisposals().stream().findFirst().map(NhiExtendDisposal::getA17).map(DateTimeUtil::transformA71ToDisplay).get();
        List<NhiRuleCheckTxSnapshot> nhiRuleCheckTxSnapshotList = nhiRuleCheckTestInfoHolder.getNhiRuleCheckTxSnapshotList();
        List<NhiRuleCheckTxSnapshot> snapshotsOfIssueNhiCode = nhiRuleCheckTxSnapshotList.stream().filter(snapshot -> issueNhiCode.equals(snapshot.getNhiCode())).collect(Collectors.toList());
        List<NhiTreatment> nhiTreatments = mergeTreatmentAndMedical();
        String date = nhiTreatments.stream()
            .filter(nt -> !disposal.getId().equals(nt.getDisposalId()))
            .filter(nt -> issueNhiCode.equals(nt.getCode()))
            .map(NhiTreatment::getDatetime).max(naturalOrder()).map(DateTimeUtil::transformA71ToDisplay).orElse("Date Not Found");
        String type;

        if (nhiMedicalRecordList.size() == 1) {
            type = NHI_CARD_RECORD.getValue();
        } else if (snapshotsOfIssueNhiCode.size() > 1) {
            date = disposal.getNhiExtendDisposals().stream().findFirst().map(NhiExtendDisposal::getA17).map(DateTimeUtil::transformA71ToDisplay).get();
            type = CURRENT_DISPOSAL.getValue();
        } else {
            if (date.equals(issueDisposalDate)) {
                type = TODAY_OTHER_DISPOSAL.getValue();
            } else {
                type = SYSTEM_RECORD.getValue();
            }
        }

        String message = formatMsg(!passOrNot).apply(NhiRuleCheckFormat.D2_1, new Object[]{issueNhiCode, type, date});
        checkResult(resultActions, passOrNot, message);
    }

    @Then("每位醫生限申報一次 {word} 代碼，確認結果是否為 {passOrNot}")
    public void checkOnceInWholeLifeAtDoctor(String issueNhiCode, Boolean passOrNot) throws Exception {
        ResultActions resultActions = nhiRuleCheckTestInfoHolder.getResultActions();
        List<NhiRuleCheckTxSnapshot> nhiRuleCheckTxSnapshotList = nhiRuleCheckTestInfoHolder.getNhiRuleCheckTxSnapshotList();
        List<NhiRuleCheckTxSnapshot> snapshotsOfIssueNhiCode = nhiRuleCheckTxSnapshotList.stream().filter(snapshot -> issueNhiCode.equals(snapshot.getNhiCode())).collect(Collectors.toList());
        Disposal disposal = disposalTestInfoHolder.getDisposal();
        List<NhiTreatment> nhiTreatments = mergeTreatmentAndMedical();
        String date = nhiTreatments.stream()
            .filter(nt -> !disposal.getId().equals(nt.getDisposalId()))
            .filter(nt -> issueNhiCode.equals(nt.getCode()))
            .map(NhiTreatment::getDatetime).max(naturalOrder()).map(DateTimeUtil::transformA71ToDisplay).orElse("Date Not Found");
        String type;

        if (snapshotsOfIssueNhiCode.size() > 0) {
            type = CURRENT_DISPOSAL.getValue();
        } else {
            type = SYSTEM_RECORD.getValue();
        }

        String message = formatMsg(passOrNot).apply(NhiRuleCheckFormat.D2_3, new Object[]{issueNhiCode, type, date});
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
        resultActions.andExpect(jsonPath("$.validated").value(equalTo(passOrNot)));

        if (message != null) {
            resultActions.andExpect(jsonPath(innerPath + ".message").value(hasItem(message)));
            resultActions.andExpect(jsonPath(innerPath + ".validated").value(hasItem(innerPassOrNot)));
        }
    }

    private Set<TreatmentProcedure> mergeDisposalToTreatmentProcedure(List<Disposal> disposalList) {
        return disposalList.stream()
            .map(Disposal::getTreatmentProcedures)
            .reduce(new HashSet<>(), (tps1, tps2) -> {
                tps1.addAll(tps2);
                return tps1;
            });
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
}
