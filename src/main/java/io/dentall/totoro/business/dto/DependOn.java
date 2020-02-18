package io.dentall.totoro.business.dto;

public class DependOn {

    private String code;

    private Long interval;

    private String message;

    public DependOn(String rule) {
        try {
            String[] tokens = rule.split("@");
            for (String token : tokens) {
                String[] ruleToken = token.split("=");
                String keyToken = ruleToken.length > 0 ? ruleToken[0] : "OUT_OF_SWITCH";
                String valueToken = ruleToken.length > 1 ? ruleToken[1] : "";
                switch (keyToken) {
                    case "code":
                        this.code = valueToken;
                        break;
                    case "message":
                        this.message = valueToken;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getInterval() {
        return interval;
    }

    public void setInterval(Long interval) {
        this.interval = interval;
    }
}
