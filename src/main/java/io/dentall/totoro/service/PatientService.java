package io.dentall.totoro.service;

import io.dentall.totoro.domain.*;
import io.dentall.totoro.domain.enumeration.TreatmentType;
import io.dentall.totoro.repository.*;
import io.dentall.totoro.service.dto.PatientCriteria;
import io.dentall.totoro.service.util.FilterUtil;
import io.github.jhipster.service.QueryService;
import io.github.jhipster.service.filter.InstantFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.JoinType;
import java.time.Instant;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service class for managing patients.
 */
@Service
@Transactional
public class PatientService extends QueryService<Patient> {

    private final Logger log = LoggerFactory.getLogger(PatientService.class);

    private final PatientRepository patientRepository;

    private final TagRepository tagRepository;

    private final QuestionnaireRepository questionnaireRepository;

    private final ExtendUserRepository extendUserRepository;

    private final PatientIdentityRepository patientIdentityRepository;

    private final TreatmentRepository treatmentRepository;

    private final TreatmentPlanRepository treatmentPlanRepository;

    private final TreatmentTaskRepository treatmentTaskRepository;

    private final RelationshipService relationshipService;

    private final ToothRepository toothRepository;

    public PatientService(
        PatientRepository patientRepository,
        TagRepository tagRepository,
        QuestionnaireRepository questionnaireRepository,
        ExtendUserRepository extendUserRepository,
        PatientIdentityRepository patientIdentityRepository,
        TreatmentRepository treatmentRepository,
        TreatmentPlanRepository treatmentPlanRepository,
        TreatmentTaskRepository treatmentTaskRepository,
        RelationshipService relationshipService,
        ToothRepository toothRepository
    ) {
        this.patientRepository = patientRepository;
        this.tagRepository = tagRepository;
        this.questionnaireRepository = questionnaireRepository;
        this.extendUserRepository = extendUserRepository;
        this.patientIdentityRepository = patientIdentityRepository;
        this.treatmentRepository = treatmentRepository;
        this.treatmentPlanRepository = treatmentPlanRepository;
        this.treatmentTaskRepository = treatmentTaskRepository;
        this.relationshipService = relationshipService;
        this.toothRepository = toothRepository;
    }

    /**
     * Return a {@link Page} of {@link Patient} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Patient> findByCriteria(PatientCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Patient> specification = createSpecification(criteria);
        return patientRepository.findAll(specification, page);
    }

    /**
     * Save a patient.
     *
     * @param patient the entity to save
     * @return the persisted entity
     */
    public Patient save(Patient patient) {
        log.debug("Request to save Patient : {}", patient);

        patient = patientRepository.save(patient.newPatient(true));
        patient.setMedicalId(String.format("%05d", patient.getId()));
        patient.getTreatments().add(createGeneralTreatmentAndPlanAndTask(patient));

        return patient;
    }

