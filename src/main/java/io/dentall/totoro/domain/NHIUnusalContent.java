package io.dentall.totoro.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A NHIUnusalContent.
 */
@Entity
@Table(name = "nhi_unusal_content")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class NHIUnusalContent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "no_seq_number")
    private String noSeqNumber;

    @Column(name = "got_seq_number")
    private String gotSeqNumber;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public NHIUnusalContent content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNoSeqNumber() {
        return noSeqNumber;
    }

    public NHIUnusalContent noSeqNumber(String noSeqNumber) {
        this.noSeqNumber = noSeqNumber;
        return this;
    }

    public void setNoSeqNumber(String noSeqNumber) {
        this.noSeqNumber = noSeqNumber;
    }

    public String getGotSeqNumber() {
        return gotSeqNumber;
    }

    public NHIUnusalContent gotSeqNumber(String gotSeqNumber) {
        this.gotSeqNumber = gotSeqNumber;
        return this;
    }

    public void setGotSeqNumber(String gotSeqNumber) {
        this.gotSeqNumber = gotSeqNumber;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        NHIUnusalContent nHIUnusalContent = (NHIUnusalContent) o;
        if (nHIUnusalContent.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), nHIUnusalContent.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "NHIUnusalContent{" +
            "id=" + getId() +
            ", content='" + getContent() + "'" +
            ", noSeqNumber='" + getNoSeqNumber() + "'" +
            ", gotSeqNumber='" + getGotSeqNumber() + "'" +
            "}";
    }
}
