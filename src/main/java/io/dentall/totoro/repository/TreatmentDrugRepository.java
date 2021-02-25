package io.dentall.totoro.repository;

import io.dentall.totoro.domain.TreatmentDrug;
import io.dentall.totoro.service.dto.PlainDisposalInfoDTO;
import io.dentall.totoro.service.dto.table.TreatmentDrugsTable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Set;


/**
 * Spring Data  repository for the TreatmentDrug entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TreatmentDrugRepository extends JpaRepository<TreatmentDrug, Long>, JpaSpecificationExecutor<TreatmentDrug> {

    Set<TreatmentDrugsTable> findTreatmentDrugByPrescription_Id(Long id);

    boolean existsByDrugId(Long drugId);

    @Query(
        countQuery =
            "select count(*)" +
            "from treatment_drug td " +
            "    left join drug dg on dg.id = td.drug_id " +
            "    left join disposal d on td.prescription_id = d.prescription_id " +
            "    left join registration r on d.registration_id = r.id " +
            "    left join appointment a on a.registration_id = r.id " +
            "    left join patient p on a.patient_id = p.id " +
            "    left join jhi_user ju on a.doctor_user_id = ju.id " +
            "where r.arrival_time is not null " +
            "  and td.prescription_id is not null " +
            "  and dg.id = :id " +
            "  and r.arrival_time between :begin and :end ",
        nativeQuery = true,
        value =
            "select " +
            "       r.arrival_time as arrivalTime, " +
            "       ju.first_name as doctorName, " +
            "       p.id as patientId, " +
            "       p.name as patientName, " +
            "       p.birth as birth, " +
            "       dg.chinese_name as infoContent, " +
            "       p.note  as note " +
            "from treatment_drug td " +
            "    left join drug dg on dg.id = td.drug_id " +
            "    left join disposal d on td.prescription_id = d.prescription_id " +
            "    left join registration r on d.registration_id = r.id " +
            "    left join appointment a on a.registration_id = r.id " +
            "    left join patient p on a.patient_id = p.id " +
            "    left join jhi_user ju on a.doctor_user_id = ju.id " +
            "where r.arrival_time is not null " +
            "  and td.prescription_id is not null " +
            "  and dg.id = :id " +
            "  and ju.id in (:doctorIds) " +
            "  and r.arrival_time between :begin and :end " +
            "  order by ju.id, r.arrival_time "
    )
    Page<PlainDisposalInfoDTO> findPlainDisposalDrug(
        @Param(value="begin") Instant beginTime,
        @Param(value="end") Instant endTime,
        @Param(value="doctorIds") List<Long> doctorIds,
        @Param(value="id") Long nhiProcedureId,
        Pageable page);
}
