package io.dentall.totoro.business.service.nhi;

import io.dentall.totoro.business.service.nhi.util.CopaymentCode;
import io.dentall.totoro.business.service.nhi.util.NhiRuleCheckUtil;
import io.dentall.totoro.business.service.nhi.util.ToothConstraint;
import io.dentall.totoro.business.service.nhi.util.ToothUtil;
import io.dentall.totoro.domain.NhiExtendDisposal;
import io.dentall.totoro.domain.NhiExtendTreatmentProcedure;
import io.dentall.totoro.repository.NhiExtendDisposalRepository;
import io.dentall.totoro.repository.NhiExtendTreatmentProcedureRepository;
import io.dentall.totoro.repository.NhiMedicalRecordRepository;
import io.dentall.totoro.repository.PatientRepository;
import io.dentall.totoro.service.mapper.NhiExtendDisposalMapper;
import io.dentall.totoro.service.mapper.NhiExtendTreatmentProcedureMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
        Assert.assertEquals(rdto.isValidated(), true);
        Assert.assertEquals(rdto.getMessage(), null);
    }

    @Test
    public void isAllLimitTooth_2() {
        NhiRuleCheckDTO dto = new NhiRuleCheckDTO();
        NhiExtendTreatmentProcedure netp = new NhiExtendTreatmentProcedure();
        netp.setA74("1254");
        dto.setNhiExtendTreatmentProcedure(netp);

        NhiRuleCheckResultDTO rdto = nhiRuleCheckUtil.isAllLimitedTooth(dto, ToothConstraint.PERMANENT_TOOTH);
        Assert.assertEquals(rdto.isValidated(), false);
        Assert.assertEquals(rdto.getMessage(), ToothUtil.getToothConstraintsFailureMessage(ToothConstraint.PERMANENT_TOOTH));
    }

    @Test
    public void isAllLimitTooth_3() {
        NhiRuleCheckDTO dto = new NhiRuleCheckDTO();
        NhiExtendTreatmentProcedure netp = new NhiExtendTreatmentProcedure();
        netp.setA74("");
        dto.setNhiExtendTreatmentProcedure(netp);

        NhiRuleCheckResultDTO rdto = nhiRuleCheckUtil.isAllLimitedTooth(dto, ToothConstraint.PERMANENT_TOOTH);
        Assert.assertEquals(rdto.isValidated(), false);
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
    public void isPatientIdentityInclude() {
        NhiRuleCheckDTO dto = new NhiRuleCheckDTO();
        NhiExtendDisposal ned = new NhiExtendDisposal();
        ned.setPatientIdentity("001");
        dto.setNhiExtendDisposal(ned);

        NhiRuleCheckResultDTO rdto = nhiRuleCheckUtil.isPatientIdentityInclude(dto, CopaymentCode._001);
        Assert.assertEquals(rdto.isValidated(), true);
        Assert.assertEquals(rdto.getMessage(), CopaymentCode._001.getNotification());
    }

}
