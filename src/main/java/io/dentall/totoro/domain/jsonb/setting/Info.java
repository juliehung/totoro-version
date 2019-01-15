package io.dentall.totoro.domain.jsonb.setting;

import java.io.Serializable;

public class Info implements Serializable {

    private String theme;

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    @Override
    public String toString() {
        return "Info{" +
            "theme='" + getTheme() + "'" +
            "}";
    }
}
