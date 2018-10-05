package io.dentall.totoro.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Link {

    @NotNull
    @Size(min = 1, max = 50)
    private String gmail;

    @NotNull
    private String calendarName;

    @Override
    public String toString() {
        return "Link{" +
            "gmail='" + gmail + '\'' +
            ", calendarName=" + calendarName +
            '}';
    }

    public void setCalendarName(String calendarName) {
        this.calendarName = calendarName;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public String getGmail() {
        return gmail;
    }

    public String getCalendarName() {
        return calendarName;
    }

}
