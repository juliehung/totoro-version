package io.dentall.totoro.web.rest.vm;

import java.time.LocalDate;

public class PatientDueDateVM {
    LocalDate dueDate;

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
}
