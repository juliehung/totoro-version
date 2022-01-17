package io.dentall.totoro.repository;

import io.dentall.totoro.domain.Hashtag;
import io.dentall.totoro.domain.enumeration.HashTagType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface HashtagRepository extends JpaRepository<Hashtag, Long>, JpaSpecificationExecutor<Hashtag> {

    Page<Hashtag> findAllByTagNameIgnoreCaseLikeAndTagType(String search, HashTagType tagType, Pageable pageable);

    boolean existsByTagNameAndTagType(String tagName, HashTagType tagType);

    Optional<Hashtag> findByTagNameAndTagType(String tagName, HashTagType tagType);

    default boolean existsPatientDocumentTagByTagName(String tagName) {
        return existsByTagNameAndTagType(tagName, HashTagType.PatientDocument);
    }

    default Optional<Hashtag> findPatientDocumentTagByTagName(String tagName) {
        return findByTagNameAndTagType(tagName, HashTagType.PatientDocument);
    }


}
