package io.dentall.totoro.business.service.nhi.metric.util;

import java.math.BigDecimal;

public class NumericUtils {

    private NumericUtils() {}

    public static BigDecimal divide(BigDecimal val1, BigDecimal val2) {
        return val1.divide(val2, 2, BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal divide(Long val1, Long val2) {
        return divide(new BigDecimal(val1), new BigDecimal(val2));
    }
}
