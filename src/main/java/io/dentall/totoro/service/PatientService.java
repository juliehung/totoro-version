package io.dentall.totoro.service;

import io.dentall.totoro.domain.*;
import io.dentall.totoro.domain.enumeration.TagName;
import io.dentall.totoro.repository.ExtendUserRepository;
import io.dentall.totoro.repository.PatientRepository;
import io.dentall.totoro.repository.QuestionnaireRepository;
import io.dentall.totoro.repository.TagRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Supplier;

/**
 * Service class for managing patients.
 */
@Service
public class PatientService {

    private final Logger log = LoggerFactory.getLogger(PatientService.class);

    private final PatientRepository patientRepository;

    private final TagRepository tagRepository;

    private final QuestionnaireRepository questionnaireRepository;

    private final ExtendUserRepository extendUserRepository;

    public PatientService(PatientRepository patientRepository, TagRepository tagRepository, QuestionnaireRepository questionnaireRepository, ExtendUserRepository extendUserRepository) {
        this.patientRepository = patientRepository;
        this.tagRepository = tagRepository;
        this.questionnaireRepository = questionnaireRepository;
        this.extendUserRepository = extendUserRepository;
    }

    public void setTagsByQuestionnaire(Set<Tag> tags, Questionnaire questionnaire) {
        if (questionnaire != null) {
            setTag(tags, TagName.Hypertension, () -> questionnaire.getHypertension() != null);
            setTag(tags, TagName.LiverDiseases, () -> questionnaire.getLiverDiseases() != null || questionnaire.getHepatitisType() != null);
            setTag(tags, TagName.Smoker, () -> questionnaire.getSmokeNumberADay() != null);
            setTag(tags, TagName.Pregnancy, () -> questionnaire.getProductionYear() != null && questionnaire.getProductionMonth() != null);
            setTag(tags, TagName.DifficultExtractionOrContinuousBleeding,
                () -> questionnaire.isDifficultExtractionOrContinuousBleeding() != null && questionnaire.isDifficultExtractionOrContinuousBleeding());
            setTag(tags, TagName.NauseaOrDizziness,
                () -> questionnaire.isNauseaOrDizziness() != null && questionnaire.isNauseaOrDizziness());
            setTag(tags, TagName.AdverseReactionsToAnestheticInjections,
                () -> questionnaire.isAdverseReactionsToAnestheticInjections() != null && questionnaire.isAdverseReactionsToAnestheticInjections());
        }
    }

