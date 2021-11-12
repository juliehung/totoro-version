package io.dentall.totoro.web.rest.vm;

import io.dentall.totoro.domain.Ledger;
import io.dentall.totoro.domain.enumeration.LedgerReceiptRangeType;
import io.dentall.totoro.domain.enumeration.LedgerReceiptType;

import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.List;

public class LedgerReceiptCreateVM {

    @NotNull
    private LedgerReceiptType type;

    @NotNull
    private LedgerReceiptRangeType rangeType;

    private Instant rangeBegin;

    private Instant rangeEnd;

    private Boolean signed;

    private Boolean stampTax;

    @NotNull
    private Instant time;

    @NotNull
    private Long gid;

    @NotNull
    private List<Ledger> ledgers;

    public List<Ledger> getLedgers() {
        return ledgers;
    }

    public void setLedgers(List<Ledger> ledgers) {
        this.ledgers = ledgers;
    }

    public Long getGid() {
        return gid;
    }

    public void setGid(Long gid) {
        this.gid = gid;
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
