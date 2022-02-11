package io.dentall.totoro.web.rest.vm;

import io.dentall.totoro.domain.enumeration.NoteType;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.Instant;
import java.util.Objects;

public class NoteVM {

    private Long id;

    @Enumerated(value = EnumType.STRING)
    private NoteType type;

    private String content;

    private Long userId;

    private Long patientId;

    private String lastModifiedBy;

    private Instant lastModifiedDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NoteType getType() {
        return type;
    }

    public void setType(NoteType type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NoteVM noteVM = (NoteVM) o;
        return Objects.equals(id, noteVM.id) && type == noteVM.type && Objects.equals(content, noteVM.content) && Objects.equals(userId, noteVM.userId) && Objects.equals(patientId, noteVM.patientId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, content, userId, patientId);
    }

}
