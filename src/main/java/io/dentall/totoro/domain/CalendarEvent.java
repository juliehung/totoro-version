package io.dentall.totoro.domain;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class CalendarEvent {

    @NotNull
    private String eventName;

    @NotNull
    private Date start;

    @NotNull
    private Date end;

    @Override
    public String toString() {
        return "CalendarEvent{" +
            "calendarName='" + eventName + '\'' +
            ", start='" + start + '\'' +
            ", end=" + end +
            '}';
    }

    public String getEventName() {
        return eventName;
    }

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

}
