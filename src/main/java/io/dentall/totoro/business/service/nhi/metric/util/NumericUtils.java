package io.dentall.totoro.business.service.nhi.metric.util;

import lombok.val;

import java.math.BigDecimal;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ROUND_HALF_UP;

public class NumericUtils {

    public static final BigDecimal ONE_HUNDRED = new BigDecimal(100);

    private NumericUtils() {
    }

    public static BigDecimal toOppositePercentage(Long val1, Long val2) {
       return toOppositePercentage(new BigDecimal(val1), new BigDecimal(val2));
    }

    public static BigDecimal toOppositePercentage(BigDecimal val1, BigDecimal val2) {
        return val2.subtract(val1).multiply(ONE_HUNDRED).divide(val2, 2, ROUND_HALF_UP);
    }

    public static BigDecimal toPercentage(int val1, int val2) {
        return toPercentage(new BigDecimal(val1), new BigDecimal(val2));
    }

    public static BigDecimal toPercentage(Long val1, Long val2) {
        return toPercentage(new BigDecimal(val1), new BigDecimal(val2));
    }

    public static BigDecimal toPercentage(BigDecimal val1, BigDecimal val2) {
        return val1.multiply(ONE_HUNDRED).divide(val2, 2, ROUND_HALF_UP);
    }

    public static BigDecimal divide(BigDecimal val1, BigDecimal val2) {
        return val1.divide(val2, 2, ROUND_HALF_UP);
    }

    public static BigDecimal divide(Long val1, Long val2) {
        return divide(new BigDecimal(val1), new BigDecimal(val2));
    }

    public static BigDecimal divide(int val1, int val2) {
        return divide(new BigDecimal(val1), new BigDecimal(val2));
    }
}
