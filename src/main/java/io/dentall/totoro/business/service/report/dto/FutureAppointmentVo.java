package io.dentall.totoro.business.service.report.dto;

import java.time.LocalDateTime;

public class FutureAppointmentVo {

    private long id;

    private String note;

    private LocalDateTime expectedArrivalTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public LocalDateTime getExpectedArrivalTime() {
        return expectedArrivalTime;
    }

    public void setExpectedArrivalTime(LocalDateTime expectedArrivalTime) {
        this.expectedArrivalTime = expectedArrivalTime;
    }
}
