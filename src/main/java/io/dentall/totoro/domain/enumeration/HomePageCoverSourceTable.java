package io.dentall.totoro.domain.enumeration;

public enum HomePageCoverSourceTable {
    DOC_NP,
    IMAGE;

    public static String listAll() {
        return DOC_NP.toString()
            .concat(",")
            .concat(IMAGE.toString());
    }
}
