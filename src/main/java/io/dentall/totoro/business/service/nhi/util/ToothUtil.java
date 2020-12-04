package io.dentall.totoro.business.service.nhi.util;

import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class ToothUtil {

    public enum ToothPhase {
        P1("[1|5][1-9]|UR|UA|UB|FM"),
        P2("[2|6][1-9]|UL|UA|UB|FM"),
        P3("[3|7][1-9]|LL|LA|LB|FM"),
        P4("[4|8][1-9]|LR|LA|LB|FM");

        private final String regex;

        ToothPhase(String regex) {
            this.regex = regex;
        }

        public String getRegex() {
            return regex;
        }
    }

    /**
     * 輸入牙位，並回傳所佔據的象限
     * @param teeth 牙位
     * @return 回傳輸入牙位所佔據的牙位象限
     */
    public static HashSet<ToothPhase> markAsPhase(List<String> teeth) {
        HashSet<ToothPhase> result = new HashSet<>();

        teeth.stream().forEach(t -> {
            if (t.matches(ToothPhase.P1.getRegex())) {
                result.add(ToothPhase.P1);
            }
            if (t.matches(ToothPhase.P2.getRegex())) {
                result.add(ToothPhase.P2);
            }
            if (t.matches(ToothPhase.P3.getRegex())) {
                result.add(ToothPhase.P3);
            }
            if (t.matches(ToothPhase.P4.getRegex())) {
                result.add(ToothPhase.P4);
            }
        });

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

}

