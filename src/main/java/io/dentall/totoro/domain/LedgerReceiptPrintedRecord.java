package io.dentall.totoro.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;
import java.time.Instant;

@Entity(name = "ledger_receipt_printed_record")
@IdClass( LedgerReceiptPrintedRecordCompositeId.class )
public class LedgerReceiptPrintedRecord extends AbstractAuditingEntity implements Serializable {

    @Id
    private Long ledgerReceiptId;

    @Id
    private Instant time;

    public Long getLedgerReceiptId() {
        return ledgerReceiptId;
    }

    public void setLedgerReceiptId(Long ledgerReceiptId) {
        this.ledgerReceiptId = ledgerReceiptId;
    }

    public Instant getTime() {
        return time;
    }

    public void setTime(Instant time) {
        this.time = time;
    }
}
