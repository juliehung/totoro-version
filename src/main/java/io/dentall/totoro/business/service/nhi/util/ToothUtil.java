package io.dentall.totoro.business.service.nhi.util;

import com.google.common.collect.ImmutableMap;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ToothUtil {

    private static final Map<ToothConstraint, String> toothConstraints =
        ImmutableMap.of(
            ToothConstraint.FRONT_TOOTH, "^[1-8][1-3]$|^[1-9]9$"
        );


    /**
     * 使用 ToothConstraint 決定牙齒屬於哪個範圍的檢核標準，並且利用 regex 來判斷，僅提供單牙
     */
    public boolean validateToothConstraint(ToothConstraint tc, String tooth) {
        return tooth.matches(toothConstraints.get(tc));
    }

    public List<String> splitA74(String a74) {
        return Arrays.asList(a74.split("(?<=\\G..)"));
    }

}

