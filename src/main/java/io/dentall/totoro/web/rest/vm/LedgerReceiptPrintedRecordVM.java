package io.dentall.totoro.web.rest.vm;

import io.dentall.totoro.domain.LedgerReceiptPrintedRecord;
import io.dentall.totoro.domain.enumeration.LedgerReceiptRangeType;
import io.dentall.totoro.domain.enumeration.LedgerReceiptType;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class LedgerReceiptPrintedRecordVM {

    private Long id;

    private Instant time;

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
