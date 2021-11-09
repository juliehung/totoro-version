package io.dentall.totoro.domain;

import io.dentall.totoro.domain.enumeration.LedgerReceiptRangeType;
import io.dentall.totoro.domain.enumeration.LedgerReceiptType;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ledger_receipt")
@EntityListeners(AuditingEntityListener.class)
public class LedgerReceipt extends AbstractAuditingEntity implements Serializable {

    @Id
    @SequenceGenerator(
        name = "ledgerReceiptSeq",
        sequenceName = "ledger_receipt_seq",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "ledgerReceiptSeq"
    )
    private Long id;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private LedgerReceiptType type;

    @Column(name = "range_type")
    @Enumerated(EnumType.STRING)
    private LedgerReceiptRangeType rangeType;

    @Column(name = "range_begin")
    private Instant rangeBegin;

    @Column(name = "range_end")
    private Instant rangeEnd;

    @Column(name = "signed")
    private Boolean signed;

    @Column(name = "stamp_tax")
    private Boolean stampTax;

    @Column(name = "time")
    private Instant time;

    @ManyToMany
    @JoinTable(
        name="ledger_ledger_receipt",
        joinColumns= @JoinColumn(
            name = "ledger_receipt_id",
            referencedColumnName="id"
        ),
        inverseJoinColumns= @JoinColumn(
            name = "ledger_id",
            referencedColumnName = "id"
        )
    )
    private List<Ledger> ledgers = new ArrayList<>();

    @OneToMany(
        fetch = FetchType.EAGER,
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    @JoinColumn(
        name = "ledger_receipt_id"
    )
    private List<LedgerReceiptPrintedRecord> ledgerReceiptPrintedRecords = new ArrayList<>();

    public List<Ledger> getLedgers() {
        return ledgers;
    }

    public void setLedgers(List<Ledger> ledgers) {
        this.ledgers = ledgers;
    }

    public List<LedgerReceiptPrintedRecord> getLedgerReceiptPrintedRecords() {
        return ledgerReceiptPrintedRecords;
    }

    public void setLedgerReceiptPrintedRecords(List<LedgerReceiptPrintedRecord> ledgerReceiptPrintedRecords) {
        this.ledgerReceiptPrintedRecords = ledgerReceiptPrintedRecords;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LedgerReceiptType getType() {
        return type;
    }

    public void setType(LedgerReceiptType type) {
        this.type = type;
    }

    public LedgerReceiptRangeType getRangeType() {
        return rangeType;
    }

    public void setRangeType(LedgerReceiptRangeType rangeType) {
        this.rangeType = rangeType;
    }

    public Instant getRangeBegin() {
        return rangeBegin;
    }

    public void setRangeBegin(Instant rangeBegin) {
        this.rangeBegin = rangeBegin;
    }

    public Instant getRangeEnd() {
        return rangeEnd;
    }

    public void setRangeEnd(Instant rangeEnd) {
        this.rangeEnd = rangeEnd;
    }

    public Boolean getSigned() {
        return signed;
    }

    public void setSigned(Boolean signed) {
        this.signed = signed;
    }

    public Boolean getStampTax() {
        return stampTax;
    }

    public void setStampTax(Boolean stampTax) {
        this.stampTax = stampTax;
    }

    public Instant getTime() {
        return time;
    }

    public void setTime(Instant time) {
        this.time = time;
    }
}
