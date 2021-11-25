package io.dentall.totoro.business.service.report.context;

import io.dentall.totoro.business.service.report.ReportBuilderService;
import io.dentall.totoro.business.service.report.dto.DrugVo;
import io.dentall.totoro.business.service.report.dto.FutureAppointmentVo;
import io.dentall.totoro.business.service.report.dto.NhiVo;
import io.dentall.totoro.business.service.report.dto.OwnExpenseVo;
import io.dentall.totoro.config.TimeConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.Period;
import java.time.YearMonth;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static io.dentall.totoro.service.util.DateTimeUtil.formatToMinguoDate;
import static java.util.Arrays.stream;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static net.logstash.logback.encoder.org.apache.commons.lang.StringUtils.isNumeric;
import static org.apache.commons.lang3.StringUtils.isBlank;

public class ReportHelper {

    private static final Logger log = LoggerFactory.getLogger(ReportHelper.class);

    private ReportHelper() {
    }

    public static String displayDisposalDate(YearMonth yearMonth) {
        return yearMonth == null ? "" : formatToMinguoDate(yearMonth, "yyy/MM");
    }

    public static String displayDisposalDate(LocalDate localDate) {
        return localDate == null ? "" : formatToMinguoDate(localDate, "yyy/MM/dd");
    }

    public static String displayAge(Period age) {
        return age.getYears() + "y" + age.getMonths() + "m";
    }

    public static Period toAge(LocalDate birth, LocalDate disposalDate) {
        if (birth == null || disposalDate == null) {
            return Period.ZERO;
        }
        return Period.between(birth, disposalDate);
    }

    public static String displayBirthAge(LocalDate birth, String age) {
        return formatToMinguoDate(birth, "yyy/MM/dd") + "(" + age + ")";
    }

    public static String displayFutureAppointmentMemo(List<FutureAppointmentVo> appointmentList) {
        return appointmentList.stream().map(
            appointment -> formatToMinguoDate(appointment.getExpectedArrivalTime().atOffset(TimeConfig.ZONE_OFF_SET), "yyy/MM/dd HH:mm:ss")
                + "_"
                + ofNullable(appointment.getNote()).map(note -> note.replace("\n", " ").replace("\r", " ")).orElse("")
        ).collect(joining("\n"));
    }

    public static String displaySubsequentNhi(List<NhiVo> subsequentList) {
        return subsequentList.stream().map(
            subsequent -> formatToMinguoDate(subsequent.getRegistrationDate().atOffset(TimeConfig.ZONE_OFF_SET), "yyy/MM/dd HH:mm:ss")
                + "_"
                + subsequent.getProcedureCode()
                + "(" + subsequent.getProcedureTooth() + ")"
        ).collect(joining("\n"));
    }

    public static String displaySubsequentOwnExpense(List<OwnExpenseVo> subsequentList) {
        return subsequentList.stream().map(
            subsequent -> formatToMinguoDate(subsequent.getRegistrationDate().atOffset(TimeConfig.ZONE_OFF_SET), "yyy/MM/dd HH:mm:ss")
                + "_"
                + subsequent.getProcedureName()
                + "(" + subsequent.getProcedureTooth() + ")"
        ).collect(joining("\n"));
    }

    public static String displaySubsequentDrug(List<DrugVo> subsequentList) {
        return subsequentList.stream().map(
            subsequent -> formatToMinguoDate(subsequent.getRegistrationDate().atOffset(TimeConfig.ZONE_OFF_SET), "yyy/MM/dd HH:mm:ss")
                + "_"
                + subsequent.getDrugName()
        ).collect(joining("\n"));
    }

    public static String displayNextAvailableTreatmentDate(LocalDate localDate) {
        return formatToMinguoDate(localDate, "yyy/MM/dd") + "開始";
    }

    public static List<Report> getReportList(BookSetting bookSetting, ApplicationContext applicationContext) {
        return retrieveSetting(bookSetting).stream()
            .map(setting -> {
                Optional<Class<ReportBuilderService>> builderServiceOptional = setting.getBuilderService();
                if (!builderServiceOptional.isPresent()) {
                    return null;
                }
                ReportBuilderService service = applicationContext.getBean(builderServiceOptional.get());
                return service.build(setting);
            })
            .filter(Objects::nonNull)
            .collect(toList());
    }

    public static List<ReportSetting> retrieveSetting(BookSetting bookSetting) {
        return stream(bookSetting.getClass().getDeclaredMethods())
            .filter(m -> m.getName().startsWith("get"))
            .filter(m -> m.getName().endsWith("ReportSetting"))
            .filter(m -> ReportSetting.class.isAssignableFrom(m.getReturnType()))
            .map(m -> {
                try {
                    return (ReportSetting) m.invoke(bookSetting);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    log.error("process method=" + m.getName() + " error", e);
                }
                return null;
            })
            .filter(Objects::nonNull)
            .collect(toList());
    }


    // 先複製過來使用，等程式合併進release後再改用
    public static int calculatePtCount(
        Long disposalId,
        LocalDate date,
        Long patientId,
        String cardNumber,
        int count,
        Set<String> existPatientCardNumber,
        Set<Long> existDisposal
    ) {
        if (isBlank(cardNumber) || date == null || disposalId == null) {
            return count;
        }

        if (existDisposal.contains(disposalId)) {
            return count;
        } else {
            existDisposal.add(disposalId);
        }

        boolean isNumeric = isNumericCardNumber(cardNumber);

        if (isNumeric) {
            String key = date.getMonth().getValue() + "_" + patientId + "_" + cardNumber;
            if (existPatientCardNumber.contains(key)) {
                return count;
            } else {
                existPatientCardNumber.add(key);
            }
        }

        return count + 1;
    }

    public static boolean isNumericCardNumber(String cardNumber) {
        return isNumeric(cardNumber);
    }
}