    public Patient update(Patient updatePatient) {
        return patientRepository
            .findById(updatePatient.getId())
            .map(patient -> {
                // questionnaire
                if (updatePatient.getQuestionnaire() != null) {
                    log.debug("Update questionnaire({}) of Patient(id: {})", updatePatient.getQuestionnaire(), updatePatient.getId());
                    Questionnaire questionnaire = patient.getQuestionnaire();
                    Questionnaire updateQuestionnaire = updatePatient.getQuestionnaire();
                    patient.setQuestionnaire(questionnaire == null ? questionnaireRepository.save(updateQuestionnaire) : updateQuestionnaire(questionnaire, updateQuestionnaire));
                }

                // tags
                if (updatePatient.getTags() != null) {
                    log.debug("Update tags({}) of Patient(id: {})", updatePatient.getTags(), updatePatient.getId());
                    patient.getTags().clear();
                    updatePatient
                        .getTags()
                        .stream()
                        .map(tag -> tagRepository.findById(tag.getId()))
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .forEach(patient.getTags()::add);
                }

                // basic info.
                if (updatePatient.getName() != null) {
                    patient.setName(updatePatient.getName());
                }

                if (updatePatient.getPhone() != null) {
                    patient.setPhone(updatePatient.getPhone());
                }

                if (updatePatient.getGender() != null) {
                    patient.setGender(updatePatient.getGender());
                }

                if (updatePatient.getBirth() != null) {
                    patient.setBirth(updatePatient.getBirth());
                }

                if (updatePatient.getNationalId() != null) {
                    patient.setNationalId(updatePatient.getNationalId());
                }

                if (updatePatient.getAddress() != null) {
                    patient.setAddress(updatePatient.getAddress());
                }

                if (updatePatient.getEmail() != null) {
                    patient.setEmail(updatePatient.getEmail());
                }

                if (updatePatient.getBlood() != null) {
                    patient.setBlood(updatePatient.getBlood());
                }

                if (updatePatient.getCardId() != null) {
                    patient.setCardId(updatePatient.getCardId());
                }

                if (updatePatient.getVip() != null) {
                    patient.setVip(updatePatient.getVip());
                }

                if (updatePatient.getEmergencyName() != null) {
                    patient.setEmergencyName(updatePatient.getEmergencyName());
                }

                if (updatePatient.getEmergencyPhone() != null) {
                    patient.setEmergencyPhone(updatePatient.getEmergencyPhone());
                }

                if (updatePatient.getDeleteDate() != null) {
                    patient.setDeleteDate(updatePatient.getDeleteDate());
                }

                if (updatePatient.getScaling() != null) {
                    patient.setScaling(updatePatient.getScaling());
                }

                if (updatePatient.getLineId() != null) {
                    patient.setLineId(updatePatient.getLineId());
                }

                if (updatePatient.getFbId() != null) {
                    patient.setFbId(updatePatient.getFbId());
                }

                if (updatePatient.getNote() != null) {
                    patient.setNote(updatePatient.getNote());
                }

                if (updatePatient.getClinicNote() != null) {
                    patient.setClinicNote(updatePatient.getClinicNote());
                }

                if (updatePatient.getWriteIcTime() != null) {
                    patient.setWriteIcTime(updatePatient.getWriteIcTime());
                }

                // introducer
                if (updatePatient.getIntroducer() != null) {
                    log.debug("Update introducer({}) of Patient(id: {})", updatePatient.getIntroducer(), updatePatient.getId());
                    patient.setIntroducer(patientRepository.findById(updatePatient.getIntroducer().getId()).orElse(null));
                }

                // lastDoctor
                if (updatePatient.getLastDoctor() != null) {
                    log.debug("Update lastDoctor({}) of Patient(id: {})", updatePatient.getLastDoctor(), updatePatient.getId());
                    patient.setLastDoctor(extendUserRepository.findById(updatePatient.getLastDoctor().getId()).orElse(null));
                }

                // firstDoctor
                if (updatePatient.getFirstDoctor() != null) {
                    log.debug("Update firstDoctor({}) of Patient(id: {})", updatePatient.getFirstDoctor(), updatePatient.getId());
                    patient.setFirstDoctor(extendUserRepository.findById(updatePatient.getFirstDoctor().getId()).orElse(null));
                }

                // patientIdentity
                if (updatePatient.getPatientIdentity() != null) {
                    log.debug("Update patientIdentity({}) of Patient(id: {})", updatePatient.getPatientIdentity(), updatePatient.getId());
                    patient.setPatientIdentity(patientIdentityRepository.findById(updatePatient.getPatientIdentity().getId()).orElse(null));
                }

                // teeth
                if (updatePatient.getTeeth() != null) {
                    log.debug("Update teeth({}) of Patient(id: {})", updatePatient.getTeeth(), updatePatient.getId());
                    relationshipService.deleteRelationshipWithTeeth(
                        patient.getTeeth(),
                        updatePatient.getTeeth().stream().map(Tooth::getId).collect(Collectors.toSet())
                    );
                    relationshipService.addRelationshipWithTeeth(patient.teeth(updatePatient.getTeeth()));
                }

                return patient;
            })
            .get();
    }

