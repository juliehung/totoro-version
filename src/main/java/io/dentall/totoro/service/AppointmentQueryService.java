package io.dentall.totoro.service;

import java.time.Instant;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.function.Predicate;

import javax.persistence.criteria.JoinType;

import io.dentall.totoro.domain.enumeration.RegistrationType;
import io.dentall.totoro.service.filter.RegistrationTypeFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.InstantFilter;
import io.github.jhipster.service.filter.LongFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import io.dentall.totoro.domain.Appointment;
import io.dentall.totoro.domain.*; // for static metamodels
import io.dentall.totoro.repository.AppointmentRepository;
import io.dentall.totoro.service.dto.AppointmentCriteria;

/**
 * Service for executing complex queries for Appointment entities in the database.
 * The main input is a {@link AppointmentCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Appointment} or a {@link Page} of {@link Appointment} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AppointmentQueryService extends QueryService<Appointment> {

    private final Logger log = LoggerFactory.getLogger(AppointmentQueryService.class);

    private final AppointmentRepository appointmentRepository;

    public AppointmentQueryService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    /**
     * Return a {@link List} of {@link Appointment} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Appointment> findByCriteria(AppointmentCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Appointment> specification = createSpecification(criteria);
        return appointmentRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Appointment} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Appointment> findByCriteria(AppointmentCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Appointment> specification = createSpecification(criteria);
        return appointmentRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AppointmentCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Appointment> specification = createSpecification(criteria);
        return appointmentRepository.count(specification);
    }

    /**
     * Function to convert AppointmentCriteria to a {@link Specification}
     */
    private Specification<Appointment> createSpecification(AppointmentCriteria criteria) {
        Specification<Appointment> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Appointment_.id));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), Appointment_.status));
            }
            if (criteria.getSubject() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSubject(), Appointment_.subject));
            }
            if (criteria.getNote() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNote(), Appointment_.note));
            }
            if (criteria.getExpectedArrivalTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getExpectedArrivalTime(), Appointment_.expectedArrivalTime));
            } else {
                if ((predicateRegistrationId.test(criteria.getRegistrationId()) && predicateFilterEquals.test(criteria.getPatientId())) ||
                    predicateFilterEquals.test(criteria.getRegistrationType()) ||
                    predicateFilterEquals.test(criteria.getRegistrationTypeValue())) {
                    // default expectedArrivalTime is today
                    Instant start = OffsetDateTime.now().toZonedDateTime().with(LocalTime.MIN).toInstant();
                    Instant end = OffsetDateTime.now().toZonedDateTime().with(LocalTime.MAX).toInstant();
                    specification = specification.and(buildRangeSpecification(new InstantFilter().setGreaterOrEqualThan(start).setLessOrEqualThan(end), Appointment_.expectedArrivalTime));
                }
            }
            if (criteria.getRequiredTreatmentTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRequiredTreatmentTime(), Appointment_.requiredTreatmentTime));
            }
            if (criteria.getMicroscope() != null) {
                specification = specification.and(buildSpecification(criteria.getMicroscope(), Appointment_.microscope));
            }
            if (criteria.getNewPatient() != null) {
                specification = specification.and(buildSpecification(criteria.getNewPatient(), Appointment_.newPatient));
            }
            if (criteria.getBaseFloor() != null) {
                specification = specification.and(buildSpecification(criteria.getBaseFloor(), Appointment_.baseFloor));
            }
            if (criteria.getColorId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getColorId(), Appointment_.colorId));
            }
            if (criteria.getArchived() != null) {
                specification = specification.and(buildSpecification(criteria.getArchived(), Appointment_.archived));
            }
            if (criteria.getPatientId() != null) {
                specification = specification.and(buildSpecification(criteria.getPatientId(),
                    root -> root.join(Appointment_.patient, JoinType.LEFT).get(Patient_.id)));
            }
            if (criteria.getRegistrationId() != null) {
                specification = specification.and(buildSpecification(criteria.getRegistrationId(),
                    root -> root.join(Appointment_.registration, JoinType.LEFT).get(Registration_.id)));
            }
            if (criteria.getRegistrationType() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getRegistrationType(), Appointment_.registration, Registration_.type));
            }
            if (criteria.getRegistrationTypeValue() != null) {
                RegistrationType type = RegistrationType.intToTypeMap.get(criteria.getRegistrationTypeValue().getEquals());
                if (type == null) {
                    return alwaysFalse;
                } else {
                    RegistrationTypeFilter filter = new RegistrationTypeFilter();
                    filter.setEquals(type);
                    specification = specification.and(buildReferringEntitySpecification(filter, Appointment_.registration, Registration_.type));
                }
            }
            if (criteria.getDoctorId() != null) {
                specification = specification.and(buildSpecification(criteria.getDoctorId(),
                    root -> root.join(Appointment_.doctor, JoinType.LEFT).get(ExtendUser_.id)));
            }
        }
        return specification;
    }

    // registrationId criteria is not null and registrationId specified is not null and registrationId specified is false (registrationId is null)
    private Predicate<LongFilter> predicateRegistrationId = filter -> filter != null && filter.getSpecified() != null && !filter.getSpecified();

    // criteria is not null and criteria equals is not null
    private Predicate<Filter> predicateFilterEquals = filter -> filter != null && filter.getEquals() != null;

    // always not found criteria
    private Specification<Appointment> alwaysFalse = (Specification<Appointment>) (root, query, criteriaBuilder) -> criteriaBuilder.or();
}
