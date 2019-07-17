package io.dentall.totoro.repository;

import io.dentall.totoro.domain.DocNp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.print.Doc;
import java.util.List;


/**
 * Spring Data  repository for the DocNp entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DocNpRepository extends JpaRepository<DocNp, Long> {

    List<DocNp> findByPatientId(Long patientId);

    List<DocNp> findByPatientIdAndEsignId(Long patientId, Long esignId);

}