    void setNewPatient(Patient patient) {
        patient.setNewPatient(
            patient
                .getAppointments()
                .stream()
                .filter(appointment -> appointment.getRegistration() != null)
                .count() <= 1
        );
    }

    private Questionnaire updateQuestionnaire(Questionnaire questionnaire, Questionnaire updateQuestionnaire) {
        if (updateQuestionnaire.isDrug() != null) {
            questionnaire.setDrug(updateQuestionnaire.isDrug());
        }

        if (updateQuestionnaire.getDrugName() != null) {
            questionnaire.setDrugName(updateQuestionnaire.getDrugName());
        }

        if (updateQuestionnaire.getGlycemicAC() != null) {
            questionnaire.setGlycemicAC(updateQuestionnaire.getGlycemicAC());
        }

        if (updateQuestionnaire.getGlycemicPC() != null) {
            questionnaire.setGlycemicPC(updateQuestionnaire.getGlycemicPC());
        }

        if (updateQuestionnaire.getSmokeNumberADay() != null) {
            questionnaire.setSmokeNumberADay(updateQuestionnaire.getSmokeNumberADay());
        }

        if (updateQuestionnaire.getOtherInTreatment() != null) {
            questionnaire.setOtherInTreatment(updateQuestionnaire.getOtherInTreatment());
        }

        return questionnaire;
    }

    private Treatment createGeneralTreatmentAndPlanAndTask(Patient patient) {
        log.debug("Request to create general Treatment");

        Treatment treatment = new Treatment().name("General Treatment").type(TreatmentType.GENERAL).patient(patient);
        treatment = treatmentRepository.save(treatment);

        TreatmentPlan treatmentPlan = new TreatmentPlan().activated(true).name("General TreatmentPlan").treatment(treatment);
        treatmentPlan = treatmentPlanRepository.save(treatmentPlan);
        treatment.getTreatmentPlans().add(treatmentPlan);

        TreatmentTask treatmentTask = new TreatmentTask().name("General TreatmentTask").treatmentPlan(treatmentPlan);
        treatmentTask = treatmentTaskRepository.save(treatmentTask);
        treatmentPlan.getTreatmentTasks().add(treatmentTask);

        return treatment;
    }

    /**
     * Function to convert PatientCriteria to a {@link Specification}
     */
    private Specification<Patient> createSpecification(PatientCriteria criteria) {
        Specification<Patient> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getQuestionnaireId() != null) {
                specification = specification.and(buildSpecification(criteria.getQuestionnaireId(),
                    root -> root.join(Patient_.questionnaire, JoinType.LEFT).get(Questionnaire_.id)));
            }

            if (criteria.getNewPatient() != null) {
                specification = specification.and(buildSpecification(criteria.getNewPatient(), Patient_.newPatient));
            }

            if (criteria.getDeleteDate() != null) {
                specification = specification.and(buildSpecification(criteria.getDeleteDate(), Patient_.deleteDate));
            }

            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), Patient_.createdDate));
            } else {
                if (FilterUtil.predicateIsNull.test(criteria.getQuestionnaireId())) {
                    // default createdDate is today
                    Instant start = OffsetDateTime.now().toZonedDateTime().with(LocalTime.MIN).toInstant();
                    Instant end = OffsetDateTime.now().toZonedDateTime().with(LocalTime.MAX).toInstant();
                    specification = specification.and(buildRangeSpecification(new InstantFilter().setGreaterOrEqualThan(start).setLessOrEqualThan(end), Patient_.createdDate));
                }
            }

            if (criteria.getSearch() != null && criteria.getSearch().getContains() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSearch(), Patient_.name)
                    .or(buildStringSpecification(criteria.getSearch(), Patient_.phone))
                    .or((root, query, builder) -> builder.like(builder.function("TO_CHAR", String.class, root.get(Patient_.birth), builder.literal("yyyyMMdd")), wrapLikeQuery(criteria.getSearch().getContains())))
                );
            }
        }

        return specification;
    }
}
