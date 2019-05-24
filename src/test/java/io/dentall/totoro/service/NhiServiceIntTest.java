package io.dentall.totoro.service;

import io.dentall.totoro.TotoroApp;
import io.dentall.totoro.domain.NhiExtendTreatmentProcedure;
import io.dentall.totoro.repository.NhiProcedureRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TotoroApp.class)
public class NhiServiceIntTest {

    @Autowired
    private NhiService nhiService;

    @Autowired
    private NhiProcedureRepository nhiProcedureRepository;

    @Test
    public void testCheckExclude() {
        NhiExtendTreatmentProcedure p1 = new NhiExtendTreatmentProcedure().a73("00121C");
        NhiExtendTreatmentProcedure p2 = new NhiExtendTreatmentProcedure().a73("89001C");
        NhiExtendTreatmentProcedure p3 = new NhiExtendTreatmentProcedure().a73("96001C");
        Set<NhiExtendTreatmentProcedure> nhiExtendTreatmentProcedures = Stream.of(p1, p2, p3).collect(Collectors.toSet());

        nhiService.checkExclude(nhiProcedureRepository.findAll(), nhiExtendTreatmentProcedures);
        assertThat(p2.getCheck()).isEqualTo("代碼 " + p2.getA73() + " 不得與代碼 " + p3.getA73() + " 同時申報\n");
        assertThat(p3.getCheck()).isEqualTo("代碼 " + p3.getA73() + " 不得與代碼 " + p2.getA73() + " 同時申報\n");
    }

    @Test
    public void testCheckFdi() {
        NhiExtendTreatmentProcedure p1 = new NhiExtendTreatmentProcedure().a73("00121C");
        NhiExtendTreatmentProcedure p2 = new NhiExtendTreatmentProcedure().a73("89001C").a74("LR");
        NhiExtendTreatmentProcedure p3 = new NhiExtendTreatmentProcedure().a73("91011C");
        NhiExtendTreatmentProcedure p4 = new NhiExtendTreatmentProcedure().a73("89013C").a74("2233");
        Set<NhiExtendTreatmentProcedure> nhiExtendTreatmentProcedures = Stream.of(p1, p2, p3, p4).collect(Collectors.toSet());

        nhiService.checkFdi(nhiProcedureRepository.findAll(), nhiExtendTreatmentProcedures);
        assertThat(p2.getCheck()).isEqualTo("代碼 " + p2.getA73() + " 需填入正確牙位\n");
        assertThat(p3.getCheck()).isEqualTo("代碼 " + p3.getA73() + " 需填入牙位\n");
    }
}
