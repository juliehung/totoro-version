package io.dentall.totoro.business.dto;

import java.util.Arrays;
import java.util.List;

public class IdentityLimit {

    private List<String> identity;

    private Integer age;

    private Long interval;

    private String message;

    public IdentityLimit(String rule) {
        try {
            String[] tokens = rule.split("@");
            for (String token : tokens) {
                String[] ruleToken = token.split("=");
                String keyToken = ruleToken.length > 0 ? ruleToken[0] : "OUT_OF_SWITCH";
                String valueToken = ruleToken.length > 1 ? ruleToken[1] : "";
                switch (keyToken) {
                    case "identity":
                        this.identity = Arrays.asList(valueToken.split(","));
                        break;
                    case "age":
                        this.age = Integer.valueOf(valueToken);
                        break;
                    case "interval":
                        this.interval = Long.valueOf(valueToken);
                        break;
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

    public List<String> getIdentity() {
        return identity;
    }

    public void setIdentity(List<String> identity) {
        this.identity = identity;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Long getInterval() {
        return interval;
    }

    public void setInterval(Long interval) {
        this.interval = interval;
    }
}
