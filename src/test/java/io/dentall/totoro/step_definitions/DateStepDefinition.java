package io.dentall.totoro.step_definitions;

import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static io.dentall.totoro.step_definitions.StepDefinitionUtil.DateParamTypeRegularExpression;
import static io.dentall.totoro.test.TestUtils.parseDate;
import static io.dentall.totoro.test.TestUtils.parseDays;
import static java.time.format.DateTimeFormatter.ofPattern;
import static org.assertj.core.api.Assertions.*;

public class DateStepDefinition extends AbstractStepDefinition {

    private LocalDate baseDate = LocalDate.of(2020, 06, 15);

    private LocalDate pastDate;

    private DateTimeFormatter dateTimeFormatter = ofPattern("yyyy-MM-dd").withZone(ZoneId.of("Asia/Taipei"));

    @ParameterType(value = DateParamTypeRegularExpression)
    public LocalDate dateForTest(String yearStr, String monthStr, String dateStr) {
        System.out.println("year=" + yearStr + ", month=" + monthStr + ", date=" + dateStr);
        return parseDate(baseDate, yearStr, monthStr, dateStr);
    }

    @Given("測試日期語義 {dateForTest}")
    public void changeToInstant(LocalDate pastDate) {
        this.pastDate = pastDate;
    }

    @Then("確認 {word} 是否與 {word} 一樣")
    public void testDate(String dateSemantic, String date) {
        assertThat(dateTimeFormatter.format(pastDate)).isEqualTo(date);
    }

}
