package io.dentall.totoro.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

/**
 * A NhiMedicalRecord.
 */
@Entity
@Table(name = "nhi_medical_record")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class NhiMedicalRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "jhi_date")
    private String date;

    @Column(name = "nhi_category")
    private String nhiCategory;

    @Column(name = "nhi_code")
    private String nhiCode;

    @Column(name = "part")
    private String part;

    @Column(name = "jhi_usage")
    private String usage;

    @Column(name = "total")
    private String total;

    @Column(name = "note")
    private String note;

    @Column(name = "days")
    private String days;

    @ManyToOne
    @JsonProperty(access = WRITE_ONLY)
    private NhiExtendPatient nhiExtendPatient;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public NhiMedicalRecord date(String date) {
        this.date = date;
        return this;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNhiCategory() {
        return nhiCategory;
    }

    public NhiMedicalRecord nhiCategory(String nhiCategory) {
        this.nhiCategory = nhiCategory;
        return this;
    }

    public void setNhiCategory(String nhiCategory) {
        this.nhiCategory = nhiCategory;
    }

    public String getNhiCode() {
        return nhiCode;
    }

    public NhiMedicalRecord nhiCode(String nhiCode) {
        this.nhiCode = nhiCode;
        return this;
    }

    public void setNhiCode(String nhiCode) {
        this.nhiCode = nhiCode;
    }

    public String getPart() {
        return part;
    }

    public NhiMedicalRecord part(String part) {
        this.part = part;
        return this;
    }

    public void setPart(String part) {
        this.part = part;
    }

    public String getUsage() {
        return usage;
    }

    public NhiMedicalRecord usage(String usage) {
        this.usage = usage;
        return this;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public String getTotal() {
        return total;
    }

    public NhiMedicalRecord total(String total) {
        this.total = total;
        return this;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getNote() {
        return note;
    }

    public NhiMedicalRecord note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDays() {
        return days;
    }

    public NhiMedicalRecord days(String days) {
        this.days = days;
        return this;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public NhiExtendPatient getNhiExtendPatient() {
        return nhiExtendPatient;
    }

    public NhiMedicalRecord nhiExtendPatient(NhiExtendPatient nhiExtendPatient) {
        this.nhiExtendPatient = nhiExtendPatient;
        return this;
    }

    public void setNhiExtendPatient(NhiExtendPatient nhiExtendPatient) {
        this.nhiExtendPatient = nhiExtendPatient;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NhiMedicalRecord that = (NhiMedicalRecord) o;
        if (nhiExtendPatient == null || nhiExtendPatient.getId() == null) {
            return false;
        }
        return Objects.equals(date, that.date) &&
            Objects.equals(nhiCategory, that.nhiCategory) &&
            Objects.equals(nhiCode, that.nhiCode) &&
            Objects.equals(part, that.part) &&
            Objects.equals(usage, that.usage) &&
            Objects.equals(total, that.total) &&
            Objects.equals(note, that.note) &&
            nhiExtendPatient.getId().equals(that.nhiExtendPatient.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "NhiMedicalRecord{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", nhiCategory='" + getNhiCategory() + "'" +
            ", nhiCode='" + getNhiCode() + "'" +
            ", part='" + getPart() + "'" +
            ", usage='" + getUsage() + "'" +
            ", total='" + getTotal() + "'" +
            ", note='" + getNote() + "'" +
            ", days='" + getDays() + "'" +
            "}";
    }
}
