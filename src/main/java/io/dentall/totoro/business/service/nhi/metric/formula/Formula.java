package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.filter.Collector;

import java.math.BigDecimal;

public interface Formula {

    BigDecimal calculate(Collector collector);
}
