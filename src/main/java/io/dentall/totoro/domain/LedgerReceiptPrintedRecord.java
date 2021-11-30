package io.dentall.totoro.domain;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name = "ledger_receipt_printed_record")
@EntityListeners(AuditingEntityListener.class)
public class LedgerReceiptPrintedRecord extends AbstractAuditingEntity implements Serializable {

    @Id
    @SequenceGenerator(
        name = "ledgerReceiptPrintedRecordSeq",
        sequenceName = "ledger_receipt_printed_record_seq",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "ledgerReceiptPrintedRecordSeq"
    )
    private Long id;

    @Column(name = "time")
    private Instant time;

    @ManyToOne
    private LedgerReceipt ledgerReceipt;

    public LedgerReceipt getLedgerReceipt() {
        return ledgerReceipt;
    }

    public void setLedgerReceipt(LedgerReceipt ledgerReceipt) {
        this.ledgerReceipt = ledgerReceipt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getTime() {
        return time;
    }

    public void setTime(Instant time) {
        this.time = time;
    }
}
