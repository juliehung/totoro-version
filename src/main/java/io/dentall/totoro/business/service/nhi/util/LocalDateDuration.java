package io.dentall.totoro.business.service.nhi.util;

import java.time.LocalDate;

public class LocalDateDuration {

    private LocalDate begin;

    private LocalDate end;

    public LocalDateDuration begin(LocalDate begin) {
        this.begin = begin;
        return this;
    }

    public LocalDateDuration end(LocalDate end) {
        this.end = end;
        return this;
    }

    public LocalDate getBegin() {
        return begin;
    }

    public void setBegin(LocalDate begin) {
        this.begin = begin;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }
}
