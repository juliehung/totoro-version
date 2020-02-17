package io.dentall.totoro.business.dto;

import com.univocity.parsers.annotations.BooleanString;
import com.univocity.parsers.annotations.Convert;
import com.univocity.parsers.annotations.Parsed;
import io.dentall.totoro.service.NhiService;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

public class Rule {

    @Parsed
    private String code;

    @BooleanString(falseStrings = {""}, trueStrings = {"y", "Y"})
    @Parsed(field = "x_ray", defaultNullRead = "")
    private Boolean xRay;

    @BooleanString(falseStrings = {""}, trueStrings = {"y", "Y"})
    @Parsed(field = "medical_record", defaultNullRead = "")
    private Boolean medicalRecord;

    @Parsed
    @Convert(conversionClass = NhiService.Splitter.class, args = ",")
    private String[] exclude;

    @Parsed
    private String interval;

    @Parsed
    @Convert(conversionClass = NhiService.Splitter.class, args = ",")
    private String[] fdi;

    @Parsed(field = "position_limit", defaultNullRead = "")
    @Convert(conversionClass = NhiService.Splitter.class, args = "=")
    private String[] positionLimit;

    @Parsed(field = "surface_limit", defaultNullRead = "")
    @Convert(conversionClass = NhiService.Splitter.class, args = {"="})
    private String[] surfaceLimit;

    @Parsed(field = "other_code_declaration_interval")
    @Convert(conversionClass = NhiService.Splitter.class, args = {";"})
    private String[] otherCodeDeclarationInterval;

    @Parsed(field = "other_tooth_declaration_interval")
    @Convert(conversionClass = NhiService.Splitter.class, args = {";"})
    private String[] otherToothDeclarationInterval;

    @Parsed(field = "identity_limit")
    @Convert(conversionClass = NhiService.Splitter.class, args = {";"})
    private String[] identityLimit;

    public static Rule allPass() {
        Rule allPass = new Rule();
        allPass.setCode(null);

        return allPass;
    }

    public String[] getIdentityLimit() {
        return identityLimit;
    }

    public void setIdentityLimit(String[] identityLimit) {
        this.identityLimit = identityLimit;
    }

    public void setOtherCodeDeclarationInterval(String[] otherCodeDeclarationInterval) {
        this.otherCodeDeclarationInterval = otherCodeDeclarationInterval;
    }

    public String[] getOtherToothDeclarationInterval() {
        return otherToothDeclarationInterval;
    }

    public void setOtherToothDeclarationInterval(String[] otherToothDeclarationInterval) {
        this.otherToothDeclarationInterval = otherToothDeclarationInterval;
    }

    public String[] getOtherCodeDeclarationInterval() {
        return otherCodeDeclarationInterval;
    }

    public String[] getSurfaceLimit() {
        return surfaceLimit;
    }

    public void setSurfaceLimit(String[] surfaceLimit) {
        this.surfaceLimit = surfaceLimit;
    }

    public String[] getPositionLimit() {
        return positionLimit;
    }

