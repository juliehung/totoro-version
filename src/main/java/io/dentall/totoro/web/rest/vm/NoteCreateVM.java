package io.dentall.totoro.web.rest.vm;

import io.dentall.totoro.domain.enumeration.NoteType;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

public class NoteCreateVM {

    @NotNull
    @Enumerated(EnumType.STRING)
    private NoteType type;

    private String content;

    @NotNull
    private Long patientId;

    private Long userId;

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

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
