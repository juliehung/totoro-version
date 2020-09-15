package io.dentall.totoro.business.service.nhi;

import io.dentall.totoro.business.service.nhi.util.CopaymentCode;
import io.dentall.totoro.business.service.nhi.util.NhiRuleCheckUtil;
import io.dentall.totoro.business.service.nhi.util.ToothConstraint;
import io.dentall.totoro.business.service.nhi.util.ToothUtil;
import io.dentall.totoro.domain.NhiExtendDisposal;
import io.dentall.totoro.domain.NhiExtendTreatmentProcedure;
import io.dentall.totoro.domain.Patient;
import io.dentall.totoro.repository.NhiExtendDisposalRepository;
import io.dentall.totoro.repository.NhiExtendTreatmentProcedureRepository;
import io.dentall.totoro.repository.NhiMedicalRecordRepository;
import io.dentall.totoro.repository.PatientRepository;
import io.dentall.totoro.service.mapper.NhiExtendDisposalMapper;
import io.dentall.totoro.service.mapper.NhiExtendTreatmentProcedureMapper;
import io.dentall.totoro.service.util.DateTimeUtil;
import io.dentall.totoro.util.DataGenerator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {io.dentall.totoro.config.TimeConfig.class})
public class NhiRuleCheckUtilMockTest {

    private final Long patientId_1 = 1L;

    private final Long patientId_2 = 2L;

    private final Long treatmentProcedureId_1 = 1L;

    private final Long treatmentProcedureId_2 = 2L;

    @Mock
    private NhiExtendDisposalRepository nhiExtendDisposalRepository;

    @Mock
    private NhiExtendTreatmentProcedureRepository nhiExtendTreatmentProcedureRepository;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private NhiMedicalRecordRepository nhiMedicalRecordRepository;

    @Mock
    private NhiExtendDisposalMapper nhiExtendDisposalMapper;

    @Mock
    private NhiExtendTreatmentProcedureMapper nhiExtendTreatmentProcedureMapper;

    @InjectMocks
    private NhiRuleCheckUtil nhiRuleCheckUtil;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void addNotification() {
        final String msg = "hello golang";
        NhiRuleCheckResultDTO rdto = nhiRuleCheckUtil.addNotification(msg);

        Assert.assertEquals(rdto.getMessage(), msg);
    }

    /**
     * Test case for isAllLimitTooth
     * 牙位 必須符合給訂之牙位 regex 規則
     * 1. T, 複數個 恆牙牙位 ，條件為 恆牙，並無錯誤訊息
     * 2. F, 恆牙牙位 ＋ 乳牙牙位，條件為 恆牙，並出現錯誤訊息
     * 3. F, 牙位為空，並出現錯誤訊息
     */
    @Test
    public void isAllLimitTooth_1() {
        NhiRuleCheckDTO dto = new NhiRuleCheckDTO();
        NhiExtendTreatmentProcedure netp = new NhiExtendTreatmentProcedure();
        netp.setA74("1234");
        dto.setNhiExtendTreatmentProcedure(netp);

        NhiRuleCheckResultDTO rdto = nhiRuleCheckUtil.isAllLimitedTooth(dto, ToothConstraint.PERMANENT_TOOTH);
        Assert.assertEquals(true, rdto.isValidated());
        Assert.assertEquals(rdto.getMessage(), null);
    }

    @Test
    public void isAllLimitTooth_2() {
        NhiRuleCheckDTO dto = new NhiRuleCheckDTO();
        NhiExtendTreatmentProcedure netp = new NhiExtendTreatmentProcedure();
        netp.setA74("1254");
        dto.setNhiExtendTreatmentProcedure(netp);

        NhiRuleCheckResultDTO rdto = nhiRuleCheckUtil.isAllLimitedTooth(dto, ToothConstraint.PERMANENT_TOOTH);
        Assert.assertEquals(false, rdto.isValidated());
        Assert.assertEquals(rdto.getMessage(), ToothUtil.getToothConstraintsFailureMessage(ToothConstraint.PERMANENT_TOOTH));
    }

