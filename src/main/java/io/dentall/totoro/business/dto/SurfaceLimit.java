package io.dentall.totoro.business.dto;

import java.util.Arrays;
import java.util.List;

public class SurfaceLimit {

    private SurfaceLimitType type;

    private List<String> surface;

    private List<String> surfaceCombo;

    private Integer mustNumb;

    public SurfaceLimit(String rule) {
        try {
            String[] tokens = rule.split("@");
            for (String token : tokens) {
                String[] ruleToken = token.split("=");
                String keyToken = ruleToken.length > 0 ? ruleToken[0] : "OUT_OF_SWITCH";
                String valueToken = ruleToken.length > 1 ? ruleToken[1] : "";
                switch (keyToken) {
                    case "type":
                        if ("SURFACE_SPECIFIC_ONLY".equals(valueToken)) {
                            this.type = SurfaceLimitType.SURFACE_SPECIFIC_ONLY;
                        } else {
                            this.type = SurfaceLimitType.SURFACE_BLANK_ONLY;
                        }
                        break;
                    case "surface":
                        this.surface = Arrays.asList(valueToken.split(","));
                        break;
                    case "surfaceCombo":
                        this.surfaceCombo = Arrays.asList(valueToken.split(","));
                        break;
                    case "mustNumb":
                        this.mustNumb = Integer.valueOf(valueToken);
                        break;
                    default:
                        break;
                }
            }
        } catch (Exception e) {
            // do nothing
        }
    }

     public SurfaceLimitType getType() {
        return type;
    }

    public void setType(SurfaceLimitType type) {
        this.type = type;
    }

    public List<String> getSurface() {
        return surface;
    }

    public void setSurface(List<String> surface) {
        this.surface = surface;
    }

    public List<String> getSurfaceCombo() {
        return surfaceCombo;
    }

    public void setSurfaceCombo(List<String> surfaceCombo) {
        this.surfaceCombo = surfaceCombo;
    }

    public Integer getMustNumb() {
        return mustNumb;
    }

    public void setMustNumb(Integer mustNumb) {
        this.mustNumb = mustNumb;
    }
}
