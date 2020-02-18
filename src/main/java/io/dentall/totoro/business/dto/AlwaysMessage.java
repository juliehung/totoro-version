package io.dentall.totoro.business.dto;

public class AlwaysMessage {

    private String message;

    public AlwaysMessage(String rule) {
        try {
            String[] tokens = rule.split("@");
            for (String token : tokens) {
                String[] ruleToken = token.split("=");
                String keyToken = ruleToken.length > 0 ? ruleToken[0] : "OUT_OF_SWITCH";
                String valueToken = ruleToken.length > 1 ? ruleToken[1] : "";
                switch (keyToken) {
                    case "message":
                        this.message = valueToken;
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
}
