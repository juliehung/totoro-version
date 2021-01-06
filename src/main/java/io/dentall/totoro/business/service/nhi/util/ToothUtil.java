package io.dentall.totoro.business.service.nhi.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.constraints.NotNull;

import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;

public class ToothUtil {

    private static String toothRegexLiteral = "\\d{2}";

    private static String toothPositionRegexLiteral = "(?<=\\G..)";

    private static Pattern toothRegexPattern = Pattern.compile(toothRegexLiteral);

    private static Pattern toothPositionRegexPattern = Pattern.compile(toothPositionRegexLiteral);


    /**
     * 使用 ToothConstraint 決定牙齒屬於哪個範圍的檢核標準，並且利用 regex 來判斷，僅提供單牙
     *
     * @param tc 提供例如 前牙限定、後牙限定、FM限定⋯⋯等 regex
     * @param tooth 單牙牙位
     * @return true -> 核可的牙位， false -> 不允許的牙位
     */
    public static boolean validatedToothConstraint(@NotNull ToothConstraint tc, @NotNull String tooth) {
        return tooth.matches(tc.getRegex());
    }

    /**
     * 把 a74 兩兩切分為數個 單牙牙位
     *
     * @param a74 健保處置牙位位置
     * @return 解析 a74 並回傳 數個兩碼字串
     */
    public static List<String> splitA74(String a74) {
        return StringUtils.isNotBlank(a74) ? Arrays.asList(a74.split("(?<=\\G..)")) : new ArrayList<>();
    }

    /**
     * 提供指定 ToothConstraint 檢核失敗時的錯誤訊息
     *
     * @param tc 提供例如 前牙限定、後牙限定、FM限定⋯⋯等 regex
     * @return 檢核失敗錯誤訊息
     */
    public static String getToothConstraintsFailureMessage(@NotNull ToothConstraint tc) {
        return tc.getMessage();
    }

    /**
     * <pre>
     * input=3M3G-> count=0
     * input=M3G3-> count=0
     * input=M33-> count=0
     * input=0-> count=0
     * input=01-> count=1
     * input=012-> count=1
     * input=0123-> count=2
     * input=01234-> count=2
     * input=0F12GEWE39-> count=2
     * input=0F12GEWE397-> count=2
     * </pre>
     * 
     * @param toothString
     * @return
     */
    public static long getToothCount(String toothString) {
        if (toothString == null || toothString.isEmpty()) {
            return 0L;
        }

        return Arrays.asList(toothPositionRegexPattern.split(toothString))
                .stream().filter(tooth -> {
                    return toothRegexPattern.matcher(tooth).matches();
                }).count();
    }
}

