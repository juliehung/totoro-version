package io.dentall.totoro.service.dto;

import java.time.LocalDate;

public class InfectionControlDuration {
    private LocalDate infectionControlBeginDate;

    private LocalDate infectionControlEndDate;

    public InfectionControlDuration infectionControlBeginDate(LocalDate infectionControlBeginDate) {
        this.infectionControlBeginDate = infectionControlBeginDate;
        return this;
    }

    public InfectionControlDuration infectionControlEndDate(LocalDate infectionControlEndDate) {
        this.infectionControlEndDate = infectionControlEndDate;
        return this;
    }

    public LocalDate getInfectionControlBeginDate() {
        return infectionControlBeginDate;
    }

    public void setInfectionControlBeginDate(LocalDate infectionControlBeginDate) {
        this.infectionControlBeginDate = infectionControlBeginDate;
    }

    public LocalDate getInfectionControlEndDate() {
        return infectionControlEndDate;
    }

    public void setInfectionControlEndDate(LocalDate infectionControlEndDate) {
        this.infectionControlEndDate = infectionControlEndDate;
    }
}
