package io.dentall.totoro.domain;

import com.vladmihalcea.hibernate.type.array.StringArrayType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.time.Instant;
import java.util.Objects;

import static java.util.Arrays.asList;

@Entity
@Table(name = "document")
@TypeDef(name = "string-array", typeClass = StringArrayType.class)
public class Document extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @SequenceGenerator(
        name = "documentSeq",
        sequenceName = "document_seq",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "documentSeq"
    )
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "file_path", nullable = false)
    private String filePath;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "file_real_name", nullable = false)
    private String fileRealName;

    @Column(name = "file_extension")
    private String fileExtension;

    @Column(name = "file_size")
    private Long fileSize;

    @Column(name = "hashtags", nullable = false, columnDefinition = "text[]")
    @Type(type = "string-array")
    private String[] hashtags = new String[0];

    // 提供給前端使用的最後更新使用者
    @Column(name = "upload_user")
    private String uploadUser;

    // 提供給前端使用的上傳者
    @Column(name = "upload_time", nullable = false)
    private Instant uploadTime;

    // 提供給前端使用的最後上傳者
    @Column(name = "modified_user")
    private String modifiedUser;

    // 提供給前端使用的最後更新時間
    @Column(name = "modified_time", nullable = false)
    private Instant modifiedTime;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Document document = (Document) o;
        return id.equals(document.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Document{" +
            "id=" + id +
            ", title='" + title + '\'' +
            ", description='" + description + '\'' +
            ", filePath='" + filePath + '\'' +
            ", fileName='" + fileName + '\'' +
            ", fileRealName='" + fileRealName + '\'' +
            ", fileExtension='" + fileExtension + '\'' +
            ", fileSize=" + fileSize +
            ", hashtags=" + asList(hashtags) +
            ", uploadUser='" + uploadUser + '\'' +
            ", uploadTime=" + uploadTime +
            ", modifiedUser='" + modifiedUser + '\'' +
            ", modifiedTime=" + modifiedTime +
            '}';
    }
}
