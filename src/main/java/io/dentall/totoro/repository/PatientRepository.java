package io.dentall.totoro.repository;

import io.dentall.totoro.business.vm.PatientSearchVM;
import io.dentall.totoro.domain.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Patient entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PatientRepository extends JpaRepository<Patient, Long>, JpaSpecificationExecutor<Patient> {

    @Query(value = "SELECT new io.dentall.totoro.business.vm.PatientSearchVM(patient.id, patient.name, patient.medicalId) " +
        "FROM Patient patient WHERE to_char(patient.birth, 'yyyyMMdd') like '%'||:search||'%' " +
        "ORDER BY " +
        "CASE " +
        "      WHEN (to_char(patient.birth, 'yyyyMMdd') = :search) THEN 1 " +
        "      WHEN (to_char(patient.birth, 'yyyyMMdd') like :search||'%') THEN 2 " +
        "      WHEN (to_char(patient.birth, 'yyyyMMdd') like '%'||:search) THEN 4 " +
        "      WHEN (to_char(patient.birth, 'yyyyMMdd') like '%'||:search||'%') THEN 3 " +
        "      ELSE 5 " +
        "END",
        countQuery = "SELECT count(*) FROM Patient patient WHERE to_char(patient.birth, 'yyyyMMdd') like '%'||:search||'%'")
    Page<PatientSearchVM> findByBirthCE(@Param("search") String search, Pageable pageable);

    @Query(value = "SELECT new io.dentall.totoro.business.vm.PatientSearchVM(patient.id, patient.name, patient.medicalId) " +
        "FROM Patient patient WHERE FUNCTION('SUCK_ROC_BIRTH_TO_CHAR', patient.birth) like '%'||:search||'%' " +
        "ORDER BY " +
        "CASE " +
        "      WHEN (FUNCTION('SUCK_ROC_BIRTH_TO_CHAR', patient.birth) = :search) THEN 1 " +
        "      WHEN (FUNCTION('SUCK_ROC_BIRTH_TO_CHAR', patient.birth) like :search||'%') THEN 2 " +
        "      WHEN (FUNCTION('SUCK_ROC_BIRTH_TO_CHAR', patient.birth) like '%'||:search) THEN 4 " +
        "      WHEN (FUNCTION('SUCK_ROC_BIRTH_TO_CHAR', patient.birth) like '%'||:search||'%') THEN 3 " +
        "      ELSE 5 " +
        "END",
        countQuery = "SELECT count(*) FROM Patient patient WHERE function('SUCK_ROC_BIRTH_TO_CHAR', patient.birth) like '%'||:search||'%'")
    Page<PatientSearchVM> findByBirthROC(@Param("search") String search, Pageable pageable);

    @Query(value = "SELECT new io.dentall.totoro.business.vm.PatientSearchVM(patient.id, patient.name, patient.medicalId) " +
        "FROM Patient patient WHERE UPPER(patient.name) like '%'||:search||'%' " +
        "ORDER BY " +
        "CASE " +
        "      WHEN (UPPER(patient.name) = :search) THEN 1 " +
        "      WHEN (UPPER(patient.name) like :search||'%') THEN 2 " +
        "      WHEN (UPPER(patient.name) like '%'||:search) THEN 4 " +
        "      WHEN (UPPER(patient.name) like '%'||:search||'%') THEN 3 " +
        "      ELSE 5 " +
        "END",
        countQuery = "SELECT count(*) FROM Patient patient WHERE UPPER(patient.name) like '%'||:search||'%'")
    Page<PatientSearchVM> findByName(@Param("search") String search, Pageable pageable);

    @Query(value = "SELECT new io.dentall.totoro.business.vm.PatientSearchVM(patient.id, patient.name, patient.medicalId) " +
        "FROM Patient patient WHERE patient.phone like '%'||:search||'%' " +
        "ORDER BY " +
        "CASE " +
        "      WHEN (patient.phone = :search) THEN 1 " +
        "      WHEN (patient.phone like :search||'%') THEN 2 " +
        "      WHEN (patient.phone like '%'||:search) THEN 4 " +
        "      WHEN (patient.phone like '%'||:search||'%') THEN 3 " +
        "      ELSE 5 " +
        "END",
        countQuery = "SELECT count(*) FROM Patient patient WHERE patient.phone like '%'||:search||'%'")
    Page<PatientSearchVM> findByPhone(@Param("search") String search, Pageable pageable);

    @Query(value = "SELECT new io.dentall.totoro.business.vm.PatientSearchVM(patient.id, patient.name, patient.medicalId) " +
        "FROM Patient patient WHERE UPPER(patient.nationalId) like '%'||:search||'%' " +
        "ORDER BY " +
        "CASE " +
        "      WHEN (UPPER(patient.nationalId) = :search) THEN 1 " +
        "      WHEN (UPPER(patient.nationalId) like :search||'%') THEN 2 " +
        "      WHEN (UPPER(patient.nationalId) like '%'||:search) THEN 4 " +
        "      WHEN (UPPER(patient.nationalId) like '%'||:search||'%') THEN 3 " +
        "      ELSE 5 " +
        "END",
        countQuery = "SELECT count(*) FROM Patient patient WHERE UPPER(patient.nationalId) like '%'||:search||'%'")
    Page<PatientSearchVM> findByNationalId(@Param("search") String search, Pageable pageable);

    @Query(value = "SELECT new io.dentall.totoro.business.vm.PatientSearchVM(patient.id, patient.name, patient.medicalId) " +
        "FROM Patient patient WHERE FUNCTION('regexp_replace', patient.medicalId, '^0*|-', '', 'g') like '%'||:search||'%' " +
        "ORDER BY " +
        "CASE " +
        "      WHEN (FUNCTION('regexp_replace', patient.medicalId, '^0*|-', '', 'g') = :search) THEN 1 " +
        "      WHEN (FUNCTION('regexp_replace', patient.medicalId, '^0*|-', '', 'g') like :search||'%') THEN 2 " +
        "      WHEN (FUNCTION('regexp_replace', patient.medicalId, '^0*|-', '', 'g') like '%'||:search) THEN 4 " +
        "      WHEN (FUNCTION('regexp_replace', patient.medicalId, '^0*|-', '', 'g') like '%'||:search||'%') THEN 3 " +
        "      ELSE 5 " +
        "END",
        countQuery = "SELECT count(*) FROM Patient patient WHERE FUNCTION('regexp_replace', patient.medicalId, '^0*|-', '', 'g') like '%'||:search||'%'")
    Page<PatientSearchVM> findByMedicalId(@Param("search") String search, Pageable pageable);
}
