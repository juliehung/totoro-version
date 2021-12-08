package io.dentall.totoro.business.service.nhi.util;

import io.dentall.totoro.business.service.nhi.*;

import java.util.regex.Pattern;

public enum NhiRuleCheckScriptType {
    _8X(Pattern.compile("8."), NhiRuleCheckScript8X.class),
    _9X(Pattern.compile("9."), NhiRuleCheckScript9X.class),
    _00XXXC(Pattern.compile("00...C"), NhiRuleCheckScript00XXXC.class),
    _012XXC(Pattern.compile("012..C"), NhiRuleCheckScript012XXC.class),
    _340XXC(Pattern.compile("340..C"), NhiRuleCheckScript340XXC.class),
    _89XXXC(Pattern.compile("89...C"), NhiRuleCheckScript89XXXC.class),
    _900XXC(Pattern.compile("90...C"), NhiRuleCheckScript90XXXC.class),
    _910XXC(Pattern.compile("91...C"), NhiRuleCheckScript91XXXC.class),
    _92XXXC(Pattern.compile("92...C"), NhiRuleCheckScript92XXXC.class),
    _96XXXC(Pattern.compile("96...C"), NhiRuleCheckScript96XXXC.class),
    _P3XXXX(Pattern.compile("P3...."), NhiRuleCheckScriptP3XXXX.class),
    _P6XXXX(Pattern.compile("P6...."), NhiRuleCheckScriptP6XXXX.class),
    ;

    private Pattern regex;

    private Class scriptClass;

    NhiRuleCheckScriptType(
        Pattern regex,
        Class scriptClass
    ) {
        this.regex = regex;
        this.scriptClass = scriptClass;
    }

    public Pattern getRegex() {
        return regex;
    }

    public Class getScriptClass() {
        return scriptClass;
    }
}
