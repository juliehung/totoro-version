package io.dentall.totoro.util;

import io.dentall.totoro.service.util.DateTimeUtil;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {io.dentall.totoro.config.TimeConfig.class})
public class DataGenerator {

    public static final Long ID_1 = 1L;

    public static final Long ID_2 = 2L;

    public static final String PATIENT_IDENTITY_1 = "001";

    public static final String PATIENT_IDENTITY_2 = "002";

    public static final String NHI_CODE_1 = "FAKE1";

    public static final String NHI_CODE_2 = "FAKE2";

    public static final String NHI_TREATMENT_DATE_MIND = "0010101";

    public static final String NHI_TREATMENT_DATE_MAX = "9991231";

    public static final String NHI_TREATMENT_DATE_NOW = DateTimeUtil.transformLocalDateToRocDate(Instant.now());

    public static final String NHI_TREATMENT_DATE_NOW_PLUS_1_MONTH = DateTimeUtil.transformLocalDateToRocDate(Instant.now().plus(DateTimeUtil.NHI_1_MONTH));

    public static final String NHI_TREATMENT_DATE_NOW_PLUS_3_MONTH = DateTimeUtil.transformLocalDateToRocDate(Instant.now().plus(DateTimeUtil.NHI_3_MONTH));

    public static final String TOOTH_PERMANENT_1 = "12";

    public static final String TOOTH_PERMANENT_2 = "1223";

    public static final String TOOTH_PERMANENT_3 = "122345";

}
