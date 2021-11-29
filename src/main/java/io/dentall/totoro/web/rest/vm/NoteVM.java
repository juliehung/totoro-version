package io.dentall.totoro.web.rest.vm;

import io.dentall.totoro.domain.enumeration.NoteType;
import io.dentall.totoro.service.dto.DoctorVM;

public class NoteVM {

    private Long id;

    private NoteType type;

    private String content;

    private Long userId;

    private Long patientId;

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
}
