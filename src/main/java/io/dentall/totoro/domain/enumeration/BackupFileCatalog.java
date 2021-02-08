package io.dentall.totoro.domain.enumeration;

public enum BackupFileCatalog {
    NHI_LOG("nhi-log", "nhi-log", "text/plain"),
    DAY_UPLOAD_XML("day-upload-xml", "day-upload-xml", "text/xml"),
    MONTH_DECLARE_XML("month-declare-xml", "month-declare-xml",  "text/xml");

    private String urlPath;

    private String remotePath;

    private String fileExtension;

    BackupFileCatalog(String urlPath, String remotePath, String fileExtension) {
        this.urlPath = urlPath;
        this.remotePath = remotePath;
        this.fileExtension = fileExtension;
    }

    public String getRemotePath() {
        return remotePath;
    }

    public void setRemotePath(String remotePath) {
        this.remotePath = remotePath;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    @Override
    public String toString() {
        return this.urlPath;
    }
}
