package io.dentall.totoro.util;

import io.dentall.totoro.config.TimeConfig;
import io.dentall.totoro.service.util.DateTimeUtil;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class DataGenerator {

    public static final Long ID_1 = 1L;

    public static final Long ID_2 = 2L;

    public static final String PATIENT_IDENTITY_1 = "001";

    public static final String PATIENT_IDENTITY_2 = "002";

    public static final String NHI_CODE_1 = "FAKE1";

    public static final String NHI_CODE_2 = "FAKE2";

    public static final List<String> NHI_CODE_LIST_1 = Arrays.asList(NHI_CODE_1);

    public static final List<String> NHI_CODE_LIST_2 = Arrays.asList(NHI_CODE_1, NHI_CODE_2);

    public static final String NHI_TREATMENT_DATE_MIN = "0010101";

    public static final String NHI_TREATMENT_DATE_MAX = "9991231";

    public static final LocalDate NHI_TREATMENT_DATE_NOW = Instant.now().atZone(TimeConfig.ZONE_OFF_SET).toLocalDate();

    public static final String NHI_TREATMENT_DATE_NOW_STRING = DateTimeUtil.transformLocalDateToRocDate(Instant.now());

    public static final LocalDate NHI_TREATMENT_DATE_NOW_PLUS_1_MONTH = Instant.now().plus(DateTimeUtil.NHI_1_MONTH).atZone(TimeConfig.ZONE_OFF_SET).toLocalDate();

    public static final String NHI_TREATMENT_DATE_NOW_PLUS_1_MONTH_STRING = DateTimeUtil.transformLocalDateToRocDate(Instant.now().plus(DateTimeUtil.NHI_1_MONTH));

    public static final LocalDate NHI_TREATMENT_DATE_NOW_PLUS_3_MONTH = Instant.now().plus(DateTimeUtil.NHI_3_MONTH).atZone(TimeConfig.ZONE_OFF_SET).toLocalDate();

    public static final String NHI_TREATMENT_DATE_NOW_PLUS_3_MONTH_STRING = DateTimeUtil.transformLocalDateToRocDate(Instant.now().plus(DateTimeUtil.NHI_3_MONTH));

    public static final String TOOTH_PERMANENT_1 = "12";

    public static final String TOOTH_PERMANENT_2 = "1223";

    public static final String TOOTH_PERMANENT_3 = "122345";

    public static final String TOOTH_DECIDUOUS_1 = "51";

    public static final String TOOTH_DECIDUOUS_2 = "5162";

    public static final String TOOTH_DECIDUOUS_3 = "516273";

    public static final String SURFACE_BLANK = "";

}
