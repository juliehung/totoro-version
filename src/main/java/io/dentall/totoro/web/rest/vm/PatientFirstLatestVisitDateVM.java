package io.dentall.totoro.web.rest.vm;

import java.time.Instant;

public class PatientFirstLatestVisitDateVM {
    private Instant firstVisitDate;
    private Instant latestVisitDate;

    public Instant getFirstVisitDate() {
        return firstVisitDate;
    }

    public void setFirstVisitDate(Instant firstVisitDate) {
        this.firstVisitDate = firstVisitDate;
    }

    public Instant getLatestVisitDate() {
        return latestVisitDate;
    }

    public void setLatestVisitDate(Instant latestVisitDate) {
        this.latestVisitDate = latestVisitDate;
    }
}
