package io.dentall.totoro.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dentall.totoro.domain.enumeration.ImageRelationDomain;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

/**
 * A ImageRelation.
 */
@Entity
@Table(name = "image_relation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ImageRelation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "domain")
    private ImageRelationDomain domain;

    @NotNull
    @Column(name = "domain_id")
    private Long domainId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonProperty(access = WRITE_ONLY)
    private Image image;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ImageRelationDomain getDomain() {
        return domain;
    }

    public ImageRelation domain(ImageRelationDomain domain) {
        this.domain = domain;
        return this;
    }

    public void setDomain(ImageRelationDomain domain) {
        this.domain = domain;
    }

    public Long getDomainId() {
        return domainId;
    }

    public ImageRelation domainId(Long domainId) {
        this.domainId = domainId;
        return this;
    }

    public void setDomainId(Long domainId) {
        this.domainId = domainId;
    }

    @ApiModelProperty(hidden = true)
    public Image getImage() {
        return image;
    }

    public ImageRelation image(Image image) {
        this.image = image;
        return this;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ImageRelation imageRelation = (ImageRelation) o;
        if (imageRelation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), imageRelation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ImageRelation{" +
            "id=" + getId() +
            ", domain='" + getDomain() + "'" +
            ", domainId=" + getDomainId() +
            "}";
    }
}
