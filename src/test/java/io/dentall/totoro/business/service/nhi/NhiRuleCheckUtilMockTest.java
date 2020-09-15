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
import io.dentall.totoro.util.TableGenerator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {io.dentall.totoro.config.TimeConfig.class})
public class NhiRuleCheckUtilMockTest {

    private final Long patientId_1 = 1L;

    private final Long patientId_2 = 2L;

    private final Long treatmentProcedureId_1 = 1L;

    private final Long treatmentProcedureId_2 = 2L;

    @Mock
    private NhiExtendDisposalRepository nhiExtendDisposalRepository;

    @Spy
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

    @Test
    public void addNotification() {
        final String msg = "hello golang";
        NhiRuleCheckResultDTO rdto = nhiRuleCheckUtil.addNotification(msg);

        Assert.assertEquals(rdto.getMessage(), msg);
    }

    /**
     * Test case for findPatientTreatmentProcedureAtCodesAndBeforePeriod
     * 給定 code 且 在給定日期內不包含其他資料
     * 1. T, 過去無資料
     * 2. T, 過去有資料，且超過限制日期
     * 3. T, 未來有資料
     * 4. T, 過去有資料，但不同 code
     * 5. T, 有資料，但是與自己相同 tx
     * 6. F, 過去有資料，且未超過限制日期
     */
    @Test
    public void findTxCode_1() {
        NhiExtendTreatmentProcedure netp = nhiRuleCheckUtil.findPatientTreatmentProcedureAtCodesAndBeforePeriod(
            DataGenerator.ID_1,
            DataGenerator.ID_1,
            DataGenerator.NHI_TREATMENT_DATE_NOW,
            DataGenerator.NHI_CODE_LIST_1,
            DateTimeUtil.NHI_1_MONTH
        );

        Assert.assertNull(netp);
    }

    @Test
    public void findTxCode_2() {
        Mockito
            .when(nhiExtendTreatmentProcedureRepository.findAllByTreatmentProcedure_Disposal_Registration_Appointment_Patient_IdAndA73In(
                DataGenerator.ID_1,
                DataGenerator.NHI_CODE_LIST_1
            ))
            .thenReturn(
                Arrays.asList(new TableGenerator.NhiExtendTreatmentProcedureTableGenerator(
                    DataGenerator.ID_2,
                    DataGenerator.NHI_TREATMENT_DATE_MIN,
                    DataGenerator.NHI_CODE_1,
                    DataGenerator.TOOTH_PERMANENT_1,
                    DataGenerator.SURFACE_BLANK
                ))
            );

        NhiExtendTreatmentProcedure netp = nhiRuleCheckUtil.findPatientTreatmentProcedureAtCodesAndBeforePeriod(
            DataGenerator.ID_1,
            DataGenerator.ID_1,
            DataGenerator.NHI_TREATMENT_DATE_NOW,
            DataGenerator.NHI_CODE_LIST_1,
            DateTimeUtil.NHI_1_MONTH
        );

        Assert.assertNull(netp);
    }

    @Test
    public void findTxCode_3() {
        Mockito
            .when(nhiExtendTreatmentProcedureRepository.findAllByTreatmentProcedure_Disposal_Registration_Appointment_Patient_IdAndA73In(
                DataGenerator.ID_1,
                DataGenerator.NHI_CODE_LIST_1
            ))
            .thenReturn(
                Arrays.asList(new TableGenerator.NhiExtendTreatmentProcedureTableGenerator(
                    DataGenerator.ID_2,
                    DataGenerator.NHI_TREATMENT_DATE_MAX,
                    DataGenerator.NHI_CODE_1,
                    DataGenerator.TOOTH_PERMANENT_1,
                    DataGenerator.SURFACE_BLANK
                ))
            );

        NhiExtendTreatmentProcedure netp = nhiRuleCheckUtil.findPatientTreatmentProcedureAtCodesAndBeforePeriod(
            DataGenerator.ID_1,
            DataGenerator.ID_1,
            DataGenerator.NHI_TREATMENT_DATE_NOW,
            DataGenerator.NHI_CODE_LIST_1,
            DateTimeUtil.NHI_1_MONTH
        );

        Assert.assertNull(netp);
    }

    @Test
    public void findTxCode_4() {
        Mockito
            .when(nhiExtendTreatmentProcedureRepository.findAllByTreatmentProcedure_Disposal_Registration_Appointment_Patient_IdAndA73In(
                DataGenerator.ID_1,
                DataGenerator.NHI_CODE_LIST_1
            ))
            .thenReturn(
                new ArrayList<>()
            );

        NhiExtendTreatmentProcedure netp = nhiRuleCheckUtil.findPatientTreatmentProcedureAtCodesAndBeforePeriod(
            DataGenerator.ID_1,
            DataGenerator.ID_1,
            DataGenerator.NHI_TREATMENT_DATE_NOW,
            DataGenerator.NHI_CODE_LIST_1,
            DateTimeUtil.NHI_1_MONTH
        );

        Assert.assertNull(netp);
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
        netp.setA71(DataGenerator.NHI_TREATMENT_DATE_MIN);
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

    @Test
    public void isPatientToothAtCodesBeforePeriod_2() {
        NhiRuleCheckDTO dto = new NhiRuleCheckDTO();
        Patient p = new Patient();
        NhiExtendDisposal ned = new NhiExtendDisposal();
        NhiExtendTreatmentProcedure netp = new NhiExtendTreatmentProcedure();
        p.setId(DataGenerator.ID_1);
        ned.setPatientIdentity(null);
        netp.setA71(DataGenerator.NHI_TREATMENT_DATE_MIN);
        netp.setA74(DataGenerator.TOOTH_DECIDUOUS_2);
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

    @Test
    public void isPatientToothAtCodesBeforePeriod_3() {
        NhiRuleCheckDTO dto = new NhiRuleCheckDTO();
        Patient p = new Patient();
        NhiExtendDisposal ned = new NhiExtendDisposal();
        NhiExtendTreatmentProcedure netp = new NhiExtendTreatmentProcedure();
        p.setId(DataGenerator.ID_1);
        ned.setPatientIdentity(null);
        netp.setA71(DataGenerator.NHI_TREATMENT_DATE_MIN);
        netp.setA73(DataGenerator.NHI_CODE_1);
        netp.setA74(DataGenerator.TOOTH_PERMANENT_1);
        dto.setPatient(p);
        dto.setNhiExtendDisposal(ned);
        dto.setNhiExtendTreatmentProcedure(netp);

        List<String> codes = Mockito.mock(List.class);
        when(codes.size()).thenReturn(1);
        when(codes.get(0)).thenReturn("FAKE1");
    }
}
