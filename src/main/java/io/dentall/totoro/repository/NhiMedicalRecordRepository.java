package io.dentall.totoro.repository;

import io.dentall.totoro.domain.NhiMedicalRecord;
import io.dentall.totoro.service.dto.NhiMedicalRecordDTO;
import io.dentall.totoro.web.rest.vm.NhiMedicalRecordVM;
import io.dentall.totoro.web.rest.vm.NhiMedicalRecordVM2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the NhiMedicalRecord entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NhiMedicalRecordRepository extends JpaRepository<NhiMedicalRecord, Long>, JpaSpecificationExecutor<NhiMedicalRecord> {

    List<NhiMedicalRecord> findByNhiExtendPatient_Patient_IdOrderByDateDesc(Long id);

    List<NhiMedicalRecord> findByNhiExtendPatient_Patient_IdAndNhiCodeOrderByDateDesc(Long id, String code);

    List<NhiMedicalRecord> findByNhiExtendPatient_Patient_IdAndNhiCodeInOrderByDateDesc(Long id, List<String> codes);

    List<NhiMedicalRecordDTO> findByNhiExtendPatient_Patient_IdAndDateLikeOrNhiExtendPatient_Patient_IdAndDateLikeOrNhiExtendPatient_Patient_IdAndDateLikeOrderByDateDesc(
        Long _1stPatientId,
        String _1stDateString,
        Long _2ndPatientId,
        String _2ndDateString,
        Long _3rdPatientId,
        String _3rdDateString
    );

    @Query(
        nativeQuery = true,
        countQuery = "with " +
            "for_pagination_normal as ( " +
            "select 1 from nhi_medical_record nmr " +
            "), " +
            "system_record as ( " +
            "select distinct cast(ned.a17 as text) as recordDateTime " +
            "from disposal d " +
            "  left join appointment a on d.registration_id = a.registration_id " +
            "  left join treatment_procedure tp on d.id = tp.disposal_id " +
            "  left join nhi_extend_disposal ned on d.id = ned.disposal_id " +
            "  left join nhi_extend_treatment_procedure netp on tp.id = netp.treatment_procedure_id " +
            "where a.patient_id = :patientId " +
            "  and trim(ned.a18) <> '' " +
            "  and tp.nhi_procedure_id is not null " +
            ") " +
            "select count(*)  " +
            "from nhi_medical_record nmr " +
            "left join system_record sr on nmr.jhi_date = sr.recordDateTime " +
            "left join nhi_tx nt on nmr.nhi_code = nt.nhi_code " +
            "left join ( " +
            "  select distinct medicine_code, medicine_mandarin " +
            "  from nhi_medicine " +
            ") as nm on nm.medicine_code = nmr.nhi_code " +
            "where nhi_extend_patient_patient_id = :patientId " +
            "and sr.recordDateTime is null " +
            "and nmr.nhi_category not in ('A', 'B', 'C', 'D', 'E', 'a', 'b', 'c', 'd', 'e') ",
        value = "with " +
            "for_pagination_normal as ( " +
            "select 1 from nhi_medical_record nmr " +
            "), " +
            "system_record as ( " +
            "select distinct cast(ned.a17 as text) as recordDateTime " +
            "from disposal d " +
            "  left join appointment a on d.registration_id = a.registration_id " +
            "  left join treatment_procedure tp on d.id = tp.disposal_id " +
            "  left join nhi_extend_disposal ned on d.id = ned.disposal_id " +
            "  left join nhi_extend_treatment_procedure netp on tp.id = netp.treatment_procedure_id " +
            "where a.patient_id = :patientId " +
            "  and trim(ned.a18) <> '' " +
            "  and tp.nhi_procedure_id is not null " +
            ") " +
            "select nmr.jhi_date as date, " +
            "    case when nt.nhi_mandarin is not null then nt.nhi_mandarin " +
            "         when nm.medicine_mandarin is not null then nm.medicine_mandarin " +
            "         else '' " +
            "    end as mandarin, " +
            "    nmr.nhi_code as nhiCode, " +
            "    nmr.note, " +
            "    nmr.jhi_usage as usage, " +
            "    nmr.days, " +
            "    nmr.part, " +
            "    nmr.total, " +
            "    nmr.nhi_category as nhiCategory " +
            "from nhi_medical_record nmr " +
            "left join system_record sr on nmr.jhi_date = sr.recordDateTime " +
            "left join nhi_tx nt on nmr.nhi_code = nt.nhi_code " +
            "left join ( " +
            "  select distinct medicine_code, medicine_mandarin " +
            "  from nhi_medicine " +
            ") as nm on nm.medicine_code = nmr.nhi_code " +
            "where nhi_extend_patient_patient_id = :patientId " +
            "and sr.recordDateTime is null " +
            "and nmr.nhi_category not in ('A', 'B', 'C', 'D', 'E', 'a', 'b', 'c', 'd', 'e') "
    )
    Page<NhiMedicalRecordVM2> findNoneCancelledAndNotInSystem(
        @Param(value = "patientId") Long patientId,
        Pageable pageable
    );

    @Query(
        nativeQuery = true,
        countQuery =
            "select count(*) " +
            "from nhi_medical_record nmr " +
            "left join nhi_tx nt on nmr.nhi_code = nt.nhi_code " +
            "left join ( " +
            "  select distinct medicine_code, medicine_mandarin " +
            "  from nhi_medicine " +
            ") as nm on nm.medicine_code = nmr.nhi_code " +
            "where nhi_extend_patient_patient_id = :patientId " +
            "and nmr.nhi_category not in ('A', 'B', 'C', 'D', 'E', 'a', 'b', 'c', 'd', 'e') ",
        value =
            "select nmr.jhi_date as date, " +
            "    case when nt.nhi_mandarin is not null then nt.nhi_mandarin " +
            "         when nm.medicine_mandarin is not null then nm.medicine_mandarin " +
            "         else '' " +
            "    end as mandarin, " +
            "    nmr.nhi_code as nhiCode, " +
            "    nmr.note, " +
            "    nmr.jhi_usage as usage, " +
            "    nmr.days, " +
            "    nmr.part, " +
            "    nmr.total, " +
            "    nmr.nhi_category as nhiCategory " +
            "from nhi_medical_record nmr " +
            "left join nhi_tx nt on nmr.nhi_code = nt.nhi_code " +
            "left join ( " +
            "  select distinct medicine_code, medicine_mandarin " +
            "  from nhi_medicine " +
            ") as nm on nm.medicine_code = nmr.nhi_code " +
            "where nhi_extend_patient_patient_id = :patientId " +
            "and nmr.nhi_category not in ('A', 'B', 'C', 'D', 'E', 'a', 'b', 'c', 'd', 'e') "
    )
    Page<NhiMedicalRecordVM2> findNoneCancelled(
        @Param(value = "patientId") Long patientId,
        Pageable pageable
    );
}
