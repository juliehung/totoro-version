package io.dentall.totoro.domain.enumeration;

public enum PatientRelationshipType {
    SPOUSE1("spouse1", "getSpouse1S","setSpouse1S", "setSpouse2S"),
    SPOUSE2("spouse2", "getSpouse2S","setSpouse2S", "setSpouse1S"),
    CHILDREN("children", "getChildren", "setChildren", "setParents"),
    PARENTS("parents", "getParents", "setParents", "setChildren");

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
