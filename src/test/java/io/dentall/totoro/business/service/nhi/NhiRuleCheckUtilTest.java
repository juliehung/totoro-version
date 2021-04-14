package io.dentall.totoro.business.service.nhi;

import io.dentall.totoro.TotoroApp;
import io.dentall.totoro.business.service.nhi.util.NhiRuleCheckUtil;
import io.dentall.totoro.business.vm.nhi.NhiRuleCheckBody;
import io.dentall.totoro.business.vm.nhi.NhiRuleCheckTxSnapshot;
import io.dentall.totoro.config.TimeConfig;
import io.dentall.totoro.domain.NhiExtendDisposal;
import io.dentall.totoro.domain.Patient;
import io.dentall.totoro.util.DataGenerator;
import io.dentall.totoro.util.DomainGenerator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TotoroApp.class)
public class NhiRuleCheckUtilTest {

    @Autowired
    private NhiRuleCheckUtil nhiRuleCheckUtil;

    @Autowired
    private DomainGenerator domainGenerator;

    private Patient patient;

    private NhiExtendDisposal nhiExtendDisposal;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        patient = domainGenerator.generatePatientAndTxFamily(DataGenerator.PATIENT_NAME_1, DataGenerator.PATIENT_PHONE_1);
        nhiExtendDisposal= domainGenerator.generateNhiExtendDisposal(
            DataGenerator.NHI_TREATMENT_DATE_NOW.atStartOfDay().toInstant(TimeConfig.ZONE_OFF_SET),
            DataGenerator.NHI_CATEGORY_1
        );
        System.out.println("a");
    }

    @Test
    public void convertWithPatient() {
        NhiRuleCheckBody body = new NhiRuleCheckBody();
        body.setPatientId(patient.getId());

        NhiRuleCheckDTO dto = nhiRuleCheckUtil.convertVmToDto(DataGenerator.NHI_CODE_1, body);

        Assert.assertEquals(patient.getId(), dto.getPatient().getId());
    }

    @Test
    public void convertNullPatient() {
        NhiRuleCheckBody body = new NhiRuleCheckBody();
        body.setPatientId(DataGenerator.ID_2);

        NhiRuleCheckDTO dto = nhiRuleCheckUtil.convertVmToDto(DataGenerator.NHI_CODE_1, body);

        Assert.assertNotNull(dto.getPatient());
        Assert.assertNull(dto.getPatient().getId());
    }

    @Test
    public void convertDisposal() {
        NhiRuleCheckBody body = new NhiRuleCheckBody();
        body.setPatientId(DataGenerator.ID_1);
        body.setDisposalId(nhiExtendDisposal.getDisposal().getId());

        NhiRuleCheckDTO dto = nhiRuleCheckUtil.convertVmToDto(DataGenerator.NHI_CODE_1, body);

        Assert.assertNull(dto.getNhiExtendDisposal().getA23());
    }

    @Test
    public void convertDisposalWithSameNhiCategory() {
        NhiRuleCheckBody body = new NhiRuleCheckBody();
        body.setPatientId(DataGenerator.ID_1);
        body.setDisposalId(nhiExtendDisposal.getDisposal().getId());
        body.setNhiCategory(DataGenerator.NHI_CATEGORY_1);

        NhiRuleCheckDTO dto = nhiRuleCheckUtil.convertVmToDto(DataGenerator.NHI_CODE_1, body);

        Assert.assertEquals(DataGenerator.NHI_CATEGORY_1, dto.getNhiExtendDisposal().getA23());
    }

    @Test
    public void convertDisposalWithDiffNhiCategory() {
        NhiRuleCheckBody body = new NhiRuleCheckBody();
        body.setPatientId(DataGenerator.ID_1);
        body.setDisposalId(nhiExtendDisposal.getDisposal().getId());
        body.setNhiCategory(DataGenerator.NHI_CATEGORY_2);

        NhiRuleCheckDTO dto = nhiRuleCheckUtil.convertVmToDto(DataGenerator.NHI_CODE_1, body);

        Assert.assertEquals(DataGenerator.NHI_CATEGORY_2, dto.getNhiExtendDisposal().getA23());
    }

    @Test
    public void convertTxSnapshotNew() {
        List<NhiRuleCheckTxSnapshot> txSnapshots = new ArrayList<>();

        NhiRuleCheckTxSnapshot txSnapshot = new NhiRuleCheckTxSnapshot();
        txSnapshot.setNhiCode(DataGenerator.NHI_CODE_1);
        txSnapshot.setTeeth(DataGenerator.TOOTH_PERMANENT_2);
        txSnapshot.setSurface(DataGenerator.SURFACE_BLANK);

        txSnapshots.add(txSnapshot);

        NhiRuleCheckBody body = new NhiRuleCheckBody();
        body.setPatientId(DataGenerator.ID_1);
        body.setDisposalTime("1100101090909");
        body.setDisposalId(nhiExtendDisposal.getDisposal().getId());
        body.setNhiCategory(DataGenerator.NHI_CATEGORY_2);
        body.setTxSnapshots(txSnapshots);

        NhiRuleCheckDTO dto = nhiRuleCheckUtil.convertVmToDto(DataGenerator.NHI_CODE_1, body);

        Assert.assertEquals(DataGenerator.NHI_CATEGORY_2, dto.getNhiExtendDisposal().getA23());
    }
}
