package io.dentall.totoro.business.dto;

import java.util.Arrays;
import java.util.List;

public class OtherToothDeclarationInterval {
    private OtherToothDeclaratioinType type;

    private Long interval;

    private List<String> code = null;

    public OtherToothDeclaratioinType getType() {
        return type;
    }

    public void setType(OtherToothDeclaratioinType type) {
        this.type = type;
    }

    public Long getInterval() {
        return interval;
    }

    public void setInterval(Long interval) {
        this.interval = interval;
    }

    public List<String> getCode() {
        return code;
    }

    public void setCode(List<String> code) {
        this.code = code;
    }

    public OtherToothDeclarationInterval(String rule) {
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
                    case "type":
                        if ("PT".equals(valueToken)) {
                            this.type = OtherToothDeclaratioinType.PT;
                        } else if ("DT".equals(valueToken)) {
                            this.type = OtherToothDeclaratioinType.DT;
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
}
