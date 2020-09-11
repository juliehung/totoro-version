package io.dentall.totoro.business.service.nhi.util;

import com.google.common.collect.ImmutableMap;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ToothUtil {

    /**
     * 根據 牙位限動規則 ，牙面錯誤時提供的訊息
     */
    private static final Map<ToothConstraint, String> toothConstraintsFailureMessage =
        ImmutableMap.of(
            ToothConstraint.FRONT_TOOTH, "限填前牙牙位，11-19, 21-29, 31-39, 41-49, 51-59, 61-69, 71-79, 81-89"
        );

    /**
     * 根據 牙位限動規則 ，牙面檢核規則 (regex)
     */
    private static final Map<ToothConstraint, String> toothConstraints =
        ImmutableMap.of(
            ToothConstraint.FRONT_TOOTH, "^[1-8][1-3]$|^[1-9]9$"
        );


    /**
     * 使用 ToothConstraint 決定牙齒屬於哪個範圍的檢核標準，並且利用 regex 來判斷，僅提供單牙
     *
     * @param tc 提供例如 前牙限定、後牙限定、FM限定⋯⋯等 regex
     * @param tooth 單牙牙位
     * @return true -> 核可的牙位， false -> 不允許的牙位
     */
    public static boolean validatedToothConstraint(ToothConstraint tc, String tooth) {
        return tooth.matches(toothConstraints.get(tc));
    }

    /**
     * 把 a74 兩兩切分為數個 單牙牙位
     *
     * @param a74 健保處置牙位位置
     * @return 解析 a74 並回傳 數個兩碼字串
     */
    public static List<String> splitA74(String a74) {
        return Arrays.asList(a74.split("(?<=\\G..)"));
    }

    /**
     * 提供指定 ToothConstraint 檢核失敗時的錯誤訊息
     *
     * @param tc 提供例如 前牙限定、後牙限定、FM限定⋯⋯等 regex
     * @return
     */
    public static String getToothConstraintsFailureMessage(ToothConstraint tc) {
        return toothConstraintsFailureMessage.get(tc);
    }

}