    public void setPositionLimit(String[] positionLimit) {
        this.positionLimit = positionLimit;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setXRay(Boolean xRay) {
        this.xRay = xRay;
    }

    public Boolean getXRay() {
        return xRay;
    }

    public void setMedicalRecord(Boolean medicalRecord) {
        this.medicalRecord = medicalRecord;
    }

    public Boolean getMedicalRecord() {
        return medicalRecord;
    }

    public void setExclude(String[] exclude) {
        this.exclude = exclude;
    }

    public String[] getExclude() {
        return exclude;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public String getInterval() {
        return interval;
    }

    public void setFdi(String[] fdi) {
        this.fdi = fdi;
    }

    public String[] getFdi() {
        return fdi;
    }

    public static Supplier<Map<Integer, Integer>> emptyQuadrantsCount = () -> new HashMap<Integer, Integer>() {{
        put(1, 0);
        put(2, 0);
        put(3, 0);
        put(4, 0);
    }};

    public static List<Integer> getQuadrants(String val) {
        List<Integer> quadrants = new ArrayList<>();
        switch (val) {
            case "FM":
                quadrants.addAll(Arrays.asList(1, 2, 3, 4));
                break;
            case "UB":
            case "UA":
                quadrants.addAll(Arrays.asList(1, 2));
                break;
            case "UR":
            case "11":
            case "12":
            case "13":
            case "14":
            case "15":
            case "16":
            case "17":
            case "18":
            case "51":
            case "52":
            case "53":
            case "54":
            case "55":
                quadrants.add(1);
                break;
            case "UL":
            case "21":
            case "22":
            case "23":
            case "24":
            case "25":
            case "26":
            case "27":
            case "28":
            case "61":
            case "62":
            case "63":
            case "64":
            case "65":
                quadrants.add(2);
                break;
            case "LB":
            case "LA":
                quadrants.addAll(Arrays.asList(3, 4));
                break;
            case "LR":
            case "41":
            case "42":
            case "43":
            case "44":
            case "45":
            case "46":
            case "47":
            case "48":
            case "81":
            case "82":
            case "83":
            case "84":
            case "85":
                quadrants.add(4);
                break;
            case "LL":
            case "31":
            case "32":
            case "33":
            case "34":
            case "35":
            case "36":
            case "37":
            case "38":
            case "71":
            case "72":
            case "73":
            case "74":
            case "75":
                quadrants.add(3);
                break;
            default:
                break;
        }

        return quadrants;
    }

    public static Supplier<Map<String, Integer>> emptyPermanentTeethCount = () -> new HashMap<String, Integer>() {{
        put("11", 0);
        put("12", 0);
        put("13", 0);
        put("14", 0);
        put("15", 0);
        put("16", 0);
        put("17", 0);
        put("18", 0);
        put("21", 0);
        put("22", 0);
        put("23", 0);
        put("24", 0);
        put("25", 0);
        put("26", 0);
        put("27", 0);
        put("28", 0);
        put("31", 0);
        put("32", 0);
        put("33", 0);
        put("34", 0);
        put("35", 0);
        put("36", 0);
        put("37", 0);
        put("38", 0);
        put("41", 0);
        put("42", 0);
        put("43", 0);
        put("44", 0);
        put("45", 0);
        put("46", 0);
        put("47", 0);
        put("48", 0);
    }};

    public static Function<String, List<String>> getPermanentTeeth = val -> {
        List<String> teeth = new ArrayList<>();
        switch (val) {
            case "FM":
                teeth.addAll(Arrays.asList(
                    "11", "12", "13", "14", "15", "16", "17", "18",
                    "21", "22", "23", "24", "25", "26", "27", "28",
                    "31", "32", "33", "34", "35", "36", "37", "38",
                    "41", "42", "43", "44", "45", "46", "47", "48"
                ));
                break;
            case "UB":
                teeth.addAll(Arrays.asList(
                    "11", "12", "13", "14", "15", "16", "17", "18",
                    "21", "22", "23", "24", "25", "26", "27", "28"
                ));
                break;
            case "UA":
                teeth.addAll(Arrays.asList(
                    "11", "12", "13",
                    "21", "22", "23"
                ));
                break;
            case "UR":
                teeth.addAll(Arrays.asList(
                    "11", "12", "13", "14", "15", "16", "17", "18"
                ));
                break;
            case "UL":
                teeth.addAll(Arrays.asList(
                    "21", "22", "23", "24", "25", "26", "27", "28"
                ));
                break;
            case "LB":
                teeth.addAll(Arrays.asList(
                    "31", "32", "33", "34", "35", "36", "37", "38",
                    "41", "42", "43", "44", "45", "46", "47", "48"
                ));
                break;
            case "LA":
                teeth.addAll(Arrays.asList(
                    "31", "32", "33",
                    "41", "42", "43"
                ));
                break;
            case "LR":
                teeth.addAll(Arrays.asList(
                    "41", "42", "43", "44", "45", "46", "47", "48"
                ));
                break;
            case "LL":
                teeth.addAll(Arrays.asList(
                    "31", "32", "33", "34", "35", "36", "37", "38"
                ));
                break;
            case "11":
            case "12":
            case "13":
            case "14":
            case "15":
            case "16":
            case "17":
            case "18":
            case "21":
            case "22":
            case "23":
            case "24":
            case "25":
            case "26":
            case "27":
            case "28":
            case "31":
            case "32":
            case "33":
            case "34":
            case "35":
            case "36":
            case "37":
            case "38":
            case "41":
            case "42":
            case "43":
            case "44":
            case "45":
            case "46":
            case "47":
            case "48":
                teeth.addAll(Collections.singletonList(val));
                break;
            default:
                break;
        }

        return teeth;
    };

    public static Supplier<Map<String, Integer>> emptyDeciduousTeethCount = () -> new HashMap<String, Integer>() {{
        put("51", 0);
        put("52", 0);
        put("53", 0);
        put("54", 0);
        put("55", 0);
        put("61", 0);
        put("62", 0);
        put("63", 0);
        put("64", 0);
        put("65", 0);
        put("71", 0);
        put("72", 0);
        put("73", 0);
        put("74", 0);
        put("75", 0);
        put("81", 0);
        put("82", 0);
        put("83", 0);
        put("84", 0);
        put("85", 0);
    }};

    public static Function<String, List<String>> getDeciduousTeeth = val -> {
        List<String> teeth = new ArrayList<>();
        switch (val) {
            case "FM":
                teeth.addAll(Arrays.asList(
                    "51", "52", "53", "54", "55",
                    "61", "62", "63", "64", "65",
                    "71", "72", "73", "74", "75",
                    "81", "82", "83", "84", "85"
                ));
                break;
            case "UB":
                teeth.addAll(Arrays.asList(
                    "51", "52", "53", "54", "55",
                    "61", "62", "63", "64", "65"
                ));
                break;
            case "UA":
                teeth.addAll(Arrays.asList(
                    "51", "52", "53",
                    "61", "62", "63"
                ));
                break;
            case "UR":
                teeth.addAll(Arrays.asList(
                    "51", "52", "53", "54", "55"
                ));
                break;
            case "UL":
                teeth.addAll(Arrays.asList(
                    "61", "62", "63", "64", "65"
                ));
                break;
            case "LB":
                teeth.addAll(Arrays.asList(
                    "71", "72", "73", "74", "75",
                    "81", "82", "83", "84", "85"
                ));
                break;
            case "LA":
                teeth.addAll(Arrays.asList(
                    "71", "72", "73",
                    "81", "82", "83"
                ));
                break;
            case "LR":
                teeth.addAll(Arrays.asList(
                    "81", "82", "83", "84", "85"
                ));
                break;
            case "LL":
                teeth.addAll(Arrays.asList(
                    "71", "72", "73", "74", "75"
                ));
                break;
            case "51":
            case "52":
            case "53":
            case "54":
            case "55":
            case "61":
            case "62":
            case "63":
            case "64":
            case "65":
            case "71":
            case "72":
            case "73":
            case "74":
            case "75":
            case "81":
            case "82":
            case "83":
            case "84":
            case "85":
                teeth.addAll(Collections.singletonList(val));
                break;
            default:
                break;
        }

        return teeth;
    };
}
