package io.dentall.totoro.web.rest.vm;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import io.dentall.totoro.domain.Ledger;
import io.dentall.totoro.domain.Patient;

public class LedgerVM {
    @JsonUnwrapped
    Ledger ledger;

    Patient patient;

    public LedgerVM() {

    }

    public LedgerVM(Ledger ledger) {
        this.ledger = ledger;
    }

    public Ledger getLedger() {
        return ledger;
    }

    public void setLedger(Ledger ledger) {
        this.ledger = ledger;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}
