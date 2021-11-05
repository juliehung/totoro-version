package io.dentall.totoro.domain;

import javax.persistence.Column;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

public class LedgerReceiptPrintedRecordCompositeId implements Serializable {

    @Column(name = "ledger_receipti_id")
    private Long ledgerReceiptId;

    @Column(name = "time")
    private Instant time;

    public LedgerReceiptPrintedRecordCompositeId() { }

    public LedgerReceiptPrintedRecordCompositeId(Long ledgerReceiptId, Instant time) {
        this.ledgerReceiptId = ledgerReceiptId;
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LedgerReceiptPrintedRecordCompositeId that = (LedgerReceiptPrintedRecordCompositeId) o;
        return Objects.equals(ledgerReceiptId, that.ledgerReceiptId) && Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ledgerReceiptId, time);
    }
}
