package io.dentall.totoro.web.rest.vm;

import java.time.Instant;
import java.util.Arrays;
import java.util.Objects;

public class DocumentVM {

    private Long id;

    private String title;

    private String description;

    private String filePath;

    private String fileName;

    private String fileRealName;

    private String fileExtension;

    private Long fileSize;

    private String[] hashtags;

    private String uploadUser;

    private Instant uploadTime;

    private String modifiedUser;

    private Instant modifiedTime;

    private String url;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileRealName(String fileRealName) {
        this.fileRealName = fileRealName;
    }

    public String getFileRealName() {
        return fileRealName;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public String[] getHashtags() {
        return hashtags;
    }

    public void setHashtags(String[] hashtags) {
        this.hashtags = hashtags;
    }

    public void setUploadUser(String uploadUser) {
        this.uploadUser = uploadUser;
    }

    public String getUploadUser() {
        return uploadUser;
    }

    public void setUploadTime(Instant uploadTime) {
        this.uploadTime = uploadTime;
    }

    public Instant getUploadTime() {
        return uploadTime;
    }

    public void setModifiedUser(String modifiedUser) {
        this.modifiedUser = modifiedUser;
    }

    public String getModifiedUser() {
        return modifiedUser;
    }

    public void setModifiedTime(Instant modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public Instant getModifiedTime() {
        return modifiedTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DocumentVM document = (DocumentVM) o;
        return id.equals(document.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "DocumentVM{" +
            "id=" + id +
            ", title='" + title + '\'' +
            ", description='" + description + '\'' +
            ", filePath='" + filePath + '\'' +
            ", fileName='" + fileName + '\'' +
            ", fileRealName='" + fileRealName + '\'' +
            ", fileExtension='" + fileExtension + '\'' +
            ", fileSize=" + fileSize +
            ", hashtags=" + Arrays.toString(hashtags) +
            ", uploadUser='" + uploadUser + '\'' +
            ", uploadTime=" + uploadTime +
            ", modifiedUser='" + modifiedUser + '\'' +
            ", modifiedTime=" + modifiedTime +
            ", url='" + url + '\'' +
            '}';
    }
}
