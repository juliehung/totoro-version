package io.dentall.totoro.repository;

import io.dentall.totoro.domain.Appointment;
import io.dentall.totoro.repository.dao.AppointmentDAO;
import io.dentall.totoro.service.dto.AppointmentDTO;
import io.dentall.totoro.service.dto.table.AppointmentTable;
import io.dentall.totoro.web.rest.vm.UWPRegistrationPageVM;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Optional;


/**
 * Spring Data  repository for the Appointment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long>, JpaSpecificationExecutor<Appointment> {

    @Query(
        nativeQuery = true,
        value = "select" +
            "       p.id as patientId," +
            "       p.name as patientName," +
            "       p.birth as patientBirth," +
            "       p.medical_id as patientMedicalId," +
            "       p.gender as patientGender," +
            "       d.id as disposalId," +
            "       a.id as appointmentId," +
            "       a.note as appointmentNote," +
            "       a.expected_arrival_time as appointmentExpectedArrivalTime," +
            "       a.microscope as appointmentMicroscope," +
            "       a.base_floor as appointmentBaseFloor," +
            "       r.id as registrationId," +
            "       r.arrival_time as registrationArrivalTime," +
            "       r.status as registrationStatus," +
            "       r.abnormal_code as registrationAbnormalCode," +
            "       r.jhi_type as registrationType," +
            "       r.no_card as registrationNoCard," +
            "       u.first_name as userFirstName," +
            "       u.last_name as userLastName," +
            "       ned.A17 as nhiExtendDisposalA17," +
            "       ned.A18 as nhiExtendDisposalA18," +
            "       ned.A23 as nhiExtendDisposalA23," +
            "       ned.A54 as nhiExtendDisposalA54," +
            "       acc.transaction_time as accountingTransactionTime," +
            "       replace(replace(cast(array(select tags_id from patient_tag pt where pt.patients_id = p.id) as varchar), '{', ''), '}', '') as patientTags," +
            "       count(procedure_id) filter ( where procedure_id is not null )         as procedureCounter," +
            "       count(nhi_procedure_id) filter ( where nhi_procedure_id is not null ) as nhiProcedureCounter" +
            " from appointment a" +
            "         left join registration r on a.registration_id = r.id" +
            "         left join disposal d on r.id = d.registration_id" +
            "         left join treatment_procedure tp on d.id = tp.disposal_id" +
            "         left join patient p on a.patient_id = p.id" +
            "         left join jhi_user u on a.doctor_user_id = u.id" +
            "         left join (select max(id) as mxId, disposal_id from nhi_extend_disposal group by disposal_id) mned on d.id = mned.disposal_id" +
            "         left join nhi_extend_disposal ned on mned.mxId = ned.id" +
            "         left join accounting acc on r.accounting_id = acc.id " +
            " where a.expected_arrival_time between ?1 and ?2 " +
            "  and a.status <> 'CANCEL'" +
            "group by tp.disposal_id," +
            "         p.id," +
            "         p.name," +
            "         p.birth," +
            "         p.medical_id," +
            "         p.gender," +
            "         d.id," +
            "         a.id," +
            "         a.note," +
            "         a.expected_arrival_time," +
            "         a.microscope," +
            "         a.base_floor," +
            "         r.id," +
            "         r.arrival_time," +
            "         r.status," +
            "         r.abnormal_code," +
            "         r.jhi_type," +
            "         r.no_card," +
            "         u.first_name," +
            "         u.last_name," +
            "         ned.A17," +
            "         ned.A18," +
            "         ned.A23," +
            "         ned.A54," +
            "         acc.transaction_time" +
            " order by a.expected_arrival_time;"
    )
    List<UWPRegistrationPageVM> findAppointmentWithTonsOfDataForUWPRegistrationPage(Instant start, Instant end);

    Optional<AppointmentTable> findAppointmentByRegistration_Id(Long id);

    Page<AppointmentTable> findByPatient_Id(Long id, Pageable page);

    List<Appointment> findByRegistrationIsNullAndExpectedArrivalTimeBetweenOrderByExpectedArrivalTimeAsc(Instant start, Instant end);

    Collection<AppointmentTable> findByExpectedArrivalTimeBetweenOrderByExpectedArrivalTimeAsc(Instant start, Instant end);

    <T> Collection<T> findByExpectedArrivalTimeBetweenOrderByExpectedArrivalTimeAsc(Instant start, Instant end, Class<T> type);

    <T> Collection<T> findByRegistrationIsNullAndExpectedArrivalTimeBetweenOrderByExpectedArrivalTimeAsc(Instant start, Instant end, Class<T> type);

    <T> Collection<T> findByRegistrationIsNotNullAndExpectedArrivalTimeBetweenOrderByExpectedArrivalTimeAsc(Instant start, Instant end, Class<T> type);

    @Query(value =
        "select new io.dentall.totoro.service.dto.AppointmentDTO( " +
            "appointment.patient.id, " +
            "appointment.patient.name, " +
            "appointment.patient.birth, " +
            "appointment.patient.nationalId, " +
            "appointment.patient.gender, " +
            "appointment.patient.phone, " +
            "appointment.expectedArrivalTime, " +
            "appointment.doctor, " +
            "appointment.requiredTreatmentTime, " +
            "appointment.note, " +
            "appointment.microscope, " +
            "appointment.baseFloor, " +
            "appointment.status, " +
            "registration.arrivalTime, " +
            "appointment.id, " +
            "appointment.patient.newPatient, " +
            "registration.status, " +
            "appointment.patient.lastModifiedDate, " +
            "appointment.patient.lastModifiedBy, " +
            "appointment.patient.medicalId, " +
            "appointment.colorId" +
            ") " +
            "from Appointment as appointment left outer join appointment.registration as registration " +
            "where appointment.expectedArrivalTime between :beginDate and :endDate ")
    List<AppointmentDTO> findMonthAppointment(@Param("beginDate") Instant beginDate, @Param("endDate") Instant endDate);

    @Query(value =
        "select new io.dentall.totoro.repository.dao.AppointmentDAO( " +
            "appointment.id, " +
            "appointment.createdBy, " +
            "appointment.createdDate, " +
            "appointment.status, " +
            "appointment.subject, " +
            "appointment.note, " +
            "appointment.expectedArrivalTime, " +
            "appointment.requiredTreatmentTime, " +
            "appointment.microscope, " +
            "appointment.baseFloor, " +
            "appointment.colorId, " +
            "appointment.archived, " +
            "appointment.contacted, " +
            "patient.id, " +
            "patient.createdBy, " +
            "patient.createdDate, " +
            "patient.lastModifiedBy, " +
            "patient.lastModifiedDate, " +
            "patient.name, " +
            "patient.phone, " +
            "patient.gender, " +
            "patient.birth, " +
            "patient.nationalId, " +
            "patient.medicalId, " +
            "patient.address, " +
            "patient.email, " +
            "patient.blood, " +
            "patient.cardId, " +
            "patient.vip, " +
            "patient.emergencyName, " +
            "patient.emergencyPhone, " +
            "patient.emergencyAddress, " +
            "patient.emergencyRelationship, " +
            "patient.deleteDate, " +
            "patient.scaling, " +
            "patient.lineId, " +
            "patient.fbId, " +
            "patient.note, " +
            "patient.clinicNote, " +
            "patient.writeIcTime, " +
            "patient.mainNoticeChannel, " +
            "patient.career, " +
            "patient.marriage, " +
            "patient.newPatient, " +
            "patient.patientIdentity, " +
            "registration.id, " +
            "registration.createdBy, " +
            "registration.createdDate, " +
            "registration.lastModifiedBy, " +
            "registration.lastModifiedDate, " +
            "registration.status, " +
            "registration.arrivalTime, " +
            "registration.type, " +
            "registration.onSite, " +
            "registration.noCard, " +
            "registration.abnormalCode, " +
            "doctor," +
            "disposal.id," +
            "disposal.createdBy, " +
            "disposal.createdDate, " +
            "disposal.lastModifiedBy, " +
            "disposal.lastModifiedDate, " +
            "nhiPatient.cardNumber, " +
            "nhiPatient.cardAnnotation, " +
            "nhiPatient.cardValidDate, " +
            "nhiPatient.cardIssueDate, " +
            "nhiPatient.nhiIdentity, " +
            "nhiPatient.availableTimes, " +
            "nhiPatient.scaling, " +
            "nhiPatient.fluoride, " +
            "nhiPatient.perio, " +
            "nhiPatient.createdBy, " +
            "nhiPatient.createdDate, " +
            "nhiPatient.lastModifiedBy, " +
            "nhiPatient.lastModifiedDate," +
            "accounting " +
            ") " +
            "from Appointment as appointment " +
                "left join appointment.patient as patient " +
                "left join appointment.patient.nhiExtendPatient as nhiPatient " +
                "left join appointment.registration as registration " +
                "left join appointment.doctor as doctor " +
                "left join appointment.registration.disposal as disposal " +
                "left join appointment.registration.accounting as accounting " +
            "where appointment.expectedArrivalTime between :beginDate and :endDate")
    List<AppointmentDAO> findAppointmentWithRelationshipBetween(@Param("beginDate") Instant beginDate, @Param("endDate") Instant endDate);
}
