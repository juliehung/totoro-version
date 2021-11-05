package io.dentall.totoro.domain;

import io.dentall.totoro.domain.enumeration.LedgerReceiptRangeType;
import io.dentall.totoro.domain.enumeration.LedgerReceiptType;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@Entity(name = "ledger_receipt")
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

    @Column(name = "type")
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
