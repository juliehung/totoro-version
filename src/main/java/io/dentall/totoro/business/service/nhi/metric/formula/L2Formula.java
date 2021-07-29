package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.filter.Collector;
import io.dentall.totoro.business.service.nhi.metric.meta.*;

import java.math.BigDecimal;

/**
 * 診察費點數 ＠date-15＠ (@Exam-1@+@Exam-2@+@Exam-3@+@Exam-4@)
 */
public class L2Formula implements Formula {

    private final String sourceName;

    public L2Formula(String sourceName) {
        this.sourceName = sourceName;
    }

    @Override
    public BigDecimal calculate(Collector collector) {
        Exam1 exam1 = new Exam1(sourceName);
        Exam2 exam2 = new Exam2(sourceName);
        Exam3 exam3 = new Exam3(sourceName);
        Exam4 exam4 = new Exam4(sourceName);
        collector.apply(exam1).apply(exam2).apply(exam3).apply(exam4);
        long result = exam1.getResult() + exam2.getResult() + exam3.getResult() + exam4.getResult();

        return new BigDecimal(result);
    }
}
