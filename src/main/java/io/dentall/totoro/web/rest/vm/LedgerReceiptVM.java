package io.dentall.totoro.web.rest.vm;

import io.dentall.totoro.domain.enumeration.LedgerReceiptRangeType;
import io.dentall.totoro.domain.enumeration.LedgerReceiptType;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class LedgerReceiptVM {

    private Long id;

    private LedgerReceiptType type;

    private LedgerReceiptRangeType rangeType;

    private Instant rangeBegin;

    private Instant rangeEnd;

    private Boolean signed;

    private Boolean stampTax;

    private Instant time;

    private String filePath;

    private String fileName;

    private String url;

    private List<LedgerReceiptPrintedRecordVM> ledgerReceiptPrintedRecords = new ArrayList<>();

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public List<LedgerReceiptPrintedRecordVM> getLedgerReceiptPrintedRecords() {
        return ledgerReceiptPrintedRecords;
    }

    public void setLedgerReceiptPrintedRecords(List<LedgerReceiptPrintedRecordVM> ledgerReceiptPrintedRecords) {
        this.ledgerReceiptPrintedRecords = ledgerReceiptPrintedRecords;
    }
}
