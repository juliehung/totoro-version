package io.dentall.totoro.repository;

import io.dentall.totoro.domain.Tooth;
import io.dentall.totoro.service.dto.table.ToothTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Set;


/**
 * Spring Data  repository for the Tooth entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ToothRepository extends JpaRepository<Tooth, Long>, JpaSpecificationExecutor<Tooth> {

    Set<ToothTable> findToothByTreatmentProcedure_Id(Long id);

}
