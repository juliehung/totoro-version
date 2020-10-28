package io.dentall.totoro.domain.enumeration;

public enum PatientRelationshipType {
    SPOUSE1S("spouse1s", "getSpouse1S","setSpouse1S", "setSpouse2S"),
    SPOUSE2S("spouse2s", "getSpouse2S","setSpouse2S", "setSpouse1S"),
    CHILDREN("children", "getChildren", "setParents", "setChildren"),
    PARENTS("parents", "getParents", "setChildren", "setParents");

    private String urlPath;

    private String getterName;

    private String mainSetterName;

    private String subSetterName;

    PatientRelationshipType(String urlPath, String getterName, String mainSetterName, String subSetterName) {
        this.urlPath = urlPath;
        this.getterName = getterName;
        this.mainSetterName = mainSetterName;
        this.subSetterName = subSetterName;
    }

    public String getUrlPath() {
        return this.urlPath;
    }

    public String getGetterName() {
        return this.getterName;
    }

    public String getMainSetterName() {
        return mainSetterName;
    }

    public String getSubSetterName() {
        return subSetterName;
    }

    @Override
    public String toString() {
        return this.urlPath;
    }
}
