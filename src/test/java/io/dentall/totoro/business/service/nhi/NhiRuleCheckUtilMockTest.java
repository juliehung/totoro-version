package io.dentall.totoro.business.service.nhi;

import io.dentall.totoro.business.service.nhi.util.NhiRuleCheckUtil;
import io.dentall.totoro.business.service.nhi.util.ToothConstraint;
import io.dentall.totoro.business.service.nhi.util.ToothUtil;
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
     * 1. 複數個 恆牙牙位 ，條件為 恆牙，檢核失敗，並無錯誤訊息
     * 2. 恆牙牙位 ＋ 乳牙牙位，條件為 恆牙，檢核失敗，並出現錯誤訊息
     * 3. 牙位為空，檢核失敗，並出現錯誤訊息
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
     *
     */

}
