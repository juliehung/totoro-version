package io.dentall.totoro.web.rest.vm;

import java.time.Instant;

public class LedgerReceiptPrintedRecordVM {

    private Instant time;

    public Instant getTime() {
        return time;
    }

    public void setTime(Instant time) {
        this.time = time;
    }
}
