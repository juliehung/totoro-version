package io.dentall.totoro.business.service.nhi.util;

import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;

import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ToothUtil {

    private static String toothRegexLiteral = "\\d{2}";

    private static String toothPositionRegexLiteral = "(?<=\\G..)";

    private static Pattern toothRegexPattern = Pattern.compile(toothRegexLiteral);

    private static Pattern toothPositionRegexPattern = Pattern.compile(toothPositionRegexLiteral);

    public enum ToothPhase {
        P1("[1|5][1-9]|UR|UA|UB|FM", "第一象限"),
        P2("[2|6][1-9]|UL|UA|UB|FM", "第二象限"),
        P3("[3|7][1-9]|LL|LA|LB|FM", "第三象限"),
        P4("[4|8][1-9]|LR|LA|LB|FM", "第四象限");

        private final String regex;

        private final String nameOfPhase;

        ToothPhase(String regex, String nameOfPhase) {
            this.regex = regex;
            this.nameOfPhase = nameOfPhase;
        }

        public String getRegex() {
            return regex;
        }

        public String getNameOfPhase() {
            return nameOfPhase;
        }

        public String toString() {
            return this.nameOfPhase;
        }

    }

    public static class ToothPhaseComparator implements Comparator<ToothPhase> {
        public int compare(ToothPhase o1, ToothPhase o2) {
            return o1.ordinal() <= o2.ordinal() ? -1 : 1;
        }
    }

    public static Comparator<ToothPhase> toothPhaseComparator() {
        return new ToothPhaseComparator();
    }

    /**
     * 輸入牙位，並回傳所佔據的象限
     * @param teeth 牙位
     * @return 回傳輸入牙位所佔據的牙位象限
     */
    public static List<ToothPhase> markAsPhase(List<String> teeth) {
        List<ToothPhase> result = new ArrayList<>();
        HashSet<ToothPhase> h = new HashSet<>();

        teeth.stream().forEach(t -> {
            if (t.matches(ToothPhase.P1.getRegex())) {
                h.add(ToothPhase.P1);
            }
            if (t.matches(ToothPhase.P2.getRegex())) {
                h.add(ToothPhase.P2);
            }
            if (t.matches(ToothPhase.P3.getRegex())) {
                h.add(ToothPhase.P3);
            }
            if (t.matches(ToothPhase.P4.getRegex())) {
                h.add(ToothPhase.P4);
            }
        });

        h.forEach(p -> result.add(p));
        result.sort((o1, o2) -> o1.ordinal() <= o2.ordinal() ?-1 : 1);

        return result;
    }

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
     * 計算牙齒顆數
     *
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
     * @param toothString 牙齒字串
     * @return
     */
    public static long getToothCount(ToothConstraint tc, String toothString) {
        if (toothString == null || toothString.isEmpty()) {
            return 0L;
        }

        return Arrays.stream(toothPositionRegexPattern.split(toothString))
            .filter(tooth -> toothRegexPattern.matcher(tooth).matches())
            .filter(tooth -> tooth.matches(tc.getRegex()))
            .count();
    }

    public static String multipleToothToDisplay(String teeth) {
        if (StringUtils.isBlank(teeth) ||
            teeth.length() % 2 != 0
        ) {
            return "";
        } else {
            return String.join("/", ToothUtil.splitA74(teeth));
        }
    }

    public static List<String> listDuplicatedTooth(String a74A, String a74B) {
        List<String> result = new ArrayList<>();

        List<String> toothListA = ToothUtil.splitA74(
            a74A
        );
        List<String> toothListB = ToothUtil.splitA74(
            a74B
        );

        toothListA.stream()
            .forEach(t -> {
                if (toothListB.contains(t)) {
                    result.add(t);
                }
            });

        return result;
    }

    public static List<ToothPhase> listDuplicatedToothPhase(String a74A, String a74B) {
        List<ToothPhase> result = new ArrayList<>();

        List<ToothPhase> listA = ToothUtil.markAsPhase(
            ToothUtil.splitA74(
                a74A
            )
        );

        List<ToothPhase> listB = ToothUtil.markAsPhase(
            ToothUtil.splitA74(
                a74B
            )
        );

        listA.stream()
            .forEach(t -> {
                if (listB.contains(t)) {
                    result.add(t);
                }
            });

        return result;
    }
}

