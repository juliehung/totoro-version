package io.dentall.totoro.domain;

import io.dentall.totoro.domain.enumeration.NoteType;
import org.springframework.boot.actuate.audit.listener.AbstractAuditListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "note")
@EntityListeners(AbstractAuditListener.class)
public class Note extends AbstractAuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "NoteSequenceGenerator")
    @SequenceGenerator(name = "NoteSequenceGenerator", sequenceName = "note_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    @Column(name = "type")
    private NoteType type;

    @Column(name = "content")
    private String content;

    @ManyToOne
    @JoinColumn(name="patient_id", nullable=false, updatable=false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name="user_id", updatable=false)
    private User user;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

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

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
