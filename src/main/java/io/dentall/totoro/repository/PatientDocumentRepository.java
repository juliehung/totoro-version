package io.dentall.totoro.repository;

import io.dentall.totoro.domain.Document_;
import io.dentall.totoro.domain.PatientDocument;
import io.dentall.totoro.domain.PatientDocumentDisposal_;
import io.dentall.totoro.domain.PatientDocument_;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public interface PatientDocumentRepository extends JpaRepository<PatientDocument, Long>, JpaSpecificationExecutor<PatientDocument> {

    @EntityGraph("PatientDocument.all")
    @Override
    Optional<PatientDocument> findById(Long aLong);

    @EntityGraph("PatientDocument.all")
    @Override
    Page<PatientDocument> findAll(Specification<PatientDocument> spec, Pageable pageable);

    default Specification<PatientDocument> specification(Long patientId, Long disposalId, String search, boolean searchForTag) {
        return (root, query, cb) -> {
            List<Predicate> predicateList = new ArrayList<>();

            Predicate patientIdPredicate = cb.equal(root.get(PatientDocument_.patientId), patientId);
            predicateList.add(patientIdPredicate);

            if (nonNull(disposalId)) {
                Predicate disposalIdPredicate = cb.equal(root.get(PatientDocument_.disposal).get(PatientDocumentDisposal_.id), disposalId);
                predicateList.add(disposalIdPredicate);
            }

            if (searchForTag) {
                predicateList.add(
                    cb.gt(
                        cb.function(
                            "array_position",
                            Integer.class,
                            root.get(PatientDocument_.document).get(Document_.hashtags),
                            cb.literal(search)),
                        0));
            } else if (isNotBlank(search)) {
                Path<String> titlePath = root.get(PatientDocument_.document).get(Document_.title);
                Path<String> descriptionPath = root.get(PatientDocument_.document).get(Document_.description);
                predicateList.add(
                    cb.or(cb.like(cb.lower(titlePath), "%" + search.toLowerCase() + "%"),
                        cb.like(cb.lower(descriptionPath), "%" + search.toLowerCase() + "%")));
            }

            return cb.and(predicateList.toArray(new Predicate[0]));
        };
    }
}
