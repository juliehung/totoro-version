package io.dentall.totoro.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.dentall.totoro.domain.Appointment;
import io.dentall.totoro.service.AppointmentService;
import io.dentall.totoro.web.rest.errors.BadRequestAlertException;
import io.dentall.totoro.web.rest.util.HeaderUtil;
import io.dentall.totoro.web.rest.util.PaginationUtil;
import io.dentall.totoro.service.dto.AppointmentCriteria;
import io.dentall.totoro.service.AppointmentQueryService;
import io.dentall.totoro.web.rest.vm.PatientCardVM;
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

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing Appointment.
 */
@RestController
@RequestMapping("/api")
public class AppointmentResource {

    private final Logger log = LoggerFactory.getLogger(AppointmentResource.class);

    private static final String ENTITY_NAME = "appointment";

    private final AppointmentService appointmentService;

    private final AppointmentQueryService appointmentQueryService;

    public AppointmentResource(AppointmentService appointmentService, AppointmentQueryService appointmentQueryService) {
        this.appointmentService = appointmentService;
        this.appointmentQueryService = appointmentQueryService;
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
        Appointment result = appointmentService.save(appointment);
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

        return ResponseUtil.wrapOrNotFound(
            appointmentService.updateAppointment(appointment),
            HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, appointment.getId().toString())
        );
    }

    /**
     * GET  /appointments : get all the appointments.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of appointments in body
     */
    @GetMapping("/appointments")
    @Timed
    public ResponseEntity<List<Appointment>> getAllAppointments(AppointmentCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Appointments by criteria: {}", criteria);
        Page<Appointment> page = appointmentQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/appointments");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /appointments/count : count all the appointments.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/appointments/count")
    @Timed
    public ResponseEntity<Long> countAppointments(AppointmentCriteria criteria) {
        log.debug("REST request to count Appointments by criteria: {}", criteria);
        return ResponseEntity.ok().body(appointmentQueryService.countByCriteria(criteria));
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
        Optional<Appointment> appointment = appointmentService.findOne(id);
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
        appointmentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * GET  /appointments/patient-cards : get all patient cards between start and end time interval
     *
     * @param start the start time
     * @param end the end time
     * @param doctorId the doctor id of the appointment
     * @return the ResponseEntity with status 200 (OK) and the list of PatientCardVM in body
     */
    @GetMapping("/appointments/patient-cards")
    @Timed
    public ResponseEntity<List<PatientCardVM>> getAllPatientCards(@RequestParam(required = false) Instant start, @RequestParam(required = false) Instant end, @RequestParam(required = false) Long doctorId) {
        List<Appointment> appointments = appointmentService.getAppointmentsForPatientCard(start, end);
        if (appointments == null) {
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK);
        } else {
            List<PatientCardVM> patientCardVMS = appointments
                .stream()
                .filter(appointment -> {
                    if (doctorId == null) {
                        return true;
                    } else {
                        return appointment.getDoctor().getId().equals(doctorId);
                    }
                })
                .map(appointment -> new PatientCardVM(appointment.getPatient(), appointment, appointment.getRegistration()))
                .collect(Collectors.toList());

            return new ResponseEntity<>(patientCardVMS, HttpStatus.OK);
        }
    }
}
