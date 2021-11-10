package io.dentall.totoro.web.rest.vm;

import javax.validation.constraints.NotNull;
import java.time.Instant;

public class LedgerReceiptPrintedRecordCreateVM {

    @NotNull
    private Instant time;

    private String filePath;

    private String fileName;

    public Instant getTime() {
        return time;
    }

    public void setTime(Instant time) {
        this.time = time;
    }

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
}
