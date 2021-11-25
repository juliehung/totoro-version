package io.dentall.totoro.domain;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import io.dentall.totoro.business.service.report.context.ReportCategory;
import io.dentall.totoro.domain.enumeration.BatchStatus;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@Entity
@Table(name = "report_record")
public class ReportRecord extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reportRecordSequenceGenerator")
    @SequenceGenerator(name = "reportRecordSequenceGenerator", sequenceName = "report_record_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "category", nullable = false)
    private ReportCategory category;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status", nullable = false)
    private BatchStatus status;

    @Type(type = "jsonb")
    @Column(name = "attrs", nullable = false)
    private Map<String, String> attrs = new HashMap<>();

    @Column(name = "file_path", nullable = false)
    private String filePath;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "comment")
    private String comment;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setCategory(ReportCategory category) {
        this.category = category;
    }

    public ReportCategory getCategory() {
        return category;
    }

    public void setStatus(BatchStatus status) {
        this.status = status;
    }

    public BatchStatus getStatus() {
        return status;
    }

    public void setAttrs(Map<String, String> attrs) {
        this.attrs = attrs;
    }

    public Map<String, String> getAttrs() {
        return attrs;
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

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    @Override
    public String toString() {
        return "ReportRecord{" +
            "id=" + id + '\'' +
            "category=" + category + '\'' +
            "status=" + status + '\'' +
            "attrs=" + attrs + '\'' +
            "filePath=" + filePath + '\'' +
            "fileName=" + fileName + '\'' +
            "comment=" + comment + '\'' +
            "createdBy=" + getCreatedBy() + '\'' +
            "createdDate=" + getCreatedDate() + '\'' +
            "lastModifiedBy=" + getLastModifiedBy() + '\'' +
            "lastModifiedDate=" + getLastModifiedDate() + '\'' +
            '}';
    }
}
