package io.dentall.totoro.domain;

import io.dentall.totoro.domain.enumeration.HashTagType;

import javax.persistence.*;

@Entity
@Table(name = "hashtag")
public class Hashtag extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @SequenceGenerator(
        name = "hashtagSeq",
        sequenceName = "hashtag_seq",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "hashtagSeq"
    )
    private Long id;

    @Column(name = "tag_name", nullable = false)
    private String tagName;

    @Column(name = "tag_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private HashTagType tagType;

    @Column(name = "reference_count", nullable = false)
    private Integer referenceCount;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getTagName() {
        return tagName;
    }

    public HashTagType getTagType() {
        return tagType;
    }

    public void setTagType(HashTagType tagType) {
        this.tagType = tagType;
    }

    public void setReferenceCount(Integer referenceCount) {
        this.referenceCount = referenceCount;
    }

    public Integer getReferenceCount() {
        return referenceCount;
    }

    @Override
    public String toString() {
        return "Hashtag{" +
                "id=" + id + '\'' +
                "tagName=" + tagName + '\'' +
                "tagType=" + tagType + '\'' +
                "referenceCount=" + referenceCount + '\'' +
                '}';
    }
}