    @Transactional
    public Optional<Patient> updatePatient(Patient updatePatient) {
        return patientRepository.findById(updatePatient.getId()).map(patient -> {
            // questionnaire
            if (updatePatient.getQuestionnaire() != null) {
                log.debug("Update questionnaire({}) of Patient(id: {})", updatePatient.getQuestionnaire(), updatePatient.getId());
                Questionnaire questionnaire = patient.getQuestionnaire();
                Questionnaire updateQuestionnaire = updatePatient.getQuestionnaire();
                patient.setQuestionnaire(questionnaire == null ? questionnaireRepository.save(updateQuestionnaire) : updateQuestionnaire(questionnaire, updateQuestionnaire));
                setTagsByQuestionnaire(patient.getTags(), patient.getQuestionnaire());
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

            if (updatePatient.getMedicalId() != null) {
                patient.setMedicalId(updatePatient.getMedicalId());
            }

            if (updatePatient.getAddress() != null) {
                patient.setAddress(updatePatient.getAddress());
            }

            if (updatePatient.getEmail() != null) {
                patient.setEmail(updatePatient.getEmail());
            }

            if (updatePatient.getPhoto() != null) {
                patient.setPhoto(updatePatient.getPhoto());
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

            if (updatePatient.getReminder() != null) {
                patient.setReminder(updatePatient.getReminder());
            }

            if (updatePatient.getWriteIcTime() != null) {
                patient.setWriteIcTime(updatePatient.getWriteIcTime());
            }

            if (updatePatient.getBurdenCost() != null) {
                patient.setBurdenCost(updatePatient.getBurdenCost());
            }

            // introducer
            if (updatePatient.getIntroducer() != null) {
                log.debug("Update introducer({}) of Patient(id: {})", updatePatient.getIntroducer(), updatePatient.getId());
                patient.setIntroducer(patientRepository.findById(updatePatient.getIntroducer().getId()).orElse(null));
            }

            // dominantDoctor
            if (updatePatient.getDominantDoctor() != null) {
                log.debug("Update dominantDoctor({}) of Patient(id: {})", updatePatient.getDominantDoctor(), updatePatient.getId());
                patient.setDominantDoctor(extendUserRepository.findById(updatePatient.getDominantDoctor().getId()).orElse(null));
            }

            // firstDoctor
            if (updatePatient.getFirstDoctor() != null) {
                log.debug("Update firstDoctor({}) of Patient(id: {})", updatePatient.getFirstDoctor(), updatePatient.getId());
                patient.setFirstDoctor(extendUserRepository.findById(updatePatient.getFirstDoctor().getId()).orElse(null));
            }

            return patient;
        });
    }

    private Questionnaire updateQuestionnaire(Questionnaire questionnaire, Questionnaire updateQuestionnaire) {
        if (updateQuestionnaire.getHypertension() != null) {
            questionnaire.setHypertension(updateQuestionnaire.getHypertension());
        }

        if (updateQuestionnaire.getHeartDiseases() != null) {
            questionnaire.setHeartDiseases(updateQuestionnaire.getHeartDiseases());
        }

        if (updateQuestionnaire.getKidneyDiseases() != null) {
            questionnaire.setKidneyDiseases(updateQuestionnaire.getKidneyDiseases());
        }

        if (updateQuestionnaire.getBloodDiseases() != null) {
            questionnaire.setBloodDiseases(updateQuestionnaire.getBloodDiseases());
        }

        if (updateQuestionnaire.getLiverDiseases() != null) {
            questionnaire.setLiverDiseases(updateQuestionnaire.getLiverDiseases());
        }

        if (updateQuestionnaire.getHepatitisType() != null) {
            questionnaire.setHepatitisType(updateQuestionnaire.getHepatitisType());
        }

        if (updateQuestionnaire.getGastrointestinalDiseases() != null) {
            questionnaire.setGastrointestinalDiseases(updateQuestionnaire.getGastrointestinalDiseases());
        }

        if (updateQuestionnaire.getReceivingMedication() != null) {
            questionnaire.setReceivingMedication(updateQuestionnaire.getReceivingMedication());
        }

        if (updateQuestionnaire.getAnyAllergySensitivity() != null) {
            questionnaire.setAnyAllergySensitivity(updateQuestionnaire.getAnyAllergySensitivity());
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

        if (updateQuestionnaire.getProductionYear() != null) {
            questionnaire.setProductionYear(updateQuestionnaire.getProductionYear());
        }

        if (updateQuestionnaire.getProductionMonth() != null) {
            questionnaire.setProductionMonth(updateQuestionnaire.getProductionMonth());
        }

        if (updateQuestionnaire.getOther() != null) {
            questionnaire.setOther(updateQuestionnaire.getOther());
        }

        if (updateQuestionnaire.isDifficultExtractionOrContinuousBleeding() != null) {
            questionnaire.setDifficultExtractionOrContinuousBleeding(updateQuestionnaire.isDifficultExtractionOrContinuousBleeding());
        }

        if (updateQuestionnaire.isNauseaOrDizziness() != null) {
            questionnaire.setNauseaOrDizziness(updateQuestionnaire.isNauseaOrDizziness());
        }

        if (updateQuestionnaire.isAdverseReactionsToAnestheticInjections() != null) {
            questionnaire.setAdverseReactionsToAnestheticInjections(updateQuestionnaire.isAdverseReactionsToAnestheticInjections());
        }

        if (updateQuestionnaire.getOtherInTreatment() != null) {
            questionnaire.setOtherInTreatment(updateQuestionnaire.getOtherInTreatment());
        }

        return questionnaire;
    }

    private void setTag(Set<Tag> tags, TagName tagName, Supplier<Boolean> condition) {
        Optional<Tag> tag = tagRepository.findById(tagName.getValue());
        if (condition.get()) {
            tag.ifPresent(tags::add);
        } else {
            tag.ifPresent(tags::remove);
        }
    }
}
