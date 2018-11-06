package io.dentall.totoro.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.dentall.totoro.domain.Appointment;
import io.dentall.totoro.domain.Patient;
import io.dentall.totoro.domain.Registration;
import io.dentall.totoro.repository.AppointmentRepository;
import io.dentall.totoro.web.rest.errors.BadRequestAlertException;
import io.dentall.totoro.web.rest.util.HeaderUtil;
import io.dentall.totoro.web.rest.util.PaginationUtil;
import io.dentall.totoro.web.rest.vm.PatientCard;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

/**
 * REST controller for managing Appointment.
 */
@RestController
@RequestMapping("/api")
public class AppointmentResource {

    private final Logger log = LoggerFactory.getLogger(AppointmentResource.class);

    private static final String ENTITY_NAME = "appointment";

    private final AppointmentRepository appointmentRepository;

    public AppointmentResource(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    /**
     * POST  /appointments : Create a new appointment.
     *
     * @param appointment the appointment to create
     * @return the ResponseEntity with status 201 (Created) and with body the new appointment, or with status 400 (Bad Request) if the appointment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/appointments")
    @Timed
    public ResponseEntity<Appointment> createAppointment(@Valid @RequestBody Appointment appointment) throws URISyntaxException {
        log.debug("REST request to save Appointment : {}", appointment);
        if (appointment.getId() != null) {
            throw new BadRequestAlertException("A new appointment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Appointment result = appointmentRepository.save(appointment);
        return ResponseEntity.created(new URI("/api/appointments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /appointments : Updates an existing appointment.
     *
     * @param appointment the appointment to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated appointment,
     * or with status 400 (Bad Request) if the appointment is not valid,
     * or with status 500 (Internal Server Error) if the appointment couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/appointments")
    @Timed
    public ResponseEntity<Appointment> updateAppointment(@Valid @RequestBody Appointment appointment) throws URISyntaxException {
        log.debug("REST request to update Appointment : {}", appointment);
        if (appointment.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Appointment result = appointmentRepository.save(appointment);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, appointment.getId().toString()))
            .body(result);
    }

    /**
     * GET  /appointments : get all the appointments.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of appointments in body
     */
    @GetMapping("/appointments")
    @Timed
    public ResponseEntity<List<Appointment>> getAllAppointments(Pageable pageable) {
        log.debug("REST request to get a page of Appointments");
        Page<Appointment> page = appointmentRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/appointments");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /appointments/:id : get the "id" appointment.
     *
     * @param id the id of the appointment to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the appointment, or with status 404 (Not Found)
     */
    @GetMapping("/appointments/{id}")
    @Timed
    public ResponseEntity<Appointment> getAppointment(@PathVariable Long id) {
        log.debug("REST request to get Appointment : {}", id);
        Optional<Appointment> appointment = appointmentRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(appointment);
    }

    /**
     * DELETE  /appointments/:id : delete the "id" appointment.
     *
     * @param id the id of the appointment to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/appointments/{id}")
    @Timed
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long id) {
        log.debug("REST request to delete Appointment : {}", id);

        appointmentRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * GET  /patient-cards : get all today expected arrival patients.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of patients in body
     */
    @GetMapping("/appointments/patient-cards")
    @Timed
    public ResponseEntity<List<PatientCard>> getAllPatientCards(Pageable pageable) {
        log.debug("REST request to get appointment patient cards");
        ZonedDateTime start = LocalDate.now().atTime(LocalTime.MIN).atZone(ZoneId.systemDefault());
        ZonedDateTime end = LocalDate.now().atTime(LocalTime.MAX).atZone(ZoneId.systemDefault());
        Page<Appointment> appointments = appointmentRepository.findByRegistrationIsNullAndExpectedArrivalTimeBetween(start, end, pageable);

        Page<PatientCard> page = appointments
            .map(appointment -> {
                PatientCard card = new PatientCard();

                Patient patient = appointment.getPatient();
                card.setName(patient.getName());
                card.setGender(patient.getGender().getValue());
                card.setMedicalId(patient.getMedicalId());
                card.setBirthday(patient.getBirth());
                card.setDominantDoctor(patient.getDominantDoctor());
                card.setFirstDoctor(patient.getFirstDoctor());
                card.setReminder(patient.getReminder());
                card.setLastModifiedDate(patient.getLastModifiedDate());
                card.setWriteIcTime(patient.getWriteIcTime());

                card.setExpectedArrivalTime(appointment.getExpectedArrivalTime());
                card.setSubject(appointment.getSubject());
                card.setRequiredTreatmentTime(appointment.getRequiredTreatmentTime());
                card.setNewPatient(appointment.isNewPatient());

                card.setRegistration(null);

                return card;
            });

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/appointments/patient-cards");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
}