    @Test
    public void isAllLimitTooth_3() {
        NhiRuleCheckDTO dto = new NhiRuleCheckDTO();
        NhiExtendTreatmentProcedure netp = new NhiExtendTreatmentProcedure();
        netp.setA74("");
        dto.setNhiExtendTreatmentProcedure(netp);

        NhiRuleCheckResultDTO rdto = nhiRuleCheckUtil.isAllLimitedTooth(dto, ToothConstraint.PERMANENT_TOOTH);
        Assert.assertEquals(false, rdto.isValidated());
        Assert.assertEquals(rdto.getMessage(), ToothUtil.getToothConstraintsFailureMessage(ToothConstraint.PERMANENT_TOOTH));
    }

    /**
     * Test case for isPatientIdentityInclude
     * 檢驗 選定的部分負擔代碼符合選定項目
     * 1. T, 符合指定部分負擔代碼
     * 2. F, 不符合指定不分代碼
     * 3. F, 沒有指定代碼
     */
    @Test
    public void isPatientIdentityInclude_1() {
        NhiRuleCheckDTO dto = new NhiRuleCheckDTO();
        NhiExtendDisposal ned = new NhiExtendDisposal();
        ned.setPatientIdentity(DataGenerator.PATIENT_IDENTITY_1);
        dto.setNhiExtendDisposal(ned);

        NhiRuleCheckResultDTO rdto = nhiRuleCheckUtil.isPatientIdentityInclude(dto, CopaymentCode._001);
        Assert.assertEquals(true, rdto.isValidated());
        Assert.assertEquals(null, rdto.getMessage());
    }

    @Test
    public void isPatientIdentityInclude_2() {
        NhiRuleCheckDTO dto = new NhiRuleCheckDTO();
        NhiExtendDisposal ned = new NhiExtendDisposal();
        ned.setPatientIdentity(DataGenerator.PATIENT_IDENTITY_2);
        dto.setNhiExtendDisposal(ned);

        NhiRuleCheckResultDTO rdto = nhiRuleCheckUtil.isPatientIdentityInclude(dto, CopaymentCode._001);
        Assert.assertEquals(false, rdto.isValidated());
        Assert.assertEquals(CopaymentCode._001.getNotification(), rdto.getMessage());
    }

    @Test
    public void isPatientIdentityInclude_3() {
        NhiRuleCheckDTO dto = new NhiRuleCheckDTO();
        NhiExtendDisposal ned = new NhiExtendDisposal();
        ned.setPatientIdentity(null);
        dto.setNhiExtendDisposal(ned);

        NhiRuleCheckResultDTO rdto = nhiRuleCheckUtil.isPatientIdentityInclude(dto, CopaymentCode._001);
        Assert.assertEquals(false, rdto.isValidated());
        Assert.assertEquals(CopaymentCode._001.getNotification(), rdto.getMessage());
    }

    /**
     * Test case for isPatientToothAtCodesBeforePeriod
     * 1. T, 恆牙未過期
     * 2. T, 乳牙未過期
     * 3. F, 恆牙過期
     * 4. F, 乳牙過期
     * 5. T, 時間內存在相同處置代碼，但不同牙位
     * 6. T, 在未來時間的處置，同代碼，同牙位
     * 7. F, 乳牙過期，恆牙未過期
     * 8. F, 乳牙未過期，恆牙過期
     */
    @Test
    public void isPatientToothAtCodesBeforePeriod_1() {
        NhiRuleCheckDTO dto = new NhiRuleCheckDTO();
        Patient p = new Patient();
        NhiExtendDisposal ned = new NhiExtendDisposal();
        NhiExtendTreatmentProcedure netp = new NhiExtendTreatmentProcedure();
        p.setId(DataGenerator.ID_1);
        ned.setPatientIdentity(null);
        netp.setA71(DataGenerator.NHI_TREATMENT_DATE_MIND);
        netp.setA74(DataGenerator.TOOTH_PERMANENT_1);
        dto.setPatient(p);
        dto.setNhiExtendDisposal(ned);
        dto.setNhiExtendTreatmentProcedure(netp);

        NhiRuleCheckResultDTO rdto = nhiRuleCheckUtil.isPatientToothAtCodesBeforePeriod(
            dto,
            Arrays.asList(new String[]{DataGenerator.NHI_CODE_1}.clone()),
            DateTimeUtil.NHI_12_MONTH,
            DateTimeUtil.NHI_18_MONTH);

        Assert.assertEquals(true, rdto.isValidated());
        Assert.assertEquals(null, rdto.getMessage());
    }
}
