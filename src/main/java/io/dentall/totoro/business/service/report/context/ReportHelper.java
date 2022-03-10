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
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.time.YearMonth;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static io.dentall.totoro.service.util.DateTimeUtil.formatToMinguoDate;
import static java.util.Arrays.stream;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.*;
import static net.logstash.logback.encoder.org.apache.commons.lang.StringUtils.isNumeric;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

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

    public static int countingSettingCount(BookSetting bookSetting) {
        return (int) stream(bookSetting.getClass().getDeclaredMethods())
            .filter(m -> m.getName().startsWith("get"))
            .filter(m -> m.getName().endsWith("ReportSetting"))
            .filter(m -> ReportSetting.class.isAssignableFrom(m.getReturnType()))
            .count();
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

    /**
     *
     * @param pixels 一個字寬佔多少pixels，例字體「Calibri」的「0」佔「7 pixels」相當excel font size「11」
     * @param visibleCharacters 可見字數，中文字的話，visibleCharacters需乘以兩倍
     * @return 得出可填入sheet.setColumnWidth的with數字
     */
    public static int calculateColumnWith(int pixels, int visibleCharacters) {
        BigDecimal a = new BigDecimal(visibleCharacters * pixels + 5);
        BigDecimal b = new BigDecimal(pixels);
        return a.divide(b, RoundingMode.CEILING).intValue() * 256;
    }

    /**
     * 因診察代碼不一定會寫進treatment_procedure(醫生沒有特別在處置單上新增診察代碼)，所以要特別產生沒有在treatment_procedure的診察代碼資料出來
     */
    public static List<NhiVo> generateExamNhiVoList(List<NhiVo> nhiVoList) {
        // 找出 treatment_procedure 已有診察代碼的 disposalId
        Set<Long> theDisposalIdThatExamCodeIncludedInTreatment =
            nhiVoList
                .stream()
                .parallel()
                .filter(data -> isNotBlank(data.getExamCode()) && data.getExamCode().equals(data.getProcedureCode()))
                .map(NhiVo::getDisposalId)
                .collect(toSet());

        // 產生以診察代碼為主的資料
        return new ArrayList<>(nhiVoList
            .stream()
            .parallel()
            .filter(data -> !theDisposalIdThatExamCodeIncludedInTreatment.contains(data.getDisposalId())) // 濾掉已經有在treatment_procedure的診察代碼的處置單
            .filter(data -> isNotBlank(data.getExamCode()))
            .reduce(new ConcurrentHashMap<Long, NhiVo>(nhiVoList.size() / 3), (map, nhiVo) -> {
                map.computeIfAbsent(nhiVo.getDisposalId(), disposalId -> {
                    NhiVo examNhiVo = ReportMapper.INSTANCE.mapToNhiVo(nhiVo);
                    examNhiVo.setProcedureId(null);
                    examNhiVo.setProcedureCode(nhiVo.getExamCode());
                    examNhiVo.setProcedureTooth(null);
                    examNhiVo.setProcedureSurface(null);
                    examNhiVo.setProcedurePoint(nhiVo.getExamPoint());
                    return examNhiVo;
                });
                return map;
            }, (map1, map2) -> {
                map1.putAll(map2);
                return map1;
            }).values());
    }
}
