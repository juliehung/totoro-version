package io.dentall.totoro.business.service.nhi.util;

import io.dentall.totoro.business.service.nhi.*;

import java.util.regex.Pattern;

public enum NhiRuleCheckScriptType {
    _8X(Pattern.compile("8."), NhiRuleCheckScript8X.class),
    _012XXC(Pattern.compile("012..C"), NhiRuleCheckScript012XXC.class),
    _89XXXC(Pattern.compile("89...C"), NhiRuleCheckScript89XXXC.class),
    _900XXC(Pattern.compile("900..C"), NhiRuleCheckScript900XXC.class),
    _910XXC(Pattern.compile("910..C"), NhiRuleCheckScript910XXC.class),
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
