package io.dentall.totoro.web.rest.vm;

import io.dentall.totoro.domain.enumeration.LedgerReceiptRangeType;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class LedgerReceiptExcelVM {
    private Instant time;

    private String displayName;

    private String projectCode;

    private String ledgerGroupType;

    private Boolean stampTax;

    private LedgerReceiptRangeType receiptRangeType;

    private List<LedgerReceiptPrintedRecordVM> ledgerReceiptPrintedRecords = new ArrayList<>();

    public Instant getTime() {
        return time;
    }

    public void setTime(Instant time) {
        this.time = time;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getLedgerGroupType() {
        return ledgerGroupType;
    }

    public void setLedgerGroupType(String ledgerGroupType) {
        this.ledgerGroupType = ledgerGroupType;
    }

    public Boolean getStampTax() {
        return stampTax;
    }

    public void setStampTax(Boolean stampTax) {
        this.stampTax = stampTax;
    }

    public LedgerReceiptRangeType getReceiptRangeType() {
        return receiptRangeType;
    }

    public void setReceiptRangeType(LedgerReceiptRangeType receiptRangeType) {
        this.receiptRangeType = receiptRangeType;
    }

    public List<LedgerReceiptPrintedRecordVM> getLedgerReceiptPrintedRecords() {
        return ledgerReceiptPrintedRecords;
    }

    public void setLedgerReceiptPrintedRecords(List<LedgerReceiptPrintedRecordVM> ledgerReceiptPrintedRecords) {
        this.ledgerReceiptPrintedRecords = ledgerReceiptPrintedRecords;
    }
}
