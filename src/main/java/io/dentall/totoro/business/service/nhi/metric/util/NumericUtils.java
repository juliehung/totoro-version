package io.dentall.totoro.business.service.nhi.metric.util;

import java.math.BigDecimal;

public class NumericUtils {

    public static final BigDecimal ONE_THOUSAND = new BigDecimal(100);

    private NumericUtils() {
    }

    public static BigDecimal toPercentage(BigDecimal val) {
        return val.multiply(ONE_THOUSAND);
    }

    public static BigDecimal divide(BigDecimal val1, BigDecimal val2) {
        return val1.divide(val2, 2, BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal divide(Long val1, Long val2) {
        return divide(new BigDecimal(val1), new BigDecimal(val2));
    }

    public static BigDecimal divide(int val1, int val2) {
        return divide(new BigDecimal(val1), new BigDecimal(val2));
    }
}
