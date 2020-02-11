package io.dentall.totoro.business.dto;

import java.util.Arrays;
import java.util.List;

public class OtherCodeDeclarationInterval {

    private List<String> code = null;

    private Long interval;

    private OtherCodeDeclarationIntervalRange range;

    public OtherCodeDeclarationInterval(String rule) {
        try {
            String[] tokens = rule.split("@");
            for (String token : tokens) {
                String[] ruleToken = token.split("=");
                String keyToken = ruleToken.length > 0 ? ruleToken[0] : "OUT_OF_SWITCH";
                String valueToken = ruleToken.length > 1 ? ruleToken[1] : "";
                switch (keyToken) {
                    case "code":
                        this.code = Arrays.asList(valueToken.split(","));
                        break;
                    case "range":
                        if ("YEAR".equals(valueToken)) {
                            this.range = OtherCodeDeclarationIntervalRange.YEAR;
                        } else if ("MONTH".equals(valueToken)) {
                            this.range = OtherCodeDeclarationIntervalRange.MONTH;
                        }
                        break;
                    case "interval":
                        this.interval = Long.valueOf(valueToken);
                        break;
                    default:
                        break;
                }
            }
        } catch (Exception e) {
            // do nothing
        }
    }

    public OtherCodeDeclarationIntervalRange getRange() {
        return range;
    }

    public void setRange(OtherCodeDeclarationIntervalRange range) {
        this.range = range;
    }

    public List<String> getCode() {
        return code;
    }

    public void setCode(List<String> code) {
        this.code = code;
    }

    public Long getInterval() {
        return interval;
    }

    public void setInterval(Long interval) {
        this.interval = interval;
    }
}
